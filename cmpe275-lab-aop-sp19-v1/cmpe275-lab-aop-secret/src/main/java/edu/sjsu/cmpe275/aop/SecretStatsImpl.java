package edu.sjsu.cmpe275.aop;

import java.util.*;

import javax.crypto.SecretKey;

public class SecretStatsImpl implements SecretStats {
    /***
     * Following is a dummy implementation.
     * You are expected to provide an actual implementation based on the requirements.
     */
	
	Map<String, List<UUID>> createdBy = new HashMap<String, List<UUID>>();
	Map<String, List<UUID>> sharedWith = new HashMap<String, List<UUID>>();
	Map<String, List<UUID>> receivedBy = new HashMap<String, List<UUID>>();
	
	public void resetStatsAndSystem() {
		// TODO Auto-generated method stub
		
	}

	public int getLengthOfLongestSecret() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getMostTrustedUser() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getWorstSecretKeeper() {
		System.out.println("Created Wali list");
		for (String key : createdBy.keySet()) {
		    System.out.println(key + " " + createdBy.get(key));
		}
		System.out.println("Shared Wali list");
		for (String key : sharedWith.keySet()) {
		    System.out.println(key + " " + sharedWith.get(key));
		}
		System.out.println("Received Wali list");
		for (String key : receivedBy.keySet()) {
		    System.out.println(key + " " + receivedBy.get(key));
		}
		// TODO Auto-generated method stub
		return null;
	}

	public String getBestKnownSecret() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void recordSecretCreation(String userId, UUID scretKey) {
		List<UUID> itemsList = createdBy.get(userId);

	    // if list does not exist create it
	    if(itemsList == null) {
	         itemsList = new ArrayList<UUID>();
	         itemsList.add(scretKey);
	         createdBy.put(userId, itemsList);
	    } else {
	        // add if item is not already in list
	        if(!itemsList.contains(scretKey)) itemsList.add(scretKey);
	    }
	}
	
	public void recordSecretShare(String userId, UUID scretKey, String targetUser) {
		List<UUID> shareList = sharedWith.get(userId);
		List<UUID> receivedList = receivedBy.get(targetUser);

	    // if list does not exist create it
	    if(shareList == null) {
	    	shareList = new ArrayList<UUID>();
	    	shareList.add(scretKey);
	         sharedWith.put(userId, shareList);
	    } else {
	        // add if item is not already in list
	        if(!shareList.contains(scretKey)) shareList.add(scretKey);
	    }
	    
	    if (receivedList == null) {
	    	receivedList = new ArrayList<UUID>();
	    	receivedList.add(scretKey);
	    	receivedBy.put(targetUser, receivedList);
	    } else {
	    	if(!receivedList.contains(scretKey)) receivedList.add(scretKey);
	    }
	}

	public  boolean canUserReadIt(String targetUser, UUID secretID) {
		List<UUID> receivedList = receivedBy.get(targetUser);
		List<UUID> createList = createdBy.get(targetUser);
		if (createList == null || !createList.contains(secretID)) {
			if (receivedList == null) {
				throw new NotAuthorizedException();
			} else if (receivedList.contains(secretID)) {
				return true;
			} else {
				throw new NotAuthorizedException();
			}
		}
		else {
			return true;
		}
	}
	
	public boolean hasUserCreatedIt(String targetUser, UUID secretID) {
		List<UUID> createList = createdBy.get(targetUser);
		if (createList == null || !createList.contains(secretID)) {
				throw new NotAuthorizedException();
		}
		else {
			return true;
		}
	}
	
	public void recordSecretUnshare(String userId, UUID scretKey, String targetUser) {
		List<UUID> receivedList = receivedBy.get(targetUser);
	    if(receivedList.contains(scretKey))
	    	receivedList.remove(scretKey);
	}
	
    
}



