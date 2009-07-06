package pensjonsberegning;

import org.joda.time.DateMidnight;

public class Inntekt {

	private DateMidnight aar;
	private double verdi;

	public Inntekt(DateMidnight aar, double verdi) {
		this.aar = aar;
		this.verdi = verdi;
	}

	public double verdi() {
		return verdi;
	}

	public DateMidnight aar() {
		return aar;
	}

}
