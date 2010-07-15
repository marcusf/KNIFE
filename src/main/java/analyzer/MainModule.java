package analyzer;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;

public class MainModule extends AbstractModule {

    private final ArrayList<String> arguments;

    public MainModule(ArrayList<String> arguments) {
        this.arguments = arguments;
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
    }

    @Provides
    PrintStream providePrintStream() {
        return System.out;
    }
    
}
