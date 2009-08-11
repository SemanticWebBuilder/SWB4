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
 
/*
 * SWBIndexer.java
 *
 * Created on 11 de mayo de 2006, 06:57 PM
 */

package org.semanticwb.portal.indexer;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticLiteral;
import org.semanticwb.portal.api.SWBResource;

/**
 *
 * @author Javier Solis Gonzalez
 */
public abstract class SWBIndexer
{
    private static Logger log=SWBUtils.getLogger(SWBIndexer.class);

    public static final String ATT_ID="id";
    public static final String ATT_TYPE="type";
    public static final String ATT_TOPIC="topic";
    public static final String ATT_TOPICMAP="topicmap";
    public static final String ATT_RESID="resid";
    public static final String ATT_LANG="lang";
    public static final String ATT_URL="url";
    public static final String ATT_TITLE="title";
    public static final String ATT_CATEGORY="category";
    public static final String ATT_DATA="data";
    public static final String ATT_SUMMARY="summary";
    
    private Properties props = null;
    private String name = null;    
    private Timer timer=null;
    
    private List list=null;
    
    /** Creates a new instance of WBIndexer */
    public SWBIndexer()
    {
    }
    
    protected void init(String name, Properties props) {
        this.props = props;
        this.name = name;
        list=Collections.synchronizedList(new LinkedList());
        int delays=Integer.parseInt(props.getProperty("delay","30"));
        //System.out.println("delays:"+delays);
        
        TimerTask t=new TimerTask(){
            public void run()
            {
                _run();
            }
        };
        timer = new Timer();
        timer.schedule(t, delays*1000, delays*1000);
        
        init();
    }

    protected void _run()
    {
        //System.out.println("SWBIndexer _run()");
        log.debug("indexer:"+name+" is running...");
        while(list.size()>0)
        {
            SWBIndexObj obj=(SWBIndexObj)list.remove(0);
            //System.out.println("SWBIndexObj:"+obj);
            try
            {
                if(obj.is4Remove())
                {
                    removeObj(obj);
                }else
                {
                    parseObject(obj);
                    writeObject(obj);
                }
            }catch(Throwable t)
            {
                log.error(t);
            }
        }
        //System.out.println("SWBIndexer _run2()");
    }
    
    public abstract void init();    
    
    public abstract void reset();
    public abstract void remove();
    public abstract void unLock() throws IOException;
    public abstract boolean isLocked() throws IOException;
    public abstract void optimize();
    public abstract List search4Id(String id);

    protected abstract void createIndex();
    protected abstract void parseObject(SWBIndexObj obj);
    protected abstract void writeObject(SWBIndexObj obj);
    
    protected abstract SWBIndexObjList searchObj(String queryString, SWBIndexObj obj, User user, int page, int pindex);
    protected abstract void removeAll(String topicmapid);
    protected abstract void removeObj(SWBIndexObj obj);
    
    
    /**
     * Getter for property name.
     * @return Value of property name.
     */
    public java.lang.String getName()
    {
        return name;
    }
    
    /**
     * Getter for property props.
     * @return Value of property props.
     */
    public java.util.Properties getProperties()
    {
        return props;
    }
    
    public void indexWebSite(WebSite tm)
    {
        if(tm!=null)
        {
            Iterator<WebPage> itTopics=tm.listWebPages();
            while(itTopics.hasNext())
            {
                WebPage topic=itTopics.next();
                try
                {
                    indexWebPage(topic,true);
                }catch(Exception e){log.error("WebPage:"+topic,e);}
            }
        }
    }
    
    public void removeWebSite(String websiteid)
    {
        removeAll(websiteid);
    }
    
    public void indexWebPage(WebPage topic)
    {
        indexWebPage(topic,false);
    }
    
    public void indexWebPage(WebPage topic, boolean contents)
    {
        if(topic.isVisible() && topic.isIndexable())
        {
            SWBIndexObj obj=new SWBIndexObj();
            obj.setType(SWBIndexObj.TYPE_TOPIC);
            obj.setUrl(topic.getUrl());
            obj.setId(topic.getWebSiteId()+" "+topic.getId());
            obj.setCategory(getCategory(topic));
            obj.setTopicMapID(topic.getWebSiteId());
            obj.setTopicID(topic.getId());
            obj.setTitle(topic.getDisplayName());
            //obj.setSummary(topic.getPath());
            obj.setData(getTopicNames(topic)+"\n"+getTopicDescription(topic));
            list.add(obj);
            
            if(contents)
            {
                Iterator<Resource> itOccurrences=topic.listResources();
                while(itOccurrences.hasNext())
                {
                    Resource occ=itOccurrences.next();
                    if(occ!=null && occ.isActive() && !occ.isDeleted())
                    {
                        indexContent(occ, topic);
                    }
                }
            }
        }
    }    
    
    public void removeTopic(String topicmapid, String topicid)    
    {
        removeTopic(topicmapid, topicid,false);
    }
    
    public void removeTopic(String topicmapid, String topicid, boolean contents)
    {
        SWBIndexObj obj=new SWBIndexObj();
        obj.setType(SWBIndexObj.TYPE_TOPIC);
        obj.setId(topicmapid+" "+topicid);
        if(contents)
        {
            obj.setTopicID(topicid);
        }else
        {
            obj.setTopicID(null);
        }
        obj.setTopicMapID(topicmapid);
        obj.set4Remove(true);
        list.add(obj);        
    }
    
    public void indexContent(Resource resb, WebPage topic)
    {
        SWBResource res=SWBPortal.getResourceMgr().getResource(resb);
        if(res!=null && res.getResourceBase().isActive() && !res.getResourceBase().isDeleted())
        {
            SWBIndexObj obj=new SWBIndexObj();
            obj.setType(SWBIndexObj.TYPE_CONTENT);
            obj.setId(topic.getWebSiteId()+" "+resb.getId());
            obj.setCategory(getCategory(topic));
            obj.setUrl(topic.getUrl());
            obj.setTopicMapID(topic.getWebSiteId());
            obj.setTopicID(topic.getId());
            obj.setResId(resb.getId());
            //obj.setLang(lang);
            obj.setTitle(res.getResourceBase().getTitle());
            list.add(obj);
        }
    }   
    
    public void removeContent(String resid, String topicmapid)
    {
        SWBIndexObj obj=new SWBIndexObj();
        obj.setType(SWBIndexObj.TYPE_CONTENT);
        obj.setId(topicmapid+" "+resid);
        obj.setTopicMapID(topicmapid);
        obj.set4Remove(true);
        list.add(obj);
    }

//    public void indexFile(File file, String url) throws IOException
//    {
//        indexFile(file, url, null);
//    }
    
    public void indexFile(File file, String url, WebPage topic, String resid) throws IOException
    {
        SWBIndexObj obj=new SWBIndexObj();
        obj.setType(SWBIndexObj.TYPE_FILE);
        obj.setUrl(url);
        obj.setId(file.getCanonicalPath());
        if(topic!=null)
        {
            obj.setCategory(getCategory(topic));
            obj.setTopicMapID(topic.getWebSiteId());
            obj.setTopicID(topic.getId());
            if(resid!=null)
            {
                obj.setResId(resid);
            }
        }
        obj.setTitle(file.getName());
        list.add(obj);
    }  
    
    public void removeFile(File file)
    {
        try
        {
            SWBIndexObj obj=new SWBIndexObj();
            obj.setType(SWBIndexObj.TYPE_FILE);
            obj.setId(file.getCanonicalPath());
            obj.set4Remove(true);
            list.add(obj);
        }catch(Exception e){log.error(e);}
    }
    
    public int getIndexSize()
    {
        return list.size();
    }
    
    private String getCategory(WebPage topic)
    {
        WebPage aux=topic;
        StringBuffer ret=new StringBuffer();
        ret.append(aux.getId());
        while(aux.getParent()!=null)
        {
            aux=aux.getParent();
            ret.insert(0,aux.getId()+" ");
        }
        ret.insert(0,getLevel(topic)+" ");
        return ret.toString();
    }
    
    private String getLevel(WebPage topic)
    {
        String ret=""+topic.getLevel();
        int l=ret.length();
        for(int x=0;x<4-l;x++)
        {
            ret="0"+ret;
        }
        return ret;
    }
    
    private String getTopicNames(WebPage page)
    {
        StringBuffer names = new StringBuffer();
        if (page!=null)
        {
            Iterator<SemanticLiteral> it=page.getSemanticObject().listLiteralProperties(WebPage.swb_title);
            while(it.hasNext())
            {
                SemanticLiteral lit=it.next();
                names.append(lit.getString());
                if(it.hasNext())names.append(" / ");
            }
            //TODO: Agregar nombres y variantes
//            Iterator na = t1.getBaseNames().iterator();
//            while (na.hasNext())
//            {
//                BaseName bn = (BaseName) na.next();
//                names.append(bn.getBaseNameString() + " / ");
//
//                Iterator va = bn.getVariants().iterator();
//                while (va.hasNext())
//                {
//                    Variant v = (Variant) va.next();
//                    VariantName vn = v.getVariantName();
//                    names.append(vn.getResource() + " ");
//                }
//            }
        }
        return names.toString();
    }

    private String getTopicDescription(WebPage page)
    {
        StringBuffer descb = new StringBuffer();
        if (page!=null)
        {
            Iterator<SemanticLiteral> it=page.getSemanticObject().listLiteralProperties(WebPage.swb_description);
            while(it.hasNext())
            {
                SemanticLiteral lit=it.next();
                descb.append(lit.getString());
                if(it.hasNext())descb.append(" / ");
            }
        }
        return descb.toString();
    }    
    
    public SWBIndexObjList search(String queryString, User user)
    {
        return searchObj(queryString,null,user,0,0);
    }
    
    public SWBIndexObjList search(String queryString, User user, int page, int pindex)
    {
        return searchObj(queryString,null,user,page,pindex);
    }

    public SWBIndexObjList search(String queryString, SWBIndexObj obj, User user, int page, int pindex)
    {
        return searchObj(queryString,obj,user,page,pindex);
    }
   
    private static WebPage getTopic(SWBIndexObj obj)
    {
        String tm=obj.getTopicMapID();
        String tp=obj.getTopicID();
        if(tm==null || tp==null)return null;
        WebSite topicmap=SWBContext.getWebSite(tm);
        if(topicmap!=null)
        {
            return topicmap.getWebPage(tp);
        }
        return null;
    }
    
    private static boolean haveAcces2Topic(WebPage topic, User user)
    {
        boolean ret=false;
        if(topic!=null)
        {
            if(topic.isVisible() && user.haveAccess(topic))
            {
                return true;
            }
        }
        return ret;
    }
    
    protected static boolean validate(SWBIndexObj obj, User user)
    {
        WebPage topic=getTopic(obj);
        boolean tpacc=haveAcces2Topic(topic,user);
        
        if(tpacc)
        {
            if(obj.getType().equals(SWBIndexObj.TYPE_TOPIC))
            {
                //se agregar sumamary
                obj.setSummary(topic.getDisplayDescription(user.getLanguage()));
                return true;
            }else if(obj.getType().equals(SWBIndexObj.TYPE_CONTENT))
            {
                try
                {
                    String aux=obj.getResId();
                    String id=aux;
                    SWBResource res=null;
//                    if(aux.length()>0 && aux.charAt(0)=='0')
//                    {
//                        res=ResourceMgr.getInstance().getResource(TopicMgr.TM_GLOBAL, id);
//                    }else
//                    {
                        res=SWBPortal.getResourceMgr().getResource(obj.getTopicMapID(), id);
//                    }
                    if(res!=null)
                    {
                        return user.haveAccess(res.getResourceBase());
                    }
                }catch(Exception e)
                {
                    log.error(e);
                }
                return false;
            }else if(obj.getType().equals(SWBIndexObj.TYPE_FILE))
            {
                try
                {
                    String aux=obj.getResId();
                    if(aux!=null)
                    {
                        String id=aux;
                        SWBResource res=null;
//                        if(aux.length()>0 && aux.charAt(0)=='0')
//                        {
//                            res=ResourceMgr.getInstance().getResource(TopicMgr.TM_GLOBAL, id);
//                        }else
//                        {
                            res=SWBPortal.getResourceMgr().getResource(obj.getTopicMapID(), id);
//                        }
                        if(res!=null)
                        {
                            return user.haveAccess(res.getResourceBase());
                        }
                    }else
                    {
                        return true;
                    }
                }catch(Exception e)
                {
                    log.error(e);
                }
                return false;             
            }else
            {
                return true;
            }
        }else if(topic==null)
        {
            return true;
        }else
        {
            return false;
        }
    }      
}
