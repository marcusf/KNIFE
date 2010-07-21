package knife.analysis;

import static com.google.common.base.Preconditions.checkState;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import knife.JavacFileSupplier;
import knife.Output;

import com.google.inject.Inject;
import com.sun.source.util.JavacTask;
import com.sun.source.util.Trees;

/**
 * Bootstraps a visitor by getting the files required and 
 * starting up the analysis.
 */
public class VisitorDriver {

    private final StandardJavaFileManager fileManager;
    private final JavacFileSupplier fileSupplier;
    private final PrintStream err;

    @Inject
    VisitorDriver(JavacFileSupplier fileSupplier, 
                  StandardJavaFileManager fileManager,
                  @Output.Err PrintStream err) 
    {
        this.fileSupplier = fileSupplier;
        this.fileManager  = fileManager;
        this.err = err;
    }


    public <R> R analysis(AbstractFoldingVisitor<R> visitor) 
        throws IOException 
    {
        JavacTask task = getTaskForFiles();
        visitor.scan(task.parse(), Trees.instance(task));
        return visitor.result();
    }

    private JavacTask getTaskForFiles()
    {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        CompilationTask possibleTask = compiler.getTask(new PrintWriter(err), fileManager, null, null, null, fileSupplier.getFiles());
        checkState(possibleTask instanceof JavacTask, "Run sun-javac");

        JavacTask task = (JavacTask) possibleTask;
        return task;
    }
    
}
