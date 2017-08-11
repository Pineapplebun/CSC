package grocerystore;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * The Inventory stores all the products within a store and organizes them such that one can grab a
 * product by giving it's UPC, print out a file of currently backordered products (that is always
 * updating), give the isle number to receiver an array of all the products in that aisle, add new
 * products to the inventory, update quantities of products in the inventory (when they are sold or
 * pre-existing units are entered), add to the storage room and remove from the storage room carts
 * of products, and stores the list of products that have automatically created orders to send to
 * the distirbutor but are waiting for the Manager to edit/send them, hence the pending Order
 * Products list.
 */
public class Inventory implements Serializable {

  /**
   * all products in store that aren't backordered
   */
  Map<String, Product> productMap = new HashMap<>();
  /**
   * a map of the store layout (integer aislnumber to an arraylist of products)
   */
  Map<Integer, ArrayList<Product>> storeLayout = new HashMap<>();
  /**
   * The list of products that still have pending orders waiting for manager to handle.
   */
  ArrayList<Product> pendingOrderProducts = new ArrayList<>();
  Map<String, String[]> membershipData = new HashMap<>();

  /**
   * all backordered products in store.
   */
  private Map<String, Product> backOrder = new HashMap<>();
  /**
   * all carts of products in the storageroom
   */
  private ArrayList<Map> storageRoom = new ArrayList<>();

  /**
   * Creates an inventory instance, requires a filepath for initial set up in order to create and
   * store all the Product objects
   *
   * @param filePath a file path of a csv file containing columns of UPC number, quantity, section,
   * subsection, name, theshold, aisle number, price, distributor's name, and cost (information in
   * order to create the products).
   */
  Inventory(String filePath) throws IOException {
    IO.newInventory(filePath, this);

  }

  /**
   * Remove a Pending product.
   * @param upc The UPC of the product.
   */
  public void removePendingProduct(String upc) {
    ArrayList<Product> pToRemove = new ArrayList<>();
    for (Product p : pendingOrderProducts) {
      if (p.getUPC().equals(upc)) {
        pToRemove.add(p);
      }
    }
    if (!pToRemove.isEmpty()) {
      pendingOrderProducts.remove(pToRemove.get(0));
    }
  }

  /**
   * Add one member
   * @param memberId the member id
   * @param memberInfo the member info in a string array
   */
  public void addOneMember(String memberId, String[] memberInfo) {
    membershipData.put(memberId, memberInfo);
  }

  /**
   * Returns whether the memberId exists
   * @param memberId the memberId to check
   * @return whether the memberId exists
   */
  public boolean isAMember(String memberId) {
    return membershipData.containsKey(memberId);
  }

  public String getMemberName(String memberId) {
    if (membershipData.containsKey(memberId)) {
      String[] memberInfo = membershipData.get(memberId);
      String memberName = memberInfo[1] + memberInfo[2];
      return memberName;
    } else {
      return null;
    }
  }

  public String getMemberNumber(String memberId) {
    if (membershipData.containsKey(memberId)) {
      String[] memberInfo = membershipData.get(memberId);
      return memberInfo[3];
    } else {
      return null;
    }
  }

  /**
   * Returns the product when given the UPC
   *
   * @param productUPC the unique String UPC of the product that will be returned.
   * @return the product object requested by its UPC.
   */
  Product getProduct(String productUPC) {
    if (productMap.containsKey(productUPC)) {
      return productMap.get(productUPC);
    } else {
      return backOrder.get(productUPC);
    }
  }

  /**
   * Get the map of all products in the inventory.
   */
  Map<String, Product> getProductMap() {
    return productMap;
  }

  /**
   * Adds a new product to the inventory.
   *
   * @param newProduct the product object to be added to this inventory.
   */
  void addNewProduct(Product newProduct) {

    productMap.put(newProduct.getUPC(), newProduct);
    addToLayout(newProduct);
  }

  /**
   * Increases quantity within product when the upc string of that scanned product is entered, also
   * updates backorder and productMap accordingly. Receiving a product occurs when new units of
   * pre-existing products within the inventory are scanned.
   *
   * @param productUPC the unique UPC of the product being receiver.
   */
  void receivedProduct(String productUPC) {
    Product currProduct = this.getProduct(productUPC);
    currProduct.addOne();

    //if the status of the product is no long quantity 0 becuase we've just added a product,
    //and if that product was on the backorder list map than remove it from there.
    if (!currProduct.backOrderStatus) {
      if (backOrder.containsKey(productUPC)) {
        backOrder.remove(productUPC);
        productMap.put(productUPC, currProduct);
      }
    }
  }

  /**
   * Updates quantity in the inventory of product being sold; when the cashier sells a product that
   * is one quantity of that product sold.
   *
   * @param currProduct the product being sold once.
   */
  void sellProduct(Product currProduct) {
    currProduct.soldOne();
    String productUPC = currProduct.getUPC();
    if (currProduct.backOrderStatus) {
      productMap.remove(productUPC);
      backOrder.put(productUPC, currProduct);
    }
  }


  /**
   * Takes a product, checks information on its location, then places it within the class variable
   * storeLayout depending on its aisle number value.
   */
  void addToLayout(Product currproduct) {
    Integer aisleNum = currproduct.getAisleNo();

    //if the aisle number already exists in the store.
    if (storeLayout.containsKey(aisleNum)) {
      ArrayList<Product> currAisleProducts = storeLayout.get(aisleNum);
      currAisleProducts.add(currproduct);
      storeLayout.put(aisleNum, currAisleProducts);
      //if the asile number does not exist in the store, create it and add this product to it's
      // corresponding arraylist.
    } else {
      ArrayList<Product> newAisleArray = new ArrayList<>();
      newAisleArray.add(currproduct);
      storeLayout.put(aisleNum, newAisleArray);
    }

  }

  /**
   * Updates the location of this product in the store layout once the product's location has been
   * changed Note: the location of the product must be changed within the product first before
   * changing its location.
   *
   * @param currProduct the current product to be have its location updated in the layout
   * @param aisleNum the old aisle number this product has as its location aisle number in store.
   */
  void updateLayout(Product currProduct, Integer aisleNum) {
    if (storeLayout.containsKey(aisleNum)) {
      (storeLayout.get(aisleNum)).remove(currProduct);
    }
    addToLayout(currProduct);
  }

  /**
   * Returns arraylist of all the products within an aisle when given the aisle number integer
   *
   * @param aisleNum the aisle number of the products that will be returned.
   * @return an arraylist of products from the aisle requested.
   */
  ArrayList<Product> getProductsFromAisle(Integer aisleNum) {
    return storeLayout.get(aisleNum);
  }

  /**
   * Adds the product to a list of products that contain pending orders (a pending order occurs when
   * the quantity of a product is below its threshold the product creates an order containing
   * specific details of the order stored within it. However, the order isn't sent until the manager
   * sends it therefore, rather than the manager checking all the products in the store for if they
   * have pending orders to be sent, the manager just checks the products in the
   * pendingOrderProducts which this method adds to).
   *
   * @param currproduct the product that has a pending order.
   */
  void addPendingOrder(Product currproduct) {
    if (!pendingOrderProducts.contains(currproduct)) {
      pendingOrderProducts.add(currproduct);
    }
  }

  /**
   * Returns a map of backordered items.
   * @return a map of the backordered items
   */
  Map<String, Product> getBackOrder() {
    return this.backOrder;
  }

  /**
   * Checks if this product is recorded as something the store sells and recieves (so if its in
   * inventory or backorder it still counts as an item beloning in this store).
   *
   * @param productUPC the unique UPC of the product being checked
   * @return boolean true if the product is in the store, false otherwise.
   */
  boolean isInStore(String productUPC) {
    return (isBackOrdered(productUPC) || productMap.containsKey(productUPC));
  }

  /**
   * Checks if this product is backordered (has quantity = 0).
   *
   * @param productUPC the unique UPC string of this product
   * @return true if the product is backordered, false otherwise.
   */
  private boolean isBackOrdered(String productUPC) {
    return backOrder.containsKey(productUPC);
  }

  /**
   * Adds a cart full of products to the storage room.
   *
   * @param currCart the cart being added to the storage room.
   */
  void cartToStorage(Map currCart) {
    Map<Product, Integer> cartToStore = new HashMap<>();

    //In order to prevent creating an alias, a cart identical to the cart being
    //given to the storage room will be recreated so that when the original cart
    //is cleared it won't affect the cart in the storage room.
    for (Object p : currCart.keySet()) {
      Product cartProduct = this.getProduct(((Product) p).getUPC());
      Integer theNumTimesScanned = (Integer) (currCart.get(p));
      cartToStore.put(cartProduct, theNumTimesScanned);
    }
    storageRoom.add(cartToStore);

  }

  /**
   * Returns a cart full of products from the storage room that needs to be put on shelves. The cart
   * is hence removed from the storage room.
   *
   * If there are no carts in the storage room it returns null.
   */
  Map getStoredCart() {
    if (storageRoom.size() > 0) {
      Map currCart = storageRoom.get(0);
      storageRoom.remove(0);
      return currCart;
    } else {
      return null;
    }
  }

}

