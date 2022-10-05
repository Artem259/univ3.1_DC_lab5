import java.util.Random;

public class RandString {
    private final StringBuilder string;
    private final Random rand;
    private static final StringBuilder chars = new StringBuilder("ACBD");

    public RandString(int length) {
        rand = new Random();
        string = new StringBuilder();
        for(int i=0; i<length; i++) {
            string.append(chars.charAt(rand.nextInt(4)));
        }
    }

    public void replaceChar() {
        int toChange = rand.nextInt(string.length());
        string.setCharAt(toChange, getOppositeTo(string.charAt(toChange)));
    }

    public long countAB() {
        return string.chars().filter(ch -> ch == 'A' || ch == 'B').count();
    }

    private char getOppositeTo(char c) {
        return switch (c) {
            case 'A' -> 'C';
            case 'C' -> 'A';
            case 'B' -> 'D';
            case 'D' -> 'B';
            default -> throw new IllegalStateException();
        };
    }

    @Override
    public String toString() {
        return string.toString();
    }
}
