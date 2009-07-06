package pensjonsberegning;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateMidnight;

public class GrunnbeloepRepository {

	private static Map<Integer, Grunnbeloep> grunnbelop = new HashMap<Integer, Grunnbeloep>();

	public Grunnbeloep gjennomsnittligGrunnbeloepFor(DateMidnight aar) {
		return grunnbelop.get(aar.getYear());
	}

	public static final Grunnbeloep _2008 = opprettGrunnbeloep(2008, 69108);
	public static final Grunnbeloep _2007 = opprettGrunnbeloep(2007, 65505);
	public static final Grunnbeloep _2006 = opprettGrunnbeloep(2006, 62161);
	public static final Grunnbeloep _2005 = opprettGrunnbeloep(2005, 60059);
	public static final Grunnbeloep _2004 = opprettGrunnbeloep(2004, 58139);
	public static final Grunnbeloep _2003 = opprettGrunnbeloep(2003, 55964);
	public static final Grunnbeloep _2002 = opprettGrunnbeloep(2002, 53233);
	public static final Grunnbeloep _2001 = opprettGrunnbeloep(2001, 50603);
	public static final Grunnbeloep _2000 = opprettGrunnbeloep(2000, 48377);
	public static final Grunnbeloep _1999 = opprettGrunnbeloep(1999, 46423);
	public static final Grunnbeloep _1998 = opprettGrunnbeloep(1998, 44413);
	public static final Grunnbeloep _1997 = opprettGrunnbeloep(1997, 42000);
	public static final Grunnbeloep _1996 = opprettGrunnbeloep(1996, 40410);
	public static final Grunnbeloep _1995 = opprettGrunnbeloep(1995, 38847);
	public static final Grunnbeloep _1994 = opprettGrunnbeloep(1994, 37820);
	public static final Grunnbeloep _1993 = opprettGrunnbeloep(1993, 37033);
	public static final Grunnbeloep _1992 = opprettGrunnbeloep(1992, 36167);
	public static final Grunnbeloep _1991 = opprettGrunnbeloep(1991, 35033);
	public static final Grunnbeloep _1990 = opprettGrunnbeloep(1990, 33575);
	public static final Grunnbeloep _1989 = opprettGrunnbeloep(1989, 32275);
	public static final Grunnbeloep _1988 = opprettGrunnbeloep(1988, 30850);
	public static final Grunnbeloep _1987 = opprettGrunnbeloep(1987, 29267);
	public static final Grunnbeloep _1986 = opprettGrunnbeloep(1986, 27433);
	public static final Grunnbeloep _1985 = opprettGrunnbeloep(1985, 25333);
	public static final Grunnbeloep _1984 = opprettGrunnbeloep(1984, 23667);
	public static final Grunnbeloep _1983 = opprettGrunnbeloep(1983, 22333);
	public static final Grunnbeloep _1982 = opprettGrunnbeloep(1982, 20667);
	public static final Grunnbeloep _1981 = opprettGrunnbeloep(1981, 18658);
	public static final Grunnbeloep _1980 = opprettGrunnbeloep(1980, 16633);
	public static final Grunnbeloep _1979 = opprettGrunnbeloep(1979, 15200);
	public static final Grunnbeloep _1978 = opprettGrunnbeloep(1978, 14550);
	public static final Grunnbeloep _1977 = opprettGrunnbeloep(1977, 13383);
	public static final Grunnbeloep _1976 = opprettGrunnbeloep(1976, 12000);
	public static final Grunnbeloep _1975 = opprettGrunnbeloep(1975, 10800);
	public static final Grunnbeloep _1974 = opprettGrunnbeloep(1974, 9533);
	public static final Grunnbeloep _1973 = opprettGrunnbeloep(1973, 8500);
	public static final Grunnbeloep _1972 = opprettGrunnbeloep(1972, 7900);
	public static final Grunnbeloep _1971 = opprettGrunnbeloep(1971, 7400);
	public static final Grunnbeloep _1970 = opprettGrunnbeloep(1970, 6800);
	public static final Grunnbeloep _1969 = opprettGrunnbeloep(1969, 6400);
	public static final Grunnbeloep _1968 = opprettGrunnbeloep(1968, 5900);
	public static final Grunnbeloep _1967 = opprettGrunnbeloep(1967, 5400);

	private static Grunnbeloep opprettGrunnbeloep(int aar, double verdi) {
		Grunnbeloep grunnbeloep = new Grunnbeloep(verdi);
		grunnbelop.put(aar, grunnbeloep);
		return grunnbeloep;
	}
}
