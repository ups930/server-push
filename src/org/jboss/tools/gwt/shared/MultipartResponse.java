package org.jboss.tools.gwt.shared;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * 12 * A utility class to generate <tt>multipart/x-mixed-replace</tt>
 * responses, 13 * the kind of responses that implement server push. Note that
 * Microsoft 14 * Internet Explorer does not understand this sort of response.
 * 15 *
 * <p>
 * 16 * To use this class, first construct a new MultipartResponse 17 * passing
 * to its constructor the servlet's response parameter. 18 * MultipartResponse
 * uses the response object to fetch the 19 * servlet's output stream and to set
 * the response's content type. 20 *
 * <p>
 * 21 * Then, for each page of content, begin by calling
 * <tt>startResponse()</tt> 22 * passing in the content type for that page. Send
 * the content for the 23 * page by writing to the output stream as usual. A
 * call to 24 * <tt>endResponse()</tt> ends the page and flushes the content so
 * the 25 * client can see it. At this point a <tt>sleep()</tt> or other delay
 * 26 * can be added until the next page is ready for sending. 27 *
 * <p>
 * 28 * The call to <tt>endResponse()</tt> is optional. The 29 *
 * <tt>startResponse()</tt> method knows whether the last response has 30 * been
 * ended, and ends it itself if necessary. However, it's wise to 31 * call
 * <tt>endResponse()</tt> if there's to be a delay between the 32 * time one
 * response ends and the next begins. It lets the client display 33 * the latest
 * response during the time it waits for the next one. 34 *
 * <p>
 * 35 * Finally, after each response page has been sent, a call to the 36 *
 * <tt>finish()</tt> method finishes the multipart response and sends a 37 *
 * code telling the client there will be no more responses. 38 *
 * <p>
 * 39 * For example: 40 * <blockquote>
 * 
 * <pre>
 * 41  * MultipartResponse multi = new MultipartResponse(res);
 * 42  * &nbsp;
 * 43  * multi.startResponse("text/plain");
 * 44  * out.println("On your mark");
 * 45  * multi.endResponse();
 * 46  * &nbsp;
 * 47  * try { Thread.sleep(1000); } catch (InterruptedException e) { }
 * 48  * &nbsp;
 * 49  * multi.startResponse("text/plain");
 * 50  * out.println("Get set");
 * 51  * multi.endResponse();
 * 52  * &nbsp;
 * 53  * try { Thread.sleep(1000); } catch (InterruptedException e) { }
 * 54  * &nbsp;
 * 55  * multi.startResponse("image/gif");
 * 56  * ServletUtils.returnFile(req.getRealPath("/images/go.gif"), out);
 * 57  * &nbsp;
 * 58  * multi.finish();
 * 59  *
 * </pre>
 * 
 * </blockquote> 60 * 61 * @see ServletUtils 62 * 63 * @author <b>Jason
 * Hunter</b>, Copyright &#169; 1998 64 * @version 1.0, 98/09/18 65
 */
public class MultipartResponse {

	HttpServletResponse res;
	ServletOutputStream out;
	boolean endedLastResponse = true;

	/**
	 * 73 * Constructs a new MultipartResponse to send content to the given 74 *
	 * servlet response. 75 * 76 * @param response the servlet response 77 * @exception
	 * IOException if an I/O error occurs 78
	 */
	public MultipartResponse(HttpServletResponse response) throws IOException {
		// Save the response object and output stream
		res = response;
		out = res.getOutputStream();

		// Set things up
		res.setContentType("multipart/x-mixed-replace;boundary=End");
		out.println();
		out.println("--End");
	}

	/**
	 * 91 * Begins a single response with the specified content type. 92 * This
	 * method knows whether the last response has been ended, and 93 * ends it
	 * itself if necessary. 94 * 95 * @param contentType the content type of
	 * this response part 96 * @exception IOException if an I/O error occurs 97
	 */
	public void startResponse(String contentType) throws IOException {
		// End the last response if necessary
		if (!endedLastResponse) {
			endResponse();
		}
		// Start the next one
		out.println("Content-type: " + contentType);
		out.println();
		endedLastResponse = false;
	}

	/**
	 * Ends a single response. Flushes the output. 111 * 112 * @exception
	 * IOException if an I/O error occurs 113
	 */
	public void endResponse() throws IOException {
		// End the last response, and flush so the client sees the content
		out.println();
		out.println("--End");
		out.flush();
		endedLastResponse = true;
	}

	/**
	 * 123 * Finishes the multipart response. Sends a code telling the client
	 * 124 * there will be no more responses and flushes the output. 125 * 126 * @exception
	 * IOException if an I/O error occurs 127
	 */
	public void finish() throws IOException {
		out.println("--End--");
		out.flush();
	}
}
