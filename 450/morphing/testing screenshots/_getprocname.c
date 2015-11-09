#include <lib.h>
#define getprocname _getprocname
#include <unistd.h>
#include <string.h>

PUBLIC int getprocname(int index, char *namestr) {
  message m;  
  int i;
  
  m.m1_i1 = index;
  
  /* If syscall returns an error, return the error to the calling *
   * program.  Currently only returns EINVAL.                     */
  if(i = _syscall(PM_PROC_NR, GETPROCNAME, &m))
    return i;

  strcpy(namestr, m.m3_ca1);  
  return 0;
}
