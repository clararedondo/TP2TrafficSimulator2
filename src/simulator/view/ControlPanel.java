package simulator.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class ControlPanel extends JPanel implements TrafficSimObserver{
	private Controller _ctrl;
	public JToolBar toolBar;
	private JButton load, changeCont, changeW, run, stop, exit;
	//for run sim method, not sure
	private JSpinner ticks;
	private boolean _stopped;
	
	public ControlPanel(Controller c) {
		this._ctrl = c;
		initPanel();
		_ctrl.addObserver(this);
		
	}
	
	public void initPanel() {
		toolBar = new JToolBar(); //create toolbar
		
		//initialize buttons
		//load button     
		load = new JButton();
		load.setToolTipText("Run the simulator.");
		load.setIcon(new ImageIcon ("resources/icons/open.png"));
		load.addActionListener( new ActionListener() { 
			
			public void actionPerformed(ActionEvent e) {
				loadEvents(); //should open dialogue
			}	
		});
		this.add(load);
		
		
		
		//changeContamination button - 
		changeCont = new JButton();
		changeCont.setToolTipText("Change the contamination.");
		changeCont.setIcon(new ImageIcon ("resources/icons/co2class.png"));
		changeCont.addActionListener( new ActionListener() { 
			
			public void actionPerformed(ActionEvent e) {
				changeContamination(); //should open dialogue
			}
		});
		this.add(changeCont);
		
		
		
		//changeWeather button
		changeW = new JButton();
		run.setToolTipText("Change the weather.");
		run.setIcon(new ImageIcon ("resources/icons/weather.png"));
		changeW.addActionListener( new ActionListener() { 
			
			public void actionPerformed(ActionEvent e) {
				changeWeather(); //should open dialogue
			}
		});
		this.add(changeW);
		
		
	
		//run button
		run = new JButton();
		run.setToolTipText("Run the simulator.");
		run.setIcon(new ImageIcon ("resources/icons/run.png"));
		run.addActionListener( new ActionListener() { 
			
			public void actionPerformed(ActionEvent e) {
				_stopped = false;
				enableToolBar(false);
				run_sim((int)ticks.getValue());
			}	
		});
		this.add(run);
		
		
		//stop button
		stop = new JButton();
		stop.setLocation(0, 0); 
		stop.setSize(120, 30); 
		stop.addActionListener( new ActionListener() { 
			
			public void actionPerformed(ActionEvent e) {
				stop();
			}	
		});
		this.add(stop);
	}
	

	private void loadEvents() {
		// complete
		
	}

	private void changeContamination() {
		// complete
		
	}
	

	private void changeWeather() {
		// complete
		
	}
	
	private void run_sim(int n) {
		if (n > 0 && !_stopped) {
			try {
				_ctrl.run(1);
			} catch (Exception e) {
				// TODO show error message
				JOptionPane.showMessageDialog(null, "The simulation could not be displayed.", "Error", JOptionPane.WARNING_MESSAGE);
				_stopped = true;
				return;
			}
			SwingUtilities.invokeLater(() -> run_sim(n - 1));
			
		} else {
			enableToolBar(true);
			_stopped = true;
		}
	}

	
	private void stop() {
		_stopped = true;
	}
	
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
