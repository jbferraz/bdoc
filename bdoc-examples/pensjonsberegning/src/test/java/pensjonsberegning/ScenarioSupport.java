package pensjonsberegning;

@SuppressWarnings("unchecked")
public class ScenarioSupport<T> {
	T gitt = createScenarioKeywordGiven("gitt");
	T naar = createScenarioKeywordWhen("naar");
	T saa = createScenarioKeywordThen("saa");
	T og = createScenarioKeywordGeneric("og");
	T hvor = createScenarioKeywordGeneric("hvor");
	T samtidig = createScenarioKeywordGeneric("samtidig");

	T eksempel = createExampleKeyword("eksempel");

	public T createScenarioKeywordGiven(String localizedGiven) {
		return (T) this;
	}

	public T createScenarioKeywordWhen(String localizedWhen) {
		return (T) this;
	}

	public T createScenarioKeywordThen(String localizedThen) {
		return (T) this;
	}

	public T createScenarioKeywordGeneric(String localizedGeneric) {
		return (T) this;
	}

	public T createExampleKeyword(String name) {
		return (T) this;
	}
}
