package simulator.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToolBar;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel _currTime =   new JLabel();
	private JLabel _message = new JLabel("WELCOME! :)");
	private JLabel eventDescr = new JLabel();
	private JLabel event = new JLabel();
	
	private JToolBar toolBar;
	

	public StatusBar(Controller _ctrl) {
		initGUI();
		_ctrl.addObserver(this);
		}

	public void initGUI() {
		this.setLayout( new FlowLayout( FlowLayout.LEFT )); 
		this.setBorder( BorderFactory.createBevelBorder( 1 ));
		
		
		this.add(_message);
		
		toolBar = new JToolBar();
		this.add(toolBar, BorderLayout.PAGE_END);
		
		toolBar.add(new JLabel("Time: "));
		toolBar.add(_currTime);
		
		toolBar.addSeparator(new Dimension(100,0));
		JSeparator sep = new JSeparator(JSeparator.VERTICAL);
		sep.setPreferredSize(new Dimension(1,0));
		toolBar.add(sep);
		toolBar.addSeparator(new Dimension(4, 0));
		
		//notify events
		this.add(eventDescr);
		this.add(event);
	}
	
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		eventDescr.setText("");
		event.setText("");
		_currTime.setText(time + "");
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		this._currTime.setText("" + time);
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		eventDescr.setText("Event added (" + e.toString() + ")");
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		boolean first = true;
		for (int i = 0; i < events.size(); ++i) {
			_currTime.setText( time + "");
			
			if (events.get(i).getTime() > time + 1 && !first){
				first = false;
				eventDescr.setText("Event added (" + events.get(i).toString() + ")");
			}
		}
		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
