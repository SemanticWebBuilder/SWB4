/*
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
 */
package org.semanticwb.process.model;

import org.semanticwb.platform.SemanticProperty;

/**
 *
 * @author javier.solis
 */
public class SWBProcessProperty
{
    private String m_name=null;
    private SemanticProperty m_prop=null;
    private String m_mode=null;
    private String varTitle=null;

    public SWBProcessProperty(String varName, SemanticProperty prop, String mode)
    {
        this(varName, prop, null, mode);
    }

    public SWBProcessProperty(String varName, SemanticProperty prop, String varTitle, String mode)
    {
        this.m_name=varName;
        this.m_prop=prop;
        this.m_mode=mode;
        this.varTitle=varTitle;
    }

    /**
     * @return the m_cls
     */
    public String getVarName() {
        return m_name;
    }

    /**
     * @param m_cls the m_cls to set
     */
    public void setVarName(String varName) {
        this.m_name = varName;
    }

    /**
     * @return the m_prop
     */
    public SemanticProperty getSemanticProperty() {
        return m_prop;
    }

    /**
     * @param m_prop the m_prop to set
     */
    public void setSemanticProperty(SemanticProperty prop) {
        this.m_prop = prop;
    }

    /**
     * @return the m_view
     */
    public String getMode() {
        return m_mode;
    }

    /**
     * @param view the m_view to set
     */
    public void setMode(String mode) {
        this.m_mode = mode;
    }

    public String getVarTitle()
    {
        return varTitle;
    }

    public void setVarTitle(String varTitle)
    {
        this.varTitle = varTitle;
    }


}
