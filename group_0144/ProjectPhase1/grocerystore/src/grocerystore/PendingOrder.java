package grocerystore;

import java.io.Serializable;
import java.util.ArrayList;

public class PendingOrder implements Serializable {

  private ArrayList<Object> pendingOrder; //A pending order request waiting for a manager's approval
  private Product product; //The Product that is associated with this PendingOrder
  private String name;
  private String upc;
  private String distributor;
  private int reOrderQuantity;
  private boolean isPending;

  /**
   * Creates a PendingOrder by associating with the corresponding Product.
   * @param product The Product that is associated with this PendingOrder.
   */
  public PendingOrder(Product product) {
    this.product = product;
    this.isPending = false;
  }

  /**
   * The store can make a pending ordering request to the unique distributor for
   * product (Any given product will only be ordered from one distributor). This pending ordering
   * request needs to be approved by a manager. A manager can also edit this pending order using the
   * editPendingOrder method.
   */
  void addPendingOrder() {
    this.name = product.getName();
    this.upc = product.getUPC();
    this.distributor = product.getDistributor();
    this.reOrderQuantity = product.getReorderQ();
  }

  /**
   * Return if the product's order is pending.
   * @return whether it is pending
   */
  boolean isPending() {
    return isPending;
  }

  /**
   * Set pending order to be true.
   */
  void setPendingTrue() {
    isPending = true;
  }

  /**
   * Get the reorder quantity for this pending order
   */
  int getPendingOrderQuantity() {
    return this.reOrderQuantity;
  }

  /**
   * Set the reorderquantity for this pending order
   *
   * @param q the new pending order quantity for the product.
   */
  void setPendingOrderQuantity(int q) {
    this.reOrderQuantity = q;
  }

  /**
   * A manager can use this method to clear a pending ordering request if he/she wants to approves
   * the request and send it to a distributor, or he/she wants to cancel the pending ordering
   * request.
   */
  void clearPendingOrder() {
    this.reOrderQuantity = product.reorderQuantity;
    this.isPending = false;
  }

}
