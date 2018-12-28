import java.util.ArrayList;

public class Set {
    //D(x), num rolls, mod, group

    private ArrayList<int[]> diceSet = new ArrayList<>();
    private String name;

    public Set(String baseName){
        setName(baseName);
    }

    public void setName(String newName){
        name = newName;
    }

    public void addToSet(int dx, int num, int mod, int group) {
        diceSet.add(new int[]{dx, num, mod, group});
    }

    public int getDx(int index){
        return diceSet.get(index)[0];
    }

    public int getNum(int index){
        return diceSet.get(index)[1];
    }

    public int[] getSubSet(int index){
        return  diceSet.get(index);
    }

    public int setSize(){
        return diceSet.size();
    }

    public String getName(){
        return name;
    }

    public int[][] getSet(Set in){
        int[][] rtn = new int[in.setSize()][4];
        for (int i = 0; i < in.setSize(); i++){
            rtn[i] = in.getSubSet(i);
        }
        return rtn;
    }
}
