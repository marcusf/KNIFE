package knife.analysis;

import java.util.List;

import knife.analysis.UsageMap.New;


import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.ImportTree;
import com.sun.source.util.Trees;

/**
 * Visitor used to construct the {@link UsageMap} by walking the AST
 * and for each class in every compilation unit sifting through all
 * identifiers and mapping them against the {@link ClassIndex} to
 * see which are valid classes.
 */
public class UsageMapVisitor extends AbstractFoldingVisitor<UsageMap> {

    private CompilationUnitTree currentUnit;
    private String packageName;
    
    private List<String> classUsages;
    
    private final ClassIndex index;
    private String className;
        
    private boolean visitedClassAlready = false;
    
    @Inject
    public UsageMapVisitor(ClassIndex index, @New UsageMap usageMap) {
        super(usageMap);
        this.index = index;
    }

    public Object visitCompilationUnit(CompilationUnitTree node, Trees p)
    {
        visitedClassAlready = false;
        className           = null;
        currentUnit         = node;
        classUsages         = Lists.newArrayList();

        packageName         = currentUnit.getPackageName() != null
                            ? currentUnit.getPackageName().toString()
                            : ""
                            ;
                
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
                ClassName k = new ClassName(packageName, possibleClass);
                result().addFilesUsedByClass(className, k.toString());
            }
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
