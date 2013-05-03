
import java.io.File;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.codegen.CodeGenerator;
import org.semanticwb.codegen.CodeGeneratorException;

/**
 *
 * @author juan.fernandez
 */
public class CodeGeneratorLODP {
     public static void main(String []args)
    {
        new CodeGeneratorLODP().codeGen(args);
    }
    
    public void codeGen(String []args)
    {
        String base=SWBUtils.getApplicationPath();
        SWBPlatform.createInstance();
         
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../../web/WEB-INF/owl/swb.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../../web/WEB-INF/owl/ext/lodp.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../../web/WEB-INF/owl/ext/lodpCodeGen.owl");
       
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        SWBPlatform.getSemanticMgr().getOntology().rebind();

        try
        {
            String path = getClass().getResource("/").getPath().replaceAll("%20", " ");
            File dir = new File(path+"/../../../../../swbproys/LODP/src");
            CodeGenerator codeGeneration = new CodeGenerator();
            //codeGeneration.generateCode("lodp", false, dir);
            codeGeneration.generateCode("lodpcg", false, dir);
            System.out.println("Generación de clases completa: "+dir);
        }
        catch (CodeGeneratorException cge)
        {
            cge.printStackTrace(System.out);
        }
    }
}
