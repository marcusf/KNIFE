package analyzer.spec;

import java.util.List;

public class TestSpec {

    private String analysis;
    private String options;
    private List<String> expected;
    
    public void setAnalysis(String analysis)
    {
        this.analysis = analysis;
    }
    
    public String getAnalysis()
    {
        return analysis;
    }
    
    public void setOptions(String options)
    {
        this.options = options;
    }
    
    public String getOptions()
    {
        return options;
    }
    
    public void setExpected(List<String> expected)
    {
        this.expected = expected;
    }
    
    public List<String> getExpected()
    {
        return expected;
    }
    
}
