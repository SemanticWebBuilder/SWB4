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

import java.util.Iterator;
import org.semanticwb.model.User;


public class MessageStartEvent extends org.semanticwb.process.model.base.MessageStartEventBase 
{
    public MessageStartEvent(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        FlowNodeInstance from=instance.getSourceInstance();
        
        Iterator<ItemAwareMapping> it=listItemAwareMappings();
        while (it.hasNext())
        {
            ItemAwareMapping itemAwareMapping = it.next();
            ItemAware loc=itemAwareMapping.getLocalItemAware();
            ItemAware rem=itemAwareMapping.getRemoteItemAware();
            
            ItemAwareReference itemAwareReference=getItemAwareReference(instance, loc);
            
            Iterator<ItemAwareReference> it3=from.listHeraquicalItemAwareReference().iterator();
            while (it3.hasNext())
            {
                ItemAwareReference remitem = it3.next();
                if(remitem.getItemAware().equals(rem))
                {
                    if(itemAwareReference==null)
                    {
                        itemAwareReference=ItemAwareReference.ClassMgr.createItemAwareReference(instance.getProcessSite());
                        itemAwareReference.setItemAware(loc);                
                        instance.addItemAwareReference(itemAwareReference);        
                    }
                    itemAwareReference.setProcessObject(remitem.getProcessObject());
                    remitem.setProcessObjectReused(true);
                }
            }
        }        
        
        super.execute(instance, user);
    }
    
    public ItemAwareReference getItemAwareReference(FlowNodeInstance instance, ItemAware item)
    {
        Iterator<ItemAwareReference> it=instance.listHeraquicalItemAwareReference().iterator();
        while (it.hasNext())
        {
            ItemAwareReference itemAwareReference = it.next();
            if(itemAwareReference.getItemAware().equals(item))
            {
                return itemAwareReference;
            }
        }
        return null;
    }    
    
    
    
}
