package com.baliset.feed.controller;

import com.baliset.feed.model.*;
import com.baliset.feed.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.util.*;

@RestController
public class QuoteController
{
  private final OrderService orderService;

  @Autowired
  public QuoteController(OrderService orderService)
  {
    this.orderService = orderService;
  }


  @RequestMapping(value="/quote/list", method = RequestMethod.GET, produces={"application/json"})
  public ResponseEntity<List<Quote>> instrumentList() throws Exception
  {
    List<Quote> list       = orderService.top();
    HttpStatus  httpStatus =  HttpStatus.OK;
    return new ResponseEntity<>(list,httpStatus);
  }

}
