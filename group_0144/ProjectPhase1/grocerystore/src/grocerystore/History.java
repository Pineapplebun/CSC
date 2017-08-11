package grocerystore;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * History class logs events that happen to log.txt and record histories of events that happen
 * mainly for manager to later look up things such as the profits made at when and when something
 * was ordered.When these things are recorded they are recorded to separate csv files so that they
 * can be printed if needed.
 */
public class History implements Serializable {

  //Array list of HistoryObjects
  private List<HistoryObject> historylist;
  //Hash map of date and profit made
  private Map<String, Double> profitmap;
  //Hash map of product and Hash map of date and new regular price
  private Map<Product, Map<String, Double>> regmap;
  //Hash map of product and Hash map of date and new sale price
  private Map<Product, Map<String, Double>> salemap;
  //Logger object that does logging only
  private Logging logging;

  //========== Files ===========
  File salefile;
  File pricefile;
  File orderfile;
  File sellfile;
  File profitfile;
  File shelvedfile;

  /**
   * When history constructor is called, necessary files are created if they don't already exist.
   * Handler is created and a formatter is added to that handler. Finally, handler is added into the
   * logger.
   *
   * @throws IOException Throws an IOException.
   */
  public History() throws IOException {
    historylist = new ArrayList<>();
    profitmap = new HashMap<>();
    // This should have products inside...
    regmap = new HashMap<Product, Map<String, Double>>();
    salemap = new HashMap<Product, Map<String, Double>>();
    //create csv files if they aren't there already
    salefile = IO.createNewCSVFile("salehistory.csv");
    pricefile = IO.createNewCSVFile("pricehistory.csv");
    orderfile = IO.createNewCSVFile("orderhistory.csv");
    sellfile = IO.createNewCSVFile("sellhistory.csv");
    profitfile = IO.createNewCSVFile("profithistory.csv");
    shelvedfile = IO.createNewCSVFile("shelvedhistory.csv");
    logging = new Logging();
  }

  /**
   * Records the start and end date of sale of products and their sale prices and who this operation
   * was done by to HistoryObject and salemap. Then, write them down to salehistory.csv. Also,
   * records what happened to log.txt.
   *
   * @param w the worker that is performing the operation
   * @param start start date of sale
   * @param end end date of sale
   * @param pricing map of product and the sale price
   * @throws IOException Throws an IOException.
   */
  void recordOnSale(Worker w, String start, String end, Map<Product, Double> pricing)
      throws IOException {
    Set<Product> products = pricing.keySet();
    for (Product p : products) {
      ArrayList<String> csvline = new ArrayList<>(6);
      if (salemap.containsKey(p)) {
        salemap.get(p).put(start, pricing.get(p));
      } else {
        Map<String, Double> tempmap = new HashMap<String, Double>();
        tempmap.put(start, pricing.get(p));
        salemap.put(p, tempmap);
      }
      HistoryObject histob = new HistoryObject("discount", start, end, w,
          p);
      historylist.add(histob);
      csvline.add(start);
      csvline.add(end);
      csvline.add(pricing.get(p).toString());
      csvline.add(String.valueOf(p.getRegPrice()));
      csvline.add(p.getUPC());
      csvline.add(w.toString());
      IO.writeToCsv(salefile, csvline, this.logging);
      logging.logOnSale(p.getName(), start, end, pricing.get(p).toString());
    }
  }

  /**
   * Records the change of a products' regular prices and by who into HistoryObject and regmap.
   * Writes down those changes into pricehistory.csv and log.txt.
   *
   * @param w the worker that is performing the action
   * @param pricing map of product and the new regular price
   * @throws IOException Throws an IOException.
   */
  void recordChangeRegPrice(Worker w, Map<Product, Double> pricing)
      throws IOException {
    LocalDateTime today = LocalDateTime.now();
    String dateoftoday = today.toString();
    Set<Product> products = pricing.keySet();
    ArrayList<String> csvline = new ArrayList<>(4);
    for (Product p : products) {
      csvline.clear();
      if (regmap.containsKey(p)) {
        regmap.get(p).put(dateoftoday, pricing.get(p));
      } else {
        Map<String, Double> tempmap = new HashMap<String, Double>();
        tempmap.put(dateoftoday, pricing.get(p));
        regmap.put(p, tempmap);
      }
      HistoryObject histob = new HistoryObject("pricechange", dateoftoday, dateoftoday, w,
          p);
      historylist.add(histob);
      csvline.add(dateoftoday);
      csvline.add(String.valueOf(p.getRegPrice()));
      csvline.add(p.getUPC());
      csvline.add(w.toString());
      IO.writeToCsv(pricefile, csvline, this.logging);
      logging.logChangePrice(p.getName(), String.valueOf(p.getRegPrice()));
    }
  }

  /**
   * Records the number of orders of a product and by who into HistoryObjects. Writes down those
   * changes into pricehistory.csv and log.txt.
   *
   * @param product product that is being ordered
   * @param quantity quantity of the product being ordered
   * @throws IOException Throws an IOException.
   */
  void recordReorder(Product product, Integer quantity) throws IOException {
    LocalDateTime today = LocalDateTime.now();
    String dateoftoday = today.toString();
    HistoryObject histob = new HistoryObject("order", dateoftoday, dateoftoday,
        product.getDistributor(), product, quantity);
    historylist.add(histob);
    ArrayList<String> csvline = new ArrayList<>(5);
    csvline.add(dateoftoday);
    csvline.add(quantity.toString());
    csvline.add(product.getName());
    csvline.add(product.getUPC());
    csvline.add(product.getDistributor());
    IO.writeToCsv(orderfile, csvline, this.logging);
    logging.logReorder(product.getName(), quantity.toString());
  }

  /**
   * Go through each product and integer in map and make a HistoryObject and add it to the
   * historylist.
   *
   * @param w the worker that is performing this action
   * @param map map of product and integer amount of products being shelved
   * @throws IOException Throws an IOException.
   */
  void recordShelved(Worker w, Map<Product, Integer> map) throws IOException {
    Set<Product> products = map.keySet();
    LocalDateTime today = LocalDateTime.now();
    String dateoftoday = today.toString();
    ArrayList<String> csvline = new ArrayList<>(5);
    for (Product p : products) {
      csvline.clear();
      HistoryObject histob = new HistoryObject("reshelf", dateoftoday, dateoftoday, w, p,
          map.get(p));
      csvline.add(dateoftoday);
      csvline.add(p.getName());
      csvline.add(p.getUPC());
      csvline.add(map.get(p).toString());
      csvline.add(w.toString());
      IO.writeToCsv(shelvedfile, csvline, this.logging);
      logging.logShelved(p.getName(), map.get(p).toString());
    }
  }

  /**
   * Logs how many of what product was received to storage room by who
   *
   * @param w the receiver that is receiving
   * @param cart map of products and integer of how many they were received
   */
  void recordReceived(Worker w, Map<Product, Integer> cart) {
    Set<Product> products = cart.keySet();
    for (Product product : products) {
      logging.logReceived(product.getName(), w.toString(), cart.get(product).toString());
    }
  }

  /**
   * Logs how many of what product was registered by who
   *
   * @param w the receiver that is registering
   * @param cart map of products and integer of how many they were registered
   */
  void recordRegistered(Worker w, Map<Product, Integer> cart) {
    Set<Product> products = cart.keySet();
    for (Product product : products) {
      logging.logRegistered(product.getName(), w.toString(), cart.get(product).toString());
    }
  }

  /**
   * Records to sellhitory file how many of products were sold. Sends total profit earned to
   * recordProfit method to be recorded to a separate file. Record what happened to sellhistory.csv
   * and log.txt.
   *
   * @param w the worker that is performing this action
   * @param sold map of product and integer amount of product being sold
   * @param profit profit that is made from selling all of the products
   * @throws IOException Throws an IOException.
   */
  void recordSell(Worker w, Map<Product, Integer> sold, Double profit)
      throws IOException {
    Set<Product> products = sold.keySet();
    ArrayList<String> csvline = new ArrayList<>(4);
    LocalDateTime today = LocalDateTime.now();
    String dateoftoday = today.toString();
    for (Product p : products) {
      csvline.clear();
      HistoryObject histob = new HistoryObject("buy", dateoftoday, dateoftoday, w,
          p, sold.get(p));
      historylist.add(histob);
      csvline.add(dateoftoday);
      csvline.add(String.valueOf(histob.boughtorsold));
      csvline.add(p.getUPC());
      csvline.add(w.toString());
      IO.writeToCsv(sellfile, csvline, this.logging);
      logging.logSell(p.getName(), String.valueOf(histob.boughtorsold));
    }
    recordProfit(w, dateoftoday, profit);
  }

  /**
   * Record how much was returned to profits so it can be viewed when profit is viewed.
   *
   * @param w the worker processing the return
   * @param loss how much loss this return is responsible for
   * @throws IOException Throws an IOException.
   */
  void recordReturn(Worker w, Double loss) throws IOException {
    LocalDateTime today = LocalDateTime.now();
    String when = today.toString();
    recordProfit(w, when, loss);
    logging.logReturn(loss.toString());
  }

  /**
   * Records the profit earned at checkout and by who it was checked-out at when to
   * profithistory.csv file and log to log.txt.
   *
   * @param w the worker that is performing this action
   * @param when when the profit was made
   * @param profit profit made at that time
   * @throws IOException Throws an IOException.
   */
  void recordProfit(Worker w, String when, Double profit) throws IOException {
    profitmap.put(when, profit);
    ArrayList<String> csvline = new ArrayList<>(3);
    csvline.add(w.toString());
    csvline.add(profit.toString());
    csvline.add(when);
    IO.writeToCsv(profitfile, csvline, this.logging);
    if (profit > 0) {
      logging.logProfit(profit.toString());
    }
  }

  //Wasn't being used but left it in just in case it might be needed later.
  /*
  /**
   * Sorts through historylist and return a map of product and a list of how many were sold at each
   * checkout.
   *
   * @param start  date of which to get sell history from
   * @param end date of which to get sell history until
   * @param item product which sell history is returned
   * @return map of date and integer amount of products sold
   * @throws ParseException
   *
  Map<String, Integer> getSellHistory(String start,String end, Product item)
      throws ParseException {
    LocalDateTime date1 = LocalDateTime.parse(start);
    LocalDateTime date2 = LocalDateTime.parse(end);
    Map<String, Integer> soldorbought = new HashMap<String,Integer>();
    for (HistoryObject history : historylist) {
      if (history.type.equals("sell") && history.productupc.equals(item.getUPC())) {
        LocalDateTime date3 = LocalDateTime.parse(history.start);
        if ((date3.isAfter(date1) || date3.isEqual(date1))
            && (date2.isAfter(date3) || date2.isEqual(date3))) {
          soldorbought.put(history.start, history.boughtorsold);
        }
      }
    }
    return soldorbought;
  }
*/

  /**
   * Return regmap which contains changes of regular price of products.
   *
   * @return the map of key products and map values with date key and price value.
   */
  Map<Product, Map<String, Double>> getRegPriceHistory() {
    logging.logRegPriceHistory();
    return regmap;

  }

  /**
   * Return salemap which contains changes of regular price of products.
   *
   * @return the map of key products and map values with start date of sale key and price value.
   */
  Map<Product, Map<String, Double>> getSalePriceHistory() {
    logging.logSalePriceHistory();
    return salemap;
  }

  /**
   * Return the history of orders that have been made.
   *
   * @param prod the product of which order history is returned.
   * @return map of date and number of orders
   */
  Map<String, Integer> getOrderHistory(Product prod) {
    Map<String, Integer> orders = new HashMap<String, Integer>();
    for (HistoryObject history : historylist) {
      if (history.type.equals("order") && history.productupc.equals(prod.getUPC())) {
        orders.put(history.start, history.boughtorsold);
      }
    }
    logging.logOrderHistory(prod.getName());
    return orders;
  }

  /**
   * Return the history of profits of all the dates between start and end.
   *
   * @param start the start to find profit history from
   * @param end the end to find  profit history until
   * @return map of date and profit made on that date
   */
  Map<String, Double> getProfitHistory(String start, String end)
      throws DateTimeParseException {
    LocalDateTime date1 = LocalDateTime.parse(start);
    LocalDateTime date2 = LocalDateTime.parse(end);
    Map<String, Double> profits = new HashMap<String, Double>();
    Set<String> recorddates = profitmap.keySet();
    for (String recorddate : recorddates) {
      LocalDateTime date3 = LocalDateTime.parse(recorddate);
      if ((date3.isAfter(date1) || date3.isEqual(date1))
          && (date2.isAfter(date3) || date2.isEqual(date3))) {

        profits.put(recorddate, profitmap.get(recorddate));
      }
    }
    logging.logProfitHistory(start, end);
    return profits;
  }
}

