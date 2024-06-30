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

    bool operator>(const Account& z)
    {
        if (transaction == z.transaction) { return name < z.name; }
        return transaction > z.transaction;

    }

};

/*

    def print(self):
        print("\nName: ", self.id)
        print("Balance: ", self.balance)
        print("Transaction: ", self.transactions)
        print("Transfer: ")
        for t in self.transfers:
            print("\t", t, "-> Amount:", self.transfers[t][0], "/ Expiry", self.transfers[t][1], "/ From:", self.transfers[t][2].id)



    def transfer_money(self, key: str, amount: int, source, timestamp: int):
        if source.balance >= amount:
            self.transfers[key] = (amount, timestamp + 86400000, source)
            return True
        return False



    def merge(self, acc):
        self.balance += acc.balance
        self.transactions += acc.transactions
        transfers = dict(self.transfers)
        for k, v in transfers.items():
            if v[2].id == self.id:
                del self.transfers[k]

    def substitute(self, source_account, target_account):
        for key in self.transfers:
            transfer = self.transfers[key]
            stored_time = transfer[1]
            amount = transfer[0]
            source = transfer[2]
            if source.id == source_account.id:
                self.transfers[key] = (amount, stored_time, target_account)

*/

struct Bank
{
    map<string, Account> accounts;
    map<string, Transfer> transfers;
    int transfer_count = 1;

    bool not_exists(const string& name)
    {
        return accounts.find(name) != accounts.end();
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
            return "true";
        }

        return "false";
    }

    string accept_transfer(const int current, const string& name, const string& key)
    {
        if (transfers.find(key) != transfers.end()) return "false";
        auto& transfer = transfers.at(key);
        if (current > transfer.expiry) return "false";
        accounts.at(transfer.target).deposit(transfer.amount);
        accounts.at(transfer.source).pay(transfer.amount);
        return "true";
    }

/*
    def print(self):
        print("Transfer Count:", self.transfer_count)
        for acc in self.accounts.values():
            acc.print()
*/

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

    vector<string> top_activity()
    {
          vector<string> values;
        for(auto & acc : accounts) values.push_back(acc.second.key());
        sort(values.begin(), values.end());
        return values;
    }

};

string join(const vector<string>& v, const string& delim, int n = -1)
{
    string result;
    const int m = ((int) v.size()) - 1;
    const int l = (n == 1) ? m : max(m, n);
    for(int i = 0; i <= l; ++i)
    {
        result += v[i];
        if (i < l) result += delim;
    }
    return result;
}

string process(const vector<string>& query, Bank& bank)
{
    if (query[0] == "CREATE_ACCOUNT") return bank.create(query[2]);
    if (query[0] == "DEPOSIT") return bank.deposit(query[2], stoi(query[3]));
    if (query[0] == "PAY") return bank.pay(query[2], stoi(query[3]));
    if (query[0] == "TRANSFER") return bank.transfer(query[2], query[3], stoi(query[4]), stoi(query[1]));
    if (query[0] == "ACCEPT_TRANSFER") return bank.accept_transfer(stoi(query[1]), query[2], query[3]);
    if (query[0] == "MERGE") return bank.merge(query[2], query[3]);
    if (query[0] == "TOP_ACTIVITY") return join(bank.top_activity(), ", ", stoi(query[2]));
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
