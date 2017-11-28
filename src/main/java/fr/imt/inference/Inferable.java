package fr.imt.inference;

import io.vavr.control.Either;

public interface Inferable<T, R> {
    Either<String, R> infer(T expression);
}
