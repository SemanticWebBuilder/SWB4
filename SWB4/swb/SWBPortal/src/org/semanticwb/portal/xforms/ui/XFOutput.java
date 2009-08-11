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
 * XFOutput.java
 *
 * Created on 1 de julio de 2008, 06:58 PM
 */

package org.semanticwb.portal.xforms.ui;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.xforms.lib.XformsBaseImp;
import org.semanticwb.xforms.drop.RDFElement;

/**
 *
 * @author  jorge.jimenez
 */
public class XFOutput extends XformsBaseImp
{
    private static Logger log = SWBUtils.getLogger(XFOutput.class);
    protected String value=null;
    protected String mediatype=null;    
    protected RDFElement rdfElement=null;
    
    public XFOutput(RDFElement rdfElement){
        this.rdfElement=rdfElement;
        setRDFAttributes();
    }
    
    // Sets
    
    public void setValue(String value){
        this.value=value;
    }
    
    public void setMediaType(String mediatype){
        this.mediatype=mediatype;
    }
    
    // Gets
    
    public String getValue(){
        return value;
    }
    
    public String getMediaType(){
       return mediatype;
    }
    
    public void setRDFAttributes(){
        if(rdfElement.getId()!=null) {
            id=rdfElement.getId();
        }
        if(rdfElement.getLabel()!=null) {
            label=rdfElement.getLabel();
        }
        if(rdfElement.getSType()!=null) {
            subType=rdfElement.getSType();
        }
        if(rdfElement.getHint()!=null) {
            hint=rdfElement.getHint();
        }
        if(rdfElement.getValue()!=null) {
            value=rdfElement.getValue();
        }
    }
    
    @Override
   public String getXmlBind() 
    {
        StringBuffer strbXml=new StringBuffer();
        strbXml.append("<bind id=\"bind_"+id+"\" nodeset=\""+id+"\"");
        if(isrequired) {
            strbXml.append(" required=\"true()\" "); 
        }
        if(subType!=null) {
            strbXml.append(" type=\""+subType+"\" "); 
        }
        strbXml.append("/>");
        return strbXml.toString();
    }    
    
    @Override
    public String getXml() 
    {
        StringBuffer strbXml=new StringBuffer();
        try {
            strbXml.append("<output bind=\"bind_"+id+"\"");
            
            strbXml.append(">");
            
            if(label!=null) 
            {
                strbXml.append("<label>");
                strbXml.append(label.trim());
                strbXml.append("</label>");
            }
           
            strbXml.append("</output>");
        }
        catch(Exception e) {log.error(e); }
        return strbXml.toString();
    }
    
    @Override
    public void setXml(String xml) {
        this.xml=xml;
    }
}
