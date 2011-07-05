package org.semanticwb.portal.resources.sem.contact;


public class Phone extends org.semanticwb.portal.resources.sem.contact.base.PhoneBase 
{
    public Phone(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public String toString() {
        StringBuilder c = new StringBuilder();
        if(getRegion()!=null)
            c.append(getRegion().getTitle()).append(". ");
        if(getAreaCode()!=0)
            c.append(getAreaCode()).append(" ");
        if(getLocalNumber()!=0)
            c.append(getLocalNumber());
        if(getExt()!=0)
            c.append(" Ext.").append(getExt());
        return c.toString();
    }
}
