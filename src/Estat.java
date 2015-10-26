
import IA.Bicing.Estacion;
import IA.util.Quicksort;
import java.util.Random;

public class Estat {

	public Furgo[] furgos;
	/**
	 * Configuracion inicial de las estaciones en base a las bicicletas de las
	 * que dispone. Valores positivos indican que dispone de bicicletas, valores
	 * negativos indican que faltan bicicletas.
	 */
	static public Integer[] configuracio_inicial;

	/**
	 * Informacio sobre la configuracio inicial
	 */
	static private Integer estacions_superhabit = 0;
	static private Integer estacions_demanda = 0;


	Estat(Integer nfurg)
	{
		furgos = new Furgo[nfurg];
		for (int i = 0; i < nfurg; ++i)
		{
			furgos[i] = new Furgo();
		}
	}

	public Estat copia()
	{
		Estat a = new Estat(furgos.length);
		for (int i = 0; i < furgos.length; ++i)
		{
			a.furgos[i] = furgos[i].copia();
		}

		return a;

	}

	/**
	 * Genera solucion random. Coge un numero aleatorio de bicis de una estacion
	 * que mas le sobra y los envia a donde mas falta
	 */
	private void random_ordenado()
	{
		Integer[] estacions = configuracio_inicial.clone();
		Integer[] ids = new Integer[Main.nestacions];
		for (int i = 0; i < Main.nestacions; ++i)
		{
			ids[i] = i;
		}
		Quicksort.sort(estacions, ids, 0, ids.length);
		int f=0;
                for (int i = 0, j = ids.length - 1;
				i < j && f < Main.nfurgos && estacions[i] > 0; ++f)
		{
			Random rand = new Random();
			int enviar = Math.min(rand.nextInt(estacions[i]) + 1, Furgo.MAX);
			//System.out.printf("E=%d(%d) B=%d E=%d(%d)\n", ids[i], estacions[i], enviar, ids[j], estacions[j]);
			furgos[f].enviar(ids[i++], enviar);
			furgos[f].enviar(ids[j--], -(enviar>>1));
            furgos[f].enviar(ids[j], -furgos[f].getbicicletes());
		}

	}

	private void full_random()
	{
		Random rand = new Random();
		Integer[] ids = new Integer[Main.nestacions];
		Integer[] pes = new Integer[Main.nestacions];
		for (int i = 0; i < Main.nestacions; ++i)
		{
			ids[i] = i;
			pes[i] = rand.nextInt();
		}
		Quicksort.sort(pes, ids, 0, ids.length);
		for (int i = 0, f = 0; f < Main.nfurgos && i < Main.nestacions; ++i, ++f)
		{
			int enviar = rand.nextInt(Furgo.MAX);
			furgos[f].enviar(ids[i], enviar);
			int d1;
			int d2;
			do
			{
				d1 = rand.nextInt(Main.nestacions);
				d2 = ids[d1];
			} while (d1 == ids[i] || d2 == ids[i] || d1 == d2);
			int e2 = (enviar > 0 ? rand.nextInt(enviar) + 1 : 0);
			furgos[f].enviar(d1, -e2);
			furgos[f].enviar(d2, e2 - enviar);
		}
	}

	/**
	 * Wrapper para generar soluciones.
	 * 0: Vacia 1: random_ordenado
	 *
	 * @param i El algoritmo de generacion
	 */
	public void generar_solucion(int i)
	{
		switch (i)
		{
			case 1:
				random_ordenado();
				break;
			case 2:
				full_random();
				break;
		}
	}

        public void mostrar_solucion()
        {
            for ( int i = 0; i < Main.nfurgos; ++i)
            {
                System.out.printf("F:%d ", i);
                furgos[i].mostrar();
            }
        }

	public Integer size()
	{
		return furgos.length;
	}

	/**
	 * Calcula la configuracio de les estacions. Posa a 0 les estacions de les
	 * que ja sha agafat.
	 *
	 * @return Configuracio de les estacions
	 */
	public Integer[] estacions()
	{
		Integer[] r = (Integer[]) configuracio_inicial.clone();

		for (Furgo f : furgos)
		{

			if (f.i > 0)
			{
				r[f.dest[0].i1] = Math.min(r[f.dest[0].i1] - f.dest[0].i2, 0);
			}
			// para cada viaje de la furgo
			for (int i = 1; i < f.i; ++i)
			{
				iPair destino = f.dest[i];
				/* Los simbolos son distintos
				 * En Furgos positivo es que a recogido, es decir que ha quitado
				 * de la estacion. */
				r[destino.i1] -= destino.i2;
			}
		}

		return r;
	}

	/**
	 * Calcula la configuracion inicial de las estaciones. No llamar antes de
	 * que Main.problema este inicializado Se asume que Next = nousadas + c;
	 * para c >= 0
	 */
	static public void calcula_conf_inicial()
	{
		configuracio_inicial = new Integer[Main.nestacions];
		for (int e = 0; e < Main.nestacions; ++e)
		{
			Estacion estacion = Main.Problema.get(e);
			configuracio_inicial[e] = Math.min(
					estacion.getNumBicicletasNoUsadas(),
					estacion.getNumBicicletasNext() - estacion.getDemanda());
			if (configuracio_inicial[e] > 0)
			{
				configuracio_inicial[e] = Math.min(configuracio_inicial[e], Furgo.MAX);
				++estacions_superhabit;
			} else if (configuracio_inicial[e] < 0)
			{
				++estacions_demanda;
			}
		}
	}

	private double eurus1()
	{
		double r = 0;
		Integer[] estacions = estacions();
		for (int e = 0; e < Main.nestacions; ++e)
		{
			r += Math.min(estacions[e], 0) - Math.min(configuracio_inicial[e], 0);
		}
		return r;
	}

	private double eurus2()
	{
		double r = eurus1();
		for (Furgo f : furgos)
		{
			r -= f.coste_combustible();
		}
		return r;
	}

	/**
	 * Eurus wrapper
	 *
	 * @param m Mode de funcio 0=sense combustible 1=amb combustible
	 *
	 * @return eurus
	 */
	public double eurus(int m)
	{
		switch (m)
		{
			case 0:
				return eurus1();
			case 1:
				return eurus2();
			default:
				return -123456.789;
		}
	}

	public void canonizar()
	{
		for (int f = 0; f < Main.nfurgos; ++f)
			furgos[f].canonizar();
	}

	public void _end()
	{
		for (Furgo f : furgos)
		{
			f.canonizar(true);
		}
	}

	public double distancia_total()
	{
		double d = 0;
		for (Furgo f : furgos)
		{
			d += f.distancia_recorrida();
		}
		return d;
	}

}
