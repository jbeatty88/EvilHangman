package tests;

import hangman.EmptyDictionaryException;
import hangman.EvilHangmanGame;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EvilHangmanGameTest {
    @Test
    void makeGuess() {
        EvilHangmanGame game = new EvilHangmanGame();
        try {
            game.startGame(new File("small.txt"), 5);
        } catch (IOException e) {
            fail("Start game threw IOException");
        } catch (EmptyDictionaryException e) {
            fail("Dictionary that contains words is counted as empty");
        }
        Set<String> possibleWords = new HashSet<>();
        try {
            possibleWords = game.makeGuess('a');
        } catch (Throwable e) {
            fail("Making a guess threw: " + e.getClass());
        }
        assertEquals(4, possibleWords.size(), "Incorrect size after 1 guess");
        assertFalse(possibleWords.contains("lambs"), "Incorrect contents after 1 guess");
        assertFalse(possibleWords.contains("lakes"), "Incorrect contents after 1 guess");
        assertTrue(possibleWords.contains("toner"), "Incorrect contents after 1 guess");
    }

    @Test
    void makePattern() {
        EvilHangmanGame game = new EvilHangmanGame();
        assertEquals("--ll-", game.makePattern("Hello", 'l'));
        assertEquals("------a----a-", game.makePattern("Supercalifraj", 'a'));
        assertEquals("--s---", game.makePattern("Joshua", 's'));
    }

    @Test
    @DisplayName("Pattern With Rightmost Instances Test")
    void testRightmostLetter() {
        EvilHangmanGame studentGame = new EvilHangmanGame();

        try {
            studentGame.startGame(new File("small.txt"), 3);
        } catch (IOException e) {
            fail("Start game threw IOException");
        } catch (EmptyDictionaryException e) {
            fail("Dictionary that contains words is counted as empty");
        }
        Set<String> possibleWords = new HashSet<>();
        try {
            possibleWords = studentGame.makeGuess('a');
        } catch (Throwable e) {
            fail("Making a guess threw: " + e.getClass());
        }
        assertEquals(2, possibleWords.size(), "Incorrect word count after 1st guess.");
        assertFalse(possibleWords.contains("abs"), "Incorrect content after 1st guess.");
        assertFalse(possibleWords.contains("are"), "Incorrect content after 1st guess.");
        assertFalse(possibleWords.contains("bar"), "Incorrect content after 1st guess.");
        assertFalse(possibleWords.contains("tag"), "Incorrect content after 1st guess.");
        assertTrue(possibleWords.contains("bra"), "Incorrect content after 1st guess.");
        assertTrue(possibleWords.contains("moa"), "Incorrect content after 1st guess.");

        try {
            studentGame.startGame(new File("small.txt"), 12);
        } catch (IOException e) {
            fail("Start game threw IOException");
        } catch (EmptyDictionaryException e) {
            fail("Dictionary that contains words is counted as empty");
        }
        possibleWords = new HashSet<>();
        try {
            possibleWords = studentGame.makeGuess('h');
        } catch (Throwable e) {
            fail("Making a guess threw: " + e.getClass());
        }
        assertEquals(2, possibleWords.size(), "Incorrect word count after 1st guess.");
        assertFalse(possibleWords.contains("charmillions"), "Incorrect content after 1st guess.");
        assertFalse(possibleWords.contains("phylogenesis"), "Incorrect content after 1st guess.");
        assertTrue(possibleWords.contains("antimonarchy"), "Incorrect content after 1st guess.");
        assertTrue(possibleWords.contains("boxingweight"), "Incorrect content after 1st guess.");
    }

    @Test
    @DisplayName("Pattern With Fewest Instances Test")
    void testFewestInstances() {
        EvilHangmanGame studentGame = new EvilHangmanGame();

        try {
            studentGame.startGame(new File("small.txt"), 7);
        } catch (IOException e) {
            fail("Start game threw IOException");
        } catch (EmptyDictionaryException e) {
            fail("Dictionary that contains words is counted as empty");
        }
        Set<String> possibleWords = new HashSet<>();
        try {
            possibleWords = studentGame.makeGuess('z');
        } catch (Throwable e) {
            fail("Making a guess threw: " + e.getClass());
        }
        assertEquals(2, possibleWords.size(), "Incorrect size after 1 guess");
        assertFalse(possibleWords.contains("zyzzyva"), "Incorrect contents after 1 guess");
        assertFalse(possibleWords.contains("zizzled"), "Incorrect contents after 1 guess");
        assertTrue(possibleWords.contains("buzzwig"), "Incorrect contents after 1 guess");


        try {
            studentGame.startGame(new File("small.txt"), 8);
        } catch (IOException e) {
            fail("Start game threw IOException");
        } catch (EmptyDictionaryException e) {
            fail("Dictionary that contains words is counted as empty");
        }
        possibleWords = new HashSet<>();
        try {
            possibleWords = studentGame.makeGuess('e');
        } catch (Throwable e) {
            fail("Making a guess threw: " + e.getClass());
        }
        assertEquals(4, possibleWords.size(), "Incorrect size after 1 guess");
        assertFalse(possibleWords.contains("bythelee"), "Incorrect contents after 1 guess");
        assertFalse(possibleWords.contains("dronebee"), "Incorrect contents after 1 guess");
        assertFalse(possibleWords.contains("parmelee"), "Incorrect contents after 1 guess");
        assertFalse(possibleWords.contains("tuskegee"), "Incorrect contents after 1 guess");
        assertTrue(possibleWords.contains("gardened"), "Incorrect contents after 1 guess");
        assertTrue(possibleWords.contains("forgemen"), "Incorrect contents after 1 guess");
        assertTrue(possibleWords.contains("lingerer"), "Incorrect contents after 1 guess");
        assertTrue(possibleWords.contains("ohmmeter"), "Incorrect contents after 1 guess");
    }

}