package knife;

import static com.google.common.base.Joiner.on;

import com.google.common.collect.Ordering;

public class Common {

    public static final String OPT_EXCLUDE      = "x";
    public static final String OPT_REFCOUNT     = "r";
    public static final String OPT_COUNT        = "n";
    public static final String OPT_IMPORTED_BY  = "i";
   
    public static <T> String orderedJoin(String sep, Iterable<? extends Comparable<T>> col) {
        return on(sep).join(Ordering.natural().sortedCopy(col));
    }
}
