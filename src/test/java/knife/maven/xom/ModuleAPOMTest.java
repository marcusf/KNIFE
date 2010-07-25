package knife.maven.xom;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ModuleAPOMTest extends XOMParserTestBase {

    private Model target;

    @Before
    public void setUp() throws Exception {
        target = parse("module-a/pom.xml");
    }
    
    @Test
    public void artifactId_is_moduleA_and_groupId_is_null() throws Exception
    {
       assertEquals("module-a", target.getArtifactId());
       assertNull(target.getGroupId());
    }
    
    @Test
    public void parent_is_myTest_in_knifeTests() throws Exception
    {
        assertEquals("knife.tests", target.getParent().getGroupId());
        assertEquals("my-test", target.getParent().getArtifactId());
    }
    
    @Test
    public void has_one_dependency_which_is_module_b() throws Exception
    {
        assertEquals(1, target.getModuleDependencies().size());
        Dependency d = target.getModuleDependencies().get(0);
        assertEquals("module-b", d.getArtifactId());
        assertEquals("knife.tests", d.getGroupId());
    }
}
