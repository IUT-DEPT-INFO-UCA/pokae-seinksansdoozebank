/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 * @date
 * ICapacite.java
 */
package interfaces;

/**
 * @author Leo Donati
 *
 */
public interface ICapacite extends IAttaque {
	String getNom();
	double getPrecision();
	int getPuissance();
	int getPP();
	void resetPP();
	ICategorie getCategorie();
	IType getType();
}
