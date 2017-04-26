package t411np2;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class App {

	public static void main(String[] args) {
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
		panel.add(listScroller);
		frame.add(panel);
		Object[] data2 = { "stoppada 1", "stoppada 2" };
		JList list2 = new JList(data2); // data has type Object[]
		list2.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list2.setLayoutOrientation(JList.VERTICAL);
		list2.setVisibleRowCount(-1);
		JScrollPane listScroller2 = new JScrollPane(list2);
		listScroller2.setPreferredSize(new Dimension(250, 80));
		panel.add(listScroller2);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
