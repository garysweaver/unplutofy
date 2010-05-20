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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gary S. Weaver
 */
public class StringUtil {

    public static List convertCommaDelimitedStringToList(String s) {
        List result = null;
        if (s != null) {
            result = new ArrayList();
            String[] valueArray = s.split(",");
            for (int i = 0; i < valueArray.length; i++) {
                String value = valueArray[i];
                if (value != null) {
                    value = value.trim();
                    if (!"".equals(value)) {
                        result.add(value);
                    }
                }
            }
        }
        return result;
    }

    public static String convertListToCommaDelimitedString(List list) {
        StringBuffer sb = new StringBuffer();
        if (list != null) {
            boolean addComma = false;
            for (int i=0; i<list.size(); i++) {
                if (addComma) {
                    sb.append(", ");
                }
                sb.append(list.get(i));
                addComma = true;
            }
        }
        return sb.toString();
    }
}
