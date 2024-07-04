import java.util.ArrayList;
import java.util.Hashtable;


class Transfer
{
    public Account source;
    public Account target;
    public int amount;
    public int expiry;
};

class Account
{
    String name;
    int balance = 0;
    int transaction = 0;

    Account(String n) { name = n; }

    String deposit(int x)
    {
        balance += x;
        transaction += x;
        return Integer.toString(balance);
    }

    String pay(int x)
    {
        if (balance < x) return "";
        balance -= x;
        transaction += x;
        return Integer.toString(balance);
    }

    String key()
    {
        return name + "(" + Integer.toString(transaction) + ")";
    }

    boolean greater(Account z)
    {
        if (transaction == z.transaction) { return (name.compareTo(z.name) < 0); }
        return transaction > z.transaction;
    }
};

class Bank
{
    Hashtable<String, Account> accounts;
    Hashtable<String, Account> transfers;
    int transfer_count = 1;

    boolean not_exists(String name)
    {
        return (!accounts.containsKey(name));
    }

    String create(String name)
    {
        if (not_exists(name))
        {
            accounts.put(name, new Account(name));
            return "true";
        }
        return "false";
    }

    String deposit(String name, int amount)
    {
        if (not_exists(name)) return "";
        return accounts.get(name).deposit(amount);
    }

    String pay(String name, int amount)
    {
        if (not_exists(name)) return "";
        return accounts.get(name).pay(amount);
    }

    String transfer(String source, String target, int amount, int timestamp)
    {
        if (source == target) return "";
        if (not_exists(source)) return "";
        if (not_exists(target)) return "";

        String key = "transfer" + Integer.toString(transfer_count);
        if (accounts.get(source).balance >= amount) {
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

    public static ArrayList<String> Solution(ArrayList<ArrayList<String>> queries)
    {
        Bank bank = new Bank();
        ArrayList<String> result = new ArrayList<>();
        for(ArrayList<String>  query: queries) result.add(Process(query, bank));
        return result;
    }
}

