package edu.pdx.cs410J.jonerik.client;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.Collections;
import java.util.List;


/**
 * The Pretty Printer is used to format a phone bill
 * too look like a bill you would see in real life,
 * thus making the phone bill information look nice.
 */
public class Prettify {


    private StringBuilder call = new StringBuilder();

    /**
     * Constructor for the PrettyPrinter
     * @param call takes in a stream from
     * Project3 and uses the stream in dump
     */
    public Prettify() {}


    /**
     * This dump function takes in a phone bill and dumps it to a file
     * much like the text dumper, but this time in a pretty format like
     * one you would see in the real world. Dumps to a file that is
     * passed in with the constructor of PrettyPrinter
     */
    public String dumpToWindow(AbstractPhoneBill bill)  {

        call.append("*** " + bill.getCustomer() + "'s Phone Bill ***");
        call.append("\n\n");

        List<PhoneCall> temp = (List<PhoneCall>) bill.getPhoneCalls();

        Collections.sort(temp);

        int length = temp.size();
        temp = removeDuplicates(temp, length);



        call.append("    Caller" +  "|" + "Callee" +  "|" +  "Start Time" +  "|" +  "End Time" +  "|" +  "Duration");
        call.append("-------------------------------------------------------"
                + "------------------------------------------------------------------\n");


        for (AbstractPhoneCall each : temp) {

            Long duration = Math.abs(each.getStartTime().getTime() - each.getEndTime().getTime());
            Long durationHours = duration; //TimeUnit.MILLISECONDS.toHours(duration);
            Long durationMins =  duration; //TimeUnit.MILLISECONDS.toMinutes(duration);
            durationMins = durationMins % 60;


            call.append("  ").append(each.getCaller()).append("|").append(each.getCallee()).append("|")
                    .append(each.getStartTimeString()).append("|").append(each.getEndTimeString())
                    .append("|").append(" -> Duration: ").append(durationHours).append(" Hours and ")
                    .append(durationMins).append(" minutes!");
        }


        call.append("---------------------------------------------------------------------------" +
                "----------------------------------------------\n");

        return call.toString();
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

}
