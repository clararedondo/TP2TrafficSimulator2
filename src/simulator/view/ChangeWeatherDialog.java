package simulator.view;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import simulator.model.Road;
import simulator.model.Vehicle;
import simulator.model.Weather;

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

public class ChangeWeatherDialog extends JDialog{

	private static final long serialVersionUID = 1L;

	private int _status;
//	private JComboBox<Vehicle> vehicles;
//	private DefaultComboBoxModel<Vehicle> vehiclesModel;
	private JComboBox<Road> roads;
	private DefaultComboBoxModel<Road> roadsModel;
	private JComboBox<String> types;
	private JSpinner ticks;

	public ChangeWeatherDialog(Frame parent) {
		super(parent, true);
		initGUI();
	}

	private void initGUI() {
		_status = 0;
		setTitle("Change Weather");
		JPanel mainPanel = new JPanel();
		
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);

		JLabel helpMsg = new JLabel("Schedule an event to change weather of a "
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

		
		roadsModel = new DefaultComboBoxModel<>();
		roads = new JComboBox<>(roadsModel);

		viewsPanel.add(new JLabel("Road: "));
		viewsPanel.add(roads);
		
		String[] typesList = {"SUNNY", "CLOUDY", "RAINY", "WINDY", "STORM"};

		types = new JComboBox<String>(typesList);
		types.setSelectedIndex(0);
		types.setToolTipText("Weather:");
		
		viewsPanel.add(new JLabel("Weather:"));
		
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
				ChangeWeatherDialog.this.setVisible(false);
			}
		});
		buttonsPanel.add(cancelButton);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (roadsModel.getSelectedItem() != null) {
					_status = 1;
					ChangeWeatherDialog.this.setVisible(false);
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
	
	public int open(List<Road> roads) {

		// update the comboxBox model -- if you always use the same no
		// need to update it, you can initialize it in the constructor.
		//
		roadsModel.removeAllElements();
		for (Road r : roads)
			roadsModel.addElement(r);

		// You can change this to place the dialog in the middle of the parent window.
		// It can be done using using getParent().getWidth, this.getWidth(),
		// getParent().getHeight, and this.getHeight(), etc.
		//
		setLocation(getParent().getLocation().x + 10, getParent().getLocation().y + 10);

		setVisible(true);
		return _status;
	}

	Road getRoads() {
		return (Road) roadsModel.getSelectedItem();
	}
	
	 protected Weather getTypes() {
			return Weather.parse(types.getSelectedItem());
		}

	public int getTicks() {
		return (int) ticks.getValue();
	}

}

