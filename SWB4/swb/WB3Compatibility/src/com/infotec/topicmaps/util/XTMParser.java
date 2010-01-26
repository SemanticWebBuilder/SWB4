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
 * XTMParser.java
 *
 * Created on 23 de abril de 2002, 16:49
 */

package com.infotec.topicmaps.util;

import com.infotec.topicmaps.db.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

import java.util.*;
import java.io.*;

import com.infotec.topicmaps.*;
import com.sun.org.apache.xerces.internal.parsers.SAXParser;



/** objeto: parser de XTM
 * @author Javier Solis Gonzalez
 * @version
 */
public class XTMParser extends DefaultHandler
{

    protected String m_id;              //id data
    protected StringBuffer m_data;      //data
    protected boolean m_getData;        //getData
    //protected TopicMapHandler m_builder;

    protected static final String TOPICMAP = "topicMap";
    protected static final String TOPIC = "topic";
    protected static final String SUBJECTIDENTITY = "subjectIdentity";
    protected static final String INSTANCEOF = "instanceOf";
    protected static final String TOPICREF = "topicRef";
    protected static final String SUBJECTINDICATORREF = "subjectIndicatorRef";
    protected static final String BASENAME = "baseName";
    protected static final String BASENAMESTRING = "baseNameString";
    protected static final String VARIANT = "variant";
    protected static final String VARIANTNAME = "variantName";
    protected static final String PARAMETERS = "parameters";
    protected static final String SCOPE = "scope";
    protected static final String ASSOCIATION = "association";
    protected static final String MEMBER = "member";
    protected static final String ROLESPEC = "roleSpec";
    protected static final String OCCURRENCE = "occurrence";
    protected static final String RESOURCEREF = "resourceRef";
    protected static final String RESOURCEDATA = "resourceData";
    protected static final String MERGEMAP = "mergeMap";

    protected static final String ID = "id";
    protected static final String HREF = "href";
    protected static final String CLASS = "class";

    protected static final String XMLNS = "http://www.topicmaps.org/xtm/1.0/";
    protected static final String XLINK = "http://www.w3.org/1999/xlink";

    private static Stack m_objStack;

    private Object startFrom = null;
    private TopicMap auxMap = null;
    private Topic auxTopic = null;
    private Association auxAssociation = null;
    private Occurrence auxOccurrence = null;

    /** Creates new XTMParser */
    public XTMParser()
    {
    }

    public void characters(char[] ch, int start, int len)
    {
        if (m_getData) m_data.append(ch, start, len);
    }

    public void startDocument()
    {
        m_objStack = new Stack();
        if (startFrom != null) m_objStack.push(startFrom);
    }

    public void endDocument()
    {
        if (startFrom != null) m_objStack.pop();
        if (!m_objStack.isEmpty())
        {
            System.out.println("Error en start/end tags!");
        }
        m_objStack = null;
    }

    public void startElement(String nsURI, String localName, String qName, Attributes atts)
            throws SAXException
    {
        String xtmObject = getXTMObject(nsURI, localName, qName, atts);
        //m_objStack.push(xtmObject);

        //System.out.println("_startElement(\""+getXTMObject(nsURI,localName,qName,atts)+"\")");

        if (xtmObject.equals(TOPICMAP))
        {
            String id = getXTMAttribute(ID, atts);
            //String xmlns = atts.getValue("xmlns");
            //String xmlns_xlink = atts.getValue("xmlns:xlink");
            String xml_base = atts.getValue("xml:base");
            auxMap = new TopicMap();
            auxMap.setId(id);
            //auxMap.setXmlns(xmlns);
            //auxMap.setXmlns_xlink(xmlns_xlink);
            auxMap.setXml_base(xml_base);
            auxMap.getDbdata().setActive(1);
            m_objStack.push(auxMap);
        } else if (xtmObject.equals(TOPIC))
        {
            String id = getXTMAttribute(ID, atts);
            Topic obj = (Topic) auxMap.getTopics().get(id);
            if (obj == null)
            {
                obj = new Topic(auxMap);
                obj.setId(id);
            }
            if (m_objStack.peek() instanceof TopicMap)
            {
                if (auxMap.getTopics().get(id) == null)
                {
                    TopicMap tm = ((TopicMap) m_objStack.peek());
                    obj.setMap(tm);
                    tm.getTopics().put(obj.getId(), obj);
                    //tm.addTopic(obj);
                }
            } else
            {
                System.out.println("Error al asignar " + xtmObject);
            }
            obj.getDbdata().setActive(1);
            m_objStack.push(obj);
            auxTopic = obj;
        } else if (xtmObject.equals(INSTANCEOF))
        {
            String id = getXTMAttribute(ID, atts);
            InstanceOf obj = new InstanceOf();
            obj.setId(id);
            if (m_objStack.peek() instanceof Topic)
            {
                //((Topic)m_objStack.peek()).getInstanceOf().add(obj);
            } else if (m_objStack.peek() instanceof Occurrence)
            {
                ((Occurrence) m_objStack.peek()).setInstanceOf(obj);
            } else if (m_objStack.peek() instanceof Association)
            {
                ((Association) m_objStack.peek()).setInstanceOf(obj);
            } else
            {
                System.out.println("Error al leer tipo " + xtmObject);
            }
            m_objStack.push(obj);
        } else if (xtmObject.equals(TOPICREF))
        {
            String id = getXTMAttribute(ID, atts);
            String href = getXLinkAttribute(HREF, atts);
            if (href != null)
            {
                if (href.startsWith("#")) href = href.substring(1);
                Topic obj = (Topic) auxMap.getTopics().get(href);
                if (obj == null)
                {
                    obj = new Topic();
                    obj.setId(href);

                    obj.setMap(auxMap);
                    auxMap.getTopics().put(obj.getId(), obj);
                    //auxMap.addTopic(obj);
                }
                if (m_objStack.peek() instanceof InstanceOf)
                {
                    Object aux = m_objStack.pop();
                    if (m_objStack.peek() instanceof Topic)
                    {
                        ((Topic) m_objStack.peek()).addType(obj);
                        m_objStack.push(aux);
                    } else
                    {
                        m_objStack.push(aux);
                        ((InstanceOf) m_objStack.peek()).setTopicRef(obj);
                    }
                } else if (m_objStack.peek() instanceof Parameters)
                {
                    ((Parameters) m_objStack.peek()).getTopicRefs().put(href, obj);
                } else if (m_objStack.peek() instanceof Scope)
                {
                    ((Scope) m_objStack.peek()).getTopicRefs().put(href, obj);
                } else if (m_objStack.peek() instanceof RoleSpec)
                {
                    ((RoleSpec) m_objStack.peek()).setTopicRef(obj);
                } else if (m_objStack.peek() instanceof Member)
                {
                    ((Member) m_objStack.peek()).getTopicRefs().put(href, obj);
                } else if (m_objStack.peek() instanceof SubjectIdentity)
                {
                    ((SubjectIdentity) m_objStack.peek()).getTopicRefs().put(href, obj);
                } else
                {
                    System.out.println("Error al asignar " + xtmObject);
                }
            } else
            {
                System.out.println("Error al leer tipo " + xtmObject);
            }
            m_objStack.push(TOPICREF);
        } else if (xtmObject.equals(BASENAME))
        {
            String id = getXTMAttribute(ID, atts);
            BaseName obj = new BaseName();
            obj.setId(id);
            if (m_objStack.peek() instanceof Topic)
            {
                ((Topic) m_objStack.peek()).getBaseNames().add(obj);
            } else if (m_objStack.peek() instanceof Occurrence)
            {
                ((Occurrence) m_objStack.peek()).getBaseNames().add(obj);
            } else
            {
                System.out.println("Error al asignar " + xtmObject);
            }
            m_objStack.push(obj);
        } else if (xtmObject.equals(BASENAMESTRING))
        {
            m_id = getXTMAttribute(ID, atts);
            m_data = new StringBuffer();
            m_getData = true;
            m_objStack.push(BASENAMESTRING);
        } else if (xtmObject.equals(VARIANT))
        {
            String id = getXTMAttribute(ID, atts);
            Variant obj = new Variant();
            obj.setId(id);
            if (m_objStack.peek() instanceof BaseName)
            {
                ((BaseName) m_objStack.peek()).getVariants().add(obj);
            } else if (m_objStack.peek() instanceof Variant)
            {
                ((Variant) m_objStack.peek()).getVariants().add(obj);
            } else
            {
                System.out.println("Error al asignar " + xtmObject);
            }
            m_objStack.push(obj);
        } else if (xtmObject.equals(VARIANTNAME))
        {
            String id = getXTMAttribute(ID, atts);
            VariantName obj = new VariantName();
            obj.setId(id);
            if (m_objStack.peek() instanceof Variant)
            {
                ((Variant) m_objStack.peek()).setVariantName(obj);
            } else
            {
                System.out.println("Error al asignar " + xtmObject);
            }
            m_objStack.push(obj);
        } else if (xtmObject.equals(RESOURCEDATA))
        {
            m_id = getXTMAttribute(ID, atts);
            m_data = new StringBuffer();
            m_getData = true;
            m_objStack.push(RESOURCEDATA);
        } else if (xtmObject.equals(SUBJECTIDENTITY))
        {
            String id = getXTMAttribute(ID, atts);
            SubjectIdentity obj = new SubjectIdentity();
            obj.setId(id);
            if (m_objStack.peek() instanceof Topic)
            {
                ((Topic) m_objStack.peek()).setSubjectIdentity(obj);
            } else
            {
                System.out.println("Error al asignar " + xtmObject);
            }
            m_objStack.push(obj);
        } else if (xtmObject.equals(RESOURCEREF))
        {
            String id = getXTMAttribute(ID, atts);
            String href = getXLinkAttribute(HREF, atts);
            if (href != null)
            {
                if (m_objStack.peek() instanceof SubjectIdentity)
                {
                    ((SubjectIdentity) m_objStack.peek()).setResourceRef(href);
                } else if (m_objStack.peek() instanceof VariantName)
                {
                    ((VariantName) m_objStack.peek()).setResourceRef(href);
                } else if (m_objStack.peek() instanceof Occurrence)
                {
                    ((Occurrence) m_objStack.peek()).setResourceRef(href);
                } else if (m_objStack.peek() instanceof Scope)
                {
                    ((Scope) m_objStack.peek()).getResourceRefs().add(href);
                } else if (m_objStack.peek() instanceof Member)
                {
                    ((Member) m_objStack.peek()).getResourceRefs().add(href);
                } else
                {
                    System.out.println("Error al asignar " + xtmObject);
                }
            } else
            {
                System.out.println("Error al leer tipo " + xtmObject);
            }
            m_objStack.push(RESOURCEREF);
        } else if (xtmObject.equals(SUBJECTINDICATORREF))
        {
            String id = getXTMAttribute(ID, atts);
            String href = getXLinkAttribute(HREF, atts);
            if (href != null)
            {
                if (m_objStack.peek() instanceof SubjectIdentity)
                {
                    ((SubjectIdentity) m_objStack.peek()).getSubjectIndicatorRefs().add(href);
                } else if (m_objStack.peek() instanceof Parameters)
                {
                    ((Parameters) m_objStack.peek()).getSubjectIndicatorRefs().add(href);
                } else if (m_objStack.peek() instanceof Scope)
                {
                    ((Scope) m_objStack.peek()).getSubjectIndicatorRefs().add(href);
                } else if (m_objStack.peek() instanceof InstanceOf)
                {
                    ((InstanceOf) m_objStack.peek()).setSubjectIndicatorRef(href);
                } else if (m_objStack.peek() instanceof RoleSpec)
                {
                    ((RoleSpec) m_objStack.peek()).setSubjectIndicatorRef(href);
                } else if (m_objStack.peek() instanceof Member)
                {
                    ((Member) m_objStack.peek()).getResourceRefs().add(href);
                } else
                {
                    System.out.println("Error al asignar " + xtmObject);
                }
            } else
            {
                System.out.println("Error al leer tipo " + xtmObject);
            }
            m_objStack.push(SUBJECTINDICATORREF);
        } else if (xtmObject.equals(OCCURRENCE))
        {
            String id = getXTMAttribute(ID, atts);
            Occurrence obj = new Occurrence();
            if(id!=null)obj.setId(id);
            if (m_objStack.peek() instanceof Topic)
            {
                ((Topic) m_objStack.peek()).getOccurrences().add(obj);
            } else
            {
                System.out.println("Error al leer tipo " + xtmObject);
            }
            obj.getDbdata().setActive(1);
            m_objStack.push(obj);
            auxOccurrence = obj;
        } else if (xtmObject.equals(ASSOCIATION))
        {
            String id = getXTMAttribute(ID, atts);
            Association obj = new Association();
            if(id!=null)obj.setId(id);
            if (m_objStack.peek() instanceof TopicMap)
            {
                ((TopicMap) m_objStack.peek()).addAssociation(obj, false);
            } else
            {
                System.out.println("Error al leer tipo " + xtmObject);
            }
            m_objStack.push(obj);
            auxAssociation = obj;
        } else if (xtmObject.equals(MEMBER))
        {
            String id = getXTMAttribute(ID, atts);
            Member obj = new Member();
            obj.setId(id);
            if (m_objStack.peek() instanceof Association)
            {
                ((Association) m_objStack.peek()).getMembers().add(obj);
            } else
            {
                System.out.println("Error al leer tipo " + xtmObject);
            }
            m_objStack.push(obj);
        } else if (xtmObject.equals(SCOPE))
        {
            String id = getXTMAttribute(ID, atts);
            Scope obj = new Scope();
            obj.setId(id);
            if (m_objStack.peek() instanceof BaseName)
            {
                ((BaseName) m_objStack.peek()).setScope(obj);
            } else if (m_objStack.peek() instanceof Occurrence)
            {
                ((Occurrence) m_objStack.peek()).setScope(obj);
            } else if (m_objStack.peek() instanceof Association)
            {
                ((Association) m_objStack.peek()).setScope(obj);
            } else
            {
                System.out.println("Error al leer tipo " + xtmObject);
            }
            m_objStack.push(obj);
        } else if (xtmObject.equals(ROLESPEC))
        {
            String id = getXTMAttribute(ID, atts);
            RoleSpec obj = new RoleSpec();
            obj.setId(id);
            if (m_objStack.peek() instanceof Member)
            {
                ((Member) m_objStack.peek()).setRoleSpec(obj);
            } else
            {
                System.out.println("Error al leer tipo " + xtmObject);
            }
            m_objStack.push(obj);
        } else if (xtmObject.equals(PARAMETERS))
        {
            String id = getXTMAttribute(ID, atts);
            Parameters obj = new Parameters();
            obj.setId(id);
            if (m_objStack.peek() instanceof Variant)
            {
                ((Variant) m_objStack.peek()).setParameters(obj);
            } else
            {
                System.out.println("Error al leer tipo " + xtmObject);
            }
            m_objStack.push(obj);
        } else
        {
            m_objStack.push(xtmObject);
            System.out.println("Elemento no soportado...");
            for (int x = 0; x < atts.getLength(); x++)
            {
                System.out.println("-->name:" + atts.getLocalName(x));
                System.out.println("-->type:" + atts.getType(x));
                System.out.println("-->value:" + atts.getValue(x));
            }
        }


    }

    public void endElement(String nsURI, String localName, String qName) throws SAXException
    {
        Object obj = m_objStack.pop();

        if (obj.equals(BASENAMESTRING))
        {
            m_getData = false;
            if (m_objStack.peek() instanceof BaseName)
            {
                ((BaseName) m_objStack.peek()).setBaseNameString(m_data.toString());
            } else
            {
                System.out.println("Error al asignar " + BASENAMESTRING);
            }
        } else if (obj.equals(RESOURCEDATA))
        {
            m_getData = false;
            if (m_objStack.peek() instanceof VariantName)
            {
                ((VariantName) m_objStack.peek()).setResourceData(m_data.toString());
            } else if (m_objStack.peek() instanceof Occurrence)
            {
                //System.out.println(AFUtils.replaceXMLTags(m_data.toString()));
                ((Occurrence) m_objStack.peek()).setResourceData(m_data.toString());
            } else
            {
                System.out.println("Error al asignar " + RESOURCEDATA);
            }
        }

    }

    protected String getXTMAttribute(String localName, Attributes atts)
    {
        String ret = atts.getValue(XMLNS, localName);
        if (ret == null) ret = atts.getValue(localName);
        return ret;
    }

    protected String getXTMObject(String nsURI, String localName, String qName, Attributes atts)
    {
        String ret = null;
        if (nsURI.equals(XMLNS))
        {
            ret = localName;
        } else
        {
            ret = atts.getValue(XMLNS, CLASS);
            if (ret == null) ret = qName;
        }
        return ret;
    }

    protected String getXLinkAttribute(String localName, Attributes atts)
    {
        return atts.getValue(XLINK, localName);
    }

    /** Getter for property auxMap.
     * @return Value of property auxMap.
     */
    public TopicMap getTopicMap()
    {
        return auxMap;
    }

    public void setTopicMap(TopicMap topicmap)
    {
        auxMap = topicmap;
    }

    /** Getter for property auxTopic.
     * @return Value of property auxTopic.
     */
    public Topic getTopic()
    {
        return auxTopic;
    }

    /** Getter for property auxOccurrence.
     * @return Value of property auxOccurrence.
     */
    public Occurrence getOccurrence()
    {
        return auxOccurrence;
    }

    /** Getter for property auxAssociation.
     * @return Value of property auxAssociation.
     */
    public Association getAssociation()
    {
        return auxAssociation;
    }

    public static TopicMap readTopicMap(InputStream in) throws IOException, SAXException
    {
        XTMParser reader = new XTMParser();
        SAXParser parser = new SAXParser();
        parser.setContentHandler(reader);
        parser.parse(new InputSource(in));
        return reader.getTopicMap();
    }

    public TopicMap parseTopicMap(RecTopicMap rectopicmap) throws IOException, SAXException
    {
        XTMParser reader = new XTMParser();
        SAXParser parser = new SAXParser();
        parser.setContentHandler(reader);
        parser.parse(new InputSource(rectopicmap.getXml()));
        return reader.getTopicMap();
    }

    public Topic parseTopic(RecTopic rectopic) throws IOException, SAXException
    {
        XTMParser reader = new XTMParser();
        SAXParser parser = new SAXParser();
        parser.setContentHandler(reader);
        parser.parse(new InputSource(rectopic.getXml()));
        return reader.getTopic();
    }

    /** Getter for property startFrom.
     * @return Value of property startFrom.
     */
    public java.lang.Object getStartFrom()
    {
        return startFrom;
    }

    /** Setter for property startFrom.
     * @param startFrom New value of property startFrom.
     */
    public void setStartFrom(java.lang.Object startFrom)
    {
        this.startFrom = startFrom;
    }

}
