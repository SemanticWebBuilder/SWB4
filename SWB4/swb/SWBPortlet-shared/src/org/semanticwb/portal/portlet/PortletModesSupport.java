/*
 * PortletModesSupport.java
 *
 * Created on 20 de marzo de 2006, 12:08 PM
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
