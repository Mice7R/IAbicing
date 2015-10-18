
import aima.search.framework.HeuristicFunction;

public class Heuristic2 implements HeuristicFunction {

	@Override
	public double getHeuristicValue(Object arg0)
	{
		Estat estat = (Estat) arg0;
		double r = 0;
		for (int i = 0; i < Main.nfurgos; ++i)
		{
			r += -(estat.furgos[i].i > 0 ? estat.furgos[i].dest[0].i2 : 0);
			for (int j = 1; j < estat.furgos[i].i; ++j)
			{
				r += 10 * (estat.furgos[i].dest[j].i2 + estat.furgos[i].coste_combustible());
			}
		}
		return r;
	}
}
