package knife;

import java.io.PrintStream;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.internal.Lists;

/**
 * ListOutput is the basic output type of analyses.
 * Grabs strings as input and prints them when required.
 */
public class ListOutput implements Output {

    protected final List<String> list = Lists.newArrayList();
    private final PrintStream out;

    @Inject
    ListOutput(PrintStream out) {
        this.out = out;
    }
    
    public void add(String output) {
        list.add(output);
    }

    @Override
    public void write()
    {
        for (String s: list) {
            out.println(s);
        }
    }

}
