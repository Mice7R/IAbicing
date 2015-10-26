
import aima.search.framework.HeuristicFunction;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

	// problema per defecte
	public static Integer nfurgos = 5;
	public static Integer nestacions = 25;
	public static Integer nbic = 1250;
	public static Integer demanda = 0;
	public static Integer seed = 1234;
	private static int heur = 0; // heuristic
	private static int algo = 0; // Hill
	private static int inicial = 0;

	// Simulated Annealing
	private static int steps = 5000;
	private static int stiter = 200;
	private static int k = 2;
	private static double lamb = 0.0001;

	public static IA.Bicing.Estaciones Problema;

	public static void main(String[] args)
	{

		BufferedReader scan = new BufferedReader(new InputStreamReader(System.in));
		switch (args.length)
		{
			case 7:
			case 6:
				steps = parse(scan, steps, "SA:steps?");
				stiter = parse(scan, stiter, "SA:stiter?");
				k = parse(scan, k, "SA:k?");
				lamb = parse(scan, lamb, "SA:lamb?");
			case 5:
				seed = Integer.parseInt(args[4]);
			case 4:
				nfurgos = Integer.parseInt(args[3]);
			case 3:
				demanda = Integer.parseInt(args[2]);
			case 2:
				nbic = Integer.parseInt(args[1]);
			case 1:
				nestacions = Integer.parseInt(args[0]);
		}

		switch (args.length)
		{
			case 0:
				nestacions = parse(scan, nestacions, "Numero de estacions");
			case 1:
				nbic = parse(scan, nbic, "Numero de bicicletes");
			case 2:
				demanda = parse(scan, demanda, "Demanda 0=equilibrada 1=rush");
			case 3:
				nfurgos = parse(scan, nfurgos, "Numero de furgos");
			case 4:
				seed = (int) (Math.random() * 10000);
				System.out.printf("Seed=%d\n", seed);
		}

		Problema = new IA.Bicing.Estaciones(nestacions, nbic, demanda, seed);


		// calcular configuracion inicial de las estaciones
		Estat.calcula_conf_inicial();

		if (args.length == 7)
		{
			heur = 1;
			algo = 1;
			inicial = 2;
		} else
		{
			heur = parse(scan, heur, "Considerar l'us de combustible? 0=NO 1=SI");
			algo = parse(scan, algo, "Algorisme 0=HillClimbing 1=SimulatedAnnealing");
			inicial = parse(scan, inicial, "Com crear la solucio inicla? 0=Buida 1=Ordenada 2=Random");
		}

		long startTime = System.nanoTime();
		switch (algo)
		{
			case 0:
				HillClimbingSearch();
				break;
			case 1:
				SimulatedAnnealing();
				break;
			default:
				System.err.println("BAD ALGORITHM");
		}
		System.out.println("Temps: " + (System.nanoTime() - startTime)
				/ 1000000);

	}

	/**
	 * Parsejar els valors llegits
	 *
	 * @param scan D'on es llegeixen les dades
	 * @param o    Una dada per defecte
	 * @param s    Un string per mostrar
	 *
	 * @return El int llegit
	 */
	private static int parse(BufferedReader scan, int o, String s)
	{
		System.out.printf("%s [%d] ", s, o);
		String r;
		try
		{
			r = scan.readLine();
			if (!"".equals(r.trim()))
			{
				return Integer.parseInt(r);
			}
		} catch (IOException | NumberFormatException ex)
		{
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
		return o;
	}

	private static double parse(BufferedReader scan, double o, String s)
	{
		System.out.printf("%s [%f] ", s, o);
		String r;
		try
		{
			r = scan.readLine();
			if (!"".equals(r.trim()))
			{
				return Double.parseDouble(r);
			}
		} catch (IOException | NumberFormatException ex)
		{
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
		return o;
	}

	private static void amañar(int e, int x, int y, int no, int d)
	{
		Problema.get(e).setCoordX(x);
		Problema.get(e).setCoordY(y);
		Problema.get(e).setNumBicicletasNoUsadas(no);
		Problema.get(e).setNumBicicletasNext(no);
		Problema.get(e).setDemanda(d);
	}

	private static void SayHello(Estat e, HeuristicFunction h)
	{
		e.generar_solucion(inicial);
		e.mostrar_solucion();
		System.out.println("Cost inicial (Heuristic): "
				+ h.getHeuristicValue(e));
		System.out.println("Const inicial (Eurus): " + e.eurus(heur));
	}

	private static void SayGoodBye(Estat e, HeuristicFunction h)
	{
		e._end();
		e.mostrar_solucion();
		System.out.println("Cost final (Heuristic): " + h.getHeuristicValue(e));
		System.out.println("Cost final (Eurus): " + e.eurus(heur));
		System.out.println("Distancia total recorrida: " + e.distancia_total() + " Km");
	}

	private static HeuristicFunction getheuristic(int mode) throws Exception
	{
		switch (mode)
		{
			case 0:
				return (inicial == 0 ? new Heuristic1HC1() : new Heuristic1HC2());
			case 1:
				return (inicial == 0 ? new Heuristic2HC1() : new Heuristic2HC2());
			default:
				System.err.println("BAD HEURISTIC");
				throw new Exception("BAD HEUR");
		}
	}
	private static void SimulatedAnnealing()
	{
		System.out.printf("steps=%d stiter=%d k=%d lamb=%f\n", steps, stiter, k, lamb);
		try
		{
			HeuristicFunction h = getheuristic(heur);
			Estat e = new Estat(nfurgos);
			SayHello(e, h);
			Problem problem = new Problem(e,
					(inicial == 0 ? new GeneradorEstatsSA1() : new GeneradorEstatsSA2()),
					new Poker(), h);
			Search search = new SimulatedAnnealingSearch(steps, stiter, k, lamb);
			SearchAgent agent = new SearchAgent(problem, search);

			printInstrumentation(agent.getInstrumentation());
			SayGoodBye((Estat) search.getGoalState(), h);
		} catch (Exception ex)
		{
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private static void HillClimbingSearch()
	{
		try
		{
			HeuristicFunction h = getheuristic(heur);
			Estat e = new Estat(nfurgos);
			SayHello(e, h);
			Problem problem = new Problem(e,
					(inicial == 0 ? new GeneradorEstatsHC1() : new GeneradorEstatsHC2()),
					new Poker(), h);
			Search search = new HillClimbingSearch();
			SearchAgent agent = new SearchAgent(problem, search);

			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
			SayGoodBye((Estat) search.getGoalState(), h);

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static void printInstrumentation(Properties properties)
	{
		Iterator keys = properties.keySet().iterator();
		while (keys.hasNext())
		{
			String key = (String) keys.next();
			String property = properties.getProperty(key);
			System.out.println(key + " : " + property);
		}

	}

	private static void printActions(List actions)
	{
		if (algo == 0)
		{
			for (Object action1 : actions)
			{
				String action = (String) action1;
				System.out.println(action);
			}
		}

		System.out.println("Passos: " + actions.size());
	}

}
