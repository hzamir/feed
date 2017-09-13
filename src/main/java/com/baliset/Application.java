package com.baliset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
public class Application
{
  private static Logger logger = LoggerFactory.getLogger(Application.class);

  ApplicationContext applicationContext;

  public static void main(String[] args)
  {
    ApplicationContext ctx = SpringApplication.run(Application.class, args);

    Application application = ctx.getBean(Application.class);  // the application might as well be a singleton too, no?
    application.applicationContext = ctx;  // todo: can this be injected straight into Application instance

    application.stuff();                   // todo: maybe just use initializationaware lifecycle routine for this?

  }
  //do as much as possible inside an instance of Application rather than in a static method
  private void stuff()
  {
    logger.info("Kudos, the server is ready"); // todo: replace with pattern that checks all parts are ready

  }
}
