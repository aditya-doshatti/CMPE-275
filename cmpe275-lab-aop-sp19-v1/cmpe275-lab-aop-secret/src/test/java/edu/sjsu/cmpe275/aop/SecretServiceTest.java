package edu.sjsu.cmpe275.aop;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
/***
 * 
 * @author adityadoshatti
 *	
 */
public class SecretServiceTest {

    /***
     * These are dummy test cases. You may add test cases based on your own need.
     */
	SecretService secretService;
	SecretStats stats;
	ApplicationContext context;
	
	@Before
    public void setUp() throws Exception {
    	context = new FileSystemXmlApplicationContext("src/main/resources/context.xml");
    	secretService=  (SecretService)context.getBean("secretService");
    	stats = (SecretStats) context.getBean("secretStats");
    }
	
	@After
	public void tearDown() {
		stats.resetStatsAndSystem();
	}
    
    @Test(expected = Test.None.class /* no exception expected */)
    public void happyPath() {
		try {
			UUID secret = secretService.createSecret("Alice", "My little secret");
			UUID secret2 = secretService.createSecret("Bob", "My little secret2");
	    	secretService.shareSecret("Alice", secret, "Carl");
	    	secretService.shareSecret("Bob", secret2, "Carl");
	    	secretService.shareSecret("Carl", secret, "Bob");
	    	secretService.readSecret("Bob", secret2);
	    	secretService.readSecret("Carl", secret2);
	    	secretService.readSecret("Carl", secret); 
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	 
    }
    
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void longSecret() throws IllegalArgumentException, IOException {
       	String longString = new String("a very long string which is not allowed as part of our"
    			+ "program so this string should throw an exception, SO I am testing this string"
    			+ "PLease hope that this is greater than 100 characters long");
       	UUID secret = secretService.createSecret("Bob", longString);
    }
    
    @Test
    public void nullSecretMsg() throws IllegalArgumentException, IOException {
       	UUID secret = secretService.createSecret("Bob", null);
    }
    
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void nullSecretUser() throws IllegalArgumentException, IOException {
       	UUID secret = secretService.createSecret(null, "Msg");
    }
    
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void nullShareUser() throws IllegalArgumentException, IOException {
    	UUID secret = secretService.createSecret("Alice", "My little secret");
       	secretService.shareSecret("Alice", secret, null);
    }
    
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void nullReadUser() throws IllegalArgumentException, IOException {
    	UUID secret = secretService.createSecret("Alice", "My little secret");
       	secretService.readSecret(null, secret);
    }
    
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void nullUnshareUser() throws IllegalArgumentException, IOException {
    	UUID secret = secretService.createSecret("Alice", "My little secret");
       	secretService.unshareSecret("Alice", secret, null);
    }
    
    @Test(expected = NotAuthorizedException.class)
    public void unauthorizedShareUser() throws IllegalArgumentException, IOException {
    	UUID secret = secretService.createSecret("Alice", "My little secret");
       	secretService.shareSecret("Bob", secret, "Alice");
    }
    
    @Test(expected = NotAuthorizedException.class)
    public void unauthorizedunshareUser() throws IllegalArgumentException, IOException {
    	UUID secret = secretService.createSecret("Alice", "My little secret");
       	secretService.unshareSecret("Bob", secret, "Alice");
    }
    
    @Test(expected = NotAuthorizedException.class)
    public void unauthorizedReadUser() throws IllegalArgumentException, IOException {
    	UUID secret = secretService.createSecret("Alice", "My little secret");
       	secretService.readSecret("Bob", secret);
    }
    
    @Test
    public void shoudlHaveCorrectLengthOfLongestSecret() throws Throwable {
    	UUID secret = secretService.createSecret("Alice", "My little secret");
    	UUID secret2 = secretService.createSecret("Alice", "My little secret2");
    	UUID secret3 = secretService.createSecret("Alice", "little secret");
    	UUID secret4 = secretService.createSecret("Alice", "My little longest secret");
    	assertEquals(24, stats.getLengthOfLongestSecret());
    }
    
    @Test
    public void shoudlAplhaHaveCorrectWorstSecretKeeper() throws Throwable{
    	UUID secret = secretService.createSecret("Alice", "My little secret");
		UUID secret2 = secretService.createSecret("Bob", "My little secret2");
    	secretService.shareSecret("Alice", secret, "Matt");
    	secretService.shareSecret("Alice", secret, "Carl");
    	secretService.shareSecret("Bob", secret2, "Matt");
    	secretService.shareSecret("Bob", secret2, "Jhon");
    	secretService.shareSecret("Carl", secret, "Ed");
    	assertEquals("Alice", stats.getWorstSecretKeeper());
    }
    
    @Test
    public void shoudlHaveCorrectWorstSecretKeeper() throws Throwable{
    	UUID secret = secretService.createSecret("Alice", "My little secret");
		UUID secret2 = secretService.createSecret("Bob", "My little secret2");
    	secretService.shareSecret("Alice", secret, "David");
    	secretService.shareSecret("Bob", secret2, "David");
    	secretService.shareSecret("Alice", secret, "Carl");
    	secretService.shareSecret("Bob", secret2, "Carl");
    	secretService.shareSecret("Carl", secret2, "Ed");
    	secretService.shareSecret("Carl", secret2, "Alice");
    	secretService.shareSecret("Ed", secret2, "Matt");
    	assertEquals("Bob", stats.getWorstSecretKeeper());
    }
    
    @Test
    public void shouldReturnNullWorstSecretKeeper() throws Throwable {
    	UUID secret = secretService.createSecret("Alice", "My little secret");
		UUID secret2 = secretService.createSecret("Bob", "My little secret2");
		assertEquals(null, stats.getWorstSecretKeeper());
    }
    
    @Test
    public void shouldHaveCorrectBestKnownSecret() throws Throwable {
    	UUID secret = secretService.createSecret("Alice", "My little secret");
		UUID secret2 = secretService.createSecret("Bob", "My little secret2");
		secretService.shareSecret("Alice", secret, "David");
    	secretService.shareSecret("Bob", secret2, "David");
    	secretService.shareSecret("Bob", secret2, "Alice");
    	secretService.readSecret("David", secret);
    	secretService.readSecret("Alice", secret);
    	secretService.readSecret("David", secret);
    	secretService.readSecret("David", secret);
    	secretService.readSecret("David", secret2);
    	secretService.readSecret("Alice", secret2);
    	secretService.readSecret("Bob", secret2);
    	secretService.unshareSecret("Bob", secret2, "David");
    	assertEquals("My little secret2", stats.getBestKnownSecret());	
    }
    
    @Test
    public void shouldHaveAlphaCorrectBestKnownSecret() throws Throwable {
    	UUID secret = secretService.createSecret("Alice", "My little secret");
		UUID secret2 = secretService.createSecret("Bob", "Am I bored?");
		secretService.shareSecret("Alice", secret, "David");
    	secretService.shareSecret("Bob", secret2, "David");
    	secretService.shareSecret("Bob", secret2, "Alice");
    	secretService.readSecret("David", secret);
    	secretService.readSecret("Alice", secret);
    	secretService.readSecret("David", secret2);
    	secretService.readSecret("Bob", secret2);
    	secretService.unshareSecret("Bob", secret2, "David");
    	assertEquals("Am I bored?", stats.getBestKnownSecret());	
    }
    
    @Test
    public void shouldHavenullCorrectBestKnownSecret() throws Throwable {
    	UUID secret = secretService.createSecret("Alice", "My little secret");
		UUID secret2 = secretService.createSecret("Bob", "Am I bored?");
    	secretService.unshareSecret("Bob", secret2, "David");
    	assertEquals(null, stats.getBestKnownSecret());	
    }
    
    @Test
    public void shouldHaveCorrectMostTrustedUser() throws Throwable {
    	UUID secret = secretService.createSecret("Alice", "My little secret");
		UUID secret2 = secretService.createSecret("Bob", "My little secret2");
		secretService.shareSecret("Alice", secret, "David");
    	secretService.shareSecret("Bob", secret2, "David");
    	secretService.shareSecret("Bob", secret2, "Alice");
    	secretService.shareSecret("Bob", secret2, "David");
    	secretService.shareSecret("Bob", secret2, "Alice");
    	secretService.shareSecret("Bob", secret2, "Alice");
    	secretService.shareSecret("Bob", secret2, "Alice");
    	secretService.shareSecret("Bob", secret2, "Alice");
    	secretService.unshareSecret("Bob", secret2, "David");
    	secretService.unshareSecret("Alice", secret, "David");
    	assertEquals("David", stats.getMostTrustedUser());	
    }
    
    @Test
    public void shouldHaveAplhaCorrectMostTrustedUser() throws Throwable {
    	UUID secret = secretService.createSecret("Alice", "My little secret");
		UUID secret2 = secretService.createSecret("Bob", "My little secret2");
		secretService.shareSecret("Alice", secret, "David");
    	secretService.shareSecret("Bob", secret2, "David");
    	secretService.shareSecret("Bob", secret2, "Alice");
    	secretService.shareSecret("Bob", secret2, "Ed");
    	secretService.shareSecret("Ed", secret2, "Alice");
    	assertEquals("Alice", stats.getMostTrustedUser());	
    }
    
    @Test
    public void shouldHavenullCorrectMostTrustedUser() throws Throwable {
    	UUID secret = secretService.createSecret("Alice", "My little secret");
		UUID secret2 = secretService.createSecret("Bob", "My little secret2");
    	assertEquals(null, stats.getMostTrustedUser());	
    }
}