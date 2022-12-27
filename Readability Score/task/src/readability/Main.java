package readability;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Analyzer analyzer = new Analyzer();
        analyzer.analyse(args[0]);

        System.out.printf("Words: %d\n", analyzer.getWordsCount());
        System.out.printf("Sentences: %d\n", analyzer.getSentencesCount());
        System.out.printf("Characters: %d\n", analyzer.getCharsCount());
        System.out.printf("Syllables: %d\n", analyzer.getSyllabeCount());
        System.out.printf("Polysyllables: %d\n", analyzer.getPolysyllabeCount());

        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");

        String type = scanner.nextLine();

        System.out.println();

        switch (type) {
            case "ari":
                printReadabilityScore("Automated Readability Index", analyzer.getAriScore());
                break;
            case "fk":
                printReadabilityScore("Flesch–Kincaid readability tests", analyzer.getFsScore());
                break;
            case "smog":
                printReadabilityScore("Simple Measure of Gobbledygook", analyzer.getSmogScore());
                break;
            case "cl":
                printReadabilityScore("Coleman–Liau index", analyzer.getClScore());
                break;
            case "all":
                printReadabilityScore("Automated Readability Index", analyzer.getAriScore());
                printReadabilityScore("Flesch–Kincaid readability tests", analyzer.getFsScore());
                printReadabilityScore("Simple Measure of Gobbledygook", analyzer.getSmogScore());
                printReadabilityScore("Coleman–Liau index", analyzer.getClScore());
                break;
        }
    }

    private static void printReadabilityScore(String text, double score) {
        System.out.printf("%s: %.2f (about %d-year-olds).\n", text, score, ARI.getByScore(score).getMaxAge());
    }
}


enum ARI {
    KINDERGARTEN("Kindergarten", 1, 5, 6),
    FIRST_GRADE("First Garde", 2, 6, 7),
    SECOND_GRADE("Second Garde", 3, 7, 8),
    THIRD_GRADE("Third Garde", 4, 8, 9),
    FOURTH_GRADE("Fourth Garde", 5, 9, 10),
    FIFTH_GRADE("Fifth Garde", 6, 10, 11),
    SIXTH_GRADE("Sixth Garde", 7, 11, 12),
    SEVENTH_GRADE("Seventh Garde", 8, 12, 13),
    EIGHTH_GRADE("Eighth Garde", 9, 13, 14),
    NINTH_GRADE("Ninth Garde", 10, 14, 15),
    TENTH_GRADE("Tenth Garde", 11, 15, 16),
    ELEVENTH_GRADE("Eleventh Garde", 12, 16, 17),
    TWELFTH_GRADE("Twelfth Garde", 13, 17, 18),
    COLLEGE_STUDENT("College student", 14, 18, 22);

    private String name;
    private int score;
    private int minAge;
    private int maxAge;

    ARI(String name, int score, int minAge, int maxAge) {
        this.name = name;
        this.score = score;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public String getName() {
        return name;
    }

    public double getScore() {
        return score;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public static ARI getByScore(double score) {
        for (ARI ari : values()) {
            if (ari.score == (int) Math.ceil(score)) {
                return ari;
            }
        }
        return COLLEGE_STUDENT;
    }
}
