
import IA.Bicing.Estacion;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import java.util.ArrayList;
import java.util.List;

/**
 * Generador Estats Hill-Climbing mode 2. Busqueda local.
 *
 */
public class GeneradorEstatsHC2 implements SuccessorFunction {

	@Override
	public List getSuccessors(Object state)
	{
		List<Object> R = new ArrayList<>();
		Estat estat = (Estat) state;
		Integer estacions[] = estat.estacions();

		for (int e = 0; e < Main.nestacions; ++e)
		{
			// Operador 1: modificar_estacio
			if (Estat.configuracio_inicial[e] > 0)
			{
				for (int f = 0; f < Main.nfurgos; ++f)
				{
                                    if (!furgoEstacio(estat,e))
                                    {
                                        int bicis = Math.min(Estat.configuracio_inicial[e], 
                                                getDemanda(estacions, estat.furgos[f].dest[1].i1, estat.furgos[f].dest[2].i1));
                                        Estat nou = estat.copia();
                                        nou.furgos[f].dest[0].i1 = e;
                                        nou.furgos[f].dest[0].i2 = bicis;
                                        int bicis2 = Math.min(bicis, 
                                                -nopositiu(estacions[estat.furgos[f].dest[1].i1]));
                                        nou.furgos[f].dest[1].i2 = -bicis2;
                                        nou.furgos[f].dest[2].i2 = bicis2-bicis;
                                        R.add(new Successor("123:)", nou));
                                    }
				}
			} else if (Estat.configuracio_inicial[e] < 0)
			{
				for (int f = 0; f < Main.nfurgos; ++f)
				{
                                    if ( ! estat.furgos[f].has_anat(e))
                                    {
                                        for ( int me = 1; me < Furgo.MAX_VIAJES; ++me)
                                        {
                                            Estat nou = estat.copia();
                                            nou.furgos[f].dest[me].i1 = e;
                                            
                                         int bicis = Math.min(nonegatiu(Estat.configuracio_inicial[nou.furgos[f].dest[0].i1]), 
                                                getDemanda(estacions, nou.furgos[f].dest[1].i1, nou.furgos[f].dest[2].i1));
             
                                        nou.furgos[f].dest[0].i2 = bicis;
                                        int bicis2 = Math.min(bicis, 
                                                -nopositiu(estacions[nou.furgos[f].dest[1].i1]));
                                        nou.furgos[f].dest[1].i2 = -bicis2;
                                        nou.furgos[f].dest[2].i2 = bicis2-bicis;
                                        R.add(new Successor("e="+e+"f="+f+"me="+me+"a="
                                                +nou.furgos[f].dest[0].i2+
                                                "b="+nou.furgos[f].dest[1].i2
                                                +"c="+nou.furgos[f].dest[2].i2, nou));                                           
                                        }
                                    }
				}
			}
		}

		return R;
	}

        int nopositiu(int a)
        {
            return a>0?0:a;
        }
        int nonegatiu(int a)
        {
            return a<0?0:a;
        }
        
        
        boolean furgoEstacio(Estat estat, int e) {
            for (Furgo f: estat.furgos)
            {
                if ( e == f.dest[0].i1 ) return true;
            }
            return false;
        }
        
	/**
	 * Operador 1: Modificar Estacion. Hace que una furgoneta vaya a otra
	 * estacion.
	 *
	 * PRE: v [0,Furgo.MAX) POST: SI pot: Furgo f va a estacio e en el viatge v
	 *
	 * @param nou       El estat a modificar
	 * @param estacions
	 * @param f
	 * @param e
	 * @param v
	 */
	void modificar_estacion(List R, Estat estat, Integer[] estacions, int f,
			int e,
			int v)
	{
		if (v == 0) // recogida
		{
			if (Estat.configuracio_inicial[e] - estacions[e] == 0)
			{
				Estat nou = estat.copia();
				nou.furgos[f].dest[0].i1 = e;
				R.add(new Successor("ME: F:" + f + " E:" + e + " V:" + v, nou));
			}
		} else
		{
			if (estat.furgos[f].i >= v)
			{
				Estat nou = estat.copia();
				nou.furgos[f].dest[v].i1 = e;
				R.add(new Successor("ME: F:" + f + " E:" + e + " V:" + v, nou));
			}
		}
	}

	/**
	 * Operador 2.
	 *
	 * @param R
	 * @param estat
	 * @param f
	 * @param q
	 * @param v
	 */
	/*void modificar_cantidad(List R, Estat estat, int f, Integer q, int v)
	{
		if (estat.furgos[f].i == Furgo.MAX_VIAJES
				|| q < 0 && v == 0 || q > 0 && v != 0)
		{
			return;
		}
		Estat nou = estat.copia();
		nou.furgos[f].modificar_recogida(v, q);
		R.add(new Successor("123", nou));

	}

	int cantidad(Integer[] estacions, int e, int f)
	{
		if (estacions[e] > 0)
		{
			return Math.min(Furgo.MAX, estacions[e]);
		} else
		{
			return -Math.min()
		}
	}*/
        
        /**
         * Retorna la demanda de dos estacions com a valor positiu
         * @param estacions
         * @param e1
         * @param e2
         * @return 
         */
        private int getDemanda(Integer[] estacions, int e1, int e2)
        {
            int val1 = estacions[e1];
            if (val1 > 0) val1 = 0;
            int val2 = estacions[e2];
            if (val2 > 0) val2 = 0;
            return - (val1 + val2);
            /*
            return -(estacions[e1]>0?0:estacions[e1] + 
                    estacions[e2]>0?0:estacions[e2]);
        */}
}
