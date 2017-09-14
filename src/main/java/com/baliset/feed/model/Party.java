package com.baliset.feed.model;

import java.util.*;

// who can be a party to a order/trade. All orders must be connected to a recognized party
public class Party
{
            public Party(String name)       { this.name = name;          }
            public String getName()         { return name;               }

  @Override public String toString()        { return name;               }
  @Override public int hashCode()           { return Objects.hash(name); }
  @Override public boolean equals(Object o) { return o == this || o instanceof Party && ((Party) o).name.equals(name); }

  // instance variables
            private String name;
}
