import io.jenetics.EnumGene;
import io.jenetics.Phenotype;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    GridPane gridMines;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String[] heads = {"hora","Lunes","Martes","Miercoles","Jueves","Viernes","Sabado"};
        Label label;
        gridMines.setVgap(20);
        gridMines.setHgap(30);
        for (int i = 0;i<heads.length;i++) {
            label = new Label(heads[i]);
            gridMines.add(label, i,0);
        }
        String[] sides = {"06:45:00-08:15:00","08:15:00-09:45:00","09:45:00-11:15:00","11:15:00-12:45:00",
                "12:45:00-14:15:00","14:15:00-15:45:00",
                "15:45:00-17:15:00","17:15:00-18:45:00","18:45:00-20:15:00","20:15:00-21:45:00"};
        ArrayList<String> sidesz = new ArrayList<>();
        sidesz.addAll(Arrays.asList(sides));
        ArrayList<String> headsz = new ArrayList<>();
        headsz.addAll(Arrays.asList(heads));
        Label label2;
        for (int i = 0;i < sides.length;i++) {
            label = new Label(sides[i]);
            gridMines.add(label, 0, i+1);
        }
        try {
            Phenotype<EnumGene<Grupo>, Integer> best = Evolucionador.evolucionar(3);
            System.out.println(best.getFitness());
            best.getGenotype().getChromosome().stream().forEach(a->{
                a.getAllele().getPeriodos().stream().forEach(p->{
                    System.out.println(p.intervalo());
                    int hr = sidesz.indexOf(p.intervalo());
                    int dia = headsz.indexOf(p.getDia());
                    gridMines.add(new Label(a.getAllele().getMateria()),dia,hr+1);
                });
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}