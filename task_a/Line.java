import java.util.Random;

public class Line {
    private final StringBuilder line;
    private static final StringBuilder chars = new StringBuilder("<>");

    public Line(int length) {
        Random rand = new Random();
        line = new StringBuilder();
        for (int i=0; i<length; i++) {
            line.append(chars.charAt(rand.nextInt(2)));
        }
    }

    public int length() {
        return line.length();
    }

    public boolean areFacingEachOther(int i1, int i2) {
        if (i1 == i2) {
            throw new IllegalArgumentException();
        }
        if (i1 > i2) {
            int temp = i1;
            i1 = i2;
            i2 = temp;
        }
        return (line.charAt(i1) == '>' && line.charAt(i2) == '<');
    }

    public void rotateAt(int i) {
        if (line.charAt(i) == '>') {
            line.setCharAt(i, '<');
        }
        else {
            line.setCharAt(i, '>');
        }
    }

    @Override
    public String toString() {
        return line.toString();
    }
}
