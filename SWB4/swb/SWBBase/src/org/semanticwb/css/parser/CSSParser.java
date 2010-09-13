/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.css.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

// TODO: Auto-generated Javadoc
/**
 * The Class CSSParser.
 * 
 * @author victor.lorenzana
 */
public class CSSParser
{

    /** The selectors. */
    private ArrayList<Selector> selectors = new ArrayList<Selector>();

    /**
     * Instantiates a new cSS parser.
     * 
     * @param data the data
     */
    public CSSParser(String data)
    {
        data = cleanComments(data);
        String header = null;
        if (data.startsWith("@"))
        {
            data = data.substring(1);
            int pos = data.indexOf(";");
            if (pos != -1)
            {
                header = data.substring(0, pos);
                data = data.substring(pos + 1);
            }
        }
        data = removeNewLines(data);
        int pos = data.indexOf('{');
        while (pos != -1)
        {
            String selectorrow = data.substring(0, pos).trim();
            HashSet<Attribute> atts = new HashSet<Attribute>();
            data = data.substring(pos + 1);
            int pos2 = data.indexOf('}');
            if (pos2 != -1)
            {
                String values = data.substring(0, pos2).trim();
                data = data.substring(pos2 + 1);
                StringTokenizer st = new StringTokenizer(values, ";");
                while (st.hasMoreTokens())
                {
                    String attribute = st.nextToken();
                    StringTokenizer st2 = new StringTokenizer(attribute, ":");
                    if (st2.countTokens() == 2)
                    {
                        String name = st2.nextToken().trim();
                        String value = st2.nextToken().trim();
                        Attribute att = new Attribute(name, value);
                        atts.add(att);
                    }
                    else if(st2.countTokens() == 1)
                    {
                        String name = "";
                        String value = st2.nextToken();
                        Attribute att = new Attribute(name, value);
                        atts.add(att);
                    }
                    else
                    {
                        throw new IllegalArgumentException("The css is invalid");
                    }
                }
            }
            else
            {
                throw new IllegalArgumentException("The css is invalid");
            }
            
            for(String selector : getSelectors(selectorrow))
            {
                selectors.add(new Selector(selector, atts));
            }

            pos = data.indexOf('{');
        }
    }
    
    /**
     * Gets the selectors.
     * 
     * @param selector the selector
     * @return the selectors
     */
    private String[] getSelectors(String selector)
    {
        HashSet<String> getSelectors=new HashSet<String>();
        selector=selector.replace(' ',',');
        StringTokenizer st=new StringTokenizer(selector,",");
        while(st.hasMoreTokens())
        {
            String nselector= st.nextToken().trim();
            if(!nselector.equals(""))
            {
                getSelectors.add(nselector);                
            }            
        }
        return getSelectors.toArray(new String[getSelectors.size()]);
        
    }

    /**
     * Gets the selectors.
     * 
     * @return the selectors
     */
    public Selector[] getSelectors()
    {
        return selectors.toArray(new Selector[selectors.size()]);
    }

    /**
     * Clean comments.
     * 
     * @param data the data
     * @return the string
     */
    private String cleanComments(String data)
    {
        StringBuilder cssclean = new StringBuilder();
        int pos = data.indexOf("/*");
        while (pos != -1)
        {
            cssclean.append(data.substring(0, pos));
            data = data.substring(pos + 2);
            int pos2 = data.indexOf("*/");
            if (pos2 != -1)
            {
                //System.out.println(data.substring(0, pos2));
                data = data.substring(pos2 + 2);
            }
            pos = data.indexOf("/*");
        }
        cssclean.append(data);
        return cssclean.toString().trim();
    }

    /**
     * Removes the new lines.
     * 
     * @param data the data
     * @return the string
     */
    private String removeNewLines(String data)
    {
        StringBuilder cs = new StringBuilder();
        char[] values = data.toCharArray();
        for (char value : values)
        {
            if (!(value == '\r' || value == '\n'))
            {
                cs.append(value);
            }
        }
        return cs.toString();
    }

    /**
     * The main method.
     * 
     * @param args the arguments
     */
    public static void main(String[] args)
    {
        //String path = "C:\\Documents and Settings\\victor.lorenzana\\Escritorio\\estilos.css";
        String path = "C:\\Documents and Settings\\victor.lorenzana\\Escritorio\\SEGOB XHTML Strict\\images\\estilos.css";
        File file = new File(path);
        StringBuilder css = new StringBuilder();
        try
        {
            FileReader fr = new FileReader(file);
            BufferedReader bf = new BufferedReader(fr);
            String line = bf.readLine();
            while (line != null)
            {
                css.append(line);
                css.append("\r\n");
                line = bf.readLine();
            }
            CSSParser p = new CSSParser(css.toString());
            for (Selector selector : p.getSelectors())
            {
                for (Attribute att : selector.getAttributes())
                {
                    if (att.getName().equals("background-image") || att.getName().equals("background") || att.getName().equals("list-style"))
                    {
                        for (String value : att.getValues())
                        {
                            if (value.startsWith("url("))
                            {
                                value = value.substring(4);                                
                                int pos = value.indexOf(")");
                                if (pos != -1)
                                {
                                    value = value.substring(0, pos).trim();
                                    
                                    if (value.indexOf(".") != -1 && !value.startsWith("http://") && !value.toLowerCase().startsWith("wbrelpath://") && !value.startsWith("https://") && !value.startsWith("mailto:") && !value.startsWith("javascript:") && !value.startsWith("ftp:") && !value.startsWith("rtsp:") && !value.startsWith("telnet:") && !value.startsWith("#") && !value.startsWith("/") && !value.startsWith("../"))
                                    {
                                        if(value.startsWith("\"") && value.endsWith("\""))
                                        {
                                            value=value.substring(1,value.length()-1);
                                        }
                                        System.out.println(value);
                                    }

                                }

                            }
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
