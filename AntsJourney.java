import java.util.*;

public class AntsJourney {
    private static ArrayList<ArrayList<String>> ants = new ArrayList<ArrayList<String>>();
    private static ArrayList<Integer> steps = new ArrayList<>();
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> lines = new ArrayList<String>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        scanner.close();
        lineProcessing(lines);
        Ant[] ant = new Ant[steps.size()];
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
        }
    }
    public static void lineProcessing(ArrayList<String> lines){
        int arraylistIndex = -1;
        for(int i = 0; i<lines.size(); i++){
            if(!lines.get(i).trim().isEmpty()){
                char temp = lines.get(i).charAt(0);
                if(temp=='#'){
                    ants.add(new ArrayList<String>());

                    arraylistIndex++;
                }else if(Character.isDigit(temp)){
                    steps.add(stringToInt(lines.get(i).trim()));
                }else{
                    ants.get(arraylistIndex).add(lines.get(i));
                }
            }
        }

    }
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
