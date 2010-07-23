package knife.analysis;

import org.junit.Test;

public class LongestPathAnalysisTest extends LongestPathAnalysisTestBase {

    @Test
    public void simple_path_has_length_4() {
        thread(0, 1, 2, 3);
        thread(0, 2);
        thread(4, 3);
        
        test();
        
        longestIs(0, 1, 2, 3);
    }
    
    @Test
    public void wikipedia_example_graph_has_length_3() {
        /** Note, this graph has three equivalent solutions.
         *  Right now the algorithm simply chooses one. */
        thread(5, 11, 9);
        thread(5, 11, 2);
        thread(5, 11, 10);
        thread(7, 8, 9);
        
        thread( 7, 11);
        thread( 3, 10);
        thread( 3,  8);

        test();
        
        longestIs(5, 11, 10);
    }
    
    @Test
    public void complex_graph_has_length_5() {
        thread(0, 1, 2, 3, 4, 5);
        thread(1, 6, 7);
        thread(9, 8);
        thread(1, 9);
        thread(0, 9);
        thread(9, 7);
        
        test();
        
        longestIs(0, 1, 2, 3, 4, 5);
    }
    
}
