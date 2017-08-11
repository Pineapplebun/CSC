package grocerystore;

import java.io.IOException;

/**
 * A Cashier working for the store, who can scan items to see the prices, add items to a cart,
 * checkout items from a cart, and other functions.
 */
public class Cashier extends Worker {

  /*
    cartTotal is the price displayed on the screen for the customer and cashier.
   */
  private Double cartTotal = 0.0;
  private boolean membershipCartStatus = false;

  /**
   * Creates a Cashier.
   *
   * @param h A <code>History</code> object.
   * @param id The worker identifier of this Cashier.
   * @param name The name of this Cashier.
   */
  public Cashier(History h, String id, String name) {
    super(h, id, name);
    this.setWorkerType("Cashier");
  }

  /**
   * Adds a Product with a specified quantity to a cart.
   *
   * @param upc the unique UPC of this product.
   * @param q the number of times this product is scanned (i.e. the quantity).
   */
  public void addToCart(String upc, Integer q, Inventory inventory) {
    Product p = inventory.getProduct(upc);
    super.addToCart(upc, q, inventory);
    cartTotal = cartTotal + p.getCurrPrice() * q;
  }

  /**
   * Clears the cart.
   */
  public void clearCart() {
    cart.clear();
    cartTotal = 0.0;
    membershipCartStatus = false;
  }

  /**
   * Returns the total price for a cart.
   *
   * @return The total price for a cart.
   */
  public Double getCartTotal() {
    Double cartTotal = 0.0;
    for (Product p : cart.keySet()) {
      if(membershipCartStatus)
      {
        cartTotal += p.getMemberPrice() * cart.get(p);
      }
      else
      {
        cartTotal += p.getCurrPrice() * cart.get(p);
      }
    }
    return cartTotal;
  }

  // this is called before checking out
  public void hasMembershipStatus(String memberId, Inventory inventory) {
    if (inventory.isAMember(memberId)) {
      membershipCartStatus = true;
    }
  }
//  void addMember(String memberId, String firstName, String lastName, String phoneNum,
//      Inventory inventory){
//    String[] memberInfo = new String[4];
//    memberInfo[0] = memberId;
//    memberInfo[1] = firstName;
//    memberInfo[2] = lastName;
//    memberInfo[3] = phoneNum;
//    inventory.addOneMember(memberId, memberInfo);
//  }

  /**
   * Processes the items in a cart. Reduces the number of items in inventory and sends transaction
   * information to the History.
   * @param inventory the inventory to look in
   * @throws IOException Throws an IOException.
   */
  public void checkout(Inventory inventory) throws IOException {
    Double totalProfit = 0.0;
    for (Product p : cart.keySet()) {
      Integer q = 0;
      Integer quantity = cart.get(p);
      while (q < quantity) {
        inventory.sellProduct(p); // should this be product or upc
        totalProfit += p.getProfit(membershipCartStatus);
        q++;

        // Checks if below threshold
        if (p.needReorder && !p.getPendingOrder().isPending()) {
          PendingOrder order= p.getPendingOrder();
          order.addPendingOrder();
          order.setPendingTrue();
          inventory.addPendingOrder(p);
        }
      }
    }
    // Tells History that a transaction has gone through
    history.recordSell(this, cart, totalProfit);
    performMeasure += 1;
    clearCart();
  }

  /**
   * Processes return of the items in a cart. Increases the number of items in inventory and sends
   * the items to the storage room so they can be reshelved later with other items. Send how much
   * loss was counted for this return to History.
   */
  public void customerReturn(Inventory inventory) throws IOException {
    Double totalLoss = 0.0;
    inventory.cartToStorage(cart);
    for (Product p : cart.keySet()) {
      Integer q = 0;
      Integer quantity = cart.get(p);
      String upc = p.getUPC();
      while (q < quantity) {
        inventory.receivedProduct(upc); // should this be product or upc
        totalLoss -= p.getProfit(membershipCartStatus);
        q++;
      }
    }
    // Tells History that a transaction has gone through
    history.recordReturn(this, totalLoss);
    performMeasure += 1;
    clearCart();
  }

  /**
   * Returns a String representation of this Cashier.
   *
   * @return A String representation of this Cashier.
   */
  public String toString() {
    return "Cashier " + id + " " + name;
  }
}

