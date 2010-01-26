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
 * XTMEncoder.java
 *
 * Created on 1 de julio de 2002, 10:25
 */

package com.infotec.topicmaps.util;

import java.io.*;
import java.util.*;

import com.infotec.topicmaps.*;
import com.infotec.topicmaps.bean.*;
import org.w3c.dom.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;


/** objeto: Codifica un TopicMap al formato XTM
 * @author Javier Solis Gonzalez
 * @version
 */
public class XTMEncoder
{
    protected OutputStream out = null;
    protected Document dom = null;
    protected DocumentBuilderFactory dbf = null;
    protected DocumentBuilder db = null;

    /** Creates new XTMEncoder */
    public XTMEncoder()
    {
        dbf = null;
        db = null;
        try
        {
            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            dom = db.newDocument();
        } catch (Exception e)
        {
            TopicMgr.getInstance().log(e, "Error creando Document...", true);
        }
    }

    public XTMEncoder(OutputStream out)
    {
        this();
        this.out = out;
    }

    public void writeXml()
    {
        try
        {
            java.io.OutputStreamWriter osw = new java.io.OutputStreamWriter(out, TopicMgr.getInstance().getEncode());
            StreamResult streamResult = new StreamResult(osw);
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, TopicMgr.getInstance().getEncode());
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(dom);
            transformer.transform(source, streamResult);
        } catch (Exception e)
        {
            TopicMgr.getInstance().log(e, "Error al Transformar TopicMap a xml...", true);
        }
    }

    public void writeXml(OutputStream out)
    {
        try
        {
            java.io.OutputStreamWriter osw = new java.io.OutputStreamWriter(out, TopicMgr.getInstance().getEncode());
            StreamResult streamResult = new StreamResult(osw);
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, TopicMgr.getInstance().getEncode());
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(dom);
            transformer.transform(source, streamResult);
        } catch (Exception e)
        {
            TopicMgr.getInstance().log(e, "Error al Transformar TopicMap a xml...", true);
        }
    }

    public void encodeTopicMap(TopicMap tm)
    {
        encodeTopicMap(tm, true);
    }

    public void encodeTopicMap(TopicMap tm, boolean child)
    {
        try
        {
            Element map = dom.createElement(XTMParser.TOPICMAP);
            map.setAttribute("xmlns", tm.getXmlns());
            map.setAttribute("xmlns:xlink", tm.getXmlns_xlink());
            if (tm.getXml_base() != null) map.setAttribute("xml:base", tm.getXml_base());
            dom.appendChild(map);

            if (child)
            {
                Iterator en = tm.getTopics().values().iterator();
                while (en.hasNext())
                {
                    Topic ref = (Topic) en.next();
                    this.encodeTopic(ref, map);
                }

                en = tm.getAssociations().values().iterator();
                while (en.hasNext())
                {
                    Association ref = (Association) en.next();
                    this.encodeAssociation(ref, map);
                }
            }

        } catch (Exception e)
        {
            TopicMgr.getInstance().log(e, "Error al Codificar TopicMap a Document...", true);
        }

    }

    public void encodeTopic(Topic obj, Element parent)
    {
        encodeTopic(obj, parent, true);
    }

    public void encodeTopic(Topic obj, Element parent, boolean occurrences)
    {
        try
        {
            Element ele = dom.createElement(XTMParser.TOPIC);
            if (obj.getId() != null) ele.setAttribute("id", obj.getId());

            Iterator en = obj.getInstanceOf().iterator();
            while (en.hasNext())
            {
                InstanceOf ref = (InstanceOf) en.next();
                encodeInstanceOf(ref, ele);
            }

            en = obj.getBaseNames().iterator();
            while (en.hasNext())
            {
                BaseName ref = (BaseName) en.next();
                encodeBaseName(ref, ele);
            }

            encodeSubjectIdentity(obj.getSubjectIdentity(), ele);

            if (occurrences)
            {
                en = obj.getOccurrences().iterator();
                while (en.hasNext())
                {
                    Occurrence ref = (Occurrence) en.next();
                    encodeOccurrence(ref, ele);
                }
            }

            if (parent == null)
            {
                ele.setAttribute("xmlns", XTMParser.XMLNS);
                ele.setAttribute("xmlns:xlink", XTMParser.XLINK);
                dom.appendChild(ele);
            } else
                parent.appendChild(ele);
        } catch (Exception e)
        {
            TopicMgr.getInstance().log(e, "Error al crear elemento, XTMEncoder:encoderTopico", true);
        }
    }

    public void encodeAssociation(Association obj, Element parent)
    {
        try
        {
            Element ele = dom.createElement(XTMParser.ASSOCIATION);
            if (obj.getId() != null) ele.setAttribute("id", obj.getId());

            encodeInstanceOf(obj.getInstanceOf(), ele);

            Iterator en = obj.getMembers().iterator();
            while (en.hasNext())
            {
                Member ref = (Member) en.next();
                encodeMember(ref, ele);
            }

            encodeScope(obj.getScope(), ele);

            if (parent == null)
            {
                ele.setAttribute("xmlns", XTMParser.XMLNS);
                ele.setAttribute("xmlns:xlink", XTMParser.XLINK);
                dom.appendChild(ele);
            } else
                parent.appendChild(ele);
        } catch (Exception e)
        {
            TopicMgr.getInstance().log(e, "Error al crear elemento, XTMEncoder:encodeAssociation", true);
        }
    }

    public void encodeOccurrence(Occurrence obj, Element parent)
    {
        try
        {
            Element ele = dom.createElement(XTMParser.OCCURRENCE);
            if (obj.getId() != null) ele.setAttribute("id", obj.getId());

            encodeInstanceOf(obj.getInstanceOf(), ele);

            Iterator en = obj.getBaseNames().iterator();
            while (en.hasNext())
            {
                BaseName ref = (BaseName) en.next();
                encodeBaseName(ref, ele);
            }

            encodeResourceData(obj.getResourceData(), ele);
            encodeResourceRef(obj.getResourceRef(), ele);
            encodeScope(obj.getScope(), ele);

            if (parent == null)
            {
                ele.setAttribute("xmlns", XTMParser.XMLNS);
                ele.setAttribute("xmlns:xlink", XTMParser.XLINK);
                dom.appendChild(ele);
            } else
                parent.appendChild(ele);
        } catch (Exception e)
        {
            TopicMgr.getInstance().log(e, "Error al crear elemento, XTMEncoder:encodeOccurrence", true);
        }
    }

    public void encodeInstanceOf(InstanceOf obj, Element parent)
    {
        if (obj == null) return;
        try
        {
            Element ele = dom.createElement(XTMParser.INSTANCEOF);
            if (obj.getId() != null) ele.setAttribute("id", obj.getId());

            encodeTopicRef(obj.getTopicRef(), ele);
            encodeSubjectIndicatorRefs(obj.getSubjectIndicatorRef(), ele);

            parent.appendChild(ele);
        } catch (Exception e)
        {
            TopicMgr.getInstance().log(e, "Error al crear elemento, XTMEncoder:encodeInstanceOf", true);
        }
    }

    public void encodeMember(Member obj, Element parent)
    {
        try
        {
            Element ele = dom.createElement(XTMParser.MEMBER);
            if (obj.getId() != null) ele.setAttribute("id", obj.getId());

            encodeRoleSpec(obj.getRoleSpec(), ele);

            Iterator en = obj.getTopicRefs().values().iterator();
            while (en.hasNext())
            {
                Topic ref = (Topic) en.next();
                encodeTopicRef(ref, ele);
            }

            en = obj.getResourceRefs().iterator();
            while (en.hasNext())
            {
                String ref = (String) en.next();
                encodeResourceRef(ref, ele);
            }

            en = obj.getSubjectIndicatorRefs().iterator();
            while (en.hasNext())
            {
                String ref = (String) en.next();
                encodeSubjectIndicatorRefs(ref, ele);
            }

            if (parent == null)
                dom.appendChild(ele);
            else
                parent.appendChild(ele);
        } catch (Exception e)
        {
            TopicMgr.getInstance().log(e, "Error al crear elemento, XTMEncoder:encodeMember", true);
        }
    }

    public void encodeRoleSpec(RoleSpec obj, Element parent)
    {
        if (obj == null) return;
        try
        {
            Element ele = dom.createElement(XTMParser.ROLESPEC);
            if (obj.getId() != null) ele.setAttribute("id", obj.getId());

            encodeTopicRef(obj.getTopicRef(), ele);
            encodeSubjectIndicatorRefs(obj.getSubjectIndicatorRef(), ele);

            parent.appendChild(ele);
        } catch (Exception e)
        {
            TopicMgr.getInstance().log(e, "Error al crear elemento, XTMEncoder:encodeRoleSpec", true);
        }
    }

    public void encodeBaseName(BaseName obj, Element parent)
    {
        if (obj == null) return;
        try
        {
            Element ele = dom.createElement(XTMParser.BASENAME);
            if (obj.getId() != null) ele.setAttribute("id", obj.getId());

            encodeBaseNameString(obj.getBaseNameString(), ele);

            Iterator en = obj.getVariants().iterator();
            while (en.hasNext())
            {
                Variant ref = (Variant) en.next();
                encodeVariant(ref, ele);
            }

            encodeScope(obj.getScope(), ele);

            parent.appendChild(ele);
        } catch (Exception e)
        {
            TopicMgr.getInstance().log(e, "Error al crear elemento, XTMEncoder:encodeBaseName", true);
        }
    }

    public void encodeScope(Scope obj, Element parent)
    {
        if (obj == null) return;
        try
        {
            Element ele = dom.createElement(XTMParser.SCOPE);
            if (obj.getId() != null) ele.setAttribute("id", obj.getId());

            Iterator en = obj.getTopicRefs().values().iterator();
            while (en.hasNext())
            {
                Topic ref = (Topic) en.next();
                encodeTopicRef(ref, ele);
            }

            en = obj.getSubjectIndicatorRefs().iterator();
            while (en.hasNext())
            {
                String ref = (String) en.next();
                encodeSubjectIndicatorRefs(ref, ele);
            }

            en = obj.getResourceRefs().iterator();
            while (en.hasNext())
            {
                String ref = (String) en.next();
                encodeResourceRef(ref, ele);
            }

            parent.appendChild(ele);
        } catch (Exception e)
        {
            TopicMgr.getInstance().log(e, "Error al crear elemento, XTMEncoder:encodeScope", true);
        }
    }

    public void encodeVariant(Variant obj, Element parent)
    {
        if (obj == null) return;
        try
        {
            Element ele = dom.createElement(XTMParser.VARIANT);
            if (obj.getId() != null) ele.setAttribute("id", obj.getId());

            encodeVariantName(obj.getVariantName(), ele);
            encodeParameters(obj.getParameters(), ele);

            Iterator en = obj.getVariants().iterator();
            while (en.hasNext())
            {
                Variant ref = (Variant) en.next();
                encodeVariant(ref, ele);
            }

            parent.appendChild(ele);
        } catch (Exception e)
        {
            TopicMgr.getInstance().log(e, "Error al crear elemento, XTMEncoder:encodeVariant", true);
        }
    }

    public void encodeVariantName(VariantName obj, Element parent)
    {
        if (obj == null) return;
        try
        {
            Element ele = dom.createElement(XTMParser.VARIANTNAME);
            if (obj.getId() != null) ele.setAttribute("id", obj.getId());

            encodeResourceData(obj.getResourceData(), ele);
            encodeResourceRef(obj.getResourceRef(), ele);

            parent.appendChild(ele);
        } catch (Exception e)
        {
            TopicMgr.getInstance().log(e, "Error al crear elemento, XTMEncoder:encodeVariantName", true);
        }
    }

    public void encodeParameters(Parameters obj, Element parent)
    {
        if (obj == null) return;
        try
        {
            Element ele = dom.createElement(XTMParser.PARAMETERS);
            if (obj.getId() != null) ele.setAttribute("id", obj.getId());

            Iterator en = obj.getTopicRefs().values().iterator();
            while (en.hasNext())
            {
                Topic ref = (Topic) en.next();
                encodeTopicRef(ref, ele);
            }

            en = obj.getSubjectIndicatorRefs().iterator();
            while (en.hasNext())
            {
                String ref = (String) en.next();
                encodeSubjectIndicatorRefs(ref, ele);
            }

            parent.appendChild(ele);
        } catch (Exception e)
        {
            TopicMgr.getInstance().log(e, "Error al crear elemento, XTMEncoder:encodeParameters", true);
        }
    }

    public void encodeSubjectIdentity(SubjectIdentity obj, Element parent)
    {
        if (obj == null) return;
        try
        {
            Element ele = dom.createElement(XTMParser.SUBJECTIDENTITY);
            if (obj.getId() != null) ele.setAttribute("id", obj.getId());

            encodeResourceRef(obj.getResourceRef(), ele);

            Iterator en = obj.getTopicRefs().values().iterator();
            while (en.hasNext())
            {
                Topic ref = (Topic) en.next();
                encodeTopicRef(ref, ele);
            }

            en = obj.getSubjectIndicatorRefs().iterator();
            while (en.hasNext())
            {
                String ref = (String) en.next();
                encodeSubjectIndicatorRefs(ref, ele);
            }

            parent.appendChild(ele);
        } catch (Exception e)
        {
            TopicMgr.getInstance().log(e, "Error al crear elemento, XTMEncoder:encodeSubjectIdentity", true);
        }
    }

    public void encodeTopicRef(Topic obj, Element parent)
    {
        if (obj == null) return;
        try
        {
            Element ele = dom.createElement(XTMParser.TOPICREF);
            ele.setAttribute("xlink:href", "#" + obj.getId());
            parent.appendChild(ele);

        } catch (Exception e)
        {
            TopicMgr.getInstance().log(e, "Error al crear elemento, XTMEncoder:encodeTopicRef", true);
        }
    }

    public void encodeResourceRef(String obj, Element parent)
    {
        if (obj == null) return;
        try
        {
            Element ele = dom.createElement(XTMParser.RESOURCEREF);
            ele.setAttribute("xlink:href", obj);
            parent.appendChild(ele);

        } catch (Exception e)
        {
            TopicMgr.getInstance().log(e, "Error al crear elemento, XTMEncoder:encodeResourceRef", true);
        }
    }

    public void encodeResourceData(String obj, Element parent)
    {
        if (obj == null) return;
        try
        {
            Element ele = dom.createElement(XTMParser.RESOURCEDATA);
            ele.appendChild(dom.createTextNode(obj));
            parent.appendChild(ele);
        } catch (Exception e)
        {
            TopicMgr.getInstance().log(e, "Error al crear elemento, XTMEncoder:encodeResourceData", true);
        }
    }

    public void encodeSubjectIndicatorRefs(String obj, Element parent)
    {
        if (obj == null) return;
        try
        {
            Element ele = dom.createElement(XTMParser.SUBJECTINDICATORREF);
            ele.setAttribute("xlink:href", obj);
            parent.appendChild(ele);

        } catch (Exception e)
        {
            TopicMgr.getInstance().log(e, "Error al crear elemento, XTMEncoder:encodeSubjectIndicatorRefs", true);
        }
    }

    public void encodeBaseNameString(String obj, Element parent)
    {
        if (obj == null) return;
        try
        {
            Element ele = dom.createElement(XTMParser.BASENAMESTRING);
            ele.appendChild(dom.createTextNode(obj));
            parent.appendChild(ele);
        } catch (Exception e)
        {
            TopicMgr.getInstance().log(e, "Error al crear elemento, XTMEncoder:encodeBaseNameString", true);
        }
    }

    public void clearDocement()
    {
        dom = db.newDocument();
    }

    public void close()
    {
        try
        {
            if (out != null) out.close();
        } catch (Exception e)
        {
            TopicMgr.getInstance().log(e, "Error al cerrar outputstream del encoder...", true);
        }
    }
}
