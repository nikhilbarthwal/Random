#include <map>

class result
{
private:
    map<string, int> counts;

public:
    void add(const string& ip, int value = 1)
    {
        if (counts.contains(ip))
            counts[ip] += 1;
        else
            counts[ip] = 1;
    }

    void merge(const result& r) // MErge all the results!
    {
        for(auto it = r.counts.begin(); it != r.counts.end(); it++)
            add(it->first, it->second);
    }

    void print()
    {
        // Print the top 5 ip addresses which have the highest value in the count!
    }
};
