package pensjonsberegning;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import pensjonsberegning.bdoc.Ref;
import pensjonsberegning.bdoc.RefClass;
import pensjonsberegning.bdoc.Story;

@Ref(Story.BEREGNING_AV_ALDERSPENSJON)
@RefClass(Sluttpoengtall.class)
public class TestSluttpoengtallBehaviour {

	@Test
	public void sluttpoengtalletErGjennomsnittetAvDe20HoeyestePoengtallene() {
		List<Double> poentallRekke = new ArrayList<Double>(); 
		poentallRekke.addAll( asList(1d, 2d, 3d, 4d, 5d, 6d, 7d, 8d, 1d, 2d, 3d, 4d, 5d, 6d, 7d, 8d, 1d, 2d, 3d, 4d) );
		poentallRekke.addAll( asList(1d, 1d, 1d, 1d, 1d, 1d, 1d, 1d, 1d, 1d, 1d, 1d, 1d, 1d, 1d) );
		
		eksempelPaaSluttpoengtallberegning("Rekke med 35 poengtall", poentallRekke, 4.1);
	}

	@Test
	public void vedOpptjentPensjonspoengI20AarEllerFaerreEnn20AarBeregnesSluttpoengtalletSomGjennomsnittetAvAllePoengtallene() {
		eksempelPaaSluttpoengtallberegning("Rekke med et poengtall", asList(1d), 1);
		eksempelPaaSluttpoengtallberegning("Rekke med to poengtall", asList(1d, 2d), 1.5);

		List<Double> poentallRekke = asList(1d, 2d, 3d, 4d, 5d, 6d, 7d, 8d, 1d, 2d, 3d, 4d, 5d, 6d, 7d, 8d, 1d, 2d, 3d, 4d);
		eksempelPaaSluttpoengtallberegning("Rekke med 20 poengtall", poentallRekke, 4.1);
	}

	public void eksempelPaaSluttpoengtallberegning(String beskrivelse, List<Double> pensjonspoengtall, double sluttpoengtall) {
		assertEquals(beskrivelse, sluttpoengtall, Sluttpoengtall.beregn(pensjonspoengtall), .001);
	}

	@Test
	public void sluttpoengtallAvrundesMedToDesimaler() {
		assertEquals("Poengtallrekke lik [3,3,4] skal gi sluttpoengtall = 3.33", 3.33, Sluttpoengtall.beregn(asList(3d, 3d, 4d)), .001);
	}
}
