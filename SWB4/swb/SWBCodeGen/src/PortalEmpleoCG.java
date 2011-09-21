import java.io.File;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.codegen.CodeGenerator;
import org.semanticwb.codegen.CodeGeneratorException;

/**
 *
 * @author carlos.ramos
 */

public class PortalEmpleoCG {
    public static void main(String []args)
    {
        new PortalEmpleoCG().codeGen(args);
    }

    public void codeGen(String []args)
    {
        String base=SWBUtils.getApplicationPath();
        SWBPlatform.createInstance();
        //SWBPlatform.getSemanticMgr().initializeDB();
        SWBPlatform.getSemanticMgr().addBaseOntology("C:/Users/carlos.ramos/desarrollo/SWB4/swb/web/WEB-INF/owl/swb.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology("C:/Users/carlos.ramos/desarrollo/SWB4/swb/web/WEB-INF/owl/community.owl");
        //SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../../../swbproys/PortaldelEmpleo/empleo/src/portalempleo.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology("C:/Users/carlos.ramos/desarrollo/swbproys/PortaldelEmpleo/empleo/src/portalempleo.owl");
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        //SWBPlatform.getSemanticMgr().loadDBModels();
        SWBPlatform.getSemanticMgr().getOntology().rebind();

        try
        {
            String path = getClass().getResource("/").getPath().replaceAll("%20", " ");
            File dir = new File(path+"../../../../../swbproys/PortaldelEmpleo/empleo/src");
            CodeGenerator codeGeneration = new CodeGenerator();
            codeGeneration.generateCode("portalempleo", false, dir);
            System.out.println("Generación de clases completa: "+dir);
        }
        catch (CodeGeneratorException cge)
        {
            cge.printStackTrace();
        }
    }
}
