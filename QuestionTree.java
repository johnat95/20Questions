


//D:\Coding\Java Projects\20Questions\src\question1.txt
import com.sun.istack.internal.Nullable;

import java.io.PrintStream;
import java.sql.SQLOutput;
import java.util.Objects;
import java.util.Scanner;
import java.util.Stack;

public class QuestionTree {
    private int totalGames;
    private int gamesWon;

    Scanner scan = new Scanner(System.in);

    private UserInterface ui;

    private QuestionNode root;

    public QuestionTree(UserInterface ui){
        this.ui = ui;
    }

    private boolean getBoolean(){
        return scan.nextBoolean();
    }

    public void play(){

        QuestionNode currentNode = root;

        do {

            // TODO: 6/4/2022 regex
            String article;
            switch (currentNode.displayString.charAt(0)){
                case 'a':
                case 'e':
                case 'i':
                case 'o':
                case 'u': article = "an ";
                        break;
                default: article = "a ";
            }


            if (currentNode.isLeaf()) {
                System.out.println("Is it " + article + currentNode.displayString);
            } else {
                System.out.println(currentNode.displayString);
            }


            // TODO: 6/5/2022 fix this. Incorrect flow. 
            if(!currentNode.isLeaf()){
                if(getBoolean()){
                    currentNode = currentNode.leftNode;
                }else{
                    currentNode = currentNode.rightNode;
                }
            }else {
                if(getBoolean()){
                    System.out.println("I win!");
                    break;
                }
                
                //get new node information
                System.out.println("I lost! What was your object?");
                String userObject = scan.nextLine().toLowerCase();
                
                System.out.println("Type a yes/no question to distinguish your item from "+ userObject+"?");
                String userQuestion = scan.nextLine();

                System.out.println("What is the answer for your question? Enter y or n.");
                Boolean affirmative = null;
                
                do {
                    String userResponse = scan.nextLine().toLowerCase();
                    
                    switch (userResponse){
                        case "y":
                            affirmative = true;
                            break;
                        case "n:":
                            affirmative = false;
                            break;
                        default:
                            System.out.println("Enter y or n.");
                    }
                    
                }while (Objects.equals(affirmative, null));
            }



        }while (getBoolean());

    }


    public void load(Scanner in){

        if(in ==null){
            throw new IllegalArgumentException();
        }

        QuestionNode previousNode = null;
        QuestionNode currentNode;

        Stack<QuestionNode> questionStack = new Stack<>();
        while(in.hasNextLine()){

            String currentLine = in.nextLine();
            System.out.println(currentLine);

            currentNode = new QuestionNode(currentLine.substring(2));

            //if current node isn't a leaf,
            if(currentLine.startsWith("Q")){
                questionStack.add(currentNode);
            }

            if(previousNode == null){

                root = currentNode;
                previousNode = currentNode;
                continue;
            }

            if(previousNode.leftNode == null){
                previousNode.leftNode = currentNode;
            }else{
                previousNode.rightNode = currentNode;
            }

            previousNode = currentNode;

            if(currentLine.startsWith("A") && !questionStack.isEmpty()){
                previousNode = questionStack.pop();
            }

        }
    }

    public void save(PrintStream out){

        if(out ==null){
            throw new IllegalArgumentException();
        }
        Stack<QuestionNode> unprintedStack = new Stack<>();

        unprintedStack.add(root);


        while(!unprintedStack.isEmpty()){

            QuestionNode currentNode = unprintedStack.pop();

            while(!currentNode.isLeaf()){
                out.println("Q: " + currentNode.displayString);

                //save right node to stack
                unprintedStack.add(currentNode.rightNode);
                //move to next left
                currentNode = currentNode.leftNode;
            }

            //print leaf
            out.println("A: " + currentNode.displayString);

        }

    }

    public int totalGames() {
        return totalGames;
    }

    public int gamesWon(){
        return gamesWon;
    }


    // TODO: 6/4/2022 continue comments
    public void addNode(QuestionNode childNode, String questionString,String answerString){
        String leafString = childNode.displayString;

        //set childNode display string to question string
        childNode.displayString = questionString;

        childNode.leftNode = new QuestionNode(answerString);
        childNode.rightNode = new QuestionNode(leafString);
    }


    public void printTraversePreorder(QuestionNode rootNode){

        Stack<QuestionNode> unprintedStack = new Stack<>();

        unprintedStack.add(rootNode);


        while(!unprintedStack.isEmpty()){

            QuestionNode currentNode = unprintedStack.pop();

            while(!currentNode.isLeaf()){
                System.out.println(currentNode.displayString);

                //save right node to stack
                unprintedStack.add(currentNode.rightNode);
                //move to next left
                currentNode = currentNode.leftNode;
            }

            //print leaf
            System.out.println(currentNode.displayString);

        }

    }


    private class QuestionNode{
        private String displayString;

        private QuestionNode leftNode;
        private QuestionNode rightNode;

        public QuestionNode(String displayString){
            this.displayString = displayString;
        }

        public boolean isLeaf(){
            if(leftNode == null || rightNode == null){
                return true;
            }
            return false;
        }

        @Override
        public String toString() {
            return "Display String: "+displayString +
                    "\nyNode: "+ leftNode.displayString +
                    "\nnNode: "+ rightNode.displayString+ "\n";
        }
    }



}

