package pensjonsberegning.bdoc;

import pensjonsberegning.Alderspensjon;
import pensjonsberegning.Folketrygdberegningtjeneste3;
import pensjonsberegning.Grunnpensjon;
import pensjonsberegning.Pensjonspoeng;
import pensjonsberegning.Pensjonsprosent;
import pensjonsberegning.Sluttpoengtall;
import pensjonsberegning.Tilleggspensjon;

public class ReportConfig {
	Class<?>[] presentationOrder = {
	//
			Folketrygdberegningtjeneste3.class,//
			Alderspensjon.class, //
			Grunnpensjon.class,//
			Tilleggspensjon.class, //			
			Pensjonsprosent.class,//
			Sluttpoengtall.class,//
			Pensjonspoeng.class };

}
