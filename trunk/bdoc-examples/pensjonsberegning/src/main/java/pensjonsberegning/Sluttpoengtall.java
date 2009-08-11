package pensjonsberegning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Sluttpoengtall {

	private final static Comparator<Double> doubleAscComparator = new Comparator<Double>() {
		public int compare(Double arg0, Double arg1) {
			return arg0.compareTo(arg1);
		}
	};

	public Sluttpoengtall(List<Inntekt> inntekt, GrunnbeloepTabell grunnbeloepTabell) {
		// TODO Auto-generated constructor stub
	}

	public Double beregnet() {
		return null;
	}
	
	public static double beregn(List<Double> pensjonspoengListe) {

		List<Double> tyveBestePensjonspoeng = new ArrayList<Double>();
		tyveBestePensjonspoeng.addAll(pensjonspoengListe);
		Collections.sort(tyveBestePensjonspoeng, doubleAscComparator);

		while (20 < tyveBestePensjonspoeng.size()) {
			tyveBestePensjonspoeng.remove(0);
		}

		double pensjonspoengSum = 0;
		for (Double pensjonspoeng : tyveBestePensjonspoeng) {
			pensjonspoengSum = pensjonspoengSum + pensjonspoeng;
		}

		return Pensjonspoeng.rundAvTilToDesimaler(pensjonspoengSum / tyveBestePensjonspoeng.size());
	}
}
