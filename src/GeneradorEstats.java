import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import java.util.ArrayList;
import java.util.List;

public class GeneradorEstats implements SuccessorFunction {

	@Override
	public List getSuccessors(Object state)
	{
		ArrayList<Object> R = new ArrayList<>();
		Estat estat = (Estat) state;
		/*
		 * > 0 : Sobran bicis
		 * < 0 : Faltan bicis
		 */
		Integer estacions[] = estat.estacions();

		//Operado 1 (Entregar bicicletes)
		for ( int i = 0; i < Main.nfurgos; ++i)
		{
			// la furgo esta buida
			if (estat.furgos[i].pot_entregar())
			{
				for (int j = 0; j < Main.nestacions; ++j)
				{
					// nomes deixem bicis a les estacions que falten
					if (estacions[j] < 0)
					{
						Estat nouestat = estat.copia();
						// deixem el minim entre les que tenim i les que fan falta
						int bicis = -(Math.min(nouestat.furgos[i].getbicicletes(),
								-estacions[j]));
						nouestat.furgos[i].enviar(j, bicis);
						nouestat.furgos[i].canonizar();
						R.add(new Successor("E E:" + j + " F:" + i
								+ " Q:" + bicis, nouestat));
					}
				}
			}
		}

		// Operador 2 ( Recollir bicicletes )
		for ( int i = 0; i < Main.nfurgos; ++i)
		{
			if ( estat.furgos[i].pot_recollir() ) {
				for (int j = 0; j < Main.nestacions; ++j) {
					// Nomes recollim bicicletes d'on sobren
					if (estacions[j] > 0) {
						Estat nouestat = estat.copia();
						// agafem el min entre el que podem portar i el que sobra.
						int bicis = Math.min(Furgo.MAX, estacions[j]);
						nouestat.furgos[i].enviar(j,bicis);
						R.add(new Successor("R E:" + j + " F:" + i +
								" Q:" + bicis, nouestat));
					}
				}
			}

		}

		return R;
	}

}
