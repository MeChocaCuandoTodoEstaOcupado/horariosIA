import java.sql.Time;
import java.util.Objects;

public class Periodo {
	private final Time inicio;
	private final  Time fin;
	private final String dia;

	public Periodo(String dia, Time inicio, Time fin) {
		this.dia = dia;
		this.inicio = inicio;
		this.fin = fin;
	}

	public String getDia() {
		return dia;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Periodo)) return false;
		Periodo periodo = (Periodo) o;

		return (inicio.getTime() >= periodo.fin.getTime() || periodo.inicio.getTime() >= fin.getTime()) &&
			dia.equals(periodo.dia);
	}

	@Override
	public int hashCode() {
		return Objects.hash(inicio, fin, dia);
	}

	@Override
	public String toString() {
		return "Periodo{" +
			"inicio=" + inicio +
			", fin=" + fin +
			", dia='" + dia + '\'' +
			'}';
	}

	public String intervalo(){
		return inicio+"-"+fin;
	}
}
