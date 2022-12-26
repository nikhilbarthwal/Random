#include <iostream>
#include <string>
#include <vector>
#include <map>
#include <thread>
#include <fstream>

#include "result.h"
#include "filter.h"

using namespace std;

const vector<filter*> filters = { new filter_password() }; // Add more filters here!

void run(char* log_name, result* intermediate_result);

int main(int argc, char**argv)
{
    vector<result> results(argc);
    vector<thread> threads(argc);

    for(int i=0; i<argc; ++i) // Creates threads for parallel processing!
        threads[i] = thread(run, argv[i], &results[i]);

    for(int i=0; i<argc; ++i)
        threads[i].join();


    result final;
    for(int i=0; i<argc; ++i)
        final.merge(results[i]); // Merge all the results from different threads!

    final.print();
    return;
}

void run(char* log_name, result* intermediate_result)
{
    ifstream file("/home/stark/eclipse-workspace/Aditya/src/sample.txt", "r");
    if(!file.is_open())
    {
        cout<<"Unable to open the file."<<endl;
        return;
    }

    string line, ip;
    while(getline(file, line))
        for(auto f: filters)
            if (f->check(line, ip)) // Check with all filters and ...
            {
                intermediate_result->add(ip); // Add the IP address!
                break;
            }

    file.close();
}
