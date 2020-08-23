public class List<T>
{
    private Node<T> head = null;

    private class Node<T>
    {
        public T data;
        public Node next;

        public Node(T z)
        {
            this.data = z;
            this.next = null;
        }
    }

    public List()
    {
        return;
    }

    public void add(T z)
    {
        if (head == null)
            head = new Node(z);
        else
        {
            Node current = head;
            while(current.next != null)
                current = current.next;
            current.next = new Node(z);
        }
    }

    public void print()
    {
        Node current = head;
        while(current != null)
        {
            System.out.println(current.data);
            current = current.next;
        }
    }
}
