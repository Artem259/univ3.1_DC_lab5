import java.util.Arrays;

public class StringsController {
    private final int stringsAmount;
    private final int equalsRequired;
    private final RandString[] strings;
    private volatile boolean lastCheckResult;

    public StringsController(int stringsAmount, int equalsRequired, int stringsSize) {
        if(equalsRequired > stringsAmount)
            throw new RuntimeException();
        this.stringsAmount = stringsAmount;
        this.equalsRequired = equalsRequired;
        strings = new RandString[stringsAmount];
        for(int i=0; i<stringsAmount; i++) {
            strings[i] = new RandString(stringsSize);
        }
        checkCondition();
    }

    public RandString stringRef(int i) {
        return strings[i];
    }

    public boolean isLastCheckSuccessful() {
        return lastCheckResult;
    }

    public void checkCondition() {
        long[] counters = countAllAB();
        int max_count=1, count=1;
        Arrays.sort(counters);
        for(int i=1; i<counters.length; i++) {
            if(counters[i] == counters[i-1])
                count++;
            if(counters[i] != counters[i-1] || i==counters.length-1) {
                if(count > max_count) {
                    max_count = count;
                }
                count =1;
            }
        }
        lastCheckResult = (max_count >= equalsRequired);
    }

    private long[] countAllAB() {
        long[] res = new long[stringsAmount];
        for(int i=0; i<stringsAmount; i++) {
            res[i] = strings[i].countAB();
        }
        System.out.println(Arrays.toString(res));
        return res;
    }
}
