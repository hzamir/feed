package com.baliset.feed.service;

import com.baliset.feed.model.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class PartyService
{
  public Party create(String name)
  {
    if(parties.containsKey(name))
      throw new ExUniqueParty(name);
    Party p = new Party(name);
    parties.put(name, p);
    return p;
  }
  public Party lookup(String name)
  {
    return parties.get(name);
  }

  public List<Party> list()
  {
    return new ArrayList<>(parties.values());
  }

  class ExUniqueParty extends RuntimeException { ExUniqueParty(String s) {super(s); }}

  private Map<String, Party> parties = new HashMap<>();

}
