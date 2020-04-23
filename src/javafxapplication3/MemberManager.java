// Janit Sriganeshaelankovan 101229102
// Shelton Dmello 101186743
// Saif Bakhtaria 101028504

package javafxapplication3;

import java.util.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class MemberManager {
    
    private static int currentUnusedId;
    private ObservableList<Member> memberList;
    
    public MemberManager(int idSeed){
        this.currentUnusedId = idSeed;
        memberList = FXCollections.observableArrayList();
    }
    
    public void addMember(String fName,String lName,int nw,int nl,int ng,double wr){
        Member m = new Member(currentUnusedId++,fName,lName,nw,nl,ng,wr);
        memberList.add(m);
    }
    
    public boolean deleteMember(int id){
        int loc = findMember(id);
        if(loc != -1){
            memberList.remove(loc);
            return true;
        }
        return false;
    }
    
    public ObservableList<Member> getMembers(){return memberList;}
    
    public String getHighestWinrate(){
        String s = "***Highest Win Rate***\n";
        String temp = ""; 
        double highest = 0;
        
        for(int i=0; i < memberList.size(); i++){
            double memberWinRate = calculateWinRate(memberList.get(i).getNumWins(), memberList.get(i).getNumLosses());
            if(memberWinRate == highest)
            {
                temp += memberList.get(i).toString() + "\n";
                temp += "-----------------------------------\n";
            }
            
            if(memberWinRate > highest){
                temp = "";
                temp += memberList.get(i).toString() + "\n";
                temp += "-----------------------------------\n";
                highest = memberWinRate;
            }
        }
        s += temp;
        return s;
    }
    
    public String getMostWins(){
        String s = "***Most Wins***\n";
        String temp = ""; 
        int highest = 0;
        for(int i=0; i < memberList.size(); i++){
            if(memberList.get(i).getNumWins() == highest)
            {
                temp += memberList.get(i).toString() + "\n";
                temp += "-----------------------------------\n";
            }
            
            if(memberList.get(i).getNumWins() > highest){
                temp = "";
                temp += memberList.get(i).toString() + "\n";
                temp += "-----------------------------------\n";
                highest = memberList.get(i).getNumWins();
            }
        }
        s += temp;
        return s;
    }
    
    public double calculateWinRate(int win, int numGame){
        double calculation = win* 1.0 / numGame;
        double out = Double.isNaN(calculation) ? 0.001 : calculation;
        return out;
    }
    
    private int findMember(int id){
        for(int i=0; i < memberList.size(); i++){
            if(memberList.get(i).getMemberId() == id){return i;}
        }
        return -1;
    }
    
    public boolean memberExists(int id)
    {
        int loc = findMember(id);
        if(loc != -1){
            return true;
        }
        return false;
    }
    
    public String getMember(int id)
    {
        int loc = findMember(id);
        String output = "";
        if(loc != -1){
            output += memberList.get(loc).toString();
        }
        return output;
    }
    
    public int getMemberWins(int id)
    {
        int loc = findMember(id);
        if(loc != -1){
            return memberList.get(loc).getNumWins();
        }
        return -1;
    }
    
    public int getMemberLosses(int id)
    {
        int loc = findMember(id);
        
        if(loc != -1){
            return memberList.get(loc).getNumLosses();
        }
        return -1;
    }
    
    public boolean addLosses(int id, int l){
        int loc = findMember(id);
        if(loc != -1){
            memberList.get(loc).setNumLosses(l);
            memberList.get(loc).setGamesPlayed(l);
            memberList.get(loc).setWinRate(calculateWinRate(memberList.get(loc).getNumWins(), memberList.get(loc).getNumGames()));
            memberList.set(loc, memberList.get(loc));
            return true;
        }
        return false;
    }
    
   public boolean addWins(int id, int w){
        int loc = findMember(id);
        if(loc != -1){
            memberList.get(loc).setNumWins(w);
            memberList.get(loc).setGamesPlayed(w);
            memberList.get(loc).setWinRate(calculateWinRate(memberList.get(loc).getNumWins(), memberList.get(loc).getNumGames()));
            memberList.set(loc, memberList.get(loc));
            return true;
        }
        return false;
    }
    
    @Override
    public String toString(){
        String s = "***Club Members***\n";
        
        for(int i=0; i < memberList.size(); i++){
            s += memberList.get(i).toString() + "\n";
        }
        return s;
    }
}
