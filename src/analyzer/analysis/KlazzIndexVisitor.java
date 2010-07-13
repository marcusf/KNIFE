package analyzer.analysis;


import analyzer.Common;
import analyzer.analysis.KlazzIndex.New;

import com.google.inject.Inject;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.Trees;

class KlazzIndexVisitor extends AbstractFoldingVisitor<KlazzIndex> {

    private String packageName;

    @Inject
    public KlazzIndexVisitor(@New KlazzIndex index) {
        super(index);
    }

    public Object visitCompilationUnit(CompilationUnitTree node, Trees p)
    {
        packageName = Common.getPackageName(node.getPackageName());
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
