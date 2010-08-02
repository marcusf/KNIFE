package knife.maven;

import knife.maven.xom.Dependency;
import knife.maven.xom.Model;

/**
 * Represents an artifacts unique name. Used for equality
 * and hash code.
 * 
 * Since it is only fed a groupId and an artifactId, this
 * means we treat two modules with different versions, in
 * different scopes or with different types as the
 * same artifact.
 *
 */
public class POMName {

    private final String artifactId;
    private final String groupId;
    
    /**
     * Constructs a POMName from a JAXB-generated model file.
     * Convenience method that does group resolution correctly,
     * using the parents groupId if none exists for the module.
     */
    static POMName newPomName(Model pomModel) {
        String groupId = pomModel.getGroupId() != null 
                       ? pomModel.getGroupId()
                       : pomModel.getParent().getGroupId()
                       ;      

        return new POMName(groupId, pomModel.getArtifactId());
    }
    
    /**
     * Constructs a POMName from a dependency. Used to get names
     * without having to create an entire POMModel object first.
     */
    static POMName newPomName(Dependency pomDependency) {
        return new POMName(pomDependency.getGroupId(), pomDependency.getArtifactId());
    }

    public POMName(String groupId, String artifactId) {        
        this.artifactId = artifactId;
        this.groupId = groupId;
    }
    
    public String getArtifactId()
    {
        return artifactId;
    }

    public String getGroupId()
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
