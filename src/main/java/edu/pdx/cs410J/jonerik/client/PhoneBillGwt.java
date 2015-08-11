package edu.pdx.cs410J.jonerik.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.core.client.GWT;

/**
 * A basic GWT class that makes sure that we can send an Phone Bill back from the server
 */
public class PhoneBillGwt implements EntryPoint {

    private static TextBox customerName = new TextBox();
    private static TextBox caller = new TextBox();
    private static TextBox callee = new TextBox();
    private static TextBox startDate = new TextBox();
    private static TextBox startTime = new TextBox();
    private static TextBox endDate = new TextBox();
    private static TextBox endTime = new TextBox();
    private static ToggleButton startAM = new ToggleButton("AM", "PM");
    //private static RadioButton startPM = new RadioButton("start", "PM"); // finish making radio buttons global
    //private static RadioButton endAM = new RadioButton("end", "AM");
    //private static RadioButton endPM = new RadioButton("end", "PM");
    private static ToggleButton endAM = new ToggleButton("AM", "PM");


  public void onModuleLoad() {

      addTextBox(customerName, "Name on Phone Bill");
      addTextBox(caller, "Enter Caller Name");
      addTextBox(callee, "Enter Callee Name");
      addTextBox(startDate, "DD/MM/YYYY");
      addTextBox(endDate, "DD/MM/YYYY");
      addTextBox(startTime, "HH:MM");
      addTextBox(endTime, "HH:MM");


      /*
    Button button = new Button("Ping Server");
    button.addClickHandler(new ClickHandler() {
        public void onClick(ClickEvent clickEvent) {
            PingServiceAsync async = GWT.create(PingService.class);
            async.ping(new AsyncCallback<AbstractPhoneBill>() {

                public void onFailure(Throwable ex) {
                    Window.alert(ex.toString());
                }

                public void onSuccess(AbstractPhoneBill phonebill) {
                    StringBuilder sb = new StringBuilder(phonebill.toString());
                    Collection<AbstractPhoneCall> calls = phonebill.getPhoneCalls();
                    for (AbstractPhoneCall call : calls) {
                        sb.append(call);
                        sb.append("\n");
                    }
                    Window.alert(sb.toString());
                }
            });
        }
    });
        */

      VerticalPanel panel2 = new VerticalPanel();
      //panel2.setSpacing(10);
      panel2.getElement().setAttribute("padding", "10");
      panel2.getElement().setAttribute("align", "center");
      panel2.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

      RootPanel rootPanel = RootPanel.get();
      panel2.add(caller);

      rootPanel.get("gwtcontainer").add(setVerticalPanel(panel2));

  }


    public static TextBox addTextBox(final TextBox textbox,  String text){

        textbox.setText(text);
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
        label.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        return label;
    }

    public static HorizontalPanel addHorizontalTextBox(TextBox box, String labelText) {
        HorizontalPanel panel = new HorizontalPanel();
        DialogBox help = new DialogBox();
        panel.setSpacing(5);

        panel.add(addLabel(labelText));
        panel.add(box);
        panel.add(help);
        return panel;

    }

    public static HorizontalPanel addHorizontalTimeTextBox(TextBox box, String labelText, ToggleButton ampm) {
        HorizontalPanel panel = new HorizontalPanel();
        DialogBox help = new DialogBox();
        panel.setSpacing(5);

        panel.add(addLabel(labelText));
        panel.add(box);
        panel.add(ampm);
        panel.add(help);
        return panel;

    }

    public static DialogBox helpButton (String whichHelp) {
        DialogBox help = new DialogBox(true);
        help.setText("Hello");
        return help;
    }


    public static HorizontalPanel addAMPMButtonStart() {
        HorizontalPanel ampmStart = new HorizontalPanel();
        ampmStart.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        ampmStart.add(startAM);

        return ampmStart;
    }

    public static HorizontalPanel addAMPMButtonEnd() {
        HorizontalPanel ampmEnd = new HorizontalPanel();
        ampmEnd.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        ampmEnd.add(endAM);

        return ampmEnd;
    }

    public static Button addCallButton(String buttonName) {
        Button addCall = new Button(buttonName);
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

                /*
                ArrayList<String> inputs = new ArrayList<>();
                inputs.add(customerNameInput);
                inputs.add(callerInput);
                inputs.add(calleeInput);
                inputs.add(startDateInput + " " + startTimeInput + " " + startAmPm);
                inputs.add(endDateInput + " " + endTimeInput + " " + endAmPm);
                */

                String startTimeString = startDateInput + " " + startTimeInput + " " + startAmPm;
                String endTimeString = endDateInput + " " + endTimeInput + " " + endAmPm;


                PingServiceAsync pinger = GWT.create(PingService.class);
                boolean checkArgs = validateCall(customerNameInput, callerInput, calleeInput,
                        startDateInput, startTimeInput, endDateInput, endTimeInput);
                //boolean checkArgs = false;

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

    public static Button addSearchButton(String buttonName, final String windowAlert) {
        Button searchCall = new Button(buttonName);

        searchCall.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                Window.alert(windowAlert);
            }
        });
        return searchCall;

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
        panel2.add(addSearchButton("Search", "Searcher"));

        return panel2;
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
        if (!startTime.matches("[0-9][0-2]{0,1}[:][0-5][0-9]")) {
            sb.append("Invalid Start Time! Be sure to format the start time h:mm\n");
            check = false;
        }
        if (!endDate.matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/[0-9]{2}([0-9]{2})")) {
            sb.append("Invalid End Date! Be sure to format the end date MM/DD/YYYY\n");
            check = false;
        }
        if (!endTime.matches("[0-9][0-2]{0,1}[:][0-5][0-9]")) {
            sb.append("Invalid End Time! Be sure to format the end time h:mm\n");
            check = false;
        }

        if (!check) { Window.alert(sb.toString()); }

        return check;
    }

    public static TextBox getUserInput(String box) {
        return TextBox.wrap(getElementId(box));
    }

    private static Element getElementId(String box){
        return Document.get().getElementById(box);
    }

}
