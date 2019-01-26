/* ***********************************************
    KEYCACHE.CS: Class for representing Key Cache
   *********************************************** */

using System.Collections.Generic;
using System.Diagnostics;

class keyCache
{
    // Node for Key,Value pair for the Doubly linked list.
    private class node
    {
        public string key;
        public node next;
        public node prev;
        public node(string k)
        {
            key=k;
            next=null;
            prev = null;
        }
    }

    private readonly int max;

    public keyCache(int n)
    {
        max = n;
        head = null;
        tail = null;
    }

    // Hashmap string the keys and their nodes for quick reference.
    private Dictionary<string, node> cache = new Dictionary<string, node>();

    // Head and Tail for the Double linked list.
    private node head;
    private node tail;

    // Place a new key in the key cache and if this key displaced a existing key,
    // then return that key, else return null.
    public string Put(string key)
    {
        Debug.Assert(key != null, "Section is null");
        Debug.Assert(cache.Count< max, "keyCache size blew up");

        node tmp;

        if (cache.TryGetValue(key, out tmp)) // If the value exists ...
        {
            if ((tmp == head) || (cache.Count == 1)) return null;

            Debug.Assert(cache.Count > 1, "Cache unexpectedly empty");

            if (tmp == tail) // If the last element ...
                tail = tail.prev; // The delete the last element,
            else // Else delete the element (which is in the middle somewhere)
            {
                tmp.prev.next = tmp.next;
                tmp.next.prev = tmp.prev;
            }

            // And finally move it to the head.
            tmp.prev = null;
            tmp.next = head;
            head.prev = tmp; // THIS WAS THE BUG - This line was missing!
            head = tmp;
        }
        else // If the element does not exists ...
        {
            tmp = new node(key);
            cache.Add(key, tmp);

            // Add that element ...
            if (head == null) // If list is empty, initialize head & tail.
            {
                Debug.Assert(tail == null, "Tail not null, when head is null");
                head = tail = tmp;
            }
            else  // If list is empty, add the element in head.
            {
                Debug.Assert(tail != null, "Tail should not be null, when head is null");
                tmp.next = head;
                head.prev = tmp;
                head=tmp;
            }

            Debug.Assert(cache.Count<= max, "keyCache size blew up");

            // If Cache has exceeded the max limit, then delete the last element and
            // set it to Key Out and also return true (Othwise false).
            if (cache.Count == max)
            {
                Debug.Assert(tail != null, "Tail should not be null");
                Debug.Assert(head != null, "Head should not be null");
                string keyOut = tail.key;
                cache.Remove(keyOut);
                tail = tail.prev;
                return keyOut;
            }
        }
        return null;
    }
}
