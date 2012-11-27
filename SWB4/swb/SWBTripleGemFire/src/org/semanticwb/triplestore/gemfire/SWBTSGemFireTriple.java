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
package org.semanticwb.triplestore.gemfire;

import com.gemstone.gemfire.DataSerializable;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author javier.solis.g
 */
public class SWBTSGemFireTriple implements Serializable
{
    private String id=null;
    private String subj=null;
    private String prop=null;
    private String obj=null;
    private String ext=null;
    private String model=null;

    public SWBTSGemFireTriple()
    {
        
    }
    
    public SWBTSGemFireTriple(String subj, String prop, String obj, String ext, String model)
    {
        this(UUID.randomUUID().toString(), subj, prop, obj, ext, model);
    }
    
    public SWBTSGemFireTriple(String id, String subj, String prop, String obj, String ext, String model)
    {
        this.id=id;
        this.subj=subj;
        this.prop=prop;
        this.obj=obj;
        if(ext!=null && ext.length()>0)this.ext=ext;
        this.model=model;
    }        

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }    
    
    public String getSubj()
    {
        return subj;
    }

    public void setSubj(String subj)
    {
        this.subj = subj;
    }

    public String getProp()
    {
        return prop;
    }

    public void setProp(String prop)
    {
        this.prop = prop;
    }

    public String getObj()
    {
        return obj;
    }

    public void setObj(String obj)
    {
        this.obj = obj;
    }

    public String getExt()
    {
        return ext;
    }

    public void setExt(String ext)
    {
        this.ext = ext;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    
    
}
