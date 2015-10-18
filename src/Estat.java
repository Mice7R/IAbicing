import IA.Bicing.Estacion;


public class Estat {

	public Furgo[] furgos;
	/**
	 * Configuracion inicial de las estaciones en base a las bicicletas de las
	 * que dispone. Valores positivos indican que dispone de bicicletas, valores
	 * negativos indican que faltan bicicletas.
	 */
	static public Integer[] configuracio_inicial;


	Estat(Integer nfurg)
	{
		furgos = new Furgo[nfurg];
		for ( int i = 0; i < nfurg; ++i )
			furgos[i] = new Furgo();
	}

	public Estat copia()
	{
		Estat a = new Estat(furgos.length);
		for ( int i = 0; i < furgos.length; ++i )
			a.furgos[i] = furgos[i].copia();

		return a;

	}

	public Integer size() { return furgos.length; }

	/**
	 * Calcula la configuracio de les estacions
	 * @return Configuracio de les estacions
	 */
	public Integer[] estacions()
	{
		Integer[] r = (Integer[])configuracio_inicial.clone();

		for ( int f = 0; f < Main.nfurgos; ++f )
		{
			// para cada viaje de la furgo
			for ( int i = 0; i < furgos[f].i; ++i)
			{
				iPair destino = furgos[f].dest[i];
				/* Los simbolos son distintos
				 * En Furgos positivo es que a recogido, es decir que ha quitado
				 * de la estacion. */
				r[destino.i1] -= destino.i2;
			}
		}

		return r;
	}

	/**
	 * Calcula la configuracion inicial de las estaciones.
	 * No llamar antes de que Main.problema este inicializado
	 * Se asume que Next = nousadas + c; para c >= 0
	 */
	static public void calcula_conf_inicial()
	{
		configuracio_inicial = new Integer[Main.nestacions];
		for ( int e = 0; e < Main.nestacions; ++e )
		{
			Estacion estacion = Main.Problema.get(e);
			configuracio_inicial[e] = Math.min(
					estacion.getNumBicicletasNoUsadas(),
					estacion.getNumBicicletasNext() - estacion.getDemanda());
		}
	}

	public double eurus1() {
		double r = 0;
		/*for ( int i = 0; i < Main.nfurgos; ++i )
		{
			r += -( estat.furgos[i].i > 0 ? estat.furgos[i].dest[0].i2 : 0);
			for ( int j = 1; j < estat.furgos[i].i; ++j )
			{
				r += 10*estat.furgos[i].dest[j].i2;
			}
		}*/

		for (int i = 0; i < furgos.length; ++i) {
			Integer treu = furgos[i].dest[0].i2;
			Integer est = furgos[i].dest[0].i1;
			Estacion e = Main.Problema.get(est);
			if (e.getNumBicicletasNoUsadas()-e.getDemanda()-treu < 0 && treu > 0) {
				r = r - (e.getNumBicicletasNoUsadas()-e.getDemanda()-treu);
			}
			else {
				//estacio 1
				Integer est1 = furgos[i].dest[1].i1;
				Integer posa1 = furgos[i].dest[1].i2;
				Estacion e1 = Main.Problema.get(est1);
				if (e1.getNumBicicletasNoUsadas()-e1.getDemanda() < 0) {
					r = r + Math.min(-(e1.getNumBicicletasNoUsadas()-e1.getDemanda()),posa1);
				}
				//estaio2
				Integer est2 = furgos[i].dest[2].i1;
				Integer posa2 = furgos[i].dest[2].i2;
				Estacion e2 = Main.Problema.get(est2);
				if (e2.getNumBicicletasNoUsadas()-e2.getDemanda() < 0) {
					r = r + Math.min(-(e2.getNumBicicletasNoUsadas()-e2.getDemanda()),posa2);
				}
			}
		}

		return r;
	}

}
