package br.unifor.cloud;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public class FileListRefresher implements Runnable {

	private JList<FileData> list;
	private BucketManager manager;

	public FileListRefresher(JList<FileData> list, BucketManager manager) {
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
		DefaultListModel<FileData> model = new DefaultListModel<FileData>();
		List<FileData> fileList = FileData.fromSummaries(manager.listFiles());
		for (FileData data : fileList) {
			model.addElement(data);
		}
		list.setModel(model);
		list.setSelectedIndex(index);
		list.repaint();
	}

}
