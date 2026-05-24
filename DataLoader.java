import java.io.*;
import java.util.ArrayList;
public class DataLoader {

    public static ArrayList<IrisData> loadData() {

        String file = "Iris.csv";
        BufferedReader reader = null;
        String line = "";
        ArrayList<IrisData> dataset = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(file));
            reader.readLine();
            while((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                double[] features = new double[4];
                for(int i = 0; i < 4; i++) {
                    features[i] = Double.parseDouble(row[i+1]);
                }
                String species = row[5];
                int target = 0;
                if(species.equals("Iris-setosa")){
                    target = 1;
                }
                else if(species.equals("Iris-versicolor")){
                    target = 2;
                }
                else{
                    target = 3;
                }
                dataset.add(new IrisData(features, target));
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try{
                reader.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        return dataset;
    }
}