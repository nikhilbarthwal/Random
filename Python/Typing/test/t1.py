import unittest
import demo


class TestAddMethods(unittest.TestCase):

    def test1(self) -> None:
        z = demo.add(3,4)
        self.assertEqual(z, 7)

    def test2(self) -> None:
        z = demo.add(5,4)
        self.assertEqual(z, 8)


if __name__ == '__main__':
    unittest.main()
