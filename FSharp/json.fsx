(*
<Project Sdk="Microsoft.NET.Sdk">

    <PropertyGroup>
        <OutputType>Exe</OutputType>
        <TargetFramework>netcoreapp3.1</TargetFramework>
    </PropertyGroup>

    <ItemGroup>
        <Compile Include="Program.fs" />
    </ItemGroup>

    <ItemGroup>
      <PackageReference Include="FSharp.Data" Version="4.0.1" />
      <PackageReference Include="FSharp.Data.JsonProvider" Version="0.5.0" />
    </ItemGroup>

</Project>
*)


open System
open FSharp.Data
open FSharp.Data.JsonExtensions

type Person = {ID: int; Name:string}


[<EntryPoint>]
let main argv =
    let json = """[ { "ID": 1, "Name": "First"  }, { "ID": 2, "Name": "Second" }]"""
    json |> printfn "%s"
    
    
    
    
    let x = "{" + 
                        "  \"id\": 123,\n" +
                        "  \"name\": { \"first\": \"Pankaj\", \"last\": \"Agarwal\" }, \n" +
                        "  \"permanent\": true,\n" +
                        "  \"address\": {\n" +
                        "    \"street\": \"Albany Dr\",\n" +
                        "    \"city\": \"San Jose\",\n" +
                        "    \"zipcode\": 95129\n" +
                        "  },\n" +
                        "  \"phoneNumbers\": [\n" +
                        "    123456,\n" +
                        "    987654\n" +
                        "  ],\n" +
                        "  \"role\": \"Manager\",\n" +
                        "  \"cities\": [\n" +
                        "    \"Los Angeles\",\n" +
                        "    \"New York\"\n" +
                        "  ],\n" +
                        "  \"properties\": {\n" +
                        "    \"age\": \"29 years\",\n" +
                        "    \"salary\": \"1000 USD\"\n" +
                        "  } " +
                        "}";

    let z = JsonValue.Parse(x)
    let x1 = (z ? name ? first).AsString()
    let x2 = (z ? phoneNumbers).AsArray()  |> Array.map (fun x -> x.AsInteger())

    
    
    match JsonValue.Parse(json) with
    | JsonValue.Array(x) ->
        x |> Array.map(fun x -> {ID = System.Int32.Parse((x?ID).ToString()); Name = (string x?Name)})
    | _ -> failwith "fail json"
    |> Array.iter(fun x -> printfn "%i  %s" x.ID x.Name)
    printfn "Hello world %s %A" x1 x2
    0 // return an integer exit code
    




