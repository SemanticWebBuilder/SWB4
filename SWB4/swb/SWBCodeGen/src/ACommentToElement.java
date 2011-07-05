
import java.io.File;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.codegen.CodeGenerator;
import org.semanticwb.codegen.CodeGeneratorException;

public class ACommentToElement {
    public static void main(String []args)
    {
        new ACommentToElement().codeGen(args);
    }

    public void codeGen(String []args)
    {
        String base=SWBUtils.getApplicationPath();
        SWBPlatform.createInstance();
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../web/WEB-INF/owl/swb.owl");
        //SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../SWBPortal/src/org/semanticwb/portal/resources/sem/SWBDocuments.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology("C:/desarrollo/amafore/amafore/src/org/amafore/intranet/swb/comments/ACommentToElement.owl");
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        SWBPlatform.getSemanticMgr().getOntology().rebind();

        try
        {
            String path = getClass().getResource("/").getPath().replaceAll("%20", " ");
            //File dir = new File(path+"../../../SWBPortal/src");
            File dir = new File("C:/desarrollo/amafore/amafore/src");
            CodeGenerator codeGeneration = new CodeGenerator();
            codeGeneration.generateCode("acmtte",false,dir);
            System.out.println("Generación de clases completa");
        }
        catch (CodeGeneratorException cge)
        {
            cge.printStackTrace();
        }
    }
}
