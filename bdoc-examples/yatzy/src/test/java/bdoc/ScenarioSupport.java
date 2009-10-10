package bdoc;

@SuppressWarnings("unchecked")
public class ScenarioSupport<T> {
	protected T given = createScenarioKeywordGiven("given");
	protected T when = createScenarioKeywordWhen("when");
	protected T then = createScenarioKeywordThen("then");
	protected T and = createScenarioKeywordGeneric("and");

	protected T example = createExampleKeyword("example");

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
