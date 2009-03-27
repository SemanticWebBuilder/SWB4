package org.semanticwb.model;


public class SWBClass extends org.semanticwb.model.base.SWBClassBase 
{
    public SWBClass(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public boolean isValid()
    {
        boolean ret=true;
        if(this instanceof Activeable)
        {
            if(!((Activeable)this).isActive())ret=false;
        }
        if(ret && this instanceof Trashable)
        {
            if(((Trashable)this).isDeleted())ret=false;
        }
        //TODO: calendarizacion
        return ret;
    }


}
