package analyzer.analysis;

import static com.google.common.base.Preconditions.checkState;

import java.io.IOException;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import analyzer.FileSupplier;
import analyzer.LoggingFileManager;

import com.google.inject.Inject;
import com.sun.source.util.JavacTask;
import com.sun.source.util.Trees;

public class VisitorDriver {

    private List<String> files;

    @Inject
    VisitorDriver(FileSupplier files) {
        this.files = files.getFiles();
    }


    public <R> R analysis(AbstractFoldingVisitor<R> visitor) 
        throws IOException 
    {
        JavacTask task = getTaskForFiles(files);
        visitor.scan(task.parse(), Trees.instance(task));
        return visitor.result();
    }

    private JavacTask getTaskForFiles(List<String> files)
    {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        LoggingFileManager fm = new LoggingFileManager(fileManager);
        Iterable<? extends JavaFileObject> compilationUnits = fm.getJavaFileObjectsFromStrings(files);
        CompilationTask possibleTask = compiler.getTask(null, fm, null, null, null, compilationUnits);

        checkState(possibleTask instanceof JavacTask, "Run sun-javac");

        JavacTask task = (JavacTask) possibleTask;
        return task;
    }
    
}
