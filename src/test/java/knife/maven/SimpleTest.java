package knife.maven;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Lists;

public class SimpleTest {

    private static TopPOMLoader target;

    @BeforeClass
    public static void set_up_target() throws Exception 
    {
        URL inputUrl = ClassLoader.getSystemClassLoader().getResource("mvn-pom-test/pom.xml");
        File input = new File(inputUrl.toURI());
        target = new TopPOMLoader(input);        
    }
   
    
    @Test 
    public void top_pom_contains_module_names() throws Exception 
    {   
        List<String> modules = target.getTopPOM().getModuleNames();
        assertEquals(modules, Lists.newArrayList("module-a", "module-b"));
    }
    
    @Test 
    public void top_pom_has_a_and_b_as_submodules() throws Exception
    {
        POMModel pom = target.getTopPOM();
        List<POMModel> modules = pom.getModules();
        assertEquals(2, modules.size());
        assertEquals("knife.tests.module-a", modules.get(0).getName().toString());
        assertEquals("knife.tests.module-b", modules.get(1).getName().toString());
    }
    
    @Test 
    public void module_a_depends_on_b() throws Exception
    {
        Set<POMName> deps = target.getDependencies(new POMName("knife.tests", "module-a"));
        assertEquals(1, deps.size());
        assertTrue(deps.contains(new POMName("knife.tests", "module-b")));
    }
    
    @Test
    public void module_b_has_no_dependencies() throws Exception
    {
        Set<POMName> deps = target.getDependencies(new POMName("knife.tests", "module-b"));
        assertEquals(0, deps.size());
    }
    
    
    
}
