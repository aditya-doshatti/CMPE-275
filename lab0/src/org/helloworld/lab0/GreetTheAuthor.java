package org.helloworld.lab0;

/**
 * Class which the bean would be using
 * @author adityadoshatti
 *
 */
public class GreetTheAuthor implements Greeter {
	String name;
	/**
	 * Method to great everyone
	 * @return String with Greeting
	 */
	public String getGreeting() {
		return "Hello World from "+ this.name;
	}

	@Override
	/**
	 * Method to set Author Name
	 * @param name Author Name
	 */
	public void setName(String name) {
		this.name = name;		
	}

}
