#include <iostream>
#include <vector>
#include <string>
#include <map>
#include <cassert>
#include <algorithm>

using namespace std;

struct Transfer
{
    string source;
    string target;
    int amount;
    int expiry;

    void substitute(const string& s, const string& t)
    {
        if (s==target) target = t;
    }
};

struct Account
{
    string name;
    int balance = 0;
    int transaction = 0;

    explicit Account(const string& n): name(n) {}

    string deposit(int x)
    {
        balance += x;
        transaction += x;
        return to_string(balance);
    }

    string pay(int x)
    {
        if (balance < x) return "";
        balance -= x;
        transaction += x;
        return to_string(balance);
    }

    string key()
    {
        return name + "(" + to_string(transaction) + ")";
    }
};

bool operator<(const Account& z1, const Account& z2)
{
    if (z1.transaction == z2.transaction) { return z1.name < z2.name; }
    return z1.transaction > z2.transaction;
}

struct Bank
{
    map<string, Account> accounts;
    map<string, Transfer> transfers;
    int transfer_count = 1;

    bool not_exists(const string& name)
    {
        return accounts.find(name) == accounts.end();
    }

    string create(const string& name)
    {
        if (not_exists(name))
        {
            accounts.insert({name, Account(name)});
            return "true";
        }
        return "false";
    }

    string deposit(const string& name, const int amount)
    {
        if (not_exists(name)) return "";
        return accounts.at(name).deposit(amount);
    }

    string pay(const string& name, const int amount)
    {
        if (not_exists(name)) return "";
        return accounts.at(name).pay(amount);
    }

    string transfer(const string& source, const string& target, const int amount, const int timestamp)
    {
        if (source == target) return "";
        if (not_exists(source)) return "";
        if (not_exists(target)) return "";

        string key = "transfer" + to_string(transfer_count);
        if (accounts.at(source).balance >= amount) {
            Transfer transfer;
            transfer.amount = amount;
            transfer.source = source;
            transfer.target = target;
            transfer.expiry = timestamp + 86400000;
            transfers.insert({key, transfer});
            transfer_count++;
            return key;
        }

        return "";
    }

    string accept_transfer(const int current, const string& name, const string& key)
    {
        if (transfers.find(key) == transfers.end()) return "false";
        auto& transfer = transfers.at(key);
        if (current > transfer.expiry) return "false";
        Account& target_account = accounts.at(transfer.target);
        Account& source_account = accounts.at(transfer.source);
        target_account.deposit(transfer.amount);
        source_account.pay(transfer.amount);
        return "true";
    }

    string merge(const string& source, const string& target)
    {
        if (not_exists(source)) return "";
        if (not_exists(target)) return "";
        if (source == target) return "";

        Account& target_account = accounts.at(target);
        Account& source_account = accounts.at(source);

        target_account.balance += source_account.balance;
        target_account.transaction += source_account.transaction;

        for(auto & transfer : transfers)
            transfer.second.substitute(source, target);

        accounts.erase(accounts.find(source));
        return "true";
    }
};

template<class K, class V>
vector<string> top_activity(const map<K, V>&  map, int n = -1)
{
    vector<V> values;
    for(auto & x : map)
        values.push_back(x.second);
    sort(values.begin(), values.end());
    const int m = (int) map.size();
    const int l = (n == -1) ? m : min(m, n);
    vector<string> z(l);
    for(int i=0; i< l; i++)
        z[i] = values[i].key();
    return z;
}

string join(const vector<string>& v, const string& delim)
{
    string result;
    const int l = (int) v.size();
    for(int i = 0; i < l; ++i)
    {
        result += v[i];
        if (i < l-1) result += delim;
    }
    return result;
}

string process(const vector<string>& query, Bank& bank)
{
    if (query[0] == "CREATE_ACCOUNT")
        return bank.create(query[2]);

    if (query[0] == "DEPOSIT")
        return bank.deposit(query[2], stoi(query[3]));

    if (query[0] == "PAY")
        return bank.pay(query[2], stoi(query[3]));

    if (query[0] == "TRANSFER")
        return bank.transfer(query[2], query[3], stoi(query[4]), stoi(query[1]));

    if (query[0] == "ACCEPT_TRANSFER")
        return bank.accept_transfer(stoi(query[1]), query[2], query[3]);

    if (query[0] == "MERGE")
        return bank.merge(query[2], query[3]);

    if (query[0] == "TOP_ACTIVITY")
        return join(top_activity(bank.accounts, stoi(query[2])), ", ");
    return "";
}

vector<string> solution(const vector<vector<string>>& queries)
{
    Bank bank;
    vector<string> results;
    results.reserve(queries.size());
    for(auto& query: queries)
        results.push_back(process(query, bank));
    return results;
}
