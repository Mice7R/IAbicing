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

	public static void main(String[] args) {

		if ( args.length < 4 ) return; // FIXME Faltan argumentos
		// tipus d'execucio
		demanda = Integer.parseInt(args[2]);

		// get params
		nestacions = Integer.parseInt(args[0]);
		nfurgos = Integer.parseInt(args[3]);
		nbic = Integer.parseInt(args[1]);

		seed = (int) (Math.random() * 10000);
		if (args.length == 5)
			seed = Integer.parseInt(args[4]);

		Problema = new IA.Bicing.Estaciones(nestacions,
				nbic, demanda, seed);

		// calcular configuracion inicial de las estaciones
		Estat.calcula_conf_inicial();

		long startTime = System.nanoTime();
		HillClimbingSearch();
		System.out.println("Temps: " + (System.nanoTime() - startTime)
				/ 1000000);


	}

	private static void HillClimbingSearch() {
		try {
			Estat inicial = new Estat(nfurgos);
			Heuristic1 h = new Heuristic1();
			System.out.println("\n Cost inicial: "
					+ h.getHeuristicValue(inicial));
			Problem problem = new Problem(inicial, new GeneradorEstats(),
					new Poker(), h);
			Search search = new HillClimbingSearch();
			SearchAgent agent = new SearchAgent(problem, search);


			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
			System.out.println("\n Cost final: "
					+ ((Estat) (search.getGoalState())).eurus1());//(search.goalestate()).eurus1
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void printInstrumentation(Properties properties) {
		Iterator keys = properties.keySet().iterator();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			String property = properties.getProperty(key);
			System.out.println(key + " : " + property);
		}

	}

	private static void printActions(List actions) {
		//if (mode == 1 || mode == 3) {
			for (int i = 0; i < actions.size(); i++) {
				String action = (String) actions.get(i);
				System.out.println(action);
			}
		//}

		System.out.println("Passos: " + actions.size());
	}

}
