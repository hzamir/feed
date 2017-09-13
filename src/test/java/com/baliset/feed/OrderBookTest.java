package com.baliset.feed;

import com.baliset.feed.model.*;
import org.junit.Assert;
import org.junit.Test;

public class OrderBookTest {

  @Test
  public void testBasicOrderBook() {
    OrderBook orderBook = new OrderBook("foo");

    orderBook.bid(430, 5);
    orderBook.bid(430, 5);
    orderBook.bid(430.10, 10);
    Assert.assertEquals("{bids={430.1=10, 430.0=10}, asks={}}", orderBook.getBookAsString());

    orderBook.ask(432, 2);
    orderBook.ask(433, 25);
    orderBook.ask(432.5, 8);
    orderBook.ask(433, 3);

    System.out.println("-->{bids={430.1=10, 430.0=10}, asks={432.0=2, 432.5=8, 433.0=28}}");
    Assert.assertEquals("{bids={430.1=10, 430.0=10}, asks={432.0=2, 432.5=8, 433.0=28}}",
        orderBook.getBookAsString());

    orderBook.ask(420, 3);
    System.out.println("-->{bids={430.1=7, 430.0=10}, asks={432.0=2, 432.5=8, 433.0=28}}");

    Assert.assertEquals("{bids={430.1=7, 430.0=10}, asks={432.0=2, 432.5=8, 433.0=28}}",
        orderBook.getBookAsString());

    orderBook.bid(500, 100);
    System.out.println("-->{bids={500.0=62, 430.1=7, 430.0=10}, asks={}}");

    Assert.assertEquals("{bids={500.0=62, 430.1=7, 430.0=10}, asks={}}", orderBook.getBookAsString());
  }

}
