package knife;

import java.io.PrintStream;
import java.util.List;

import org.apache.commons.cli.CommandLine;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;

/**
 * Guice module to wire together the shell of the application,
 * sets up which output streams go where and what things are
 * the arguments and so on.
 */
public class MainModule extends AbstractModule {

    private final List<String> arguments;

    public MainModule(List<String> arguments) {
        this.arguments = arguments.subList(1, arguments.size());
    }

    @Override
    protected void configure()
    {
        bind(CommandLine.class)
            .toProvider(CommandLineProvider.class);
        bind(FileSupplier.class)
        .to(FileSupplierBase.class);
        bind(JavacFileSupplier.class)
            .to(JavacFileSupplierImpl.class);
        bind(new TypeLiteral<List<String>>(){})
            .annotatedWith(Names.named("Argv"))
            .toInstance(arguments);
        
        bind(PrintStream.class)
            .toInstance(System.out);
        
        bind(Output.class)
            .to(ListOutput.class);
        
        bind(PrintStream.class)
            .annotatedWith(Output.Err.class)
            .toInstance(System.err);
    }
    
}
