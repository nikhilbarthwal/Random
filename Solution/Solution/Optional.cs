/* *****************************************************
    OPTIONAL.CS: Class for representing optional values
   ***************************************************** */

class optional
{
    private readonly bool valid;

    protected optional(bool b)
    {
        valid = b;
    }

    public bool Valid()
    {
        return valid;
    }
}
