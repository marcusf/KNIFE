package knife.analysis;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Sets;
import com.google.inject.internal.Lists;

public class UsageMapTest {

    UsageMap target;
    
    @Before
    public void init() {
        target = new UsageMap();
    }
    
    @Test
    public void no_null_class_names() {
        try {
            target.addFilesUsedByClass(null, Lists.newArrayList("a","b","c"));
        } catch (NullPointerException e) {
            return;
        }
        fail();
    }
    
    @Test
    public void no_null_class_name() {
        try {
            target.addFilesUsedByClass(null, "a");
        } catch (NullPointerException e) {
            return;
        }
        fail();
    }
    
    
    @Test
    public void no_empty_class_names() {
        try {
            target.addFilesUsedByClass("", Lists.newArrayList("a","b","c"));
        } catch (IllegalArgumentException e) {
            return;
        }
        fail();
    }
    
    @Test
    public void no_empty_class_name() {
        try {
            target.addFilesUsedByClass("", "a");
        } catch (IllegalArgumentException e) {
            return;
        }
        fail();
    }
    
    @Test
    public void reverse_mapping() {
        target.addFilesUsedByClass("a", "b");
        target.addFilesUsedByClass("a", Lists.newArrayList("c","d"));
        assertEquals(Sets.newHashSet("a"),target.getUsagesByClass("b"));
        assertEquals(Sets.newHashSet("a"),target.getUsagesByClass("c"));
        assertEquals(Sets.newHashSet("a"),target.getUsagesByClass("d"));
    }
    
    @Test
    public void all_classes_includes_references_and_referenced() {
        target.addFilesUsedByClass("a", "b");
        target.addFilesUsedByClass("a", Lists.newArrayList("c","d"));
        assertEquals(Sets.newHashSet("a","b","c","d"),target.getClasses());
    }
    
}
