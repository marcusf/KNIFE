package knife.spec;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import org.yaml.snakeyaml.Loader;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.google.common.io.Resources;
import com.google.inject.internal.Lists;

public class SpecLoader {

    
    static public List<TestSpec> loadSpecs(String testName) throws Exception 
    {
        String urlName = testName + "/spec.yaml";
        URL resourceUrl = Resources.getResource(urlName);
        String yamlInput = Resources.toString(resourceUrl, Charset.forName("UTF-8"));
        Loader loader = new Loader(new Constructor(TestSpec.class));
        Yaml yaml = new Yaml(loader);
        List<TestSpec> result = Lists.newArrayList();
        for (Object o: yaml.loadAll(yamlInput)) {
            if (o instanceof TestSpec) {
                result.add((TestSpec) o);
            }
        }
        return result;
    }
    
}
