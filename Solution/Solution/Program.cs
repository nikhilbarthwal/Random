/* **********************************************************************
    PROGRAM.CS: The main program and all the important constants defined
   ********************************************************************** */

using System;

#if DEBUG
using System.IO;
#endif

/*
 * Main Program class
 */
static class Program
{
    /*
     * Maximum size of the Map where URL sections are stored
     */
    private const int MaxSize = 10000;

    /*
     * Threshold for declaring High/Low traffic
     */
    private const int Threshold = 1000;

    /*
     * Maximum number of entries to be displayed
     */
    private const int DisplaySize = 10;

    /*
     * Timespan for flipping between High & Low traffic
     */
    private static readonly TimeSpan TimeWindow = TimeSpan.FromMinutes(2);

    static void Main(string[] args)
    {
        traffic Traffic = new traffic(MaxSize);


#if DEBUG
        foreach (string statement in File.ReadAllLines("test.txt"))
#else
        // Infinite loop that keeps reading the incoming traffic.
        string statement;
        while (!string.IsNullOrEmpty(statement = Console.ReadLine()))
#endif
            try
            {
                logStatement LogStatement = new logStatement(statement);
#if DEBUG
                Console.WriteLine("Statement: " + LogStatement);
#endif
                Traffic.Update(LogStatement.Url, LogStatement.Timestamp);

                if (LogStatement.Timestamp.Edge())
                    Traffic.PrintStats(DisplaySize);

                int trafficSize = Traffic.Check(LogStatement.Timestamp, TimeWindow);

                if ((trafficSize >= Threshold) && Traffic.Low)
                {
                    Traffic.SetHigh();
                    Console.WriteLine("High traffic generated an alert - hits = {0}, triggered at {1}",
                        Threshold, LogStatement.Timestamp);
                }

                if ((trafficSize < Threshold) && Traffic.High)
                {
                    Traffic.SetLow();
                    Console.WriteLine("Low traffic generated recoveryhits = {0}, triggered at {1}",
                        Threshold, LogStatement.Timestamp);
                }
            }
            catch (LogParseException e)
            {
                Console.WriteLine("Parse exception in Log Statement: " + e);
            }
    }
}
