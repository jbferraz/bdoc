package pensjonsberegning;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pensjonsberegning.bdoc.Ref;
import pensjonsberegning.bdoc.RefClass;
import pensjonsberegning.bdoc.Spec;
import pensjonsberegning.bdoc.Story;

/**
 * @author Per Otto Bergum Christensen
 */
@Ref(Story.BEREGNING_AV_ALDERSPENSJON)
@RefClass(Pensjonspoeng.class)
public class TestPensjonspoengBehaviour extends ScenarioSupport<TestPensjonspoengBehaviour>{

	private GrunnbeloepTabell grunnbeloepRepository = new GrunnbeloepTabell();
	private Pensjonspoeng pensjonspoeng = new Pensjonspoeng(grunnbeloepRepository);
	private double inntektIGrunnbelopet;
	private double pensjonspoengBeregnet;

	@Test
	@Spec(": (Inntekt - G ) / G = Pensjonspoeng")
	public void hovedregelVedPensjonspoengberegningEr$spec$() {
		gitt.enInntektIGrunnbelopetLik(4);
		naar.pensjonspoengBeregnes();
		saa.skalAntallPensjonspoengVeareLik(3);
	}

	void enInntektIGrunnbelopetLik(double inntektIGrunnbeloepet) {
		this.inntektIGrunnbelopet = inntektIGrunnbeloepet;
	}

	void pensjonspoengBeregnes() {
		pensjonspoengBeregnet = pensjonspoeng.beregn(2000, inntektIGrunnbelopet * Grunnbeloep._2000);
	}

	void skalAntallPensjonspoengVeareLik(double i) {
		assertEquals(i, pensjonspoengBeregnet, .01);
	}

	@Test
	public void forAarene1967Til1970BleInntektInntilAatteGangerGrunnbeloepetMedregnet() {
		eksempel.pensjonspoengberegning("Inntekt p� 4G skal gi 3 pensjonspoeng", 1967, 4 * Grunnbeloep._1967, 3);
		eksempel.pensjonspoengberegning("Inntekt p� 8G skal gi maksgrensen p� 7 pensjonspoeng", 1967, 8 * Grunnbeloep._1967, 7);
		eksempel.pensjonspoengberegning("Inntekt p� 12G skal begrensens til maksgrensen p� 7 pensjonspoeng", 1967,
				12 * Grunnbeloep._1967, 7);
	}

	@Test
	public void forAarene1971Til1991BleInntektBegrensetTil12_G_HvorDenDelenAvInntektenSomOversteg8_G_kunBleMedregnetMedEnTredjedel() {
		eksempel.pensjonspoengberegning("Inntekt p� 8G skal gi 7 pensjonspoeng", 1971, 8 * Grunnbeloep._1971, 7);
		eksempel.pensjonspoengberegning("Inntekt p� 10G skal gi 7.67 pensjonspoeng", 1971, 10 * Grunnbeloep._1971, 7.67);
		eksempel.pensjonspoengberegning("Inntekt p� 12G skal gi maksgrensen p� 8.33 pensjonspoeng", 1971, 12 * Grunnbeloep._1971,
				8.33);
		eksempel.pensjonspoengberegning("Inntekt p� 14G skal begrenses til maksgrensen p� 8.33 pensjonspoeng", 1971,
				14 * Grunnbeloep._1971, 8.33);
	}

	@Test
	public void fra1992MedregnesBareEnTredjedelAvInntektenSomLiggerMellom6Og12_G() {
		eksempel.pensjonspoengberegning("Inntekt p� 4G skal gi 3 pensjonspoeng", 1992, 4 * Grunnbeloep._1992, 3);
		eksempel.pensjonspoengberegning("Inntekt p� 6G skal gi 5 pensjonspoeng", 1992, 6 * Grunnbeloep._1992, 5);
		eksempel.pensjonspoengberegning("Inntekt p� 8G skal gi 5.67 pensjonspoeng", 1992, 8 * Grunnbeloep._1992, 5.67);
		eksempel.pensjonspoengberegning("Inntekt p� 12G skal gi maksgrensen p� 7 pensjonspoeng", 1992, 12 * Grunnbeloep._1992, 7);
	}

	public void pensjonspoengberegning(String beskrivelse, int inntektsaar, int inntekt, double pensjonspoeng) {
		assertEquals(beskrivelse, pensjonspoeng, this.pensjonspoeng.beregn(inntektsaar, inntekt), .001);
	}

	@Test
	public void pensjonspoengOpptjenesKunNaarPensjonsgivendeInntektOverstigerGrunnbeloepet() {
		assertEquals(0, pensjonspoeng.beregn(2000, Grunnbeloep._2000 - 10), .001);
		assertEquals(1, pensjonspoeng.beregn(2000, Grunnbeloep._2000 * 2), .001);
	}

	@Test
	public void gjennomsnittligGrunnbeloepFraDetAaretInntektenGjaldtSkalBenyttesVedBeregningAvPensjonspoeng() {
		assertEquals(3.58, pensjonspoeng.beregn(2007, 300000), .001);
		assertEquals(3.34, pensjonspoeng.beregn(2008, 300000), .001);
	}

}
