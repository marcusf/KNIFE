package analyzer.spec;


public class TestSpec {
    
    private String analysis;
    private String options;
    private String expected;
    
    private String description;
    
    private boolean expectError = false;
    
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

    public boolean getExpectError()
    {
        return expectError;
    }

    public void setExpectError(boolean expectError)
    {
        this.expectError = expectError;
    }

}
