package analyzer.analysis;

import static com.google.common.base.Preconditions.checkState;

import java.io.IOException;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import analyzer.FileSupplier;

import com.google.inject.Inject;
import com.sun.source.util.JavacTask;
import com.sun.source.util.Trees;

public class VisitorDriver {

    private final StandardJavaFileManager fileManager;
    private final FileSupplier fileSupplier;

    @Inject
    VisitorDriver(FileSupplier fileSupplier, StandardJavaFileManager fileManager) {
        this.fileSupplier = fileSupplier;
        this.fileManager  = fileManager;
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
        CompilationTask possibleTask = compiler.getTask(null, fileManager, null, null, null, fileSupplier.getFiles());

        checkState(possibleTask instanceof JavacTask, "Run sun-javac");

        JavacTask task = (JavacTask) possibleTask;
        return task;
    }
    
}
