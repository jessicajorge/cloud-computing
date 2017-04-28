package br.unifor.cloud;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class App {
	
	private JFrame frame;
	private JFrame listFrame;
	private JList<InstanceData> list;

	private static InstanceManager manager;

	public static void main(String[] args) {
		App app = new App();
		manager = InstanceManager.manager();
		app.desenhar();
	}

	public void desenhar() {
		frame = new JFrame("Instances");
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		list = new JList<InstanceData>();
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		updateList();
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(300, 280));
		panel.add(listScroller);
		JPanel panelButtons = new JPanel(new GridLayout(2, 2));
		JButton buttonNew = new JButton("Create");
		buttonNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showImageList();
				frame.setEnabled(false);
			}
		});
		panelButtons.add(buttonNew);
		JButton buttonStart = new JButton("Start");
		buttonStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO
				System.out.println();
			}
		});
		panelButtons.add(buttonStart);
		JButton buttonStop = new JButton("Stop");
		buttonStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO
				System.out.println();
			}
		});
		panelButtons.add(buttonStop);
		JButton buttonTerminate = new JButton("Terminate");
		buttonTerminate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO
				System.out.println();
			}
		});
		panelButtons.add(buttonTerminate);
		panel.add(panelButtons);
		createInstanceFrame();
		frame.add(panel);
		frame.setLocation(300, 300);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void showImageList() {
		listFrame.setLocationRelativeTo(frame);
		listFrame.setVisible(true);
	}
	
	public void createInstanceFrame() {
		listFrame = new JFrame("Select image");
		JPanel listPanel = new JPanel();
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.PAGE_AXIS));
		JButton image1 = new JButton("Amazon Linux");
		image1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO
				System.out.println(InstanceManager.AMAZON_LINUX);
				listFrame.setVisible(false);
				frame.setEnabled(true);
			}
		});
		JButton image2 = new JButton("Redhat Linux");
		image2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO
				System.out.println(InstanceManager.REDHAT_LINUX);
				listFrame.setVisible(false);
				frame.setEnabled(true);
			}
		});
		JButton image3 = new JButton("SUSE Linux");
		image3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO
				System.out.println(InstanceManager.SUSE_LINUX);
				listFrame.setVisible(false);
				frame.setEnabled(true);
			}
		});
		JButton image4 = new JButton("Ubuntu Server");
		image4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO
				System.out.println(InstanceManager.UBUNTU_SERVER);
				listFrame.setVisible(false);
				frame.setEnabled(true);
			}
		});
		JButton image5 = new JButton("Windows Server");
		image5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO
				System.out.println(InstanceManager.WINDOWS_SERVER);
				listFrame.setVisible(false);
				frame.setEnabled(true);
			}
		});
		listPanel.add(image1);
		listPanel.add(image2);
		listPanel.add(image3);
		listPanel.add(image4);
		listPanel.add(image5);
		listFrame.add(listPanel);
		listFrame.setLocationRelativeTo(frame);
		listFrame.pack();
		listFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void updateList() {
		DefaultListModel<InstanceData> model = new DefaultListModel<InstanceData>();
		for (InstanceData data : InstanceData.fromInstances(manager.getInstances())) {
			model.addElement(data);
		}
		list.setModel(model);
	}
}
