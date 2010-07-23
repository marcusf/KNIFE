package knife.maven;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class POMGraphProvider implements Provider<SimpleDirectedGraph<POMName, DefaultEdge>> {

    private final TopPOMLoader pomLoader;

    @Inject
    public POMGraphProvider(TopPOMLoader pomLoader) {
        this.pomLoader = pomLoader;
    }
    
    public SimpleDirectedGraph<POMName, DefaultEdge> getGraph() {
        SimpleDirectedGraph<POMName, DefaultEdge> graph = 
            new SimpleDirectedGraph<POMName, DefaultEdge>(DefaultEdge.class);
        
        for (POMName pomName: pomLoader.getPOMs()) {
            if (!graph.containsVertex(pomName)) {
                graph.addVertex(pomName);
            }
            for (POMName dependency: pomLoader.getDependencies(pomName)) {
                if (!graph.containsVertex(dependency)) {
                    graph.addVertex(dependency);
                }
                graph.addEdge(pomName, dependency);
            }
        }
        
        return graph;
    }

    @Override
    public SimpleDirectedGraph<POMName, DefaultEdge> get()
    {
        return getGraph();
    }
    
    
}
