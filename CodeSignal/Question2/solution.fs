
open System.Collections.Generic

type Value(value: string, start: int, ttl: int) =
    new (v) = Value(v, -1, -1)
    member this.Start = start
    member this.Stop = start + ttl
    member this.Value(timestamp) =
        if start = -1 then value else
            if (timestamp >= this.Start) && (this.Stop > timestamp) then value else ""
    member this.ToString() = $"{value} {start} {ttl}"

type Database() =
    let data = Dictionary<string, Dictionary<string, Value>>()
    
    let cleanup(timestamp: int) =
        let clean(m: Dictionary<string, Value>) =
            for k in m.Keys do
                if (m[k].Stop >= 0) && (m[k].Stop <= timestamp) then
                    m.Remove(k) |> ignore
        for key in data.Keys do
            clean(data[key])
            if data[key].Count = 0 then (ignore <| data.Remove(key))

    
    member this.Backup(timestamp: int): string =
        if timestamp >=0 then cleanup(timestamp)
        $"{data.Count}"

    member this.Get(key, field, timestamp: int): string =
        if timestamp >=0 then cleanup(timestamp)
        if data.ContainsKey(key) then
            let z = data[key]
            if z.ContainsKey(field) then
                z[field].Value(timestamp)
            else ""
        else ""

    member this.Set(key, field, value, timestamp: int, ttl: int): string =
        if timestamp >=0 then cleanup(timestamp)
        if not (data.ContainsKey(key)) then data.Add(key, Dictionary<string, Value>())
        let z = data[key]
        z.Remove(field) |> ignore
        let v = if ttl = -1 then Value(value) else Value(value, timestamp, ttl)
        z.Add(field, v)
        printfn $"Add {field} {v.ToString()}"
        ""
    
    member this.Delete(key, field, timestamp: int): string =
        if timestamp >=0 then cleanup(timestamp)
        if data.ContainsKey(key) then
            let z = data[key]
            if z.Remove(field) then "true" else "false"
        else "false"

    member this.Scan(key, prefix: string, timestamp: int): string =
        if timestamp >=0 then cleanup(timestamp)
        if data.ContainsKey(key) then
            let z = data[key]
            let keys: string[] = z.Keys |> Array.ofSeq |> Array.filter (fun key -> key.StartsWith(prefix)) |> Array.sort
            let f (k: string) = $"{k}({z[k].Value(timestamp)})"
            keys |> Array.map f |> String.concat ", "
        else ""


let solution (queries: string[][]) =
    let db = Database()

    let solve(query: string[]) =
        match query[0] with
        | "SET" -> db.Set(query[1], query[2], query[3], -1, -1)
        | "GET" -> db.Get(query[1], query[2], -1)
        | "SET_AT" -> db.Set(query[1], query[2], query[3], int query[4], -1)
        | "SET_AT_WITH_TTL" -> db.Set(query[1], query[2], query[3], int query[4], int query[5])
        | "GET_AT" -> db.Get(query[1], query[2], int query[3])
        | "DELETE_AT" -> db.Delete(query[1], query[2], int query[3])
        | "DELETE" -> db.Delete(query[1], query[2], -1)
        | "SCAN" -> db.Scan(query[1], "", -1)
        | "SCAN_BY_PREFIX" -> db.Scan(query[1], query[2], -1)
        | "SCAN_AT" -> db.Scan(query[1], "", int query[2])
        | "SCAN_BY_PREFIX_AT" -> db.Scan(query[1], query[2], int query[3])
        | "BACKUP" -> db.Backup(int query[1])
        | _ -> ""

    Array.map solve queries
