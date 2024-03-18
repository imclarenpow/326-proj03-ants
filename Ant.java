import java.util.*;

public class Ant{
    private int posX;
    private int posY;
    private String[][] dna;
    private char defaultValue;
    private HashMap<Integer, HashMap<Integer, Character>> map = new HashMap<>();
    // this keeps track of the last step direction
    private char lastStep;
    private int lastStepNum;

    public Ant(ArrayList<String> dna){
        dnaDecoder(dna);
    }
    /** method to understand what order to run the other methods in */
    public void task(int numberOfMoves){
        initialise();
        for(int i=0; i<numberOfMoves; i++){
        // check value of point
            char pointVal = mapGet(posX, posY);
            int ptVal = dnaChoice(pointVal);
            char colour = colourPos(posX, posY, ptVal);
            mapStore(posX, posY, colour);
            char nextStep = stepDirection(ptVal, lastStep); 
            movement(nextStep);
        }

    }

    /** method to process the dna into an array */
    public void dnaDecoder(ArrayList<String> lines){
        // create an nested array for the DNA
        dna = new String[lines.size()][3];
        // write and split lines to new array
        for(int i=0; i<lines.size(); i++){
            dna[i] = lines.get(i).split(" ");
        }
    }

    public void initialise(){
        int initX = 0;
        int initY = 0;
        defaultValue = dna[0][0].charAt(0);
        // ant is presumed to have made last move in first array
        lastStep = dna[0][1].charAt(3);
        lastStepNum = 3;
        mapStore(initX, initY, defaultValue);
    }

    public int dnaChoice(char ptVal){
        for(int i=0; i<dna.length; i++){
            if(ptVal == dna[i][0].charAt(0)){
                return i;
            }
        }
        // this should not ever be triggered
        return -99;
    }

    /** method that controls the position change of the and based on the cardinal point given */
    public void movement(char cardinalPt){
        if(cardinalPt=='N'){
            lastStep='N';
            posY++;
        }else if(cardinalPt=='E'){
            lastStep='E';
            posX++;
        }else if(cardinalPt=='W'){
            lastStep='W';
            posX--;
        }else if(cardinalPt=='S'){
            lastStep='S';
            posY--;
        }
    }

    public void mapStore(int x, int y, char value){
        if(!map.containsKey(x)){
            map.put(x, new HashMap<Integer, Character>());
        }
        HashMap<Integer, Character> innerMap = map.get(x);
        innerMap.put(y, value);
    }
    public char mapGet(int x, int y){
        // if the ant checks the coords and it returns null, assume default value by going:
        if(!map.containsKey(x)){
            map.put(x, new HashMap<Integer, Character>());
        }
        HashMap<Integer, Character> innerMap = map.get(x);
        if(!innerMap.containsKey(y)){
            innerMap.put(y, defaultValue);
        }
        return innerMap.get(y);
    }
    /** utility method for returning the direction of the next step */
    public char stepDirection(int pointVal, char step){
        // iterate through the chars at the point value until the step is out
        if(lastStepNum==dna[pointVal][1].length()-1){
            lastStepNum = 0;
        }else{
            lastStepNum++;
        }
        return dna[pointVal][1].charAt(lastStepNum);
    }
    
    public char colourPos(int x, int y, int posVal){
        for(int i=0; i<dna[posVal][1].length(); i++){
            if(lastStep == dna[posVal][1].charAt(i)){
                return dna[posVal][2].charAt(i);
            }
        }
        // this should never be returned
        return '!';
    }
    // methods for getting info from private datafields
    public String[] getDNA(){
        String[] output = new String[dna.length];
        for(int i=0; i<dna.length; i++){
            for(int j=0; j<dna[i].length; j++){
                output[i] = output[i] + dna[i][j] + " ";
            }
            output[i].trim();
        }
        return output;
    }
    // simple method that returns the coordinates as a string
    public String getPosition(){
        return "# " + posX + " " + posY;
    }
    public String stepDebug(int stepNum){
        return "#" + stepNum + " " + posX + " " + posY + " Last Step: " + lastStep + " Colour of Current Step: " + mapGet(posX, posY);
    }
}