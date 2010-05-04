using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using WBOffice4.Interfaces;
namespace WBOffice4.Forms
{
    public class SemanticFolderNode : TreeNode
    {
        private SemanticFolderRepository semanticFolderRepository;
        public SemanticFolderNode(SemanticFolderRepository semanticFolderRepository)
        {
            this.semanticFolderRepository = semanticFolderRepository;
            this.Text = semanticFolderRepository.name;
            this.ImageIndex = 0;
            if (semanticFolderRepository.haschilds)
            {
                this.Nodes.Add("");
            }
            this.SelectedImageIndex = 1;
            this.ImageIndex = 0;
        }
        public SemanticFolderRepository SemanticFolderRepository
        {
            get
            {
                return semanticFolderRepository;
            }
        }
    }
}
