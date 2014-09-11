/*     */ import java.awt.Button;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Frame;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.TextField;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class class_FrameMsgBox extends Frame
/*     */ {
/*     */   int tipo_msg;
/*     */   int Tipo_Ventana;
/* 146 */   String[] mensaje = { "" };
/*     */   Font Fuente;
/*     */   class_AppletChat AppletChat;
/*     */   Button BT_ok;
/*     */   Button BT_Cancel;
/*     */   TextField TxtMsgPriv;
/*     */ 
/*     */   public class_FrameMsgBox(class_AppletChat AppletChat, int tipo_msg, String titulo, String aux_mensaje, String SBoton1, String SBoton2, int Tipo_Ventana)
/*     */   {
/*  80 */     this.tipo_msg = 0;
/*  81 */     this.Tipo_Ventana = 0;
/*  82 */     this.Fuente = new Font("Arial", 0, 12);
/*  83 */     this.AppletChat = null;
/*  84 */     this.BT_ok = new Button();
/*  85 */     this.BT_Cancel = new Button();
/*  86 */     this.TxtMsgPriv = new TextField();
/*  87 */     this.AppletChat = AppletChat;
/*  88 */     setTitle(titulo);
/*  89 */     Vector aux_vector = new Vector();
/*  90 */     this.BT_ok.setLabel(SBoton1);
/*  91 */     this.BT_Cancel.setLabel(SBoton2);
/*  92 */     for (; aux_mensaje.indexOf("|") >= 0; aux_mensaje = aux_mensaje.substring(aux_mensaje.indexOf("|") + 1)) {
/*  93 */       aux_vector.addElement(aux_mensaje.substring(0, aux_mensaje.indexOf("|")));
/*     */     }
/*  95 */     aux_vector.addElement(aux_mensaje);
/*  96 */     this.mensaje = new String[aux_vector.size()];
/*  97 */     for (int j = 0; j < aux_vector.size(); j++)
/*     */     {
/*  99 */       String str_aux = (String)aux_vector.elementAt(j);
/* 100 */       this.mensaje[j] = str_aux;
/*     */     }
/*     */ 
/* 103 */     this.tipo_msg = tipo_msg;
/* 104 */     this.Tipo_Ventana = Tipo_Ventana;
/* 105 */     setLayout(null);
/* 106 */     setBackground(Color.lightGray);
/* 107 */     addWindowListener(new class_FrameMsgBox.WindowHandler());
/* 108 */     FontMetrics fm_aux = getFontMetrics(this.Fuente);
/* 109 */     int aux_max_len = 0;
/* 110 */     for (int j = 0; j < this.mensaje.length; j++) {
/* 111 */       if (fm_aux.stringWidth(this.mensaje[j]) > aux_max_len)
/* 112 */         aux_max_len = fm_aux.stringWidth(this.mensaje[j]);
/*     */     }
/* 114 */     if (aux_max_len > 600) {
/* 115 */       setSize(600, 100 + this.mensaje.length * 16);
/*     */     }
/* 117 */     else if (aux_max_len < 120)
/* 118 */       setSize(120, 100 + this.mensaje.length * 16);
/*     */     else
/* 120 */       setSize(aux_max_len + 60, 100 + this.mensaje.length * 16);
/* 121 */     setResizable(false);
/* 122 */     if (Tipo_Ventana == 0)
/*     */     {
/* 124 */       this.TxtMsgPriv.setBounds((getSize().width - 250) / 2, (getSize().height - 20) / 2, 250, 20);
/* 125 */       this.TxtMsgPriv.setBackground(Color.white);
/*     */     }
/* 127 */     add(this.TxtMsgPriv);
/* 128 */     this.BT_ok.setBounds((getSize().width - 100) / 3, getSize().height - 43, 50, 20);
/* 129 */     add(this.BT_ok);
/* 130 */     this.BT_ok.addActionListener(new class_FrameMsgBox.ActionHandler());
/* 131 */     this.BT_Cancel.setBounds((getSize().width - 100) / 3 * 2 + 50, getSize().height - 43, 50, 20);
/* 132 */     add(this.BT_Cancel);
/* 133 */     this.BT_Cancel.addActionListener(new class_FrameMsgBox.ActionHandler());
/*     */   }
/*     */ 
/*     */   public void paint(Graphics g)
/*     */   {
/* 138 */     g.setFont(this.Fuente);
/* 139 */     for (int j = 0; j < this.mensaje.length; j++)
/* 140 */       g.drawString(this.mensaje[j], 10, 40 + (j * this.Fuente.getSize() + 3));
/*     */   }
/*     */ 
/*     */   class ActionHandler
/*     */     implements ActionListener
/*     */   {
/*     */     public void actionPerformed(ActionEvent e)
/*     */     {
/*  33 */       if (e.getSource().equals(class_FrameMsgBox.this.BT_ok))
/*     */       {
/*  35 */         class_FrameMsgBox.this.AppletChat.modalWindow = false;
/*  36 */         switch (class_FrameMsgBox.this.Tipo_Ventana)
/*     */         {
/*     */         default:
/*  39 */           break;
/*     */         case 0:
/*  42 */           if (class_FrameMsgBox.this.TxtMsgPriv.getText().trim().equalsIgnoreCase("")) break;
/*  43 */           class_FrameMsgBox.this.AppletChat.ack_tx_msg_priv(class_FrameMsgBox.this.TxtMsgPriv.getText().trim()); break;
/*     */         case 1:
/*  47 */           class_FrameMsgBox.this.AppletChat.ignorar_usuario();
/*  48 */           break;
/*     */         case 2:
/*  51 */           class_FrameMsgBox.this.AppletChat.ack_tx_expulsar();
/*  52 */           break;
/*     */         case 3:
/*  55 */           class_FrameMsgBox.this.AppletChat.rehabilita_usuario();
/*  56 */           break;
/*     */         case 4:
/*  59 */           class_FrameMsgBox.this.AppletChat.ack_tx_pregunta(class_FrameMsgBox.this.mensaje[0]);
/*     */         }
/*     */ 
/*  62 */         class_FrameMsgBox.this.dispose();
/*  63 */         return;
/*     */       }
/*  65 */       if (e.getSource().equals(class_FrameMsgBox.this.BT_Cancel))
/*     */       {
/*  67 */         class_FrameMsgBox.this.AppletChat.modalWindow = false;
/*  68 */         class_FrameMsgBox.this.dispose();
/*     */       }
/*     */     }
/*     */ 
/*     */     ActionHandler()
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   class WindowHandler extends WindowAdapter
/*     */   {
/*     */     public void windowClosing(WindowEvent e)
/*     */     {
/*  18 */       class_FrameMsgBox.this.AppletChat.modalWindow = false;
/*  19 */       class_FrameMsgBox.this.dispose();
/*     */     }
/*     */ 
/*     */     WindowHandler()
/*     */     {
/*     */     }
/*     */   }
/*     */ }

/* Location:           /programming/proys/SWB4/swb/applets/AppletChat/src/
 * Qualified Name:     class_FrameMsgBox
 * JD-Core Version:    0.6.0
 */