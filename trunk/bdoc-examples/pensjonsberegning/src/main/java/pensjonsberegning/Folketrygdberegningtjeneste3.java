package pensjonsberegning;


/**
 * @author Per Otto Bergum Christensen
 */
public class Folketrygdberegningtjeneste3 {

	private GrunnbeloepTabell grunnbeloepTabell;
	private OpptjeningsAarTjeneste opptjeningsAarTjeneste;

	public Folketrygdberegningtjeneste3(GrunnbeloepTabell grunnbeloepTabell) {
		this.grunnbeloepTabell = grunnbeloepTabell;
		this.opptjeningsAarTjeneste = new OpptjeningsAarTjeneste(grunnbeloepTabell);
	}

	public Alderspensjon beregnAlderspensjonFor(MedlemAvFolketrygden medlem, int aar) {

		Integer grunnbeloepForAar = grunnbeloepTabell.gjennomsnittligGrunnbeloepFor(aar);
		Grunnpensjon grunnpensjon = new Grunnpensjon(medlem, grunnbeloepForAar);

		OpptjeningsAarListe opptjeningsAarListe = opptjeningsAarTjeneste.opprettOpptjeningsAarFor(medlem);

		Tilleggspensjon tilleggspensjon = new Tilleggspensjon(grunnbeloepForAar, opptjeningsAarListe );
		
		return new Alderspensjon(grunnpensjon, tilleggspensjon);
	}
}
