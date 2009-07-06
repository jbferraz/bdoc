package pensjonsberegning;

public class Inntekt {

	private int aar;
	private double verdi;

	public Inntekt(int aar, double verdi) {
		this.aar = aar;
		this.verdi = verdi;
	}

	public double verdi() {
		return verdi;
	}

	public int aar() {
		return aar;
	}

	@Override
	public String toString() {
		return aar + ": " + verdi;
	}

}
