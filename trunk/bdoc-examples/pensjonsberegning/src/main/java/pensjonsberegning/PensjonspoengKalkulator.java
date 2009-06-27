package pensjonsberegning;

/**
 * 
 * @author Per Otto Bergum Christensen
 */
public class PensjonspoengKalkulator {

	private Inntekt inntekt;
	private GjennomsnittligGrunnbeloep grunnbeloep;

	public PensjonspoengKalkulator(Inntekt inntekt, GjennomsnittligGrunnbeloep grunnbeloep) {
		this.inntekt = inntekt;
		this.grunnbeloep = grunnbeloep;
	}

	public double beregnet() {

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
