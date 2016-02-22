package com.akka.actor;

import com.akka.model.RpcCallMethod;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.cluster.client.ClusterClient;

/**
 * Created by linyang on 16/1/7.
 */
public class RpcClientActor extends UntypedActor {

    private ActorRef clusterCLient;

    public RpcClientActor(ActorRef clusterClient) {
        this.clusterCLient = clusterClient;
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof RpcCallMethod) {
            clusterCLient.tell(new ClusterClient.Send("/user/rpcServer", message), getSender());
        }
    }
}
