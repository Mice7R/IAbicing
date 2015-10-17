import java.util.*;


public class Furgo {
	
	private Integer nbicicletes;
	public static final Integer MAX = 30;
	
	public iPair[] dest;
	public Integer i;
	
	Furgo() {
		dest = new iPair[3];
		nbicicletes = i = 0;
	}
	
	void enviar(Integer estacio, Integer q)
	{
		dest[i] = new iPair(estacio, q);
		i++;
		nbicicletes += q;
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
}



