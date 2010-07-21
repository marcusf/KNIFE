package knife.analysis;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Set;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.google.inject.BindingAnnotation;
import com.google.inject.Singleton;


/**
 * Index of classes in the files given as input to KNIFE.
 * Used in analysis step and in the UsageMap derivation as an
 * index of which classes are actually available.
 * 
 * This is required as a kind of half-measure for checking 
 * which identifiers are potentially classes. Since the AST 
 * visited does not have names resolved, we have no other way 
 * of finding out which identifiers are actually classes then 
 * to populate an index of the classes given as input, and resolve
 * in that.
 * 
 * Populated by the {@link ClassIndexVisitor}.
 */
@Singleton
public class ClassIndex {

    private SetMultimap<String, ClassName> simpleNameResolver; 
    private Set<ClassName> classes;
        
    ClassIndex() {
        int initialSize = 1000;
        simpleNameResolver = HashMultimap.create(initialSize, (int) Math.ceil(Math.sqrt(initialSize)));
        classes            = Sets.newHashSet();
    }
    
    void add(String packageName, String className) {
        ClassName klazz = new ClassName(packageName, className);        
        classes.add(klazz);
        simpleNameResolver.put(className, klazz);
    }
    
    boolean contains(String packageName, String className) {
        return classes.contains(new ClassName(packageName, className));
    }
    
    boolean contains(String className) {
        return simpleNameResolver.containsKey(className);
    }
    
    /**
     * Returns which qualified class names exist that share the same class
     * name, eg if com.a.ClassName and com.b.ClassName exist, both will
     * be returned if you call this with the string ClassName. 
     * 
     * @param className: A simple class name, "AClass", "TheVisitor", etc.
     * @return
     */
    Set<ClassName> getResolvedClassesByName(String className) {
         return simpleNameResolver.get(className);
    }
    
    /**
     * Binding used by Guice to signal that we want to create a new 
     * ClassIndex, instead of taking the one already prepopulated.
     * 
     * This is used as a kind of bootstrap, eg since the {@link ClassIndexVisitor}
     * needs an empty ClassIndex to construct from, it needs to be able
     * to get a clean one. It uses this annotation to signal that.
     */
    @BindingAnnotation @Target({ FIELD, PARAMETER, METHOD }) @Retention(RUNTIME)
    public @interface New {}

}
