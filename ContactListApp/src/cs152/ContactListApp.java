package cs152;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ContactListApp extends JFrame{

	private ArrayList<Contact> contactArrayList;
	private JList<Contact> contactList;

	public ContactListApp(){
		super("Contact List");
		this.setSize(600, 600);
		populate(this);
	}


	public void populate(Container c){
		JPanel north = new JPanel();

		contactArrayList = new ArrayList<Contact>();
		contactList = new JList<Contact>(contactListToArray());


		JScrollPane scrollPane = new JScrollPane(contactList);


		north.add(scrollPane);
		c.add(north, BorderLayout.NORTH);



		JPanel south = new JPanel();
		JButton addButton = new JButton("Add");
		addButton.addActionListener(new AddActionListener());
		JButton removeButton = new JButton("Remove");
		removeButton.addActionListener(new RemoveActionListener());
		south.add(addButton);
		south.add(removeButton);

		c.add(south, BorderLayout.SOUTH);

		contactList.addMouseListener( new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(e.getClickCount() == 2){
					int item = contactList.locationToIndex(e.getPoint());
					System.out.println("Double clicked on item" + item);
					Contact c = contactArrayList.get(item);
					new AddContactFrame(c).setVisible(true);
				}
			}
		}
				);


	}


	public void sortContactArrayList(){
		ArrayList<Contact> newList = new ArrayList<Contact>();
		Collections.sort(newList);
	}


	public Contact[] contactListToArray() {
		sortContactArrayList();

		Contact[] arr = new Contact[contactArrayList.size()];
		for(int i = 0; i < arr.length; i++)
			arr[i] = contactArrayList.get(i);
		return arr;
	}



	public class AddActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			AddContactFrame addWindow = new AddContactFrame();
			addWindow.setVisible(true);
			if(addWindow.isSaved()){
				contactArrayList.add(addWindow.getContact());
				contactList.setListData(contactListToArray());
			}
			else
				System.out.println("Contact not saved!");
		}
	}

	public class RemoveActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(contactList.getSelectedIndices().length > 1){
				removeMultipleContacts();
			}
			else if(contactList.getSelectedIndices().length == 1){
				removeSingleContact();
			}
			else
				JOptionPane.showMessageDialog(null, "No contact has been selected!", "No Contact Selected", JOptionPane.INFORMATION_MESSAGE);

		}
	}
	

	public void removeMultipleContacts(){
		int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove " + contactList.getSelectedIndices().length + " contacts?", "Remove Multiple Contacts", JOptionPane.YES_NO_OPTION);
		if(answer == 0){

			/*
			Make the loop correctly remove at the indices!
			
			int[] selectedIndices = contactList.getSelectedIndices();
			for (int i = 0; i < selectedIndices.length; i++) {
				contactArrayList.remove(selectedIndices[i]);
				System.out.println("hello");
				contactList.setListData(contactListToArray());
			}
			*/
			
		}
	}
	

	public void removeSingleContact(){
		int selectedIndex = contactList.getSelectedIndex();
		int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove " + contactArrayList.get(selectedIndex).getName() + "?", "Remove Contact", JOptionPane.YES_NO_OPTION);
		if(answer == 0){
			contactArrayList.remove(selectedIndex);
			contactList.setListData(contactListToArray());
		}
	}


	public static void main(String[] args) {
		ContactListApp app = new ContactListApp();
		app.setVisible(true);
	}


}
