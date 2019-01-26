/* **************************************************************************
    REGISTRY.CS: Registry where the section and it's count values are stored
   ************************************************************************** */

using System;
using System.Collections.Generic;
using System.Diagnostics;

class registry
{

    public registry(int n) { }

    private class node
    {
        // Amount to be decreased if an overflow happens
        private const int Decrease = 3;
        public string section;
        public ulong count = 1;
        public node next = null;
        public node prev = null;

        public node(string k) { section=k; }

        // Decrease the keys by some small amount
        public void Reduce() { count -= Decrease; }

        public void Incr() { count += 1; }
    };

    // Hashmap where key and nodes are stored.
    private Dictionary<string, node> cache = new Dictionary<string, node>();

    // Head and Tail of the doubly linked list where nodes are stored
    private node head = null;
    private node tail = null;

    public void Increment(string section)
    {
        Debug.Assert(section != null, "Section is null");

        node tmp;
        if (cache.TryGetValue(section, out tmp)) // If section exists ...
        {
            if (tmp.count >= ulong.MaxValue) // If the key exceeds Max, then Normalize
                for(node p = head; p != tail; p = p.next)
                    p.Reduce();

            tmp.Incr();

            if (tmp == head) return;

            // Arrange the node so the linked list is always sorted.
            while ((tmp.prev != null) && (tmp.prev.count <= tmp.count))
            {
                node t = tmp.prev;
                ulong n = t.count;
                t.count = tmp.count;
                tmp.count = n;

                string s = t.section;
                t.section = tmp.section;
                tmp.section = s;

                cache[t.section] = t;
                cache[tmp.section] = tmp;
                tmp = t;
            }
        }
        else
        {
            tmp = new node(section);
            cache[section] = tmp;

            if (head == null)
            {
                Debug.Assert(tail == null, "Tail is not null, when head is null");
                head = tail = tmp;
            }
            else // Put the new item in the tail
            {
                Debug.Assert(tail != null, "Tail is null, when head not is null");
                tmp.prev = tail;
                tail.next = tmp;
                tail = tmp;
            }
        }
    }

    public void Remove(string section)
    {
        Debug.Assert(cache.ContainsKey(section), "Tries removing section that does not exists");
        node tmp = cache[section];
        cache.Remove(section);

        if (head == tmp)
        {
            head = head.next;
            head.prev = null;
        }
        else if (tmp == tail)
        {
            tail = tail.prev;
            tail.next = null;
        }
        else
        {
            tmp.prev.next = tmp.next;
            tmp.next.prev = tmp.prev;
        }
    }

    public void PrintStats(int n)
    {
        node p = head;
        Console.WriteLine("\nTop Hits:");
        for (int i = 0; (p != null) && (i < n) ; i++, p = p.next)
            Console.WriteLine("\t" + p.section + " : " + p.count);
        Console.WriteLine("");
    }
}
