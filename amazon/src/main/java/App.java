
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.RunInstancesRequest;

public class App {

	public static void main(String[] args) {
		App app = new App();
		AmazonEC2 ec2 = app.runInstance();
		System.out.println(ec2.describeInstances().toString());
		System.out.println(ec2.describeInstances().getNextToken());
		System.out.println(ec2.describeInstances().getNextToken());
	}

	public AmazonEC2 runInstance(){
		AWSCredentials credentials = new ProfileCredentialsProvider("default").getCredentials();
        AmazonEC2 ec2 = new AmazonEC2Client(credentials);
        
        ec2.setEndpoint("ec2.us-west-2.amazonaws.com");
        
    	RunInstancesRequest runInstancesRequest = new RunInstancesRequest();
    		        	
    	runInstancesRequest.withImageId("ami-8ca83fec")
	                     .withInstanceType("t2.micro")
	                     .withMinCount(1)
	                     .withMaxCount(1)
	                     .withKeyName("firstKeyPair")
	                     .withSecurityGroups("default");
	  
    	ec2.runInstances(runInstancesRequest);
    	return ec2;
	}
	
	public void desenhar(){
		JFrame frame = new JFrame("np2");
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		Object[] data = { "startada 1", "startada 2" };
		JList list = new JList(data); // data has type Object[]
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(250, 80));
		JLabel startLabel = new JLabel("startadas");
		panel.add(startLabel);
		panel.add(listScroller);
		frame.add(panel);
		Object[] data2 = { "stoppada 1", "stoppada 2" };
		JList list2 = new JList(data2); // data has type Object[]
		list2.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list2.setLayoutOrientation(JList.VERTICAL);
		list2.setVisibleRowCount(-1);
		JScrollPane listScroller2 = new JScrollPane(list2);
		listScroller2.setPreferredSize(new Dimension(250, 80));
		JLabel startLabel2 = new JLabel("stoppadas");
		panel.add(startLabel2);
		panel.add(listScroller2);
		JButton buttonStart = new JButton("start");
		panel.add(buttonStart);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
