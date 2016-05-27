package com.ff7damage.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.nio.ByteBuffer;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.ff7damage.Controller;
import com.ff7damage.Event;
import com.ff7damage.Events;
import com.ff7damage.Utils;

public class RightPanel extends Observable implements Observer {
	private JPanel rightPanel;
	private JLabel targetLabel;
	private int width;
	private int height;
	private GridBagConstraints rightPanelGBC;
	
	private JScrollPane defenseScrollPane;
	private JScrollPane defendScrollPane;
	private JScrollPane sadnessScrollPane;
	private JScrollPane barrierScrollPane;
	private JScrollPane backScrollPane;
	private JScrollPane backMultiplierScrollPane;
	private JScrollPane rowScrollPane;
	private JScrollPane elementAffinitiesScrollPane;
	private JScrollPane elementMultiplierScrollPane;
	
	private JList<String> defense;
	private JList<String> defend;
	private JList<String> sadness;
	private JList<String> barrier;
	private JList<String> back;
	private JList<String> backMult;
	private JList<String> row;
	private JList<String> elementAffinities;
	private JList<String> elementMult;
	
	public RightPanel(int width, int height) {
		this.width = width;
		this.height = height;
		
		this.rightPanel = new JPanel() {
			private static final long serialVersionUID = 115006498954513639L;
			
			@Override
			public Dimension getPreferredSize() {
			      return new Dimension(width, height);
			};
		};
		
		this.targetLabel = new JLabel("Target information");
		this.rightPanel.setLayout(new GridBagLayout());
		this.rightPanelGBC = new GridBagConstraints();
	}
	
	public JPanel getRightPanel() {
		return this.rightPanel;
	}
	
	public void drawPanel() {
		this.rightPanelGBC.anchor = GridBagConstraints.NORTH;
		this.rightPanelGBC.weighty = 1.0;
		this.rightPanelGBC.gridy = 0;
		this.rightPanelGBC.insets = new Insets(5, 5, 5, 5);
		
		this.rightPanel.add(this.targetLabel, this.rightPanelGBC);
		this.rightPanelGBC.fill = GridBagConstraints.NONE;
		
		addElements();
	}

	private void addElements() {
		addDefenseList();
		addDefendList();
		addSadnessList();
		addBarrierList();
		addBackList();
		addBackMultList();
		addRowList();
		addElementAffinitiesList();
		addElementMultList();
		
		this.rightPanel.repaint();
	}

	private void addElementMultList() {
		String[] data = new String[]{"0", "0.5", "1", "2"};
		
		this.elementMult = new JList<String>(data);
		this.elementMultiplierScrollPane = new JScrollPane(this.elementMult);
		this.elementMultiplierScrollPane.setMinimumSize(new Dimension(5*this.width/6, this.height/10));
		
		JLabel header = Utils.drawHeader("Target multipier with attack element");
		this.elementMultiplierScrollPane.setColumnHeaderView(header);
		
		this.rightPanelGBC.gridy++;
		this.rightPanel.add(this.elementMultiplierScrollPane, this.rightPanelGBC);
	}

	private void addElementAffinitiesList() {
		String[] data = new String[]{"Absorbs", "Doesn't absorb"};
		
		this.elementAffinities = new JList<String>(data);
		this.elementAffinitiesScrollPane = new JScrollPane(this.elementAffinities);
		this.elementAffinitiesScrollPane.setMinimumSize(new Dimension(5*this.width/6, this.height/10));
		
		JLabel header = Utils.drawHeader("Target affinities with attack element");
		this.elementAffinitiesScrollPane.setColumnHeaderView(header);
		
		this.rightPanelGBC.gridy++;
		this.rightPanel.add(this.elementAffinitiesScrollPane, this.rightPanelGBC);
	}

	private void addRowList() {
		String[] data = new String[]{"Front", "Back"};
		
		this.row = new JList<String>(data);
		this.rowScrollPane = new JScrollPane(this.row);
		this.rowScrollPane.setMinimumSize(new Dimension(5*this.width/6, this.height/10));
		
		JLabel header = Utils.drawHeader("Which row is the target in?");
		this.rowScrollPane.setColumnHeaderView(header);
		
		this.rightPanelGBC.gridy++;
		this.rightPanel.add(this.rowScrollPane, this.rightPanelGBC);
	}

	private void addBackMultList() {
		String[] data = new String[]{"2", "4", "8"};
		
		this.backMult = new JList<String>(data);
		this.backMultiplierScrollPane = new JScrollPane(this.backMult);
		this.backMultiplierScrollPane.setMinimumSize(new Dimension(5*this.width/6, this.height/10));
		
		JLabel header = Utils.drawHeader("Select target back attack multiplier");
		this.backMultiplierScrollPane.setColumnHeaderView(header);
		
		this.rightPanelGBC.gridy++;
		this.rightPanel.add(this.backMultiplierScrollPane, this.rightPanelGBC);
	}

	private void addBackList() {
		String[] data = new String[]{"Yes", "No"};
		
		this.back = new JList<String>(data);
		this.backScrollPane = new JScrollPane(this.back);
		this.backScrollPane.setMinimumSize(new Dimension(5*this.width/6, this.height/10));
		
		JLabel header = Utils.drawHeader("Is the target attacked on its back?");
		this.backScrollPane.setColumnHeaderView(header);
		
		this.rightPanelGBC.gridy++;
		this.rightPanel.add(this.backScrollPane, this.rightPanelGBC);
	}

	private void addBarrierList() {
		String[] data = new String[]{"Yes", "No"};
		
		this.barrier = new JList<String>(data);
		this.barrierScrollPane = new JScrollPane(this.barrier);
		this.barrierScrollPane.setMinimumSize(new Dimension(5*this.width/6, this.height/10));
		
		JLabel header = Utils.drawHeader("Is the target in barrier status?");
		this.barrierScrollPane.setColumnHeaderView(header);
		
		this.rightPanelGBC.gridy++;
		this.rightPanel.add(this.barrierScrollPane, this.rightPanelGBC);
	}

	private void addSadnessList() {
		String[] data = new String[]{"Yes", "No"};
		
		this.sadness = new JList<String>(data);
		this.sadnessScrollPane = new JScrollPane(this.sadness);
		this.sadnessScrollPane.setMinimumSize(new Dimension(5*this.width/6, this.height/10));
		
		JLabel header = Utils.drawHeader("Is the target in sadness status?");
		this.sadnessScrollPane.setColumnHeaderView(header);
		
		this.rightPanelGBC.gridy++;
		this.rightPanel.add(this.sadnessScrollPane, this.rightPanelGBC);
	}

	private void addDefendList() {
		String[] data = new String[]{"Yes", "No"};
		
		this.defend = new JList<String>(data);
		this.defendScrollPane = new JScrollPane(this.defend);
		this.defendScrollPane.setMinimumSize(new Dimension(5*this.width/6, this.height/10));
		
		JLabel header = Utils.drawHeader("Is the target defending?");
		this.defendScrollPane.setColumnHeaderView(header);
		
		this.rightPanelGBC.gridy++;
		this.rightPanel.add(this.defendScrollPane, this.rightPanelGBC);
	}

	private void addDefenseList() {
		String[] data = new String[513];
		
		for(int i=0; i < 513; i++) {
			data[i] = Integer.valueOf(i).toString();
		}
		
		this.defense = new JList<String>(data);
		this.defenseScrollPane = new JScrollPane(this.defense);
		this.defenseScrollPane.setMinimumSize(new Dimension(5*this.width/6, this.height/10));
		
		JLabel header = Utils.drawHeader("Select target defense");
		this.defenseScrollPane.setColumnHeaderView(header);
		
		this.rightPanelGBC.gridy++;
		this.rightPanel.add(this.defenseScrollPane, this.rightPanelGBC);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof Event){
			Event e = (Event) arg;
		
			if(o instanceof Controller) {
				manageControllerEvent(e);
			}
		}
	}

	private void manageControllerEvent(Event e) {
		if(e.getCode().equals(Events.PLEASE_SEND_RIGHT_PANEL_DATA)) {
			byte[] data = collectData();
			Event newEvent = new Event(Events.SENDING_RIGHT_PANEL_DATA, data);
			
			setChanged();
			notifyObservers(newEvent);
		}
	}

	private byte[] collectData() {
		/*
		 * target defense: 2 bytes (0x0000-0x0200)
		 * target defending: 1 byte
		 *  0x00 false, 0x01 true
		 * target in sadness: 1 byte
		 *  0x00 false, 0x01 true
		 * target in barrier: 1 byte
		 *  0x00 false, 0x01 true
		 * target back attacked: 1 byte
		 *  0x00 false, 0x01 true
		 * target back attack multiplier: 1 byte
		 *  0x02, 0x04, 0x08
		 * target row: 1 byte
		 *  0x00 back, 0x01 front
		 * elemental affinities: 1 byte
		 *  0x00 doesn't absorb, 0x01 absorbs
		 * elemental multiplier: 1 byte
		 *  0x00 0, 0x01 1, 0x02 2, 0x03 0.5
		 */
		
		int length = 10;
		ByteBuffer dataBuffer = ByteBuffer.allocate(length);
		
		dataBuffer.put(Utils.stringToShortToByteArray(this.defense.getSelectedValue()));
		dataBuffer.put(Utils.stringToByteCode(this.defend.getSelectedValue()));
		dataBuffer.put(Utils.stringToByteCode(this.sadness.getSelectedValue()));
		dataBuffer.put(Utils.stringToByteCode(this.barrier.getSelectedValue()));
		dataBuffer.put(Utils.stringToByteCode(this.back.getSelectedValue()));
		dataBuffer.put(Utils.stringToHexToByte(this.backMult.getSelectedValue()));
		dataBuffer.put(Utils.stringToByteCode(this.row.getSelectedValue()));
		dataBuffer.put(Utils.stringToByteCode(this.elementAffinities.getSelectedValue()));
		dataBuffer.put(Utils.getElementalMultiplier(this.elementMult.getSelectedValue()));
		
		return dataBuffer.array();
	}
}