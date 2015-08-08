package edu.pdx.cs410J.jonerik.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.Collection;

/**
 * A basic GWT class that makes sure that we can send an Phone Bill back from the server
 */
public class PhoneBillGwt implements EntryPoint {
  public void onModuleLoad() {
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


      //Button button2 = new Button("Pong Server");
      VerticalPanel panel2 = new VerticalPanel();
      panel2.getElement().setAttribute("align", "center");
      panel2.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);




      //panel2.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
      //panel2.add(button2);
      RootPanel rootPanel = RootPanel.get();

      //rootPanel.add(button);
      panel2.add(addHorizontalTextBox("Enter Caller Name", "Caller"));
      panel2.add(addHorizontalTextBox("Enter Callee Name", "Callee"));
      panel2.add(addHorizontalTextBox("Enter Date DD/MM/YYYY", "Start Date"));
      panel2.add(addHorizontalTextBox("Start Time HH:MM", "Start Time"));
      panel2.add(addHorizontalTextBox("Enter Date DD/MM/YYYY", "End Date"));
      panel2.add(addHorizontalTextBox("End Time HH:MM", "End Time"));

      HorizontalPanel ampm = new HorizontalPanel();
      ampm.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
      final RadioButton am = new RadioButton("up", "AM");
      final RadioButton pm = new RadioButton("up", "PM");

      ampm.add(am);
      ampm.add(pm);
      panel2.add(ampm);
      
      rootPanel.add(panel2);
      //rootPanel.add(ampm);
      //rootPanel.add(button2);
  }


    public static TextBox addTextBox(String text){
        final TextBox textbox = new TextBox();
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

    public static HorizontalPanel addHorizontalTextBox(String textBox, String labelText) {
        HorizontalPanel panel = new HorizontalPanel();

        panel.add(addLabel(labelText));
        panel.add(addTextBox(textBox));
        return panel;

    }



}
