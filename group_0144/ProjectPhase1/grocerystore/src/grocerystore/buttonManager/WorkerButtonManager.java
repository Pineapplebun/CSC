package grocerystore.buttonManager;

import grocerystore.DisplayManager;
import grocerystore.Inventory;
import grocerystore.Worker;
import grocerystore.Receiver;

/**
 * The button manager receives input from the SignInPanel, LaunchPanel, WorkerPanel. For each panel,
 * it maps the function of each button to the corresponding method in the responsible class and
 * returns a String or boolean that for displaying in the Panels. Every
 * worker has its own button manager.
 */
abstract public class WorkerButtonManager {

  /**
   * The inventory and worker the button manager uses for some methods.
   */
  Inventory inventory;
  Worker worker;

  /**
   * Create a WorkerButtonManager.
   *
   * @param worker The worker of this WorkerButtonManager.
   * @param inventory The inventory of the store.
   */
  public WorkerButtonManager(Worker worker, Inventory inventory) {
    this.worker = worker;
    this.inventory = inventory;
  }

  /**
   * Calls the clear cart method.
   * @return the string that verifies the cart is cleared
   */
  public String callClearCart() {
    worker.clearCart();
    return "Cart is cleared!";
  }

  /**
   * Calls the isCartEmpty method.
   * @return whether the cart is empty
   */
  public boolean callIsCartEmpty() {
    return worker.isCartEmpty();
  }

  /**
   * Calls the checkUPCValid method.
   * @param upc the upc to check
   * @return whether the UPC is valid
   */
  public boolean callCheckUPCValid(String upc) {
    return worker.checkUPCValid(upc, inventory);
  }

  public boolean callIsInNewItems(String upc) {
    if (worker instanceof Receiver) {
      return ((Receiver) worker).isInNewItems(upc);
    }
    return false;
  }
  /**
   * Calls the checkCost method.
   *
   * @param upc The UPC of the requested Product.
   * @return A string showing the cost per unit for the requested Product.
   */
  public String callCheckCost(String upc) {
    Double cost = worker.checkCost(upc, inventory);
    return cost.toString();
  }

  /**
   * Calls the getCart method.
   * @return A string that displays the content of the cart
   */
  public String callGetCart() {
    return DisplayManager.displayMap(worker.getCart());
  }

  /**
   * Calls the setNotActive method.
   * @return A string that verifies the worker is not active.
   */
  public String callSetNotActive() {
    worker.setActive();
    return worker.toString() + " is not active.";
  }

  /**
   * Calls the addToCart method.
   *
   * @param upc The UPC of the product being added.
   * @param q The specified quantity of the product being added.
   */
  public void callAddToCart(String upc, Integer q) {
    worker.addToCart(upc, q, inventory);
  }

  /**
   * Remove a product from the cart.
   *
   * @param upc the upc of the product
   */
  public void callRemoveFromCart(String upc) {
    worker.removeFromCart(upc, inventory);
  }

  /**
   * Calls the checkLocation method.
   *
   * @param upc The UPC of the requested Product.
   * @return An integer variable showing aisle number for the requested Product.
   */
  public String callCheckLocation(String upc) {
    return worker.checkLocation(upc, inventory).toString();
  }

  /**
   * Calls the checkCurrQuantity method.
   * @param upc the upc to check
   * @return a string representation of the current quantity
   */
  public String callCheckCurrQuantity(String upc) {
    return worker.checkCurrQuantity(upc, inventory).toString();
  }

  /**
   * Calls the changeQuantity method.
   *
   * @param upc The unique UPC of a Product.
   * @param q The number of items of the Product in the cart.
   */
  public String callChangeQuantity(String upc, int q) {
    worker.changeQuantity(upc, q, inventory);
    return upc + " quantity has been changed to " + q;
  }

  /**
   * Calls the setReOrderQuantity method.
   *
   * @param upc the upc of the product
   * @param q the reorder quantity multiplier
   */
  public String callSetReOrderQuantity(String upc, Integer q) {
    worker.setReOrderQuantity(upc, q, inventory);
    return upc + " now has a ReOrder quantity of " + q;
  }

  // ==================================== QUERIES ======================================

  /**
   * Calls the query method that pertains to the string.
   *
   * @param question a question of either the location, price, section or quantity of a product
   * @return a String of the answer to the question
   */
  public String callQuery(String question) {
    switch (question) {
      case "queryLocation": {
        return DisplayManager.displayMap(worker.getLocations(inventory));
      }
      case "queryPrice": {
        return DisplayManager.displayMap(worker.getPrices(inventory));
      }
      case "querySection": {
        return DisplayManager.displayMap(worker.getSections(inventory));
      }
      case "queryQuantity": {
        return DisplayManager.displayMap(worker.getQuantities(inventory));
      }
      case "queryThreshold": {
        return DisplayManager.displayMap(worker.getThresholds(inventory));
      }
      default:
        return "Sorry, that is not a valid query.";
    }
  }

}
