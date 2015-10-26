
import IA.Bicing.Estacion;


public class Furgo {

	private Integer nbicicletes;
	/**
	 * Capacitat max de les furgos.
	 */
	public static final int MAX = 30;
	/**
	 * Numero maximo de viajes que puede hacer.
	 * Notese que la recogida inicial tambien cuenta.
	 */
	public static final Integer MAX_VIAJES = 3;

	public iPair[] dest;
	public Integer i;

	Furgo() {
		dest = new iPair[MAX_VIAJES];
		nbicicletes = i = 0;
	}

	public void enviar(Integer estacio, Integer q)
	{
		dest[i] = new iPair(estacio, q);
		i++;
		nbicicletes += q;
	}

	/**
	 * Modificar un envio. 0 <= v < MAX_VIAJES>
	 *
	 *
	 * @param v       Numero de viaje
	 * @param estacio Estacion
	 * @param q       cantidad
	 */
	public void enviar(int v, Integer estacio, Integer q)
	{
		nbicicletes -= dest[v].i2 - q;
		dest[v].i1 = estacio;
		dest[v].i2 = q;
	}

	public void mod_bicis(int v, Integer q)
	{
		nbicicletes -= dest[v].i2 - q;
		dest[v].i2 = q;
	}

	public void mostrar()
	{
		for (int i = 0; i < this.i; ++i)
		{
			if (dest[i].i2 != 0)
			{
				System.out.printf("(E:%d %d) ", dest[i].i1, dest[i].i2);
			}
		}
		System.out.println("");
	}

	public Boolean has_anat(Integer e)
	{
		for (int i = 0; i < this.i; ++i)
		{
			if (this.dest[i].i1 == e)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Retorna si la furgoneta puede recoger bicicletas.
	 * Una de las restricciones es que solo se puede en la primera estacion.
	 * @return CIERTO si puede recoger bicicletas
	 */
	public Boolean pot_recollir()
	{
		/* en principi i = 0 -> nbicicletes = 0
		pero deixarem la comprobacio ...*/
		return i == 0 && nbicicletes < Furgo.MAX;
	}

	/**
	 * Retorna si la furgoneta pot entregar bicicletes
	 *
	 * @return TRUE si pot entregar
	 */
	public Boolean pot_entregar()
	{
		return nbicicletes > 0 && i < Furgo.MAX_VIAJES;
	}

	public Furgo copia(){
		Furgo a = new Furgo();
		for ( a.i=0; a.i < i; ++a.i) {
			a.dest[a.i] = dest[a.i].copia();
			a.nbicicletes = nbicicletes;
		}
		return a;
	}

	public Integer getbicicletes()
	{
		return nbicicletes;
	}

	public Integer getbiciclietes(int v)
	{
		Integer b = 0;
		for (int i = 0; i < v && i < this.i; ++i)
		{
			b += dest[i].i2;
		}
		return b;
	}

	/**
	 * bicicletes que sobren
	 *
	 * @return 0 si no sobre cap bicicleta o si encara queden viatges, el numero
	 * de bicicletes que es van agafar de mes en altre cas.
	 */
	public Integer bicicletessobren()
	{
		return i < Furgo.MAX_VIAJES ? 0 : nbicicletes;
	}

	/**
	 * Se carga las bicicletas que sobran o no hace nada si no sobran.
	 */
	public void canonizar()
	{
		// si todo va bien i nunca sera > MAXVIAJES >.<
		if (i == Furgo.MAX_VIAJES)
		{
			dest[0].i2 -= nbicicletes;
			nbicicletes = 0;
		}
	}

	public void canonizar(boolean x)
	{
		dest[0].i2 -= nbicicletes;
		nbicicletes = 0;
	}

	/**
	 * Retorna el coste del viaje.
	 *
	 * @return El coste total en combustible que custa el viaje
	 */
	public double coste_combustible()
	{
		if (i > 0)
		{
			double coste = 0;
			int nb = dest[0].i2;
			for (int x = 1; x < i; ++x)
			{
				coste += ((int) (nb + 9) / 10) * distancia(Main.Problema.get(dest[x - 1].i1),
						Main.Problema.get(dest[x].i1));
				nb += dest[x].i2;
			}
			return coste;
		} else
			return 0;
	}

	/**
	 *
	 * @return Distancia total recorrida (km)
	 */
	public double distancia_recorrida()
	{
		double d = 0;
		for (int x = 1; x < i; ++x)
		{
			if (dest[x - 1].i2 != 0 && dest[x].i2 != 0)
			{
				d += distancia(Main.Problema.get(dest[x - 1].i1),
						Main.Problema.get(dest[x].i1));
			}
		}
		return d;
	}

	/**
	 * Distancia Manhattan entre dos estaciones
	 *
	 * @param e1 Estacion 1
	 * @param e2 Estacion 2
	 *
	 * @return La distancia Manhattan entre las dos estaciones. (km)
	 */
	private double distancia(Estacion e1, Estacion e2)
	{
		return (Math.abs(e1.getCoordX() - e2.getCoordX())
				+ Math.abs(e1.getCoordY() - e2.getCoordY())) / 1000;
	}
}
