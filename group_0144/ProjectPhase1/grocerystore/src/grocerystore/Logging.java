package grocerystore;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

class Logging implements Serializable {

  //Instantiate a logger
  private static final java.util.logging.Logger logger =
      java.util.logging.Logger.getLogger(History.class.getName());

  /**
   * Creates Logging Object
   * @throws IOException Throws an IOException.
   */
  Logging() throws IOException {
    File log = new File("log.txt");
    // Associate the handler with the logger.
    FileHandler logfile = new FileHandler("log.txt", true);
    logger.addHandler(logfile);
    //Add formatter to the handler
    SimpleFormatter formatter = new SimpleFormatter();
    logfile.setFormatter(formatter);
    logger.setUseParentHandlers(false); //disables from printing to the console
  }

  /**
   * Log product and it's sale start and end date and it's sale price
   * @param product the product on sale
   * @param start start date of the sale
   * @param end end date of the sale
   * @param price sale price during the duration
   */
  void logOnSale(String product, String start, String end, String price) {
    logger.info(product + " on sale from " + start + " to " + end + ". Price: " +
        price);
  }

  /**
   * Log price change a product
   * @param product the product that's price is being changed
   * @param newprice the new price that is assigned to the product
   */
  void logChangePrice(String product, String newprice) {
    logger.info(product + " price changed to " + newprice);
  }

  /**
   * Log reorder of a product
   * @param product the product being ordered
   * @param orderquant the quantity of order
   */
  void logReorder(String product, String orderquant) {
    logger.info(orderquant + " of " + product + " were reordered");
  }

  /**
   * Log how many of the product was shelved
   * @param product the product being shelved
   * @param shelvedquant the quantity shelved
   */
  void logShelved(String product, String shelvedquant) {
    logger.info(shelvedquant + " of " + product + " were shelved");
  }

  /**
   * Log the quantity of a product received by the receiver
   * @param product the product that was received
   * @param worker the worker receiving the product
   * @param amount amount of product received
   */
  void logReceived(String product, String worker, String amount) {
    logger.info(amount + " " + product + " received to storage room by " + worker);
  }

  /**
   * Log the amount of product registered by a worker
   * @param product the product that was registered
   * @param worker the worker that registered
   * @param amount amount of product that was registered
   */
  void logRegistered(String product, String worker, String amount) {
    logger.info(amount + " " + product + " registered by " + worker);
  }

  /**
   * Log how many of a product were sold
   * @param product the product that was sold
   * @param sold amount of product that was sold
   */
  void logSell(String product, String sold) {
    logger.info(sold + " " + product + " sold.");
  }

  /**
   * Log how much loss there was because a customer made a return
   * @param loss the amount of money lost
   */
  void logReturn(String loss) {
    logger.info(loss + " loss from customer return");
  }

  /**
   * Log how much profit was made
   * @param profit the amount of profit made
   */
  void logProfit(String profit) {
    logger.info(profit + " earned.");
  }

  /**
   * Log when regular price history was called
   */
  void logRegPriceHistory() {
    logger.info("Returned history of products and changes of their regular prices at when");
  }

  /**
   * log when sale price history was called
   */
  void logSalePriceHistory() {
    logger.info("Returned history of products and changes of their sale prices at when");
  }

  /**
   * log when order history of a product was called
   * @param product the product the manager wants order hitory of
   */
  void logOrderHistory(String product) {
    logger.info("Returned order history of " + product);
  }

  /**
   * Log when profit history of start to end date was called
   * @param start start date of search profit history
   * @param end end date of search profit history
   */
  void logProfitHistory(String start, String end) {
    logger.info("Returned profit earned from " + start + " to " + end);
  }

  /**
   * Log what line was written to what file
   * @param file the file the line was written to
   * @param line the line that written
   */
  void logWriteCsv(String file, String line) {
    logger.info(line + " written to file " + file);
  }

  /**
   * Return the name of the file logger is logging to
   * @return the name of the text file
   */
  public String toString() {
    return "log.txt";
  }
}