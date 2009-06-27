package pensjonsberegning;

public class Alderspensjon {

	private GjennomsnittligGrunnbeloep grunnpensjon;
	Tilleggspensjon tilleggspensjon;

	public Alderspensjon(GjennomsnittligGrunnbeloep grunnpensjon, Tilleggspensjon tilleggspensjon) {
		this.grunnpensjon = grunnpensjon;
		this.tilleggspensjon = tilleggspensjon;
	}

	public double verdi() {
		return grunnpensjon.verdi() + tilleggspensjon.verdi();
	}

}
