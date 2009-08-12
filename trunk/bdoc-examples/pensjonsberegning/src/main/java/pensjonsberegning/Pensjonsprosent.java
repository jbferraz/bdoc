package pensjonsberegning;

public class Pensjonsprosent {

	private int opptjeningsaar1967Til1991;
	private int opptjeningsaarFraOgMed1992;

	public Pensjonsprosent(int opptjeningsaar1967Til1991, int opptjeningsaarFraOgMed1992) {
		this.opptjeningsaar1967Til1991 = opptjeningsaar1967Til1991;
		this.opptjeningsaarFraOgMed1992 = opptjeningsaarFraOgMed1992;
	}

	public Pensjonsprosent(OpptjeningsAarListe opptjeningsAarListe) {
		this(opptjeningsAarListe.antallOpptjeningsaarIPerioden1967Til1991(), opptjeningsAarListe.antallOpptjeningsaarFraOgMed1992());
	}

	public static double forAar(int aar) {
		if (1991 < aar) {
			return 42;
		}
		return 45;
	}

	public Double beregnet() {

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
