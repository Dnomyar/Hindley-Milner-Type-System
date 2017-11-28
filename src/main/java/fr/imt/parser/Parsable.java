package fr.imt.parser;

import io.vavr.control.Either;

public interface Parsable<T> {
    Either<String, T> parse(String str);
}
