package grocerystore.buttonManager;

import grocerystore.DisplayManager;
import grocerystore.Inventory;
import grocerystore.Manager;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

/**
 * When button that belongs to the manager is pressed, one of the methods in this class is called to
 * take action. Methods are generally returned as Strings
 */
public class ManagerButtonManager extends WorkerButtonManager {

  Manager manager;

  /**
   * Sets up the button manager
   *
   * @param m the manager
   * @param inventory the inventory
   */
  public ManagerButtonManager(Manager m, Inventory inventory) {
    super(m, inventory);
    manager = m;
  }

  /**
   * Clears the sale cart
   *
   * @return string that says the sale cart has been cleared
   */
  public String callClearPriceCart() {
    manager.clearSaleCart();
    return "Sale Cart has been cleared";
  }

  public String callGetPriceCart() {
    return DisplayManager.displayMap(manager.getSaleCart());
  }

  /**
   * Calls the isSaleCartEmpty method.
   * @return True if saleCart of manager is empty.
   */
  public boolean callIsSaleCartEmpty() {
    return manager.isSaleCartEmpty();
  }

  /**
   * Checks the profit history from when to when
   *
   * @param start start date
   * @param end end date
   * @return string of how much profit was made at when
   */
  public String callCheckProfitHistory(String start, String end) {
    return DisplayManager.displayMap(manager.checkProfitHistory(start, end));
  }

  /**
   * Checks the total profit from when to when
   *
   * @param start start date
   * @param end end date
   * @return string of how much was made in the time period
   */
  public String callCheckTotalProfit(String start, String end) {
    return manager.checkTotalProfit(manager.checkProfitHistory(start, end)).toString();
  }

  /**
   * Add product with it's sale/new regular price to the sale cart
   *
   * @param upc upc of the product
   * @param price new sale/regular price of the product
   * @return string notifying product with it's new sale price has been added to the sale cart
   */
  public String callAddToPriceCart(String upc, Double price) {
    manager.addToSaleCart(upc, price, inventory);
    return "Added " + upc + " with the price " + price + " to the sale cart";
  }

  /**
   * Calls the removeFromPriceCart method.
   *
   * @param upc The upc of the product to be removed from price cart.
   * @return A string shows the the product has been removed from price cart.
   */
  public String callRemoveFromPriceCart(String upc) {
    manager.removeFromPriceCart(upc, inventory);
    return "Removed " + upc + " from price cart.";
  }

  /**
   * Returns the regular price history
   *
   * @return the regular price history
   */
  public String callCheckRegPriceHistory() {
    return DisplayManager.displayPriceHistory(manager.checkRegPriceHistory());
  }

  /**
   * Returns the sale price history
   *
   * @return the sale price history
   */
  public String callCheckSalePriceHistory() {
    return DisplayManager.displayPriceHistory(manager.checkSalePriceHistory());
  }


  /**
   * Put items in the sale cart to sale from start to end
   *
   * @param start start date
   * @param end end date
   * @return string notifying the items in the cart have been set on sale
   */
  public String callPutOnSale(String start, String end) throws IOException {
    manager.putOnSale(start, end);
    return "Items in the cart have been put on sale from " + start + " to " + end;
  }

  /**
   * Check what products are on sale
   *
   * @return string of what is on sale
   */
  public String callCheckOnSale() {
    return DisplayManager.displayArray(manager.checkOnSale());
  }

  /**
   * Check what products are in an aisle
   *
   * @param aisle the aisle number
   * @return string of items in the aisle
   */
  public String callCheckAisleProducts(Integer aisle) {
    return DisplayManager.displayArray(manager.checkAisleProducts(aisle, inventory));
  }

  /**
   * Changes the product location of a product to another aisle
   *
   * @param upc upc of the product
   * @param aisle new aisle number for the product
   * @return string notifying the product;s location has been changed
   */
  public String callChangeProductLocation(String upc,Integer aisle) {
    manager.changeProductLocation(upc, aisle, inventory);
    return upc + " has been relocated to aisle " + aisle;
  }

  /**
   * Check whether there is any pending order
   *
   * @return string telling if there is or there is not any pending order
   */
  public String callCheckPendingOrders() {
    if (manager.checkPendingOrders(inventory)) {
      return "There are no pending orders";
    } else {
      return "There are pending orders";
    }
  }

  public String callGetPendingOrders() {
    return DisplayManager.displayMap(manager.getPendingOrders());
  }

  /**
   * Edit quantity of pending order of a product
   *
   * @param upc upc of the product
   * @param quantity new quantity for the product's pending order
   * @return string notifying the pending order of the product has been changed
   */
  public String callEditPendingOrder(String upc, Integer quantity) {
    manager.editPendingOrder(upc, quantity, inventory);
    return "Pending order of " + upc + " has been changed to " + quantity;
  }

  /**
   * Send the orders to the distributor
   *
   * @return string notifying the order has been sent
   */
  public String callSendOrders() throws IOException {
    manager.sendOrders(inventory);
    return "Orders have been sent";
  }

  /**
   * Create a back order file
   *
   * @return string notifying a back order file has been created
   */
  public String callCreateBackOrderFile() throws IOException {
    manager.createBackOrderFile(inventory);
    return "Back order file has been created";
  }

  /**
   * Change the default quantity of a product
   *
   * @param upc upc of the product
   * @param quantity new default quantity of the product
   * @return string notifying the default quantity of the product has been changed
   */
  public String callChangeDefaultQuantity(String upc, Integer quantity) {
    manager.changeDefaultQuantity(upc, quantity, inventory);
    return "Threshold quantity of " + upc + " has been changed to " + quantity;
  }

  /**
   * Change the regular price of the products in the cart to their new prices
   *
   * @return string notifying the regular prices of the products have been changed
   */
  public String callChangeRegPrice() throws IOException, ParseException {
    manager.changeRegPrice();
    return "Regular prices of the products in the cart have been changed";
  }

  /**
   * Check each worker's performance
   * @return String of workers and their performance measure
   */
  public String callCheckPerformance() {
    return DisplayManager.displayMap(manager.checkPerformance());
  }

  /**
   * Add new members in the file from filepath to inventory
   * @param filepath path of the file
   * @return String notifying the members in the file have been added
   */
  public String callAddNewMembers(String filepath) {
    try {
      manager.addNewMembers(filepath, inventory);
    } catch (FileNotFoundException e) {
      System.err.println(e.getMessage());
    }
    return "Members in the file have been added";
  }

  public String callRemovePendingOrder(String upc) {
    if (manager.removePendingOrder(upc, inventory)) {
      return "Pending order for " + upc + " has been removed";
    } else {
      return upc + "does not have a pending order or there is none left in grocery store";
    }
  }


}
