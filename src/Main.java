
import aima.search.framework.HeuristicFunction;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
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

	public static IA.Bicing.Estaciones Problema;

	public static void main(String[] args)
	{

		switch (args.length)
		{
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

		BufferedReader scan = new BufferedReader(new InputStreamReader(System.in));
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

		/*nestacions = 3;
			nbic = 21;
		nfurgos = 1;*/
		Problema = new IA.Bicing.Estaciones(nestacions, nbic, demanda, seed);
		// Amañar problema
		/*amañar(0, 0, 0, 11, 0);
			amañar(1, 0, 1, 0, 10);
		amañar(2, 999, 999, 0, 11);*/


		// calcular configuracion inicial de las estaciones
		Estat.calcula_conf_inicial();
		//Estat.calcula_conf_inicial_triky();

		heur = parse(scan, heur, "Considerar l'us de combustible? 0=NO 1=SI");
		algo = parse(scan, algo, "Algorisme 0=HillClimbing");

		HeuristicFunction h;
		switch (heur)
		{
			case 0:
				h = new Heuristic1();
				break;
			case 1:
				h = new Heuristic2();
				break;
			default:
				System.err.println("BAD HEURISTIC");
				return;
		}

		long startTime = System.nanoTime();
		switch (algo)
		{
			case 0:
				HillClimbingSearch(h);
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

	private static void amañar(int e, int x, int y, int no, int d)
	{
		Problema.get(e).setCoordX(x);
		Problema.get(e).setCoordY(y);
		Problema.get(e).setNumBicicletasNoUsadas(no);
		Problema.get(e).setNumBicicletasNext(no);
		Problema.get(e).setDemanda(d);
	}

	private static void HillClimbingSearch(HeuristicFunction h)
	{
		try
		{
			Estat e = new Estat(nfurgos);
			e.generar_solucion(1);
			System.out.println("Cost inicial (Heuristic): "
					+ h.getHeuristicValue(e));
			System.out.println("Const inicial (Eurus): " + e.eurus(heur));
			Problem problem = new Problem(e, new GeneradorEstats(),
					new Poker(), h);
			Search search = new HillClimbingSearch();
			SearchAgent agent = new SearchAgent(problem, search);

			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
			e = (Estat) search.getGoalState();
			e.canonizar();
			System.out.println("Cost final (Heuristic): " + h.getHeuristicValue(e));
			System.out.println("Cost final (Eurus): " + e.eurus(heur));
			System.out.println("Distancia total recorrida: " + e.distancia_total() + " Km");
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
		//if (mode == 1 || mode == 3) {
		for (Object action1 : actions)
		{
			String action = (String) action1;
			System.out.println(action);
		}
		//}

		System.out.println("Passos: " + actions.size());
	}

}
