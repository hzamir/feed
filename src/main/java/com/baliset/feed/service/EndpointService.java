package com.baliset.feed.service;

import com.baliset.feed.model.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.*;
import org.springframework.web.servlet.mvc.method.*;
import org.springframework.web.servlet.mvc.method.annotation.*;

import java.util.*;

@Service
public class EndpointService
{
  private final RequestMappingHandlerMapping requestMappingHandlerMapping;

  @Autowired
  public EndpointService(RequestMappingHandlerMapping requestMappingHandlerMapping) {this.requestMappingHandlerMapping = requestMappingHandlerMapping;}

  public List<Endpoint> list()
  {
    List<Endpoint> endpoints = new ArrayList<>();

    Map<RequestMappingInfo, HandlerMethod> mappings = requestMappingHandlerMapping.getHandlerMethods();

    try {
      for (RequestMappingInfo i : mappings.keySet()) {

        HandlerMethod hm = mappings.get(i);
        String beanclass = hm.getBean().toString();


        Set<RequestMethod> methods              = i.getMethodsCondition().getMethods();
        Set<MediaType>     producibleMediaTypes = i.getProducesCondition().getProducibleMediaTypes();
        MediaType          mediatype            = producibleMediaTypes.iterator().hasNext()? producibleMediaTypes.iterator().next(): null;
        String             mt                   = mediatype != null? mediatype.toString(): null;

        String method = methods.iterator().hasNext()? methods.iterator().next().name(): null;


        for (String urlPattern : i.getPatternsCondition().getPatterns()) {
          Endpoint ep = new Endpoint();
          ep.controller = beanclass;
          ep.url = urlPattern;
          ep.method = method;
          ep.produces = mt; // this only returns one of the media types
          endpoints.add(ep);
        }

      }
    } catch(Throwable t) {
      System.out.println("whoa");
    }

    return endpoints;

  }
}
