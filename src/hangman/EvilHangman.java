package hangman;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;

public class EvilHangman {
    public static void main(String[] args) {
        File wordBase = new File(args[0]);
        String usageMsgGuess = "Word length must be >= 2", usageMsgWordLen = "Number of guesses must be >= 1.";
        int wordLen = Integer.parseInt(args[1]), numGuess = Integer.parseInt(args[2]);

        // Validate user input for wordLength and numGuesses
        if(wordLen < 2) System.out.println(usageMsgWordLen);
        if(numGuess < 1) System.out.println(usageMsgGuess);
        // Create an instance of the game (unstarted)
        EvilHangmanGame evilGame = new EvilHangmanGame();
        // Attempt to start the game with the dictionary given by the user and the wordLen
        try { evilGame.startGame(wordBase, wordLen); }
        catch (IOException e) { e.printStackTrace(); }
        catch (EmptyDictionaryException e) { System.out.println(e.getMessage()); }

        // Game loop: while the user still has guesses left, allow them to guess
        while(numGuess > 0 ) {
            // Prompt the user for input and show game stats
            prompt(wordLen, numGuess, evilGame);
            // Try to make a guess
            try {
                // Ask user for input and store it
                char userGuess = getUserInput();
                // Guess that word
                evilGame.makeGuess(userGuess);
                // Decrement the number of guesses
                numGuess--;
                // If there are still guesses left
                if(numGuess != 0) {
                    // If the current pattern does not have the user guessed character, output the rejection msg
                    if(!evilGame.patternShow.contains(Character.toString(userGuess))) System.out.printf("Sorry, there are no %c\'s\n\n", userGuess);
                    // If the current pattern does include the user guessed character
                    else {
                        // Keep track of how many characters are in the pattern
                        int charCount = 0;
                        String areOrIs =  "are";
                        // Go through every character in the pattern and increment char count every time we find the user char
                        for(char c : evilGame.patternShow.toCharArray()) if( c == userGuess) charCount++;
                        // If there is only a singular character, use "is"
                        if(charCount < 2) areOrIs = "is";
                        System.out.printf("Yes, there %s %d %c\'s\n\n", areOrIs, charCount, userGuess);
                    }
                }
            } catch (GuessAlreadyMadeException e) { System.out.println(e.getMessage()); }
        }
        System.out.println("You lose!!");
        System.out.printf("The word was: %s\n", evilGame.dictionaryWords.first());
    }

    public static char getUserInput() {
        char userInput = ' ';
        while (!Character.isAlphabetic(userInput)) {
            System.out.print("Enter guess: ");
            userInput = new Scanner(System.in).next().charAt(0);
            if(!Character.isAlphabetic(userInput)) System.out.println("Invalid input");
        }
        return Character.toLowerCase(userInput);
    }

    public static void prompt(int wordLen, int numGuess, EvilHangmanGame eV) {
        System.out.printf("You have %d guesses left\n", numGuess);
        System.out.print("Used letters: ");
        if(!eV.guessedLetters.isEmpty()) {
            for(Character c : eV.guessedLetters) System.out.printf("%c ", c);
            System.out.println();
        }
        System.out.printf("Word: %s\n", eV.patternShow);
    }

}
