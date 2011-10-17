import java.io.File;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.codegen.CodeGenerator;
import org.semanticwb.codegen.CodeGeneratorException;

/**
 *
 * @author carlos.ramos
 */
public class Eworkplace {
    public static void main(String []args)
    {
        new Eworkplace().codeGen(args);
    }
    
    public void codeGen(String []args)
    {
        String base=SWBUtils.getApplicationPath();
        SWBPlatform.createInstance();
        //SWBPlatform.getSemanticMgr().initializeDB();
        SWBPlatform.getSemanticMgr().addBaseOntology("C:/Users/carlos.ramos/desarrollo/SWB4/swb/build/web/WEB-INF/owl/swb.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology("C:/Users/carlos.ramos/desarrollo/swbproys/Eworkplace/src/eworkplace.owl");
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        //SWBPlatform.getSemanticMgr().loadDBModels();
        SWBPlatform.getSemanticMgr().getOntology().rebind();

        try
        {
            String path = getClass().getResource("/").getPath().replaceAll("%20", " ");
            File dir = new File("C:/Users/carlos.ramos/desarrollo/swbproys/Eworkplace/src");
            CodeGenerator codeGeneration = new CodeGenerator();
            codeGeneration.generateCode("ewp", false, dir);
            System.out.println("Generación de clases completa: "+dir);
        }
        catch (CodeGeneratorException cge)
        {
            cge.printStackTrace(System.out);
        }
    }
}
