package analyzer.spec;


public class TestSpec {
    
    private String analysis;
    private String options;
    private String expected;
    
    private String description;
    
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
    
    public void setExpected(String expected)
    {
        this.expected = expected;
    }
    
    public String getExpected()
    {
        return expected;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    } 
}
