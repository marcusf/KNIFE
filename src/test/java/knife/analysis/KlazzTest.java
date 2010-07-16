package knife.analysis;

import static org.junit.Assert.*;

import org.junit.Test;

public class KlazzTest {

    private Klazz target;
    
    @Test
    public void test_null_package() 
    {
        target = new Klazz(null, "Class");
        assertEquals("Class", target.toString());
    }
    
    @Test
    public void test_empty_package()
    {
        target = new Klazz("", "Class");
        assertEquals("Class", target.toString());
    }
    
    @Test
    public void test_with_package()
    {
        target = new Klazz("a.b","C");
        assertEquals("a.b.C", target.toString());
    }
    
    @Test
    public void test_equality() 
    {
        target = new Klazz("a","Be");
        Klazz target2 = new Klazz("a","Be");
        assertTrue(target.equals(target2));
    }
    
}
