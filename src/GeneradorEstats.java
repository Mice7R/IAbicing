import java.util.*;
import aima.search.framework.*;
import IA.Bicing.*;
import java.lang.Math;

public class GeneradorEstats implements SuccessorFunction {
	
	public List getSuccessors(Object state)
	{
		ArrayList R = new ArrayList();
		Estat estat = (Estat) state;
		/*
		 * > 0 : Sobran bicis
		 * < 0 : Faltan bicis
		 */
		Integer estacions[] = new Integer[Main.nestacions];
		
		// com estan les estacions?
		// pas 1: al estat -1
		for ( int j = 0; j < Main.nestacions; ++j )
		{
			int a = Main.Problema.get(j).getNumBicicletasNoUsadas() - Main.Problema.get(j).getDemanda(); //les bicicletes que tindrem lliures la seguent hora
			
			if ( a > 0 ) estacions[j]=a;
			else
			{
				a = Main.Problema.get(j).getNumBicicletasNext() - Main.Problema.get(j).getDemanda();
				estacions[j]=a;

			}
		}
		
		// pas 2: en aquest estat
		for ( int i = 0; i < Main.nfurgos; ++i)
		{
			for ( int j = 0; j < estat.furgos[i].i; ++j )
			{
				if ( estat.furgos[i].dest[j].i2 > 0) estacions[estat.furgos[i].dest[j].i1] = 0;
				else estacions[estat.furgos[i].dest[j].i1] -= estat.furgos[i].dest[j].i2;
			}
		}
		
		//Operado 1 (Entregar bicicletes)
		for ( int i = 0; i < Main.nfurgos; ++i)
		{
			if ( estat.furgos[i].getbicicletes() == 0 ) continue;
			for ( int j = 0; j < Main.nestacions; ++j )
			{
				if ( estacions[j] < 0 )
				{
					Estat nouestat = estat.copia();
					int caca = -(Math.min(nouestat.furgos[i].getbicicletes(), -estacions[j]));
					nouestat.furgos[i].enviar(j, caca);
					// TODO Registrar estat
					R.add(new Successor("O1 - " + j + " - " + i + " " + caca, nouestat));
				}
			}
		}
		
		// Operador 2 ( Recollir bicicletes )
		
		for ( int i = 0; i < Main.nfurgos; ++i)
		{
			if ( estat.furgos[i].i > 0) continue;
			if ( estat.furgos[i].getbicicletes() == Furgo.MAX ) continue;
			for ( int j = 0; j < Main.nestacions; ++j )
			{
				//System.out.println(estacions[j] + "yo");
				if ( estacions[j] > 0 )
				{
					
					Estat nouestat = estat.copia();
					nouestat.furgos[i].enviar(j, 
							Math.min(Furgo.MAX, estacions[j])
							);
					R.add(new Successor("O2 - " + j + " - " + i + " " + Math.min(Furgo.MAX, estacions[j]), nouestat));
				}
			}
		}
		
		return R;
	}

}
