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
            prefix = getObjective().getPrefix();
            setPrefix(prefix);
        }
        return prefix;
    }

    @Override
    public void setPrefix(String value) {
        super.setPrefix(value+"."+getObjective().getSerial());
           
    }
}
