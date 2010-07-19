package knife;

import static com.google.common.base.Joiner.on;
import static com.google.common.base.Predicates.containsPattern;
import static com.google.common.base.Predicates.not;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;

import java.util.List;

import org.apache.commons.cli.CommandLine;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;


abstract class FileSupplierBase implements FileSupplier {

    private List<String> argv;
    private String excludePattern;


    protected FileSupplierBase(CommandLine arguments) {
        argv = newArrayList(filter(newArrayList(arguments.getArgs()), not(containsPattern("^\\s*$"))));
        excludePattern = arguments.getOptionValue(Common.OPT_EXCLUDE); 
    }
    
    protected List<String> getFileNames()
    {
        boolean hasExcludePattern = Strings.emptyToNull(excludePattern) != null;

        List<String> unfiltered = transform(argv, pathNormalizer());

        if (hasExcludePattern) {
            // Very windows hostile
            String excludePackage = excludePattern.replace('.', '/');
            return newArrayList(filter(unfiltered, not(containsPattern(excludePackage))));                
        }
        return unfiltered;
    }


    private static Function<String, String> pathNormalizer() {
        return new NormalizePathNames();
    }

    private static class NormalizePathNames implements Function<String, String>
    {
        public String apply(String input)
        {
            return on('/').skipNulls().join(Splitter.onPattern("/").omitEmptyStrings().split(input));
        }
    }
    
}
