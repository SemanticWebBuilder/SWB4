<%@ page import="com.infotec.appfw.util.AFUtils,
                 com.infotec.appfw.exception.AFException,
                 com.infotec.wb.core.db.RecUser,
                 com.infotec.wb.core.db.DBUser,
                 com.infotec.wb.core.db.DBUserRepository,
                 java.util.Enumeration,
                 java.util.Iterator"%>
 <%--
 Utilería que sirve para encriptar las contraseñas de los
 usuarios que se migraron de WB ver. 2.0

  Created by IntelliJ IDEA.
  User: Sergio Martínez
  Date: 20/01/2005
  Time: 06:40:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head><title>Actualización de Usuarios</title></head>
  <body>
  <font size=3 face=verdana>
    Actualizando el repositorio WB
  <%
      RecUser ru = null;

       Iterator iteRepo = DBUser.getInstance().getRepositories();
       
       while(iteRepo.hasNext())
       {
           
           DBUserRepository repo = (DBUserRepository) iteRepo.next();
           String strRep = repo.getName();
           out.println("<br><br>Repositorio: " + strRep + "<br>");
           long counter=0;
           Enumeration usrs =DBUser.getInstance(strRep).getUsers();
           while (usrs.hasMoreElements()){
             ru = (RecUser)usrs.nextElement();
             if (null==ru) continue;
             ru.setPassword(ru.getPassword());
             counter++;
             if(counter%1000==0)out.println("<br>Alterando... "+strRep+" "+counter);
             try {
               ru.update("Adminwb","Actualización a SHA");
             }
             catch (AFException e) {
               e.printStackTrace();
               AFUtils.log(e, "");
             }
           }

       }
       //usrs=null;
       
  %>
<br>Terminado el proceso de actualización de usuarios .....
</font>
</body>
</html>