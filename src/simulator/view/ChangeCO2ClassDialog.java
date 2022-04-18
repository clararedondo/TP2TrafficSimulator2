package simulator.view;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import simulator.model.Vehicle;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ChangeCO2ClassDialog extends JDialog{

	private static final long serialVersionUID = 1L;

	private int _status;
	private JComboBox<Vehicle> vehicles;
	private DefaultComboBoxModel<Vehicle> vehiclesModel;
	private JComboBox<String> types;
	private JSpinner ticks;

	public ChangeCO2ClassDialog(Frame parent) {
		super(parent, true);
		initGUI();
	}

	private void initGUI() {
		_status = 0;
		setTitle("Change CO2 Class");
		JPanel mainPanel = new JPanel();
		
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);

		JLabel helpMsg = new JLabel("Schedule an event to change the CO2 class of a "
				+ "vehicle after a number of simulation ticks from now.");
		helpMsg.setAlignmentX(CENTER_ALIGNMENT);

		mainPanel.add(helpMsg);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		JPanel viewsPanel = new JPanel();
		viewsPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(viewsPanel);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(buttonsPanel);

		vehiclesModel = new DefaultComboBoxModel<>();
		vehicles = new JComboBox<>(vehiclesModel);

		viewsPanel.add(new JLabel("Vehicle: "));
		viewsPanel.add(vehicles);
		
		String[] typesList = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

		types = new JComboBox<String>(typesList);
		types.setSelectedIndex(0);
		types.setToolTipText("COntamination Classes:");
		
		viewsPanel.add(new JLabel("CO2 Class:"));
		
		types.setMaximumSize(new Dimension(100, 30));
		types.setPreferredSize(new Dimension(100, 30));
		
		viewsPanel.add(types);
		
		ticks = new JSpinner(new SpinnerNumberModel());
		ticks.setValue(1);
		
		viewsPanel.add(new JLabel("Ticks: "));
		
		ticks.setSize(100, 30);
		viewsPanel.add(ticks);
		
		
		// good
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 0;
				ChangeCO2ClassDialog.this.setVisible(false);
			}
		});
		buttonsPanel.add(cancelButton);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (vehiclesModel.getSelectedItem() != null) {
					_status = 1;
					ChangeCO2ClassDialog.this.setVisible(false);
				}
			}
		});
		buttonsPanel.add(okButton);

		setPreferredSize(new Dimension(500, 200));
		pack();
		setResizable(false); // true?
		setVisible(false);
	}
// to here^^
	
	public int open(List<Vehicle> vehicles) {

		// update the comboxBox model -- if you always use the same no
		// need to update it, you can initialize it in the constructor.
		//
		vehiclesModel.removeAllElements();
		for (Vehicle v : vehicles)
			vehiclesModel.addElement(v);

		// You can change this to place the dialog in the middle of the parent window.
		// It can be done using using getParent().getWidth, this.getWidth(),
		// getParent().getHeight, and this.getHeight(), etc.
		//
		setLocation(getParent().getLocation().x + 10, getParent().getLocation().y + 10);

		setVisible(true);
		return _status;
	}

	Vehicle getVehicle() {
		return (Vehicle) vehiclesModel.getSelectedItem();
	}
	
	 protected int getTypes() {
			return Integer.parseInt((String) types.getSelectedItem());
		}

	public int getTicks() {
		return (int) ticks.getValue();
	}

}

