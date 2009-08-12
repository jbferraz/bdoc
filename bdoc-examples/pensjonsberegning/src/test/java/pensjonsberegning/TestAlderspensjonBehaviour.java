package pensjonsberegning;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
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
public class TestAlderspensjonBehaviour {

	private Grunnpensjon grunnpensjon;
	private Tilleggspensjon tilleggspensjon;
	private Alderspensjon alderspensjon;

	@Test
	public void alderspensjonErLikGrunnpensjonPlussTilleggspensjon() {
		gittEnGrunnpensjonLik(50000);
		gittEnTilleggspensjonLik(100000);
		naarAlderspensjonBeregnes();
		saaSkalAlderspensjonVaereLik(150000);
	}

	void gittEnGrunnpensjonLik(final Integer verdi) {
		grunnpensjon = new Grunnpensjon(null,null) {
			public Double beregnet() {
				return verdi.doubleValue();
			}
		};
	}

	void gittEnTilleggspensjonLik(final Integer verdi) {
		tilleggspensjon = new Tilleggspensjon(null,null,null) {
			public Double beregnet() {
				return verdi.doubleValue();
			}
		};
	}

	void naarAlderspensjonBeregnes() {
		alderspensjon = new Alderspensjon(grunnpensjon, tilleggspensjon);
	}

	void saaSkalAlderspensjonVaereLik(double forventetAlderspensjon) {
		assertEquals(forventetAlderspensjon, alderspensjon.beregnet(), .001);
	}

	@Test
	@Ignore
	public void grunnpensjonErLikGrunnbeloepet() {
	}
}
