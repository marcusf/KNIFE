package knife.analysis;

import static com.google.common.base.Functions.compose;
import static com.google.common.base.Functions.toStringFunction;
import static com.google.common.collect.Collections2.transform;
import static com.google.inject.internal.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

import java.util.List;

import knife.TestableListOutput;
import knife.maven.POMName;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.junit.Before;

import com.google.common.base.Function;

public class LongestPathAnalysisTestBase {

    protected final int NODE_COUNT = 20;
    protected LongestPathAnalysis target;
    protected TestableListOutput output;
    protected POMName[] n = new POMName[NODE_COUNT];
    protected SimpleDirectedGraph<POMName, DefaultEdge> graph;

    @Before
    public void create_graph()
    {
        
        final String T = "T";
        
        graph = new SimpleDirectedGraph<POMName, DefaultEdge>(DefaultEdge.class);
        
        for (int i = 0; i < NODE_COUNT; i++) {            
            n[i] = new POMName(T, ""+i);
        }
        
        output = new TestableListOutput(); 
        target = new LongestPathAnalysis(graph, output);
    }
    
    protected void test() {
        target.execute();
    }

    protected LongestPathAnalysisTestBase bind(int source, int target)
    {
        Graphs.addEdgeWithVertices(graph, n[source], n[target]);
        return this;
    }

    protected void thread(int... nodes)
    {
        for (int i = 0; i < nodes.length-1; i++) {
            bind(nodes[i], nodes[i+1]);
        }
    }

    protected void longestIs(Integer... expected)
    {
        
        List<Integer> input = newArrayList(expected);
        
        List<String> exp = newArrayList(transform(input, 
                compose(toStringFunction(), arrayLookup())));
        assertEquals(exp, output.getListOutput());
    }

    private Function<Integer, POMName> arrayLookup()
    {
        return new Function<Integer, POMName>() {
            public POMName apply(Integer arg0) {
                return n[arg0];
            }    
        };
    }

}
