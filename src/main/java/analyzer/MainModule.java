package analyzer;

import java.io.PrintStream;

import org.apache.commons.cli.CommandLine;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class MainModule extends AbstractModule {

    @Override
    protected void configure()
    {
        bind(CommandLine.class)
            .toProvider(CommandLineProvider.class);
        bind(FileSupplier.class)
            .to(FileSupplierImpl.class);
    }

    @Provides
    PrintStream providePrintStream() {
        return System.out;
    }
    
}
