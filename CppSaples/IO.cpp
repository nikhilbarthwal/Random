#include <iostream>
#include <fstream>
using namespace std;

int main (void)
{
	//ofstream myfile ("example.bin", ios::out | ios::app | ios::binary)
    ofstream myfile;
	myfile.open ("input.txt");
    if (myfile.is_open())
    {
        myfile << "Writing this to a file.\n";
    }

	myfile.close();

    string s;

    ifstream myReadFile;
    myReadFile.open("text.txt");

    if (myReadFile.is_open())
    {
        while (!myReadFile.eof())
        {
            myReadFile >> s;
            cout<<s;
        }
    }

    myReadFile.close();

	return 0;
}

/*
bad() : Returns true if a reading or writing operation fails.
fail() : Returns true in the same cases as bad(), but also in the case that a format error happens
eof() : Returns true if a file open for reading has reached the end.
good() : It is the most generic state flag: it returns false in the same cases
*/
