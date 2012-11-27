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
package org.semanticwb.portal.xforms.ui;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.xforms.lib.XformsBaseImp;
import org.semanticwb.portal.xforms.ElementsProp;

// TODO: Auto-generated Javadoc
/**
 * The Class XFButton.
 * 
 * @author  jorge.jimenez
 */
public class XFButton extends XformsBaseImp 
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(XFButton.class);
    
    /** The elements prop. */
    protected ElementsProp elementsProp=null;
    
    
    /**
     * Instantiates a new xF button.
     * 
     * @param elementsProp the elements prop
     */
    public XFButton(ElementsProp elementsProp){
        this.elementsProp=elementsProp;
        setRDFAttributes();
    }
    
    // Sets
    
    
    // Gets
    
    
    /**
     * Sets the rdf attributes.
     */
    public void setRDFAttributes(){
        if(elementsProp.getId()!=null) {
            id=elementsProp.getId();
        }
        if(elementsProp.getSType()!=null) {
            subType=elementsProp.getSType();
        }
    }
    
    
    /* (non-Javadoc)
     * @see org.semanticwb.xforms.lib.XformsBaseImp#getXml()
     */
    /**
     * Gets the xml.
     * 
     * @return the xml
     */
    @Override
    public String getXml() {
        StringBuffer strbXml=new StringBuffer();
        try {
            strbXml.append("<");
            strbXml.append(subType);
            if(id!=null)
            {
                if(subType!=null && subType.equalsIgnoreCase("submit")) {
                   strbXml.append(" submission=\""+id+"\"");
                }else {
                    strbXml.append(" id=\""+id+"\"");
                }
            }            
            strbXml.append("/>");
        }
        catch(Exception e) {log.error(e); }
        return strbXml.toString();
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.xforms.lib.XformsBaseImp#setXml(java.lang.String)
     */
    /**
     * Sets the xml.
     * 
     * @param xml the new xml
     */
    @Override
    public void setXml(String xml) {
        this.xml=xml;
    }
        
}
