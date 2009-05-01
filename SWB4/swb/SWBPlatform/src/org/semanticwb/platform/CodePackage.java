/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.platform;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import java.util.HashMap;
import java.util.Iterator;
import org.semanticwb.SWBPlatform;

/**
 *
 * @author javier.solis
 */
public class CodePackage
{
    private HashMap<String, String> map;
            
    public CodePackage()
    {
        HashMap map=new HashMap();
    }

    public String getPackage(String prefix)
    {
        String spkg=map.get(prefix);
        if(spkg==null)
        {
            SemanticProperty pfx=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(SemanticVocabulary.SWB_PROP_PREFIX);
            SemanticProperty pkg=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(SemanticVocabulary.SWB_PROP_PACKAGE);
            Iterator<Resource> it=SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().listSubjectsWithProperty(pfx.getRDFProperty(), prefix);
            while(it.hasNext())
            {
                Resource res=it.next();
                Statement st=res.getProperty(pkg.getRDFProperty());
                if(st!=null)
                {
                    spkg=st.getString();
                }
            }
        }
        return spkg;
    }
}
