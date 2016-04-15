package com.mydomain.myapp;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class Application {
	public static void main(String[] args) {
		VertxOptions options = new VertxOptions().setWorkerPoolSize(10);
		Vertx vertx = Vertx.factory.vertx(options);
		
		HttpServer server = vertx.createHttpServer();
		Router router = Router.router(vertx);

		Router restAPI = Router.router(vertx);
		restAPI.route().handler(BodyHandler.create());
		
		router.mountSubRouter("/api", restAPI);
		
		restAPI.get("/user/phone/:lookup").handler(new PhoneLookupHandler());
		
		restAPI.get("/user/:userId").handler(rc -> {
			rc.response().headers().add("content-type", "application/json");
			rc.response().end("{"+rc.request().getParam("userId")+"}");
		});
		
		restAPI.post("/user").handler(rc -> {
			JsonObject jsonObj = rc.getBodyAsJson();
			UserDTO dto = new UserDTO();
			dto.setFirst(jsonObj.getString("first"));
			System.out.println(dto.getFirst());
				
			rc.response().setStatusCode(204);
			rc.response().end();
	
		});

		restAPI.put("/user").handler(rc -> {
			JsonObject jsonObj = rc.getBodyAsJson();
			UserDTO dto = new UserDTO();
			dto.setFirst(jsonObj.getString("first"));
			System.out.println(dto.getFirst());
				
			rc.response().setStatusCode(204);
			rc.response().end();
		});
		
		

//		
//		router.route().handler(routingContext -> {
//
//		  // This handler will be called for every request
//		  HttpServerResponse response = routingContext.response();
//		  response.putHeader("content-type", "text/plain");
//
//		  // Write to the response and end it
//		  response.end("Hello World from Vert.x-Web!");
//		});
		server.requestHandler(router::accept).listen(8080);
	}
}

