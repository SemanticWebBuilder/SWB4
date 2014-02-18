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
package org.semanticwb.servlet.internal;

import java.util.*;
import javax.servlet.http.*;
import java.net.URLDecoder;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.SFBase64;
import org.semanticwb.model.Country;
import org.semanticwb.model.Dns;
import org.semanticwb.model.IPFilter;
import org.semanticwb.model.Language;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.servlet.SWBHttpServletRequestWrapper;

// TODO: Auto-generated Javadoc
/**
 * Clase que lee parametros del request y se los envia al distribuidor para la realización de ciertas operaciones
 * <p>
 * Object that reads request parameters and send them to the wb distributor for some operations.
 * 
 * @author Javier Solis Gonzalez
 */
public class DistributorParams
{
    
    /** The log. */
    static Logger log=SWBUtils.getLogger(DistributorParams.class);
    
    /** The Constant ACC_TYPE_NONE. */
    public static final int ACC_TYPE_NONE=0;
    
    /** The Constant ACC_TYPE_RENDER. */
    public static final int ACC_TYPE_RENDER=1;
    
    /** The Constant ACC_TYPE_ACTION. */
    public static final int ACC_TYPE_ACTION=2;
    
    /** The Constant URLP_RENDERID. */
    public static final String URLP_RENDERID="_rid";
    
    /** The Constant URLP_TOPICMAPID. */
    public static final String URLP_TOPICMAPID="_idtm";
    
    /** The Constant URLP_NUMBERID. */
    public static final String URLP_NUMBERID="_nid";
    
    /** The Constant URLP_ACTIONID. */
    public static final String URLP_ACTIONID="_aid";
    
    /** The Constant URLP_METHOD. */
    public static final String URLP_METHOD="_mto";
    
    /** The Constant URLP_MODE. */
    public static final String URLP_MODE="_mod";
    
    /** The Constant URLP_WINSTATE. */
    public static final String URLP_WINSTATE="_wst";
    
    /** The Constant URLP_ACTION. */
    public static final String URLP_ACTION="_act";
    
    /** The Constant URLP_VTOPIC. */
    public static final String URLP_VTOPIC="_vtp";
    
    /** The Constant URLP_ONLYCONTENT. */
    public static final String URLP_ONLYCONTENT="_cnt";
    
    /** The Constant URLP_LANG. */
    public static final String URLP_LANG="_lang";
    
    /** The Constant URLP_DEVICE. */
    public static final String URLP_DEVICE="_devc";
    
    /** The Constant URLP_PARAMKEY. */
    public static final String URLP_PARAMKEY="_prk_";
    
    /** The Constant URLP_PARAMVALUE. */
    public static final String URLP_PARAMVALUE="_prv_";
    
    /** The Constant URLP_REMOTEURL. */
    public static final String URLP_REMOTEURL="_url";
    
    //String nombre = "wb2";
    
    /** The user. */
    private User user=null;
    
    /** The webpage. */
    private WebPage webpage=null;
    
    /** The virtwebpage. */
    private WebPage virtwebpage=null;
    
    /** The filtered. */
    private int filtered=-1;
    
    /** The URI params. */
    private ArrayList URIParams=null;
    
    /** The vmodel. */
    private String vmodel=null;
    
    /** The vwebpage. */
    private String vwebpage=null;
    
    /** The smodel. */
    private String smodel=null;
    
    /** The swebpage. */
    private String swebpage=null;
    
    /** The resparams. */
    private HashMap resparams=null;
    
    /** The ordresparams. */
    private ArrayList ordresparams=null;
    
    /** The adicparams. */
    private HashMap adicparams=null;    
    
    /** The acc resource. */
    private HashMap accResource=null;
    
    /** The acc resource id. */
    private String accResourceID=null;
    
    /** The acc type. */
    private int accType=0;
    
    /** The query string. */
    private String queryString=null;
    
    /** The internal query. */
    private HashMap internalQuery=null;
    
    /** The only content. */
    private boolean onlyContent=false;
    
    /** The lang. */
    private String lang=null;
    private String country=null;
    
    /** The device. */
    private String device=null;

    /**
     * Creates a new instance of DistributorParams.
     *
     * @param request the request
     * @param uri the uri
     * @param lang the lang
     */
    public DistributorParams(HttpServletRequest request, String uri, String lang)
    {
        this(request, uri, lang, null);
    }

    /**
     * Creates a new instance of DistributorParams.
     * 
     * @param request the request
     * @param uri the uri
     * @param lang the lang
     */
    public DistributorParams(HttpServletRequest request, String uri, String lang, String country)
    {
        this.lang=lang;
        this.country=country;
        init(request,uri);
    }
    
    /**
     * Creates a new instance of DistributorParams.
     * 
     * @param request the request
     * @param uri the uri
     */
    public DistributorParams(HttpServletRequest request, String uri)
    {
        init(request,uri);
    }
    
    /**
     * Creates a new instance of DistributorParams.
     * 
     * @param request the request
     */
    public DistributorParams(HttpServletRequest request)
    {
        String uri = request.getRequestURI();
        String cntx = request.getContextPath();
        String path = uri.substring(cntx.length());
        String iserv = "";

        if (path == null || path.length() == 0)
        {
            path = "/";
        } else
        {
            int j = path.indexOf('/', 1);
            if (j > 0)
            {
                iserv = path.substring(1, j);
            } else
            {
                iserv = path.substring(1);
            }
        }
        String auri = path.substring(iserv.length() + 1);
        
        //System.out.println("uri:"+uri);
        //System.out.println("cntx:"+cntx);
        //System.out.println("path:"+path);
        //System.out.println("iserv:"+iserv);
        //System.out.println("auri:"+auri);
        init(request, auri);
    }

    /**
     * Inits the.
     * 
     * @param request the request
     * @param uri the uri
     */
    private void init(HttpServletRequest request, String uri)
    {
        URIParams=_getURIParams(request, uri);
        //System.out.println("URIParams"+URIParams);
        webpage=_getWebPage(request);
        //System.out.println("webpage"+webpage);
        virtwebpage=_getVirtWebPage();
        //System.out.println("virtwebpage"+virtwebpage);
        if(webpage!=null)
        {
            filtered=_getFiltered(request);
        }else
        {
            log.warn("WebPage not Found:"+request.getRequestURI()+" Ref:"+request.getHeader("Referer"));
        }

        if(smodel==null)
        {
            user=_getUser(request,SWBContext.getGlobalWebSite());
        }else
        {
            if(webpage!=null)
            {
                user=_getUser(request,webpage.getWebSite());
            }else
            {
                WebSite site=SWBContext.getWebSite(smodel);
                if(site!=null)
                {
                    user = _getUser(request, site);
                }
                else
                {
                    user=_getUser(request,SWBContext.getGlobalWebSite());
                }
            }
        }

        SWBContext.setSessionUser(user);
        //System.out.println("user"+user);
        queryString=_getQueryString(request);
        //System.out.println("queryString:"+queryString);
    }
    
    /**
     * _get uri params.
     * 
     * @param request the request
     * @param uri the uri
     * @return the array list
     */
    private ArrayList _getURIParams(HttpServletRequest request, String uri)
    {
        ArrayList arr=new ArrayList();
//        String serv=request.getServletPath();
//        String acont=request.getContextPath();
        boolean allAditionals=false;
//        int lcont=0;
//        if(acont!=null)
//        {
//            lcont=acont.length();
//            if(lcont==1)lcont=0;
//        }
//        String uri=request.getRequestURI().substring(lcont+serv.length());
        
//        System.out.println("URI:"+request.getRequestURI());
//        System.out.println("serv:"+serv);
//        System.out.println("uri:"+uri);
//        System.out.println("context:"+request.getContextPath());
        
        if(uri.indexOf('?')>-1)uri=uri.substring(0,uri.indexOf('?'));
        StringTokenizer st = new StringTokenizer(uri, "/;");

        adicparams=new HashMap();
        ArrayList adicaux=new ArrayList();
        adicparams.put(Long.valueOf(0), adicaux);
        
        internalQuery=new HashMap();
        ArrayList intqaux=null;

        HashMap aux=null;
        int x=0;
        while(st.hasMoreTokens())
        {
            String tok=st.nextToken();
            //System.out.println("Token :"+x+"="+tok);
            arr.add(tok);
            
            if(x==0)smodel=tok;
            else if(x==1)swebpage=tok;
            else if(allAditionals)adicaux.add(tok);
            else if(tok.equals(URLP_NUMBERID) || tok.equals(URLP_RENDERID) || tok.equals(URLP_ACTIONID))
            {
                if(resparams==null)
                {
                    resparams=new HashMap();
                    ordresparams=new ArrayList();
                }
                if(st.hasMoreTokens())                
                {
                    String val=st.nextToken();
                    
                    try
                    {
                        //Long v=new Long(val);

                        aux=new HashMap();
                        aux.put(URLP_NUMBERID, val);
                        ordresparams.add(val);
                        resparams.put(val,aux);
                        //System.out.println("val:"+val+" aux:"+aux);

                        adicaux=new ArrayList();
                        adicparams.put(val,adicaux);  
                        
                        intqaux=new ArrayList();
                        internalQuery.put(val,intqaux);  

                        if(tok.equals(URLP_RENDERID))
                        {
                            accResource=aux;
                            accType=ACC_TYPE_RENDER;
                            accResourceID=val;
                        }else if(tok.equals(URLP_ACTIONID))
                        {
                            accResource=aux;
                            accType=ACC_TYPE_ACTION;
                            accResourceID=val;
                        }
                    }catch(NumberFormatException ex){log.error("Malformed URL:"+request.getRequestURI());}
                }
            }else if(tok.equals(URLP_TOPICMAPID) || tok.equals(URLP_METHOD) || tok.equals(URLP_MODE) || tok.equals(URLP_WINSTATE) || tok.equals(URLP_ACTION))
            {
                if(st.hasMoreTokens())
                {
                    String val=st.nextToken();
                    aux.put(tok, val);
                }
            }else if(tok.equals(URLP_VTOPIC))
            {
                if(st.hasMoreTokens())vmodel=st.nextToken();
                if(st.hasMoreTokens())vwebpage=st.nextToken();
            }else if(tok.startsWith(URLP_PARAMKEY))
            {
                String key=tok.substring(URLP_PARAMKEY.length());
                if(key!=null)
                {
                    byte b[]=SFBase64.decode(key);
                    if(b!=null)
                        intqaux.add(URLP_PARAMKEY+new String(b));
                }
                //System.out.println("key:"+new String(SFBase64.decode(key)));
            }else if(tok.startsWith(URLP_PARAMVALUE))
            {
                String val=tok.substring(URLP_PARAMVALUE.length());
                if(val!=null)
                {
                    byte b[]=SFBase64.decode(val);
                    if(b!=null)
                        intqaux.add(URLP_PARAMVALUE+new String(b));
                }
                //System.out.println("val:"+new String(SFBase64.decode(val)));
            }else if(tok.equals(URLP_ONLYCONTENT))
            {
                onlyContent=true;
            }else if(tok.equals(URLP_LANG))
            {
                if(st.hasMoreTokens())lang=st.nextToken();
            }else if(tok.equals(URLP_DEVICE))
            {
                if(st.hasMoreTokens())device=st.nextToken();
            }else
            {
                adicaux.add(tok);
                if(tok.equals(URLP_REMOTEURL))
                {
                    allAditionals=true;
                }
            }
            x++;
        }
        return arr;
    }
    
    /**
     * regresa un congunto de parametros relacionados con el id de un recurso.
     * 
     * @param resid the resid
     * @return the resource uri
     */
    public HashMap getResourceURI(String resid)
    {
        if(resparams==null)return null;
        return (HashMap)resparams.get(resid);
    }
    
    /**
     * Tiene parametros relacionados de los recursos.
     * 
     * @return true, if successful
     */
    public boolean haveResourcesURI()
    {
        if(ordresparams!=null)return true;
        else return false;
    }

    /**
     * regresa parametros de recursos ordernados.
     * 
     * @return the resources uri
     */
    public ArrayList getResourcesURI()
    {
        return ordresparams;
    }
    
    /*
     * regresa el valor de un parametro de un recurso.
     */
    /**
     * Gets the resource uri value.
     * 
     * @param resid the resid
     * @param param the param
     * @return the resource uri value
     */
    public String getResourceURIValue(String resid, String param)
    {
        HashMap map=(HashMap)resparams.get(resid);
        if(map!=null)
        {
            return (String)map.get(param);
        }
        return null;
    }
    
    /**
     * _get user.
     * 
     * @param request the request
     * @param site the site
     * @return the user
     */
    private User _getUser(HttpServletRequest request, WebSite site)
    {
        User user=SWBPortal.getUserMgr().getUser(request, site);
        //System.out.println("_getUser:"+site+" "+user);
        if(lang!=null)
        {
            if(user.getLanguage()==null || !user.getLanguage().equals(lang))
            {
                if(site.hasLanguage(lang))
                {
                    user.setLanguage(lang);
                }else
                {
                    Language l=site.getLanguage();
                    if(l==null)
                    {
                        Iterator<Language> i=SWBUtils.Collections.copyIterator(site.listLanguages()).iterator();
                        if(i.hasNext())l=i.next();
                    }
                    if(l!=null)user.setLanguage(l.getId());
                    //log.warn("Language not found:"+site.getId()+":"+lang);
                }
            }
        }
        if(country!=null)
        {
            if(user.getCountry()==null || !user.getCountry().equals(country))
            {
                if(site.hasCountry(country))
                {
                    user.setCountry(country);
                }else
                {
                    Country l=site.getCountry();
                    if(l==null)
                    {
                        Iterator<Country> i=SWBUtils.Collections.copyIterator(site.listCountries()).iterator();
                        if(i.hasNext())l=i.next();
                    }
                    if(l!=null)user.setCountry(l.getId());
                    //log.warn("Language not found:"+site.getId()+":"+lang);
                }
            }
        }        
        if (device!=null) 
        {
            user.setDevice(site.getDevice(device));
        }
        return user;        
    }

    /**
     * Gets the default webpage.
     * 
     * @param request the request
     * @return the default webpage
     */
    private WebPage getDefaultWebpage(HttpServletRequest request)
    {
        Dns dns=Dns.getDns(request.getServerName());
        
        //encomtrar sitio
        WebPage wp=null;
        if (dns != null)
        {
            wp=dns.getWebPage();
            if(wp==null)
            {
                wp=dns.getWebSite().getHomePage();
            }
            if(wp!=null && (!wp.isValid() || !wp.getWebSite().isValid()))wp=null;
        }

        if(wp==null)
        {
            Iterator<WebSite> it=SWBContext.listWebSites();
            while(it.hasNext())
            {
                WebSite site=it.next();
                if(!site.equals(SWBContext.getAdminWebSite())
                   && !site.equals(SWBContext.getGlobalWebSite()))
                {
                    if(site.isValid() && site.getHomePage()!=null && site.getHomePage().isValid())
                    {
                        wp=site.getHomePage();
                        break;
                    }
                }
            }
            if(wp==null)
            {
                wp=SWBContext.getAdminWebSite().getHomePage();
            }
        }
        return wp;
    }
    
    /**
     * _get web page.
     * 
     * @param request the request
     * @return the web page
     */
    private WebPage _getWebPage(HttpServletRequest request)
    {
        WebPage webpage=null;
        //System.out.println("request:"+request);
        //System.out.println("smodel:"+smodel);
        if(smodel!=null)
        {
            WebSite tm=SWBContext.getWebSite(smodel);
            if(tm!=null)
            {
                if(swebpage!=null)
                {
                    webpage=tm.getWebPage(swebpage);
                }else
                {
                    webpage=tm.getHomePage();
                }
            }else{
                WebPage wp=getDefaultWebpage(request);
                smodel = wp.getWebSite().getId();
            }
            //System.out.println("tm:"+tm);
        }else
        {
            webpage=getDefaultWebpage(request);
            smodel = webpage.getWebSite().getId();
            swebpage = webpage.getId();
        }
        //System.out.println("_getWebPage:"+webpage);
        return webpage;
    }
    
    /**
     * _get virt web page.
     * 
     * @return the web page
     */
    private WebPage _getVirtWebPage()
    {
        WebPage webpage=null;
        if(vmodel!=null && vwebpage!=null)
        {
            WebSite tm=SWBContext.getWebSite(vmodel);
            if(tm!=null)
            {
                webpage=tm.getWebPage(vwebpage);
            }
        }
        return webpage;
    }    
    
    /**
     * _get filtered.
     * 
     * @param request the request
     * @return the int
     */
    private int _getFiltered(HttpServletRequest request)
    {
        //TODO:
//        String ipuser = request.getRemoteAddr().toString();
//        Iterator it = DBCatalogs.getInstance().getIpFilters(webpage.getMap().getId()).values().iterator();
//        while (it.hasNext())
//        {
//            RecIpFilter ip = (RecIpFilter) it.next();
//            if (ipuser.indexOf(ip.getIp()) > -1)
//            {
//                return ip.getAction();
//            }
//        }

        //MAPS74
        int ret=-1;
        String ipuser = request.getRemoteAddr();
        Iterator<IPFilter> it = webpage.getWebSite().listIPFilters();
        while (it.hasNext())
        {
            IPFilter ip =  it.next();
            if(ip.isValid())
            {
                int action=ip.getAction();
                String ipn=ip.getIpNumber();
                if(action!=2)
                {
                    if (ipn!=null && (ipuser==null || ipuser.startsWith(ipn)))
                    {
                        ret=action;
                        break;
                    }
                }else
                {
                    if (ipn!=null  && (ipuser==null || ipuser.startsWith(ipn)))
                    {
                        ret=-1;
                        break;
                    }else
                    {
                        ret=action;
                    }
                }
            }
        }
        return ret;
    }
    
    /**
     * Gets the filtered.
     * 
     * @return the filtered
     */
    public int getFiltered()
    {
        return filtered;
    }
    
    /** Getter for property user.
     * @return Value of property user.
     *
     */
    public User getUser()
    {
        return user;
    }
    
    /** Getter for property webpage.
     * @return Value of property webpage.
     *
     */
    public WebPage getWebPage()
    {
        //TODO: Filtros de Sitios
//        if(webpage==null)return null;
//        if(webpage.getMap()==TopicMgr.getInstance().getAdminTopicMap())
//        {
//            return webpage;
//        }else
//        {
//            return AdmFilterMgr.getInstance().getTopicFiltered(webpage, user);
//        }
        return webpage;
    } 
    
    /**
     * Sets the web page.
     * 
     * @param webpage the new web page
     */
    public void setWebPage(WebPage webpage)
    {
        this.webpage=webpage;
    }
    
    /**
     * Gets the virt web page.
     * 
     * @return the virt web page
     */
    public WebPage getVirtWebPage()
    {
        //TODO: Filtros de Sitios        
//        if(webpage==null)return null;
//        if(webpage.getMap()==TopicMgr.getInstance().getAdminTopicMap())
//        {
//            return virtwebpage;
//        }else
//        {
//            return AdmFilterMgr.getInstance().getTopicFiltered(virtwebpage, user);
//        }
        return virtwebpage;
    } 
    
    /**
     * Regresa ArrayList de Strings con todos los parametros de URI
     * Ejemplo:
     * Se tiene este URI -> /wb/WBAdmin/home/_rid/15/_idtm/demo
     * el arreglo que retorna -> ["WBAdmin","home","_rid","15","_idtm","demo"].
     * 
     * @return the uRI params
     */
    public ArrayList getURIParams()
    {
        return URIParams;
    }

    /**
     * Regresa parametros adicionales o extendidos, no reconocidos en la ruta base.
     * 
     * @return the ext uri params
     */
    public ArrayList getExtURIParams()
    {
        return (ArrayList)adicparams.get(Long.valueOf(0));
    }
    
    /**
     * Regresa parametros adicionales o extendidos, no reconocidos como parametros del recurso.
     * 
     * @param res the res
     * @return the ext uri resource params
     */
    public ArrayList getExtURIResourceParams(String res)
    {
        return (ArrayList)adicparams.get(res);
    }    
    
    /** Getter for property accResource.
     * @return Value of property accResource.
     *
     */
    public java.util.HashMap getAccResource()
    {
        return accResource;
    }    
    
    /**
     * Gets the acc resource id.
     * 
     * @return the acc resource id
     */
    public String getAccResourceID()
    {
        return accResourceID;
    }
    
    /**
     * Regresa Iterador de Longs con identificadores de recursos pasados por URL.
     * 
     * @return the resource i ds
     */
    public Iterator getResourceIDs()
    {
        if(resparams==null)
        {
            return new ArrayList().iterator();
        }
        return resparams.keySet().iterator();
    }
    
    /**
     * Regresa String de los parametros agenos al identificador del recursos.
     * getNotAccResourceURI(86)
     * ejemplo:   /wb/Test/Test_testsuite/_rid/88/_mod/help/_nid/86/_mod/help
     * resultado: /_nid/88/_mod/help
     * 
     * @param resid the resid
     * @return the not acc resource uri
     */
    public String getNotAccResourceURI(String resid)
    {
        //new Exception().printStackTrace();
        StringBuffer ret=new StringBuffer();
        Iterator it=getResourceIDs();
        while(it.hasNext())
        {
            String id=(String)it.next();
            //System.out.println("id:"+id+" resid:"+resid);
            if(!id.equals(resid))
            {
                HashMap params=getResourceURI(id);
                
                StringBuffer aux=new StringBuffer();
                
                Iterator it2=params.keySet().iterator();
                while(it2.hasNext())
                {
                    String key=(String)it2.next();
                    if(!key.equals(URLP_NUMBERID))
                    {
                        aux.append("/"+key+"/"+params.get(key));
                    }
                }
                
                //internal query
                it2=getIntResourceQuery(id).iterator();
                while(it2.hasNext())
                {
                    String pr=(String)it2.next();
                    if(pr.startsWith(URLP_PARAMKEY))
                    {
                        String key=pr.substring(URLP_PARAMKEY.length());
                        aux.append("/"+URLP_PARAMKEY+SFBase64.encodeString(key));
                    }else if(pr.startsWith(URLP_PARAMVALUE))                    
                    {
                        String val=pr.substring(URLP_PARAMVALUE.length());
                        aux.append("/"+URLP_PARAMVALUE+SFBase64.encodeString(val));
                    }
                }
                
                //adic params
                it2=getExtURIResourceParams(id).iterator();
                while(it2.hasNext())
                {
                    String pr=(String)it2.next();
                    aux.append("/"+pr);
                }
                
                if(id.equals(getAccResourceID()))
                {
                    if(queryString!=null)
                    {
                        aux.append(queryString);
                    }
                }
                
                if(aux.length()>0)
                {
                    ret.append("/"+URLP_NUMBERID+"/"+params.get(URLP_NUMBERID));
                    ret.append(aux);
                }
                
            }
        }
        if(ret.length()==0)return null;
        return ret.toString();
    }
    
    /**
     * Gets the resource tmid.
     * 
     * @param resid the resid
     * @return the resource tmid
     */
    public String getResourceTMID(String resid)
    {
        String tm=getResourceURIValue(resid,URLP_TOPICMAPID);
        if(tm==null)
        {
            if(getVirtWebPage()!=null)
                tm=getVirtWebPage().getWebSite().getId();
            else
                tm=getWebPage().getWebSite().getId();
        }
        return tm;        
    }    
    
    /**
     * Gets the acc resource tmid.
     * 
     * @return the acc resource tmid
     */
    public String getAccResourceTMID()
    {
        return getResourceTMID(accResourceID);
    }
   
    /** Getter for property accType.
     * @return Value of property accType.
     *
     */
    public int getAccType()
    {
        return accType;
    }    
    
    /**
     * Mostrar solamente el contenido.
     * 
     * @return Value of property onlyContent.
     */
    public boolean isOnlyContent()
    {
        return onlyContent;
    }
    
    /*
     *  regresa queryString codificado en base a los parametros del HashMap (String, String[])
     */
    /**
     * _get query string.
     * 
     * @param map the map
     * @return the string
     */
    public static String _getQueryString(HashMap map)
    {
        if(map!=null)
        {
            StringBuffer ret=new StringBuffer();
            Iterator it=map.keySet().iterator();
            while(it.hasNext())
            {
                String key=(String)it.next();
                Object obj=map.get(key);
                if(obj!=null)
                {
                    String values[];
                    if(obj instanceof String)
                    {
                        values=new String[]{(String)obj};
                    }else
                    {
                        values=(String[])obj;
                    }
                    key=SFBase64.encodeString(key);
                    for(int x=0;x<values.length;x++)
                    {
                        String value=values[x];
                        ret.append("/"+URLP_PARAMKEY+key);
                        if(value!=null)
                        {
                            value=SFBase64.encodeString(value);
                            ret.append("/"+URLP_PARAMVALUE+value);
                        }
                    }
                }
            }
            if(ret.length()==0)return null;
            return ret.toString();
        }
        return null;
    }    
    
    //regresa queryString codificado
    /**
     * _get query string.
     * 
     * @param request the request
     * @return the string
     */
    public String _getQueryString(HttpServletRequest request)
    {
        String qs=request.getQueryString();
        try
        {
            if(qs!=null)
            {
                StringBuffer ret=new StringBuffer();
                StringTokenizer st=new StringTokenizer(qs,"&");
                while(st.hasMoreTokens())
                {
                    String param=st.nextToken();

                    String key=null;
                    String value=null;
                    StringTokenizer st2=new StringTokenizer(param,"=");
                    if(st2.hasMoreTokens())key=st2.nextToken();
                    if(st2.hasMoreTokens())value=st2.nextToken();
                    if(key!=null)
                    {
                        key=URLDecoder.decode(key);
                        key=SFBase64.encodeString(key);
                        ret.append("/"+URLP_PARAMKEY+key);
                        if(value!=null)
                        {
                            value=URLDecoder.decode(value);
                            value=SFBase64.encodeString(value);
                            ret.append("/"+URLP_PARAMVALUE+value);
                        }
                    }
                }
                if(ret.length()==0)return null;
                return ret.toString();
            }
        }catch(Exception e){log.error("Error to process QueryString:"+qs);}
        return null;
    }
    
    /**
     * Gets the int resource query.
     * 
     * @param resid the resid
     * @return the int resource query
     */
    public ArrayList getIntResourceQuery(String resid)
    {
        return (ArrayList)internalQuery.get(resid);
    }
    
    /**
     * Adds the request params.
     * 
     * @param req the req
     * @param arr the arr
     */
    private void addRequestParams(HttpServletRequest req, ArrayList arr)
    {
        if(arr!=null)
        {
            Iterator it2=arr.iterator();
            String key=null;
            String val=null;
            while(it2.hasNext())
            {
                String pr=(String)it2.next();
                if(pr.startsWith(URLP_PARAMKEY))
                {
                    if(key!=null)
                    {
                        key=pr.substring(URLP_PARAMKEY.length());
                        ((SWBHttpServletRequestWrapper)req).addParameter(key,null);
                        key=null;
                    }else
                    {
                        key=pr.substring(URLP_PARAMKEY.length());
                    }
                }else if(pr.startsWith(URLP_PARAMVALUE))
                {
                    val=pr.substring(URLP_PARAMVALUE.length());
                    ((SWBHttpServletRequestWrapper)req).addParameter(key,val);
                    key=null;
                    val=null;
                }
            }
        }        
    }
    
    /**
     * Gets the internal request.
     * 
     * @param request the request
     * @param rid the rid
     * @return the internal request
     */
    public HttpServletRequest getInternalRequest(javax.servlet.http.HttpServletRequest request, String rid)
    {
        ArrayList arr=getIntResourceQuery(rid);
        javax.servlet.http.HttpServletRequest req=request;
        if(getAccResourceID()!=null && !rid.equals(getAccResourceID()))
        {
            req=new SWBHttpServletRequestWrapper(request,getUser().getLanguage(),getWebPage().getWebSite().getId(),true);
            addRequestParams(req,arr);
        }else if(rid.equals(getAccResourceID()))
        {
            if(arr==null)
            {
                req=new SWBHttpServletRequestWrapper(request,getUser().getLanguage(),getWebPage().getWebSite().getId(),false);
            }else
            {
                req=new SWBHttpServletRequestWrapper(request,getUser().getLanguage(),getWebPage().getWebSite().getId(),false,true);
                addRequestParams(req,arr);
            }
        }
        return req;
    }

    /**
     * Gets the model id.
     * 
     * @return the model id
     */
    public String getModelId()
    {
        return smodel;
    }
    
}
