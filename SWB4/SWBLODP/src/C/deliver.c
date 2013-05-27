/* 
 * File:   deliver.c
 * Author: serch
 *
 * Created on May 22, 2013, 3:20 PM
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>


/*
 * Sends the filename to the Standard Output in binary format
 */
void sendFile(char * filename){
    unsigned char buff[8192];
    FILE *fp;
    fp=fopen(filename, "r");
    int tam;
    while ((tam = fread(buff, 1, sizeof buff, fp)) > 0)
    {
        fwrite(buff,1,tam,stdout);
    }
    fclose(fp);
}

/*
 * If the filename has .csv or .CSV it sets the Mimetype to text/csv 
 * otherwise is set to application/octet-stream
 * 
 * the rfile is sent as part of the Content-Disposition header
 */
void setMimeType (char *filename, char *rfile){
    char *bkdir = strstr(filename,".csv");
    char *bkdir2 = strstr(filename,".CSV");
    char *file = strrchr(filename,'/');
    file++;
    if ((NULL!= bkdir)||(NULL!=bkdir2))
        printf("Content-Type: text/csv\n");
    else
        printf("Content-Type: application/octet-stream\n");
    printf("Content-Disposition: attachment; filename=%s\n",file);
    printf("\n");
}


/*
 * CGI to send files, by page or compleate, the PATH_INFO envar reprsents the actual filename 
 * within the workint directory /apps/DataSetsReposiroty, the parameter page sets the number 
 * of page to deliver
 */
int main(int argc, char** argv) {
        
    char *method;
    char *file;
    char *query;
    char tmp[1024];
    int page=-1;
    char *base ="/apps/DataSetsReposiroty";  //"/apps/dataLink";
    method = getenv("REQUEST_METHOD");
    if ((method!=NULL)&&(strcmp(method,"GET")==0)){
        file = getenv("PATH_INFO");
        query = getenv("QUERY_STRING");
        if (query!=NULL)
            sscanf(query,"page=%d",&page);
        if (file!=NULL) {
            char *bkdir = strstr(file,"..");
            if (bkdir==NULL) {
                strcpy(tmp, base);
                strncat(tmp,file, 512);
                if (access(tmp, R_OK)==0){
                    setMimeType(tmp, file);
                    sendFile(tmp);
                } else if (page>0){
                    char pag[16];
                    sprintf(pag,"_%d",page);
                    strncat(tmp,pag,16);
                    if (access(tmp, R_OK)==0){
                        setMimeType(tmp, file);
                        sendFile(tmp);
                    }
                } else {
                    page=1;
                    char ldir[1024];
                    char pag[16];
                    int flag = 0;
                    sprintf(pag,"_%d",page);
                    strcpy(ldir, tmp);
                    strncat(ldir,pag, 16);
                    if (access(ldir, R_OK)==0){
                        setMimeType(tmp, file);
                        page=0;
                        sprintf(pag,"_%d",page);
                        strcpy(ldir, tmp);
                        strncat(ldir,pag, 16);
                        if (access(ldir, R_OK)==0){
                            sendFile(ldir);
                        }
                        while (flag == 0){
                            page++;
                            sprintf(pag,"_%d",page);
                            strcpy(ldir, tmp);
                            strncat(ldir,pag, 16);
                            if (access(ldir, R_OK)==0){
                                sendFile(ldir);
                            } else {
                                flag = 1;
                            }
                        }
                    }
                }


            }
        }
    }
    return (EXIT_SUCCESS);
}
