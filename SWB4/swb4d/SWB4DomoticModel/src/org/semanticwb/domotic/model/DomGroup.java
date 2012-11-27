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
package org.semanticwb.domotic.model;

import java.util.Iterator;
import org.semanticwb.domotic.server.WebSocketServlet;


public class DomGroup extends org.semanticwb.domotic.model.base.DomGroupBase 
{
    private int oldStat=0;
    
    public DomGroup(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    public void setStatus(int stat)
    {
        setStatus(stat, true);
    }
    
    public void setStatus(int stat, boolean notifyParents)
    {
        System.out.println("DomGroup:setStatus:"+stat+" "+notifyParents);
        
        Iterator<DomDevice> it=listDomDevices();
        while (it.hasNext())
        {
            DomDevice domDevice = it.next();
            domDevice.setStatus(stat,true,false);
        }
        
        Iterator<DomGroup> it2=listChildGroups();
        while (it2.hasNext())
        {
            DomGroup domGroup = it2.next();
            domGroup.setStatus(stat,false);
        }        
        
        sendWebSocketStatus(notifyParents);
        
    }
    
    public int getStatus()
    {
        int ret=0;
        Iterator<DomDevice> it=listDomDevices();
        while (it.hasNext())
        {
            DomDevice domDevice = it.next();
            if(domDevice.getStatus()>0)
            {
                ret=16;
                break;
            }
        }
        
        if(ret==0)
        {        
            Iterator<DomGroup> it2=listChildGroups();
            while (it2.hasNext())
            {
                DomGroup domGroup = it2.next();
                if(domGroup.getStatus()>0)
                {
                    ret=16;
                    break;
                }
            }        
        }
        
        if(oldStat!=ret)
        {
            Iterator<OnGroupChange> it3=listOnGroupChanges();
            while (it3.hasNext())
            {
                OnGroupChange onGroupChange = it3.next();
                onGroupChange.onEvent(""+ret);
            }            
        }
        
        oldStat=ret;
        return ret;
    }
    
    
    /**
     * Notifica a los grupos por websockets si notifyParents es true 
     * @param notifyParents 
     */
    public void sendWebSocketStatus(boolean notifyParents)
    {
        WebSocketServlet.broadcast(getShortURI()+" "+getStatus());
        if(notifyParents)
        {
            DomGroup domGroup=getParentGroup();
            if(domGroup!=null)domGroup.sendWebSocketStatus(notifyParents);
        }
    }    
    
}
