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

package unplutofy.ant.task;

import unplutofy.service.*;
import unplutofy.service.request.*;
import unplutofy.util.FileUtil;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * @author Gary S. Weaver
 */
public class UnplutofyTask extends Task {

    private String inputFile;
    private String outputFile;


    public void execute() throws BuildException {
        try {
            UnplutofyRequest req = new UnplutofyRequestImpl();
            req.setInputFile(getInputFile());
            req.setOutputFile(getOutputFile());

            System.out.println("Un-plutofying... inputFile=" + FileUtil.getCanonicalPath(req.getInputFile()) + ", outputFile=" + FileUtil.getCanonicalPath(req.getOutputFile()));

            Unplutofier unplutofier = new Unplutofier();
            unplutofier.unplutofy(req);
        }
        catch (Throwable t) {
            throw new BuildException("Failed un-plutofication!", t);
        }
    }


    public String getInputFile() {
        return inputFile;
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }
}
