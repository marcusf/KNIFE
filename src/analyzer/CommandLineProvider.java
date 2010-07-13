package analyzer;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

public class CommandLineProvider implements Provider<CommandLine> {
    
    private final String[] argv;

    @Inject
    CommandLineProvider(@Named("Argv") String[] argv) {
        this.argv = argv;
    }
    
    public CommandLine get() 
    {
        Options opts = new Options();
        
        opts.addOption(AppOptions.OPT_EXCLUDE, "exclude-packet", true, 
                "Exclude any files in the packet (and sub packets) given");
                
        opts.addOption(AppOptions.OPT_REFCOUNT, "show-referenced-count", false, 
                "Show how many classes reference the import.");
              
        opts.addOption(AppOptions.OPT_COUNT, "show-referenced-classes-for", true, 
                "Display class names for all classes having less than N references");

        opts.addOption(AppOptions.OPT_IMPORTED_BY, "who-imports", true, 
                "Shows which classes import this class");

        CommandLineParser cp = new GnuParser();
        
        try {
            CommandLine cl = cp.parse(opts, argv);
            return cl;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        
    }

}
