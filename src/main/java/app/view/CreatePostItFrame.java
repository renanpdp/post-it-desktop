package app.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import app.model.Color;
import app.model.PostIt;
import app.model.User;
import app.service.PostItService;
import app.service.UserService;
import app.service.impl.PostItServiceImpl;
import app.service.impl.UserServiceImpl;
import app.util.ResourcesUtil;
import app.util.Util;

public class CreatePostItFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private PostItService postItService;
	private UserService userService;
	
	private MainFrame mainFrame;
	
	private List<User> users;
	
	private JButton cancelButton;
	private JComboBox<Color> colorComboBox;
	private JLabel colorLabel;
	private JScrollPane scrollPane;
	private JButton saveButton;
	private JLabel textLabel;
	private JTextArea textTextArea;
	private JComboBox<User> userComboBox;
	private JLabel userLabel;
	
	private Boolean edit;
	private PostIt postIt;

	public CreatePostItFrame(final MainFrame mainFrame) {
		this.edit = false;
		this.mainFrame = mainFrame;
		this.setTitle("Criar novo post-it");
		
		postItService = new PostItServiceImpl();
		userService = new UserServiceImpl();
		
		users = userService.findAll();
		initComponents();
	}
	
	public CreatePostItFrame(final MainFrame mainFrame, final PostIt postIt) {
		this.edit = true;
		this.postIt = postIt;
		this.mainFrame = mainFrame;
		this.setTitle("Editar post-it");
		
		postItService = new PostItServiceImpl();
		userService = new UserServiceImpl();
		
		users = userService.findAll();
		initComponents();
	}

	private void initComponents() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
		} catch (UnsupportedLookAndFeelException ex) {
			Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
		}

		userLabel = new JLabel();
		colorLabel = new JLabel();
		userComboBox = new JComboBox<User>();
		colorComboBox = new JComboBox<Color>();
		textLabel = new JLabel();
		scrollPane = new JScrollPane();
		textTextArea = new JTextArea();
		saveButton = new JButton();
		cancelButton = new JButton();

		getContentPane().setLayout(null);

		userLabel.setText("Usuário:");
		getContentPane().add(userLabel);
		userLabel.setBounds(10, 10, 40, 14);

		colorLabel.setText("Cor:");
		getContentPane().add(colorLabel);
		colorLabel.setBounds(10, 40, 30, 14);

		final User[] comboUsers = new User[users.size()];
		int i=0;
		for (final User user : users) {
			comboUsers[i] = user;
			i++;
		}
		userComboBox.setModel(new DefaultComboBoxModel<User>(comboUsers));
		if (edit) {
			userComboBox.setSelectedItem(postIt.getUser());
			userComboBox.setEnabled(false);
		} else {
			final Object object = mainFrame.getUsersComboBox().getSelectedItem();
			if (object instanceof User) {
				userComboBox.setSelectedItem((User) object);
			}
		}
		getContentPane().add(userComboBox);
		userComboBox.setBounds(60, 10, 130, 20);
		
		final Color[] comboColors = new Color[3];
		comboColors[0] = Color.GREEN;
		comboColors[1] = Color.PINK;
		comboColors[2] = Color.PURPLE;
		colorComboBox.setModel(new DefaultComboBoxModel<Color>(comboColors));
		if (edit) {
			colorComboBox.setSelectedItem(postIt.getColor());
		}
		getContentPane().add(colorComboBox);
		colorComboBox.setBounds(60, 40, 130, 20);

		textLabel.setText("Texto:");
		getContentPane().add(textLabel);
		textLabel.setBounds(10, 70, 32, 14);

		textTextArea.setColumns(20);
		textTextArea.setRows(5);
		if (edit) {
			textTextArea.setText(postIt.getText());
		}
		scrollPane.setViewportView(textTextArea);

		getContentPane().add(scrollPane);
		scrollPane.setBounds(60, 70, 240, 210);

		saveButton.setText("Salvar");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				saveButtonActionPerformed(evt);
			}
		});
		getContentPane().add(saveButton);
		saveButton.setBounds(240, 290, 63, 23);

		cancelButton.setText("Cancelar");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				cancelButtonActionPerformed(evt);
			}
		});
		getContentPane().add(cancelButton);
		cancelButton.setBounds(150, 290, 80, 23);
		
		if (edit) {
			this.setIconImage(ResourcesUtil.getImage("note_edit.png"));
		} else {
			this.setIconImage(ResourcesUtil.getImage("note_add.png"));
		}
		this.setSize(325, 350);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	
	private void saveButtonActionPerformed(final ActionEvent evt) {
		if (textTextArea.getText() != null && !textTextArea.getText().equals("")) {
			
			if (edit) {
				this.postIt.setColor((Color) colorComboBox.getSelectedItem());
				this.postIt.setText(textTextArea.getText());
				
				postItService.update(this.postIt);
				JOptionPane.showMessageDialog(this, "Post-it editado com sucesso!", null, JOptionPane.INFORMATION_MESSAGE);
			} else {
				final PostIt newPostIt = new PostIt();
				newPostIt.setUser((User) userComboBox.getSelectedItem());
				newPostIt.setColor((Color) colorComboBox.getSelectedItem());
				newPostIt.setDateCreated(Util.getCurrentTime());
				newPostIt.setText(textTextArea.getText());
				
				postItService.create(newPostIt);
				JOptionPane.showMessageDialog(this, "Post-it criado com sucesso!", null, JOptionPane.INFORMATION_MESSAGE);
			}
			
			refreshMainFrame();
			this.dispose();
			
		} else {
			JOptionPane.showMessageDialog(this, "Campo de texto não preenchido!", null, JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void cancelButtonActionPerformed(final ActionEvent evt) {
		this.dispose();
	}
	
	private void refreshMainFrame(){
		mainFrame.refreshUsersCombo(mainFrame.getUsersComboBox().getSelectedItem());
	}
	
}
