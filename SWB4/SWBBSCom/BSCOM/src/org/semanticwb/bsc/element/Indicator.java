package org.semanticwb.bsc.element;

import java.util.Iterator;
import org.semanticwb.bsc.BSC;
import org.semanticwb.model.WebSite;


public class Indicator extends org.semanticwb.bsc.element.base.IndicatorBase 
{
    private String id;
    
    public Indicator(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
//        this.id = "1";
    }
    
    @Override
    public String getPrefix() {
        String prefix = super.getPrefix();
        if(prefix==null) {
            try {
                prefix = getObjective().getPrefix().trim() + "." + getObjective().getSerial();
            }catch(Exception e) {
                prefix = getObjective().getTitle().substring(0, 1).toUpperCase() + "." + getObjective().getSerial();
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
