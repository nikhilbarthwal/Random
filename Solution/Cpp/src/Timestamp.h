#ifndef TIMESTAMP_H
#define TIMESTAMP_H

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
    bool Edge();
    string ToString();
    static timestamp Create(string, errors&);
};

#endif /* TIMESTAMP_H */
