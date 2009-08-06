package pensjonsberegning.bdoc;

import pensjonsberegning.Alderspensjon;
import pensjonsberegning.Pensjonspoeng;
import pensjonsberegning.Sluttpoengtall;
import pensjonsberegning.Tilleggspensjon;

public class ReportConfig {
	Class<?>[] presentationOrder = { Alderspensjon.class, Tilleggspensjon.class, Sluttpoengtall.class, Pensjonspoeng.class };
}
