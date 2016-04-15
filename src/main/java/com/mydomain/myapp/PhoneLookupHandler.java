package com.mydomain.myapp;

import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class PhoneLookupHandler implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext rc) {
		 PythonInterpreter python = new PythonInterpreter();
		 
		 python.set("file_name", "phonebook.csv");
		 python.set("lookup_name", rc.request().getParam("lookup"));
		 python.execfile("parser.py");
		 PyObject retval = python.get("ret_val");
		 rc.response().end(retval.toString());

	}
	
}
