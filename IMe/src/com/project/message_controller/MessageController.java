package com.project.message_controller;

import com.base.common.Common;
import com.base.controller.Controller;
import com.base.model.Configuration;

public class MessageController extends Controller {
	private String addFriend = "AddFriend.do";
	private String getFriends = "GetFriend.do";

	public MessageController(Configuration configuration) {
		super(configuration);
	}

	public void addFriend() {
		this.startRequest(addFriend, Common.POST);
	}

	public void getFriend() {
		this.startRequest(getFriends, Common.POST);
	}

}
