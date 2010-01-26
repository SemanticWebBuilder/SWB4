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
 * Member.java
 *
 * Created on 22 de abril de 2002, 15:54
 */

package com.infotec.topicmaps;

import java.util.*;

/**
 * Objeto <B>Member</B> que es parte de la implementacion del estandar XTM de TopicMaps.
 * <BR>
 * A <B>member</B> is a set of topics that play a particular role in an
 * association.
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class Member
{

    protected String m_id;
    protected RoleSpec m_roleSpec;

    protected HashMap m_topicrefs;
    protected ArrayList m_subjectindicatorrefs;
    protected ArrayList m_resourceRefs;


    /** Creates new Member */
    public Member()
    {
        m_topicrefs = new HashMap();
        m_subjectindicatorrefs = new ArrayList();
        m_resourceRefs = new ArrayList();
    }
    
    //regresa otro objeto con la misma informacion que este
    public Member getCopy()
    {
        Member aux=new Member();
        aux.m_id=m_id;
        if(m_roleSpec!=null)aux.m_roleSpec=m_roleSpec.getCopy();
        aux.m_topicrefs=new HashMap(m_topicrefs);
        aux.m_subjectindicatorrefs=new ArrayList(m_subjectindicatorrefs);
        aux.m_resourceRefs=new ArrayList(m_resourceRefs);        
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

    /** Getter for property m_roleSpec.
     * @return Value of property m_roleSpec.
     */
    public RoleSpec getRoleSpec()
    {
        return m_roleSpec;
    }

    /** Setter for property m_roleSpec.
     * @param roleSpec New value of property m_roleSpec.
     */
    public void setRoleSpec(RoleSpec roleSpec)
    {
        this.m_roleSpec = roleSpec;
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

    /** Getter for property m_resourceRefs.
     * @return Value of property m_resourceRefs.
     */
    public java.util.ArrayList getResourceRefs()
    {
        return m_resourceRefs;
    }

    /** Setter for property m_resourceRefs.
     * @param resourceRefs  */
    public void setResourceRefs(java.util.ArrayList resourceRefs)
    {
        this.m_resourceRefs = resourceRefs;
    }

}

