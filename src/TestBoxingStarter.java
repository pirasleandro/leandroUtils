import com.github.pirasleandro.io.TablePrinter;
import com.github.pirasleandro.io.TablePrinter.Meta;

public class TestBoxingStarter {
    public static void main(String[] args) {
        Object[][] test = {
            {1, 2, 3, 434},
            {5, 2346, 7, 8}
        };

        TablePrinter.print(test, Meta.ROUNDED_CORNERS);
    }
}
