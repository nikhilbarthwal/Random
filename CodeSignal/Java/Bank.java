import java.util.ArrayList;
import java.util.Arrays;
/*#include <iostream>
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

*

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

*

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

*
    def print(self):
        print("Transfer Count:", self.transfer_count)
        for acc in self.accounts.values():
            acc.print()
*

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

*/

public class Bank
{
    private static String Process(ArrayList<String> query, Bank bank)
    {
        return switch (query.get(0)) {
            case "CREATE_ACCOUNT" -> bank.create(query.get(2));
            case "DEPOSIT" -> bank.deposit(query.get(2), stoi(query.get(3)));
            case "PAY" -> bank.pay(query.get(2), stoi(query.get(3));
            case "TRANSFER" -> bank.transfer(query.get(2), query.get(3), stoi(query.get(4)), stoi(query.get(1)));
            case "ACCEPT_TRANSFER" -> bank.accept_transfer(stoi(query.get(1)), query.get(2), query.get(3));
            case "MERGE" -> bank.merge(query.get(2), query.get(3));
            case "TOP_ACTIVITY" -> String.join(", ", bank.top_activity(query.get(2)));
            default -> "";
        };
    }

    private static ArrayList<String> Solution(ArrayList<ArrayList<String>> queries)
    {
        Bank bank = new Bank();
        ArrayList<String> result = new ArrayList<>();
        for(ArrayList<String>  query: queries)
            result.add(Process(query, bank));
        return result;
    }

    public static void main(String[] args)
    {
        ArrayList<ArrayList<String>> queries = new ArrayList<>();
        queries.add(new ArrayList<>(Arrays.asList("CREATE_ACCOUNT", "1", "acc0")));
        queries.add(new ArrayList<>(Arrays.asList("CREATE_ACCOUNT", "2", "acc1")));
        queries.add(new ArrayList<>(Arrays.asList("CREATE_ACCOUNT", "3", "acc2")));
        queries.add(new ArrayList<>(Arrays.asList("CREATE_ACCOUNT", "4", "acc3")));
        queries.add(new ArrayList<>(Arrays.asList("CREATE_ACCOUNT", "5", "acc4")));
        queries.add(new ArrayList<>(Arrays.asList("CREATE_ACCOUNT", "6", "acc5")));
        queries.add(new ArrayList<>(Arrays.asList("CREATE_ACCOUNT", "7", "acc6")));
        queries.add(new ArrayList<>(Arrays.asList("CREATE_ACCOUNT", "8", "acc7")));
        queries.add(new ArrayList<>(Arrays.asList("CREATE_ACCOUNT", "9", "acc8")));
        queries.add(new ArrayList<>(Arrays.asList("CREATE_ACCOUNT", "10", "acc9")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "11", "acc0", "7757")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "12", "acc1", "8114")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "13", "acc2", "6692")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "14", "acc3", "5429")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "15", "acc4", "7852")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "16", "acc5", "6105")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "17", "acc6", "5747")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "18", "acc7", "8009")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "19", "acc8", "5165")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "20", "acc9", "5966")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "21", "acc0", "344")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "22", "acc1", "222")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "23", "acc2", "377")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "24", "acc3", "172")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "25", "acc4", "251")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "26", "acc5", "497")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "27", "acc6", "472")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "28", "acc7", "103")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "29", "acc8", "171")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "30", "acc9", "448")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "31", "acc6", "acc0", "1358")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "32", "acc0", "acc1", "1150")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "33", "acc3", "acc2", "1235")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "34", "acc0", "acc3", "1539")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "35", "acc2", "acc4", "1253")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "36", "acc2", "acc5", "1397")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "37", "acc5", "acc6", "1861")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "38", "acc2", "acc7", "1518")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "39", "acc3", "acc8", "1635")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "40", "acc1", "acc9", "1669")));
        queries.add(new ArrayList<>(Arrays.asList("TOP_ACTIVITY", "41", "10")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "86400041", "acc0", "506")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "86400042", "acc1", "276")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "86400043", "acc2", "361")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "86400044", "acc3", "757")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "86400045", "acc4", "129")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "86400046", "acc5", "477")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "86400047", "acc6", "676")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "86400048", "acc7", "754")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "86400049", "acc8", "873")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "86400050", "acc9", "242")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "86400051", "acc0", "transfer1")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "86400052", "acc1", "transfer2")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "86400053", "acc2", "transfer3")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "86400054", "acc3", "transfer4")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "86400055", "acc4", "transfer5")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "86400056", "acc5", "transfer6")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "86400057", "acc6", "transfer7")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "86400058", "acc7", "transfer8")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "86400059", "acc8", "transfer9")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "86400060", "acc9", "transfer10")));
        queries.add(new ArrayList<>(Arrays.asList("TOP_ACTIVITY", "86400061", "10")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "86400062", "acc0", "348")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "86400063", "acc1", "166")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "86400064", "acc2", "281")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "86400065", "acc3", "267")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "86400066", "acc4", "421")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "86400067", "acc5", "387")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "86400068", "acc6", "172")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "86400069", "acc7", "427")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "86400070", "acc8", "481")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "86400071", "acc9", "459")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "86400073", "acc9", "acc0", "1938")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "86400074", "acc3", "acc1", "1764")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "86400075", "acc7", "acc2", "1660")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "86400076", "acc4", "acc3", "1541")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "86400077", "acc8", "acc4", "1379")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "86400078", "acc2", "acc5", "1219")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "86400079", "acc5", "acc6", "1519")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "86400080", "acc2", "acc7", "1832")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "86400081", "acc6", "acc8", "1645")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "86400082", "acc2", "acc9", "1960")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "172800083", "acc0", "transfer11")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "172800084", "acc1", "transfer12")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "172800085", "acc2", "transfer13")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "172800086", "acc3", "transfer14")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "172800087", "acc4", "transfer15")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "172800088", "acc5", "transfer16")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "172800089", "acc6", "transfer17")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "172800090", "acc7", "transfer18")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "172800091", "acc8", "transfer19")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "172800092", "acc9", "transfer20")));
        queries.add(new ArrayList<>(Arrays.asList("TOP_ACTIVITY", "172800094", "10")));
        ArrayList<String> result = Solution(queries);
        for(String r: result) System.out.println(r);
    }
}

