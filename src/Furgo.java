

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
}
