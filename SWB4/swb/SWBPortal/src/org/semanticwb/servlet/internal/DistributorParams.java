/*
 * DistributorParams.java
 *
 * Created on 17 de junio de 2004, 06:37 PM
 */

package org.semanticwb.servlet.internal;

import java.util.*;
import javax.servlet.http.*;
import java.net.URLDecoder;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.SFBase64;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;

/**
 * Clase que lee parametros del request y se los envia al distribuidor para la realización de ciertas operaciones
 * <p>
 * Object that reads request parameters and send them to the wb distributor for some operations
 *
 * @author Javier Solis Gonzalez
 */
public class DistributorParams
{
    static Logger log=SWBUtils.getLogger(DistributorParams.class);
    public static final int ACC_TYPE_NONE=0;
    public static final int ACC_TYPE_RENDER=1;
    public static final int ACC_TYPE_ACTION=2;
    public static final String URLP_RENDERID="_rid";
    public static final String URLP_TOPICMAPID="_idtm";
    public static final String URLP_NUMBERID="_nid";
    public static final String URLP_ACTIONID="_aid";
    public static final String URLP_METHOD="_mto";
    public static final String URLP_MODE="_mod";
    public static final String URLP_WINSTATE="_wst";
    public static final String URLP_ACTION="_act";
    public static final String URLP_VTOPIC="_vtp";
    public static final String URLP_ONLYCONTENT="_cnt";
    public static final String URLP_LANG="_lang";
    public static final String URLP_DEVICE="_devc";
    public static final String URLP_PARAMKEY="_prk_";
    public static final String URLP_PARAMVALUE="_prv_";
    public static final String URLP_REMOTEURL="_url";
    
    //String nombre = "wb2";
    
    private User user=null;
    private WebPage topic=null;
    private WebPage virtTopic=null;
    private int filtered=-1;
    private ArrayList URIParams=null;
    
    private String vtopicmap=null;
    private String vtopic=null;
    private String stopicmap=null;
    private String stopic=null;
    private HashMap resparams=null;
    private ArrayList ordresparams=null;
    private HashMap adicparams=null;    
    private HashMap accResource=null;
    private long accResourceID=0;
    private int accType=0;
    private String queryString=null;
    private HashMap internalQuery=null;
    
    private boolean onlyContent=false;
    private String lang=null;
    private String device=null;
    
    /** Creates a new instance of DistributorParams */
    public DistributorParams(HttpServletRequest request)
    {
        URIParams=_getURIParams(request);
        topic=_getTopic(request);
        virtTopic=_getVirtTopic();
        UserRepository rep=null;
        if(topic!=null)
        {
            rep=topic.getWebSite().getUserRepository();
            filtered=_getFiltered(request);
        }else
        {
            log.equals("WebPage not Found:"+request.getRequestURI()+" Ref:"+request.getHeader("Referer"));
        }
        user=_getUser(request,rep);
        queryString=_getQueryString(request);
        //System.out.println("queryString:"+queryString);
    }
    
    private ArrayList _getURIParams(HttpServletRequest request)
    {
        ArrayList arr=new ArrayList();
        String serv=request.getServletPath();
        String acont=request.getContextPath();
        boolean allAditionals=false;
        int lcont=0;
        if(acont!=null)
        {
            lcont=acont.length();
            if(lcont==1)lcont=0;
        }
        String uri=request.getRequestURI().substring(lcont+serv.length());
        
        //System.out.println("URI:"+request.getRequestURI());
        //System.out.println("serv:"+serv);
        //System.out.println("uri:"+uri);
        //System.out.println("context:"+request.getContextPath());
        
        if(uri.indexOf('?')>-1)uri=uri.substring(0,uri.indexOf('?'));
        StringTokenizer st = new StringTokenizer(uri, "/;");

        adicparams=new HashMap();
        ArrayList adicaux=new ArrayList();
        adicparams.put(new Long(0), adicaux);
        
        internalQuery=new HashMap();
        ArrayList intqaux=null;

        HashMap aux=null;
        int x=0;
        while(st.hasMoreTokens())
        {
            String tok=st.nextToken();
            arr.add(tok);
            
            if(x==0)stopicmap=tok;
            else if(x==1)stopic=tok;
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
                        Long v=new Long(val);

                        aux=new HashMap();
                        aux.put(URLP_NUMBERID, val);
                        ordresparams.add(v);
                        resparams.put(v,aux);

                        adicaux=new ArrayList();
                        adicparams.put(v,adicaux);  
                        
                        intqaux=new ArrayList();
                        internalQuery.put(v,intqaux);  

                        if(tok.equals(URLP_RENDERID))
                        {
                            accResource=aux;
                            accType=ACC_TYPE_RENDER;
                            accResourceID=v.longValue();
                        }else if(tok.equals(URLP_ACTIONID))
                        {
                            accResource=aux;
                            accType=ACC_TYPE_ACTION;
                            accResourceID=v.longValue();
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
                if(st.hasMoreTokens())vtopicmap=st.nextToken();
                if(st.hasMoreTokens())vtopic=st.nextToken();
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
     */
    public HashMap getResourceURI(long resid)
    {
        if(resparams==null)return null;
        return (HashMap)resparams.get(new Long(resid));
    }
    
    /**
     * Tiene parametros relacionados de los recursos.
     */
    public boolean haveResourcesURI()
    {
        if(ordresparams!=null)return true;
        else return false;
    }

    /**
     * regresa parametros de recursos ordernados.
     */
    public ArrayList getResourcesURI()
    {
        return ordresparams;
    }
    
    /*
     * regresa el valor de un parametro de un recurso.
     */
    public String getResourceURIValue(long resid, String param)
    {
        HashMap map=(HashMap)resparams.get(new Long(resid));
        if(map!=null)
        {
            return (String)map.get(param);
        }
        return null;
    }
    
    private User _getUser(HttpServletRequest request, UserRepository repository)
    {
        User user=WBUserMgr.getInstance().getUser(request, repository);
        if(lang!=null)
        {
            if(!user.getLanguage().equals(lang))
            {
                user.setLanguage(lang);
            }
        }
        if (device!=null) 
        {
            user.setDevice(device);        
        }
        return user;        
    }
    
    private WebPage _getTopic(HttpServletRequest request)
    {
        WebPage topic=null;
        //System.out.println("request:"+request);
        //System.out.println("stopicmap:"+stopicmap);
        if(stopicmap!=null)
        {
            WebSite tm=TopicMgr.getInstance().getTopicMap(stopicmap);
            if(tm!=null)
            {
                if(stopic!=null)
                {
                    topic=tm.getTopic(stopic);
                }else
                {
                    topic=tm.getHome();
                }
            }
        }else
        {
            RecDns dns = (RecDns) DBCatalogs.getInstance().getDns(request.getServerName());
            if (dns == null)
            {
                dns = DBCatalogs.getInstance().getDnsDefault();
            }
            //System.out.println("dns:"+dns);
            if (dns != null)
            {
                stopicmap = dns.getTopicMap();
                stopic = dns.getTopic();
            }else
            {
                stopicmap=TopicMgr.getInstance().getAdminTopicMap().getId();
                stopic=TopicMgr.getInstance().getAdminTopicMap().getHome().getId();
            }
            //System.out.println("stopicmap2:"+stopicmap);
            WebSite tm=TopicMgr.getInstance().getTopicMap(stopicmap);
            if(tm!=null)
            {
                if(stopic!=null)
                {
                    topic=tm.getTopic(stopic);
                }else
                {
                    topic=tm.getHome();
                }
            }
        }
        //System.out.println("_getTopic:"+topic);
        return topic;
    }
    
    private WebPage _getVirtTopic()
    {
        WebPage topic=null;
        if(vtopicmap!=null && vtopic!=null)
        {
            WebSite tm=TopicMgr.getInstance().getTopicMap(vtopicmap);
            if(tm!=null)
            {
                topic=tm.getTopic(vtopic);
            }
        }
        return topic;
    }    
    
    private int _getFiltered(HttpServletRequest request)
    {
        String ipuser = request.getRemoteAddr().toString();
        Iterator it = DBCatalogs.getInstance().getIpFilters(topic.getMap().getId()).values().iterator();
        while (it.hasNext())
        {
            RecIpFilter ip = (RecIpFilter) it.next();
            if (ipuser.indexOf(ip.getIp()) > -1)
            {
                return ip.getAction();
            }
        }
        return -1;
    }
    
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
    
    /** Getter for property topic.
     * @return Value of property topic.
     *
     */
    public WebPage getTopic()
    {
        if(topic==null)return null;
        if(topic.getMap()==TopicMgr.getInstance().getAdminTopicMap())
        {
            return topic;
        }else
        {
            return AdmFilterMgr.getInstance().getTopicFiltered(topic, user);
        }
    } 
    
    public void setTopic(WebPage topic)
    {
        this.topic=topic;
    }
    
    public WebPage getVirtTopic()
    {
        if(topic==null)return null;
        if(topic.getMap()==TopicMgr.getInstance().getAdminTopicMap())
        {
            return virtTopic;
        }else
        {
            return AdmFilterMgr.getInstance().getTopicFiltered(virtTopic, user);
        }
        
    } 
    
    /**
     *  Regresa ArrayList de Strings con todos los parametros de URI
     * Ejemplo:
     * Se tiene este URI -> /wb/WBAdmin/home/_rid/15/_idtm/demo
     * el arreglo que retorna -> ["WBAdmin","home","_rid","15","_idtm","demo"]
     */
    public ArrayList getURIParams()
    {
        return URIParams;
    }

    /**
     * Regresa parametros adicionales o extendidos, no reconocidos en la ruta base
     */
    public ArrayList getExtURIParams()
    {
        return (ArrayList)adicparams.get(new Long(0));
    }
    
    /**
     * Regresa parametros adicionales o extendidos, no reconocidos como parametros del recurso.
     */
    public ArrayList getExtURIResourceParams(long res)
    {
        return (ArrayList)adicparams.get(new Long(res));
    }    
    
    /**
     * Convierte un String a entero, si el estring es nulo o esta mal formado regresa el valor de defecto.
     */
    public int getInt(String val, int defa)
    {
        if(val==null)return defa;
        try
        {
            return Integer.parseInt(val);
        }catch(Exception e){return defa;}
    }

    /** Getter for property accResource.
     * @return Value of property accResource.
     *
     */
    public java.util.HashMap getAccResource()
    {
        return accResource;
    }    
    
    public long getAccResourceID()
    {
        return accResourceID;
    }
    
    /**
     * Regresa Iterador de Longs con identificadores de recursos pasados por URL
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
     */
    public String getNotAccResourceURI(long resid)
    {
        StringBuffer ret=new StringBuffer();
        Iterator it=getResourceIDs();
        while(it.hasNext())
        {
            Long id=(Long)it.next();
            if(id.longValue()!=resid)
            {
                HashMap params=getResourceURI(id.longValue());
                
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
                it2=getIntResourceQuery(id.longValue()).iterator();
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
                it2=getExtURIResourceParams(id.longValue()).iterator();
                while(it2.hasNext())
                {
                    String pr=(String)it2.next();
                    aux.append("/"+pr);
                }
                
                if(id.longValue()==getAccResourceID())
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
    
    public String getResourceTMID(long resid)
    {
        String tm=getResourceURIValue(resid,URLP_TOPICMAPID);
        if(tm==null)
        {
            if(getVirtTopic()!=null)
                tm=getVirtTopic().getMap().getId();
            else
                tm=getTopic().getMap().getId();
        }
        return tm;        
    }    
    
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
     * Mostrar solamente el contenido
     * @return Value of property onlyContent.
     */
    public boolean isOnlyContent()
    {
        return onlyContent;
    }
    
    /*
     *  regresa queryString codificado en base a los parametros del HashMap (String, String[])
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
        }catch(Exception e){AFUtils.log("Error to process QueryString:"+qs);}
        return null;
    }
    
    public ArrayList getIntResourceQuery(long resid)
    {
        return (ArrayList)internalQuery.get(new Long(resid));
    }
    
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
                        ((WBHttpServletRequestWrapper)req).addParameter(key,null);
                        key=null;
                    }else
                    {
                        key=pr.substring(URLP_PARAMKEY.length());
                    }
                }else if(pr.startsWith(URLP_PARAMVALUE))
                {
                    val=pr.substring(URLP_PARAMVALUE.length());
                    ((WBHttpServletRequestWrapper)req).addParameter(key,val);
                    key=null;
                    val=null;
                }
            }
        }        
    }
    
    public HttpServletRequest getInternalRequest(javax.servlet.http.HttpServletRequest request, long rid)
    {
        ArrayList arr=getIntResourceQuery(rid);
        javax.servlet.http.HttpServletRequest req=request;
        if(getAccResourceID()>0 && rid!=getAccResourceID())
        {
            req=new WBHttpServletRequestWrapper(request,getUser().getLanguage(),getTopic().getMap().getId(),true);
            addRequestParams(req,arr);
        }else if(rid==getAccResourceID())
        {
            if(arr==null)
            {
                req=new WBHttpServletRequestWrapper(request,getUser().getLanguage(),getTopic().getMap().getId(),false);
            }else
            {
                req=new WBHttpServletRequestWrapper(request,getUser().getLanguage(),getTopic().getMap().getId(),false,true);
                addRequestParams(req,arr);
            }
        }
        return req;
    }    
    
}
