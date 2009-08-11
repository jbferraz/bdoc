package pensjonsberegning;

public class Alderspensjon {

	Grunnpensjon grunnpensjon;
	Tilleggspensjon tilleggspensjon;

	public Alderspensjon(Grunnpensjon grunnpensjon, Tilleggspensjon tilleggspensjon) {
		this.grunnpensjon = grunnpensjon;
		this.tilleggspensjon = tilleggspensjon;
	}

	public double beregnet() {
		return grunnpensjon.beregnet() + tilleggspensjon.beregnet();
	}

}
