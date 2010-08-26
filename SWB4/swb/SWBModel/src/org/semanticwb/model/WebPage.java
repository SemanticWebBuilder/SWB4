/**  
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
**/ 
 
package org.semanticwb.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeSet;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.base.*;
import org.semanticwb.platform.SemanticObject;

// TODO: Auto-generated Javadoc
/**
 * The Class WebPage.
 */
public class WebPage extends WebPageBase 
{
    
    /** The log. */
    private final static Logger log=SWBUtils.getLogger(WebPage.class);

    /** The siteid. */
    private String siteid=null;
    
    /** The realurl. */
    private String realurl=null;

    /** The views. */
    private long views=-1;
    
    /** The timer. */
    private long timer;                     //valores de sincronizacion de views, hits
    
    /** The time. */
    private static long time;                      //tiempo en milisegundos por cada actualizacion
    
    /** The viewed. */
    private boolean viewed = false;
    
    
    static
    {
        time = 600000L;
        try
        {
            time = 1000L * Long.parseLong((String) SWBPlatform.getEnv("swb/accessLogTime","600"));
        } catch (Exception e)
        {
            log.error("Error to read accessLogTime...",e);
        }
    }

    /**
     * Instantiates a new web page.
     * 
     * @param base the base
     */
    public WebPage(SemanticObject base)
    {
        super(base);
        timer = System.currentTimeMillis();
    }
    
    /**
     * Gets the web site id.
     * 
     * @return the web site id
     */
    public String getWebSiteId()
    {
        if(siteid==null)
        {
            siteid=getWebSite().getId();
        }
        return siteid;
    }

    /**
     * Regresa el Url de la pagina
     * Ejemplo: /wb2/jei/home.
     * 
     * @return String
     */
    public String getRealUrl()
    {
        String ret=null;
        User user=SWBContext.getSessionUser(getWebSite().getUserRepository().getId());
        if(user!=null)
        {
            ret=getRealUrl(user.getLanguage());
        }else
        {
            ret=getRealUrl(null);
        }
        return ret;
    }

    /**
     * Regresa el Url de la pagina
     * Ejemplo: /wb2/jei/home.
     * 
     * @param lang the lang
     * @return String
     */
    public String getRealUrl(String lang)
    {
        if(realurl==null)
        {
            realurl="/" + getWebSiteId() + "/" + getId();
        }
        return SWBPlatform.getContextPath() + "/" + (lang==null?SWBPlatform.getEnv("swb/distributor","swb"):lang) + realurl;
    }    

    /**
     * Regresa el Url de la pagina
     * Ejemplo: /swb/jei/home.
     * 
     * @return String
     */
    public String getUrl()
    {
        return getUrl((String)null);
    }
    
    /**
     * Regresa el Url de la pagina
     * Ejemplo: /swb/jei/home.
     * 
     * @param lang the lang
     * @return String
     */
    public String getUrl(String lang)
    {
        String url=getWebPageURL();
        if(url!=null)
        {
            if(getWebPageURLType()==2)  //Friendly
            {
                url=SWBPlatform.getContextPath()+url;
            }else if(getWebPageURLType()==1) //Redirect
            {
                if(url.startsWith("//"))
                {
                    url=url.substring(1);
                }else
                {
                    url=SWBPlatform.getContextPath()+url;
                }
//                Iterator it=paths.keySet().iterator();
//                while(it.hasNext())
//                {
//                    String key=(String)it.next();
//                    int i=url.indexOf(key);
//                    if(i>=0)
//                    {
//                        url=url.substring(0,i)+paths.get(key)+url.substring(i+key.length());
//                    }
//                }
            }
            return url;
        }
        return getRealUrl();
    }
    
    /** Regresa una representacion en html de la ruta de navegacion de la pagina.
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
        StringBuilder ret = new StringBuilder();
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

        if (separator == null)
        {
            separator = "";
        }
        if (cssclass == null)
        {
            cssclass = "";
        } else
        {
            cssclass = "class=\"" + cssclass + "\"";
        }
        if (selectcolor == null)
        {
            selectcolor = "";
        } else
        {
            selectcolor = "color=\"" + selectcolor + "\"";
        }
        if (links == null)
        {
            links = "true";
        }
        if(target==null)
        {
            target="";
        }
        else
        {
            target="target=\""+target+"\"";
        }

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
            if (tphome == null)
            {
                tphome = getWebSite().getHomePage();
            }
        } else
        {
            tphome = getWebSite().getHomePage();
        }

        if (tpacssclass!=null && tpacssclass.length() > 0)
        {
            ret.append("<span ").append(tpacssclass).append(">");
        }
        else if (cssclass != null && cssclass.length() > 0)
        {
            ret.append("<span ").append(cssclass).append(">");
        }
        if (selectcolor != null && selectcolor.length() > 0)
        {
            ret.append("<font ").append(selectcolor).append(">");
        }
        ret.append(this.getDisplayName(language));
        if (selectcolor != null && selectcolor.length() > 0)
        {
            ret.append("</font>");
        }
        if ((tpacssclass!=null && tpacssclass.length() > 0) || (cssclass != null && cssclass.length() > 0))
        {
            ret.append("</span>");
        }
            
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
                        ret.insert(0, "<a href=\"" + tp.getUrl() + "\" " + cssclass + " "+target+">" + tp.getDisplayName(language) + "</a>");
                    }
                    else if(active==null || (tp.isActive() && active!=null && !active.toLowerCase().trim().equals("true")))
                    {
                        ret.insert(0, separator);
                        ret.insert(0, tp.getDisplayName(language));
                    }
                }
                if (arr.contains(tp))
                {
                    break;
                }
                arr.add(tp);
                if (tphome == tp)
                {
                    break;
                }
                tp = tp.getParent();
            }
        }
        return ret.toString();
    }    
    
    /**
     * Checks if is visible.
     * 
     * @return true, if is visible
     */
    public boolean isVisible()
    {
        return isValid() && !isHidden();
    }
    
    /**
     * Regresa el Url de la pagina
     * Ejemplo: /swb/jei/home.
     * 
     * @param virtualtopic the virtualtopic
     * @return String
     */
    public String getUrl(WebPage virtualtopic)
    {
        if(virtualtopic==null)
        {
            return getUrl();
        }
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
                if (home.equals(tp))
                {
                    break;
                }
                if (arr.contains(tp))
                {
                    break;
                }
                arr.add(tp);
                tp = tp.getParent();
            }
        }
        return ret;
    }    
    
    /**
     * indica si el topico es hijo de otro topico.
     * 
     * @param page the page
     * @return boolean
     */
    public boolean isChildof(WebPage page)
    {
        boolean ret=false;
        ArrayList<WebPage> arr = new ArrayList();
        WebPage tp = this.getParent();
        while (tp!=null)
        {
            if (tp.equals(page))
            {
                ret=true;
                break;
            }
            if (arr.contains(tp))
            {
                break;
            }
            arr.add(tp);
            tp=tp.getParent();
        }
        return ret;
    }

    /**
     * indica si el topico es padre de otro topico.
     * 
     * @param page the page
     * @return bollean
     */
    public boolean isParentof(WebPage page)
    {
        return page.isChildof(this);
    }

    /**
     * Indica si la pagina tienen alguna regla, rol o grupo de usuarios asignado o heredado.
     * 
     * @return true, if is filtered     
     */
    public boolean isFiltered()
    {
        boolean ret=false;
        ret=listInheritRoleRefs().hasNext();
        if(!ret)
        {
            ret=listInheritUserGroupRefs().hasNext();
        }
        if(!ret)
        {
            ret=listInheritRuleRefs().hasNext();
        }
        return ret;
    }
    
    /** Regresa el nombre por defecto.
     * @return String
     */
    public String getDisplayName()
    {
        return getDisplayName((String)null);
    }    
    
    /**
     * Regresa el nombre por defecto, en base a un idioma.
     * 
     * @param lang the lang
     * @return String
     */
    public String getDisplayName(String lang)
    {
        return getSemanticObject().getLocaleProperty(Descriptiveable.swb_title, lang);
    }

    /**
     * Regresa el nombre por defecto, en base a un idioma que recibe como parametro
     * con identificador "<B>language</B>".
     * 
     * Ejemplo:
     * HashMap arg=new HashMap();
     * args.pur("language","es");
     * String name=topic.getDisplayName(args);
     * 
     * Este metodo normalmente se utiliza en templates.
     * parametros:
     * -   languege: idioma de despliege (ejemplo es, en, fr).
     * 
     * @param args HashMap, con paraetros del template
     * ejemplo:
     * language=es
     * @return String
     */
    public String getDisplayName(HashMap args)
    {
        String language = (String) args.get("language");
        String id = (String) args.get("id");
        String pathLevel = (String) args.get("pathlevel");
        if(pathLevel!=null)
        {
            int level=0;
            try
            {
                level=Integer.parseInt(pathLevel);
            }catch(Exception e)
            {
                return getDisplayName(language);
            }
            ArrayList<WebPage> arr=new ArrayList();
            WebPage page=getParent();
            while(page!=null)
            {
                arr.add(page);
                if(page.equals(page.getWebSite().getHomePage()))break;
                page=page.getParent();
            }
            int i=arr.size()-1-level;
            if(i>=0)
            {
                return arr.get(i).getDisplayName(language);
            }else
            {
                return getDisplayName(language);
            }

        }else if(id!=null)
        {
            WebPage page=getWebSite().getWebPage(id);
            return page.getDisplayName(language);
        }else
        {
            return getDisplayName(language);
        }
    }    
    
    /**
     * Lista templates activos y no borrados, si no existen en la pagina regresa las del padre.
     * 
     * @return true, if is on schedule
     * @return
     * 
     * public Iterator<TemplateRef> listConfigTemplateRefs()
     * {
     * boolean inherit=true;
     * ArrayList<TemplateRef> ret=new ArrayList();
     * Iterator<TemplateRef> it=listTemplateRefs();
     * while(it.hasNext())
     * {
     * TemplateRef ref=it.next();
     * if(ref.isActive())
     * {
     * ret.add(ref);
     * }
     * }
     * if(inherit && ret.size()==0)
     * {
     * WebPage parent=getParent();
     * if(parent!=null)
     * {
     * return parent.listConfigTemplateRefs();
     * }
     * }
     * return ret.iterator();
     * }
     */

    public boolean isOnSchedule()
    {
        boolean ret=true;
        Iterator<CalendarRef> it=((CalendarRefable)this).listCalendarRefs();
        while(it.hasNext())
        {
            CalendarRef calref=it.next();
            Calendar cal=calref.getCalendar();
            if(cal!=null && cal.isActive() && calref.isActive() && !cal.isOnSchedule())
            {
                ret=false;
                break;
            }
        }
        return ret;
    }
    
    /**
     * List visible childs.
     * 
     * @param sortLang the sort lang
     * @return the iterator
     */
    public Iterator<WebPage> listVisibleChilds(String sortLang)
    {
        TreeSet set= new TreeSet(new SWBComparator(sortLang));
        Iterator<WebPage> it = listChilds();
        while (it.hasNext())
        {
            WebPage tp=it.next();
            if(tp.isVisible())
            {
                set.add(tp);
            }
        }
        it = listWebPageVirtualChilds();
        while (it.hasNext())
        {
            WebPage tp=it.next();
            if(tp.isVisible())
            {
                set.add(tp);
            }
        }
        return set.iterator();
    }

    /**
     * List childs.
     * 
     * @param sortLang the sort lang
     * @param active the active
     * @param deleted the deleted
     * @param hidden the hidden
     * @param onSchedule the on schedule
     * @return the iterator
     */
    public Iterator<WebPage> listChilds(String sortLang, Boolean active, Boolean deleted, Boolean hidden, Boolean onSchedule)
    {
        return listChilds(sortLang, active, deleted, hidden, onSchedule, true);
    }
    
    /**
     * List childs.
     * 
     * @param sortLang the sort lang
     * @param active the active
     * @param deleted the deleted
     * @param hidden the hidden
     * @param onSchedule the on schedule
     * @param incVirChilds the inc vir childs
     * @return the iterator
     */
    public Iterator<WebPage> listChilds(String sortLang, Boolean active, Boolean deleted, Boolean hidden, Boolean onSchedule, boolean incVirChilds)
    {
        TreeSet set= new TreeSet(new SWBComparator(sortLang));
        //set.addAll(getChild());
        Iterator<WebPage> it = listChilds();
        while (it.hasNext())
        {
            WebPage tp=it.next();
            if (active!=null && tp.isActive() != active)
            {
                continue;
            }
            if (deleted!=null && tp.isDeleted() != deleted)
            {
                continue;
            }
            if (hidden!=null && tp.isHidden() != hidden)
            {
                continue;
            }
            if (onSchedule!=null && tp.isOnSchedule() != onSchedule)
            {
                continue;
            }
            set.add(tp);
        }
        if(incVirChilds)
        {
            it = listWebPageVirtualChilds();
            while (it.hasNext())
            {
                WebPage tp=it.next();
                if (active!=null && tp.isActive() != active)
                {
                    continue;
                }
                if (deleted!=null && tp.isDeleted() != deleted)
                {
                    continue;
                }
                if (hidden!=null && tp.isHidden() != hidden)
                {
                    continue;
                }
                if (onSchedule!=null && tp.isOnSchedule() != onSchedule)
                {
                    continue;
                }
                set.add(tp);
            }
        }
        return set.iterator();        
    }
    
//    public void addHit(HttpServletRequest request, User user)
//    {
//        //TODO:
//    }
    
    /* (non-Javadoc)
 * @see org.semanticwb.model.base.WebPageBase#getDiskUsage()
 */
@Override
    public long getDiskUsage()
    {
        long ret=0;
        Iterator<Resource> it=listResources();
        while(it.hasNext())
        {
            Resource res=it.next();
            String work=SWBPlatform.createInstance().getPlatformWorkPath()+res.getWorkPath();
            ret+=SWBUtils.IO.getFileSize(work);
        }
        return ret/1000;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.WebPageBase#getViews()
     */
    @Override
    public long getViews()
    {
        if(views==-1)
        {
            views=super.getViews();
        }
        return views;
    }

    /**
     * Inc views.
     * 
     * @return true, if successful
     */
    public boolean incViews()
    {
        viewed = true;
        if(views==-1)
        {
            views=getViews();
        }
        views+=1;
        long t = System.currentTimeMillis() - timer;
        if (t > time || t < -time)
        {
            return true;
        }
        return false;
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.model.base.WebPageBase#setViews(long)
     */
    @Override
    public void setViews(long views)
    {
        super.setViews(views);
        this.views=views;
    }

    /**
     * Update views.
     */
    public void updateViews()
    {
        if(viewed)
        {
            timer = System.currentTimeMillis();
            setViews(views);
            viewed = false;
        }
    }


     /** Regresa string con el nombre del usuario que actualizo el primer contenido.
     * @return String
     */
    public String getContentsAuthor()
    {
        String ret = "";
        Resource resource=getLastContent();
        if(resource!=null)
        {
           if(resource.getModifiedBy()!=null && resource.getModifiedBy().getName()!=null && !resource.getModifiedBy().getName().trim().equals(""))
           {
                return resource.getModifiedBy().getFullName();
           }
           else if(resource.getCreator()!=null && resource.getCreator().getName()!=null)
           {
               return resource.getCreator().getFullName();
           }
        }
        return ret;
    }
    
    /**
     * Gets the contents last update.
     * 
     * @return the contents last update
     */
    public String getContentsLastUpdate()
    {
        return getContentsLastUpdate(null,null);
    }


    private Resource getLastContent(){
        Resource ret = null;
        Date auxt = null;
        Iterator<Resource> it = listResources();
        while (it.hasNext())
        {
            Resource recRes=it.next();
            if(recRes.isActive() && !recRes.isDeleted())
            {
                Date ts = recRes.getUpdated();
                if (auxt == null || auxt.before(ts))
                {
                    auxt = ts;
                    ret=recRes;
                }
            }
        }        
        return ret;
    }
    /**
     * Gets the contents last update.
     * 
     * @param lang the lang
     * @param format the format
     * @return the contents last update
     */
    public String getContentsLastUpdate(String lang, String format){
        String ret = "";        
        Resource resource=getLastContent();
        if(resource!=null)
        {
            if (lang==null && getWebSite().getLanguage() != null)
            {
                lang = getWebSite().getLanguage().getId();
            }
            if(format==null)
            {
                ret = SWBUtils.TEXT.getStrDate(resource.getUpdated(), lang);
            }else
            {
                ret = SWBUtils.TEXT.getStrDate(resource.getUpdated(), lang, format);
            }
        }
        return ret;
    }

    /**
     * Gets the contents last update.
     * 
     * @param args the args
     * @return the contents last update
     */
    public String getContentsLastUpdate(HashMap args)
    {
        String lang = (String) args.get("language");
        String format = (String) args.get("format");
        return getContentsLastUpdate(lang, format);
    }

    /**
     * Gets the contents meta.
     *
     * @param args the args
     * @return the contents last update
     */
    public String getContentMeta(HashMap args)
    {
        Resource resource=getLastContent();
        String lang = (String) args.get("language");
        String author=null;
        String description=null;
        String keywords = null;

        if(resource!=null && resource.getCreator()!=null)author=resource.getCreator().getFullName();
        author=(String) args.get("author");
        if(author==null)author=(String) args.get("author");

        if(resource!=null)description=resource.getDisplayDescription(lang);
        if(description==null)description=getDisplayDescription(lang);
        if(description==null)description=(String) args.get("description");

        if(resource!=null)keywords=resource.getTags();
        if(keywords==null)keywords=getTags();
        if(keywords==null)keywords=(String) args.get("keywords");

        StringBuffer ret=new StringBuffer();
        if(author!=null)ret.append("<meta name=\"Author\" content=\""+author+"\"/>\n");
        if(description!=null)ret.append("<meta name=\"description\" content=\""+description+"\"/>\n");
        if(keywords!=null)ret.append("<meta name=\"keywords\" content=\""+keywords+"\"/>\n");
        return ret.toString();
    }

}
