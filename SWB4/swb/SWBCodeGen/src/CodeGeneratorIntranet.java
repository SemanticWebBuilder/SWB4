
import java.io.File;
import java.util.Date;
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
public class CodeGeneratorIntranet
{

    public static void main(String[] args)
    {
        try
        {
            new CodeGeneratorIntranet().codeGen(args);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void codeGen(String[] args) throws Exception
    {
        String base = SWBUtils.getApplicationPath();
        System.out.println("appPath:" + base);
        SWBPlatform.createInstance();
        //SWBPlatform.getSemanticMgr().initializeDB();
        SWBPlatform.getSemanticMgr().addBaseOntology(base + "/../../../web/WEB-INF/owl/swb.owl");
        File file=new File(base + "/../../../web/WEB-INF/owl/intranet.owl");
        System.out.println("path="+ file.getPath()+" date: "+new Date(file.lastModified()));
        SWBPlatform.getSemanticMgr().addBaseOntology(base + "/../../../web/WEB-INF/owl/intranet.owl");
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        //SWBPlatform.getSemanticMgr().loadDBModels();
        SWBPlatform.getSemanticMgr().getOntology().rebind();

        try
        {
            String path = getClass().getResource("/").getPath().replaceAll("%20", " ");
            File dir = new File(path + "../../../../../swbproys/Eworkplace/src");
            try
            {
                System.out.println(dir.getCanonicalPath());
            }
            catch (Exception e)
            {
            }

            CodeGenerator codeGeneration = new CodeGenerator();
            codeGeneration.generateCode("intraxf", false, dir);
            System.out.println("Generación de clases completa");
        }
        catch (CodeGeneratorException cge)
        {
            cge.printStackTrace();
        }
    }
}
