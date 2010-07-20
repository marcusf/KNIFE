package knife.analysis;

import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;

/**
 * Base class for parse tree visitors that produce some 
 * kind of result.
 * 
 * The name comes from the fact that it very much acts 
 * like a fold over a tree, eg something that reduces 
 * the input to another form, such as a map or a list 
 * or something like that.
 */
abstract class AbstractFoldingVisitor<R> extends TreePathScanner<Object, Trees> {

    private final R result;

    protected AbstractFoldingVisitor(R result) {
        this.result = result;
        
    }
    
    public R result() {
        return result;
    }
    
}
