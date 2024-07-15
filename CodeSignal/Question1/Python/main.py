import json
import solution


def main(name):
    f = open(name)
    data = json.load(f)
    f.close()
    input = data["input"]["queries"]
    expected = data["output"]
    user = data["userOutput"]
    result = solution.solution(input)
    n = len(result)
    assert n == len(expected)
    err = 0
    for i in range(n):
        if result[i] != expected[i]:
            err += 1
    print(err)
#            print(i, input[i], " |ERROR| ", result[i], " / ", expected[i])
#        else:
#            print(i) #, input[i], " : ", result[i])


def test1():
    main("test-30.json")


def test2():
    inp = [
        ["CREATE_ACCOUNT", "1", "account1"],
        ["CREATE_ACCOUNT", "2", "account2"],
        ["CREATE_ACCOUNT", "3", "account3"],
        ["CREATE_ACCOUNT", "4", "account4"],
        ["DEPOSIT", "5", "account1", "2000"],
        ["DEPOSIT", "6", "account2", "3000"],
        ["DEPOSIT", "7", "account3", "4000"],
        ["DEPOSIT", "8", "account4", "5000"],
        ["TRANSFER", "9", "account1", "account2", "500"],
        ["ACCEPT_TRANSFER", "10", "account2", "transfer1"],
        ["TRANSFER", "11", "account2", "account3", "500"],
        ["TRANSFER", "12", "account2", "account4", "500"],
        [""],
        ["MERGE", "13", "account2", "account1"],
        [""]
    ]
    results = solution.solution(inp)
    n = len(results)
    assert n == len(inp)
    for i in range(n):
        print(i, ":", inp[i], " -> ", results[i])


test2()












"""    
        ["TRANSFER", "5", "account1", "account2", "5000"],
        ["TRANSFER", "16", "account1", "account2", "1000"],
        ["ACCEPT_TRANSFER", "20", "account1", "transfer1"],
        ["ACCEPT_TRANSFER", "21", "non-existing", "transfer1"],
        ["ACCEPT_TRANSFER", "22", "account1", "transfer2"],
        ["ACCEPT_TRANSFER", "25", "account2", "transfer1"],
        ["ACCEPT_TRANSFER", "30", "account2", "transfer1"],
        ["TRANSFER", "40", "account1", "account2", "1000"],
        ["ACCEPT_TRANSFER", str(45 + MILLISECONDS_IN_1_DAY), "account2", "transfer2"],
        ["TRANSFER", str(50 + MILLISECONDS_IN_1_DAY), "account1", "account1", "1000"]
    ]

"""