/**
* SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integraciÃ³n,
* colaboraciÃ³n y conocimiento, que gracias al uso de tecnologÃ­a semÃ¡ntica puede generar contextos de
* informaciÃ³n alrededor de algÃºn tema de interÃ©s o bien integrar informaciÃ³n y aplicaciones de diferentes
* fuentes, donde a la informaciÃ³n se le asigna un significado, de forma que pueda ser interpretada y
* procesada por personas y/o sistemas, es una creaciÃ³n original del Fondo de InformaciÃ³n y DocumentaciÃ³n
* para la Industria INFOTEC, cuyo registro se encuentra actualmente en trÃ¡mite.
*
* INFOTEC pone a su disposiciÃ³n la herramienta SemanticWebBuilder a travÃ©s de su licenciamiento abierto al pÃºblico (â€˜open sourceâ€™),
* en virtud del cual, usted podrÃ¡ usarlo en las mismas condiciones con que INFOTEC lo ha diseÃ±ado y puesto a su disposiciÃ³n;
* aprender de Ã©l; distribuirlo a terceros; acceder a su cÃ³digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
* todo ello de conformidad con los tÃ©rminos y condiciones de la LICENCIA ABIERTA AL PÃšBLICO que otorga INFOTEC para la utilizaciÃ³n
* del SemanticWebBuilder 4.0.
*
* INFOTEC no otorga garantÃ­a sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implÃ­cita ni explÃ­cita,
* siendo usted completamente responsable de la utilizaciÃ³n que le dÃ© y asumiendo la totalidad de los riesgos que puedan derivar
* de la misma.
*
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposiciÃ³n la siguiente
* direcciÃ³n electrÃ³nica:
*  http://www.semanticwebbuilder.org
**/

import java.io.File;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.codegen.CodeGenerator;
import org.semanticwb.codegen.CodeGeneratorException;

/**
 *
 *
 * @author serch
 */
public class CodeGeneratorProjectDriver
{
    public static void main(String []args)
    {
        new CodeGeneratorProjectDriver().codeGen(args);
    }

    public void codeGen(String []args)
    {
        String base=SWBUtils.getApplicationPath();
        SWBPlatform.createInstance();
        //SWBPlatform.getSemanticMgr().initializeDB();
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../web/WEB-INF/owl/swb.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../web/WEB-INF/owl/swb_proy.owl");
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        //SWBPlatform.getSemanticMgr().loadDBModels();
        SWBPlatform.getSemanticMgr().getOntology().rebind();

        try
        {
            String path = getClass().getResource("/").getPath().replaceAll("%20", " ");
            File dir = new File(path+"../../../SWBPortal/src");
            CodeGenerator codeGeneration = new CodeGenerator();
            codeGeneration.generateCode("swbproy",false,dir);
            System.out.println("Generación de clases completa");
        }
        catch (CodeGeneratorException cge)
        {
            cge.printStackTrace();
        }
    }
}

