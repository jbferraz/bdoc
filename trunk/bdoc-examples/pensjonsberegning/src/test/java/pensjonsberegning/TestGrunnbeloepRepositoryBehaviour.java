package pensjonsberegning;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import pensjonsberegning.bdoc.RefClass;

@RefClass(GrunnbeloepRepository.class)
public class TestGrunnbeloepRepositoryBehaviour {

	private GrunnbeloepRepository grunnbeloepRepository = new GrunnbeloepRepository();

	@Test
	public void skalInneholdeGjennomsnittligGrunnbelopForAarene1967Til2008() {
		for (int aar = 1967; aar < 2009; aar++) {
			eksempelPaaGrunnbelopForEtGittAar(aar, grunnbeloepRepository.gjennomsnittligGrunnbeloepFor(aar));
		}
	}

	void eksempelPaaGrunnbelopForEtGittAar(Integer aar, Grunnbeloep grunnbeloep) {
		assertNotNull("Skal inneholde grunnbelop for " + aar, grunnbeloep);
	}

	@Test
	public void grunnbelopetErStigendeHvertAarFra1967() {
		int grunnbeloep = GrunnbeloepRepository._1967;
		for (int aar = 1968; aar < 2009; aar++) {
			Grunnbeloep grunnbeloepAaretEtter = grunnbeloepRepository.gjennomsnittligGrunnbeloepFor(aar);

			assertTrue("Grunnbeloep skal være høyere i [" + aar + "] enn året før", grunnbeloep < grunnbeloepAaretEtter.verdi());
			grunnbeloep = grunnbeloepAaretEtter.verdi();
		}
	}

}
