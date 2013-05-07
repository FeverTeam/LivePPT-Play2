package controllers.app;

import play.Logger;
import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.mvc.Controller;
import play.mvc.WebSocket;

import com.fever.liveppt.service.MeetingService;
import com.google.inject.Inject;

public class App_MeetingController extends Controller {
	@Inject
	MeetingService meetingService;
	
	
	public static WebSocket<String> testWS() {
		  return new WebSocket<String>() {
		      
		    // Called when the Websocket Handshake is done.
		    public void onReady(WebSocket.In<String> in, WebSocket.Out<String> out) {
		      
		      // For each event received on the socket,
		      in.onMessage(new Callback<String>() {
		         public void invoke(String event) {
		             
		           // Log events to the console
		           Logger.info(event);  
		             
		         } 
		      });
		      
		      // When the socket is closed.
		      in.onClose(new Callback0() {
		         public void invoke() {
		             
		           Logger.info("Disconnected");
		             
		         }
		      });
		      
		      // Send a single 'Hello!' message
		      out.write("Hello!");
		      
		    }
		    
		  };
		}

}
