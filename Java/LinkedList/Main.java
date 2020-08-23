import java.lang.Integer;

public class Main
{
    public static void main(String[] args)
    {
        int[] arr = {4, 5, 8, 3, 2, 9, 0, 1};
        List<Integer> L = new List<Integer>();

        for(int a : arr)
            L.add(a);

        L.print();

        System.out.println("_____________");
        Integer x1 = 5;
        Integer x2 = 6;
        Integer y = x1 + x2;
        System.out.println(y);
    }
}
