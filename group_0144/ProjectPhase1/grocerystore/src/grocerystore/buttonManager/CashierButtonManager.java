package grocerystore.buttonManager;

import grocerystore.Cashier;
import grocerystore.Inventory;
import java.io.IOException;

/**
 * The CashierButtonManager receives input from the SignInPanel, LaunchPanel, WorkerPanel.
 * For each panel, it maps the function of each button to the corresponding method in the
 * responsible class.
 */
public class CashierButtonManager extends WorkerButtonManager{
  Cashier cashier;

  /**
   * Sets up a CashierButtonManager by inputting a specific Cashier and the Inventory.
   *
   * @param cashier the Cashier
   * @param inventory the inventory
   */
  public CashierButtonManager(Cashier cashier, Inventory inventory) {
    super(cashier, inventory);
    this.cashier = cashier;
  }

  /**
   * Calls the checkout method in <code>Cashier</code>.
   *
   * @return A string shows all items in a cart have been checked out.
   * @throws IOException Throws an IOException.
   */
  public String callCheckOut() throws IOException
  {
    cashier.checkout(inventory);
    return "All items in the cart are checked out.";
  }

  /**
   * Calls the customerReturn method in <code>Cashier</code>.
   *
   * @return A string shows the items in a cart have been returned to the store.
   * @throws IOException Throws an IOException.
   */
  public String callCustomerReturn() throws IOException
  {
    cashier.customerReturn(inventory);
    return "Items returned.";
  }

  /**
   * Calls the addOneMember method in <code>Inventory</code> to add a new member.
   *
   * @param memberId A member unique identifier will be assigned to this new member.
   * @param fullName The full name of this new member.
   * @param phoneNum The phone number of this new member.
   * @return A string shows a new member has been registered.
   */
  public String callAddNewMember(String memberId, String fullName, String phoneNum)
  {
    String [] name = fullName.split(" ");
    String[] memberInfo = new String[4];
    memberInfo[0] = memberId;
    memberInfo[1] = name[0];
    memberInfo[2] = name[1];
    memberInfo[3] = phoneNum;
    inventory.addOneMember(memberId, memberInfo);
    return "Added one new member.";
  }

  /**
   * Applies membership discount to the cart using methods from Inventory and Cashier.
   * @param memberId The pre-assigned membership ID for the customer.
   * @return A string shows that the membership discount has been applied to the cart.
   */
  public String callMemberDiscount(String memberId)
  {
    if(inventory.isAMember(memberId)){
      cashier.hasMembershipStatus(memberId, inventory);
      return "Membership discounts have been applied to the cart, "
          + "please click Get Cart Total button to see the discounted prices";
    }else{
      return "This customer is not a member; membership discount will not be applied to this cart";
    }
  }

  /**
   * Get the total price of the cart.
   * @return A string shows the cart total price.
   */
  public String callGetCartTotal()
  {
    return "The cart total price is: " + cashier.getCartTotal();
  }
}

