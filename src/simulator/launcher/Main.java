package simulator.launcher;

import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;


import simulator.factories.*;
import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.TrafficSimulator;
import simulator.control.Controller;

public class Main {

	private final static Integer _timeLimitDefaultValue = 10;
	private static String _inFile = null;
	private static String _outFile = null;
	private static Factory<Event> _eventsFactory = null;
	private static int ticks;
	private static String t;
	
	
	private static void parseArgs(String[] args) {

		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			parseHelpOption(line, cmdLineOptions);
			parseInFileOption(line);
			parseOutFileOption(line);
			parseTickOption(line);
			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	private static void parseTickOption(CommandLine line) throws ParseException {
		
		if (line.hasOption("t")) {
			t = line.getOptionValue("t");
			if (Integer.parseInt(t) < 0 ) throw new IllegalArgumentException("Number of ticks must be positive");

			else ticks = Integer.parseInt(t);
			
		}
		else ticks = _timeLimitDefaultValue;

	}

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Events input file").build());
		cmdLineOptions.addOption(
				Option.builder("o").longOpt("output").hasArg().desc("Output file, where reports are written.").build());
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message").build());
		cmdLineOptions.addOption(Option.builder("t").longOpt("tick").hasArg().desc("Ticks to the simulator's main loop. "
				+ "Default value is " + _timeLimitDefaultValue + ".").build());

		return cmdLineOptions;
	}

	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}

	private static void parseInFileOption(CommandLine line) throws ParseException {
		_inFile = line.getOptionValue("i");
		if (_inFile == null) {
			throw new ParseException("An events file is missing");
		}
	}

	private static void parseOutFileOption(CommandLine line) throws ParseException {
		_outFile = line.getOptionValue("o");
	}

	private static void initFactories() {
		//light switching
				ArrayList<Builder<LightSwitchingStrategy>> lsbs = new ArrayList<>();
				lsbs.add( new MostCrowdedStrategyBuilder() );
				lsbs.add( new RoundRobinStrategyBuilder() );
				Factory<LightSwitchingStrategy> lssFactory = new BuilderBasedFactory<>(lsbs);
	
		//dequeuing		
				ArrayList<Builder<DequeuingStrategy>> dqbs = new ArrayList<>();
				dqbs.add( new MoveAllStrategyBuilder() );
				dqbs.add( new MoveFirstStrategyBuilder() );
				Factory<DequeuingStrategy> dqsFactory = new BuilderBasedFactory<>(dqbs);
				
				
				ArrayList<Builder<Event>> ebs = new ArrayList<>();
				ebs.add( new NewJunctionEventBuilder(lssFactory,dqsFactory) );
				ebs.add( new NewCityRoadEventBuilder() );
				ebs.add( new NewInterCityRoadEventBuilder() );
				ebs.add( new NewVehicleEventBuilder());
				ebs.add( new SetWeatherEventBuilder());
				ebs.add( new SetContClassEventBuilder());
				
				_eventsFactory = new BuilderBasedFactory<>(ebs);


	}

	private static void startBatchMode() throws IOException {
		TrafficSimulator sim = new TrafficSimulator();
		Controller control = new Controller(sim, _eventsFactory);
		InputStream in = new FileInputStream(_inFile);

	
		OutputStream out;
	
		if (_outFile == null) {
			out = System.out;
		}
		else { out = new FileOutputStream(_outFile); }
		
		control.loadEvents(in);
		control.run(ticks, out);
		out.close();
		
		
		
		
	}

	private static void start(String[] args) throws IOException {
		initFactories();
		parseArgs(args);
		startBatchMode();
	}

	// example command lines:
	//
	// -i resources/examples/ex1.json
	// -i resources/examples/ex1.json -t 300
	// -i resources/examples/ex1.json -o resources/tmp/ex1.out.json
	// --help

	public static void main(String[] args) {
		try {
			start(args);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
