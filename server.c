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

    int client_queue_id,server_queue_id;
    key_t client_key,server_key;
    sem_t *read_semaphore,*write_semaphore;

    struct message msg;

    client_key=ftok(".",69);
    server_key=ftok(".",79);

    if(client_key==-1 || server_key ==-1){
        printf("Error in key creation");
        exit(1);
    }

    client_queue_id=msgget(client_key, 0666 | IPC_CREAT);
    server_queue_id=msgget(server_key, 0666 | IPC_CREAT);

    if(client_queue_id ==-1 || server_queue_id == -1){
        printf("Error in queue creation");
        exit(1);
    }

    read_semaphore=sem_open("/read_semaphore", O_CREAT, 0666, 1);
    write_semaphore=sem_open("/write_semaphore", O_CREAT, 0666, 1);
    if(read_semaphore == SEM_FAILED || write_semaphore ==SEM_FAILED){
        printf("Semaphore getting error \n");
        exit(1);
    }

    while(1){
        //reading part
        printf("Server:Waiting for read semaphore\n");
        if(sem_wait(read_semaphore)==-1){
            printf("Server:error waiting read semaphore");
            exit(1);
        }
        printf("Sever:Waiting to recieve\n");

        if (msgrcv(client_queue_id, &msg, sizeof(msg.mesg_text), 1, 0) == -1) {
            perror("msgrcv");
            exit(EXIT_FAILURE);
        }

        printf("Received message: %s\n", msg.mesg_text);

        if(sem_post(read_semaphore)==-1){
            printf("Sever:error closing read semaphore");
            exit(1);
        }
        printf("Server: Closed read semaphore\n");

        printf("Server:Waiting for write semaphore\n");
        //writing part
        if(sem_wait(write_semaphore)==-1){
            printf("Sever:error waiting write semaphore");
            exit(1);
        }
        printf("Server : Enter a message: ");
        fgets(msg.mesg_text, MAX, stdin);
        msg.mesg_type=1;
        if(msgsnd(server_queue_id,&msg,strlen(msg.mesg_text)+1,0)==-1){
            printf("Sever:Error:Sending message");
            exit(1);
        }

        if(sem_post(write_semaphore)==-1){
            printf("Sever:error closing write semaphore");
            exit(1);
            printf("Server:Closed write semaphore\n");
        }
        
    }

}