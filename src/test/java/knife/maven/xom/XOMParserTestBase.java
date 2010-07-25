package knife.maven.xom;

import java.io.File;
import java.net.URL;

public class XOMParserTestBase {

    protected Model parse(String fname) throws Exception
    {
        URL inputUrl = ClassLoader.getSystemClassLoader().getResource("mvn-pom-test/" + fname);
        File input = new File(inputUrl.toURI());
        return (new POMParser(input)).parse();
    }
   
    
}
