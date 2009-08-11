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

package org.semanticwb.xmlrpc;

import java.io.File;

/**
 *
 * @author victor.lorenzana
 */
public class Attachment {
    private final File file;
    private final String name;
    public Attachment(File file,String name)
    {
        this.file=file;
        this.name=name;
    }
    public Attachment(File file)
    {
        this.file=file;
        this.name=file.getName();
    }
    public File getFile()
    {
        return file;
    }
    public String getName()
    {
        return name;    
    }
    @Override
    public String toString()
    {
        this.hashCode();
        return this.name;        
    }
    @Override
    public int hashCode()
    {
        return this.name.hashCode();
    }
    @Override
    public boolean equals(Object obj)
    {
        boolean equals=false;
        if(obj instanceof Attachment)
        {
            equals=((Attachment)obj).name.equals(this.name);
        }
        return equals;
    }
}
