package org.semanticwb.bsc.accessory;

import java.util.Date;


   /**
   * Período de medición. 
   */
public class Period extends org.semanticwb.bsc.accessory.base.PeriodBase implements Comparable<Period>
{
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
