package grocerystore;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The manager of the store. They can do the following tasks:
 * 1. check sale and regular price changes since initial set up and the current on sale items
 * 2. check profit histories from a start and end date and the total since initial set up
 * 3. check price changes of any items in any particular day
 * 4. change default threshold quantities a product
 * 5. change reorder quantities for a product
 * 6. change the regular price of a certain items
 * 7. add/remove items to the "on sale" list
 * 8. add/remove orders to the "order" list and edit pending order quantities
 * 9. create a current backorder file.
 */
public class Manager extends Worker {

  /**
   * OnSale : A list of products that are on sale mapped to their end date
   *
   * pendingOnSale : A list of product upc that will be on sale in the future mapped to a date array
   * that consists of its start and end dates and price
   *
   * pendingOrders : A list of product upc mapped to a quantity for reordering
   *
   * saleCart : A list of products mapped to a price
   *
   * currDate : The current time on the computer
   *
   * workerList : A list of workers under this manager
   */
  Map<Product, LocalDateTime> onSale;
  private Map<String, Map<LocalDateTime[], Double>> pendingOnSale;
  private Map<String, Integer> pendingOrders;
  private LocalDateTime currDate;
  private Map<Product, Double> saleCart = new HashMap<>();
  private ArrayList<Worker> workerlist;
  private LocalDateTime openingDay;

  /**
   * Create a Manager.
   * @param h the history that scribes all actions of this worker
   * @param id the unique id of the manager
   * @param name the name of the manager
   */
  public Manager(History h, String id, String name) {
    super(h, id, name);
    pendingOnSale = new HashMap<>();
    onSale = new HashMap<>();
    pendingOrders = new HashMap<>();
    currDate = LocalDateTime.now();
    this.setWorkerType("Manager");
    this.openingDay = LocalDateTime.now();
  }

  // SETUP ========================================================================================
  public void setWorkers(ArrayList<Worker> workers) {
    workerlist = workers;
  }
  /**
   * Manager sets up the store before opening sales.
   */
  public void updateStore(Inventory inventory) throws ParseException {
    updateOnSale(inventory);
    currDate = LocalDateTime.now();
  }

  // PROFIT =======================================================================================

  /**
   * Returns the profit for each day specified from start to end date.
   *
   * @return A map of the profit from start to end
   */
  public Map<String, Double> checkProfitHistory(String start, String end) throws DateTimeParseException {
    return history.getProfitHistory(start, end);
  }

  /**
   * Returns the total profit from the start and end date of a profitHistory map.
   *
   * @param profitHistory a map of the profits from a start day to end day
   */
  public Double checkTotalProfit(Map<String, Double> profitHistory) {
    Set<String> keys = profitHistory.keySet();
    Double total = 0.0;
    for (String key : keys) {
      total = total + profitHistory.get(key);
    }
    return total;
  }

  // SALE =========================================================================================

  /**
   * Adds an item with its discounted price to a temporary list.
   */
  public void addToSaleCart(String upc, Double price, Inventory inventory) {
    if (price > 0.0) {
      Product p = inventory.getProduct(upc);
      saleCart.put(p, price);
    }
  }

  /**
   * Return the sale cart
   * @return the sale cart
   */
  public Map<Product, Double> getSaleCart() {
    return saleCart;
  }

  /**
   * Remove the product from the price cart.
   *
   * @param upc the upc of the product
   * @param inventory the inventory to look in
   */
  public void removeFromPriceCart(String upc, Inventory inventory) {
    saleCart.remove(inventory.getProduct(upc));}

  /**
   * Clears the sale cart.
   */
  public void clearSaleCart() {
    saleCart.clear();
  }

  /**
   * Return if the sale cart is empty.
   * @return True if saleCart of this Manager is empty.
   */
  public boolean isSaleCartEmpty() {
    return saleCart.isEmpty();
  }

  /**
   * Check the regular price history of all products and the duration of that price.
   *
   * @return a map of the product and its value as another map of date and price
   */
  public Map<Product, Map<String, Double>> checkRegPriceHistory() {
    return history.getRegPriceHistory();
  }

  /**
   * Check the on sale price history of all products that have been on sale and the duration of that
   * price.
   *
   * @return a map of the product and its value as another map of date and price
   */
  public Map<Product, Map<String, Double>> checkSalePriceHistory() {
    return history.getSalePriceHistory();
  }

  /**
   * Accepts start and end strings of i.e. 2007-12-03T10:15:30 Adds products to a list of items that
   * are pending to be on sale. If the date of the on sale pricing is the current date, then add it
   * to the current on sale list.
   *
   * @param start the start date in format i.e. 2007-12-03T10:15:30
   * @param end the end date in format i.e. 2007-12-03T10:15:30
   * @throws DateTimeParseException Throws a DateTimeParseException.
   * @throws IOException Throws an IOException.
   */
  public void putOnSale(String start, String end) throws DateTimeParseException,
      IOException {
    Set<Product> products = saleCart.keySet();
    for (Product p : products) {

      LocalDateTime startDate = LocalDateTime.parse(start);
      LocalDateTime endDate = LocalDateTime.parse(end);

      if (currDate.isEqual(startDate) ||
          (currDate.isAfter(startDate) && currDate.isBefore(endDate)) ||
          currDate.isEqual(endDate)) {
        OnSaleInfo info = p.getOnSaleInfo();
        info.setSalePrice(false, 0, saleCart.get(p),
            startDate, endDate);
        onSale.put(p, endDate);
      } else {

        LocalDateTime[] startEnd = new LocalDateTime[2];
        startEnd[0] = startDate;
        startEnd[1] = endDate;

        if (pendingOnSale.containsKey(p.getUPC())) {
          pendingOnSale.get(p.getUPC()).put(startEnd, saleCart.get(p));
        } else {
          pendingOnSale.put(p.getUPC(), new HashMap<>());
          pendingOnSale.get(p.getUPC()).put(startEnd, saleCart.get(p));
        }
      }
    }

    //send info to history
    history.recordOnSale(this, start, end, saleCart);
  }

  /**
   * Checks whether items are to be on sale starting today and whether items are off sale. Updates
   * the pendingSale list and onSale list.
   *
   * @throws ParseException Throws a ParseException.
   */
  private void updateOnSale(Inventory inventory) throws ParseException {
    // check if items in the onSale list have passed the end
    Set<Product> products = onSale.keySet();
    for (Product p : products) {
      // the current date has passed the sale date and update the price of the product
      if (currDate.isAfter(onSale.get(p))) {
        OnSaleInfo info = p.getOnSaleInfo();
        info.priceUpdate();
        onSale.remove(p);
      }
    }
    updatePendingOnSale(inventory);

  }

  /**
   * Helper method for updateOnSale.
   */
  private void updatePendingOnSale(Inventory inventory) {
    // check if there are any items whose start date is today or has passed
    Set<String> pendingProducts = pendingOnSale.keySet();
    for (String item : pendingProducts) {
      Product p = inventory.getProduct(item);
      // check if items are to be on sale
      Set<LocalDateTime[]> dates = pendingOnSale.get(p.getUPC()).keySet();
      // add to a pending removal list if finished
      ArrayList<LocalDateTime[]> clear = new ArrayList<>();

      // check if the start date is today
      for (LocalDateTime[] date : dates) {
        if ((currDate.isAfter(date[0]) || currDate.equals(date[0])) &&
            currDate.isBefore(date[1])) {
          onSale.put(p, date[1]);
          OnSaleInfo info = p.getOnSaleInfo();
          info.setSalePrice(false, 0, pendingOnSale.get(p.getUPC()).get(date),
              date[0], date[1]);
          clear.add(date);
        }
      }
      // safely remove from pendingOnSale
      for (LocalDateTime[] date : clear) {
        pendingOnSale.get(p.getUPC()).remove(date);
      }
    }
  }

  /**
   * Check which items are on sale. Not necessarily returning the product.
   *
   * @return A list of the product upc's on sale
   */
  public ArrayList<String> checkOnSale() {
    ArrayList<String> saleList = new ArrayList<>();
    for (Product p : onSale.keySet()) {
      saleList.add(p.getName() + " : " + p.getUPC());
    }
    return saleList;
  }

  // AISLE ========================================================================================

  /**
   * Returns a String array of all products in that aisle.
   *
   * @param aisle the aisle number
   * @return A list of products in a certain aisle
   */
  public ArrayList<Product> checkAisleProducts(int aisle, Inventory inventory) {
    if (inventory.storeLayout.containsKey(aisle)) {
      return inventory.getProductsFromAisle(aisle);
    } else {
      return new ArrayList<>();
    }
  }

  /**
   * Changes the location of a product to another aisle.
   *
   * @param upc the upc of the product
   * @param aisle the new aisle for the product
   */
  public void changeProductLocation(String upc, int aisle, Inventory inventory) {
    Integer previousAisle = inventory.getProduct(upc).getAisleNo();
    inventory.getProduct(upc).setAisleNo(aisle);
    inventory.updateLayout(inventory.getProduct(upc), previousAisle);
  }

  // ORDERS =======================================================================================

  /**
   * Checks if there are any orders that need to be approved.
   *
   * @return true if such order exists
   */
  public boolean checkPendingOrders(Inventory inventory) {
    if (!inventory.pendingOrderProducts.isEmpty()) {
      for (Product p : inventory.pendingOrderProducts) {
        if (!pendingOrders.containsKey(p.getUPC())) {
          pendingOrders.put(p.getUPC(), p.getPendingOrder().getPendingOrderQuantity());
        }
      }
    }
    return (pendingOrders.isEmpty());
  }

  public Map<String, Integer> getPendingOrders() {
    return pendingOrders;
  }

  /**
   * Edits the quantity of the pending order before being sent out.
   *
   * @param upc the upc of the product
   * @param quantity the new quantity for the order of that product
   */
  public void editPendingOrder(String upc, int quantity, Inventory inventory) {
    Product p = inventory.getProduct(upc);
    if (quantity > 0 && pendingOrders.containsKey(upc)) {
      p.getPendingOrder().setPendingOrderQuantity(quantity);
      pendingOrders.put(upc, quantity);
    }
  }

  /**
   * Remove a pending order from the managers list.
   *
   * @param upc the upc
   * @param inventory the inventory to look in
   * @return True if pending order is removed successfully.
   */
  public boolean removePendingOrder(String upc, Inventory inventory) {
    if (inventory.getProduct(upc).getStock() > 0) {
      Product p = inventory.getProduct(upc);
      pendingOrders.remove(upc);
      p.getPendingOrder().clearPendingOrder();
      inventory.removePendingProduct(upc);
      return true;
    }
    return false;
  }

  /**
   * Sends the orders to the distributor and clears all pending orders.
   */
  public void sendOrders(Inventory inventory) throws IOException {
    // sends orders to distributor
    Set<String> allOrders = pendingOrders.keySet();
    for (String item : allOrders) {
      Product p = inventory.getProduct(item);
      history.recordReorder(p, pendingOrders.get(item));
      PendingOrder pendingOrder = p.getPendingOrder();
      pendingOrder.setPendingTrue();
      inventory.removePendingProduct(p.getUPC());
    }
    // updates orders
    pendingOrders.clear();
  }

  /**
   * Creates the current backorder file.
   */
  public void createBackOrderFile(Inventory inventory) throws IOException {
    IO.createCurrentBackorderedFile(inventory.getBackOrder());
  }

  // PRODUCT PROPERTIES ===========================================================================

  /**
   * Changes the threshold quantity of a single product.
   *
   * @param upc the upc of the product
   * @param quantity the new default quantity of the product
   */
  public void changeDefaultQuantity(String upc, int quantity, Inventory inventory) {
    Product p = inventory.getProduct(upc);
    if (quantity > 0 && p != null) {
      if (p.setThreshold(quantity)) {
        inventory.addPendingOrder(p);
      }
    }
  }

  /**
   * Changes the regular pricing for all products in the cart.
   */
  public void changeRegPrice()
      throws IOException, ParseException {
    Set<Product> products = saleCart.keySet();
    for (Product p : products) {
      p.setRegPrice(saleCart.get(p));
    }

    //send info to history
    history.recordChangeRegPrice(this, saleCart);
  }

  /**
   * Return a line of worker's name and a line of worker's performance measure for each worker
   * @return String of worker:performance measure
   */
  public Map<String, String> checkPerformance() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    String openingTime = (openingDay).format(formatter);
    LocalDateTime currentDateTime = LocalDateTime.now();
    String currentTime = currentDateTime.format(formatter);
    Map<String, Double> profitsCum = this.checkProfitHistory(openingTime, currentTime);
    double managerPerMea = 0.0;
    for (double value : profitsCum.values()) {
      managerPerMea += value;
    }
    Map<String, String> PerformReport = new HashMap<>();
    PerformReport.put(this.toString(), Double.toString(managerPerMea));
    for (Worker w : workerlist) {
      if (!(w instanceof Manager))
      {
        PerformReport.put(w.toString(), Integer.toString(w.getPerMeasure()));
      }
    }
    return PerformReport;
  }

  /**
   * Takes in a file with new members and their info and add them to the inventory
   * @param filepath file path to the file with information of new members in it
   * @param inventory The inventory to be added to
   * @throws FileNotFoundException Throws a FileNotFoundException.
   */
  public void addNewMembers(String filepath, Inventory inventory) throws FileNotFoundException {
    IO.addMembersFile(filepath, inventory.membershipData);
  }

  /**
   * Returns a String representation of this Manager.
   *
   * @return A String representation of this Manager.
   */
  public String toString() {
    return "Manager " + id + " " + name;
  }
}
