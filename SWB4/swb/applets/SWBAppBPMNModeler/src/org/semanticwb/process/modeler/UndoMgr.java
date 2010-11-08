/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.modeler;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author jei
 */
public abstract class UndoMgr
{
    private List<String> arr=new ArrayList();
    private int index=-1;

    public abstract String getState();

    Timer timer=null;

    public UndoMgr()
    {
        long delays=5;
        TimerTask t=new TimerTask(){
            public void run()
            {
                try {
                    _run();
                } catch (java.lang.NullPointerException npe){
                //Ignore, Posible NPE at Exit - MAPS74
                }
            }
        };
        timer = new Timer("SWBMonitor("+delays+"s)", true);
        timer.schedule(t, delays*1000, delays*1000);
    }

    private void _run()
    {
        String aux=getState();

        if(index==-1 || (index>-1 && aux.hashCode()!=arr.get(index).hashCode()))
        {
            index++;
            arr=arr.subList(0, index);
            System.out.println(index+" "+aux);
            arr.add(aux);
        }
        //System.out.println("index:"+index);
    }

    public String undo()
    {
        if(index>-1)
        {
            index--;
            System.out.println(index+" "+arr.get(index));
            return arr.get(index);
        }
        return null;
    }

    public String redo()
    {
        if(index+1<arr.size())
        {
            index++;
            System.out.println(index+" "+arr.get(index));
            return arr.get(index);
        }
        return null;
    }
}
