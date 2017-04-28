package br.unifor.cloud;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public class ListRefresher implements Runnable {

	private JList<InstanceData>  list;
	private InstanceManager manager;

	public ListRefresher(JList<InstanceData> list, InstanceManager manager) {
		this.list = list;
		this.manager = manager;
	}

	public void run() {
		while (true) {
			updateList();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
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