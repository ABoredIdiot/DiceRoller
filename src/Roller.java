import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Roller {
    public static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    //All the sets made by the user
    public static ArrayList<Set> userSets = new ArrayList<>();
    //The queue of dice results to display
    public static ArrayList<String> queue = new ArrayList<>();

    public static char getCharInput() throws IOException {
        String in = input.readLine();
        try {
            return in.charAt(0);
        } catch (StringIndexOutOfBoundsException e){
            System.out.println("Enter a character");
            getCharInput();
        }
        return 'b';
    }

    public static int getIntInput() throws IOException {
        String in = input.readLine();
        try {
            return Integer.parseInt(in);
        } catch (NumberFormatException e){
            System.out.println("Enter a number");
            getIntInput();
        }
        return 0;
    }

    /**
     * DONE: Split into 2 methods.
     *  Method 1: rollDice
     *      Input: int[] of all subsets in set. Die size (0), number of dice (1), modifier (2), group (3)
     *      Output: ArrayList<int[]> of outcomes + modifier, their die size, modifier, and group (index 0 is outcome, index 1 is size of die, 2 is modifier, 3 is group)
     *  Method 2: processDice
     *      Input: ArrayList<int[]> of outcomes and die size, , group
     *      Output: String of the total sum summed up, followed by individual outcomes followed by their sizes and modifier (13, 4 on a d6 +3, 3 on a d6 +3).
     */

    public static ArrayList<int[]> rollDice(int[][] diceInput){
        int count = 0;
        ArrayList<int[]> out = new ArrayList<>();
        for (int i = 0; i < diceInput.length; i++){
            for (int j = 0; j < diceInput[i][1]; j++) {
                out.add(new int[]{(((int) (Math.random() * diceInput[i][0])) + 1) + diceInput[i][2], diceInput[i][0], diceInput[i][2], diceInput[i][3]});
            }
        }
        return out;
    }

    /**
     * Steps:
     * 1) x on a dy + z
     * 2) group1: a, group2: b...
     *  Group adding done
     */
    public static String processDice(ArrayList<int[]> rawDice){
        String out;
        ArrayList<String> XDY = new ArrayList<>();
        int[][] sums = new int[rawDice.size()][2];
        String finalSum = "";
        for (int i = 0; i < rawDice.size(); i++){
            sums[rawDice.get(i)[3]][0] += rawDice.get(i)[0];
            sums[rawDice.get(i)[3]][1]++;
        }
        for (int i = 0; i < sums.length; i++){
            if (sums[i][1] > 1){
                finalSum += "Group" + i + ": " + sums[i][0] + ", ";
            }
        }

        for (int i = 0; i < rawDice.size(); i++){
            XDY.add(rawDice.get(i)[0] + " on a d" + rawDice.get(i)[1] + " +" + rawDice.get(i)[2]);
        }

        out = finalSum + XDY.toString();
        return out;
    }

    public static String rollDX(int d){
        return ((int)(Math.random() * d)) + " on a d" + d;
    }

    /**
     * Initialize the set
     * Name the set
     * Create the diceSet for the set
     */
    public static void makeSet() throws IOException{
        String tempName;
        int numSubSets;
        int tempDie;
        int tempRolls;
        int tempMod;
        int tempGroup;
        //Make the set
        System.out.println("Name your set");
        tempName = input.readLine();
        //Overwrite same-named sets
        deleteSet(tempName);
        userSets.add(new Set(tempName));
        System.out.println("How many subsets do you want?");
        numSubSets = getIntInput();
        //Make the subsets
        for (int i = 0; i < numSubSets; i++){
            System.out.println("Enter the die");
            tempDie = getIntInput();
            System.out.println("Enter the number of times to roll a die");
            tempRolls = getIntInput();
            System.out.println("Enter a modifier to the die");
            tempMod = getIntInput();
            System.out.println("Enter a group");
            tempGroup = getIntInput();
            userSets.get(userSets.size() - 1).addToSet(tempDie, tempRolls, tempMod, tempGroup);
        }

    }

    /**
     * Input: Set
     * Output: Returns each die in the set, rolled the correct number of times
     */
    public static ArrayList<String> rollSet(Set set){

        ArrayList<String> out = new ArrayList<>();
        out.add(set.getName() + ":");
        for (int i = 0; i < set.setSize(); i++){
            for (int j = 0; j < set.getNum(i); j++){
                out.add(rollDX(set.getDx(i)));
            }
        }
        return out;
    }

    public static String rollSetNew(Set set){
        return processDice(rollDice(set.getSet(set)));
    }

    //Runs through each set and finds the one with the given name, or returns null if none/more than 1 match.
    public static Set getNamedSet(String setName){
        int setNum = -1;
        for (int i = 0; i < userSets.size(); i++){
            if (userSets.get(i).getName().equals(setName)){
                setNum = i;
            }
        }
        if (setNum >= 0){
            return userSets.get(setNum);
        } else {
            return null;
        }
    }

    public static void clearQueue(){
        queue.removeAll(queue);
    }

    public static void deleteSet(String setName){
        for (int i = 0; i < userSets.size(); i++){
            if (userSets.get(i).getName().equals(setName)){
                userSets.remove(i);
                i--;
            }
        }
    }

    /**
     * TODO: Set modifying
     * Change die, number, modifiers, groups
     */

    /**
     * TODO: exportSet
     * Input: Set
     * Output: Prints the set to a .txt file
     */
    public static void exportSet(){}

    /**
     * TODO: importSet
     * Input: .txt file
     * Output: adds each set in that file to userSets
     */
    public static void importSet(){}

    /**
     * 2 options
     * a) Old way. All code in a while true loop in the main method. This one used
     * b) New way. Functions handle input. They are in the while true loop
     */
    public static void main(String[] args) throws IOException{
        char switchInput;
        int tempDX;
        int tempNumDice;
        String temp;
        while (true){
            System.out.println("Would you like to make a (s)et, (r)oll a set, roll a number of (d)ice, (e)xport a set, (i)import a set, or (b)reak the loop?");
            switchInput = getCharInput();
            if (switchInput == 'b'){
                break;
            }
            switch (switchInput){
                default:
                    System.out.println("Choose one of the options");
                    break;
                case 's':
                    makeSet();
                    break;
                case 'r':
                    System.out.println("What set do you want to roll?");
                    temp = input.readLine();
                    if (getNamedSet(temp) == null){
                        System.out.println("You did not input a set");
                    } else {
                        System.out.println(rollSetNew(getNamedSet(temp)));
                        //System.out.println(rollSet(getNamedSet(temp)).toString());
                    }
                    break;
                case 'd':
                    System.out.println("What die would you like to roll?");
                    tempDX = getIntInput();
                    System.out.println("How many would you like to roll");
                    tempNumDice = getIntInput();
                    for (int i = 0; i < tempNumDice; i++){
                        queue.add(rollDX(tempDX));
                    }
                    System.out.println(queue.toString());
                    clearQueue();
                    break;
                case 'e':
                    //TODO: This
                    exportSet();
                    System.out.println("Not implemented yet!");
                    break;
                case 'i':
                    //TODO: This
                    importSet();
                    System.out.println("Not implemented yet!");
                    break;
            }
        }
    }
}