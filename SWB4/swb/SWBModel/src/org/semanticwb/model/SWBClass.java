package org.semanticwb.model;


public class SWBClass extends org.semanticwb.model.base.SWBClassBase 
{
    public SWBClass(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public boolean isValid()
    {
        //System.out.println("isValid:"+getURI()+" "+getClass().getName());
        boolean ret=true;
        if(this instanceof Activeable)
        {
            if(!((Activeable)this).isActive())ret=false;
        }
        if(ret && this instanceof Viewable)
        {
            long val=((Viewable)this).getViews();
            long max=((Viewable)this).getMaxViews();
            //System.out.println("views:"+max+" "+val);
            if((max>0) && (val>=max))ret=false;
        }
        if(ret && this instanceof Hitable)
        {
            long val=((Hitable)this).getHits();
            long max=((Hitable)this).getMaxHits();
            //System.out.println("hits:"+max+" "+val);
            if((max>0) && (val>=max))ret=false;
        }
        if(ret && this instanceof Trashable)
        {
            if(((Trashable)this).isDeleted())ret=false;
        }
        //TODO: calendarizacion
        return ret;
    }


}
