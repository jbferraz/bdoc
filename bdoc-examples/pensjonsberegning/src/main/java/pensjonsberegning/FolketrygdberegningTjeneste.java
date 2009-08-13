package pensjonsberegning;


/**
 * @author Per Otto Bergum Christensen
 */
public class FolketrygdberegningTjeneste {

	private GrunnbeloepTabell grunnbeloepTabell;
	private OpptjeningsAarTjeneste opptjeningsAarTjeneste;

	public FolketrygdberegningTjeneste(GrunnbeloepTabell grunnbeloepTabell) {
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
