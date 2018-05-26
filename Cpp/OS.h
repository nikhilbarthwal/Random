#include <windows.h>
#include <string>
#include <iostream>
#include <tchar.h>

#ifndef MAX_PATH
#define MAX_PATH 250
#endif

using namespace std;

string CurrentPath()
{
	TCHAR buffer[MAX_PATH];
	GetModuleFileName( NULL, buffer, MAX_PATH );

	string s(MAX_PATH,'\0');

	unsigned int i=0;
	while(buffer[i]!='\0')
	{
		s[i]=(char) buffer[i];
		i++;
	}

	while(buffer[i]!='\\')
		i--;

	return s.substr(0,i+1);
}



bool CreatePath(string s)
{
	TCHAR buffer[MAX_PATH];
	cout<<s<<endl;
	int l=s.length();
	int i;
	for(i=0;i<l;i++)
	{
		buffer[i]=(TCHAR) s[i];
	}
	buffer[i]='\0';
	if (!CreateDirectory(buffer, NULL)) 
	{ 
		cout<<"CreateDirectory failed\n"; 
		return false;
	} 
	return true;
}

bool Exists(string s)
{
	TCHAR buffer[MAX_PATH];
	int l=s.length();

	for(int i=0;i<l;i++)
	{
		buffer[i]=(TCHAR) s[i];
	}
	buffer[l]='\0';

	return (GetFileAttributes(buffer) != INVALID_FILE_ATTRIBUTES);
}

bool isDirectory(string s)
{
	TCHAR buffer[MAX_PATH];
	int l=s.length();

	for(int i=0;i<l;i++)
	{
		buffer[i]=(TCHAR) s[i];
	}
	buffer[l]='\0';

	DWORD dwAttrib = GetFileAttributes(buffer);

	return (dwAttrib != INVALID_FILE_ATTRIBUTES && (dwAttrib & FILE_ATTRIBUTE_DIRECTORY));
}

