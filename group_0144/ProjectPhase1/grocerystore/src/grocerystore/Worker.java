package grocerystore;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * A worker of the store, who can be anyone including the employer and employees of the store.
 * Workers all have a cart to which they can add items and manipulate based on their status in the
 * store.
 */
abstract public class Worker implements Serializable {

  /**
   * name : the name of the worker
   * id : the unique id of the worker
   * active : whether the worker ahs signed in
   * cart : the cart that contains products
   * history : the worker's scribe that keeps track of its actions
   * workerType : the string type of the worker
   */
  String name;
  String id;
  boolean active; // If this Worker has an active working status
  Map<Product, Integer> cart = new LinkedHashMap<Product, Integer>();
  History history;
  private String workerType;

  /**
  This is a variable recording the number of activities done by this Worker. It is used for
  performance measurement.
  */
  int performMeasure = 0;

  /**
   * Creates a Worker.
   *
   * @param h A <code>History</code> object.
   * @param id The worker identifier of this Worker.
   * @param name The name of this Worker.
   */
  public Worker(History h, String id, String name) {
    history = h;
    this.name = name;
    this.id = id;
    createCart();
  }

  /**
   * Sets a string that denotes the type of the worker
   * @param workerType The worker type that is assigned to this Worker.
   */
  public void setWorkerType(String workerType){
    this.workerType = workerType;
  }

  /**
   * Sets a string that retrieves the type of the worker
   * @return A string of the type of the worker
   */
  public String getWorkerType(){
    return workerType;
  }

  /**
   * Returns the performance-measure indicator for this Worker.
   *
   * @return The performance-measure indicator for this Worker.
   */
  public int getPerMeasure() {
    return performMeasure;
  }

  /**
   * Instantiate a cart for this Worker.
   */
  private void createCart() {
    cart = new LinkedHashMap<>();
  }

  /**
   * Clears the cart.
   */
  public void clearCart() {
    cart.clear();
  }

  /**
   * Sets the working status of this Worker as Active.
   */
  public void setActive() {
    active = true;
  }

  /**
   * Sets the working status of this Worker as not Active.
   */
  public void setNotActive() {
    active = false;
  }

  /**
   * Returns true if the upc is a valid product
   *
   * @param upc the upc to check
   * @param inventory the inventory to look in
   * @return whether the upc is in the inventory
   */
  public boolean checkUPCValid(String upc, Inventory inventory) {
    Product p = inventory.getProduct(upc);
    return p != null;
  }

  /**
   * Adds a Product with specified quantity to a cart. If the product already exists, add the
   * quantity specified. Return true if it doesn't exist in the cart yet.
   *
   * @param upc The UPC of the product being added.
   * @param q The specified quantity of the product being added.
   * @param inventory the inventory to look in
   */
  public void addToCart(String upc, Integer q, Inventory inventory) {
    Product p = inventory.getProduct(upc);
    if (q >= 1 && q <= p.getStock()) {
      if (!checkInCart(p)) {
        cart.put(p, q);
      } else {
        Integer newQ = cart.get(p) + q;
        cart.put(p, newQ);
      }
    }
  }

  /**
   * Removes a upc from the cart.
   * @param upc the upc to remove
   * @param inventory the inventory to look in
   */
  public void removeFromCart(String upc, Inventory inventory) {
    cart.remove(inventory.getProduct(upc));
  }

  /**
   * Return if the cart is empty.
   * @return whether the cart is empty
   */
  public boolean isCartEmpty() {
    return cart.isEmpty();
  }

  /**
   * Return the cart.
   *
   * @return A map of the product to its quantity
   */
  public Map<Product, Integer> getCart() {
    return cart;
  }

  /**
   * Return whether the product is in the cart
   * @param p the product to check
   * @return whether the product is in the cart
   */
  private boolean checkInCart(Product p) {
    return cart.containsKey(p);
  }

  /**
   * Checks the cost per unit for a Product.
   *
   * @param upc The UPC of the requested Product.
   * @return A double variable showing the cost per unit for the requested Product.
   */
  public Double checkCost(String upc, Inventory inventory) {
    return inventory.getProduct(upc).costPerQ;
  }

  /**
   * Checks the location of a Product.
   *
   * @param upc The UPC of the requested Product.
   * @return An integer variable showing aisle number for the requested Product.
   */
  public Integer checkLocation(String upc, Inventory inventory) {
    if (checkUPCValid(upc, inventory)) {
      return inventory.getProduct(upc).getAisleNo();
    } else {
      return null;
    }
  }

  /**
   * Return the current quantity for a upc
   * @param upc the upc to check
   * @param inventory the inventory to look in
   * @return the stock of the product
   */
  public Integer checkCurrQuantity(String upc, Inventory inventory) {
    return inventory.getProduct(upc).getStock();
  }

  /**
   * Changes the quantity in a cart for a specific Product.
   *
   * @param upc The unique UPC of a Product.
   * @param q The number of items of the Product in the cart.
   */
  public void changeQuantity(String upc, int q, Inventory inventory) {
    Product p = inventory.getProduct(upc);
    cart.remove(p);
    if (q > 0) {
      cart.put(inventory.getProduct(upc), q);
    }
  }

  /**
   * Checks the regular price changes of all products since inital set up.
   *
   * @return A map of the product and its value as another map of dates and prices.
   */
  public Map<Product, Map<String, Double>> checkRegPriceHistory() {
    return history.getRegPriceHistory();
  }

  /**
   * Check the on-sale price changes of all products that have been on sale.
   *
   * @return A map of the product and its value as another map of dates and prices.
   */
  public Map<Product, Map<String, Double>> checkSalePriceHistory() {
    return history.getSalePriceHistory();
  }

  /**
   * Set reorder quantity of a product given its upc.
   *
   * @param upc the upc of the product
   * @param q the reorder quantity multiplier
   * @param inventory the inventory to look in
   */
  public void setReOrderQuantity(String upc, Integer q, Inventory inventory) {
    inventory.getProduct(upc).setReorderQ(q);
  }

  // ==================================== QUERIES ======================================

  /**
   * Return a map of the products to its locations.
   * @param inventory the inventory to look in
   * @return a string map of the product to its location
   */
  public Map<String, String> getLocations(Inventory inventory) {
    Map<String, String> answers = new LinkedHashMap<>();
    Set<Product> pList = cart.keySet();
    for (Product p : pList) {
      Integer n = p.getAisleNo();
      answers.put(p.getUPC(), n.toString());
    }
    return answers;
  }

  /**
   * Return a map of the products to its thresholds.
   * @param inventory the inventory to look in
   * @return a string map of the product to its threshold
   */
  public Map<String, String> getThresholds(Inventory inventory) {
    Map<String, String> answers = new LinkedHashMap<>();
    Set<Product> pList = cart.keySet();
    for (Product p : pList) {
      Integer n = p.getThreshold();
      answers.put(p.getUPC(), n.toString());
    }
    return answers;
  }

  /**
   * Return a map of the products to its prices.
   * @param inventory the inventory to look in
   * @return a string map of the product to its prices
   */
  public Map<String, String> getPrices(Inventory inventory) {
    Map<String, String> answers = new LinkedHashMap<>();
    Set<Product> pList = cart.keySet();
    for (Product p : pList) {
      Double n = p.getCurrPrice();
      answers.put(p.getUPC(), n.toString());
    }
    return answers;
  }

  /**
   * Return a map of the products to its sections.
   * @param inventory the inventory to look in
   * @return a string map of the product to its section
   */
  public Map<String, String> getSections(Inventory inventory) {
    Map<String, String> answers = new LinkedHashMap<>();
    Set<Product> pList = cart.keySet();
    for (Product p : pList) {
      answers.put(p.getUPC(), p.section);
    }
    return answers;
  }

  /**
   * Return a map of the products to its quantities.
   * @param inventory the inventory to look in
   * @return a string map of the product to its quantities
   */
  public Map<String, String> getQuantities(Inventory inventory) {
    Map<String, String> answers = new LinkedHashMap<>();
    Set<Product> pList = cart.keySet();
    for (Product p : pList) {
      Integer n = p.getStock();
      answers.put(p.getUPC(), n.toString());
    }
    return answers;
  }

  /**
   * Returns a String representation of this Worker.
   *
   * @return A String representation of this Worker.
   */
  public String toString() {
    return name;
  }
}
