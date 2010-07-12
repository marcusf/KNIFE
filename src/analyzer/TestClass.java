package analyzer;

import java.net.NetworkInterface;
import java.net.SocketException;

public class TestClass {

    void myMethod() throws SocketException {
        StringBuilder b = new StringBuilder();
        NetworkInterface n = NetworkInterface.getByName("abc");
        
        System.out.println(b.toString());
        
        if (n != null) {
            System.out.println(n.toString());
        }
    }
    
}
