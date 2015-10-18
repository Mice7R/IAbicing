
import IA.Bicing.Estacion;


public class Furgo {

	private Integer nbicicletes;
	/**
	 * Capacitat max de les furgos.
	 */
	public static final Integer MAX = 30;
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

	void enviar(Integer estacio, Integer q)
	{
		dest[i] = new iPair(estacio, q);
		i++;
		nbicicletes += q;
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
		}
	}

	/**
	 * Retorna el coste del viaje.
	 *
	 * @return El coste total en combustible que custa el viaje
	 */
	public int coste_combustible()
	{
		if (i > 0)
		{
			Integer coste = 0;
			int nb = dest[0].i2;
			for (int x = 1; x < i; ++x)
			{
				coste += ((nb + 9) / 10) * distancia(Main.Problema.get(dest[x-1].i1),
						Main.Problema.get(dest[x].i1));
				nb += dest[x].i2;
			}
			return coste;
		} else
			return 0;
	}

	/**
	 * Distancia Manhattan entre dos estaciones
	 *
	 * @param e1 Estacion 1
	 * @param e2 Estacion 2
	 *
	 * @return La distancia Manhattan entre las dos estaciones.
	 */
	private int distancia(Estacion e1, Estacion e2)
	{
		return Math.abs(e1.getCoordX() - e2.getCoordX())
				+ Math.abs(e1.getCoordY() - e2.getCoordY());
	}
}
