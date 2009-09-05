package pensjonsberegning;


/**
 * @author Per Otto Bergum Christensen
 */
public class OpptjeningsAarTjeneste {

	private GrunnbeloepTabell grunnbeloepTabell;

	public OpptjeningsAarTjeneste(GrunnbeloepTabell grunnbeloepTabell) {
		this.grunnbeloepTabell = grunnbeloepTabell;
	}

	public OpptjeningsAarListe opprettOpptjeningsAarFor(MedlemAvFolketrygden medlem) {

		OpptjeningsAarListe result = new OpptjeningsAarListe();
		for (Inntekt inntekt : medlem.getInntekt()) {
			if (grunnbeloepTabell.gjennomsnittligGrunnbeloepFor(inntekt.getAar()) < inntekt.getBeloep()) {
				result.add(new OpptjeningsAar( inntekt.getAar(), Pensjonspoeng.beregn(inntekt, grunnbeloepTabell) ) );
			}
		}

		return result;
	}

}
