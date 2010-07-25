package knife;

/**
 * KNIFE: Source code analysis for dependency management
 * 
 * A simple little application for getting batch information on how
 * your code relates to itself in various sub modules.
 * 
 * A typical use case is for splitting up large and unruly packages
 * in a code base to smaller ones. Usually it is very tedious to use
 * the IDE to find referenced classes, and visualizing the structure
 * in a conventional way, eg by Maven dependency analysis gives 
 * information that is too dense to derive anything important from.
 * 
 * KNIFE has a few core design goals:
 * 
 *  o Provide code analysis that can answer specific questions, eg
 *    what are my highly referenced objects, and how do they relate
 *    to each other? Analyses should provide direction for package
 *    management work.
 *    
 *  o Print output in a manner that is easily grep-able. Maven spews
 *    ungodly amounts of logging in a manner that is not easily 
 *    susceptible to grep, awk and sed. KNIFE should print output
 *    in a UNIX-friendly way. 
 * 
 */
public class KNIFE {

    /**
     * KNIFE Entry point. Since this is generally untested, it
     * is kept short and sweet, just creating an entry point
     * and doing the bad exit if needed.
     */
    public static void main(String[] argv) throws Exception {  
        Thread.sleep(25000);
        try {
            new Entrypoint(argv, new UsageWriter(System.err)).run();
        } catch (IllegalArgumentException e) {
            System.exit(1);
        }
    }

}
