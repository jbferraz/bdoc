package pensjonsberegning;

public class Grunnpensjon {

	private Person person;
	private Integer grunnbeloep;

	public Grunnpensjon(Person person, Integer grunnbeloep) {
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
