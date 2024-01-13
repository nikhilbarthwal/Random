#include <iostream>
#include <list>
#include <map>
#include <string>
#include <cassert>
#include <algorithm>

using namespace std;


class AllForOne {

private:

    struct item {
        list<string> keys;
        int frequency;

        item(const string &key, const int freq) : frequency(freq) {
            keys.push_back(key);
        }

        void removeKey(string key) {
            auto i = std::find(keys.begin(), keys.end(), key);
            assert (i != keys.end());
            keys.erase(i);
        }

        void addKey(string key) {
            keys.push_back(key);
        }

        string anyKey() {
            return *keys.begin();
        }

        int size() {
            return keys.size();
        }

        /* void print() {
            cout << frequency << " :";
            for (const string &k: keys)
                cout << " " << k;
            cout << endl;
        } */
    };

    list<item> data;
    map<string, list<item>::iterator> m;

    list<item>::iterator last() {
        auto i = data.end();
        i--;
        return i;
    }

public:

    virtual ~AllForOne() = default;

    /**
     * Increment the provided key's frequency value by 1.  Or add the key with the frequency 1 if it's not present.
     *
     * Note that the actual frequency value of the key is not necessary to expose to the caller; its value is only kept internally for bookkeeping purposes.
     */

    void incrementKey(const string &key) {

        if (m.find(key) == m.end()) {
            auto i = last();

            if (i->frequency == 1)
                i->addKey(key);
            else {
                item z(key, 1);
                data.push_back(z);
            }
            m[key] = last();
            return;
        }

        auto i = m[key];
        int f = i->frequency + 1;

        if (i == data.begin()) {
            if (i->size() == 1)
                i->frequency = f;
            else {
                i->removeKey(key);
                item z(key, f);
                data.insert(data.begin(), z);
                m[key] = data.begin();
            }

            return;
        }

        auto j = i;
        j--;
        if (j->frequency == f) {
            j->addKey(key);
            m[key] = j;
            assert (m[key]->frequency == f);
        } else {
            item z(key, f);
            data.insert(i, z);
            j = i;
            j--;
            m[key] = j;
            assert (m[key]->frequency == f);
        }
        if (i->size() == 1) data.erase(i); else i->removeKey(key);
    }

    /**
      * Decrement the provided key's frequency value by 1.  Or remove the key if its frequency is 1.  Do nothing if the key is not present.
      *
      * Note that the actual frequency value of the key is not necessary to expose to the caller; its value is only kept internally for bookkeeping purposes.
      */
    void decrementKey(const string &key) {
        if (m.find(key) == m.end()) return;

        auto i = m[key];
        int f = i->frequency - 1;
        auto j = i;
        j++;
        if (i->size() == 1) data.erase(i); else i->removeKey(key);
        if (f == 0)
        {
            m.erase(key);
            return;
        }

        if (j == data.end()) {
            item z(key, f);
            data.push_back(z);
            m[key] = last();
            assert (m[key]->frequency == f);
            return;
        }

        if (j->frequency == f) {
            j->addKey(key);
            m[key] = j;
            return;
        }

        item z(key, f);
        data.insert(j, z);
        j--;
        m[key] = j;
        assert (m[key]->frequency == f);
    }

    /**
     * Get one of the keys with the max frequency, or empty string if there are no keys.
     */
    string getMaxKey() {
        if (data.empty()) return "";
        return data.begin()->anyKey();
    }

    /**
      * Get one of the keys with the min frequency, or empty string if there are no keys.
      */
    string getMinKey() {
        if (data.empty()) return "";
        return last()->anyKey();
    }

    /*
    void print() {
        cout << "______________________________________________" << endl;
        for (auto &i: m)
            cout << (i.first) << " : " << (i.second)->frequency << endl;
        cout << "............................" << endl;
        for (auto &i: data) i.print();
    }
     */
};



int main() {
    AllForOne x;
    x.incrementKey("foo"); // void
    x.incrementKey("foo"); // void
    x.incrementKey("foo"); // void
    x.incrementKey("foo"); // void
    x.incrementKey("bar"); // void
    x.incrementKey("bar"); // void
    x.incrementKey("bar"); // void
    x.incrementKey("bar"); // void
    cout<<x.getMaxKey()<<endl; // "foo" or "bar" can be returned because foo -> 4, and bar -> 4
    cout<<x.getMinKey()<<endl; // "foo" or "bar" can be returned because foo -> 4, and bar -> 4
    x.decrementKey("foo"); // void
    cout<<x.getMaxKey()<<endl; // "bar" because foo -> 3, and bar -> 4
    cout<<x.getMinKey()<<endl;; // "foo" because foo -> 3, and bar -> 4
    x.decrementKey("foo"); // void
    x.decrementKey("foo"); // void
    x.decrementKey("foo"); // void
    cout<<x.getMaxKey()<<endl; // "bar" because foo is not in the structure, and bar -> 4
    cout<<x.getMinKey()<<endl; // "bar" because foo is not in the structure, and bar -> 4
}
