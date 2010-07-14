package analyzer;

import java.io.PrintStream;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.internal.Lists;

public class ListOutput implements Output {

    private final List<String> list = Lists.newArrayList();
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
