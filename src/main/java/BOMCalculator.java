import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Calculates things about a BOM from the database
 */
public class BOMCalculator {
    private final BOMFetcher bomFetcher;

    /**
     * Create a BOM Calculator that can be used to calculate the Cost of BOM
     * @param bomFetcher An Object to fetch data from the database
     */
    public BOMCalculator(BOMFetcher bomFetcher) {
        this.bomFetcher = bomFetcher;
    }

    /**
     * Get the totalCost of the BOM Line Items from the database
     * @param bomId The Unique ID of the BOM in the database
     * @return The sum of line item costs
     */
    public BigDecimal getTotalCost(BigInteger bomId) {
        return bomFetcher.lineItems(bomId).stream().map(lineItem -> lineItem.getCost()).reduce(BigDecimal.ZERO, (t, v) -> t.add(v));
    }
}
