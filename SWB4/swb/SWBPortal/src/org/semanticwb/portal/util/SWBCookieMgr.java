/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */


package org.semanticwb.portal.util;

import java.util.*;


/** Esta clase adminsitra las cookies de los usuarios del portal.
 *
 * This class manages user's cookies.
 * @author : Jorge Alberto Jim�nez
 */
public class SWBCookieMgr
{
    ArrayList cookies;

    public SWBCookieMgr()
    {
        cookies = new ArrayList();
    }

    
    public void addCookie(SWBCookie cookie)
    {
        cookies.add(cookie);
    }

    /**
     * @param cadenaCookie
     * @param host
     * @param resid
     * @return
     */
    public boolean setCookie(String cadenaCookie, String host, long resid)
    {
        //System.out.println("setCookie:"+cadenaCookie+" "+host+" "+resid);
        boolean regresa = false,bexistcookie = false;
        if (cadenaCookie != null)
        {
            String CookName = null;
            int pos = -1;
            pos = cadenaCookie.indexOf("=");
            if (pos != -1)
            {
                CookName = cadenaCookie.substring(0, pos);
            }
            //System.out.println("NCookie:"+CookName);

            SWBCookie cookie = null;
            Iterator itcookies = cookies.iterator();
            while (itcookies.hasNext())
            {
                cookie = (SWBCookie) itcookies.next();
                if (cookie.getName().equalsIgnoreCase(CookName) && cookie.getResID() == resid && (host==null || host.equalsIgnoreCase(cookie.getHost())))
                {
                    //System.out.println("Ya existe el objeto Cookie");
                    bexistcookie = true;
                    break;
                }
            }
            if (!bexistcookie)
            {
                cookie = new SWBCookie();
                //System.out.println("Creo nuevo objeto Cookie");
            }

            if (host != null) cookie.setHost(host);
            cookie.setResID(resid);

            //System.out.println("cadenaCookie:"+cadenaCookie);
            
            StringTokenizer st = new StringTokenizer(cadenaCookie, ";");
            while (st.hasMoreElements())
            {
                String token = st.nextToken().trim();
                if (token == null)
                {
                    continue;
                }
                pos = -1;
                pos = token.indexOf("=");
                if(pos>-1)
                {
                    //System.out.println("token:"+token);
                    String aName = token.substring(0, pos);
                    String aValue = token.substring(pos + 1);
                    if (aName.equalsIgnoreCase("expires"))
                    {
                        cookie.setExpires(aValue);
                    } else if (aName.equalsIgnoreCase("path"))
                    {
                        cookie.setPath(aValue);
                    } else if (aName.equalsIgnoreCase("domain"))
                    {
                        cookie.setHost(aValue);
                    } else if (aName.equalsIgnoreCase(CookName))
                    {
                        cookie.setName(aName);
                        cookie.setValue(aValue);
                    }
                }

            }

            if (CookName != null && !bexistcookie)
            {
                //System.out.println("AddCookie:"+cookie);
                cookies.add(cookie);
                regresa = true;
            }
        }
        return regresa;
    }
    
    
    /**
     *  Hace un parser de un String separando las cookies
     *  name1=value1; name2=value2
     */
    public void addCookies(String headercookies, String host, long resid)
    {
        StringTokenizer st=new StringTokenizer(headercookies,";");
        while(st.hasMoreTokens())
        {
            String token=st.nextToken();
            int pos = token.indexOf("=");
            String name=null;
            String value=null;
            if (pos != -1)
            {
                name=token.substring(0, pos);
                value=token.substring(pos+1);
            }
            if(name!=null && value!=null)
            {
                SWBCookie wbc=new SWBCookie(name.trim(),value.trim(),null,"/",host,resid);
                cookies.add(wbc);
            }
        }
            
    }
        

    
        /**
     * @param host
     * @param path
     * @param dfecha
     * @return
     */
    public Iterator getWBSSOCookies()
    {
        String startName="_wb_sso";
        ArrayList ret=new ArrayList();
        Iterator itkeycokie = cookies.iterator();
        while (itkeycokie.hasNext())
        {
            SWBCookie WBC = (SWBCookie) itkeycokie.next();
            if (WBC != null)
            {    
                String name = WBC.getName();
                if(name.startsWith(startName))
                {
                    ret.add(WBC);
                }
            }
        }
        return ret.iterator();
    }

    /**
     * @param host
     * @param path
     * @param dfecha
     * @return
     */
    public String getCookie(String host, String path, Date dfecha, long resid)
    {
        StringBuffer SBcookie = new StringBuffer();
        boolean entry = false;

        Iterator itkeycokie = cookies.iterator();
        while (itkeycokie.hasNext())
        {
            SWBCookie WBC = (SWBCookie) itkeycokie.next();
            if (WBC != null)
            {    //Checa si el objeto WBCookie coincide con los datos enviados
                String WBChost = WBC.getHost();
                String WBCpath = WBC.getPath();
                long rid = WBC.getResID();
                //Date dWBCexpires=new Date(WBC.getExpires());
                boolean bpath = false,bpath1 = false;
                if ((host.endsWith(WBChost) && resid==rid )|| (WBChost.equals("[*]") && rid==0) )
                {
                    if (!WBCpath.equals("/"))
                    {
                        if (path.startsWith(WBCpath))
                        {
                            bpath = true;
                        }
                    } else
                        bpath1 = true;

                    if (bpath1 || bpath)
                    {
                        if (entry) SBcookie.append("; ");
                        SBcookie.append(WBC.getName() + "=" + WBC.getValue());
                        entry = true;
                    }
                }
            }
        }
        return SBcookie.toString();
    }

    public boolean haveInstanceCookies(long resid)
    {
        Iterator itkeycokie = cookies.iterator();
        while (itkeycokie.hasNext())
        {
            SWBCookie WBC = (SWBCookie) itkeycokie.next();
            if (WBC != null)
            {
                if(resid==WBC.getResID())
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void removeInstanceCookies(long resid)
    {
        Iterator itkeycokie = cookies.iterator();
        while (itkeycokie.hasNext())
        {
            SWBCookie WBC = (SWBCookie) itkeycokie.next();
            if (WBC != null)
            {
                if(resid==WBC.getResID())
                {
                    itkeycokie.remove();
                }
            }
        }
    }

}