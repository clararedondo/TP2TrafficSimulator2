package simulator.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class ControlPanel extends JPanel implements TrafficSimObserver{
	private Controller controller;
	private JButton load, changeCont, changeWeather, run, stop, ticks, exit;
	
	public ControlPanel(Controller c) {
		this.controller = c;
	}
	
	public void initPanel() {
		//initialize buttons
		//load button     -icon, description, size
		load = new JButton();
		load.setLocation(0, 0);
		load.setSize(120, 30);
		load.addActionListener( new ActionListener() { 
			
			public void actionPerformed(ActionEvent e) {
				loadEvents();
		}

	
		});
		idkwhichPanel.add(load);
	}
	

	private void loadEvents() {
		// complete
		
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
