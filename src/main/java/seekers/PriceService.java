package seekers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;

@Configuration
@EnableAutoConfiguration
@RestController
public class PriceService {

  private static final Logger LOGGER = LoggerFactory.getLogger(PriceService.class);

  private PriceRepository repository;

  @CrossOrigin(origins = "https://seekersweb.herokuapp.com")
  @RequestMapping(value = "/averagePrice/{numberOfPrices}", method=RequestMethod.GET)
  public Double calculateAveragePrice(@PathVariable("numberOfPrices") int numberOfPrices) {

    double sum = 0d;

    Page<Price> pricePage = repository.findAll(
            new PageRequest(0, numberOfPrices, new Sort(Sort.Direction.DESC, "timestamp")));
    if(pricePage == null) {
      return sum;
    }

    List<Price> prices = pricePage.getContent();
    if(prices == null || prices.size() == 0) {
      return sum;
    }

    for(Price each : prices) {
      sum += Integer.parseInt(each.getValue());
    }

    LOGGER.info("Sum=" + sum + ", size=" + prices.size() + ", input=" + numberOfPrices);

    if(numberOfPrices > prices.size()) {
      return sum/prices.size();
    }

    return sum/numberOfPrices;
  }

  @Autowired
  public void setRepository(PriceRepository repository) {
    this.repository = repository;
  }

}
