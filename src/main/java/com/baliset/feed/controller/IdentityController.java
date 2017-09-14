package com.baliset.feed.controller;

import com.baliset.feed.model.*;
import com.baliset.feed.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.*;

import java.util.*;

@RestController
public class IdentityController
{
  private final String hello="hello";
  private final RequestMappingHandlerMapping requestMappingHandlerMapping;
  private final EndpointService endpointService;

  @Autowired
  public IdentityController(RequestMappingHandlerMapping requestMappingHandlerMapping, EndpointService endpointService) {this.requestMappingHandlerMapping = requestMappingHandlerMapping;
    this.endpointService = endpointService;
  }

  @RequestMapping(value={"/", "/healthping", "/version"}, method = RequestMethod.GET, produces={"text/plain"})
  public String hello()
  {
    return hello;
  }


  @RequestMapping( value = "/endpoints", method = RequestMethod.GET, produces={"application/json"} )
  public ResponseEntity<List<Endpoint>> endpoints()
  {
    List<Endpoint> endpoints = endpointService.list();
    return  new ResponseEntity<>(endpoints,HttpStatus.OK);
  }

  //todo: add a true version call which will require some assets created as part of the build to identify the version info
}
