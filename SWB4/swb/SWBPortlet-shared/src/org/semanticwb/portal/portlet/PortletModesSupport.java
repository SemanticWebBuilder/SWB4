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
package org.semanticwb.portal.portlet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class PortletModesSupport
{
    HashMap mimes=new HashMap();
    HashMap modes=new HashMap();
    
    /** Creates a new instance of PortletModesSupport */
    public PortletModesSupport()
    {
        System.out.println("PortletModesSupport initialized..");
    }
    
    public Set getModes()
    {
        return modes.keySet();
    }
    
    public boolean haveMode(String mode)
    {
        Iterator it=modes.keySet().iterator();
        while(it.hasNext())
        {
            String str=(String)it.next();
            if(str.equals(mode.toLowerCase()))
                return true;
        }
        return false;
    }
    
    
    public Set getModes(String mime)
    {
        ArrayList arr=(ArrayList)mimes.get(mime);
        if(arr==null)
        {
            return new TreeSet();
        }
        return new TreeSet(arr);
    }
    
    
    public Set getMimeTypes()
    {
        return mimes.keySet();
    }
    
    public boolean haveMimeType(String mime)
    {
        Iterator it=mimes.keySet().iterator();
        while(it.hasNext())
        {
            String str=(String)it.next();
            if(str.equalsIgnoreCase(mime))
                return true;
        }
        return false;
    }    
    
    public Set getMimeTypes(String mode)
    {
        ArrayList arr=(ArrayList)modes.get(mode.toLowerCase());
        if(arr==null)
        {
            return new TreeSet();
        }
        return new TreeSet(arr);
    }    
    
    
    public void addMode(String mime, String mode)
    {
        addMode(mime,new String[]{mode});
    }
    
    public void addMode(String mime, String mode[])
    {
        for(int x=0;x<mode.length;x++)
        {
            String smode=mode[x].toLowerCase();
            
            //System.out.println("mime:"+mime+" mode:"+smode);
            
            ArrayList aux=(ArrayList)mimes.get(mime);
            if(aux==null)
            {
                aux=new ArrayList();
                mimes.put(mime,aux);
            }
            if(!aux.contains(smode))aux.add(smode);


            aux=(ArrayList)modes.get(smode);
            if(aux==null)
            {
                aux=new ArrayList();
                modes.put(smode,aux);
            }
            if(!aux.contains(mime))aux.add(mime);        
        }
    }    
    
}
