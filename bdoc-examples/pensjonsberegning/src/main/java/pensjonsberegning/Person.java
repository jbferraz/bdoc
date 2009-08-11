package pensjonsberegning;

import java.util.ArrayList;
import java.util.List;

public class Person {
	private List<Inntekt> inntekt = new ArrayList<Inntekt>();
	private Boolean giftEllerHarSamboerMedPensjonFraFolketrygdenEllerAfp;
	private Integer ektefelleEllerSamboerSinInntekt;

	public Boolean getGiftEllerHarSamboerMedPensjonFraFolketrygdenEllerAfp() {
		return Boolean.TRUE.equals(giftEllerHarSamboerMedPensjonFraFolketrygdenEllerAfp);
	}

	public void setEktefelleEllerSamboerMedPensjonFraFolketrygdenEllerAfp(Boolean ektefelleEllerSamboerMedPensjonFraFolketrygden) {
		this.giftEllerHarSamboerMedPensjonFraFolketrygdenEllerAfp = ektefelleEllerSamboerMedPensjonFraFolketrygden;
	}

	public Integer getEktefelleEllerSamboerSinInntekt() {
		return ektefelleEllerSamboerSinInntekt;
	}

	public void setEktefelleEllerSamboerSinInntekt(Integer ektefelleEllerSamboerSinInntekt) {
		this.ektefelleEllerSamboerSinInntekt = ektefelleEllerSamboerSinInntekt;

	}

	public List<Inntekt> getInntekt() {
		return inntekt;
	}
}
