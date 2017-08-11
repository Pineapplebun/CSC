package userView;

import grocerystore.buttonManager.CashierButtonManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;

/**
 * CashierPanel is a subclass of WorkerPanel. It contains working panels for a Cashier.
 */
public class CashierPanel extends WorkerPanel{
  JButton checkout = new JButton("Check Out Cart");
  JButton customerReturn =  new JButton("Return Cart Item(s)");
  JButton getCartTotal =  new JButton("Get Cart Total");

  JButton giveDiscount = new JButton("Give Cart Member Discount");
  JLabel discountInstructions = new JLabel("Apply Discounts: "
      + "Enter a unique membership Id for this customer");
  JTextField idField = new JTextField(12);
  String memberId;


  JButton addNewMember = new JButton("Add New Member");
  JLabel idinstructions = new JLabel("Register New Member: "
      + "Enter new ID E.g.: 12345678");
  JTextField newId = new JTextField(12);

  JLabel nameinstructions = new JLabel("Register New Member: "
      + "Enter new name E.g.: Lily Potter");
  JTextField newName = new JTextField(12);

  JLabel phoneinstructions = new JLabel("Register New Member: "
      + "Enter new number E.g.: (416) 555-5555");
  JTextField newNumber = new JTextField(12);

  CashierButtonManager cashierBM;

  /**
   * Creates a CashierPanel.
   */
  public CashierPanel(){
    super(false);
    this.setPanelTitle("Cashier's Unique Action Panel:");

    this.textFieldtoBP(newId, idinstructions, 0, 7);
    this.textFieldtoBP(newName, nameinstructions, 0, 8);
    this.textFieldtoBP(newNumber, phoneinstructions, 0, 9);

    List<JButton> aButton = Arrays.asList(addNewMember);
    this.addNewButton(aButton, 2, 9, new CashierListener());

    this.textFieldtoBP(idField, discountInstructions, 0, 10);

    List<JButton> allButtons = Arrays.asList(giveDiscount, checkout, customerReturn, getCartTotal);
    this.addNewButton(allButtons, 2, 10, new CashierListener());
  }

  /**
   * Sets cashierBM of this CashierPanel to cashierBM.
   * @param cashierBM cashierBM of CashierButtonManager type.
   */
  public void setCashierBM(CashierButtonManager cashierBM){
    this.cashierBM = cashierBM;
    setWorkerBM(cashierBM);
  }

  /**
   * Handles the button events recorded from the user interface and then take action accordingly.
   */
  private class CashierListener implements ActionListener {

    public void actionPerformed(ActionEvent event) {
      Object source = event.getSource();
      clearDisplayAnswer();

      if (source == checkout && !cashierBM.callIsCartEmpty()) {
        clearDisplayAnswer();
        try {
          addTextDisplayAnswer(cashierBM.callCheckOut());
          clearDisplayCart();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }else if(source == customerReturn && !cashierBM.callIsCartEmpty()){
        clearDisplayAnswer();
        try {
          addTextDisplayAnswer(cashierBM.callCustomerReturn());
        } catch (IOException e) {
          e.printStackTrace();
        }
      }else if(source == getCartTotal && !cashierBM.callIsCartEmpty()){
        clearDisplayAnswer();
        addTextDisplayAnswer(cashierBM.callGetCartTotal());
      }else if(source == addNewMember){
        clearDisplayAnswer();
        String memberId = newId.getText();
        String memberName = newName.getText();
        String memberPhone = newNumber.getText();
        addTextDisplayAnswer(cashierBM.callAddNewMember(memberId, memberName, memberPhone));
      }else if(source == giveDiscount && !cashierBM.callIsCartEmpty()){
        clearDisplayAnswer();
        memberId = idField.getText();
        addTextDisplayAnswer(cashierBM.callMemberDiscount(memberId));
      }
    }
  }
}
