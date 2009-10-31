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
 * VariantName.java
 *
 * Created on 22 de abril de 2002, 15:53
 */

package com.infotec.topicmaps;

import java.util.*;

/**
 * Objeto <B>VariantName</B> que es parte de la implementacion del estandar XTM de TopicMaps.
 * <BR>
 * A <B>variant name</b> is an alternative form of a base name, that is optimized for a particular computational purpose,
 * such as sorting or display. It may be any kind of a resource, including a string. An application chooses among variant names by evaluating their parameters.
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class VariantName
{

    protected String m_id;
    protected String m_resourceData;
    protected String m_resourceRef;
    protected boolean m_isResourceRef;


    /** Creates new VariantName */
    public VariantName()
    {
    }
    
    /** Creates new VariantName */
    public VariantName(String resourceData)
    {
        setResourceData(resourceData);
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
    }

    /**
     * @return  */
    public String getResource()
    {
        if (m_isResourceRef)
            return m_resourceRef;
        else
            return m_resourceData;
    }

}
