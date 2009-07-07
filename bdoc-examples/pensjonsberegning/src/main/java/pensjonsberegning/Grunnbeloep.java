package pensjonsberegning;

public class Grunnbeloep {

	private int verdi;

	public Grunnbeloep(int verdi) {
		this.verdi = verdi;
	}

	public int verdi() {
		return verdi;
	}

	@Override
	public String toString() {
		return String.valueOf(verdi);
	}

}
