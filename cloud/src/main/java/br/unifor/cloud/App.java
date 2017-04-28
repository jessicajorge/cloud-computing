package br.unifor.cloud;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class App {

	private static InstanceManager manager;

	public static void main(String[] args) {
		App app = new App();
		manager = InstanceManager.manager();
		app.desenhar();
	}

	public void desenhar() {
		JFrame frame = new JFrame("Instances");
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		List<InstanceData> data = InstanceData.fromInstances(manager.getInstances());
		JList list = new JList(data.toArray());
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(300, 240));
		panel.add(listScroller);
		JPanel panelButtons = new JPanel(new GridLayout(2, 2));
		JButton buttonNew = new JButton("Create");
		panelButtons.add(buttonNew);
		JButton buttonStart = new JButton("Start");
		panelButtons.add(buttonStart);
		JButton buttonStop = new JButton("Stop");
		panelButtons.add(buttonStop);
		JButton buttonTerminate = new JButton("Terminate");
		panelButtons.add(buttonTerminate);
		panel.add(panelButtons);
		frame.add(panel);
		frame.setLocation(300, 300);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
