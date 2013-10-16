package org.semanticwb.bsc.tracing;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.GenericFilterRule;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.catalogs.Format;
import org.semanticwb.bsc.tracing.base.SeriesBase;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;


public class Series extends org.semanticwb.bsc.tracing.base.SeriesBase implements Comparable<Series>
{
    static
    {
        SeriesBase.bsc_format.registerObserver(new SemanticObserver() {
            @Override
            public void notify(SemanticObject obj, Object prop, String lang, String action)
            {
                Series s = (Series)obj.createGenericInstance();
                if("REMOVE".equalsIgnoreCase(action)) {
                    s.formatter = null;
                }else {
                    s.setFormatter();
                }
            }
        });
        
        /*sclass.registerObserver(new SemanticObserver() {
            @Override
            public void notify(SemanticObject obj, Object prop, String lang, String action)
            {
                SWBModel model = (SWBModel)obj.getModel().getModelObject().createGenericInstance();
                if("CREATE".equalsIgnoreCase(action)) {
System.out.println("\nSeries...CREATE");
System.out.println("obj="+obj);
System.out.println("prop="+prop);
BSC bsc = (BSC)obj.getModel().getModelObject().createGenericInstance();
Series series = (Series)obj.getGenericInstance();
//Indicator indicator = series.getIndicator();
//List<State> validSates = indicator.listValidStates();
//for(State state:validSates) {
//    EvaluationRule rule = EvaluationRule.ClassMgr.createEvaluationRule(bsc);
//    rule.setAppraisal(state);
//    series.addEvaluationRule(rule);
//}
                }else if("SET".equalsIgnoreCase(action)) {
System.out.println("\nSeries...SET");
System.out.println("obj="+obj);
System.out.println("prop="+prop);
Series series = (Series)obj.getGenericInstance();
                }else if("ADD".equalsIgnoreCase(action)) {
System.out.println("\nSeries...ADD");
System.out.println("obj="+obj);
System.out.println("prop="+prop);
Series series = (Series)obj.getGenericInstance();
                }else if("REMOVE".equalsIgnoreCase(action)) {
System.out.println("\nSeries...REMOVE");
System.out.println("obj="+obj);
System.out.println("prop="+prop);
Series series = (Series)obj.getGenericInstance();
                }
            }
        });*/
    }
    
    
    
    
    
    private DecimalFormat formatter;
    
    public Series(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public NumberFormat getFormatter() {
        if(formatter==null) {
            setFormatter();
        }
        return formatter;
    }
    
    private void setFormatter() {
        org.semanticwb.bsc.catalogs.Format format = getFormat();
        Locale locale;
        try {
            locale = new Locale(format.getLanguage().getId().toLowerCase(), format.getCountry().getId().toUpperCase());
        }catch(Exception e) {
            locale = new Locale("es","MX");
        }
        NumberFormat numFormat = NumberFormat.getNumberInstance(locale);
        formatter = (DecimalFormat)numFormat;
        try {
            formatter.applyPattern(format.getFormatPattern());
        }catch(Exception iae) {
            formatter.applyPattern(Format.defaultFormatPattern);
        }
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
    
    public List<EvaluationRule> listValidEvaluationRules() {
        List<EvaluationRule> validRules = SWBUtils.Collections.filterIterator(listEvaluationRules(), new GenericFilterRule<EvaluationRule>() {
                                                                        @Override
                                                                        public boolean filter(EvaluationRule r) {
                                                                            User user = SWBContext.getSessionUser();
                                                                            return !r.isValid() || !user.haveAccess(r);
                                                                        }            
                                                                    });
        return validRules;
    }
}
