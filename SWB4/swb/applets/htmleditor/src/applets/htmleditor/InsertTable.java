/**
* SemanticWebBuilder Process (SWB Process) es una plataforma para la gesti?n de procesos de negocio mediante el uso de 
* tecnolog?a sem?ntica, que permite el modelado, configuraci?n, ejecuci?n y monitoreo de los procesos de negocio
* de una organizaci?n, as? como el desarrollo de componentes y aplicaciones orientadas a la gesti?n de procesos.
* 
* Mediante el uso de tecnolog?a sem?ntica, SemanticWebBuilder Process puede generar contextos de informaci?n
* alrededor de alg?n tema de inter?s o bien integrar informaci?n y aplicaciones de diferentes fuentes asociadas a
* un proceso de negocio, donde a la informaci?n se le asigna un significado, de forma que pueda ser interpretada
* y procesada por personas y/o sistemas. SemanticWebBuilder Process es una creaci?n original del Fondo de 
* Informaci?n y Documentaci?n para la Industria INFOTEC.
* 
* INFOTEC pone a su disposici?n la herramienta SemanticWebBuilder Process a trav?s de su licenciamiento abierto 
* al p?blico (?open source?), en virtud del cual, usted podr? usarlo en las mismas condiciones con que INFOTEC 
* lo ha dise?ado y puesto a su disposici?n; aprender de ?l; distribuirlo a terceros; acceder a su c?digo fuente,
* modificarlo y combinarlo (o enlazarlo) con otro software. Todo lo anterior de conformidad con los t?rminos y 
* condiciones de la LICENCIA ABIERTA AL P?BLICO que otorga INFOTEC para la utilizaci?n de SemanticWebBuilder Process. 
* 
* INFOTEC no otorga garant?a sobre SemanticWebBuilder Process, de ninguna especie y naturaleza, ni impl?cita ni 
* expl?cita, siendo usted completamente responsable de la utilizaci?n que le d? y asumiendo la totalidad de los 
* riesgos que puedan derivar de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder Process, INFOTEC pone a su disposici?n la
* siguiente direcci?n electr?nica: 
*  http://www.semanticwebbuilder.org.mx
**/
 


/*
 * InsertTable.java
 *
 * Created on 20 de enero de 2005, 03:58 PM
 */

package applets.htmleditor;
import java.util.*;
import javax.swing.*;
import javax.swing.text.html.parser.*;
import java.awt.*;
/**
 *
 * @author  Victor Lorenzana
 */
public class InsertTable extends javax.swing.JDialog {
    
    
    Locale locale;
    WBElement tag;
    public CAling ALIGN_DEFAULT=new CAling("","predeterminada");
    public CAling ALIGN_CENTER=new CAling("center","center");
    public CAling ALIGN_RIGHT=new CAling("right","right");
    public CAling ALIGN_LEFT=new CAling("left","left");
    
    int ret=0;
    public CAling VALIGN_TOP=new CAling("top","top");
    public CAling VALIGN_MIDDLE=new CAling("midlle","midlle");
    public CAling VALIGN_BOTTOM=new CAling("bottom","bottom");
    
    /** Creates new form InsertTable */
    public InsertTable(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        locale=Locale.getDefault();
        setLocale(locale);
        this.jTextFieldTamano.setDocument(new FixedNumericDocument(3, true));
        setSize(500,400);
        setLocation(200,200); 
        this.jSpinnerColumnas.setValue(new Integer(1));
        this.jSpinnerFilas.setValue(new Integer(1));
        this.jTextFieldTamano.setText("100");
    }
    public int getResultValue()
    {
        return ret;
    }
    public WBElement getTag()
    {
        return tag;
    }
    public void setTag(WBElement tag)
    {
        this.tag=tag;
        if(tag.getName()!=null && tag.getName().equalsIgnoreCase("table"))
        {
            Vector elements=new Vector();
            tag.getContent().getElements(elements);
            int rows=0;           
            int maxrows=0;
            for(int i=0;i<elements.size();i++)
            {                
                if(elements.elementAt(i) instanceof WBElement)
                {
                    WBElement element=(WBElement)elements.elementAt(i);
                    if(element.getName()!=null && element.getName().equalsIgnoreCase("tr"))
                    {
                        int icolumns=0;
                        rows++;
                        Vector elementsc=new Vector();
                        element.getContent().getElements(elementsc);
                        for(int j=0;j<elementsc.size();j++)
                        {
                            if(elementsc.elementAt(i) instanceof WBElement)
                            {
                                WBElement elementc=(WBElement)elementsc.elementAt(i);
                                if(elementc.getName()!=null && elementc.getName().equalsIgnoreCase("td"))
                                {
                                    icolumns++;
                                    if(maxrows<icolumns)
                                    {
                                        maxrows=icolumns;
                                    }
                                }
                            }
                        }                        
                    }
                }
            }
            this.jSpinnerColumnas.setValue(new Integer(maxrows));
            this.jSpinnerFilas.setValue(new Integer(rows));            
            if(tag.getAttribute("align").getValue()!=null)
            {
                if(tag.getAttribute("align").getValue().equalsIgnoreCase("center"))
                {
                    this.jComboBoxalineacion.setSelectedItem(ALIGN_CENTER);
                }
                else if(tag.getAttribute("align").getValue().equalsIgnoreCase("right"))
                {
                    this.jComboBoxalineacion.setSelectedItem(ALIGN_RIGHT);
                }
                else if(tag.getAttribute("align").getValue().equalsIgnoreCase("left"))
                {
                    this.jComboBoxalineacion.setSelectedItem(ALIGN_LEFT);
                }
            }
            if(tag.getAttribute("valign").getValue()!=null)
            {
                if(tag.getAttribute("valign").getValue().equalsIgnoreCase("top"))
                {
                    this.jComboBoxAlineacionVertical.setSelectedItem(VALIGN_TOP);
                }
                else if(tag.getAttribute("valign").getValue().equalsIgnoreCase("middle"))
                {
                    this.jComboBoxAlineacionVertical.setSelectedItem(VALIGN_MIDDLE);
                }
                else if(tag.getAttribute("valign").getValue().equalsIgnoreCase("bottom"))
                {
                    this.jComboBoxAlineacionVertical.setSelectedItem(VALIGN_BOTTOM);
                }
            }
            
            if(tag.getAttribute("cellspacing").getValue()!=null)
            {
                String value=tag.getAttribute("cellspacing").getValue();
                try
                {
                    this.jSpinnerEspacioCeldas.setValue(new Integer(value));
                }
                catch(Exception e)
                {
                    e.printStackTrace(System.out);
                }
            }
            if(tag.getAttribute("cellpadding").getValue()!=null)
            {
                String value=tag.getAttribute("cellpadding").getValue();
                try
                {
                    this.jSpinnerMargenCeldas.setValue(new Integer(value));
                }
                catch(Exception e)
                {
                    e.printStackTrace(System.out);
                }
            }
            if(tag.getAttribute("border").getValue()!=null)
            {
                String value=tag.getAttribute("border").getValue();
                try
                {
                    this.jSpinnerTamanoBorde.setValue(new Integer(value));
                }
                catch(Exception e)
                {
                    e.printStackTrace(System.out);
                }
            }
            if(tag.getAttribute("width").getValue()!=null)
            {      
                
                String value=tag.getAttribute("width").getValue();
                int pos=tag.getAttribute("width").getValue().indexOf("%");
                if(pos>=0)
                {
                    this.jRadioButtonPorcentaje.setSelected(true);
                    value=tag.getAttribute("width").getValue().substring(0,pos);
                    try
                    {
                        this.jTextFieldTamano.setText(value);                        
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace(System.out);
                    }
                }
                else
                {
                    this.jRadioButtonPixeles.setSelected(true);
                    try
                    {
                        this.jTextFieldTamano.setText(value);                        
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace(System.out);
                    }
                }
                
            }
            
        }        
    }
    public void show()
    {
        ALIGN_DEFAULT.setName(java.util.ResourceBundle.getBundle("applets/htmleditor/InsertTable",locale).getString("default"));
        ALIGN_CENTER.setName(java.util.ResourceBundle.getBundle("applets/htmleditor/InsertTable",locale).getString("center"));
        ALIGN_RIGHT.setName(java.util.ResourceBundle.getBundle("applets/htmleditor/InsertTable",locale).getString("right"));
        ALIGN_RIGHT.setName(java.util.ResourceBundle.getBundle("applets/htmleditor/InsertTable",locale).getString("left"));
        
          
        
        VALIGN_TOP.setName(java.util.ResourceBundle.getBundle("applets/htmleditor/InsertTable",locale).getString("top"));
        VALIGN_MIDDLE.setName(java.util.ResourceBundle.getBundle("applets/htmleditor/InsertTable",locale).getString("middle"));
        VALIGN_BOTTOM.setName(java.util.ResourceBundle.getBundle("applets/htmleditor/InsertTable",locale).getString("bottom"));
        
        this.jComboBoxalineacion.addItem(ALIGN_DEFAULT);
        this.jComboBoxalineacion.addItem(ALIGN_CENTER);
        this.jComboBoxalineacion.addItem(ALIGN_RIGHT);
        this.jComboBoxalineacion.addItem(ALIGN_LEFT);
        this.jComboBoxalineacion.setSelectedItem(ALIGN_DEFAULT);
        
        this.jComboBoxAlineacionVertical.addItem(ALIGN_DEFAULT);
        this.jComboBoxAlineacionVertical.addItem(VALIGN_TOP);
        this.jComboBoxAlineacionVertical.addItem(VALIGN_MIDDLE);
        this.jComboBoxAlineacionVertical.addItem(VALIGN_BOTTOM);
        this.jComboBoxAlineacionVertical.setSelectedItem(ALIGN_DEFAULT);
        super.show();
    }
    public void setLocale(Locale locale)
    {
        this.locale=locale;        
        
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents()//GEN-BEGIN:initComponents
    {
        buttonGroup1 = new javax.swing.ButtonGroup();
        jColorChooser1 = new javax.swing.JColorChooser();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSpinnerFilas = new javax.swing.JSpinner();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jSpinnerColumnas = new javax.swing.JSpinner();
        jPanel9 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jTextFieldTamano = new javax.swing.JTextField();
        jRadioButtonPixeles = new javax.swing.JRadioButton();
        jRadioButtonPorcentaje = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        jCheckBoxUseColor = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBoxalineacion = new javax.swing.JComboBox();
        jSpinnerTamanoBorde = new javax.swing.JSpinner();
        jSpinnerEspacioCeldas = new javax.swing.JSpinner();
        jSpinnerMargenCeldas = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        jComboBoxAlineacionVertical = new javax.swing.JComboBox();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jButtonAceptar = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();

        setBackground(java.awt.Color.gray);
        setModal(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(java.awt.event.WindowEvent evt)
            {
                closeDialog(evt);
            }
        });

        jPanel1.setLayout(new java.awt.GridLayout(1, 2));

        jPanel1.setPreferredSize(new java.awt.Dimension(10, 60));
        jPanel4.setLayout(new java.awt.GridLayout(1, 2));

        jPanel4.setBorder(new javax.swing.border.TitledBorder(java.util.ResourceBundle.getBundle("applets/htmleditor/InsertTable",locale).getString("tamano")));
        jLabel1.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/InsertTable",locale).getString("filas"));
        jPanel5.add(jLabel1);

        jSpinnerFilas.setPreferredSize(new java.awt.Dimension(50, 20));
        jSpinnerFilas.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                jSpinnerFilasStateChanged(evt);
            }
        });

        jPanel5.add(jSpinnerFilas);

        jPanel4.add(jPanel5);

        jLabel2.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/InsertTable",locale).getString("columnas"));
        jPanel6.add(jLabel2);

        jSpinnerColumnas.setPreferredSize(new java.awt.Dimension(50, 20));
        jSpinnerColumnas.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                jSpinnerColumnasStateChanged(evt);
            }
        });

        jPanel6.add(jSpinnerColumnas);

        jPanel4.add(jPanel6);

        jPanel1.add(jPanel4);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel3.setBorder(new javax.swing.border.TitledBorder(java.util.ResourceBundle.getBundle("applets/htmleditor/InsertTable",locale).getString("Diseno")));
        jPanel7.setLayout(null);

        jPanel7.setPreferredSize(new java.awt.Dimension(160, 200));
        jTextFieldTamano.setText("100");
        jPanel7.add(jTextFieldTamano);
        jTextFieldTamano.setBounds(0, 30, 50, 20);

        jRadioButtonPixeles.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/InsertTable",locale).getString("pixeles"));
        buttonGroup1.add(jRadioButtonPixeles);
        jPanel7.add(jRadioButtonPixeles);
        jRadioButtonPixeles.setBounds(50, 20, 83, 24);

        jRadioButtonPorcentaje.setSelected(true);
        jRadioButtonPorcentaje.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/InsertTable",locale).getString("porcentaje"));
        buttonGroup1.add(jRadioButtonPorcentaje);
        jPanel7.add(jRadioButtonPorcentaje);
        jRadioButtonPorcentaje.setBounds(50, 40, 103, 24);

        jPanel2.setLayout(null);

        jPanel2.setBorder(new javax.swing.border.TitledBorder(java.util.ResourceBundle.getBundle("applets/htmleditor/InsertTable",locale).getString("colortit")));
        jCheckBoxUseColor.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/InsertTable",locale).getString("sinColor"));
        jPanel2.add(jCheckBoxUseColor);
        jCheckBoxUseColor.setBounds(10, 50, 140, 24);

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setOpaque(true);
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseReleased(java.awt.event.MouseEvent evt)
            {
                jLabel9MouseReleased(evt);
            }
        });
        jLabel9.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
        {
            public void mouseMoved(java.awt.event.MouseEvent evt)
            {
                jLabel9MouseMoved(evt);
            }
        });

        jPanel2.add(jLabel9);
        jLabel9.setBounds(10, 20, 140, 30);

        jPanel7.add(jPanel2);
        jPanel2.setBounds(0, 60, 160, 90);

        jLabel8.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/InsertTable",locale).getString("ancho_tabla"));
        jPanel7.add(jLabel8);
        jLabel8.setBounds(0, 0, 150, 16);

        jPanel3.add(jPanel7, java.awt.BorderLayout.EAST);

        jPanel8.setLayout(null);

        jLabel3.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/InsertTable",locale).getString("alineacion"));
        jPanel8.add(jLabel3);
        jLabel3.setBounds(10, 10, 130, 16);

        jLabel4.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/InsertTable",locale).getString("tam_borde"));
        jPanel8.add(jLabel4);
        jLabel4.setBounds(10, 70, 103, 16);

        jLabel5.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/InsertTable",locale).getString("margen_celdas"));
        jPanel8.add(jLabel5);
        jLabel5.setBounds(10, 130, 121, 16);

        jLabel6.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/InsertTable",locale).getString("espacio_celdas"));
        jPanel8.add(jLabel6);
        jLabel6.setBounds(10, 100, 130, 16);

        jPanel8.add(jComboBoxalineacion);
        jComboBoxalineacion.setBounds(140, 10, 150, 20);

        jPanel8.add(jSpinnerTamanoBorde);
        jSpinnerTamanoBorde.setBounds(140, 70, 150, 24);

        jPanel8.add(jSpinnerEspacioCeldas);
        jSpinnerEspacioCeldas.setBounds(140, 100, 150, 24);

        jPanel8.add(jSpinnerMargenCeldas);
        jSpinnerMargenCeldas.setBounds(140, 130, 150, 24);

        jLabel7.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/InsertTable",locale).getString("alineacion_vertical"));
        jPanel8.add(jLabel7);
        jLabel7.setBounds(10, 40, 120, 16);

        jPanel8.add(jComboBoxAlineacionVertical);
        jComboBoxAlineacionVertical.setBounds(140, 40, 150, 20);

        jPanel3.add(jPanel8, java.awt.BorderLayout.CENTER);

        jPanel9.add(jPanel3, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel9, java.awt.BorderLayout.CENTER);

        jPanel10.setLayout(new java.awt.BorderLayout());

        jPanel10.setPreferredSize(new java.awt.Dimension(10, 60));
        jPanel11.setPreferredSize(new java.awt.Dimension(200, 10));
        jButtonAceptar.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/InsertTable",locale).getString("aceptar"));
        jButtonAceptar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButtonAceptarActionPerformed(evt);
            }
        });

        jPanel11.add(jButtonAceptar);

        jButtonCancel.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/InsertTable",locale).getString("cancelar"));
        jButtonCancel.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButtonCancelActionPerformed(evt);
            }
        });

        jPanel11.add(jButtonCancel);

        jPanel10.add(jPanel11, java.awt.BorderLayout.EAST);

        jPanel12.setLayout(null);

        jPanel10.add(jPanel12, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel10, java.awt.BorderLayout.SOUTH);

        pack();
    }//GEN-END:initComponents

    private void jSpinnerColumnasStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinnerColumnasStateChanged
        if(((Integer)this.jSpinnerColumnas.getValue()).intValue()<0)
        {
            this.jSpinnerColumnas.setValue(new Integer(0));
            this.jSpinnerColumnas.updateUI();
        }
        jSpinnerColumnas.grabFocus();
    }//GEN-LAST:event_jSpinnerColumnasStateChanged

    private void jSpinnerFilasStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinnerFilasStateChanged
        if(((Integer)this.jSpinnerFilas.getValue()).intValue()<0)
        {
            this.jSpinnerFilas.setValue(new Integer(0));
            this.jSpinnerFilas.updateUI();
        }
        jSpinnerColumnas.grabFocus();
    }//GEN-LAST:event_jSpinnerFilasStateChanged

    private void jLabel9MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseReleased
        try
        {   
            Color newcolor=this.jColorChooser1.showDialog(this, "", this.jLabel9.getBackground());
            this.jLabel9.setBackground(newcolor);
            this.jLabel9.updateUI();
        }
        catch(Exception e)
        {
            e.printStackTrace(System.out);
        }
    }//GEN-LAST:event_jLabel9MouseReleased

    private void jLabel9MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseMoved
        // Add your handling code here:
        this.jLabel9.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_jLabel9MouseMoved

    private void jButtonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAceptarActionPerformed
        Integer valuec=(Integer)this.jSpinnerColumnas.getValue();
        if(valuec.intValue()==0)
        {
            this.jSpinnerColumnas.grabFocus();
            JOptionPane.showMessageDialog(this,java.util.ResourceBundle.getBundle("applets/htmleditor/InsertTable",locale).getString("indicar_columnas"),java.util.ResourceBundle.getBundle("applets/htmleditor/InsertTable",locale).getString("title"),JOptionPane.ERROR_MESSAGE);            
            return;
        }
        valuec=(Integer)this.jSpinnerFilas.getValue();
        if(valuec.intValue()==0)
        {
            this.jSpinnerFilas.grabFocus();
            JOptionPane.showMessageDialog(this,java.util.ResourceBundle.getBundle("applets/htmleditor/InsertTable",locale).getString("indicar_filas"),java.util.ResourceBundle.getBundle("applets/htmleditor/InsertTable",locale).getString("title"),JOptionPane.ERROR_MESSAGE);            
            return;
        }
        /*if(this.jTextFieldTamano.getText().trim().equals(""))
        {
            this.jTextFieldTamano.grabFocus();
            JOptionPane.showMessageDialog(this,java.util.ResourceBundle.getBundle("applets/htmleditor/InsertTable",locale).getString("indicar_ancho"),java.util.ResourceBundle.getBundle("applets/htmleditor/InsertTable",locale).getString("title"),JOptionPane.ERROR_MESSAGE);            
            return;
        }*/
        if(this.jRadioButtonPorcentaje.isSelected() && !this.jTextFieldTamano.getText().trim().equals(""))
        {
            int value=Integer.parseInt(this.jTextFieldTamano.getText());
            if(value>100)
            {
                this.jTextFieldTamano.grabFocus();
                JOptionPane.showMessageDialog(this,java.util.ResourceBundle.getBundle("applets/htmleditor/InsertTable",locale).getString("max_per"),java.util.ResourceBundle.getBundle("applets/htmleditor/InsertTable",locale).getString("title"),JOptionPane.ERROR_MESSAGE);            
                return;
            }
        }
        if(tag==null)
        {
              tag =new WBElement("table",0);                                               
              int iRows=((Integer)this.jSpinnerFilas.getValue()).intValue();              
              int iColumns=((Integer)this.jSpinnerColumnas.getValue()).intValue();              
              for(int i=0;i<iRows;i++)
              {                  
                  WBElement tr=new WBElement("tr",i);
                  tag.add(tr);                  
                  for(int j=0;j<iColumns;j++)
                  {
                        WBElement td=new WBElement("td",j);                        
                        WBElement p=new WBElement("p",0);
                        p.setAttribute("style","margin-top: 0");
                        td.add(p);                        
                        tr.add(td);
                  }                  
              }
              
              //WBContentModel content=new WBContentModel(tr);
              //tag.setContentModel(content);
        }
        
        if(this.jCheckBoxUseColor.isSelected())
        {
            Color color=this.jLabel9.getBackground();
            String bgcolor="#"+colorToHex(color);            
            tag.setAttribute("bgcolor",bgcolor);
        }
        else
        {
            tag.setAttribute("bgcolor",null);
        }
        if(!this.jTextFieldTamano.getText().trim().equals(""))
        {
            if(this.jRadioButtonPorcentaje.isSelected())
            {
                tag.setAttribute("width",this.jTextFieldTamano.getText().trim()+"%");    
            }
            else
            {
                tag.setAttribute("width",this.jTextFieldTamano.getText().trim());
            }
        }     
        else
        {
            tag.setAttribute("width",null);
        }
        
        tag.setAttribute("cellpadding",((Integer)this.jSpinnerMargenCeldas.getValue()).toString());
        tag.setAttribute("cellspacing",((Integer)this.jSpinnerEspacioCeldas.getValue()).toString());
        tag.setAttribute("border",((Integer)this.jSpinnerTamanoBorde.getValue()).toString());

        if(this.jComboBoxalineacion.getSelectedItem().equals(ALIGN_DEFAULT))
        {
            tag.setAttribute("align",null);
        }
        else
        {            
            CAling align=(CAling)this.jComboBoxalineacion.getSelectedItem();
            tag.setAttribute("align",align.getValue());
        }
        if(this.jComboBoxAlineacionVertical.getSelectedItem().equals(ALIGN_DEFAULT))
        {
            tag.setAttribute("valign",null);
        }
        else
        {            
            CAling align=(CAling)this.jComboBoxAlineacionVertical.getSelectedItem();
            tag.setAttribute("valign",align.getValue());
        }        
        System.out.println(tag.getHtml());
        this.ret=2;
        setVisible(false);
        dispose();
    }//GEN-LAST:event_jButtonAceptarActionPerformed
    public static String colorToHex (Color c) {
	int r = c.getRed ();
	int g = c.getGreen ();
	int b = c.getBlue ();
	return paddedHex (r) + paddedHex (g) + paddedHex (b);
    }

    public static String paddedHex (int i) {
	String s = Integer.toHexString (i);
	if (s.length () == 1) s = "0" + s;
	return s;
    }
    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        setVisible(false);
        dispose();
    }//GEN-LAST:event_jButtonCancelActionPerformed
    
    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog
    
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButtonAceptar;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JCheckBox jCheckBoxUseColor;
    private javax.swing.JColorChooser jColorChooser1;
    private javax.swing.JComboBox jComboBoxAlineacionVertical;
    private javax.swing.JComboBox jComboBoxalineacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButtonPixeles;
    private javax.swing.JRadioButton jRadioButtonPorcentaje;
    private javax.swing.JSpinner jSpinnerColumnas;
    private javax.swing.JSpinner jSpinnerEspacioCeldas;
    private javax.swing.JSpinner jSpinnerFilas;
    private javax.swing.JSpinner jSpinnerMargenCeldas;
    private javax.swing.JSpinner jSpinnerTamanoBorde;
    private javax.swing.JTextField jTextFieldTamano;
    // End of variables declaration//GEN-END:variables
    
}
