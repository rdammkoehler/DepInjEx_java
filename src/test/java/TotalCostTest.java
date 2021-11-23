import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TotalCostTest {
    @Test
    public void totalCost_ReturnsSumOfLineItemsInBOM_OneItem() {
        BigDecimal expectedSum = new BigDecimal(10.00);
        BOMFetcher bomFetcher = bomId -> Arrays.asList((BOMLineItem) () -> new BigDecimal(10.00));
        BOMCalculator bomCalculator = new BOMCalculator(bomFetcher);
        BigInteger bomId = new BigInteger("1");

        BigDecimal result = bomCalculator.getTotalCost(bomId);

        assertEquals(result, expectedSum);
    }

    @Test
    public void totalCost_ReturnsSumOfLineItemsInBOM_EightItems() {
        BigDecimal expectedSum = new BigDecimal(8.00);
        BOMFetcher bomFetcher = bomId -> {
            List<BOMLineItem> lineItems = new ArrayList<>();
            for (int idx = 0; idx < 8; idx++) {
                lineItems.add(() -> new BigDecimal(1.00));
            }
            return lineItems;
        };
        BOMCalculator bomCalculator = new BOMCalculator(bomFetcher);
        BigInteger bomId = new BigInteger("1");

        BigDecimal result = bomCalculator.getTotalCost(bomId);

        assertEquals(result, expectedSum);
    }

    @Test
    public void totalCost_ReturnsSumOfLineItemsInBOM_ZeroItems() {
        BigDecimal expectedSum = new BigDecimal(0);
        BOMFetcher bomFetcher = bomId -> new ArrayList<>();
        BOMCalculator bomCalculator = new BOMCalculator(bomFetcher);
        BigInteger bomId = new BigInteger("1");

        BigDecimal result = bomCalculator.getTotalCost(bomId);

        assertEquals(result, expectedSum);
    }

    @Test
    public void totalCost_RaisesAnExceptionIfTheFetcherFails() {
        BOMFetcher bomFetcher = bomId -> {
            throw new RuntimeException("Not Implemented");
        };
        BOMCalculator bomCalculator = new BOMCalculator(bomFetcher);
        BigInteger bomId = new BigInteger("1");

        assertThrows(RuntimeException.class, () -> bomCalculator.getTotalCost(bomId));
    }

}
