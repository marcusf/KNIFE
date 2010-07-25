package knife.maven.xom;

import java.util.List;

import com.google.inject.internal.Lists;

public class Model {

    List<String> moduleNames            = Lists.newArrayList();
    List<Dependency> moduleDependencies = Lists.newArrayList();
    String groupId                      = null;
    String artifactId                   = null;
    Parent parent                       = new Parent();
    
    public List<String> getModuleNames()
    {
        return moduleNames;
    }
    
    public List<Dependency> getModuleDependencies() {
        return moduleDependencies;
    }

    public String getGroupId()
    {
        return groupId;
    }
    
    public String getArtifactId() 
    {
        return artifactId;
    }
    
    public Parent getParent() 
    {
        return parent;
    }
    
}
