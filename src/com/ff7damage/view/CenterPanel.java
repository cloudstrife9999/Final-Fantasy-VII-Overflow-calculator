package com.ff7damage.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.ff7damage.Event;
import com.ff7damage.Events;
import com.ff7damage.Utils;

public class CenterPanel extends Observable {
	private JPanel centerPanel;

	private JLabel currentCharacter;
	private WrapperInterface selectedCharacter;
	private int width;
	private int height;
	private GridBagConstraints centerPanelGBC;
	
	private JPanel firstLine;
	private JScrollPane weaponScrollPane;
	private JScrollPane levelScrollPane;
	private JScrollPane strengthScrollPane;
	private JList<String> weapon;
	private JList<String> level;
	private JList<String> strength;
	
	private JPanel secondLine;
	private JScrollPane powerScrollPane;
	private JScrollPane elementScrollPane;
	private JScrollPane heroDrinkScrollPane;
	private JList<String> power;
	private JList<String> element;
	private JList<String> heroDrink;
	
	private JPanel thirdLine;
	private JScrollPane criticalScrollPane;
	private JScrollPane berserkScrollPane;
	private JScrollPane rowScrollPane;
	private JList<String> critical;
	private JList<String> berserk;
	private JList<String> row;
	
	private JPanel fourthLine;
	private JScrollPane splitScrollPane;
	private JScrollPane frogScrollPane;
	private JScrollPane miniScrollPane;
	private JList<String> split;
	private JList<String> frog;
	private JList<String> mini;
	
	private JPanel fifthLine;
	private JScrollPane firstScrollPane;
	private JScrollPane secondScrollPane;
	private JList<String> firstList;
	private JList<String> secondList;
	
	public CenterPanel(int width, int height) {
		this.width = width;
		this.height = height;
		
		this.centerPanel = new JPanel() {
			private static final long serialVersionUID = -6250177094978368777L;

			public Dimension getPreferredSize() {
			      return new Dimension(width, height);
			};
		};
		
		this.currentCharacter = new JLabel("Selected: Cloud Strife"); //default on creation
		this.centerPanel.setLayout(new GridBagLayout());
		this.centerPanelGBC = new GridBagConstraints();
	}
	
	public JLabel getCurrentCharacter() {
		return this.currentCharacter;
	}
	
	public JPanel getCenterPanel() {
		return this.centerPanel;
	}
	
	public void setCurrentCharacter(String name, WrapperInterface character) {
		JLabel characterLabel = new JLabel("Selected: " + name);
		setCurrentCharacter(characterLabel, character);
	}
	
	public void setCurrentCharacter(JLabel characterLabel, WrapperInterface character) {
		this.currentCharacter = characterLabel;
		this.selectedCharacter = character;
		this.centerPanelGBC.gridy = 0;
		this.centerPanelGBC.weighty = 1.0;
		this.centerPanelGBC.insets = new Insets(5, 5, 5, 5);
		this.centerPanelGBC.anchor = GridBagConstraints.NORTH;
		this.centerPanel.add(characterLabel, this.centerPanelGBC);
		
		drawCustomCenterPanel();
	}

	private void drawCustomCenterPanel() {
		this.centerPanelGBC.gridy = 1;
		this.centerPanelGBC.fill = GridBagConstraints.NONE;
		
		drawFirstLine();
		drawSecondLine();
		drawThirdLine();
		drawFourthLine();
		
		this.centerPanel.add(this.firstLine, this.centerPanelGBC);
		
		this.centerPanelGBC.gridy++;
		this.centerPanel.add(this.secondLine, this.centerPanelGBC);
		
		this.centerPanelGBC.gridy++;
		this.centerPanel.add(this.thirdLine, this.centerPanelGBC);
		
		this.centerPanelGBC.gridy++;
		this.centerPanel.add(this.fourthLine, this.centerPanelGBC);
		
		if(!(this.selectedCharacter instanceof Yuffie)) {
			drawFifthLine();
			this.centerPanelGBC.gridy++;
			this.centerPanel.add(this.fifthLine, this.centerPanelGBC);
		}
		
		this.centerPanelGBC.gridy++;
		
		JButton confirm = new JButton("Calculate");
		confirm.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				setChanged();
				byte[] data = collectData();
				Event event = new Event(Events.SENDING_CENTER_PANEL_DATA, data);
				notifyObservers(event);
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
		});
		
		this.centerPanel.add(confirm, this.centerPanelGBC);
		
		this.centerPanel.repaint();
	}

	protected byte[] collectData() {
		// TODO Auto-generated method stub
		return null;
	}

	private void drawFirstLine() {
		this.firstLine = new JPanel();
		this.firstLine.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
		
		drawWeaponList();
		drawLevelList();
		drawStrengthList();
		
		this.firstLine.repaint();
	}
	
	private void drawSecondLine() {
		this.secondLine = new JPanel();
		this.secondLine.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
		
		drawPowerList();
		drawElementList();
		drawHeroDrinksList();
		
		this.secondLine.repaint();
	}
	
	private void drawThirdLine() {
		this.thirdLine = new JPanel();
		this.thirdLine.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
		
		drawCriticalList();
		drawBerserkList();
		drawRowList();
		
		this.thirdLine.repaint();
	}

	private void drawFourthLine() {
		this.fourthLine = new JPanel();
		this.fourthLine.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
		
		drawSplitList();
		drawFrogList();
		drawMiniList();
		
		this.fourthLine.repaint();
	}
	
	private void drawFifthLine() {
		this.fifthLine = new JPanel();
		this.fifthLine.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
		
		drawFirstList();
		
		if(this.selectedCharacter instanceof Cloud || this.selectedCharacter instanceof Cid ||
				this.selectedCharacter instanceof RedXIII || this.selectedCharacter instanceof CaitSith ||
				this.selectedCharacter instanceof Tifa) {
			
			drawSecondList();
		}
		
		this.fifthLine.repaint();
	}

	private void drawSecondList() {
		int[] bounds = getSecondListBounds();
		String[] data = new String[bounds[1] - bounds[0] + 1];
		
		for(int i=bounds[0]; i <= bounds[1]; i++) {
			data[i - bounds[0]] = Integer.valueOf(i).toString();
		}
		
		String headerMessage = getSecondListHeaderMessage();
		
		this.secondList = new JList<String>(data);
		this.secondScrollPane = new JScrollPane(this.secondList);
		this.secondScrollPane.setPreferredSize(new Dimension(this.width/4, this.height/10));
		
		JLabel header = Utils.drawHeader(headerMessage);
		
		this.secondScrollPane.setColumnHeaderView(header);
		this.fifthLine.add(this.secondScrollPane);
	}

	private void drawFirstList() {
		int[] bounds = getFirstListBounds();
		String[] data; 
		
		if(this.selectedCharacter instanceof Barret) {
			data = new String[401];
			
			for(int i=bounds[0] + 10000, j=0; i <= bounds[1]; i = i + 10000, j++) {
				data[j] = "Less than " + Integer.valueOf(i).toString();
			}
			
			data[400] = "4000000";
		}
		else {
			data = new String[bounds[1] - bounds[0] + 1];
			
			for(int i=bounds[0]; i <= bounds[1]; i++) {
				data[i - bounds[0]] = Integer.valueOf(i).toString();
			}
		}
		
		String headerMessage = getFirstListHeaderMessage();
		
		this.firstList = new JList<String>(data);
		this.firstScrollPane = new JScrollPane(this.firstList);
		this.firstScrollPane.setPreferredSize(new Dimension(this.width/4, this.height/10));
		
		JLabel header = Utils.drawHeader(headerMessage);
		
		this.firstScrollPane.setColumnHeaderView(header);
		this.fifthLine.add(this.firstScrollPane);
	}

	private String getSecondListHeaderMessage() {
		if(this.selectedCharacter instanceof Cloud) {
			return "Select Cloud Strife's max HP";
		}
		else if(this.selectedCharacter instanceof CaitSith) {
			return "Select Cait Sith's max HP";
		}
		else if(this.selectedCharacter instanceof RedXIII) {
			return "Select Red XIII's max MP";
		}
		else if(this.selectedCharacter instanceof Cid) {
			return "Select Cid Highwind's max MP";
		}
		else if(this.selectedCharacter instanceof Tifa) {
			return "Select Tifa Lockhart's limit gauge level";
		}
		else {
			return "";
		}
	}

	private int[] getSecondListBounds() {
		if(this.selectedCharacter instanceof Cloud) {
			return new int[]{1, 9999};
		}
		else if(this.selectedCharacter instanceof CaitSith) {
			return new int[]{1, 9999};
		}
		else if(this.selectedCharacter instanceof RedXIII) {
			return new int[]{0, 9999};
		}
		else if(this.selectedCharacter instanceof Cid) {
			return new int[]{0, 9999};
		}
		else if(this.selectedCharacter instanceof Tifa) {
			return new int[]{0, 255};
		}
		else {
			return new int[]{0, 0};
		}
	}
	
	private String getFirstListHeaderMessage() {
		if(this.selectedCharacter instanceof Cloud) {
			return "Select Cloud Strife's current HP";
		}
		else if(this.selectedCharacter instanceof CaitSith) {
			return "Select Cait Sith's current HP";
		}
		else if(this.selectedCharacter instanceof RedXIII) {
			return "Select Red XIII's current MP";
		}
		else if(this.selectedCharacter instanceof Cid) {
			return "Select Cid Highwind's current MP";
		}
		else if(this.selectedCharacter instanceof Tifa) {
			return "Select Tifa Lockhart's limit level";
		}
		else if(this.selectedCharacter instanceof Barret) {
			return "How many APs are on the Missing Score?";
		}
		else if(this.selectedCharacter instanceof Vincent) {
			return "How many enemies did Vincent kill?";
		}
		else if(this.selectedCharacter instanceof Aerith) {
			return "How many active characters are dead?";
		}
		else {
			return "";
		}
	}

	private int[] getFirstListBounds() {
		if(this.selectedCharacter instanceof Cloud) {
			return new int[]{1, 9999};
		}
		else if(this.selectedCharacter instanceof CaitSith) {
			return new int[]{1, 9999};
		}
		else if(this.selectedCharacter instanceof RedXIII) {
			return new int[]{0, 9999};
		}
		else if(this.selectedCharacter instanceof Cid) {
			return new int[]{0, 9999};
		}
		else if(this.selectedCharacter instanceof Tifa) {
			return new int[]{1, 4};
		}
		else if(this.selectedCharacter instanceof Barret) {
			return new int[]{0, 4000000};
		}
		else if(this.selectedCharacter instanceof Vincent) {
			return new int[]{0, 65535};
		}
		else if(this.selectedCharacter instanceof Aerith) {
			return new int[]{0, 2};
		}
		else {
			return new int[]{0, 0};
		}
	}

	private void drawMiniList() {
		String[] data = new String[]{"Yes", "No"};
		
		this.mini = new JList<String>(data);
		this.miniScrollPane = new JScrollPane(this.mini);
		this.miniScrollPane.setPreferredSize(new Dimension(this.width/4, this.height/10));
		
		JLabel header = Utils.drawHeader("Is " + this.currentCharacter.getText().substring(10) + " in mini status?");
		
		this.miniScrollPane.setColumnHeaderView(header);
		this.fourthLine.add(this.miniScrollPane);
	}

	private void drawFrogList() {
		String[] data = new String[]{"Yes", "No"};
		
		this.frog = new JList<String>(data);
		this.frogScrollPane = new JScrollPane(this.frog);
		this.frogScrollPane.setPreferredSize(new Dimension(this.width/4, this.height/10));
		
		JLabel header = Utils.drawHeader("Is " + this.currentCharacter.getText().substring(10) + " in frog status?");
		
		this.frogScrollPane.setColumnHeaderView(header);
		this.fourthLine.add(this.frogScrollPane);
	}

	private void drawSplitList() {
		String[] data = new String[]{"Yes", "No"};
		
		this.split = new JList<String>(data);
		this.splitScrollPane = new JScrollPane(this.split);
		this.splitScrollPane.setPreferredSize(new Dimension(this.width/4, this.height/10));
		
		JLabel header = Utils.drawHeader("Does the attack hit multiple targets?");
		
		this.splitScrollPane.setColumnHeaderView(header);
		this.fourthLine.add(this.splitScrollPane);
	}
	
	private void drawRowList() {
		String[] data = new String[]{"Front", "Back"};
		
		this.row = new JList<String>(data);
		this.rowScrollPane = new JScrollPane(this.row);
		this.rowScrollPane.setPreferredSize(new Dimension(this.width/4, this.height/10));
		
		JLabel header = Utils.drawHeader("Which row is " + this.currentCharacter.getText().substring(10) + " in?");
		
		this.rowScrollPane.setColumnHeaderView(header);
		this.thirdLine.add(this.rowScrollPane);
	}

	private void drawBerserkList() {
		String[] data = new String[]{"Yes", "No"};
		
		this.berserk = new JList<String>(data);
		this.berserkScrollPane = new JScrollPane(this.berserk);
		this.berserkScrollPane.setPreferredSize(new Dimension(this.width/4, this.height/10));
		
		JLabel header = Utils.drawHeader("Is " + this.currentCharacter.getText().substring(10) + " in berserk status?");
		
		this.berserkScrollPane.setColumnHeaderView(header);
		this.thirdLine.add(this.berserkScrollPane);
	}

	private void drawCriticalList() {
		String[] data = new String[]{"Yes", "No"};
		
		this.critical = new JList<String>(data);
		this.criticalScrollPane = new JScrollPane(this.critical);
		this.criticalScrollPane.setPreferredSize(new Dimension(this.width/4, this.height/10));
		
		JLabel header = Utils.drawHeader("Is the attack a critical hit?");
		
		this.criticalScrollPane.setColumnHeaderView(header);
		this.thirdLine.add(this.criticalScrollPane);
	}

	private void drawHeroDrinksList() {
		String[] data = new String[5];
		
		for(int i=0; i < 5; i++) {
			data[i] = Integer.valueOf(i).toString();
		}
		
		this.heroDrink = new JList<String>(data);
		this.heroDrinkScrollPane = new JScrollPane(this.heroDrink);
		this.heroDrinkScrollPane.setPreferredSize(new Dimension(this.width/4, this.height/10));
		
		JLabel header = Utils.drawHeader("How many Hero Drinks were used?");
		
		this.heroDrinkScrollPane.setColumnHeaderView(header);
		this.secondLine.add(this.heroDrinkScrollPane);
	}

	private void drawElementList() {
		String[] data = new String[]{"Fire", "Ice", "Lightning", "Earth", "Wind", "Water", "Poison", "Holy", "Gravity",
				"Restorative", "Cut", "Hit", "Punch", "Shoot", "Shout", "Hidden", "Non-elemental"};
		
		this.element = new JList<String>(data);
		this.elementScrollPane = new JScrollPane(this.element);
		this.elementScrollPane.setPreferredSize(new Dimension(this.width/4, this.height/10));
		
		JLabel header = Utils.drawHeader("Select the attack element");
		
		this.elementScrollPane.setColumnHeaderView(header);
		this.secondLine.add(this.elementScrollPane);
	}

	private void drawPowerList() {
		String[] data = new String[255]; //assuming min is 1
		
		for(int i=1; i < 256; i++) {
			data[i-1] = Integer.valueOf(i).toString();
		}
		
		this.power = new JList<String>(data);
		this.powerScrollPane = new JScrollPane(this.power);
		this.powerScrollPane.setPreferredSize(new Dimension(this.width/4, this.height/10));
		
		JLabel header = Utils.drawHeader("Select the technique power");
		
		this.powerScrollPane.setColumnHeaderView(header);
		this.secondLine.add(this.powerScrollPane);
	}

	private void drawStrengthList() {
		String[] data = new String[255]; //assuming min is 1
		
		for(int i=1; i < 256; i++) {
			data[i-1] = Integer.valueOf(i).toString();
		}
		
		this.strength = new JList<String>(data);
		this.strengthScrollPane = new JScrollPane(this.strength);
		this.strengthScrollPane.setPreferredSize(new Dimension(this.width/4, this.height/10));
		
		JLabel header = Utils.drawHeader("Select a strength value");
		
		this.strengthScrollPane.setColumnHeaderView(header);
		this.firstLine.add(this.strengthScrollPane);
	}

	private void drawLevelList() {
		String[] data = new String[99]; //assuming min is 1
		
		for(int i=1; i < 100; i++) {
			data[i-1] = Integer.valueOf(i).toString();
		}
		
		this.level = new JList<String>(data);
		this.levelScrollPane = new JScrollPane(this.level);
		this.levelScrollPane.setPreferredSize(new Dimension(this.width/4, this.height/10));
		
		JLabel header = Utils.drawHeader("Select a level");
		
		this.levelScrollPane.setColumnHeaderView(header);
		this.firstLine.add(this.levelScrollPane);
	}

	private void drawWeaponList() {
		String[] data = this.selectedCharacter.getWeapons();
		
		this.weapon = new JList<String>(data);
		this.weaponScrollPane = new JScrollPane(this.weapon);
		this.weaponScrollPane.setPreferredSize(new Dimension(this.width/4, this.height/10));
		
		JLabel header = Utils.drawHeader("Select a weapon");
		
		this.weaponScrollPane.setColumnHeaderView(header);
		this.firstLine.add(this.weaponScrollPane);
	}
	
	/*private void drawList(String[] data, JScrollPane scroller, JList<String> list) {
		list = new JList<String>(data);
		scroller = new JScrollPane(list);
		scroller.setPreferredSize(new Dimension(this.width/4, this.height/10));
		
		JLabel header = drawHeader("Select a weapon");
		
		scroller.setColumnHeaderView(header);
	}*/
}