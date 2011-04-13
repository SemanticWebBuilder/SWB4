package org.semanticwb.process.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import org.semanticwb.SWBPlatform;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;


public class SubProcess extends org.semanticwb.process.model.base.SubProcessBase 
{
    public SubProcess(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Crea una instancia del objeto de flujo
     * @param fobj
     * @param pinst
     * @return
     */
    @Override
    public FlowNodeInstance createInstance(ContainerInstanceable pinst)
    {
        //System.out.println("createInstance subprocess:"+this);
        SubProcessInstance inst=(SubProcessInstance)super.createInstance(pinst);
        return inst;
    }


}
