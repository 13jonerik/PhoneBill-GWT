package edu.pdx.cs410J.jonerik.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.Map;

/**
 * The client-side interface to the ping service
 */
public interface PingServiceAsync {

  /**
   * Return the current date/time on the server
   */
  void ping(String customer, PhoneCall call, AsyncCallback<Void> async);
  void getBills(AsyncCallback<Map<String, PhoneBill>> async);
}
