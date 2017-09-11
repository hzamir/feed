package com.baliset.feed.conf;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.*;

import javax.validation.constraints.*;


@Configuration
@ConfigurationProperties(prefix = "feed")
@Validated
public class FeedConfig implements InitializingBean
{

  @Min(1) @Max(86400) public int    unitSeconds;     // allows specifying units in readable form 1=secs, 60=mins, 3600=hrs, 86,400=days
  @Min(1) @Max(86400) public int    frequency;       // how often the updater task run per unitSeconds
  @Min(1) @Max(1000)  public int    itemsPerUpdate;  // how many items should be updated
  @Pattern(regexp = Patterns.cronExpression)
                      public String cron;            // forget the above for now, use a cron for @scheduled annotation

  private int periodMillis;                       // computed value, how often to schedule

  public int  getPeriodMillis()        { return periodMillis;}
  public void setUnitSeconds(int v)    { unitSeconds = v;    }
  public void setFrequency(int v)      { frequency = v;      }
  public void setItemsPerUpdate(int v) { itemsPerUpdate = v; }
  public void setCron(String v)        { cron = v;           }

  @Override
  public void afterPropertiesSet() throws Exception
  {
    periodMillis = (unitSeconds * 1000) / frequency;
  }

  @Override public String toString()
  {
    return String.format("{unitSeconds:%d, frequency:%d, itemsPerUpdate:%d, cron:'%s', periodMillis:%d}",
        unitSeconds,frequency,itemsPerUpdate,cron,periodMillis
    );
  }
}
