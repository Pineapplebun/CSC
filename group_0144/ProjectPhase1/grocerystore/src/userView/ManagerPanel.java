package userView;

import grocerystore.buttonManager.ManagerButtonManager;
import grocerystore.Store;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;
import javax.swing.text.NumberFormatter;

/**
 * ManagerPanel is a subclass of WorkerPanel. It contains working panels for a Manager.
 */
public class ManagerPanel extends WorkerPanel{

  private JButton addToPriceCart = new JButton("Add to Price Cart");
  private JFormattedTextField aPCinput;

  //Buttons that don't require a textfield (use price cart) below:
  private JButton clearPriceCart = new JButton("Clear Price Cart");
  private JButton changeRegPrice = new JButton("Change Regular Price");

  //Buttons that don't require a textfield and no cart below:
  private JButton checkPerformance = new JButton("Check Workers Performance");
  private JButton createBackOrderFile = new JButton("Create BackOrder File");
  private JButton checkSalePriceHist = new JButton("Check Sale Price History");
  private JButton checkRegPriceHist = new JButton("Check Regular Price History");
  private JButton checkpendingOrders = new JButton("Check System's Pending Orders");
  private JButton sendOrders = new JButton("Send All System's Pending Orders");
  private JButton checkOnSale = new JButton("Check System's On Sale Products");
  private JButton closeStore = new JButton("Close Store");
  private JButton removePendingOrder = new JButton("Remove a pending order");
  private JButton removeFromPriceCart = new JButton("Remove from Price Cart");
  //Buttons require text field for additional info but use Cart products

  //needs a start and end date textfield.
  private JButton putsOnSale = new JButton("Put Cart On Sale By Date");
  //JFormattedTextField pOSinput;

  //needs new location for all the cart's products
  private JButton changeProductLocation = new JButton("Change Product Location");
  private JFormattedTextField cPLinput;
  private JButton changeDefaultQuantity = new JButton("Change Product Threshold Quantity");  // needs a quantity textfield to indicate new threshold.
  private JFormattedTextField cDQquantity;
  private JButton checkProfitHistory = new JButton("Check Profit History By Date"); //needs start and end date in textfield
  private JButton checkTotalProfit = new JButton("Check Total Profit By Date"); //needs start and end date in textfield
  private JButton checkAisleProducts = new JButton("Check Products of Aisle"); // textfield for aisle number.
  private JFormattedTextField cAPinput;
  private JButton editpendingOrders = new JButton("Edit Product's Pending Order"); //needs its own textfield for new quantity, does not interact with cart.
  private JFormattedTextField ePOinput;
  private JButton addNewMembers = new JButton("Add New Members File"); // needs filepath textfield
  private JTextField aNMinput = new JTextField(12);
  private JFormattedTextField startDate;
  private JFormattedTextField endDate;

  private JFrame personalFrame;
  private ManagerButtonManager managerBM;
  private Store currStore;

  /**
   * Creates a ManagerPanel.
   */
  public ManagerPanel(){
    super(true);

    // Integer Formatter
    NumberFormatter numberFormatter = new NumberFormatter();
    numberFormatter.setValueClass(Integer.class);

    cPLinput = new JFormattedTextField(numberFormatter);
    cPLinput.setValue(1);
    cPLinput.setColumns(12);

    cDQquantity = new JFormattedTextField(numberFormatter);
    cDQquantity.setValue(10);
    cDQquantity.setColumns(12);

    cAPinput = new JFormattedTextField(numberFormatter);
    cAPinput.setValue(1);
    cAPinput.setColumns(12);

    ePOinput = new JFormattedTextField(numberFormatter);
    ePOinput.setValue(6);
    ePOinput.setColumns(12);

    // LocalDatetime Formatter

    startDate = new JFormattedTextField();
    startDate.setValue("2017-07-24T23:10:10");
    startDate.setColumns(12);

    endDate = new JFormattedTextField();
    endDate.setValue("2017-08-25T23:50:10");
    endDate.setColumns(12);

    // Double Formatter
    NumberFormatter doubleFormatter = new NumberFormatter();
    doubleFormatter.setValueClass(Double.class);

    aPCinput = new JFormattedTextField(doubleFormatter);
    aPCinput.setValue(5.0);
    aPCinput.setColumns(12);

    this.setPanelTitle("Manager's Unique Action Panel:");
    addAllManagerTextFields();

    addAllManagerButtons();
  }

  /**
   * Sets managerBM of this ManagerPanel to managerBM.
   * @param managerBM managerBM of ManagerButtonManager type.
   */
  public void setManagerBM(ManagerButtonManager managerBM){
    this.managerBM = managerBM;
    setWorkerBM(managerBM);
  }

  /**
   * This method adds some text fields to the user interface for a Manager.
   */
  public void addAllManagerTextFields(){

    JLabel cDQInstructions = new JLabel("Enter upc in UPC field. Enter new threshold number: ");
    this.addButtonPlusInfo(changeDefaultQuantity, cDQquantity, cDQInstructions, 0, 7);

    JLabel cPLInstructions = new JLabel("Enter upc in UPC field. Enter new aisle number:");
    this.addButtonPlusInfo(changeProductLocation, cPLinput, cPLInstructions, 0, 8);

    JLabel dateStartInstructions = new JLabel("Enter start date E.g.: "
        + "2017-07-24T10:10:10");

    this.textFieldtoBP(startDate, dateStartInstructions, 0, 9);

    JLabel dateEndInstructions = new JLabel("Enter end date, E.g.: "
        + "2017-07-24T23:10:10");
    this.textFieldtoBP(endDate, dateEndInstructions, 0, 10);

    List<JButton> dateButtons = Arrays.asList(checkProfitHistory, checkTotalProfit, putsOnSale);
    this.addNewButton(dateButtons,0, 11, new ManagerInputListener());

    JLabel cAPInstructions = new JLabel("Enter one aisle number:");
    this.addButtonPlusInfo(checkAisleProducts, cAPinput, cAPInstructions, 0, 12);

    JLabel ePOInstructions = new JLabel("Enter UPC in UPC field. Enter new quantity to order: ");
    this.addButtonPlusInfo(editpendingOrders, ePOinput, ePOInstructions, 0, 13);

    JLabel aNMInstructions = new JLabel("Enter new member file's filepath:");
    this.addButtonPlusInfo(addNewMembers, aNMinput, aNMInstructions, 0, 14);

    JLabel aSCInstructions = new JLabel
        ("Enter UPC in UPC field. Enter new sale/regular price, E.g. 5.99");
    this.addButtonPlusInfo(addToPriceCart, aPCinput, aSCInstructions, 0, 15);
    this.addNewButton(Arrays.asList(removeFromPriceCart), 0,16, new ManagerInputListener());

    List<JButton> inputButtons = Arrays.asList(changeDefaultQuantity,changeProductLocation,
        checkAisleProducts, editpendingOrders, addNewMembers, addToPriceCart, removeFromPriceCart);

    for(JButton button: inputButtons){
      button.addActionListener(new ManagerInputListener());
    }
  }

  /**
   * This method adds all buttons for a Manager that will be shown in the user interface.
   */
  public void addAllManagerButtons(){

    List<JButton> allButtons = Arrays.asList(clearPriceCart, changeRegPrice, checkPerformance,
        createBackOrderFile, checkSalePriceHist, checkpendingOrders, removePendingOrder, sendOrders, checkOnSale,
        closeStore, checkRegPriceHist);

    this.addNewButton(allButtons, 0, 17, new ManagerButtonListener());

  }

  /**
   * Sets personalFrame to frame.
   * @param frame A JFrame to which the personalFrame is assigned.
   */
  public void setFrame(JFrame frame){
    personalFrame = frame;
  }

  /**
   * Handles the button events recorded from the user interface and then take action accordingly.
   */
  private class ManagerInputListener implements ActionListener {

    /**
     * Check if the date can be parsed into LocalDateTime
     *
     * @param date String representation of the date
     * @return boolean of whether the date is valid or not
     */
    public boolean checkValidDate(String date) {
      try {
        LocalDateTime.parse(date);
        return true;
      } catch (DateTimeParseException e) {
        return false;
      }
    }

    /**
     * Check what the manager has entered and pressed  and send information to
     * <code>ManagerButtonManager</code> depending on which event was performed.
     *
     * @param event The action performed on the panel
     */
    public void actionPerformed(ActionEvent event) {
      Object source = event.getSource();
      String start = startDate.getText();
      String end = endDate.getText();
      String upc = upcInput.getText();
      Integer aisleNum = (Integer) cAPinput.getValue();
      Integer quantityEPO = (Integer) ePOinput.getValue();
      Integer quantityCDQ = (Integer) cDQquantity.getValue();
      Double price = (Double) aPCinput.getValue();

      if (source == changeDefaultQuantity && managerBM.callCheckUPCValid(upc)) {
        clearDisplayAnswer();
        addTextDisplayAnswer(managerBM.callChangeDefaultQuantity(upc, quantityCDQ));
        cDQquantity.setText("");
        upcInput.setText("");

      }else if(source == addToPriceCart && managerBM.callCheckUPCValid(upc)) {
        clearDisplayPriceCart();
        clearDisplayAnswer();
        addTextDisplayAnswer(managerBM.callAddToPriceCart(upc, price));
        addTextDisplayPriceCart(managerBM.callGetPriceCart());

      } else if (source == removeFromPriceCart && managerBM.callCheckUPCValid(upc)) {
        clearDisplayPriceCart();
        clearDisplayAnswer();
        addTextDisplayAnswer(managerBM.callRemoveFromPriceCart(upc));
        addTextDisplayPriceCart(managerBM.callGetPriceCart());

      } else if(source == changeProductLocation && managerBM.callCheckUPCValid(upc)){
        clearDisplayAnswer();
        Integer location = (Integer) cPLinput.getValue();
        addTextDisplayAnswer(managerBM.callChangeProductLocation(upc, location));

      }else if(source == checkProfitHistory && checkValidDate(start) && checkValidDate(end)){
        clearDisplayAnswer();
        addTextDisplayAnswer(managerBM.callCheckProfitHistory(start, end));

      }else if(source == checkTotalProfit && checkValidDate(start) && checkValidDate(end)){
        clearDisplayAnswer();
        addTextDisplayAnswer(managerBM.callCheckTotalProfit(start, end));

      }else if(source == checkAisleProducts){
        clearDisplayAnswer();
        addTextDisplayAnswer(managerBM.callCheckAisleProducts(aisleNum));

      } else if(source == editpendingOrders && managerBM.callCheckUPCValid(upc)){
        clearDisplayAnswer();
        addTextDisplayAnswer(managerBM.callEditPendingOrder(upc, quantityEPO));

      }else if(source == addNewMembers){
        clearDisplayAnswer();
        String filepath = aNMinput.getText();
        addTextDisplayAnswer(managerBM.callAddNewMembers(filepath));

      }else if(source == putsOnSale && checkValidDate(start) && checkValidDate(end) &&
          !managerBM.callIsSaleCartEmpty()){
        clearDisplayAnswer();
        try {
          addTextDisplayAnswer(managerBM.callPutOnSale(start, end));
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * Handles the button events recorded from the user interface and then take action accordingly.
   * This method has a parameter of Store type.
   * @param currStore The Store that a manager is managing and viewing in the user interface.
   */
  public void setSystemIOAbility(Store currStore){
    this.currStore = currStore;
  }
  private class ManagerButtonListener implements ActionListener {

    /**
     * Checks which button was pressed and send it to <code>ManagerButtonManager</code> and
     * display it on the panel
     * @param event Event performed on the panel
     */
    public void actionPerformed(ActionEvent event) {
      Object source = event.getSource();
      String upc = upcInput.getText();
      clearDisplayAnswer();

      if (source == checkPerformance) {
        clearDisplayAnswer();
        addTextDisplayAnswer(managerBM.callCheckPerformance());

      } else if(source == changeRegPrice && !managerBM.callIsSaleCartEmpty()){
        clearDisplayAnswer();
        try {
          addTextDisplayAnswer(managerBM.callChangeRegPrice());
        } catch (IOException e) {
          e.printStackTrace();
        } catch (ParseException e) {
          e.printStackTrace();
        }

      } else if(source == createBackOrderFile){
        clearDisplayAnswer();
        try {
          addTextDisplayAnswer(managerBM.callCreateBackOrderFile());
        } catch (IOException e) {
          e.printStackTrace();
        }

      }else if (source == clearPriceCart){
        clearDisplayPriceCart();
        addTextDisplayAnswer(managerBM.callClearPriceCart());

      } else if(source == checkSalePriceHist){
        clearDisplayAnswer();
        addTextDisplayAnswer(managerBM.callCheckSalePriceHistory());

      } else if (source == checkRegPriceHist) {
        clearDisplayAnswer();
        addTextDisplayAnswer(managerBM.callCheckRegPriceHistory());

      } else if(source == checkpendingOrders){
        clearDisplayAnswer();
        addTextDisplayAnswer(managerBM.callCheckPendingOrders());
        addTextDisplayAnswer(managerBM.callGetPendingOrders());

      } else if (source == removePendingOrder && managerBM.callCheckUPCValid(upc)) {
        clearDisplayAnswer();
        addTextDisplayAnswer(managerBM.callRemovePendingOrder(upc));

      } else if(source == sendOrders){
        clearDisplayAnswer();
        try {
          addTextDisplayAnswer(managerBM.callSendOrders());
        } catch (IOException e) {
          e.printStackTrace();
        }

      } else if(source == checkOnSale){
        clearDisplayAnswer();
        addTextDisplayAnswer(managerBM.callCheckOnSale());

      } else if(source == closeStore){
        managerBM.callClearCart();
        managerBM.callClearPriceCart();
        clearDisplayPriceCart();
        clearDisplayAnswer();
        clearDisplayCart();
        currStore.closeStore();
        personalFrame.dispose();
      }
    }
  }
}
