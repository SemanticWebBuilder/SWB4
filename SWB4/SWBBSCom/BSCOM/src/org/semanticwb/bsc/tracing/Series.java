package org.semanticwb.bsc.tracing;

import java.util.Iterator;
import org.semanticwb.bsc.Measure;
import org.semanticwb.bsc.accessory.Period;


public class Series extends org.semanticwb.bsc.tracing.base.SeriesBase implements Comparable<Series>
{
    public Series(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    @Override
    public int compareTo(Series anotherSeries) {
        int compare = 0;
        compare = getIndex() > anotherSeries.getIndex() ? 1 : -1;
        return compare;
    }
    
    public Measure getMeasureByPeriod(Period period)
    {
        Iterator<Measure> measures = listMeasures();
        Measure measure = null;
        while(measures.hasNext())
        {
            measure = measures.next();
            if(measure.getEvaluation().getPeriod().equals(period))
            {
                return measure;
            }
        }
        return null;
    }
}
