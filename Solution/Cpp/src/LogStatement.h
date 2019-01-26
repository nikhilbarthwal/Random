#ifndef LOGSTATEMENT_H
#define LOGSTATEMENT_H

class parseErrors
{
private:
	vector<string> errors parseErrors;

public:
	void AddError(string);
	bool Valid();
	void Print(string);
};

struct logStatement
{
	ipAddress IpAddress;
};

logStatement ParseStatement(string, parseErrors&);

#endif /* LOGSTATEMENT_H */
