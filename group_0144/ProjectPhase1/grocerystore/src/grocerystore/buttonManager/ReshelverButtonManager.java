package grocerystore.buttonManager;

import grocerystore.DisplayManager;
import grocerystore.Inventory;
import grocerystore.Reshelver;
import java.io.IOException;

/**
 * The ReshelverButtonManager receives input from the SignInPanel, LaunchPanel, WorkerPanel.
 * For each panel, it maps the function of each button to the corresponding method in the
 * responsible class.
 */
public class ReshelverButtonManager extends WorkerButtonManager {
  Reshelver reshelver;

  /**
   * Create a ReshelverButtonManager.
   * @param reshelver the reshelver of this button manager
   * @param inventory the inventory to look in
   */
  public ReshelverButtonManager(Reshelver reshelver, Inventory inventory) {
    super(reshelver, inventory);
    this.reshelver = reshelver;
  }

  /**
   * Calls the reshelvesCart method in <code>Reshelver</code>.
   * @return A string that verifies that the cart has been reshelved.
   * @throws IOException Throws an IOException.
   */
  public String callReshelvesCart() throws IOException
  {
    reshelver.reshelvesCart(inventory);
    return "All items in the cart are reshelved.";
  }

  public String callGetOrderHistory(String upc) {
    return DisplayManager.displayMap(reshelver.getOrderHist(upc, inventory));
  }
}
