package pensjonsberegning;

public class Alderspensjon {

	Double grunnpensjon;
	Tilleggspensjon tilleggspensjon;
	

	public Alderspensjon(Grunnpensjon grunnpensjon, Tilleggspensjon tilleggspensjon) {
		this.grunnpensjon = grunnpensjon.beregnet();
		this.tilleggspensjon = tilleggspensjon;
	}

	public double beregnet() {
		return grunnpensjon + tilleggspensjon.beregnet();
	}

	public Double getGrunnpensjon() {
		return grunnpensjon;
	}

	public Tilleggspensjon getTilleggspensjon() {
		return tilleggspensjon;
	}
	
}
