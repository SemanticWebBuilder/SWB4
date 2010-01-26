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
 * BaseName.java
 *
 * Created on 22 de abril de 2002, 15:53
 */

package com.infotec.topicmaps;

import java.util.*;

/**
 * Objeto <B>BaseName</B> que es parte de la implementacion del estandar XTM de TopicMaps.
 * <BR>
 * A base name is the base form of a topic name; it is always a string.
 * When an application chooses to use a particular topic name to label a topic,
 * the base name provides the string for the application to use unless a variant exists
 * that is deemed to be more apposite in the processing context.
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class BaseName
{

    protected String m_id;

    protected Scope m_scope;
    protected String m_baseNameString;
    protected ArrayList m_variants;    //Variant

    /** Creates new BaseName */
    public BaseName()
    {
        m_variants = new ArrayList();
    }

    /**
     * @param baseNameString  */
    public BaseName(String baseNameString)
    {
        this();
        this.m_baseNameString = baseNameString;
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

    /** Getter for property m_baseName.
     * @return Value of property m_baseName.
     */
    public String getBaseNameString()
    {
        return m_baseNameString;
    }

    /** Setter for property m_baseNameString.
     * @param baseNameString New value of property m_baseNameString.
     */
    public void setBaseNameString(String baseNameString)
    {
        this.m_baseNameString = baseNameString;
    }

    /** Getter for property m_variants.
     * @return Value of property m_variants.
     */
    public ArrayList getVariants()
    {
        return m_variants;
    }

    /** Setter for property m_variants.
     * @param variants New value of property m_variants.
     */
    public void setVariants(ArrayList variants)
    {
        this.m_variants = variants;
    }

}
