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
 * Association.java
 *
 * Created on 22 de abril de 2002, 15:54
 */

package com.infotec.topicmaps;

import com.infotec.topicmaps.bean.*;
import com.infotec.topicmaps.util.*;

import java.util.*;

import com.infotec.topicmaps.db.*;
import org.xml.sax.*;
import org.apache.xerces.parsers.*;

import java.io.*;

/**
 * Objeto <B>Association</B> que es parte de la implementacion del estandar XTM de
 * TopicMaps.
 * <BR>
 *
 * An <B>association</B> is a relationship between one or more topics, each of which plays a {@link role} as a {@link member} of that association.
 * The roles a topic plays in associations are among the characteristics that can be assigned to it and are therefore governed by {@link scope}.
 * Each individual association is an instance of a single class of association (also known as an association type) that may or may not be indicated explicitly.
 * The default association type is defined by the �association� published subject.
 * There is no directionality inherent in an association.
 * (Associations describe relationships: If A is related to B, then B must also be related to A. The issue is rather, what is the type of the relationship, and what roles are played by its members.
 * The question of how to label a relationship is one of naming, not direction.)
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class Association
{

    protected String m_id;
    protected InstanceOf m_instanceof;                  //InstanceOf
    protected Scope m_scope;
    protected ArrayList m_members;

    protected RecAssociation dbdata = null;  //objeto de base de datos, si es nulo, no se sincroniza con base de datos
    protected boolean syncronized = false;

    protected TopicMap m_parent;

    /** Creates new Association */
    public Association()
    {
        m_id = TopicMgr.getInstance().getNewId();
        m_members = new ArrayList();
        dbdata = new RecAssociation(m_id);
    }
    
    //regresa otro objeto con la misma informacion que este
    public Association getCopy()
    {
        Association ass=new Association(m_parent);
        ass.m_id=m_id;
        if(m_instanceof!=null)ass.m_instanceof=m_instanceof.getCopy();
        if(m_scope!=null)ass.m_scope=m_scope.getCopy();
        Iterator it=m_members.iterator();
        while(it.hasNext())
        {
            Member mem=(Member)it.next();
            ass.m_members.add(mem.getCopy());
        }
        ass.dbdata=dbdata;
        return ass;
    }

    /**
     * @param parent  */
    public Association(TopicMap parent)
    {
        this();
        m_parent = parent;
        dbdata.setIdtm(parent.getId());
    }

    /**
     * @param rec
     * @throws UnsupportedEncodingException
     * @throws SAXException
     * @throws IOException
     * @return  */
    public static Association createAssociation(RecAssociation rec) throws java.io.UnsupportedEncodingException, org.xml.sax.SAXException, java.io.IOException
    {
        XTMParser reader = new XTMParser();
        SAXParser parser = new SAXParser();
        parser.setContentHandler(reader);
        //System.out.println("xml de la occurrencia:"+rec.getXml());
        TopicMap tm = TopicMgr.getInstance().getTopicMap(rec.getIdtm());
        //System.out.println("Topic"+tp.getDisplayName());
        reader.setTopicMap(tm);
        reader.setStartFrom(tm);
        parser.parse(new InputSource(new InputStreamReader(new java.io.ByteArrayInputStream(rec.getXml().getBytes()), TopicMgr.getInstance().getEncode())));
        Association ass = reader.getAssociation();
        ass.setDbdata(rec);
        ass.setDBSyncronized(true);
        return ass;
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

    /** Getter for property m_members.
     * @return Value of property m_members.
     */
    public ArrayList getMembers()
    {
        return m_members;
    }

    /** Setter for property m_members.
     * @param members New value of property m_members.
     */
    public void setMembers(ArrayList members)
    {
        this.m_members = members;
    }

    /**
     * @return  */
    public Topic getType()
    {
        return m_instanceof.getTopicRef();
    }

    /** Getter for property dbdata.
     * @return Value of property dbdata.
     */
    public RecAssociation getDbdata()
    {
        return dbdata;
    }

    /** Setter for property dbdata.
     * @param dbdata New value of property dbdata.
     */
    public void setDbdata(RecAssociation dbdata)
    {
        this.dbdata = dbdata;
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
    
    public boolean isActive()
    {
        if(getType()==null || (getType()!=null && getType().isDeleted()))return false;
        
        Iterator itaux = getMembers().iterator();
        while (itaux.hasNext())
        {
            Member mem = (Member)itaux.next();
            if(mem.getRoleSpec()!=null && mem.getRoleSpec().getTopicRef()!=null)
            {
                if(mem.getRoleSpec().getTopicRef().isDeleted())return false;
            }else
            {
                return false;
            }
            Iterator itpla = mem.getTopicRefs().values().iterator();
            while (itpla.hasNext())
            {
                Topic tpla = (Topic) itpla.next();
                if(!tpla.isActive())return false;
            }
        }        
        return true;
    }

}
