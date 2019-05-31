import io.jenetics.util.ISeq;

import java.util.Collection;

public class Grupo {
	private Collection<Periodo> periodos;
	private String materia;
	public Grupo(Collection<Periodo> periodos, String materia){
		this.periodos = periodos;
		this.materia = materia;
	}
	public String getMateria(){
		return materia;
	}

	public Collection<Periodo> getPeriodos(){
		return this.periodos;
	}

	public int choques(ISeq<Grupo> materias){
		int res =0;
		for (Grupo grupo : materias
			 ) {
			for ( Periodo periodo: grupo.getPeriodos()
				 ) {
				for (Periodo periodo1: periodos
					 ) {
					res = periodo.equals(periodo1)?res+1:res;
				}
			}
		}
		return res;
	}

	@Override
	public String toString() {
		return "Grupo{" +"materia:"+ materia +
			"periodos=" + periodos +
			'}';
	}

	public int comparar(ISeq<Grupo> horario) {
		int res =0;
		for (Grupo grupo: horario) {
			if(grupo.materia.equals(materia)){
				res += -horario.length()*3;
			}
		}
		return res;
	}
}
