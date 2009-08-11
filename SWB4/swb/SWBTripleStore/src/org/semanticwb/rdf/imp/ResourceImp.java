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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rdf.imp;

import org.semanticwb.rdf.*;

/**
 *
 * @author Jei
 */
public class ResourceImp extends RDFNodeImp implements Resource
{
    private com.hp.hpl.jena.rdf.model.Resource interObject;

    public ResourceImp(com.hp.hpl.jena.rdf.model.Resource interObject)
    {
        super(interObject);
        this.interObject=interObject;
    }
    
    public com.hp.hpl.jena.rdf.model.Resource getInternalObject() 
    {
        return interObject;
    }     
    
    public Resource addProperty(Property prop, String value)
    {
        interObject.addProperty(((org.semanticwb.rdf.imp.PropertyImp)prop).getInternalObject(), value);
        return this;
    }
    
    public Resource addProperty(Property prop, String value, String lang)
    {
        interObject.addProperty(((org.semanticwb.rdf.imp.PropertyImp)prop).getInternalObject(), value, lang);
        return this;
    }
    
    public Resource addProperty(Property prop, RDFNode node)
    {
        interObject.addProperty(((org.semanticwb.rdf.imp.PropertyImp)prop).getInternalObject(), ((org.semanticwb.rdf.imp.RDFNodeImp)node).getInternalObject());
        return this;
    }

    public boolean isAnon() {
        return interObject.isAnon();
    }

    public boolean isLiteral() {
        return interObject.isLiteral();
    }

    public boolean isURIResource() {
        return interObject.isURIResource();
    }

    public boolean isResource() {
        return interObject.isResource();
    }
    
    @Override
    public String toString()
    {
        return interObject.toString();
    }
    
}
