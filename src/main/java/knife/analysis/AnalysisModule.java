package knife.analysis;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;

public class AnalysisModule extends AbstractModule {

    private final String analysis;

    public AnalysisModule(String analysis) {
        this.analysis = analysis;
    }

    @Override
    protected void configure()
    {
            
        bind(UsageMapVisitor.class);
        bind(KlazzIndexVisitor.class);
        
        bind(UsageMap.class)
            .toProvider(PopulatedUsageMapProvider.class);
        
        bind(KlazzIndex.class)
            .toProvider(PopulatedKlazzIndexProvider.class);
        
        bind(new TypeLiteral<AbstractFoldingVisitor<UsageMap>>(){})
            .to(UsageMapVisitor.class);

        bind(new TypeLiteral<AbstractFoldingVisitor<KlazzIndex>>(){})
            .to(KlazzIndexVisitor.class);
                
        bind(Analysis.class)
            .to(AvailableAnalyses.getAnalyses().get(analysis));
    }

    @Provides @UsageMap.New
    UsageMap providesUsageMap() {
        return new UsageMap();
    }
    
    @Provides @KlazzIndex.New
    KlazzIndex providesKlazzIndex() {
        return new KlazzIndex();
    }
    
    @Provides
    StandardJavaFileManager providesStandardJavaFileManager() {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        return compiler.getStandardFileManager(null, null, null);
    }

    
}
