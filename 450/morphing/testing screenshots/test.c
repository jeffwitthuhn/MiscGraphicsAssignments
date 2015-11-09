#include <lib.h>
#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

int main(int argc, char *argv) {
  int err, indexEntered;
  char namestr[16];
  
  do {
    printf("Enter an index number to get a process name (-1 to exit): ");
    scanf("%d", &indexEntered);
   
    printf("%d\n", indexEntered);
    if(indexEntered == -1)
      break;
	
    if(getprocname(indexEntered, namestr) < 0)
      printf("Bad index number.\n");
    else
      printf("Process %d: %s\n", indexEntered, namestr);
  } while(1);

  return(0);
}
