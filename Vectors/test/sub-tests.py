import unittest
import vector

class TestSubMethods(unittest.TestCase):

    def test_uneven_size(self) -> None:
        try:
            _discard = vector.sub([1, 2, 3], [4, 5])
            self.assertFalse(True)
        except:
            self.assertTrue(True)

    def test_postive_value(self) -> None:
        z = vector.sub([4.6, 5.2, 6.9], [1.5, 2.8, 3.2])
        self.assertEqual(z, [4.6 - 1.5, 5.2 - 2.8, 6.9 - 3.2])

    def test_negative_value(self) -> None:
        z = vector.sub([1.6, 2.2, 3.5], [4.9, 5.2, 6.9])
        self.assertEqual(z, [1.6 - 4.9, 2.2 - 5.2, 3.5 - 6.9])

if __name__ == '__main__':
    unittest.main()
