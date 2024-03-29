package pensjonsberegning;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author Per Otto Bergum Christensen
 */
public class TestSluttpoengtall {

	@Test
	public void erSnittetAvOpptjentePoengtall() {
		verifiserSluttpoengtallberegning(asList(1d), 1);
		verifiserSluttpoengtallberegning(asList(1d, 2d), 1.5);
		verifiserSluttpoengtallberegning(asList(1d, 2d, 3d), 2);
	}

	@Test
	public void regnesUtBasertPaaDe20BestePoengtallene() {
		List<Double> tyvePoengtall = asList( //
				1d, 2d, 3d, 4d, 5d, 6d, 7d, 8d, //
				1d, 2d, 3d, 4d, 5d, 6d, 7d, 8d, //
				1d, 2d, 3d, 4d);//

		List<Double> enogtyvePoengtall = new ArrayList<Double>();
		enogtyvePoengtall.addAll(tyvePoengtall);
		enogtyvePoengtall.add(1d);
			
		verifiserSluttpoengtallberegning(tyvePoengtall, 4.1);
		verifiserSluttpoengtallberegning(enogtyvePoengtall, 4.1);
	}

	public void verifiserSluttpoengtallberegning(List<Double> pensjonspoengtall, double sluttpoengtall) {
		assertEquals(sluttpoengtall, Sluttpoengtall.beregn(pensjonspoengtall), .001);
	}

}
