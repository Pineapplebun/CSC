package grocerystore.buttonManager;

import grocerystore.*;
import java.io.FileNotFoundException;

/**
 * The ReceiverButtonManager receives input from the SignInPanel, LaunchPanel, WorkerPanel.
 * For each panel, it maps the function of each button to the corresponding method in the
 * responsible class.
 */
public class ReceiverButtonManager extends WorkerButtonManager {
    Receiver receiver;

  /**
   * Create the ReceiverButtonManager.
   * @param receiver the receiver of this button manager
   * @param inventory the inventory to look in
   */
  public ReceiverButtonManager(Receiver receiver, Inventory inventory) {
      super(receiver, inventory);
      this.receiver = receiver;
  }

  /**
   * Calls the newShipment method.
   * @param filePath the filepath of the shipment data
   * @return a string that verifies adding of the new shipment
   * @throws FileNotFoundException Throws a FileNotFoundException if the filePath is invalid.
   */
  public String callNewShipment (String filePath) throws FileNotFoundException
  {
    receiver.newShipment(filePath);
    return "Done adding new shipment to the system.";
  }

  /**
   * Calls the checkout method.
   * @return a string that verifies that the products are received
   */
  public String callCheckout()
  {
    receiver.checkout(inventory);
    return "Pre-existing products are received.";
  }

  /**
   * Calls the isNewCartEmpty method.
   * @return whether the new cart is empty
   */
  public boolean callIsNewCartEmpty() {
    return receiver.isNewCartEmpty();
  }

  /**
   * Calls the checkoutNew method.
   * @return a string that verifies that the new products are received.
   */
  public String callCheckoutNew() {
    receiver.checkoutNew(inventory);
    return "New products are received.";
  }
}

