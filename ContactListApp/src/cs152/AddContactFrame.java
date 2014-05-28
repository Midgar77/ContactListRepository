package cs152;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class AddContactFrame extends JDialog{

	private static final long serialVersionUID = 1L;
	private String name;
	private String email;
	private String number;
	private String notes;
	private JTextField[] textFields;
	private JTextArea notesArea;
	private Contact currentContact;
	private boolean saved;

	public AddContactFrame(){
		super();
		this.setSize(600, 600);
		this.setModal(true);
		currentContact = new Contact("","","","");
		saved = false;
		populate(this);
	}
	
	public AddContactFrame(Contact contact){
		super();
		this.setSize(600, 600);
		this.setModal(true);
		currentContact = new Contact(contact);
		saved = false;
		populate(this);
		
	}

	
	public void populate(Container c){

		

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
		cancel.addActionListener(new CancelActionListener(this));

		JButton save = new JButton("Save");
		save.addActionListener(new SaveActionListener());	


		south.add(cancel);
		south.add(save);
	}
	
	
	public boolean isSaved(){
		return saved;
	}
	
	
	public Contact getContact(){
		name = textFields[0].getText();
		email = textFields[1].getText();
		number = textFields[2].getText();
		notes = notesArea.getText();

		return new Contact(name, email, number, notes);
	}
	
	
	public class CancelActionListener implements ActionListener{

		Container parent;
		public CancelActionListener(Container parent){
			this.parent = parent;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel?", "Cancel Application", JOptionPane.YES_NO_OPTION);
			if(answer == 0){
				parent.setVisible(false);
			}			
		}
		
	}
	
	
	
	public class SaveActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			saved = true;
			JOptionPane.showMessageDialog(null, "Contact has been saved!", "Save Contact", JOptionPane.INFORMATION_MESSAGE);
		}

	}
	
	
	

}
