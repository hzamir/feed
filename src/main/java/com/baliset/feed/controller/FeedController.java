package com.baliset.feed.controller;

import com.baliset.feed.model.*;
import com.baliset.feed.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.util.*;

@RestController
public class FeedController
{
  private final String hello="hello";
  private final InstrumentService updateService;
  private final OrderService      orderService;

  @Autowired
  public FeedController(InstrumentService updateService, OrderService orderService)
  {
    this.updateService = updateService;
    this.orderService = orderService;
  }

  @RequestMapping(value={"/", "/healthping", "/version"}, method = RequestMethod.GET, produces={"text/plain"})
  public String hello()
  {
    return hello;
  }


  @RequestMapping(value="/instrument/list", method = RequestMethod.GET, produces={"application/json"})
  public ResponseEntity<List<Quote>>
  instrumentList(HttpServletRequest req) throws Exception
  {
    List<Quote> list       = updateService.list();
    HttpStatus  httpStatus =  HttpStatus.OK;
    return new ResponseEntity<>(list,httpStatus);
  }

  @RequestMapping(value="/order/list", method = RequestMethod.GET, produces={"application/json"})
  public ResponseEntity<List<Quote>>
  orderList(HttpServletRequest req) throws Exception
  {
    List<Quote> list       = orderService.top();
    HttpStatus  httpStatus =  HttpStatus.OK;
    return new ResponseEntity<>(list,httpStatus);
  }


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
