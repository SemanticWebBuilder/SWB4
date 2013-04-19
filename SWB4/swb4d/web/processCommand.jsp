<%-- 
    Document   : processCommand
    Created on : 22-oct-2012, 13:40:01
    Author     : javier.solis.g
--%><%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Calendar"%><%@page import="org.semanticwb.nlp.CommandTranslator.Command"%><%@page import="java.util.ArrayList"%><%@page import="java.util.HashMap"%><%@page import="org.semanticwb.nlp.CommandTranslator.CommandParser"%><%@page import="java.util.List"%><%@page import="org.semanticwb.domotic.server.WebSocketServlet"%><%@page import="org.semanticwb.domotic.model.DomContext"%><%@page import="org.semanticwb.model.GenericObject"%><%@page import="org.semanticwb.platform.SemanticObject"%><%@page import="org.semanticwb.model.SWBContext"%><%@page import="org.semanticwb.domotic.model.SWB4DContext"%><%@page import="org.semanticwb.model.WebSite"%><%@page import="org.semanticwb.domotic.model.DomGroup"%><%@page import="org.semanticwb.domotic.model.DomDevice"%><%@page import="java.util.Iterator"%><%@page import="org.semanticwb.SWBUtils"%><%@page import="org.semanticwb.model.SWBModel"%><%@page contentType="text/html" pageEncoding="UTF-8"%><%!


    //***Crear objecto de la clase command parser
//    CommandParser cp=null;
    
    
    private CommandParser ini(SWBModel model)     
    {
        CommandParser cp=null;
        //if(cp==null)
        {
            cp = new CommandParser();

            //***Opcional. Asignar las acciones disponibles con un hash map <sinonimo,acción>
            //Estas son las acciones por default si no se especifica alguna
            //En el futuro se contempla el uso de un diccionario de verbos
            HashMap Actions = new HashMap();

            Actions.put("encender", "Encender");
            Actions.put("conectar", "Encender");
            Actions.put("activar", "Encender");
            Actions.put("prender", "Encender");
            Actions.put("cambiar", "Encender");

            Actions.put("apagar", "Apagar");
            Actions.put("desactivar", "Apagar");
            Actions.put("cortar", "Apagar");
            Actions.put("desconectar", "Apagar");

            cp.setActions(Actions);
        }
        
        //***Asignar un arreglo de los elementos disponibles (Catálogo de elementos)
        ArrayList<String> arr=new ArrayList<String>();
        
        Iterator<DomDevice> it = DomDevice.ClassMgr.listDomDevices(model);
        while (it.hasNext())
        {
            DomDevice dev = it.next();
            String title=dev.getDisplayTitle("es");

            if (title!=null)
            {
                arr.add(title);
            }
        }   

        Iterator<DomGroup> it2 = DomGroup.ClassMgr.listDomGroups(model);
        while (it.hasNext())
        {
            DomGroup dev = it2.next();
            String title=dev.getDisplayTitle("es");

            if (title!=null)
            {
                arr.add(title);
            }
        }  
        
        Iterator<DomContext> it3 = DomContext.ClassMgr.listDomContexts(model);
        while (it.hasNext())
        {
            DomContext dev = it3.next();
            String title=dev.getDisplayTitle("es");

            if (title!=null)
            {
                arr.add(title);
            }
        }          
                                         
        cp.setElements(arr.toArray(new String[0]));   
        return cp;            
    }

 
    String processComands(String commands, SWBModel model)
    {
        System.out.println("data:"+commands);
        String ret = "Comando no reconocido";
        if (commands != null)
        {
            
            try
            {
                CommandParser cp=ini(model);
            
                //try{ret=SWBUtils.TEXT.decode(ret, "utf8");}catch(Exception e){e.printStackTrace();}
                if (commands.startsWith("["))
                {
                    commands = commands.substring(1, commands.length() - 1);
                }
                
                //***Parsear un conjunto de oraciones que serán resultado de las variaciones de la api 
                //de voz de google dada una oración
                String sentences[]=commands.split(",");
                
                //System.out.println(sentences+" "+sentences.length);
                
                //El parseo devuelve un objeto de la clase Command
                // {String Element(perteneciente a los elementos agregados), 
                //  String Action(perteneciente al catálogo de acciones),
                //  int Intensidad( 0 <= Intensidad <= 16, -1 no especificado),
                //  Calendar Time
                //}
                Command command = cp.parseCommand(sentences);

                String ele=command.getElement();
                String act=command.getAction();
                int inte=command.getIntensity();
                Calendar cal=command.getTime();
                
                System.out.println("Selected element: " + ele);
                System.out.println("Selected action: " + act);
                System.out.println("Selected intensity: " + inte);
                System.out.println("Selected time: " + cal);             


                if(ele!=null)
                {
                    //Dispositivos
                    Iterator<DomDevice> it = DomDevice.ClassMgr.listDomDevices(model);
                    while (it.hasNext())
                    {
                        DomDevice dev = it.next();
                        String title=dev.getDisplayTitle("es");

                        if (ele.equals(title))
                        {
                            if(act==null && inte<0)
                            {
                                if(dev.getStatus()!=0)
                                {
                                  //dev.setStatus(0);                                    
                                    ret="El dispositivo "+title+" se encuentra activo";
                                    if(dev.getStatus()<16)
                                    {
                                        ret+=" al "+((int)(dev.getStatus()*100)/16)+"%";
                                    }
                                }
                                else
                                {
                                    ret="El dispositivo "+title+" se encuentra inactivo";
                                  //dev.setStatus(16);
                                }
                                
                            }else if(act!=null && act.equals("Apagar"))
                            {
                                dev.setStatus(0);
                                ret="El dispositivo "+title+" se ha desactivado";
                            }else
                            {
                                if(inte>-1)
                                {
                                    dev.setStatus(inte);
                                }else
                                {
                                    dev.setStatus(16);
                                }
                                
                                if(dev.getStatus()!=0)
                                {
                                  //dev.setStatus(0);                                    
                                    ret="El dispositivo "+title+" se ha activado";
                                    if(dev.getStatus()<16)
                                    {
                                        ret+=" al "+((int)(dev.getStatus()*100)/16)+"%";
                                    }
                                }
                            }
                        }
                    }  

                                                    
                }
                                                               
                
            }catch(Throwable e){e.printStackTrace();}
            

            boolean found=false;
            
/*            
            if(!found)
            {
                Iterator<DomGroup> it2 = DomGroup.ClassMgr.listDomGroups(model);
                while (it2.hasNext())
                {
                    DomGroup grp = it2.next();
                    String title=grp.getDisplayTitle("es");

                    if (title!=null && ret.indexOf(title.toLowerCase()) > -1)
                    {
                        if(ret.indexOf("desactiva")>-1 || ret.indexOf("apaga")>-1 || ret.indexOf("desconecta")>-1)
                        {
                            grp.setStatus(0);
                            ret="Grupo "+grp.getDisplayTitle("es")+" desactivado";
                        }else if(ret.indexOf("activa")>-1 || ret.indexOf("enciende")>-1 || ret.indexOf("encender")>-1 || ret.indexOf("conecta")>-1)
                        {
                            grp.setStatus(16);
                            ret="Grupo \""+grp.getDisplayTitle("es")+"\" activado";
                        }else
                        {
                            if (grp.getStatus() != 0)
                            {
                                grp.setStatus(0);
                                ret="Grupo "+grp.getDisplayTitle("es")+" desactivado";
                            } else
                            {                        
                                grp.setStatus(16);
                                ret="Grupo \""+grp.getDisplayTitle("es")+"\" activado";
                            }                        
                        }                                          
                        break;
                    }
                }            
            }
 */

        }
        System.out.println("ret:"+ret);
        return ret;
    }

    
    String processComandsOld(String commands, SWBModel model)
    {
        System.out.println(commands);
        String ret = "Comando no reconocido";;
        if (commands != null)
        {
            //try{ret=SWBUtils.TEXT.decode(ret, "utf8");}catch(Exception e){e.printStackTrace();}
            if (commands.startsWith("["))
            {
                commands = commands.substring(1, commands.length() - 1);
            }
            
            commands=commands.toLowerCase();

            boolean found=false;
            
            boolean grupo=(commands.indexOf("grupo ")>-1);
            boolean dispositivo=(commands.indexOf("dispositivo ")>-1);
            boolean contexto=(commands.indexOf("contexto ")>-1);            
            int inten=-1;
            
            Pattern p = Pattern.compile("\\d+%");
            Matcher m = p.matcher(commands); 
            if(m.find()) {
                String inte=m.group();
                inten=(int)((Integer.parseInt(inte.substring(0,inte.length()-1))*16)/100);
            }            
            
            Iterator<DomDevice> it = DomDevice.ClassMgr.listDomDevices(model);
            while (it.hasNext() && !grupo && !contexto)
            {
                DomDevice dev = it.next();
                String title=dev.getDisplayTitle("es");
                
                if (title!=null && commands.indexOf(title.toLowerCase()) > -1)
                {
                    if(commands.indexOf("estado de")>-1 || commands.indexOf("estatus de")>-1 || commands.indexOf("se encuentra")>-1)
                    {
                        if (dev.getStatus() != 0)
                        {
                            ret="El Dispositivo "+dev.getDisplayTitle("es")+" se encuentra activo";
                            
                            if(dev.getStatus()<16)
                            {
                                ret+=" al "+((int)(dev.getStatus()*100)/16)+"%";
                            }                                    
                        } else
                        {                        
                            ret="El Dispositivo "+dev.getDisplayTitle("es")+" se encuentra inactivo";
                        }                        
                    }
                    else if(commands.indexOf("desactiva")>-1 || commands.indexOf("apaga")>-1 || commands.indexOf("desconecta")>-1)
                    {
                        dev.setStatus(0);
                        ret="Dispositivo "+dev.getDisplayTitle("es")+" desactivado";
                    }else if(commands.indexOf("activa")>-1 || commands.indexOf("enciende")>-1 || commands.indexOf("encender")>-1 || commands.indexOf("conecta")>-1)
                    {
                        if(inten>-1)dev.setStatus(inten);
                        else dev.setStatus(16);
                        ret="Dispositivo "+dev.getDisplayTitle("es")+" activado";
                        if(dev.getStatus()<16)
                        {
                            ret+=" al "+((int)(dev.getStatus()*100)/16)+"%";
                        }
                    }else
                    {
                        if(inten>-1)
                        {
                            if(inten==0)
                            {
                                dev.setStatus(0);
                                ret="Dispositivo "+dev.getDisplayTitle("es")+" desactivado";
                            }else
                            {
                                dev.setStatus(inten);
                                if(inten>-1)dev.setStatus(inten);
                                else dev.setStatus(16);
                                ret="Dispositivo "+dev.getDisplayTitle("es")+" activado";
                                if(dev.getStatus()<16)
                                {
                                    ret+=" al "+((int)(dev.getStatus()*100)/16)+"%";
                                }                            
                             }
                        }else
                        {
                            if (dev.getStatus() != 0)
                            {
                                ret="El Dispositivo "+dev.getDisplayTitle("es")+" se encuentra activo";
                                if(dev.getStatus()<16)
                                {
                                    ret+=" al "+((int)(dev.getStatus()*100)/16)+"%";
                                }                            
                            } else
                            {                        
                                ret="El Dispositivo "+dev.getDisplayTitle("es")+" se encuentra inactivo";
                            }                                                  
                        }
                    }         
                    found=true;                                                  
                    break;
                }
            }
            
            
            if(!found && !dispositivo && !contexto)
            {
                Iterator<DomGroup> it2 = DomGroup.ClassMgr.listDomGroups(model);
                while (it2.hasNext())
                {
                    DomGroup grp = it2.next();
                    String title=grp.getDisplayTitle("es");

                    if (title!=null && commands.indexOf(title.toLowerCase()) > -1)
                    {
                        if(commands.indexOf("estado de")>-1 || commands.indexOf("estatus de")>-1 || commands.indexOf("se encuentra")>-1)
                        {
                            if (grp.getStatus() != 0)
                            {
                                ret="El Grupo "+grp.getDisplayTitle("es")+" se encuentra activo";
                            } else
                            {                        
                                ret="El Grupo "+grp.getDisplayTitle("es")+" se encuentra inactivo";
                            }                        
                        }
                        else if(commands.indexOf("desactiva")>-1 || commands.indexOf("apaga")>-1 || commands.indexOf("desconecta")>-1)
                        {
                            grp.setStatus(0);
                            ret="Grupo "+grp.getDisplayTitle("es")+" desactivado";
                        }else if(commands.indexOf("activa")>-1 || commands.indexOf("enciende")>-1 || commands.indexOf("encender")>-1 || commands.indexOf("conecta")>-1)
                        {
                            grp.setStatus(16);
                            ret="Grupo "+grp.getDisplayTitle("es")+" activado";
                        }else
                        {
                            /*
                            if (grp.getStatus() != 0)
                            {
                                grp.setStatus(0);
                                ret="Grupo "+grp.getDisplayTitle("es")+" desactivado";
                            } else
                            {                        
                                grp.setStatus(16);
                                ret="Grupo "+grp.getDisplayTitle("es")+" activado";
                            } 
                            */
                            if (grp.getStatus() != 0)
                            {
                                ret="El Grupo "+grp.getDisplayTitle("es")+" se encuentra activo";
                            } else
                            {                        
                                ret="El Grupo "+grp.getDisplayTitle("es")+" se encuentra inactivo";
                            }                                                   
                        }          
                        found=true;                                                       
                        break;
                    }
                }                            
            }
            
            if(!found && !dispositivo && !grupo)
            {
                Iterator<DomContext> it2 = DomContext.ClassMgr.listDomContexts(model);
                while (it2.hasNext())
                {
                    DomContext grp = it2.next();
                    String title=grp.getDisplayTitle("es");

                    if (title!=null && commands.indexOf(title.toLowerCase()) > -1)
                    {
                        if(commands.indexOf("estado de")>-1 || commands.indexOf("estatus de")>-1 || commands.indexOf("se encuentra")>-1)
                        {
                            if (grp.isActive())
                            {
                                ret="El Contexto "+grp.getDisplayTitle("es")+" se encuentra activo";
                            } else
                            {                        
                                ret="El Contexto "+grp.getDisplayTitle("es")+" se encuentra inactivo";
                            }                        
                        }
                        else if(commands.indexOf("desactiva")>-1 || commands.indexOf("apaga")>-1 || commands.indexOf("desconecta")>-1)
                        {
                            //grp.setStatus(0);
                            ret="Para desactivar el Contexto "+grp.getDisplayTitle("es")+" debe de activar otro contexto";
                        }else if(commands.indexOf("cambia")>-1 || commands.indexOf("activa")>-1 || commands.indexOf("enciende")>-1 || commands.indexOf("encender")>-1 || commands.indexOf("conecta")>-1)
                        {
                            grp.setActive(true);
                            ret="Contexto "+grp.getDisplayTitle("es")+" activado";
                        }else
                        {
                            if (grp.isActive())
                            {
                                ret="El Contexto "+grp.getDisplayTitle("es")+" se encuentra activo";
                            } else
                            {                        
                                ret="El Contexto "+grp.getDisplayTitle("es")+" se encuentra inactivo";
                            }                                                  
                        }    
                        found=true;                                                             
                        break;
                    }
                }          
            }                             

        }
        
        return ret;
    }

%><%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");

    WebSite site = SWBContext.getWebSite("dom");
    SWB4DContext domotic = SWB4DContext.getInstance(site);
    SWB4DContext.getServer().setModel(site);
    
    String suri = request.getParameter("suri");
    String sstat = request.getParameter("stat");
    String sdata = request.getParameter("data");
    String gmsg = request.getParameter("gmsg");
    
    //System.out.println("suri:"+suri);
    //System.out.println("sstat:"+sstat);
    //System.out.println("sdata:"+sdata);
    //System.out.println("gmsg:"+gmsg);
    
    if (suri != null)
    {
        String uri=SemanticObject.shortToFullURI(suri);
        GenericObject obj=SemanticObject.createSemanticObject(uri).createGenericInstance();
        if(obj instanceof DomDevice)
        {        
            DomDevice dev = (DomDevice)obj;
            dev.setStatus(Integer.parseInt(sstat));
        }else if(obj instanceof DomGroup)
        {
            DomGroup grp=(DomGroup)obj;
            grp.setStatus(Integer.parseInt(sstat));
        }else if(obj instanceof DomContext)
        {
            DomContext cnt=(DomContext)obj;
            cnt.setActive(Boolean.parseBoolean(sstat));
        }
    }    

    if(sdata!=null)
    {
        out.println(processComandsOld(sdata, site));
    }
    
    if(gmsg!=null)
    {
        Iterator<String> it=WebSocketServlet.getLastMessages().iterator();
        while(it.hasNext())
        {
            out.println(it.next());
        }
    }
    
%>
