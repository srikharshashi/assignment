#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <semaphore.h>
#include<string.h>
#include<fcntl.h>
#define MAX 10

//server
// structure for message queue
struct message {
	long mesg_type;
	char mesg_text[100];
};

int main(){

    int queue_id;
    key_t key;
    
    struct message msg;

    key=ftok(".",69);
   

    if(key==-1){
        printf("Server:Error in key creation");
        exit(1);
    }
    queue_id=msgget(key, 0666 | IPC_CREAT);;
    if(queue_id ==-1){
        printf("Server:Error in queue creation");
        exit(1);
    }
    while(1){
        printf("Sever:Waiting to recieve\n");
        if (msgrcv(queue_id, &msg, sizeof(msg.mesg_text), 1, 0) == -1) {
            perror("msgrcv");
            exit(EXIT_FAILURE);
        }
        if (strcmp(msg.mesg_text, "exit\n") == 0) {
            printf("Server:Terminating the server...\n");
            break;
        }
        printf("Sever:Received message: %s\n", msg.mesg_text);
        printf("Server : Enter a message: ");
        fgets(msg.mesg_text, MAX, stdin);
        msg.mesg_type=2;
        if(msgsnd(queue_id,&msg,strlen(msg.mesg_text)+1,0)==-1){
            printf("Sever:Error:Sending message");
            exit(1);
        }
        if (strcmp(msg.mesg_text, "exit\n") == 0) {
            printf("Server:Terminating the server...\n");
            break;
        }
    }

}