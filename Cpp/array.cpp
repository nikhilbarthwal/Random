#include <vector>
#include <iostream>

using namespace std;

class Array
{
private:
    vector<char> z;

public:

    Array(const int k): z(k)
        {
            for(int i=0; i<k; i++)
                z[i] ='A' + i;
        }

        char& operator[](const int i)
        {
            return z[i];

        }

        char operator[]( const int i) const
        {
            return z[i];
        }

        void Print()
        {
            for(char& c:z) cout<<c; cout<<endl;
        }
};

int main(void)
{
    Array z(10);
    z[1] = 'Z';
    z[0] = z[1];
    cout<<z[5]<<endl;
    z.Print();
    return 0;
}
