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
import java.util.jar.*;

// unjar methods heavily based on http://piotrga.wordpress.com/2008/05/07/how-to-unzip-archive-in-java/
public class JarUtil {

    public static Manifest unjar(File archive, File outputDir) throws IOException {

        JarFile jarfile = new JarFile(archive);
        Manifest manifest = jarfile.getManifest();
        for (Enumeration e = jarfile.entries(); e.hasMoreElements(); ) {
            JarEntry entry = (JarEntry) e.nextElement();
            unjarEntry(jarfile, entry, outputDir);
        }
        return manifest;
    }

    private static void unjarEntry(JarFile jarfile, JarEntry entry, File outputDir) throws IOException {

        if (entry.isDirectory()) {
            File dir = new File(outputDir, entry.getName());
            System.err.println("Extracting directory: '" + dir.getCanonicalPath() + "'");
            createDirectoryAndParentDirectories(dir);
            return;
        }

        File outputFile = new File(outputDir, entry.getName());
        System.err.println("Extracting file: '" + outputFile.getCanonicalPath() + "'");
        if (!outputFile.getParentFile().exists()){
            createDirectoryAndParentDirectories(outputFile.getParentFile());
        }

        BufferedInputStream in = new BufferedInputStream(jarfile.getInputStream(entry));
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outputFile));

        try {
            IOUtil.copy(in, out);
        } finally {
            try {
                out.close();
            }
            catch (Throwable t) {
                t.printStackTrace();
            }

            try {
                in.close();
            }
            catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
    
    private static void createDirectoryAndParentDirectories(File dir) throws IOException {
        if(!dir.mkdirs()) throw new IOException("Can not create directory '" + dir.getCanonicalPath() + "'");
    }

    public static void jar(File inputDir, File jarFilePathname, Manifest manifest, String[] excludeFilenamePatterns) throws IOException {
        JarOutputStream out = null;
        try {
            out = new JarOutputStream(new FileOutputStream(jarFilePathname), manifest);
            jarDirectory(inputDir, out, inputDir.getCanonicalPath(), excludeFilenamePatterns);
        }
        finally {
            if (out != null) {
                out.close();
            }
        }
        System.err.println("Archive created '" + jarFilePathname.getCanonicalPath() + "'");
    }

    private static void jarDirectory(File jarDir, JarOutputStream out, String rootDir, String[] excludeFilenamePatterns) throws IOException
    {         
        System.err.println("Compressing directory '" + jarDir.getCanonicalPath() + "'");
        String[] dirList = jarDir.list();

        for(int i=0; i<dirList.length; i++) { 
            File f = new File(jarDir, dirList[i]);
            if(f.isDirectory()) { 
                jarDirectory(f, out, rootDir, excludeFilenamePatterns);
            }
            else {
                boolean exclude = false;
                for (int j=0; !exclude && j<excludeFilenamePatterns.length; j++) {
                    if (f.getName().matches(excludeFilenamePatterns[j])) {
                        exclude = true;
                    }
                }

                // convert /(tmpdirpath)/(relativepath) to /(relativepath) or
                //         \tmpdirpath\(relativepath) to \(relativepath)
                // and remove preceding / or \ (shouldn't assume it is File.separator)
                String path = f.getCanonicalPath().replace(rootDir, "").substring(1);
                
                if (exclude) {
                    System.err.println("Not including file '" + path + "'");
                }
                else {
                    System.err.println("Compressing file '" + path + "'");
                    JarEntry entry = new JarEntry(path);
                    out.putNextEntry(entry);

                    FileInputStream in = new FileInputStream(f);
                    try {
                        IOUtil.copy(in, out);
                    } finally {
                        // Note: don't close out - need to leave open
                        try {
                            in.close();
                        }
                        catch (Throwable t) {
                            t.printStackTrace();
                        }
                    }
                }
            }
        }        
    }
}