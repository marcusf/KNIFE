package knife.maven.xom;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TopPOMTest extends XOMParserTestBase {

    private Model target;

    @Before
    public void setUp() throws Exception {
        target = parse("pom.xml");
    }
    
    @Test
    public void artifactId_is_mytest_and_groupId_is_knifetests() throws Exception 
    {
        assertEquals("my-test", target.getArtifactId());
        assertEquals("knife.tests", target.getGroupId());
    }
    
    @Test
    public void module_list_contains_a_and_b_in_that_order() throws Exception
    {
        assertEquals(2, target.getModuleNames().size());
        assertEquals("module-a", target.getModuleNames().get(0));
        assertEquals("module-b", target.getModuleNames().get(1));
    }
    
}
