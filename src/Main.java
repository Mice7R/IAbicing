
import aima.search.framework.HeuristicFunction;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class Main {

	// problema per defecte
	public static Integer nfurgos = 5;
	public static Integer nestacions = 25;
	public static Integer nbic = 1250;
	public static Integer demanda = 0;
	public static Integer seed = 1234;

	public static IA.Bicing.Estaciones Problema;

	public static void main(String[] args)
	{

		if (args.length < 4)
		{
			return; // FIXME Faltan argumentos
		}		// tipus d'execucio
		demanda = Integer.parseInt(args[2]);

		// get params
		nestacions = Integer.parseInt(args[0]);
		nfurgos = Integer.parseInt(args[3]);
		nbic = Integer.parseInt(args[1]);

		seed = (int) (Math.random() * 10000);
		if (args.length == 5)
		{
			seed = Integer.parseInt(args[4]);
		}

		/*nestacions = 3;
			nbic = 21;
		nfurgos = 1;*/
		Problema = new IA.Bicing.Estaciones(nestacions,
		 nbic, demanda, seed);
		// Amañar problema
		/*amañar(0, 0, 0, 11, 0);
			amañar(1, 0, 1, 0, 10);
		amañar(2, 999, 999, 0, 11);*/


		// calcular configuracion inicial de las estaciones
		Estat.calcula_conf_inicial();
		//Estat.calcula_conf_inicial_triky();

		long startTime = System.nanoTime();
		HillClimbingSearch();
		System.out.println("Temps: " + (System.nanoTime() - startTime)
				/ 1000000);

	}

	private static void amañar(int e, int x, int y, int no, int d)
	{
		Problema.get(e).setCoordX(x);
		Problema.get(e).setCoordY(y);
		Problema.get(e).setNumBicicletasNoUsadas(no);
		Problema.get(e).setNumBicicletasNext(no);
		Problema.get(e).setDemanda(d);
	}

	private static void HillClimbingSearch()
	{
		try
		{
			Estat e = new Estat(nfurgos);
			HeuristicFunction h = new Heuristic2();
			System.out.println("Cost inicial (Heuristic): "
					+ h.getHeuristicValue(e));
			System.out.println("Const inicial (Eurus): " + e.eurus1());
			Problem problem = new Problem(e, new GeneradorEstats(),
					new Poker(), h);
			Search search = new HillClimbingSearch();
			SearchAgent agent = new SearchAgent(problem, search);

			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
			e = (Estat) search.getGoalState();
			e.canonizar();
			System.out.println("Cost final (Heuristic): " + h.getHeuristicValue(e));
			System.out.println("Cost final (Eurus): " + e.eurus2());
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
		for (int i = 0; i < actions.size(); i++)
		{
			String action = (String) actions.get(i);
			System.out.println(action);
		}
		//}

		System.out.println("Passos: " + actions.size());
	}

}
