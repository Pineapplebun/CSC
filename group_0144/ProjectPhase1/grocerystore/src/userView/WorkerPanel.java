/**
 * Created by rimael-sayed on 2017-08-05.
 */

package userView;
import grocerystore.buttonManager.WorkerButtonManager;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;
import javax.swing.text.*;

/**
 * Panel displaying set of actions that all workers at the grocery store should be capable of using
 * and viewing.
 */
public class WorkerPanel extends JPanel {
  JPanel buttonPanel = new JPanel();
  GridBagConstraints panelGBC = new GridBagConstraints();
  JPanel inputPanel = new JPanel();
  JPanel displayPanel = new JPanel();

  JFormattedTextField quantityInput;
  JTextField upcInput = new JTextField(12);

  JButton addToCart = new JButton("Add to Cart");
  JButton removeFromCart = new JButton("Remove from Cart");
  JButton clearCart = new JButton("Clear the Cart");
  JButton checkCost = new JButton("Check Cost");
  JButton checkLocation = new JButton("Check Location");
  JButton changeQuantity = new JButton("Change Quantity");
  JButton checkCurrQuantity = new JButton("Check Current Stock");
  JButton setReOrderQuantity = new JButton("Set ReOrdering Quantity");

  JTextArea displayCart;
  JTextArea displayPriceCart;
  JTextArea displayAnswer = new JTextArea(8, 27);

  JButton signOut = new JButton("Sign out");

  JTextField queryText = new JTextField(15);
  JButton answer = new JButton("Answer");
  JButton queries = new JButton("Queries");

  private CardLayout switchAbility;
  private JPanel parentPanel;
  private WorkerButtonManager workerBM;

  public WorkerPanel(boolean aManagerPanel){
    setLayout(new BorderLayout());

    signOut.addActionListener(new ButtonPanelListener());
    add(signOut, BorderLayout.NORTH);

    // set default text
    upcInput.setText("");

    // NOTE: This is how you make the text field only output i.e. Integer
    NumberFormatter numberFormatter = new NumberFormatter();
    numberFormatter.setValueClass(Integer.class);
    quantityInput = new JFormattedTextField(numberFormatter);
    quantityInput.setValue(1);
    quantityInput.setColumns(12);

    addAllButtons();
    addInputWidgets();

    if(aManagerPanel){
      addManagerDisplayPanel();
    }else{
      addDisplayPanel();
    }
    add(buttonPanel, BorderLayout.EAST);
    add(displayPanel, BorderLayout.WEST);
    add(inputPanel, BorderLayout.SOUTH);
  }

  /**
   * Allows this panel operate within a parent panel, for example
   * after signing in its possible to launch to another panel, this method sets the
   * required inforamtion to do so.
   * @param switchAbility A CardLayout object.
   */
  public void setSwitchAbility(CardLayout switchAbility, JPanel parentPanel){
    this.switchAbility = switchAbility;
    this.parentPanel = parentPanel;
  }

  public void setWorkerBM(WorkerButtonManager workerBM){
    this.workerBM = workerBM;
  }

  public void setPanelTitle(String workerTitle){
    JLabel workertitle = new JLabel(workerTitle);
    panelGBC.gridx = 1;
    panelGBC.gridy = 0;
    buttonPanel.add(workertitle, panelGBC);
    JLabel titlespace = new JLabel("    ");
    panelGBC.gridx = 1;
    panelGBC.gridy = 1;
    buttonPanel.add(titlespace, panelGBC);

    JLabel secondspace = new JLabel("    ");
    panelGBC.gridx = 1;
    panelGBC.gridy = 2;
    buttonPanel.add(secondspace, panelGBC);

  }

  public void clearDisplayCart(){
    displayCart.setText("...Cart Display is currently empty...");
  }

  public void addTextDisplayCart(String newText){
    if(displayCart.getText().equals("...Cart Display is currently empty...")){
      displayCart.setText(newText);
    }else{
      String currText = displayCart.getText();
      displayCart.setText(currText + "\n" + newText);
    }
  }

  public void addTextDisplayAnswer(String newText){
    if(displayAnswer.getText().equals("...Answer Display is currently empty...")){
      displayAnswer.setText(newText);
    }else{
      String currText = displayAnswer.getText();
      displayAnswer.setText(currText + "\n" + newText);
    }
  }

  public void clearDisplayAnswer(){
    displayAnswer.setText("...Answer Display is currently empty...");
  }

  public void addTextDisplayPriceCart(String newText){
    if(displayPriceCart.getText().equals("...Price Cart Display is currently empty...")){
      displayPriceCart.setText(newText);
    }else{
      String currText = displayPriceCart.getText();
      displayPriceCart.setText(currText + "\n" + newText);
    }

  }

  public void clearDisplayPriceCart(){
    displayPriceCart.setText("...Price Cart Display is currently empty...");
  }

  /**
   * Adding a textfield to the button pattern at the specific grid locations.
   * @param text A JTextField object.
   * @param xposition position of description/instruction
   * @param yposition position of description/instruction
   */
  public void textFieldtoBP(JTextField text, JLabel label, int xposition, int yposition){
    panelGBC.gridx = xposition;
    panelGBC.gridy = yposition;
    buttonPanel.add(label, panelGBC);

    panelGBC.gridx += 1;
    buttonPanel.add(text, panelGBC);

  }

  public void addButtonPlusInfo(JButton button, JTextField text, JLabel label, int xposition, int yposition){
    panelGBC.gridx = xposition;
    panelGBC.gridy = yposition;
    buttonPanel.add(label, panelGBC);

    panelGBC.gridx += 1;
    buttonPanel.add(text, panelGBC);

    panelGBC.gridx += 1;
    buttonPanel.add(button, panelGBC);
  }

  /**
   * Helper function adds new button to buttonPanel
   * @param xposition: the column position the first button in the array should be placed.
   * @param yposition the row position the first button in the array should be placed.
   */
  public void addNewButton(List<JButton> allButtons, int xposition, int yposition,
                            ActionListener specificListener){

    int counterXposition = xposition;
    int counterYposition = yposition;
    for(JButton widget: allButtons){
      if(counterXposition <= 2){
        panelGBC.gridx = counterXposition;
        panelGBC.gridy = counterYposition;
        counterXposition += 1;
      }else{
        counterYposition += 1;
        counterXposition = 0;
        panelGBC.gridx = counterXposition;
        panelGBC.gridy = counterYposition;
        counterXposition = 1;
      }
      widget.addActionListener(specificListener);
      buttonPanel.add(widget, panelGBC);

    }


  }

  private void addAllButtons(){
    buttonPanel.setLayout(new GridBagLayout());

    panelGBC.gridx = 0;
    panelGBC.gridy = 3;
    JLabel upcInstructions = new JLabel("UPC");
    buttonPanel.add(upcInstructions, panelGBC);

    panelGBC.gridx = 1;
    panelGBC.gridy = 3;
    buttonPanel.add(upcInput, panelGBC);

    panelGBC.gridx = 0;
    panelGBC.gridy = 4;
    JLabel quantityInstructions = new JLabel("Enter quantity of this product and add to cart");
    buttonPanel.add(quantityInstructions, panelGBC);

    panelGBC.gridx = 1;
    panelGBC.gridy = 4;
    buttonPanel.add(quantityInput, panelGBC);


    panelGBC.gridx = 2;
    panelGBC.gridy = 4;
    addToCart.addActionListener(new ButtonPanelListener());
    buttonPanel.add(addToCart, panelGBC);

    List<JButton> allButtons = Arrays.asList(clearCart, checkCost,
        checkLocation, changeQuantity, checkCurrQuantity, setReOrderQuantity);

    addNewButton(allButtons, 0, 5, new ButtonPanelListener());

  }

  private class ButtonPanelListener implements ActionListener {

    public void actionPerformed(ActionEvent event) {
      Object source = event.getSource();

      String currUPC = upcInput.getText();
      Integer currQ = (Integer) quantityInput.getValue();

      if(source == addToCart && (workerBM.callCheckUPCValid(currUPC) || workerBM.callIsInNewItems(currUPC))) {
        //testing displaycart sizes
        // displayCart.setText("123456789999 6.99");
        // displayAnswer.setText("2017-07-24T00:00:00,  2017-07-24T23:00:10");
        // restrict input to be integer; for now assume it to be always a number
        workerBM.callAddToCart(currUPC, currQ);
        clearDisplayCart();
        addTextDisplayCart(workerBM.callGetCart());

        //once added to the workers cart, the rest of the buttons that do things with cart will be
        //good to go because the back end has the products added to cart.

        upcInput.setText("");
        quantityInput.setText("");

      }else if(source == clearCart) {
        workerBM.callClearCart();
        clearDisplayCart();

      }else if(source == checkCost && workerBM.callCheckUPCValid(currUPC)) {
        clearDisplayAnswer();
        addTextDisplayAnswer("The supplier cost per unit : " +
            workerBM.callCheckCost(currUPC));

      }else if(source == checkLocation && workerBM.callCheckUPCValid(currUPC)) {
        clearDisplayAnswer();
        addTextDisplayAnswer("Aisle number : " + workerBM.callCheckLocation(currUPC));

      }else if(source == changeQuantity && workerBM.callCheckUPCValid(currUPC)
          && !workerBM.callIsCartEmpty()) {
        clearDisplayAnswer();
        clearDisplayCart();
        addTextDisplayAnswer(workerBM.callChangeQuantity(currUPC, currQ));
        addTextDisplayCart(workerBM.callGetCart());

      }else if(source == signOut){
        //Sets this worker inactive and goes back to sign in page.
        clearDisplayAnswer();
        workerBM.callClearCart();
        workerBM.callSetNotActive();
        switchAbility.show(parentPanel, "Sign in Page");

      } else if(source == checkCurrQuantity && workerBM.callCheckUPCValid(currUPC)){
        //Sets this worker inactive and goes back to sign in page.
        clearDisplayAnswer();
        addTextDisplayAnswer("Current stock : " + workerBM.callCheckCurrQuantity(currUPC));

      } else if(source == setReOrderQuantity && workerBM.callCheckUPCValid(currUPC)){
        //Sets this worker inactive and goes back to sign in page.
        clearDisplayAnswer();
        addTextDisplayAnswer(workerBM.callSetReOrderQuantity(currUPC, currQ));

      } else if (source == answer){
        clearDisplayAnswer();
        String question = queryText.getText();
        addTextDisplayAnswer(workerBM.callQuery(question));

      } else if (source == queries){
        //prints to displayAnswer the list of possible questions.
        clearDisplayAnswer();
        addTextDisplayAnswer
            ("queryLocation, queryPrice, querySection, queryQuantity, queryThreshold");

      } else if (source == removeFromCart && !workerBM.callIsCartEmpty()) {
        clearDisplayAnswer();
        workerBM.callRemoveFromCart(currUPC);
      }
    }
  }

  private void addInputWidgets(){
    inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.PAGE_AXIS));
    JLabel instructions = new JLabel("Enter one query and click Answer. To see list "
        + "of all possible queries click Queries");

    answer.addActionListener(new ButtonPanelListener());
    queries.addActionListener(new ButtonPanelListener());

    inputPanel.add(instructions);
    inputPanel.add(queryText);

    displayAnswer.setEnabled(false);
    displayAnswer.setLineWrap(true);
    displayAnswer.setWrapStyleWord(true);
    displayAnswer.setText("...Answers Display is currently empty...");

    inputPanel.add(answer);
    inputPanel.add(queries);

    inputPanel.add(displayAnswer);

  }


  public void addDisplayPanel(){

    displayCart = new JTextArea(30, 60);
    displayCart.setEnabled(false);
    displayCart.setLineWrap(true);
    displayCart.setWrapStyleWord(true);
    displayCart.setText("...Cart Display is currently empty...");


    displayPanel.add(displayCart);
    //displayPanel.add(displayAnswer);

  }

  public void addManagerDisplayPanel(){

    displayCart = new JTextArea(30, 27);
    displayPriceCart = new JTextArea(30, 27);

    displayCart.setEnabled(false);
    displayCart.setLineWrap(true);
    displayCart.setWrapStyleWord(true);
    displayCart.setText("...Cart Display is currently empty...");

    displayPriceCart.setEnabled(false);
    displayPriceCart.setLineWrap(true);
    displayPriceCart.setWrapStyleWord(true);
    displayPriceCart.setText("...Price Cart Display is currently empty...");

    displayPanel.add(displayPriceCart);

    displayPanel.add(displayCart);
    //displayPanel.add(displayAnswer);

  }



}
