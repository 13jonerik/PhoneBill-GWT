package edu.pdx.cs410J.jonerik.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.jonerik.client.PhoneBill;
import edu.pdx.cs410J.jonerik.client.PhoneCall;
import edu.pdx.cs410J.jonerik.client.PingService;

import java.util.HashMap;
import java.util.Map;

/**
 * The server-side implementation of the Phone Bill service
 */
public class PingServiceImpl extends RemoteServiceServlet implements PingService
{


  private Map<String, PhoneBill> bills = new HashMap<>();

  public Map<String, PhoneBill> getBills() {
    return bills;
  }

  public void ping(String customer, PhoneCall call) {
    if (bills.containsKey(customer)){
      bills.get(customer).addPhoneCall(call);
    } else {
      PhoneBill phonebill = new PhoneBill();
      phonebill.addPhoneCall(call);
      bills.put(customer, phonebill);
    }

  }

  /**
   * Log unhandled exceptions to standard error
   *
   * @param unhandled
   *        The exception that wasn't handled
   */
  @Override
  protected void doUnexpectedFailure(Throwable unhandled) {
    unhandled.printStackTrace(System.err);
    super.doUnexpectedFailure(unhandled);
  }
}
