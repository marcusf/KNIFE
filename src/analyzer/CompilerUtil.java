package analyzer;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MemberSelectTree;

public class CompilerUtil {

    public static String getPackageName(ExpressionTree packageName)
    {
        if (packageName instanceof MemberSelectTree) {
            return packageName.toString();
        } else {
            return "";
        }
    }
}
