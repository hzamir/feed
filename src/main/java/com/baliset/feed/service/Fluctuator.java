package com.baliset.feed.service;

public class Fluctuator
{
  private RangeGenerator rg;
  private RangeGenerator overUnder;


  // this is a convenience constructor
  public Fluctuator(double min, double max)
  {
    overUnder = new RangeGenerator(0,1);
    rg = new RangeGenerator(dtoi(min), dtoi(max));   // degree of fluctuation
  }

  static final int dtoi(double v) { return (int)(v * 100.0); }
  static final double itod(int i) { return (double)i * 0.01; }

  public double fluctuate(double v)
  {
    int i = rg.generate();

    if(overUnder.generate() > 0) {
      return v + itod(i);
    } else {
      return v -  itod(i);
    }
  }

}
