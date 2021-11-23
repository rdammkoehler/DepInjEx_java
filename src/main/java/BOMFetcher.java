import java.math.BigInteger;
import java.util.List;

public interface BOMFetcher {
    List<BOMLineItem> lineItems(BigInteger bomId);
}
