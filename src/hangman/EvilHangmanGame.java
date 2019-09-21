package hangman;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class EvilHangmanGame implements IEvilHangmanGame {
    TreeMap<String, String> words = new TreeMap<>();
    SortedSet<Character> guessedLetters = new TreeSet<>();

    public EvilHangmanGame() { }

    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
        // Clear out previously used variable of any values
        words.clear();
        guessedLetters.clear();
        // Default pattern
        String defaultPattern = new String(new char[wordLength]).replace("\0", "-");
        // If the dictionary file is not empty
        if(dictionary.length() > 0) {
            // Scan the file
            try(Scanner sc = new Scanner(new FileReader(dictionary))) {
                // Only store the words of the user given size
                while(sc.hasNext()) {
                    String curWord = sc.next();
                    if(curWord.length() == wordLength) words.put(curWord, defaultPattern);
                }
                // If no words are found in the dictionary, throw this exception
                if(words.size() == 0) throw new EmptyDictionaryException("Word Not Found In Dictionary");
            }
        }
        // If the dictionary is really empty, throw this exception
        else throw new EmptyDictionaryException("There are no words in your dictionary");

        // OUTPUT FOR DEBUGGING
//        for(Map.Entry<String, String> entry: words.entrySet()) System.out.printf("Word: %s  Pattern: %s\n", entry.getKey(), entry.getValue());
    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        // If the user has made guesses before
        if(!guessedLetters.isEmpty()) {
            // If the uses guesses a previously guessed letter, throw this exception
            if(guessedLetters.contains(guess)) throw new GuessAlreadyMadeException("You have already guessed this character");
        }
        // Add the letter to the set of guessed letters
        guessedLetters.add(guess);

        TreeSet<String> s = new TreeSet<>();
        s.add("Hello World");
        return s;
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return this.guessedLetters;
    }
}
