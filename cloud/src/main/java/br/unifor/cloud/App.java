package br.unifor.cloud;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class App {

	private JFrame frame;
	private JFrame listFrame;
	private static JList<InstanceData> list;
	private static JList<FileData> fileList;

	private static InstanceManager manager;
	private static BucketManager fileManager;

	public static void main(String[] args) {
		App app = new App();
		app.init();
		new Thread(new FileListRefresher(fileList, fileManager)).start();
		new Thread(new ListRefresher(list, manager)).start();
	}

	public void init() {
		manager = InstanceManager.manager();
		fileManager = BucketManager.manager();
		frame = new JFrame("Cloud");
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

		fileList = new JList<FileData>();
		fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fileList.setLayoutOrientation(JList.VERTICAL);
		fileList.setVisibleRowCount(-1);
		fileList.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));

		updateFileList();

		JScrollPane fileListScroller = new JScrollPane(fileList);
		fileListScroller.setPreferredSize(new Dimension(480, 240));
		panel.add(fileListScroller);

		JPanel filePanelButtons = new JPanel(new GridLayout(3, 1));

		JButton buttonUpload = new JButton("Upload");
		buttonUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File file = browseForFile();
				if (file != null)
					fileManager.uploadFile(file);
			}
		});

		JButton buttonDownload = new JButton("Download");
		buttonDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File directory = browseForDownloadDirectory();
				String key = fileList.getSelectedValue().getName();
				File file = fileManager.downloadFile(key);
				System.out.println("downloading to: " + directory.getAbsolutePath() + "/" + file.getName());
				file.renameTo(new File(directory.getAbsolutePath() + "/" + file.getName()));
			}
		});

		JButton buttonDelete = new JButton("Delete");
		buttonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileManager.deleteFile(fileList.getSelectedValue().getName());
			}
		});

		filePanelButtons.add(buttonUpload);
		filePanelButtons.add(buttonDownload);
		filePanelButtons.add(buttonDelete);

		panel.add(filePanelButtons);

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

	public void updateFileList() {
		int index = fileList.getSelectedIndex();
		DefaultListModel<FileData> model = new DefaultListModel<FileData>();
		List<FileData> files = FileData.fromSummaries(fileManager.listFiles());
		for (FileData data : files) {
			model.addElement(data);
		}
		fileList.setModel(model);
		fileList.setSelectedIndex(index);
		fileList.repaint();
	}

	private File browseForFile() {
		JFileChooser browser = new JFileChooser();
		browser.setDialogTitle("Select file to upload");
		browser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		frame.setEnabled(false);
		browser.setApproveButtonText("Upload");
		File toUpload = null;
		int returnVal = browser.showOpenDialog(frame);
		frame.setEnabled(true);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			toUpload = browser.getSelectedFile();
		} else {
			return null;
		}
		return toUpload;
	}

	private File browseForDownloadDirectory() {
		JFileChooser browser = new JFileChooser();
		browser.setDialogTitle("Select directory to save file in");
		browser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		frame.setEnabled(false);
		browser.setApproveButtonText("Save here");
		File output = null;
		int returnVal = browser.showOpenDialog(frame);
		frame.setEnabled(true);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			output = browser.getSelectedFile();
		} else {
			return null;
		}
		return output;
	}

}
