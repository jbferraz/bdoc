package pensjonsberegning;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import pensjonsberegning.bdoc.Ref;
import pensjonsberegning.bdoc.RefClass;
import pensjonsberegning.bdoc.Story;

@Ref(Story.BEREGNING_AV_ALDERSPENSJON)
@RefClass( FolketrygdberegningTjeneste.class )
public class TestFolketrygdberegningTjeneste {

	private GrunnbeloepTabell grunnbeloepTabell = new GrunnbeloepTabell();
	private FolketrygdberegningTjeneste folketrygdberegningTjeneste = new FolketrygdberegningTjeneste(grunnbeloepTabell);

	@Test
	public void skalBeregneAlderspensjonForMedlemAvFolketrygden() {

		MedlemAvFolketrygden person = new MedlemAvFolketrygden();
		for (int aar = 1970; aar < 2010; aar++) {
			person.addInntektForAar(aar, grunnbeloepTabell.gjennomsnittligGrunnbeloepFor(aar) * 12);
		}

		Alderspensjon alderspensjon = folketrygdberegningTjeneste.beregnAlderspensjonFor(person, 2009);
		assertEquals(Grunnbeloep._2009.doubleValue(), alderspensjon.getGrunnpensjon(), .001);

		assertEquals(8.33, alderspensjon.getTilleggspensjon().getSluttpoengtall(), .001);
		
//		assertEquals(0.44, alderspensjon.getTilleggspensjon().getPensjonsprosent(), .001);
//		assertEquals(261367.2, alderspensjon.getTilleggspensjon().beregnet(), .1);
	}
}
