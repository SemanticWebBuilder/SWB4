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
 * RoleSpec.java
 *
 * Created on 22 de abril de 2002, 15:54
 */

package com.infotec.topicmaps;

import java.util.*;

/**
 * Objeto <B>RoleSpec</B> que es parte de la implementacion del estandar XTM de TopicMaps.
 * <BR>
 * The concept of <B>role</B> expresses the nature of a topic's involvement as a member of an
 * association.
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class RoleSpec
{

    protected String m_id;

    protected Topic m_topicref;
    protected String m_subjectindicatorref;


    /** Creates new Member */
    public RoleSpec()
    {
    }
    
    //regresa otro objeto con la misma informacion que este
    public RoleSpec getCopy()
    {
        RoleSpec aux=new RoleSpec();
        aux.m_id=m_id;
        aux.m_topicref=m_topicref;
        aux.m_subjectindicatorref=m_subjectindicatorref;
        return aux;
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
    }

    /** Getter for property m_topicref.
     * @return Value of property m_topicref.
     */
    public Topic getTopicRef()
    {
        return m_topicref;
    }

    /** Setter for property m_topicref.
     * @param topicref New value of property m_topicref.
     */
    public void setTopicRef(Topic topicref)
    {
        this.m_topicref = topicref;
    }

    /** Getter for property m_subjectindicatorref.
     * @return Value of property m_subjectindicatorref.
     */
    public java.lang.String getSubjectIndicatorRef()
    {
        return m_subjectindicatorref;
    }

    /** Setter for property m_subjectindicatorref.
     * @param subjectindicatorref New value of property m_subjectindicatorref.
     */
    public void setSubjectIndicatorRef(java.lang.String subjectindicatorref)
    {
        this.m_subjectindicatorref = subjectindicatorref;
    }

}
