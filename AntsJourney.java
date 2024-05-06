import java.util.*;
/**
 * A class to represent an ants journey.
 *
 * @author Isaac Powell and Rochelle Cole
 */
public class AntsJourney {
    private static ArrayList<ArrayList<String>> ants = new ArrayList<ArrayList<String>>();
    private static ArrayList<Long> steps = new ArrayList<>();

    /**
     * The main method to execute the program.
     * @param args command line
     */
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> lines = new ArrayList<String>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        scanner.close();
        lineProcessing(lines);
        Ant[] ant = new Ant[steps.size()];
        // TODO: figure if we need to do ant.length-1 for this loop
            // or if we need to do ant.length
        for(int i=0; i<ant.length; i++){
            ant[i] = new Ant(ants.get(i));
            ant[i].task(steps.get(i));
            String[] dna = ant[i].getDNA();
            for(int j=0; j<dna.length; j++){
                String tempDNA = dna[j];

                System.out.println(tempDNA.substring(4));
            }
            System.out.println(steps.get(i));
            System.out.println(ant[i].getPosition());
            System.out.println();
            ant[i]=null;
        }
    }

    /**
     * A method to Process each line of the .txt file
     * @param lines lines from the .txt file
     */
    public static void lineProcessing(ArrayList<String> lines){
        int arraylistIndex = -1;
       
        for(int i = 0; i<lines.size(); i++){//loop through lines
            if (lines.get(i).trim().isEmpty()) { 
                ants.add(new ArrayList<String>()); //add an ant to the ants array 
                arraylistIndex++;
            }
            else{
                char temp = lines.get(i).charAt(0);//gets first character of the line
                if (Character.isDigit(temp)) {
                    steps.add(Long.parseLong(lines.get(i).trim()));                    
                }
                else if (temp!='#') {
                    if (arraylistIndex==-1) {
                        ants.add(new ArrayList<String>()); //add an ant to the ants array 
                        arraylistIndex++;
                    }
                    ants.get(arraylistIndex).add(lines.get(i));
                }

                }
        }

    }

    /**
     * A method for converting a String to an Integer
     * @param input a String
     * @return an int
     */
    public static int stringToInt(String input){
        try{
            return Integer.parseInt(input);
        }catch(NumberFormatException e){
            System.out.println("stringToInt error");
        }
        // this should never be returned
        return -1;
    }
}
