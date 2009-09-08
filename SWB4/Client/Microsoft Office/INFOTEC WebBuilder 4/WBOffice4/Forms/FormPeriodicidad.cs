/**  
* SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integraciÃ³n, 
* colaboraciÃ³n y conocimiento, que gracias al uso de tecnologÃ­a semÃ¡ntica puede generar contextos de 
* informaciÃ³n alrededor de algÃºn tema de interÃ©s o bien integrar informaciÃ³n y aplicaciones de diferentes 
* fuentes, donde a la informaciÃ³n se le asigna un significado, de forma que pueda ser interpretada y 
* procesada por personas y/o sistemas, es una creaciÃ³n original del Fondo de InformaciÃ³n y DocumentaciÃ³n 
* para la Industria INFOTEC, cuyo registro se encuentra actualmente en trÃ¡mite. 
* 
* INFOTEC pone a su disposiciÃ³n la herramienta SemanticWebBuilder a travÃ©s de su licenciamiento abierto al pÃºblico (â€˜open sourceâ€™), 
* en virtud del cual, usted podrÃ¡ usarlo en las mismas condiciones con que INFOTEC lo ha diseÃ±ado y puesto a su disposiciÃ³n; 
* aprender de Ã©l; distribuirlo a terceros; acceder a su cÃ³digo fuente y modificarlo, y combinarlo o enlazarlo con otro software, 
* todo ello de conformidad con los tÃ©rminos y condiciones de la LICENCIA ABIERTA AL PÃšBLICO que otorga INFOTEC para la utilizaciÃ³n 
* del SemanticWebBuilder 4.0. 
* 
* INFOTEC no otorga garantÃ­a sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implÃ­cita ni explÃ­cita, 
* siendo usted completamente responsable de la utilizaciÃ³n que le dÃ© y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposiciÃ³n la siguiente 
* direcciÃ³n electrÃ³nica: 
*  http://www.webbuilder.org.mx 
**/ 
 
/*	INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboración e integración para Internet, la cual, es una creación original del Fondo de Información y Documentación para la Industria INFOTEC, misma que se encuentra debidamente registrada ante el Registro Público del Derecho de Autor de los Estados Unidos Mexicanos con el No. 03-2002-052312015400-14, para la versión 1; No. 03-2003-012112473900 para la versión 2, y No. 03-2006-012012004000-01 para la versión 3, respectivamente. 
	INFOTEC pone a su disposición la herramienta INFOTEC WebBuilder a través de su licenciamiento abierto al público (‘open source’), en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición; aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software, todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización de INFOTEC WebBuilder 3.2
	INFOTEC no otorga garantía sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita, siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar de la misma.
	Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposición la siguiente dirección electrónica: http://www.webbuilder.org.mx	
*/
using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using System.Xml;
using System.Diagnostics;
namespace WBOffice4.Forms
{
	/// <summary>Forma que permite definir o modificar una periodicidad</summary>
	public class FrmPeriodicidad : System.Windows.Forms.Form
	{		
		private System.Windows.Forms.GroupBox groupBox2;
		private System.Windows.Forms.DateTimePicker dateTimeendDate;
		private System.Windows.Forms.DateTimePicker dateTimeInitDate;
		private System.Windows.Forms.RadioButton sinfecha;
		private System.Windows.Forms.RadioButton endselect;
		private System.Windows.Forms.Label label9;
		private System.Windows.Forms.GroupBox groupBox1;
		private System.Windows.Forms.GroupBox groupBox9;
		private System.Windows.Forms.Label label21;
		private System.Windows.Forms.Label label20;
		private System.Windows.Forms.Label label19;
		private System.Windows.Forms.CheckBox yday7;
		private System.Windows.Forms.CheckBox yday6;
		private System.Windows.Forms.CheckBox yday5;
		private System.Windows.Forms.CheckBox yday4;
		private System.Windows.Forms.CheckBox yday3;
		private System.Windows.Forms.CheckBox yday2;
        private System.Windows.Forms.CheckBox yday1;
		private System.Windows.Forms.ComboBox ymonth2;
		private System.Windows.Forms.ComboBox yweek;
		private System.Windows.Forms.RadioButton periodyear2;
		private System.Windows.Forms.RadioButton periodweek;
		private System.Windows.Forms.GroupBox groupBox8;
        private System.Windows.Forms.Label label15;
		private System.Windows.Forms.Label label14;
		private System.Windows.Forms.CheckBox mday7;
		private System.Windows.Forms.CheckBox mday6;
		private System.Windows.Forms.CheckBox mday5;
		private System.Windows.Forms.CheckBox mday4;
		private System.Windows.Forms.CheckBox mday3;
		private System.Windows.Forms.CheckBox mday2;
		private System.Windows.Forms.CheckBox mday1;
		private System.Windows.Forms.RadioButton periodmont2;
		private System.Windows.Forms.ComboBox mweek;
		private System.Windows.Forms.GroupBox groupBox7;
		private System.Windows.Forms.CheckBox wday4;
		private System.Windows.Forms.CheckBox wday7;
		private System.Windows.Forms.CheckBox wday6;
		private System.Windows.Forms.CheckBox wday5;
		private System.Windows.Forms.CheckBox wday3;
		private System.Windows.Forms.CheckBox wday2;
		private System.Windows.Forms.CheckBox wday1;
		private System.Windows.Forms.GroupBox groupBox5;
		private System.Windows.Forms.GroupBox groupBox4;
		private System.Windows.Forms.GroupBox groupBox3;
		private System.Windows.Forms.RadioButton periodyear;
		private System.Windows.Forms.RadioButton periodmonth;
		private System.Windows.Forms.GroupBox groupBox11;
		private System.Windows.Forms.CheckBox periodicidad;
		private System.Windows.Forms.Button buttonCancelar;
		private System.Windows.Forms.Button buttonAceptar;
		/// <summary>
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.Container components = null;
		
		
        private System.Windows.Forms.Label label13;
        private System.Windows.Forms.Label label10;
		private System.Windows.Forms.RadioButton periodmont1;
		private System.Windows.Forms.Label label18;
		private System.Windows.Forms.Label label17;
		private System.Windows.Forms.Label label16;
        private System.Windows.Forms.ComboBox ymonth;
		private System.Windows.Forms.RadioButton periodyear1;
		private System.Windows.Forms.Panel panel3;
		private System.Windows.Forms.Panel panel1;
		
		private System.Windows.Forms.Button buttonAvanzado;		
		private System.Windows.Forms.GroupBox groupBox6;
		private System.Windows.Forms.Label label1;
		private System.Windows.Forms.Label label2;
        bool expanded = false;
        public TextBox textBoxTitle;
		private System.Windows.Forms.Label label3;
		private System.Windows.Forms.DateTimePicker dateTimePickerHoraFin;
		private System.Windows.Forms.DateTimePicker dateTimePickerHoraInicio;		
		private System.Windows.Forms.CheckBox checkBoxPorHora;
        private NumericUpDown mmonth;
        private NumericUpDown mday;
        private NumericUpDown yyear2;
        private NumericUpDown yday;
        private NumericUpDown yyear;
        private NumericUpDown mmonth2;
        private CheckBox checkBoxActive;
		//ListView list;
		/// <summary>
		/// Constructor de forma para configurar periodicidad
		/// </summary>
		/// <param name="item">Peridiocidad a cambiar</param>
		/// <param name="user">Usuario</param>
		/// <param name="list">Lista de periodicidades</param>
		public FrmPeriodicidad(bool active)
		{
			//
			// Required for Windows Form Designer support
			//
			InitializeComponent();
            //this.list=list;
            buttonAvanzado_Click(null, null);
            this.checkBoxActive.Checked = active;
		}
        public bool isActive()
        {
            return this.checkBoxActive.Checked;
        }
        public XmlDocument Document
        {
            get
            {
                XmlDocument doc = new XmlDocument();
                #region Generación del XML
                
                doc.AppendChild(doc.CreateXmlDeclaration("1.0", "UTF-8", null));
                System.Xml.XmlElement elem;
                System.Xml.XmlElement interval;
                System.Xml.XmlElement resource = doc.CreateElement("Resource");
                doc.AppendChild(resource);

                interval = doc.CreateElement("interval");
                resource.AppendChild(interval);

                XmlElement title = doc.CreateElement("title");
                title.InnerText = this.textBoxTitle.Text;
                interval.AppendChild(title);

                if (this.checkBoxPorHora.Checked)
                {
                    XmlElement starttime = doc.CreateElement("starthour");
                    interval.AppendChild(starttime);
                    starttime.InnerText = this.dateTimePickerHoraInicio.Value.Hour.ToString("00") + ":" + this.dateTimePickerHoraInicio.Value.Minute.ToString("00");


                    XmlElement endtime = doc.CreateElement("endhour");
                    interval.AppendChild(endtime);
                    endtime.InnerText = this.dateTimePickerHoraFin.Value.Hour.ToString("00") + ":" + this.dateTimePickerHoraFin.Value.Minute.ToString("00");
                }


                elem = doc.CreateElement("inidate");
                elem.InnerText = this.dateTimeInitDate.Value.Month + "/" + this.dateTimeInitDate.Value.Day + "/" + this.dateTimeInitDate.Value.Year;
                interval.AppendChild(elem);
                if (this.endselect.Checked)
                {
                    elem = doc.CreateElement("enddate");
                    elem.InnerText = this.dateTimeendDate.Value.Month + "/" + this.dateTimeendDate.Value.Day + "/" + this.dateTimeendDate.Value.Year;
                    interval.AppendChild(elem);
                }
                if (this.periodicidad.Checked)
                {
                    System.Xml.XmlElement iterations = doc.CreateElement("iterations");
                    interval.AppendChild(iterations);
                    if (this.periodweek.Checked)
                    {
                        int x = 0;
                        System.Xml.XmlElement weekly = doc.CreateElement("weekly");
                        iterations.AppendChild(weekly);
                        if (this.wday1.Checked)
                            x += 1;
                        if (this.wday2.Checked)
                            x += 2;
                        if (this.wday3.Checked)
                            x += 4;
                        if (this.wday4.Checked)
                            x += 8;
                        if (this.wday5.Checked)
                            x += 16;
                        if (this.wday6.Checked)
                            x += 32;
                        if (this.wday7.Checked)
                            x += 64;
                        elem = doc.CreateElement("wdays");
                        elem.InnerText = x.ToString();
                        weekly.AppendChild(elem);
                    }
                    if (this.periodmonth.Checked)
                    {
                        System.Xml.XmlElement monthly = doc.CreateElement("monthly");
                        iterations.AppendChild(monthly);
                        if (this.periodmont1.Checked)
                        {
                            if (this.mday.Text != null)
                            {
                                elem = doc.CreateElement("day");
                                elem.InnerText = mday.Text;
                                monthly.AppendChild(elem);
                            }
                            if (this.mmonth.Text != null)
                            {
                                elem = doc.CreateElement("months");
                                elem.InnerText = mmonth.Text;
                                monthly.AppendChild(elem);
                            }
                        }
                        else if (this.periodmont2.Checked)
                        {
                            int x = 0;
                            if (this.mweek.Text != null)
                            {
                                elem = doc.CreateElement("week");
                                elem.InnerText = "" + (mweek.SelectedIndex + 1);
                                monthly.AppendChild(elem);
                            }
                            if (this.mday1.Checked)
                                x += 1;
                            if (this.mday2.Checked)
                                x += 2;
                            if (this.mday3.Checked)
                                x += 4;
                            if (this.mday4.Checked)
                                x += 8;
                            if (this.mday5.Checked)
                                x += 16;
                            if (this.mday6.Checked)
                                x += 32;
                            if (this.mday7.Checked)
                                x += 64;
                            elem = doc.CreateElement("wdays");
                            elem.InnerText = x.ToString();
                            monthly.AppendChild(elem);
                            if (this.mmonth2.Text != null)
                            {
                                elem = doc.CreateElement("months");
                                elem.InnerText = mmonth2.Text;
                                monthly.AppendChild(elem);
                            }
                        }
                    }
                    if (this.periodyear.Checked)
                    {
                        System.Xml.XmlElement yearly = doc.CreateElement("yearly");
                        iterations.AppendChild(yearly);
                        if (this.periodyear1.Checked)
                        {
                            if (this.yday.Text != null)
                            {
                                elem = doc.CreateElement("day");
                                elem.InnerText = yday.Text;
                                yearly.AppendChild(elem);
                            }
                            elem = doc.CreateElement("month");
                            elem.InnerText = "" + (ymonth.SelectedIndex + 1);
                            yearly.AppendChild(elem);

                            if (this.yyear.Text != null)
                            {
                                elem = doc.CreateElement("years");
                                elem.InnerText = yyear.Text;
                                yearly.AppendChild(elem);
                            }
                        }
                        else if (this.periodyear2.Checked)
                        {
                            int x = 0;
                            if (this.yweek.Text != null)
                            {
                                elem = doc.CreateElement("week");
                                elem.InnerText = "" + (yweek.SelectedIndex + 1);
                                yearly.AppendChild(elem);
                            }
                            if (this.yday1.Checked)
                                x += 1;
                            if (this.yday2.Checked)
                                x += 2;
                            if (this.yday3.Checked)
                                x += 4;
                            if (this.yday4.Checked)
                                x += 8;
                            if (this.yday5.Checked)
                                x += 16;
                            if (this.yday6.Checked)
                                x += 32;
                            if (this.yday7.Checked)
                                x += 64;
                            elem = doc.CreateElement("wdays");
                            elem.InnerText = x.ToString();
                            yearly.AppendChild(elem);
                            if (this.ymonth2.Text != null)
                            {
                                elem = doc.CreateElement("month");
                                elem.InnerText = "" + (ymonth2.SelectedIndex + 1);
                                yearly.AppendChild(elem);
                            }
                            if (this.yyear2.Text != null)
                            {
                                elem = doc.CreateElement("years");
                                elem.InnerText = yyear2.Text;
                                yearly.AppendChild(elem);
                            }
                        }
                    }
                }

               


                #endregion 			
                return doc;
            }
            set
            {
                buttonAvanzado_Click(null, null);
                String tipo = "0";

                this.dateTimePickerHoraFin.Value = DateTime.Now;


                mweek.SelectedIndex = 0;
                yweek.SelectedIndex = 0;
                ymonth2.SelectedIndex = 0;
                ymonth.SelectedIndex = 0;
                string[] siteminterval = new String[9];



                String xmlconf = value.OuterXml;
                XmlDocument docc = value;
                if (xmlconf != null)
                {
                    if (!(xmlconf.Equals("") || xmlconf.Equals("null")))
                    {

                        if (docc.GetElementsByTagName("title").Count > 0)
                        {
                            this.textBoxTitle.Text = docc.GetElementsByTagName("title")[0].InnerText;
                        }
                        if (docc.GetElementsByTagName("starthour").Count > 0 && docc.GetElementsByTagName("endhour").Count > 0)
                        {
                            this.checkBoxPorHora.Checked = true;
                            this.checkBoxPorHora_CheckedChanged(null, null);
                            if (docc.GetElementsByTagName("starthour").Count > 0)
                            {
                                this.dateTimePickerHoraInicio.Value = DateTime.Parse(docc.GetElementsByTagName("starthour")[0].InnerText);
                            }
                            if (docc.GetElementsByTagName("endhour").Count > 0)
                            {
                                this.dateTimePickerHoraFin.Value = DateTime.Parse(docc.GetElementsByTagName("endhour")[0].InnerText);
                            }
                        }


                        if (docc.GetElementsByTagName("iterations").Count > 0)
                        {
                            this.periodicidad.Checked = true;
                        }
                        System.Xml.XmlNodeList nodosweekly = docc.GetElementsByTagName("weekly");
                        if (nodosweekly.Count > 0)
                        {
                            tipo = "1";
                        }
                        System.Xml.XmlNodeList nodosmonthly = docc.GetElementsByTagName("monthly");
                        if (nodosmonthly.Count > 0)
                        {
                            tipo = "2";
                        }
                        System.Xml.XmlNodeList nodosyearly = docc.GetElementsByTagName("yearly");
                        if (nodosyearly.Count > 0)
                        {
                            tipo = "3";
                        }
                        System.Xml.XmlNodeList nodosinterval = docc.GetElementsByTagName("interval");
                        for (int i = 0; i < nodosinterval.Count; i++)
                        {
                            System.Xml.XmlElement xmlinterval = (XmlElement)nodosinterval[i];
                            for (int l = 0; l < xmlinterval.ChildNodes.Count; l++)
                            {
                                if (xmlinterval.ChildNodes[l].Name.Equals("inidate")) //fecha inicial
                                {
                                    siteminterval[0] = xmlinterval.ChildNodes[l].InnerText;
                                }
                                if (xmlinterval.ChildNodes[l].Name.Equals("enddate")) //fecha final
                                {
                                    siteminterval[1] = xmlinterval.ChildNodes[l].InnerText;
                                }
                            }
                        }
                        if (docc.GetElementsByTagName("enddate").Count == 0)
                        {
                            sinfecha.Checked = false;
                            endselect.Checked = true;
                        }
                        else
                        {
                            sinfecha.Checked = true;
                            endselect.Checked = false;
                        }
                        System.Xml.XmlNodeList nodositera = docc.GetElementsByTagName("iterations");
                        for (int i = 0; i < nodositera.Count; i++)
                        {
                            System.Xml.XmlElement xmlitera = (XmlElement)nodositera[i];
                            for (int l = 0; l < xmlitera.ChildNodes.Count; l++)
                            {
                                System.Xml.XmlNodeList nodosit = xmlitera.GetElementsByTagName("weekly");
                                for (int j = 0; j < nodosit.Count; j++)
                                {
                                    System.Xml.XmlElement xmlit = (XmlElement)nodosit[j];
                                    for (int k = 0; k < xmlit.ChildNodes.Count; k++)
                                    {
                                        if (xmlit.ChildNodes[l].Name.Equals("wdays")) //días
                                        {
                                            siteminterval[2] = xmlit.ChildNodes[k].InnerText;
                                        }
                                    }
                                }
                                System.Xml.XmlNodeList nodosit1 = xmlitera.GetElementsByTagName("monthly");
                                for (int j = 0; j < nodosit1.Count; j++)
                                {
                                    System.Xml.XmlElement xmlit = (XmlElement)nodosit1[j];
                                    for (int k = 0; k < xmlit.ChildNodes.Count; k++)
                                    {
                                        if (xmlit.ChildNodes[k].Name.Equals("wdays")) //días
                                        {
                                            siteminterval[2] = xmlit.ChildNodes[k].InnerText;
                                        }
                                        if (xmlit.ChildNodes[k].Name.Equals("week")) //semanas
                                        {
                                            siteminterval[3] = xmlit.ChildNodes[k].InnerText;
                                        }
                                        if (xmlit.ChildNodes[k].Name.Equals("months")) //meses
                                        {
                                            siteminterval[4] = xmlit.ChildNodes[k].InnerText;
                                        }
                                        if (xmlit.ChildNodes[k].Name.Equals("day")) //día específico
                                        {
                                            siteminterval[6] = xmlit.ChildNodes[k].InnerText;
                                        }
                                    }
                                }
                                System.Xml.XmlNodeList nodosit2 = xmlitera.GetElementsByTagName("yearly");
                                for (int j = 0; j < nodosit2.Count; j++)
                                {
                                    System.Xml.XmlElement xmlit = (XmlElement)nodosit2[j];
                                    for (int k = 0; k < xmlit.ChildNodes.Count; k++)
                                    {
                                        if (xmlit.ChildNodes[k].Name.Equals("wdays")) //días
                                        {
                                            siteminterval[2] = xmlit.ChildNodes[k].InnerText;
                                        }
                                        if (xmlit.ChildNodes[k].Name.Equals("week")) //semanas
                                        {
                                            siteminterval[3] = xmlit.ChildNodes[k].InnerText;
                                        }
                                        if (xmlit.ChildNodes[k].Name.Equals("months")) //meses
                                        {
                                            siteminterval[4] = xmlit.ChildNodes[k].InnerText;
                                        }
                                        if (xmlit.ChildNodes[k].Name.Equals("years")) //años
                                        {
                                            siteminterval[5] = xmlit.ChildNodes[k].InnerText;
                                        }
                                        if (xmlit.ChildNodes[k].Name.Equals("day")) //día específico
                                        {
                                            siteminterval[6] = xmlit.ChildNodes[k].InnerText;
                                        }
                                        if (xmlit.ChildNodes[k].Name.Equals("month")) //mes específico
                                        {
                                            siteminterval[7] = xmlit.ChildNodes[k].InnerText;
                                        }
                                    }
                                }
                            }
                        }
                        #region Cargado de forma


                        String sdate = "";
                        if (siteminterval[0] != null)
                        {
                            if (!siteminterval[0].StartsWith("/"))
                            {
                                siteminterval[0] = "/" + siteminterval[0];
                            }
                            sdate = siteminterval[0].Substring(1);
                            int pos = sdate.IndexOf("/");
                            String mes = sdate.Substring(0, pos);
                            sdate = sdate.Substring(pos + 1);

                            pos = sdate.IndexOf("/");
                            String dia = sdate.Substring(0, pos);
                            sdate = sdate.Substring(pos + 1);
                            String ano = sdate;
                            
                            //this.dateTimeInitDate.Value = System.Convert.ToDateTime(dia + "/" + mes + "/" + ano);
                            this.dateTimeInitDate.Value = new DateTime(int.Parse(ano), int.Parse(mes), int.Parse(dia));
                        }

                        if (siteminterval[1] != null)
                        {
                            if (!siteminterval[1].StartsWith("/"))
                            {
                                siteminterval[1] = "/" + siteminterval[1];
                            }
                            sdate = siteminterval[1].Substring(1);
                            int pos = sdate.IndexOf("/");
                            String mes = sdate.Substring(0, pos);
                            sdate = sdate.Substring(pos + 1);

                            pos = sdate.IndexOf("/");
                            String dia = sdate.Substring(0, pos);
                            sdate = sdate.Substring(pos + 1);

                            String ano = sdate;

                            this.endselect.Checked = true;
                            //this.dateTimeendDate.Value = System.Convert.ToDateTime(dia + "/" + mes + "/" + ano);
                            this.dateTimeendDate.Value = new DateTime(int.Parse(ano), int.Parse(mes), int.Parse(dia));
                        }
                        else
                        {
                            this.sinfecha.Checked = true;
                        }

                        if (tipo.Equals("1"))
                        {
                            this.periodweek.Checked = true;
                            string[] days = { "", "", "", "", "", "", "" };
                            int dias = System.Convert.ToInt32(siteminterval[2]);
                            int res = 0;
                            int cdias = 0;
                            while (dias > 0)
                            {
                                res = dias % 2;
                                days[cdias] = res.ToString();
                                dias = dias / 2;
                                cdias++;
                            }
                            for (int i = 0; i < days.Length; i++)
                            {
                                if (days[i] != null)
                                {
                                    if (i == 0 && days[i].Equals("1"))
                                        this.wday1.Checked = true;
                                    if (i == 1 && days[i].Equals("1"))
                                        this.wday2.Checked = true;
                                    if (i == 2 && days[i].Equals("1"))
                                        this.wday3.Checked = true;
                                    if (i == 3 && days[i].Equals("1"))
                                        this.wday4.Checked = true;
                                    if (i == 4 && days[i].Equals("1"))
                                        this.wday5.Checked = true;
                                    if (i == 5 && days[i].Equals("1"))
                                        this.wday6.Checked = true;
                                    if (i == 6 && days[i].Equals("1"))
                                        this.wday7.Checked = true;
                                }
                            }
                        }
                        if (tipo.Equals("2"))
                        {
                            this.periodmonth.Checked = true;
                            if (siteminterval[6] != null)
                            {
                                this.periodmont1.Checked = true;
                                this.mday.Text = siteminterval[6];
                                this.mmonth.Text = siteminterval[4];
                            }
                            else
                            {
                                this.periodmont2.Checked = true;
                                string[] days = { "", "", "", "", "", "", "" };
                                int dias = System.Convert.ToInt32(siteminterval[2]);
                                int res = 0;
                                int cdias = 0;
                                while (dias > 0)
                                {
                                    res = dias % 2;
                                    days[cdias] = res.ToString();
                                    dias = dias / 2;
                                    cdias++;
                                }
                                for (int i = 0; i < days.Length; i++)
                                {
                                    if (i == 0 && days[i].Equals("1"))
                                        this.mday1.Checked = true;
                                    if (i == 1 && days[i].Equals("1"))
                                        this.mday2.Checked = true;
                                    if (i == 2 && days[i].Equals("1"))
                                        this.mday3.Checked = true;
                                    if (i == 3 && days[i].Equals("1"))
                                        this.mday4.Checked = true;
                                    if (i == 4 && days[i].Equals("1"))
                                        this.mday5.Checked = true;
                                    if (i == 5 && days[i].Equals("1"))
                                        this.mday6.Checked = true;
                                    if (i == 6 && days[i].Equals("1"))
                                        this.mday7.Checked = true;
                                }
                                if (siteminterval[3] != null)
                                {
                                    this.mweek.SelectedItem = Int32.Parse(siteminterval[3]) - 1;
                                }
                            }
                        }
                        if (tipo.Equals("3"))
                        {
                            this.periodyear.Checked = true;
                            if (siteminterval[6] != null)
                            {
                                this.periodyear1.Checked = true;
                                this.yday.Text = siteminterval[6];
                                if (siteminterval[7] != null)
                                {
                                    this.ymonth.SelectedIndex = Int32.Parse(siteminterval[7]) - 1;
                                }
                                this.yyear.Text = siteminterval[5];
                            }
                            else
                            {
                                this.periodyear2.Checked = true;

                                string[] days = { "", "", "", "", "", "", "" };
                                int dias = System.Convert.ToInt32(siteminterval[2]);
                                int res = 0;
                                int cdias = 0;
                                while (dias > 0)
                                {
                                    res = dias % 2;
                                    days[cdias] = res.ToString();
                                    dias = dias / 2;
                                    cdias++;
                                }
                                for (int i = 0; i < days.Length; i++)
                                {
                                    if (i == 0 && days[i].Equals("1"))
                                        this.yday1.Checked = true;
                                    if (i == 1 && days[i].Equals("1"))
                                        this.yday2.Checked = true;
                                    if (i == 2 && days[i].Equals("1"))
                                        this.yday3.Checked = true;
                                    if (i == 3 && days[i].Equals("1"))
                                        this.yday4.Checked = true;
                                    if (i == 4 && days[i].Equals("1"))
                                        this.yday5.Checked = true;
                                    if (i == 5 && days[i].Equals("1"))
                                        this.yday6.Checked = true;
                                    if (i == 6 && days[i].Equals("1"))
                                        this.yday7.Checked = true;
                                }
                                if (siteminterval[3] != null)
                                {
                                    this.yweek.SelectedIndex = Int32.Parse(siteminterval[3]) - 1;
                                }
                                if (siteminterval[7] != null)
                                {
                                    this.ymonth2.SelectedIndex = Int32.Parse(siteminterval[7]) - 1;
                                }
                                this.yyear2.Text = siteminterval[5];
                            }
                        }


                        #endregion
                    }
                }
            }

        }
		/// <summary>
		/// Clean up any resources being used.
		/// </summary>
		protected override void Dispose( bool disposing )
		{
			if( disposing )
			{
				if(components != null)
				{
					components.Dispose();
				}
			}
			base.Dispose( disposing );
		}

		#region Windows Form Designer generated code
		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(FrmPeriodicidad));
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.dateTimeendDate = new System.Windows.Forms.DateTimePicker();
            this.dateTimeInitDate = new System.Windows.Forms.DateTimePicker();
            this.sinfecha = new System.Windows.Forms.RadioButton();
            this.endselect = new System.Windows.Forms.RadioButton();
            this.label9 = new System.Windows.Forms.Label();
            this.buttonAvanzado = new System.Windows.Forms.Button();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.groupBox9 = new System.Windows.Forms.GroupBox();
            this.yyear = new System.Windows.Forms.NumericUpDown();
            this.yyear2 = new System.Windows.Forms.NumericUpDown();
            this.yday = new System.Windows.Forms.NumericUpDown();
            this.panel1 = new System.Windows.Forms.Panel();
            this.label18 = new System.Windows.Forms.Label();
            this.label17 = new System.Windows.Forms.Label();
            this.label16 = new System.Windows.Forms.Label();
            this.ymonth = new System.Windows.Forms.ComboBox();
            this.periodyear1 = new System.Windows.Forms.RadioButton();
            this.label21 = new System.Windows.Forms.Label();
            this.label20 = new System.Windows.Forms.Label();
            this.label19 = new System.Windows.Forms.Label();
            this.yday7 = new System.Windows.Forms.CheckBox();
            this.yday6 = new System.Windows.Forms.CheckBox();
            this.yday5 = new System.Windows.Forms.CheckBox();
            this.yday4 = new System.Windows.Forms.CheckBox();
            this.yday3 = new System.Windows.Forms.CheckBox();
            this.yday2 = new System.Windows.Forms.CheckBox();
            this.yday1 = new System.Windows.Forms.CheckBox();
            this.ymonth2 = new System.Windows.Forms.ComboBox();
            this.yweek = new System.Windows.Forms.ComboBox();
            this.periodyear2 = new System.Windows.Forms.RadioButton();
            this.periodweek = new System.Windows.Forms.RadioButton();
            this.groupBox8 = new System.Windows.Forms.GroupBox();
            this.mmonth2 = new System.Windows.Forms.NumericUpDown();
            this.mmonth = new System.Windows.Forms.NumericUpDown();
            this.mday = new System.Windows.Forms.NumericUpDown();
            this.panel3 = new System.Windows.Forms.Panel();
            this.label13 = new System.Windows.Forms.Label();
            this.label10 = new System.Windows.Forms.Label();
            this.periodmont1 = new System.Windows.Forms.RadioButton();
            this.label15 = new System.Windows.Forms.Label();
            this.label14 = new System.Windows.Forms.Label();
            this.mday7 = new System.Windows.Forms.CheckBox();
            this.mday6 = new System.Windows.Forms.CheckBox();
            this.mday5 = new System.Windows.Forms.CheckBox();
            this.mday4 = new System.Windows.Forms.CheckBox();
            this.mday3 = new System.Windows.Forms.CheckBox();
            this.mday2 = new System.Windows.Forms.CheckBox();
            this.mday1 = new System.Windows.Forms.CheckBox();
            this.periodmont2 = new System.Windows.Forms.RadioButton();
            this.mweek = new System.Windows.Forms.ComboBox();
            this.groupBox7 = new System.Windows.Forms.GroupBox();
            this.wday4 = new System.Windows.Forms.CheckBox();
            this.wday7 = new System.Windows.Forms.CheckBox();
            this.wday6 = new System.Windows.Forms.CheckBox();
            this.wday5 = new System.Windows.Forms.CheckBox();
            this.wday3 = new System.Windows.Forms.CheckBox();
            this.wday2 = new System.Windows.Forms.CheckBox();
            this.wday1 = new System.Windows.Forms.CheckBox();
            this.groupBox5 = new System.Windows.Forms.GroupBox();
            this.groupBox4 = new System.Windows.Forms.GroupBox();
            this.groupBox3 = new System.Windows.Forms.GroupBox();
            this.periodyear = new System.Windows.Forms.RadioButton();
            this.periodmonth = new System.Windows.Forms.RadioButton();
            this.groupBox11 = new System.Windows.Forms.GroupBox();
            this.label3 = new System.Windows.Forms.Label();
            this.textBoxTitle = new System.Windows.Forms.TextBox();
            this.groupBox6 = new System.Windows.Forms.GroupBox();
            this.checkBoxPorHora = new System.Windows.Forms.CheckBox();
            this.dateTimePickerHoraFin = new System.Windows.Forms.DateTimePicker();
            this.label2 = new System.Windows.Forms.Label();
            this.dateTimePickerHoraInicio = new System.Windows.Forms.DateTimePicker();
            this.label1 = new System.Windows.Forms.Label();
            this.periodicidad = new System.Windows.Forms.CheckBox();
            this.buttonCancelar = new System.Windows.Forms.Button();
            this.buttonAceptar = new System.Windows.Forms.Button();
            this.checkBoxActive = new System.Windows.Forms.CheckBox();
            this.groupBox2.SuspendLayout();
            this.groupBox1.SuspendLayout();
            this.groupBox9.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.yyear)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.yyear2)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.yday)).BeginInit();
            this.groupBox8.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.mmonth2)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.mmonth)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.mday)).BeginInit();
            this.groupBox7.SuspendLayout();
            this.groupBox11.SuspendLayout();
            this.groupBox6.SuspendLayout();
            this.SuspendLayout();
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.dateTimeendDate);
            this.groupBox2.Controls.Add(this.dateTimeInitDate);
            this.groupBox2.Controls.Add(this.sinfecha);
            this.groupBox2.Controls.Add(this.endselect);
            this.groupBox2.Controls.Add(this.label9);
            this.groupBox2.Controls.Add(this.buttonAvanzado);
            resources.ApplyResources(this.groupBox2, "groupBox2");
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.TabStop = false;
            // 
            // dateTimeendDate
            // 
            resources.ApplyResources(this.dateTimeendDate, "dateTimeendDate");
            this.dateTimeendDate.Name = "dateTimeendDate";
            // 
            // dateTimeInitDate
            // 
            resources.ApplyResources(this.dateTimeInitDate, "dateTimeInitDate");
            this.dateTimeInitDate.Name = "dateTimeInitDate";
            // 
            // sinfecha
            // 
            this.sinfecha.Checked = true;
            resources.ApplyResources(this.sinfecha, "sinfecha");
            this.sinfecha.Name = "sinfecha";
            this.sinfecha.TabStop = true;
            this.sinfecha.CheckedChanged += new System.EventHandler(this.sinfecha_CheckedChanged);
            // 
            // endselect
            // 
            resources.ApplyResources(this.endselect, "endselect");
            this.endselect.Name = "endselect";
            this.endselect.CheckedChanged += new System.EventHandler(this.endselect_CheckedChanged);
            // 
            // label9
            // 
            resources.ApplyResources(this.label9, "label9");
            this.label9.Name = "label9";
            // 
            // buttonAvanzado
            // 
            resources.ApplyResources(this.buttonAvanzado, "buttonAvanzado");
            this.buttonAvanzado.Name = "buttonAvanzado";
            this.buttonAvanzado.Click += new System.EventHandler(this.buttonAvanzado_Click);
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.groupBox9);
            this.groupBox1.Controls.Add(this.periodweek);
            this.groupBox1.Controls.Add(this.groupBox8);
            this.groupBox1.Controls.Add(this.groupBox7);
            this.groupBox1.Controls.Add(this.groupBox5);
            this.groupBox1.Controls.Add(this.groupBox4);
            this.groupBox1.Controls.Add(this.groupBox3);
            this.groupBox1.Controls.Add(this.periodyear);
            this.groupBox1.Controls.Add(this.periodmonth);
            resources.ApplyResources(this.groupBox1, "groupBox1");
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.TabStop = false;
            // 
            // groupBox9
            // 
            this.groupBox9.Controls.Add(this.yyear);
            this.groupBox9.Controls.Add(this.yyear2);
            this.groupBox9.Controls.Add(this.yday);
            this.groupBox9.Controls.Add(this.panel1);
            this.groupBox9.Controls.Add(this.label18);
            this.groupBox9.Controls.Add(this.label17);
            this.groupBox9.Controls.Add(this.label16);
            this.groupBox9.Controls.Add(this.ymonth);
            this.groupBox9.Controls.Add(this.periodyear1);
            this.groupBox9.Controls.Add(this.label21);
            this.groupBox9.Controls.Add(this.label20);
            this.groupBox9.Controls.Add(this.label19);
            this.groupBox9.Controls.Add(this.yday7);
            this.groupBox9.Controls.Add(this.yday6);
            this.groupBox9.Controls.Add(this.yday5);
            this.groupBox9.Controls.Add(this.yday4);
            this.groupBox9.Controls.Add(this.yday3);
            this.groupBox9.Controls.Add(this.yday2);
            this.groupBox9.Controls.Add(this.yday1);
            this.groupBox9.Controls.Add(this.ymonth2);
            this.groupBox9.Controls.Add(this.yweek);
            this.groupBox9.Controls.Add(this.periodyear2);
            resources.ApplyResources(this.groupBox9, "groupBox9");
            this.groupBox9.Name = "groupBox9";
            this.groupBox9.TabStop = false;
            // 
            // yyear
            // 
            resources.ApplyResources(this.yyear, "yyear");
            this.yyear.Minimum = new decimal(new int[] {
            1,
            0,
            0,
            0});
            this.yyear.Name = "yyear";
            this.yyear.Value = new decimal(new int[] {
            1,
            0,
            0,
            0});
            // 
            // yyear2
            // 
            resources.ApplyResources(this.yyear2, "yyear2");
            this.yyear2.Minimum = new decimal(new int[] {
            1,
            0,
            0,
            0});
            this.yyear2.Name = "yyear2";
            this.yyear2.Value = new decimal(new int[] {
            1,
            0,
            0,
            0});
            // 
            // yday
            // 
            resources.ApplyResources(this.yday, "yday");
            this.yday.Maximum = new decimal(new int[] {
            31,
            0,
            0,
            0});
            this.yday.Minimum = new decimal(new int[] {
            1,
            0,
            0,
            0});
            this.yday.Name = "yday";
            this.yday.Value = new decimal(new int[] {
            1,
            0,
            0,
            0});
            // 
            // panel1
            // 
            this.panel1.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            resources.ApplyResources(this.panel1, "panel1");
            this.panel1.Name = "panel1";
            // 
            // label18
            // 
            resources.ApplyResources(this.label18, "label18");
            this.label18.Name = "label18";
            // 
            // label17
            // 
            resources.ApplyResources(this.label17, "label17");
            this.label17.Name = "label17";
            // 
            // label16
            // 
            resources.ApplyResources(this.label16, "label16");
            this.label16.Name = "label16";
            // 
            // ymonth
            // 
            this.ymonth.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            resources.ApplyResources(this.ymonth, "ymonth");
            this.ymonth.Items.AddRange(new object[] {
            resources.GetString("ymonth.Items"),
            resources.GetString("ymonth.Items1"),
            resources.GetString("ymonth.Items2"),
            resources.GetString("ymonth.Items3"),
            resources.GetString("ymonth.Items4"),
            resources.GetString("ymonth.Items5"),
            resources.GetString("ymonth.Items6"),
            resources.GetString("ymonth.Items7"),
            resources.GetString("ymonth.Items8"),
            resources.GetString("ymonth.Items9"),
            resources.GetString("ymonth.Items10"),
            resources.GetString("ymonth.Items11")});
            this.ymonth.Name = "ymonth";
            // 
            // periodyear1
            // 
            resources.ApplyResources(this.periodyear1, "periodyear1");
            this.periodyear1.Name = "periodyear1";
            this.periodyear1.CheckedChanged += new System.EventHandler(this.periodyear1_CheckedChanged);
            // 
            // label21
            // 
            resources.ApplyResources(this.label21, "label21");
            this.label21.Name = "label21";
            // 
            // label20
            // 
            resources.ApplyResources(this.label20, "label20");
            this.label20.Name = "label20";
            // 
            // label19
            // 
            resources.ApplyResources(this.label19, "label19");
            this.label19.Name = "label19";
            // 
            // yday7
            // 
            resources.ApplyResources(this.yday7, "yday7");
            this.yday7.Name = "yday7";
            // 
            // yday6
            // 
            resources.ApplyResources(this.yday6, "yday6");
            this.yday6.Name = "yday6";
            // 
            // yday5
            // 
            resources.ApplyResources(this.yday5, "yday5");
            this.yday5.Name = "yday5";
            // 
            // yday4
            // 
            resources.ApplyResources(this.yday4, "yday4");
            this.yday4.Name = "yday4";
            // 
            // yday3
            // 
            resources.ApplyResources(this.yday3, "yday3");
            this.yday3.Name = "yday3";
            // 
            // yday2
            // 
            resources.ApplyResources(this.yday2, "yday2");
            this.yday2.Name = "yday2";
            // 
            // yday1
            // 
            resources.ApplyResources(this.yday1, "yday1");
            this.yday1.Name = "yday1";
            // 
            // ymonth2
            // 
            this.ymonth2.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            resources.ApplyResources(this.ymonth2, "ymonth2");
            this.ymonth2.Items.AddRange(new object[] {
            resources.GetString("ymonth2.Items"),
            resources.GetString("ymonth2.Items1"),
            resources.GetString("ymonth2.Items2"),
            resources.GetString("ymonth2.Items3"),
            resources.GetString("ymonth2.Items4"),
            resources.GetString("ymonth2.Items5"),
            resources.GetString("ymonth2.Items6"),
            resources.GetString("ymonth2.Items7"),
            resources.GetString("ymonth2.Items8"),
            resources.GetString("ymonth2.Items9"),
            resources.GetString("ymonth2.Items10"),
            resources.GetString("ymonth2.Items11")});
            this.ymonth2.Name = "ymonth2";
            // 
            // yweek
            // 
            this.yweek.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            resources.ApplyResources(this.yweek, "yweek");
            this.yweek.Items.AddRange(new object[] {
            resources.GetString("yweek.Items"),
            resources.GetString("yweek.Items1"),
            resources.GetString("yweek.Items2"),
            resources.GetString("yweek.Items3"),
            resources.GetString("yweek.Items4")});
            this.yweek.Name = "yweek";
            // 
            // periodyear2
            // 
            this.periodyear2.Checked = true;
            resources.ApplyResources(this.periodyear2, "periodyear2");
            this.periodyear2.Name = "periodyear2";
            this.periodyear2.TabStop = true;
            this.periodyear2.CheckedChanged += new System.EventHandler(this.periodyear2_CheckedChanged);
            // 
            // periodweek
            // 
            this.periodweek.Checked = true;
            resources.ApplyResources(this.periodweek, "periodweek");
            this.periodweek.Name = "periodweek";
            this.periodweek.TabStop = true;
            this.periodweek.CheckedChanged += new System.EventHandler(this.periodweek_CheckedChanged);
            // 
            // groupBox8
            // 
            this.groupBox8.Controls.Add(this.mmonth2);
            this.groupBox8.Controls.Add(this.mmonth);
            this.groupBox8.Controls.Add(this.mday);
            this.groupBox8.Controls.Add(this.panel3);
            this.groupBox8.Controls.Add(this.label13);
            this.groupBox8.Controls.Add(this.label10);
            this.groupBox8.Controls.Add(this.periodmont1);
            this.groupBox8.Controls.Add(this.label15);
            this.groupBox8.Controls.Add(this.label14);
            this.groupBox8.Controls.Add(this.mday7);
            this.groupBox8.Controls.Add(this.mday6);
            this.groupBox8.Controls.Add(this.mday5);
            this.groupBox8.Controls.Add(this.mday4);
            this.groupBox8.Controls.Add(this.mday3);
            this.groupBox8.Controls.Add(this.mday2);
            this.groupBox8.Controls.Add(this.mday1);
            this.groupBox8.Controls.Add(this.periodmont2);
            this.groupBox8.Controls.Add(this.mweek);
            resources.ApplyResources(this.groupBox8, "groupBox8");
            this.groupBox8.Name = "groupBox8";
            this.groupBox8.TabStop = false;
            // 
            // mmonth2
            // 
            resources.ApplyResources(this.mmonth2, "mmonth2");
            this.mmonth2.Maximum = new decimal(new int[] {
            12,
            0,
            0,
            0});
            this.mmonth2.Minimum = new decimal(new int[] {
            1,
            0,
            0,
            0});
            this.mmonth2.Name = "mmonth2";
            this.mmonth2.Value = new decimal(new int[] {
            1,
            0,
            0,
            0});
            // 
            // mmonth
            // 
            resources.ApplyResources(this.mmonth, "mmonth");
            this.mmonth.Maximum = new decimal(new int[] {
            12,
            0,
            0,
            0});
            this.mmonth.Minimum = new decimal(new int[] {
            1,
            0,
            0,
            0});
            this.mmonth.Name = "mmonth";
            this.mmonth.Value = new decimal(new int[] {
            1,
            0,
            0,
            0});
            // 
            // mday
            // 
            resources.ApplyResources(this.mday, "mday");
            this.mday.Maximum = new decimal(new int[] {
            31,
            0,
            0,
            0});
            this.mday.Minimum = new decimal(new int[] {
            1,
            0,
            0,
            0});
            this.mday.Name = "mday";
            this.mday.Value = new decimal(new int[] {
            1,
            0,
            0,
            0});
            // 
            // panel3
            // 
            this.panel3.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            resources.ApplyResources(this.panel3, "panel3");
            this.panel3.Name = "panel3";
            // 
            // label13
            // 
            resources.ApplyResources(this.label13, "label13");
            this.label13.Name = "label13";
            // 
            // label10
            // 
            resources.ApplyResources(this.label10, "label10");
            this.label10.Name = "label10";
            // 
            // periodmont1
            // 
            resources.ApplyResources(this.periodmont1, "periodmont1");
            this.periodmont1.Name = "periodmont1";
            this.periodmont1.CheckedChanged += new System.EventHandler(this.periodmont1_CheckedChanged);
            // 
            // label15
            // 
            resources.ApplyResources(this.label15, "label15");
            this.label15.Name = "label15";
            // 
            // label14
            // 
            resources.ApplyResources(this.label14, "label14");
            this.label14.Name = "label14";
            // 
            // mday7
            // 
            resources.ApplyResources(this.mday7, "mday7");
            this.mday7.Name = "mday7";
            // 
            // mday6
            // 
            resources.ApplyResources(this.mday6, "mday6");
            this.mday6.Name = "mday6";
            // 
            // mday5
            // 
            resources.ApplyResources(this.mday5, "mday5");
            this.mday5.Name = "mday5";
            // 
            // mday4
            // 
            resources.ApplyResources(this.mday4, "mday4");
            this.mday4.Name = "mday4";
            // 
            // mday3
            // 
            resources.ApplyResources(this.mday3, "mday3");
            this.mday3.Name = "mday3";
            // 
            // mday2
            // 
            resources.ApplyResources(this.mday2, "mday2");
            this.mday2.Name = "mday2";
            // 
            // mday1
            // 
            resources.ApplyResources(this.mday1, "mday1");
            this.mday1.Name = "mday1";
            // 
            // periodmont2
            // 
            this.periodmont2.Checked = true;
            resources.ApplyResources(this.periodmont2, "periodmont2");
            this.periodmont2.Name = "periodmont2";
            this.periodmont2.TabStop = true;
            this.periodmont2.CheckedChanged += new System.EventHandler(this.periodmont2_CheckedChanged);
            // 
            // mweek
            // 
            this.mweek.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            resources.ApplyResources(this.mweek, "mweek");
            this.mweek.Items.AddRange(new object[] {
            resources.GetString("mweek.Items"),
            resources.GetString("mweek.Items1"),
            resources.GetString("mweek.Items2"),
            resources.GetString("mweek.Items3"),
            resources.GetString("mweek.Items4")});
            this.mweek.Name = "mweek";
            this.mweek.SelectedIndexChanged += new System.EventHandler(this.mweek_SelectedIndexChanged);
            // 
            // groupBox7
            // 
            this.groupBox7.Controls.Add(this.wday4);
            this.groupBox7.Controls.Add(this.wday7);
            this.groupBox7.Controls.Add(this.wday6);
            this.groupBox7.Controls.Add(this.wday5);
            this.groupBox7.Controls.Add(this.wday3);
            this.groupBox7.Controls.Add(this.wday2);
            this.groupBox7.Controls.Add(this.wday1);
            resources.ApplyResources(this.groupBox7, "groupBox7");
            this.groupBox7.Name = "groupBox7";
            this.groupBox7.TabStop = false;
            // 
            // wday4
            // 
            resources.ApplyResources(this.wday4, "wday4");
            this.wday4.Name = "wday4";
            // 
            // wday7
            // 
            resources.ApplyResources(this.wday7, "wday7");
            this.wday7.Name = "wday7";
            // 
            // wday6
            // 
            resources.ApplyResources(this.wday6, "wday6");
            this.wday6.Name = "wday6";
            // 
            // wday5
            // 
            resources.ApplyResources(this.wday5, "wday5");
            this.wday5.Name = "wday5";
            // 
            // wday3
            // 
            resources.ApplyResources(this.wday3, "wday3");
            this.wday3.Name = "wday3";
            // 
            // wday2
            // 
            resources.ApplyResources(this.wday2, "wday2");
            this.wday2.Name = "wday2";
            // 
            // wday1
            // 
            resources.ApplyResources(this.wday1, "wday1");
            this.wday1.Name = "wday1";
            // 
            // groupBox5
            // 
            this.groupBox5.BackColor = System.Drawing.SystemColors.Info;
            resources.ApplyResources(this.groupBox5, "groupBox5");
            this.groupBox5.Name = "groupBox5";
            this.groupBox5.TabStop = false;
            // 
            // groupBox4
            // 
            this.groupBox4.BackColor = System.Drawing.SystemColors.Info;
            resources.ApplyResources(this.groupBox4, "groupBox4");
            this.groupBox4.Name = "groupBox4";
            this.groupBox4.TabStop = false;
            // 
            // groupBox3
            // 
            this.groupBox3.BackColor = System.Drawing.SystemColors.Info;
            resources.ApplyResources(this.groupBox3, "groupBox3");
            this.groupBox3.Name = "groupBox3";
            this.groupBox3.TabStop = false;
            // 
            // periodyear
            // 
            resources.ApplyResources(this.periodyear, "periodyear");
            this.periodyear.Name = "periodyear";
            this.periodyear.CheckedChanged += new System.EventHandler(this.periodyear_CheckedChanged);
            // 
            // periodmonth
            // 
            resources.ApplyResources(this.periodmonth, "periodmonth");
            this.periodmonth.Name = "periodmonth";
            this.periodmonth.CheckedChanged += new System.EventHandler(this.periodmonth_CheckedChanged);
            // 
            // groupBox11
            // 
            this.groupBox11.Controls.Add(this.checkBoxActive);
            this.groupBox11.Controls.Add(this.label3);
            this.groupBox11.Controls.Add(this.textBoxTitle);
            this.groupBox11.Controls.Add(this.groupBox6);
            this.groupBox11.Controls.Add(this.periodicidad);
            this.groupBox11.Controls.Add(this.groupBox2);
            resources.ApplyResources(this.groupBox11, "groupBox11");
            this.groupBox11.Name = "groupBox11";
            this.groupBox11.TabStop = false;
            // 
            // label3
            // 
            resources.ApplyResources(this.label3, "label3");
            this.label3.Name = "label3";
            // 
            // textBoxTitle
            // 
            resources.ApplyResources(this.textBoxTitle, "textBoxTitle");
            this.textBoxTitle.Name = "textBoxTitle";
            // 
            // groupBox6
            // 
            this.groupBox6.Controls.Add(this.checkBoxPorHora);
            this.groupBox6.Controls.Add(this.dateTimePickerHoraFin);
            this.groupBox6.Controls.Add(this.label2);
            this.groupBox6.Controls.Add(this.dateTimePickerHoraInicio);
            this.groupBox6.Controls.Add(this.label1);
            resources.ApplyResources(this.groupBox6, "groupBox6");
            this.groupBox6.Name = "groupBox6";
            this.groupBox6.TabStop = false;
            // 
            // checkBoxPorHora
            // 
            resources.ApplyResources(this.checkBoxPorHora, "checkBoxPorHora");
            this.checkBoxPorHora.Name = "checkBoxPorHora";
            this.checkBoxPorHora.CheckedChanged += new System.EventHandler(this.checkBoxPorHora_CheckedChanged);
            // 
            // dateTimePickerHoraFin
            // 
            resources.ApplyResources(this.dateTimePickerHoraFin, "dateTimePickerHoraFin");
            this.dateTimePickerHoraFin.Format = System.Windows.Forms.DateTimePickerFormat.Custom;
            this.dateTimePickerHoraFin.Name = "dateTimePickerHoraFin";
            this.dateTimePickerHoraFin.ShowUpDown = true;
            this.dateTimePickerHoraFin.Value = new System.DateTime(2004, 8, 30, 23, 59, 10, 0);
            // 
            // label2
            // 
            resources.ApplyResources(this.label2, "label2");
            this.label2.Name = "label2";
            // 
            // dateTimePickerHoraInicio
            // 
            resources.ApplyResources(this.dateTimePickerHoraInicio, "dateTimePickerHoraInicio");
            this.dateTimePickerHoraInicio.Format = System.Windows.Forms.DateTimePickerFormat.Custom;
            this.dateTimePickerHoraInicio.Name = "dateTimePickerHoraInicio";
            this.dateTimePickerHoraInicio.ShowUpDown = true;
            this.dateTimePickerHoraInicio.Value = new System.DateTime(2004, 8, 6, 0, 0, 0, 0);
            // 
            // label1
            // 
            resources.ApplyResources(this.label1, "label1");
            this.label1.Name = "label1";
            // 
            // periodicidad
            // 
            resources.ApplyResources(this.periodicidad, "periodicidad");
            this.periodicidad.Name = "periodicidad";
            this.periodicidad.CheckedChanged += new System.EventHandler(this.periodicidad_CheckedChanged);
            // 
            // buttonCancelar
            // 
            this.buttonCancelar.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            resources.ApplyResources(this.buttonCancelar, "buttonCancelar");
            this.buttonCancelar.Name = "buttonCancelar";
            this.buttonCancelar.Click += new System.EventHandler(this.buttonCancelar_Click);
            // 
            // buttonAceptar
            // 
            resources.ApplyResources(this.buttonAceptar, "buttonAceptar");
            this.buttonAceptar.Name = "buttonAceptar";
            this.buttonAceptar.Click += new System.EventHandler(this.buttonAceptar_Click);
            // 
            // checkBoxActive
            // 
            resources.ApplyResources(this.checkBoxActive, "checkBoxActive");
            this.checkBoxActive.Name = "checkBoxActive";
            this.checkBoxActive.UseVisualStyleBackColor = true;
            // 
            // FrmPeriodicidad
            // 
            this.AcceptButton = this.buttonAceptar;
            resources.ApplyResources(this, "$this");
            this.CancelButton = this.buttonCancelar;
            this.Controls.Add(this.buttonAceptar);
            this.Controls.Add(this.buttonCancelar);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.groupBox11);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "FrmPeriodicidad";
            this.groupBox2.ResumeLayout(false);
            this.groupBox1.ResumeLayout(false);
            this.groupBox9.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.yyear)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.yyear2)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.yday)).EndInit();
            this.groupBox8.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.mmonth2)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.mmonth)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.mday)).EndInit();
            this.groupBox7.ResumeLayout(false);
            this.groupBox11.ResumeLayout(false);
            this.groupBox11.PerformLayout();
            this.groupBox6.ResumeLayout(false);
            this.ResumeLayout(false);

		}
		#endregion

		private void buttonCancelar_Click(object sender, System.EventArgs e)
		{
            this.DialogResult = DialogResult.Cancel;
			this.Close();
        }

		private void buttonAceptar_Click(object sender, System.EventArgs e)
		{

			
			#region validación de periodicidad
			if(this.textBoxTitle.Text.Trim()=="")
			{
				this.Cursor=Cursors.Default; 
				MessageBox.Show(this,"¡Debe indicar el título de la calendarización!",this.Text,MessageBoxButtons.OK,MessageBoxIcon.Error);  
				return;
			}
			/*foreach(ListViewItem itemlist in this.list.Items)
			{
				if(itemlist!=this.item && itemlist.Text.Trim()==this.textBox1.Text.Trim())
				{
					this.Cursor=Cursors.Default;
                    MessageBox.Show(this, "Ya existe una calendarización con el nombre " + this.textBox1.Text.Trim() + "", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);  
					return;
				}
			}*/
			
			if(this.dateTimePickerHoraFin.Value<this.dateTimePickerHoraInicio.Value)
			{
				this.Cursor=Cursors.Default; 
				MessageBox.Show(this,"La hora de inicio es mayor que la hora de término",this.Text,MessageBoxButtons.OK,MessageBoxIcon.Error);  
				return;
			}
			if(periodicidad.Checked)
			{
				if(periodweek.Checked)
				{
					if(!(wday1.Checked || wday2.Checked  || wday3.Checked || wday4.Checked || wday5.Checked || wday6.Checked || wday7.Checked))
					{
						this.Cursor=Cursors.Default; 
						MessageBox.Show(this,"¡Debe indicar un día de la semana!",this.Text,MessageBoxButtons.OK,MessageBoxIcon.Error);  
						return;
					}
				}
				if(periodmonth.Checked)
				{
					if(periodmont2.Checked)
					{
						if(!(mday1.Checked || mday2.Checked  || mday3.Checked || mday4.Checked || mday5.Checked || mday6.Checked || mday7.Checked))
						{
							this.Cursor=Cursors.Default;
                            MessageBox.Show(this, "¡Debe indicar un día de la semana!", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);  
							return ;
						}
					}
					else
					{						
						try
						{
							int idia=(int)mday.Value;							
						}
						catch(Exception ue)
						{
                            OfficeApplication.WriteError(ue);
						}
						try
						{
                            int imes = (int)mmonth.Value;					
						}
						catch(Exception ue)
						{
                            OfficeApplication.WriteError(ue);
						}
					}
				}
				if(periodyear.Checked)
				{
					if(periodyear2.Checked)
					{
						if(!(yday1.Checked || yday2.Checked  || yday3.Checked || yday4.Checked || yday5.Checked || yday6.Checked || yday7.Checked))
						{
							this.Cursor=Cursors.Default;
                            MessageBox.Show(this, "¡Debe indicar un día de la semana!", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);  
							return;
						}
						
						try
						{
                            int imes = (int)yyear2.Value; 							
						}
						catch(Exception ue)
						{
                            OfficeApplication.WriteError(ue);
						}

					}
					else
					{
						
						try
						{
                            int idia = (int)yday.Value;							
						}
						catch(Exception ue)
						{
                            OfficeApplication.WriteError(ue);
						}
						try
						{
                            int imes = (int)yyear.Value;
														
						}
						catch(Exception ue)
						{
                            OfficeApplication.WriteError(ue);
						}
					}
				}
			}
			if(this.dateTimeInitDate.Enabled && this.dateTimeendDate.Enabled)
			{
				if(this.dateTimeInitDate.Value>this.dateTimeendDate.Value)
				{
					this.Cursor=Cursors.Default; 
					MessageBox.Show(this,"¡La fecha de inicio es mayor que la fecha final!",this.Text,MessageBoxButtons.OK,MessageBoxIcon.Error);  
					return;					
				}
			}
			#endregion			
						
			
            this.DialogResult = DialogResult.OK;
			this.Close();
		}

		
		private void endselect_CheckedChanged(object sender, System.EventArgs e)
		{
			dateTimeendDate.Enabled=true;
		}

		private void sinfecha_CheckedChanged(object sender, System.EventArgs e)
		{
			dateTimeendDate.Enabled=false; 
		}

		private void periodicidad_CheckedChanged(object sender, System.EventArgs e)
		{
			if(periodicidad.Checked) 
			{				
				this.groupBox1.Enabled=true;				
				
			}
			else
			{
				this.groupBox1.Enabled=false;				
			}
		}

		private void periodweek_CheckedChanged(object sender, System.EventArgs e)
		{
			if(periodweek.Checked)
			{
				groupBox7.Enabled=true;
			}
			else
			{
				groupBox7.Enabled=false;
			}
			if(periodmonth.Checked)
			{
				
				groupBox8.Enabled=true; 
			}
			else
			{
				
				groupBox8.Enabled=false;
			}
			if(periodyear.Checked)
			{
				groupBox9.Enabled=true;
				
			}
			else
			{
				groupBox9.Enabled=false;
				
			}
		}

		private void periodmonth_CheckedChanged(object sender, System.EventArgs e)
		{
			if(periodweek.Checked)
			{
				groupBox7.Enabled=true;
			}
			else
			{
				groupBox7.Enabled=false;
			}
			if(periodmonth.Checked)
			{
				
				groupBox8.Enabled=true; 
			}
			else
			{
				
				groupBox8.Enabled=false;
			}
			if(periodyear.Checked)
			{
				groupBox9.Enabled=true;
				
			}
			else
			{
				groupBox9.Enabled=false;
				
			}
		}

		private void periodyear_CheckedChanged(object sender, System.EventArgs e)
		{
			if(periodweek.Checked)
			{
				groupBox7.Enabled=true;
			}
			else
			{
				groupBox7.Enabled=false;
			}
			if(periodmonth.Checked)
			{
				
				groupBox8.Enabled=true; 
			}
			else
			{
				
				groupBox8.Enabled=false;
			}
			if(periodyear.Checked)
			{
				groupBox9.Enabled=true;
				
			}
			else
			{
				groupBox9.Enabled=false;
				
			}
		}

		private void periodmont1_CheckedChanged(object sender, System.EventArgs e)
		{
			
			if(this.periodmont2.Checked)
			{
				mweek.Enabled=true;
				mday1.Enabled=true;
				mday2.Enabled=true;
				mday3.Enabled=true;
				mday4.Enabled=true;
				mday5.Enabled=true;
				mday6.Enabled=true;
				mday7.Enabled=true;
				mmonth2.Enabled=true;

				mday.Enabled=false; 
				mmonth.Enabled=false;
			}
			else
			{
				mweek.Enabled=false;
				mday1.Enabled=false;
				mday2.Enabled=false;
				mday3.Enabled=false;
				mday4.Enabled=false;
				mday5.Enabled=false;
				mday6.Enabled=false;
				mday7.Enabled=false;
				mmonth2.Enabled=false;

				mday.Enabled=true; 
				mmonth.Enabled=true;
			}
		}

		private void mday_TextChanged(object sender, System.EventArgs e)
		{
		
		}

		private void mmonth_TextChanged(object sender, System.EventArgs e)
		{
		
		}

		private void mweek_SelectedIndexChanged(object sender, System.EventArgs e)
		{
		
		}

		private void periodmont2_CheckedChanged(object sender, System.EventArgs e)
		{
			
			if(this.periodmont2.Checked)
			{
				mweek.Enabled=true;
				mday1.Enabled=true;
				mday2.Enabled=true;
				mday3.Enabled=true;
				mday4.Enabled=true;
				mday5.Enabled=true;
				mday6.Enabled=true;
				mday7.Enabled=true;
				mmonth2.Enabled=true;

				mday.Enabled=false; 
				mmonth.Enabled=false;
			}
			else
			{
				mweek.Enabled=false;
				mday1.Enabled=false;
				mday2.Enabled=false;
				mday3.Enabled=false;
				mday4.Enabled=false;
				mday5.Enabled=false;
				mday6.Enabled=false;
				mday7.Enabled=false;
				mmonth2.Enabled=false;

				mday.Enabled=true; 
				mmonth.Enabled=true;
			}
		}

		private void periodyear2_CheckedChanged(object sender, System.EventArgs e)
		{
			
			if(this.periodyear2.Checked)
			{
				yweek.Enabled=true;
				yday1.Enabled=true;
				yday2.Enabled=true;
				yday3.Enabled=true;
				yday4.Enabled=true;
				yday5.Enabled=true;
				yday6.Enabled=true;
				yday7.Enabled=true;
				ymonth2.Enabled=true;
				yyear2.Enabled=true;

				yday.Enabled=false;
				ymonth.Enabled=false;
				yyear.Enabled=false; 
			}
			else
			{
				yweek.Enabled=false;
				yday1.Enabled=false;
				yday2.Enabled=false;
				yday3.Enabled=false;
				yday4.Enabled=false;
				yday5.Enabled=false;
				yday6.Enabled=false;
				yday7.Enabled=false;
				ymonth2.Enabled=false;
				yyear2.Enabled=false;

				yday.Enabled=true;
				ymonth.Enabled=true;
				yyear.Enabled=true; 
			}
		}

		private void periodyear1_CheckedChanged(object sender, System.EventArgs e)
		{
			
			if(this.periodyear2.Checked)
			{
				yweek.Enabled=true;
				yday1.Enabled=true;
				yday2.Enabled=true;
				yday3.Enabled=true;
				yday4.Enabled=true;
				yday5.Enabled=true;
				yday6.Enabled=true;
				yday7.Enabled=true;
				ymonth2.Enabled=true;
				yyear2.Enabled=true;

				yday.Enabled=false;
				ymonth.Enabled=false;
				yyear.Enabled=false; 
			}
			else
			{
				yweek.Enabled=false;
				yday1.Enabled=false;
				yday2.Enabled=false;
				yday3.Enabled=false;
				yday4.Enabled=false;
				yday5.Enabled=false;
				yday6.Enabled=false;
				yday7.Enabled=false;
				ymonth2.Enabled=false;
				yyear2.Enabled=false;

				yday.Enabled=true;
				ymonth.Enabled=true;
				yyear.Enabled=true; 
			}
		}

		private void mmonth2_KeyPress(object sender, System.Windows.Forms.KeyPressEventArgs e)
		{
			if(e.KeyChar==8)
			{
				return;
			}
			if(!(e.KeyChar>=48 && e.KeyChar<=57))
			{
				e.Handled=true; 
			}
		}

		private void mday_KeyPress(object sender, System.Windows.Forms.KeyPressEventArgs e)
		{
			if(e.KeyChar==8)
			{
				return;
			}
			if(!(e.KeyChar>=48 && e.KeyChar<=57))
			{
				e.Handled=true; 
			}
		}

		private void mmonth_KeyPress(object sender, System.Windows.Forms.KeyPressEventArgs e)
		{
			if(e.KeyChar==8)
			{
				return;
			}
			if(!(e.KeyChar>=48 && e.KeyChar<=57))
			{
				e.Handled=true; 
			}
		}

		private void yyear2_KeyPress(object sender, System.Windows.Forms.KeyPressEventArgs e)
		{
			if(e.KeyChar==8)
			{
				return;
			}
			if(!(e.KeyChar>=48 && e.KeyChar<=57))
			{
				e.Handled=true; 
			}
		}

		private void yday_KeyPress(object sender, System.Windows.Forms.KeyPressEventArgs e)
		{
			if(e.KeyChar==8)
			{
				return;
			}
			if(!(e.KeyChar>=48 && e.KeyChar<=57))
			{
				e.Handled=true; 
			}
		}

		private void yyear_KeyPress(object sender, System.Windows.Forms.KeyPressEventArgs e)
		{
			if(e.KeyChar==8)
			{
				return;
			}
			if(!(e.KeyChar>=48 && e.KeyChar<=57))
			{
				e.Handled=true; 
			}
		}

		private void buttonAvanzado_Click(object sender, System.EventArgs e)
		{
			if(!expanded)
			{
				this.Height=216;
				this.periodicidad.Visible=false;
				groupBox1.Visible=false;
				buttonAceptar.Top=152;
				this.buttonCancelar.Top=152;
				groupBox11.Height=135;
			}
			else
			{
				this.Height=608;
				this.periodicidad.Visible=true;
				groupBox1.Visible=true;
				buttonAceptar.Top=544;
				this.buttonCancelar.Top=544;
				groupBox11.Height=536;
			}
			
			expanded=!expanded;
		}

		private void checkBoxPorHora_CheckedChanged(object sender, System.EventArgs e)
		{
			if(this.checkBoxPorHora.Checked)
			{
				dateTimePickerHoraInicio.Enabled=true;
				this.dateTimePickerHoraFin.Enabled=true;
			}
			else
			{
				dateTimePickerHoraInicio.Enabled=false;
				this.dateTimePickerHoraFin.Enabled=false;
			}
		}
	}
}
