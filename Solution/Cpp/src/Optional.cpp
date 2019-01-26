
class optional
{
private:
    bool valid;

protected:
    optional(bool b) : valid(b) { }

public:
    bool Valid() { return valid; }
}
