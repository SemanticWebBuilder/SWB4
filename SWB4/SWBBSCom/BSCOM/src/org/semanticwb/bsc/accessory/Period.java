package org.semanticwb.bsc.accessory;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;
import org.semanticwb.platform.SemanticProperty;
import static org.semanticwb.bsc.formelement.Periodicity.*;


   /**
   * Período de medición. 
   */
public class Period extends org.semanticwb.bsc.accessory.base.PeriodBase implements Comparable<Period>
{
    static
    {        
        bsc_start.registerObserver(new SemanticObserver() {
            @Override
            public void notify(SemanticObject obj, Object prop, String lang, String action)
            {
                if("SET".equalsIgnoreCase(action)) {
                    SimpleDateFormat sdf = new SimpleDateFormat(formatPattern);
                    if(obj.getDateProperty(Period.bsc_start)!=null && obj.getDateProperty(Period.bsc_end)!=null) {
                        Date start = obj.getDateProperty(Period.bsc_start);
                        Date end = obj.getDateProperty(Period.bsc_end);
                        if(end.getTime()<start.getTime()) {
                            obj.setDateProperty(bsc_start, end);
                            obj.setDateProperty(bsc_end, start);
                        }
                    }
                }
            }
        });
        
        bsc_end.registerObserver(new SemanticObserver() {
            @Override
            public void notify(SemanticObject obj, Object prop, String lang, String action)
            {
                if("SET".equalsIgnoreCase(action)) {
                    SimpleDateFormat sdf = new SimpleDateFormat(formatPattern);
                    if(obj.getDateProperty(Period.bsc_start)!=null && obj.getDateProperty(Period.bsc_end)!=null) {
                        Date start = obj.getDateProperty(Period.bsc_start);
                        Date end = obj.getDateProperty(Period.bsc_end);
                        if(end.getTime()<start.getTime()) {
                            obj.setDateProperty(bsc_start, end);
                            obj.setDateProperty(bsc_end, start);
                        }
                    }
                }
            }
        });
    }
    
    public Period(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    @Override
    public int compareTo(Period anotherPeriod) {
        int compare = 0;
        Date d1 = getStart();
        Date d2 = anotherPeriod.getStart();
        compare = d1.getTime() > d2.getTime() ? 1 : -1;
        return compare;
    }
}
