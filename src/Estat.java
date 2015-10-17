import IA.Bicing.*;
import java.util.*;


public class Estat {
	
	public Furgo[] furgos;
	

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
