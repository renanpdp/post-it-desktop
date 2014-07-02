package app.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import app.model.PostIt;
import app.model.User;
import app.service.PostItService;
import app.service.UserService;
import app.service.impl.PostItServiceImpl;
import app.service.impl.UserServiceImpl;
import app.util.ResourcesUtil;
import app.view.panel.PostItPanel;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private PostItService postItService;
	private UserService userService;
	
	private List<PostIt> postIts;
	private List<User> users;
	
	Object comboItems[];
	
	private JPanel mainPanel;
	private JButton createUserButton;
	private JLabel usersLabel;
	private JComboBox<Object> usersComboBox;
	private JButton deleteUserButton;
	private JButton editUserButton;
	
	private JPanel postItsPanel;
	private JButton createPostItButton;
	private JScrollPane postItsScrollPane;
	private JPanel postItsInsidePanel;
	
	private final Integer WIDTH = 290;
	private final Integer HEIGHT = 290;
	private final Integer GAP = 20;

	public MainFrame() {
		postItService = new PostItServiceImpl();
		userService = new UserServiceImpl();
		
		postIts = postItService.findAll();
		users = userService.findAll();
		initComponents();
		populatePostItsPanel();
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
		
		try {
		     GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, ResourcesUtil.getFile("font/Mf Delicate Little Flower.ttf")));
		} catch (IOException e) {
		     e.printStackTrace();
		} catch (FontFormatException e) {
		     e.printStackTrace();
		}

		mainPanel = new JPanel();
		createUserButton = new JButton();
		usersLabel = new JLabel();
		usersComboBox = new JComboBox<Object>();
		deleteUserButton = new JButton();
		editUserButton = new JButton();
		
		postItsPanel = new JPanel();
		createPostItButton = new JButton();
		postItsScrollPane = new JScrollPane();
		postItsInsidePanel = new JPanel();

		this.getContentPane().setLayout(null);

		postItsPanel.setBorder(BorderFactory.createTitledBorder("Post-its"));
		postItsPanel.setToolTipText("");
		postItsPanel.setLayout(null);

		createPostItButton.setText("Novo post-it");
		if (users.isEmpty()) {
			createPostItButton.setEnabled(false);
		}
		createPostItButton.setIcon(new ImageIcon(ResourcesUtil.getImage("note_add.png")));
		createPostItButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				createPostItButtonActionPerformed(evt);
			}
		});
		postItsPanel.add(createPostItButton);
		createPostItButton.setBounds(10, 20, 115, 23);
		
		postItsInsidePanel.setLayout(null);
		postItsInsidePanel.setBackground(Color.WHITE);
		postItsInsidePanel.setPreferredSize(new Dimension(639, getPostItsInsidePanelHeight()));
		postItsScrollPane.setViewportView(postItsInsidePanel);

		postItsPanel.add(postItsScrollPane);
		postItsScrollPane.setBounds(10, 50, 658, 650);

		this.getContentPane().add(postItsPanel);
		postItsPanel.setBounds(0, 100, 678, 710);

		mainPanel.setLayout(null);

		usersLabel.setText("Usuário:");
		mainPanel.add(usersLabel);
		usersLabel.setBounds(10, 60, 50, 14);
		
		refreshUsersCombo(null);
		usersComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				usersComboBoxActionPerformed(evt);
			}
		});
		mainPanel.add(usersComboBox);
		usersComboBox.setBounds(60, 60, 140, 20);

		createUserButton.setText("Novo usuário");
		createUserButton.setIcon(new ImageIcon(ResourcesUtil.getImage("user_add.png")));
		createUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				createUserButtonActionPerformed(evt);
			}
		});
		mainPanel.add(createUserButton);
		createUserButton.setBounds(10, 20, 115, 23);

		deleteUserButton.setText("Excluir usuário");
		deleteUserButton.setIcon(new ImageIcon(ResourcesUtil.getImage("user_delete.png")));
		deleteUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				deleteUserButtonActionPerformed(evt);
			}
		});
		deleteUserButton.setEnabled(false);
		mainPanel.add(deleteUserButton);
		deleteUserButton.setBounds(220, 60, 125, 23);

		editUserButton.setText("Editar usuário");
		editUserButton.setIcon(new ImageIcon(ResourcesUtil.getImage("user_edit.png")));
		editUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				editUserButtonActionPerformed(evt);
			}
		});
		editUserButton.setEnabled(false);
		mainPanel.add(editUserButton);
		editUserButton.setBounds(355, 60, 120, 23);

		this.getContentPane().add(mainPanel);
		mainPanel.setBounds(0, 0, 830, 100);
		
		this.setIconImage(ResourcesUtil.getImage("note.png"));
		this.setTitle("Post-its");
		this.setSize(685, 838);
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}
	
	private void usersComboBoxActionPerformed(final ActionEvent evt) {
		final Object selectedItem = usersComboBox.getSelectedItem();
		if (selectedItem.equals("Todos")) {
			postIts = postItService.findAll();
			editUserButton.setEnabled(false);
			deleteUserButton.setEnabled(false);
		} else {
			postIts = postItService.findByUser((User) selectedItem);
			editUserButton.setEnabled(true);
			deleteUserButton.setEnabled(true);
		}
		populatePostItsPanel();
	}
	
	private void createPostItButtonActionPerformed(final ActionEvent evt) {
		new CreatePostItFrame(this).setVisible(true);
	}
	
	private void createUserButtonActionPerformed(final ActionEvent evt) {
		new CreateUserFrame(this).setVisible(true);
	}
	
	private void deleteUserButtonActionPerformed(final ActionEvent evt) {
		final int answer = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir este usuário?", null, JOptionPane.YES_NO_OPTION);
		if (answer == 0) {
			//Answer is yes.
			final User selectedUser = (User) usersComboBox.getSelectedItem();
			final List<PostIt> postIts = postItService.findByUser(selectedUser);
			
			if (postIts.isEmpty()) {
				userService.delete((User) usersComboBox.getSelectedItem());
				JOptionPane.showMessageDialog(this, "Usuário excluído com sucesso!", null, JOptionPane.INFORMATION_MESSAGE);
				
				users = userService.findAll();
				refreshUsersCombo(null);
			} else {
				JOptionPane.showMessageDialog(this, "Exclua todos os post-its do usuário para poder exlcuí-lo!", null, JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	private void editUserButtonActionPerformed(final ActionEvent evt) {
		new CreateUserFrame(this, (User) usersComboBox.getSelectedItem()).setVisible(true);
	}
	
	public void populatePostItsPanel() {
		postItsInsidePanel.removeAll();
		
		int i = 1;
		for (final PostIt postIt : postIts) {
			PostItPanel postItPanel = new PostItPanel(this);
			postItsInsidePanel.add(postItPanel);
			postItPanel.setPostIt(postIt);
			postItPanel.setBounds(getBounds(i));
			i++;
		}
		
		postItsInsidePanel.setPreferredSize(new Dimension(639, getPostItsInsidePanelHeight()));
		postItsInsidePanel.repaint();
	}
	
	private Rectangle getBounds(final Integer position) {
		return new Rectangle(GAP + (WIDTH+GAP)*((position-1)%2), GAP + (HEIGHT+GAP)*((position-1)/2), WIDTH, HEIGHT);
	}
	
	private Integer getPostItsInsidePanelHeight() {
		return ((((postIts.size()-1)/2)+1)*(HEIGHT+GAP)) + GAP;
	}
	
	public void refreshUsersCombo(final Object object) {
		if (users.isEmpty()) {
			createPostItButton.setEnabled(false);
		} else {
			createPostItButton.setEnabled(true);
		}
	
		comboItems = new Object[users.size()+1];
		comboItems[0] = "Todos";
		int i=1;
		for (final User user : users) {
			comboItems[i] = user;
			i++;
		}
		usersComboBox.setModel(new DefaultComboBoxModel<Object>(comboItems));
		if (object != null) {
			usersComboBox.setSelectedItem(object);
			if (object instanceof User) {
				postIts = postItService.findByUser((User) object);
				editUserButton.setEnabled(true);
				deleteUserButton.setEnabled(true);
			} else {
				postIts = postItService.findAll();
				editUserButton.setEnabled(false);
				deleteUserButton.setEnabled(false);
			}
		} else {
			postIts = postItService.findAll();
			editUserButton.setEnabled(false);
			deleteUserButton.setEnabled(false);
		}
		populatePostItsPanel();
	}
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(final List<User> users) {
		this.users = users;
		refreshUsersCombo(null);
	}
	
	public List<PostIt> getPostIts() {
		return postIts;
	}

	public void setPostIts(final List<PostIt> postIts) {
		this.postIts = postIts;
		populatePostItsPanel();
	}
	
	public JPanel getPostItsInsidePanel() {
		return postItsInsidePanel;
	}

	public void setPostItsInsidePanel(JPanel postItsInsidePanel) {
		this.postItsInsidePanel = postItsInsidePanel;
	}
	
	public JComboBox<Object> getUsersComboBox() {
		return usersComboBox;
	}

	public void setUsersComboBox(JComboBox<Object> usersComboBox) {
		this.usersComboBox = usersComboBox;
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MainFrame().setVisible(true);
			}
		});
	}

}
