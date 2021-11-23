import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        BigInteger bomId = new BigInteger(args[0]);
        BigDecimal totalCost = new ClassicBOMCalculator().getTotalCost(bomId);
        System.out.println("The total cost of BOM " + bomId + " is " + totalCost.toString());
    }

    public static void main_(String[] args) {
        BigInteger bomId = new BigInteger(args[0]);
        BigDecimal totalCost = new BOMCalculator(new DBBomFetcher()).getTotalCost(bomId);
        System.out.println("The total cost of BOM " + bomId + " is " + totalCost.toString());
    }
}

class DBBomFetcher implements BOMFetcher {
    @Override
    public List<BOMLineItem> lineItems(BigInteger bomId) {
        List<LineItem> lineItems = new BomFetcher().lineItems(bomId);
        return lineItems.stream().map(LineItem::getCost).map(cost -> (BOMLineItem) () -> cost).collect(Collectors.toList());
    }
}
