package pensjonsberegning;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateMidnight;

public class GrunnbeloepRepository {

	private static Map<Integer, Grunnbeloep> grunnbelop = new HashMap<Integer, Grunnbeloep>();

	public Grunnbeloep gjennomsnittligGrunnbeloepFor(DateMidnight aar) {
		return grunnbelop.get(aar.getYear());
	}

	static {
		grunnbelop.put(new Integer(2008), new Grunnbeloep(69108));
		grunnbelop.put(new Integer(2007), new Grunnbeloep(65505));
		grunnbelop.put(new Integer(2006), new Grunnbeloep(62161));
		grunnbelop.put(new Integer(2005), new Grunnbeloep(60059));
		grunnbelop.put(new Integer(2004), new Grunnbeloep(58139));
		grunnbelop.put(new Integer(2003), new Grunnbeloep(55964));
		grunnbelop.put(new Integer(2002), new Grunnbeloep(53233));
		grunnbelop.put(new Integer(2001), new Grunnbeloep(50603));
		grunnbelop.put(new Integer(2000), new Grunnbeloep(48377));
		grunnbelop.put(new Integer(1999), new Grunnbeloep(46423));
		grunnbelop.put(new Integer(1998), new Grunnbeloep(44413));
		grunnbelop.put(new Integer(1997), new Grunnbeloep(42000));
		grunnbelop.put(new Integer(1996), new Grunnbeloep(40410));
		grunnbelop.put(new Integer(1995), new Grunnbeloep(38847));
		grunnbelop.put(new Integer(1994), new Grunnbeloep(37820));
		grunnbelop.put(new Integer(1993), new Grunnbeloep(37033));
		grunnbelop.put(new Integer(1992), new Grunnbeloep(36167));
		grunnbelop.put(new Integer(1991), new Grunnbeloep(35033));
		grunnbelop.put(new Integer(1990), new Grunnbeloep(33575));
		grunnbelop.put(new Integer(1989), new Grunnbeloep(32275));
		grunnbelop.put(new Integer(1988), new Grunnbeloep(30850));
		grunnbelop.put(new Integer(1987), new Grunnbeloep(29267));
		grunnbelop.put(new Integer(1986), new Grunnbeloep(27433));
		grunnbelop.put(new Integer(1985), new Grunnbeloep(25333));
		grunnbelop.put(new Integer(1984), new Grunnbeloep(23667));
		grunnbelop.put(new Integer(1983), new Grunnbeloep(22333));
		grunnbelop.put(new Integer(1982), new Grunnbeloep(20667));
		grunnbelop.put(new Integer(1981), new Grunnbeloep(18658));
		grunnbelop.put(new Integer(1980), new Grunnbeloep(16633));
		grunnbelop.put(new Integer(1979), new Grunnbeloep(15200));
		grunnbelop.put(new Integer(1978), new Grunnbeloep(14550));
		grunnbelop.put(new Integer(1977), new Grunnbeloep(13383));
		grunnbelop.put(new Integer(1976), new Grunnbeloep(12000));
		grunnbelop.put(new Integer(1975), new Grunnbeloep(10800));
		grunnbelop.put(new Integer(1974), new Grunnbeloep(9533));
		grunnbelop.put(new Integer(1973), new Grunnbeloep(8500));
		grunnbelop.put(new Integer(1972), new Grunnbeloep(7900));
		grunnbelop.put(new Integer(1971), new Grunnbeloep(7400));
		grunnbelop.put(new Integer(1970), new Grunnbeloep(6800));
		grunnbelop.put(new Integer(1969), new Grunnbeloep(6400));
		grunnbelop.put(new Integer(1968), new Grunnbeloep(5900));
		grunnbelop.put(new Integer(1967), new Grunnbeloep(5400));
	}
}
