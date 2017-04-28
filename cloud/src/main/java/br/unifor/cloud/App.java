package br.unifor.cloud;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class App {

	private JFrame frame;
	private JFrame listFrame;
	private static JList<InstanceData> list;

	private static InstanceManager manager;

	public static void main(String[] args) {
		App app = new App();
		app.init();
		new Thread(new ListRefresher(list, manager)).run();
	}

	public void init() {
		manager = InstanceManager.manager();
		frame = new JFrame("Instances");
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		list = new JList<InstanceData>();
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		list.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		
		updateList();
		
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(480, 240));
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
				InstanceData selected = list.getSelectedValue();
				if (selected != null) {
					String instanceId = selected.getId();
					manager.startInstance(instanceId);
					System.out.println("Starting");
				}
			}
		});
		panelButtons.add(buttonStart);
		JButton buttonStop = new JButton("Stop");
		buttonStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InstanceData selected = list.getSelectedValue();
				if (selected != null) {
					String instanceId = selected.getId();
					manager.stopInstance(instanceId);
					System.out.println("Stopping");
				}
			}
		});
		panelButtons.add(buttonStop);
		JButton buttonTerminate = new JButton("Terminate");
		buttonTerminate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InstanceData selected = list.getSelectedValue();
				if (selected != null) {
					String instanceId = selected.getId();
					manager.terminateInstance(instanceId);
					System.out.println("Terminating");
				}
			}
		});
		panelButtons.add(buttonTerminate);
		panel.add(panelButtons);
		createInstanceFrame();
		frame.add(panel);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
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
				System.out.println(manager.createInstance(InstanceManager.AMAZON_LINUX));
				listFrame.setVisible(false);
				updateList();
				frame.setEnabled(true);
			}
		});
		JButton image2 = new JButton("Redhat Linux");
		image2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(manager.createInstance(InstanceManager.REDHAT_LINUX));
				listFrame.setVisible(false);
				updateList();
				frame.setEnabled(true);
			}
		});
		JButton image3 = new JButton("SUSE Linux");
		image3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(manager.createInstance(InstanceManager.SUSE_LINUX));
				listFrame.setVisible(false);
				updateList();
				frame.setEnabled(true);
			}
		});
		JButton image4 = new JButton("Ubuntu Server");
		image4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(manager.createInstance(InstanceManager.UBUNTU_SERVER));
				listFrame.setVisible(false);
				updateList();
				frame.setEnabled(true);
			}
		});
		JButton image5 = new JButton("Windows Server");
		image5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(manager.createInstance(InstanceManager.WINDOWS_SERVER));
				listFrame.setVisible(false);
				updateList();
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
		int index = list.getSelectedIndex();
		DefaultListModel<InstanceData> model = new DefaultListModel<InstanceData>();
		List<InstanceData> instanceList = InstanceData.fromInstances(manager.getInstances());
		for (InstanceData data : instanceList) {
			model.addElement(data);
		}
		list.setModel(model);
		list.setSelectedIndex(index);
		list.repaint();
	}

}
