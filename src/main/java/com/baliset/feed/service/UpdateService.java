package com.baliset.feed.service;

import com.baliset.feed.conf.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;

@Service
public class UpdateService
{
  private static Logger logger = LoggerFactory.getLogger(UpdateService.class);
  private FeedConfig config;

  public UpdateService(@Autowired FeedConfig config)
  {
    this.config = config;
    logger.info("config: " + config.toString());
  }

  @Scheduled(cron = "${feed.defaults.cron}")
  private void udpater()
  {
    logger.info("+++update");
  }
}
