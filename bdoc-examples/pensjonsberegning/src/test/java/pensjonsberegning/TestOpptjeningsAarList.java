package pensjonsberegning;

import static org.junit.Assert.assertEquals;
import static java.util.Arrays.asList;

import java.util.List;

import org.junit.Test;

public class TestOpptjeningsAarList {

	@Test
	public void skalAngiAntallOpptjeningsaarFraOgMed1967TilOgMed1991() {
		OpptjeningsAarListe opptjeningsAarList = new OpptjeningsAarListe();
		opptjeningsAarList.add(new OpptjeningsAar(1967, 10000));
		opptjeningsAarList.add(new OpptjeningsAar(1991, 10000));
		opptjeningsAarList.add(new OpptjeningsAar(1992, 10000));
		assertEquals(2, opptjeningsAarList.antallOpptjeningsaarIPerioden1967Til1991());
	}

	@Test
	public void skalAngiAntallOpptjeningsaarFraOgMed1992() {
		OpptjeningsAarListe opptjeningsAarList = new OpptjeningsAarListe();
		opptjeningsAarList.add(new OpptjeningsAar(1991, 10000));
		opptjeningsAarList.add(new OpptjeningsAar(1992, 10000));
		opptjeningsAarList.add(new OpptjeningsAar(1993, 10000));
		assertEquals(2, opptjeningsAarList.antallOpptjeningsaarFraOgMed1992());
	}

	@Test
	public void skalKunneReturnereEnListeMedPensjonspoengForPerioden() {
		OpptjeningsAarListe opptjeningsAarListe = new OpptjeningsAarListe();
		opptjeningsAarListe.add(new OpptjeningsAar(1991, 1));
		opptjeningsAarListe.add(new OpptjeningsAar(1992, 2));
		opptjeningsAarListe.add(new OpptjeningsAar(1993, 3));

		assertEquals(asList(1d, 2d, 3d), opptjeningsAarListe.getPensjonspoengListe());
	}

}
