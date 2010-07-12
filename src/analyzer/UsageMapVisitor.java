package analyzer;

import java.util.List;


import com.google.common.collect.Lists;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.ImportTree;
import com.sun.source.util.Trees;

class UsageMapVisitor extends AbstractFoldingVisitor<UsageMap> {

    private CompilationUnitTree currentUnit;
    private String packageName;
    
    private List<String> classUsages;
    
    private final KlazzIndex index;
    private String className;
    
    private boolean visitedClassAlready = false;
    
    public UsageMapVisitor(KlazzIndex index, UsageMap dependencyMap) {
        super(dependencyMap);
        this.index = index;
    }

    public Object visitCompilationUnit(CompilationUnitTree node, Trees p)
    {
        visitedClassAlready = false;
        className           = null;
        currentUnit         = node;
        packageName         = CompilerUtil.getPackageName(currentUnit.getPackageName());
        classUsages         = Lists.newArrayList();
        return super.visitCompilationUnit(node, p);
    }   
    
    @Override
    public Object visitImport(ImportTree node, Trees p)
    {
        String theImport = node.getQualifiedIdentifier().toString();
        classUsages.add(theImport);
        return super.visitImport(node, p);
    }
    
    @Override
    public Object visitIdentifier(IdentifierTree node, Trees p)
    {
        if (visitedClassAlready) {
           
            String possibleClass = node.getName().toString();
            
            if (index.contains(packageName, possibleClass)) {
                Klazz k = new Klazz(packageName, possibleClass);
                result().addFilesUsedByClass(className, k.toString());
            }
            
            //
            // This code is not really necessary, cause all uses of classes not in this package
            // should be caught by imports.
            //
            // Still require a lot of work.
            //
            
            /*else if (index.contains(possibleClass)) {
                Set<Klazz> resolved = index.getResolvedClassesByName(possibleClass);
                for (Klazz item: resolved) {
                    if (imports.contains(item.toString())) {
                        addUsagesByClass(item.toString());
                        break;
                    }
                }
            }*/

        }
        
        return super.visitIdentifier(node, p);
    }

    @Override
    public Object visitClass(ClassTree node, Trees p)
    {
        visitedClassAlready = true;
        
        if (!node.getSimpleName().toString().equals("")) {
            className = packageName + "." + node.getSimpleName().toString();
            result().addFilesUsedByClass(className, classUsages);
            
            // TODO: Need to get extends and implements as well -- possibly? 
            // Might be that identifier catches this as well. Test test test.
            
        }
        
        return super.visitClass(node, p);
    }
    
}
