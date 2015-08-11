package edu.pdx.cs410J.jonerik.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.Map;

/**
 * A GWT remote service that returns a dummy Phone Bill
 */
@RemoteServiceRelativePath("ping")
public interface PingService extends RemoteService {

  public Map<String, PhoneBill> getBills();

  /**
   * Returns the a dummy Phone Bill
   */
  void ping(String customer, PhoneCall call);




}
