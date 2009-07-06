package pensjonsberegning;

public class Grunnbeloep {

	private double verdi;

	public Grunnbeloep(double verdi) {
		this.verdi = verdi;
	}

	public double verdi() {
		return verdi;
	}

	@Override
	public String toString() {
		return String.valueOf(verdi);
	}

}
