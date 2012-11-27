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
            //System.out.println(index+" "+aux);
            arr.add(aux);
        }
        //System.out.println("index:"+index);
    }

    public String undo()
    {
        if(index>-1)
        {
            index--;
            //System.out.println(index+" "+arr.get(index));
            return arr.get(index);
        }
        return null;
    }

    public String redo()
    {
        if(index+1<arr.size())
        {
            index++;
            //System.out.println(index+" "+arr.get(index));
            return arr.get(index);
        }
        return null;
    }
}
