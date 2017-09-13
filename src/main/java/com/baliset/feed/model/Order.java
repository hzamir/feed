package com.baliset.feed.model;

import java.text.*;

public class Order
{
  double limitPrice;
  int quantity;

  static DecimalFormat df = new DecimalFormat("#0.0#");

  Order(double limitPrice, int quantity)
  {
    this.limitPrice = limitPrice;
    this.quantity = quantity;
  }

  // utility for output
  String priceAndQuantity()
  {
    return df.format(limitPrice)+"="+quantity;
  }

}
