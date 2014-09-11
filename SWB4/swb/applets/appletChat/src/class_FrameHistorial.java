/*    */ import java.awt.Button;
/*    */ import java.awt.Color;
/*    */ import java.awt.Font;
/*    */ import java.awt.Frame;
/*    */ import java.awt.Label;
/*    */ import java.awt.List;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import java.awt.event.WindowAdapter;
/*    */ import java.awt.event.WindowEvent;
/*    */ 
/*    */ public class class_FrameHistorial extends Frame
/*    */ {
/*    */   List LstHistorial;
/*    */   Label lblTitulo;
/*    */   Button BtnOK;
/*    */   class_AppletChat AppletChat;
/*    */ 
/*    */   public void Limpia_Controles()
/*    */   {
/* 57 */     this.LstHistorial.removeAll();
/*    */   }
/*    */ 
/*    */   class_FrameHistorial(class_AppletChat AppletChat)
/*    */   {
/* 62 */     this.LstHistorial = new List();
/* 63 */     this.lblTitulo = new Label("Mensajes Registrados");
/* 64 */     this.BtnOK = new Button("Regresar");
/* 65 */     this.AppletChat = null;
/* 66 */     this.AppletChat = AppletChat;
/* 67 */     setBounds(50, 50, 380, 380);
/* 68 */     setLayout(null);
/* 69 */     setBackground(Color.lightGray);
/* 70 */     setTitle("Historial del Chat");
/* 71 */     setResizable(false);
/* 72 */     this.lblTitulo.setBounds(10, 25, 300, 15);
/* 73 */     this.lblTitulo.setFont(new Font("Dialog", 1, 10));
/* 74 */     add(this.lblTitulo);
/* 75 */     this.LstHistorial.setBounds(10, 40, 360, 290);
/* 76 */     add(this.LstHistorial);
/* 77 */     this.BtnOK.setBounds(140, 335, 100, 20);
/* 78 */     add(this.BtnOK);
/* 79 */     this.BtnOK.addActionListener(new class_FrameHistorial.ActionHandler());
/* 80 */     addWindowListener(new class_FrameHistorial.WindowHandler());
/*    */   }
/*    */ 
/*    */   public void Agrega_Elemento(String x_aux_dato)
/*    */   {
/* 85 */     String usuario = "";
/* 86 */     String mensaje = "";
/* 87 */     String hora = "";
/* 88 */     usuario = x_aux_dato.substring(0, x_aux_dato.indexOf('|'));
/* 89 */     x_aux_dato = x_aux_dato.substring(x_aux_dato.indexOf('|') + 1);
/* 90 */     mensaje = x_aux_dato.substring(0, x_aux_dato.indexOf('|'));
/* 91 */     x_aux_dato = x_aux_dato.substring(x_aux_dato.indexOf('|') + 1);
/* 92 */     hora = x_aux_dato;
/* 93 */     this.LstHistorial.addItem("El usuario " + usuario.trim() + " dijo " + mensaje.trim() + ", fecha y hora: " + hora.trim());
/*    */   }
/*    */ 
/*    */   class WindowHandler extends WindowAdapter
/*    */   {
/*    */     public void windowClosing(WindowEvent e)
/*    */     {
/* 45 */       class_FrameHistorial.this.AppletChat.BtnHistorial.setEnabled(true);
/* 46 */       class_FrameHistorial.this.setVisible(false);
/*    */     }
/*    */ 
/*    */     WindowHandler()
/*    */     {
/*    */     }
/*    */   }
/*    */ 
/*    */   class ActionHandler
/*    */     implements ActionListener
/*    */   {
/*    */     public void actionPerformed(ActionEvent e)
/*    */     {
/* 28 */       if (e.getSource().equals(class_FrameHistorial.this.BtnOK))
/*    */       {
/* 30 */         class_FrameHistorial.this.AppletChat.BtnHistorial.setEnabled(true);
/* 31 */         class_FrameHistorial.this.setVisible(false);
/*    */       }
/*    */     }
/*    */ 
/*    */     ActionHandler()
/*    */     {
/*    */     }
/*    */   }
/*    */ }