
import java.io.File;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.codegen.CodeGenerator;
import org.semanticwb.codegen.CodeGeneratorException;
import org.semanticwb.platform.SemanticMgr;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author victor.lorenzana
 */
public class CodeGeneratorJSR170 {


    public static void main(String []args)
    {
        new CodeGeneratorJSR283().codeGen(args);
    }

    public void codeGen(String []args)
    {
        String base=SWBUtils.getApplicationPath();
        SemanticMgr.setSchemaModel(SemanticMgr.ModelSchema.OWL_LITE_MEM_RDFS_INF);
        SWBPlatform.createInstance();
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../web/WEB-INF/owl/swb.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../web/WEB-INF/owl/swb_rep.owl");
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        SWBPlatform.getSemanticMgr().getOntology().rebind();

        try
        {
            String path = getClass().getResource("/").getPath().replaceAll("%20", " ");
            File dir = new File(path+"../../../SWBRepository170/src");
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
