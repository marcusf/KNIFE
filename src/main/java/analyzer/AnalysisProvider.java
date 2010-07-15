package analyzer;

import analyzer.analysis.Analysis;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

public class AnalysisProvider {

    private final Injector injector;

    @Inject
    AnalysisProvider(Injector injector) {
        this.injector = injector;
        
    }
    
    Analysis getAnalysis(String name) {
        return injector.getInstance(Key.get(Analysis.class, Names.named(name)));
    }
    
}
