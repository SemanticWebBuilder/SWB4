/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function setParentUUID(forma,val)
{
    forma.parentUUID.value=val;
}

function textCounter(field,  maxlimit) {
    if (field.value.length > maxlimit)
        field.value = field.value.substring(0, maxlimit);
}
function submitform()
{
    document.frmnewdoc.submit();
}
function valida()
{
    if(document.frmnewdoc.repftitle.value=="")
    {
        alert("Defina el título");
        return;
    }
    if(document.frmnewdoc.repfdescription.value=="")
    {
        alert("Indique la descripción");
        return;
    }
    if(document.frmnewdoc.repfdoc.value=="")
    {
        alert("Defina un archivo");
        return;
    }
    submitform();
}

function validateEditFile()
{
    if(document.frmnewdoc.repftitle.value=="")
    {
        alert("Defina el título");
        return;
    }
    if(document.frmnewdoc.repfdescription.value=="")
    {
        alert("Indique la descripción");
        return;
    }
//    if(document.frmnewdoc.repfdoc.value=="")
//    {
//        alert("Defina un archivo");
//        return;
//    }
    submitform();
}

function validateAddFolder()
{
    if(document.frmnewfolder.repftitle.value=="")
    {
        alert("Defina el título");
        return;
    }
    if(document.frmnewfolder.repfdescription.value=="")
    {
        alert("Indique la descripción");
        return;
    }

    document.frmnewfolder.submit();
}
