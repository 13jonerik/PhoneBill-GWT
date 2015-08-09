package edu.pdx.cs410J.jonerik.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;

/**
 * A basic GWT class that makes sure that we can send an Phone Bill back from the server
 */
public class PhoneBillGwt implements EntryPoint {

    private static TextBox caller = new TextBox();
    private static TextBox callee = new TextBox();
    private static TextBox startDate = new TextBox();
    private static TextBox startTime = new TextBox();
    private static TextBox endDate = new TextBox();
    private static TextBox endTime = new TextBox();

  public void onModuleLoad() {



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
      panel2.setSpacing(10);

      panel2.getElement().setAttribute("align", "center");
      panel2.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

      RootPanel rootPanel = RootPanel.get();
      panel2.add(caller);

      HorizontalPanel ampmStart = new HorizontalPanel();
      HorizontalPanel ampmEnd = new HorizontalPanel();

      rootPanel.add(setVerticalPanel(panel2, ampmStart, ampmEnd));


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
        panel.setSpacing(5);

        panel.add(addLabel(labelText));
        panel.add(box);
        return panel;

    }

    public static HorizontalPanel addAMPMButtonStart(HorizontalPanel ampm) {
        ampm.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        final RadioButton am = new RadioButton("start", "AM");
        final RadioButton pm = new RadioButton("start", "PM");
        ampm.add(am);
        ampm.add(pm);
        return ampm;
    }

    public static HorizontalPanel addAMPMButtonEnd(HorizontalPanel ampm) {
        ampm.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        final RadioButton am = new RadioButton("end", "AM");
        final RadioButton pm = new RadioButton("end", "PM");
        ampm.add(am);
        ampm.add(pm);
        return ampm;
    }

    public static Button addCallButton(String buttonName, final String windowAlert) {
        Button addCall = new Button(buttonName);

        addCall.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                StringBuilder sb = new StringBuilder();
                sb.append("Call Info: " + "\n");
                sb.append("\n");
                sb.append(caller.getText());
                sb.append("\n");
                sb.append(callee.getText());
                sb.append("\n");
                sb.append(startDate.getText());
                sb.append("\n");
                sb.append(startTime.getText());
                sb.append("\n");
                sb.append(endDate.getText());
                sb.append("\n");
                sb.append(endTime.getText());
                sb.append("\n");
                sb.append("\nCall Added!");

                Window.alert(sb.toString());
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

    public static VerticalPanel setVerticalPanel(VerticalPanel panel2, HorizontalPanel ampmStart, HorizontalPanel ampmEnd){
        panel2.add(addHorizontalTextBox(caller, "Caller"));
        panel2.add(addHorizontalTextBox(callee, "Callee"));
        panel2.add(addHorizontalTextBox(startDate, "Start Date"));
        panel2.add(addHorizontalTextBox(startTime, "Start Time"));
        panel2.add(addAMPMButtonStart(ampmStart));
        panel2.add(addHorizontalTextBox(endDate, "End Date"));
        panel2.add(addHorizontalTextBox(endTime, "End Time"));
        panel2.add(addAMPMButtonEnd(ampmEnd));
        panel2.add(addCallButton("Add Call", "Call Added!"));
        panel2.add(addSearchButton("Search", "Searcher"));

        /*
        Button addCall = new Button("Add Call");

        addCall.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                Window.alert("Call Saved!");
            }
        });

        Button addSearch = new Button("Search");
        addSearch.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                Window.alert("Searcher");
            }
        });
        */

        //panel2.add(addCall);
        //panel2.add(addSearch);

        return panel2;

    }

    public static TextBox getUserInput(String box) {
        return TextBox.wrap(getElementId(box));
    }

    private static Element getElementId(String box){
        return Document.get().getElementById(box);
    }

}
