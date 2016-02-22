package com.akka.config;

import akka.actor.ActorPath;
import akka.actor.ActorPaths;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.Cluster;
import akka.cluster.client.ClusterClient;
import akka.cluster.client.ClusterClientSettings;
import akka.cluster.routing.AdaptiveLoadBalancingGroup;
import akka.cluster.routing.ClusterRouterGroup;
import akka.cluster.routing.ClusterRouterGroupSettings;
import akka.cluster.routing.HeapMetricsSelector;
import akka.routing.FromConfig;

import com.akka.actor.RpcClientActor;
import com.akka.model.RpcBeanProxy;
import com.gs.collections.api.list.MutableList;
import com.gs.collections.impl.list.mutable.FastList;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by liguangqiang on 16/1/7.
 */
public final class RpcClient extends Thread{
    private static final AtomicBoolean INTIALIZED = new AtomicBoolean(false);

/*	private ActorSystem system;

	private ActorRef rpc;

	private ActorRef clientServer;*/

	private ActorSystem system;

    private ActorRef clusterClient;

    private ActorRef clientRouter;
	
	private static RpcClient instance = null;

    private RpcClient() {
        final Config config = ConfigFactory.load("application.conf");

        system = ActorSystem.create("cp150", config);
        clusterClient = system.actorOf(ClusterClient.props(
                ClusterClientSettings.create(system).withInitialContacts(initialContacts(config))),
                "client");
    }

    public void tell(String url,Object message){
    	clusterClient.tell(new ClusterClient.Send(url, message, true), ActorRef.noSender());
    }
       
    private Set<ActorPath> initialContacts(Config config) {
    	List<String> actorPaths = config.getStringList("akka.cluster.client.initial-contacts");
    	List<ActorPath> ActorPathList = new ArrayList<ActorPath>();
        if(null != actorPaths && actorPaths.size() > 0){
        	for(String path : actorPaths){
            	ActorPathList.add(ActorPaths.fromString(path));
        	}
        }
    	return new HashSet<ActorPath>(ActorPathList);
    }

    public static final RpcClient getInstance() {
    	if (INTIALIZED.compareAndSet(false, true)) {
            if (instance == null) {
                instance = new RpcClient();
            }
        }
		return instance;
    }

    public <T> T getBean(Class<T> clz) {
        return new RpcBeanProxy().proxy(clientRouter, clz);
    }
}
