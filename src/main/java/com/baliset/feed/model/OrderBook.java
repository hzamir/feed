package com.baliset.feed.model;

import com.baliset.feed.service.*;
import org.slf4j.*;

import java.util.*;

public class OrderBook
{
  private static Logger logger = LoggerFactory.getLogger(OrderBook.class);


  private Comparator<Integer> ascendingOrder  = (o1, o2) -> o1 - o2;
  private Comparator<Integer> descendingOrder = (o1, o2) -> o2 - o1;

  // Use Integer keys, because Double keys just don't work well for this
  private final SortedMap<Integer, Order> bids = new TreeMap<>(descendingOrder);
  private final SortedMap<Integer, Order> asks = new TreeMap<>(ascendingOrder);

  private final String symbol;

  public OrderBook(String symbol)
  {
    this.symbol = symbol;
  }

  private void bidOrAsk(SortedMap<Integer, Order> sameSide, SortedMap<Integer, Order> otherSide, double limitPrice, int quantity)
  {

    if (quantity <= 0) // no guidance on handling negative quantities
      return;

    int     unmatchedQuantity = quantity;
    boolean moreMatching;

    // match bids or asks against best prices on opposing side
    do {
      moreMatching = false;
      if (!otherSide.isEmpty()) {
        Integer counterpartKey = otherSide.firstKey();
        Order   counterpart    = otherSide.get(counterpartKey);


        if (sameSide == bids ? counterpart.limitPrice <= limitPrice : counterpart.limitPrice >= limitPrice) {
          if (counterpart.quantity > unmatchedQuantity)  // put unmatched balance back
          {
            otherSide.put(counterpartKey, new Order(counterpart.limitPrice,
                counterpart.quantity - unmatchedQuantity));
            unmatchedQuantity = 0;
          } else {
            otherSide.remove(counterpartKey);
            unmatchedQuantity -= counterpart.quantity;
          }
          if (unmatchedQuantity > 0)
            moreMatching = true;
        }
      }
    } while (moreMatching);

    // any unmatched quantities need to be added to orders
    // in the real world, we might prefer that they exist even briefly as orders prior to matching
    // or otherwise be recorded as a match
    // but for the purposes of this test, only remaining unmatched orders are needed per spec
    if (unmatchedQuantity > 0) {
      Integer limitKey   = keyFor(limitPrice);
      Order   toCoalesce = sameSide.get(limitKey);

      if (toCoalesce != null) {
        unmatchedQuantity += toCoalesce.quantity;
      }
      // add or overwrite the order for the same price with correct quantity
      sameSide.put(limitKey, new Order(limitPrice, unmatchedQuantity));
    }

  }

  public void bid(double limitPrice, int quantity)
  {
    // todo because of where I am plugging in this log statement, am creating an order for no other reason than to print
    Order antiPatternOrderCreatedHereFixThis = new Order(limitPrice, quantity);
    
    logger.info(String.format("%s bidding %s", symbol,quantity, antiPatternOrderCreatedHereFixThis.priceAndQuantity()));

    bidOrAsk(bids, asks, limitPrice, quantity);
  }

  public void ask(double limitPrice, int quantity)
  {
    Order antiPatternOrderCreatedHereFixThis = new Order(limitPrice, quantity);

    logger.info(String.format("%s asking %s", symbol,quantity, antiPatternOrderCreatedHereFixThis.priceAndQuantity()));

    bidOrAsk(asks, bids, limitPrice, quantity);
  }


  static private int     dtoi(double v)   { return (int)(v * 100.0); }
  static private double  itod(int i)      { return (double)i * 0.01; }
  static private Integer keyFor(double v) { return dtoi(v);          }

  public Instrument topOfBook()
  {
    Integer bestBid = bids.firstKey();
    Integer bestAsk = asks.firstKey();

    // todo: yes, but how many at that price? And instrument is the wrong term is should be BidAsk or something.
    return new Instrument(symbol, itod(bestBid != null? bestBid: 0), itod(bestAsk != null? bestAsk: Integer.MAX_VALUE));
  }

  private void appendOrders(StringBuilder sb, SortedMap<Integer, Order> orders)
  {
    boolean prefixCommas = false;
    sb.append("{");
    for (Order o : orders.values()) {
      if (prefixCommas)
        sb.append(", ");
      else
        prefixCommas = true;
      sb.append(o.priceAndQuantity());
    }
    sb.append("}");
  }

  //"{bids={500.0=62, 430.1=7, 430.0=10}, asks={}}"
  public String getBookAsString()
  {
    StringBuilder sb = new StringBuilder(10 * (asks.size() + bids.size()));
    sb.append("{bids=");
    appendOrders(sb, bids);
    sb.append(", asks=");
    appendOrders(sb, asks);
    sb.append("}");
    return sb.toString();
  }
}
