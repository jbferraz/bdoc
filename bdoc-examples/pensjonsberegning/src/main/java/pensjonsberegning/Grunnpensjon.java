package pensjonsberegning;

/**
 * @author Per Otto Bergum Christensen
 */
public class Grunnpensjon {

	private Double beregnet;

	public Grunnpensjon(Double manueltBeregnet) {
		this.beregnet = manueltBeregnet;
	}

	public Grunnpensjon(MedlemAvFolketrygden medlem, Integer grunnbeloep) {
		this.beregnet = beregnet(medlem, grunnbeloep);
	}

	public Double beregnet() {
		return beregnet;
	}
	
	public static Double beregnet(MedlemAvFolketrygden medlem, Integer grunnbeloep) {
		if (null != medlem.getEktefelleEllerSamboerSinInntekt() && (2 * grunnbeloep < medlem.getEktefelleEllerSamboerSinInntekt())) {
			return 0.85 * grunnbeloep.doubleValue();
		}

		if (medlem.getGiftEllerHarSamboerMedPensjonFraFolketrygdenEllerAfp()) {
			return 0.85 * grunnbeloep.doubleValue();
		}
		return grunnbeloep.doubleValue();
	}
	

}
