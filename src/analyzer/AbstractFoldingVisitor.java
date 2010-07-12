package analyzer;

import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;

abstract class AbstractFoldingVisitor<R> extends TreePathScanner<Object, Trees> {

    private final R result;

    protected AbstractFoldingVisitor(R result) {
        this.result = result;
        
    }
    
    public R result() {
        return result;
    }
    
}
