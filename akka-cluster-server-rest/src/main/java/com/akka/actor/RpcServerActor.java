package com.akka.actor;

import akka.actor.UntypedActor;

import org.apache.commons.lang3.reflect.MethodUtils;

import com.akka.model.Cp150DownMessage;
import com.akka.model.RpcCallMethod;

import java.util.Map;

/**
 * Created by linyang on 16/1/7.
 */
public class RpcServerActor extends UntypedActor {


    @Override
    public void onReceive(Object message) throws Exception {
    	if(message instanceof String){
        	System.out.println(message);
        }else if(message instanceof Cp150DownMessage){
        	Cp150DownMessage cp150DownMessage = (Cp150DownMessage) message;
        	System.out.println("message imei:"+cp150DownMessage.getImei()+",message content:"+cp150DownMessage.getMessage());
        }else{
        	unhandled(message);
        }
    }
}
