
import aima.search.framework.HeuristicFunction;

/**
 *
 * @author Mike7R
 */
public class Heuristic1HC2 implements HeuristicFunction {

	@Override
	public double getHeuristicValue(Object arg0)
	{
		Estat estat = (Estat) arg0;
		return -estat.eurus(0);
	}
}
