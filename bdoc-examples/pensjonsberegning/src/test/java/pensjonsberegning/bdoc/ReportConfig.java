package pensjonsberegning.bdoc;

import pensjonsberegning.Alderspensjon;
import pensjonsberegning.FolketrygdberegningTjeneste;
import pensjonsberegning.Grunnpensjon;
import pensjonsberegning.Pensjonspoeng;
import pensjonsberegning.Pensjonsprosent;
import pensjonsberegning.Sluttpoengtall;
import pensjonsberegning.Tilleggspensjon;

public class ReportConfig {
	Class<?>[] presentationOrder = {
	//
			FolketrygdberegningTjeneste.class,//
			Alderspensjon.class, //
			Grunnpensjon.class,//
			Tilleggspensjon.class, //			
			Pensjonsprosent.class,//
			Sluttpoengtall.class,//
			Pensjonspoeng.class };

}
