package userView;
import grocerystore.buttonManager.*;
import grocerystore.Store;
import javax.swing.*;
import java.awt.CardLayout;

/**
 * The instance of Panel Container creates a sign in and launch panel in order to set up the
 * grocery system, and depending on the information inputted to the sign in panel, creates and
 * sets up the respective worker panel of the worker who signed in (along with getting and
 * setting the specific button manager for that panel to function).
 */
public class PanelContainer extends JPanel{

  /**
   * sIPanel: The signinPanel that loads when the program is run.
   * launchPanel: the launch panel created for setting up the program at start of the day.
   * cardlayout: PanelContainer's specifc type of layout (in order to smoothyl switch between panels
   * systemLaunched: boolean tracks if the Launch panel has set up the system.
   * personalFrame: allows for closure of the JFrame this panel resides in.
   * currStore: the Store that this system belongs to.
   */
  private SignInPanel sIPanel = new SignInPanel();
  private LaunchPanel launchPanel = new LaunchPanel();
  private CardLayout cardlayout = new CardLayout();

  private boolean systemLaunched;
  private JFrame personalFrame;
  private Store currStore;


  public PanelContainer(){
    setLayout(cardlayout);

    //add sign in panel and a sign in Button that has the ability to switch to another panel.
    sIPanel.setSwitchAbility(cardlayout, this);
    add(sIPanel, "Sign in Page");

    //adding these purely viewable panels will allow the JFrame to pack to the largest size.
    add(new ManagerPanel());
    add(new CashierPanel());
    add(new ReceiverPanel());
    add(new ReshelverPanel());

    cardlayout.show(this, "Sign in Page");
  }

  /**
   * If the Sign in indicates that an initial laucnh is necessary, a launch panel is set and added
   * to this panel container.
   * @param store the store passed in so that it's information can be set up.
   */

  public void setSystemPanel(Store store){
    //add launch panel
    this.currStore = store;
    launchPanel.setSwitchAbility(cardlayout, this);
    launchPanel.setSystemIOAbility(currStore);
    add(launchPanel, "Launch Page");

  }

  /**
   * Gets the Store to check if this worker sign in information belongs to an actual worker.
   * @param workerType the type of worker: Manager, Cashier, Receiver, or Reshelver
   * @param workerId a unique id given to each worker.
   * @return True if the signed in person is a valid worker.
   */
  public boolean getIsAWorker(String workerType, String workerId){
    return currStore.isAWorker(workerType, workerId);
  }

  /**
   * sets the JFrame this panel resides in.
   * @param frame The Jframe this panel resides in.
   */
  public void setFrame(JFrame frame){
    personalFrame = frame;
  }

  /**
   * Allows for a button in one of the panels to close the JFrame.
   */
  public void closeFrame(){
    personalFrame.dispose();
  }

  /**
   * Prevents the user from clickin gthe close window button.
   */
  public void preventCloseWindow(){
    sIPanel.changeCloseWindow();
  }

  /**
   * if a Cashier has successfully signed in, then the cashier panel is set up and added to
   * this panel container.
   */
  public void setUpCashier(){
    CashierPanel cashierPanel = new CashierPanel();
    cashierPanel.setCashierBM(currStore.getCashierBM());
    cashierPanel.setSwitchAbility(cardlayout, this);
    add(cashierPanel, "Cashier Action Panel");

  }

  /**
   * if a Manager has successfully signed in, then the manager panel is set up and added to
   * this panel container.
   */
  public void setUpManager(){
    ManagerPanel managerPanel = new ManagerPanel();
    managerPanel.setSwitchAbility(cardlayout, this);
    managerPanel.setManagerBM(currStore.getManagerBM());
    managerPanel.setFrame(personalFrame);
    managerPanel.setSystemIOAbility(currStore);
    add(managerPanel, "Manager Action Panel");

  }

  /**
   * if a Reshelver has successfully signed in, then the reshelver panel is  set up and added to
   * this panel container.
   */
  public void setUpReshelver(){
    //add worker panels
    ReshelverPanel reshelverPanel = new ReshelverPanel();
    reshelverPanel.setSwitchAbility(cardlayout, this);
    reshelverPanel.setReshelverBM(currStore.getReshelverBM());
    add(reshelverPanel, "Reshelver Action Panel");

  }

  /**
   * if a Receiver has successfully signed in, then the receiver panel will be set up and added to
   * this panel container.
   */
  public void setUpReceiver(){
    ReceiverPanel receiverPanel = new ReceiverPanel();
    receiverPanel.setSwitchAbility(cardlayout, this);
    receiverPanel.setReceiverBM(currStore.getReceiverBM());
    add(receiverPanel, "Receiver Action Panel");

  }

  /**
   * Sets if the system has been successfully launched the panelContainer records that information from
   * the launch panel.
   * @param systemLaunched true if the system has been launched (intiail set up or open store options)
   */
  public void setSystemLaunched(boolean systemLaunched){
    this.systemLaunched = systemLaunched;
  }

  /**
   * gets if system is laucnhed since in order for users to sign in the System must be launched,
   * therefore the Sign in panel within the panel container can get this information.
   * @return boolean true if the system has been laucnhed
   */
  public boolean getSystemLaunched(){
    return systemLaunched;
  }





}
