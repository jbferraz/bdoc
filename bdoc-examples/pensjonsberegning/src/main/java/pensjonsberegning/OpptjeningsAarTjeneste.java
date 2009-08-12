package pensjonsberegning;


public class OpptjeningsAarTjeneste {

	private GrunnbeloepTabell grunnbeloepTabell;

	public OpptjeningsAarTjeneste(GrunnbeloepTabell grunnbeloepTabell) {
		this.grunnbeloepTabell = grunnbeloepTabell;
	}

	public OpptjeningsAarListe opprettOpptjeningsAarFor(MedlemAvFolketrygden medlem) {

		OpptjeningsAarListe result = new OpptjeningsAarListe();
		for (Inntekt inntekt : medlem.getInntekt()) {
			if (grunnbeloepTabell.gjennomsnittligGrunnbeloepFor(inntekt.aar()) < inntekt.beloep()) {
				result.add(new OpptjeningsAar( inntekt.aar(), Pensjonspoeng.beregn(inntekt, grunnbeloepTabell) ) );
			}
		}

		return result;
	}

}
