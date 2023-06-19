package epamLambda.task1;
import java.io.IOException;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        // declaration
        ThrowingFunction<String, Integer, IOException> ioFunc = new ThrowingFunction<>() {
            @Override
            public Integer apply(String fileName) throws IOException {
                return fileName.length();
            }
        };
        Function<String, Integer> func = ThrowingFunction.quiet(ioFunc);
        System.out.println(func.apply("big-file.csv"));
    }
}
