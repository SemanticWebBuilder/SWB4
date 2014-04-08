package org.semanticwb.social;

import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;
import org.semanticwb.platform.SemanticProperty;


   /**
   * Clase que controla un tab de page en Facebook 
   */
public class FacePageTab extends org.semanticwb.social.base.FacePageTabBase 
{
    public FacePageTab(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
      static {
       //Observador de la clase "FacePageTab", cada que haya un cambio en ella se ejecuta el siguiente código
        FacePageTab.sclass.registerObserver(new SemanticObserver() {
            @Override
            public void notify(SemanticObject obj, Object prop, String lang, String action) {
                if(action!=null && obj.instanceOf(FacePageTab.social_FacePageTab))
                {
                    FacePageTab facePageTab = (FacePageTab) obj.createGenericInstance();
                    WebPage parentPage=facePageTab.getParent();
                    if(parentPage instanceof FacebookFanPage)
                    {
                        FacebookFanPage faceFanPage=(FacebookFanPage)parentPage;
                        if(faceFanPage.getSn_socialNet()!=null && faceFanPage.getSn_socialNet() instanceof Pageable)
                        {
                            Pageable pageable=(Pageable)faceFanPage.getSn_socialNet();
                            SemanticProperty semProp = (SemanticProperty) prop;;
                            System.out.println("facePageTab/Accion:"+action+", facePageTab:"+facePageTab.getURI()+",semProp:"+semProp);
                            if(action.equalsIgnoreCase("CREATE"))   //Quiere decir que se esta creando una instancia de la clase nueva
                            {
                                System.out.println("Entra a facePageTab/Create...");
                                
                                pageable.createPageTab(facePageTab);
                                
                                //Revisar si esta activo el facePageTab y tiene un app_id, ligar con la página
                            }else if(action.equalsIgnoreCase("REMOVE") && semProp==null)  //Quiere decir que se esta eliminando una instancia de clase completa, no una propiedad
                            {
                                System.out.println("Entra a facePageTab/Remove...");
                                
                                pageable.removePageTab(facePageTab);

                                //Desligar app_id de página.
                            }else if((action.equalsIgnoreCase("SET") && (semProp.getURI().equals("http://www.semanticwebbuilder.org/swb4/ontology#active") || semProp.getURI().equals(FacePageTab.social_face_appid.getURI()))))
                            {
                                //Revisar

                                //si desactivaron el FacePageTab, eliminar el tab de la página

                                //Si activaron el el FacePageTab,  y tiene un app_id, ligarlo con la página

                                //Si cambiaron el app_id y esta activa la FacePageTab, eliminar el app_id(desligar) y ligar el nuevo (aunque haya sido el anterior)
                            }
                        }
                    }
                }
            }
        });
        
                
    }
    
    
}
