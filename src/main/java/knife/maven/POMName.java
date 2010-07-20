package knife.maven;

import static com.google.inject.internal.Preconditions.*;
import knife.maven.generated_pom_4_0_0.Dependency;
import knife.maven.generated_pom_4_0_0.Model;

public class POMName {

    private final String artifactId;
    private final String groupId;
    
    static POMName newPomName(Model pomModel) {
        String groupId = pomModel.getGroupId() != null 
        ? pomModel.getGroupId()
        : pomModel.getParent().getGroupId()
        ;      

        return new POMName(groupId, pomModel.getArtifactId());
    }
    
    static POMName newPomName(Dependency pomDependency) {
        return new POMName(pomDependency.getGroupId(), pomDependency.getArtifactId());
    }

    public POMName(String groupId, String artifactId) {
        checkNotNull(artifactId);
        checkNotNull(groupId);
        
        this.artifactId = artifactId;
        this.groupId = groupId;
    }
    
    public String getArtifactId()
    {
        return artifactId;
    }

    public String getPackageName()
    {
        return groupId;
    }
    
    public String toString()
    {
        return groupId + "." + artifactId;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + artifactId.hashCode();
        result = prime * result + groupId.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        POMName other = (POMName) obj;
        if (!artifactId.equals(other.artifactId))
            return false;
        if (!groupId.equals(other.groupId))
            return false;
        return true;
    }

    
    
    
}
