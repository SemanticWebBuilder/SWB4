/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.portal.resources.projectdriver;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;
import org.semanticwb.model.DisplayProperty;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import java.sql.Timestamp;
import java.util.Date;
// TODO: Auto-generated Javadoc

/**
 * The Class ProjectDriverTools.
 * 
 * @author martha.jimenez
 */
public class ProjectDriverTools {
       //Agrupa en arreglos los tipos de WebPage y regresa un hashmap
       /**
        * Calculate array pages.
        * 
        * @param it the it
        * @return the hash map
        */
       public HashMap calculateArrayPages(Iterator it)
       {
           ArrayList actPageCon=new ArrayList();
           ArrayList webPage=new ArrayList();
           ArrayList proPage=new ArrayList();
           ArrayList usPageCon=new ArrayList();
           HashMap container = new HashMap();
           
           while(it.hasNext())
           {
               WebPage page1=(WebPage)it.next();
               String namewp=page1.getSemanticObject().getSemanticClass().getName();
               if(namewp.equals("ActivityContainer"))
                   actPageCon.add(page1);
               else if(namewp.equals("WebPage"))
                   webPage.add(page1);
               else if(namewp.equals("Project"))
                   proPage.add(page1);
               else if(namewp.equals("UserWebPageContainer"))
                   usPageCon.add(page1);
           }
           container.put("actPageCon", actPageCon);
           container.put("webPage",webPage);
           container.put("proPage",proPage);
           container.put("usPageCon",usPageCon);
           return container;
       }
       //Calcula la barra de progreso de avance general, la barra de avance esperado, los dias laborables disponibles y los dias restantes
       /**
        * Calculate progress project.
        * 
        * @param wp the wp
        * @param user the user
        * @param msg1 the msg1
        * @param msg2 the msg2
        * @return the string[]
        */
       public String[] calculateProgressProject(WebPage wp,User user,String msg1, String msg2)
       {
           Project wpPro = (Project)wp;
           String[] progress = new String[4];
           long f1a=0,f1b=0,projectdays2=0,useddays=-1;
           String val=msg1;

           if(wpPro.getStartDatep()!=null)
               f1a=wpPro.getStartDatep().getTime();
           if(wpPro.getEndDatep()!=null)
               f1b=wpPro.getEndDatep().getTime();
           if(f1a>0&&f1b>0){
                projectdays2 = calcuteLaborableDays(f1a,f1b);// días laborables disponibles entre las dos fechas definidas
                 useddays = calcuteLaborableDays(f1a,System.currentTimeMillis()); // días laborables al día de hoy
                float avance = ((useddays * 100) / projectdays2);
                if (avance > 100)
                    avance = 100;
                else if(avance<0)
                    avance=0;
                val =getProgressHtml(avance);
                if(val==null) val = msg1;
           }
           String avan = getProgressBar(getListLeafActivities(wp,user),msg2);
           if(avan==null) avan = msg1;
           progress[0] = avan;
           progress[1]=val;
           progress[2]=projectdays2+"";
           progress[3]=useddays+"";
           return progress;
       }

       /**
        * Calcute laborable days.
        * 
        * @param a1 the a1
        * @param a2 the a2
        * @return the long
        */
       private long calcuteLaborableDays(long a1, long a2) {
            long numdays = 0;
            long oneday = 24 * 60 * 60 * 1000;
            int nodays = 0;
            Calendar cal1 = Calendar.getInstance();
            cal1.setTimeInMillis(a1);
            Calendar cal2 = Calendar.getInstance();
            a2=a2+(23*59*59*1000);
            cal2.setTimeInMillis(a2);
            if(a1 < a2) {
                while (a1 < a2){
                    if (cal1.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && cal1.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) 
                        numdays++;
                    else
                        nodays++;
                    a1 += oneday;
                    cal1.setTimeInMillis(a1);
                }
            }else
                numdays = -1;
            return numdays;
        }
        //Obtiene los usuarios validos que esten asociados a las paginas de usuario de un sitio web.
        /**
         * Gets the user valid.
         * 
         * @param wpUs the wp us
         * @return the user valid
         */
        public ArrayList getUserValid(WebPage wpUs){
              ArrayList listUser=new ArrayList();
              Iterator<UserWebPage> itU = UserWebPage.ClassMgr.listUserWebPageByParent(wpUs, wpUs.getWebSite());
              while(itU.hasNext()){
                  UserWebPage uwpi= itU.next();
                  if(uwpi.isActive()&& uwpi!=null && uwpi.isVisible()&& uwpi.getChild()==null && !uwpi.isHidden() && uwpi.isValid() && !uwpi.isDeleted())
                    listUser.add(uwpi);
              }
              return listUser;
        }
        
        /**
         * Acts by status.
         * 
         * @param userwp the userwp
         * @param user the user
         * @return the hash map
         */
        public HashMap actsByStatus(UserWebPage userwp, User user){
            HashMap contStatus=new HashMap();
            ArrayList assig=new ArrayList();
            ArrayList canc=new ArrayList();
            ArrayList devel=new ArrayList();
            ArrayList paus=new ArrayList();
            ArrayList end =new ArrayList();

            if(userwp.getUserWP()!=null){
                WebPage project = getProject(userwp);
                ArrayList acts=actsByResponsible(userwp, user, project);
                Iterator ita = acts.iterator();
                acts=new ArrayList();
                while(ita.hasNext()){
                    Activity act=(Activity)ita.next();
                    acts.add(act.getCurrentPercentage());
                    acts.add(act.getPlannedHour());
                    if(act.getStatus().equals("assigned"))
                        assig.add(act);
                    else if(act.getStatus().equals("canceled"))
                        canc.add(act);
                    else if(act.getStatus().equals("develop"))
                        devel.add(act);
                    else if(act.getStatus().equals("paused"))
                        paus.add(act);
                    else if(act.getStatus().equals("ended"))
                        end.add(act);
                }

                contStatus.put("assigned", assig);
                contStatus.put("canceled",canc);
                contStatus.put("develop", devel);
                contStatus.put("paused",paus);
                contStatus.put("ended", end);
                contStatus.put("list",acts);
            }
            return contStatus;
        }
        
        /**
         * Acts by responsible.
         * 
         * @param uwp the uwp
         * @param user the user
         * @param project the project
         * @return the array list
         */
        private ArrayList actsByResponsible(UserWebPage uwp, User user,WebPage project){
            ArrayList listActs=new ArrayList();
              Iterator itA=Activity.ClassMgr.listActivityByResponsible(uwp.getUserWP(),uwp.getWebSite());
              while(itA.hasNext()){
                  WebPage acts= (WebPage)itA.next();
                  if(acts.isVisible()&&parentActive(acts)&&!hasChild((WebPage)acts,user)&&acts.isChildof(project)&&acts!=null&&!acts.isHidden()&&acts.isValid()&&!acts.isDeleted()){
                      Activity acti = (Activity)acts;
                      if(acti.getStatus()==null)
                          acti.setStatus("assigned");
                      if(acti.getStatus().equals("unassigned"))
                         acti.setStatus("assigned");
                      listActs.add(acts);
                  }
              }
              return listActs;
        }
        //Obtiene un hashmap con los usuarios y sus actividades asociadas de un sitio web
        /**
         * Gets the map users.
         * 
         * @param listUser the list user
         * @param project the project
         * @param user the user
         * @return the map users
         */
        public HashMap getMapUsers(ArrayList listUser,WebPage project, User user){
              Iterator<UserWebPage> itU = listUser.iterator();
              HashMap users = new HashMap();
              ArrayList listActs= new ArrayList();
              while(itU.hasNext()){
                  UserWebPage uwpi = itU.next();
                  if(uwpi.getUserWP()!=null)
                      listActs =actsByResponsible(uwpi,user,project);
                  users.put(uwpi.getURI(), listActs);
                  listActs=new ArrayList();
              }
              return users;
        }
        //Obtiene en codigo html la lista de usuario asociados a un sitio web
        /**
         * View list users.
         * 
         * @param users the users
         * @param itU the it u
         * @param titleLan the title lan
         * @param label the label
         * @return the string
         */
        public String viewListUsers(HashMap users,Iterator<UserWebPage> itU, String titleLan,String label)
        {
            StringBuffer buff=new StringBuffer();
            while(itU.hasNext()){
                UserWebPage wpu=(UserWebPage)itU.next();
                ArrayList activ=(ArrayList)users.get(wpu.getURI());
                Iterator itA = activ.iterator();
                ArrayList listActu = new ArrayList();
                if(wpu.getUserWP()!=null)
                    listActu = getArrayforProgress(itA);
                String avan=getProgressBar(listActu,titleLan);
                if(avan==null)
                    avan=label;
                buff.append("            <div class=\"liespa\">\n");
                buff.append("               <div class=\"indentation\"><a href=\""+wpu.getUrl()+"\">"+wpu.getDisplayName()+"</a></div>\n");
                buff.append("               <div class=\"espa\">"+avan+"\n              </div>\n");
                buff.append("            </div>\n");
            }
            return buff.toString();
        }

        /**
         * Prints the page.
         * 
         * @param mpag the mpag
         * @param title the title
         * @param user the user
         * @param progressBar the progress bar
         * @param titleLan the title lan
         * @return the string
         */
        public String printPage(ArrayList mpag, String title,User user,boolean progressBar,String titleLan)
        {
            Iterator itpr=mpag.iterator();
            StringBuffer strb = new StringBuffer();
            strb.append("");
            if(itpr.hasNext())
            {
                strb.append("       <h2>"+title+"</h2>\n");
                strb.append("          <ul>\n");
                while(itpr.hasNext()){
                    WebPage wpage=(WebPage)itpr.next();
                    strb.append("            <li>\n                <a href=\""+wpage.getUrl()+"\">"+wpage.getDisplayName()+"</a>\n            </li>\n");
                    if(progressBar){
                        strb.append(getProgressBar(getListLeafActivities(wpage,user),titleLan));
                    }
                }
                strb.append("          </ul>");
            }
            return strb.toString();
        }

        /**
         * Gets the progress bar.
         * 
         * @param info the info
         * @param titleLan the title lan
         * @return the progress bar
         */
        public String getProgressBar(ArrayList info, String titleLan)
        {
            String porcentaje = "", horas = "";
            float porcentajeTotal = 0, horasTotales = 0, horasParciales = 0;
            UUID uuid = UUID.randomUUID();
            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            dfs.setDecimalSeparator('.');
            DecimalFormat df = new DecimalFormat("###.##", dfs);
            StringBuffer ret = new StringBuffer();

            if(!info.isEmpty())
            {
                boolean bandera = true;
                Iterator it = info.iterator();
                while(it.hasNext())
                {
                    if(bandera) 
                    {
                        porcentaje = String.valueOf(it.next());
                        horas = "";
                        bandera = false;
                    }
                    else
                    {
                        horas = String.valueOf(it.next());
                        horasTotales += Float.valueOf(horas);
                        float dummy = Float.valueOf(porcentaje);
                        if(dummy >= 1)
                            dummy /= 100;
                        horasParciales += dummy * Float.valueOf(horas);
                        porcentaje = "";
                        horas = "";
                        bandera = true;
                    }
                }
                porcentajeTotal = (horasParciales / horasTotales) * 100;
                if(Float.isNaN(porcentajeTotal))
                    porcentajeTotal=0;
                ret.append("\n");
                ret.append("                  <div class=\"contenedor\">\n");
                ret.append("                     <div class=\"barraProgreso\" title=\""+titleLan+" "+horasTotales+"\" onmouseover=\"javascript:showDiv('divStatusBar" + uuid + "'); return true;\" onmouseout=\"javascript:hideDiv('divStatusBar" + uuid + "'); return true;\">\n");
                ret.append("                           <div class=\"defaultPorcentaje\"></div>\n");
                ret.append("                           <div class=\"porcentaje\">\n");
                ret.append("                              <div class=\"estatusBarra\" id=\"divStatusBar" + uuid + "\" name=\"divStatusBar" + uuid + "\">"+titleLan+":<span class=\"text\">" + horasTotales + "</span>&nbsp;&nbsp;&nbsp;&nbsp;</div>\n");
                ret.append("                              <div class=\"porcentajeAvance\" style=\"width:"+df.format(porcentajeTotal)+"%\"></div>\n");
                ret.append("                           </div>\n");
                ret.append("                     </div>\n");
                ret.append("                     <div class=\"tag_porcentaje\">"+ df.format(porcentajeTotal) +"%</div>\n");
                ret.append("                  </div>");
                return ret.toString();
            }
            else
                return null;
        }

        /**
         * Gets the progress html.
         * 
         * @param porcentajeTotal the porcentaje total
         * @return the progress html
         */
        private String getProgressHtml(float porcentajeTotal){
            StringBuffer bf = new StringBuffer();
            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            dfs.setDecimalSeparator('.');
            DecimalFormat df = new DecimalFormat("###.##", dfs);
            bf.append("                  <div class=\"contenedor\">\n");
            bf.append("                     <div class=\"barraProgreso\">\n");
            bf.append("                           <div class=\"defaultPorcentaje\"></div>\n");
            bf.append("                           <div class=\"porcentaje\">\n");
            bf.append("                              <div class=\"porcentajeAvance\" style=\"width:"+df.format(porcentajeTotal)+"%\"></div>\n");
            bf.append("                           </div>\n");
            bf.append("                     </div>\n");
            bf.append("                     <div class=\"tag_porcentaje\">"+ df.format(porcentajeTotal) +"%</div>\n");
            bf.append("                  </div>\n");
            return bf.toString();
        }

        /**
         * Checks for child.
         * 
         * @param webpage the webpage
         * @param user the user
         * @return true, if successful
         */
        public boolean hasChild(WebPage webpage,User user)
        {
            boolean result = false;
            ArrayList checks=new ArrayList();
            Iterator childs = webpage.listVisibleChilds(user.getLanguage());
            while(childs.hasNext()){
                WebPage child = (WebPage)childs.next();
                if(child != null && child.isVisible() && child.isActive() && !child.isHidden() && child.isValid() && !child.isDeleted())
                    checks.add(true);
                else
                    checks.add(false);
            }
            if(checks.contains(true))
                result = true;
           return result;
        }
        //Obtiene los nodos finales
        /**
         * Gets the list leaf activities.
         * 
         * @param wp the wp
         * @param user the user
         * @return the list leaf activities
         */
        public ArrayList getListLeafActivities(WebPage wp, User user){
            Iterator<Activity> it = Activity.ClassMgr.listActivities(wp.getWebSite());
            ArrayList result = new ArrayList();
            while(it.hasNext()){
                Activity act = it.next();
                if((act.isVisible()&&parentActive(act)&&!hasChild((WebPage)act,user)&&act.isChildof(wp))||(act.isVisible()&&parentActive(act)&&!hasChild((WebPage)act,user)&&act.equals(wp))){
                    result.add(act.getCurrentPercentage());
                    result.add(act.getPlannedHour());
                }
            }
            return result;
        }
        
        /**
         * Parent active.
         * 
         * @param wp the wp
         * @return true, if successful
         */
        public boolean parentActive(WebPage wp){
            WebPage parent=wp.getParent();
            boolean haveParent=true;
            while(parent!=null){
                if(!parent.isActive())
                   haveParent=false;
                parent=parent.getParent();
            }
            return haveParent;
        }
        
        /**
         * Gets the web page list level.
         * 
         * @param wp1 the wp1
         * @param user the user
         * @param level the level
         * @param title the title
         * @param indentation the indentation
         * @param titleLan the title lan
         * @return the web page list level
         */
        public String getWebPageListLevel(WebPage wp1,User user, int level, String title, String indentation,String titleLan)
        {
            level=level+wp1.getLevel();
            Iterator<Activity> it = Activity.ClassMgr.listActivityByParent(wp1,wp1.getWebSite());
            Activity act;
            ArrayList ChildVisible=new ArrayList();
            while(it.hasNext())
            {
                WebPage wp=(WebPage)it.next();
                if(wp.isVisible())
                    ChildVisible.add(wp);
            }
            it=ChildVisible.iterator();
            StringBuffer st=new StringBuffer();
            if(it.hasNext()){
                st.append("    <h2>"+title+"</h2>\n");
                st.append("    <br>\n");
                st.append("        <div class=\"list\">\n");
                while(it.hasNext())
                {
                    act = (Activity)it.next();
                    String pgrb = getProgressBar(getListLeafActivities(act,user),titleLan);
                    st.append("          <div class=\"liespa\">\n");

                    st.append("            <div class=\"activity\">"+indentation+"      <a href=\""+act.getUrl()+"\">"+act.getDisplayName()+"</a></div>\n");
                    st.append("            <div class=\"espacio\">\n");
                    st.append("                <div class=\"contPorcentaje\">\n");
                    st.append(pgrb);
                    st.append("                </div>\n");
                    st.append("            </div>\n");

                    st.append("          </div>\n");
                    if(level!=act.getLevel()&&pgrb!=null){
                        StringBuffer st1=new StringBuffer();
                        indentation=indentation+"&nbsp;&nbsp;&nbsp;&nbsp;";
                        String childp=getWebPageListLevel1(act,user,level,st1,indentation,titleLan);
                        indentation=indentation.substring(0, indentation.length()-24);
                        st.append(childp);
                    }
                }
                st.append("    </div>\n");
            }
            return st.toString();
        }
        
        /**
         * Gets the web page list level1.
         * 
         * @param act the act
         * @param user the user
         * @param level the level
         * @param st1 the st1
         * @param indentation the indentation
         * @param titleLan the title lan
         * @return the web page list level1
         */
        private String getWebPageListLevel1(Activity act, User user, int level,StringBuffer st1, String indentation,String titleLan)
        {
            Iterator<Activity> it = Activity.ClassMgr.listActivityByParent(act,act.getWebSite());
            ArrayList ChildVisible=new ArrayList();
            while(it.hasNext())
            {
                WebPage wp=(WebPage)it.next();
                if(wp.isVisible())
                    ChildVisible.add(wp);
            }
            it=ChildVisible.iterator();
            if(it.hasNext()){
                while(it.hasNext())
                {
                    act = (Activity)it.next();
                    String pgrb = getProgressBar(getListLeafActivities(act,user),titleLan);
                    st1.append("          <div class=\"liespa\">\n");

                    st1.append("            <div class=\"activity\">"+indentation+"      <a href=\""+act.getUrl()+"\">"+act.getDisplayName()+"</a></div>\n");
                    st1.append("            <div class=\"espacio\">\n");
                    st1.append("                <div class=\"contPorcentaje\">\n");
                    st1.append(pgrb);
                    st1.append("                </div>\n");
                    st1.append("            </div>\n");
                    st1.append("          </div>\n");
                    if(level != act.getLevel()&&pgrb!=null){
                        indentation=indentation+"&nbsp;&nbsp;&nbsp;&nbsp;";
                        getWebPageListLevel1(act,user,level,st1,indentation,titleLan);
                        indentation=indentation.substring(0, indentation.length()-24);
                    }
                }
            }
            return st1.toString();
        }
        
        /**
         * Gets the project.
         * 
         * @param wp the wp
         * @return the project
         */
        public WebPage getProject(WebPage wp){
           boolean p;p=false;
           WebPage parent=wp;
           while(!p){
                SemanticObject obj1= SemanticObject.createSemanticObject(wp.getURI());
                if(obj1.instanceOf(Project.sclass)){
                    parent=wp;p=true;}
                wp=wp.getParent();
            }
            return parent;
        }
        
        /**
         * Calc progress user gral.
         * 
         * @param users the users
         * @return the array list
         */
        public ArrayList calcProgressUserGral(HashMap users)
        {
            Iterator it = users.entrySet().iterator();
            ArrayList actValid=new ArrayList();
            while (it.hasNext()) {
              Map.Entry e = (Map.Entry)it.next();
              ArrayList activ = (ArrayList)e.getValue();
              actValid.addAll(activ);
            }
            //Obtiene el porcentaje actual y las horas planeadas para obtener la barra de progreso de avance general
            it=actValid.iterator();
            actValid=new ArrayList();
            actValid=getArrayforProgress(it);
            return actValid;
        }
        
        /**
         * Gets the acts prede.
         * 
         * @param act the act
         * @param user the user
         * @return the acts prede
         */
        public ArrayList getActsPrede(Activity act,User user){
            //Obtiene las actividades predecesoras relacionadas a la actividad y que sean validas
            Iterator listobj=act.listPredecessors();
            ArrayList result=new ArrayList();
            while(listobj.hasNext()){
                WebPage wp = (WebPage)listobj.next();
                if(wp.isVisible()&&parentActive(wp)&&wp.isChildof(getProject(act))&&wp.isActive()&&wp!=null&&!wp.isHidden()&&wp.isValid()&&!wp.isDeleted())//!hasChild((WebPage)wp,user)&&
                    result.add(wp.getURI());
            }
            return result;
        }
        
        /**
         * Gets the acts for prec.
         * 
         * @param act the act
         * @param parent the parent
         * @return the acts for prec
         */
        public ArrayList getActsForPrec(Activity act, WebPage parent){
            //Obtiene las actividades relacionadas al modelo o sitio web y que sean validas
            Iterator it= Activity.ClassMgr.listActivities(act.getWebSite());
            ArrayList result = new ArrayList();
            while(it.hasNext()){
                WebPage wp = (WebPage)it.next();
                 if(wp.isVisible()&&parentActive(wp)&&wp.isChildof(parent)&&wp.isActive()&&wp!=null&&!wp.isHidden()&&wp.isValid()&&!wp.isDeleted()&&!((Activity)wp).equals(act))//!hasChild((WebPage)wp,user)&&
                   result.add(wp); 
            }
            return result;
        }
        
        /**
         * Gets the label status.
         * 
         * @param act the act
         * @param user the user
         * @return the label status
         */
        public ArrayList getLabelStatus(Activity act, User user){
            String value1=act.getStatus().toString();
            ArrayList labels = new ArrayList();
            boolean valid = true;
            DisplayProperty sobj = new DisplayProperty(Activity.swbproy_status.getDisplayProperty());
            String selectValues=sobj.getDisplaySelectValues(user.getLanguage());

            StringTokenizer st = new StringTokenizer(selectValues, "|");
            while (st.hasMoreTokens()) {
                String tok = st.nextToken();
                int    ind = tok.indexOf(':');
                String id  = tok;
                String val = tok;
                if (ind > 0) {
                    id  = tok.substring(0, ind);
                    val = tok.substring(ind + 1);
                    if(id.equals(value1))valid=true;
                    else valid=false;
                    labels.add(id);
                    labels.add(val);
                    labels.add(valid);
                }
            }
            return labels;
        }
        
        /**
         * Check predecesor.
         * 
         * @param act the act
         * @return true, if successful
         */
        public boolean checkPredecesor(Activity act){
            boolean check=false;
            Iterator prede = act.listPredecessors();
            while(prede.hasNext())
            {
                Activity predecessor = (Activity)prede.next();
                if(predecessor.getChild()==null){
                    if(predecessor.getStatus()==null||predecessor.getStatus().equals("assigned")||predecessor.getStatus().equals("unassigned")||predecessor.getStatus().equals("develop")||predecessor.getStatus().equals("paused"))
                        check = true;
                }
            }
            return check;
        }
        
        /**
         * List user repository.
         * 
         * @param wp the wp
         * @return the array list
         */
        public ArrayList listUserRepository(WebSite wp){
            ArrayList usrs =new ArrayList();
            UserRepository urep=wp.getUserRepository();
            Iterator users = urep.listUsers();
            while(users.hasNext()){
                User us = (User)users.next();
                usrs.add(us.getURI());
            }
            return usrs;
        }
        
        /**
         * Valid act.
         * 
         * @param act the act
         */
        public void validAct(Activity act){
             if(act.getStatus()==null){
                if(act.getResponsible()==null)
                    act.setStatus("unassigned");
                else
                    act.setStatus("assigned");
            }
            if(act.getStatus().equals("unassigned")){
                if(act.getResponsible()!=null) act.setStatus("assigned");
            }else if(act.getStatus().equals("develop")||act.getStatus().equals("canceled")||act.getStatus().equals("paused")||act.getStatus().equals("ended")){
                if(act.getStartDate()==null)act.setStartDate(new Timestamp(new Date().getTime()));
                if(act.getStatus().equals("canceled")||act.getStatus().equals("ended")){
                    if(act.getEndDate()==null)act.setEndDate(new Timestamp(new Date().getTime()));
                }
            }
            if(act.getActType()==null)
              act.setActType("");
            if(act.getCurrentHour()==0)
             act.setCurrentHour(0);
            if(act.getCurrentPercentage()==0)
              act.setCurrentPercentage(0);
            if(act.getPlannedHour()==0)
              act.setPlannedHour(0);
        }
        
        /**
         * Prints the status activity.
         * 
         * @param listActs the list acts
         * @param titleLan the title lan
         * @return the string
         */
        public String printStatusActivity(ArrayList listActs,String titleLan){
            StringBuffer buf = new StringBuffer();
            Iterator list = listActs.iterator();
            buf.append("");
            if(list.hasNext()){
                while(list.hasNext()){
                    ArrayList lprogress= new ArrayList();
                    Activity act = (Activity)list.next();
                    lprogress.add(act.getCurrentPercentage());
                    lprogress.add(act.getPlannedHour());
                    buf.append("<div class=\"liespa\">");
                    buf.append("<span class=\"indentation\"><a href=\""+act.getUrl()+"\">"+act.getDisplayName()+"</a></span>");
                    buf.append("<div class=\"espa\">"+getProgressBar(lprogress,titleLan)+"</div>");
                    buf.append("</div>");
                }
            }
            return buf.toString();
        }
        
        /**
         * Gets the arrayfor progress.
         * 
         * @param it the it
         * @return the arrayfor progress
         */
        public ArrayList getArrayforProgress(Iterator<Activity> it){
            ArrayList progress = new ArrayList();
            while(it.hasNext()){
                Activity act = it.next();
                progress.add(act.getCurrentPercentage());
                progress.add(act.getPlannedHour());
            }
            return progress;
        }
}
