package edu.pdx.cs410J.jonerik.client;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonerik13 on 7/6/15.
 * PhoneBill Class extends AbstractPhoneBill
 * and adds all functionality for the interface.
 */
public class PhoneBill extends AbstractPhoneBill {


  /**
   * A phone bill contains a customer name and
   * a list of phone calls made.
   */
  private String customer;
  private List<AbstractPhoneCall> calls = new ArrayList<>();

  public PhoneBill() { }

  /**
   * constructor for a PhoneBil;
   */
  public PhoneBill(String customer) {
    this.customer   = customer;
  }

  public String getCustomer() {
    return customer;
  }

  /**
   * Add a phone call to the customers bill
   */
  public void addPhoneCall(AbstractPhoneCall var1) {
    calls.add(var1);
  }

  public List getPhoneCalls(){
    return calls;
  }

  public String toString() {
    return this.getCustomer() + "\'s phone bill with " + this.getPhoneCalls().size() + " phone calls";
  }


}
