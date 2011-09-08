package org.jboss.tools.gwt.client;

import java.io.IOException;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface ChatServiceAsync {
	void chatServer(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;
}
