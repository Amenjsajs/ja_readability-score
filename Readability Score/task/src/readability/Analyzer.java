package readability;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Analyzer {
    private int wordsCount = 0;
    private int charsCount = 0;
    private int sentencesCount = 0;
    private int syllabeCount = 0;
    private int polysyllabeCount = 0;

    public void analyse(String filePath) {
        StringBuilder text = new StringBuilder();
        String[] sentences;
        String line;
        String[] words;

        File file = new File(filePath);

        int syb;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                line = scanner.nextLine();
                if (text.length() > 0) {
                    text.append("\n");
                }

                text.append(line);

                sentences = line.split("[.?!]");
                sentencesCount += sentences.length;

                for (String sentence : sentences) {
                    words = sentence.trim().split(" ");
                    wordsCount += words.length;

                    for (String word : words) {
                        syb = countSyllables(word);
                        syllabeCount += syb;
                        if (syb > 2) {
                            polysyllabeCount++;
                        }
                    }
                }

                charsCount += line.replaceAll("\\s", "").length();
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + filePath);
        }
    }

    protected int countSyllables(String word) {
        // TODO: Implement this method so that you can call it from the
        // getNumSyllables method in BasicDocument (module 1) and
        // EfficientDocument (module 2).
        int count = 0;
        word = word.toLowerCase();

        if (word.charAt(word.length() - 1) == 'e') {
            if (silente(word)) {
                String newword = word.substring(0, word.length() - 1);
                count = count + countit(newword);
            } else {
                count++;
            }
        } else {
            count = count + countit(word);
        }
        return count;
    }

    private int countit(String word) {
        int count = 0;
        Pattern splitter = Pattern.compile("[^aeiouy]*[aeiouy]+");
        Matcher m = splitter.matcher(word);

        while (m.find()) {
            count++;
        }
        return count;
    }

    private boolean silente(String word) {
        word = word.substring(0, word.length() - 1);

        Pattern yup = Pattern.compile("[aeiouy]");
        Matcher m = yup.matcher(word);

        if (m.find()) {
            return true;
        } else
            return false;
    }

    public int getWordsCount() {
        return wordsCount;
    }

    public int getCharsCount() {
        return charsCount;
    }

    public int getSentencesCount() {
        return sentencesCount;
    }

    public int getSyllabeCount() {
        return syllabeCount;
    }

    public int getPolysyllabeCount() {
        return polysyllabeCount;
    }

    public double getAriScore() {
        return 4.71 * ((double) charsCount / (double) wordsCount) + .5 * ((double) wordsCount / (double) sentencesCount) - 21.43;
    }

    public double getFsScore() {
        return 0.39 * ((double) wordsCount / (double) sentencesCount) + 11.8 * ((double) syllabeCount / (double) wordsCount) - 15.59;

    }

    public double getSmogScore() {
        return 1.043 * (Math.sqrt((double) polysyllabeCount * 30 / (double) sentencesCount)) + 3.1291;
    }

    public double getClScore() {
        return 0.0588 * ((double) charsCount / (double) wordsCount * 100) - 0.296 * ((double) sentencesCount / (double) wordsCount * 100) - 15.8;
    }
}
