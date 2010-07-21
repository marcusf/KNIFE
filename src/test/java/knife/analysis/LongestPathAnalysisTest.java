package knife.analysis;

import static org.junit.Assert.*;
import knife.TestableListOutput;
import knife.maven.POMName;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.internal.Lists;

public class LongestPathAnalysisTest {

    LongestPathAnalysis target;
    TestableListOutput output;
    private POMName[] n = new POMName[5];
    private SimpleDirectedGraph<POMName, DefaultEdge> graph;
    
    @Before
    public void create_graph() {
        
        final String T = "T";
        
        graph = new SimpleDirectedGraph<POMName, DefaultEdge>(DefaultEdge.class);
        
        n[0] = new POMName(T, "0");
        n[1] = new POMName(T, "1");
        n[2] = new POMName(T, "2");
        n[3] = new POMName(T, "3");
        n[4] = new POMName(T, "4");
        
        output = new TestableListOutput(); 
        target = new LongestPathAnalysis(graph, output);
    }
    
    @Test
    public void simple_path_of_length_4() {
        Graphs.addEdgeWithVertices(graph, n[0], n[1]);
        Graphs.addEdgeWithVertices(graph, n[1], n[2]);
        Graphs.addEdgeWithVertices(graph, n[2], n[3]);

        Graphs.addEdgeWithVertices(graph, n[0], n[2]);
        Graphs.addEdgeWithVertices(graph, n[4], n[3]);
        
        target.execute();
        
        assertEquals(Lists.newArrayList(n[0].toString(), 
                                        n[1].toString(), 
                                        n[2].toString(), 
                                        n[3].toString()), 
                output.getListOutput());
    }
    
    
}
