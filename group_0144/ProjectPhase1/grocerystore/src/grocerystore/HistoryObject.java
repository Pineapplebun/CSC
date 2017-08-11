package grocerystore;

import java.io.Serializable;

/**
 * HistoryObject is an object that holds information of action recorded. HistoryObject is stored in
 * a list in the History class that is later used to return information of same type.
 */
class HistoryObject implements Serializable {

  String type; //type of operation
  String start; //start date if its on sale or else just date for that action
  String end; //end date if its on sale or else just date for that action
  Object author; //worker performing this action
  String productupc; //upc of the product
  Double regularprice; //regular price of the product
  Double saleprice; //sale price of the product
  Integer quantity; //quantity of the product
  Product prod; //the product
  Integer boughtorsold = 0; //number of product sold or ordered or received

  /**
   * Creates a HistoryObject with information given by History.
   *
   * @param multipurpose the quantity that is being sold or ordered.
   */
  HistoryObject(String type, String start, String end, Object author,
      Product product, Integer multipurpose) {
    this(type, start, end, author, product);
    if (type.equals("order") || type.equals("reshelf")) {
      this.boughtorsold = multipurpose;
    }
    if (type.equals("sell")) {
      this.boughtorsold = multipurpose;
    }
  }

  /**
   * Creates a HistoryObject with information given by History.
   *
   * @param type the type of operation being done
   * @param start start date of operation
   * @param end end date of operation
   * @param author the worker that is responsible for this operation
   * @param product product that operation is being held on
   */
  HistoryObject(String type, String start, String end, Object author,
      Product product) {
    this.type = type;
    this.start = start;
    this.end = end;
    this.author = author;
    this.prod = product;
    this.productupc = product.getUPC();
    this.regularprice = product.getRegPrice();
    OnSaleInfo saleInfo = product.getOnSaleInfo();
    this.saleprice = saleInfo.getSalePrice();
    this.quantity = product.getStock();
  }
}
