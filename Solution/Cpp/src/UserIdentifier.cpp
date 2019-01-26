
class userIdentifier :  public optional
{
private:
    string data;

    userIdentifier();

public:

    string ToString();

    static userIdentifier Create();

}
