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


/*
 * TopicMapFiltered.java
 *
 * Created on 15 de junio de 2005, 12:34 PM
 */

package com.infotec.topicmaps;

import com.infotec.topicmaps.bean.*;
import com.infotec.appfw.util.*;
import com.infotec.wb.core.*;
import org.w3c.dom.*;
import java.util.*;
import org.semanticwb.SWBUtils;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class TopicMapFiltered extends TopicMap
{
    TopicMap m_topicmap=null;
    HashMap m_topicsFiltered=new HashMap();          //TopicFiltered del filtro (solo los del filtro).
    
    public TopicMapFiltered(TopicMap tm)
    {
        m_topicmap=tm;
        setId(m_topicmap.getId());
        setLang(getNewTopicFiltered(m_topicmap.getlang().getId()));
        //setHome(m_topicmap.getHome());
    }
    
    /** Creates a new instance of TopicMapFiltered */
    public TopicMapFiltered(String xml)
    {
        this(SWBUtils.XML.xmlToDom(xml));
    }
    
    public TopicMapFiltered(Document dom)
    {
        //System.out.println("TopicMapFiltered:"+AFUtils.getInstance().DomtoXml(dom,true));
        Element ele=(Element)dom.getFirstChild();
        String id=ele.getAttribute("topicmap");
        
        m_topicmap=TopicMgr.getInstance().getTopicMap(id);
        setId(m_topicmap.getId());
        setLang(getNewTopicFiltered(m_topicmap.getlang().getId()));
        getDbdata().setActive(m_topicmap.getDbdata().getActive());
        
        NodeList m_topics_nl=ele.getElementsByTagName("node");
        for(int x=0;x<m_topics_nl.getLength();x++)
        {
            Element el=(Element)m_topics_nl.item(x);
            String tpid=el.getAttribute("id");
            String childs=el.getAttribute("childs");
            String home=el.getAttribute("home");
            
            TopicFiltered tpf=getNewTopicFiltered(tpid);
            if(tpf!=null)
            {
                tpf._setHaveChilds(childs.equalsIgnoreCase("true"));
                if(home!=null && home.equalsIgnoreCase("true"))
                {
                    setHome(tpf);
                }
                m_topicsFiltered.put(tpid, tpf);
            }
        }
        build();
    }    

    private TopicFiltered getNewTopicFiltered(String id)
    {
        return getNewTopicFiltered(id, true, m_topics);
    }
    
    private TopicFiltered getNewTopicFiltered(String id, boolean add2Map, HashMap map)
    {
        TopicFiltered tp=(TopicFiltered)getTopic(id);
        if(tp!=null)return tp;
        Topic topic=m_topicmap.getTopic(id,true);
        if(topic!=null)
        {
            tp=new TopicFiltered(topic,this);
        }
        if(tp!=null && add2Map)map.put(tp.getId(),tp);
        return tp;
    }
    
    public void build()
    {
        //agregar topicos
        m_topics.clear();
        m_topics.put(getHome().getId(),getHome());
        m_topics.put(getlang().getId(),getlang());
        
        Iterator it=m_topicmap.getTopics().values().iterator();
        while(it.hasNext())
        {
            Topic tp=(Topic)it.next();
            Topic aux=getValidTopicFilter(tp);
        }
        
        
        //agregar asociaciones
        m_associations.clear();
        
        it=m_topicmap.getAssociations().values().iterator();
        while(it.hasNext())
        {
            boolean add=true;
            HashMap topics=new HashMap();
            
            Association ass=(Association)it.next();
            Association aux=ass.getCopy();
            aux.getInstanceOf().setTopicRef(getNewTopicFiltered(ass.getInstanceOf().getTopicRef().getId(),true,topics));
            
            if(aux.getScope()!=null)replaceTopic4TopicFiltered(aux.getScope().getTopicRefs(),true,topics);
            
            Iterator ita=aux.getMembers().iterator();
            while(ita.hasNext())
            {
                Member mem=(Member)ita.next();
                mem.getRoleSpec().m_topicref=getNewTopicFiltered(mem.getRoleSpec().m_topicref.getId(),true,topics);
                if(!replaceTopic4TopicFiltered(mem.m_topicrefs))
                {
                    add=false;
                }
            }
            if(add)
            {
                m_associations.put(aux.getId(),aux);
                m_topics.putAll(topics);
            }
        }
        
        //ligar topicos
        it=m_topics.values().iterator();
        while(it.hasNext())
        {
            Topic tp=(Topic)it.next();
            //System.out.println("ligar:"+tp);
            if(tp!=getHome())linkTypes(tp);
        }
    }

    private void linkTypes(Topic tp)
    {
        //System.out.println("linkTypes:"+tp);
        linkTypes(tp,m_topicmap.getTopic(tp.getId(),true));
    }
    
    private void linkTypes(Topic tp, Topic org)
    {
        //System.out.println("linkTypes:"+tp+" - "+org);
        Iterator it=org.getTypes();
        if(!it.hasNext())
        {
            if(org==org.getMap().getHome() || org.isChildof(org.getMap().getHome()))
            {
                tp.addType(getHome());
                //System.out.println("addType:"+tp+" - "+getHome());
            }
        }
        while(it.hasNext())
        {
            Topic otype=(Topic)it.next();
            Topic type=getTopic(otype.getId());
            if(type!=null)
            {
                tp.addType(type);
                //System.out.println("addType:"+tp+" - "+type);
            }else
            {
                linkTypes(tp,otype);
            }
        }
    }
    

    //remplaza los topicos del HashMap por topicosFiltrados
    //si no existe algun topico dentro del topicmapfiltered regresa false
    private boolean replaceTopic4TopicFiltered(HashMap topics)
    {
        return replaceTopic4TopicFiltered(topics, false,null);
    }
    
    //remplaza los topicos del HashMap por topicosFiltrados
    //si no existe algun topico dentro del topicmapfiltered y createdNew=true lo crea.
    private boolean replaceTopic4TopicFiltered(HashMap topics, boolean createNew, HashMap topicsMap)
    {
        boolean ret=true;
        HashMap map=new HashMap();
        Iterator it=topics.values().iterator();
        while(it.hasNext())
        {
            Topic tp=(Topic)it.next();
            Topic aux=null;
            if(createNew)
            {
                aux=getNewTopicFiltered(tp.getId(),true,topicsMap);
            }
            else aux=getTopic(tp.getId());
            if(aux!=null)
            {
                map.put(aux.getId(),aux);
            }else
            {
                ret=false;
            }
            it.remove();
        }        
        topics.putAll(map);
        return ret;
    }
    
    

    /** trae un topico deleted o no
     * @param id
     * @param deleted
     * @return  */
    private Topic getValidTopicFilter(Topic tp)
    {
        if(tp==null)return null;
        
        TopicFiltered tpf=null;
        
        if(tp.isChildof(m_topicmap.getHome()))
        {
            Iterator it=m_topicsFiltered.values().iterator();
            boolean ischild=false;
            while(it.hasNext() && !ischild)
            {
                TopicFiltered aux=(TopicFiltered)it.next();
                if(tp==aux._getTopic())ischild=true;
                else if(aux._haveChilds())
                {
                    if(tp.isChildof(aux._getTopic()))
                    {
                        ischild=true;
                    }
                }
            }
            if(ischild)tpf=getNewTopicFiltered(tp.getId());
        }else
        {
            tpf=getNewTopicFiltered(tp.getId());
        }
        //System.out.println("getTopic:"+tpf);
        return tpf;
    }    
    

}
