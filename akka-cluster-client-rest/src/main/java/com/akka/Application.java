package com.akka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import scala.collection.immutable.Set;
import akka.actor.ActorPath;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.cluster.client.ClusterClient;
import akka.cluster.client.ClusterClientSettings;

import com.akka.config.RpcClient;
import com.akka.service.ExampleInterface;
import com.gs.collections.api.list.MutableList;
import com.gs.collections.impl.list.mutable.FastList;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

@Configuration
@EnableJpaRepositories
@ComponentScan(basePackages = { "com.akka" })
@Import(RepositoryRestMvcConfiguration.class)
@EnableAutoConfiguration
public class Application extends RepositoryRestMvcConfiguration {
	
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
