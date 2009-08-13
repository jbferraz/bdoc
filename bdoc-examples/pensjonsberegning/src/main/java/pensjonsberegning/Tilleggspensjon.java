package pensjonsberegning;

/**
 * @author Per Otto Bergum Christensen
 */
public class Tilleggspensjon {

	private Integer grunnbeloep;
	private Double pensjonsprosent;
	private Double sluttpoengtall;
	private OpptjeningsAarListe opptjeningsAarListe;
	private Double beregnet;

	public Tilleggspensjon(Double manueltBeregnet) {
		this.beregnet = manueltBeregnet;
	}

	public Tilleggspensjon(Integer grunnbeloep, OpptjeningsAarListe opptjeningsAarListe) {
		this(grunnbeloep, new Pensjonsprosent(opptjeningsAarListe).beregnet(), Sluttpoengtall.beregn(opptjeningsAarListe
				.getPensjonspoengListe()));

		this.opptjeningsAarListe = opptjeningsAarListe;
	}

	public Tilleggspensjon(Integer grunnbeloep, Double pensjonsprosent, Double sluttpoengtall) {
		this.grunnbeloep = grunnbeloep;
		this.pensjonsprosent = pensjonsprosent;
		this.sluttpoengtall = sluttpoengtall;
		this.beregnet = beregnet(grunnbeloep, pensjonsprosent, sluttpoengtall);
	}

	public Double beregnet() {
		return beregnet;
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

	public static Double beregnet(Integer grunnbeloep, Double pensjonsprosent, Double sluttpoengtall) {
		return grunnbeloep * pensjonsprosent * sluttpoengtall;
	}

}
