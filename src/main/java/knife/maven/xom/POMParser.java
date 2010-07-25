package knife.maven.xom;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Node;
import nu.xom.ParsingException;
import nu.xom.Text;
import nu.xom.ValidityException;

import com.google.inject.internal.Preconditions;

public class POMParser {

    private File input;
    private Model output;

    public POMParser(File input) throws FileNotFoundException 
    {
        this.input  = input;
        this.output = new Model();
    }
    
    public Model parse() 
           throws ValidityException, ParsingException, IOException 
    {
        Builder builder = new Builder();
        Document doc = builder.build(input);
        Element root = doc.getRootElement();
        
        Preconditions.checkArgument("project".equals(root.getLocalName()));
        
        Elements childElements = root.getChildElements();
        
        for (int i = 0; i < childElements.size(); i++) {
            Element e = childElements.get(i);
            
            String v = e.getLocalName();
            
            if ("artifactId".equals(v)) {
                output.artifactId = getTextValue(e);
            } else
            if ("groupId".equals(v)) {
                output.groupId = getTextValue(e);
            } else
            if ("parent".equals(v)) {
                parseParent(e);
            } else
            if ("modules".equals(v)) {
                parseModules(e);
            } else
            if ("dependencies".equals(v)) {
                parseDependencies(e);
            }
        }
        
        return output;
    }

    private String getTextValue(Element e)
    {
        Node child = e.getChild(0);
        if (child instanceof Text) {
            String v = ((Text) child).getValue();
            v = v.replace('\n', ' ').trim();
            return v;
        }
        return "";
    }

    private void parseModules(Element e)
    {
        Elements modules = e.getChildElements();
        for (int i = 0; i < modules.size(); i++) {
            Element module = modules.get(i);
            if ("module".equals(module.getLocalName())) {
                String moduleName = getTextValue(module);
                if (moduleName.length() > 0) {
                    output.moduleNames.add(moduleName);
                }
            }
        }
        
    }

    private void parseDependencies(Element e)
    {
        Elements dependencies = e.getChildElements();
        for (int i = 0; i < dependencies.size(); i++) {
            Element dep = dependencies.get(i);
            Dependency dependency = parseDependency(dep);
            output.moduleDependencies.add(dependency);
        }
    }
    
    private Dependency parseDependency(Element dep) 
    {
        Dependency d = new Dependency();
        
        Elements es = dep.getChildElements();
        for (int i = 0; i < es.size(); i++) {
            Element e = es.get(i);
            String v = e.getLocalName();

            if ("artifactId".equals(v)) {
                d.artifactId = getTextValue(e);
            } else
            if ("groupId".equals(v)) {
                d.groupId = getTextValue(e);
            }
        }
        
        return d;
    }


    private void parseParent(Element parent)
    {
        Elements es = parent.getChildElements();
        for (int i = 0; i < es.size(); i++) {
            Element e = es.get(i);
            String v = e.getLocalName();

            if ("artifactId".equals(v)) {
                output.parent.artifactId = getTextValue(e);
            } else
            if ("groupId".equals(v)) {
                output.parent.groupId = getTextValue(e);
            }
        }
    }
    
    
}
