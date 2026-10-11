import java.io.*;
import java.util.ArrayList;

public class DataLoader {

    public static ArrayList<IrisData> loadData() {
        String file = "Iris.csv";
        ArrayList<IrisData> dataset = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine(); // Praleidziu csv header
            String line;
            
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
            return dataset;
        }

        if (dataset.isEmpty()) {
            return dataset;
        }

        //Min ir Max reiksmiu paieska kiekvienam iš 4 pozymiu
        double[] minValues = new double[4];
        double[] maxValues = new double[4];

        //Inicializuojame masyvus pirmojo elemento reiksmemis
        for (int i = 0; i < 4; i++) {
            minValues[i] = dataset.get(0).features[i];
            maxValues[i] = dataset.get(0).features[i];
        }

        //Perziuriu visa dataset ir ieskau tikruju min/max
        for (IrisData data : dataset) {
            for (int i = 0; i < 4; i++) {
                if (data.features[i] < minValues[i]) minValues[i] = data.features[i];
                if (data.features[i] > maxValues[i]) maxValues[i] = data.features[i];
            }
        }

        //Reikšmiu normalizavimas i [0, 1]
        for (IrisData data : dataset) {
            for (int i = 0; i < 4; i++) {
                double range = maxValues[i] - minValues[i];
                if (range != 0) {
                    data.features[i] = (data.features[i] - minValues[i]) / range;
                } else {
                    data.features[i] = 0.0;
                }
            }
        }
        return dataset;
    }
}
