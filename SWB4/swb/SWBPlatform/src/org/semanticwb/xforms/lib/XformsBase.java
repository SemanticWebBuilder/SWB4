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
 * The Interface XformsBase.
 * 
 * @author  jorge.jimenez
 */
public interface XformsBase {
    
    /**
     * Sets the lable.
     * 
     * @param label the new lable
     */
    public void setLable(String label);
    
    /**
     * Gets the label.
     * 
     * @return the label
     */
    public String getLabel();
    
    /**
     * Sets the id.
     * 
     * @param id the new id
     */
    public void setId(String id);
    
    /**
     * Gets the id.
     * 
     * @return the id
     */
    public String getId();
    
    /**
     * Sets the sub type.
     * 
     * @param subType the new sub type
     */
    public void setSubType(String subType);
    
    /**
     * Gets the sub type.
     * 
     * @return the sub type
     */
    public String getSubType();
    
    /**
     * Sets the required.
     * 
     * @param isrequired the new required
     */
    public void setRequired(boolean isrequired);
    
    /**
     * Checks if is required.
     * 
     * @return true, if is required
     */
    public boolean isRequired();
    
    /**
     * Sets the xml.
     * 
     * @param xml the new xml
     */
    public void setXml(String xml);
    
    /**
     * Gets the xml.
     * 
     * @return the xml
     */
    public String getXml();
    
    /**
     * Sets the xml bind.
     * 
     * @param xmlBind the new xml bind
     */
    public void setXmlBind(String xmlBind);
    
    /**
     * Gets the xml bind.
     * 
     * @return the xml bind
     */
    public String getXmlBind();
    
    /**
     * Sets the help.
     * 
     * @param help the new help
     */
    public void setHelp(String help);
    
    /**
     * Gets the help.
     * 
     * @return the help
     */
    public String getHelp();
    
    /**
     * Sets the hint.
     * 
     * @param hint the new hint
     */
    public void setHint(String hint);
    
    /**
     * Gets the hint.
     * 
     * @return the hint
     */
    public String getHint();
    
  
}
