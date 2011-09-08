package org.jboss.tools.gwt.client;

import java.io.IOException;
import com.oreilly.servlet.*;

import com.google.gwt.autobean.server.Configuration.Builder;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.http.client.Header;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Gwt_jboss implements EntryPoint {

	
	final Button sendButton = new Button("Send");
	final TextArea messagesField = new TextArea();
	final TextBox messageField = new TextBox();
	final Label errorLabel = new Label();
	
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final ChatServiceAsync chatService = GWT
			.create(ChatService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
	
		
		// We can add style names to widgets
		sendButton.addStyleName("sendButton");
		
		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		
		RootPanel.get("messagesFieldContainer").add(messagesField);
		RootPanel.get("messageFieldContainer").add(messageField);
		RootPanel.get("messageFieldContainer").add(sendButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);
	//	RootPanel.get("message").add(messageField);

		// Focus the cursor on the name field when the app loads
		messageField.setFocus(true);
		messageField.selectAll();
		messagesField.setSize("250px","300px");
		messagesField.setReadOnly(true);
		messageField.setWidth("250px");
		sendButton.setWidth("260px");

		

		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				addMessage();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					addMessage();
				}
			}

			
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
		messageField.addKeyUpHandler(handler);
		
		// Setup timer to refresh list automatically.
		startConnection();
//	    Timer refreshTimer = new Timer() {
//	      @Override
//	      public void run() {
//	        refreshChatList();
//	      }
//	    };
//	    refreshTimer.scheduleRepeating(5000);
	}
	
	public static final int STATUS_CODE_OK = 200;
	  
	  public static void doGet(String url) {
	    RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
	    
	    try {
	      Request response = builder.sendRequest(null, new RequestCallback() {
			
			@Override
			public void onResponseReceived(Request arg0, Response arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onError(Request arg0, Throwable arg1) {
				// TODO Auto-generated method stub
				
			}
		});
	      
	    } catch (RequestException e) {
	      // Code omitted for clarity
	    }
	  }
	
	private void addMessage(){
		String currentMessage = messageField.getText();
		
		chatService.chatServer(currentMessage,new AsyncCallback<String>() {
				public void onFailure(Throwable caught) {
					System.out.println("blad w addMessage");
					
				}
	
				public void onSuccess(String result) {
					//updateChatWindow(result);
					System.out.println("sukces w addMessage");
					
				}
		});
		
		
		
	}
	
	private void updateChatWindow(String result){
		messagesField.setText(messagesField.getText()+"\n"+result);
		System.out.println("update chat box");
	}
	
	private void startConnection()
	{
		chatService.chatServer("",new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				System.out.println("blad w startConnection");
			}

			public void onSuccess(String result) {
				updateChatWindow(result);
				System.out.println("sukces w startConnection");
			}
	});
		
	}
	
	
	private void refreshConnection() {

		chatService.chatServer("",new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				System.out.println("blad w refreshConnection");
				refreshConnection();
			}

			public void onSuccess(String result) {
				updateChatWindow(result);
				System.out.println("sukces w refreshConnection");
				refreshConnection();
			}
	});

		  }
	
}
