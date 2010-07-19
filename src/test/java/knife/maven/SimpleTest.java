package knife.maven;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

public class SimpleTest {

    private TopPOMLoader target;

    @Before
    public void set_up() throws Exception {
        URL inputUrl = ClassLoader.getSystemClassLoader().getResource("mvn-pom-test/pom.xml");
        File input = new File(inputUrl.toURI());
        target = new TopPOMLoader(input);
    }
    
    @Test
    public void get_module_names() throws Exception 
    {   
        List<String> modules = target.getPOMTree().getModuleNames();
        assertEquals(modules, Lists.newArrayList("module-a", "module-b"));
    }
    
    @Test
    public void get_modules() throws Exception
    {
        SimplePOMModel pom = target.getPOMTree();
        List<SimplePOMModel> modules = pom.getModules();
        assertEquals(2, modules.size());
        assertEquals("module-a", modules.get(0).getArtifactId());
        assertEquals("module-b", modules.get(1).getArtifactId());
    }
    
}
