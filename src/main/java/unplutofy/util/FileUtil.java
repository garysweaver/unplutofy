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
import java.util.*;
import java.util.zip.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gary S. Weaver
 */
public class FileUtil {

    public static String getCanonicalPath(String filename) throws IOException {
        File file = new File(filename);
        return file.getCanonicalPath();
    }

    public static List getCanonicalPaths(List filenames) throws IOException {
        List result = new ArrayList();
        for (int i=0; i<filenames.size(); i++) {
            File file = new File((String)filenames.get(i));
            result.add(file.getCanonicalPath());
        }
        return result;
    }

    public static void makeParentDirectories(String filePathname) throws IOException {
        File file = new File(filePathname);
        File dir = file.getParentFile();
        if (dir != null) {
            if (!dir.exists()) {
                if(!dir.mkdirs()) {
                    throw new IOException("Could not make directory: " + dir.getCanonicalPath() + " !");
                }
            }
        }
    }
    
    public static String readFileAsString(String filePathname) throws IOException {
        StringBuffer sb = new StringBuffer();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(filePathname));
            String str;
            while ((str = in.readLine()) != null) {                
                sb.append(str + "\n");
            }            
        }
        finally {
            if (in!=null) {
                in.close();
            }
        }
        return sb.toString();
    }
    
    public static void writeStringToFile(String s, String filePathname) throws IOException {
        makeParentDirectories(filePathname);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filePathname));
            writer.write(s);
        } finally {
            if (writer!=null) {
                writer.close();
            }
        }
    }
    
    // based on solution from TofuBeer http://stackoverflow.com/questions/617414/create-a-temporary-directory-in-java
    public static File createTempDirectory() throws IOException {
        final File temp;
        temp = File.createTempFile("temp", Long.toString(System.nanoTime()));

        if(!(temp.delete())) {
            throw new IOException("Could not delete temp file: " + temp.getCanonicalPath());
        }

        if(!(temp.mkdir())) {
            throw new IOException("Could not create temp directory: " + temp.getCanonicalPath());
        }

        return temp;
    }    
}
