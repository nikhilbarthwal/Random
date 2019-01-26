#ifndef OPTIONAL_H
#define OPTIONAL_H

class optional
{
private:
    bool valid;

protected:
    optional(bool b) : valid(b) { }

public:
    bool Valid() { return valid; }
};
#endif /* OPTIONAL_H */
