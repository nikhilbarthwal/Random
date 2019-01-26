
class timestamp :  public optional
{
private:
    //  10/Oct/2000:13:55:36 -0700
    int day;
    int month;
    int year;
    int hour;
    int mins;
    int secs;
    string timezone;


    timestamp();

    long epoch();

    long diff(); // Show the difference

public:

    string ToString();

    static timestamp Create();

}
