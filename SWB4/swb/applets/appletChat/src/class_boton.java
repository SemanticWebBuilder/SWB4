/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.Panel;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ 
/*     */ public class class_boton extends Panel
/*     */ {
/*     */   public boolean status;
/*     */   String etiqueta;
/*     */   public Image img_norm;
/*     */   public Image img_press;
/*     */   public boolean bTogle;
/*     */   class_AppletChat comp_padre;
/*     */ 
/*     */   class_boton(String s, class_AppletChat comp_padre)
/*     */   {
/*  57 */     this.status = false;
/*  58 */     this.img_norm = null;
/*  59 */     this.img_press = null;
/*  60 */     this.bTogle = true;
/*  61 */     this.comp_padre = null;
/*  62 */     this.comp_padre = comp_padre;
/*  63 */     this.status = false;
/*  64 */     this.etiqueta = s;
/*  65 */     setBackground(Color.gray);
/*  66 */     addMouseListener(new class_boton.MouseHandler());
/*     */   }
/*     */ 
/*     */   public void paint(Graphics g)
/*     */   {
/*  71 */     if ((this.img_norm != null) && (this.img_press != null)) {
/*  72 */       if (this.status)
/*     */       {
/*  74 */         g.drawImage(this.img_press, 0, 0, this);
/*  75 */         return;
/*     */       }
/*     */ 
/*  78 */       g.drawImage(this.img_norm, 0, 0, this);
/*  79 */       return;
/*     */     }
/*  81 */     if (this.etiqueta.compareTo("I") == 0)
/*     */     {
/*  83 */       g.setFont(new Font("Dialog", 2, 10));
/*  84 */       g.drawString(this.etiqueta, 3, 13);
/*  85 */       return;
/*     */     }
/*     */ 
/*  88 */     g.setFont(new Font("Dialog", 1, 10));
/*  89 */     g.drawString(this.etiqueta, 3, 13);
/*     */   }
/*     */ 
/*     */   public void CambiaStatus()
/*     */   {
/*  96 */     if (this.status)
/*     */     {
/*  98 */       setBackground(Color.gray);
/*  99 */       repaint();
/* 100 */       this.status = false;
/* 101 */       return;
/*     */     }
/*     */ 
/* 104 */     setBackground(Color.lightGray);
/* 105 */     repaint();
/* 106 */     this.status = true;
/*     */   }
/*     */ 
/*     */   class MouseHandler
/*     */     implements MouseListener
/*     */   {
/*     */     public void mouseClicked(MouseEvent e)
/*     */     {
/*  17 */       if (class_boton.this.bTogle)
/*     */       {
/*  19 */         if ((e.getClickCount() == 1) && ((e.getModifiers() & 0x10) == 16))
/*     */         {
/*  21 */           class_boton.this.CambiaStatus();
/*  22 */           return;
/*     */         }
/*     */       }
/*     */       else {
/*  26 */         if (class_boton.this.etiqueta.compareTo("S") == 0)
/*  27 */           class_boton.this.comp_padre.playRisa();
/*  28 */         if (class_boton.this.etiqueta.compareTo("X") == 0)
/*  29 */           class_boton.this.comp_padre.salir();
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
 * Qualified Name:     class_boton
 * JD-Core Version:    0.6.0
 */