package analyzer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.apache.commons.cli.CommandLine;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;

public class TestModule extends AbstractModule {

    private final String[] arguments;
    private final ByteArrayOutputStream output;

    public TestModule(String[] commandLineArguments, ByteArrayOutputStream output) {
        this.arguments = commandLineArguments;
        this.output = output;
    }
    
    @Override
    protected void configure()
    {
        bind(CommandLine.class)
            .toProvider(CommandLineProvider.class);
        
        bind(String[].class)
            .annotatedWith(Names.named("Argv"))
            .toInstance(arguments);
        
        bind(FileSupplier.class)
            .to(TestFileSupplier.class);
    }

    @Provides
    PrintStream providePrintStream() {
        return new PrintStream(output);
    }
}
