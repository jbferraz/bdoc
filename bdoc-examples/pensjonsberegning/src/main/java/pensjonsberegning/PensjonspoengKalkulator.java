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

		Integer grunnbeloep = gjennomsnittligGrunnbeloepRepository.gjennomsnittligGrunnbeloepFor(inntekt.aar());

		if (inntekt.verdi() < grunnbeloep ) {
			return 0;
		}

		if (inntekt.aar() < 1971) {
			if (8 < (inntekt.verdi() / grunnbeloep)) {
				return 7;
			}
			return avrund((inntekt.verdi() - grunnbeloep) / grunnbeloep);
		}

		if (inntekt.aar() < 1992) {

			if ((inntekt.verdi() / grunnbeloep) <= 8) {
				return avrund((inntekt.verdi() - grunnbeloep) / grunnbeloep);
			}
			
			if ((inntekt.verdi() / grunnbeloep) < 12) {
				Double nedjustertInntekt = (inntekt.verdi() - 8 * grunnbeloep) / 3;
				return 7 + avrund((nedjustertInntekt) / grunnbeloep);
			}

			return 8.33;
		}

		if ((inntekt.verdi() / grunnbeloep) < 6) {
			return avrund((inntekt.verdi() - grunnbeloep) / grunnbeloep);
		}

		if ((inntekt.verdi() / grunnbeloep) < 12) {
			Double nedjustertInntekt = (inntekt.verdi() - 6 * grunnbeloep) / 3;
			return 5 + avrund((nedjustertInntekt) / grunnbeloep);
		}

		return 7;
	}

	private double avrund(double verdi) {
		return Math.round(100 * verdi) / 100D;
	}
}
