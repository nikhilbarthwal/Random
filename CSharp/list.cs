/* ***********************************************
    KEYCACHE.CS: Class for representing Key Cache
   *********************************************** */

using System.Collections.Generic;
using System.Diagnostics;

// Node for Key,Value pair for the Doubly linked list.


class MyList<T>
{
    private class node
    {
        public readonly T data;
        public node next;
        public node prev;
        public node(T x)
        {
            data = x;
            next = null;
            prev = null;
        }
    }
    public MyList()
    {
        head = null;
        tail = null;
    }

    // Head and Tail for the Double linked list.
    private node head;
    private node tail;

    public void Add(T x)
    {
        //Debug.Assert(key != null, "Section is null");
        //Debug.Assert(cache.Count< max, "keyCache size blew up");

        node tmp = new node(x);

        // Add that element ...
        if (head == null) // If list is empty, initialize head & tail.
        {
            Debug.Assert(tail == null, "Tail not null, when head is null");
            head = tail = tmp;
        }
        else  // If list is empty, add the element in head.
        {
            Debug.Assert(tail != null, "Tail should not be null, when head is null");
            tail.next = tmp;
            tmp.prev = tail;
            tail=tmp;
        }
    }


    // When you implement IEnumerable, you must also implement IEnumerator. 
    public class Enum : IEnumerator
    {
        private node current = null;
        private node head = null;

        private Enum(node x)
        {
            current = head = x;
        }

        public bool MoveNext()
        {
            current = current.next;
            return (current != null);
        }

        public void Reset()
        {
            current = head;
        }

        public T Current
        {
            get { return current.data; }
        }

        object IEnumerator.Current
        {
            get { return (object) current.data; }
        }
    }
}

/* When you implement IEnumerable, you must also implement IEnumerator. 
public class Enum<T> : IEnumerator<T>
{
    private node<T> current = null;
    private node<T> head = null;

    public Enum(node<T> x)
    {
        current = head = x;
    }

    public bool MoveNext()
    {
        current = current.next;
        return (current != null);
    }

    public void Reset()
    {
        current = head;
    }

    public T Current
    {
        get { return current.data; }
    }

    object IEnumerator<T>.Current
    {
        get { return (object) current.data; }
    }
}*/
    

class App
{
    static void Main()
    {
        
        MyList<string> X = new MyList<string>();
        /*Person[] peopleArray = new Person[3]
        {
            new Person("John", "Smith"),
            new Person("Jim", "Johnson"),
            new Person("Sue", "Rabon"),
        };

        People peopleList = new People(peopleArray);
        foreach (Person p in peopleList)
            Console.WriteLine(p.firstName + " " + p.lastName); */

    }
}

//MoveNext
//MovePrevious
//Data
