package grocerystore;

import java.io.Serializable;

/**
 * A Product has properties such as name and UPC. It can be re-ordered from a
 * distributor when current stock is below a certain threshold, can be put on sale, can
 * be sold to a customer, etc.
 */
public class Product implements Serializable {

  String section; //The product type of this Product.
  boolean backOrderStatus; //True if the stock of this Product is <= 0
  boolean needReorder;
  int reorderQuantity; //Default quantity for re-ordering is 3 times the threshold
  double costPerQ; //Ordering price from a supplier
  private String subSection;
  private int threshold; //The quantity below which this Product is automatically re-ordered.
  private String name;
  private String UPC;
  private String distributor;
  private int aisleNo;
  private int stock;
  private double currPricePerQ;
  private double regularPricePerQ;
  private OnSaleInfo onsaleInfo; //The on-sale info for this Product, if any
  private PendingOrder pendingOrder; //The associated pending order info for this Product, if any

  //individuals who hold membership status can get the membership deduction percentage off of the
  //current price of the product.
  private double membershipDeduction = 0.10;

  // the price of the product after considering membership status.
  private double membershipPriceQ;

  //the minimum percentage the store makes above the cost of the product, regardless of discounts.
  private double minimumProfitPercentage = 0.2;

  //the least possible price this product can be sold at due to the set minimum profit percentage.
  private double  maximumDiscountPrice = costPerQ + (costPerQ * minimumProfitPercentage);

  /**
   * Creates a Product, by initializing its related information.
   *
   * @param productInfo An ArrayList consists of product information in the following order: UPC
   * (Universal Price Code), initial quantity, section (product type), subsection (sub product
   * type), name, threshold, initial aisle number, initial price per unit, the name of the supplier
   * for this Product, supplier cost per unit.
   */
  public Product(String[] productInfo) {
    UPC = productInfo[0];
    stock = Integer.parseInt(productInfo[1]);
    section = productInfo[2];
    subSection = productInfo[3];
    name = productInfo[4];
    threshold = Integer.parseInt(productInfo[5]);
    aisleNo = Integer.parseInt(productInfo[6]);
    regularPricePerQ = Double.parseDouble(productInfo[7]);
    distributor = productInfo[8];
    costPerQ = Double.parseDouble(productInfo[9]);

    needReorder = false;
    currPricePerQ = regularPricePerQ;
    reorderQuantity = 3 * threshold;

    pendingOrder = new PendingOrder(this);
    onsaleInfo = new OnSaleInfo(this);
  }

  /**
   * Returns the current reorder quantity of this Product.
   *
   * @return The current reorder quantity of this Product.
   */
  int getReorderQ() {
    return reorderQuantity;
  }

  /**
   * Returns the membership price per unit of this Product.
   *
   * @return The membership price per unit of this Product.
   */
  double getMemberPrice() {
    membershipPriceQ = currPricePerQ - (currPricePerQ * membershipDeduction);
    return membershipPriceQ;
  }

  /**
   * For every Product, the default re-order quantity is 3 times its threshold quantity. But the
   * store can change this re-order quantity also.
   *
   * @param newQuantity A user-specified re-order quantity which will be used for this Product.
   */
  public void setReorderQ(int newQuantity) {
    reorderQuantity = newQuantity;
  }

  /**
   * Returns the threshold of this Product.
   *
   * @return The threshold of this Product.
   */
  int getThreshold(){return threshold;}

  /**
   * Returns the name of this Product.
   *
   * @return The name of this Product as a String.
   */
  String getName() {
    return name;
  }

  /**
   * Returns the UPC of this Product.
   *
   * @return The UPC of this Product as a String.
   */
  String getUPC() {
    return UPC;
  }

  /**
   * Returns where in the store this Product can be found.
   *
   * @return The aisle number of this Product.
   */
  int getAisleNo() {
    return aisleNo;
  }

  /**
   * This method can change the aisle number of this Product.
   *
   * @param newAisleNo A new aisle number needs to be associated with this Product.
   */
  void setAisleNo(int newAisleNo) {
    aisleNo = newAisleNo;
  }

  /**
   * Gets the current price of this Product.
   *
   * @return The current price of this Product.
   */
  double getCurrPrice() {
    return currPricePerQ;
  }

  /**
   * This method can change the current price per unit of this Product.
   *
   * @param price A new current price needs to be associated with this Product.
   */
  void setCurrPrice(double price) {
    currPricePerQ = price;
  }

  /**
   * Gets the regular price of this Product.
   *
   * @return The regular price of this Product.
   */
  double getRegPrice() {
    return regularPricePerQ;
  }

  /**
   * This method changes the regular price of this Product. For example, during different seasons,
   * the store may have different regular prices for this Product.
   *
   * @param newRegP A new regular price for this Product.
   */
  void setRegPrice(double newRegP) {
    regularPricePerQ = newRegP;
  }

  /**
   * Gets the on-sale information object of this Product.
   *
   * @return The on-sale information object of this Product.
   */
  OnSaleInfo getOnSaleInfo() {
    return onsaleInfo;
  }

  /**
   * Gets the pending order request of this Product.
   *
   * @return The pending order request of this Product.
   */
  PendingOrder getPendingOrder() {
    return pendingOrder;
  }

  /**
   * Returns the unique distributor for this Product.
   *
   * @return The unique distributor for this Product.
   */
  String getDistributor() {
    return distributor;
  }

  /**
   * Returns the current stock of this Product.
   *
   * @return The current stock of this Product.
   */
  int getStock() {
    return stock;
  }

  /**
   * Returns the profit when one unit of this Product is sold.
   *
   * @return Profit per unit which equals to the current price per unit minus the supplier cost per
   * unit.
   */
  public double getProfit(boolean membershipStatus) {

    //Checks whether to use an added reduction on this products price due to the customer having
    //membership status or not.
    double productPrice;
    if (membershipStatus) {
      productPrice = membershipPriceQ;
    } else {
      productPrice = currPricePerQ;
    }

    //ensures that profit is always earned by checking the result of a maximum discount price on
    //the product and not following lower than this price or else the store is at a disadvantage.
    if (productPrice > maximumDiscountPrice) {
      return productPrice - costPerQ;
    } else {
      return maximumDiscountPrice - costPerQ;
    }


  }

  /**
   * This method changes the re-ordering threshold quantity for this Product. After setting the
   * threshold, if the stock of this Product is less than the new threshold, a pending re-ordering
   * request will be made and waits for a manager to approve.
   *
   * @param newThreshold A new threshold for this Product.
   * @return true if below threshold
   */
  boolean setThreshold(int newThreshold) {
    threshold = newThreshold;
    if ((stock < threshold) && !needReorder) {
      needReorder = true;
      pendingOrder.addPendingOrder();
      pendingOrder.setPendingTrue();
      return true;
    } else {
      return false;
    }
  }

  /**
   * This method is called when one item of this Product is sold, so the stock of this Product
   * decreases by 1. If the stock is less than the threshold, a pending re-order request will be
   * made and waits for a manager to approve.
   */
  void soldOne() {
    stock -= 1;

    if (stock < threshold && !needReorder) {
      needReorder = true;
    }
    if (stock <= 0 && !backOrderStatus) {

      backOrderStatus = true;
    }
  }

  /**
   * This method is called when one item of this Product is added to the store's inventory, so the
   * stock of this Product increases by 1.
   */
  void addOne() {
    if (backOrderStatus) {
      backOrderStatus = false;
    }
    stock += 1;
  }

  /**
   * Return the name of the product
   *
   * @return The name of the product
   */
  public String toString() {
    return this.getName() + " : " + this.getUPC();
  }
}
