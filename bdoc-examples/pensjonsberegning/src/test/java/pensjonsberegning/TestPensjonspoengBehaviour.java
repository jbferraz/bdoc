package pensjonsberegning;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pensjonsberegning.bdoc.Ref;
import pensjonsberegning.bdoc.RefClass;
import pensjonsberegning.bdoc.Spec;
import pensjonsberegning.bdoc.Story;

/**
 * http://no.wikipedia.org/wiki/Pensjonspoeng
 * http://www.nav.no/1073750161.cms?kapittel=10
 * http://www.nav.no/1073750161.cms?kapittel=11 detaljert:
 * http://www.nav.no/rettskildene/Rundskriv/147945.cms
 * http://www.nav.no/rettskildene/Rundskriv/147955.cms Bra:
 * http://www.dinside.no/1671/hva-faar-jeg-i-pensjon
 * 
 * Liste over grunnbelop:
 * http://www.consis.no/Endring+av+grunnbel%C3%B8pet+(G).FglLG2m.ips
 * 
 * @author Per Otto Bergum Christensen
 * 
 */
@Ref(Story.BEREGNING_AV_ALDERSPENSJON)
@RefClass(Pensjonspoeng.class)
public class TestPensjonspoengBehaviour {

	private GrunnbeloepRepository grunnbeloepRepository = new GrunnbeloepRepository();
	private Pensjonspoeng pensjonspoeng = new Pensjonspoeng(grunnbeloepRepository);

	@Test
	public void pensjonspoengOpptjenesKunNaarPensjonsgivendeInntektOverstigerGrunnbeloepet() {
		assertEquals(0, pensjonspoeng.beregn(2000, Grunnbeloep._2000 - 10), .001);
		assertEquals(1, pensjonspoeng.beregn(2000, Grunnbeloep._2000 * 2), .001);
	}

	@Test
	public void gjennomsnittligGrunnbelopFraDetAaretInntektenGjaldtSkalBenyttesVedBeregningAvPensjonspoeng() {
		assertEquals(3.58, pensjonspoeng.beregn(2007, 300000), .001);
		assertEquals(3.34, pensjonspoeng.beregn(2008, 300000), .001);
	}

	@Test
	@Spec(": (Inntekt - G ) / G = Pensjonspoeng")
	public void hovedregelVedBeregningEr$spec$() {
		assertEquals(4.79, pensjonspoeng.beregn(2008, 400000), .001);
	}

	@Test
	public void forAarene1967Til1970BleInntektInntilAatteGangerGrunnbeloepeMedregnet() {
		eksempel("Inntekt på 4G skal gi 3 pensjonspoeng", 1967, 4 * Grunnbeloep._1967, 3);
		eksempel("Inntekt på 8G skal gi maksgrensen på 7 pensjonspoeng", 1967, 8 * Grunnbeloep._1967, 7);
		eksempel("Inntekt på 12G skal begrensens til maksgrensen på 7 pensjonspoeng", 1970, 12 * Grunnbeloep._1970, 7);
	}

	@Test
	public void forAarene1971Til1991BleInntektBegrensetTil12_G_HvorDenDelenAvInntektenSomOversteg8_G_kunBleMedregnetMedEnTredjedel() {
		eksempel("Inntekt på 8G skal gi 7 pensjonspoeng", 1971, 8 * Grunnbeloep._1971, 7);
		eksempel("Inntekt på 10G skal gi 7.67 pensjonspoeng", 1991, 10 * Grunnbeloep._1991, 7.67);
		eksempel("Inntekt på 12G skal gi maksgrensen på 8.33 pensjonspoeng", 1971, 12 * Grunnbeloep._1971, 8.33);
		eksempel("Inntekt på 14G skal begrenses til maksgrensen på 8.33 pensjonspoeng", 1971, 14 * Grunnbeloep._1971, 8.33);
	}

	@Test
	public void fra1992MedregnesBareEnTredjedelAvInntektenSomLiggerMellom6Og12_G() {
		eksempel("Inntekt på 6G skal gi 5 pensjonspoeng", 2000, 6 * Grunnbeloep._2000, 5);
		eksempel("Inntekt på 8G skal gi 5.67 pensjonspoeng", 2000, 8 * Grunnbeloep._2000, 5.67);
		eksempel("Inntekt på 12G skal gi maksgrensen på 7 pensjonspoeng", 2000, 12 * Grunnbeloep._2000, 7);
	}

	public void eksempel(String beskrivelse, int inntektsaar, int inntekt, double pensjonspoeng) {
		assertEquals(beskrivelse, pensjonspoeng, this.pensjonspoeng.beregn(inntektsaar, inntekt), .001);
	}

}
