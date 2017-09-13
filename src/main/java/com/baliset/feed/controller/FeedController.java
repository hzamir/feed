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
  public ResponseEntity<List<Instrument>>
  instrumentList(HttpServletRequest req) throws Exception
  {
    List<Instrument> list = updateService.list();
    HttpStatus httpStatus =  HttpStatus.OK;
    return new ResponseEntity<>(list,httpStatus);
  }

  @RequestMapping(value="/order/list", method = RequestMethod.GET, produces={"application/json"})
  public ResponseEntity<List<Instrument>>
  orderList(HttpServletRequest req) throws Exception
  {
    List<Instrument> list = orderService.top();
    HttpStatus httpStatus =  HttpStatus.OK;
    return new ResponseEntity<>(list,httpStatus);
  }


}
