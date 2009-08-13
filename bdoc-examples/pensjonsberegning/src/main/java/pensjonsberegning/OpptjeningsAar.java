package pensjonsberegning;

/**
 * @author Per Otto Bergum Christensen
 */
public class OpptjeningsAar {

	private int aar;
	private double pensjonspoeng;

	public OpptjeningsAar(int aar, double pensjonspoeng) {
		super();
		this.aar = aar;
		this.pensjonspoeng = pensjonspoeng;
	}

	public int getAar() {
		return aar;
	}

	public double getPensjonspoeng() {
		return pensjonspoeng;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof OpptjeningsAar)) {
			return false;
		}
		return (aar == ((OpptjeningsAar) obj).aar) && (pensjonspoeng == ((OpptjeningsAar) obj).pensjonspoeng);
	}

	@Override
	public String toString() {
		return aar + ": " + pensjonspoeng;
	}

}