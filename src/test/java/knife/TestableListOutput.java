package knife;

import java.util.List;


public class TestableListOutput extends ListOutput {

    public TestableListOutput() {
        super(null);
    }
    
    public List<String> getListOutput() {
        return list;
    }

}
