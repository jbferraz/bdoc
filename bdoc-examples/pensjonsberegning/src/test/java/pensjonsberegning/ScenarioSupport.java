package pensjonsberegning;

@SuppressWarnings("unchecked")
public class ScenarioSupport<T> {
	T gitt = createScenarioKeyword("gitt");
	T naar = createScenarioKeyword("naar");
	T saa = createScenarioKeyword("saa");
	T og = createScenarioKeyword("og");
	T hvor = createScenarioKeyword("hvor");
	T samtidig = createScenarioKeyword("samtidig");

	public T createScenarioKeyword(String name) {
		return (T) this;
	}
}
