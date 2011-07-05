import java.io.File;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.codegen.CodeGenerator;
import org.semanticwb.codegen.CodeGeneratorException;

/**
 *
 * @author carlos.ramos
 */

public class Infotec {
    public static void main(String []args)
    {
        new Infotec().codeGen(args);
    }

    public void codeGen(String []args)
    {
        String base=SWBUtils.getApplicationPath();
        SWBPlatform.createInstance();
        //SWBPlatform.getSemanticMgr().initializeDB();
        SWBPlatform.getSemanticMgr().addBaseOntology("C:/desarrollo/SWB4/swb/web/WEB-INF/owl/swb.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology("C:/desarrollo/SWB4/swb/web/WEB-INF/owl/community.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology("C:/desarrollo/swbproys/portalInfotec/infotec/src/portal.infotec.owl");
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        //SWBPlatform.getSemanticMgr().loadDBModels();
        SWBPlatform.getSemanticMgr().getOntology().rebind();

        try
        {
            String path = getClass().getResource("/").getPath().replaceAll("%20", " ");
            File dir = new File(path+"../../../../../swbproys/portalInfotec/infotec/src");
            CodeGenerator codeGeneration = new CodeGenerator();
            codeGeneration.generateCode("infotec", false, dir);
            System.out.println("Generación de clases completa: "+dir);
        }
        catch (CodeGeneratorException cge)
        {
            cge.printStackTrace();
        }
    }
}
