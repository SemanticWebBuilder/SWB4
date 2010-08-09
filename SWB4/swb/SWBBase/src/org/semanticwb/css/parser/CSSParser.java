/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.css.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 *
 * @author victor.lorenzana
 */
public class CSSParser {

    private Set<Selector> selectors=new HashSet<Selector>();
    public CSSParser(String data)
    {
        data=cleanComments(data);
        String header=null;
        if(data.startsWith("@"))
        {
            data=data.substring(1);
            int pos=data.indexOf(";");
            if(pos!=-1)
            {
                header=data.substring(0,pos);
                data=data.substring(pos+1);
            }
        }         
        data=removeNewLines(data);
        int pos=data.indexOf('{');
        while(pos!=-1)
        {
            String selector=data.substring(0,pos).trim();
            HashSet<Attribute> atts=new HashSet<Attribute>();
            data=data.substring(pos+1);
            int pos2=data.indexOf('}');
            if(pos2!=-1)
            {
                String values=data.substring(0,pos2);
                data=data.substring(pos2+1);
                StringTokenizer st=new StringTokenizer(values, ";");
                while(st.hasMoreTokens())
                {
                    String attribute=st.nextToken();
                    StringTokenizer st2=new StringTokenizer(attribute, ":");
                    if(st2.countTokens()==2)
                    {
                        String name=st2.nextToken();
                        String value=st2.nextToken();
                        Attribute att=new Attribute(name, value);
                        atts.add(att);
                    }
                }                
            }
            else
            {
                throw new IllegalArgumentException("The css is invalid");
            }
            selector=selector.trim();
            if(selector.indexOf(",")!=-1)
            {
                StringTokenizer st=new StringTokenizer(selector,",");
                while(st.hasMoreTokens())
                {
                    String newSelector=st.nextToken();
                    Selector oselector=new Selector(newSelector, atts);
                    selectors.add(oselector);
                }
            }
            else
            {
                Selector oselector=new Selector(selector, atts);
                selectors.add(oselector);    
            }
            
            pos=data.indexOf('{');
        }
    }
    public Selector[] getSelectors()
    {
        return selectors.toArray(new Selector[selectors.size()]);
    }
    
    private String cleanComments(String data)
    {
        StringBuilder cssclean=new StringBuilder();
        int pos=data.indexOf("/*");
        while(pos!=-1)
        {
            cssclean.append(data.substring(0,pos));
            data=data.substring(pos+2);
            int pos2=data.indexOf("*/");
            if(pos2!=-1)
            {
                data=data.substring(0,pos2+2);
            }
            pos=data.indexOf("/*");
        }
        return cssclean.toString().trim();
    }
    private String removeNewLines(String data)
    {
        StringBuilder cs=new StringBuilder();
        char[] values=data.toCharArray();
        for(char value : values)
        {
            if(!(value=='\r' || value=='\n'))
            {
                cs.append(value);
            }
        }
        return cs.toString();
    }
    public static void main(String[] args)
    {
        String path="C:\\Documents and Settings\\victor.lorenzana\\Escritorio\\estilos.css";
        File file=new File(path);
        StringBuilder css=new  StringBuilder();
        try
        {
            FileReader fr=new FileReader(file);
            BufferedReader bf=new BufferedReader(fr);
            String line=bf.readLine();
            while(line!=null)
            {
                css.append(line);
                css.append("\r\n");
                line=bf.readLine();
            }
            CSSParser p=new CSSParser(css.toString());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
