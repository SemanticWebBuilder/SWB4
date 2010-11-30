using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using WBOffice4.Interfaces;
namespace WBOffice4.Forms
{
    public class SemanticRepositoryNode : TreeNode
    {
        private SemanticRepository semanticRepository;
        public SemanticRepositoryNode(SemanticRepository semanticRepository)
        {
            this.semanticRepository = semanticRepository;
            this.Text = semanticRepository.name;
            this.ImageIndex = 0;            
            this.SelectedImageIndex = 1;
            this.ImageIndex = 0;
        }
        public SemanticRepository SemanticRepository
        {
            get
            {
                return semanticRepository;
            }
        }
    }
}
