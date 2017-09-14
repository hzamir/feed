package com.baliset.feed.model;

import java.text.*;

public class AgOrder
{
  double limitPrice;
  int quantity;

  private static DecimalFormat df = new DecimalFormat("#0.0#");

  AgOrder(double limitPrice, int quantity)
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
