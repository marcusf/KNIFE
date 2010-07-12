package analyzer;

import static com.google.common.base.Joiner.on;

import java.util.List;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.google.common.collect.Ordering;

public class CodeAnalyzer {

    static final String OPT_EXCLUDE       = "x";
    static final String OPT_REFCOUNT      = "r";
    static final String OPT_REFCOUNT_SHOW = "n";
    static final String OPT_IMPORTED_BY   = "i";


    public static void main(String[] argv) throws Exception {
   
        CommandLine opts = getOptions(argv);         
        List<String> files = FileSupplier.getFilesToCompile(opts.getArgs(), 
                                opts.getOptionValue(OPT_EXCLUDE));
        UsageMap depMap = DataSupplier.getUsages(files);
        
        // Walk the documents
        if (opts.hasOption(OPT_REFCOUNT)) {
            
            new RefCountAnalysis(opts, depMap, System.out).execute();
                     
        } else if (opts.hasOption(OPT_IMPORTED_BY)) {
            String className = opts.getOptionValue(OPT_IMPORTED_BY);
            Set<String> usagesByClass = depMap.getUsagesByClass(className);
            System.out.println(Common.orderedJoin("\n", usagesByClass));      
        } else {
            List<String> sortedResult = Ordering.natural().sortedCopy(depMap.getUsedGlobal());
            System.out.println(on('\n').skipNulls().join(sortedResult));
        }
        
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




}
