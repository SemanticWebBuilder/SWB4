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
        this.id = "1";
    }
    
@Override
    public String getFolio() {
        String folio = super.getFolio();
        Indicator indicator = (Indicator) getSemanticObject().createGenericInstance();
        if (folio == null) {
            String folioIndicator = "";
            BSC bsc = indicator.getBSC();
            WebSite bscSite = (WebSite) bsc;
            Iterator<Objective> listObjs = Objective.ClassMgr.listObjectives(bscSite);
            while (listObjs.hasNext()) {
                Objective objective = listObjs.next();
                if (objective.hasIndicator(this)) {
                    objective.setProperty("folioObjective", "F1");
                    folioIndicator = generateFolio(objective, folioIndicator);
                }
            }
            return folioIndicator;
        } else {
            return super.getFolio();
        }
    }

    @Override
    public void setFolio(String value) {
        super.setFolio(value);
    }

    public synchronized String generateFolio(Objective objective, String folioIndicator) {
        if (objective.getProperty("folioIndicator") == null) {
            objective.setProperty("folioIndicator", this.id);
        }
        this.id = objective.getProperty("folioIndicator");
        folioIndicator = objective.getProperty("folioObjective") + "." + objective.getProperty("folioIndicator");
        int idInc = Integer.parseInt(id);
        idInc++;
        objective.setProperty("folioIndicator", idInc + "");
        setFolio(folioIndicator);
        notifyAll(); 
        return folioIndicator;
    }    
}
