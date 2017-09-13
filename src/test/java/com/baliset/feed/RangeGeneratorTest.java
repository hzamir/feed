package com.baliset.feed;

import com.baliset.feed.service.*;
import org.junit.Assert;
import org.junit.Test;

public class RangeGeneratorTest
{

  private void testBoundaries(int min, int max, int trials)
  {
    RangeGenerator rg = new RangeGenerator(min, max);

    for(int i = 0; i < trials; ++i) {
      int result = rg.generate();
      Assert.assertTrue(result >= min && result <= max);
    }
  }

  // tests that the values are in range, but alas does not check for randomness of the values
  @Test public void test0_1()    { testBoundaries(0,1,   10000); }
  @Test public void test1_1()    { testBoundaries(1,1,   10000); }
  @Test public void test3_17()   { testBoundaries(3,17,  10000); }
  @Test public void test0_1000() { testBoundaries(0,1000,10000); }
}
