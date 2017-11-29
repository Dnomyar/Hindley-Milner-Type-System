package fr.imt.parser;

import fr.imt.inference.Inferable;
import fr.imt.logger.Color;

public class InferenceProcessor<T> implements ReplProcessable<T> {

    private Inferable<T, ?> inferer;

    public InferenceProcessor(Inferable<T, ?> inferer) {
        this.inferer = inferer;
    }

    @Override
    public String process(T value) {
        return inferer.infer(value)
                .fold(
                        inferError -> Color.red("Inference error : " + inferError),
                        Object::toString
                );
    }
}
