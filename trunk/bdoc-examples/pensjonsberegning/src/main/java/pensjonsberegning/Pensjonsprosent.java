package pensjonsberegning;

import java.util.List;

public class Pensjonsprosent {

	private List<Inntekt> inntekt;
	private GrunnbeloepTabell grunnbeloepTabell;

	public Pensjonsprosent(List<Inntekt> inntekt, GrunnbeloepTabell grunnbeloepTabell ) {
		this.inntekt = inntekt;
		this.grunnbeloepTabell = grunnbeloepTabell;
	}
	
	public Double beregnet() {
		return null;
	}

	public static double forAar(int aar) {
		if (1991 < aar) {
			return 42;
		}
		return 45;
	}

	public static double beregn(int opptjeningsaar1967Til1991, int opptjeningsaarFraOgMed1992) {

		int medregnetOpptjeningsaar1967Til1912 = opptjeningsaar1967Til1991;
		if (40 < medregnetOpptjeningsaar1967Til1912) {
			medregnetOpptjeningsaar1967Til1912 = 40;
		}

		int medregnetOpptjeningsaarFraOgMed1992 = opptjeningsaarFraOgMed1992;
		if (40 < medregnetOpptjeningsaarFraOgMed1992) {
			medregnetOpptjeningsaarFraOgMed1992 = 40;
		}

		int totaltAntallOpptjeningsAar = medregnetOpptjeningsaar1967Til1912 + medregnetOpptjeningsaarFraOgMed1992;

		if (40 < totaltAntallOpptjeningsAar) {
			int antallOpptjeningsaarForMye = totaltAntallOpptjeningsAar - 40;
			medregnetOpptjeningsaarFraOgMed1992 = medregnetOpptjeningsaarFraOgMed1992 - antallOpptjeningsaarForMye;
		}

		return (0.45 * medregnetOpptjeningsaar1967Til1912 + 0.42 * medregnetOpptjeningsaarFraOgMed1992) / 40.0;
	}

}
