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
 * Variant.java
 *
 * Created on 22 de abril de 2002, 15:55
 */

package com.infotec.topicmaps;

import java.util.*;

/**
 * Objeto <B>Variant</B> que es parte de la implementacion del estandar XTM de TopicMaps.
 * <BR>
 * The <B>variant</B> element is an alternate form of a topic's base name appropriate for a processing context specified by the variant's <B>parameters</B> child element. Among these contexts may be sorting and display.
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class Variant
{

    protected String m_id;
    protected Parameters m_parameters;
    protected VariantName m_variantName;
    protected ArrayList m_variants;    //Variant


    /** Creates new VAriant */
    public Variant()
    {
        m_variants = new ArrayList();
    }

    /**
     * @param variantName  */
    public Variant(String variantName)
    {
        this();
        m_variantName = new VariantName();
        m_variantName.setResourceData(variantName);
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

    /** Getter for property m_parameter.
     * @return Value of property m_parameters.
     */
    public Parameters getParameters()
    {
        return m_parameters;
    }

    /** Setter for property m_parameter.
     * @param parameters New value of property m_parameters.
     */
    public void setParameters(Parameters parameters)
    {
        this.m_parameters = parameters;
    }

    /** Getter for property m_variantName.
     * @return Value of property m_variantName.
     */
    public VariantName getVariantName()
    {
        return m_variantName;
    }

    /** Setter for property m_variantName.
     * @param variantName New value of property m_variantName.
     */
    public void setVariantName(VariantName variantName)
    {
        this.m_variantName = variantName;
    }

    /** Getter for property m_variants.
     * @return Value of property m_variants.
     */
    public ArrayList getVariants()
    {
        return m_variants;
    }
}
