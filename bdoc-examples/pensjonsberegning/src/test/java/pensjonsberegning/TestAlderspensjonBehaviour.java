package pensjonsberegning;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pensjonsberegning.bdoc.Ref;
import pensjonsberegning.bdoc.RefClass;
import pensjonsberegning.bdoc.Story;

/**
 * Total oversikt over begreper i pensjon:
 * http://www.pensjonsreform.no.htest.osl
 * .basefarm.net/ordliste.asp?searchtxt=pensjon&word=Alderspensjon
 * 
 * Pensjon etter skattereformen: http://www.smartepenger.no/article.php?id=1311
 * Alderspensjon i dag: http://www.smartepenger.no/article.php?id=937 Nav sin
 * side for dinpensjon:
 * https://applikasjoner.nav.no/dinpensjon/pselv/tilleggsfunksjonalitet
 * /innlogging.jsf?_flowId=innlogging-flow
 * 
 * @author Per Otto
 * 
 */
@Ref(Story.BEREGNING_AV_ALDERSPENSJON)
@RefClass(Alderspensjon.class)
public class TestAlderspensjonBehaviour extends ScenarioSupport<TestAlderspensjonBehaviour> {
	
	private Grunnpensjon grunnpensjon;
	private Tilleggspensjon tilleggspensjon;
	private Alderspensjon alderspensjon;
	
	@Test
	public void alderspensjonErLikGrunnpensjonPlussTilleggspensjon() {
		gitt.grunnpensjon_er(50000.0);
		og.tilleggspensjon_er(100000.0);
		naar.alderspensjon_beregnes();
		saa.skal_alderspensjon_vaere_lik(150000);
	}

	void grunnpensjon_er(Double manueltBeregnet) {
		grunnpensjon = new Grunnpensjon(manueltBeregnet);
	}

	void tilleggspensjon_er(Double manueltBeregnet) {
		tilleggspensjon = new Tilleggspensjon(manueltBeregnet);
	}

	void alderspensjon_beregnes() {
		alderspensjon = new Alderspensjon(grunnpensjon, tilleggspensjon);
	}

	void skal_alderspensjon_vaere_lik(double forventetAlderspensjon) {
		assertEquals(forventetAlderspensjon, alderspensjon.beregnet(), .001);
	}

}
