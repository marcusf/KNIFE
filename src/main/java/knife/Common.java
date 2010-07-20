package knife;

import static com.google.common.base.Joiner.on;

import com.google.common.collect.Ordering;

/**
 * Stuff that should be elsewhere or todos that I haven't
 * bothered moving yet.
 */
public class Common {
    
    public static final String OPT_EXCLUDE      = "x";
    public static final String OPT_REFCOUNT     = "r";
    public static final String OPT_COUNT        = "n";
    public static final String OPT_IMPORTED_BY  = "i";
   
    /**
     * Returns an ordered join of the input, according to the 
     * natural ordering of the comparable. Used to sort output
     * according to whatever.
     */
    public static <T> String orderedJoin(String sep, Iterable<? extends Comparable<T>> col) {
        return on(sep).join(Ordering.natural().sortedCopy(col));
    }
}
