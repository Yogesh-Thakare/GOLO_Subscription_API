package com.yogesh.model;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadSafeDatabase {

    private static ThreadSafeDatabase handleTODB = null;

    private HashMap<String, ConcurrentHashMap<String,String>> CACHE = new HashMap<>();

    private ThreadSafeDatabase() {

    }

    public static ThreadSafeDatabase getHandle() {
        if(handleTODB == null) {
            handleTODB = new ThreadSafeDatabase();
        }

        return handleTODB;
    }

	public HashMap<String, ConcurrentHashMap<String, String>> getCACHE() {
		return CACHE;
	}

	public void setCACHE(HashMap<String, ConcurrentHashMap<String, String>> cACHE) {
		CACHE = cACHE;
	}

    
}
