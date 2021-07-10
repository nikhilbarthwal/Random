module Transaction

open System

type transaction =
    | Buy of Symbol:string * Timestamp:DateTime * Price:float * Quantity:int * Some(Expiry)
    | Sell of Symbol:string * Timestamp:DateTime * Price:float * Quantity:int
    | Deposit of Timestamp:DateTime * Amount:int


let Strip (s:string) =
    let ss = s.Trim()
    let start = if (ss.StartsWith("\"") || ss.StartsWith("\'")) then 1 else 0;
    let offset = if (ss.EndsWith("\"") || ss.EndsWith("\'")) then 1 else 0;
    let z = s.Substring(start, ss.Length - offset)
    if z.StartsWith("$") then z.Substring(1) else z


let Parse (tokens:string[]) : transaction option =
    let timestamp = DateTime.Parse(tokens.[0])
    match tokens.[1] with
    | s when (s = "Sell") || (s = "Buy") ->
        let price: float = tokens.[5] |> float
        let quantity: int = tokens.[4] |> int
        Some(
            if (s = "Sell") then
                Sell(tokens.[2], timestamp, price, quantity)
            else
                Buy(tokens.[2], timestamp, price, quantity)
        )

    | _ -> None



let ParseFile (filename:string) : transaction list =
    System.IO.File.ReadLines(filename)
    |> List.ofSeq
    |> List.map (fun (s:string) -> s.Trim())
    |> List.filter (fun (s:string) -> ((String.length s) > 0) && not(s.StartsWith("#")))
    |> List.map (fun (s:string) -> let z = s.Split() in (z |> Array.map Strip))
    |> List.filter (fun l -> (l.Length > 1) && (l.[0] <> "Date"))
    |> List.choose Parse
