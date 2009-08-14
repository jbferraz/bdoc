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
@RefClass(FolketrygdberegningTjeneste.class)
public class TestFolketrygdberegningTjeneste {

	private GrunnbeloepTabell grunnbeloepTabell = new GrunnbeloepTabell();
	private FolketrygdberegningTjeneste folketrygdberegningTjeneste = new FolketrygdberegningTjeneste(grunnbeloepTabell);

	@Test
	public void skalBeregneAlderspensjonForEtMedlemAvFolketrygden() {

		MedlemAvFolketrygden person = new MedlemAvFolketrygden();
		// 40 �r med maksimal opptjening
		for (int aar = 1970; aar < 2010; aar++) {
			person.addInntektForAar(aar, grunnbeloepTabell.gjennomsnittligGrunnbeloepFor(aar) * 12);
		}

		Alderspensjon alderspensjon = folketrygdberegningTjeneste.beregnAlderspensjonFor(person, 2009);
		assertEquals(Grunnbeloep._2009.doubleValue(), alderspensjon.getGrunnpensjon(), .001);

		Tilleggspensjon tilleggspensjon = alderspensjon.getTilleggspensjon();

		assertEquals(8.33, tilleggspensjon.getSluttpoengtall(), .001);
		assertEquals(0.4365, tilleggspensjon.getPensjonsprosent(), .0001);
		assertEquals(Grunnbeloep._2009, tilleggspensjon.getGrunnbeloep());
		assertEquals(261817, tilleggspensjon.beregnet(), .1);
	}
}