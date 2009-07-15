package pensjonsberegning;

/**
 * Klassen beregner antall Pensjonspoeng basert på inntekstår og inntekt. Basert
 * på inntekstår vil aktuell G verdi for året benyttes.
 * 
 * @author Per Otto Bergum Christensen
 */
public class Pensjonspoeng {

	private GrunnbeloepTabell grunnbeloepRepository;

	public Pensjonspoeng(GrunnbeloepTabell gjennomsnittligGrunnbeloepRepository) {
		this.grunnbeloepRepository = gjennomsnittligGrunnbeloepRepository;
	}

	public double beregn(int inntektsaar, double inntekt) {
		if ((inntektsaar < 1967) || (2008 < inntektsaar)) {
			throw new IllegalArgumentException("Inntektsår må ligge mellom 1967 og 2008");
		}
		Integer grunnbeloep = grunnbeloepRepository.gjennomsnittligGrunnbeloepFor(inntektsaar);

		if (inntekt < grunnbeloep) {
			return 0;
		}

		if (inntektsaar < 1971) {
			if ((inntekt / grunnbeloep) < 8) {
				return rundAvTilToDesimaler((inntekt - grunnbeloep) / grunnbeloep);
			}
			return 7;
		}

		if (inntektsaar < 1992) {

			if ((inntekt / grunnbeloep) < 8) {
				return rundAvTilToDesimaler((inntekt - grunnbeloep) / grunnbeloep);
			}

			if ((inntekt / grunnbeloep) < 12) {
				Double inntektMellom8og12G = inntekt - (8 * grunnbeloep);
				return 7 + rundAvTilToDesimaler(inntektMellom8og12G / (3 * grunnbeloep));
			}

			return 8.33;
		}

		if ((inntekt / grunnbeloep) < 6) {
			return rundAvTilToDesimaler((inntekt - grunnbeloep) / grunnbeloep);
		}

		if ((inntekt / grunnbeloep) < 12) {
			Double inntektMellom6og12G = inntekt - (6 * grunnbeloep);
			return 5 + rundAvTilToDesimaler(inntektMellom6og12G / (3 * grunnbeloep));
		}

		return 7;
	}

	public static double rundAvTilToDesimaler(double verdi) {
		return Math.round(100 * verdi) / 100D;
	}
}
