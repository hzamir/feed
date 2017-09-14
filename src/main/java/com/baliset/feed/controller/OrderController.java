package com.baliset.feed.controller;

import com.baliset.feed.model.*;
import com.baliset.feed.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

public class OrderController
{

  private final OrderService orderService;
  @Autowired public OrderController(OrderService orderService) {this.orderService = orderService;}


  @RequestMapping(value="/order/bid", method = RequestMethod.POST, produces={"application/json"})
  public ResponseEntity<Quote>
  bid(@RequestBody OrderRequest req) throws Exception
  {
    orderService.bid(req.symbol, req.party, req.quantity, req.price);

    HttpStatus httpStatus =  HttpStatus.OK;
    return new ResponseEntity<>(orderService.top(req.symbol),httpStatus);
  }

  @RequestMapping(value="/order/ask", method = RequestMethod.POST, produces={"application/json"})
  public ResponseEntity<Quote>
  ask(@RequestBody OrderRequest req) throws Exception
  {
    orderService.ask(req.symbol, req.party, req.quantity, req.price);

    HttpStatus httpStatus =  HttpStatus.OK;
    return new ResponseEntity<>(orderService.top(req.symbol),httpStatus);
  }

}
