package analyzer;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import analyzer.analysis.Analysis;
import analyzer.analysis.AnalysisModule;
import analyzer.spec.SpecLoader;
import analyzer.spec.TestSpec;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.io.Resources;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.internal.Lists;
import com.google.inject.name.Names;

/**
 * 
 * Uses a combination of YAML files and parameterized JUnit tests
 * to drive testing directly from spec, eg specifying input and 
 * asserting output without 
 * 
 * @author marcusf
 *
 */
@RunWith(Parameterized.class)
public class SpecTest {

    private final TestSpec spec;
    private final Injector injector;
    
    private Module analysisModule = new AnalysisModule();
    ByteArrayOutputStream output = new ByteArrayOutputStream(); 

    public SpecTest(String testCaseDir, int testNum, TestSpec spec) {
        this.spec = spec;
        analysisModule = new AnalysisModule();
        injector = Guice.createInjector(analysisModule,
                    new TestModule(getArguments(spec.getOptions()), output, testCaseDir));
    }
    
    @Test
    public void run_test() throws Exception {
        Analysis target = getAnalysis(spec.getAnalysis());
        target.execute().write();
        // For now, split on line breaks to get list.
        assertEquals(spec.getExpected(), output.toString());
    }

    @Parameters
    public static Collection<Object[]> constructTest() throws Exception {
        
        List<Object[]> results = Lists.newArrayList();
        
        List<String> availableTests = 
            Resources.readLines(ClassLoader.getSystemClassLoader().getResource("tests"), Charset.forName("UTF-8"));
        for (String testCase: availableTests) {
            int i = 0;
            List<TestSpec> specs = SpecLoader.loadSpecs(testCase);
            for (TestSpec spec: specs) {
                results.add(new Object[]{ testCase, i, spec });
                i++;
            }
        }
        return results;
    }
    
    private Analysis getAnalysis(String analysisName)
    {
        return injector.getInstance(Key.get(Analysis.class, Names.named(analysisName)));
    }

    private String[] getArguments(String options)
    {
        String[] arguments = Iterables.toArray(Splitter.on(" ").split(options), String.class);
        return arguments;
    }
}
