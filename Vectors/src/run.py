from typing import List
import vector


l1: List[float] = [1.2, 1.4]
l2: List[float] = [3.4, 5.4]

# Add vector arg1 and arg2 and return the result
def add(arg1: List[float], arg2: List[float]) -> List[float]:
    return vector.add(arg1, arg2)

# Subtract vector arg1 from arg2 and return the result
def sub(arg1: List[float], arg2: List[float]) -> List[float]:
    return vector.sub(arg1, arg2)

# Take the dot product of vector arg1 and arg2 and return the result
def dot(arg1: List[float], arg2: List[float]) -> float:
    return vector.dot(arg1, arg2)


if __name__ == '__main__':
    print(str(l1), " + ", str(l2), " = ", add(l1, l2))
    print(str(l1), " - ", str(l2), " = ", sub(l1, l2))
    print(str(l1), " . ", str(l2), " = ", dot(l1, l2))
