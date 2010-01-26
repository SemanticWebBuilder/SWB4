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
 * Occurrence.java
 *
 * Created on 22 de abril de 2002, 15:54
 */

package com.infotec.topicmaps;

import java.util.*;

import com.infotec.topicmaps.bean.*;
import com.infotec.topicmaps.db.*;
import com.infotec.topicmaps.util.*;
import org.xml.sax.*;
import org.apache.xerces.parsers.*;

import java.io.*;

/**
 * Objeto <B>Occurrence</B> que es parte de la implementacion del estandar XTM de TopicMaps.
 * <BR>
 * An <B>occurrence</B> is any information that is specified as being relevant to a given subject.
 * Occurrences constitute one of the three kinds of characteristic that can be assigned to a topic and are therefore governed by scope.
 * Each individual occurrence is an instance of a single class of occurrence (also known as an occurrence type) that may or may not be indicated explicitly.
 * The default occurrence type is defined by the �occurrence� published subject.
 * In order to be expressed in an XTM topic map, such occurrences must be resources that are either
 * addressable by reference using a URI (a �resource reference�), or
 * capable of being placed inline as character data (�resource data�).
 * The latter (resource data) provides a useful way of expressing a short piece of information about a subject (e.g. a work's date of composition).
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class Occurrence
{
    protected String m_id;
    protected InstanceOf m_instanceof;                  //InstanceOf
    protected Scope m_scope;
    protected ArrayList m_basenames;                       //BaseName
    protected String m_resourceData;
    protected String m_resourceRef;
    protected boolean m_isResourceRef;

    protected RecOccurrence dbdata = null;  //objeto de base de datos, si es nulo, no se sincroniza con base de datos
    protected boolean syncronized = false;

    /** Creates new Occurrence */
    public Occurrence()
    {
        m_id = TopicMgr.getInstance().getNewId();
        m_basenames = new ArrayList();
        dbdata = new RecOccurrence(m_id);
    }

    /**
     * @param type
     * @param data  */
    public Occurrence(Topic type, String data)
    {
        this();
        m_instanceof = new InstanceOf(type);
        m_resourceData = data;
    }

    /**
     * @param rec
     * @throws UnsupportedEncodingException
     * @throws SAXException
     * @throws IOException
     * @return  */
    public static Occurrence createOccurrence(RecOccurrence rec) throws java.io.UnsupportedEncodingException, org.xml.sax.SAXException, java.io.IOException
    {
        XTMParser reader = new XTMParser();
        SAXParser parser = new SAXParser();
        parser.setContentHandler(reader);
        //System.out.println("xml de la occurrencia:"+rec.getXml());
        TopicMap tm = TopicMgr.getInstance().getTopicMap(rec.getIdtm());
        Topic tp = tm.getTopic(rec.getIdtp());
        if (tp == null)
        {
            tp = new Topic();
            tp.setId(rec.getIdtp());
            tp.setMap(tm);
            tm.getTopics().put(tp.getId(), tp);
        }
        //System.out.println("Topic:"+tp.getDisplayName());
        //System.out.println("rec:"+rec.getXml());
        reader.setTopicMap(tm);
        reader.setStartFrom(tp);
        parser.parse(new InputSource(new InputStreamReader(new java.io.ByteArrayInputStream(rec.getXml().getBytes()), TopicMgr.getInstance().getEncode())));
        Occurrence occ = reader.getOccurrence();
        occ.setDbdata(rec);
        occ.setDBSyncronized(true);
        return occ;
    }


    /** Getter for property m_id.
     * @return Value of property m_id.
     */
    public String getId()
    {
        return m_id;
    }

    /** Setter for property m_id.
     * @param id New value of property m_id.
     */
    public void setId(String id)
    {
        this.m_id = id;
        dbdata.setId(id);
    }

    /** Getter for property m_scope.
     * @return Value of property m_scope.
     */
    public Scope getScope()
    {
        return m_scope;
    }

    /** Setter for property m_scope.
     * @param scope New value of property m_scope.
     */
    public void setScope(Scope scope)
    {
        this.m_scope = scope;
    }

    /** Getter for property m_resourceData.
     * @return Value of property m_resourceData.
     */
    public String getResourceData()
    {
        return m_resourceData;
    }

    /** Setter for property m_resourceData.
     * @param resourceData New value of property m_resourceData.
     */
    public void setResourceData(String resourceData)
    {
        m_isResourceRef = false;
        this.m_resourceData = resourceData;
        if (syncronized) TopicMgr.getInstance().getTopicMap(getDbdata().getIdtm()).changes.add("uo:" + m_id);
    }

    /** Getter for property m_resourceRef.
     * @return Value of property m_resourceRef.
     */
    public String getResourceRef()
    {
        return m_resourceRef;
    }

    /** Setter for property m_resourceRef.
     * @param resourceRef New value of property m_resourceRef.
     */
    public void setResourceRef(String resourceRef)
    {
        m_isResourceRef = true;
        this.m_resourceRef = resourceRef;
        if (syncronized) TopicMgr.getInstance().getTopicMap(getDbdata().getIdtm()).changes.add("uo:" + m_id);
    }

    /** Getter for property m_instanceof.
     * @return Value of property m_instanceof.
     */
    public com.infotec.topicmaps.InstanceOf getInstanceOf()
    {
        return m_instanceof;
    }

    /** Setter for property m_instanceof.
     * @param instanceOf New value of property m_instanceof.
     */
    public void setInstanceOf(com.infotec.topicmaps.InstanceOf instanceOf)
    {
        this.m_instanceof = instanceOf;
    }

    /** Getter for property m_names.
     * @return Value of property m_names.
     */
    public ArrayList getBaseNames()
    {
        return m_basenames;
    }

    /** Setter for property m_names.
     * @param basenames New value of property m_names.
     */
    public void setBaseNames(ArrayList basenames)
    {
        this.m_basenames = basenames;
    }


    /** Getter for property dbdata.
     * @return Value of property dbdata.
     */
    public RecOccurrence getDbdata()
    {
        return dbdata;
    }

    /** Setter for property dbdata.
     * @param rec  */
    public void setDbdata(RecOccurrence rec)
    {
        this.dbdata = rec;
    }

    /** Getter for property syncronized.
     * @return Value of property syncronized.
     */
    public boolean isDBSyncronized()
    {
        return syncronized;
    }

    /** Setter for property syncronized.
     * @param syncronized New value of property syncronized.
     */
    public void setDBSyncronized(boolean syncronized)
    {
        this.syncronized = syncronized;
    }

    /** Getter for property m_isResourceRef.
     * @return Value of property m_isResourceRef.
     */
    public boolean isResourceRef()
    {
        return m_isResourceRef;
    }
    
    public boolean isActive()
    {
        return getDbdata()!=null && getDbdata().getActive()==1 && getDbdata().getDeleted()==0;
    }    
    
    /**
     * @return  */
    public int getPriority()
    {
        return getDbdata().getPriority();
    }
    
}
