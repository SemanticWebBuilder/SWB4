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
 * SubjectIdentity.java
 *
 * Created on 22 de abril de 2002, 15:54
 */

package com.infotec.topicmaps;

import java.util.*;

/**
 * Objeto <B>SubjectIdentity</B> que es parte de la implementacion del estandar XTM de TopicMaps.
 * <BR>
 * Subject identity is the means whereby it can be established which subject is reified by a particular topic. When two topics have the same subject identity, they are considered to be �about� the same thing, and must therefore be merged. Because of the need to be able to merge topic maps � in effect, to interchange their semantics � the topic map paradigm goes to great lengths to make it possible to establish the identity of a topic (and hence its subject) as robustly as possible.<BR>
 * Subject identity can be established in one of two ways:<BR>
 * 1.- By addressing the subject directly. This is only possible when the subject is an addressable information resource.<BR>
 * 2.- By indicating the subject via a subject indicator (see below).<BR>
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class SubjectIdentity
{

    protected String m_id;
    protected String m_resourceRef;

    protected HashMap m_topicrefs;
    protected ArrayList m_subjectindicatorrefs;


    /** Creates new Member */
    public SubjectIdentity()
    {
        m_topicrefs = new HashMap();
        m_subjectindicatorrefs = new ArrayList();
    }
    
    public SubjectIdentity(String resourceRef)
    {
        this();
        this.m_resourceRef=resourceRef;
    }

    /** Getter for property m_id.
     * @return Value of property m_id.
     */
    public String getId()
    {
        return m_id;
    }
    
    public String toString()
    {
        return m_resourceRef;
    }

    /** Setter for property m_id.
     * @param id New value of property m_id.
     */
    public void setId(String id)
    {
        this.m_id = id;
    }

    /** Getter for property m_resourceRef.
     * @return Value of property m_resourceRef.
     */
    public java.lang.String getResourceRef()
    {
        return m_resourceRef;
    }

    /** Setter for property m_resourceRef.
     * @param resourceRefs New value of property m_resourceRef.
     */
    public void setResourceRef(java.lang.String resourceRefs)
    {
        this.m_resourceRef = resourceRefs;
    }

    /** Getter for property m_topicref.
     * @return Value of property m_topicref.
     */
    public java.util.HashMap getTopicRefs()
    {
        return m_topicrefs;
    }

    /** Setter for property m_topicref.
     * @param topicrefs New value of property m_topicref.
     */
    public void setTopicRefs(java.util.HashMap topicrefs)
    {
        this.m_topicrefs = topicrefs;
    }

    /** Getter for property m_subjectindicatorref.
     * @return Value of property m_subjectindicatorref.
     */
    public java.util.ArrayList getSubjectIndicatorRefs()
    {
        return m_subjectindicatorrefs;
    }

    /** Setter for property m_subjectindicatorref.
     * @param subjectindicatorrefs  */
    public void setSubjectIndicatorRefs(java.util.ArrayList subjectindicatorrefs)
    {
        this.m_subjectindicatorrefs = subjectindicatorrefs;
    }

}
