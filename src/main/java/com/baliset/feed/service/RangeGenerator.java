package com.baliset.feed.service;

import java.util.*;

public class RangeGenerator
{
  protected int min;
  protected int max;
  protected Random r;

  // this constructor for classic integer cases
  public RangeGenerator(int min, int max)
  {
    this.min = min;
    this.max = max;
    r = new Random();
  }

  public RangeGenerator(int size)
  {
    this.min = 0;
    this.max = size - 1;
    r = new Random();
  }

  public int generate()
  {
    return r.nextInt((max - min) + 1) + min;
  }



}
