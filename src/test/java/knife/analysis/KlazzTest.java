package knife.analysis;

import static org.junit.Assert.*;

import org.junit.Test;

public class KlazzTest {

    private ClassName target;
    
    @Test
    public void test_null_package() 
    {
        target = new ClassName(null, "Class");
        assertEquals("Class", target.toString());
    }
    
    @Test
    public void test_empty_package()
    {
        target = new ClassName("", "Class");
        assertEquals("Class", target.toString());
    }
    
    @Test
    public void test_with_package()
    {
        target = new ClassName("a.b","C");
        assertEquals("a.b.C", target.toString());
    }
    
    @Test
    public void test_equality() 
    {
        target = new ClassName("a","Be");
        ClassName target2 = new ClassName("a","Be");
        assertTrue(target.equals(target2));
    }
    
}
