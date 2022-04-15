//test
package extra.dialog;

import javax.swing.*;

import simulator.model.Vehicle;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class DialogWindow extends JFrame {


	public DialogWindow() {
		super("Custom Dialog ");
		initGUI();
	}

	private void initGUI() {

		JPanel mainPanel = new JPanel();
		this.setContentPane(mainPanel);

		mainPanel.add(new JLabel("Click "));
		JButton here = new JButton("HERE");
		here.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				select_food();
			}
		});
		mainPanel.add(here);
		mainPanel.add(new JLabel(" to select your food"));

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);

	}

//	protected void load() {
//
//		// if you're in a JPanel class, you can use the following
//		//
//		// (Frame) SwingUtilities.getWindowAncestor(this)
//		//
//		// in order to get the parent JFrame. Then pass it to the constructor
//		// of MyDialogWindow instead of 'this'
//		//
//
//		MyDialogWindow dialog = new MyDialogWindow(this);
//
//		List<Dish> dishes = new ArrayList<Dish>();
//		for (int i = 0; i < 10; i++) {
//			dishes.add(new Dish("Yum Yum " + i));
//		}
//
//		int status = dialog.open(dishes);
//
//		if (status == 0) {
//			System.out.println("Canceled");
//		} else {
//			System.out.println("Your favorite dish is: " + dialog.getDish());
//		}
//	}
	
	//dialog for changing contamination
	protected void changeCont() {

		// if you're in a JPanel class, you can use the following
		//
		// (Frame) SwingUtilities.getWindowAncestor(this)
		//
		// in order to get the parent JFrame. Then pass it to the constructor
		// of MyDialogWindow instead of 'this'
		//

		ChangeCO2ClassDialog dialog = new ChangeCO2ClassDialog(this);

		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		for (int i = 0; i < 10; i++) {
			//vehicles.add(new Vehicle); // how do we get all vehicles on the road?
		}

		int status = dialog.open(vehicles);

		if (status == 0) {
			System.out.println("Cancelled");
		} else {
			System.out.println("Selected vehicles: " + dialog.getVehicle());
		}
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new DialogWindow();
			}
		});
	}
}