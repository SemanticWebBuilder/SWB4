
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.HashSet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author javier.solis.g
 */
public class MeshModels
{
    public static void main(String arg[])
    {        
        HashSet set=new HashSet();
//*        
        set.add("swb_Collection");
        set.add("swb_Country");
        set.add("swb_Device");
        set.add("swb_FriendlyURL");
        set.add("swb_Language");
        set.add("swb_ModelProperty");
        set.add("swb_ResourceFilter");
        set.add("swb_ResourceSubType");
        set.add("swb_ResourceType");
        set.add("swb_RoleRef");
        set.add("swb_TemplateGroup");
        set.add("swb_TemplateRef");
        set.add("swb_VersionInfo");
        set.add("swb_Rule");
        set.add("swb_RuleRef");
        set.add("Resource");
        set.add("Template");
        set.add("WebPage");
        set.add("swbres_HTMLContent");
        
        set.add("swb_res_cmts_SWBComments");
        set.add("catalog_SWBCatalogResource");
        set.add("catalogcomment_CatalogoComment");
        set.add("icono_CatalogoIconoEntidad");
        set.add("catalogvgn_CatalogResource");
        set.add("leyenda_CatalogoLeyendasCapa");
//*/
/*        
        set.add("counter");
        set.add("vgn_Noticias");
        set.add("vgn_VariablesSistema");
        //set.add("vgn_Bitacora");
        set.add("vgn_Capa");
        set.add("vgn_Codigo");
        set.add("vgn_ConsolaEventoCapa");
        set.add("vgn_ContactoMetadatos");
        set.add("vgn_Entidad");
        set.add("vgn_EstadoRelevancia");
        set.add("vgn_FormatoCapa");
        set.add("vgn_GeometriaCapa");
        set.add("vgn_IdentificacionConjunto");
        set.add("vgn_MunicipioRelevancia");
        set.add("vgn_RecursoDigitalBusqueda");
        set.add("vgn_Tema");
        set.add("vgn_TipoCapa");
        set.add("vgn_TipoCatalogo");
        set.add("vgn_TipoEventoCapa");
        set.add("vgn_TipoGeometria");
        set.add("vgn_TipoUbicacionRelevancia");
        set.add("vgn_MedioContacto");
        set.add("vgn_ResponsabilidadConjuntoDatos");
        set.add("vgn_TipoMedio");
        set.add("vgn_LocalidadRelevancia");
        set.add("vgn_Sector");
        set.add("vgn_TipoEntidad");
        set.add("vgn_Dimension");
        set.add("vgn_Eje");
        set.add("vgn_Atributos");
        set.add("vgn_CalidadInformacion");
        set.add("vgn_Pais");
        set.add("vgn_SistemaReferencia");
        set.add("vgn_UbicacionContacto");
        set.add("vgn_UbicacionGeografica");
        set.add("vgn_IconoEntidad");
        set.add("vgn_Coleccion");
        set.add("vgn_DominiosEmail");
        set.add("vgn_LeyendaCapa");
        set.add("vgn_FormaPresentacion");
        set.add("vgn_FormatoRecurso");
        set.add("vgn_RecursoDigital");
        set.add("vgn_EstatusCapa");
        set.add("vgn_TemasINEGI");
        //set.add("vgn_RecursoGeograficoBusqueda");
        set.add("vgn_GrupoDatosEspaciales");
        set.add("vgn_Favoritos");
        set.add("vgn_NoticiasGeneral");
        set.add("vgn_NoticiasMapa");
        set.add("vgn_ComentariosCapa");
*/        
        
        
        String file="/data/vgn_des.nt";
        //String file="/data/vgn_prod.nt";
        String fout=file+"2";
        try
        {
            PrintWriter out=new PrintWriter(new FileOutputStream(fout));
            DataInputStream data=new DataInputStream(new FileInputStream(file));
            String line;
            int x=0;
            while((line=data.readLine())!=null)// && x<100)
            {
                int i=0;
                int j=0;
                int k=0;
                for(int y=0;y<line.length();y++)
                {
                    char ch=line.charAt(y);
                    if(y==0 && ch!='<')break;
                    if(ch=='>')
                    {
                        i=y;
                        break;
                    }else if(ch=='#')
                    {
                        j=y;
                    }else if(ch==':')
                    {
                        k=y;
                    }
                }

                if(i>0 && j<k)
                {
                    String cls=line.substring(j+1,k);
//                    if(!set.contains(cls))
//                    {
//                        set.add(cls);
//                        System.out.println(cls);
//                    }
                    
                    if(set.contains(cls))
                    {
                        out.println(line);
                    }
                }else
                {
                    out.println(line);
                }
                x++;
            }
            out.flush();
            out.close();            
        }catch(Exception e)
        {e.printStackTrace();} 
    }
}
