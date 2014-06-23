package org.semanticwb.bsc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.GenericFilterRule;
import org.semanticwb.bsc.accessory.DifferentiatorGroup;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.element.Objective;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


public class Perspective extends org.semanticwb.bsc.base.PerspectiveBase implements Comparable<Perspective>
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
    
    @Override
    public int compareTo(Perspective anotherPerspective)
    {
        int compare = getIndex()>anotherPerspective.getIndex()?1:-1;
        return compare;
    }
    
    public List<Theme> listValidThemes() {
        List<Theme> validThemes = SWBUtils.Collections.filterIterator(super.listThemes(), new GenericFilterRule<Theme>() {
                                                                        @Override
                                                                        public boolean filter(Theme t) {
                                                                            if(t==null) {
                                                                                return true;
                                                                            }
                                                                            User user = SWBContext.getSessionUser(getBSC().getUserRepository().getId());
                                                                            if(user==null) {
                                                                                user = SWBContext.getAdminUser();
                                                                            }
                                                                            return !t.isValid() || !user.haveAccess(t);
                                                                        }            
                                                                    });
        return validThemes;
    }
    
    public List<DifferentiatorGroup> listValidDifferentiatorGroups() {
        List<DifferentiatorGroup> validDiffGroups = SWBUtils.Collections.filterIterator(super.listDifferentiatorGroups(), new GenericFilterRule<DifferentiatorGroup>() {
                                                                        @Override
                                                                        public boolean filter(DifferentiatorGroup diffgroup) {
                                                                            if(diffgroup==null) {
                                                                                return true;
                                                                            }
                                                                            User user = SWBContext.getSessionUser(getBSC().getUserRepository().getId());
                                                                            if(user==null) {
                                                                                user = SWBContext.getAdminUser();
                                                                            }
                                                                            return !diffgroup.isValid() || !user.haveAccess(diffgroup);
                                                                        }            
                                                                    });
        return validDiffGroups;
    }
    
    public List<Objective> listValidObjectives() {
        List<Objective> objectives = new ArrayList<>();
        List<Objective> objs;
        List<Theme> themes = listValidThemes();
        Collections.sort(themes);
        for(Theme t:themes) {
            objs = t.listValidObjectives();
            Collections.sort(objs);
            objectives.addAll(objs);
        }
        return objectives;
    }
    
    public List<Objective> listValidObjectives(Period period) {
        List<Objective> objectives = new ArrayList<>();
        List<Objective> objs;
        List<Theme> themes = listValidThemes();
        Collections.sort(themes);
        for(Theme t:themes) {
            objs = t.listValidObjectives();
            Collections.sort(objs);
            for(Objective o : objs) {
                if(!o.hasPeriod(period)) {
                    continue;
                }
                objectives.add(o);
            }
        }
        return objectives;
    }

    @Override
    public void validOrder(HttpServletRequest request, SemanticProperty prop, String propName) throws FormValidateException {
        int ordinal;
        try            
        {
            String value = request.getParameter(propName);
            ordinal = Integer.parseInt(value);
        }   
        catch(NumberFormatException pe)
        {            
            throw new FormValidateException("El valor debe ser numérico y no debe repetirse");
        }
        
        SemanticObject obj = getSemanticObject();
        SWBModel model = (SWBModel)obj.getModel().getModelObject().createGenericInstance();
        Iterator<SemanticObject> it = model.getSemanticModel().listInstancesOfClass(obj.getSemanticClass());
        while(it.hasNext()) {
            SemanticObject so = it.next();
            if( obj.equals(so) ) {
                continue;
            }
            if(ordinal == so.getIntProperty(prop)) {
                throw new FormValidateException("El valor ordinal debe ser numérico y no puede repetirse");
            }
        }
    }
}
