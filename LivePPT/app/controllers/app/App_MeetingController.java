package controllers.app;

import play.mvc.Controller;

import com.fever.liveppt.service.MeetingService;
import com.google.inject.Inject;

public class App_MeetingController extends Controller {
	@Inject
	MeetingService meetingService;

}
