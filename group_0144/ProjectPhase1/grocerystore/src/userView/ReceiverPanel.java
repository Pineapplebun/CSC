package userView;

import grocerystore.buttonManager.ReceiverButtonManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;

/**
 * ReceiverPanel is a subclass of WorkerPanel. It contains working panels for a Receiver.
 */
public class ReceiverPanel extends WorkerPanel {
  JButton checkout = new JButton("Check Out");
  JButton checkoutNew = new JButton("Check Out New Items");

  JButton newShipment = new JButton("Enter New Shipment");
  JLabel newinstructions = new JLabel("Add File of new item info to system by entering filepath:");
  JTextField textField = new JTextField(12);
  String newItemsFilePath;

  ReceiverButtonManager receiverBM;


  /**
   * Creates a ReceiverPanel.
   */
  public ReceiverPanel(){
    super(false);
    this.setPanelTitle("Receiver's Unique Action Panel:");

    this.textFieldtoBP(textField, newinstructions, 0, 7);


    List<JButton> allButtons = Arrays.asList(newShipment, checkout, checkoutNew);
    this.addNewButton(allButtons, 2, 7, new ReceiverListener());
  }

  /**
   * Sets receiverBM of this ReceiverPanel to receiverBM.
   * @param receiverBM receiverBM of ReceiverButtonManager type.
   */
  public void setReceiverBM(ReceiverButtonManager receiverBM){
    this.receiverBM = receiverBM;
    setWorkerBM(receiverBM);
  }

  /**
   * Handles the button events recorded from the user interface and then take action accordingly.
   */
  private class ReceiverListener implements ActionListener {

    public void actionPerformed(ActionEvent event) {
      Object source = event.getSource();
      clearDisplayAnswer();

      if (source == checkout && !receiverBM.callIsCartEmpty()) {
        clearDisplayAnswer();
        addTextDisplayAnswer(receiverBM.callCheckout());
        clearDisplayCart();
      }else if(source == checkoutNew && !receiverBM.callIsNewCartEmpty()){
        clearDisplayAnswer();
        addTextDisplayAnswer(receiverBM.callCheckoutNew());
        clearDisplayCart();
      }else if(source == newShipment){
        clearDisplayAnswer();
        try {
          newItemsFilePath = textField.getText();
          addTextDisplayAnswer(receiverBM.callNewShipment(newItemsFilePath));
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
