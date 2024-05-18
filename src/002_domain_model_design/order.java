public class Product {}
public class Money {}

public class ShppingInfo {
    private String receiverName;
    private String receiverPhoneNumber;
    private String shippingAddress1;
    private String shippingAddress2;
    private String shippingZipcode;
}

public class Order {
    private List<OrderLine> orderLines;
    private ShippingInfo shippingInfo;
    private OrderState state;
    private Money totalAmounts;
    
    // Constructor
    public Order(List<OrderLine> orderLines, ShippingInfo shippingInfo) {
        setOrderLines(orderLines);
        setShippingInfo(shippingInfo);
    }

    // Basic Implements
    public void changeShipped() {}
    public void changeShippingInfo(ShppingInfo newShippingInfo) {
        verifyNotYetShipped();
        setShippingInfo(newShippingInfo);
    }
    public void cancel() {
        verifyNotYetShipped();
        this.state = OrderState.CANCELED;
    }
    public void completePayment() {}
    public void verifyNotYetShipped() {
        if (state != OrderState.PRIVATE_WAITING && state !=OrderState.PREPARING)
            throw new IllegalArgumentsException("Already Shipped");
    }

    // Advacned Implements for OrderLines
    private void setOrderLines(List<OrderLine> orderLines) {
        verifyAtleastOneOrMoreOrderLines(orderLines);

        this.orderLines = orderLines;
        calculateTotalAmounts();
    }

    private void verifyAtleastOneOrMoreOrderLines(List<OrderLine> orderLines) {
        if (orderLines == null || orderLines.isEmpty()) {
            throw new IllegalArgumentsException("No orderLines");
        }
    }

    private void calculateTotalAmounts() {
        int sum = orderLines.stream().mapToInt(x -> x.getAmounts()).sum();
        
        this.totalAmounts = new Money(sum);
    }

    // Advanced Implements for ShippingInfo
    private void setShippingInfo(ShippingInfo shippingInfo) {
        if (shippingInfo == null)
            throw new IllegalArgumentsException("Now Shipping Info");

        this.shippingInfo = shippingInfo;
    }
}

public class OrderLine {
    private Product product;
    private int price;
    private int quantity;
    private int amounts;

    public OrderLine(Product product, int price, int quantity) {
        this.product = product;
        this.price = price;
        this.quantity = quantity;
        this.amounts = calculateAmounts();
    }

    public int calculateAmounts() {
        return price * quantity;
    }

    public int getAmounts() {}
}

public enum OrderState {
    PRIVATE_WAITING, PREPARING, SHIPPED, DELIVERING, DELIVERY_COMPLETED, CANCELED;
}