package pensjonsberegning;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pensjonsberegning.bdoc.Ref;
import pensjonsberegning.bdoc.RefClass;
import pensjonsberegning.bdoc.Story;

/**
 * @author Per Otto Bergum Christensen
 */
@Ref(Story.BEREGNING_AV_ALDERSPENSJON)
@RefClass(Alderspensjon.class)
public class TestAlderspensjonBehaviour extends ScenarioSupport<TestAlderspensjonBehaviour> {

	private Grunnpensjon grunnpensjon;
	private Tilleggspensjon tilleggspensjon;
	private Alderspensjon alderspensjon;

	@Test
	public void alderspensjonErLikGrunnpensjonPlussTilleggspensjon() {
		gittEnGrunnpensjonLik(50000d);
		gittEnTilleggspensjonLik(100000d);
		naarAlderspensjonBeregnes();
		saaSkalAlderspensjonPrAarVaereLik(150000);
	}

	void gittEnGrunnpensjonLik(Double manueltBeregnet) {
		grunnpensjon = new Grunnpensjon(manueltBeregnet);
	}

	void gittEnTilleggspensjonLik(Double manueltBeregnet) {
		tilleggspensjon = new Tilleggspensjon(manueltBeregnet);
	}

	void naarAlderspensjonBeregnes() {
		alderspensjon = new Alderspensjon(grunnpensjon, tilleggspensjon);
	}

	void saaSkalAlderspensjonPrAarVaereLik(double forventetAlderspensjon) {
		assertEquals(forventetAlderspensjon, alderspensjon.beregnet(), .001);
	}

}
