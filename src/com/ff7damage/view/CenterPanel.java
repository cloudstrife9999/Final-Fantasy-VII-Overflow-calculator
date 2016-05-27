package com.ff7damage.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.ByteBuffer;
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
		/*
		 * character: 1 byte
		 * 	0x00 cloud, 0x01 barret, 0x02 tifa, 0x03 aerith, 0x04 red xiii, 0x05 yuffie, 0x06 cait sith, 0x07 vincent, 0x08 cid
		 * level: 1 byte (0x01-0x63)
		 * strength: 1 byte (0x00-0xFF)
		 * weapon: 1 byte
		 *  0x00 final weapon, 0x01 other weapon
		 * technique power: 1 byte (0x01-0xFF)
		 * attack element: 1 byte
		 *  0x00 fire, 0x01 ice, 0x02 lightning, 0x03 earth, 0x04 wind, 0x05 water, 0x06 poison, 0x07 holy, 0x08 gravity,
		 *  0x09 restorative, 0x0A cut, 0x0B hit, 0x0C punch, 0x0D shoot, 0x0E shout, 0x0F hidden, 0x10 non-elemental
		 * hero drinks: 1 byte (0x00-0x04)
		 * critical: 1 byte
		 *  0x00 false, 0x01 true
		 * breserk: 1 byte
		 *  0x00 false, 0x01 true
		 * attacker row: 1 byte
		 *  0x00 back, 0x01 front
		 * split attack: 1 byte
		 *  0x00 false, 0x01 true
		 * frog: 1 byte
		 *  0x00 false, 0x01 true
		 * mini: 1 byte
		 *  0x00 false, 0x01 true
		 * 1st additional parameter: 1 + 4? + x bytes
		 *  1st byte: 0x00 useless, 0x01 useful
		 *  2nd to 5th bytes: len(x) in binary or nothing if useless
		 *  6th to (5 + x)th bytes: the parameter or nothing if useless
		 * 2nd additional parameter: 1 + 4? + x bytes
		 *  1st byte: 0x00 useless, 0x01 useful
		 *  2nd to 5th bytes: len(x) in binary
		 *  6th to (5 + x)th bytes: the parameter
		 * 3rd additional parameter (weapon atk bonus if not final): 1 + 1? bytes
		 * 	1st byte: 0x00 useless, 0x01 useful
		 *  2nd byte: bonus atk value (0x00-0xFF) or nothing if useless
		 */
		
		int firstAdditionalParameterLength = getFirstAdditionalParameterLength();
		int firstAdditionalParameterTotalLength = 1 + firstAdditionalParameterLength != 0 ? 4 + firstAdditionalParameterLength : 0;
		
		int secondAdditionalParameterLength = getSecondAdditionalParameterLength();
		int secondAdditionalParameterTotalLength = 1 + secondAdditionalParameterLength != 0 ? 4 + secondAdditionalParameterLength : 0;
		
		int thirdAdditionalParameterLength = getThirdAdditionalParameterLength();
		int thirdAdditionalParameterTotalLength = 1 + thirdAdditionalParameterLength;
		
		int totalLength = 13 + firstAdditionalParameterTotalLength + secondAdditionalParameterTotalLength + thirdAdditionalParameterTotalLength;
		
		ByteBuffer data = ByteBuffer.allocate(totalLength);
		data.put(Utils.characterWrapperToCode(this.selectedCharacter));
		data.put(Utils.stringToHexToByte(this.level.getSelectedValue()));
		data.put(Utils.stringToHexToByte(this.strength.getSelectedValue()));
		data.put(Utils.getWeaponCode(this.weapon.getSelectedValue()));
		data.put(Utils.stringToHexToByte(this.power.getSelectedValue()));
		data.put(Utils.elementToCode(this.element.getSelectedValue()));
		data.put(Utils.stringToHexToByte(this.heroDrink.getSelectedValue()));
		data.put(Utils.stringToByteCode(this.critical.getSelectedValue()));
		data.put(Utils.stringToByteCode(this.berserk.getSelectedValue()));
		data.put(Utils.stringToByteCode(this.row.getSelectedValue()));
		data.put(Utils.stringToByteCode(this.split.getSelectedValue()));
		data.put(Utils.stringToByteCode(this.frog.getSelectedValue()));
		data.put(Utils.stringToByteCode(this.mini.getSelectedValue()));
		
		byte first = (byte) (firstAdditionalParameterLength == 0 ? 0x00 : 0x01);
		byte[] firstLength = ByteBuffer.allocate(4).putInt(firstAdditionalParameterLength).array();
		
		byte second = (byte) (secondAdditionalParameterLength == 0 ? 0x00 : 0x01);
		byte[] secondLength = ByteBuffer.allocate(4).putInt(secondAdditionalParameterLength).array();
		
		byte third = (byte) (thirdAdditionalParameterLength == 0 ? 0x00 : 0x01);
		byte[] thirdLength = ByteBuffer.allocate(4).putInt(thirdAdditionalParameterLength).array();
		
		data.put(first);
		
		if(firstAdditionalParameterLength != 0) {
			data.put(firstLength);
			
			byte[] firstParameter;
			
			if(!(this.selectedCharacter instanceof Barret)) {
				firstParameter = Utils.stringToIntToByteArray(this.firstList.getSelectedValue());
			}
			else {
				String index = Integer.valueOf(this.firstList.getSelectedIndex()).toString(); //it is correct
				firstParameter = Utils.stringToShortToByteArray(index);
			}
			
			data.put(firstParameter);
		}
		
		data.put(second);
		
		if(secondAdditionalParameterLength != 0) {
			data.put(secondLength);
			byte[] secondParameter = Utils.stringToIntToByteArray(this.secondList.getSelectedValue()); //ok for Tifa as well
			data.put(secondParameter);
		}
		
		data.put(third);
		
		if(thirdAdditionalParameterLength != 0) {
			data.put(thirdLength);
			byte thirdParameter = 0x00;
			data.put(thirdParameter);
		}
		
		return data.array();
	}

	private int getThirdAdditionalParameterLength() {
		return 1; //TODO implement non-ultimate weapons.
	}

	private int getSecondAdditionalParameterLength() {
		if(this.selectedCharacter instanceof Cloud || this.selectedCharacter instanceof CaitSith ||
				this.selectedCharacter instanceof RedXIII || this.selectedCharacter instanceof Cid) {
				
				return 2;
			}
			else if(this.selectedCharacter instanceof Tifa) {
				return 1;
			}
			else if(this.selectedCharacter instanceof Yuffie || this.selectedCharacter instanceof Vincent ||
					this.selectedCharacter instanceof Barret || this.selectedCharacter instanceof Aerith) {
				
				return 0;
			}
			else {
				return 0;
			}
	}

	private int getFirstAdditionalParameterLength() {
		if(this.selectedCharacter instanceof Cloud || this.selectedCharacter instanceof CaitSith ||
			this.selectedCharacter instanceof RedXIII || this.selectedCharacter instanceof Cid ||
			this.selectedCharacter instanceof Vincent || this.selectedCharacter instanceof Barret) {
			
			return 2;
		}
		else if(this.selectedCharacter instanceof Tifa || this.selectedCharacter instanceof Aerith) {
			return 1;
		}
		else if(this.selectedCharacter instanceof Yuffie) {
			return 0;
		}
		else {
			return 0;
		}
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