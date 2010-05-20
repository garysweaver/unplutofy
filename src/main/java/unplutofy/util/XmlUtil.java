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

package unplutofy.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gary S. Weaver
 */
public class XmlUtil {
    
    public static String readElementValue(String elementName, String s) throws IOException {
        int indexOfElementStart = s.indexOf("<" + elementName);
        if (indexOfElementStart == -1) {
            throw new IOException("Couldn't find element '" + elementName + "'!");
        }
        int indexOfValueStart = s.indexOf(">", indexOfElementStart) + 1;
        if (indexOfElementStart == -1) {
            throw new IOException("Couldn't find element '" + elementName + "'! (couldn't find end of start tag)");
        }
        int indexOfValueEnd = s.indexOf("<", indexOfValueStart);
        if (indexOfElementStart == -1) {
            throw new IOException("Couldn't find element '" + elementName + "'! (couldn't find end tag)");
        }
        return s.substring(indexOfValueStart, indexOfValueEnd);
    }
    
    public static String removeElementsContainingString(String stringToParse, String startElement, String endElement, String containedString) {
        String result = stringToParse;
        
        int indexOfPortletClass;
        while ((indexOfPortletClass = result.indexOf(containedString)) != -1) {
            String beforePortletClass = result.substring(0, indexOfPortletClass);
            int indexOfServletBeginElement = beforePortletClass.lastIndexOf(startElement);
            int indexOfServletFinishElement = result.indexOf(endElement, indexOfPortletClass);
            String beforeServlet = result.substring(0, indexOfServletBeginElement);
            String afterServlet = result.substring(indexOfServletFinishElement + endElement.length());
            result = beforeServlet + afterServlet;
        }
        
        return result;
    }
}
