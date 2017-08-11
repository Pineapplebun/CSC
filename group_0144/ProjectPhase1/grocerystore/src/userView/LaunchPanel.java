/**
 * Created by rimael-sayed on 2017-08-05.
 */

package userView;
import grocerystore.Store;
import java.awt.CardLayout;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;

/**
 * A Launch Panel allows for the launching of thise grocery store system (either in the scenerio of
 * an initial set up or in the scenario of opening the store at the start of every morning).
 * After completeing the launch the Launch Panel allows user to return to signing in panel.
 */

public class LaunchPanel extends JPanel{

  /**
   * initialsetUp: the button used only during the first ever setup of this system in the store.
   * startday: the button used to open the store everyday in the morning before anyone can use the
   *          system.
   * closeWindow: the button available to close the window (only available if the start has not yet
   *              been opened).
   * previouslySetUp: this boolean checks if this system has been initialized for the store before.
   * nextInstructions: additional instructions for which button to press in which scenerio
   * nextButton: Now that the system has been set up, it takes the manager back to sign in page.
   * switchAbility: Stores the card layout such that the launch page and switch back to the sign in
   * parentPanel: the parent panel is stored in order for LaunchPanel to update it.
   * currStore: Launch panel holds the ability to open the store and hence needs access to the store.
   */

  private JButton initialsetUp = new JButton("Initial Setup System");
  private JButton startday = new JButton("Open Store ");
  private JButton closeWindow = new JButton("Close Window");

  private boolean previouslySetUp;

  private JLabel nextInstructions = new JLabel("If you have already set up store today, "
      + "simply click continue");
  private JButton nextButton = new JButton("Now can Sign in");
  private CardLayout switchAbility;
  private PanelContainer parentPanel;
  private Store currStore;

  public LaunchPanel(){
    initialsetUp.addActionListener(new LaunchListener());
    startday.addActionListener(new LaunchListener());
    closeWindow.addActionListener(new LaunchListener());
    nextButton.addActionListener(new LaunchListener());
    nextButton.setEnabled(false);

    boolean check = new File("inventory.ser").exists();
    if(check){
      initialsetUp.setEnabled(false);
    }

    add(initialsetUp);
    add(startday);
    add(closeWindow);

    add(nextInstructions);
    add(nextButton);
  }

  /**
   * Allows this panel to operate within a parent panel, for example after opening store its possible
   * to launch to another panel, this method sets the required inforamtion to do so.
   * @param switchAbility the cardlayout used in order to switch between panels
   * @param parentPanel the parent panelcontainer that needs to be checked and updated before
   *                    switching panels.
   */
  public void setSwitchAbility(CardLayout switchAbility, PanelContainer parentPanel){
    this.switchAbility = switchAbility;
    this.parentPanel = parentPanel;
    if(previouslySetUp){
      nextButton.setEnabled(true);
    }

  }

  /**
   * Sets for the input of data to be corresponding to the given store when store is opened.
   * @param currStore The Store to be set up when opened.
   */
  public void setSystemIOAbility(Store currStore){
    this.currStore = currStore;
  }

  /**
   * Handles the Actions made by the user on Buttons within a LaunchPanel instance. 
   */
  private class LaunchListener implements ActionListener {

    public void actionPerformed(ActionEvent event){
      Object source = event.getSource();

      if (source == initialsetUp){
        currStore.initialSetUp();
        previouslySetUp = true;
        parentPanel.setSystemLaunched(true);
        closeWindow.setEnabled(false);
        parentPanel.preventCloseWindow();
        setSwitchAbility(switchAbility, parentPanel);
      }else if(source == startday) {
        currStore.readStore();
        previouslySetUp = true;
        parentPanel.setSystemLaunched(true);
        closeWindow.setEnabled(false);
        parentPanel.preventCloseWindow();
        setSwitchAbility(switchAbility, parentPanel);

      }else if(source == closeWindow) {
        parentPanel.closeFrame();

      }else if(nextButton!=null && source == nextButton){
        //checks that the id is a valid id and that the id has the same worker type as the one bubbled in.
        switchAbility.show(parentPanel, "Sign in Page");
      }
    }
  }
}
