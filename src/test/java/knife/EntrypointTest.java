package knife;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import knife.Entrypoint;
import knife.UsageWriter;

import org.junit.Before;
import org.junit.Test;

import com.google.common.io.CountingOutputStream;

public class EntrypointTest {

    @SuppressWarnings("unused")
    private Entrypoint target;    
    private UsageWriter uw;
    private CountingOutputStream err;    
    
    
    @Test
    public void bad_arguments_give_error() {
        String[] argv = new String[] {"crazy"};
        try {
            target = new Entrypoint(argv, uw);
        } catch(IllegalArgumentException e) {            
            assertTrue("Did not print error message", err.getCount() > 0);
            return;
        }
        
        fail("Did not fail on bad arguments");
    }

    @Test
    public void no_arguments_give_error() {
        String[] argv = new String[] {};
        try {
            target = new Entrypoint(argv, uw);
        } catch(IllegalArgumentException e) {            
            assertTrue("Did not print error message", err.getCount() > 0);
            return;
        }
        
        fail("Did not fail on bad arguments");
    }
    
    @Test
    public void known_analysis_works() {
        String[] argv = new String[] { "importedby" };
        try {
            target = new Entrypoint(argv, uw);
        } catch(IllegalArgumentException e) {            
            fail();
        }
        
        assertTrue("Printed error message", err.getCount() == 0);
    }


    
    @Before
    public void setUpTarget() {
        err = new CountingOutputStream(new ByteArrayOutputStream());
        PrintStream printErr = new PrintStream(err);
        uw = new UsageWriter(printErr);
    }
    
}
