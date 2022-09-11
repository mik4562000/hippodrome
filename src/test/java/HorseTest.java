import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class HorseTest {
    private static Horse horse;

    @Test
    void horseIfNameIsNullShouldThrowIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Horse(null, 15));
    }

    @Test
    void horseIfNameIsNullShouldMessageBeAboutName() {
        Throwable actualException = Assertions.assertThrows(IllegalArgumentException.class, () -> new Horse(null, 15));

        Assertions.assertEquals("Name cannot be null.", actualException.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t"})
    void horseIfNameIsEmptyStringShouldThrowIllegalArgumentException(String argument) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Horse(argument, 15));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t"})
    void horseIfNameIsEmptyStringShouldMessageBeAboutBlankName(String argument) {
        Throwable actualException = Assertions.assertThrows(IllegalArgumentException.class, () -> new Horse(argument, 15));

        Assertions.assertEquals("Name cannot be blank.", actualException.getMessage());
    }

    @Test
    void horseIfSpeedIsNegativeShouldThrowIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Horse("testName", -10));
    }

    @Test
    void horseIfSpeedIsNegativeShouldMessageBeAboutNegativeSpeed() {
        Throwable actualException = Assertions.assertThrows(IllegalArgumentException.class, () -> new Horse("testName", -10));

        Assertions.assertEquals("Speed cannot be negative.", actualException.getMessage());
    }

    @Test
    void horseIfDistanceIsNegativeShouldThrowIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Horse("testName", 15, -100));
    }

    @Test
    void horseIfDistanceIsNegativeShouldMessageBeAboutNegativeDistance() {
        Throwable actualException = Assertions.assertThrows(IllegalArgumentException.class, () -> new Horse("testName", 15, -100));

        Assertions.assertEquals("Distance cannot be negative.", actualException.getMessage());
    }

    @BeforeEach
    void createHorseWithThreeParameters() {
        horse = new Horse("TestName", 25, 150);
    }

    @Test
    void getName() {
        Assertions.assertEquals("TestName", horse.getName());
    }

    @Test
    void getSpeed() {
        Assertions.assertEquals(25, horse.getSpeed());
    }

    @Test
    void getDistance() {
        Assertions.assertEquals(150, horse.getDistance());
    }

    @Test
    void getDistanceWhenCreatingHorseWithTwoParameters() {
        Horse horse1 = new Horse("TestName", 15);
        Assertions.assertEquals(0, horse1.getDistance());
    }

    @Test
    void moveCheckGetRandomDoubleIsExecuted() {
        try (MockedStatic<Horse> staticHorse = Mockito.mockStatic(Horse.class)) {
            horse.move();
            staticHorse.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {1, 2, 3, 4, 5})
    void moveCheckGetDistanceByFormula(double argument) {
        try (MockedStatic<Horse> staticHorse = Mockito.mockStatic(Horse.class)) {
            staticHorse.when(() -> Horse.getRandomDouble(anyDouble(), anyDouble())).thenReturn(argument);
            double distance = horse.getDistance();
            horse.move();
            Assertions.assertEquals(distance + horse.getSpeed() * argument, horse.getDistance());
        }

    }
}
