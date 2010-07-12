package analyzer;

import static com.google.common.base.Joiner.on;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.base.Predicates.containsPattern;
import static com.google.common.base.Predicates.not;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Lists.newArrayList;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.sun.source.util.JavacTask;
import com.sun.source.util.Trees;

public class CodeAnalyzer {

    static final String OPT_EXCLUDE       = "x";
    static final String OPT_REFCOUNT      = "r";
    static final String OPT_REFCOUNT_SHOW = "n";
    static final String OPT_IMPORTED_BY   = "i";


    public static void main(String[] argv) throws Exception {
        
                
        CommandLine opts = getOptions(argv); 
        boolean hasExcludePattern = opts.hasOption(OPT_EXCLUDE);
        
        List<String> files = getFilesToCompile(opts.getArgs(), hasExcludePattern, opts.getOptionValue(OPT_EXCLUDE));
               
        UsageMap depMapper = getUsages(files);
       
        
        // Walk the documents
        if (opts.hasOption(OPT_REFCOUNT)) {
            
            analyzeRefCount(opts, depMapper);
                     
        } else if (opts.hasOption(OPT_IMPORTED_BY)) {
            String className = opts.getOptionValue(OPT_IMPORTED_BY);
            Set<String> usagesByClass = depMapper.getUsagesByClass(className);
            System.out.println(orderedJoin("\n", usagesByClass));      
        } else {
            List<String> sortedResult = Ordering.natural().sortedCopy(depMapper.getUsedGlobal());
            System.out.println(on('\n').skipNulls().join(sortedResult));
        }
        
    }

    private static void analyzeRefCount(CommandLine opts,
            UsageMap depMap)
    {
        RefCountAnalysis refCountAnalysis = new RefCountAnalysis(opts, depMap);
        refCountAnalysis.execute();
    }

    private static KlazzIndex getKlazzIndex(List<String> files) 
        throws IOException 
    {
        KlazzIndexVisitor kip = new KlazzIndexVisitor(new KlazzIndex(files.size()));
        return analysis(kip, files);
    }
    
    private static UsageMap getUsages(List<String> files) 
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

    private static CommandLine getOptions(String[] argv) throws ParseException
    {
        Options opts = new Options();
        
        opts.addOption(OPT_EXCLUDE, "exclude-packet", true, 
                "Exclude any files in the packet (and sub packets) given");
                
        opts.addOption(OPT_REFCOUNT, "show-referenced-count", false, 
                "Show how many classes reference the import.");
              
        opts.addOption(OPT_REFCOUNT_SHOW, "show-referenced-classes-for", true, 
                "Display class names for all classes having less than N references");

        opts.addOption(OPT_IMPORTED_BY, "who-imports", true, 
                "Shows which classes import this class");

        
        CommandLineParser cp = new GnuParser();
        CommandLine cl = cp.parse(opts, argv);
        return cl;
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

    private static List<String> getFilesToCompile(String[] argv, 
                        boolean hasExcludePattern, 
                        String excludePattern)
    {
        List<String> unfiltered = Lists.transform(newArrayList(argv), pathNormalizer());

        if (hasExcludePattern) {
            // Very windows hostile
            String excludePackage = excludePattern.replace('.', '/');
            return newArrayList(filter(unfiltered, not(containsPattern(excludePackage))));                
        }
        return unfiltered;      
    }
    
    private static <T> String orderedJoin(String sep, Iterable<? extends Comparable<T>> col) {
        return on(sep).join(Ordering.natural().sortedCopy(col));
    }

    static Function<String, String> pathNormalizer() {
       return new NormalizePathNames();
    }
      
    static class NormalizePathNames implements Function<String, String>
    {
        public String apply(String input)
        {
            return on('/').skipNulls().join(Splitter.onPattern("/").omitEmptyStrings().split(input));
        }
    }
}
