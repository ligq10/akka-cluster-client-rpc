package com.akka;


import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.client.ClusterClientReceptionist;
import akka.routing.FromConfig;

import com.akka.actor.RpcServerActor;
import com.akka.service.ExampleInterface;
import com.akka.service.ExampleInterfaceImpl;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

@Configuration
@EnableJpaRepositories
@ComponentScan(basePackages = { "com.akka" })
@Import(RepositoryRestMvcConfiguration.class)
@EnableAutoConfiguration
public class Application extends RepositoryRestMvcConfiguration {
	
	
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
      
        final Config config = ConfigFactory.load("application.conf");

 
        ActorSystem system = ActorSystem.create("cp150", config);
        // Server 加入发布的服务
        Map<Class<?>, Object> beans = new HashMap<Class<?>, Object>();
        beans.put(ExampleInterface.class, new ExampleInterfaceImpl());
        //system.actorOf(Props.create(RpcServer.class, beans), "rpcServer");
        ActorRef rpcRouter = system.actorOf(Props.create(RpcServerActor.class), "rpcServer");
        ClusterClientReceptionist.get(system).registerService(rpcRouter);
	}
}
