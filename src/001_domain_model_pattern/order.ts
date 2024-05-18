enum OrderState {
  PAYMENT_WAITING,
  PREPARING,
  SHIPPED,
  DELIVERING,
  DELIVERY_COMPLETED,
}

class IllegalStateException extends Error {}

class ShippingInfo {}

class Order {
  private state: OrderState;
  private shippingInfo: ShippingInfo;

  public changeShippingInfo(newShippingInfo: ShippingInfo): void {
    if (!this.isShippingChangeable())
      throw new IllegalStateException("Can't change shipping in " + this.state);

    this.shippingInfo = newShippingInfo;
  }

  private isShippingChangeable(): boolean {
    return (
      this.state == OrderState.PAYMENT_WAITING ||
      this.state == OrderState.PREPARING
    );
  }
}
