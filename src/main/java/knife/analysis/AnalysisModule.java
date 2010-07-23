package knife.analysis;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import knife.maven.POMGraphProvider;
import knife.maven.POMName;
import knife.maven.TopPOMLoader;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;

/**
 * Wiring for the analysis module. Guice goo to keep things together.
 */
public class AnalysisModule extends AbstractModule {

    private final String analysis;

    public AnalysisModule(String analysis) {
        this.analysis = analysis;
    }

    @Override
    protected void configure()
    {
            
        bind(UsageMapVisitor.class);
        bind(ClassIndexVisitor.class);
        
        bind(UsageMap.class)
            .toProvider(PopulatedUsageMapProvider.class);
        
        bind(ClassIndex.class)
            .toProvider(PopulatedClassIndexProvider.class);
        
        bind(new TypeLiteral<AbstractFoldingVisitor<UsageMap>>(){})
            .to(UsageMapVisitor.class);

        bind(new TypeLiteral<AbstractFoldingVisitor<ClassIndex>>(){})
            .to(ClassIndexVisitor.class);
        
        bind(new TypeLiteral<SimpleDirectedGraph<POMName, DefaultEdge>>(){})
            .toProvider(POMGraphProvider.class);
        
        bind(TopPOMLoader.class)
            .toProvider(TopPOMLoaderProvider.class);
                
        bind(Analysis.class)
            .to(AvailableAnalyses.getAnalyses().get(analysis));
    }

    @Provides @UsageMap.New
    UsageMap providesUsageMap() {
        return new UsageMap();
    }
    
    @Provides @ClassIndex.New
    ClassIndex providesKlazzIndex() {
        return new ClassIndex();
    }
    
    @Provides
    StandardJavaFileManager providesStandardJavaFileManager() {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        return compiler.getStandardFileManager(null, null, null);
    }

    
}
