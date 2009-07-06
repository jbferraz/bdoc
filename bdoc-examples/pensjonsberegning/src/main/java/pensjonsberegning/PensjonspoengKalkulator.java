package pensjonsberegning;

import org.joda.time.DateMidnight.Property;

/**
 * 
 * @author Per Otto Bergum Christensen
 */
public class PensjonspoengKalkulator {

	private GrunnbeloepRepository gjennomsnittligGrunnbeloepRepository;

	public PensjonspoengKalkulator(GrunnbeloepRepository gjennomsnittligGrunnbeloepRepository) {
		this.gjennomsnittligGrunnbeloepRepository = gjennomsnittligGrunnbeloepRepository;
	}

	public double beregn(Inntekt inntekt) {

		Grunnbeloep grunnbeloep = gjennomsnittligGrunnbeloepRepository.gjennomsnittligGrunnbeloepFor(inntekt.aar());

		if (inntekt.verdi() < grunnbeloep.verdi()) {
			return 0;
		}

		if (inntekt.aar() < 1971) {
			if (8 < (inntekt.verdi() / grunnbeloep.verdi())) {
				return 7;
			}
			return avrund((inntekt.verdi() - grunnbeloep.verdi()) / grunnbeloep.verdi());
		}

		if ((inntekt.verdi() / grunnbeloep.verdi()) < 6) {
			return avrund((inntekt.verdi() - grunnbeloep.verdi()) / grunnbeloep.verdi());
		}

		if ((inntekt.verdi() / grunnbeloep.verdi()) < 12) {
			Double nedjustertInntekt = (inntekt.verdi() - 6 * grunnbeloep.verdi()) / 3;
			return 5 + avrund((nedjustertInntekt) / grunnbeloep.verdi());
		}

		return 7;
	}

	private double avrund(double verdi) {
		return Math.round(100 * verdi) / 100D;
	}
}
