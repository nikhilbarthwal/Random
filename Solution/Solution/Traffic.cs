/* ********************************************
    TRAFFIC.CS: Class for representing traffic
   ******************************************** */

using System;
using System.Collections.Generic;
using System.Diagnostics;

class traffic
{
    private bool status; // True for HIGH & False for LOW

    private readonly registry Registry;

    private readonly keyCache KeyCache;

    public traffic(int size)
    {
        Registry = new registry(size);
        KeyCache = new keyCache(size);
        status = false;
    }

    // Structure to hold the timestamps withing last TimeWindos = 2 mins
    private static Queue<DateTime> queue = new Queue<DateTime>();

    public void Update(url Url, timestamp Timestamp)
    {
        if (Url.Valid())
        {
            Registry.Increment(Url.GetSection());

            string outKey = KeyCache.Put(Url.GetSection());
            if (outKey != null)
                Registry.Remove(outKey);
        }

        if (Timestamp.Valid())
            queue.Enqueue(Timestamp.GetValue());
    }

    public void PrintStats(int n)
    {
        Registry.PrintStats(n);
    }

    public int Check(timestamp Timestamp, TimeSpan TimeWindow)
    {
        if (!Timestamp.Valid()) return -1;
        DateTime current = Timestamp.GetValue();

        while ((queue.Count>1) && (TimeWindow > current.Subtract(queue.Peek())))
            queue.Dequeue();

        return queue.Count;
    }

    public bool High
    {
        get
        {
            return !status;
        }
    }

    public bool Low
    {
        get
        {
            return status;
        }
    }

    public void SetHigh()
    {
        Debug.Assert(status, "Trying to High traffic value when it is already high");
        status = true;
    }

    public void SetLow()
    {
        Debug.Assert(!status, "Trying to Low traffic value when it is already low");
        status = false;
    }
}
