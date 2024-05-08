import java.util.*;
/**
 * A class to represent an ant and its movement on a grid.
 *
 * @author Isaac Powell and Rochelle Cole
 */
public class Ant{
    private long posX;
    private long posY; 
    private String[][] dna;
    private char defaultValue; // char to represent the default value of the squre for the ant
    private HashMap<Long, HashMap<Long, Character>> map = new HashMap<>();
    // this keeps track of the last step direction
    private char lastStep; // char to represent the direction of last step taken by the ant
    private int lastStepNum; // int to keep track of the index place of the last step in the DNA structure

    /**
     * Constructor to initialize the ant.
     * 
     * @param dna The DNA of the ant as a list of strings.
     */   
    public Ant(ArrayList<String> dna){
        dnaDecoder(dna);
    }

    /** 
     * A method to understand what order to run the other methods in 
     * @param numberOfMoves the number of steps the ant should take.
     * */
    public void task(long numberOfMoves){
        initialise();        
        for(long i=0; i<numberOfMoves; i++){
        // check value of point
            char pointVal = mapGet(posX, posY);
            int ptVal = dnaChoice(pointVal); 
            char nextStep = stepDirection(ptVal, lastStep); 
            char colour = dna[ptVal][2].charAt(lastStepNum);
            mapStore(posX, posY, colour);
            movement(nextStep);
        }

    }

    /** 
     * A method to process the dna into an array 
     * @param lines the line from the txt file
     * */
    public void dnaDecoder(ArrayList<String> lines){
        // create an nested array for the DNA
        dna = new String[lines.size()][3];
        // write and split lines to new array
        for(int i=0; i<lines.size(); i++){
            dna[i] = lines.get(i).split(" ");
        }
    }
    /**
     * A method to initialise the ant.
     * Setting the starting point at 0,0 , the default value as the first character and is north facing having arrived from the south .
     */
    public void initialise(){
        int initX = 0;
        int initY = 0;
        defaultValue = dna[0][0].charAt(0);
        lastStep = 'N';
        mapStore(initX, initY, defaultValue);
    }

    /**
     * Method to return the index of DNA based off the current value
     * @param ptVal
     * @return Index of the DNA
     */
    public int dnaChoice(char ptVal){
        for(int i=0; i<dna.length; i++){
            if(ptVal == dna[i][0].charAt(0)){
                return i;
            }
        }
        // this should not ever be triggered
        return -99;
    }

    /** 
     * A method that controls the position change of the and based on the cardinal point given.
     * @param cardinalPt the direction of the step either N,S,E,W
     */
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
    /**
     * A method to store position and colour the position
     * @param x position on the x-axis
     * @param y position on the y-axis
     * @param value colour of square
     */
    public void mapStore(long x, long y, char value){
        if(!map.containsKey(x)){
            map.put(x, new HashMap<Long, Character>());
        }
        HashMap<Long, Character> innerMap = map.get(x);
        innerMap.put(y, value);
    }

    /**
     * A Method to return colour of given square if it is null
     * @param x position on the x-axis
     * @param y position on the y-axis
     * @return colour of the square at the given position
     */
    public char mapGet(long x, long y){
        // if the ant checks the coords and it returns null, assume default value by going:
        if(!map.containsKey(x)){
            map.put(x, new HashMap<Long, Character>());
        }
        HashMap<Long, Character> innerMap = map.get(x);
        if(!innerMap.containsKey(y)){
            innerMap.put(y, defaultValue);
        }
        return innerMap.get(y);
    }

    /**
     * A utility method for returning the direction of the next step
     * @param pointVal
     * @param step
     * @return Direction of the next step
     */
    public char stepDirection(int pointVal, char step){
        // iterate through the chars at the point value until the step is out
        if(step == 'N'){
            lastStepNum = 0;
        }else if (step == 'E') {
            lastStepNum = 1;
        }else if (step == 'S') {
            lastStepNum = 2;
        }else if (step == 'W') {
            lastStepNum = 3;
        }
        return dna[pointVal][1].charAt(lastStepNum);
    }

    /**
     * A method for getting info from private datafields
     * @return DNA of the ant as a String array
     */
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
    
    /**
     * A simple method that returns the coordinates as a string
     * @return Position of the ant as a String
     */
    public String getPosition(){
        return "# " + posX + " " + posY;
    }
}   