package org.semanticwb.social;

import org.semanticwb.model.Activeable;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
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
                    //System.out.println("FacePageTab-2:"+parentPage);
                    if(parentPage instanceof FacebookFanPage)
                    {
                        FacebookFanPage faceFanPage=(FacebookFanPage)parentPage;
                        //System.out.println("FacePageTab-3:"+faceFanPage);
                        if(!faceFanPage.isFp_notLinked() && faceFanPage.getSn_socialNet()!=null && faceFanPage.getSn_socialNet() instanceof Pageable)
                        {
                            Pageable pageable=(Pageable)faceFanPage.getSn_socialNet();
                            //System.out.println("FacePageTab-4:"+pageable);
                            if(pageable!=null)
                            {
                                if(prop!=null)
                                {
                                    SemanticProperty semProp = (SemanticProperty) prop;
                                    //System.out.println("FacePageTab-5:"+action+",semProp:"+semProp.getName()+",semPropValue:"+obj.getProperty(semProp)+"Activeble:"+Activeable.swb_active.getName());
                                    if((action.equalsIgnoreCase("SET") && semProp!=null && (semProp.getName().equals(Activeable.swb_active.getName()) ||
                                            semProp.getName().equals(FacePageTab.social_face_appid.getName()))))
                                    {
                                        if(semProp.getName().equals(Activeable.swb_active.getName()))   //Si la propiedad es Active
                                        {
                                            if(obj.getBooleanProperty(semProp)) //Si se activa el tab
                                            {
                                                if(facePageTab.getFace_appid()!=null)   //Crear Tab
                                                {
                                                    //System.out.println("FacePageTab-6-Eliminar Antiguo");
                                                    if(facePageTab.getFace_old_appid()!=null){
                                                        pageable.removePageTab(faceFanPage, facePageTab.getFace_old_appid());
                                                    }
                                                    if(pageable.createPageTab(facePageTab))  //Si facebook regresa que si se pudo crear el tab en la página
                                                    {
                                                        //System.out.println("FacePageTab-6.6-Crear Nuevo");
                                                        facePageTab.setFace_old_appid(facePageTab.getFace_appid());
                                                    }
                                                }
                                            }else if(!obj.getBooleanProperty(semProp))
                                            {
                                                if(facePageTab.getFace_appid()!=null)   //Eliminar Tab
                                                {
                                                    //System.out.println("FacePageTab-7--Eliminar");
                                                    pageable.removePageTab(facePageTab);
                                                }
                                            }
                                        }else if(semProp.getName().equals(FacePageTab.social_face_appid.getName()))   //Si la propiedad es app_id
                                        {
                                            //System.out.println("Va eliminar x propiedad K");
                                            if(facePageTab.isActive())
                                            {
                                                if(facePageTab.getFace_old_appid()!=null){
                                                    //System.out.println("Va eliminar x propiedad K-1");
                                                    pageable.removePageTab(faceFanPage, facePageTab.getFace_old_appid());
                                                }
                                                if(pageable.createPageTab(facePageTab))  //Si facebook regresa que si se pudo crear el tab en la página
                                                {
                                                    //System.out.println("Va crear x propiedad K-2");
                                                    facePageTab.setFace_old_appid(facePageTab.getFace_appid());
                                                }
                                            }
                                        }
                                    }else if((action.equalsIgnoreCase("REMOVE") && semProp.getName().equals(FacePageTab.social_face_appid.getName()))) //Set a null la propiedad app_id
                                    {
                                        if(facePageTab.isActive())
                                        {
                                            //System.out.println("FacePageTab-7--Eliminar Por Propiedad");
                                            pageable.removePageTab(facePageTab);
                                        }
                                    }
                                }else if(action.equalsIgnoreCase("REMOVE"))  //Elimina el FacePageTab
                                {
                                    //System.out.println("FacePageTab-7--Eliminar Por Eliminar");
                                    pageable.removePageTab(facePageTab);
                                }
                            }
                        }
                    }
                }
            }
        });
        
                
    }
    
    
}
