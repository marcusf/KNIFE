package knife;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import knife.CommandLineProvider;
import knife.JavacFileSupplier;
import knife.Output;

import org.apache.commons.cli.CommandLine;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;

public class TestModule extends AbstractModule {

    private final List<String> arguments;
    private final OutputStream output;
    private final String testName;
    private final OutputStream error;

    public TestModule(List<String> commandLineArguments, 
                      OutputStream output, 
                      OutputStream error,
                      String testName) 
    {
        this.arguments = commandLineArguments;
        this.output = output;
        this.error = error;
        this.testName = testName;
    }
    
    @Override
    protected void configure()
    {
        bind(CommandLine.class)
            .toProvider(CommandLineProvider.class);
        
        bind(new TypeLiteral<List<String>>(){})
            .annotatedWith(Names.named("Argv"))
            .toInstance(arguments);
        
        bind(JavacFileSupplier.class)
            .to(JavacTestFileSupplierImpl.class);
        
        bind(FileSupplier.class)
            .to(TestFileSupplierImpl.class);
        
        bind(String.class)
            .annotatedWith(Names.named("testCase"))
            .toInstance(testName);

        bind(Output.class)
            .to(ListOutput.class);
        
    }

    @Provides
    PrintStream providePrintStream() {
        return new PrintStream(output);
    }
    
    @Provides @Output.Err
    PrintStream provideErrStream() {
        return new PrintStream(error);
    }
}
