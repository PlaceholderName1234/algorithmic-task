import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void testMainDoesNotThrowException() {
        assertDoesNotThrow(() -> Main.main(new String[]{}));
    }
}