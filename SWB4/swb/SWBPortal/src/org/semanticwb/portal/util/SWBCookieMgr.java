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
package org.semanticwb.portal.util;

import java.util.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;


// TODO: Auto-generated Javadoc
/** Esta clase adminsitra las cookies de los usuarios del portal.
 *
 * This class manages user's cookies.
 * @author : Jorge Alberto Jim�nez
 */
public class SWBCookieMgr
{
    
    /** The cookies. */
    ArrayList cookies;

    /**
     * Instantiates a new sWB cookie mgr.
     */
    public SWBCookieMgr()
    {
        cookies = new ArrayList();
    }

    
    /**
     * Adds the cookie.
     * 
     * @param cookie the cookie
     */
    public void addCookie(SWBCookie cookie)
    {
        cookies.add(cookie);
    }

    /**
     * Sets the cookie.
     * 
     * @param cadenaCookie the cadena cookie
     * @param host the host
     * @param resid the resid
     * @return true, if successful
     * @return
     */
    public boolean setCookie(String cadenaCookie, String host, String resid)
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
                if (cookie.getName().equalsIgnoreCase(CookName) && cookie.getResID().equals(resid) && (host==null || host.equalsIgnoreCase(cookie.getHost())))
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
     * Hace un parser de un String separando las cookies
     * name1=value1; name2=value2.
     * 
     * @param headercookies the headercookies
     * @param host the host
     * @param resid the resid
     */
    public void addCookies(String headercookies, String host, String resid)
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
         * Gets the wBSSO cookies.
         * 
         * @return the wBSSO cookies
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
     * Gets the cookie.
     * 
     * @param host the host
     * @param path the path
     * @param dfecha the dfecha
     * @param resid the resid
     * @return the cookie
     * @return
     */
    public String getCookie(String host, String path, Date dfecha, String resid)
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
                String rid = WBC.getResID();
                //Date dWBCexpires=new Date(WBC.getExpires());
                boolean bpath = false,bpath1 = false;
                if ((host.endsWith(WBChost) && resid.equals(rid) )|| (WBChost.equals("[*]") && rid.equals("")) )
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

    /**
     * Have instance cookies.
     * 
     * @param resid the resid
     * @return true, if successful
     */
    public boolean haveInstanceCookies(String resid)
    {
        Iterator itkeycokie = cookies.iterator();
        while (itkeycokie.hasNext())
        {
            SWBCookie WBC = (SWBCookie) itkeycokie.next();
            if (WBC != null)
            {
                if(resid.equals(WBC.getResID()))
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Removes the instance cookies.
     * 
     * @param resid the resid
     */
    public void removeInstanceCookies(String resid)
    {
        Iterator itkeycokie = cookies.iterator();
        while (itkeycokie.hasNext())
        {
            SWBCookie WBC = (SWBCookie) itkeycokie.next();
            if (WBC != null)
            {
                if(resid.equals(WBC.getResID()))
                {
                    itkeycokie.remove();
                }
            }
        }
    }
    
    
    /**
     * Clase que actua como utileria para busqueda,grabado y borrado
     * de cookies.
     * 
     * @param sName :<PRE>Este es el nombre de la cookie que este metodo debera
     * buscar en el sistema</PRE>
     * @param request :<PRE>request que recibe para buscar la cookie de acuerdo
     * al dominio del mismo</PRE>
     * @return :regresa el valor de la cookie en caso
     * de haberla encontrado, de lo contrario regresa nulo
     */
    public String SearchCookie(String sName, HttpServletRequest request)
    {
        String name = null;
        String valor = null;
        String Cvalue = null;
        String ena = SWBPlatform.getEnv("wb/CookEnabled");
        if (ena == null) {
            ena = "true";
        }
        if (ena.equalsIgnoreCase("true"))
        {
            Cookie cooks[] = request.getCookies();
            if (cooks != null)
            {
                for (int icook = 0; icook < cooks.length; icook++)
                {
                    Cookie cook = cooks[icook];
                    name = cook.getName();
                    valor = cook.getValue();
                    if (name.equalsIgnoreCase(sName))
                    {
                        Cvalue = valor;
                        icook = cooks.length;
                    }
                }
            }
        }
        return Cvalue;
    }
    
    
    /**
     * Adds the cookie.
     * 
     * @param cookieName the cookie name
     * @param cookieValue the cookie value
     * @param save the save
     * @param cookieEnable the cookie enable
     * @param request the request
     * @param response the response
     */
    public void AddCookie(String cookieName, String cookieValue, boolean save, boolean cookieEnable,HttpServletRequest request, HttpServletResponse response)
    {
        String ena=null;
        if(!cookieEnable){
            ena = SWBPlatform.getEnv("wb/CookEnabled");
        }
        if (ena == null){ 
            ena = "true";
        }
        if (ena.equalsIgnoreCase("true"))
        {
            Cookie cneecook = new Cookie(cookieName, cookieValue);
            if (save)
            {
                cneecook.setMaxAge(60 * 60 * 365);
            } else
            {
                cneecook.setMaxAge(-1);
            }
            cneecook.setPath("/");
            response.addCookie(cneecook);
        }
    }
    
    
    /**
     * metodo que se encarga de grabar y borrar una cookie.
     * 
     * @param cookieName :<PRE>Este es el nombre de la cookie que grabara o
     * borrara este metodo</PRE>
     * @param cookieValue :<PRE>Este es el valor de la cookie que sera grabada o borrada</PRE>
     * @param save :<PRE>si este parametro es true graba la cookie, de lo
     * contrario la borra</PRE>
     * @param request :<PRE>Este request es para grabar la cookie</PRE>
     * @param response :<PRE>Este response es para borrar la cookie</PRE>
     */
    public void AddCookie(String cookieName, String cookieValue, boolean save, HttpServletRequest request, HttpServletResponse response)
    {
        AddCookie(cookieName,cookieValue,save,false,request,response);
    }
    
    
}
