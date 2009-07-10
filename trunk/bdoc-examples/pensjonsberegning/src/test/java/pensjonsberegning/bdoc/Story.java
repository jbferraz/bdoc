package pensjonsberegning.bdoc;

public enum Story {

	BEREGNING_AV_ALDERSPENSJON(1, "Som medlem av folketrygden",
			"Vil jeg registrere min forventet inntekt fram til ønsket pensjonsalder",
			"slik at jeg kan få beregnet min alderspensjon fra folketrygden");

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
