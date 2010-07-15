package analyzer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.apache.commons.cli.CommandLine;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.google.inject.internal.Lists;
import com.google.inject.name.Names;

public class TestModule extends AbstractModule {

    private final List<String> arguments;
    private final ByteArrayOutputStream output;
    private final String testName;

    public TestModule(String[] commandLineArguments, ByteArrayOutputStream output, String testName) {
        this.arguments = Lists.newArrayList(commandLineArguments);
        this.output = output;
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
        
        bind(FileSupplier.class)
            .to(TestFileSupplierImpl.class);
        
        bind(String.class)
            .annotatedWith(Names.named("testCase"))
            .toInstance(testName);
    }

    @Provides
    PrintStream providePrintStream() {
        return new PrintStream(output);
    }
}
