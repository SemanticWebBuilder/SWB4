
import java.io.File;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.codegen.CodeGenerator;
import org.semanticwb.codegen.CodeGeneratorException;

/**
 *
 * @author carlos.ramos
 */
public class SlideShow {
    public static void main(String []args)
    {
        new SlideShow().codeGen(args);
    }

    public void codeGen(String []args)
    {
        String base=SWBUtils.getApplicationPath();
        SWBPlatform.createInstance();
        //SWBPlatform.getSemanticMgr().initializeDB();
        SWBPlatform.getSemanticMgr().addBaseOntology("C:/desarrollo/SWB4/swb/web/WEB-INF/owl/swb.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology("C:/desarrollo/SWB4/swb/SWBPortal/src/org/semanticwb/portal/resources/sem/slideshow/SlideShow.owl");
        //SWBPlatform.getSemanticMgr().addBaseOntology("C:/desarrollo/swbproys/promexico/portalpromex/src/mx/gob/se/promexico/portal/swb/Carousel.owl");
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        //SWBPlatform.getSemanticMgr().loadDBModels();
        SWBPlatform.getSemanticMgr().getOntology().rebind();

        try
        {
            String path = getClass().getResource("/").getPath().replaceAll("%20", " ");
            File dir = new File(path+"../../../SWBPortal/src");
            CodeGenerator codeGeneration = new CodeGenerator();
            codeGeneration.generateCode("slideshow", false, dir);
            System.out.println("Generación de clases completa: "+dir);
        }
        catch (CodeGeneratorException cge)
        {
            cge.printStackTrace();
        }
    }    
}
