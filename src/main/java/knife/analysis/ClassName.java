package knife.analysis;

/**
 * Representation of a class name. Used for equality
 * and hash code. 
 */
public class ClassName {

    private String packageName;
    private String className;
    
    public ClassName(String packageName, String className) {
        this.packageName = packageName;
        this.className = className;
    }

    public String getClassName()
    {
        return className;
    }
    
    public String getPackageName()
    {
        return packageName;
    }
    
    @Override
    public String toString()
    {
        if (getPackageName() != null && getPackageName().length() > 0) {
            return getPackageName() + "." + getClassName();
        } else {
            return getClassName();
        }
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((className == null) ? 0 : className.hashCode());
        result = prime * result
                + ((packageName == null) ? 0 : packageName.hashCode());
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
        ClassName other = (ClassName) obj;
        if (className == null) {
            if (other.className != null)
                return false;
        } else if (!className.equals(other.className))
            return false;
        if (packageName == null) {
            if (other.packageName != null)
                return false;
        } else if (!packageName.equals(other.packageName))
            return false;
        return true;
    }
    
    
    
}
