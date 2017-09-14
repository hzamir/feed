package com.baliset.feed.model;

public class OrderRequest
{
   public String symbol;
   public String party;
   public double price;
   public int quantity;

   public OrderRequest(String s, String p, int q, double pr)
   {
      symbol=s;party=p;quantity=q;price=pr;
   }
}
