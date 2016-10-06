package seekers;

import com.google.common.collect.Lists;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class PriceServiceTest {

    private PriceService priceServiceToTest = new PriceService();

    @Mock
    private PriceRepository mockRepository;

    @Before
    public void init() {
        priceServiceToTest.setRepository(mockRepository);
    }

    @Test
    public void noDataInRepository() {
        Double result = priceServiceToTest.calculateAveragePrice(10);
        Assert.assertThat(result, Matchers.closeTo(0.0d, 0.0001d));
    }

    @Test
    public void emptyPageReturnFromRepository() {
        Mockito.when(mockRepository.findAll(ArgumentCaptor.forClass(PageRequest.class).capture())).thenReturn(new Page<Price>() {
            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return 0;
            }

            @Override
            public <S> Page<S> map(Converter<? super Price, ? extends S> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return 0;
            }

            @Override
            public int getNumberOfElements() {
                return 0;
            }

            @Override
            public List<Price> getContent() {
                return null;
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public Iterator<Price> iterator() {
                return null;
            }
        });
        Double result = priceServiceToTest.calculateAveragePrice(10);

    }

    @Test
    public void numberOfPricesIsSmallerThanRequestedNumber() {
        Mockito.when(mockRepository.findAll(ArgumentCaptor.forClass(PageRequest.class).capture())).thenReturn(new Page<Price>() {
            @Override
            public int getTotalPages() {
                return 1;
            }

            @Override
            public long getTotalElements() {
                return 1;
            }

            @Override
            public <S> Page<S> map(Converter<? super Price, ? extends S> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return 1;
            }

            @Override
            public int getSize() {
                return 1;
            }

            @Override
            public int getNumberOfElements() {
                return 1;
            }

            @Override
            public List<Price> getContent() {
                return Lists.newArrayList(new Price("20", LocalDateTime.now(Clock.systemUTC()).toString()));
            }

            @Override
            public boolean hasContent() {
                return true;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public Iterator<Price> iterator() {
                return null;
            }
        });
        Double result = priceServiceToTest.calculateAveragePrice(10);
        Assert.assertThat(result, Matchers.closeTo(20.0d, 0.0001d));
    }

    @Test
    public void numberOfPricesIsLargerThanRequestedNumber() {
        Mockito.when(mockRepository.findAll(ArgumentCaptor.forClass(PageRequest.class).capture())).thenReturn(new Page<Price>() {
            @Override
            public int getTotalPages() {
                return 1;
            }

            @Override
            public long getTotalElements() {
                return 1;
            }

            @Override
            public <S> Page<S> map(Converter<? super Price, ? extends S> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return 1;
            }

            @Override
            public int getSize() {
                return 1;
            }

            @Override
            public int getNumberOfElements() {
                return 1;
            }

            @Override
            public List<Price> getContent() {
                return Lists.newArrayList(
                        new Price("20", LocalDateTime.now(Clock.systemUTC()).toString()),
                        new Price("21", LocalDateTime.now(Clock.systemUTC()).toString()),
                        new Price("19", LocalDateTime.now(Clock.systemUTC()).toString()),
                        new Price("23", LocalDateTime.now(Clock.systemUTC()).toString())
                );
            }

            @Override
            public boolean hasContent() {
                return true;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public Iterator<Price> iterator() {
                return null;
            }
        });
        Double result = priceServiceToTest.calculateAveragePrice(3);
        Assert.assertThat(result, Matchers.closeTo(27.666d, 0.001d));
    }

    }
