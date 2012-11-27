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
package org.semanticwb.xforms.lib;

// TODO: Auto-generated Javadoc
/**
 * The Class XformsBaseImp.
 * 
 * @author  jorge.jimenez
 */
public class XformsBaseImp implements XformsBase
{
    
    /** The label. */
    protected String label;
    
    /** The id. */
    protected String id;
    
    /** The sub type. */
    protected String subType;
    
    /** The isrequired. */
    protected boolean isrequired;
    
    /** The xml. */
    protected String xml;
    
    /** The xml bind. */
    protected String xmlBind;
    
    /** The help. */
    protected String help;
    
    /** The hint. */
    protected String hint;
        
    /* (non-Javadoc)
     * @see org.semanticwb.xforms.lib.XformsBase#setLable(java.lang.String)
     */
    public void setLable(String label)
    {
        this.label=label;
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.xforms.lib.XformsBase#getLabel()
     */
    public String getLabel()
    {
        return label;
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.xforms.lib.XformsBase#setId(java.lang.String)
     */
    public void setId(String id)
    {
        this.id=id;
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.xforms.lib.XformsBase#getId()
     */
    public String getId()
    {
        return id;
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.xforms.lib.XformsBase#setSubType(java.lang.String)
     */
    public void setSubType(String subType)
    {
        this.subType=subType;
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.xforms.lib.XformsBase#getSubType()
     */
    public String getSubType()
    {
        return subType;
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.xforms.lib.XformsBase#setRequired(boolean)
     */
    public void setRequired(boolean isrequired)
    {
        this.isrequired=isrequired;
    }
   
    /* (non-Javadoc)
     * @see org.semanticwb.xforms.lib.XformsBase#isRequired()
     */
    public boolean isRequired()
    {
        return isrequired;
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.xforms.lib.XformsBase#setXml(java.lang.String)
     */
    public void setXml(String xml) {
        this.xml=xml;
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.xforms.lib.XformsBase#getXml()
     */
    public String getXml() {
        return xml;
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.xforms.lib.XformsBase#setXmlBind(java.lang.String)
     */
    public void setXmlBind(String xmlBind) {
        this.xmlBind=xmlBind;
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.xforms.lib.XformsBase#getXmlBind()
     */
    public String getXmlBind() {
        return xmlBind;
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.xforms.lib.XformsBase#setHelp(java.lang.String)
     */
    public void setHelp(String help) {
        this.help=help;
    }    
    
    /* (non-Javadoc)
     * @see org.semanticwb.xforms.lib.XformsBase#getHelp()
     */
    public String getHelp() {
        return help;
    }    
    
    /* (non-Javadoc)
     * @see org.semanticwb.xforms.lib.XformsBase#getHint()
     */
    public String getHint() {
        return hint;
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.xforms.lib.XformsBase#setHint(java.lang.String)
     */
    public void setHint(String hint) {
        this.hint=hint;
    }
    
}
