package com.ff7damage.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Dialog.ModalityType;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.ByteBuffer;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
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
	private JScrollPane thirdScrollPane;
	private JList<String> firstList;
	private JList<String> secondList;
	private JList<String> thirdList;
	
	private ByteBuffer data;
	
	private JDialog aboutDialog;
	private JPanel aboutPanel;
	
	
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
		drawFifthLine();
		
		addButton();
		addAboutButton();
		
		this.centerPanel.repaint();
	}

	private void addAboutButton() {
		JButton about = new JButton("About");
		about.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				createAboutDialog();
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
		
		this.centerPanelGBC.gridy++;
		this.centerPanel.add(about, this.centerPanelGBC);
	}

	private void createAboutDialog() {
		this.aboutDialog = new JDialog();
		this.aboutDialog.setTitle("About");
		this.aboutDialog.setPreferredSize(new Dimension(2*this.width/3, this.height/5));
		
		createAboutPanel();
		
		this.aboutDialog.add(this.aboutPanel);
		this.aboutDialog.setModal(true);
		this.aboutDialog.setAlwaysOnTop(true);
		this.aboutDialog.setModalityType(ModalityType.APPLICATION_MODAL);
		this.aboutDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.aboutDialog.pack();
		this.aboutDialog.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width)/2 - this.aboutDialog.getWidth()/2, (Toolkit.getDefaultToolkit().getScreenSize().height)/2 - this.aboutDialog.getHeight()/2);
		this.aboutDialog.setVisible(true);
	}
	
	private void createAboutPanel() {
		this.aboutPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		String[] message = new String[]{
				"Final Fantasy VII damage and overflow calculator",
				"Version 1.0, 28 May 2016",
				"by Cloudstrife9999 a.k.a. Emanuele Uliana",
				"Computer engineer (bachelor and master) at Politecnico di Milano",
				"Computer Science PhD student at Royal Holloway University of London"
		};
		
		gbc.gridy = 0;
		
		for(String s : message) {
			this.aboutPanel.add(new JLabel(s), gbc);
			gbc.gridy++;
		}
		
		addClosingButton(gbc);
	}

	private void addClosingButton(GridBagConstraints gbc) {
		JButton button = new JButton("Close");
		button.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				aboutDialog.dispose();
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
		
		gbc.insets = new Insets(20, 20 , 20 , 20);
		this.aboutPanel.add(button, gbc);
	}

	private void addButton() {
		JButton confirm = new JButton("Calculate the damage");
		confirm.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				byte[] data = collectData();
				Event event = new Event(Events.SENDING_CENTER_PANEL_DATA, data);
				setChanged();
				System.out.println("CenterPanel: sending center panel data to Controller...");
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
		
		this.centerPanelGBC.gridy++;
		this.centerPanel.add(confirm, this.centerPanelGBC);
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
		 * 1st additional parameter: 1 byte
		 *  0x00 not a limit, 0x01-0x0... the limits
		 * 2nd additional parameter: 1 + 4? + x bytes
		 *  1st byte: 0x00 useless, 0x01 useful
		 *  2nd to 5th bytes: len(x) in binary or nothing if useless
		 *  6th to (5 + x)th bytes: the parameter or nothing if useless
		 * 3rd additional parameter: 1 + 4? + x bytes
		 *  1st byte: 0x00 useless, 0x01 useful
		 *  2nd to 5th bytes: len(x) in binary
		 *  6th to (5 + x)th bytes: the parameter
		 * 4th additional parameter (weapon atk bonus if not final): 1 + 1? bytes
		 * 	1st byte: 0x00 useless, 0x01 useful
		 *  2nd byte: bonus atk value (0x00-0xFF) or nothing if useless
		 */
		
		int secondAdditionalParameterLength = getSecondAdditionalParameterLength();
		int secondAdditionalParameterTotalLength = 1 + (secondAdditionalParameterLength != 0 ? 4 + secondAdditionalParameterLength : 0);
		
		int thirdAdditionalParameterLength = getThirdAdditionalParameterLength();
		int thirdAdditionalParameterTotalLength = 1 + (thirdAdditionalParameterLength != 0 ? 4 + thirdAdditionalParameterLength : 0);
		
		int fourthAdditionalParameterLength = getfourthAdditionalParameterLength();
		int fourthAdditionalParameterTotalLength = 1 + fourthAdditionalParameterLength;
		
		int totalLength = 14 + secondAdditionalParameterTotalLength + thirdAdditionalParameterTotalLength + fourthAdditionalParameterTotalLength;
		
		this.data = ByteBuffer.allocate(totalLength);
		
		addBasicData();
		addAdditionalData(secondAdditionalParameterLength, thirdAdditionalParameterLength, fourthAdditionalParameterLength);
		
		return this.data.array();
	}

	private void addAdditionalData(int secondAdditionalParameterLength, int thirdAdditionalParameterLength, int fourthAdditionalParameterLength) {
		byte first = (byte) this.firstList.getSelectedIndex();
		this.data.put(first);
		
		putSecondAdditionalParameter(secondAdditionalParameterLength);
		putThirdAdditionalParameter(thirdAdditionalParameterLength);
		putFourthAdditionalParameter(fourthAdditionalParameterLength);
	}

	private void putFourthAdditionalParameter(int fourthAdditionalParameterLength) {
		byte fourth = (byte) (fourthAdditionalParameterLength == 0 ? 0x00 : 0x01);
		byte[] fourthLength = ByteBuffer.allocate(4).putInt(fourthAdditionalParameterLength).array();
		
		this.data.put(fourth);
		
		if(fourthAdditionalParameterLength != 0) {
			this.data.put(fourthLength);
			byte thirdParameter = 0x00;
			this.data.put(thirdParameter);
		}
	}

	private void putThirdAdditionalParameter(int thirdAdditionalParameterLength) {
		byte third = (byte) (thirdAdditionalParameterLength == 0 ? 0x00 : 0x01);
		byte[] thirdLength = ByteBuffer.allocate(4).putInt(thirdAdditionalParameterLength).array();
		
		this.data.put(third);
		
		if(thirdAdditionalParameterLength != 0) {
			this.data.put(thirdLength);
			byte[] thirdParameter = Utils.stringToIntToByteArray(this.thirdList.getSelectedValue());
			this.data.put(thirdParameter);
		}
	}

	private void putSecondAdditionalParameter(int secondAdditionalParameterLength) {
		byte second = (byte) (secondAdditionalParameterLength == 0 ? 0x00 : 0x01);
		byte[] secondLength = ByteBuffer.allocate(4).putInt(secondAdditionalParameterLength).array();
		
		this.data.put(second);
		
		if(secondAdditionalParameterLength != 0) {
			this.data.put(secondLength);
			
			byte[] secondParameter;
			
			if(!(this.selectedCharacter instanceof Barret)) {
				secondParameter = Utils.stringToIntToByteArray(this.secondList.getSelectedValue());
			}
			else {
				String index = Integer.valueOf(this.secondList.getSelectedIndex()).toString(); //it is correct
				secondParameter = Utils.stringToIntToByteArray(index);
			}
			
			this.data.put(secondParameter);
		}
	}

	private void addBasicData() {
		this.data.put(Utils.characterWrapperToCode(this.selectedCharacter));
		this.data.put(Utils.stringToHexToByte(this.level.getSelectedValue()));
		this.data.put(Utils.stringToHexToByte(this.strength.getSelectedValue()));
		this.data.put(Utils.getWeaponCode(this.weapon.getSelectedValue()));
		this.data.put(Utils.stringToHexToByte(this.power.getSelectedValue()));
		this.data.put(Utils.elementToCode(this.element.getSelectedValue()));
		this.data.put(Utils.stringToHexToByte(this.heroDrink.getSelectedValue()));
		this.data.put(Utils.stringToByteCode(this.critical.getSelectedValue()));
		this.data.put(Utils.stringToByteCode(this.berserk.getSelectedValue()));
		this.data.put(Utils.stringToByteCode(this.row.getSelectedValue()));
		this.data.put(Utils.stringToByteCode(this.split.getSelectedValue()));
		this.data.put(Utils.stringToByteCode(this.frog.getSelectedValue()));
		this.data.put(Utils.stringToByteCode(this.mini.getSelectedValue()));
	}

	private int getfourthAdditionalParameterLength() {
		return 0; //TODO implement non-ultimate weapons.
	}

	private int getThirdAdditionalParameterLength() {
		if(this.selectedCharacter instanceof Cloud || this.selectedCharacter instanceof CaitSith ||
				this.selectedCharacter instanceof RedXIII || this.selectedCharacter instanceof Cid ||
				this.selectedCharacter instanceof Tifa) {
				
				return 4;
			}
			else {
				return 0;
			}
	}

	private int getSecondAdditionalParameterLength() {
		if(this.selectedCharacter instanceof Yuffie) {
			return 0;
		}
		else {
			return 4;
		}
	}

	private void drawFirstLine() {
		this.firstLine = new JPanel();
		this.firstLine.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
		
		drawWeaponList();
		drawLevelList();
		drawStrengthList();
		
		this.firstLine.repaint();
		this.centerPanel.add(this.firstLine, this.centerPanelGBC);
	}
	
	private void drawSecondLine() {
		this.secondLine = new JPanel();
		this.secondLine.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
		
		drawPowerList();
		drawElementList();
		drawHeroDrinksList();
		
		this.secondLine.repaint();
		this.centerPanelGBC.gridy++;
		this.centerPanel.add(this.secondLine, this.centerPanelGBC);
	}
	
	private void drawThirdLine() {
		this.thirdLine = new JPanel();
		this.thirdLine.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
		
		drawCriticalList();
		drawBerserkList();
		drawRowList();
		
		this.thirdLine.repaint();
		this.centerPanelGBC.gridy++;
		this.centerPanel.add(this.thirdLine, this.centerPanelGBC);
	}

	private void drawFourthLine() {
		this.fourthLine = new JPanel();
		this.fourthLine.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
		
		drawSplitList();
		drawFrogList();
		drawMiniList();
		
		this.fourthLine.repaint();
		this.centerPanelGBC.gridy++;
		this.centerPanel.add(this.fourthLine, this.centerPanelGBC);
	}
	
	private void drawFifthLine() {
		this.fifthLine = new JPanel();
		this.fifthLine.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
		
		drawFirstList();
		drawOptionalLists();
		
		this.fifthLine.repaint();
		this.centerPanelGBC.gridy++;
		this.centerPanel.add(this.fifthLine, this.centerPanelGBC);
	}

	private void drawOptionalLists() {
		if(!(this.selectedCharacter instanceof Yuffie)) {
			drawSecondList();
		}
		
		if(this.selectedCharacter instanceof Cloud || this.selectedCharacter instanceof Cid ||
				this.selectedCharacter instanceof RedXIII || this.selectedCharacter instanceof CaitSith ||
				this.selectedCharacter instanceof Tifa) {
			
			drawThirdList();
		}
	}

	private void drawThirdList() {
		int[] bounds = getThirdListBounds();
		String[] data = new String[bounds[1] - bounds[0] + 1];
		
		for(int i=bounds[0]; i <= bounds[1]; i++) {
			data[i - bounds[0]] = Integer.valueOf(i).toString();
		}
		
		String headerMessage = getThirdListHeaderMessage();
		
		this.thirdList = new JList<String>(data);
		this.thirdList.setSelectedIndex(data.length - 1);
		this.thirdScrollPane = new JScrollPane(this.thirdList);
		this.thirdScrollPane.setPreferredSize(new Dimension(this.width/4, this.height/10));
		
		JLabel header = Utils.drawHeader(headerMessage);
		
		this.thirdScrollPane.setColumnHeaderView(header);
		this.fifthLine.add(this.thirdScrollPane);
	}

	private void drawSecondList() {
		int[] bounds = getSecondListBounds();
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
		
		String headerMessage = getSecondListHeaderMessage();
		
		this.secondList = new JList<String>(data);
		this.secondList.setSelectedIndex(data.length - 1);
		this.secondScrollPane = new JScrollPane(this.secondList);
		this.secondScrollPane.setPreferredSize(new Dimension(this.width/4, this.height/10));
		
		JLabel header = Utils.drawHeader(headerMessage);
		
		this.secondScrollPane.setColumnHeaderView(header);
		this.fifthLine.add(this.secondScrollPane);
	}
	
	private void drawFirstList() {
		String[] partialData = Utils.limits.get(this.currentCharacter.getText().substring(10));
		String[] data = new String[partialData.length + 1];
		data[0] = "The attack is not a limit";
		System.arraycopy(partialData, 0, data, 1, partialData.length);
		
		this.firstList = new JList<String>(data);
		this.firstList.setSelectedIndex(0);
		this.firstScrollPane = new JScrollPane(this.firstList);
		this.firstScrollPane.setPreferredSize(new Dimension(this.width/4, this.height/10));
		
		JLabel header = Utils.drawHeader("If the attack is a limit, select it");
		
		this.firstScrollPane.setColumnHeaderView(header);
		this.fifthLine.add(this.firstScrollPane);
	}

	private String getThirdListHeaderMessage() {
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

	private int[] getThirdListBounds() {
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
	
	private String getSecondListHeaderMessage() {
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
		this.mini.setSelectedIndex(1);
		this.miniScrollPane = new JScrollPane(this.mini);
		this.miniScrollPane.setPreferredSize(new Dimension(this.width/4, this.height/10));
		
		JLabel header = Utils.drawHeader("Is " + this.currentCharacter.getText().substring(10) + " in mini status?");
		
		this.miniScrollPane.setColumnHeaderView(header);
		this.fourthLine.add(this.miniScrollPane);
	}

	private void drawFrogList() {
		String[] data = new String[]{"Yes", "No"};
		
		this.frog = new JList<String>(data);
		this.frog.setSelectedIndex(1);
		this.frogScrollPane = new JScrollPane(this.frog);
		this.frogScrollPane.setPreferredSize(new Dimension(this.width/4, this.height/10));
		
		JLabel header = Utils.drawHeader("Is " + this.currentCharacter.getText().substring(10) + " in frog status?");
		
		this.frogScrollPane.setColumnHeaderView(header);
		this.fourthLine.add(this.frogScrollPane);
	}

	private void drawSplitList() {
		String[] data = new String[]{"Yes", "No"};
		
		this.split = new JList<String>(data);
		this.split.setSelectedIndex(1);
		this.splitScrollPane = new JScrollPane(this.split);
		this.splitScrollPane.setPreferredSize(new Dimension(this.width/4, this.height/10));
		
		JLabel header = Utils.drawHeader("Does the attack hit multiple targets?");
		
		this.splitScrollPane.setColumnHeaderView(header);
		this.fourthLine.add(this.splitScrollPane);
	}
	
	private void drawRowList() {
		String[] data = new String[]{"Front", "Back"};
		
		this.row = new JList<String>(data);
		this.row.setSelectedIndex(0);
		this.rowScrollPane = new JScrollPane(this.row);
		this.rowScrollPane.setPreferredSize(new Dimension(this.width/4, this.height/10));
		
		JLabel header = Utils.drawHeader("Which row is " + this.currentCharacter.getText().substring(10) + " in?");
		
		this.rowScrollPane.setColumnHeaderView(header);
		this.thirdLine.add(this.rowScrollPane);
	}

	private void drawBerserkList() {
		String[] data = new String[]{"Yes", "No"};
		
		this.berserk = new JList<String>(data);
		this.berserk.setSelectedIndex(0);
		this.berserkScrollPane = new JScrollPane(this.berserk);
		this.berserkScrollPane.setPreferredSize(new Dimension(this.width/4, this.height/10));
		
		JLabel header = Utils.drawHeader("Is " + this.currentCharacter.getText().substring(10) + " in berserk status?");
		
		this.berserkScrollPane.setColumnHeaderView(header);
		this.thirdLine.add(this.berserkScrollPane);
	}

	private void drawCriticalList() {
		String[] data = new String[]{"Yes", "No"};
		
		this.critical = new JList<String>(data);
		this.critical.setSelectedIndex(0);
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
		this.heroDrink.setSelectedIndex(4);
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
		this.element.setSelectedIndex(16);
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
		this.power.setSelectedIndex(15);
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
		this.strength.setSelectedIndex(254);
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
		this.level.setSelectedIndex(98);
		this.levelScrollPane = new JScrollPane(this.level);
		this.levelScrollPane.setPreferredSize(new Dimension(this.width/4, this.height/10));
		
		JLabel header = Utils.drawHeader("Select a level");
		
		this.levelScrollPane.setColumnHeaderView(header);
		this.firstLine.add(this.levelScrollPane);
	}

	private void drawWeaponList() {
		String[] data = this.selectedCharacter.getWeapons();
		
		this.weapon = new JList<String>(data);
		this.weapon.setSelectedIndex(0);
		this.weaponScrollPane = new JScrollPane(this.weapon);
		this.weaponScrollPane.setPreferredSize(new Dimension(this.width/4, this.height/10));
		
		JLabel header = Utils.drawHeader("Select a weapon");
		
		this.weaponScrollPane.setColumnHeaderView(header);
		this.firstLine.add(this.weaponScrollPane);
	}
}