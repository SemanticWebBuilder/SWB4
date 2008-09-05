package org.semanticwb.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.base.*;
import org.semanticwb.platform.SemanticObject;

public class WebPage extends WebPageBase 
{
    private String siteid=null;
     
    public WebPage(SemanticObject base)
    {
        super(base);
    }
    
    public String getWebSiteId()
    {
        if(siteid==null)
        {
            siteid=getWebSite().getId();
        }
        return siteid;
    }     
    
    /**  Regresa el Url de la pagina
     *  Ejemplo: /wb2/jei/home
     * @return String
     */
    public String getRealUrl()
    {
        return SWBPlatform.getContextPath() + SWBPlatform.getEnv("swb/distributor","swb") + "/" + getWebSiteId() + "/" + getId();
    }    
    
    /**  Regresa el Url del topico
     *  Ejemplo: /wb2/jei/home
     * @return String
     */
    public String getUrl()
    {
//        if(this.getSubjectIdentity()!=null)
//        {
//            String url=this.getSubjectIdentity().getResourceRef();
//            if(url!=null)
//            {
//                if(url.startsWith(WBVirtualHostMgr.PREF_FURL))
//                {
//                    url=url.substring(WBVirtualHostMgr.PREF_FURL.length());
//                    if(url.startsWith("/"))url=url.substring(1);
//                    url=WBUtils.getInstance().getWebPath()+url;
//                }else
//                {
//                    Iterator it=paths.keySet().iterator();
//                    while(it.hasNext())
//                    {
//                        String key=(String)it.next();
//                        int i=url.indexOf(key);
//                        if(i>=0)
//                        {
//                            url=url.substring(0,i)+paths.get(key)+url.substring(i+key.length());
//                        }
//                    }
//                }
//                return url;
//            }
//        }
        return getRealUrl();
    }
    
    /** Regresa un representacion en html de la ruta de navegacion el topico.
     * @return String en formato html
     */
    public String getPath()
    {
        return getPath(new HashMap());
    }

    /** Obtiene la ruta de navegacion de la seccion
     * parametros:
     *      -   separator: caracter que se utilizara como separador.
     *      -   cssclass: nombre de la clase definida en el archivo CSS para utilizar en las ligas de la ruta.
     *      -   selectcolor: color de la seccion seleccionada.
     *      -   links: true/false, si tendra links en la ruta.
     *      -   language: lenguaje en el que se quiere presentar la ruta.
     *      -   home: Identificador de la sección home (si se quiere definir un home
     *          diferente al de defecto).
     *      -   target: Definicion del frame destino.
     *          diferente al de defecto).
     *      -   hiddentopics: identificadores de topicos seperados por comas que se quiere que se despliegen en la ruta.
     * @param args HashMap con los parametros del template
     * @return String de la ruta.
     */
    public String getPath(HashMap args)
    {
        //AFUtils.log("entra a nuevo topic getPath george...",true);
        StringBuffer ret = new StringBuffer();
        String separator = (String) args.get("separator");
        String cssclass = (String) args.get("cssclass");
        String tpacssclass = (String) args.get("tpacssclass");
        String selectcolor = (String) args.get("selectcolor");
        String links = (String) args.get("links");
        String language = (String) args.get("language");
        String home = (String) args.get("home");
        String active = (String) args.get("active");
        String target = (String) args.get("target");
        String hiddentopics=(String)args.get("hiddentopics");

        if (separator == null) separator = "";
        if (cssclass == null) cssclass = ""; else cssclass = "class=\"" + cssclass + "\"";
        if (selectcolor == null) selectcolor = ""; else selectcolor = "color=\"" + selectcolor + "\"";
        if (links == null) links = "true";
        if(target==null)target="";
        else target="target=\""+target+"\"";

        //Hidden Topics
        ArrayList hd=null;
        if(hiddentopics!=null)
        {
            hd=new ArrayList();
            StringTokenizer st=new StringTokenizer(hiddentopics," ,|;&:");
            while(st.hasMoreTokens())
            {
                WebPage tp=getWebSite().getWebPage(st.nextToken());
                if(tp!=null)
                {
                    hd.add(tp);
                }
            }
        }

        WebPage tphome = null;
        if (home != null)
        {
            tphome = getWebSite().getWebPage(home);
            if (tphome == null) tphome = getWebSite().getHomePage();
        } else
            tphome = getWebSite().getHomePage();

        if (tpacssclass!=null && tpacssclass.length() > 0) ret.append("<span " + tpacssclass + ">");
        else if (cssclass != null && cssclass.length() > 0) ret.append("<span " + cssclass + ">");
        if (selectcolor != null && selectcolor.length() > 0) ret.append("<font " + selectcolor + ">");
        ret.append(this.getTitle(language));
        if (selectcolor != null && selectcolor.length() > 0) ret.append("</font>");
        if ((tpacssclass!=null && tpacssclass.length() > 0) || (cssclass != null && cssclass.length() > 0)) ret.append("</span>");
            
        if (tphome != this)
        {
            ArrayList arr = new ArrayList();
            WebPage tp=this.getParent();
            while (tp!=null)
            {
                if(hd==null || (hd!=null && !hd.contains(tp)))
                {
                    if (links.equals("true") && tp.isActive())
                    {
                        ret.insert(0, separator);
                        ret.insert(0, "<a href=\"" + tp.getUrl() + "\" " + cssclass + " "+target+">" + tp.getTitle(language) + "</a>");
                    }
                    else if(active==null || (tp.isActive() && active!=null && !active.toLowerCase().trim().equals("true")))
                    {
                        ret.insert(0, separator);
                        ret.insert(0, tp.getTitle(language));
                    }
                }
                if (arr.contains(tp)) break;
                arr.add(tp);
                tp = tp.getParent();
                if (tphome == tp) break;
            }
        }
        return ret.toString();
    }    
    
    public boolean isVisible()
    {
        return isActive() && !isDeleted() && !isHidden();
    }
    
    /**  Regresa el Url del topico
     *  Ejemplo: /wb2/jei/home
     * @return String
     */
    public String getUrl(WebPage virtualtopic)
    {
        if(virtualtopic==null)return getUrl();
        String ret=getRealUrl();
        ret+="/"+"_vtp";                    //DistributorParams.URLP_VTOPIC;
        ret+="/" + virtualtopic.getWebSiteId() + "/" + virtualtopic.getId();
        return ret;
    }    
    
    /** Regresa el nivel de profundidad del topico con respecto a la seccion home.
     * @return int, nivel de profundidad.
     */
    public int getLevel()
    {
        int ret = 0;
        WebPage home=getWebSite().getHomePage();
        if (!home.equals(this))
        {
            ArrayList arr = new ArrayList();
            WebPage tp = this.getParent();
            while (tp!=null)
            {
                ret++;
                if (arr.contains(tp)) break;
                arr.add(tp);
                tp = tp.getParent();
                if (home.equals(tp)) break;
            }
        }
        return ret;
    }    
    
    /** indica si el topico es hijo de otro topico.
     * @param topic Topic
     * @return boolean
     */
    public boolean isChildof(WebPage page)
    {
        ArrayList<WebPage> arr = new ArrayList();
        WebPage tp = this.getParent();
        while (tp!=null)
        {
            if (tp.equals(page)) return true;
            if (arr.contains(tp)) return false;
            arr.add(tp);
            tp=tp.getParent();
        }
        return false;
    }

    /** indica si el topico es padre de otro topico.
     * @param topic Topci
     * @return bollean
     */
    public boolean isParentof(WebPage page)
    {
        return page.isChildof(this);
    }    
    
    /** Regresa el nombre por defecto.
     * @return String
     * @deprecated Usar getTitle()
     */
    @Deprecated
    public String getDisplayName()
    {
        return getTitle();
    }    
    
    /** Regresa el nombre por defecto, en base a un idioma.
     * @param String Idioma
     * @return String
     * @deprecated 
     */
    @Deprecated
    public String getDisplayName(String lang)
    {
        return getTitle(lang);
    }    

    /** Regresa el nombre por defecto, en base a un idioma que recibe como parametro
     * con identificador "<B>language</B>".
     *
     * Ejemplo:
     *    HashMap arg=new HashMap();
     *    args.pur("language","es");
     *    String name=topic.getDisplayName(args);
     *
     * Este metodo normalmente se utiliza en templates.
     * parametros:
     *      -   languege: idioma de despliege (ejemplo es, en, fr).
     * @return String
     * @param args HashMap, con paraetros del template
     * ejemplo:
     *    language=es
     * @deprecated 
     */
    @Deprecated
    public String getDisplayName(HashMap args)
    {
        Language language = (Language) args.get("language");
        return getTitle(language.getId());
    }    
    
}
