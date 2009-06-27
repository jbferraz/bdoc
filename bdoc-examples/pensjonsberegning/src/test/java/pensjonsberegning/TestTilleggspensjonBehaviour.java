package pensjonsberegning;

import org.junit.Test;

import pensjonsberegning.bdoc.Ref;
import pensjonsberegning.bdoc.RefClass;
import pensjonsberegning.bdoc.Story;

@Ref(Story.BEREGNING_AV_ALDERSPENSJON)
@RefClass(Tilleggspensjon.class)
public class TestTilleggspensjonBehaviour {

	@Test
	public void tilleggspensjonErLikGrunnbeloepet_x_Sluttpoengtall_x_Pensjonsprosent() {
		gittEnGrunnpensjonLik(70256);
		gittEtSluttpoengtallLik(5.16);
		gittAntallAarMed45SomPensjonsProsent(25);
		gittAntallAarMed42SomPensjonsProsent(15);
		naarBeregningAvTilleggspensjonUtfoeres();
		saaSkalTilleggspensjonPerAarVaereLik(159056);
	}

	void gittEnGrunnpensjonLik(int i) {
	}

	void gittEtSluttpoengtallLik(double d) {
	}

	void gittAntallAarMed42SomPensjonsProsent(int i) {
	}

	void gittAntallAarMed45SomPensjonsProsent(int i) {
	}

	void naarBeregningAvTilleggspensjonUtfoeres() {
	}

	void saaSkalTilleggspensjonPerAarVaereLik(double d) {
	}
}
