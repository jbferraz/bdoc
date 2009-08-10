package pensjonsberegning.bdoc;

public enum Story {

	BEREGNING_AV_ALDERSPENSJON(1,
			"Som medlem av folketrygden og snart i pensjonsalder",//
			"Vil jeg registrere min inntekt for tidligere �r",
			"slik at jeg kan f� beregnet min alderspensjon fra folketrygden"), // 

	TABELL_FOR_GRUNNBELOEP(2,
			"Som medlem av folketrygden",// 
			"Vil jeg se en tabell over grunnbel�p pr �r fra 1967",
			"slik at jeg kan kontrollregne p� min beregnet alderspensjon fra folketrygden"),
	;

	private Integer id;
	private String[] narrative;

	Story(Integer id, String... narrative) {
		this.id = id;
		this.narrative = narrative;
	}

	public Integer id() {
		return id;
	}

	public String[] narrative() {
		return narrative;
	}
}
