package knife.analysis;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
        
        Map<POMName, LinkedList<POMName>> cache = new HashMap<POMName, LinkedList<POMName>>();
        
        TopologicalOrderIterator<POMName, DefaultEdge> iter = 
            new TopologicalOrderIterator<POMName, DefaultEdge>(graph);

        List<POMName> max = Lists.newLinkedList();
        
        while (iter.hasNext()) {
            POMName fromNode = iter.next();
            
            if (!graph.incomingEdgesOf(fromNode).isEmpty()) {
                break;
            }

            List<POMName> possibleMax = calculateMaxLength(cache, graph, fromNode);
            
            if (possibleMax.size() > max.size()) {
                max = possibleMax;
            }
        }
        
        for (POMName node: max) {
            output.add(node.toString());
        }
        
        return output;
    }
    
    @SuppressWarnings("unchecked")
    private <A,B extends DefaultEdge> LinkedList<A> 
        calculateMaxLength(Map<A, LinkedList<A>> cache, 
                           SimpleDirectedGraph<A, B> graph, 
                           A fromNode)
    {
        if (cache.containsKey(fromNode)) {
            return (LinkedList<A>) cache.get(fromNode).clone();
        }

        Set<B> edgesFromNode = graph.outgoingEdgesOf(fromNode);

        LinkedList<A> longestList = new LinkedList<A>();
        
        for (B e: edgesFromNode) {
            LinkedList<A> candidate = calculateMaxLength(cache, graph, graph.getEdgeTarget(e));
            if (candidate.size() >= longestList.size()) {
                longestList = candidate;
            }
        }
        
        longestList.addFirst(fromNode);
        
        cache.put(fromNode, (LinkedList<A>) longestList.clone());
        
        return longestList;
    }

}
