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

// unzip methods heavily based on http://piotrga.wordpress.com/2008/05/07/how-to-unzip-archive-in-java/
public class ZipUtil {

    public static void unzip(File archive, File outputDir) throws IOException {

        ZipFile zipfile = new ZipFile(archive);
        for (Enumeration e = zipfile.entries(); e.hasMoreElements(); ) {
            ZipEntry entry = (ZipEntry) e.nextElement();
            unzipEntry(zipfile, entry, outputDir);
        }        
    }

    private static void unzipEntry(ZipFile zipfile, ZipEntry entry, File outputDir) throws IOException {

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

        BufferedInputStream in = new BufferedInputStream(zipfile.getInputStream(entry));
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outputFile));

        try {
            IOUtil.copy(in, out);
        } finally {
            if (out != null) {
                try {
                    out.close();
                }
                catch (Throwable t) {
                    t.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                }
                catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
    }
    
    private static void createDirectoryAndParentDirectories(File dir) throws IOException {
        if(!dir.mkdirs()) throw new IOException("Can not create directory '" + dir.getCanonicalPath() + "'");
    }

    public static void zip(File inputDir, File zipFilePathname, String[] excludeFilenamePatterns) throws IOException {
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new FileOutputStream(zipFilePathname)); 
            zipDirectory(inputDir, zos, inputDir.getCanonicalPath(), excludeFilenamePatterns);
        }
        finally {
            if (zos != null) {
                zos.close();
            }
        }
        System.err.println("Archive created '" + zipFilePathname.getCanonicalPath() + "'");
    }

    public static void zipDirectory(File zipDir, ZipOutputStream zos, String rootDir, String[] excludeFilenamePatterns) throws IOException
    {         
        System.err.println("Zipping directory '" + zipDir.getCanonicalPath() + "'");
        String[] dirList = zipDir.list();         

        for(int i=0; i<dirList.length; i++) { 
            File f = new File(zipDir, dirList[i]); 
            if(f.isDirectory()) { 
                zipDirectory(f, zos, rootDir, excludeFilenamePatterns);
            }
            else {
                FileInputStream in = new FileInputStream(f);
                String path = "." + f.getCanonicalPath().replace(rootDir, "");
                
                boolean exclude = false;
                for (int j=0; !exclude && j<excludeFilenamePatterns.length; j++) {
                    if (f.getName().matches(excludeFilenamePatterns[j])) {
                        exclude = true;
                    }
                }
                
                if (exclude) {
                    System.err.println("Not including file '" + path + "'"); 
                }
                else {
                    System.err.println("Zipping file '" + path + "'"); 
                    ZipEntry entry = new ZipEntry(path); 
                    zos.putNextEntry(entry); 
                
                    try {
                        IOUtil.copy(in, zos);
                    } finally {
                        // don't close zos- need to leave open
                        if (in != null) {
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
}