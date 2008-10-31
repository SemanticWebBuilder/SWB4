package org.semanticwb.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.base.*;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.platform.SemanticVocabulary;

public class FormView extends FormViewBase 
{
    public static final String MODE_CREATE="create";
    public static final String MODE_VIEW="view";
    public static final String MODE_EDIT="edit";
    
    private SemanticOntology ont=SWBPlatform.getSemanticMgr().getOntology();
    private SemanticProperty propertyMode=new SemanticProperty(ont.getRDFOntModel().getProperty(SemanticVocabulary.URI+"propertyMode"));
    private SemanticProperty propertyRef=new SemanticProperty(ont.getRDFOntModel().getProperty(SemanticVocabulary.URI+"propertyRef"));
    
    SWBVocabulary vocabulary=SWBContext.getVocabulary();
    
    public FormView(SemanticObject base)
    {
        super(base);
    }

    /**
     * Regrasa Mapa con las propiedades como llave y el modo relacionado como valor
     * @param mode (modo por default de la vista)
     * @return
     */
    public Map<SemanticProperty,String> getPropertyMap(String mode) 
    {
        HashMap<SemanticProperty,String> map=new HashMap();
        Iterator<SemanticObject> it=null;
        if(mode.equals(MODE_CREATE))it=listCreatePropertys();
        if(mode.equals(MODE_VIEW))it=listViewPropertys();
        if(mode.equals(MODE_EDIT))it=listEditPropertys();
        if(it!=null)
        {
            while(it.hasNext())
            {
                SemanticObject obj=it.next();
                SemanticProperty prop=obj.transformToSemanticProperty();
                String modeaux=obj.getProperty(propertyMode);
                if(modeaux==null)modeaux=mode;
                SemanticObject ref=obj.getObjectProperty(propertyRef);                
                if(ref!=null)prop=ref.transformToSemanticProperty();
                if(prop!=null)map.put(prop,modeaux);
            }
        }
        return map;
    }
    
    
}
