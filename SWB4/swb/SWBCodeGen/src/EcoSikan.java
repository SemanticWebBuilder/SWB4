/**
 *
 * @author carlos.ramos
 */

import java.io.File;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.codegen.CodeGenerator;
import org.semanticwb.codegen.CodeGeneratorException;

public class EcoSikan {
    public static void main(String []args)
    {
        new EcoSikan().codeGen(args);
    }

    public void codeGen(String []args)
    {
        String base=SWBUtils.getApplicationPath();
        SWBPlatform.createInstance();
        //SWBPlatform.getSemanticMgr().initializeDB();
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../web/WEB-INF/owl/swb.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../web/WEB-INF/owl/community.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../../../swbproys/EcoSikan/src/ecosikan.infotec.com.owl");
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        //SWBPlatform.getSemanticMgr().loadDBModels();
        SWBPlatform.getSemanticMgr().getOntology().rebind();

        try
        {
            String path = getClass().getResource("/").getPath().replaceAll("%20", " ");
            File dir = new File(path+"../../../../../swbproys/EcoSikan/src");
            CodeGenerator codeGeneration = new CodeGenerator();
            codeGeneration.generateCode("ecoskn", false, dir);
            System.out.println("Generación de clases completa: "+dir);
        }
        catch (CodeGeneratorException cge)
        {
            cge.printStackTrace();
        }
    }
}
