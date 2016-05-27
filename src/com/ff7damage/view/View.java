package com.ff7damage.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import com.ff7damage.Controller;
import com.ff7damage.Event;
import com.ff7damage.Events;

public class View extends Observable implements Observer, VisitorInterface {
	private Controller controllerInstance;
	
	private JFrame main;
	private JLabel currentCharacter;
	private JPanel leftPanel;
	private CenterPanel centerPanel;
	private RightPanel rightPanel;
	private JScrollPane left;
	private JScrollPane center;
	private JScrollPane right;
	
	public View(Controller instance) {
		this.currentCharacter = null;
		this.controllerInstance = instance;
	}
	
	public String getCurrentCharacter() {
		return this.currentCharacter.getName();
	}

	public void createAndShowInitialView() {
		this.main = new JFrame();
		this.main.setSize(new Dimension(2048, 1024));
		BorderLayout mainLayout = new BorderLayout();
		this.main.setLayout(mainLayout);
		
		createLeftPanel(this.main.getWidth()/10, this.main.getHeight());
		createCenterPanel(6*this.main.getWidth()/10, this.main.getHeight());
		createRightPanel(this.main.getWidth()/5, this.main.getHeight());
		
		this.left = new JScrollPane(this.leftPanel);
		this.center = new JScrollPane(this.centerPanel.getCenterPanel());
		this.right = new JScrollPane(this.rightPanel.getRightPanel());
		
		this.main.getContentPane().add(this.left, BorderLayout.WEST);
		this.main.getContentPane().add(this.center, BorderLayout.CENTER);
		this.main.getContentPane().add(this.right, BorderLayout.EAST);
		
		this.main.pack();
		this.main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.main.setVisible(true);
	}
	
	private void createLeftPanel(int width, int height) {
		this.leftPanel = new JPanel() {
			private static final long serialVersionUID = 7127669893485044937L;

			public Dimension getPreferredSize() {
			      return new Dimension(width, height);
			};
			
			public Dimension getMinimumSize() {
			      return new Dimension(width, height);
			};
		};
		
		this.leftPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.leftPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints leftPanelConstraints = new GridBagConstraints();
		leftPanelConstraints.gridy = 0;
		leftPanelConstraints.weighty=1.0;
		leftPanelConstraints.insets = new Insets(5, 5, 5, 5);
		leftPanelConstraints.fill = GridBagConstraints.VERTICAL;
		
		JLabel leftPanelTitle = new JLabel("Select a character");
		this.leftPanel.add(leftPanelTitle, leftPanelConstraints);
		
		String[] names = new String[]{"cloud", "barret", "tifa", "aerith", "red", "yuffie", "cait", "vincent", "cid"};
		String[] iconPaths = new String[]{"res/cloud.png", "res/barret.png", "res/tifa.png", "res/aerith.png", "res/red.png", "res/yuffie.png", "res/cait.png", "res/vincent.png", "res/cid.png"};
		JLabel[] characters = new JLabel[9];
		
		for(int i=0; i< characters.length; i++) {
			characters[i] = createCharacterLabel(names[i], iconPaths[i]);
			insertCharacterIntoPanel(this.leftPanel, characters[i], leftPanelConstraints);
			characters[i].addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent arg0) {
					JLabel oldCharacter = currentCharacter;
					currentCharacter = ((JLabel)(arg0.getSource()));
					
					if(currentCharacter.getName() == oldCharacter.getName()) {
						return;
					}
					
					swapCurrentCharacterSelection(oldCharacter, currentCharacter);
					Event selectedCharacter = new Event(Events.CHARACTER_CHANGED, currentCharacter.getName());
					setChanged();
					notifyObservers(selectedCharacter);
				}

				private void swapCurrentCharacterSelection(JLabel oldCharacter, JLabel currentCharacter) {
					oldCharacter.setBorder(null);
					currentCharacter.setBorder(new LineBorder(Color.BLACK, 2));
				}

				@Override
				public void mouseEntered(MouseEvent arg0) {}

				@Override
				public void mouseExited(MouseEvent arg0) {}

				@Override
				public void mousePressed(MouseEvent arg0) {}

				@Override
				public void mouseReleased(MouseEvent arg0) {}
			});
		}
	}
	
	private JLabel createCharacterLabel(String name, String iconPath) {
		JLabel character = new JLabel(new ImageIcon(iconPath));
		character.setName(name);
		
		return character;
	}
	
	private void insertCharacterIntoPanel(JPanel panel, JLabel character, GridBagConstraints constraints) {
		constraints.gridy++;
		panel.add(character, constraints);
		
		if(character.getName() == "cloud") {
			this.currentCharacter = character;
			this.currentCharacter.setBorder(new LineBorder(Color.BLACK, 2));
		}
	}
	
	private void createCenterPanel(int width, int height) {
		this.centerPanel = new CenterPanel(width, height);
		this.centerPanel.getCenterPanel().setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.centerPanel.addObserver(this.controllerInstance);
		
		WrapperInterface character = CharactersFactory.getCharacterWrapper(getCurrentCharacter());
		character.accept(this);
	}

	private void createRightPanel(int width, int height) {
		this.rightPanel = new RightPanel(width, height);
		this.rightPanel.getRightPanel().setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.rightPanel.addObserver(this.controllerInstance);
		this.rightPanel.drawPanel();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof Event){
			Event e = (Event) arg1;
			
			if(arg0 instanceof Controller) {
				manageControllerEvent(e);
			}
		}
	}

	private void manageControllerEvent(Event e) {
		switch(e.getCode()) {
		case UPDATE_CENTER_PANEL:
		{
			WrapperInterface character = CharactersFactory.getCharacterWrapper((String)(e.getParams()[0]));
			character.accept(this);
			break;
		}
		case RESULT_FOR_VIEW:
		{
			//TODO
			break;
		}
		default:
		{
			break;
		}
		}
	}

	@Override
	public void visit(Cloud character) {
		this.centerPanel.getCenterPanel().removeAll();
		this.centerPanel.setCurrentCharacter(new JLabel("Selected: Cloud Strife"), character);
		this.centerPanel.getCenterPanel().repaint();
		this.main.revalidate();
	}

	@Override
	public void visit(Barret character) {
		this.centerPanel.getCenterPanel().removeAll();
		this.centerPanel.setCurrentCharacter(new JLabel("Selected: Barret Wallace"), character);
		this.centerPanel.getCenterPanel().repaint();
		this.main.revalidate();
	}

	@Override
	public void visit(Tifa character) {
		this.centerPanel.getCenterPanel().removeAll();
		this.centerPanel.setCurrentCharacter(new JLabel("Selected: Tifa Lockhart"), character);
		this.centerPanel.getCenterPanel().repaint();
		this.main.revalidate();
	}

	@Override
	public void visit(Aerith character) {
		this.centerPanel.getCenterPanel().removeAll();
		this.centerPanel.setCurrentCharacter(new JLabel("Selected: Aerith Gainsborough"), character);
		this.centerPanel.getCenterPanel().repaint();
		this.main.revalidate();
	}

	@Override
	public void visit(RedXIII character) {
		this.centerPanel.getCenterPanel().removeAll();
		this.centerPanel.setCurrentCharacter(new JLabel("Selected: Red XIII"), character);
		this.centerPanel.getCenterPanel().repaint();
		this.main.revalidate();
	}

	@Override
	public void visit(Yuffie character) {
		this.centerPanel.getCenterPanel().removeAll();
		this.centerPanel.setCurrentCharacter(new JLabel("Selected: Yuffie Kisaragi"), character);
		this.centerPanel.getCenterPanel().repaint();
		this.main.revalidate();
	}

	@Override
	public void visit(CaitSith character) {
		this.centerPanel.getCenterPanel().removeAll();
		this.centerPanel.setCurrentCharacter(new JLabel("Selected: Cait Sith"), character);
		this.centerPanel.getCenterPanel().repaint();
		this.main.revalidate();
	}

	@Override
	public void visit(Vincent character) {
		this.centerPanel.getCenterPanel().removeAll();
		this.centerPanel.setCurrentCharacter(new JLabel("Selected: Vincent Valentine"), character);
		this.centerPanel.getCenterPanel().repaint();
		this.main.revalidate();
	}

	@Override
	public void visit(Cid character) {
		this.centerPanel.getCenterPanel().removeAll();
		this.centerPanel.setCurrentCharacter(new JLabel("Selected: Cid Highwind"), character);
		this.centerPanel.getCenterPanel().repaint();
		this.main.revalidate();
	}
}