import java.util.*;

public class iPair {
		public Integer i1;
		public Integer i2;
		
		iPair() { i1 = 0; i2 = 0; }
		
		iPair(Integer a, Integer b)
		{ i1 = a; i2 = b; }
		
		public iPair copia()
		{
			return new iPair(i1, i2);
		}
}
