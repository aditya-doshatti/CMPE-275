package edu.sjsu.cmpe275.aop;

import java.util.UUID;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        /***
         * Following is a dummy implementation of App to demonstrate bean creation with Application context.
         * You may make changes to suit your need, but this file is NOT part of your submission.
         */

    	ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
        SecretService secretService = (SecretService) ctx.getBean("secretService");
        SecretStats stats = (SecretStats) ctx.getBean("secretStats");

        try {
        	UUID secret = secretService.createSecret("Alice", "My little secret");
        	UUID secret2 = secretService.createSecret("Bob", "My little secret2");
        	secretService.shareSecret("Alice", secret, "Bob");
        	secretService.shareSecret("Bob", secret2, "Carl");
        	secretService.shareSecret("Bob", secret, "Carl");
        	secretService.readSecret("Bob", secret2);
        	secretService.readSecret("Carl", secret2);
        	secretService.readSecret("Carl", secret);
//        	String longString = new String("a very long string which is not allowed as part of our"
//        			+ "program so this string should throw an exception, SO I am testing this string"
//        			+ "PLease hope that this is greater than 100 characters long");
//        	UUID secret3 = secretService.createSecret("Bob", longString);
        	secretService.unshareSecret("Alice", secret, "Carl");
//        	secretService.unshareSecret("Bob", secret2, "Carl");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Best known secret: " + stats.getBestKnownSecret());
        System.out.println("Worst secret keeper: " + stats.getWorstSecretKeeper());
        System.out.println("Most trusted user: " + stats.getMostTrustedUser());
        ctx.close();
    }
}
