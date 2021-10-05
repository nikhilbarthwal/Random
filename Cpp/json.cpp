// https://github.com/nlohmann/json

#include <iostream>
#include <nlohmann/json.hpp>
using namespace std;

using json = nlohmann::json;

int main()
{
	json J = {
	  {"pi", 3.141},
	  {"happy", true},
	  {"name", "Niels"},
	  {"nothing", nullptr},
	  {"answer", {
	    {"everything", 42}
	  }},
	  {"list", {1, 0, 2}},
	  {"object", {
	    {"currency", "USD"},
	    {"value", 42.99}
	  }}
	};
	
	
	json J2 = J["list"];
	string ss = J["name"].get<string>();
	cout<<ss<<endl;
	cout<<J2.dump()<<endl; 
	cout<<J2.is_array()<<endl;
	cout<<J2.is_boolean()<<endl;
	
	for (json::iterator it = J2.begin(); it != J2.end(); ++it) {
		auto J3 = *it;
		cout<<J3.is_number();
	}
	cout<<endl;
	
	for (json::iterator it = J2.begin(); it != J2.end(); ++it) {
		auto J3 = *it;
		int t = J3.get<int>();
		cout<<t<<endl;
	}
	
	/*
    // create JSON values
    json object = {{"one", 1}, {"two", 2}};
    json null;

    // print values
    std::cout << object << '\n';
    std::cout << null << '\n';

    // add values
    auto res1 = object.emplace("three", 3);
    null.emplace("A", "a");
    null.emplace("B", "b");

    // the following call will not add an object, because there is already
    // a value stored at key "B"
    auto res2 = null.emplace("B", "c");

    // print values
    std::cout << object << '\n';
    std::cout << *res1.first << " " << std::boolalpha << res1.second << '\n';

    std::cout << null << '\n';
    std::cout << *res2.first << " " << std::boolalpha << res2.second << '\n';
	*/
	
}
