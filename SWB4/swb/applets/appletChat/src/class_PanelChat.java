/*     */ import java.awt.Button;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Event;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.Panel;
/*     */ import java.awt.Scrollbar;
/*     */ import java.awt.TextField;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.AdjustmentEvent;
/*     */ import java.awt.event.AdjustmentListener;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class class_PanelChat extends Panel
/*     */ {
/*     */   class_AppletChat comp_padre;
/*     */   String tooltip;
/*     */   Vector v_tooltip;
/*     */   Vector v_chat;
/*     */   Vector v_formato;
/*     */   Vector v_textocompleto;
/*     */   int anchoChat;
/*     */   int altoChat;
/*     */   Color colorFondo;
/*     */   Color colorLetra;
/*     */   int Page_Row_Size;
/*     */   int Cur_Row;
/*     */   int altoFont;
/*     */   int anchoReal;
/*     */   int TamFont;
/*     */   int img_size;
/*     */   int img_size2;
/*     */   Image imagen;
/*     */   Image img_enojado;
/*     */   Image img_feliz;
/*     */   Image img_sorprendido;
/*     */   Image img_serio;
/*     */   Image img_beso;
/*     */   Image img_guinio;
/*     */   Image img_mueca;
/*     */   Image img_lengua;
/*     */   Image img1;
/*     */   Image img2;
/*     */   TextField TX_chat;
/*     */   Button BtnEnviar;
/*     */   Scrollbar SB_vert;
/*     */ 
/*     */   public boolean formato_texto(Graphics g, int j)
/*     */   {
/* 130 */     String FontTexto = "";
/* 131 */     String x_aux_formato = this.v_formato.elementAt(j).toString();
/* 132 */     int Estilo = Integer.valueOf(x_aux_formato.substring(0, x_aux_formato.indexOf('-'))).intValue();
/* 133 */     x_aux_formato = x_aux_formato.substring(x_aux_formato.indexOf('-') + 1);
/* 134 */     FontTexto = x_aux_formato.substring(0, x_aux_formato.indexOf('-'));
/* 135 */     x_aux_formato = x_aux_formato.substring(x_aux_formato.indexOf('-') + 1);
/* 136 */     String Color_Texto = x_aux_formato.substring(0, x_aux_formato.indexOf('-'));
/* 137 */     x_aux_formato = x_aux_formato.substring(x_aux_formato.indexOf('-') + 1);
/* 138 */     boolean auxModerador = x_aux_formato.compareTo("1") == 0;
/* 139 */     if (Color_Texto.trim().equals("Negro")) {
/* 140 */       g.setColor(Color.black);
/*     */     }
/* 142 */     else if (Color_Texto.trim().equals("Azul")) {
/* 143 */       g.setColor(Color.blue);
/*     */     }
/* 145 */     else if (Color_Texto.trim().equals("Cyan")) {
/* 146 */       g.setColor(Color.cyan);
/*     */     }
/* 148 */     else if (Color_Texto.trim().equals("Gris Obscuro")) {
/* 149 */       g.setColor(Color.darkGray);
/*     */     }
/* 151 */     else if (Color_Texto.trim().equals("Gris")) {
/* 152 */       g.setColor(Color.gray);
/*     */     }
/* 154 */     else if (Color_Texto.trim().equals("Verde")) {
/* 155 */       g.setColor(Color.green);
/*     */     }
/* 157 */     else if (Color_Texto.trim().equals("Gris Claro")) {
/* 158 */       g.setColor(Color.lightGray);
/*     */     }
/* 160 */     else if (Color_Texto.trim().equals("Magenta")) {
/* 161 */       g.setColor(Color.magenta);
/*     */     }
/* 163 */     else if (Color_Texto.trim().equals("Naranja")) {
/* 164 */       g.setColor(Color.orange);
/*     */     }
/* 166 */     else if (Color_Texto.trim().equals("Rosa")) {
/* 167 */       g.setColor(Color.pink);
/*     */     }
/* 169 */     else if (Color_Texto.trim().equals("Rojo")) {
/* 170 */       g.setColor(Color.red);
/*     */     }
/* 172 */     else if (Color_Texto.trim().equals("Amarillo")) {
/* 173 */       g.setColor(Color.yellow);
/*     */     }
/* 175 */     else if (Color_Texto.trim().equals("Blanco"))
/* 176 */       g.setColor(Color.white);
/* 177 */     g.setFont(new Font(FontTexto, Estilo, this.TamFont));
/* 178 */     return auxModerador;
/*     */   }
/*     */ 
/*     */   public class_PanelChat(class_AppletChat comp_padre)
/*     */   {
/* 183 */     this.comp_padre = null;
/* 184 */     this.tooltip = "";
/* 185 */     this.v_tooltip = new Vector();
/* 186 */     this.v_chat = new Vector();
/* 187 */     this.v_formato = new Vector();
/* 188 */     this.v_textocompleto = new Vector();
/* 189 */     this.anchoChat = 0;
/* 190 */     this.altoChat = 0;
/* 191 */     this.colorFondo = Color.white;
/* 192 */     this.colorLetra = Color.gray;
/* 193 */     this.Page_Row_Size = 0;
/* 194 */     this.Cur_Row = -1;
/* 195 */     this.altoFont = 0;
/* 196 */     this.anchoReal = 0;
/* 197 */     this.TamFont = 14;
/* 198 */     this.img_size = 15;
/* 199 */     this.img_size2 = 30;
/* 200 */     this.TX_chat = new TextField();
/* 201 */     this.BtnEnviar = new Button("Enviar");
/* 202 */     this.SB_vert = new Scrollbar(1, 1, 1, 0, 1);
/* 203 */     setLayout(null);
/* 204 */     this.comp_padre = comp_padre;
/* 205 */     setBackground(Color.white);
/* 206 */     setSize(120, 420);
/* 207 */     setLocation(0, 0);
/* 208 */     this.anchoChat = 120;
/* 209 */     this.altoChat = 420;
/* 210 */     this.TX_chat.setBounds(0, this.altoChat - 25, this.anchoChat - 100, 25);
/* 211 */     this.TX_chat.setBackground(Color.lightGray);
/* 212 */     this.TX_chat.setFont(new Font("Dialog", 1, 12));
/* 213 */     this.BtnEnviar.setBounds(this.anchoChat - 100 + 2, this.altoChat - 25, 100, 25);
/* 214 */     add(this.TX_chat);
/* 215 */     add(this.BtnEnviar);
/* 216 */     this.BtnEnviar.addActionListener(new class_PanelChat.ActionHandler());
/*     */   }
/*     */ 
/*     */   public class_PanelChat(class_AppletChat comp_padre, Color colorFondo, Color colorLetra, int anchoChat, int altoChat)
/*     */   {
/* 221 */     this.comp_padre = null;
/* 222 */     this.tooltip = "";
/* 223 */     this.v_tooltip = new Vector();
/* 224 */     this.v_chat = new Vector();
/* 225 */     this.v_formato = new Vector();
/* 226 */     this.v_textocompleto = new Vector();
/* 227 */     this.anchoChat = 0;
/* 228 */     this.altoChat = 0;
/* 229 */     this.colorFondo = Color.white;
/* 230 */     this.colorLetra = Color.gray;
/* 231 */     this.Page_Row_Size = 0;
/* 232 */     this.Cur_Row = -1;
/* 233 */     this.altoFont = 0;
/* 234 */     this.anchoReal = 0;
/* 235 */     this.TamFont = 14;
/* 236 */     this.img_size = 15;
/* 237 */     this.img_size2 = 30;
/* 238 */     this.TX_chat = new TextField();
/* 239 */     this.BtnEnviar = new Button("Enviar");
/* 240 */     this.SB_vert = new Scrollbar(1, 1, 1, 0, 1);
/* 241 */     setLayout(null);
/* 242 */     this.comp_padre = comp_padre;
/* 243 */     setBackground(Color.white);
/* 244 */     this.colorFondo = colorFondo;
/* 245 */     setBackground(colorFondo);
/* 246 */     this.colorLetra = colorLetra;
/* 247 */     this.anchoChat = anchoChat;
/* 248 */     this.altoChat = altoChat;
/* 249 */     setSize(anchoChat, altoChat);
/* 250 */     this.TX_chat.setBounds(0, altoChat - 25, anchoChat - 100, 25);
/* 251 */     this.BtnEnviar.setBounds(anchoChat - 100 + 2, altoChat - 25, 100, 25);
/* 252 */     add(this.BtnEnviar);
/* 253 */     this.SB_vert.setBounds(anchoChat - 10, 0, 10, altoChat - 25);
/* 254 */     this.SB_vert.addAdjustmentListener(new class_PanelChat.AdjustmentHandler());
/* 255 */     this.imagen = comp_padre.getImage(comp_padre.getCodeBase(), "arrow.gif");
/* 256 */     this.img_enojado = comp_padre.getImage(comp_padre.getCodeBase(), "enojado.gif");
/* 257 */     this.img_feliz = comp_padre.getImage(comp_padre.getCodeBase(), "sonrisa.gif");
/* 258 */     this.img_sorprendido = comp_padre.getImage(comp_padre.getCodeBase(), "sorprendido.gif");
/* 259 */     this.img_serio = comp_padre.getImage(comp_padre.getCodeBase(), "serio.gif");
/* 260 */     this.img_beso = comp_padre.getImage(comp_padre.getCodeBase(), "beso.gif");
/* 261 */     this.img_guinio = comp_padre.getImage(comp_padre.getCodeBase(), "guinio.gif");
/* 262 */     this.img_mueca = comp_padre.getImage(comp_padre.getCodeBase(), "mueca.gif");
/* 263 */     this.img_lengua = comp_padre.getImage(comp_padre.getCodeBase(), "lengua.gif");
/* 264 */     this.img1 = comp_padre.getImage(comp_padre.getCodeBase(), "flor.gif");
/* 265 */     this.img2 = comp_padre.getImage(comp_padre.getCodeBase(), "pastel.gif");
/* 266 */     this.anchoReal = (anchoChat - 15);
/* 267 */     anchoChat -= 10;
/* 268 */     this.TX_chat.setBackground(Color.white);
/* 269 */     add(this.TX_chat);
/* 270 */     FontMetrics fmetrics = getFontMetrics(new Font("Helvetica", 1, this.TamFont));
/* 271 */     this.Page_Row_Size = ((altoChat - 35) / fmetrics.getHeight());
/* 272 */     this.SB_vert.setBlockIncrement(fmetrics.getHeight());
/* 273 */     this.altoFont = fmetrics.getHeight();
/* 274 */     this.BtnEnviar.addActionListener(new class_PanelChat.ActionHandler());
/*     */   }
/*     */ 
/*     */   public void paint(Graphics g)
/*     */   {
/* 279 */     dibuja();
/*     */   }
/*     */ 
/*     */   public void drawString_Alt(Graphics g, int x, int y, String aux_cadena)
/*     */   {
/* 284 */     int longitud = x;
/* 285 */     FontMetrics fmetrics = g.getFontMetrics();
/* 286 */     for (int posicion = 0; posicion < aux_cadena.length(); posicion++)
/* 287 */       switch (aux_cadena.charAt(posicion))
/*     */       {
/*     */       case '<':
/* 290 */         if (posicion < aux_cadena.length() - 5)
/*     */         {
/* 292 */           if ((aux_cadena.charAt(posicion) == '<') && (aux_cadena.charAt(posicion + 1) == 'i') && (aux_cadena.charAt(posicion + 2) == 'm') && (aux_cadena.charAt(posicion + 3) == 'g') && (aux_cadena.charAt(posicion + 3) == 'g') && (aux_cadena.charAt(posicion + 4) >= '1') && (aux_cadena.charAt(posicion + 4) <= '2') && (aux_cadena.charAt(posicion + 5) == '>'))
/*     */           {
/* 294 */             switch (aux_cadena.charAt(posicion + 4))
/*     */             {
/*     */             case '1':
/* 297 */               g.drawImage(this.img1, longitud, y - 12, this);
/* 298 */               break;
/*     */             case '2':
/* 301 */               g.drawImage(this.img2, longitud, y - 12, this);
/*     */             }
/*     */ 
/* 304 */             posicion += 5;
/* 305 */             longitud += this.img_size2;
/*     */           }
/*     */           else {
/* 308 */             g.drawString(aux_cadena.substring(posicion, posicion + 1), longitud, y);
/* 309 */             longitud += fmetrics.charWidth(aux_cadena.charAt(posicion));
/*     */           }
/*     */         }
/*     */         else {
/* 313 */           g.drawString(aux_cadena.substring(posicion, posicion + 1), longitud, y);
/* 314 */           longitud += fmetrics.charWidth(aux_cadena.charAt(posicion));
/*     */         }
/* 316 */         break;
/*     */       case ':':
/*     */       case ';':
/* 320 */         if (posicion < aux_cadena.length() - 2)
/*     */         {
/* 322 */           if ((aux_cadena.charAt(posicion) == ':') && (aux_cadena.charAt(posicion + 1) == '-') && ((aux_cadena.charAt(posicion + 2) == '(') || (aux_cadena.charAt(posicion + 2) == ')') || (aux_cadena.charAt(posicion + 2) == 'O') || (aux_cadena.charAt(posicion + 2) == '|') || (aux_cadena.charAt(posicion + 2) == '*') || (aux_cadena.charAt(posicion + 2) == '>') || (aux_cadena.charAt(posicion + 2) == 'P')))
/*     */           {
/* 324 */             switch (aux_cadena.charAt(posicion + 2))
/*     */             {
/*     */             case ')':
/* 327 */               g.drawImage(this.img_feliz, longitud, y - 10, this);
/* 328 */               break;
/*     */             case '(':
/* 331 */               g.drawImage(this.img_enojado, longitud, y - 10, this);
/* 332 */               break;
/*     */             case 'O':
/* 335 */               g.drawImage(this.img_sorprendido, longitud, y - 10, this);
/* 336 */               break;
/*     */             case '>':
/* 339 */               g.drawImage(this.img_mueca, longitud, y - 10, this);
/* 340 */               break;
/*     */             case 'P':
/* 343 */               g.drawImage(this.img_lengua, longitud, y - 10, this);
/* 344 */               break;
/*     */             case '*':
/* 347 */               g.drawImage(this.img_beso, longitud, y - 10, this);
/* 348 */               break;
/*     */             case '|':
/* 351 */               g.drawImage(this.img_serio, longitud, y - 10, this);
/*     */             }
/*     */ 
/* 354 */             posicion += 2;
/* 355 */             longitud += this.img_size;
/*     */           }
/* 358 */           else if ((aux_cadena.charAt(posicion) == ';') && (aux_cadena.charAt(posicion + 1) == '-') && (aux_cadena.charAt(posicion + 2) == ')'))
/*     */           {
/* 360 */             g.drawImage(this.img_guinio, longitud, y - 10, this);
/* 361 */             posicion += 2;
/* 362 */             longitud += this.img_size;
/*     */           }
/*     */           else {
/* 365 */             g.drawString(aux_cadena.substring(posicion, posicion + 1), longitud, y);
/* 366 */             longitud += fmetrics.charWidth(aux_cadena.charAt(posicion));
/*     */           }
/*     */         }
/*     */         else {
/* 370 */           g.drawString(aux_cadena.substring(posicion, posicion + 1), longitud, y);
/* 371 */           longitud += fmetrics.charWidth(aux_cadena.charAt(posicion));
/*     */         }
/* 373 */         break;
/*     */       default:
/* 376 */         g.drawString(aux_cadena.substring(posicion, posicion + 1), longitud, y);
/* 377 */         longitud += fmetrics.charWidth(aux_cadena.charAt(posicion));
/*     */       }
/*     */   }
/*     */ 
/*     */   public int ancho_cadena(String aux_cadena, FontMetrics fmetrics, boolean auxModerador)
/*     */   {
/* 385 */     int longitud = 0;
/* 386 */     if (auxModerador)
/* 387 */       longitud = 10;
/* 388 */     for (int posicion = 0; posicion < aux_cadena.length(); posicion++) {
/* 389 */       switch (aux_cadena.charAt(posicion))
/*     */       {
/*     */       case '<':
/* 392 */         if (posicion < aux_cadena.length() - 5)
/*     */         {
/* 394 */           if ((aux_cadena.charAt(posicion) == '<') && (aux_cadena.charAt(posicion + 1) == 'i') && (aux_cadena.charAt(posicion + 2) == 'm') && (aux_cadena.charAt(posicion + 3) == 'g') && (aux_cadena.charAt(posicion + 4) >= '1') && (aux_cadena.charAt(posicion + 4) <= '2') && (aux_cadena.charAt(posicion + 5) == '>'))
/*     */           {
/* 396 */             longitud += this.img_size2;
/* 397 */             posicion += 5;
/*     */           }
/*     */           else {
/* 400 */             longitud += fmetrics.charWidth(aux_cadena.charAt(posicion));
/*     */           }
/*     */         }
/*     */         else {
/* 404 */           longitud += fmetrics.charWidth(aux_cadena.charAt(posicion));
/*     */         }
/* 406 */         break;
/*     */       case ':':
/*     */       case ';':
/* 410 */         if (posicion < aux_cadena.length() - 2)
/*     */         {
/* 412 */           if ((aux_cadena.charAt(posicion) == ':') && (aux_cadena.charAt(posicion + 1) == '-') && ((aux_cadena.charAt(posicion + 2) == '(') || (aux_cadena.charAt(posicion + 2) == ')') || (aux_cadena.charAt(posicion + 2) == 'O') || (aux_cadena.charAt(posicion + 2) == '|') || (aux_cadena.charAt(posicion + 2) == '*') || (aux_cadena.charAt(posicion + 2) == '>') || (aux_cadena.charAt(posicion + 2) == 'P')))
/*     */           {
/* 414 */             longitud += this.img_size;
/* 415 */             posicion += 2;
/*     */           }
/* 418 */           else if ((aux_cadena.charAt(posicion) == ';') && (aux_cadena.charAt(posicion + 1) == '-') && (aux_cadena.charAt(posicion + 2) == ')'))
/*     */           {
/* 420 */             longitud += this.img_size;
/* 421 */             posicion += 2;
/*     */           }
/*     */           else {
/* 424 */             longitud += fmetrics.charWidth(aux_cadena.charAt(posicion));
/*     */           }
/*     */         }
/*     */         else {
/* 428 */           longitud += fmetrics.charWidth(aux_cadena.charAt(posicion));
/*     */         }
/* 430 */         break;
/*     */       default:
/* 433 */         longitud += fmetrics.charWidth(aux_cadena.charAt(posicion));
/*     */       }
/*     */     }
/*     */ 
/* 437 */     return longitud;
/*     */   }
/*     */ 
/*     */   public String obten_elemento(int posicion)
/*     */   {
/* 442 */     String cadena = "";
/* 443 */     if (this.Cur_Row <= this.Page_Row_Size)
/*     */     {
/* 445 */       int elemento = posicion / this.altoFont;
/* 446 */       if (elemento <= this.v_tooltip.size())
/* 447 */         cadena = this.v_textocompleto.elementAt(elemento).toString();
/*     */     }
/*     */     else {
/* 450 */       int elemento = this.Cur_Row - this.Page_Row_Size + posicion / this.altoFont;
/* 451 */       if (elemento <= this.v_tooltip.size())
/* 452 */         cadena = this.v_textocompleto.elementAt(elemento).toString();
/*     */     }
/* 454 */     return cadena;
/*     */   }
/*     */ 
/*     */   public void set_value(String tooltip, String formato)
/*     */   {
/* 459 */     this.tooltip = tooltip;
/* 460 */     int ultimo_espacio = -1;
/* 461 */     int longitud = 0;
/* 462 */     int cuenta_espacios = 0;
/* 463 */     int posicion = 0;
/* 464 */     boolean hay_imagen = false;
/* 465 */     boolean auxModerador = false;
/* 466 */     String str = tooltip.trim();
/* 467 */     int Estilo = 0;
/* 468 */     String FontTexto = "Dialog";
/* 469 */     String txt_completo = tooltip.trim();
/* 470 */     String x_aux_formato = formato;
/* 471 */     Estilo = Integer.valueOf(x_aux_formato.substring(0, x_aux_formato.indexOf('-'))).intValue();
/* 472 */     x_aux_formato = x_aux_formato.substring(x_aux_formato.indexOf('-') + 1);
/* 473 */     FontTexto = x_aux_formato.substring(0, x_aux_formato.indexOf('-'));
/* 474 */     x_aux_formato = x_aux_formato.substring(x_aux_formato.indexOf('-') + 1);
/* 475 */     x_aux_formato = x_aux_formato.substring(x_aux_formato.indexOf('-') + 1);
/* 476 */     auxModerador = x_aux_formato.compareTo("1") == 0;
/* 477 */     FontMetrics fm_aux = getFontMetrics(new Font(FontTexto, Estilo, this.TamFont));
/* 478 */     if (ancho_cadena(str, fm_aux, auxModerador) > this.anchoReal)
/*     */     {
/* 480 */       while (ancho_cadena(str, fm_aux, auxModerador) > this.anchoReal)
/*     */       {
/* 482 */         posicion = 0;
/* 483 */         if (auxModerador)
/* 484 */           longitud = 10;
/*     */         else
/* 486 */           longitud = 0;
/* 487 */         cuenta_espacios = 0;
/* 488 */         hay_imagen = false;
/* 489 */         auxModerador = false;
/* 490 */         while (longitud <= this.anchoReal) {
/* 491 */           switch (str.charAt(posicion))
/*     */           {
/*     */           case '<':
/* 494 */             if (posicion < str.length() - 5)
/*     */             {
/* 496 */               if ((str.charAt(posicion) == '<') && (str.charAt(posicion + 1) == 'i') && (str.charAt(posicion + 2) == 'm') && (str.charAt(posicion + 3) == 'g') && (str.charAt(posicion + 3) == 'g') && (str.charAt(posicion + 4) >= '1') && (str.charAt(posicion + 4) <= '2') && (str.charAt(posicion + 5) == '>'))
/*     */               {
/* 498 */                 longitud += this.img_size2;
/* 499 */                 if (longitud > this.anchoReal)
/*     */                   continue;
/* 501 */                 posicion += 6;
/* 502 */                 hay_imagen = true;
/* 503 */                 ultimo_espacio = posicion - 1;
/*     */               }
/*     */               else
/*     */               {
/* 507 */                 longitud += fm_aux.charWidth(str.charAt(posicion));
/* 508 */                 posicion++;
/*     */               }
/*     */             }
/*     */             else {
/* 512 */               longitud += fm_aux.charWidth(str.charAt(posicion));
/* 513 */               posicion++;
/*     */             }
/* 515 */             break;
/*     */           case ':':
/*     */           case ';':
/* 519 */             if (posicion < str.length() - 2)
/*     */             {
/* 521 */               if ((str.charAt(posicion) == ':') && (str.charAt(posicion + 1) == '-') && ((str.charAt(posicion + 2) == '(') || (str.charAt(posicion + 2) == ')') || (str.charAt(posicion + 2) == 'O') || (str.charAt(posicion + 2) == '|') || (str.charAt(posicion + 2) == '*') || (str.charAt(posicion + 2) == '>') || (str.charAt(posicion + 2) == 'P')))
/*     */               {
/* 523 */                 longitud += this.img_size;
/* 524 */                 if (longitud > this.anchoReal)
/*     */                   continue;
/* 526 */                 posicion += 3; ultimo_espacio = posicion - 1;
/* 527 */                 cuenta_espacios++;
/*     */               }
/* 531 */               else if ((str.charAt(posicion) == ';') && (str.charAt(posicion + 1) == '-') && (str.charAt(posicion + 2) == ')'))
/*     */               {
/* 533 */                 longitud += this.img_size;
/* 534 */                 if (longitud > this.anchoReal)
/*     */                   continue;
/* 536 */                 posicion += 3; ultimo_espacio = posicion - 1;
/* 537 */                 cuenta_espacios++;
/*     */               }
/*     */               else
/*     */               {
/* 541 */                 longitud += fm_aux.charWidth(str.charAt(posicion));
/* 542 */                 posicion++;
/*     */               }
/*     */             }
/*     */             else {
/* 546 */               longitud += fm_aux.charWidth(str.charAt(posicion));
/* 547 */               posicion++;
/*     */             }
/* 549 */             break;
/*     */           case ' ':
/* 552 */             if (str.charAt(posicion) != ' ')
/*     */               continue;
/* 554 */             ultimo_espacio = posicion;
/* 555 */             longitud += fm_aux.charWidth(str.charAt(posicion));
/* 556 */             posicion++;
/* 557 */             cuenta_espacios++; break;
/*     */           default:
/* 562 */             longitud += fm_aux.charWidth(str.charAt(posicion));
/* 563 */             posicion++;
/*     */           }
/*     */         }
/* 566 */         if ((cuenta_espacios == 1) || (cuenta_espacios == 0))
/*     */         {
/* 568 */           this.SB_vert.setMaximum(this.v_tooltip.size() + 1);
/* 569 */           if ((this.Cur_Row == -1) || (this.Cur_Row == this.v_tooltip.size()))
/*     */           {
/* 571 */             this.Cur_Row = (this.v_tooltip.size() + 1);
/* 572 */             this.SB_vert.setValue(this.SB_vert.getMaximum());
/*     */           }
/* 574 */           this.v_tooltip.addElement(str.substring(0, posicion));
/* 575 */           str = str.substring(posicion);
/* 576 */           this.v_textocompleto.addElement(txt_completo);
/* 577 */           this.v_formato.addElement(formato);
/* 578 */           if (this.v_tooltip.size() == this.Page_Row_Size)
/* 579 */             add(this.SB_vert);
/*     */         }
/*     */         else {
/* 582 */           this.SB_vert.setMaximum(this.v_tooltip.size() + 1);
/* 583 */           if ((this.Cur_Row == -1) || (this.Cur_Row == this.v_tooltip.size()))
/*     */           {
/* 585 */             this.Cur_Row = (this.v_tooltip.size() + 1);
/* 586 */             this.SB_vert.setValue(this.SB_vert.getMaximum());
/*     */           }
/* 588 */           this.v_tooltip.addElement(str.substring(0, ultimo_espacio + 1));
/* 589 */           str = str.substring(ultimo_espacio + 1);
/* 590 */           this.v_textocompleto.addElement(txt_completo);
/* 591 */           this.v_formato.addElement(formato);
/* 592 */           if (this.v_tooltip.size() == this.Page_Row_Size)
/* 593 */             add(this.SB_vert);
/*     */         }
/* 595 */         if (!hay_imagen)
/*     */           continue;
/* 597 */         this.SB_vert.setMaximum(this.v_tooltip.size() + 1);
/* 598 */         if ((this.Cur_Row == -1) || (this.Cur_Row == this.v_tooltip.size()))
/*     */         {
/* 600 */           this.Cur_Row = (this.v_tooltip.size() + 1);
/* 601 */           this.SB_vert.setValue(this.SB_vert.getMaximum());
/*     */         }
/* 603 */         this.v_tooltip.addElement("");
/* 604 */         this.v_textocompleto.addElement(txt_completo);
/* 605 */         this.v_formato.addElement(formato);
/* 606 */         if (this.v_tooltip.size() == this.Page_Row_Size) {
/* 607 */           add(this.SB_vert);
/*     */         }
/*     */       }
/* 610 */       this.SB_vert.setMaximum(this.v_tooltip.size() + 1);
/* 611 */       if ((this.Cur_Row == -1) || (this.Cur_Row == this.v_tooltip.size()))
/*     */       {
/* 613 */         this.Cur_Row = (this.v_tooltip.size() + 1);
/* 614 */         this.SB_vert.setValue(this.SB_vert.getMaximum());
/*     */       }
/* 616 */       this.v_tooltip.addElement(str);
/* 617 */       this.v_textocompleto.addElement(txt_completo);
/* 618 */       this.v_formato.addElement(formato);
/* 619 */       if (this.v_tooltip.size() == this.Page_Row_Size)
/* 620 */         add(this.SB_vert);
/* 621 */       if (hay_imagen)
/*     */       {
/* 623 */         this.SB_vert.setMaximum(this.v_tooltip.size() + 1);
/* 624 */         if ((this.Cur_Row == -1) || (this.Cur_Row == this.v_tooltip.size()))
/*     */         {
/* 626 */           this.Cur_Row = (this.v_tooltip.size() + 1);
/* 627 */           this.SB_vert.setValue(this.SB_vert.getMaximum());
/*     */         }
/* 629 */         this.v_tooltip.addElement("");
/* 630 */         this.v_textocompleto.addElement(txt_completo);
/* 631 */         this.v_formato.addElement(formato);
/* 632 */         if (this.v_tooltip.size() == this.Page_Row_Size)
/* 633 */           add(this.SB_vert);
/*     */       }
/*     */     }
/*     */     else {
/* 637 */       hay_imagen = false;
/* 638 */       while (posicion < str.length()) {
/* 639 */         if (posicion < str.length() - 5)
/*     */         {
/* 641 */           if ((str.charAt(posicion) == '<') && (str.charAt(posicion + 1) == 'i') && (str.charAt(posicion + 2) == 'm') && (str.charAt(posicion + 3) == 'g') && (str.charAt(posicion + 3) == 'g') && (str.charAt(posicion + 4) >= '1') && (str.charAt(posicion + 4) <= '2') && (str.charAt(posicion + 5) == '>'))
/*     */           {
/* 643 */             hay_imagen = true;
/* 644 */             posicion = str.length();
/*     */           }
/*     */           else {
/* 647 */             posicion++;
/*     */           }
/*     */         }
/*     */         else
/* 651 */           posicion++;
/*     */       }
/* 653 */       this.SB_vert.setMaximum(this.v_tooltip.size() + 1);
/* 654 */       if ((this.Cur_Row == -1) || (this.Cur_Row == this.v_tooltip.size()))
/*     */       {
/* 656 */         this.Cur_Row = (this.v_tooltip.size() + 1);
/* 657 */         this.SB_vert.setValue(this.SB_vert.getMaximum());
/*     */       }
/* 659 */       this.v_tooltip.addElement(str);
/* 660 */       this.v_textocompleto.addElement(txt_completo);
/* 661 */       this.v_formato.addElement(formato);
/* 662 */       if (this.v_tooltip.size() == this.Page_Row_Size)
/* 663 */         add(this.SB_vert);
/* 664 */       if (hay_imagen)
/*     */       {
/* 666 */         this.SB_vert.setMaximum(this.v_tooltip.size() + 1);
/* 667 */         if ((this.Cur_Row == -1) || (this.Cur_Row == this.v_tooltip.size()))
/*     */         {
/* 669 */           this.Cur_Row = (this.v_tooltip.size() + 1);
/* 670 */           this.SB_vert.setValue(this.SB_vert.getMaximum());
/*     */         }
/* 672 */         this.v_tooltip.addElement("");
/* 673 */         this.v_textocompleto.addElement(txt_completo);
/* 674 */         this.v_formato.addElement(formato);
/* 675 */         if (this.v_tooltip.size() == this.Page_Row_Size)
/* 676 */           add(this.SB_vert);
/*     */       }
/*     */     }
/* 679 */     dibuja();
/*     */   }
/*     */ 
/*     */   void dibuja()
/*     */   {
/* 684 */     Graphics g = getGraphics();
/* 685 */     Image auxImgDb = createImage(getSize().width, getSize().height);
/* 686 */     Graphics g2 = auxImgDb.getGraphics();
/* 687 */     if (this.Cur_Row > this.Page_Row_Size)
/*     */     {
/* 689 */       for (int j = this.Cur_Row - this.Page_Row_Size; j < this.Cur_Row; j++) {
/* 690 */         if (j >= 0)
/* 691 */           if (formato_texto(g2, j))
/*     */           {
/* 693 */             g2.drawImage(this.imagen, 0, (j - this.Cur_Row + this.Page_Row_Size) * this.altoFont + 3, this);
/* 694 */             String aux_str = (String)this.v_tooltip.elementAt(j);
/* 695 */             drawString_Alt(g2, 10, (j - this.Cur_Row + this.Page_Row_Size) * this.altoFont + this.altoFont, aux_str);
/*     */           }
/*     */           else {
/* 698 */             String aux_str = (String)this.v_tooltip.elementAt(j);
/* 699 */             drawString_Alt(g2, 2, (j - this.Cur_Row + this.Page_Row_Size) * this.altoFont + this.altoFont, aux_str);
/*     */           }
/*     */       }
/*     */     }
/* 703 */     else if (this.v_tooltip.size() > this.Page_Row_Size)
/*     */     {
/* 705 */       for (int j = 0; j < this.Page_Row_Size; j++) {
/* 706 */         if (formato_texto(g2, j))
/*     */         {
/* 708 */           g2.drawImage(this.imagen, 0, j * this.altoFont + 3, this);
/* 709 */           String aux_str = (String)this.v_tooltip.elementAt(j);
/* 710 */           drawString_Alt(g2, 10, j * this.altoFont + this.altoFont, aux_str);
/*     */         }
/*     */         else {
/* 713 */           String aux_str = (String)this.v_tooltip.elementAt(j);
/* 714 */           drawString_Alt(g2, 2, j * this.altoFont + this.altoFont, aux_str);
/*     */         }
/*     */       }
/*     */     }
/*     */     else {
/* 719 */       for (int j = 0; j < this.v_tooltip.size(); j++) {
/* 720 */         if (formato_texto(g2, j))
/*     */         {
/* 722 */           g2.drawImage(this.imagen, 0, j * this.altoFont + 3, this);
/* 723 */           String aux_str = (String)this.v_tooltip.elementAt(j);
/* 724 */           drawString_Alt(g2, 10, j * this.altoFont + this.altoFont, aux_str);
/*     */         }
/*     */         else {
/* 727 */           String aux_str = (String)this.v_tooltip.elementAt(j);
/* 728 */           drawString_Alt(g2, 2, j * this.altoFont + this.altoFont, aux_str);
/*     */         }
/*     */       }
/*     */     }
/* 732 */     g2.setColor(Color.darkGray);
/* 733 */     g2.drawLine(0, getSize().height, 0, 0);
/* 734 */     g2.drawLine(getSize().width, 0, 0, 0);
/* 735 */     g2.drawLine(1, getSize().height, 1, 1);
/* 736 */     g2.drawLine(getSize().width, 1, 1, 1);
/* 737 */     g2.setColor(Color.lightGray);
/* 738 */     g2.drawLine(0, getSize().height - 35, getSize().width, getSize().height - 35);
/* 739 */     g2.drawLine(getSize().width, getSize().height - 35, getSize().width, 0);
/* 740 */     g2.setColor(Color.gray);
/* 741 */     g2.drawLine(0, getSize().height - 36, getSize().width - 1, getSize().height - 36);
/* 742 */     g2.drawLine(getSize().width - 1, getSize().height - 36, getSize().width - 1, 0);
/* 743 */     g2.setColor(Color.black);
/* 744 */     g2.fillRect(0, getSize().height - 36 + 1, getSize().width, 50);
/* 745 */     g.drawImage(auxImgDb, 0, 0, this);
/*     */   }
/*     */ 
/*     */   public boolean keyDown(Event evt, int key)
/*     */   {
/* 750 */     if ((evt.target == this.TX_chat) && (key == 10) && (this.TX_chat.getText() != ""))
/*     */     {
/* 752 */       if (this.TX_chat.getText().length() > 1999)
/* 753 */         this.comp_padre.chat(this.TX_chat.getText().substring(0, 1999));
/*     */       else
/* 755 */         this.comp_padre.chat(this.TX_chat.getText().trim());
/* 756 */       this.TX_chat.setText("");
/* 757 */       return true;
/*     */     }
/*     */ 
/* 760 */     return false;
/*     */   }
/*     */ 
/*     */   class MouseHandler
/*     */     implements MouseListener
/*     */   {
/*     */     public void mouseClicked(MouseEvent e)
/*     */     {
/*  97 */       if ((e.getSource().equals(class_PanelChat.this)) && (e.getClickCount() == 2) && ((e.getModifiers() & 0x10) == 16))
/*     */       {
/*  99 */         String elemento = "";
/* 100 */         elemento = class_PanelChat.this.obten_elemento(e.getY());
/* 101 */         if (elemento.compareTo("") != 0)
/* 102 */           class_PanelChat.this.comp_padre.guarda_pregunta(elemento);
/*     */       }
/*     */     }
/*     */ 
/*     */     public void mousePressed(MouseEvent mouseevent)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void mouseReleased(MouseEvent mouseevent)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void mouseEntered(MouseEvent mouseevent)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void mouseExited(MouseEvent mouseevent)
/*     */     {
/*     */     }
/*     */ 
/*     */     MouseHandler()
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public class AdjustmentHandler
/*     */     implements AdjustmentListener
/*     */   {
/*     */     public void adjustmentValueChanged(AdjustmentEvent e)
/*     */     {
/*  41 */       if (e.getAdjustmentType() == 2)
/*     */       {
/*  43 */         if (class_PanelChat.this.Cur_Row > 0)
/*  44 */           class_PanelChat.this.Cur_Row -= 1;
/*  45 */         if ((class_PanelChat.this.SB_vert.getValue() == class_PanelChat.this.SB_vert.getMinimum()) || (class_PanelChat.this.SB_vert.getValue() == class_PanelChat.this.Page_Row_Size))
/*     */         {
/*  47 */           class_PanelChat.this.SB_vert.setValue(0);
/*  48 */           class_PanelChat.this.Cur_Row = 0;
/*     */         }
/*     */       }
/*  51 */       else if (e.getAdjustmentType() == 3)
/*     */       {
/*  53 */         if (class_PanelChat.this.Cur_Row > 0)
/*  54 */           class_PanelChat.this.Cur_Row -= class_PanelChat.this.SB_vert.getBlockIncrement();
/*  55 */         if (class_PanelChat.this.SB_vert.getValue() == class_PanelChat.this.SB_vert.getMinimum())
/*  56 */           class_PanelChat.this.Cur_Row = 0;
/*  57 */         if (class_PanelChat.this.Cur_Row < 0)
/*  58 */           class_PanelChat.this.Cur_Row = 0;
/*     */       }
/*  60 */       else if (e.getAdjustmentType() == 1)
/*     */       {
/*  62 */         if (class_PanelChat.this.Cur_Row < class_PanelChat.this.v_tooltip.size())
/*  63 */           class_PanelChat.this.Cur_Row += 1;
/*  64 */         if (class_PanelChat.this.SB_vert.getValue() == class_PanelChat.this.SB_vert.getMaximum())
/*  65 */           class_PanelChat.this.Cur_Row = class_PanelChat.this.SB_vert.getMaximum();
/*  66 */         if (class_PanelChat.this.Cur_Row < class_PanelChat.this.Page_Row_Size)
/*     */         {
/*  68 */           class_PanelChat.this.Cur_Row = (class_PanelChat.this.Page_Row_Size + 1);
/*  69 */           class_PanelChat.this.SB_vert.setValue(class_PanelChat.this.Page_Row_Size + 1);
/*     */         }
/*     */       }
/*  72 */       else if (e.getAdjustmentType() == 4)
/*     */       {
/*  74 */         if (class_PanelChat.this.Cur_Row >= 0)
/*  75 */           class_PanelChat.this.Cur_Row += class_PanelChat.this.SB_vert.getBlockIncrement();
/*  76 */         if (class_PanelChat.this.SB_vert.getValue() == class_PanelChat.this.SB_vert.getMaximum())
/*  77 */           class_PanelChat.this.Cur_Row = class_PanelChat.this.SB_vert.getMaximum();
/*  78 */         if (class_PanelChat.this.Cur_Row > class_PanelChat.this.v_tooltip.size())
/*  79 */           class_PanelChat.this.Cur_Row = class_PanelChat.this.v_tooltip.size();
/*     */       }
/*  81 */       else if (e.getAdjustmentType() == 5) {
/*  82 */         class_PanelChat.this.Cur_Row = class_PanelChat.this.SB_vert.getValue();
/*  83 */       }class_PanelChat.this.dibuja();
/*     */     }
/*     */ 
/*     */     public AdjustmentHandler()
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   class ActionHandler
/*     */     implements ActionListener
/*     */   {
/*     */     public void actionPerformed(ActionEvent e)
/*     */     {
/*  20 */       if ((e.getSource().equals(class_PanelChat.this.BtnEnviar)) && (class_PanelChat.this.TX_chat.getText() != ""))
/*     */       {
/*  22 */         if (class_PanelChat.this.TX_chat.getText().length() > 1999)
/*  23 */           class_PanelChat.this.comp_padre.chat(class_PanelChat.this.TX_chat.getText().substring(0, 1999));
/*     */         else
/*  25 */           class_PanelChat.this.comp_padre.chat(class_PanelChat.this.TX_chat.getText().trim());
/*  26 */         class_PanelChat.this.TX_chat.setText("");
/*     */       }
/*     */     }
/*     */ 
/*     */     ActionHandler()
/*     */     {
/*     */     }
/*     */   }
/*     */ }

/* Location:           /programming/proys/SWB4/swb/applets/AppletChat/src/
 * Qualified Name:     class_PanelChat
 * JD-Core Version:    0.6.0
 */