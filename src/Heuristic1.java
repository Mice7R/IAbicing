
import aima.search.framework.HeuristicFunction;


public class Heuristic1 implements HeuristicFunction{

	@Override
	public double getHeuristicValue(Object arg0) {
		Estat estat = (Estat) arg0;
                return -estat.eurus(0);
                /*double valor = 0;
                Integer[] estacions = estat.estacions();
                for (int i = 0; i < estat.furgos.length; ++i) {
                    int agafo = estat.furgos[i].dest[0].i2;
                    if (agafo > estacions[estat.furgos[i].dest[0].i1]) {
                        valor += agafo - estacions[estat.furgos[i].dest[0].i1];
                        valor -= 10*estacions[estat.furgos[i].dest[0].i1];
                    }
                    else valor -= 10*agafo;
                    int deixo1 = estat.furgos[i].dest[1].i2;
                    valor -= 10*deixo1;
                    valor -= 10*estat.furgos[i].dest[2].i2;
                }
                return valor;*/
                /*
                double r = 0;
		for ( int i = 0; i < Main.nfurgos; ++i )
		{
			r += -( estat.furgos[i].i > 0 ? estat.furgos[i].dest[0].i2 : 0);
			for ( int j = 1; j < estat.furgos[i].i; ++j )
			{
				r += 10*estat.furgos[i].dest[j].i2;
			}
		}
		return r;*/
	}

}
