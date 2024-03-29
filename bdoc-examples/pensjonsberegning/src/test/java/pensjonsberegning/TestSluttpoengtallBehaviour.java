package pensjonsberegning;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import pensjonsberegning.bdoc.Ref;
import pensjonsberegning.bdoc.RefClass;
import pensjonsberegning.bdoc.Story;

/**
 * @author Per Otto Bergum Christensen
 */
@Ref(Story.BEREGNING_AV_ALDERSPENSJON)
@RefClass(Sluttpoengtall.class)
public class TestSluttpoengtallBehaviour extends ScenarioSupport<TestSluttpoengtallBehaviour>{

	@Test
	public void erGjennomsnittetAvDe20HoeyestePoengtallene() {
		List<Double> poentallRekke = new ArrayList<Double>();
		poentallRekke.addAll(asList(1d, 2d, 3d, 4d, 5d, 6d, 7d, 8d, 1d, 2d, 3d, 4d, 5d, 6d, 7d, 8d, 1d, 2d, 3d, 4d));
		poentallRekke.addAll(asList(1d, 1d, 1d, 1d, 1d, 1d, 1d, 1d, 1d, 1d, 1d, 1d, 1d, 1d, 1d));

		eksempel.sluttpoengtallberegning("Rekke med 35 poengtall", poentallRekke, 4.1);
	}

	@Test
	public void erSnittetAvPoengtalleneNaarAntallPoengtallEr20EllerMindre() {
		eksempel.sluttpoengtallberegning("Rekke med et poengtall", asList(1d), 1);
		eksempel.sluttpoengtallberegning("Rekke med to poengtall", asList(1d, 2d), 1.5);

		List<Double> poentallRekke = asList(5d, 5d, 5d, 5d, 5d, 
				6d, 6d, 6d, 6d, 6d, 6d, 6d, 6d, 6d, 6d, 
				7d, 7d, 7d, 7d, 7d);
		eksempel.sluttpoengtallberegning("Rekke med 20 poengtall", poentallRekke, 6);
	}

	public void sluttpoengtallberegning(String beskrivelse, List<Double> pensjonspoengtall, double sluttpoengtall) {
		assertEquals(beskrivelse, sluttpoengtall, Sluttpoengtall.beregn(pensjonspoengtall), .001);
	}

	@Test
	public void sluttpoengtallAvrundesMedToDesimaler() {
		assertEquals("Poengtallrekke lik [3,3,4] skal gi sluttpoengtall = 3.33", 3.33, Sluttpoengtall.beregn(asList(3d, 3d, 4d)), .001);
	}
}
