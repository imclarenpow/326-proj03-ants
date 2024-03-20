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
     * Setting the starting point at 0,0 , the default value as the first character and is north facing having arrived from the south where plausible.
     */
    public void initialise(){
        int initX = 0;
        int initY = 0;
        defaultValue = dna[0][0].charAt(0);
        // ant is presumed to have made last move in first array if no dna for a North facing step is present
        for(int i = 0; i<4; i++){
            if(dna[0][1].charAt(i)=='N'){
                lastStep = 'N';
                lastStepNum = i;
            }
            else{
                lastStep = dna[0][1].charAt(3);
                lastStepNum = 3;
            }
        }
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
        if(lastStepNum==dna[pointVal][1].length()-1){
            lastStepNum = 0;
        }else{
            lastStepNum++;
        }
        return dna[pointVal][1].charAt(lastStepNum);
    }
    
    /**
     * A method to return colour of the given position
     * @param x position on the x-axis
     * @param y position on the y-axis
     * @param posVal
     * @return colour at the given position
     */
    public char colourPos(long x, long y, int posVal){
        for(int i=0; i<dna[posVal][1].length(); i++){
            if(lastStep == dna[posVal][1].charAt(i)){
                return dna[posVal][2].charAt(i);
            }
        }
        // this should never be returned
        return '!';
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