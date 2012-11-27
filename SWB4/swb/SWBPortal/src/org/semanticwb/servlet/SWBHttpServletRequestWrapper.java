/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.servlet;

import java.util.Collections;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletRequest;

import java.util.Hashtable;

import java.util.Locale;
import java.util.Vector;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBHttpServletRequestWrapper.
 * 
 * @author Javier Solis Gonzalez
 */
public class SWBHttpServletRequestWrapper extends HttpServletRequestWrapper
{
    
    /** The remove parameters. */
    private boolean removeParameters=false;
    
    /** The merge parameters. */
    private boolean mergeParameters=false;
    
    /** The parms. */
    private Hashtable parms = new Hashtable();    
    
    /** The lang. */
    private String lang;
    
    /** The idtm. */
    private String idtm;
    
    /**
     * Creates a new instance of WBHttpServletRequestWrapper.
     * 
     * @param request the request
     */
    public SWBHttpServletRequestWrapper(HttpServletRequest request)
    {
        super(request);
    }
    
    /**
     * Creates a new instance of WBHttpServletRequestWrapper.
     * 
     * @param request the request
     * @param removeParameters the remove parameters
     */
    public SWBHttpServletRequestWrapper(HttpServletRequest request, boolean removeParameters)
    {
        super(request);
        this.removeParameters=removeParameters;
    }
    
    /**
     * Instantiates a new sWB http servlet request wrapper.
     * 
     * @param request the request
     * @param lang the lang
     * @param idtm the idtm
     * @param removeParameters the remove parameters
     */
    public SWBHttpServletRequestWrapper(HttpServletRequest request, String lang, String idtm, boolean removeParameters)
    {
        this(request, lang, idtm, removeParameters,false);
    }    
    
    /**
     * Instantiates a new sWB http servlet request wrapper.
     * 
     * @param request the request
     * @param lang the lang
     * @param idtm the idtm
     * @param removeParameters the remove parameters
     * @param mergeParameters the merge parameters
     */
    public SWBHttpServletRequestWrapper(HttpServletRequest request, String lang, String idtm, boolean removeParameters, boolean mergeParameters)
    {
        super(request);
        this.removeParameters=removeParameters;
        this.mergeParameters=mergeParameters;
        this.lang=lang;
        this.idtm=idtm;
    }
    
    
    /* (non-Javadoc)
     * @see javax.servlet.ServletRequestWrapper#getParameter(java.lang.String)
     */
    /**
     * Gets the parameter.
     * 
     * @param str the str
     * @return the parameter
     */
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

    /* (non-Javadoc)
     * @see javax.servlet.ServletRequestWrapper#getParameterMap()
     */
    /**
     * Gets the parameter map.
     * 
     * @return the parameter map
     */
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

    /* (non-Javadoc)
     * @see javax.servlet.ServletRequestWrapper#getParameterNames()
     */
    /**
     * Gets the parameter names.
     * 
     * @return the parameter names
     */
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

    /* (non-Javadoc)
     * @see javax.servlet.ServletRequestWrapper#getParameterValues(java.lang.String)
     */
    /**
     * Gets the parameter values.
     * 
     * @param str the str
     * @return the parameter values
     */
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
    
    /**
     * Adds the parameter map.
     * 
     * @param map the map
     */
    public void addParameterMap(java.util.Map map)
    {
        if(removeParameters || mergeParameters)
        {
            parms.putAll(map);
        }
    }
    
    /**
     * Adds the parameter.
     * 
     * @param name the name
     * @param value the value
     */
    public void addParameter(String name, String value)
    {
        if(name==null)return;
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
    
    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletRequestWrapper#getQueryString()
     */
    /**
     * Gets the query string.
     * 
     * @return the query string
     */
    @Override
    public String getQueryString()
    {
        if(removeParameters)
        {
            return null;    
        }else
            return super.getQueryString();
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletRequestWrapper#getReader()
     */
    /**
     * Gets the reader.
     * 
     * @return the reader
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public java.io.BufferedReader getReader() throws java.io.IOException
    {
        if(removeParameters)
        {
            return null;
        }else
            return super.getReader();
    }    
    
    /* (non-Javadoc)
     * @see javax.servlet.ServletRequestWrapper#getContentLength()
     */
    /**
     * Gets the content length.
     * 
     * @return the content length
     */
    @Override
    public int getContentLength()
    {
        if(removeParameters)
            return 0;
        else
            return super.getContentLength();
    }    
    
    /* (non-Javadoc)
     * @see javax.servlet.ServletRequestWrapper#getInputStream()
     */
    /**
     * Gets the input stream.
     * 
     * @return the input stream
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public javax.servlet.ServletInputStream getInputStream() throws java.io.IOException
    {
        if(removeParameters)
            return null;
        else
            return super.getInputStream();
    }    
    
    /* (non-Javadoc)
     * @see javax.servlet.ServletRequestWrapper#getLocale()
     */
    /**
     * Gets the locale.
     * 
     * @return the locale
     */
    @Override
    public java.util.Locale getLocale()
    {
        if(lang!=null)return new Locale(lang);
        return super.getLocale();
    }
    
    /* (non-Javadoc)
     * @see javax.servlet.ServletRequestWrapper#getLocales()
     */
    /**
     * Gets the locales.
     * 
     * @return the locales
     */
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
