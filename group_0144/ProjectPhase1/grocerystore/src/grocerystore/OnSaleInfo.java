package grocerystore;

import java.io.Serializable;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class OnSaleInfo implements Serializable {

  private Product product; //The Product associated with this OnSaleInfo
  private boolean onSale; //True if product is currently on sale.
  private LocalDateTime saleStartDate; //Format: MM/dd/yyyy
  private LocalDateTime saleEndDate;
  private double salePricePerQ;

  /**
   * Creates an OnSaleInfo object by associating it with the related Product.
   *
   * @param product The Product to which this OnSaleInfo belongs.
   */
  public OnSaleInfo(Product product) {
    this.product = product;
  }

  /**
   * Gets the sale price of product.
   *
   * @return The sale price of product.
   */
  double getSalePrice() {
    return salePricePerQ;
  }

  /**
   * Makes product an on-sale item, on a basis of direct pricing or providing a percentage off. This
   * method can also be used to update a on-sale price if product is already made on-sale.
   *
   * @param ifPercent True if product needs to be made on-sale by a percentage off, false if a
   * direct on-sale price will be input.
   * @param percentOff 0 if ifPercent is false, otherwise percentOff is a decimal number between 0
   * and 1, inclusive.
   * @param salePrice 0 if ifPercent is true, otherwise a direct on-sale price is specified here.
   * @param startDate The on-sale start date.
   * @param endDate The on-sale end date.
   */
  void setSalePrice(boolean ifPercent, double percentOff,
      double salePrice, LocalDateTime startDate, LocalDateTime endDate) throws
      DateTimeParseException {
    if (ifPercent) {
      double regP = product.getRegPrice();
      salePricePerQ = (1 - percentOff) * regP;
    } else {
      salePricePerQ = salePrice;
    }

    saleStartDate = startDate;
    saleEndDate = endDate;

    LocalDateTime today = LocalDateTime.now();
    if (startDate.isEqual(today)) {
      onSale = true;
    }
  }

  /**
   * Cancels the on-sale status for product.
   */
  private void setOffSale() {
    salePricePerQ = -1;
    saleStartDate = null;
    saleEndDate = null;
    onSale = false;
  }

  /**
   * Updates the current price of product based on today's datetime. It will also check if today is
   * the starting/ending date of product's on-sale period.
   */
  void priceUpdate() throws ParseException {
    LocalDateTime today = LocalDateTime.now();
    if (today.isEqual(saleStartDate) || (today.isAfter(saleStartDate) &&
        today.isBefore(saleEndDate))) {
      product.setCurrPrice(salePricePerQ);
      onSale = true;
    }
    if (today.isAfter(saleEndDate)) {
      this.setOffSale();
      product.setCurrPrice(product.getRegPrice());
      onSale = false;
    }
  }
}
