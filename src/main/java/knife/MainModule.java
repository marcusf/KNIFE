package knife;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;

public class MainModule extends AbstractModule {

    private final List<String> arguments;

    public MainModule(ArrayList<String> arguments) {
        this.arguments = arguments.subList(1, arguments.size()-1);
    }

    @Override
    protected void configure()
    {
        bind(CommandLine.class)
            .toProvider(CommandLineProvider.class);
        bind(FileSupplier.class)
            .to(FileSupplierImpl.class);
        bind(new TypeLiteral<List<String>>(){})
            .annotatedWith(Names.named("Argv"))
            .toInstance(arguments);
        
        bind(PrintStream.class)
            .toInstance(System.out);
        
        bind(PrintStream.class)
            .annotatedWith(Output.Err.class)
            .toInstance(System.err);
    }
    
}
