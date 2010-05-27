/*
    Copyright (c) 2010 Gary S. Weaver

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in
    all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
    THE SOFTWARE.
 */

package unplutofy.service;

import unplutofy.service.request.UnplutofyRequest;
import unplutofy.util.FileUtil;
import unplutofy.util.XmlUtil;
import unplutofy.util.JarUtil;

import java.io.IOException;
import java.io.File;
import java.util.jar.Manifest;

/**
 * @author Gary S. Weaver
 */
public class Unplutofier {

    public void unplutofy(UnplutofyRequest req) throws IOException {

        // unjar war file into temp dir
        File unwarDir = FileUtil.createTempDirectory();
        System.err.println("Created temporary dir '" + unwarDir.getCanonicalPath() + "'");
        System.err.println("Decompressing '" + req.getInputFile() + "'...");

        Manifest manifest = JarUtil.unjar(new File(req.getInputFile()), unwarDir);
        
        // unplutofy
        
        String webXmlPathname = unwarDir.getCanonicalPath() + File.separator + "WEB-INF" + File.separator + "web.xml";
        System.err.println("Reading " + webXmlPathname);
        String webXml = FileUtil.readFileAsString(webXmlPathname);
        String portletXmlPathname = unwarDir.getCanonicalPath() + File.separator + "WEB-INF" + File.separator + "portlet.xml";
        System.err.println("Reading " + portletXmlPathname);
        String portletXml = FileUtil.readFileAsString(portletXmlPathname);
        System.err.println("Parsing portlet-name");
        String portletName = XmlUtil.readElementValue("portlet-name", portletXml).trim();
        System.err.println("Parsed portlet-name '" + portletName + "'");
        System.err.println("Editing web.xml");
        webXml = unplutofyWebxml(webXml, portletName);
        System.err.println("Saving web.xml");
        FileUtil.writeStringToFile(webXml, webXmlPathname);
        
        // rejar war file up to output warpathname
        System.err.println("Compressing '" + req.getOutputFile() + "'...");
        // used http://www.regexplanet.com/simple/index.html to test, but online tester uses \ vs \\ like required in string...
        String[] excludeFilenamePatterns = {"MANIFEST.MF", "portlet.*\\.tld"};
        JarUtil.jar(unwarDir, new File(req.getOutputFile()), manifest, excludeFilenamePatterns);
        
        // clean-up
        unwarDir.delete();
        System.err.println("Deleted temporary dir '" + unwarDir.getCanonicalPath() + "'");
    }
    
    public String unplutofyWebxml(String originalWebXml, String portletName) {
        String result = originalWebXml;
        // pluto 1.0-RC2
        result = XmlUtil.removeElementsContainingString(result, "<servlet>", "</servlet>", "portlet-class");
        result = XmlUtil.removeElementsContainingString(result, "<servlet-mapping>", "</servlet-mapping>", "<servlet-name>" + portletName + "</servlet-name>");
        result = XmlUtil.removeElementsContainingString(result, "<taglib>", "</taglib>", "/portlet.tld");
        // pluto 1.1.7
        result = XmlUtil.removeElementsContainingString(result, "<servlet>", "</servlet>", "<param-name>portlet-name");
        result = XmlUtil.removeElementsContainingString(result, "<servlet-mapping>", "</servlet-mapping>", "PlutoInvoker");
        
        return result;
    }
}
