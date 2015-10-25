
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import java.util.ArrayList;
import java.util.List;

/**
 * Generador Estats Hill-Climbing mode 2. Busqueda local. Aquest operadors
 * necesiten que les furgos de la solucio inicial estiguin completament
 * asignats.
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
			if (Estat.configuracio_inicial[e] > 0 && estacions[e] > 0)
			{
				for (int f = 0; f < Main.nfurgos; ++f)
				{
					Estat nou = estat.copia();
					Furgo fnou = nou.furgos[f];

					int bicis = Math.min(Estat.configuracio_inicial[e],
							getDemanda(estacions, fnou));

					fnou.enviar(0, e, bicis);
					int bicis2 = Math.min(bicis, -nopositiu(estacions[fnou.dest[1].i1]));
					fnou.mod_bicis(1, -bicis2);
					fnou.mod_bicis(2, bicis2 - bicis);
					R.add(new Successor("123:)", nou));
				}
			} else if (Estat.configuracio_inicial[e] < 0)
			{
				for (int f = 0; f < Main.nfurgos; ++f)
				{
					if (!estat.furgos[f].has_anat(e))
					{
						for (int me = 1; me < Furgo.MAX_VIAJES; ++me)
						{
							Estat nou = estat.copia();
							Furgo fnou = nou.furgos[f];
							fnou.dest[me].i1 = e;

							int bicis = Math.min(nonegatiu(Estat.configuracio_inicial[fnou.dest[0].i1]),
									getDemanda(estacions, fnou));

							fnou.mod_bicis(0, bicis);
							int bicis2 = Math.min(bicis,
									-nopositiu(estacions[fnou.dest[1].i1]));
							fnou.mod_bicis(1, -bicis2);
							fnou.mod_bicis(2, bicis2 - bicis);
							R.add(new Successor("e=" + e + "f=" + f + "me=" + me + "a="
									+ nou.furgos[f].dest[0].i2
									+ "b=" + nou.furgos[f].dest[1].i2
									+ "c=" + nou.furgos[f].dest[2].i2, nou));
						}
					}
				}
			}
		}

		return R;
	}

	private int nopositiu(int a)
	{
		return a > 0 ? 0 : a;
	}

	private int nonegatiu(int a)
	{
		return a < 0 ? 0 : a;
	}

	/**
	 * Comproba si alguna furgo te com a inici la estacio e
	 *
	 * @param estat
	 * @param e
	 *
	 * @return
	 */
	private boolean furgoEstacio(Estat estat, int e)
	{
		for (Furgo f : estat.furgos)
		{
			if (e == f.dest[0].i1)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Retorna la demanda de dos estacions com a valor positiu
	 *
	 * @param estacions
	 * @param e1
	 * @param e2
	 *
	 * @return
	 */
	@Deprecated
	private int getDemanda(Integer[] estacions, int e1, int e2)
	{
		return -(estacions[e1] > 0 ? 0 : estacions[e1]
				+ estacions[e2] > 0 ? 0 : estacions[e2]);
	}

	private int getDemanda(final Integer[] estacions, final Furgo f)
	{
		return -(estacions[f.dest[1].i1] > 0 ? 0 : estacions[f.dest[1].i1]
				+ estacions[f.dest[2].i1] > 0 ? 0 : estacions[f.dest[2].i1]);
	}
}
