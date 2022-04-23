package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;
import simulator.model.Weather;

public class MapByRoadComponent extends JComponent implements TrafficSimObserver{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int _JRADIUS = 10;

	private static final Color _BG_COLOR = Color.WHITE;
	private static final Color _JUNCTION_COLOR = Color.BLUE;
	private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
	private static final Color _GREEN_LIGHT_COLOR = Color.GREEN;
	private static final Color _RED_LIGHT_COLOR = Color.RED;
	private Image _weather, _cloud, _rain, _storm, _sun, _wind, _car;
	private Image cont_0, cont_1, cont_2, cont_3, cont_4, cont_5;
	private Image contClass;
	private Image weatherImg;
	private RoadMap _map;
	private Color jColor;


	public MapByRoadComponent(Controller ctrl) {
		initGUI();
		setPreferredSize (new Dimension (300, 200));
		ctrl.addObserver(this);
	}
	
	private void initGUI() {
		_car = loadImage("car.png");
		_cloud = loadImage("cloud.png");
		_rain = loadImage("rain.png");
		_storm = loadImage("storm.png");
		_sun = loadImage("sun.png");
		_wind = loadImage("wind.png");
		
		cont_0 = loadImage("cont_0.png");
		cont_1 = loadImage("cont_1.png");
		cont_2 = loadImage("cont_2.png");
		cont_3 = loadImage("cont_3.png");
		cont_4 = loadImage("cont_4.png");
		cont_5 = loadImage("cont_5.png");
	}

	public void paintComponent(Graphics g) {
//		super.paintComponent(graphics);
//		Graphics2D g = (Graphics2D) graphics;
//		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// clear with a background color
		g.setColor(_BG_COLOR);
		g.clearRect(0, 0, getWidth(), getHeight());

		if (_map == null || _map.getJunctions().size() == 0) {
			g.setColor(Color.red);
			g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
		} else {
			updatePrefferedSize();
			drawMap(g);
		}
	}

	private void updatePrefferedSize() {
		int maxW = 200;
		int maxH = 200;
		for (Junction j : _map.getJunctions()) {
			maxW = Math.max(maxW, j.getX());
			maxH = Math.max(maxH, j.getY());
		}
		maxW += 20;
		maxH += 20;
		if (maxW > getWidth() || maxH > getHeight()) {
			setPreferredSize(new Dimension(maxW, maxH));
			setSize(new Dimension(maxW, maxH));
		}
	}


	private void drawMap(Graphics g) {
		drawRoads(g);
		drawVehicles(g);
		drawJunctions(g);
		drawCont(g);
		drawWeather(g);
	}

	private void drawRoads(Graphics g) {
		for (Road r : _map.getRoads()) {
			int i = _map.getRoads().indexOf(r);
			
			// the road goes from (x1,y1) to (x2,y2)
			int x1 = 50;
			int x2 = getWidth()-100;
			int y = (i+1)*50;

			//Road label
			g.setColor(Color.black);
			g.drawString(r.getId(), x1 - 30, y + 2);
			
			//Color lineColor = new Color(BLACK);
			g.setColor(Color.black);
			g.drawLine(x1, y, x2, y);
			
			//Find circle colors
			jColor = _RED_LIGHT_COLOR;
			int idx = r.getDest().getGreenLightIndex();
			if (idx != -1 && r.equals(r.getDest().getInRoads().get(idx))) {
				jColor = _GREEN_LIGHT_COLOR;
			}
			
			g.setColor(_JUNCTION_COLOR);
			g.fillOval(x1 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
			
			g.setColor(jColor);
			g.fillOval(x2 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
			}

	}


	private void drawVehicles(Graphics g) {
		for (Vehicle v : _map.getVehicles()) { //ERROR: w cannot be computed?
			if (v.getStatus() != VehicleStatus.ARRIVED) {

				Road r = v.getRoad();
	
				
				// The calculation below compute the coordinate (vX,vY) of the vehicle on the
				// corresponding road. It is calculated relatively to the length of the road, and
				// the location on the vehicle.
				int x = r.getSrc().getX();
				int y = r.getSrc().getY();

				double f = ((float)v.getLocation()) / r.getLength();
				int vX = (int)(x + (x)*f); 

				// Choose a color for the vehcile's label and background, depending on its
				// contamination class
				int vLabelColor = (int) (25.0 * (10.0 - (double) v.getContClass()));
				g.setColor(new Color(0, vLabelColor, 0));

				// draw an image of a car (with circle as background) and it identifier
//				g.fillOval(x1, y - 6, 14, 14);
				g.drawImage(_car, vX, y - 6, 12, 12, this);
				g.drawString(v.getId(), vX, y - 6);
			}
		}
	}
	
	private void drawCont(Graphics g) {
		for (Road r : _map.getRoads()) { 
	
				int c = r.getTotalCO2();
				int limitC = r.getContLimit();
				
				int cClass = (int) Math.floor(Math.min((double)c/(1.0 + (double)limitC),1.0) / 0.19);
				//where A is the total CO2 and B is the CO2 limit of the road, and then use the image
				//cont_C.png (i.e., cont_0.png, cont_1.png, etc.
						
				switch (cClass) {
				case 0:
					contClass = cont_0;
					break;
				case 1:
					contClass = cont_1;
					break;
				case 2:
					contClass = cont_2;
					break;
				case 3:
					contClass = cont_3;
					break;
				case 4:
					contClass = cont_4;
					break;
				case 5:
					contClass = cont_5;
					break;
				}
				
				int x = r.getSrc().getX();
				int y = r.getSrc().getY();

				// draw an image of contamination
				g.drawImage(contClass, x + 385, y - 18, 32, 32, this);
		}
	}
	private void drawWeather(Graphics g) {
		for (Road r : _map.getRoads()) { 
			Weather w = r.getWeather();
			switch (w) {
			case SUNNY:
				weatherImg = _sun;
				break;
			case CLOUDY:
				weatherImg = _cloud;
				break;
			case RAINY:
				weatherImg = _rain;
				break;
			case WINDY:
				weatherImg = _wind;
				break;
			case STORM:
				weatherImg = _storm;
				break;
			}
	
			int x = r.getSrc().getX();
			int y = r.getSrc().getY();
			
			
			// draw an image of a weather per road
			g.drawImage(weatherImg, x + 350, y - 18, 32, 32, this);
		}
	}

	private void drawJunctions(Graphics g) {
		for (Junction j : _map.getJunctions()) {

			// (x,y) are the coordinates of the junction
			int x = 50;
			int y = j.getY();

			// draw a circle with center at (x,y) with radius _JRADIUS
			g.setColor(_JUNCTION_COLOR);
			g.fillOval(x - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);

			// draw the junction's identifier at (x,y)
			g.setColor(_JUNCTION_LABEL_COLOR);
			g.drawString(j.getId(), x, y - 10);
		}
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {

	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		this._map = map;
		repaint();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		this._map = map;
		repaint();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this._map = map;
		repaint();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		this._map = map;
		repaint();
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}
	
	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} catch (IOException e) {
		}
		return i;
	}

}
