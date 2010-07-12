package analyzer;

import static com.google.common.base.Joiner.on;
import static com.google.common.base.Predicates.containsPattern;
import static com.google.common.base.Predicates.not;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

public class FileSupplier {

    static List<String> getFilesToCompile(String[] argv, 
                                          String excludePattern)
    {
        boolean hasExcludePattern = Strings.emptyToNull(excludePattern) != null;

        List<String> unfiltered = Lists.transform(newArrayList(argv), pathNormalizer());

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
