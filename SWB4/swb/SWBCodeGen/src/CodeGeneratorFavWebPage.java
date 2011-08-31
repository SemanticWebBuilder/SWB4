
import java.io.File;
import java.net.URI;
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
public class CodeGeneratorFavWebPage
{

    public static void main(String[] args)
    {
        new CodeGeneratorFavWebPage().codeGen(args);
    }

    public void codeGen(String[] args)
    {
        String base = SWBUtils.getApplicationPath();
        SWBPlatform.createInstance();
        //SWBPlatform.getSemanticMgr().initializeDB();
        try
        {
            URI file = new URI(base + "../../../../web/WEB-INF/owl/swb.owl");
            file = file.normalize();
            String _path = file.toURL().getFile();
            File _file = new File(_path);
            SWBPlatform.getSemanticMgr().addBaseOntology(_file.getAbsolutePath());
            file = new URI(base + "../../../../web/WEB-INF/owl/favwebpages.owl");
            file = file.normalize();
            _path = file.toURL().getFile();
            _file = new File(_path);

            SWBPlatform.getSemanticMgr().addBaseOntology(_file.getAbsolutePath());
            SWBPlatform.getSemanticMgr().loadBaseVocabulary();
            //SWBPlatform.getSemanticMgr().loadDBModels();
            SWBPlatform.getSemanticMgr().getOntology().rebind();

            try
            {
                String path = getClass().getResource("/").getPath().replaceAll("%20", " ");
                File dir = new File(path + "../../../SWBPortal/src");
                CodeGenerator codeGeneration = new CodeGenerator();
                codeGeneration.generateCode("fav", false, dir);
                System.out.println("Generación de clases completa");
            }
            catch (CodeGeneratorException cge)
            {
                cge.printStackTrace();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
