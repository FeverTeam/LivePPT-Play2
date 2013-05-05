package com.fever.liveppt.service;

import org.codehaus.jackson.node.ObjectNode;

public interface UserService {
	
	public ObjectNode isPassworrdCorrect(String email, String password);

}
