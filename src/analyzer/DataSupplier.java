package analyzer;

import static com.google.common.base.Preconditions.checkState;

import java.io.IOException;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

import com.sun.source.util.JavacTask;
import com.sun.source.util.Trees;

public class DataSupplier {

    public static KlazzIndex getKlazzIndex(List<String> files) 
        throws IOException 
    {
        KlazzIndexVisitor kip = new KlazzIndexVisitor(new KlazzIndex(files.size()));
        return analysis(kip, files);
    }

    public static UsageMap getUsages(List<String> files) 
        throws IOException 
    {
        UsageMapVisitor dm = new UsageMapVisitor(getKlazzIndex(files), new UsageMap());
        return analysis(dm, files);
    }

    private static <R> R analysis(AbstractFoldingVisitor<R> visitor, List<String> files) 
        throws IOException 
    {
        JavacTask task = getTaskForFiles(files);
        visitor.scan(task.parse(), Trees.instance(task));
        return visitor.result();
    }

    private static JavacTask getTaskForFiles(List<String> files)
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
