package pensjonsberegning;

public class Tilleggspensjon {

	private Integer grunnbeloep;
	private Double pensjonsprosent;
	private Double sluttpoengtall;
	private OpptjeningsAarListe opptjeningsAarListe;

	public Tilleggspensjon(Integer grunnbeloep, OpptjeningsAarListe opptjeningsAarListe) {
		this(grunnbeloep, new Pensjonsprosent(opptjeningsAarListe).beregnet(), Sluttpoengtall.beregn(opptjeningsAarListe
				.getPensjonspoengListe()));

		this.opptjeningsAarListe = opptjeningsAarListe;
	}

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

	public OpptjeningsAarListe getOpptjeningsAarListe() {
		return opptjeningsAarListe;
	}

}
