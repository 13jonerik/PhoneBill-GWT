package edu.pdx.cs410J.jonerik.client;
import edu.pdx.cs410J.AbstractPhoneCall;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jonerik13 on 7/6/15.
 * The PhoneCall class extends the AbstractPhoneCall
 * interface and adds all functionality. A PhoneCall
 * has a to and from number, and a
 * start and end time.
 */
public class PhoneCall extends AbstractPhoneCall implements Comparable<PhoneCall> {

  private String callerNumber;
  private String calleeNumber;

  private Date startTime;
  private Date endTime;

  public PhoneCall() {}

  /**
   * Constructor for the PhoneCall
   */
  public PhoneCall(String callerNumber, String calleeNumber,
                   String callTime, String endTime) {

    this.callerNumber = callerNumber;
    this.calleeNumber = calleeNumber;

    this.startTime = formatDate(callTime);
    this.endTime = formatDate(endTime);

  }

  public String getCaller() {
    return callerNumber;
  }

  public String getCallee() {
    return calleeNumber;
  }

  public Date getStartTime() {
    return startTime;
  }

  public String getStartTimeString() {
    DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    return formatter.format(startTime);
  }

  public Date getEndTime() {
    return endTime;
  }

  public String getEndTimeString() {
    DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    return formatter.format(endTime);
  }


  /**
   * The toString method is used in the -print argument for Project3
   * file to print the phone call information to the console.
   */
  public String toString() {
    return "Phone call from " + this.getCaller() + " to " + this.getCallee() + " beginning on " + this.getStartTimeString() + " and ending " + this.getEndTimeString();
  }

  /**
   * Helper function used to save the startTime and
   * endTime as dates, without changing how the dates
   * are read in from the command line. The constructor
   * takes in strings as arguments and this function
   * will format into a date object in the constructor
   */
  public static Date formatDate(String d) {

    try {
      SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
      Date date = formatter.parse(d);
      return date;

    } catch (java.text.ParseException e) {
      System.err.println("Date Malformatted. Check file and arguments and try again.");
      System.exit(1);
    }
    return null;
  }


  /**
   * Uses comparable implemented by the PhoneCall class.
   * Compares PhoneCall first by startTime of the call
   * and sorts chronologically, and then by callerNumber,
   * from least to greatest.
   *
   * @param other a PhoneBill to compare the current bill
   * @return return 0 if the PhoneCall is considered equal.
   */
  public int compareTo(PhoneCall other) {

    int firstCompare = startTime.compareTo(other.getStartTime());
    if (firstCompare != 0) {
      return firstCompare;
    }

    int secondCompare = callerNumber.compareTo(other.getCaller());
    if (secondCompare != 0) {
      return secondCompare;
    }

    return 0;

  }

}