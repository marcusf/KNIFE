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

public class FileSupplierBase implements FileSupplier {
    
    private List<String> argv;

    protected FileSupplierBase(CommandLine arguments) {
        argv = newArrayList(filter(newArrayList(arguments.getArgs()), not(containsPattern("^\\s*$"))));
        argv = transform(argv, new NormalizePathNames());
    }

    private static class NormalizePathNames implements Function<String, String>
    {
        public String apply(String input)
        {
            return on(File.separatorChar).skipNulls().join(Splitter.onPattern(File.separator).omitEmptyStrings().split(input));
        }
    }

    @Override
    public Iterable<String> getFileNames()
    {
        return argv;
    }

}
