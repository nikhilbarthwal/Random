type MyClass(x) =
    let random  = x
    do
        printfn "Init"
        //z <- x

    member val z = x //this.z //random.Next()
            
let class1 = new MyClass(3)

let modify = class1.z <- 5

printfn "class1.AutoProperty = %d" class1.z
