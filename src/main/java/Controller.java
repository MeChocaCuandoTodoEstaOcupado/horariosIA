import io.jenetics.EnumGene;
import io.jenetics.Phenotype;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    GridPane gridPane;
    @FXML
    TextField numMaterias;

    private ArrayList<String> sidesz, headsz;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String[] heads = {"hora","Lunes","Martes","Miercoles","Jueves","Viernes","Sabado"};
        Label label;
        /*gridPane.setVgap(20);
        gridPane.setHgap(30);
        for (int i = 0;i<heads.length;i++) {
            label = new Label(heads[i]);
            gridPane.add(label, i,0);
        }*/
        String[] sides = {"06:45:00-08:15:00","08:15:00-09:45:00","09:45:00-11:15:00","11:15:00-12:45:00",
                "12:45:00-14:15:00","14:15:00-15:45:00",
                "15:45:00-17:15:00","17:15:00-18:45:00","18:45:00-20:15:00","20:15:00-21:45:00"};
        sidesz = new ArrayList<>();
        sidesz.addAll(Arrays.asList(sides));
        headsz = new ArrayList<>();
        headsz.addAll(Arrays.asList(heads));
        Label label2;
        /*for (int i = 0;i < sides.length;i++) {
            label = new Label(sides[i]);
            gridPane.add(label, 0, i+1);
        }*/

    }

    public void evolucionar(){
        for (int i = 1;i<7;i++){
            for (int j = 1;j<10;j++){
                Node n = getNodeFromGridPane(gridPane, i, j);
                if(n == null){
                    Label lb =new Label("");
                    lb.setAlignment(Pos.CENTER);
                    lb.setFont(Font.font(18));
                    lb.setContentDisplay(ContentDisplay.CENTER);
                    gridPane.add(lb,i,j);
                }else{
                    Label lb = (Label) n;
                    lb.setText("");
                }
            }
        }
        try {
            Phenotype<EnumGene<Grupo>, Integer> best = Evolucionador.evolucionar(Integer.parseInt(numMaterias.getText()));
            System.out.println(best.getFitness());
            best.getGenotype().getChromosome().stream().forEach(a->{
                a.getAllele().getPeriodos().stream().forEach(p->{
                    int hr = sidesz.indexOf(p.intervalo());
                    int dia = headsz.indexOf(p.getDia());
                    Node n = getNodeFromGridPane(gridPane, dia, hr+1);
                    Label lb = (Label) n;
                    if(lb.getText().length()>0){
                        lb.setText(lb.getText()+"/"+a.getAllele().getMateria());
                    }else{
                        lb.setText(a.getAllele().getMateria());
                    }


                });
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            try {
                if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                    return node;
                }
            }catch(Exception e){};
        }
        return null;
    }

}