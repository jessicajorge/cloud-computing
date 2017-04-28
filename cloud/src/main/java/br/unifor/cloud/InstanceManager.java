package br.unifor.cloud;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.StartInstancesRequest;
import com.amazonaws.services.ec2.model.StopInstancesRequest;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;

public class InstanceManager {
	
	public static String AMAZON_LINUX = "ami-4836a428";
	public static String REDHAT_LINUX = "ami-6f68cf0f";
	public static String SUSE_LINUX = "ami-e4a30084";
	public static String UBUNTU_SERVER = "ami-efd0428f";
	public static String WINDOWS_SERVER = "ami-9b29b9fb";

	private static InstanceManager manager;
	private static AmazonEC2 ec2;

	protected InstanceManager() {
		AWSCredentials credentials = new ProfileCredentialsProvider("default").getCredentials();
		ec2 = new AmazonEC2Client(credentials);
	}

	public static InstanceManager manager() {
		if (manager == null) {
			manager = new InstanceManager();
		}
		return manager;
	}

	public List<Instance> getInstances() {
		List<Instance> instances = new ArrayList<Instance>();
		boolean done = false;
		while (!done) {
			DescribeInstancesRequest request = new DescribeInstancesRequest();
			DescribeInstancesResult response = ec2.describeInstances(request);
			for (Reservation reservation : response.getReservations()) {
				for (Instance instance : reservation.getInstances()) {
					instances.add(instance);
				}
			}
			request.setNextToken(response.getNextToken());
			if (response.getNextToken() == null) {
				done = true;
			}
		}
		return instances;
	}

	public String createInstance(String imageId) {
		ec2.setEndpoint("ec2.us-west-2.amazonaws.com");
		RunInstancesRequest runInstancesRequest = new RunInstancesRequest();
		/*
		runInstancesRequest.withImageId("ami-8ca83fec").withInstanceType("t2.micro").withMinCount(1).withMaxCount(1)
				.withKeyName("firstKeyPair").withSecurityGroups("default");
		*/
		runInstancesRequest.withImageId(imageId).withInstanceType("t2.micro").withMinCount(1).withMaxCount(1)
		.withKeyName("firstKeyPair").withSecurityGroups("default");
		String instanceId = ec2.runInstances(runInstancesRequest).getReservation().getReservationId();
		return instanceId;
	}

	public void startInstance(String id) {
		StartInstancesRequest request = new StartInstancesRequest().withInstanceIds(id);
		ec2.startInstances(request);
	}

	public void stopInstance(String id) {
		StopInstancesRequest request = new StopInstancesRequest().withInstanceIds(id);
		ec2.stopInstances(request);
	}

	public void terminateInstance(String id) {
		TerminateInstancesRequest request = new TerminateInstancesRequest().withInstanceIds(id);
		ec2.terminateInstances(request);
	}

}
