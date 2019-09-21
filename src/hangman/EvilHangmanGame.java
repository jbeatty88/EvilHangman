package hangman;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class EvilHangmanGame implements IEvilHangmanGame {
    TreeSet<String> dictionaryWords = new TreeSet<>();
    SortedSet<Character> guessedLetters = new TreeSet<>();
    String finalWord, patternShow;

    public EvilHangmanGame() { }

    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
        // Clear out previously used variable of any values
        guessedLetters.clear();
        dictionaryWords.clear();

        // Default pattern
        String defaultPattern = new String(new char[wordLength]).replace("\0", "-");
        // If the dictionary file is not empty
        if(dictionary.length() > 0) {
            // Scan the file
            try(Scanner sc = new Scanner(new FileReader(dictionary))) {
                // Only store the words of the user given size
                while(sc.hasNext()) {
                    String curWord = sc.next();
                    if(curWord.length() == wordLength) dictionaryWords.add(curWord);
                }
                // If no words are found in the dictionary, throw this exception
                if(dictionaryWords.size() == 0) throw new EmptyDictionaryException("Word Not Found In Dictionary");
            }
        }
        // If the dictionary is really empty, throw this exception
        else throw new EmptyDictionaryException("There are no words in your dictionary");

        // OUTPUT FOR DEBUGGING
//        for(Map.Entry<String, String> entry: words.entrySet()) System.out.printf("Word: %s  Pattern: %s\n", entry.getKey(), entry.getValue());
    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        TreeMap<String, TreeSet<String>> wordPartitions = new TreeMap<>();
        char guessLC = Character.toLowerCase(guess);
        // If the user has made guesses before
        if(!guessedLetters.isEmpty()) {
            // If the uses guesses a previously guessed letter, throw this exception
            if(guessedLetters.contains(guessLC)) throw new GuessAlreadyMadeException("You have already guessed this character");
        }
        // Add the letter to the set of guessed letters
        guessedLetters.add(guessLC);

        // Make a pattern for every word and put it in the map with the relative words
        for(String s : dictionaryWords) {
            // Save the word as a set to be added as/or to the set in the map
            TreeSet<String> w = new TreeSet<>();
            w.add(s);
            // Get the pattern
            String pattern = makePattern(s, guessLC);
            // If that pattern isn't already in the map, put it in the map
            if(!wordPartitions.containsKey(pattern)) wordPartitions.put(pattern, w);
            // Otherwise, add the words the the relevant set
            else wordPartitions.get(pattern).addAll(w);
        }

        // We're looking for the largest set here
        Map.Entry<String, TreeSet<String>> maxSet = null;
        // Go through each of the set of words in the map and check the size
        for(Map.Entry<String, TreeSet<String>> entry: wordPartitions.entrySet()) {
//            System.out.printf("Pattern: %s  Words: %s\n", entry.getKey(), entry.getValue());
            // Assign the largest set entry to the maxSet
            if(maxSet == null || entry.getValue().size() > maxSet.getValue().size()) maxSet = entry;
            // If there are two partitions that are the same size
            if(entry.getValue().size() == maxSet.getValue().size()) {
                // If one of the partitions doesn't have the character, choose it
                if(!entry.getKey().contains(Character.toString(guessLC))) maxSet = entry;
                // If every pattern contains that character
                else if(entry.getKey().contains(Character.toString(guessLC)) && maxSet.getKey().contains(Character.toString(guessLC))){
                    // Find the one with the least occurrences
                    long countA = entry.getKey().chars().filter(ch -> ch == guessLC).count();
                    long countB = maxSet.getKey().chars().filter(ch -> ch == guessLC).count();
                    if(countA < countB) maxSet = entry;
                    // If they have the same amount of occurrences
                    else if(countA == countB) {
                        // Choose the one with the rightmost guessed letter
                        if(entry.getKey().lastIndexOf(guessLC) > maxSet.getKey().lastIndexOf(guessLC)) maxSet = entry;
                    }
                }
            }
        };

        // Using the largest entry from our map
        assert maxSet != null;
        // Add the words to be our new dictionary to pick from
        dictionaryWords = maxSet.getValue();
        patternShow = maxSet.getKey();

//        for(String s : dictionaryWords) System.out.println(s);

        // Return our new list of words to play with
        return dictionaryWords;
    }

    public String makePattern(String s, char guess) {
        char[] stringCharArray = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(char c : stringCharArray) {
            if(c != guess) c = '-';
            sb.append(c);
        }
        return sb.toString();
    }

    @Override
    public SortedSet<Character> getGuessedLetters() { return this.guessedLetters; }
}
