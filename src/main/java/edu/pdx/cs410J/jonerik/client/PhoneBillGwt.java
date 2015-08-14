package edu.pdx.cs410J.jonerik.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * A basic GWT class that makes sure that we can send an Phone Bill back from the server
 */
public class PhoneBillGwt implements EntryPoint {

    private static TextBox customerName     = new TextBox();
    private static TextBox caller           = new TextBox();
    private static TextBox callee           = new TextBox();
    private static TextBox startDate        = new TextBox();
    private static TextBox startTime        = new TextBox();
    private static TextBox endDate          = new TextBox();
    private static TextBox endTime          = new TextBox();

    private static TextBox searchCustomer   = new TextBox();
    private static TextBox searchStartDate  = new TextBox();
    private static TextBox searchStartTime  = new TextBox();
    private static TextBox searchEndDate    = new TextBox();
    private static TextBox searchEndTime    = new TextBox();

    private static ToggleButton startAM     = new ToggleButton("AM", "PM");
    private static ToggleButton endAM       = new ToggleButton("AM", "PM");

    private static ToggleButton searchStartAM = new ToggleButton("AM", "PM");
    private static ToggleButton searchEndAM   = new ToggleButton("AM", "PM");

    private static TextBox prettyCustomer   = new TextBox();
    static PingServiceAsync pinger;

  public void onModuleLoad() {

      addTextBox(customerName, "Name on Phone Bill");
      addTextBox(caller, "Enter Caller Number");
      addTextBox(callee, "Enter Callee Number");
      addTextBox(startDate, "MM/DD/YYYY");
      addTextBox(endDate, "MM/DD/YYYY");
      addTextBox(startTime, "HH:MM");
      addTextBox(endTime, "HH:MM");

      addTextBox(searchCustomer, "Name on Phone Bill");
      addTextBox(searchStartDate, "MM/DD/YYYY");
      addTextBox(searchEndDate, "MM/DD/YYYY");
      addTextBox(searchStartTime, "HH:MM");
      addTextBox(searchEndTime, "HH:MM");

      addTextBox(prettyCustomer, "Name of Customer");

      pinger = GWT.create(PingService.class);

      VerticalPanel panel2      = new VerticalPanel();
      VerticalPanel panel3      = new VerticalPanel();
      VerticalPanel panel4      = new VerticalPanel();

      panel2.getElement().setAttribute("align", "center");
      panel3.getElement().setAttribute("align", "center");
      panel4.getElement().setAttribute("align", "center");

      panel2.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
      panel3.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
      panel4.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

      RootPanel rootPanel   = RootPanel.get("gwtcontainer");
      RootPanel rootPanel2  = RootPanel.get("search");
      RootPanel rootPanel3  = RootPanel.get("pretty");

      rootPanel.add(setVerticalPanel(panel2));
      rootPanel2.add(setSearchPanel(panel3));
      rootPanel3.add(setPrettyPanel(panel4));

  }


    public static TextBox addTextBox(final TextBox textbox,  String text){
        textbox.getElement().setAttribute("class", "form-control");
        textbox.getElement().setAttribute("placeholder", text);
        //textbox.setText(text);
        textbox.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                textbox.setText("");
            }
        });

        return textbox;
    }

    public static Label addLabel(String text) {
        Label label =  new Label(text);
        label.getElement().getStyle().setPadding(10, Unit.PX);
        return label;
    }

    public static HorizontalPanel addHorizontalTextBox(TextBox box, String labelText) {
        HorizontalPanel panel = new HorizontalPanel();
        panel.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
        //panel.add(addLabel(labelText));
        panel.getElement().getStyle().setPadding(10, Unit.PX);
        panel.add(box);
        return panel;

    }

    public static HorizontalPanel addHorizontalTimeTextBox(TextBox box, String labelText, ToggleButton ampm) {
        HorizontalPanel panel = new HorizontalPanel();
        panel.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
        //panel.add(addLabel(labelText));
        panel.getElement().getStyle().setPadding(10, Unit.PX);
        panel.add(box);
        ampm.getElement().setAttribute("class", "btn");
        panel.add(ampm);
        return panel;

    }

    public static Button addCallButton(String buttonName) {
        Button addCall = new Button(buttonName);
        addCall.getElement().setAttribute("class", "btn btn-warning");
        //validate the arguments, throw errors if not valid

        addCall.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {

                final String customerNameInput = customerName.getText();
                String callerInput = caller.getText();
                String calleeInput = callee.getText();
                String startDateInput = startDate.getText();
                String endDateInput = endDate.getText();
                String startTimeInput = startTime.getText();
                String endTimeInput = endTime.getText();
                String startAmPm;
                String endAmPm;

                if (startAM.isDown()) {
                    startAmPm = "PM";
                } else {
                    startAmPm = "AM";
                }
                if (endAM.isDown()) {
                    endAmPm = "PM";
                } else {
                    endAmPm = "AM";
                }

                final String start = startAmPm;
                final String end = endAmPm;

                String startTimeString = startDateInput + " " + startTimeInput + " " + startAmPm;
                String endTimeString = endDateInput + " " + endTimeInput + " " + endAmPm;

                boolean checkArgs = validateCall(customerNameInput, callerInput, calleeInput,
                        startDateInput, startTimeInput, endDateInput, endTimeInput);

                if (checkArgs) {
                    PhoneCall addCall = new PhoneCall(callerInput, calleeInput, startTimeString, endTimeString);
                    pinger.ping(customerNameInput, addCall, new AsyncCallback<Void>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            Window.alert("Error is saving flight! Try again: " + throwable.getMessage());
                        }

                        @Override
                        public void onSuccess(Void aVoid) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Call Info: " + "\n\n");
                            sb.append(customerName.getText() + "\n");
                            sb.append(caller.getText() + "\n");
                            sb.append(callee.getText() + "\n");
                            sb.append(startDate.getText() + " " + startTime.getText() + " " + start + "\n");
                            sb.append(endDate.getText() + " " + endTime.getText() + " " + end);
                            sb.append("\n\nCall Added!");

                            Window.alert(sb.toString());

                        }
                    });
                }
            }
        });

        return addCall;
    }


    public static Button addSearchButton(String buttonName) {
        Button searchCall = new Button(buttonName);
        searchCall.getElement().setAttribute("class", "btn btn-warning");

        searchCall.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                final String customerNameInput = searchCustomer.getText();
                String startDateInput = searchStartDate.getText();
                String endDateInput = searchEndDate.getText();
                String startTimeInput = searchStartTime.getText();
                String endTimeInput = searchEndTime.getText();
                String startAmPm;
                String endAmPm;

                if (startAM.isDown()) {
                    startAmPm = "PM";
                } else {
                    startAmPm = "AM";
                }
                if (endAM.isDown()) {
                    endAmPm = "PM";
                } else {
                    endAmPm = "AM";
                }

                final String start = startAmPm;
                final String end = endAmPm;

                String startTimeString = startDateInput + " " + startTimeInput + " " + startAmPm;
                String endTimeString = endDateInput + " " + endTimeInput + " " + endAmPm;

                boolean checkArgs = validateSearch(customerNameInput, startDateInput, startTimeInput, endDateInput, endTimeInput);
                if (checkArgs) {

                    final PhoneCall searchCall = new PhoneCall(startTimeString, endTimeString);
                    pinger.getBills(new AsyncCallback<Map<String, PhoneBill>>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            Window.alert("Error in loading phone bills. Reload page and try again.");
                        }

                        @Override
                        public void onSuccess(Map<String, PhoneBill> stringPhoneBillMap) {
                            PhoneBill bill = stringPhoneBillMap.get(customerNameInput);

                            if (bill == null) {
                                Window.alert("No calls found for that customer name. Try adding a call!");
                                return;
                            }

                            List<AbstractPhoneCall> calls;
                            calls = bill.getPhoneCalls();
                            StringBuilder sb = new StringBuilder();
                            sb.append("Here are the calls that fall within your search criteria:\n\n");
                            boolean valid;
                            boolean any = false;

                            for (AbstractPhoneCall each : calls) {
                                Date start = searchCall.getStartTime();
                                Date end = searchCall.getEndTime();
                                valid = searcher(each, start, end);
                                if (valid) {
                                    any = true;
                                    sb.append(each.toString() + "\n\n");
                                }
                            }
                            if (!any) { Window.alert("No calls found in your search criteria!"); }
                            else { Window.alert(sb.toString()); }
                        }
                    });

                }
            }
        });

        return searchCall;
    }

    public static Button addPrettyButton(String buttonName){
        Button prettyButton = new Button(buttonName);
        prettyButton.getElement().setAttribute("class", "btn btn-warning");

        prettyButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                final String customerNameInput = prettyCustomer.getText();
                pinger.getBills(new AsyncCallback<Map<String, PhoneBill>>(){

                    @Override
                    public void onFailure(Throwable throwable) {
                        Window.alert("Error in loading phone bills. Reload page and try again.");
                    }

                    @Override
                    public void onSuccess(Map<String, PhoneBill> stringPhoneBillMap) {
                        if (customerNameInput.equals("")) {
                            Window.alert("Enter a customer name!");
                            return;
                        }

                        PhoneBill bill = stringPhoneBillMap.get(customerNameInput);

                        if(bill == null) {
                            Window.alert("Phone bill for this customer not found! Try adding a new call.");
                            return;
                        }

                        List<AbstractPhoneCall> calls;
                        calls = bill.getPhoneCalls();
                        StringBuilder sb = new StringBuilder();
                        sb.append("Here are all of the calls on this customer's bill: \n\n");

                        for (AbstractPhoneCall each : calls) {
                                sb.append(each.toString() + "\n\n");

                        }
                        Window.alert(sb.toString());
                    }
                });

            }
        });

        return prettyButton;
    }

    public static VerticalPanel setVerticalPanel(VerticalPanel panel2){
        panel2.setSpacing(10);
        panel2.add(addHorizontalTextBox(customerName, "Customer Name"));
        panel2.add(addHorizontalTextBox(caller, "Caller"));
        panel2.add(addHorizontalTextBox(callee, "Callee"));
        panel2.add(addHorizontalTextBox(startDate, "Start Date"));
        panel2.add(addHorizontalTimeTextBox(startTime, "Start Time", startAM));
        panel2.add(addHorizontalTextBox(endDate, "End Date"));
        panel2.add(addHorizontalTimeTextBox(endTime, "End Time", endAM));
        panel2.add(addCallButton("Add Call"));
        return panel2;
    }

    public static VerticalPanel setSearchPanel(VerticalPanel panel2){
        panel2.setSpacing(10);
        panel2.add(addHorizontalTextBox(searchCustomer, "Customer Name"));
        panel2.add(addHorizontalTextBox(searchStartDate, "Start Date"));
        panel2.add(addHorizontalTimeTextBox(searchStartTime, "Start Time", searchStartAM));
        panel2.add(addHorizontalTextBox(searchEndDate, "End Date"));
        panel2.add(addHorizontalTimeTextBox(searchEndTime, "End Time", searchEndAM));
        panel2.add(addSearchButton("Search"));
        return panel2;
    }

    public static VerticalPanel setPrettyPanel(VerticalPanel panel4) {
        panel4.setSpacing(10);
        panel4.add(addHorizontalTextBox(prettyCustomer, "Customer Name"));
        panel4.add(addPrettyButton("Prettify"));
        return panel4;
    }

    public static boolean validateCall(String customer, String callerNumber, String calleeNumber,
                                       String startDate, String startTime, String endDate, String endTime) {

        boolean check = true;
        StringBuilder sb = new StringBuilder();

        if (!customer.matches("[a-zA-Z\\s*' - - ! @ # $ % ^ & * ? 1 2 3 4 5 6 7 8 9 0 , .]+")) {
            sb.append("Invalid Customer Name!\n");
            check = false;
        }
        if (!callerNumber.matches("[0-9]{3}[-][0-9]{3}[-][0-9]{4}")) {
            sb.append("Invalid Caller Number! Be sure to format XXX-XXX-XXXX\n");
            check = false;
        }
        if (!calleeNumber.matches("[0-9]{3}[-][0-9]{3}[-][0-9]{4}")) {
            sb.append("Invalid Callee Number! Be sure to format XXX-XXX-XXXX\n");
            check = false;
        }
        if (!startDate.matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/[0-9]{2}([0-9]{2})")) {
            sb.append("Invalid Start Date! Be sure to format the start date MM/DD/YYYY\n");
            check = false;
        }
        if (!startTime.matches("([0-9]|1[012]):[0-5][0-9]")) {
            sb.append("Invalid Start Time! Be sure to format the start time h:mm\n");
            check = false;
        }
        if (!endDate.matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/[0-9]{2}([0-9]{2})")) {
            sb.append("Invalid End Date! Be sure to format the end date MM/DD/YYYY\n");
            check = false;
        }
        if (!endTime.matches("([0-9]|1[012]):[0-5][0-9]")) {
            sb.append("Invalid End Time! Be sure to format the end time h:mm\n");
            check = false;
        }

        if (!check) { Window.alert(sb.toString()); }

        return check;
    }

    public static boolean validateSearch(String customer, String startDate, String startTime, String endDate, String endTime) {

        boolean check = true;
        StringBuilder sb = new StringBuilder();

        if (!customer.matches("[a-zA-Z\\s*' - - ! @ # $ % ^ & * ? 1 2 3 4 5 6 7 8 9 0 , .]+")) {
            sb.append("Invalid Customer Name!\n");
            check = false;
        }
        if (!startDate.matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/[0-9]{2}([0-9]{2})")) {
            sb.append("Invalid Start Date! Be sure to format the start date MM/DD/YYYY\n");
            check = false;
        }
        if (!startTime.matches("([0-9]|1[012]):[0-5][0-9]")) {
            sb.append("Invalid Start Time! Be sure to format the start time h:mm\n");
            check = false;
        }
        if (!endDate.matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/[0-9]{2}([0-9]{2})")) {
            sb.append("Invalid End Date! Be sure to format the end date MM/DD/YYYY\n");
            check = false;
        }
        if (!endTime.matches("([0-9]|1[012]):[0-5][0-9]")) {
            sb.append("Invalid End Time! Be sure to format the end time h:mm\n");
            check = false;
        }

        if (!check) { Window.alert(sb.toString()); }

        return check;
    }

    public static boolean searcher(AbstractPhoneCall call, Date start, Date end) {
        return call.getStartTime().after(start) && call.getEndTime().before(end);
    }
}
