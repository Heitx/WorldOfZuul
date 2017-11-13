package BLL.character;

import java.util.Scanner;

public class ProfessorPutricide extends SpecificNPC implements NPC, Stationary{
    private Scanner scanner;
    private String elixier;

    public ProfessorPutricide(){
        super();
        scanner = new Scanner(System.in);
    }

    @Override
    public String getName() {
        return "ProfessorPutricide";
    }

    @Override
    public boolean isGood() {
        return true;
    }

    @Override
    public String[] message() {
        return new String[]{
                "[Professor Putricide]: Hello good sir, is there something I can help you with?",
                "Enter: Y/N"
        };
    }

    public boolean hasAcceptedOffer(char c) {
        return c == 'Y' || c == 'y';
    }

    @Override
    public boolean isStationary() {
        return true;
    }


}




