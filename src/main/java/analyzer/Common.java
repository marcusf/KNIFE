package analyzer;

import static com.google.common.base.Joiner.on;

import com.google.common.collect.Ordering;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MemberSelectTree;

public class Common {

    public static String getPackageName(ExpressionTree packageName)
    {
        if (packageName instanceof MemberSelectTree) {
            return packageName.toString();
        } else {
            return "";
        }
    }
    
    
    public static <T> String orderedJoin(String sep, Iterable<? extends Comparable<T>> col) {
        return on(sep).join(Ordering.natural().sortedCopy(col));
    }
    
    public static void vomitOnNull(String name) {
        if (name == null || name.equals("")) {
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
