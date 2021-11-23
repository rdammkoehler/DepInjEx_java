import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClassicBOMCalculator {
    /**
     * Get the totalCost of the BOM Line Items from the database
     *
     * @param bomId The Unique ID of the BOM in the database
     * @return The sum of line item costs
     */
    public BigDecimal getTotalCost(BigInteger bomId) {
        return new BomFetcher().lineItems(bomId).stream().map(lineItem -> lineItem.getCost()).reduce(BigDecimal.ZERO, (t, v) -> t.add(v));
    }

}

class LineItem {
    private BigDecimal cost;

    LineItem(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getCost() {
        return cost;
    }
}

class BomFetcher {
    List<LineItem> lineItems(BigInteger bomId) {
        String url = "jdbc:mysql://localhost:3306/MyDatabase";
        String user = "root";
        String password = "1234";
        List<LineItem> lineItems = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            String sql = "select Cost from LineItem where BOMId = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, bomId.intValue());

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                BigDecimal cost = result.getBigDecimal("Cost");
                lineItems.add(new LineItem(cost));
            }
            conn.close();
        } catch (SQLException sqle) {
            throw new RuntimeException("failed to retrieve data", sqle);
        }
        return lineItems;
    }
}