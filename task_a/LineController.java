public class LineController {
    private final Line line;
    private final int lastLeftIndex;
    private volatile boolean lastLineStateIsStatic;

    public LineController(int lineLength) {
        line = new Line(lineLength);
        if (lineLength % 2 == 0) {
            lastLeftIndex = lineLength/2 - 1;
        }
        else {
            lastLeftIndex = (lineLength-1) / 2;
        }
        checkLineState();
    }

    public Line lineRef() {
        return line;
    }

    public int lastLeftIndex() {
        return lastLeftIndex;
    }

    public int firstRightIndex() {
        return lastLeftIndex + 1;
    }

    public int lineLength() {
        return line.length();
    }

    public boolean isStaticLineState() {
        return lastLineStateIsStatic;
    }

    public void checkLineState() {
        System.out.println(line);
        for (int i=0; i<line.length()-1; i++) {
            if (line.areFacingEachOther(i, i+1)) {
                lastLineStateIsStatic = false;
                return;
            }
        }
        lastLineStateIsStatic = true;
    }
}
