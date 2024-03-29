package pensjonsberegning;

/**
 * @author Per Otto Bergum Christensen
 */
public class Inntekt {

	private int aar;
	private double beloep;

	public Inntekt(int aar, double verdi) {
		this.aar = aar;
		this.beloep = verdi;
	}

	public double getBeloep() {
		return beloep;
	}

	public int getAar() {
		return aar;
	}

	@Override
	public String toString() {
		return aar + ": " + beloep;
	}

}
