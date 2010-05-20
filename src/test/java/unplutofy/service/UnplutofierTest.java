package unplutofy.service;

import java.io.*;
import junit.framework.TestCase;
import unplutofy.util.FileUtil;


public class UnplutofierTest extends TestCase
{
    public void testUnplutofyWebxml_pluto_1_0_RC2() throws IOException {
        Unplutofier unplutofier = new Unplutofier();
        
        String result = unplutofier.unplutofyWebxml(FileUtil.readFileAsString("./src/test/resources/web.xml.example-pluto-1.0-RC2"), "someportlet");
        assertNotNull("unplutofyWebxml(String) should not return null", result);
        assertTrue("unplutofyWebxml(String) should not return string containing 'portlet-class'", result.indexOf("portlet-class")==-1);
        assertTrue("unplutofyWebxml(String) should not return string containing servlet-name element with value: someportlet", result.indexOf("<servlet-name>someportlet</servlet-name>")==-1);
        // pluto 1.1 thing
        //assertTrue("unplutofyWebxml(String) should not return string containing 'PlutoInvoker'", result.indexOf("PlutoInvoker")==-1);
        FileUtil.writeStringToFile(result, "./temp.web.xml.example-pluto-1.0-RC2.converted");
    }
    
    public void testUnplutofyWebxml_pluto_1_1_7() throws IOException {
        Unplutofier unplutofier = new Unplutofier();
        
        String result = unplutofier.unplutofyWebxml(FileUtil.readFileAsString("./src/test/resources/web.xml.example-pluto-1.1.7"), "someportlet");
        assertNotNull("unplutofyWebxml(String) should not return null", result);
        assertTrue("unplutofyWebxml(String) should not return string containing 'portlet-name'", result.indexOf("portlet-name")==-1);
        assertTrue("unplutofyWebxml(String) should not return string containing 'PlutoInvoker'", result.indexOf("PlutoInvoker")==-1);
        FileUtil.writeStringToFile(result, "./temp.web.xml.example-pluto-1.1.7.converted");
    }
}
