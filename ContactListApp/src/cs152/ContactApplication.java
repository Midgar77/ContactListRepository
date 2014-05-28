package cs152;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

public class ContactApplication extends JFrame{

	private static final long serialVersionUID = 1L;
	private String name;
	private String email;
	private String number;
	private String notes;
	private JTextField[] textFields;
	private JTextArea notesArea;
	private Contact currentContact;


	public ContactApplication(){
		super("Contact Application");
		this.setSize(400, 300);
		populate(getContentPane());
		
		
	}



	public void loadContact(){

		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream("contactApp.txt"));
			currentContact = (Contact)ois.readObject();
			JOptionPane.showMessageDialog(null, "Contact has laoded successfully!", "Contact Loaded Successfully", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error loading contact: " + ex.getMessage(), "Error loading contact", JOptionPane.ERROR_MESSAGE);
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Contact not found: " + ex.getMessage(), "Error loading contact", JOptionPane.ERROR_MESSAGE);		
		} finally {
			try {
				if (ois != null) ois.close();
			} catch (IOException ex) {
				System.out.println("Error closing file: "+ex.getMessage());
			}
		}

	}


	public void populate(Container c){
		if(!new File("contactApp.txt").isFile())
			currentContact = new Contact("","","","");
		else	
			loadContact();


		JPanel north = new JPanel(new SpringLayout());
		c.add(north, BorderLayout.NORTH);


		String[] labels = {"Name: ", "Email: ", "Phone Number: "};
		int rowLength = labels.length;

		textFields = new JTextField[3];


		for (int i = 0; i < rowLength; i++) {
			JLabel l = new JLabel(labels[i], JLabel.TRAILING);
			north.add(l);
			textFields[i] = new JTextField(20);
			l.setLabelFor(textFields[i]);
			north.add(textFields[i]);
		}



		/*SpringUtlities class taken from 
		 * "http://docs.oracle.com/javase/tutorial/uiswing/examples/layout/SpringGridProject/src/layout/SpringUtilities.java
		 */
		SpringUtilities.makeCompactGrid(north, rowLength, 2, 6, 6, 6, 6);       


		JPanel center = new JPanel(new SpringLayout());
		c.add(center, BorderLayout.CENTER);

		JLabel notes = new JLabel("Notes: ", JLabel.TRAILING);
		center.add(notes);
		notesArea = new JTextArea(3,20);
		JScrollPane scrollPane = new JScrollPane(notesArea);
		notes.setLabelFor(notesArea);
		center.add(scrollPane);

		SpringUtilities.makeCompactGrid(center, 1, 2, 6, 6, 6, 6);



		//Fill the information from currentContact into the textFields
		textFields[0].setText(currentContact.getName());
		textFields[1].setText(currentContact.getEmail());
		textFields[2].setText(currentContact.getPhoneNumber());
		notesArea.setText(currentContact.getNotes());



		JPanel south = new JPanel();
		c.add(south, BorderLayout.SOUTH);
		south.setLayout(new FlowLayout());

		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new CancelActionListener());

		JButton save = new JButton("Save");
		save.addActionListener(new SaveActionListener());	


		south.add(cancel);
		south.add(save);
	}




	public Contact getContact(){
		name = textFields[0].getText();
		email = textFields[1].getText();
		number = textFields[2].getText();
		notes = notesArea.getText();

		return new Contact(name, email, number, notes);
	}


	public void setContact(Contact c){
		textFields[0].setText(c.getName());
		textFields[1].setText(c.getEmail());
		textFields[2].setText(c.getPhoneNumber());
		notesArea.setText(c.getNotes());

	}

	
	

	public class CancelActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel?", "Cancel Application", JOptionPane.YES_NO_OPTION);
			if(answer == 0)
				System.exit(0);		
		}

	}
	
	
	
	public class SaveActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {


			ObjectOutputStream oos = null;
			try {
				oos = new ObjectOutputStream(new FileOutputStream("contactApp.txt"));
				oos.writeObject(getContact());
				JOptionPane.showMessageDialog(null, "Contact has been saved!", "Save Contact", JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error Writing File: " + ex.getMessage(), "Error Saving!", JOptionPane.ERROR_MESSAGE);
			} finally {
				try {
					if (oos != null) oos.close();
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(null, "Error Closing File: " + ex.getMessage(), "Error Closing", JOptionPane.ERROR_MESSAGE);
				}
			}

		}

	}


	
public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		ContactApplication app = new ContactApplication();
		app.setVisible(true);
	}


}
