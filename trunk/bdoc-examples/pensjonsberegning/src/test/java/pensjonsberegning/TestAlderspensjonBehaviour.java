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
		gitt.enGrunnpensjonLik(50000d);
		og.enTilleggspensjonLik(100000d);
		naar.alderspensjonBeregnes();
		saa.skalAlderspensjonPrAarVaereLik(150000);
	}

	void enGrunnpensjonLik(Double manueltBeregnet) {
		grunnpensjon = new Grunnpensjon(manueltBeregnet);
	}

	void enTilleggspensjonLik(Double manueltBeregnet) {
		tilleggspensjon = new Tilleggspensjon(manueltBeregnet);
	}

	void alderspensjonBeregnes() {
		alderspensjon = new Alderspensjon(grunnpensjon, tilleggspensjon);
	}

	void skalAlderspensjonPrAarVaereLik(double forventetAlderspensjon) {
		assertEquals(forventetAlderspensjon, alderspensjon.beregnet(), .001);
	}

}
