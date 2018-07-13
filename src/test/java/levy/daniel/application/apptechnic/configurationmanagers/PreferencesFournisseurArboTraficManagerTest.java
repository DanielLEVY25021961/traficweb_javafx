/**
 * 
 */
package levy.daniel.application.apptechnic.configurationmanagers;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * CLASSE PreferencesFournisseurArboTraficManagerTest :<br/>
 * Test JUnit de la classe PreferencesFournisseurArboTraficManager.<br/>
 * <br/>
 *
 * - Exemple d'utilisation :<br/>
 *<br/>
 * 
 * - Mots-clé :<br/>
 * <br/>
 *
 * - Dépendances :<br/>
 * <br/>
 *
 *
 * @author daniel.levy Lévy
 * @version 1.0
 * @since 5 juin 2018
 *
 */
public class PreferencesFournisseurArboTraficManagerTest {

	// ************************ATTRIBUTS************************************/
	
	/**
	 * AFFICHAGE_GENERAL : Boolean :<br/>
	 * Boolean qui commande l'affichage pour tous les tests.<br/>
	 */
	public static final Boolean AFFICHAGE_GENERAL = true;

	/**
	 * KEY_TEST : String :<br/>
	 * "key.test".<br/>
	 */
	public static final String KEY_TEST = "key.test";
	
	/**
	 * VALUE_TEST : String :<br/>
	 * "test".<br/>
	 */
	public static final String VALUE_TEST = "test";
	
	/**
	 * UNUSED : String :<br/>
	 * "unused".<br/>
	 */
	public static final String UNUSED 
		= "unused";
	

	/**
	 * TAILLE_INITIALE : String :<br/>
	 * "taille initiale : ".<br/>
	 */
	public static final String TAILLE_INITIALE 
		= "taille initiale : ";
	
	/**
	 * TAILLE_FINALE : String :<br/>
	 * "taille finale : ".<br/>
	 */
	public static final String TAILLE_FINALE 
		= "taille finale : ";
	
	/**
	 * INITIAL : String :<br/>
	 * "INITIAL : \n".<br/>
	 */
	public static final String INITIAL 
		= "PROPERTIES INITIAL : \n";
	
	/**
	 * FINAL : String :<br/>
	 * "FINAL : \n".<br/>
	 */
	public static final String FINAL 
		= "PROPERTIES FINAL : \n";
	
	/**
	 * KEY_ROOTFILE : String :<br/>
	 * "rootfile".<br/>
	 */
	public static final String KEY_ROOTFILE 
		= "rootfile";

	/**
	 * LOG : Log : 
	 * Logger pour Log4j (utilisant commons-logging).
	 */
	private static final Log LOG 
	= LogFactory.getLog(PreferencesFournisseurArboTraficManagerTest.class);
	

	// *************************METHODES************************************/
	
	 /**
	 * method CONSTRUCTEUR PreferencesFournisseurArboTraficManagerTest() :<br/>
	 * CONSTRUCTEUR D'ARITE NULLE.<br/>
	 * <br/>
	 */
	public PreferencesFournisseurArboTraficManagerTest() {
		super();
	} // Fin de CONSTRUCTEUR D'ARITE NULLE.________________________________
	

	
	/**
	 * .<br/>
	 * <ul>
	 * <li></li>
	 * </ul>
	 * : void :  .<br/>
	 * 
	 * @throws Exception 
	 */
	@SuppressWarnings(UNUSED)
	@Test
	public void testGetRootFile() throws Exception {
		
		// **********************************
		// AFFICHAGE DANS LE TEST ou NON
		final boolean affichage = true;
		// **********************************
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println("********** CLASSE PreferencesFournisseurArboTraficManagerTest - méthode testGetRootFile() ********** ");
			System.out.println(INITIAL + PreferencesFournisseurArboTraficManager.afficherPreferences());
		}
		
		final File repertoire 
			= PreferencesFournisseurArboTraficManager.getRootFile();
	
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			if (repertoire != null) {
				System.out.println("REPERTOIRE ROOT : " + repertoire.getAbsolutePath());
			} else {
				System.out.println("REPERTOIRE ROOT : null");
			}			
		}
		
		assertNotNull("le répertoire rootFile ne doit pas être null : "
				, repertoire);

	} // Fin de testGetRootFile()._________________________________________

	
	
} // FIN DE LA CLASSE PreferencesFournisseurArboTraficManagerTest.-----------
