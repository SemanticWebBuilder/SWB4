/*
 * WBHttpServletRequestWrapper.java
 *
 * Created on 10 de mayo de 2005, 04:31 PM
 */

package org.semanticwb.servlet;

import java.util.Collections;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletRequest;

import java.util.Hashtable;

import java.util.Locale;
import java.util.Vector;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class SWBHttpServletRequestWrapper extends HttpServletRequestWrapper
{
    private boolean removeParameters=false;
    private boolean mergeParameters=false;
    private Hashtable parms = new Hashtable();    
    private String lang;
    private String idtm;
    
    /** Creates a new instance of WBHttpServletRequestWrapper */
    public SWBHttpServletRequestWrapper(HttpServletRequest request)
    {
        super(request);
    }
    
    /** Creates a new instance of WBHttpServletRequestWrapper */
    public SWBHttpServletRequestWrapper(HttpServletRequest request, boolean removeParameters)
    {
        super(request);
        this.removeParameters=removeParameters;
    }
    
    public SWBHttpServletRequestWrapper(HttpServletRequest request, String lang, String idtm, boolean removeParameters)
    {
        this(request, lang, idtm, removeParameters,false);
    }    
    
    public SWBHttpServletRequestWrapper(HttpServletRequest request, String lang, String idtm, boolean removeParameters, boolean mergeParameters)
    {
        super(request);
        this.removeParameters=removeParameters;
        this.mergeParameters=mergeParameters;
        this.lang=lang;
        this.idtm=idtm;
    }
    
    
    @Override
    public String getParameter(String str)
    {
        if(removeParameters || mergeParameters)
        {
            String ret=null;
            String [] arr=getParameterValues(str);
            if(arr!=null)ret=arr[0];
            //System.out.println("getParameterR:"+str+"="+ret);
            return ret;
        }
        else 
        {
            //System.out.println("getParameter:"+str+"="+super.getParameter(str));
            return super.getParameter(str);  
        }
    }

    @Override
    public java.util.Map getParameterMap()
    {
        if(mergeParameters)
        {
            Hashtable map=new Hashtable(parms);
            map.putAll(super.getParameterMap());
            return map;
        }else if(removeParameters)
        {
            return parms;
        }
        else
        {
            return super.getParameterMap();
        }
    }

    @Override
    public java.util.Enumeration getParameterNames()
    {
        if(mergeParameters)
        {     
            return Collections.enumeration(getParameterMap().keySet());
        }else if(removeParameters)
        {
            return parms.keys();
        }
        else
        {
            return super.getParameterNames();
        }
    }

    @Override
    public String[] getParameterValues(String str)
    {
        if(mergeParameters)
        {
            String ret[]=super.getParameterValues(str);
            if(ret==null)
            {
                ret=(String[])parms.get(str);
            }
            return ret;                
        }else if(removeParameters)
            return (String[])parms.get(str);
        else 
            return super.getParameterValues(str);        
    }  
    
    public void addParameterMap(java.util.Map map)
    {
        if(removeParameters || mergeParameters)
        {
            parms.putAll(map);
        }
    }
    
    public void addParameter(String name, String value)
    {
        //System.out.println("addParameter:"+name+"="+value);
        if(removeParameters || mergeParameters)
        {
            Object obj=parms.get(name);
            if(obj == null)
            {
                parms.put(name, new String[]{value});
            }else if(obj instanceof String[])
            {
                String arr[]=(String [])obj;
                String fin[]=new String[arr.length+1];
                int x=0;
                for(;x<arr.length;x++)
                {
                    fin[x]=arr[x];
                }
                fin[x]=value;
                parms.put(name, fin);
            }
        }
    }    
    
    @Override
    public String getQueryString()
    {
        if(removeParameters)
        {
            return null;    
        }else
            return super.getQueryString();
    }

    @Override
    public java.io.BufferedReader getReader() throws java.io.IOException
    {
        if(removeParameters)
        {
            return null;
        }else
            return super.getReader();
    }    
    
    @Override
    public int getContentLength()
    {
        if(removeParameters)
            return 0;
        else
            return super.getContentLength();
    }    
    
    @Override
    public javax.servlet.ServletInputStream getInputStream() throws java.io.IOException
    {
        if(removeParameters)
            return null;
        else
            return super.getInputStream();
    }    
    
    @Override
    public java.util.Locale getLocale()
    {
        if(lang!=null)return new Locale(lang);
        return super.getLocale();
    }
    
    @Override
    public java.util.Enumeration getLocales() 
    {
        //TODO:
        /*
        if(lang!=null && idtm!=null)
        {
            Vector ret=new Vector();
            ret.add(getLocale());
            Enumeration en=DBCatalogs.getInstance().getLanguages(idtm).elements();
            while(en.hasMoreElements())
            {
                RecLanguage rec=(RecLanguage)en.nextElement();
                if(!rec.getLang().equals(lang))
                {
                    ret.add(new Locale(rec.getLang()));
                }
            }
            return ret.elements();
        }
        */
        return super.getLocales();
    }
    
}
