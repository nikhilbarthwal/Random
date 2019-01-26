#ifndef UTILS_H
#define UTILS_H

#include <string>
#include <sstream>
#include <vector>

using namespace std;

class errors
{
private:
    vector<string> errorsList;

public:
    void AddError(string);
    bool OK();
    void Print(string);
};

int Find(string str, int pos);

int Find(string str, char ch, int pos);

bool CreateOptional(const string& input, string& output);

bool GetIdentifier(const string& input, int pos1, int& pos2, string& output);

bool GetIdentifier(const string& input, char ch, int pos1, int& pos2, string& output);

int Convert2Int(string s);

void Trim(const string& str, int& pos);

#endif /* UTILS_H */
