package edu.pdx.cs410J.jonerik.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

 /**
 * This class is created to parse the command line
 * and validate the incoming arguments. The goal
 * of this class is to move functionality out of
 * the main class into its own class.
 **/

public class Validator {


    /**
     * This class is created to parse the command line
     * and validate the incoming arguments. The goal
     * of this class is to move functionality out of
     * the main class into its own class.
     */



        public static String hostOption     = "-host";
        public static String portOption     = "-port";
        public static String printOption    = "-print";
        public static String readMeOption   = "-README";
        public static String searchOption   = "-search";
        public static boolean print         = false;
        public static boolean search        = false;
        public static boolean host          = false;
        public static boolean port          = false;


        public static Map<String, String> parseArgs (ArrayList<String> argList) {
            Map <String, String> argMap = new HashMap<>();
            /*
            checkForReadMe(argList);
            setHost(argMap, argList);
            setPort(argMap, argList);
            checkForPrint(argMap, argList);
            checkForSearch(argMap, argList);
            */
            if (search) {
                /*
                if (print) { System.err.println("** Cannot search and print at same time. Check args and try again"); System.exit(1); }
                validateSearch(argList);
                addSearchArgsToMap(argMap, argList);
                */
            }

            else {
                validateCall(argList);
                addCallArgsToMap(argMap, argList);
            }

            return argMap;

        }


        /**
         * Check for the ReadMe, if so execute readMe and exit(0).
         * @param list array list of incoming args
         */

        /*
        public static void checkForReadMe(ArrayList<String> list) {
            if (list.contains(readMeOption)){
                readMe();
                System.exit(0);
            }
        }
        */

        /**
         * check for the host and then put that into the map used in Project 4 class
         */

        /*
        public static ArrayList setHost(Map<String, String> map, ArrayList<String> list) {
            if (list.contains(hostOption)) {
                host = true;
                int index = list.indexOf(hostOption) + 1;
                map.put("host", list.get(index));
                list.remove(index);
                list.remove(index - 1);
            }

            return list;

        }
        */

        /**
         * Check for the port information, and put it into the map used in Project4 class.
         */

        /*


        public static ArrayList setPort(Map<String, String> map, ArrayList<String> list) {
            if (list.contains(portOption)) {
                port = true;
                int index = list.indexOf(portOption) + 1;
                map.put("port", list.get(index));
                list.remove(index);
                list.remove(index - 1);
            }

            return list;

        }
        */


        /**
         * Check for print option, set print boolean to true for main class.
         */

        /*
        public static ArrayList checkForPrint(Map<String, String> map, ArrayList<String> list) {
            if (list.contains(printOption)) {
                map.put("print", "-print");
                list.remove(list.indexOf("-print"));
                print = true;
            }

            return list;

        }
        */

        /**
         * Check for the search option and set bool to true. Important because the args
         * for the search are different than that of any other operation.
         */

        /*
        public static ArrayList checkForSearch(Map<String, String> map, ArrayList<String> list) {
            if (list.contains(searchOption)){
                map.put("search", "-search");
                list.remove(list.indexOf("-search"));
                search = true;
            }

            return list;

        }
        */

        /**
         * Add the arguments to the local map for the Project 4 class for adding a call.
         * The function below adds the args for a command that executes a search.
         */
        public static void addCallArgsToMap(Map<String, String> map, ArrayList<String> list) {

            int i = 0;
            String customer     = list.get(i++);
            String callerNumber = list.get(i++);
            String calleeNumber = list.get(i++);
            String startTime    = list.get(i++) + " " + list.get(i++) + " " + list.get(i++);
            String endTime      = list.get(i++) + " " + list.get(i++) + " " + list.get(i);

            map.put("customer", customer);
            map.put("callerNumber", callerNumber);
            map.put("calleeNumber", calleeNumber);
            map.put("startTime", startTime);
            map.put("endTime", endTime);

        }

        /*
        public static void addSearchArgsToMap(Map<String, String> map, ArrayList<String> list) {

            int i = 0;
            String customer     = list.get(i++);
            //String callerNumber = list.get(i++);
            //String calleeNumber = list.get(i++);
            String startTime    = list.get(i++) + " " + list.get(i++) + " " + list.get(i++);
            String endTime      = list.get(i++) + " " + list.get(i++) + " " + list.get(i);

            map.put("customer", customer);
            //map.put("callerNumber", callerNumber);
            //map.put("calleeNumber", calleeNumber);
            map.put("startTime", startTime);
            map.put("endTime", endTime);

        }
        */

        /**
         * Validate the incoming arguments when the search option is present. If an argument is malformed
         * exit gracefully.
         */

        /*
        private static boolean validateSearch(ArrayList callInfo) {

            int i = 0;

            if (callInfo.size() < 7) {
                System.err.println("** Missing command line arguments in search info. Check call args and try again. ");
                System.exit(1);
            } else if (callInfo.size() > 7) {
                System.err.println("** Extraneous command line arguments in search info. Check call args and try again. ");
                System.exit(1);
            }

            String customer = (String) callInfo.get(i++);
            //String callerNumber = (String) callInfo.get(i++);
            //String calleeNumber = (String) callInfo.get(i++);
            String startTime = callInfo.get(i++) + " " + callInfo.get(i++) + " " + callInfo.get(i++);
            String endTime = callInfo.get(i++) + " " + callInfo.get(i++) + " " + callInfo.get(i);

            if (!customer.matches("[a-zA-Z\\s*' - - ! @ # $ % ^ & * ? 1 2 3 4 5 6 7 8 9 0 , .]+")) {
                System.err.println("** Customer Name Invalid");
                System.exit(1);

            } else if (!startTime.matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/[0-9]{2}([0-9]{2})?[\\s*][0-9][0-2]{0,1}[:][0-5][0-9][\\s*]((A|a|P|p))(M|m)")) {
                System.err.println("** Start Time Invalid");
                System.exit(1);
            } else if (!endTime.matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/[0-9]{2}([0-9]{2})?[\\s*][0-9][0-2]{0,1}[:][0-5][0-9][\\s*]((A|a|P|p))(M|m)")) {
                System.err.println("** End Time Invalid");
                System.exit(1);
            }

            return true;
        }
        */


        /**
         * Validate the incoming arguments. If an argument is malformed
         * exit gracefully. Also check that the number of args is correct.
         */
        public static boolean validateCall(ArrayList callInfo) {

            int i = 0;

            String customer = (String) callInfo.get(i++);
            String callerNumber = (String) callInfo.get(i++);
            String calleeNumber = (String) callInfo.get(i++);
            String startTime = callInfo.get(i++) + " " + callInfo.get(i++) + " " + callInfo.get(i++);
            String endTime = callInfo.get(i++) + " " + callInfo.get(i++) + " " + callInfo.get(i);

            if (!customer.matches("[a-zA-Z\\s*' - - ! @ # $ % ^ & * ? 1 2 3 4 5 6 7 8 9 0 , .]+")) {
                return false;
                //System.exit(1);
            } else if (!callerNumber.matches("[0-9]{3}[-][0-9]{3}[-][0-9]{4}")) {
                return false;
                //System.err.println("** Caller Number Invalid");
                //System.exit(1);
            } else if (!calleeNumber.matches("[0-9]{3}[-][0-9]{3}[-][0-9]{4}")) {
                return false;
                //System.err.println("** Callee Number Invalid");
                //System.exit(1);
            } else if (!startTime.matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/[0-9]{2}([0-9]{2})?[\\s*][0-9][0-2]{0,1}[:][0-5][0-9][\\s*]((A|a|P|p))(M|m)")) {
                return false;
                //System.err.println("** Start Time Invalid");
                //System.exit(1);
            } else if (!endTime.matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/[0-9]{2}([0-9]{2})?[\\s*][0-9][0-2]{0,1}[:][0-5][0-9][\\s*]((A|a|P|p))(M|m)")) {
                return false;
                //System.err.println("** End Time Invalid");
                //System.exit(1);
            }

            return true;
        }

        /**
         * Print a README to the commandline for the user and exit.
         */

        /*
        private static void readMe() {

            DateFormat currDate = new SimpleDateFormat("MM/dd/yyyy");
            Date date = new Date();

            System.out.println("\nREADME:\n\n" +
                            "Jon-Erik Svenson\n" +
                            "Phone Bill Application for Project 4 version 1.0. " + currDate.format(date) + "\n\n" +
                            "This application is designed to record a call to a customers phone bill using\n" +
                            "command line arguments to pass necessary information. The main functionality\n" +
                            "in this version is parsing and validating each of the arguments and putting it on\n" +
                            "a server with REST-ful web services functionality.\n\n" +
                            "The order of the arguments and the corresponding types are as follows:\n\n" +
                            "   customer:           Person whose phone bill we’re modeling\n" +
                            "   callerNumber:       Phone number of caller\n" +
                            "   calleeNumber:       Phone number of person who was called\n" +
                            "   startTime:          Date and time call began (12-hour time)\n" +
                            "   endTime:            Date and time call ended (12-hour time)\n" +
                            "   options are (options may appear in any order):\n" +
                            "   -host hostName      The host computer that the server runs\n" +
                            "   -port portString    The port in which server is listening\n" +
                            "   -print:             Prints a description of the new phone call\n" +
                            "   -README:            Prints a README for this project and exits\n" +
                            "   Date and time should be in the format: mm/dd/yyyy hh:mm am/pm"
            );

        }
        */
}
