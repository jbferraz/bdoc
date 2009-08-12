package pensjonsberegning;


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
		Double pensjonsprosent = new Pensjonsprosent(opptjeningsAarListe).beregnet();
		Double sluttpoengtall = Sluttpoengtall.beregn(opptjeningsAarListe.getPensjonspoengListe());

		Tilleggspensjon tilleggspensjon = new Tilleggspensjon(grunnbeloepForAar, pensjonsprosent, sluttpoengtall);
		
		return new Alderspensjon(grunnpensjon, tilleggspensjon);
	}
}
