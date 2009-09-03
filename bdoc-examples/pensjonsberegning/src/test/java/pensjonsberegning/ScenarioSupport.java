package pensjonsberegning;

@SuppressWarnings("unchecked")
public class ScenarioSupport<T> {
	T gitt = createScenarioKeyword("gitt", false);
	T naar = createScenarioKeyword("naar", false);
	T saa = createScenarioKeyword("saa", false);
	T og = createScenarioKeyword("og", true);
	T hvor = createScenarioKeyword("hvor", true);
	T samtidig = createScenarioKeyword("samtidig", true);

	T eksempel = createExampleKeyword("eksempel");

	public T createScenarioKeyword(String name, boolean indent) {
		return (T) this;
	}

	public T createExampleKeyword(String name) {
		return (T) this;
	}
}
