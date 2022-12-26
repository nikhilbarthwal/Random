import unittest
import vector

class TestDotMethods(unittest.TestCase):

    def test_uneven_size(self) -> None:
        try:
            _discard = vector.dot([1.8, 2.5, 3.1], [4.2, 5.5])
            self.assertFalse(True)
        except:
            self.assertTrue(True)

    def test_zero_value(self) -> None:
        z = vector.dot([0.0, 0.0, 0.0], [0.0, 0.0, 0.0])
        self.assertEqual(z, 0.0)

    def test_regular_value(self) -> None:
        z = vector.dot([1.7, 2.3, 3.9], [4.1, 5.3, 6.2])
        self.assertEqual(z, 1.7 * 4.1 + 2.3 * 5.3 + 3.9 * 6.2, 5)


if __name__ == '__main__':
    unittest.main()
