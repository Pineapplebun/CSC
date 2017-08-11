package grocerystore;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * IO is the input output class that manages the reading and writing of all the files that are
 * required for the functions of each class. This includes reading the backordered files, initial
 * set up data, history reading, logging data and serialization.
 */
public class IO {

  public IO() {
    super();
  }

  // SERIALIZATION ================================================================================

  /**
   * Serializes the object passed to it and outputs a file.
   *
   * @param obj the object to serialize
   * @param outputFile the file that is output
   */
  static void doSerialize(Object obj, String outputFile)
      throws IOException {
    FileOutputStream fileToWrite = new FileOutputStream(outputFile);
    BufferedOutputStream buffer = new BufferedOutputStream(fileToWrite);
    ObjectOutputStream objToWrite = new ObjectOutputStream(buffer);
    objToWrite.writeObject(obj);
    objToWrite.close();
  }

  /**
   * Deserializes the input file.
   *
   * @param inputFile the input file that is deserialized
   * @return the deserialized object
   */
  static Object doDeserialize(String inputFile) throws IOException,
      ClassNotFoundException {
    FileInputStream fileToRead = new FileInputStream(inputFile);
    BufferedInputStream buffer = new BufferedInputStream(fileToRead);
    ObjectInputStream objToRead = new ObjectInputStream(buffer);
    return objToRead.readObject();
  }

  /**
   * Sets up the store by reading in the input and outputting the required objects.
   * @param store the store to add workers in
   * @throws IOException Throws an IOException.
   */
  public static void setUpStore(Store store) throws IOException{
    //Set up Inventory.

    //set up worker information
    File file = new File(store.workerInitialFile);
    //create a new History object

    if (file.exists()) {
      Scanner scanner = new Scanner(new FileInputStream(store.workerInitialFile));
      Worker currWorker;
      String[] workerData;

      scanner.nextLine();
      while (scanner.hasNextLine()) {
        workerData = scanner.nextLine().split(",");

        if (!store.isAWorker(workerData[0], workerData[1])) {
          switch (workerData[0]) {
            case "grocerystore.Manager":
              currWorker = new Manager(store.storeHis, workerData[1], workerData[2]);
              store.mainManager = (Manager) currWorker;
              store.workers.add(currWorker);
              break;
            case "grocerystore.Cashier":
              currWorker = new Cashier(store.storeHis, workerData[1], workerData[2]);
              store.workers.add(currWorker);
              break;
            case "grocerystore.Receiver":
              currWorker = new Receiver(store.storeHis, workerData[1], workerData[2]);
              store.workers.add(currWorker);
              break;
            case "grocerystore.Reshelver":
              currWorker = new Reshelver(store.storeHis, workerData[1], workerData[2]);
              store.workers.add(currWorker);
              break;
          }
        }
      }
      scanner.close();
    }

  }

  // HISTORY CSV METHODS ==========================================================================

  /**
   * Creates a new CSV file given a file name in the current working directory.
   * @param fileName the name of the file
   * @return a file with a filename
   * @throws IOException Throws an IOException.
   */
  static File createNewCSVFile(String fileName) throws IOException{
    File newFile = new File(fileName);
    try {
      newFile.createNewFile();
    } catch(IOException e) {
      e.printStackTrace();
    }
    return newFile;
  }

  /**
   * Write down information to csv file so that they can be printed out later. Change columns list
   * to string of csv format and then write that down to the csv of filename.
   *
   * @param file file to write information on
   * @param columns list of information to be written to csv file of filename
   * @param logging the logging to write
   * @throws IOException Throws an IOException.
   */
  static void writeToCsv(File file, List<String> columns, Logging logging)
      throws IOException {
    String line = String.join(",", columns);
    FileWriter csvwriter = new FileWriter(file, true);
    csvwriter.append(line);
    csvwriter.append("\n");
    csvwriter.close();
    logging.logWriteCsv(file.getName(), line);
  }

  // MANAGER CSV METHODS ==========================================================================

  /**
   * Creates a txt file for the manager to look at when wanting to check information (the upc and
   * name) of all of the products that are currently at that moment backordered.
   * @param backOrder a map of the backorders
   * @throws IOException Throws an IOException.
   */
  static void createCurrentBackorderedFile(Map<String, Product> backOrder) throws IOException {
    try {
      FileWriter writer = new FileWriter("currentlyBackOrdered.txt");
      for (String productUPC : backOrder.keySet()) {
        Product currProduct = backOrder.get(productUPC);
        String mainProductInfo = currProduct.getUPC() + " " + currProduct.getName();
        writer.write(mainProductInfo);
      }
      writer.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  // INVENTORY CSV METHODS ========================================================================

  /**
   * The initial set up of the inventory in order to create and store all the Product objects in a
   * file.
   * @param filePath a file path of the inventory
   * @param inventory the inventory to look in
   * @throws IOException Throws an IOException.
   */
  static void newInventory(String filePath, Inventory inventory) throws IOException {
    File file = new File(filePath);

    //set up the inventory for the first time. Everyday onwards this inventory object will just
    //be serialized and deserialized.
    if (file.exists()) {
      // FileInputStream can be used for reading raw bytes, like an image.
      Scanner scanner = new Scanner(new FileInputStream(filePath));
      String[] productData;
      Product currproduct;

      scanner.nextLine();
      while (scanner.hasNextLine()) {
        productData = scanner.nextLine().split(",");
        currproduct = new Product(productData);
        inventory.getProductMap().put(productData[0], currproduct);
        inventory.addToLayout(currproduct);
      }
      scanner.close();
    }
  }

  /**
   * Saves a java representation of the membership list into the inventory.
   * @param filePath a file path of the members
   * @param membershipData the membership data
   * @throws FileNotFoundException Throws an IOException.
   */
  static void addMembersFile(String filePath, Map<String, String[]> membershipData)
      throws FileNotFoundException {
    File file = new File(filePath);
    if (file.exists()) {
      Scanner scanner = new Scanner(new FileInputStream(filePath));
      String[] membersData;
      scanner.nextLine();
      while (scanner.hasNextLine()) {
        membersData = scanner.nextLine().split(",");
        membershipData.put(membersData[0], membersData);
      }
      scanner.close();
    }
  }
}
