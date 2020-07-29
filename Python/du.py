# statememt = line(string) ; block(list(string))
from typing import List


class Statement:

    tab = "_"

    def _init_(self):
        raise RuntimeError("Cannot create statement object directly!")

    def print(self, filename: str):
        with open(filename, 'w') as f:
            for x in self.print_this():
                f.write("%s\n" % x)


class Line(Statement):

    def __init__(self, s: str):
        self.content: str = s

    def print_this(self: Statement) -> List[str]:
        return [self.tab + self.content]


class Block(Statement):

    def __init__(self, l: List[Statement]):
        self.contents: List[Statement] = l

    def print_this(self: Statement) -> List[str]:
        z = [x.print_this() for x in self.contents]
        return [(self.tab + x) for y in z for x in y]


if __name__ == "__main__":
    n: Statement = Block([Line("nikhil"), Block([Line("nik"), Block([Line("middle")]), Line("hil")])])
    n.print("example.txt")
	