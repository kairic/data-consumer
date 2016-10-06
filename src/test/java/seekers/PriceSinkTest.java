package seekers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class PriceSinkTest {

    private PriceSink priceSinkToTest = new PriceSink();

    @Mock
    private PriceRepository mockRepository;

    @Before
    public void init() {
        priceSinkToTest.setRepository(mockRepository);
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistNullPrice() {
        priceSinkToTest.persistPrice(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void payloadIsNotAString() {
        priceSinkToTest.persistPrice(new Integer(100));
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidPayloadStringFormat() {
        priceSinkToTest.persistPrice("100");
    }

    @Test
    public void invalidPayloadStringFormatWithMultipleSeparator() {
        priceSinkToTest.persistPrice("100@2016-12-25@abcd");
        verifyZeroInteractions(mockRepository);
    }

    @Test
    public void savePayloadToRepository() {
        priceSinkToTest.persistPrice("100@2016-12-25");
        verify(mockRepository).save(ArgumentCaptor.forClass(Price.class).capture());
    }
}
