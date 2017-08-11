package grocerystore;

import java.io.IOException;
import java.util.Map;

/**
 * A Reshelver can access the location of a <code>Product</code>, and its order history and current
 * quantity in store.
 */
public class Reshelver extends Worker {

  /**
   * Creates a Reshelver.
   *
   * @param h A <code>History</code> object managed by this Reshelver.
   * @param id The unique worker ID of this Reshelver.
   * @param name The name of this Reshelver.
   */
  public Reshelver(History h, String id, String name) {
    super(h, id, name);
    this.setWorkerType("Reshelver");
  }

  /**
   * Makes a record in <code>History</code> that all products from the <code>Receiver</code> have
   * been placed onto shelves.
   */
  public void reshelvesCart(Inventory inventory) throws IOException {
    Map<Product, Integer> cartFromReceiver = inventory.getStoredCart();
    if (cartFromReceiver != null) {
      history.recordShelved(this, cartFromReceiver);
    }
    performMeasure += 1;
  }

  /**
   * FOR PHASE 2 This method helps this Reshelver to get the order history for a Product.
   *
   * @param upc The product whose order history is requested.
   * @return The order history of product.
   */
  public Map<String, Integer> getOrderHist(String upc, Inventory inventory) {
    return history.getOrderHistory(inventory.getProduct(upc));
  }

  /**
   * Returns a String representation of this Reshelver.
   *
   * @return A String representation of this Reshelver.
   */
  public String toString() {
    return "Reshelver " + id + " " + name;
  }
}


