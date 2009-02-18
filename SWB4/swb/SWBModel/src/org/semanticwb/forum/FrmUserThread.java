package org.semanticwb.forum;

import java.util.Iterator;
import org.semanticwb.model.User;


public class FrmUserThread extends org.semanticwb.forum.base.FrmUserThreadBase 
{
    public FrmUserThread(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public boolean isFavoriteThread(FrmThread thread, User user)
    {
        System.out.println("entra clase-1");
        Iterator <FrmUserThread> itFrmUserThread=FrmUserThread.listFrmUserThreads(thread.getWebSite());
        while(itFrmUserThread.hasNext()){
            System.out.println("entra clase-2");
            FrmUserThread usrThread=itFrmUserThread.next();
            if(usrThread.getThread().getURI().equals(thread.getURI()) && usrThread.getUser().getURI().equals(user.getURI()))
            {
                System.out.println("entra clase-3");
                return true;
            }
        }
        return false;
    }
}
