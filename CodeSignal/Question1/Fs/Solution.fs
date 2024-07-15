module Solution

open System.Collections.Generic

type Transfer = {
    Source: string
    Target: string
    Amount: int
    Expiry: int }

type Account(name: string) =
    let mutable balance: int = 0
    let mutable transaction: int = 0

    member this.Deposit(x) =
        balance <- balance + x
        transaction <- transaction + x
        $"{balance}"

    member this.Pay(x) =
        balance <- balance - x
        transaction <- transaction + x
        $"{balance}"

    member this.Key = $"{name}({transaction})"
    member this.Balance = balance
    member this.Transaction = transaction
    member this.Name = name
    member this.Add(acc: Account) =
        balance <- acc.Balance
        transaction <- acc.Transaction

type Bank() =
    let accounts = Dictionary<string, Account>()
    let transfers = Dictionary<string, Transfer>()
    let mutable transfer_count = 1

    let absent(key: string) = not (accounts.ContainsKey(key))

    member this.Accounts = accounts

    member this.Create(name: string) =
        if accounts.ContainsKey(name) then "false" else
            let acc = Account(name) in accounts.Add(name, acc)
            "true"

    member this.Deposit(name: string, amount: int) =
        if accounts.ContainsKey(name) then accounts[name].Deposit(amount) else ""

    member this.Pay(name: string, amount: int) =
        if accounts.ContainsKey(name) then accounts[name].Pay(amount) else ""

    member this.Transfer(source: string, target: string, amount: int, timestamp: int) =
        if source = target then ""
        else if absent(source) then ""
        else if absent(target) then ""
        else
            let key = $"transfer{transfer_count}"
            if accounts[source].Balance < amount then "" else
                transfers.Add(key, {
                    Amount = amount;
                    Source = source;
                    Target = target;
                    Expiry = timestamp + 86400000 })
                transfer_count <- transfer_count + 1
                key

    member this.AcceptTransfer(current: int, name: string, key: string) =
        if not (transfers.ContainsKey(key)) then "false" else
            let transfer = transfers[key]
            if current > transfer.Expiry then "false" else
                accounts[transfer.Target].Deposit(transfer.Amount) |> ignore
                accounts[transfer.Source].Pay(transfer.Amount) |> ignore
                "true"

    member this.Merge(source: string, target: string) =
        if absent(source) || absent(target) || (source = target) then "" else
            accounts[target].Add(accounts[source])
            for k in transfers.Keys do
                let t = transfers[k]
                if t.Target = source then transfers.Add(k, {t with Target = target})
            accounts.Remove(source) |> ignore ; "true"

let compare (z1: Account) (z2: Account): int =
    let b = if z1.Transaction = z2.Transaction then (z1.Name < z2.Name)
            else z1.Transaction > z2.Transaction
    if b then -1 else 1

let topActivity(map: Dictionary<string, Account>, n: int, delim: string) =
    let values = map.Values|> Seq.toArray |> Array.sortWith(compare)
    (if (n = -1) || (n >values.Length) then values else values[0 .. n])
    |>  Array.map _.Key |> String.concat delim


let Solution(queries: string[][]) =
    let bank = Bank()

    let solve(query: string[]) =
        match query[0] with
        | "CREATE_ACCOUNT" -> bank.Create(query[2])
        | "DEPOSIT" -> bank.Deposit(query[2], int query[3])
        | "PAY" -> bank.Pay(query[2], int query[3])
        | "TRANSFER" -> bank.Transfer(query[2], query[3], int query[4], int query[1])
        | "ACCEPT_TRANSFER" -> bank.AcceptTransfer(int query[1], query[2], query[3])
        | "MERGE" -> bank.Merge(query[2], query[3])
        | "TOP_ACTIVITY" -> topActivity(bank.Accounts, int query[2], "; ")
        | _ -> ""

    Array.map solve queries
