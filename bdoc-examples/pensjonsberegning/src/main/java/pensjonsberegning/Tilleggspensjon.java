package pensjonsberegning;

public class Tilleggspensjon {

	private Integer grunnbeloep;
	private Pensjonsprosent pensjonsprosent;
	private Sluttpoengtall sluttpoengtall;

	public Tilleggspensjon(Person person, GrunnbeloepTabell grunnbeloepTabell) {
		this( //
				grunnbeloepTabell.sisteVerdi(), // 
				new Pensjonsprosent(person.getInntekt(), grunnbeloepTabell),//
				new Sluttpoengtall(person.getInntekt(), grunnbeloepTabell));
	}

	public Tilleggspensjon(Integer grunnbeloep, Pensjonsprosent pensjonsprosent, Sluttpoengtall sluttpoengtall) {
		this.grunnbeloep = grunnbeloep;
		this.pensjonsprosent = pensjonsprosent;
		this.sluttpoengtall = sluttpoengtall;
	}

	public Double beregnet() {
		return grunnbeloep * pensjonsprosent.beregnet() * sluttpoengtall.beregnet();
	}

}
