package org.jboss.tools.gwt.client;

import java.io.IOException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface ChatService extends RemoteService {
	String chatServer(String name) throws IllegalArgumentException;

}
