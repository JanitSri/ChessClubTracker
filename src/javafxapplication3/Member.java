// Janit Sriganeshaelankovan 101229102
// Shelton Dmello 101186743
// Saif Bakhtaria 101028504

package javafxapplication3;


public class Member {
    
    private int memberId;
    private String firstName;
    private String lastName;
    private int numWins;
    private int numLosses;
    private int numGames;
    private double winRate;
    
    
    public Member(int memberId, String firstName, String lastName, int numWins, int numLosses, int numGames, double winRate){
        this.memberId = memberId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.numGames = numGames;
        this.numWins = numWins;
        this.numLosses = numLosses;
        this.winRate = winRate;
    }
    
    
    public int getMemberId(){return this.memberId;}
    
    public String getFirstName(){return this.firstName;}
    public void setFirstName(String name){this.firstName = name;}
    
    public String getLastName(){return this.lastName;}
    public void setLastName(String name){this.lastName = name;}
    
    public int getNumGames(){return this.numGames;}
    public void setGamesPlayed(int g){this.numGames += g;}
    
    public String getWinRate(){return String.format("%.2f", this.winRate);}
    public void setWinRate(double wr){this.winRate = wr;}
    
    public int getNumWins(){return this.numWins;}
    public void setNumWins(int w){this.numWins += w;}
    
    public int getNumLosses(){return this.numLosses;}
    public void setNumLosses(int l){this.numLosses += l;}
    
    @Override
    public String toString(){
        String s = "ID: " + getMemberId() + "\nFirst Name: " + getFirstName() + 
                       "\nLast Name: " + getLastName() + "\nGames Played: " + getNumGames() +
                       "\nWins: " + getNumWins() + "\nLosses: " + getNumLosses() + "\nWin Rate: " + getWinRate();
        
        return s;
    }
    
}
