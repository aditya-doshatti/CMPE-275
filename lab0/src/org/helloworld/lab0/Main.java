package org.helloworld.lab0;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		ApplicationContext appCon = new ClassPathXmlApplicationContext("beans.xml");
		GreetTheAuthor gta = (GreetTheAuthor) appCon.getBean("greeter");
		System.out.println(gta.getGreeting());

	}

}
