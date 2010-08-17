/**  
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
*  http://www.webbuilder.org.mx 
**/ 
 
﻿using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.IO;
using WBOffice4.Interfaces;
using XmlRpcLibrary;
using ICSharpCode.SharpZipLib.Zip;
namespace WBOffice4.Steps
{
    struct DriveInfo
    {
        private string letter;  //drive letter : "C:\","A:\"
        private int type;       //below
        private string name;    //Disc Label

        public int Type
        {
            get
            {
                return type;
            }
        }

        public int Icon
        {
            get
            {
                if (name == "My Computer") return 0;
                if (type == 5) return 3;//cd
                if (type == 3) return 2;//fixed
                if (type == 2) return 1;//removable
                if (type == 4) return 4;//remote disk
                if (type == 6) return 5;//ram disk
                return 6;//unknown
            }
        }

        public string Letter
        {
            get
            {
                if (letter != "") return " (" + letter + ")";
                else return "";
            }
        }
        public string Name
        {
            get
            {
                if (name != "") return name;
                else
                {
                    switch (this.Type)
                    {
                        case 3:
                            if (letter == System.IO.Directory.GetDirectoryRoot(
                                System.Environment.SystemDirectory))
                                return "System";
                            else
                                return "Local Disc";
                        case 5: return "CD Rom";
                        case 6: return "RAM Disc";
                        case 4: return "Network Drive";
                        case 2:
                            if (letter == "A:\\") return "3.5 Floppy";
                            else return "Removable Disc";
                        default: return "";
                    }
                }
            }
        }

        //TYPE:
        //5-A CD-ROM drive. 
        //3-A hard drive. 
        //6-A RAM disk. 
        //4-A network drive or a drive located on a network server. 
        //2-A floppy drive or some other removable-disk drive. 
        public DriveInfo(string strLetter, int intType, string strName)
        {
            letter = strLetter;
            name = strName;
            type = intType;
        }
    }
    internal partial class SelectDirectory : TSWizards.BaseInteriorStep
    {
        private String fileName;
        private VersionInfo version;
        private String rep;
        private FileInfo contentfile;
        public static readonly String DIRECTORY_PATH = "DIRECTORY_PATH";
        private static String lastpath = null;
        private OfficeApplication application;
        public SelectDirectory(OfficeApplication application)
        {
            InitializeComponent();
            this.application = application;
        }

        private TreeNode BuscaNodo(TreeNode root, DirectoryInfo dir)
        {
            if (((DirectoryInfo)root.Tag).FullName == dir.FullName)
            {
                return root;
            }
            foreach (TreeNode node in root.Nodes)
            {
                if (node.Tag is DirectoryInfo)
                {
                    DirectoryInfo subnode = (DirectoryInfo)node.Tag;
                    if (subnode.FullName == dir.FullName)
                    {
                        return node;
                    }
                }
            }
            return null;
        }
        private void SelectDirectory_ShowStep(object sender, TSWizards.ShowStepEventArgs e)
        {
            this.Wizard.NextEnabled = false;
            this.treedir.Nodes.Clear();
            String[] drives = System.IO.Directory.GetLogicalDrives();
            foreach (String drive in drives)
            {
                System.IO.DirectoryInfo d = new System.IO.DirectoryInfo(drive);
                int tempType = getDriveType(drive);
                string tempName = GetDriveName(drive);
                DriveInfo tempInfo = new DriveInfo(drive, tempType, tempName);
                int type = tempInfo.Type;
                TreeNode raiz = this.treedir.Nodes.Add(tempInfo.Name + " (" + drive + ")");
                raiz.Tag = d;
                //TreeNode raiz=this.treedir.Nodes.Add(drive);  
                /*if(name=="My Computer")return 0;
                if(type==5)return 3;//cd
                if(type==3)return 2;//fixed
                if(type==2)return 1;//removable
                if(type==4)return 4;//remote disk
                if(type==6)return 5;//ram disk
                return 6;//unknown*/
                switch (tempInfo.Icon)
                {
                    case 1:
                        raiz.ImageIndex = 3;
                        raiz.SelectedImageIndex = 3;
                        break;
                    case 2:
                        raiz.ImageIndex = 2;
                        raiz.SelectedImageIndex = 2;
                        break;
                    case 3:
                        raiz.ImageIndex = 4;
                        raiz.SelectedImageIndex = 4;
                        break;
                    case 4:
                        raiz.ImageIndex = 5;
                        raiz.SelectedImageIndex = 5;
                        break;
                    case 5:
                        raiz.ImageIndex = 2;
                        raiz.SelectedImageIndex = 2;
                        break;
                    default:
                        raiz.ImageIndex = 2;
                        raiz.SelectedImageIndex = 2;
                        break;

                }
                raiz.Nodes.Add("");
            }
            if (lastpath != null)
            {
                DirectoryInfo lastdir = new DirectoryInfo(lastpath);
                if (lastdir.Exists)
                {
                    List<DirectoryInfo> dirsinicio = new List<DirectoryInfo>();
                    dirsinicio.Add(lastdir);
                    while (lastdir.Parent != null)
                    {
                        dirsinicio.Add(lastdir.Parent);
                        lastdir = lastdir.Parent;
                    }
                    List<DirectoryInfo> dirs = new List<DirectoryInfo>();
                    for (int i = dirsinicio.Count - 1; i >= 0; i--)
                    {
                        dirs.Add(dirsinicio[i]);
                    }
                    lastdir = new DirectoryInfo(lastpath);
                    if (dirs.Count > 0)
                    {
                        TreeNode root = null;
                        foreach (TreeNode node in this.treedir.Nodes)
                        {
                            if (node.Tag is DirectoryInfo)
                            {
                                DirectoryInfo dirroot = (DirectoryInfo)node.Tag;
                                if (dirroot.FullName == ((DirectoryInfo)dirs[0]).FullName)
                                {
                                    root = node;
                                }
                            }
                        }
                        if (root != null)
                        {
                            foreach (DirectoryInfo dir in dirs)
                            {

                                root = BuscaNodo(root, dir);
                                if (root != null)
                                {
                                    this.treedir.SelectedNode = root;
                                    root.Expand();
                                }
                                else
                                {
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
        /// <summary>Obtiene el tipo de Drive</summary>
        /// <returns>Entero con el número de tipo</returns>
        /// <param name="drive">Nombre del drive c:, d:, etc</param>
        public int getDriveType(string drive)
        {
            /*if((GetDriveType(drive) & 5)==5)return 5;//cd
            if((GetDriveType(drive) & 3)==3)return 3;//fixed
            if((GetDriveType(drive) & 2)==2)return 2;//removable
            if((GetDriveType(drive) & 4)==4)return 4;//remote disk
            if((GetDriveType(drive) & 6)==6)return 6;//ram disk
            return 0;*/
            if (GetDriveType(drive) == DriveType.CDRom)
            {
                return 5;
            }
            if (GetDriveType(drive) == DriveType.Fixed)
            {
                return 3;
            }
            if (GetDriveType(drive) == DriveType.Removable)
            {
                return 2;
            }
            if (GetDriveType(drive) == DriveType.Ram)
            {
                return 6;
            }
            if (GetDriveType(drive) == DriveType.Network)
            {
                return 4;
            }
            else
            {
                return 0;
            }
        }

        public System.IO.DriveType GetDriveType(string drive)
        {
            System.IO.DriveInfo[] drives = System.IO.DriveInfo.GetDrives();

            foreach (System.IO.DriveInfo odrive in drives)
            {
                if (odrive.Name == drive)
                {
                    return odrive.DriveType;
                }
            }
            return System.IO.DriveType.Unknown;

        }

        /// <summary>Obtiene el nombre del drive</summary>
        /// <returns>Nombre del Drive</returns>
        /// <param name="drive">Drive a buscar nombre , puede ser c:, d:, etc.</param>
        public string GetDriveName(string drive)
        {
            string retval = "";
            System.IO.DriveInfo[] drives = System.IO.DriveInfo.GetDrives();
            foreach (System.IO.DriveInfo odrive in drives)
            {
                if (odrive.Name == drive)
                {
                    if (odrive.IsReady)
                    {
                        retval = odrive.VolumeLabel;
                    }
                }
            }
            return retval;
        }
        private void treedir_AfterSelect(object sender, TreeViewEventArgs e)
        {
            if (e.Node.Tag is DirectoryInfo && (e.Node.ImageIndex == -1 || e.Node.ImageIndex == 0 || e.Node.ImageIndex == 1))
            {
                //this.treedir.ContextMenu = this.contextMenu1;
            }
            else
            {
                this.treedir.ContextMenu = null;
            }
            this.Wizard.NextEnabled = true;
        }

        private void treedir_BeforeExpand(object sender, TreeViewCancelEventArgs e)
        {
            if (e.Node.FirstNode != null)
            {
                if (e.Node.FirstNode.Text.Equals(""))
                {
                    e.Node.Nodes.Clear();
                    try
                    {

                        System.IO.DirectoryInfo dir = (DirectoryInfo)e.Node.Tag;
                        System.IO.DirectoryInfo[] dirs = dir.GetDirectories();
                        foreach (System.IO.DirectoryInfo dirinfo in dirs)
                        {
                            TreeNode node = e.Node.Nodes.Add(dirinfo.Name);
                            node.Tag = dirinfo;
                            node.Nodes.Add("");
                        }
                    }
                    catch (System.IO.IOException ioe)
                    {
                        OfficeApplication.WriteError(ioe);
                    }
                }
            }
        }

        private void toolStripButtonCreate_Click(object sender, EventArgs e)
        {
            if (this.treedir.SelectedNode != null)
            {
                if (this.treedir.SelectedNode.Tag is DirectoryInfo)
                {
                    try
                    {
                        DirectoryInfo dir = (DirectoryInfo)this.treedir.SelectedNode.Tag;
                        int iCarpeta = 1;
                        bool continua = true;
                        while (continua)
                        {
                            TreeNode nodenew = null;
                            DirectoryInfo dirnew = new DirectoryInfo(dir.FullName + "\\Folder " + iCarpeta);
                            if (dirnew.Exists)
                            {
                                continua = true;
                                iCarpeta++;
                            }
                            else
                            {
                                continua = false;
                                dirnew.Create();
                                if (this.treedir.SelectedNode.Nodes.Count == 1 && this.treedir.SelectedNode.Nodes[0].Text == "")
                                {
                                    this.treedir.SelectedNode.Expand();
                                    foreach (TreeNode node in this.treedir.SelectedNode.Nodes)
                                    {
                                        if (node.Tag is DirectoryInfo)
                                        {
                                            DirectoryInfo dirtest = (DirectoryInfo)node.Tag;
                                            if (dirtest.FullName == dirnew.FullName)
                                            {
                                                this.treedir.SelectedNode = node;
                                                nodenew = node;
                                            }
                                        }
                                    }
                                }
                                else
                                {
                                    nodenew = new TreeNode(dirnew.Name);
                                    nodenew.Tag = dirnew;
                                    nodenew.ImageIndex = 0;
                                    nodenew.SelectedImageIndex = 0;
                                    this.treedir.SelectedNode.Nodes.Add(nodenew);
                                    this.treedir.SelectedNode = nodenew;
                                }
                                if (nodenew != null)
                                {
                                    nodenew.BeginEdit();
                                }
                            }

                        }
                    }
                    catch (Exception err)
                    {
                        OfficeApplication.WriteError(err);
                        MessageBox.Show(this, err.Message, "Crear folder", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    }

                }
            }
        }

        private void toolStripButtonRename_Click(object sender, EventArgs e)
        {
            if (this.treedir.SelectedNode != null && this.treedir.SelectedNode.Tag is DirectoryInfo && (this.treedir.SelectedNode.ImageIndex == -1 || this.treedir.SelectedNode.ImageIndex == 0 || this.treedir.SelectedNode.ImageIndex == 1))
            {
                this.treedir.SelectedNode.BeginEdit();
            }
        }

        private void toolStripButtonDelete_Click(object sender, EventArgs e)
        {
            if (this.treedir.SelectedNode != null && this.treedir.SelectedNode.Tag is DirectoryInfo && (this.treedir.SelectedNode.ImageIndex == -1 || this.treedir.SelectedNode.ImageIndex == 0 || this.treedir.SelectedNode.ImageIndex == 1))
            {
                if (MessageBox.Show(this, "¿Desea eliminar el folder?", "Borrar Folder", MessageBoxButtons.OKCancel, MessageBoxIcon.Question) == DialogResult.Cancel)
                {
                    return;
                }
                DirectoryInfo dir = (DirectoryInfo)this.treedir.SelectedNode.Tag;
                try
                {
                    dir.Delete(true);
                    TreeNode node = this.treedir.SelectedNode;
                    if (this.treedir.SelectedNode.Parent != null)
                    {
                        this.treedir.SelectedNode = this.treedir.SelectedNode.Parent;
                    }
                    node.Remove();
                }
                catch (Exception err)
                {
                    MessageBox.Show(this, err.Message, "Borrar Folder", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }
        }

        private void SelectDirectory_ValidateStep(object sender, CancelEventArgs e)
        {
            //System.Threading.Thread t=null;
            if (this.treedir.SelectedNode != null && this.treedir.SelectedNode.Tag is DirectoryInfo)
            {
                DirectoryInfo dir = (DirectoryInfo)this.treedir.SelectedNode.Tag;
                lastpath = dir.FullName;
                this.Wizard.Data[DIRECTORY_PATH] = lastpath;
                rep = this.Wizard.Data[Search.REPOSITORY_ID].ToString();
                version = (VersionInfo)this.Wizard.Data[SelectVersionToOpen.VERSION];
                IOfficeApplication app = OfficeApplication.OfficeApplicationProxy;
                this.Wizard.SetProgressBarInit(5, 1, "Descargando contenido...");
                fileName = app.openContent(rep, version);
                this.Wizard.SetProgressBarInit(5, 2, "Validando contenido...");
                String pathFile = dir.FullName + "/" + fileName;
                pathFile=pathFile.Replace('/','\\');
                contentfile = new FileInfo(pathFile);
                if (contentfile.Exists)
                {
                    DialogResult res = MessageBox.Show(this, "Existe un archivo con el nombre " + fileName + "\r\n¿Desea sobre escribir el archivo?", this.Wizard.Text, MessageBoxButtons.YesNo, MessageBoxIcon.Question);
                    if (res == DialogResult.Yes)
                    {
                        try
                        {
                            contentfile.Delete();
                        }
                        catch (Exception ue)
                        {
                            MessageBox.Show(ue.Message, this.Wizard.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                            return;
                        }
                    }
                    else
                    {
                        return;
                    }
                }
                String guid = Guid.NewGuid().ToString().Replace('-', '_');
                FileInfo zipFile = new FileInfo(dir.FullName + "/" + guid + ".zip");
                try
                {
                    foreach (Part part in OfficeApplication.OfficeApplicationProxy.ResponseParts)
                    {
                        FileStream fout = zipFile.Open(FileMode.Create, FileAccess.ReadWrite, FileShare.ReadWrite);
                        fout.Write(part.getContent(), 0, part.getContent().Length);
                        fout.Close();
                    }
                    using (ZipInputStream s = new ZipInputStream(zipFile.OpenRead()))
                    {
                        
                        ZipEntry entry;
                        // extract the file or directory entry
                        while ((entry = s.GetNextEntry()) != null)
                        {
                            if (entry.IsDirectory)
                            {
                                ExtractDirectory(s, entry.Name, entry.DateTime, zipFile.Directory);
                            }
                            else
                            {
                                ExtractFile(s, entry.Name, entry.DateTime, entry.Size, zipFile.Directory);
                            }
                            s.CloseEntry();
                        }
                        s.Close();
                    }
                    this.Wizard.SetProgressBarInit(5, 3, "Abriendo contenido...");
                    //t = new System.Threading.Thread(new System.Threading.ThreadStart(open));                    
                    this.Wizard.Visible = false;                    
                    this.Wizard.Close();                                                           
                }
                catch (Exception ue)
                {
                    MessageBox.Show(this, "El contenido tiene una falla\r\nDetalle: " + ue.Message, this.Wizard.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
                finally
                {       
                    
                    zipFile.Delete();                    
                }

            }
            else
            {
                MessageBox.Show(this, "¡Debe indicar un directorio!", this.Wizard.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        public void open()
        {
            if (contentfile != null && contentfile.Exists && rep!=null && version!=null)
            {
                try
                {
                    OfficeDocument document = application.Open(contentfile, version.contentId, rep);                    
                    //document.SaveContentProperties(version.contentId, rep);                    
                    document.Save();
                    MessageBox.Show(this, "¡Se ha abierto un contenido con el nombre " + fileName + "!", this.Wizard.Text, MessageBoxButtons.OK, MessageBoxIcon.Information);
                }
                catch (Exception ue)
                {
                    MessageBox.Show(this, "El contenido tiene una falla\r\nDetalle: " + ue.Message, this.Wizard.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }
            
        }

        
        protected void ExtractFile(Stream inputStream, string entryName, DateTime entryDate, long entrySize, DirectoryInfo ToDirectory)
        {

            FileInfo destFile = new FileInfo(Path.Combine(ToDirectory.FullName,
            entryName));

            // ensure destination directory exists
            if (!destFile.Directory.Exists)
            {
                try
                {
                    destFile.Directory.Create();
                    destFile.Directory.LastWriteTime = entryDate;
                    destFile.Directory.Refresh();
                }
                catch (Exception ex)
                {
                    MessageBox.Show(ex.Message,this.Wizard.Text,MessageBoxButtons.OK,MessageBoxIcon.Error);
                }
            }

            // determine if entry actually needs to be extracted
            if (destFile.Exists && destFile.LastWriteTime >= entryDate)
            {
                return;
            }

            try
            {
                using (FileStream sw = new FileStream(destFile.FullName, FileMode.Create, FileAccess.Write))
                {
                    int size = 2048;
                    byte[] data = new byte[2048];
                    if (entrySize > 0L)
                    {
                        while (true)
                        {
                            size = inputStream.Read(data, 0, data.Length);
                            if (size > 0)
                            {
                                sw.Write(data, 0, size);
                            }
                            else
                            {
                                break;
                            }
                        }
                    }
                    sw.Close();
                }
            }
            catch (Exception ex)
            {
                /*throw new BuildException(string.Format(CultureInfo.InvariantCulture,
                    "Unable to expand '{0}' to '{1}'.", entryName, ToDirectory.FullName),
                    Location, ex);*/
                MessageBox.Show(ex.Message, this.Wizard.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
            }

            destFile.LastWriteTime = entryDate;


        }
        protected void ExtractDirectory(Stream inputStream, string entryName, DateTime entryDate, DirectoryInfo ToDirectory)
        {
            DirectoryInfo destDir = new DirectoryInfo(Path.Combine(
                ToDirectory.FullName, entryName));
            if (!destDir.Exists)
            {
                try
                {
                    destDir.Create();
                    destDir.LastWriteTime = entryDate;
                    destDir.Refresh();
                }
                catch (Exception ex)
                {
                    MessageBox.Show(ex.Message, this.Wizard.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }
        }




        private void treedir_KeyUp(object sender, KeyEventArgs e)
        {
            if (this.treedir.SelectedNode != null && this.treedir.SelectedNode.Tag is DirectoryInfo && e.KeyCode == Keys.F2)
            {
                this.toolStripButtonRename_Click(null, null);
            }
        }

        private void treedir_AfterLabelEdit(object sender, NodeLabelEditEventArgs e)
        {
            if (e.Node.Tag != null && e.Node.Tag is DirectoryInfo && e.Label != null)
            {
                DirectoryInfo dir = (DirectoryInfo)e.Node.Tag;
                String newPath = dir.Parent.FullName + "//" + e.Label;
                DirectoryInfo newDir = new DirectoryInfo(newPath);
                if (newDir.Exists)
                {
                    MessageBox.Show(this, "¡Ya existe un directorio con ese nombre!", this.Wizard.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
                dir.MoveTo(newPath);
            }
        }
    }
}
