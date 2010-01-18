
import java.io.File;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.codegen.CodeGenerator;
import org.semanticwb.codegen.CodeGeneratorException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author victor.lorenzana
 */
public class CodeGeneratorJSR283 {

    public static void main(String []args)
    {
        new CodeGeneratorJSR283().codeGen(args);
    }

    public void codeGen(String []args)
    {
        String base=SWBUtils.getApplicationPath();
        SWBPlatform.createInstance();        
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../web/WEB-INF/owl/swb.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../web/WEB-INF/owl/jcr283.owl");        
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();        
        SWBPlatform.getSemanticMgr().getOntology().rebind();

        try
        {
            String path = getClass().getResource("/").getPath().replaceAll("%20", " ");
            File dir = new File(path+"../../../SWBJSR283Model/src");
            CodeGenerator codeGeneration = new CodeGenerator();
            codeGeneration.generateCode("nt", false,dir);
            codeGeneration.generateCode("mix", false,dir);
            codeGeneration.generateCode("swbrep", false,dir);
            codeGeneration.generateCode("jcr", false,dir);
            System.out.println("Generación de clases completa");
        }
        catch (CodeGeneratorException cge)
        {
            cge.printStackTrace();
        }
    }
}
