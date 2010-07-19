package knife.analysis;

import java.io.PrintStream;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaFileObject;

import com.google.inject.Inject;

import knife.Output;

public class ParseDiagnosticListener 
       implements DiagnosticListener<JavaFileObject> 
{
    private final PrintStream err;

    @Inject
    public ParseDiagnosticListener(@Output.Err PrintStream err) {
        this.err = err;
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void report(Diagnostic<? extends JavaFileObject> diagnostic)
    {
        String message = diagnostic.getMessage(null);
        err.println(message);
    }

}
