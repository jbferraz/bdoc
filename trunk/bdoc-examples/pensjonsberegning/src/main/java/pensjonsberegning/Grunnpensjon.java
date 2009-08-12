package pensjonsberegning;

public class Grunnpensjon {

	private MedlemAvFolketrygden person;
	private Integer grunnbeloep;

	public Grunnpensjon(MedlemAvFolketrygden person, Integer grunnbeloep) {
		this.person = person;
		this.grunnbeloep = grunnbeloep;
	}

	public Double beregnet() {
		if (null != person.getEktefelleEllerSamboerSinInntekt() && (2 * grunnbeloep < person.getEktefelleEllerSamboerSinInntekt())) {
			return 0.85 * grunnbeloep.doubleValue();
		}

		if (person.getGiftEllerHarSamboerMedPensjonFraFolketrygdenEllerAfp()) {
			return 0.85 * grunnbeloep.doubleValue();
		}
		return grunnbeloep.doubleValue();
	}

}
