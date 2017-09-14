package com.baliset.feed.service;

import com.baliset.feed.conf.*;
import com.baliset.feed.model.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;

import java.util.stream.*;

@Service
public class GhostOrderService
{
  private final OrderService orderService;
  private final GhostConfig  ghostConfig;

  private final RangeGenerator sizeGenerator;
  private final PriceGenerator priceGenerator;
  private final Fluctuator     fluctuator;

  private final String party = "Ghost";

  @Autowired public GhostOrderService(OrderService orderService, GhostConfig ghostConfig)
  {
    this.orderService = orderService;
    this.ghostConfig  = ghostConfig;
    priceGenerator    = new PriceGenerator(1, 500);
    sizeGenerator     = new RangeGenerator(1,100);
    fluctuator        = new Fluctuator(0.01, 0.15);

    ghostConfig.getSymbols().forEach(orderService::createBook);   // create some books as specified in ghost config
    ghostConfig.getSymbols().forEach(this::seedOrders);
  }


  // create some initial orders for the a given symbol, the random price chosen will be the basis
  // of future fluctuations
  private void seedOrders(String symbol)
  {
    double bid = priceGenerator.generate();
    double ask = fluctuator.fluctuate(bid);

    if(ask < bid)
    {
      double t = ask; ask  = bid; bid = t;
    }

    orderService.bid(symbol,party,sizeGenerator.generate(),bid);
    orderService.ask(symbol,party,sizeGenerator.generate(),ask);

    IntStream.range(0, 6).forEach($ -> arbitraryOrder(symbol));

  }

  private void arbitraryOrder(String symbol)
  {
    Quote quote = orderService.top(symbol);

    double bid = quote.getBid();
    double ask = quote.getAsk();

    double nbid = fluctuator.fluctuate(bid);

    double delta = nbid - bid;
    double nask = (delta > 0)? ask + delta: ask - delta;

    orderService.bid(symbol,party,sizeGenerator.generate(),nbid);
    orderService.ask(symbol,party,sizeGenerator.generate(),nask);
  }

  @Scheduled(cron = "${ghost.cron}")
  private void ghostOrders()
  {
    ghostConfig.getSymbols().forEach(this::arbitraryOrder);
  }

}
