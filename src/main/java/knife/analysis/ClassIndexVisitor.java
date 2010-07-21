package knife.analysis;


import knife.analysis.ClassIndex.New;

import com.google.inject.Inject;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.Trees;

/**
 * Visitor going over every class in every java file given and 
 * building a {@link ClassIndex} from it.
 */
class ClassIndexVisitor extends AbstractFoldingVisitor<ClassIndex> {

    private String packageName;

    @Inject
    public ClassIndexVisitor(@New ClassIndex index) {
        super(index);
    }

    public Object visitCompilationUnit(CompilationUnitTree node, Trees p)
    {
        packageName = node.getPackageName() != null 
                    ? node.getPackageName().toString()
                    : ""
                    ;
                    
        return super.visitCompilationUnit(node, p);
    }   

    @Override
    public Object visitClass(ClassTree node, Trees p)
    {
        if (!node.getSimpleName().toString().equals("")) {
            String className = node.getSimpleName().toString();
            result().add(packageName, className);
        }        
        return super.visitClass(node, p);
    }

}
