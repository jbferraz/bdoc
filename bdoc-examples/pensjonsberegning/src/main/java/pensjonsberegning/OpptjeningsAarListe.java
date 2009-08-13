package pensjonsberegning;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Per Otto Bergum Christensen
 */
public class OpptjeningsAarListe extends ArrayList<OpptjeningsAar> {

	private static final long serialVersionUID = 1L;

	public int antallOpptjeningsaarIPerioden1967Til1991() {
		int antall = 0;
		for (OpptjeningsAar opptjeningsAar : liste()) {
			if (opptjeningsAar.getAar() < 1992) {
				antall++;
			}
		}
		return antall;
	}

	public int antallOpptjeningsaarFraOgMed1992() {
		int antall = 0;
		for (OpptjeningsAar opptjeningsAar : liste()) {
			if (1991 < opptjeningsAar.getAar() ) {
				antall++;
			}
		}
		return antall;
	}

	private List<OpptjeningsAar> liste() {
		return this;
	}

	public List<Double> getPensjonspoengListe() {
		List<Double> result = new ArrayList<Double>();
		for (OpptjeningsAar opptjeningsAar : liste()) {
			result.add( opptjeningsAar.getPensjonspoeng() );
		}
		return result;
	}

}
