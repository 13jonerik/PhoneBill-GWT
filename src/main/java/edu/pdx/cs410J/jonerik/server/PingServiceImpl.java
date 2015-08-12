package edu.pdx.cs410J.jonerik.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.jonerik.client.PhoneBill;
import edu.pdx.cs410J.jonerik.client.PhoneCall;
import edu.pdx.cs410J.jonerik.client.PingService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

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
      PhoneBill thisBill = bills.get(customer);

      List<PhoneCall> remove = new ArrayList<>();
      for (PhoneCall check : remove) {
        remove.add(check);
      }

      int length = remove.size();
      remove = removeDuplicates(remove, length);

      for (PhoneCall add : remove) {
        thisBill.addPhoneCall(add);
      }

    } else {
      PhoneBill phonebill = new PhoneBill();
      phonebill.addPhoneCall(call);
      bills.put(customer, phonebill);
    }

  }


  /**
   * This helper function takes in the list of phone calls and returns
   * a new list, but without duplicates. A phone call is considered a
   * duplicate if it has the same start time and the same caller
   * number.
   */
  public List<PhoneCall> removeDuplicates(List<PhoneCall> temp, int length) {
    if (length > 0) {
      for (int i = 0; i < length; i++) {
        for (int j = i + 1; j < length; j++) {
          if (temp.get(i).compareTo(temp.get(j)) == 0) {
            temp.remove(temp.get(i));
            --length;
            removeDuplicates(temp, length);
            return temp;
          }
        }
      }
      return temp;

    } else {
      return temp;
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
