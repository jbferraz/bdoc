package pensjonsberegning.bdoc;

import pensjonsberegning.Alderspensjon;
import pensjonsberegning.Pensjonspoeng;
import pensjonsberegning.Pensjonsprosent;
import pensjonsberegning.Sluttpoengtall;
import pensjonsberegning.Tilleggspensjon;

public class ReportConfig {
	Class<?>[] presentationOrder = {
	//
			Alderspensjon.class, // 
			Tilleggspensjon.class, //			
			Pensjonsprosent.class,//
			Sluttpoengtall.class,//
			Pensjonspoeng.class };
}
