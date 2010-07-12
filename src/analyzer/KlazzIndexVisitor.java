package analyzer;


import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.Trees;

class KlazzIndexVisitor extends AbstractFoldingVisitor<KlazzIndex> {

    private String packageName;

    public KlazzIndexVisitor(KlazzIndex index) {
        super(index);
    }

    public Object visitCompilationUnit(CompilationUnitTree node, Trees p)
    {
        packageName = CompilerUtil.getPackageName(node.getPackageName());
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
