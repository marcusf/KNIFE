package knife;

import static com.google.common.base.Joiner.on;
import static com.google.common.base.Predicates.containsPattern;
import static com.google.common.base.Predicates.not;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;

import java.io.File;
import java.util.List;

import org.apache.commons.cli.CommandLine;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.inject.Inject;

/**
 * Base class implementing FileSupplier. 
 * Parses argv and normalizes path names in a way that the
 * rest of KNIFE can understand. Does not do any filtering 
 * of input.
 */
public class FileSupplierBase implements FileSupplier {
    
    private List<String> argv;

    @Inject
    protected FileSupplierBase(CommandLine arguments) {
        argv = newArrayList(filter(newArrayList(arguments.getArgs()), not(containsPattern("^\\s*$"))));
        argv = transform(argv, new NormalizePathNames());
    }

    private static class NormalizePathNames implements Function<String, String>
    {
        public String apply(String input)
        {
            /* Ugly hack. Need to fix. No time now. */
            String prefix = input.charAt(0) == File.separatorChar ? File.separator : "";
            return prefix + 
                    on(File.separatorChar).skipNulls().join(
                            Splitter.onPattern(File.separator).omitEmptyStrings().split(input));
        }
    }

    @Override
    public Iterable<String> getFileNames()
    {
        return argv;
    }

}
