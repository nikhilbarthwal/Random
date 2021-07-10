module Utils

let private quote1:char = (char) 34 // "
let private quote2:char = (char) 39 // '

let Chop (s:string) :string =
    let x = s.Trim()
    if (x.Length > 0) then
        let y = if (x.[0]=quote1 || x.[0]=quote2) then x.Substring(1) else x
        let l = y.Length
        if (y.[l-1]= quote1 || y.[l-1]=quote2) then y.Substring(0,l-1) else y
    else ""

let Convert2Float (s:string) :float32 =
    let x = Chop s
    let y = if (x.[0]= '$') then x.Substring(1) else x
    y |> float32

let Convert2Int (s:string) :int =
    let x = Chop s
    let y = if (x.[0]= '$') then x.Substring(1) else x
    y |> int

let FilterOptions l =
    let FoldFun s x = match x with Some(z) -> z::s | _ -> s
    l |> List.fold FoldFun []
