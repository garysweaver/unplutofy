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

package unplutofy.commandline;

import unplutofy.service.*;
import unplutofy.service.request.*;
import unplutofy.util.FileUtil;

/**
 * @author Gary S. Weaver
 */
public class UnplutofyCommand {

    public static void main(String[] args) {
        if (args == null || args.length != 2) {
            System.err.println();
            System.out.println("Removes changes that Apache Pluto makes to a portlet war file during pluto-fication.");
            System.err.println();
            System.err.println("usage:");
            System.err.println();
            System.err.println("java unplutofy.commandline.UnplutofyCommand inputFile outputFile");
            System.err.println();
            return;
        }

        UnplutofyRequest req = new UnplutofyRequestImpl();
        req.setInputFile(args[0]);
        req.setOutputFile(args[1]);

        Unplutofier unplutofier = new Unplutofier();
        try {
            System.out.println("Un-plutofying... inputFile=" + FileUtil.getCanonicalPath(req.getInputFile()) + ", outputFile=" + FileUtil.getCanonicalPath(req.getOutputFile()));
            unplutofier.unplutofy(req);
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
