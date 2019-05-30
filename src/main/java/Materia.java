import io.jenetics.util.ISeq;

import java.util.Collection;

public class Materia {
	private Collection<Periodo> periodos;
	public  Materia(Collection<Periodo> periodos){
		this.periodos = periodos;
	}

	public Collection<Periodo> getPeriodos(){
		return this.periodos;
	}

	public int choques(ISeq<Materia> materias){
		int res =0;
		for (Materia materia: materias
			 ) {
			for ( Periodo periodo: materia.getPeriodos()
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
		return "Materia{" +
			"periodos=" + periodos +
			'}';
	}
}
