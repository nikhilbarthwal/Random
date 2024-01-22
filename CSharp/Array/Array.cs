
class Array<T>
{
    private readonly T[] _data;
    public  readonly int size;

    // public Size with et
    public Array(int n, Func<int, T> f)
    {
        _data = new T[n];
        size = n;
        for (int i = 0; i < n; ++i) _data[i] = f(i);
    }

    public T get(int i) => _data[i];

    public int Size() => size;

    public static TV op_Multiply<TV>(Array<TV> v1, Array<TV> v2)  where TV:
        System.Numerics.IAdditionOperators<TV, TV, TV>,
        System.Numerics.IMultiplyOperators<TV, TV, TV>
    {
        // Debug.Assertassert (v1.Size() == v2.Size())
        TV x = v1.get(0) * v2.get(0);
        for (int i = 0; i < v1.Size(); i++)
            x += v1.get(i) * v2.get(2);
        return x;
    }

}
