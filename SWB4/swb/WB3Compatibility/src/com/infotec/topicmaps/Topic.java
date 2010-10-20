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
 * Topic.java
 *
 * Created on 22 de abril de 2002, 15:52
 */

package com.infotec.topicmaps;


import java.util.*;

import com.infotec.topicmaps.db.*;
import org.xml.sax.*;
import org.w3c.dom.*;

import java.io.*;

import com.infotec.appfw.util.AFUtils;
import com.infotec.topicmaps.bean.TopicMgr;
import com.infotec.topicmaps.util.TMComparator;
import com.infotec.topicmaps.util.XTMParser;
import com.infotec.wb.core.db.RecResource;
import com.sun.org.apache.xerces.internal.parsers.SAXParser;

import org.semanticwb.SWBPortal;
import org.semanticwb.model.Resource;
import org.semanticwb.model.WebPage;

/**
 * Objeto <B>Topic</B> que es parte de la implementacion del estandar XTM de TopicMaps.
 * <BR>
 * A topic is a resource that acts as a proxy for some subject;
 * it is the topic map system's representation of that subject.
 * The relationship between a topic and its subject is defined to be one of reification.
 * Reification of a subject allows topic characteristics to be assigned to the topic that reifies it.
 * <BR>
 * Each individual topic is an instance of one or more classes of topics (also known as topic types)
 * that may or may not be indicated explicitly. The default topic type is defined by the �topic� published subject.
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class Topic
{

    org.semanticwb.model.WebPage wp = null;

    public Topic(org.semanticwb.model.WebPage webpage)
    {
        m_id = webpage.getId();
        wp = webpage;
        dbdata = new RecTopic(webpage);
        m_instanceof = new ArrayList();
        m_subjectidentity = null;
        m_basenames = new ArrayList();
        m_occurs = new ArrayList();
        //m_child = new ArrayList();
        m_associations = new ArrayList();
    }

    public org.semanticwb.model.WebPage getNative()
    {
        return wp;
    }

    public static final String CONFIG_DATA_AND_CRITERIAL="_criterial_and";

    protected String m_id;

    //protected ArrayList m_types;   //Topic
    protected ArrayList m_instanceof;                  //InstanceOf
    protected SubjectIdentity m_subjectidentity;
    protected ArrayList m_basenames;                   //BaseName
    protected ArrayList m_occurs;                      //Occurrence

    protected RecTopic dbdata = null;  //objeto de base de datos, si es nulo, no se sincroniza con base de datos
    protected boolean syncronized = false;

    protected TopicMap m_parent;
    protected ArrayList m_child;

    protected ArrayList m_associations; // Association

    protected HashMap paths=new HashMap();
    {
        paths.put("{webpath}",SWBPortal.getContextPath());
        paths.put("{distpath}",SWBPortal.getDistributorPath());
    }
    

    /** Crea un nuevo topico. */
    public Topic()
    {
        m_id = TopicMgr.getInstance().getNewId();
        m_instanceof = new ArrayList();
        m_subjectidentity = null;
        m_basenames = new ArrayList();
        m_occurs = new ArrayList();
        m_child = new ArrayList();
        m_associations = new ArrayList();
        dbdata = new RecTopic(m_id);
    }

    /** Crea un nuevo topico.
     * @param parent TopicMap
     */
    public Topic(TopicMap parent)
    {
        this();
        m_parent = parent;
        dbdata.setIdtm(parent.getId());
    }

    /** M�todo que crea un tópico en base a un objeto <B>RecTopic</B>
     * @param rec RecTopic
     * @throws UnsupportedEncodingException Error de codificacion del XML
     * @throws SAXException Error al Transformar XML
     * @throws IOException Error al leer XML
     * @return Topic
     */
    public static Topic createTopic(RecTopic rec) throws java.io.UnsupportedEncodingException, org.xml.sax.SAXException, java.io.IOException
    {
        XTMParser reader = new XTMParser();
        SAXParser parser = new SAXParser();
        parser.setContentHandler(reader);
        //System.out.println("xml del topico:"+rec.getXml());
        TopicMap tm = TopicMgr.getInstance().getTopicMap(rec.getIdtm());
        reader.setTopicMap(tm);
        reader.setStartFrom(tm);
        parser.parse(new InputSource(new InputStreamReader(new java.io.ByteArrayInputStream(rec.getXml().getBytes()), TopicMgr.getInstance().getEncode())));
        Topic tp = reader.getTopic();
        tp.setDbdata(rec);
        tp.setDBSyncronized(true);
        return tp;
    }

    /** M�todo que actualiza el tópico en base a un objeto <B>RecTopic</B>
     * @param rec RecTopic
     * @throws Exception Error de al actualizar topico.
     */
    public void updateFromDbData(RecTopic rec) throws Exception
    {
        this.setDbdata(rec);
        //System.out.println("updateFromDbData:"+rec.getXml());
        if (rec.getXml() != null)
        {
            Document doc = com.infotec.appfw.util.AFUtils.getInstance().XmltoDom(rec.getXml());
            //System.out.println(doc);
            //instanceOf
            
            ArrayList auxar=new ArrayList();
            Iterator auxit=getTypes();
            while(auxit.hasNext())auxar.add(auxit.next());
            
            NodeList nl = doc.getElementsByTagName("instanceOf");
            for (int x = 0; x < nl.getLength(); x++)
            {
                NodeList types = ((Element) nl.item(x)).getElementsByTagName("topicRef");
                for (int y = 0; y < types.getLength(); y++)
                {
                    Element el = (Element) types.item(y);
                    String typeid = el.getAttribute("xlink:href");
                    if (typeid != null)
                    {
                        Topic parent = getMap().getTopic(typeid.substring(1));
                        if(parent==null)
                        {
                            parent=new Topic(getMap());
                            parent.setId(typeid.substring(1));
                            getMap().getTopics().put(typeid.substring(1), parent);
                        }
                        if (!parent.getChild().contains(this))
                        {
                            //System.out.println("agregando tipo:"+typeid);
                            InstanceOf iof = new InstanceOf();
                            this.getInstanceOf().add(iof);
                            iof.setTopicRef(parent);
                            parent.getChild().add(this);
                        }else 
                        {
                            //System.out.println("tipo ya existente"+typeid);
                            auxar.remove(parent);
                        }
                        
                    }
                }
            }
            
            auxit=auxar.iterator();
            while(auxit.hasNext())removeType((Topic)auxit.next(),false);            
            
            //baseName
            ArrayList names = new ArrayList();
            nl = doc.getElementsByTagName("baseName");
            for (int x = 0; x < nl.getLength(); x++)
            {
                BaseName bn = new BaseName();
                names.add(bn);
                NodeList nl2 = ((Element) nl.item(x)).getElementsByTagName("baseNameString");
                for (int y = 0; y < nl2.getLength(); y++)
                {
                    Element el = (Element) nl2.item(y);
                    String basename = el.getFirstChild().getNodeValue();
                    //System.out.println(basename);
                    bn.setBaseNameString(basename);
                }
                nl2 = ((Element) nl.item(x)).getElementsByTagName("resourceData");
                for (int y = 0; y < nl2.getLength(); y++)
                {
                    Element el = (Element) nl2.item(y);
                    String variant = el.getFirstChild().getNodeValue();
                    //System.out.println(variant);
                    bn.getVariants().add(new Variant(variant));
                }
                nl2 = ((Element) nl.item(x)).getElementsByTagName("topicRef");
                for (int y = 0; y < nl2.getLength(); y++)
                {
                    Element el = (Element) nl2.item(y);
                    String typeid = el.getAttribute("xlink:href");
                    if (typeid != null)
                    {
                        Topic scope = getMap().getTopic(typeid.substring(1));
                        bn.setScope(new Scope(scope));
                    }
                }
            }
            setBaseNames(names);
            
            //SubjectIdentity
            nl = doc.getElementsByTagName("subjectIdentity");
            if(nl.getLength()>0)
            {
                NodeList nl2 = ((Element) nl.item(0)).getElementsByTagName("resourceRef");
                for (int y = 0; y < nl2.getLength(); y++)
                {
                    Element el = (Element) nl2.item(y);
                    String subject = el.getAttribute("xlink:href");
                    if (subject != null)
                    {
                        SubjectIdentity sub=new SubjectIdentity(subject);
                        setSubjectIdentity(sub);
                    }
                }
            }else
            {
                setSubjectIdentity(null);
            }
            
        }
    }

    /** Regresa Identificador del Topico
     * @return String
     */
    public String getId()
    {
        return m_id;
    }

    /** Asigna identificador del Topico.
     * @param id String
     */
    public void setId(String id)
    {
        this.m_id = id;
        getDbdata().setId(id);
    }

    /** A unique hash-code for the Topic.
     * This is based on the hashcode of the SGML Identifier.
     * @return A unique hash-code for the Topic. This is based on the hashcode of the SGML Identifier.
     */
    public int hashCode()
    {
        return getId().hashCode();
    }

    /** Determines equality of Topics based on equality
     * of their SGML Identifier.
     * @param other Topic
     * @return boolean
     */
    public boolean equals(Topic other)
    {
        return (getId().compareTo(other.getId()) == 0);
    }

    /** Regresa un <B>ArrayList</B> de objetos <B>BaseName</B> que representan los nombres del tópico.
     * @return ArrayList de objetos <B>BaseName</B>
     */
    public ArrayList getBaseNames()
    {
        return m_basenames;
    }

    /** Assigna un <B>ArrayList</B> de objetos <B>BaseName</B> que representan los nombres del tópico.
     * @param basenames ArrayList de objetos BaseName que representan los nombres del tópico.
     */
    public void setBaseNames(ArrayList basenames)
    {
        //if(isDBSyncronized())System.out.println("set basenames of topic:"+getId());
        this.m_basenames = basenames;
        if (isDBSyncronized()) getMap().changes.add("ut:" + getId());
    }

    /** Elimina un nombre base <B>BaseName</B>.
     * @param basename BaseName
     */
    public void removeBaseName(BaseName basename)
    {
        //if(isDBSyncronized())System.out.println("remove basename:"+basename.getBaseNameString()+" of topic:"+getId());
        getBaseNames().remove(basename);
        if (isDBSyncronized()) getMap().changes.add("ut:" + getId());
    }

    /** Agrega un objeto <B>BaseName</B> el cual define un nombre asociado a un idioma.
     * @param basename BaseName
     */
    public void addBaseName(BaseName basename)
    {
        //if(isDBSyncronized())System.out.println("add basename:"+basename.getBaseNameString()+" of topic:"+getId());
        getBaseNames().add(basename);
        if (isDBSyncronized()) getMap().changes.add("ut:" + getId());
    }

    /** regresa un <B>ArrayList</B> de objetos <B>Occurrence</B> que representan las ocurrencias o
     * elementos del tópico.
     * @return ArrayList
     */
    public ArrayList getOccurrences()
    {
        return m_occurs;
    }

    /** asigna un <B>ArrayList</B> de objetos <B>Occurrence</B> que representan las ocurrencias o
     * elementos del tópico.
     * @param occurs New value of property m_occurs.
     */
    public void setOccurrences(ArrayList occurs)
    {
        this.m_occurs = occurs;
    }

    /** regresa un <B>ArrayList</B> de objetos <B>Association</B> que representan las
     * asociaciones del tópico con otros topicos.
     * @return ArrayList
     */
    public ArrayList getAssociations()
    {
        return m_associations;
    }

    /** Asigna un <B>ArrayList</B> de objetos <B>Association</B> que representan las
     * asociaciones del tópico con otros topicos.
     * @param associations ArrayList
     */
    public void setAssociations(ArrayList associations)
    {
        this.m_associations = associations;
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
        return m_subjectidentity;
    }

    /** Asigna objeto <B>SubjectIdentity</B> del tópico.
     * @param subjectidentity SubjectIdentity
     */
    public void setSubjectIdentity(com.infotec.topicmaps.SubjectIdentity subjectidentity)
    {
        this.m_subjectidentity = subjectidentity;
        if (isDBSyncronized()) getMap().changes.add("ut:" + getId());
    }

    /** regresa un <B>ArrayList</B> de objetos <B>Topic</B> que representan todos los hijos inmediatos del tópico, no importando si est�n activos o borrados.
     * @return ArrayList
     */
    public ArrayList getChild()
    {
        if(m_child==null)
        {
            m_child=new ArrayList();
            Iterator<WebPage> it=getNative().listChilds();
            while (it.hasNext())
            {
                WebPage webPage = it.next();
                m_child.add(new Topic(webPage));
            }
        }
        return m_child;
    }

    /** Regresa hijos ordenados alfabeticamente, valida los hijos activos, borrados y ocultos
     * @return Iterator
     */
    public Iterator getSortChild()
    {
        return getSortChildSet(true).iterator();
    }

    /** Regresa hijos ordenados alfabeticamente, valida los hijos borrados y si active=true solo regresa los activos y no ocultos
     * @param active boolean
     * @return Iterator
     */
    public Iterator getSortChild(boolean active)
    {
        return getSortChildSet(active).iterator();
    }

    /** Regresa hijos ordenados alfabeticamente en base al topico de idioma que recibe
     * por parametro (scope), valida los hijos activos, borrados y ocultos
     * @param scope Topico del idioma.
     * @return Iterator
     */
    public Iterator getSortChild(Topic scope)
    {
        return getSortChildSet(scope).iterator();
    }
    
    /** Regresa hijos ordenados alfabeticamente en base al topico de idioma, valida los hijos borrados y si active=true solo regresa los activos y no ocultos
     * @param active boolean
     * @return Iterator
     */
    public Iterator getSortChild(boolean active, Topic scope)
    {
        return getSortChildSet(active,scope).iterator();
    }    

    /** Regresa hijos ordenados alfabeticamente, valida los hijos activos, borrados y ocultos
     * @return Set
     */
    public Set getSortChildSet()
    {
        return getSortChildSet(true);
    }

    /** Regresa hijos ordenados alfabeticamente, valida los hijos borrados y si active=true solo regresa los activos y no ocultos
     * @param active boolean
     * @return set
     */
    public Set getSortChildSet(boolean active)
    {
        return getSortChildSet(active,null);
    }
    
    /** Regresa hijos ordenados alfabeticamente en base al topico de idioma que recibe
     * por parametro (scope), valida los hijos activos, borrados y ocultos
     * @param scope Topico del Idioma
     * @return Iterator
     */
    public Set getSortChildSet(Topic scope)
    {
        return getSortChildSet(true,scope);
    }    

    /** Regresa hijos ordenados alfabeticamente en base al topico de idioma, valida los hijos borrados y si active=true solo regresa los activos y no ocultos
     * @param active boolean
     * @return set
     */
    public Set getSortChildSet(boolean active, Topic scope)
    {
        //System.out.println("getSortChildSet:"+this);
        TreeSet set;
        if(scope==null)
        {
            set = new TreeSet(new TMComparator());
        }else
        {
            set = new TreeSet(new TMComparator(scope));
        }
        //set.addAll(getChild());
        Iterator it = getChild().iterator();
        while (it.hasNext())
        {
            Topic tp = (Topic) it.next();
            //System.out.println("child:"+tp);
            if (tp.getDbdata().getDeleted() == 1) continue;
            if (active && tp.getDbdata().getActive() == 0) continue;
            if (active && tp.getDbdata().getHidden() == 1) continue;
            if (active && !tp.checkSchedule(true)) continue;
            set.add(tp);
            //System.out.println("add:"+tp);
        }
        return set;
    }    

    /** Regresa todos los hijos de todos los niveles inferiores
     * No regresa los hijos virtuales o ligados.
     * No importa si los hijos estan activos, borrados u ocultos
     * @return ArrayList
     */
    public ArrayList getChildAll()
    {
        ArrayList arr = new ArrayList();
        Iterator it = getChild().iterator();
        while (it.hasNext())
        {
            Topic tp = (Topic) it.next();
            if (this == tp.getType())
            {
                arr.add(tp);
                arr.addAll(tp.getChildAll());
            }
        }
        return arr;
    }


    /** Regresa <B>Iterator</B> con todos los topicos padre
     * No se encuentran ordenados alfabeticamente.
     * El primer padre es el principal.
     * @return Iterator
     */
/*
    public void setChild(ArrayList child) {
        this.m_child = m_child;
    }
*/
    public Iterator getTypes()
    {
        ArrayList arr = new ArrayList();
        Topic type=getType();
        if(type!=null)arr.add(type);
        Iterator<WebPage> it = getNative().listVirtualParents();
        while (it.hasNext())
        {
            WebPage wp=it.next();
            arr.add(new Topic(wp));
        }
        return arr.iterator();
    }

    /** Regresa el primer topico padre (Principal).
     * @return Topic
     */
    public Topic getType()
    {
        return new Topic(getNative().getParent());
    }

    /** Regresa padres ordenados alfabeticamente, valida los padres activos y borrados
     * @return Iterator
     */
    public Iterator getSortTypes()
    {
        return getSortTypes(true);
    }


    /** Regresa padres ordenados alfabeticamente, valida los padres borrados y si active=true solo regresa los activos
     * @param active boolean
     * @return Iterator
     */
    public Iterator getSortTypes(boolean active)
    {
        TreeSet set = new TreeSet(new TMComparator());
        Iterator it = this.getTypes();
        while (it.hasNext())
        {
            Topic tp = (Topic) it.next();
            if (tp.getDbdata().getDeleted() == 1) continue;
            if (active && tp.getDbdata().getActive() == 0) continue;
            if (active && tp.getDbdata().getHidden() == 1) continue;
            if (active && !tp.checkSchedule(true)) continue;
            set.add(tp);
        }
        return set.iterator();
    }

    /** Regresa padres ordenados alfabeticamente en base al topico de idioma que recibe
     * por parametro (scope), valida los padres activos y borrados
     * @param scope Topico del Idioma
     * @return Iterator
     */
    public Iterator getSortTypes(Topic scope)
    {
        if (scope == null) return getSortTypes();
        TreeSet set = new TreeSet(new TMComparator(scope));
        Iterator it = this.getTypes();
        while (it.hasNext())
        {
            Topic tp = (Topic) it.next();
            if (!tp.isVisible()) continue;
            set.add(tp);
        }
        return set.iterator();
    }

    /** Agrega un topico padre.
     * @param type Topic
     */
    public void addType(Topic type)
    {
        addType(type, true);
    }

    /** Agrega un topico padre.
     * @param type Topic
     * @param log boolean, si es true el este cambio sera sincronizado con DB
     */
    public void addType(Topic type, boolean log)
    {
        //if(isDBSyncronized())System.out.println("add type:"+type.getId()+" of topic:"+getId());
        InstanceOf iof = new InstanceOf();
        this.getInstanceOf().add(iof);
        iof.setTopicRef(type);
        type.getChild().add(this);
        if (isDBSyncronized() && log) getMap().changes.add("ut:" + getId());
    }

    /** Elimina un padre.
     * @param type Topico padre a eliminar
     */
    public void removeType(Topic type)
    {
        removeType(type, true);
    }

    /** Elimina un padre.
     * @param type Topico padre a eliminar.
     * @param log boolean, si es true el este cambio sera sincronizado con DB
     */
    public void removeType(Topic type, boolean log)
    {
        //if(isDBSyncronized())System.out.println("remove type:"+type.getId()+" of topic:"+getId());
        Iterator it = getInstanceOf().iterator();
        while (it.hasNext())
        {
            InstanceOf insof = (InstanceOf) it.next();
            if (insof.getTopicRef() == type)
            {
                it.remove();
                type.getChild().remove(this);
            }
        }
        if (isDBSyncronized()) getMap().changes.add("ut:" + getId());
    }

    /** Elimina todos los padres. */
    public void removeAllTypes()
    {
        //if(isDBSyncronized())System.out.println("remove all types of topic:"+getId());
        Iterator it = getInstanceOf().iterator();
        while (it.hasNext())
        {
            InstanceOf insof = (InstanceOf) it.next();
            it.remove();
            insof.getTopicRef().getChild().remove(this);
        }
        if (isDBSyncronized()) getMap().changes.add("ut:" + getId());
    }
    
    /** Regresa el nombre por defecto.
     * @return String
     */
    public String getDisplayName()
    {
        return wp.getDisplayName();
    }    

    /** Regresa el nombre por defecto, en base a un idioma.
     * @param String Idioma
     * @return String
     */
    public String getDisplayName(String lang)
    {
        return wp.getDisplayName(lang);
    }

    /** Regresa el nombre en base a un idioma.
     *  Regresa null si el nombre en ese idioma no existe.
     * @param String Idioma
     * @return String 
     */
    public String getDisplayLangName(String lang)
    {
        return wp.getDisplayName(lang);
    }    
    
    /** Regresa el nombre por defecto, en base a un idioma.
     * @param scope Topio del Idioma
     * @return String
     */
    public String getDisplayName(Topic scope)
    {
//        String ret = null;
//        BaseName bn=getDisplayBaseName(scope);
//        if(bn!=null)ret=bn.getBaseNameString();
//        else ret = "Topic";
//        return ret;
        return getDisplayName();
    }
    
    /** Regresa el nombre por defecto.
     * @return String
     */
    public BaseName getDisplayBaseName()
    {
        return getDisplayBaseName((Topic) null);
    }    

    /** Regresa el nombre por defecto.
     * @return String
     */
    public BaseName getDisplayBaseName(String lang)
    {
        return getDisplayBaseName(getMap().getTopicLang(lang));
    }  
    
    /** Regresa el nombre por defecto, en base a un idioma.
     * @param scope Topio del Idioma
     * @return String
     */    
    public BaseName getDisplayBaseName(Topic scope)
    {
        BaseName ret=null;
//        if (scope != null)
//        {
//            Iterator it = getBaseNames().iterator();
//            while (it.hasNext())
//            {
//                BaseName bn = (BaseName) it.next();
//                if (bn.getScope() != null && bn.getScope().getTopicRefs().containsKey(scope.getId()))
//                {
//                    if(bn.getBaseNameString()!=null && bn.getBaseNameString().length()>0)
//                    {
//                        ret = bn;
//                    }
//                }
//            }
//        }
//
//        if (ret == null && !getBaseNames().isEmpty())
//        {
//            Topic defect = this.getMap().getlang();
//            if (defect != null)
//            {
//                Iterator it = getBaseNames().iterator();
//                while (it.hasNext())
//                {
//                    BaseName bn = (BaseName) it.next();
//                    if (bn.getScope() != null && bn.getScope().getTopicRefs().containsKey(defect.getId()))
//                    {
//                        ret = bn;
//                        break;
//                    }
//                }
//            }
//
//            if (ret == null)
//            {
//                ret = ((BaseName) getBaseNames().get(0));
//            }
//        }
        return ret;        
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
     */
    public String getDisplayName(HashMap args)
    {
        return wp.getDisplayName(args);
    }


    /** Regresa nombre de ordenamiento.
     * Si no existe regresa el basename
     * @return String
     */
    public String getSortName()
    {
        return getSortName(null);
    }
    
    /** Regresa nombre de ordenamiento.
     * Si no existe regresa el nombre en base al topico del idioma (scope).
     * @return String
     * @param scope Topico del Idioma.
     */
    public String getSortName(Topic scope)
    {
        return getSortName(scope,true);
    }
    

    /** Regresa nombre de ordenamiento.
     * Si no existe regresa el nombre en base al topico del idioma (scope).
     * siempre y cuando el parametro returnBaseName sea igual a true
     * @return String
     * @param scope Topico del Idioma.
     */
    public String getSortName(Topic scope, boolean returnBaseName)
    {
        String ret=wp.getSortName();
        if(ret==null && returnBaseName)ret=wp.getDisplayName();
        return ret;
    }


    /** Regresa el <B>TopicMap</B> al que pertenece el topico.
     * @return TopicMap
     */
    public TopicMap getMap()
    {
        if(m_parent==null)
        {
            m_parent=new TopicMap(dbdata.getNative().getWebSite());
        }
        return m_parent;
    }

    /** Asigna el <B>TopicMap</B> del topico.
     * @param parent TopicMap
     */
    public void setMap(TopicMap parent)
    {
        this.m_parent = parent;
    }

    /** Regresa el objeto <B>RecTopic</B> (objeto de base de datos) del topico.
     * @return RecTopic
     */
    public com.infotec.topicmaps.db.RecTopic getDbdata()
    {
        return dbdata;
    }

    /** Asigna el objeto <B>RecTopic</B> (objeto de base de datos) del topico.
     * @param dbdata RecTopic
     */
    public void setDbdata(com.infotec.topicmaps.db.RecTopic dbdata)
    {
        this.dbdata = dbdata;
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

    /** Agrega un objeto Occurrence, el cual puede ser del tipo CNF_WBTemplate,  CNF_WBRule, CNF_WBFlow, CNF_WBContent.
     * @param occ Occurrence
     */
    public void addOccurrence(Occurrence occ)
    {
//        getOccurrences().add(occ);
//        if (isDBSyncronized() || getMap().isDBSyncronized())
//        {
//            //System.out.println("addoccurrence:"+occ);
//            getMap().changes.add("ao:" + this.getId().length() + ":" + this.getId() + occ.getId());
//        }
    }

    /** Elimina una occurencia.
     * @param occ Occurrence
     */
    public void removeOccurrence(Occurrence occ)
    {
//        getOccurrences().remove(occ);
//        if (isDBSyncronized() || getMap().isDBSyncronized()) getMap().changes.add("ro:" + this.getId().length() + ":" + this.getId() + occ.getId());
    }

    /** regresa un Occurencia.
     * @param id String, identificador de la ocurrencia.
     * @return Occurrence
     */
    public Occurrence getOccurrence(String id)
    {
//        Iterator it = getOccurrences().iterator();
//        while (it.hasNext())
//        {
//            Occurrence occ = (Occurrence) it.next();
//            if (id.equals(occ.getId()))
//            {
//                return occ;
//            }
//        }
        return null;
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
//        Topic tp = this.getMap().getTopic(type);
//        if (tp != null) return getConfigData(tp);
        return new ArrayList().iterator();
    }
    
    /** Regresa arreglo de <B>Iterator</B> de Strings con el resourceData y resourceRef 
     * de las ocurrencias que sean de un tipo y que
     * se encuantren activas y no borradas.
     * Si no existen occurrencias, se busca este tipo de ocurrencia en
     * el topico padre (principal), a menos que existe una occurencia con la referencia "_noparent"
     * @param type Topico
     * @return String Iterator [], Posicion 0 = ResourceDatas, Posicion 1 = ResourceRefs 
     */
    public Iterator[] getConfigDataAndRef(Topic type)
    {
        Iterator[] its=new Iterator[2];
//        ArrayList ret = new ArrayList();
//        ArrayList ref = new ArrayList();
//        Iterator[] auxs=getConfigOccurrencesAndRef(type);
//        Iterator it = auxs[0];
//        while(it.hasNext())
//        {
//            Occurrence occ=(Occurrence)it.next();
//            ret.add(occ.getResourceData());
//        }
//        it = auxs[1];
//        while(it.hasNext())
//        {
//            Occurrence occ=(Occurrence)it.next();
//            ret.add(occ.getResourceRef());
//        }
//        its[0]=ret.iterator();
//        its[1]=ref.iterator();
        return its;
    }
    
    /** Regresa arreglo de <B>Iterator</B> de objetos <B>Occurrence</B> 
     * de las ocurrencias que sean de un tipo y que
     * se encuantren activas y no borradas.
     * Si no existen occurrencias, se busca este tipo de ocurrencia en
     * el topico padre (principal), a menos que existe una occurencia con la referencia "_noparent"
     * @param type Topico
     * @return String Iterator [], Posicion 0 = ResourceDatas, Posicion 1 = ResourceRefs 
     */
    public Iterator[] getConfigOccurrencesAndRef(Topic type)
    {
        Iterator[] its=new Iterator[2];
        //System.out.println(this+".getConfigData("+type+")");
//        boolean inherit=true;
//        ArrayList ret = new ArrayList();
//        ArrayList ref = new ArrayList();
//        Iterator enoc = getOccurrences().iterator();
//        while (enoc.hasNext())
//        {
//            Occurrence occ = (Occurrence) enoc.next();
//            try
//            {
//                Topic ttype = occ.getInstanceOf().getTopicRef();
//                if (ttype == type)
//                {
//                    if (!occ.isResourceRef())
//                    {
//                        if(occ.isActive())ret.add(occ);
//                        //System.out.println(this+".getConfigData."+occ.getResourceData());
//                    }
//                    else
//                    {
//                        //******** revisar el no heredar **************
//                        if (occ.getResourceRef().equals("_noparent"))
//                        {
//                            inherit=false;
//                        }else
//                        {
//                            ref.add(occ);
//                        }
//                    }
//                }
//            } catch (Exception e)
//            {
//                TopicMgr.getInstance().log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Topic_getConfigData_readConfError") + ":" + getDisplayName() + " " + com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Topic_getConfigData_type") + type, true);
//            }
//        }
        //System.out.println(this+".getConfigData.ret:"+ret.size());            
//        if(ret.size()==0 && inherit && getType()!=null)
//        {
//            return getType().getConfigOccurrencesAndRef(type);
//        }
//        its[0]=ret.iterator();
//        its[1]=ref.iterator();
        return its;
    }    
    
    /** Regresa <B>Iterator</B> de Strings con el resourceData de las ocurrencias </B> que sean de un tipo y que
     * se encuantren activas y no borradas.
     * Si no existen occurrencias, se busca este tipo de ocurrencia en
     * el topico padre (principal), a menos que existe una occurencia con la referencia "_noparent"
     * @param type Topico
     * @return String Iterator 
     */
    public Iterator getConfigData(Topic type)
    {

        ArrayList ret = new ArrayList();
//        Iterator it=getConfigOccurrences(type);
//        while(it.hasNext())
//        {
//            Occurrence occ=(Occurrence)it.next();
//            ret.add(occ.getResourceData());
//        }
        return ret.iterator();
    }    
    
    /** Regresa <B>Iterator</B> de objetos <B>Occurrence</B> que sean de un tipo y que
     * se encuantren activas y no borradas.
     * Si no existen occurrencias, se busca este tipo de ocurrencia en
     * el topico padre (principal), a menos que existe una occurencia con la referencia "_noparent"
     * @param type Topico
     * @return String Iterator 
     */
    public Iterator getConfigOccurrences(Topic type)
    {
        //return getConfigObjects(type,null);
        return new ArrayList().iterator();
    }

    /** Regresa <B>Iterator</B> de objetos <B>Occurrence</B> que sean de un tipo y que
     * se encuantren activas y no borradas.
     * Si no existen occurrencias, se busca este tipo de ocurrencia en
     * el topico padre (principal), a menos que existe una occurencia con la referencia "_noparent"
     * @param type Topico
     * @return String Iterator 
     */
    public Iterator getConfigObjects(Topic type, OccTransformer tranformer)
    {
//        boolean inherit=true;
//        ArrayList ret = new ArrayList();
//        Iterator enoc = getOccurrences().iterator();
//        while (enoc.hasNext())
//        {
//            Occurrence occ = (Occurrence) enoc.next();
//            try
//            {
//                Topic ttype = occ.getInstanceOf().getTopicRef();
//                if (ttype == type)
//                {
//                    if (!occ.isResourceRef())
//                    {
//                        if(occ.isActive())
//                        {
//                            if(tranformer!=null)
//                            {
//                                Object obj=tranformer.transform(occ);
//                                if(obj!=null)ret.add(obj);
//                            }else
//                            {
//                                ret.add(occ);
//                            }
//                        }
//                        //System.out.println(this+".getConfigOccurrences."+occ.getResourceData());
//                    }
//                    else
//                    {
//                        //System.out.println(occ.getResourceRef());
//                        //******** revisar el no heredar **************
//                        if (occ.getResourceRef().equals("_noparent"))
//                        {
//                            inherit=false;
//                        }
//                    }
//                }
//            } catch (Exception e)
//            {
//                TopicMgr.getInstance().log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Topic_getConfigData_readConfError") + ":" + getDisplayName() + " " + com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Topic_getConfigData_type") + type, true);
//            }
//        }
//        //System.out.println(this+".getConfigData.ret:"+ret.size());
//        if(ret.size()==0 && inherit && getType()!=null)
//        {
//            return getType().getConfigObjects(type,tranformer);
//        }
//        return ret.iterator();
        return new ArrayList().iterator();
    }

    /** Obtiene las ocurrencias de tipo contenido activas y que no esten borrados
     * @return Iterator
     */
    public Iterator getContents()
    {

        //TODO
//        ArrayList ret = new ArrayList();
//        Iterator enoc = getOccurrences().iterator();
//        while (enoc.hasNext())
//        {
//            Occurrence occ = (Occurrence) enoc.next();
//            try
//            {
//                if (occ.isActive())
//                {
//                    Topic ttype = occ.getInstanceOf().getTopicRef();
//                    if (ttype != null && ttype.getId().equals("REC_WBContent"))
//                    {
//                        ret.add(occ);
//                    }
//                }
//            } catch (Exception e)
//            {
//                TopicMgr.getInstance().log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Topic_getContents_readTopicContentsError") + ":" + getDisplayName(), true);
//            }
//        }
        return new ArrayList().iterator();
    }

    /** Regresa <B>Iterator</B> de objetos <B>Occurrence</B> que sean de un tipo y que no se encuentren borradas.
     * @param type String
     * @return Iterator
     */
    public Iterator getOccurrencesOfType(String type)
    {
        ArrayList arr=new ArrayList();
        if(type.equals("REC_WBContent"))
        {
            Iterator<Resource> it=getNative().listResources();
            while (it.hasNext()) {
                Resource resource = it.next();
                Occurrence occ=new Occurrence();
                occ.setResourceData(resource.getId());
                arr.add(occ);
            }
        }
//        Topic tp = this.getMap().getTopic(type);
//        if (tp != null) return getOccurrencesOfType(tp,false);
        return arr.iterator();

    }
    
    /** Regresa <B>Iterator</B> de objetos <B>Occurrence</B> que sean de un tipo y que no se encuentren borradas.
     * @param type Topic
     * @return Iterator
     */
    public Iterator getOccurrencesOfType(Topic type)
    {
        //return getOccurrencesOfType(type,false);
        return new ArrayList().iterator();
    }
    
     /** Regresa <B>Iterator</B> de objetos <B>Occurrence</B> que sean de un tipo y si deleted=false regresa solo las que no se encuentren borradas.
     * @param type String
     * @return Iterator
     */
    public Iterator getOccurrencesOfType(String type, boolean deleted)
    {
//        Topic tp = this.getMap().getTopic(type);
//        if (tp != null) return getOccurrencesOfType(tp,deleted);
        return new ArrayList().iterator();
    }
    
    
    /** Regresa <B>Iterator</B> de objetos <B>Occurrence</B> que sean de un tipo y si deleted=false regresa solo las que no se encuentren borradas.
     * @param type Topic
     * @return Iterator
     */
    public Iterator getOccurrencesOfType(Topic type, boolean deleted)
    {
        ArrayList ret = new ArrayList();
//        Iterator enoc = getOccurrences().iterator();
//        while (enoc.hasNext())
//        {
//            Occurrence occ = (Occurrence) enoc.next();
//            try
//            {
//                if (occ.getDbdata() != null)
//                {
//                    if(deleted)
//                    {
//                        Topic ttype = occ.getInstanceOf().getTopicRef();
//                        if (ttype == type)
//                        {
//                            ret.add(occ);
//                        }
//                    }else if(occ.getDbdata().getDeleted() == 0)
//                    {
//                        Topic ttype = occ.getInstanceOf().getTopicRef();
//                        if (ttype == type)
//                        {
//                            ret.add(occ);
//                        }
//                    }
//                }
//            } catch (Exception e)
//            {
//                TopicMgr.getInstance().log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Topic_getOcurrencesofType_readOcuurencesType") + ":" + getDisplayName(), true);
//            }
//        }
        return ret.iterator();
    }

    /** Regresa nombre por defecto del topico.
     * @return String
     */
    public String toString()
    {
        return this.getDisplayName();
    }

    /** Regresa un representacion en html de la ruta de navegacion el topico.
     * @return String en formato html
     */
    public String getPath()
    {
        return wp.getPath();
    }

    /** Obtiene la ruta de navegacion de la seccion
     * parametros:
     *      -   separator: caracter que se utilizara como separador.
     *      -   cssclass: nombre de la clase definida en el archivo CSS para utilizar en las ligas de la ruta.
     *      -   selectcolor: color de la seccion seleccionada.
     *      -   links: true/false, si tendra links en la ruta.
     *      -   language: lenguaje en el que se quiere presentar la ruta.
     *      -   home: Identificador de la secci�n home (si se quiere definir un home
     *          diferente al de defecto).
     *      -   target: Definicion del frame destino.
     *          diferente al de defecto).
     *      -   hiddentopics: identificadores de topicos seperados por comas que se quiere que se despliegen en la ruta.
     * @param args HashMap con los parametros del template
     * @return String de la ruta.
     */
    public String getPath(HashMap args)
    {
        return wp.getPath(args);
    }

    /** Regresa el nivel de profundidad del topico con respecto a la seccion home.
     * @return int, nivel de profundidad.
     */
    public int getLevel()
    {
        return wp.getLevel();
    }

    /** indica si el topico es hijo de otro topico.
     * @param topic Topic
     * @return boolean
     */
    public boolean isChildof(Topic topic)
    {
        return wp.isChildof(topic.getNative());
    }

    /** indica si el topico es padre de otro topico.
     * @param topic Topci
     * @return bollean
     */
    public boolean isParentof(Topic topic)
    {
        return wp.isParentof(topic.getNative());
    }

    /** Notifica que el topico fue cambiado para que los objetos que lo estan usando
     * recargen su informacion.
     */
    public void notifyDBChanged()
    {
        //if (isDBSyncronized()) getMap().changes.add("ut:" + getId());
    }

    /** activa el topico.
     * @param active int, 1=activo, 0=inactivo.
     * @throws Exception Si el topico no pudo ser activado.
     */
    public void setActive(int active) throws com.infotec.appfw.exception.AFException
    {
        if(active==1)wp.setActive(true);
        else wp.setActive(false);
    }
    
    /** cambia estatus de indexable al topico.
     * @param indexable int, 1=indexable, 0=No indexable.
     * @throws Exception Si el topico no pudo ser indexable.
     */
    public void setIndexable(int indexable) throws com.infotec.appfw.exception.AFException
    {
        if(indexable==1)wp.setIndexable(true);
        else wp.setIndexable(false);
    }
    
    /** oculta el topico.
     * @param  hidden int, 1=hidden, 0=Not hidden.
     * @throws Exception Si el topico no pudo ser ocultado.
     */
    public void setHidden(int hidden) throws com.infotec.appfw.exception.AFException
    {
        if(hidden==1)wp.setHidden(true);
        else wp.setHidden(false);
    }
    

    /** Marca como borrado el topico.
     * @throws Exception Si el topico no pudo cambiar el estatus de borrado.
     * @param deleted int, 1=borrado, 0=No borrado.
     */
    public void setDeleted(int deleted) throws com.infotec.appfw.exception.AFException
    {
        if(deleted==1)wp.setDeleted(true);
        else wp.setDeleted(false);
    }

    /** Regresa string con la fecha de ultima actualizacion de los contenidos.
     * @return String
     */
    public String getContentsLastUpdate()
    {
        return getContentsLastUpdate(null,null);
    }
    
    /** Regresa string con la fecha de ultima actualizacion de los contenidos.
     * de acuerdo al formato:
     * @see com.infotec.appfw.util.getStrFormat
     * @return String
     */
    public String getContentsLastUpdate(String lang, String format)
    {
        return wp.getContentsLastUpdate(lang, format);
    }
    
    /** Regresa string con la fecha de ultima actualizacion de los contenidos.
     * @return String
     */
    public String getContentsLastUpdate(HashMap args)
    {
        String lang = (String) args.get("language");
        String format = (String) args.get("format");
        return getContentsLastUpdate(lang, format);
    }    

        /** Regresa string con la fecha de ultima actualizacion de los contenidos.
     * @return String
     */
    public String getContentsAuthor()
    {
        //TODO
        return "";
    }
    
    /** Regresa numero de apariciones del topico o seccion.
     * @return long
     */
    public long getViews()
    {
        return wp.getViews();
    }

    /** Regresa numero de apariciones del topico o seccion.
     * Parametros:
     *        - topicid: identificador del topico.
     * @param args HashMap, con atributos del template.
     * @return long
     */
    public long getViews(HashMap args)
    {
        String tpid = (String) args.get("topicid");
        if (tpid != null)
        {
            return getMap().getTopic(tpid).getViews();
        } else
            return 0;
    }


    /** Regresa descripcion del topico, en el idioma por defecto del sitio.
     * @return String
     */
    public String getDescription()
    {
        return wp.getDescription();
    }

    /** Regresa descripcion del topico, de acuerdo al scope dado.
     * @return String
     */
    public String getDescription(Topic scope)
    {
        if (scope == null)
        {
            return wp.getDescription();
        }else
        {
            return wp.getDisplayDescription(scope.getId().substring(6)); //IDM_WB
        }
    }

    /** Regresa descripcion del topico, en el idioma que recibe por parapetro.
     * Parametros:
     *        - language: idioma de la descripcion.
     * @param args HashMap, con atributos del template.
     * @return String
     */
    public String getDescription(HashMap args)
    {
        String language = (String) args.get("language");
        if (language == null)
        {
            return getDescription(getMap().getlang());
        } else
        {
            return getDescription(getMap().getTopic("IDM_WB" + language));
        }
    }
    
    /**  Regresa el Url del topico
     *  Ejemplo: /wb2/jei/home
     * @return String
     */
    public String getRealUrl()
    {
        return wp.getRealUrl();
    }


    /**  Regresa el Url del topico
     *  Ejemplo: /wb2/jei/home
     * @return String
     */
    public String getUrl()
    {
        return wp.getUrl();
    }

    /**  Regresa el Url del topico
     *  Ejemplo: /wb2/jei/home
     * @return String
     */
    public String getUrl(Topic vtopic)
    {
        if(vtopic!=null)
        {
            return wp.getUrl(vtopic.getNative());
        }else
        {
            return wp.getUrl((WebPage)null);
        }
    }    
    
    public String getTopicHomeId()
    {
        return getMap().getHome().getId();
    }
    
    /** Regresa el Topico de Primer Nivel o Canal al que pertenece el topico.
     * @return Topic
     */
    public Topic getChannel()
    {
        Topic ret = this;
        if (getMap().getHome() != this)
        {
            Iterator aux = getTypes();
            ArrayList arr = new ArrayList();
            while (aux.hasNext())
            {
                Topic tp = (Topic) aux.next();
                if (getMap().getHome() == tp) break;
                if (arr.contains(tp)) break;
                arr.add(tp);
                aux = tp.getTypes();
                ret = tp;
            }
        }
        return ret;
    }
    
    /**
     *  Regresa valor de ocurrencia CNF_WBIcon asociada 
     *
     */
    
    public String getIcon()
    {
//        Iterator it=getOccurrencesOfType("CNF_WBIcon");
//        while(it.hasNext())
//        {
//            Occurrence occ=(Occurrence)it.next();
//            String i=occ.getResourceData();
//            return i;
//        }
        return null;
    }
    
    public Association getAssociationWithTopic(Topic tp)
    {
        //TODO
        Iterator it=tp.getAssociations().iterator();
        while(it.hasNext())
        {
            Association obj=(Association)it.next();
            if(getAssociations().contains(obj))return obj;
        }
        return null;
    }

    public boolean isActive()
    {
        return wp.isActive();
    }
    
    public boolean isVisible()
    {
        return wp.isVisible();
    }    
    
   /** Verifica si la calendarizacion del topico sen encuentra en tiempo, hereda calensarizaci�n o no dependiendo
     * del paremetro ruleInherit
     * @return boolean
     */
    public boolean checkSchedule(boolean ruleInherit)
    {
        boolean passrule = wp.isOnSchedule();
        return passrule;
    }        
        
    
    public boolean isDeleted()
    {
        return wp.isDeleted();
    }
    
    
}
