package com.mydomain.myapp;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

public class RestAppVerticle extends AbstractVerticle {
	@Override
	public void start() throws Exception {
		HttpServer server = vertx.createHttpServer();
		Router router = Router.router(vertx);

		Router restAPI = Router.router(vertx);
		router.mountSubRouter("/api", restAPI);
		
		restAPI.get("/user/:userId").handler(rc -> {
			rc.response().headers().add("content-type", "application/json");
			rc.response().end("{"+rc.request().getParam("userId")+"}");
		});
		server.requestHandler(router::accept).listen(8080);
	}
}
