import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class HippodromeTest {

    private static List<Horse> horses;

    @BeforeEach
    void createHorseWithThreeParameters() {
        horses = new ArrayList<>();
    }

    @Test
    void HippodromeIfListIsNullShouldThrowIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    void HippodromeIfListIsNullShouldMessageBeAboutHorses() {
        Throwable actualException = Assertions.assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));

        Assertions.assertEquals(actualException.getMessage(), "Horses cannot be null.");
    }

    @Test
    void HippodromeIfListIsEmptyShouldThrowIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Hippodrome(horses));
    }

    @Test
    void HippodromeIfListIsEmptyShouldMessageBeAboutHorses() {
        Throwable actualException = Assertions.assertThrows(IllegalArgumentException.class, () -> new Hippodrome(horses));
        Assertions.assertEquals(actualException.getMessage(), "Horses cannot be empty.");
    }

    @Test
    void getHorses() {
        String s = "Oracle Java is the #1 programming language and development platform. It reduces costs, shortens development timeframes, drives innovation, and improves application services. With millions of developers running more than 51 billion Java Virtual Machines worldwide, Java continues to be the development platform of choice for enterprises and developers.";
        List<String> names = new ArrayList<>(Arrays.asList(s.split(" ")));
        List<Horse> horsesWithNames = names.stream().map(name -> new Horse(name, 10)).collect(Collectors.toList());
        Hippodrome hippodrome = new Hippodrome(horsesWithNames);
        Assertions.assertArrayEquals(horsesWithNames.toArray(), hippodrome.getHorses().toArray());
    }

    @Test
    void move() {
        List<Horse> mockHorses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            mockHorses.add(Mockito.mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(mockHorses);
        hippodrome.move();

        for (Horse horse : mockHorses) {
            Mockito.verify(horse, Mockito.times(1)).move();
        }
    }

    @Test
    void getWinner() {
        List<Horse> horsesForWin = new ArrayList<>();
        Horse horse1 = new Horse("N1", 10, 150);
        horsesForWin.add(horse1);
        Horse horse2 = new Horse("N2", 10, 250);
        horsesForWin.add(horse2);
        Horse horse3 = new Horse("N3", 10, 160);
        horsesForWin.add(horse3);
        Hippodrome hippodrome = new Hippodrome(horsesForWin);
        Assertions.assertEquals(horse2, hippodrome.getWinner());
    }
}