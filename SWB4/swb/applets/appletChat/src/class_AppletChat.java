/*      */ import java.applet.Applet;
/*      */ import java.applet.AppletContext;
/*      */ import java.applet.AudioClip;
/*      */ import java.awt.Button;
/*      */ import java.awt.Choice;
/*      */ import java.awt.Color;
/*      */ import java.awt.Font;
/*      */ import java.awt.Image;
/*      */ import java.awt.Label;
/*      */ import java.awt.List;
/*      */ import java.awt.MediaTracker;
/*      */ import java.awt.Panel;
/*      */ import java.awt.TextField;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.ItemEvent;
/*      */ import java.awt.event.ItemListener;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.MouseListener;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.Socket;
/*      */ import java.net.URL;
/*      */ import java.util.Vector;
/*      */ 
/*      */ public class class_AppletChat extends Applet
/*      */   implements Runnable
/*      */ {
/*      */   boolean isStandalone;
/*      */   boolean flag_precarga;
/*      */   boolean flag_first_paint;
/*      */   Button BtnArranque;
/*      */   public int i_status;
/*      */   boolean modalWindow;
/*      */   public int puerto;
/*      */   OutputStream os_connect;
/*      */   InputStream is_connect;
/*      */   Socket mi_socket;
/*      */   PrintStream data_out;
/*      */   Thread TH_listen;
/*      */   Thread TH_Status;
/*      */   String X_Chat_ID_user;
/*      */   class_PanelChat mi_chatdomi;
/*      */   List lstUsuarios;
/*      */   List lstUsuariosIgnorados;
/*      */   String idUsuario;
/*      */   String idComunidad;
/*      */   String sesionUsuario;
/*      */   boolean flag_inicio;
/*      */   int anchoApp;
/*      */   int altoApp;
/*      */   Vector ignorar;
/*      */   Panel pnlLogin;
/*      */   Label lbLogin1;
/*      */   Label lbError;
/*      */   Label lblUsrConn;
/*      */   Label lblUsrIgn;
/*      */   Label lblUsuario;
/*      */   TextField TXuserID;
/*      */   Button BtnEntrar;
/* 1132 */   static String fDefault = "0-Dialog-Negro-0";
/*      */   int ORIGEN_X;
/*      */   int ORIGEN_Y;
/* 1135 */   static String[] colores = { "Negro", "Azul", "Cyan", "Gris Obscuro", "Gris", "Verde", "Gris Claro", "Magenta", "Naranja", "Rosa", "Rojo", "Amarillo", "Blanco" };
/*      */ 
/* 1139 */   static String[] fonts = { "Dialog", "Helvetica", "TimesRoman", "Courier", "DialogInput" };
/*      */ 
/* 1142 */   static String[] expresion = { "-Seleccione-", "Sonriente :-)", "Enojado :-(", "Sorprendido :-O", "Mueca :->", "Guiño ;-)", "Lengua :-P", "Beso :-*", "Serio :-|" };
/*      */   class_boton objBtnBold;
/*      */   class_boton objBtnItalic;
/*      */   class_boton objBtnLogo;
/*      */   class_boton objBtnSalir;
/*      */   Button BtnMsgPrivado;
/*      */   Button BtnIgnorar;
/*      */   Button BtnExpulsar;
/*      */   Button BtnPreguntas;
/*      */   Button BtnHistorial;
/*      */   Choice CBTipoFont;
/*      */   Choice CBColorFont;
/*      */   Choice CBExpresion;
/*      */   class_FrameModerador Moderador;
/*      */   class_FrameHistorial Historial;
/*      */   Image imgLogo;
/*      */   boolean bCargaImg;
/*      */   AudioClip auRisa;
/*      */   public boolean bmoderado;
/*      */   public boolean bPreguntas;
/*      */   public boolean bRespuestas;
/*      */ 
/*      */   public void stop()
/*      */   {
/*  204 */     if (this.mi_socket != null)
/*      */       try
/*      */       {
/*  207 */         this.mi_socket.close();
/*      */       } catch (IOException _ex) {
/*      */       }
/*  210 */     if (this.TH_listen != null)
/*      */     {
/*  212 */       this.TH_listen.stop();
/*  213 */       this.TH_listen = null;
/*      */     }
/*  215 */     if (this.TH_Status != null)
/*      */     {
/*  217 */       this.TH_Status.stop();
/*  218 */       this.TH_Status = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void ack_tx_expulsar()
/*      */   {
/*  224 */     PrintStream data_out = new PrintStream(this.os_connect);
/*  225 */     data_out.print("@@@CHEX|" + this.lstUsuarios.getSelectedItem() + "CCSS");
/*      */   }
/*      */ 
/*      */   public void ack_actualizaroom(String str_aux)
/*      */   {
/*  230 */     String aux_user = "";
/*  231 */     str_aux = str_aux.substring(str_aux.indexOf('|') + 1);
/*  232 */     boolean aux_yaexiste = false;
/*  233 */     while (str_aux.indexOf('|') >= 0)
/*      */     {
/*  235 */       aux_yaexiste = false;
/*  236 */       aux_user = str_aux.substring(0, str_aux.indexOf('|'));
/*  237 */       str_aux = str_aux.substring(str_aux.indexOf('|') + 1);
/*  238 */       for (int j = 0; (j < this.lstUsuarios.getItemCount()) && (!aux_yaexiste); j++)
/*      */       {
/*  240 */         String aux_user2 = this.lstUsuarios.getItem(j).toString();
/*  241 */         if (aux_user.compareTo(aux_user2) == 0)
/*      */         {
/*  243 */           aux_yaexiste = true;
/*  244 */           j = this.lstUsuarios.getItemCount();
/*      */         } else {
/*  246 */           if (aux_user.compareTo(aux_user2) >= 0)
/*      */             continue;
/*  248 */           this.lstUsuarios.addItem(aux_user, j);
/*  249 */           aux_yaexiste = true;
/*  250 */           j = this.lstUsuarios.getItemCount();
/*      */         }
/*      */       }
/*      */ 
/*  254 */       if (!aux_yaexiste)
/*  255 */         this.lstUsuarios.addItem(aux_user);
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean login(String aux_login, String aux_pwd, String aux_comun)
/*      */   {
/*  261 */     PrintStream data_out = new PrintStream(this.os_connect);
/*  262 */     data_out.print("@@@CN|" + aux_login + "|" + aux_pwd + "|" + aux_comun + "CCSS");
/*  263 */     return true;
/*      */   }
/*      */ 
/*      */   public void ack_rx_respuesta_moderador(String x_aux_dato)
/*      */   {
/*  268 */     String x_aux_user_origen = "";
/*  269 */     String x_aux_moderador = "";
/*  270 */     String x_aux_mensaje = "";
/*  271 */     String x_aux_Pregunta = "";
/*  272 */     x_aux_dato = x_aux_dato.substring(x_aux_dato.indexOf('|') + 1);
/*  273 */     x_aux_user_origen = x_aux_dato.substring(0, x_aux_dato.indexOf('|'));
/*  274 */     x_aux_dato = x_aux_dato.substring(x_aux_dato.indexOf('|') + 1);
/*  275 */     x_aux_moderador = x_aux_dato.substring(0, x_aux_dato.indexOf('|'));
/*  276 */     x_aux_dato = x_aux_dato.substring(x_aux_dato.indexOf('|') + 1);
/*  277 */     x_aux_mensaje = x_aux_dato.substring(0, x_aux_dato.indexOf('|'));
/*  278 */     x_aux_Pregunta = x_aux_dato.substring(x_aux_dato.indexOf('|') + 1);
/*  279 */     this.mi_chatdomi.set_value(x_aux_user_origen + " pregunta: " + x_aux_Pregunta + ":", "0-Dialog-Azul-1");
/*  280 */     this.mi_chatdomi.set_value("»" + x_aux_mensaje, "0-Dialog-Negro-1");
/*      */   }
/*      */ 
/*      */   public void retarda(long i_aux_milis)
/*      */   {
/*      */     try
/*      */     {
/*  287 */       Thread.sleep(i_aux_milis);
/*  288 */       return;
/*      */     }
/*      */     catch (InterruptedException _ex)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   private void jbInit()
/*      */     throws Exception
/*      */   {
/*  299 */     setSize(800, 800);
/*  300 */     setLayout(null);
/*  301 */     setBackground(new Color(80, 80, 80));
/*  302 */     if (getParameter("precarga") != null)
/*      */     {
/*  304 */       this.BtnArranque.addActionListener(new class_AppletChat.ActionHandler());
/*  305 */       add(this.BtnArranque);
/*  306 */       this.BtnArranque.setBounds(0, 0, 100, 25);
/*  307 */       setBackground(new Color(13, 36, 135));
/*  308 */       return;
/*      */     }
/*  310 */     this.auRisa = getAudioClip(getCodeBase(), "sound/risa.au");
/*      */     try
/*      */     {
/*  313 */       this.anchoApp = Integer.valueOf(getParameter("anchoApp")).intValue();
/*  314 */       this.altoApp = Integer.valueOf(getParameter("altoApp")).intValue();
/*  315 */       this.puerto = Integer.valueOf(getParameter("puerto")).intValue();
/*      */     } catch (NullPointerException _ex) {
/*      */     } catch (NumberFormatException _ex) {
/*      */     }
/*  319 */     setSize(this.anchoApp, this.altoApp);
/*  320 */     if (getParameter("idUsuario") != null)
/*  321 */       this.idUsuario = getParameter("idUsuario");
/*      */     else {
/*  323 */       this.idUsuario = "invitado";
/*      */     }
/*  325 */     if (getParameter("idComunidad") != null)
/*  326 */       this.idComunidad = getParameter("idComunidad");
/*      */     else {
/*  328 */       this.idComunidad = "general";
/*      */     }
/*  330 */     this.sesionUsuario = "0";
/*  331 */     if (getParameter("sesionUsuario") != null)
/*  332 */       this.sesionUsuario = getParameter("sesionUsuario").trim();
/*  333 */     if (getParameter("moderador") != null)
/*  334 */       if (getParameter("moderador").compareTo("1") == 0)
/*  335 */         this.bmoderado = false;
/*      */       else
/*  337 */         this.bmoderado = true;
/*  338 */     this.mi_chatdomi = new class_PanelChat(this, Color.white, Color.black, this.anchoApp - 155, this.altoApp - 8);
/*  339 */     this.mi_chatdomi.setLocation(0, 0);
/*  340 */     this.ORIGEN_Y = this.altoApp;
/*  341 */     add(this.mi_chatdomi);
/*  342 */     this.lblUsrConn.setBounds(this.anchoApp - 152 + 15, 0, 134, 15);
/*  343 */     this.lblUsrConn.setBackground(Color.lightGray);
/*  344 */     this.lblUsrConn.setFont(new Font("Dialog", 1, 10));
/*  345 */     add(this.lblUsrConn);
/*  346 */     this.lstUsuarios.setBounds(this.anchoApp - 152 + 15, 15, 135, this.altoApp / 2);
/*  347 */     this.lstUsuarios.setBackground(Color.darkGray);
/*  348 */     this.lstUsuarios.setForeground(Color.white);
/*  349 */     add(this.lstUsuarios);
/*  350 */     if (!this.bmoderado)
/*      */     {
/*  352 */       this.lblUsrIgn.setFont(new Font("Dialog", 1, 10));
/*  353 */       this.lblUsrIgn.setBackground(Color.lightGray);
/*  354 */       this.lblUsrIgn.setBounds(this.anchoApp - 152 + 15, this.altoApp / 2 + 15, 135, 15);
/*  355 */       add(this.lblUsrIgn);
/*  356 */       this.lstUsuariosIgnorados.setBounds(this.anchoApp - 152 + 15, this.altoApp / 2 + 30, 134, this.altoApp - 156 - 24);
/*  357 */       this.lstUsuariosIgnorados.setBackground(Color.gray);
/*  358 */       this.lstUsuariosIgnorados.addMouseListener(new class_AppletChat.MouseHandler());
/*  359 */       add(this.lstUsuariosIgnorados);
/*      */     }
/*  361 */     this.lstUsuarios.addMouseListener(new class_AppletChat.MouseHandler());
/*  362 */     this.objBtnSalir.setBounds(this.ORIGEN_X - 50, this.ORIGEN_Y, 25, 25);
/*  363 */     this.objBtnSalir.bTogle = false;
/*      */ 
/*  365 */     this.objBtnBold.setBounds(this.ORIGEN_X - 65, this.ORIGEN_Y, 25, 25);
/*  366 */     add(this.objBtnBold);
/*  367 */     this.objBtnItalic.setBounds(this.ORIGEN_X - 35, this.ORIGEN_Y, 25, 25);
/*  368 */     add(this.objBtnItalic);
/*  369 */     this.BtnMsgPrivado.setBounds(this.anchoApp - 140, this.ORIGEN_Y - 3, 130, 20);
/*  370 */     this.BtnMsgPrivado.addActionListener(new class_AppletChat.ActionHandler());
/*  371 */     if (!this.bmoderado)
/*      */     {
/*  373 */       add(this.BtnIgnorar);
/*  374 */       this.BtnIgnorar.setBounds(this.anchoApp - 135, this.ORIGEN_Y, 130, 20);
/*  375 */       this.BtnIgnorar.addActionListener(new class_AppletChat.ActionHandler());
/*      */     }
/*  377 */     this.BtnHistorial.setBounds(this.anchoApp - 255, this.ORIGEN_Y, 130, 20);
/*  378 */     this.BtnHistorial.addActionListener(new class_AppletChat.ActionHandler());
/*  379 */     this.lblUsuario.setFont(new Font("Dialog", 1, 10));
/*  380 */     this.lblUsuario.setForeground(Color.white);
/*  381 */     this.lblUsuario.setBackground(new Color(80, 80, 80));
/*  382 */     this.lblUsuario.setBounds(this.ORIGEN_X + 100, this.ORIGEN_Y + 21, this.anchoApp - 255, 20);
/*  383 */     this.lblUsuario.setText("Usuario: " + this.idUsuario);
/*  384 */     add(this.lblUsuario);
/*  385 */     this.Historial = new class_FrameHistorial(this);
/*  386 */     for (int i = 0; i < fonts.length; i++) {
/*  387 */       this.CBTipoFont.addItem(fonts[i]);
/*      */     }
/*  389 */     this.CBTipoFont.setBounds(this.ORIGEN_X + 28, this.ORIGEN_Y, 75, 15);
/*  390 */     add(this.CBTipoFont);
/*  391 */     for (int i = 0; i < colores.length; i++) {
/*  392 */       this.CBColorFont.addItem(colores[i]);
/*      */     }
/*  394 */     this.CBColorFont.setBounds(this.ORIGEN_X + 108, this.ORIGEN_Y, 75, 15);
/*  395 */     add(this.CBColorFont);
/*  396 */     this.CBExpresion.setBounds(this.ORIGEN_X + 188, this.ORIGEN_Y, 95, 15);
/*  397 */     for (int i = 0; i < expresion.length; i++) {
/*  398 */       this.CBExpresion.addItem(expresion[i]);
/*      */     }
/*  400 */     add(this.CBExpresion);
/*  401 */     this.CBExpresion.addItemListener(new class_AppletChat.ItemHandler());
/*  402 */     MediaTracker MT_aux = new MediaTracker(this);
/*  403 */     String[] auxNomImg = { "logo.gif", "b_negrita_o.gif", "b_negrita.gif", "b_italica_o.gif", "b_italica.gif", "b_audio_o.gif", "b_audio.gif", "b_salir.gif" };
/*      */ 
/*  406 */     Image[] img_aux = new Image[auxNomImg.length];
/*  407 */     for (int k = 0; k < auxNomImg.length; k++)
/*      */     {
/*  409 */       img_aux[k] = getImage(getCodeBase(), "images/" + auxNomImg[k]);
/*  410 */       MT_aux.addImage(img_aux[k], 0);
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  415 */       MT_aux.waitForAll();
/*  416 */       this.bCargaImg = (!MT_aux.isErrorAny());
/*      */     } catch (InterruptedException _ex) {
/*      */     }
/*  419 */     if (this.bCargaImg)
/*      */     {
/*  421 */       this.imgLogo = img_aux[0];
/*  422 */       this.objBtnBold.img_press = img_aux[1];
/*  423 */       this.objBtnBold.img_norm = img_aux[2];
/*  424 */       this.objBtnItalic.img_press = img_aux[3];
/*  425 */       this.objBtnItalic.img_norm = img_aux[4];
/*  426 */       this.objBtnLogo.img_press = img_aux[0];
/*  427 */       this.objBtnLogo.img_norm = img_aux[0];
/*  428 */       this.objBtnSalir.img_press = img_aux[7];
/*  429 */       this.objBtnSalir.img_norm = img_aux[7];
/*      */     }
/*      */   }
/*      */ 
/*      */   public void chat(String str_aux)
/*      */   {
/*  435 */     String x_aux_formato = "";
/*  436 */     int Estilo = 0;
/*  437 */     Estilo = this.objBtnBold.status ? 1 : Estilo;
/*  438 */     Estilo += (this.objBtnItalic.status ? 2 : 0);
/*  439 */     x_aux_formato = (char)(Estilo + 48) + "-";
/*  440 */     x_aux_formato = x_aux_formato + this.CBTipoFont.getSelectedItem() + "-";
/*  441 */     x_aux_formato = x_aux_formato + this.CBColorFont.getSelectedItem() + "-";
/*  442 */     x_aux_formato = x_aux_formato + "0";
/*  443 */     PrintStream data_out = new PrintStream(this.os_connect);
/*  444 */     str_aux = limpiaCaracteres(str_aux);
/*  445 */     data_out.print("@@@CH|" + this.idUsuario + "|" + x_aux_formato + "|" + str_aux + "CCSS");
/*      */   }
/*      */ 
/*      */   public void ack_rx_msg_priv(String str_aux)
/*      */   {
/*  450 */     String x_aux_user_origen = "";
/*  451 */     String x_aux_user_destino = "";
/*  452 */     String x_aux_formato = "";
/*  453 */     String x_aux_chat = "";
/*  454 */     str_aux = str_aux.substring(str_aux.indexOf('|') + 1);
/*  455 */     x_aux_user_origen = str_aux.substring(0, str_aux.indexOf('|'));
/*  456 */     str_aux = str_aux.substring(str_aux.indexOf('|') + 1);
/*  457 */     x_aux_user_destino = str_aux.substring(0, str_aux.indexOf('|'));
/*  458 */     str_aux = str_aux.substring(str_aux.indexOf('|') + 1);
/*  459 */     x_aux_formato = str_aux.substring(0, str_aux.indexOf('|'));
/*  460 */     x_aux_chat = str_aux.substring(str_aux.indexOf('|') + 1);
/*  461 */     if ((this.mi_chatdomi != null) && (!revisa_ignorados(x_aux_user_origen)))
/*  462 */       this.mi_chatdomi.set_value("Mensaje privado de " + x_aux_user_origen + " a " + x_aux_user_destino + ": " + x_aux_chat, x_aux_formato);
/*      */   }
/*      */ 
/*      */   public void start()
/*      */   {
/*  467 */     if (!this.flag_inicio)
/*      */     {
/*  469 */       this.flag_inicio = true;
/*  470 */       conecta_client_server();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void conecta_client_server()
/*      */   {
/*  476 */     showStatus("paso 1");
/*  477 */     if (this.mi_socket == null)
/*      */     {
/*  479 */       showStatus("paso 2");
/*  480 */       if (conecta())
/*      */       {
/*  482 */         showStatus("paso 2.1");
/*  483 */         login(this.idUsuario, this.idUsuario, this.idComunidad);
/*  484 */         this.TH_listen = new Thread(this);
/*  485 */         this.TH_listen.start();
/*  486 */         if (this.TH_Status != null)
/*      */         {
/*  488 */           this.mi_chatdomi.set_value("Reconectando", fDefault);
/*  489 */           this.BtnHistorial.setEnabled(true);
/*  490 */           this.TH_Status.stop();
/*  491 */           this.TH_Status = null;
/*      */         }
/*  493 */         showStatus("haciendo login2");
/*  494 */         return;
/*      */       }
/*      */ 
/*  497 */       showStatus("No se puede conectar al servidor");
/*  498 */       return;
/*      */     }
/*      */ 
/*  502 */     showStatus("paso 4");
/*  503 */     login(this.idUsuario, this.idUsuario, this.idComunidad);
/*  504 */     this.TH_listen = new Thread(this);
/*  505 */     this.TH_listen.start();
/*  506 */     showStatus("haciendo login");
/*      */   }
/*      */ 
/*      */   public String getAppletInfo()
/*      */   {
/*  513 */     return "Applet Information";
/*      */   }
/*      */ 
/*      */   public void playRisa()
/*      */   {
/*      */     try
/*      */     {
/*  520 */       this.auRisa.play();
/*  521 */       return;
/*      */     }
/*      */     catch (NullPointerException _ex)
/*      */     {
/*  525 */       return;
/*      */     }
/*      */     catch (IllegalArgumentException _ex)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean conecta()
/*      */   {
/*      */     try
/*      */     {
/*  538 */       this.mi_socket = new Socket(getCodeBase().getHost(), this.puerto);
/*      */     }
/*      */     catch (IOException _ex)
/*      */     {
/*  542 */       showStatus("No se pudo crear el socket " + this.puerto);
/*  543 */       return false;
/*      */     }
/*      */     try
/*      */     {
/*  547 */       this.os_connect = this.mi_socket.getOutputStream();
/*      */     }
/*      */     catch (IOException _ex)
/*      */     {
/*  551 */       showStatus("No se pudo crear el output stream en socket");
/*  552 */       return false;
/*      */     }
/*      */     try
/*      */     {
/*  556 */       this.is_connect = this.mi_socket.getInputStream();
/*      */     }
/*      */     catch (IOException _ex)
/*      */     {
/*  560 */       showStatus("No se pudo abrir el input stream en socket");
/*  561 */       return false;
/*      */     }
/*  563 */     new PrintStream(this.os_connect);
/*  564 */     showStatus("Estamos conectados al servidor!!!");
/*  565 */     return true;
/*      */   }
/*      */ 
/*      */   private void ack_desconecta(String str_aux)
/*      */   {
/*  570 */     String aux_user = "";
/*  571 */     str_aux = str_aux.substring(str_aux.indexOf('|') + 1);
/*  572 */     aux_user = str_aux;
/*      */     try
/*      */     {
/*  575 */       this.lstUsuarios.remove(aux_user);
/*  576 */       if (revisa_ignorados(aux_user))
/*      */       {
/*  578 */         this.lstUsuariosIgnorados.remove(aux_user);
/*  579 */         return;
/*      */       }
/*      */     }
/*      */     catch (NullPointerException _ex)
/*      */     {
/*  584 */       return;
/*      */     }
/*      */     catch (IllegalArgumentException _ex) {
/*      */     }
/*      */   }
/*      */ 
/*      */   public void ack_rx_construye_lista(String x_aux_dato) {
/*  591 */     String Tipo = "";
/*  592 */     x_aux_dato = x_aux_dato.substring(x_aux_dato.indexOf('|') + 1);
/*  593 */     Tipo = x_aux_dato.substring(0, x_aux_dato.indexOf('|'));
/*  594 */     x_aux_dato = x_aux_dato.substring(x_aux_dato.indexOf('|') + 1);
/*  595 */     if (Tipo.compareTo("P") == 0)
/*      */     {
/*  597 */       if (x_aux_dato.compareTo("NOINFO") == 0) {
/*  598 */         this.bPreguntas = false;
/*      */       }
/*  600 */       else if (x_aux_dato.compareTo("FININFO") == 0)
/*      */       {
/*  602 */         this.bPreguntas = true;
/*      */       }
/*      */       else {
/*  605 */         this.bPreguntas = false;
/*  606 */         this.Moderador.Agrega_Pregunta(x_aux_dato);
/*      */       }
/*      */     }
/*  609 */     else if (x_aux_dato.compareTo("NOINFO") == 0) {
/*  610 */       this.bRespuestas = false;
/*      */     }
/*  612 */     else if (x_aux_dato.compareTo("FININFO") == 0)
/*      */     {
/*  614 */       this.bRespuestas = true;
/*      */     }
/*      */     else {
/*  617 */       this.bRespuestas = false;
/*  618 */       this.Moderador.Agrega_Respuesta(x_aux_dato);
/*      */     }
/*  620 */     if ((this.bPreguntas) || (this.bRespuestas))
/*      */     {
/*  622 */       this.bPreguntas = false;
/*  623 */       this.bRespuestas = false;
/*  624 */       this.Moderador.setVisible(true);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void ack_tx_pide_historial()
/*      */   {
/*  630 */     this.BtnHistorial.setEnabled(false);
/*  631 */     this.Historial.Limpia_Controles();
/*  632 */     PrintStream data_out = new PrintStream(this.os_connect);
/*  633 */     data_out.print("@@@CHSH|" + this.idUsuario + "CCSS");
/*      */   }
/*      */ 
/*      */   public void ack_tx_msg_priv(String x_aux_mensaje)
/*      */   {
/*  638 */     String x_aux_formato = "";
/*  639 */     int Estilo = 0;
/*  640 */     Estilo = this.objBtnBold.status ? 1 : Estilo;
/*  641 */     Estilo += (this.objBtnItalic.status ? 2 : 0);
/*  642 */     x_aux_formato = (char)(Estilo + 48) + "-";
/*  643 */     x_aux_formato = x_aux_formato + this.CBTipoFont.getSelectedItem() + "-";
/*  644 */     x_aux_formato = x_aux_formato + this.CBColorFont.getSelectedItem() + "-";
/*  645 */     x_aux_formato = x_aux_formato + "0";
/*  646 */     PrintStream data_out = new PrintStream(this.os_connect);
/*  647 */     x_aux_mensaje = limpiaCaracteres(x_aux_mensaje);
/*  648 */     data_out.print("@@@CHMP|" + this.idUsuario + "|" + this.lstUsuarios.getSelectedItem() + "|" + x_aux_formato + "|" + x_aux_mensaje + "CCSS");
/*      */   }
/*      */ 
/*      */   public void guarda_pregunta(String pregunta)
/*      */   {
/*  653 */     if (!this.modalWindow)
/*      */     {
/*  655 */       class_FrameMsgBox msgbox = new class_FrameMsgBox(this, 0, "Guardar Pregunta?", pregunta, "Si", "No", 4);
/*  656 */       msgbox.setLocation(200, 200);
/*  657 */       msgbox.show();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void rehabilita_usuario()
/*      */   {
/*  663 */     String usuario = new String(this.lstUsuariosIgnorados.getSelectedItem());
/*  664 */     this.lstUsuariosIgnorados.remove(usuario);
/*      */   }
/*      */ 
/*      */   public void ack_chat(String str_aux)
/*      */   {
/*  669 */     String x_aux_user = "";
/*  670 */     String x_aux_formato = "";
/*  671 */     String x_aux_chat = "";
/*  672 */     str_aux = str_aux.substring(str_aux.indexOf('|') + 1);
/*  673 */     x_aux_user = str_aux.substring(0, str_aux.indexOf('|'));
/*  674 */     str_aux = str_aux.substring(str_aux.indexOf('|') + 1);
/*  675 */     x_aux_formato = str_aux.substring(0, str_aux.indexOf('|'));
/*  676 */     str_aux = str_aux.substring(str_aux.indexOf('|') + 1);
/*  677 */     x_aux_chat = str_aux.substring(0, str_aux.indexOf('|'));
/*  678 */     if ((this.mi_chatdomi != null) && (!revisa_ignorados(x_aux_user)))
/*  679 */       this.mi_chatdomi.set_value(x_aux_user + ": " + x_aux_chat, x_aux_formato);
/*      */   }
/*      */ 
/*      */   public void ack_rx_recibe_historial(String x_aux_dato)
/*      */   {
/*  684 */     x_aux_dato = x_aux_dato.substring(x_aux_dato.indexOf('|') + 1);
/*  685 */     if (x_aux_dato.compareTo("NOINFO") == 0)
/*      */     {
/*  687 */       this.BtnHistorial.setEnabled(true);
/*  688 */       return;
/*      */     }
/*  690 */     if (x_aux_dato.compareTo("FININFO") == 0)
/*      */     {
/*  692 */       this.Historial.lblTitulo.setText("Mensajes Registrados: " + this.Historial.LstHistorial.getItemCount());
/*  693 */       this.Historial.setVisible(true);
/*  694 */       return;
/*      */     }
/*      */ 
/*  697 */     this.Historial.Agrega_Elemento(x_aux_dato);
/*      */   }
/*      */ 
/*      */   public void ack_desconecta_evento(String x_aux_dato)
/*      */   {
/*  704 */     x_aux_dato = x_aux_dato.substring(x_aux_dato.indexOf('|') + 1);
/*  705 */     this.mi_chatdomi.set_value("El evento ha finalizado", fDefault);
/*  706 */     stop();
/*  707 */     destroy();
/*      */   }
/*      */ 
/*      */   public class_AppletChat()
/*      */   {
/*  712 */     this.isStandalone = false;
/*  713 */     this.flag_precarga = false;
/*  714 */     this.flag_first_paint = true;
/*  715 */     this.BtnArranque = new Button("jugar...");
/*  716 */     this.i_status = 0;
/*  717 */     this.modalWindow = false;
/*  718 */     this.puerto = 0;
/*  719 */     this.os_connect = null;
/*  720 */     this.is_connect = null;
/*  721 */     this.mi_socket = null;
/*  722 */     this.data_out = null;
/*  723 */     this.TH_listen = null;
/*  724 */     this.TH_Status = null;
/*  725 */     this.X_Chat_ID_user = "";
/*  726 */     this.mi_chatdomi = null;
/*  727 */     this.lstUsuarios = new List();
/*  728 */     this.lstUsuariosIgnorados = new List();
/*  729 */     this.idUsuario = "";
/*  730 */     this.sesionUsuario = "";
/*  731 */     this.flag_inicio = false;
/*  732 */     this.anchoApp = 0;
/*  733 */     this.altoApp = 0;
/*  734 */     this.ignorar = new Vector();
/*  735 */     this.pnlLogin = new Panel();
/*  736 */     this.lbLogin1 = new Label("Escriba el nombre alias que desee utilizar:");
/*  737 */     this.lbError = new Label("");
/*  738 */     this.lblUsrConn = new Label("Usuarios Conectados");
/*  739 */     this.lblUsrIgn = new Label("Usuarios Ignorados");
/*  740 */     this.lblUsuario = new Label("Usuario: ");
/*  741 */     this.TXuserID = new TextField();
/*  742 */     this.BtnEntrar = new Button("Chatear...");
/*  743 */     this.ORIGEN_X = 70;
/*  744 */     this.ORIGEN_Y = 0;
/*  745 */     this.objBtnBold = new class_boton("N", this);
/*  746 */     this.objBtnItalic = new class_boton("I", this);
/*  747 */     this.objBtnLogo = new class_boton("-", this);
/*  748 */     this.objBtnSalir = new class_boton("X", this);
/*  749 */     this.BtnMsgPrivado = new Button("Mensaje Privado");
/*  750 */     this.BtnIgnorar = new Button("Ignorar Usuario");
/*  751 */     this.BtnExpulsar = new Button("Expulsar Usuario");
/*  752 */     this.BtnPreguntas = new Button("Preguntas");
/*  753 */     this.BtnHistorial = new Button("Historial");
/*  754 */     this.CBTipoFont = new Choice();
/*  755 */     this.CBColorFont = new Choice();
/*  756 */     this.CBExpresion = new Choice();
/*  757 */     this.imgLogo = null;
/*  758 */     this.bCargaImg = false;
/*  759 */     this.auRisa = null;
/*  760 */     this.bmoderado = false;
/*  761 */     this.bPreguntas = false;
/*  762 */     this.bRespuestas = false;
/*      */   }
/*      */ 
/*      */   public String lee_linea_stream(InputStream is_connect)
/*      */   {
/*  767 */     byte[] c_aux_leido = new byte[1];
/*  768 */     int aux_time_out = 0;
/*  769 */     boolean aux_loop = true;
/*  770 */     String aux_result = "";
/*  771 */     int aux_leidos = 0;
/*  772 */     while (aux_loop)
/*      */     {
/*  774 */       aux_leidos = 0;
/*      */       try
/*      */       {
/*  777 */         aux_leidos = is_connect.read(c_aux_leido, 0, 1);
/*      */       }
/*      */       catch (IOException _ex)
/*      */       {
/*  781 */         showStatus("Error al leer datos");
/*      */       }
/*  783 */       if (aux_leidos <= 0)
/*      */       {
/*  785 */         aux_time_out++;
/*      */         try
/*      */         {
/*  788 */           Thread.sleep(200L);
/*      */         } catch (InterruptedException _ex) {
/*      */         }
/*  791 */         if (aux_time_out <= 100)
/*      */           continue;
/*  793 */         aux_loop = false;
/*  794 */         aux_result = "CCSS99";
/*  795 */         return aux_result;
/*      */       }
/*      */ 
/*  799 */       if (c_aux_leido[0] == 13)
/*      */       {
/*  801 */         while (aux_result.startsWith("@")) aux_result = aux_result.substring(1);
/*  802 */         while (aux_result.indexOf("&~01~&") >= 0) aux_result = aux_result.substring(0, aux_result.indexOf("&~01~&")) + "á" + aux_result.substring(aux_result.indexOf("&~01~&") + 6);
/*  803 */         while (aux_result.indexOf("&~02~&") >= 0) aux_result = aux_result.substring(0, aux_result.indexOf("&~02~&")) + "é" + aux_result.substring(aux_result.indexOf("&~02~&") + 6);
/*  804 */         while (aux_result.indexOf("&~03~&") >= 0) aux_result = aux_result.substring(0, aux_result.indexOf("&~03~&")) + "í" + aux_result.substring(aux_result.indexOf("&~03~&") + 6);
/*  805 */         while (aux_result.indexOf("&~04~&") >= 0) aux_result = aux_result.substring(0, aux_result.indexOf("&~04~&")) + "ó" + aux_result.substring(aux_result.indexOf("&~04~&") + 6);
/*  806 */         while (aux_result.indexOf("&~05~&") >= 0) aux_result = aux_result.substring(0, aux_result.indexOf("&~05~&")) + "ú" + aux_result.substring(aux_result.indexOf("&~05~&") + 6);
/*  807 */         while (aux_result.indexOf("&~11~&") >= 0) aux_result = aux_result.substring(0, aux_result.indexOf("&~11~&")) + "ñ" + aux_result.substring(aux_result.indexOf("&~11~&") + 6);
/*  808 */         while (aux_result.indexOf("&~12~&") >= 0) aux_result = aux_result.substring(0, aux_result.indexOf("&~12~&")) + "Ñ" + aux_result.substring(aux_result.indexOf("&~12~&") + 6);
/*  809 */         while (aux_result.indexOf("&~13~&") >= 0) aux_result = aux_result.substring(0, aux_result.indexOf("&~13~&")) + "Á" + aux_result.substring(aux_result.indexOf("&~13~&") + 6);
/*  810 */         while (aux_result.indexOf("&~14~&") >= 0) aux_result = aux_result.substring(0, aux_result.indexOf("&~14~&")) + "É" + aux_result.substring(aux_result.indexOf("&~14~&") + 6);
/*  811 */         while (aux_result.indexOf("&~15~&") >= 0) aux_result = aux_result.substring(0, aux_result.indexOf("&~15~&")) + "Í" + aux_result.substring(aux_result.indexOf("&~15~&") + 6);
/*  812 */         while (aux_result.indexOf("&~16~&") >= 0) aux_result = aux_result.substring(0, aux_result.indexOf("&~16~&")) + "Ó" + aux_result.substring(aux_result.indexOf("&~16~&") + 6);
/*  813 */         while (aux_result.indexOf("&~17~&") >= 0) aux_result = aux_result.substring(0, aux_result.indexOf("&~17~&")) + "Ú" + aux_result.substring(aux_result.indexOf("&~17~&") + 6);
/*  814 */         while (aux_result.indexOf("&~18~&") >= 0) aux_result = aux_result.substring(0, aux_result.indexOf("&~18~&")) + "ü" + aux_result.substring(aux_result.indexOf("&~18~&") + 6);
/*  815 */         while (aux_result.indexOf("&~19~&") >= 0) aux_result = aux_result.substring(0, aux_result.indexOf("&~19~&")) + "Ü" + aux_result.substring(aux_result.indexOf("&~19~&") + 6);
/*  816 */         return aux_result;
/*      */       }
/*  818 */       aux_result = aux_result + (char)c_aux_leido[0];
/*  819 */       aux_time_out = 0;
/*      */     }
/*      */ 
/*  822 */     return aux_result;
/*      */   }
/*      */ 
/*      */   public String[][] getParameterInfo()
/*      */   {
/*  827 */     return (String[][])null;
/*      */   }
/*      */ 
/*      */   public void salir()
/*      */   {
/*      */     try
/*      */     {
/*  834 */       getAppletContext().showDocument(new URL("http://infochat.infotec.com.mx/client/salir.htm"));
/*  835 */       return;
/*      */     }
/*      */     catch (MalformedURLException _ex)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   private void ack_conecta(String str_aux)
/*      */   {
/*  845 */     String aux_user = "";
/*  846 */     boolean aux_yaexiste = false;
/*  847 */     if (str_aux.indexOf('|') > 0)
/*  848 */       str_aux = str_aux.substring(str_aux.indexOf('|') + 1);
/*  849 */     aux_user = str_aux;
/*  850 */     for (int j = 0; (j < this.lstUsuarios.getItemCount()) && (!aux_yaexiste); j++)
/*      */     {
/*  852 */       String aux_user2 = this.lstUsuarios.getItem(j).toString();
/*  853 */       if (aux_user.compareTo(aux_user2) == 0)
/*      */       {
/*  855 */         aux_yaexiste = true;
/*  856 */         j = this.lstUsuarios.getItemCount();
/*      */       } else {
/*  858 */         if (aux_user.compareTo(aux_user2) >= 0)
/*      */           continue;
/*  860 */         this.lstUsuarios.addItem(aux_user, j);
/*  861 */         aux_yaexiste = true;
/*  862 */         j = this.lstUsuarios.getItemCount();
/*      */       }
/*      */     }
/*      */ 
/*  866 */     if (!aux_yaexiste)
/*  867 */       this.lstUsuarios.addItem(aux_user);
/*      */   }
/*      */ 
/*      */   public void ack_tx_respuesta(String x_aux_user_origen, String x_aux_num_pregunta, String x_aux_respuesta)
/*      */   {
/*  872 */     PrintStream data_out = new PrintStream(this.os_connect);
/*  873 */     x_aux_respuesta = limpiaCaracteres(x_aux_respuesta);
/*  874 */     data_out.print("@@@CHGR|" + x_aux_user_origen + "|" + this.idUsuario + "|" + x_aux_respuesta + "|" + x_aux_num_pregunta + "CCSS");
/*      */   }
/*      */ 
/*      */   public boolean revisa_ignorados(String usuario)
/*      */   {
/*  879 */     boolean existe = false;
/*  880 */     for (int j = 0; (j < this.lstUsuariosIgnorados.getItemCount()) && (!existe); j++)
/*      */     {
/*  882 */       String aux_user2 = this.lstUsuariosIgnorados.getItem(j);
/*  883 */       if (aux_user2.compareTo(usuario) == 0) {
/*  884 */         existe = true;
/*      */       }
/*      */     }
/*  887 */     return existe;
/*      */   }
/*      */ 
/*      */   public void ack_rx_expulsar(String str_aux)
/*      */   {
/*  892 */     String aux_user = "";
/*  893 */     str_aux = str_aux.substring(str_aux.indexOf('|') + 1);
/*  894 */     aux_user = str_aux;
/*  895 */     if (aux_user.compareTo(this.idUsuario) == 0)
/*      */     {
/*  897 */       this.mi_chatdomi.set_value("Usted ha sido expulsado del chat room por el moderador", fDefault);
/*  898 */       this.lstUsuarios.removeAll();
/*  899 */       this.lstUsuariosIgnorados.removeAll();
/*  900 */       this.TH_listen.stop();
/*      */     }
/*      */     else {
/*  903 */       this.mi_chatdomi.set_value("[" + aux_user + "] ha sido expulsado del chat room", fDefault);
/*      */     }
/*  905 */     this.lstUsuarios.remove(aux_user);
/*      */     try
/*      */     {
/*  908 */       if (revisa_ignorados(aux_user))
/*      */       {
/*  910 */         this.lstUsuariosIgnorados.remove(aux_user);
/*  911 */         return;
/*      */       }
/*      */     }
/*      */     catch (NullPointerException _ex)
/*      */     {
/*  916 */       return;
/*      */     }
/*      */     catch (IllegalArgumentException _ex) {
/*      */     }
/*      */   }
/*      */ 
/*      */   private void ack_changeconnect(String str_aux) {
/*  923 */     if (str_aux.indexOf('|') > 0)
/*  924 */       str_aux = str_aux.substring(str_aux.indexOf('|') + 1);
/*  925 */     this.idUsuario = str_aux;
/*  926 */     this.lblUsuario.setText("Usuario: " + this.idUsuario);
/*      */   }
/*      */ 
   public void ack_tx_pregunta(String x_aux_mensaje)
    {
        String aux_user_origen;
        String aux_msg;
        if(x_aux_mensaje.startsWith("["))
        {
            x_aux_mensaje = x_aux_mensaje.substring(x_aux_mensaje.indexOf('[') + 1);
            aux_user_origen = x_aux_mensaje.substring(0, x_aux_mensaje.indexOf(']'));
            aux_msg = x_aux_mensaje.substring(x_aux_mensaje.indexOf(']') + 1);
        } else
        {
            aux_user_origen = x_aux_mensaje.substring(0, x_aux_mensaje.indexOf(':'));
            aux_msg = x_aux_mensaje.substring(x_aux_mensaje.indexOf(':') + 2);
        }
        PrintStream data_out = new PrintStream(os_connect);
        aux_msg = limpiaCaracteres(aux_msg);
        data_out.print("@@@CHGP|" + aux_user_origen + "|" + idUsuario + "|" + aux_msg + "CCSS");
    }

    public String limpiaCaracteres(String auxStr)
    {
        String aux_result;
        for(aux_result = auxStr; aux_result.indexOf("'") >= 0; aux_result = aux_result.substring(0, aux_result.indexOf("'")) + "" + aux_result.substring(aux_result.indexOf("'") + 1));
        for(; aux_result.indexOf('\341') >= 0; aux_result = aux_result.substring(0, aux_result.indexOf('\341')) + "&~01~&" + aux_result.substring(aux_result.indexOf('\341') + 1));
        for(; aux_result.indexOf('\351') >= 0; aux_result = aux_result.substring(0, aux_result.indexOf('\351')) + "&~02~&" + aux_result.substring(aux_result.indexOf('\351') + 1));
        for(; aux_result.indexOf('\355') >= 0; aux_result = aux_result.substring(0, aux_result.indexOf('\355')) + "&~03~&" + aux_result.substring(aux_result.indexOf('\355') + 1));
        for(; aux_result.indexOf('\363') >= 0; aux_result = aux_result.substring(0, aux_result.indexOf('\363')) + "&~04~&" + aux_result.substring(aux_result.indexOf('\363') + 1));
        for(; aux_result.indexOf('\372') >= 0; aux_result = aux_result.substring(0, aux_result.indexOf('\372')) + "&~05~&" + aux_result.substring(aux_result.indexOf('\372') + 1));
        for(; aux_result.indexOf('\361') >= 0; aux_result = aux_result.substring(0, aux_result.indexOf('\361')) + "&~11~&" + aux_result.substring(aux_result.indexOf('\361') + 1));
        for(; aux_result.indexOf('\321') >= 0; aux_result = aux_result.substring(0, aux_result.indexOf('\321')) + "&~12~&" + aux_result.substring(aux_result.indexOf('\321') + 1));
        for(; aux_result.indexOf('\301') >= 0; aux_result = aux_result.substring(0, aux_result.indexOf('\301')) + "&~13~&" + aux_result.substring(aux_result.indexOf('\301') + 1));
        for(; aux_result.indexOf('\311') >= 0; aux_result = aux_result.substring(0, aux_result.indexOf('\311')) + "&~14~&" + aux_result.substring(aux_result.indexOf('\311') + 1));
        for(; aux_result.indexOf('\315') >= 0; aux_result = aux_result.substring(0, aux_result.indexOf('\315')) + "&~15~&" + aux_result.substring(aux_result.indexOf('\315') + 1));
        for(; aux_result.indexOf('\323') >= 0; aux_result = aux_result.substring(0, aux_result.indexOf('\323')) + "&~16~&" + aux_result.substring(aux_result.indexOf('\323') + 1));
        for(; aux_result.indexOf('\332') >= 0; aux_result = aux_result.substring(0, aux_result.indexOf('\332')) + "&~17~&" + aux_result.substring(aux_result.indexOf('\332') + 1));
        for(; aux_result.indexOf('\374') >= 0; aux_result = aux_result.substring(0, aux_result.indexOf('\374')) + "&~18~&" + aux_result.substring(aux_result.indexOf('\374') + 1));
        for(; aux_result.indexOf('\334') >= 0; aux_result = aux_result.substring(0, aux_result.indexOf('\334')) + "&~19~&" + aux_result.substring(aux_result.indexOf('\334') + 1));
        return aux_result;
    }
/*      */ 
/*      */   public void ignorar_usuario()
/*      */   {
/*  971 */     boolean aux_yaexiste = false;
/*  972 */     for (int j = 0; (j < this.lstUsuariosIgnorados.getItemCount()) && (!aux_yaexiste); j++)
/*      */     {
/*  974 */       String aux_user2 = this.lstUsuariosIgnorados.getItem(j);
/*  975 */       if (this.lstUsuarios.getSelectedItem().compareTo(aux_user2) == 0) {
/*  976 */         aux_yaexiste = true;
/*      */       } else {
/*  978 */         if (this.lstUsuarios.getSelectedItem().compareTo(aux_user2) >= 0)
/*      */           continue;
/*  980 */         this.lstUsuariosIgnorados.addItem(this.lstUsuarios.getSelectedItem(), j);
/*  981 */         aux_yaexiste = true;
/*      */       }
/*      */     }
/*      */ 
/*  985 */     if (!aux_yaexiste)
/*  986 */       this.lstUsuariosIgnorados.addItem(this.lstUsuarios.getSelectedItem());
/*      */   }
/*      */ 
/*      */   public void run()
/*      */   {
/*  991 */     if (Thread.currentThread() == this.TH_listen)
/*      */     {
/*  993 */       String x_aux_dato = "";
/*      */       while (true)
/*      */       {
/*  998 */         x_aux_dato = lee_linea_stream(this.is_connect);
/*  999 */         if (x_aux_dato.startsWith("CCSS99"))
/*      */         {
/* 1001 */           this.TH_Status = new Thread(this);
/* 1002 */           this.TH_Status.start();
/* 1003 */           this.mi_chatdomi.set_value("No hay respuesta del servidor", fDefault);
/* 1004 */           this.lstUsuarios.removeAll();
/* 1005 */           this.lstUsuariosIgnorados.removeAll();
/*      */           try
/*      */           {
/* 1008 */             this.is_connect.close();
/*      */           }
/*      */           catch (IOException _ex) {
/*      */           }
/*      */           try {
/* 1013 */             this.mi_socket.close();
/*      */           } catch (IOException _ex) {
/*      */           }
/* 1016 */           this.mi_socket = null;
/* 1017 */           this.TH_listen.stop();
/* 1018 */           return;
/*      */         }
/* 1020 */         if (x_aux_dato.startsWith("CCSS98"))
/*      */         {
/* 1022 */           showStatus("Error al leer respuesta del servidor");
/*      */           try
/*      */           {
/* 1025 */             this.is_connect.close();
/*      */           } catch (IOException _ex) {
/*      */           }
/* 1028 */           this.TH_listen.stop();
/* 1029 */           return;
/*      */         }
/* 1031 */         if (!x_aux_dato.startsWith("ZZSS")) {
/* 1032 */           if (x_aux_dato.startsWith("CH|")) {
/* 1033 */             ack_chat(x_aux_dato); continue;
/*      */           }
/* 1035 */           if (x_aux_dato.startsWith("CN|")) {
/* 1036 */             ack_conecta(x_aux_dato); continue;
/*      */           }
/* 1038 */           if (x_aux_dato.startsWith("ACTROOM|")) {
/* 1039 */             ack_actualizaroom(x_aux_dato); continue;
/*      */           }
/* 1041 */           if (x_aux_dato.startsWith("DCN|")) {
/* 1042 */             ack_desconecta(x_aux_dato); continue;
/*      */           }
/* 1044 */           if (x_aux_dato.startsWith("DCE|")) {
/* 1045 */             ack_desconecta_evento(x_aux_dato); continue;
/*      */           }
/* 1047 */           if (x_aux_dato.startsWith("DCNEX|")) {
/* 1048 */             ack_rx_expulsar(x_aux_dato); continue;
/*      */           }
/* 1050 */           if (x_aux_dato.startsWith("CHMP|")) {
/* 1051 */             ack_rx_msg_priv(x_aux_dato); continue;
/*      */           }
/* 1053 */           if (x_aux_dato.startsWith("CHNGCN|")) {
/* 1054 */             ack_changeconnect(x_aux_dato); continue;
/*      */           }
/* 1056 */           if (x_aux_dato.startsWith("CHCP|")) {
/* 1057 */             ack_rx_construye_lista(x_aux_dato); continue;
/*      */           }
/* 1059 */           if (x_aux_dato.startsWith("CHMR|")) {
/* 1060 */             ack_rx_respuesta_moderador(x_aux_dato); continue;
/*      */           }
/* 1062 */           if (x_aux_dato.startsWith("CHEH|"))
/* 1063 */             ack_rx_recibe_historial(x_aux_dato); 
/*      */         }
/*      */       }
/*      */     }
/* 1066 */     if (Thread.currentThread() == this.TH_Status)
/*      */       while (true)
/*      */       {
/*      */         try
/*      */         {
/* 1071 */           Thread.sleep(10000L);
/*      */         } catch (InterruptedException _ex) {
/*      */         }
/* 1074 */         conecta_client_server();
/*      */       }
/*      */   }
/*      */ 
/*      */   public void init()
/*      */   {
/*      */     try
/*      */     {
/* 1084 */       jbInit();
/* 1085 */       return;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1089 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void ack_tx_pide_preguntas()
/*      */   {
/* 1095 */     this.Moderador.LimpiaControles();
/* 1096 */     PrintStream data_out = new PrintStream(this.os_connect);
/* 1097 */     data_out.print("@@@CHCP|" + this.idUsuario + "CCSS");
/*      */   }
/*      */ 
/*      */   class ItemHandler
/*      */     implements ItemListener
/*      */   {
/*      */     public void itemStateChanged(ItemEvent e)
/*      */     {
/*  188 */       if (class_AppletChat.this.CBExpresion.getSelectedIndex() > 0)
/*      */       {
/*  190 */         String quita_texto = class_AppletChat.this.CBExpresion.getSelectedItem();
/*  191 */         quita_texto = quita_texto.substring(quita_texto.length() - 3);
/*  192 */         class_AppletChat.this.mi_chatdomi.TX_chat.setText(class_AppletChat.this.mi_chatdomi.TX_chat.getText() + quita_texto);
/*      */       }
/*      */     }
/*      */ 
/*      */     ItemHandler()
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   class MouseHandler
/*      */     implements MouseListener
/*      */   {
/*      */     public void mouseClicked(MouseEvent e)
/*      */     {
/*  148 */       if ((class_AppletChat.this.lstUsuarios.getSelectedItem() != null) && (e.getSource().equals(class_AppletChat.this.lstUsuarios)) && (e.getClickCount() == 2) && ((e.getModifiers() & 0x10) == 16) && (!class_AppletChat.this.modalWindow) && (class_AppletChat.this.lstUsuarios.getSelectedItem().compareTo(class_AppletChat.this.idUsuario) != 0))
/*      */       {
/*  150 */         String usuario = new String(class_AppletChat.this.lstUsuarios.getSelectedItem());
/*  151 */         class_FrameMsgBox msgbox = new class_FrameMsgBox(class_AppletChat.this, 0, "Mensaje Privado a " + usuario, "Teclee por favor el mensaje privado para [" + usuario + "]", "Aceptar", "Cancelar", 0);
/*  152 */         msgbox.setLocation(200, 200);
/*  153 */         msgbox.show();
/*  154 */         class_AppletChat.this.modalWindow = true;
/*  155 */         return;
/*      */       }
/*  157 */       if ((class_AppletChat.this.lstUsuariosIgnorados.getSelectedItem() != null) && (e.getSource().equals(class_AppletChat.this.lstUsuariosIgnorados)) && (e.getClickCount() == 2) && ((e.getModifiers() & 0x10) == 16) && (!class_AppletChat.this.modalWindow))
/*  158 */         class_AppletChat.this.rehabilita_usuario();
/*      */     }
/*      */ 
/*      */     public void mousePressed(MouseEvent mouseevent)
/*      */     {
/*      */     }
/*      */ 
/*      */     public void mouseReleased(MouseEvent mouseevent)
/*      */     {
/*      */     }
/*      */ 
/*      */     public void mouseEntered(MouseEvent mouseevent)
/*      */     {
/*      */     }
/*      */ 
/*      */     public void mouseExited(MouseEvent mouseevent)
/*      */     {
/*      */     }
/*      */ 
/*      */     MouseHandler()
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   class ActionHandler
/*      */     implements ActionListener
/*      */   {
/*      */     public void actionPerformed(ActionEvent e)
/*      */     {
/*   74 */       if ((e.getSource().equals(class_AppletChat.this.BtnMsgPrivado)) && (class_AppletChat.this.lstUsuarios.getSelectedItem() != null) && (class_AppletChat.this.lstUsuarios.getSelectedItem().compareTo(class_AppletChat.this.idUsuario) != 0))
/*      */       {
/*   76 */         String usuario = new String(class_AppletChat.this.lstUsuarios.getSelectedItem());
/*   77 */         class_FrameMsgBox msgbox = new class_FrameMsgBox(class_AppletChat.this, 0, "Mensaje Privado a " + usuario, "Teclee por favor el mensaje privado para [" + usuario + "]", "Aceptar", "Cancelar", 0);
/*   78 */         msgbox.setLocation(200, 200);
/*   79 */         msgbox.show();
/*   80 */         return;
/*      */       }
/*   82 */       if ((e.getSource().equals(class_AppletChat.this.BtnIgnorar)) && (class_AppletChat.this.lstUsuarios.getSelectedItem() != null) && (class_AppletChat.this.lstUsuarios.getSelectedItem().compareTo(class_AppletChat.this.idUsuario) != 0))
/*      */       {
/*   84 */         if (!class_AppletChat.this.revisa_ignorados(class_AppletChat.this.lstUsuarios.getSelectedItem()))
/*      */         {
/*   86 */           class_AppletChat.this.ignorar_usuario();
/*   87 */           return;
/*      */         }
/*      */       }
/*      */       else {
/*   91 */         if ((e.getSource().equals(class_AppletChat.this.BtnExpulsar)) && (class_AppletChat.this.lstUsuarios.getSelectedItem() != null) && (class_AppletChat.this.lstUsuarios.getSelectedItem().compareTo(class_AppletChat.this.idUsuario) != 0))
/*      */         {
/*   93 */           String usuario = new String(class_AppletChat.this.lstUsuarios.getSelectedItem());
/*   94 */           class_FrameMsgBox msgbox = new class_FrameMsgBox(class_AppletChat.this, 0, "Expulsar Usuario: " + usuario, "Está seguro de expulsar al usuario [" + usuario + "]?", "Si", "No", 2);
/*   95 */           msgbox.setLocation(200, 200);
/*   96 */           msgbox.show();
/*   97 */           return;
/*      */         }
/*   99 */         if (e.getSource().equals(class_AppletChat.this.BtnArranque))
/*      */           try
/*      */           {
/*  102 */             class_AppletChat.this.getAppletContext().showDocument(new URL(class_AppletChat.this.getCodeBase() + class_AppletChat.this.getParameter("precarga")));
/*  103 */             return;
/*      */           }
/*      */           catch (MalformedURLException _ex)
/*      */           {
/*  107 */             return;
/*      */           }
/*  109 */         if (e.getSource().equals(class_AppletChat.this.BtnEntrar))
/*      */         {
/*  111 */           if (class_AppletChat.this.TXuserID.getText().trim().equalsIgnoreCase(""))
/*      */           {
/*  113 */             class_AppletChat.this.lbError.setText("Debe escribir un nombre alias");
/*  114 */             return;
/*      */           }
/*  116 */           if ((class_AppletChat.this.TXuserID.getText().toLowerCase().indexOf("á") >= 0) || (class_AppletChat.this.TXuserID.getText().toLowerCase().indexOf("é") >= 0) || (class_AppletChat.this.TXuserID.getText().toLowerCase().indexOf("í") >= 0) || (class_AppletChat.this.TXuserID.getText().toLowerCase().indexOf("ó") >= 0) || (class_AppletChat.this.TXuserID.getText().toLowerCase().indexOf("ú") >= 0) || (class_AppletChat.this.TXuserID.getText().toLowerCase().indexOf("ñ") >= 0) || (class_AppletChat.this.TXuserID.getText().toLowerCase().indexOf("'") >= 0) || (class_AppletChat.this.TXuserID.getText().toLowerCase().indexOf("@") >= 0))
/*      */           {
/*  118 */             class_AppletChat.this.lbError.setText("El nombre alias no puede tener caracteres especiales, ni acentos ni (ñ)");
/*  119 */             return;
/*      */           }
/*      */ 
/*  122 */           class_AppletChat.this.idUsuario = class_AppletChat.this.TXuserID.getText().trim();
/*  123 */           class_AppletChat.this.conecta_client_server();
/*  124 */           return;
/*      */         }
/*      */ 
/*  127 */         if (e.getSource().equals(class_AppletChat.this.BtnPreguntas))
/*      */         {
/*  129 */           class_AppletChat.this.ack_tx_pide_preguntas();
/*  130 */           return;
/*      */         }
/*  132 */         if (e.getSource().equals(class_AppletChat.this.BtnHistorial))
/*  133 */           class_AppletChat.this.ack_tx_pide_historial();
/*      */       }
/*      */     }
/*      */ 
/*      */     ActionHandler()
/*      */     {
/*      */     }
/*      */   }
/*      */ }

/* Location:           /programming/proys/SWB4/swb/applets/AppletChat/src/
 * Qualified Name:     class_AppletChat
 * JD-Core Version:    0.6.0
 */