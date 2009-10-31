/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboración e integración para Internet,
 * la cual, es una creación original del Fondo de Información y Documentación para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro Público del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versión 1; No. 03-2003-012112473900 para la versión 2, y No. 03-2006-012012004000-01
 * para la versión 3, respectivamente.
 *
 * INFOTEC pone a su disposición la herramienta INFOTEC WebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garantía sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *
 *                                          http://www.webbuilder.org.mx
 */


/*
 * TopicFiltered.java
 *
 * Created on 15 de junio de 2005, 04:49 PM
 */

package com.infotec.topicmaps;

import com.infotec.topicmaps.bean.*;
import com.infotec.topicmaps.util.*;

import java.util.*;

import com.infotec.topicmaps.db.*;
import org.xml.sax.*;
import org.w3c.dom.*;
import org.apache.xerces.parsers.*;

import java.io.*;

import com.infotec.wb.util.*;
import com.infotec.appfw.util.AFUtils;
import java.util.ArrayList;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class TopicFiltered extends Topic
{
    boolean haveChilds=true;
    Topic topic_orig;
    TopicMapFiltered map_fil;
    
    
    /** Creates a new instance of TopicFiltered */
    public TopicFiltered(Topic topic, TopicMapFiltered topicmap)
    {
        topic_orig=topic;
        map_fil=topicmap;
    }
    
    /**
     * Getter for property haveChilds.
     * @return Value of property haveChilds.
     */
    public boolean _haveChilds()
    {
        return haveChilds;
    }
    
    /**
     * Setter for property haveChilds.
     * @param haveChilds New value of property haveChilds.
     */
    public void _setHaveChilds(boolean haveChilds)
    {
        this.haveChilds = haveChilds;
    }
    
    public Topic _getTopic()
    {
        return topic_orig;
    }    
    
    /** Regresa el <B>TopicMap</B> al que pertenece el topico.
     * @return TopicMap
     */
    public TopicMap getMap()
    {
        return map_fil;
    }    
    
    /** Asigna el <B>TopicMap</B> del topico.
     * @param parent TopicMap
     */
    public void setMap(TopicMap parent)
    {
        map_fil=(TopicMapFiltered)parent;
    }    
    
    /** Regresa Identificador del Topico
     * @return String
     */
    public String getId()
    {
        return topic_orig.getId();
    }

    /** Asigna identificador del Topico.
     * @param id String
     */
    public void setId(String id)
    {
        topic_orig.setId(id);
    }


    /** Regresa un <B>ArrayList</B> de objetos <B>BaseName</B> que representan los nombres del tópico.
     * @return ArrayList de objetos <B>BaseName</B>
     */
    public ArrayList getBaseNames()
    {
        return topic_orig.getBaseNames();
    }

    /** Assigna un <B>ArrayList</B> de objetos <B>BaseName</B> que representan los nombres del tópico.
     * @param basenames ArrayList de objetos BaseName que representan los nombres del tópico.
     */
    public void setBaseNames(ArrayList basenames)
    {
        topic_orig.setBaseNames(basenames);
    }

    /** regresa un <B>ArrayList</B> de objetos <B>Occurrence</B> que representan las ocurrencias o
     * elementos del tópico.
     * @return ArrayList
     */
    public ArrayList getOccurrences()
    {
        return topic_orig.getOccurrences();
    }

    /** asigna un <B>ArrayList</B> de objetos <B>Occurrence</B> que representan las ocurrencias o
     * elementos del tópico.
     * @param occurs New value of property m_occurs.
     */
    public void setOccurrences(ArrayList occurs)
    {
        topic_orig.setOccurrences(occurs);
    }

    /** regresa un <B>ArrayList</B> de objetos <B>Association</B> que representan las
     * asociaciones del tópico con otros topicos.
     * @return ArrayList
     */
    public ArrayList getAssociations()
    {
        return topic_orig.getAssociations();
    }

    /** Asigna un <B>ArrayList</B> de objetos <B>Association</B> que representan las
     * asociaciones del tópico con otros topicos.
     * @param associations ArrayList
     */
    public void setAssociations(ArrayList associations)
    {
        topic_orig.setAssociations(associations);
    }

    /** regresa un <B>ArrayList</B> de objetos <B>InstanceOf</B> que representan los
     * tipos o padres del tópico.
     * @return ArrayList
     */
    public ArrayList getInstanceOf()
    {
        return m_instanceof;
    }

    /** Asigna un <B>ArrayList</B> de objetos <B>InstanceOf</B> que representan los
     * tipos o padres del tópico.
     * @param instanceOf ArrayList
     */
    public void setInstanceOf(ArrayList instanceOf)
    {
        this.m_instanceof = instanceOf;
    }

    /** regresa objeto <B>SubjectIdentity</B> del tópico.
     * @return Subjectidentity
     */
    public com.infotec.topicmaps.SubjectIdentity getSubjectIdentity()
    {
        return topic_orig.getSubjectIdentity();
    }

    /** Asigna objeto <B>SubjectIdentity</B> del tópico.
     * @param subjectidentity SubjectIdentity
     */
    public void setSubjectIdentity(com.infotec.topicmaps.SubjectIdentity subjectidentity)
    {
        topic_orig.setSubjectIdentity(subjectidentity);
    }

    /** regresa un <B>ArrayList</B> de objetos <B>Topic</B> que representan todos los hijos inmediatos del tópico, no importando si están activos o borrados.
     * @return ArrayList
     */
    public ArrayList getChild()
    {
        return m_child;
    }
  
    /** Regresa el objeto <B>RecTopic</B> (objeto de base de datos) del topico.
     * @return RecTopic
     */
    public com.infotec.topicmaps.db.RecTopic getDbdata()
    {
        return topic_orig.getDbdata();
    }

    /** Asigna el objeto <B>RecTopic</B> (objeto de base de datos) del topico.
     * @param dbdata RecTopic
     */
    public void setDbdata(com.infotec.topicmaps.db.RecTopic dbdata)
    {
        topic_orig.getDbdata();
    }

    /** Indica si el topico esta sincronizado con DB
     * @return bollean.
     */
    public boolean isDBSyncronized()
    {
        return syncronized;
    }

    /** Define si el topico esta sincronizado con DB
     * @param syncronized bolean
     */
    public void setDBSyncronized(boolean syncronized)
    {
        this.syncronized = syncronized;
    }


    /** Regresa <B>Iterator</B> de objetos <B>Occurrence</B> que sean de un tipo y que
     * se encuantren activas y no borradas.
     * Si la occurencia tiene como datos "_parent", se busca este tipo de ocurrencia en
     * el topico padre (principal).
     * @param type String topicoid
     * @return Iterator
     */
    public Iterator getConfigData(String type)
    {
        return topic_orig.getConfigData(type);
    }
    
    /** Regresa <B>Iterator</B> de objetos <B>Occurrence</B> que sean de un tipo y que
     * se encuantren activas y no borradas.
     * Si no existen occurrencias, se busca este tipo de ocurrencia en
     * el topico padre (principal), a menos que existe una occurencia con la referencia "_noparent"
     * @param type Topico
     * @return String Iterator [], Posicion 0 = ResourceDatas, Posicion 1 = ResourceRefs 
     */
    public Iterator[] getConfigDataAndRef(Topic type)
    {
        return topic_orig.getConfigDataAndRef(type);
    }

    /** Regresa <B>Iterator</B> de objetos <B>Occurrence</B> que sean de un tipo y que
     * se encuantren activas y no borradas.
     * Si no existen occurrencias, se busca este tipo de ocurrencia en
     * el topico padre (principal), a menos que existe una occurencia con la referencia "_noparent"
     * @param type Topico
     * @return String Iterator 
     */
    public Iterator getConfigData(Topic type)
    {
        return topic_orig.getConfigData(type);
    }
}
