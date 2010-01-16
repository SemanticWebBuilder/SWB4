
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.jcr283.implementation.SWBRepository;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author victor.lorenzana
 */
public class TestRepository {

    public static void main(String[] args)
    {
        String base=SWBUtils.getApplicationPath();
        SWBPlatform.createInstance();
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../web/WEB-INF/owl/swb.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../web/WEB-INF/owl/jcr283.owl");
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        SWBPlatform.getSemanticMgr().getOntology().rebind();
        SWBRepository r=new SWBRepository();
    }
}
