package knife.analysis;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import knife.ListOutput;
import knife.Output;
import knife.maven.POMName;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

public class LongestPathAnalysis implements Analysis {

    private final ListOutput output;
    private final SimpleDirectedGraph<POMName, DefaultEdge> graph;

    @Inject
    public LongestPathAnalysis(SimpleDirectedGraph<POMName, DefaultEdge> graph, 
                               ListOutput output) 
    {
        this.graph = graph;
        this.output = output;
    }
    
    @Override
    public Output execute()
    {
        TopologicalOrderIterator<POMName, DefaultEdge> iter = 
            new TopologicalOrderIterator<POMName, DefaultEdge>(graph);

        List<POMName> max = Lists.newLinkedList();
        
        while (iter.hasNext()) {
            POMName fromNode = iter.next();
            
            List<POMName> possibleMax = calculateMaxLength(fromNode);
            
            if (possibleMax.size() > max.size()) {
                max = possibleMax;
            }
        }
        
        for (POMName node: max) {
            output.add(node.toString());
        }
        
        return output;
    }
    
    private LinkedList<POMName> calculateMaxLength(POMName fromNode)
    {
        Set<DefaultEdge> edgesFromNode = graph.outgoingEdgesOf(fromNode);
        LinkedList<POMName> longestList = Lists.newLinkedList();
        for (DefaultEdge e: edgesFromNode) {
            LinkedList<POMName> candidate = calculateMaxLength(graph.getEdgeTarget(e));
            if (candidate.size() >= longestList.size()) {
                longestList = candidate;
            }
        }
        
        longestList.addFirst(fromNode);
        
        return longestList;
    }

}
