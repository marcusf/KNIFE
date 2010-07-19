package knife;

import static com.google.common.base.Predicates.containsPattern;
import static com.google.common.base.Predicates.not;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Lists.newArrayList;

import java.io.File;

import org.apache.commons.cli.CommandLine;

import com.google.common.base.Strings;


abstract class JavacFileSupplierBase extends FileSupplierBase implements JavacFileSupplier {

    private String excludePattern;

    protected JavacFileSupplierBase(CommandLine arguments) {
        super(arguments);
        excludePattern = arguments.getOptionValue(Common.OPT_EXCLUDE); 
    }
    
    public Iterable<String> getFileNames()
    {        
        Iterable<String> argv = super.getFileNames();
        
        boolean hasExcludePattern = Strings.emptyToNull(excludePattern) != null;

        if (hasExcludePattern) {
            String excludePackage = excludePattern.replace('.', File.separatorChar);
            return newArrayList(filter(argv, not(containsPattern(excludePackage))));                
        }
        return argv;
    }
    
}
