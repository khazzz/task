// Copyright 2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License"). You may
// not use this file except in compliance with the License. A copy of the
// License is located at
//
//	  http://aws.amazon.com/apache2.0/
//
// or in the "license" file accompanying this file. This file is distributed
// on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
// express or implied. See the License for the specific language governing
// permissions and limitations under the License.


package com.amazonaws.lambda.task.manager;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;


public class DynamoDBManager {

    private static volatile DynamoDBManager instance;

    private static DynamoDBMapper mapper;
    
    private boolean isLocal = true;

    private DynamoDBManager() {

    		if(!isLocal) {
	        AmazonDynamoDBClient client = new AmazonDynamoDBClient();
	        client.setRegion(Region.getRegion(Regions.US_EAST_1));
	        mapper = new DynamoDBMapper(client);
    		} else {
    			// The secret key doesn't need to be valid, DynamoDB Local doesn't care.
	    	    	AWSCredentials credentials = new BasicAWSCredentials("AKIAIA6SMMY67HXL6BUA", "bogus");
	    	    	AmazonDynamoDBClient client = new AmazonDynamoDBClient(credentials);
	    	
	    	    	// Make sure you use the same port as you configured DynamoDB Local to bind to.
	    	    	client.setEndpoint("http://localhost:8000");
	
	    	    	// Sign requests for the "local" region to read data written by the toolkit.
	    	    	client.setSignerRegionOverride("local");
	    	    	
	    	    	mapper = new DynamoDBMapper(client);
    		}
    }

    public static DynamoDBManager instance() {

        if (instance == null) {
            synchronized(DynamoDBManager.class) {
                if (instance == null)
                    instance = new DynamoDBManager();
            }
        }

        return instance;
    }

    public static DynamoDBMapper mapper() {

        DynamoDBManager manager = instance();
        return manager.mapper;
    }

}
