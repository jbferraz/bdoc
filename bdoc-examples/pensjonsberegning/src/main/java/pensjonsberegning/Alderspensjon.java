package pensjonsberegning;

public class Alderspensjon {

	private Grunnbeloep grunnpensjon;
	Tilleggspensjon tilleggspensjon;

	public Alderspensjon(Grunnbeloep grunnpensjon, Tilleggspensjon tilleggspensjon) {
		this.grunnpensjon = grunnpensjon;
		this.tilleggspensjon = tilleggspensjon;
	}

	public double verdi() {
		return grunnpensjon.verdi() + tilleggspensjon.verdi();
	}

}
