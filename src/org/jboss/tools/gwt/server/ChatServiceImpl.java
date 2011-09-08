package org.jboss.tools.gwt.server;
import java.io.*;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.tools.gwt.client.ChatService;
import org.jboss.tools.gwt.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;



/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")


public class ChatServiceImpl extends RemoteServiceServlet implements
		ChatService {
	
	private  ArrayList<String> chatList = new ArrayList<String>();
	
	public String chatServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (input.equals(""))
		{
			
			int messagesCount = chatList.size();
			synchronized (this) {
			    while ( chatList.size() == messagesCount ) try {
			      this.wait();
			    } catch ( InterruptedException e ) {
			      System.out.println("blad czekania");
			    }
			    
			    return chatList.get(chatList.size()-1);
			}
		}
		else
		{
			synchronized ( this ) {
				chatList.add(input);
			    this.notifyAll();
			    return input;
			  }
			
			
		}
			
		

		// Escape data from the client to avoid cross-site script vulnerabilities.
		
	}

}
