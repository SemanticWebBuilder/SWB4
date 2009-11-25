package org.semanticwb.portal.community;

import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

public class Clasified extends org.semanticwb.portal.community.base.ClasifiedBase 
{
    private static Logger log=SWBUtils.getLogger(Clasified.class);

    private static Timer timer=null;

    static
    {
        log.debug("Starting Classifies Depuring...");
        timer=new Timer();
        TimerTask task=new TimerTask()
        {
            @Override
            public void run()
            {
                Iterator<MicrositeContainer> mit=MicrositeContainer.ClassMgr.listMicrositeContainers();
                while(mit.hasNext())
                {
                    MicrositeContainer site=mit.next();
                    System.out.println("Sitio:"+site);
                    Iterator<Clasified> it=Clasified.ClassMgr.listClasifieds(site);
                    while(it.hasNext())
                    {
                        Clasified obj=it.next();
                        System.out.println(obj+" exp:"+obj.getExpirationDate());
                        if(obj.getExpirationDate().getTime()<System.currentTimeMillis())
                        {
                            obj.remove();
                        }
                    }
                }
            }
        };
        timer.schedule(task, 1000L*60*60, 1000L*60*60*24);
        //timer.schedule(task, 1000L*60, 1000L*60);
    }


    public Clasified(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public Date getExpirationDate()
    {
        return new Date(getCreated().getTime()+1000L*60*60*24*31);
    }

}
