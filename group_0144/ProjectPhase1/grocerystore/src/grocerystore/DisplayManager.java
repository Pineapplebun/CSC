package grocerystore;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Takes in objects that has the information of what happened and translates it to a well formatted
 * String to be returned so that it can be printed in the GUI.
 */
public class DisplayManager {

  /**
   * Takes in a Map with Product key and another Map with String key and Double value as value and
   * creates a neat String to be used to be displayed on the GUI.
   *
   * @param pricemap Map with Product key and Map with String key and Double value as value
   * @return An organized string that contains all the information in pricemap
   */
  public static String displayPriceHistory(Map<Product, Map<String, Double>> pricemap) {
    StringBuilder informat = new StringBuilder();
    for (Entry<Product, Map<String, Double>> item : pricemap.entrySet()) {
      informat.append(item.getKey().getName()).append("\n");
      Map<String, Double> datepricemap = item.getValue();
      for (Entry<String, Double> dateprice : datepricemap.entrySet()) {
        String datepriceline = dateprice.getKey() + " : " + dateprice.getValue();
        informat.append(datepriceline).append("\n");
      }
    }
    return informat.toString();
  }

  /**
   * Takes in a Map with K and T and creates a neat String to be used to be displayed on the GUI.
   *
   * @param orders Map of K key and T value
   * @param <K> Could be String, Product, etc... that has toString() method
   * @param <T> Could be Integer, Double, etc... that has toString() method
   * @return rAn organized string that contains all the information in orders
   */
  public static <K, T> String displayMap(Map<K, T> orders) {
    StringBuilder informat = new StringBuilder();
    for (Entry<K, T> entry : orders.entrySet()) {
      String line = entry.getKey() + " : " + entry.getValue();
      informat.append(line).append("\n");
    }
    return informat.toString();
  }

  /**
   * Returns a string representation of an array.
   * @param items the array list to represent
   * @param <E> the type of the items in the array list
   * @return the string form of the array list
   */
  public static <E> String displayArray(ArrayList<E> items) {
    StringBuilder stringform = new StringBuilder();
    for (E item : items) {
      stringform.append(item).append("\n");
    }
    return stringform.toString();
  }
}
