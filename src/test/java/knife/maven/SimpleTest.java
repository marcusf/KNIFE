package knife.maven;

import static org.junit.Assert.*;

import java.net.URL;
import java.util.List;

import org.junit.Test;

import com.google.inject.internal.Lists;

public class SimpleTest {

    @Test
    public void get_modules() throws Exception 
    {
        URL input = ClassLoader.getSystemClassLoader().getResource("mvn-pom-test/pom.xml");
        TopPOMLoader target = new TopPOMLoader(input);
        
        List<String> modules = target.getModules();
        assertEquals(modules, Lists.newArrayList("module-a", "module-b"));
    }
    
}
