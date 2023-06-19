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

//client --recieve messages

// structure for message queue
struct message {
	long mesg_type;
	char mesg_text[100];
};

int main(){

    key_t key;
    int queue_id;
    struct message msg;
    key=ftok(".",69);
   
    if(key ==-1){
        printf("Client:Error in key creation \n");
        exit(1);
    }
    queue_id=msgget(key, 0666 | IPC_CREAT);
    
    if(queue_id == -1){
        printf("Client:Error in queue creation \n");
        exit(1);
    }
    while(1){
        printf("Client : Enter a message: ");
        fgets(msg.mesg_text, MAX, stdin);
        msg.mesg_type=1;
        if(msgsnd(queue_id,&msg,strlen(msg.mesg_text)+1,0)==-1){
            printf("Client:Error:Sending message");
            exit(1);
        }
        if (strcmp(msg.mesg_text, "exit\n") == 0) {
            printf("Client:Terminating the client...\n");
            break;
        }
        printf("Client:Waiting to recieve a message\n");
        if (msgrcv(queue_id, &msg, sizeof(msg.mesg_text), 2, 0) == -1) {
            perror("msgrcv");
            exit(EXIT_FAILURE);
        }
         if (strcmp(msg.mesg_text, "exit\n") == 0) {
            printf("Client:Terminating the client...\n");
            break;
        }
        printf("Client:Received message: %s\n", msg.mesg_text);

       
    }
}