
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
					modificar_estacion(R, estat, estacions, f, e, 0);
				}
			} else if (Estat.configuracio_inicial[e] < 0)
			{
				for (int f = 0; f < Main.nfurgos; ++f)
				{
					for (int i = 1; i < estat.furgos[f].i; ++i)
					{
						modificar_estacion(R, estat, estacions, f, e, i);
					}

				}
			}

			// Operador 2: modifcar_cantidad
			for (int f = 0; f < Main.nfurgos; ++f)
			{
				int q = cantidad(estacions, e, f);
				modificar_cantidad(R, estat, f, q, f);
			}
		}

		return R;
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
	void modificar_cantidad(List R, Estat estat, int f, Integer q, int v)
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
	}
}
