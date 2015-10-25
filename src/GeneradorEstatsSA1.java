
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneradorEstatsSA1 implements SuccessorFunction {

	@Override
	public List getSuccessors(Object o)
	{
		List<Object> R = new ArrayList();
		Estat estat = (Estat) o;
		Integer estacions[] = estat.estacions();
		Random rand = new Random();

		//Operado 1 (Entregar bicicletes)
		int i = rand.nextInt(Main.nfurgos);
		int j = rand.nextInt(Main.nestacions);
		// nomes deixem bicis a les estacions que falten
		if (estat.furgos[i].pot_entregar() && estacions[j] < 0)
		{
			Estat nouestat = estat.copia();
			// deixem el minim entre les que tenim i les que fan falta
			int bicis = -(Math.min(nouestat.furgos[i].getbicicletes(),
					-estacions[j]));
			nouestat.furgos[i].enviar(j, bicis);
			nouestat.furgos[i].canonizar();
			R.add(new Successor("E E:" + j + " F:" + i + " Q:" + bicis, nouestat));
		} else if (estat.furgos[i].pot_recollir() && estacions[j] > 0)
		{
			Estat nouestat = estat.copia();
			// agafem el min entre el que podem portar i el que sobra.
			int bicis = Math.min(Furgo.MAX, estacions[j]);
			nouestat.furgos[i].enviar(j, bicis);
			R.add(new Successor("R E:" + j + " F:" + i + " Q:" + bicis, nouestat));
		}

		return R;
	}
}
