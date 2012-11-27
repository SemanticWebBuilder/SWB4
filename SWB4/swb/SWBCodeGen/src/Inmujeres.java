import java.io.File;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.codegen.CodeGenerator;
import org.semanticwb.codegen.CodeGeneratorException;

/**
 *
 * @author carlos.ramos
 */
public class Inmujeres {
    public static void main(String []args)
    {
        new Inmujeres().codeGen(args);
    }
    
    public void codeGen(String []args)
    {
        String base=SWBUtils.getApplicationPath();
        SWBPlatform.createInstance();
        //SWBPlatform.getSemanticMgr().initializeDB();
        SWBPlatform.getSemanticMgr().addBaseOntology("C:/Users/carlos.ramos/desarrollo/SWB4/swb/build/web/WEB-INF/owl/swb.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology("C:/Users/carlos.ramos/desarrollo/swbproys/inm/src/inm.owl");
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        //SWBPlatform.getSemanticMgr().loadDBModels();
        SWBPlatform.getSemanticMgr().getOntology().rebind();

        try
        {
            String path = getClass().getResource("/").getPath().replaceAll("%20", " ");
            File dir = new File("C:/Users/carlos.ramos/desarrollo/swbproys/inm/src");
            CodeGenerator codeGeneration = new CodeGenerator();
            codeGeneration.generateCode("inm", false, dir);
            System.out.println("La generación de clases completa: "+dir);
        }
        catch (CodeGeneratorException cge)
        {
            cge.printStackTrace(System.out);
        }
    }
}
