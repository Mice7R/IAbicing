
import aima.search.framework.HeuristicFunction;


/**
 *
 * @author debian
 */
public class Heuristic2HC2 implements HeuristicFunction {

	@Override
	public double getHeuristicValue(Object arg0)
	{
		Estat estat = (Estat) arg0;
		return -estat.eurus(1);
	}
}
