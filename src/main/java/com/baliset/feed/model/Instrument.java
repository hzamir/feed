package com.baliset.feed.model;

public class Instrument
{
  private String name;
  private double bid;
  private double ask;

  public String getName() { return name; }
  public double getBid()  { return bid;  }
  public double getAsk()  { return ask;  }

  // constrain doubles to have two digits max precision
  private static double round(double value)
  {
    value *= 100;
    long tmp = Math.round(value);
    return (double) tmp / 100;
  }

  public void setBidAsk(double bid, double ask)
  {
    this.bid = round(bid); this.ask = round(ask);
  }

  public Instrument(String name, double bid, double ask)
  {
    this.name = name;
    this.bid = round(bid);
    this.ask = round(ask);
  }
  


}
