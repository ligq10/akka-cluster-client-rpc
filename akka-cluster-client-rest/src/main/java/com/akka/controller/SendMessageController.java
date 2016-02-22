package com.akka.controller;

import javax.websocket.server.PathParam;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.akka.config.RpcClient;
import com.akka.model.Cp150DownMessage;

@Controller
public class SendMessageController {

	@RequestMapping(value = "/sendmessage", method = { RequestMethod.POST }, produces = "application/hal+json;charset=UTF-8")
	public HttpEntity<?>  sendMessage(
    	@RequestBody Cp150DownMessage cp150DownMessage) {
        RpcClient client = RpcClient.getInstance();
        client.tell("/user/rpcServer",cp150DownMessage);           
        return new ResponseEntity<>(HttpStatus.OK);
	}
}
