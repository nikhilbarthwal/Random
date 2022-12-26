import unittest
import vector

class TestAddMethods(unittest.TestCase):

    def test_uneven_size(self) -> None:
        try:
            _discard = vector.add([1.5, 2.7, 3.9], [4.1, 5.3])
            self.assertFalse(True)
        except:
            self.assertTrue(True)

    def test_postive_value(self) -> None:
        z = vector.add([4.2, 5.7, 6.2], [1.8, 2.9, 3.2])
        self.assertEqual(z, [4.2 + 1.8, 5.7 + 2.9, 6.2 + 3.2])

    def test_negative_value(self) -> None:
        z = vector.add([1.8, 2.9, 3.2], [-4.2, -5.7, -6.2])
        self.assertEqual(z, [1.8 - 4.2, 2.9 - 5.7, 3.2 - 6.2])


if __name__ == '__main__':
    unittest.main()
