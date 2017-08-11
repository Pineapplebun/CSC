package userView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.List;
import java.util.Arrays;
import java.awt.CardLayout;
import java.awt.Dimension;

/**
 * A Sign in Panel that checks to make sure the worker type and id is an actual worker of this
 * store (allowing them to switch to their respective workerpanel. Also has the feature of allowing
 * the manager to enter a special password in order to take the manager to the Launch Panel (which
 * sets up the store data for the day).
 */

public class SignInPanel extends JPanel {

  /**
   * instructions: the sign in instructions (choose a worker type and enter id)
   * managerOption: the option clicked by a manager
   * cashierOption: the option clicked by a cashier
   * receiverOption: the option clicked by a receiver
   * reshelverOption: the option clicked by a reshelver
   *
   * labelforText: the label for the id text field
   * inputArea: the textfied to input id
   * worderId: the String representing the type of worker selected
   * workerType: the String representing the type of worker selected
   * launchPassword: the special password only the manager knows and uses in order to open the store
   *                 at the start of the day.
   * signIn: a button to check the sign in information is correct
   * closeWindow: the button to close the window.
   * switchAbility: access to the card layout in order for sign in panel to switch to launch panel
   * parentPanel: the parent panel of sign in so that it can update the parent panel.
   */
  private JLabel instructions = new JLabel("Please select your profession, type in your ID and "
      + "then click Sign In Button");
  private JRadioButton managerOption = new JRadioButton("Manager");
  private JRadioButton cashierOption = new JRadioButton("Cashier");
  private JRadioButton receiverOption = new JRadioButton("Receiver");
  private JRadioButton reshelverOption = new JRadioButton("Reshelver");

  private JLabel labelforText = new JLabel("Please enter your ID");
  private JTextField inputArea = new JTextField(20);
  private String workerId;
  private String workerType;
  private String launchPassword = "123";
  private JButton signIn;
  private JButton closeWindow;
  private CardLayout switchAbility;
  private PanelContainer parentPanel;

  /**
   * Creates a Sign in Panel.
   */
  public SignInPanel(){
    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    add(instructions);

    List<JRadioButton> allOptions = Arrays.asList(managerOption, cashierOption,
        receiverOption, reshelverOption);
    ButtonGroup workerOptions = new ButtonGroup();

    //adding to the group makes sure only one is clicked at a given time.
    for(JRadioButton button: allOptions){
      button.addActionListener(new PanelListener());
      workerOptions.add(button);
    }

    add(managerOption);
    add(cashierOption);
    add(receiverOption);
    add(reshelverOption);

    add(labelforText);
    inputArea.addActionListener(new PanelListener());
    inputArea.setSize(20, 1);
    inputArea.setMaximumSize(
        new Dimension(Integer.MAX_VALUE, inputArea.getPreferredSize().height) );
    add(inputArea);
  }

  private boolean isSetUpScenario(){
    return (workerType.equals("Manager") && workerId.equals(launchPassword));
  }

  /**
   * Allows this panel operate within a parent panel, for example
   * after signing in its possible to launch to another panel, this method sets the
   * required inforamtion to do so.
   * @param switchAbility switchAbility is a CardLayout object.
   */
  public void setSwitchAbility(CardLayout switchAbility, PanelContainer parentPanel){
    this.signIn = new JButton("Sign In");
    signIn.addActionListener(new PanelListener());
    add(signIn);

    this.closeWindow = new JButton("Close Window");
    closeWindow.addActionListener(new PanelListener());
    add(closeWindow);

    this.switchAbility = switchAbility;
    this.parentPanel = parentPanel;
  }

  /**
   * Changes the closing window button such that it can no longer be clicked.
   */
  public void changeCloseWindow(){
    closeWindow.setEnabled(false);
  }

  /**
   * An action listener class which responds to the JButtons used in the Sign In Panel instances.
   */
  private class PanelListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      Object source = event.getSource();

      if (source == managerOption) {
        workerType = "Manager";

      } else if (source == cashierOption) {
        workerType = "Cashier";

      } else if (source == receiverOption) {
        workerType = "Receiver";

      } else if (source == reshelverOption) {
        workerType = "Reshelver";

      }else if(source == closeWindow && !parentPanel.getSystemLaunched() ){
        parentPanel.closeFrame();

      } else if (signIn != null && source == signIn) {
        workerId = inputArea.getText();
        inputArea.setText("");
        if (workerId != null && workerType != null) {
          if (parentPanel.getIsAWorker(workerType, workerId) && parentPanel.getSystemLaunched()) {
            switch (workerType) {
              case "Cashier":
                parentPanel.setUpCashier();
                switchAbility.show(parentPanel, "Cashier Action Panel");
                break;
              case "Receiver":
                parentPanel.setUpReceiver();
                switchAbility.show(parentPanel, "Receiver Action Panel");
                break;
              case "Reshelver":
                parentPanel.setUpReshelver();
                switchAbility.show(parentPanel, "Reshelver Action Panel");
                break;
              case "Manager":
                parentPanel.setUpManager();
                switchAbility.show(parentPanel, "Manager Action Panel");
                break;
            }
          } else if (!parentPanel.getSystemLaunched()) {
            if (isSetUpScenario()) {
              switchAbility.show(parentPanel, "Launch Page");
            } else {
              //popup window
              JOptionPane.showMessageDialog(null, "Inform Manager to set"
                  + "up store system!");
            }
          } else if (!parentPanel.getIsAWorker(workerType, workerId)) {
            JOptionPane.showMessageDialog(null, "Position or "
                + "Id incorrect!");
          }
        }
        }
      }
  }
}
