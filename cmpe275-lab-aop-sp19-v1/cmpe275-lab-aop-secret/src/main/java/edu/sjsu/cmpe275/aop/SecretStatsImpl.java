package edu.sjsu.cmpe275.aop;

import java.util.*;


public class SecretStatsImpl implements SecretStats {
    /***
     * Following is a dummy implementation.
     * You are expected to provide an actual implementation based on the requirements.
     */
	
	Map<String, Set<UUID>> createdBy = new HashMap<String, Set<UUID>>();
	Map<String, Set<UUID>> sharedWith = new HashMap<String, Set<UUID>>();
	Map<String, Set<UUID>> receivedBy = new HashMap<String, Set<UUID>>();
	Map<String, Set<UUID>> readBy = new HashMap<String, Set<UUID>>();
	Map<String, Map<String, Set<UUID>>> shareData = new HashMap<String, Map<String, Set<UUID>>>();
	Map<String, Integer> shareCount = new HashMap<String, Integer>();
	Map<String, Integer> receiveCount = new HashMap<String, Integer>();
	Map<UUID, Integer> readCount = new HashMap<UUID, Integer>();
	Map<UUID, String> secretData = new HashMap<UUID, String>();
	
	int longest = 0;
	
	public void resetStatsAndSystem() {
		createdBy.clear();
		sharedWith.clear();
		receivedBy.clear();
		shareData.clear();
		shareCount.clear();
		receiveCount.clear();
		readCount.clear();
		secretData.clear();
		longest = 0;
	}

	public int getLengthOfLongestSecret() {
		return longest;
	}

	public String getWorstSecretKeeper() {
		// TODO Auto-generated method stub
		int min = Integer.MAX_VALUE;
		String retVal = null;
		for(String key: receiveCount.keySet()) {
			int temp = receiveCount.get(key) - shareCount.get(key);
			if(temp == min) {
				if(key.compareTo(retVal)<0)
					retVal = key;
			}
			if(temp < min) {
				retVal = key;
				min = temp;
			}
		}
		return retVal;
	}

	public String getMostTrustedUser() {
		int max = Integer.MIN_VALUE;
		String retVal = null;
		for(String key: receiveCount.keySet()) {
			if(receiveCount.get(key) == max) {
				if(key.compareTo(retVal)<0)
					retVal = key;
			}
			if(receiveCount.get(key) > max) {
				retVal = key;
				max = receiveCount.get(key);
			}
		}
		return retVal;
	}

	public String getBestKnownSecret() {
		int max = 0;
		UUID maxId = null;
		for (UUID id: readCount.keySet()) {
			if(readCount.get(id) == max) {
				if(secretData.get(id).compareTo(secretData.get(maxId)) < 0)
					maxId = id;
			}	
			if(readCount.get(id) > max) {
				max = readCount.get(id);
				maxId = id;
			}
		}
		return secretData.get(maxId);
	}
	
	public void recordSecretCreation(String userId, UUID scretKey, String secret) {
		if(scretKey != null) {
			Set<UUID> itemsList = createdBy.get(userId);
	
		    // if list does not exist create it
		    if(itemsList == null) {
		         itemsList = new HashSet<UUID>();
		         itemsList.add(scretKey);
		         createdBy.put(userId, itemsList);
		    } else {
		        // add if item is not already in list
		        if(!itemsList.contains(scretKey)) itemsList.add(scretKey);
		    }
		    secretData.put(scretKey, secret);
		}
	}
	
	public void storeLengthOfLongest(String secret) {
		if(secret.length() > longest) {
			longest = secret.length();
		}
	}
	
	public void recordSecretShare(String userId, UUID scretKey, String targetUser) {
		if(!userId.equals(targetUser)) {
			Set<UUID> shareList = sharedWith.get(userId);
			Set<UUID> receivedList = receivedBy.get(targetUser);
			Map<String, Set<UUID>> shareMap = shareData.get(userId);
	
		    // if list does not exist create it
		    if(shareList == null) {
		    	shareList = new HashSet<UUID>();
		    	shareList.add(scretKey);
		        sharedWith.put(userId, shareList);
		    } else {
		        // add if item is not already in list
		        if(!shareList.contains(scretKey)) shareList.add(scretKey);
		    }
		    
		    if(shareMap == null) {
		    	shareList = new HashSet<UUID>();
		    	shareList.add(scretKey);
		    	shareMap = new HashMap<String, Set<UUID>>();
		    	shareMap.put(targetUser, shareList);
		    	shareData.put(userId, shareMap);
		    	int count = receiveCount.containsKey(targetUser) ? receiveCount.get(targetUser): 0;
		    	int rcount = receiveCount.containsKey(userId) ? receiveCount.get(userId): 0;
				int sCount = shareCount.containsKey(userId) ? shareCount.get(userId): 0;
				int dummyCount = shareCount.containsKey(targetUser) ? shareCount.get(targetUser): 0;
				receiveCount.put(targetUser, count+1);
				receiveCount.put(userId, rcount);
				shareCount.put(userId, sCount + 1);
				shareCount.put(targetUser, dummyCount);
		    }
		    else {
		    	Set<UUID> uuidList = shareMap.get(targetUser);
		    	if(uuidList == null) {
		    		shareList = new HashSet<UUID>();
			    	shareList.add(scretKey);
			    	shareMap.put(targetUser, shareList);
			    	int count = receiveCount.containsKey(targetUser) ? receiveCount.get(targetUser): 0;
			    	int rcount = receiveCount.containsKey(userId) ? receiveCount.get(userId): 0;
					int sCount = shareCount.containsKey(userId) ? shareCount.get(userId): 0;
					int dummyCount = shareCount.containsKey(targetUser) ? shareCount.get(targetUser): 0;
					receiveCount.put(targetUser, count+1);
					receiveCount.put(userId, rcount);
					shareCount.put(userId, sCount + 1);
					shareCount.put(targetUser, dummyCount);
		    	}
		    	else {
		    		if (!uuidList.contains(scretKey)) {
		    			uuidList.add(scretKey);
		    			int count = receiveCount.containsKey(targetUser) ? receiveCount.get(targetUser): 0;
		    			int rcount = receiveCount.containsKey(userId) ? receiveCount.get(userId): 0;
		    			int sCount = shareCount.containsKey(userId) ? shareCount.get(userId): 0;
		    			int dummyCount = shareCount.containsKey(targetUser) ? shareCount.get(targetUser): 0;
		    			receiveCount.put(targetUser, count+1);
		    			receiveCount.put(userId, rcount);
		    			shareCount.put(userId, sCount + 1);
		    			shareCount.put(targetUser, dummyCount);
		    		}
		    	}
		    }
		    
		    if (receivedList == null) {
		    	receivedList = new HashSet<UUID>();
		    	receivedList.add(scretKey);
		    	receivedBy.put(targetUser, receivedList);
		    } else {
		    	if(!receivedList.contains(scretKey)) receivedList.add(scretKey);
		    }
		}
	}

	public  boolean canUserReadIt(String targetUser, UUID secretID) {
		Set<UUID> receivedList = receivedBy.get(targetUser);
		Set<UUID> createList = createdBy.get(targetUser);
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
	
	public void recordReadOfSecret(String userId, UUID secretId) {
		Set<UUID> createList = createdBy.get(userId);
		if(createList!=null)
			if(createList.contains(secretId))
				return;
		Set<UUID> readSet = readBy.get(userId);
		if(readSet == null) {
			readSet = new HashSet<UUID>();
			readSet.add(secretId);
			int count = readCount.containsKey(secretId) ? readCount.get(secretId): 0;
			readCount.put(secretId, count+1);
		}
		else {
			if(!readSet.contains(secretId)) {
				readSet.add(secretId);
				int count = readCount.containsKey(secretId) ? readCount.get(secretId): 0;
				readCount.put(secretId, count+1);
			}
		}
		readBy.put(userId, readSet);
	}
	
	public boolean hasUserCreatedIt(String targetUser, UUID secretID) {
		Set<UUID> createList = createdBy.get(targetUser);
		if (createList == null || !createList.contains(secretID)) {
				throw new NotAuthorizedException();
		}
		else {
			return true;
		}
	}
	
	public void recordSecretUnshare(String userId, UUID scretKey, String targetUser) {
		Set<UUID> receivedList = receivedBy.get(targetUser);
		if(receivedList != null)
		    if(receivedList.contains(scretKey))
		    	receivedList.remove(scretKey);
	}
	
	public void debugByPrint() {
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
		System.out.println("SharedData Wala Map");
		for (String key: shareData.keySet()) {
		    System.out.println(key + " " + shareData.get(key).toString());
		}
	}
	
    
}



