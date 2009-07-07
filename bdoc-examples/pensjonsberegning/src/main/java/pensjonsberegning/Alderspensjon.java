package pensjonsberegning;

public class Alderspensjon {

	private Integer grunnpensjon;
	Tilleggspensjon tilleggspensjon;

	public Alderspensjon(Integer grunnpensjon, Tilleggspensjon tilleggspensjon) {
		this.grunnpensjon = grunnpensjon;
		this.tilleggspensjon = tilleggspensjon;
	}

	public double verdi() {
		return grunnpensjon + tilleggspensjon.verdi();
	}

}
