package app.view.panel;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import app.model.PostIt;
import app.service.PostItService;
import app.service.impl.PostItServiceImpl;
import app.util.ResourcesUtil;
import app.view.CreatePostItFrame;
import app.view.MainFrame;

public class PostItPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private PostItService postItService;
	
	private MainFrame mainFrame;
	
	private PostIt postIt;
	
	private Image backgroundImage;
	private JLabel text;
	
	public PostItPanel(final MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		
		postItService = new PostItServiceImpl();
		
		this.setLayout(null);
		
		this.addMouseListener(new MouseListener() {
			@Override public void mouseReleased(MouseEvent event) { }
			@Override public void mousePressed(MouseEvent event) { }
			@Override public void mouseExited(MouseEvent event) { }
			@Override public void mouseEntered(MouseEvent event) { }
			
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2 && !event.isConsumed()) {
					event.consume();
					new CreatePostItFrame(mainFrame, postIt).setVisible(true);
				}
				if (event.getButton() == MouseEvent.BUTTON3) {
					final int answer = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir este post-it?", null, JOptionPane.YES_NO_OPTION);
					if (answer == 0) {
						//Answer is yes.
						postItService.delete(postIt);
						refreshMainFrame();
						JOptionPane.showMessageDialog(null, "Post-it excluído com sucesso!", null, JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		
		text = new JLabel();
		text.setFont(new Font("Mf Delicate Little Flower", Font.BOLD, 20));
		text.setBounds(40, 25, 218, 210);
		this.add(text);
	}
	
	public void paintComponent(final Graphics g) {
		g.drawImage(backgroundImage, 0, 0, null);
	}

	public void setPostIt(final PostIt postIt) {
		this.postIt = postIt;
		
		String html1 = "<html><body style='width: ";
        String html2 = "px'>";
		this.text.setText(html1 + "170" + html2 + postIt.getText() + "</body></html>");
		
		String imageName = "";
		switch (postIt.getColor()) {
		case GREEN:
			imageName = "postItGreen.png";
			break;
		case PINK:
			imageName = "postItPink.png";
			break;
		case PURPLE:
			imageName = "postItPurple.png";
			break;
		default: break;
		}
		
		backgroundImage = ResourcesUtil.getImage(imageName);
	}

	public PostIt getPostIt() {
		return postIt;
	}

	public void setText(final String text) {
		this.text.setText(text);
	}

	public String getText() {
		return this.text.getText();
	}

	public void setBackgroundImage(final Image backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public Image getBackgroundImage() {
		return backgroundImage;
	}
	
	private void refreshMainFrame(){
		mainFrame.refreshUsersCombo(mainFrame.getUsersComboBox().getSelectedItem());
		mainFrame.populatePostItsPanel();
	}
	
}
