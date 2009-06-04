package pensjonsberegning;

public class Alderspensjon {

	private Grunnpensjon grunnpensjon;
	Tilleggspensjon tilleggspensjon;

	public Alderspensjon(Grunnpensjon grunnpensjon, Tilleggspensjon tilleggspensjon) {
		this.grunnpensjon = grunnpensjon;
		this.tilleggspensjon = tilleggspensjon;
	}

	public double verdi() {
		return grunnpensjon.verdi() + tilleggspensjon.verdi();
	}

}
