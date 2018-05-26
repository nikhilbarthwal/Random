#include <map>
#include <list>
#include <cassert>
#include <iostream>

#define DEBUG

using namespace std;

template <class K, class V>
class LRUCache
{
    private:
        struct node
        {
            K key;
            V value;

            node(K k, V v) : key(k), value(v) { };
        };

        typedef typename list<node>::iterator nodeptr;

        map<K, nodeptr> Map;
        list<node> List;
        int Max;
        int n;

#ifdef DEBUG
    void Check()
    {
        for(auto i=Map.begin(); i!= Map.end(); i++)
        {
            K key = i->first;
            nodeptr Node = i->second;
            assert( key == Node->key);
        }
		return;
    }
#endif

    public:

        LRUCache(int max) : Max(max+1), n(0)
		{
			assert(max>1);
		}

        bool Get(K key, V& value)
        {
            if (Map.count(value) == 0) return false;

            assert(n>0);

            nodeptr Ptr = Map[key];
            value = Ptr->value;

            if (Ptr == List.begin()) return true;
            node Node = *Ptr;
            List.erase(Ptr);
            List.insert(List.begin(), Node);

            Map[key] = List.begin();

#ifdef DEBUG
            assert(Map[key] == List.begin());
            auto ptr = List.begin();
            assert(ptr->key == key);
            assert(ptr->value == value);
            Check();
#endif
        }

        bool Put(K keyIn, V value, K& keyOut)
        {
            if (Map.count(value) == 0)
            {
                node Node(keyIn, value);
                List.insert(List.begin(), Node);
                Map[keyIn] = List.begin();

                n++;
                if (n==Max)
                {
                    auto Ptr = List.end();
                    Ptr--;
                    keyOut = Ptr->key;
					n--;
                    Map.erase(Map.find(keyOut));
                    List.erase(Ptr);
#ifdef DEBUG
                    assert(Map[keyIn] == List.begin());
                    auto ptr = List.begin();
                    assert(ptr->key == keyIn);
                    assert(ptr->value == value);
                    Check();
#endif
                    return true;
                }

#ifdef DEBUG
                assert(Map[keyIn] == List.begin());
                auto ptr = List.begin();
                assert(ptr->key == keyIn);
                assert(ptr->value == value);
                Check();
#endif
                return false;
            }

            nodeptr Ptr = Map[keyIn];
            node Node = *Ptr;
            Node.value = value;
            if (Ptr == List.begin()) return true;
            List.erase(Ptr);
            List.insert(List.begin(), Node);
            return false;
        }

#ifdef DEBUG
        void Print()
        {
            int i=1;
			for(auto p = List.begin(); p!= List.end(); p++)
			{
				cout<<i<<" ("<<p->key<<" , "<<p->value<<")"<<endl;
				i++;
			}
            return;
        }
		
		int GetSize() { return n;};
#endif

};

int main(void)
{
    LRUCache<string, string> x(10);

    string t;
    cout<<x.Put("a", "1", t) << " " << x.GetSize() << endl;
    cout<<x.Put("b", "2", t) << " " << x.GetSize() << endl;
    cout<<x.Put("c", "3", t) << " " << x.GetSize() << endl;
    cout<<x.Put("d", "4", t) << " " << x.GetSize() << endl;
    cout<<x.Put("e", "5", t) << " " << x.GetSize() << endl;
    cout<<x.Put("f", "6", t) << " " << x.GetSize() << endl;
	cout<<x.Put("g", "7", t) << " " << x.GetSize() << endl;
	cout<<x.Put("h", "8", t) << " " << x.GetSize() << endl;
	cout<<x.Put("i", "9", t) << " " << x.GetSize() << endl;
	cout<<x.Put("j", "10", t) << " " << x.GetSize() << endl;
	cout<<x.Put("k", "11", t) << " " << x.GetSize() << endl;
	cout<<"____________________________________"<<endl;

	x.Print();

    return 0;
}

