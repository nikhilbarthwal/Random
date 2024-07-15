from typing import Dict, List


class Account:
    def __init__(self, name: int):
        self.balance = 0
        self.id = name
        self.transactions = 0
        self.transfers = {}

    def print(self):
        print("\nName: ", self.id)
        print("Balance: ", self.balance)
        print("Transaction: ", self.transactions)
        print("Transfer: ")
        for t in self.transfers:
            print("\t", t, "-> Amount:", self.transfers[t][0], "/ Expiry", self.transfers[t][1], "/ From:", self.transfers[t][2].id)

    def deposit(self, amount):
        self.balance += amount
        self.transactions += amount
        return str(self.balance)

    def pay(self, amount):
        if self.balance >= amount:
            self.balance -= amount
            self.transactions += amount
            return str(self.balance)
        return ""

    def str(self):
        return self.id + "(" + str(self.transactions) + ")"

    def __gt__(self, z):
        if self.transactions == z.transactions:
            return self.id < z.id
        return self.transactions > z.transactions

    def transfer_money(self, key: str, amount: int, source, timestamp: int):
        if source.balance >= amount:
            self.transfers[key] = (amount, timestamp + 86400000, source)
            return True
        return False

    def accept_transfer(self, current: int, key: str):
        if key in self.transfers:
            value = self.transfers[key]
            stored_time = value[1]
            amount = value[0]
            source = value[2]
            del self.transfers[key]
            if current <= stored_time and source.balance >= amount:
                self.deposit(amount)
                source.pay(amount)
                return "true"
        return "false"

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


class Bank:
    def __init__(self):
        self.accounts: Dict[str, Account] = {}
        self.transfer_count = 1

    def print(self):
        print("Transfer Count:", self.transfer_count)
        for acc in self.accounts.values():
            acc.print()

    def create(self, name) -> str:
        if name in self.accounts:
            return "false"
        self.accounts[name] = Account(name)
        return "true"

    def deposit(self, name, amount) -> str:
        if name in self.accounts:
            return self.accounts[name].deposit(amount)
        return ""

    def pay(self, name, amount) -> str:
        if name in self.accounts:
            return self.accounts[name].pay(amount)
        return ""

    def transfer(self, source: str, target: str, amount: int, timestamp: int):
        if (source in self.accounts) and (target in self.accounts):
            if source == target:
                return ""
            key = "transfer" + str(self.transfer_count)
            target_account = self.accounts[target]
            source_account = self.accounts[source]
            if target_account.transfer_money(key, amount, source_account, timestamp):
                self.transfer_count += 1
                return key
            return ""
        return ""

    def accept_transfer(self, current, name, key):
        if name in self.accounts:
            return self.accounts[name].accept_transfer(current, key)
        return "false"

    def merge(self, source: str, target: str):
        if (source in self.accounts) and (target in self.accounts):
            if source == target:
                return ""
            target_account = self.accounts[target]
            source_account = self.accounts[source]
            target_account.merge(source_account)
            for account in self.accounts.values():
                if (account.id == source) and (account.id == target):
                    pass
                else:
                    account.substitute(source_account, target_account)
            del self.accounts[source]
            return "true"
        return ""


bank = Bank()


def process(query):
    if query[0] == "CREATE_ACCOUNT":
        return bank.create(query[2])

    if query[0] == "DEPOSIT":
        return bank.deposit(query[2], int(query[3]))

    if query[0] == "PAY":
        return bank.pay(query[2], int(query[3]))

    if query[0] == "TOP_ACTIVITY":
        accounts = list(bank.accounts.values())
        accounts.sort(reverse=True)
        r = accounts[0:int(query[2])]
        return ", ".join(list([x.str() for x in r]))

    if query[0] == "TRANSFER":
        return bank.transfer(query[2], query[3], int(query[4]), int(query[1]))

    if query[0] == "ACCEPT_TRANSFER":
        return bank.accept_transfer(int(query[1]), query[2], query[3])

    if query[0] == "MERGE":
        return bank.merge(query[2], query[3])


    #  assert (False)
    print("_________________________________________________________________")
    bank.print()
    return None


def solution(queries):
    return [process(query) for query in queries]

