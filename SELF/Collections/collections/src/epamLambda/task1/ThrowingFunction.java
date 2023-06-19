package epamLambda.task1;

import java.util.function.Function;

@FunctionalInterface
public interface ThrowingFunction<A, R, E extends Throwable> {
    R apply(A a) throws E; // abstract method

    static <T, R, E extends Exception> Function<T, R> quiet(ThrowingFunction<T, R, E> t) {
        if (t == null) return null;

        return p -> {
            try {
                return t.apply(p);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}