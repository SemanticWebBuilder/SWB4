/*     */ import java.awt.Button;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Frame;
/*     */ import java.awt.Label;
/*     */ import java.awt.List;
/*     */ import java.awt.TextField;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.KeyListener;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class class_FrameModerador extends Frame
/*     */ {
/*     */   List LstPreguntas;
/*     */   List LstRespuestas;
/*     */   Label lblPreguntas;
/*     */   Label lblRespuestas;
/*     */   Label lblPreguntaSel;
/*     */   TextField txtRespuesta;
/*     */   Button BtnAceptar;
/*     */   Button BtnCancelar;
/*     */   class_AppletChat AppletChat;
/*     */   Vector vPregunta;
/*     */   Vector vUsuario;
/*     */   Vector vRespuesta;
/*     */   int ItemSeleccionado;
/*     */ 
/*     */   public void LimpiaControles()
/*     */   {
/* 151 */     this.LstPreguntas.removeAll();
/* 152 */     this.LstRespuestas.removeAll();
/* 153 */     this.lblPreguntaSel.setText("");
/* 154 */     this.txtRespuesta.setText("");
/* 155 */     this.vRespuesta.removeAllElements();
/*     */   }
/*     */ 
/*     */   public void Agrega_Pregunta(String x_aux_dato)
/*     */   {
/* 160 */     String AuxPregunta = "";
/* 161 */     String Mensaje = "";
/* 162 */     String AuxUser = "";
/* 163 */     AuxPregunta = x_aux_dato.substring(0, x_aux_dato.indexOf('|'));
/* 164 */     x_aux_dato = x_aux_dato.substring(x_aux_dato.indexOf('|') + 1);
/* 165 */     Mensaje = x_aux_dato.substring(0, x_aux_dato.indexOf('|'));
/* 166 */     x_aux_dato = x_aux_dato.substring(x_aux_dato.indexOf('|') + 1);
/* 167 */     AuxUser = x_aux_dato;
/* 168 */     this.vPregunta.addElement(AuxPregunta);
/* 169 */     this.vUsuario.addElement(AuxUser);
/* 170 */     this.LstPreguntas.addItem(Mensaje);
/*     */   }
/*     */ 
/*     */   public void Llena_Lst_Respuesta(String Item)
/*     */   {
/* 175 */     String Aux_Pregunta = "";
/* 176 */     String Aux_Msg = "";
/* 177 */     this.LstRespuestas.removeAll();
/* 178 */     for (int j = 0; j < this.vRespuesta.size(); j++)
/*     */     {
/* 180 */       Aux_Msg = this.vRespuesta.elementAt(j).toString();
/* 181 */       Aux_Pregunta = Aux_Msg.substring(0, Aux_Msg.indexOf('|'));
/* 182 */       if (Aux_Pregunta.compareTo(Item) != 0)
/*     */         continue;
/* 184 */       Aux_Msg = Aux_Msg.substring(Aux_Msg.indexOf('|') + 1);
/* 185 */       this.LstRespuestas.addItem(Aux_Msg);
/*     */     }
/*     */   }
/*     */ 
/*     */   class_FrameModerador(class_AppletChat AppletChat)
/*     */   {
/* 193 */     this.LstPreguntas = new List();
/* 194 */     this.LstRespuestas = new List();
/* 195 */     this.lblPreguntas = new Label("Preguntas Seleccionadas");
/* 196 */     this.lblRespuestas = new Label("Respuestas");
/* 197 */     this.lblPreguntaSel = new Label();
/* 198 */     this.txtRespuesta = new TextField();
/* 199 */     this.BtnAceptar = new Button("Enviar Respuesta");
/* 200 */     this.BtnCancelar = new Button("Cancelar");
/* 201 */     this.AppletChat = null;
/* 202 */     this.vPregunta = new Vector();
/* 203 */     this.vUsuario = new Vector();
/* 204 */     this.vRespuesta = new Vector();
/* 205 */     this.ItemSeleccionado = 0;
/* 206 */     this.AppletChat = AppletChat;
/* 207 */     setBounds(20, 20, 380, 380);
/* 208 */     setLayout(null);
/* 209 */     setBackground(Color.lightGray);
/* 210 */     setTitle("Preguntas Seleccionadas");
/* 211 */     setResizable(false);
/* 212 */     this.lblPreguntas.setBounds(10, 20, 150, 20);
/* 213 */     add(this.lblPreguntas);
/* 214 */     this.LstPreguntas.setBounds(10, 40, 340, 100);
/* 215 */     add(this.LstPreguntas);
/* 216 */     this.lblRespuestas.setBounds(10, 140, 150, 20);
/* 217 */     add(this.lblRespuestas);
/* 218 */     this.LstRespuestas.setBounds(10, 160, 340, 100);
/* 219 */     add(this.LstRespuestas);
/* 220 */     this.lblPreguntaSel.setFont(new Font("Dialog", 1, 10));
/* 221 */     this.lblPreguntaSel.setBounds(10, 260, 340, 20);
/* 222 */     add(this.lblPreguntaSel);
/* 223 */     this.txtRespuesta.setBounds(10, 280, 340, 40);
/* 224 */     add(this.txtRespuesta);
/* 225 */     this.BtnAceptar.setEnabled(false);
/* 226 */     this.BtnAceptar.setBounds(70, 335, 100, 20);
/* 227 */     add(this.BtnAceptar);
/* 228 */     this.BtnCancelar.setBounds(200, 335, 80, 20);
/* 229 */     add(this.BtnCancelar);
/* 230 */     this.LstPreguntas.addMouseListener(new class_FrameModerador.MouseHandler());
/* 231 */     this.LstRespuestas.addMouseListener(new class_FrameModerador.MouseHandler());
/* 232 */     this.txtRespuesta.addKeyListener(new class_FrameModerador.KeyHandler());
/* 233 */     this.BtnAceptar.addActionListener(new class_FrameModerador.ActionHandler());
/* 234 */     this.BtnCancelar.addActionListener(new class_FrameModerador.ActionHandler());
/* 235 */     addWindowListener(new class_FrameModerador.WindowHandler());
/*     */   }
/*     */ 
/*     */   public void Agrega_Respuesta(String x_aux_dato)
/*     */   {
/* 240 */     this.vRespuesta.addElement(x_aux_dato);
/*     */   }
/*     */ 
/*     */   class WindowHandler extends WindowAdapter
/*     */   {
/*     */     public void windowClosing(WindowEvent e)
/*     */     {
/* 140 */       class_FrameModerador.this.setVisible(false);
/*     */     }
/*     */ 
/*     */     WindowHandler()
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   class ActionHandler
/*     */     implements ActionListener
/*     */   {
/*     */     public void actionPerformed(ActionEvent e)
/*     */     {
/* 115 */       if (e.getSource().equals(class_FrameModerador.this.BtnAceptar))
/*     */       {
/* 117 */         class_FrameModerador.this.setVisible(false);
/* 118 */         class_FrameModerador.this.BtnAceptar.setEnabled(false);
/* 119 */         class_FrameModerador.this.AppletChat.ack_tx_respuesta(class_FrameModerador.this.vUsuario.elementAt(class_FrameModerador.this.ItemSeleccionado).toString(), class_FrameModerador.this.vPregunta.elementAt(class_FrameModerador.this.ItemSeleccionado).toString(), class_FrameModerador.this.txtRespuesta.getText());
/* 120 */         class_FrameModerador.this.vRespuesta.removeAllElements();
/* 121 */         return;
/*     */       }
/* 123 */       if (e.getSource().equals(class_FrameModerador.this.BtnCancelar))
/*     */       {
/* 125 */         class_FrameModerador.this.setVisible(false);
/* 126 */         class_FrameModerador.this.vRespuesta.removeAllElements();
/*     */       }
/*     */     }
/*     */ 
/*     */     ActionHandler()
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   class KeyHandler
/*     */     implements KeyListener
/*     */   {
/*     */     public void keyTyped(KeyEvent keyevent)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void keyPressed(KeyEvent keyevent)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void keyReleased(KeyEvent e)
/*     */     {
/*  93 */       if ((class_FrameModerador.this.lblPreguntaSel.getText().compareTo("") == 0) || (class_FrameModerador.this.txtRespuesta.getText().compareTo("") == 0))
/*     */       {
/*  95 */         class_FrameModerador.this.BtnAceptar.setEnabled(false);
/*  96 */         return;
/*     */       }
/*     */ 
/*  99 */       class_FrameModerador.this.BtnAceptar.setEnabled(true);
/*     */     }
/*     */ 
/*     */     KeyHandler()
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   class MouseHandler
/*     */     implements MouseListener
/*     */   {
/*     */     public void mouseClicked(MouseEvent e)
/*     */     {
/*  35 */       if ((class_FrameModerador.this.LstPreguntas.getSelectedItem() != null) && (e.getSource().equals(class_FrameModerador.this.LstPreguntas)) && ((e.getModifiers() & 0x10) == 16) && (e.getClickCount() == 1))
/*     */       {
/*  37 */         class_FrameModerador.this.lblPreguntaSel.setText("Respuesta a la pregunta: " + class_FrameModerador.this.LstPreguntas.getSelectedItem());
/*  38 */         class_FrameModerador.this.ItemSeleccionado = class_FrameModerador.this.LstPreguntas.getSelectedIndex();
/*  39 */         if ((class_FrameModerador.this.lblPreguntaSel.getText().compareTo("") == 0) || (class_FrameModerador.this.txtRespuesta.getText().compareTo("") == 0))
/*  40 */           class_FrameModerador.this.BtnAceptar.setEnabled(false);
/*     */         else
/*  42 */           class_FrameModerador.this.BtnAceptar.setEnabled(true);
/*  43 */         class_FrameModerador.this.Llena_Lst_Respuesta(class_FrameModerador.this.vPregunta.elementAt(class_FrameModerador.this.ItemSeleccionado).toString());
/*  44 */         return;
/*     */       }
/*  46 */       if ((class_FrameModerador.this.LstRespuestas.getSelectedItem() != null) && (e.getSource().equals(class_FrameModerador.this.LstRespuestas)) && ((e.getModifiers() & 0x10) == 16) && (e.getClickCount() == 2))
/*     */       {
/*  48 */         class_FrameModerador.this.txtRespuesta.setText(class_FrameModerador.this.LstRespuestas.getSelectedItem());
/*  49 */         if ((class_FrameModerador.this.lblPreguntaSel.getText().compareTo("") == 0) || (class_FrameModerador.this.txtRespuesta.getText().compareTo("") == 0))
/*     */         {
/*  51 */           class_FrameModerador.this.BtnAceptar.setEnabled(false);
/*  52 */           return;
/*     */         }
/*  54 */         class_FrameModerador.this.BtnAceptar.setEnabled(true);
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
/*     */ }

/* Location:           /programming/proys/SWB4/swb/applets/AppletChat/src/
 * Qualified Name:     class_FrameModerador
 * JD-Core Version:    0.6.0
 */