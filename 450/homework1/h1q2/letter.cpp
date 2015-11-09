//which letter is it? 
//a  b  c  d  e  f  g  h  i  j  k  l  m  n  o  p  q  r  s  t  u  v  w  x  y  z
//1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 
#include <iostream>
using namespace std; 
int main(){
int letter;
cin>>letter; 
	if (letter<=13){

		if(letter<=6){

			if(letter<=3){

				if(letter<=1){
					cout<<"letter is a!"<<endl;
				}
				else{//letter>1

					if(letter<=2){
					cout<<"letter is b!"<<endl;
					}
					else {//letter > 2
					cout<<"letter is c!"<<endl;
					}

				}

			}
			else {//letter >3

				if(letter<=4){
					cout<<"letter is d!"<<endl;
				}
				else{//letter>4
					if(letter<=5){
						cout<<"letter is e!"<<endl;
				}
					else{//letter>5
						cout <<"letter is f!"<<endl;

					}

				}
			}
		}
		else{//letter>6
			if(letter<=9){

			}
		}
	else{//letter>13
		if(letter<=19){

			if(letter<=16){

			}
			else{//letter>16

			}
		}
		else{//letter>19
			

		}
	}
}