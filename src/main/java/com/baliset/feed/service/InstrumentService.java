package com.baliset.feed.service;

import com.baliset.feed.conf.*;
import com.baliset.feed.model.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class InstrumentService
{
  private static Logger logger = LoggerFactory.getLogger(InstrumentService.class);
  private FeedConfig       config;
  private PriceGenerator   generator;
  private Fluctuator       fluctuator;
  private List<Instrument> instruments;

  public InstrumentService(@Autowired FeedConfig config)
  {
    this.config = config;
    this.instruments = new ArrayList<>();

    this.generator = new PriceGenerator(1, 500);
    this.fluctuator = new Fluctuator(0.01, 0.15);

    logger.info("config: " + config.toString());
    createInstruments();
  }

  private void alterPrices(Instrument instrument)
  {
    double bid = instrument.getBid();
    double ask = instrument.getAsk();

    double nbid = fluctuator.fluctuate(bid);

    double delta = nbid - bid;
    double nask = (delta > 0)? ask + delta: ask - delta;

    instrument.setBidAsk(nbid,nask);
  }

  private void createInstruments()
  {
     String[] names = {"ABCX", "BKRR", "CRUD", "DDXO"};

     for(String s:names)
     {
       double price = generator.generate();
       double price2 = fluctuator.fluctuate(price);

       Instrument instrument = price < price2? new Instrument(s, price, price2): new Instrument(s, price2, price);
       instruments.add(instrument);
     }

  }

  public List<Instrument> list()
  {
    List<Instrument> list = new ArrayList<>(instruments.size());
    list.addAll(instruments);
    return list;
  }

  @Scheduled(cron = "${feed.cron}")
  private void udpater()
  {
    instruments.forEach(this::alterPrices);
  }
}