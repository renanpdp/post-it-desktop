package app.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import app.model.User;
import app.service.UserService;
import app.service.impl.UserServiceImpl;
import app.util.ResourcesUtil;

public class CreateUserFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private UserService userService;
	
	private MainFrame mainFrame;
	
	private JButton cancelButton;
	private JLabel passwordLabel;
	private JPasswordField passwordTextField;
	private JButton saveButton;
	private JLabel usernameLabel;
	private JTextField usernameTextField;
	
	private Boolean edit;
	private User user;
	
	public CreateUserFrame(final MainFrame mainFrame) {
		this.edit = false;
		this.mainFrame = mainFrame;
		this.setTitle("Criar novo usuário");
		
		userService = new UserServiceImpl();
		
		initComponents();
	}
	
	public CreateUserFrame(final MainFrame mainFrame, final User user) {
		this.edit = true;
		this.user = user;
		this.mainFrame = mainFrame;
		this.setTitle("Editar usuário");
		
		userService = new UserServiceImpl();
		
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

		usernameLabel = new JLabel();
		usernameTextField = new JTextField();
		passwordLabel = new JLabel();
		passwordTextField = new JPasswordField();
		saveButton = new JButton();
		cancelButton = new JButton();

		getContentPane().setLayout(null);

		usernameLabel.setText("Nome de usuário:");
		getContentPane().add(usernameLabel);
		usernameLabel.setBounds(10, 10, 90, 14);
		
		if (edit) {
			usernameTextField.setText(user.getUsername());
		}
		getContentPane().add(usernameTextField);
		usernameTextField.setBounds(100, 10, 160, 20);

		passwordLabel.setText("Senha:");
		getContentPane().add(passwordLabel);
		passwordLabel.setBounds(10, 40, 34, 14);
		
		getContentPane().add(passwordTextField);
		passwordTextField.setBounds(100, 40, 160, 20);

		saveButton.setText("Salvar");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				saveButtonActionPerformed(evt);
			}
		});
		getContentPane().add(saveButton);
		saveButton.setBounds(200, 70, 63, 23);

		cancelButton.setText("Cancelar");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				cancelButtonActionPerformed(evt);
			}
		});
		getContentPane().add(cancelButton);
		cancelButton.setBounds(110, 70, 80, 23);
		
		if (edit) {
			this.setIconImage(ResourcesUtil.getImage("user_edit.png"));
		} else {
			this.setIconImage(ResourcesUtil.getImage("user_add.png"));
		}
		this.setSize(280, 130);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	
	private void saveButtonActionPerformed(final ActionEvent evt) {
		if (usernameTextField.getText() != null && !usernameTextField.getText().equals("")) {
			
			final char[] passwordArray = passwordTextField.getPassword();
			String password = "";
			for (int i=0; i<passwordArray.length; i++) {
				password += passwordArray[i]+"";
				passwordArray[i] = 0;
			}
			
			if (edit) {
				this.user.setUsername(usernameTextField.getText());
				this.user.setPassword(password);
				
				userService.update(this.user);
				JOptionPane.showMessageDialog(this, "Usuário editado com sucesso!", null, JOptionPane.INFORMATION_MESSAGE);
			} else {
				final User newUser = new User();
				newUser.setUsername(usernameTextField.getText());
				newUser.setPassword(password);
				
				userService.create(newUser);
				JOptionPane.showMessageDialog(this, "Usuário criado com sucesso!", null, JOptionPane.INFORMATION_MESSAGE);
			}
			
			refreshMainFrame();
			this.dispose();
		} else {
			JOptionPane.showMessageDialog(this, "Campo de nome de usuário não preenchido!", null, JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void cancelButtonActionPerformed(final ActionEvent evt) {
		this.dispose();
	}
	
	private void refreshMainFrame(){
		mainFrame.setUsers(userService.findAll());
		mainFrame.refreshUsersCombo(mainFrame.getUsersComboBox().getSelectedItem());
	}

}
