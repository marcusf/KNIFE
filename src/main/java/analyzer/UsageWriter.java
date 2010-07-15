package analyzer;

import java.io.PrintStream;

import com.google.inject.Inject;

import analyzer.analysis.AvailableAnalyses;

public class UsageWriter {

    private final PrintStream out;

    @Inject
    UsageWriter(PrintStream out) {
        this.out = out;
    }
    
    public void usage()
    {
        out.println("Usage: imports <analysis> [opts] [file [file ..]]");
        out.println("Where analyses are:");
        for (String k: AvailableAnalyses.getAnalyses().keySet()) {
            out.println("\t" + k);
        }
    }
    
}
