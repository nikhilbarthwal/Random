#include <vector>
#include <iostream>

using namespace std;

class Statement
{
public:
	static Statement Line(const string& s_);
	static Statement Block(const vector<Statement>& l_);
	void Print(const string&);
	const string tab = "  ";

private:
	class Statement* p;
	Statement(const Statement* p_);

protected:
	void print0(int); // OVERRIDE
	void printTabs(int);
};

// ------------------------

class Line : public Statement
{
private:
	string s;
	Print

public:
	Line(const string& s_) : s(s_) { };
};

class Block : public Statement
{
private:
	string l;

public:
	Block(const vector<Statement>& l_) : l(l_) { };
};

Statement::Statement(const Statement* p_) : p(p_) { }

static Statement Statement::Line(const string& s)
	{ return Statement(new Line(s)); }

static Statement Statement::Block(const string& l)
	{ return Statement(new Block(l)); }

void Statement::printTabs(int n)
	{ return Statement(new Block(l)); }

void Statement::print0(int n)
	{ p->print0(n); }

//  { p->Print0(n); }


