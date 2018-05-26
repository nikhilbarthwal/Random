// Test.cpp : Defines the entry point for the console application.
//

#include <tchar.h>
#include <string>
#include <iostream>
#include "OS.h"




using namespace std;

int main(int argc, char* argv[])
{
	//string s(L"nikhil");
	TCHAR t = '0';
	char t2=t;
	cout<<t2<<endl;
	cout<<sizeof(TCHAR)<<endl;
	//t=1000;
	t2=t;
	int i1=t;
	int i2=t2;
	
	cout<<t<<"\t"<<t2<<"\n";
	
	string s(CurrentPath());
	string s3=s + "NIKHIL\\";
	cout<<s3<< "present? "<< isDirectory(s3)<<endl;
	CreatePath(s3);
	cout<<s3<< "present? "<<isDirectory(s3)<<endl;
	cout << "my directory is " << s << "\n";
	s += "Test.Exe";
	cout<<s<<" : "<<Exists(s)<<endl;

	return 0;
}

