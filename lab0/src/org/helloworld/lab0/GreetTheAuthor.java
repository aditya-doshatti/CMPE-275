package org.helloworld.lab0;

public class GreetTheAuthor implements Greeter {
	String name;
	
	public String getGreeting() {
		return "Hello World from "+ this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;		
	}

}
