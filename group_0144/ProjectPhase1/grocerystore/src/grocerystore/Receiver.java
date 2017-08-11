package grocerystore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * A Receiver working for the store, who can scan items (getting the Product UPCs) to perform any
 * actions of a <code>Worker</code>. Their responsibility is that they scan new pre-existing items
 * into the store (and updates the inventory appropriately). They can also scan in new items into
 * the inventory if given the proper information necessary to add that Product to the inventory.
 */
public class Receiver extends Worker {

  private Map<Product, Integer> newCart = new HashMap<>();
  private Map<String, Product> newItems = new HashMap<>();

  /**
   * Creates a receiver.
   *
   * @param storeHist the history of transactions in this store.
   * @param id the unique id every worker in the store is given.
   * @param name the name of this receiver.
   */
  public Receiver(History storeHist, String id, String name) {
    super(storeHist, id, name);
    this.setWorkerType("Receiver");
  }

  /**
   * When new products that are not already in the inventory of this grocery store arrive, add them
   * all to the inventory using their information in the file given by the manager.
   *
   * @param filePath a csv file containing information on the new products, the columns of
   * information in the format and order: UPC, Quantity, Section, Subsection, Name, Threshold, Aisle
   * Number, Price, Distributor's Name, and Cost.
   */
  public void newShipment(String filePath) throws FileNotFoundException {
    File file = new File(filePath);
    if (file.exists()) {
      Scanner scanner = new Scanner(new FileInputStream(filePath));
      String[] productData;
      Product currproduct;

      scanner.nextLine();
      while (scanner.hasNextLine()) {
        productData = scanner.nextLine().split(",");
        currproduct = new Product(productData);
        newItems.put(productData[0], currproduct);
      }
      scanner.close();
    }
    performMeasure += 1;
  }

  /**
   * Check if the product with upc is in newItems, which is a map consisting of products not yet been entered.
   *
   * @param upc The upc of the product.
   * @return True if newItems contains this product.
   */
  public boolean isInNewItems(String upc){
    return newItems.containsKey(upc);
  }

  /**
   * Return the cart.
   *
   * @return A map of the product to its quantity
   */
  public Map<Product, Integer> getCart() {
    if(newCart.isEmpty())
    {
      return cart;
    }
    else
    {
      return newCart;
    }
  }

  /**
   * Adds this single item into it's specific cart to be put in the storage room.
   *
   * If this item is not already registered previously in the inventory, information about the item
   * should be input through newShipment method before adding to cart; if this rule is not
   * followed nothing will be added to the newCart.
   *
   * @param upc the UPC of the product being added to the cart.
   * @param numScanned the number of times this UPC is scanned (allows for the multiple scanning of
   * the same product to be added to the cart all at once for efficiency.
   */
  public void addToCart(String upc, Integer numScanned, Inventory inventory) {
    if (inventory.isInStore(upc)) {
      Product p = inventory.getProduct(upc);
      cart.put(p, numScanned);
    } else {
      if (newItems.containsKey(upc)) {
        Product p = newItems.get(upc);
        newCart.put(p, numScanned);
      }
    }
  }

  /**
   * Updates the inventory and puts the cart full of pre-existing items (previously registered in
   * the inventory), in the storage room. items now ready to be put on shelves.
   */
  public void checkout(Inventory inventory) {
    for (Product p : cart.keySet()) {
      String currUPC = p.getUPC();
      Integer quantity = cart.get(p);
      while (0 < quantity) {
        inventory.receivedProduct(currUPC);
        quantity = quantity - 1;
      }
      p.needReorder = false;
      p.getPendingOrder().clearPendingOrder();
    }

    //Leaves this cart of products in the storage room for the reshelver.
    inventory.cartToStorage(cart);
    history.recordReceived(this, cart);
    this.clearCart();
    performMeasure += 1;
  }

  /**
   * Updates the inventory (by registering these new items into it), these items are now ready to be
   * put on shelves.
   */
  public void checkoutNew(Inventory inventory) {
    for (Product p : newCart.keySet()) {
      Integer quantity = newCart.get(p);
      inventory.addNewProduct(p);
      while (0 < quantity) {
        inventory.receivedProduct(p.getUPC());
        quantity = quantity - 1;
      }
    }

    //Leaves this cart of products in the storage room for the reshelver.
    if (!newCart.isEmpty()) {
      inventory.cartToStorage(newCart);
      history.recordRegistered(this, newCart);
    }
    newItems.clear();
    newCart.clear();
    performMeasure += 1;
  }

  /**
   * Checks if new cart is empty.
   *
   * @return True if new cart is empty.
   */
  public boolean isNewCartEmpty()
  {
    return newCart.isEmpty();
  }

  /**
   * Returns a String representation of this Receiver.
   *
   * @return A String representation of this Receiver.
   */
  public String toString() {
    return "Receiver " + id + " " + name;
  }
}
