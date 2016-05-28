package com.ff7damage.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
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
	private JDialog results;
	private JScrollPane resultsScrollPane;
	private JPanel resultsPanel;
	private List<String> res;
	
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
		this.main.setTitle("Final Fantasy VII damage and overflow calculator");
		
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
		this.main.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		this.main.setVisible(true);
	}
	
	private void createLeftPanel(int width, int height) {
		instantiateLeftPanel(width, height);
		
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
		String[] iconPaths = new String[]{"cloud.png", "barret.png", "tifa.png", "aerith.png", "red.png", "yuffie.png", "cait.png", "vincent.png", "cid.png"};
		JLabel[] characters = new JLabel[9];
		
		populateLeftPanel(names, iconPaths, characters, leftPanelConstraints);
	}
	
	private void populateLeftPanel(String[] names, String[] iconPaths, JLabel[] characters, GridBagConstraints leftPanelConstraints) {
		for(int i=0; i< characters.length; i++) {
			characters[i] = createCharacterLabel(names[i], iconPaths[i]);
			insertCharacterIntoPanel(this.leftPanel, characters[i], leftPanelConstraints);
			addListener(characters, i);
		}
	}

	private void addListener(JLabel[] characters, int i) {
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
				System.out.println("View: sending new character selection to Controller...");
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

	private void instantiateLeftPanel(int width, int height) {
		this.leftPanel = new JPanel() {
			private static final long serialVersionUID = 7127669893485044937L;

			public Dimension getPreferredSize() {
			      return new Dimension(width, height);
			};
			
			public Dimension getMinimumSize() {
			      return new Dimension(width, height);
			};
		};
	}

	private JLabel createCharacterLabel(String name, String iconPath) {
		URL url = View.class.getResource("/images/" + iconPath);
		JLabel character = new JLabel(new ImageIcon(url));
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
		this.controllerInstance.addObserver(this.rightPanel);
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
			if(e.getParams()[0] instanceof List<?>){
				List<?> results = (List<?>) (e.getParams()[0]);
				manageResults(results);
			}
			break;
		}
		default:
		{
			break;
		}
		}
	}

	private void manageResults(List<?> results) {
		this.res = new ArrayList<String>();
		
		for(Object r : results) {
			if(r instanceof String) {
				this.res.add((String) r);
			}
		}
		
		createResultsDialog();
	}

	private void createResultsDialog() {
		this.results = new JDialog();
		this.results.setTitle("Results");
		this.results.setPreferredSize(new Dimension(this.main.getSize().width/2, this.main.getSize().height));
		
		this.resultsPanel = new JPanel();
		this.resultsScrollPane = new JScrollPane(this.resultsPanel);
		
		this.resultsPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		
		addLinesAndButton(gbc);
		
		this.results.add(this.resultsScrollPane);
		
		this.results.setModal(true);
		this.results.setAlwaysOnTop(true);
		this.results.setModalityType(ModalityType.APPLICATION_MODAL);
		this.results.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.results.pack();
		this.results.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width)/2 - this.results.getWidth()/2, (Toolkit.getDefaultToolkit().getScreenSize().height)/2 - this.results.getHeight()/2);
		this.results.setVisible(true);
	}

	private void addLinesAndButton(GridBagConstraints gbc) {
		for(int i=0; i < this.res.size(); i++) {
			JLabel label = new JLabel(this.res.get(i));
			
			if(res.get(i).startsWith("#")) {
				label.setForeground(Color.BLUE);
			}
			
			this.resultsPanel.add(label, gbc);
			gbc.gridy++;
		}
		
		addClosingButton(gbc);
	}

	private void addClosingButton(GridBagConstraints gbc) {
		JButton button = new JButton("Close");
		button.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				results.dispose();
			}
			
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
			@Override
			public void mousePressed(MouseEvent arg0) {}
			
			@Override
			public void mouseExited(MouseEvent arg0) {}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {}	
		});
		
		gbc.anchor = GridBagConstraints.SOUTH;
		gbc.insets = new Insets(20, 20 , 20 , 20);
		this.resultsPanel.add(button, gbc);
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