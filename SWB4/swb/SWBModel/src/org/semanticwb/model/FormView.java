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
 
package org.semanticwb.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.base.*;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.platform.SemanticVocabulary;

public class FormView extends FormViewBase 
{
    public static final String MODE_CREATE="create";
    public static final String MODE_VIEW="view";
    public static final String MODE_EDIT="edit";
    
    private SemanticOntology ont=SWBPlatform.getSemanticMgr().getOntology();
    private SemanticProperty propertyMode=new SemanticProperty(ont.getRDFOntModel().getProperty(SemanticVocabulary.URI+"propertyMode"));
    private SemanticProperty propertyRef=new SemanticProperty(ont.getRDFOntModel().getProperty(SemanticVocabulary.URI+"propertyRef"));
    
    public FormView(SemanticObject base)
    {
        super(base);
    }

    /**
     * Regrasa Mapa con las propiedades como llave y el modo relacionado como valor
     * @param mode (modo por default de la vista)
     * @return
     */
    public Map<SemanticProperty,String> getPropertyMap(String mode) 
    {
        HashMap<SemanticProperty,String> map=new HashMap();
        Iterator<SemanticObject> it=null;
        if(mode.equals(MODE_CREATE))it=listCreatePropertys();
        if(mode.equals(MODE_VIEW))it=listViewPropertys();
        if(mode.equals(MODE_EDIT))it=listEditPropertys();
        if(it!=null)
        {
            while(it.hasNext())
            {
                SemanticObject obj=it.next();
                SemanticProperty prop=obj.transformToSemanticProperty();
                String modeaux=obj.getProperty(propertyMode);
                if(modeaux==null)modeaux=mode;
                SemanticObject ref=obj.getObjectProperty(propertyRef);                
                if(ref!=null)prop=ref.transformToSemanticProperty();
                if(prop!=null)map.put(prop,modeaux);
            }
        }
        return map;
    }
    
    
}
