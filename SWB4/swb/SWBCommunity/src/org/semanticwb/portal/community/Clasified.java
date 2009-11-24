package org.semanticwb.portal.community;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class Clasified extends org.semanticwb.portal.community.base.ClasifiedBase 
{
    static Timer timer=null;

    static
    {
        timer=new Timer();
        TimerTask task=new TimerTask()
        {
            @Override
            public void run()
            {
                System.out.println("hola..");
            }
        };
        //timer.schedule(task, 1000L*60*60, 1000L*60*60*24);
        timer.schedule(task, 1000L*60, 1000L*60);
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
