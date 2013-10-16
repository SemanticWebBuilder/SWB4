package org.semanticwb.bsc.element;

import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.Role;
import org.semanticwb.model.RuleRef;
import org.semanticwb.model.UserGroup;


public class Perspective extends org.semanticwb.bsc.element.base.PerspectiveBase 
{
    public Perspective(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    @Override
    public synchronized int getSerial() {
        setSerial(super.getSerial()+1);
        return super.getSerial();
    }

    @Override
    public synchronized void setSerial(int value) {
        super.setSerial(value);
    }
    
    @Override
    public String getPrefix() {
        String prefix = super.getPrefix();
        if(prefix==null) {
            try {
                prefix = getTitle().trim().substring(0, 1).toUpperCase();
            }catch(Exception e) {
                prefix = "Untitled";
            }
            setPrefix(prefix);
        }
        return prefix;
    }

    @Override
    public void setPrefix(String value) {
        super.setPrefix(value);           
    }    
}
