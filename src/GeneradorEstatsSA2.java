
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneradorEstatsSA2 implements SuccessorFunction {

	@Override
	public List getSuccessors(Object state)
	{
		List<Object> R = new ArrayList<>();
		Estat estat = (Estat) state;
		Integer estacions[] = estat.estacions();
		Random rand = new Random();

		int e = rand.nextInt(Main.nestacions);
		if (Estat.configuracio_inicial[e] > 0 && estacions[e] > 0)
		{
			int f = rand.nextInt(Main.nfurgos);
			Estat nou = estat.copia();
			Furgo fnou = nou.furgos[f];

			int bicis = Math.min(Estat.configuracio_inicial[e],
					GeneradorEstatsHC2.getDemanda(estacions, fnou));

			fnou.enviar(0, e, bicis);
			int bicis2 = Math.min(bicis, -nopositiu(estacions[fnou.dest[1].i1]));
			fnou.mod_bicis(1, -bicis2);
			fnou.mod_bicis(2, bicis2 - bicis);
			R.add(new Successor("123:)", nou));

		} else if (Estat.configuracio_inicial[e] < 0)
		{
			int f = rand.nextInt(Main.nfurgos);
			if (!estat.furgos[f].has_anat(e))
			{
				int me = rand.nextInt(Furgo.MAX_VIAJES - 1) + 1;
				Estat nou = estat.copia();
				Furgo fnou = nou.furgos[f];
				fnou.dest[me].i1 = e;

				int bicis = Math.min(nonegatiu(Estat.configuracio_inicial[fnou.dest[0].i1]),
						GeneradorEstatsHC2.getDemanda(estacions, fnou));

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

}

