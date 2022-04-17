package simulator.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.view.MainWindow; //probando
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.SetContClassEvent;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class ControlPanel extends JPanel implements TrafficSimObserver{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFileChooser fileChooser;
	private Controller _ctrl;
	public JToolBar toolBar;
	private JButton load, changeCont, changeW, run, stop, exit;
	//for run sim method, not sure
	private MainWindow mW;
	private JSpinner ticks;
	private List<Vehicle> vehicles;
	private List<Road> roads;
	private boolean _stopped;
	private boolean hasLoaded;
	private int time;
	
	public ControlPanel(Controller c) {
		this._ctrl = c;
		initPanel();
		_ctrl.addObserver(this);
		
	}
	
	public void initPanel() {
		toolBar = new JToolBar(); //create toolbar
		this.add(toolBar, BorderLayout.PAGE_START);
		
		//initialize buttons
		//load button     
		load = new JButton(new ImageIcon ("resources/icons/open.png"));
		load.setToolTipText("Run the simulator.");
		//load.setIcon(new ImageIcon ("resources/icons/open.png"));
		
		
		//fileChooser - check this THIS IS WRONG
		fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/resources"));
		fileChooser.setMultiSelectionEnabled(false);
		
		//int fileNo = fileChooser.showOpenDialog(this.getParent());
		
		load.addActionListener( new ActionListener() { 		
			@Override
			public void actionPerformed(ActionEvent e) {
				//should open dialogue
				//try {
					//LINE HAS PROBLEMS
					loadEvents();
				//} catch (FileNotFoundException e1) {
				//	e1.printStackTrace();
				//} 
			}	
		});
		toolBar.add(load);
		
		
		
		//changeContamination button - 
		changeCont = new JButton(new ImageIcon ("resources/icons/co2class.png"));
		changeCont.setToolTipText("Change the contamination.");
	//	changeCont.setIcon(new ImageIcon ("resources/icons/co2class.png"));
		changeCont.addActionListener( new ActionListener() { 
			
			public void actionPerformed(ActionEvent e) {
				changeContamination(); //should open dialogue
			}
		});
		toolBar.add(changeCont);
		
		
		
		//changeWeather button
		changeW = new JButton(new ImageIcon ("resources/icons/weather.png"));
		changeW.setToolTipText("Change the weather.");
		//run.setIcon(new ImageIcon ("resources/icons/weather.png"));
		changeW.addActionListener( new ActionListener() { 
			
			public void actionPerformed(ActionEvent e) {
				changeWeather(); //should open dialogue
			}
		});
		toolBar.add(changeW);
		
		
	
		//run button
		run = new JButton(new ImageIcon ("resources/icons/run.png"));
		run.setToolTipText("Run the simulator.");
	//	run.setIcon(new ImageIcon ("resources/icons/run.png"));
		run.addActionListener( new ActionListener() { 
			
			public void actionPerformed(ActionEvent e) {
				_stopped = false;
				enableToolBar(false);
				run_sim((int)ticks.getValue());
			}	
		});
		toolBar.add(run);
		
		
		//stop button
		stop = new JButton(new ImageIcon ("resources/icons/stop.png"));
		stop.setToolTipText("Stop the simulator.");
		stop.addActionListener( new ActionListener() { 
			
			public void actionPerformed(ActionEvent e) {
				stop();
			}	
		});
		toolBar.add(stop);
	}
	

	private void loadEvents() {
		// complete
		//?JSONObject jo = new JSONObject(new JSONTokener(fileInputStream));
		//?JSONArray events = jo.getJSONArray("events");
	
		//have to find a way to acces trafficSim and eventsFactory
		//probably best to call a method in controller that does it
		//can i use getters and setters? idek
		
//		for (int i = 0; i < events.length(); i++) {
//			_ctrl.trafficSim.addEvent(_ctrl.evFactory.createInstance(events.getJSONObject(i)));
//		}
		//solution for now 
		
		 
		int fileInt = fileChooser.showOpenDialog(this.getParent());
		if (fileInt == JFileChooser.APPROVE_OPTION) {
			try {
				FileInputStream fIS = new FileInputStream(fileChooser.getSelectedFile());
				_ctrl.reset();
				_ctrl.loadEvents(fIS);
				//_ctrl.run(0);
				hasLoaded = true;
				
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, "[ERROR] Loading file error.", "[ERROR]", JOptionPane.ERROR_MESSAGE);
			}
		}
		//_ctrl.loadEvents2(fileInputStream, events);
		
	}

	private void changeContamination() {
		if(!hasLoaded) {
			JOptionPane.showMessageDialog(null, "The simulator will add the vehicles once you load a file.", "Warning", JOptionPane.WARNING_MESSAGE);
		}
		else{
			ChangeCO2ClassDialog co2Dialog = new ChangeCO2ClassDialog(mW);
			List<Vehicle> vehicleIDs = new ArrayList<Vehicle>(vehicles);
			int status = co2Dialog.open(vehicleIDs);
			if(status != 0) {
				List<Pair<String, Integer>> contClass = new ArrayList<Pair<String, Integer>>();
				Pair<String, Integer> id = new Pair<String, Integer>(co2Dialog.getVehicle().getId(), co2Dialog.getTypes());
				contClass.add(id);
				Event e = new SetContClassEvent(time + co2Dialog.getTicks(), contClass);
				_ctrl.addEvent(e);
			}
		}
		
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
	
	
	protected void enableToolBar(Boolean enable) {
		load.setEnabled(enable);
		changeCont.setEnabled(enable);
		changeW.setEnabled(enable);
		run.setEnabled(enable);
		ticks.setEnabled(enable);
		exit.setEnabled(enable);
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		vehicles = map.getVehicles();
		roads = map.getRoads();
		this.time = time;		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		vehicles = map.getVehicles();
		roads = map.getRoads();
		this.time = time;		
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
