package pensjonsberegning;

public class Tilleggspensjon {

	private Integer grunnbeloep;
	private Double pensjonsprosent;
	private Double sluttpoengtall;

	public Tilleggspensjon(Integer grunnbeloep, Double pensjonsprosent, Double sluttpoengtall) {
		this.grunnbeloep = grunnbeloep;
		this.pensjonsprosent = pensjonsprosent;
		this.sluttpoengtall = sluttpoengtall;
	}

	public Double beregnet() {
		return grunnbeloep * pensjonsprosent * sluttpoengtall;
	}

	public Integer getGrunnbeloep() {
		return grunnbeloep;
	}

	public Double getPensjonsprosent() {
		return pensjonsprosent;
	}

	public Double getSluttpoengtall() {
		return sluttpoengtall;
	}

}
