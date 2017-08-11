package grocerystore;

import grocerystore.buttonManager.CashierButtonManager;
import grocerystore.buttonManager.ManagerButtonManager;
import grocerystore.buttonManager.ReceiverButtonManager;
import grocerystore.buttonManager.ReshelverButtonManager;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.JFrame;
import userView.PanelContainer;

/**
 * The Store class starts up the GUI and the store. The store consists of workers, inventory, and
 * a history to scribe all events that take place. Upon first initialization, the store requires
 * an initialItems list and initialWorkers list in csv.
 */
public class Store {

  /**
   * inventoryInitialFile : the initial inventory filepath
   * workerInitialFile : the initial workers filepath
   * workers : a list of workers that work at the store
   * storeHis : the history scribe that records all actions of the workers
   * inventory : the inventory that holds the products
   * loadInventory : the name of the inventory ser file
   * loadWorker : the name of the worker ser file
   * currWorker : the current worker of the store
   * mainManager : the main manager of the store
   * storeName : the name of the store
   */
  String inventoryInitialFile;
  String workerInitialFile;
  ArrayList<Worker> workers;
  History storeHis;
  Inventory inventory;

  String loadInventory = "inventory.ser";
  String loadWorker = "workerData.ser";

  Worker currWorker;
  Manager mainManager;

  String storeName;

  /**
   * Instantiate Store
   * @param storeName name of the Store
   * @param inventoryFile ser file that contains the serialized inventory object
   * @param workerFile ser file that contains the serialized worker objects
   * @throws IOException Throws an IOException.
   */
  private Store(String storeName, String inventoryFile, String workerFile) throws IOException{
    this.storeName = storeName;
    this.inventoryInitialFile = inventoryFile;
    this.workerInitialFile = workerFile;
    this.workers = new ArrayList<>();
    this.storeHis = new History();
  }

  /**
   * Return the name of the store
   * @return The name of the store
   */
  private String getStoreName() {
    return storeName;
  }

  /**
   * Set up the store for when it is first used
   */
  public void initialSetUp(){

    try{
      inventory = new Inventory(inventoryInitialFile);
      IO.setUpStore(this);
      mainManager.setWorkers(workers);
      updateStore(mainManager, inventory);
    }catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Deserialize the Inventory and Workers
   */
  public void readStore(){
    //Deserialize inventory --> call a method that just deseriliazes file paths, pass in inventory
    //file path and worker file path. The file paths used are loadInventory and loadWorker

    try {
      inventory = (Inventory) IO.doDeserialize(loadInventory);
      workers = (ArrayList<Worker>) IO.doDeserialize(loadWorker);
      IO.setUpStore(this);

      for(Worker worker : workers){
        if(worker.getWorkerType().equals("Manager")){
          mainManager = (Manager)worker;
        }
      }
      updateStore(mainManager, inventory);
    } catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Deactivate all the Workers and serialize the Inventory and the Workers.
   */
  public void closeStore(){
    //set every workers as deactive in their active boolean variable
    //Serialize inventory and arraylist of workers to loadInventory and loadWorker filepaths.
    for (Worker worker : workers) {
      worker.setNotActive();
    }

    try {
      IO.doSerialize(inventory, loadInventory);
      IO.doSerialize(workers, loadWorker);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Call update store from the manager
   * @param manager The manager responsible for this store
   */
  private void updateStore(Manager manager, Inventory inventory){
    try{
      manager.updateStore(inventory);
    }catch(ParseException e){
      e.printStackTrace();
    }

  }

  /**
   * Check if there is a worker with the right type and id and set it active if it is a worker
   * @param workerType Type of the worker
   * @param id ID of the worker
   * @return True if the person with id is a valid worker.
   */
  public boolean isAWorker(String workerType, String id){
    boolean isAWorker = workerExists(workerType, id);
    if (isAWorker) {
      currWorker.setActive();
    }
    return isAWorker;
  }

  /**
   * Return whether this worker exists.
   * @param workerType the type of the worker
   * @param id the id of the worker
   * @return whether the worker exists
   */
  public boolean workerExists(String workerType, String id){
    for(Worker w: workers){
      if((w.id.equals(id)) && (w.getWorkerType().equals(workerType))){
        currWorker = w;
        return true;
      }
    }
    return false;
  }


  /**
   * Return a manager's button manager.
   * @return a manager's button manager
   */
  public ManagerButtonManager getManagerBM(){
    return new ManagerButtonManager((Manager)currWorker, inventory);
  }

  /**
   * Return a cashier's button manager.
   * @return a cashier's button manager
   */
  public CashierButtonManager getCashierBM(){
    return new CashierButtonManager((Cashier)currWorker, inventory);
  }

  /**
   * Return a receiver's button manager.
   * @return a receiver's button manager
   */
  public ReceiverButtonManager getReceiverBM(){
    return new ReceiverButtonManager((Receiver)currWorker, inventory);
  }

  /**
   * Return a receiver's button manager.
   * @return a receiver's button manager
   */
  public ReshelverButtonManager getReshelverBM(){
    return new ReshelverButtonManager((Reshelver)currWorker, inventory);
  }

  /**
   * The main method that runs the store.
   * @param args Inapplicable.
   * @throws IOException Throws an IOException.
   */
  public static void main(String[] args) throws IOException{
    Store currStore = new Store("AMAZING grocery store title window",
        "InitialItems.csv", "InitialWorkers.csv");
    JFrame frame = new JFrame(currStore.getStoreName());

    //Add Panel container and set all the information it needs to set up.
    PanelContainer thisPanel = new PanelContainer();
    thisPanel.setFrame(frame);
    thisPanel.setSystemPanel(currStore);

    //Adjust features of this frame.
    frame.add(thisPanel);
    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);

  }

}
