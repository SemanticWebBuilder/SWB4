/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopgeneratorcode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author victor.lorenzana
 */
public class RelativePath
{

    private static List getPathList(File f)
    {
        List l = new ArrayList();
        File r;
        try
        {
            r = f.getCanonicalFile();
            while (r != null)
            {
                l.add(r.getName());
                r = r.getParentFile();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            l = null;
        }
        return l;
    }

    /**
     * figure out a string representing the relative path of
     * 'f' with respect to 'r'
     * @param r home path
     * @param f path of file
     */
    private static String matchPathLists(List r, List f)
    {
        int i;
        int j;
        String s;
        // start at the beginning of the lists
        // iterate while both lists are equal
        s = "";
        i = r.size() - 1;
        j = f.size() - 1;

        // first eliminate common root
        while ((i >= 0) && (j >= 0) && (r.get(i).equals(f.get(j))))
        {
            i--;
            j--;
        }

        // for each remaining level in the home path, add a ..
        for (; i >= 0; i--)
        {
            s += ".." + File.separator;
        }

        // for each level in the file path, add the path
        for (; j >= 1; j--)
        {
            s += f.get(j) + File.separator;
        }

        // file name
        s += f.get(j);
        return s;
    }



    public static String getRelativePath(File base,File target) throws IOException
    {
        String[] baseComponents = base.getCanonicalPath().split(Pattern.quote(File.separator));
        String[] targetComponents = target.getCanonicalPath().split(Pattern.quote(File.separator));
        // skip common components   
        int index = 0;
        for (; index < targetComponents.length && index < baseComponents.length; ++index)
        {
            if (!targetComponents[index].equals(baseComponents[index]))
            {
                break;
            }
        }
        StringBuilder result = new StringBuilder();
        if (index != baseComponents.length)
        {
            // backtrack to base directory     
            for (int i = index; i < baseComponents.length; ++i)
            {
                result.append(".." + File.separator);
            }
        }
        for (; index < targetComponents.length; ++index)
        {
            result.append(targetComponents[index] + File.separator);
        }
        if (!target.getPath().endsWith("/") && !target.getPath().endsWith("\\"))
        {
            // remove final path separator     
            result.delete(result.length() - "/".length(), result.length());
        }
        return result.toString();
    }

    public static File resolve(File home, String relative)
    {
        return new File(home, relative);

    }
}
