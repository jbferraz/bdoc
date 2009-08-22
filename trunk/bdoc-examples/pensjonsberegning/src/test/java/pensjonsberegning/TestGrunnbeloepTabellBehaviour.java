package pensjonsberegning;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import pensjonsberegning.bdoc.Ref;
import pensjonsberegning.bdoc.RefClass;
import pensjonsberegning.bdoc.Story;

/**
 * Liste over grunnbeløp: http://www.nav.no/page?id=1073744172
 * 
 * @author Per Otto Bergum Christensen
 * 
 */
@RefClass(GrunnbeloepTabell.class)
public class TestGrunnbeloepTabellBehaviour {

	private GrunnbeloepTabell grunnbeloepRepository = new GrunnbeloepTabell();

	@Test
	public void inneholderGjennomsnittligGrunnbelopForAarene1967Til2008() {
		for (int aar = 1967; aar < 2009; aar++) {
			eksempelPaaGrunnbelopForEtGittAar(aar, grunnbeloepRepository.gjennomsnittligGrunnbeloepFor(aar));
		}
	}

	void eksempelPaaGrunnbelopForEtGittAar(Integer aar, Integer grunnbeloep) {
		assertNotNull("Skal inneholde grunnbelop for " + aar, grunnbeloep);
	}

	@Test
	public void erStigendeHvertAarFra1967() {
		int grunnbeloep = Grunnbeloep._1967;
		for (int aar = 1968; aar < 2009; aar++) {
			Integer grunnbeloepAaretEtter = grunnbeloepRepository.gjennomsnittligGrunnbeloepFor(aar);

			assertTrue("Grunnbeloep skal være høyere i [" + aar + "] enn året før", grunnbeloep < grunnbeloepAaretEtter);
			grunnbeloep = grunnbeloepAaretEtter;
		}
	}

}
