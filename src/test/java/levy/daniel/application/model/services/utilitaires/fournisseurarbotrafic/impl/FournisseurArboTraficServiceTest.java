/**
 * 
 */
package levy.daniel.application.model.services.utilitaires.fournisseurarbotrafic.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import levy.daniel.application.model.services.utilitaires.fournisseurarbotrafic.IArboTrafic;

/**
 * CLASSE FournisseurArboTraficServiceTest :<br/>
 * Test JUnit de la classe FournisseurArboTraficService.<br/>
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
public class FournisseurArboTraficServiceTest {

	// ************************ATTRIBUTS************************************/
	
	/**
	 * AFFICHAGE_GENERAL : Boolean :<br/>
	 * Boolean qui commande l'affichage pour tous les tests.<br/>
	 */
	public static final Boolean AFFICHAGE_GENERAL = true;
	
	
	/**
	 * ROOTFILE_STRING_PAR_DEFAUT_EN_DUR : String :<br/>
	 * rootFile (Trafic_Histonat) en dur.<br/>
	 * DOIT VISER UN REPERTOIRE EXISTANT.<br/>
	 * N'est utilisé que si l'application ne peut lire le répertoire 
	 * indiqué dans preferences.properties.<br/>
	 * "D:\Donnees\Travail\Trafic_Histonat".<br/>
	 */
	public static final String ROOTFILE_STRING_PAR_DEFAUT_EN_DUR 
		= "D:\\Donnees\\Travail\\Trafic_Histonat";
	

	
	/**
	 * ROOTFILE_STRING_PROVISOIRE_PAR_DEFAUT_EN_DUR : String :<br/>
	 * rootFile (Trafic_Histonat) provisoire en dur.<br/>
	 * DOIT VISER UN REPERTOIRE EXISTANT.<br/>
	 * N'est utilisé que si l'application ne peut lire le répertoire 
	 * indiqué dans preferences.properties.<br/>
	 * "D:\Donnees\Trafic_Histonat".<br/>
	 */
	public static final String ROOTFILE_STRING_PROVISOIRE_PAR_DEFAUT_EN_DUR 
		= "D:\\Donnees\\Trafic_Histonat";
	

	/**
	 * ANNEE2016 : String :<br/>
	 * "2016".<br/>
	 */
	public static final String ANNEE2016 = "2016";
	
	/**
	 * RESULTAT : String :<br/>
	 * "Résultat : ".<br/>
	 */
	public static final String RESULTAT 
		= "Résultat : ";
	
	/**
	 * OBJET_DOIT_EXISTER : String :<br/>
	 * "l'objet doit exister : ".<br/>
	 */
	public static final String OBJET_DOIT_EXISTER 
		= "l'objet doit exister : ";
	
	/**
	 * OBJET_DOIT_PAS_NULL : String :<br/>
	 * "l'objet ne doit pas être null : ".<br/>
	 */
	public static final String OBJET_DOIT_PAS_NULL 
		= "l'objet ne doit pas être null : ";
	
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
	 * DEUX_POINTS : String :<br/>
	 * " : ".<br/>
	 */
	public static final String DEUX_POINTS = " : ";
	
	/**
	 * TAB : String :<br/>
	 * "\t".<br/>
	 */
	public static final String TAB = "\t";
	
	/**
	 * TAB2 : String :<br/>
	 * TAB + TAB.<br/>
	 */
	public static final String TAB2 = TAB + TAB;
	
	/**
	 * TAB3 : String :<br/>
	 * TAB + TAB + TAB.<br/>
	 */
	public static final String TAB3 = TAB + TAB + TAB;
	
	/**
	 * TAB4 : String :<br/>
	 * TAB + TAB + TAB + TAB.<br/>
	 */
	public static final String TAB4 = TAB + TAB + TAB + TAB;
	
	/**
	 * TAB5 : String :<br/>
	 * TAB + TAB + TAB + TAB + TAB.<br/>
	 */
	public static final String TAB5 = TAB + TAB + TAB + TAB + TAB;
	
	/**
	 * LOG : Log : 
	 * Logger pour Log4j (utilisant commons-logging).
	 */
	private static final Log LOG 
		= LogFactory.getLog(FournisseurArboTraficServiceTest.class);


	// *************************METHODES************************************/
	
	 /**
	 * method CONSTRUCTEUR FournisseurArboTraficServiceTest() :<br/>
	 * CONSTRUCTEUR D'ARITE NULLE.<br/>
	 * <br/>
	 */
	public FournisseurArboTraficServiceTest() {
		super();
	} // Fin de CONSTRUCTEUR D'ARITE NULLE.________________________________
	

	
	/**
	 * .<br/>
	 * <br/>
	 *
	 * @throws Exception
	 */
	@SuppressWarnings(UNUSED)
	@Test
	public void testCreerArbo() throws Exception {
		
		// **********************************
		// AFFICHAGE DANS LE TEST ou NON
		final boolean affichage = true;
		// **********************************
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println("********** CLASSE FournisseurArboTraficServiceTest - méthode testCreerArbo() ********** ");
		}
		
		final File racineTest = new File("D:/Donnees/TestArbo/");
		final String anneeTest = "2018";
		
		FournisseurArboTraficService.creerRepertoireSurDisque(racineTest);
		FournisseurArboTraficService.selectionnerRootFile(racineTest);
		
		FournisseurArboTraficService.selectionnerAnneeExploitation(anneeTest);
		
		final File racine = FournisseurArboTraficService.getRootFile();
		final String annee = FournisseurArboTraficService.getAnneeExploitation();
		
		final File repertoireTraficAnnuel = FournisseurArboTraficService.fournirRepertoireTraficAnnuel();
		final File repertoireFichiersOriginaux = FournisseurArboTraficService.fournirRepertoireFichiersOriginaux();
		final File repertoireFichiersOriginauxANSI = FournisseurArboTraficService.fournirRepertoireFichiersOriginauxANSI();
		final File repertoireFichiersOriginauxANSIcsv = FournisseurArboTraficService.fournirRepertoireFichiersOriginauxANSIcsv();
		final File repertoireFichiersOriginauxUTF8 = FournisseurArboTraficService.fournirRepertoireFichiersOriginauxUTF8();
		final File repertoireFichiersOriginauxUTF8csv = FournisseurArboTraficService.fournirRepertoireFichiersOriginauxUTF8csv();
		final File repertoireFichiersNettoyesUTF8 = FournisseurArboTraficService.fournirRepertoireFichiersNettoyesUTF8();
		final File repertoireFichiersNettoyesUTF8csv = FournisseurArboTraficService.fournirRepertoireFichiersNettoyesUTF8csv();
		final File repertoireFichiersReponseLocalisationUTF8 = FournisseurArboTraficService.fournirRepertoireFichiersReponseLocalisationUTF8();
		final File repertoireAnnee = FournisseurArboTraficService.fournirRepertoireAnnee();
		final File repertoireDARWIN = FournisseurArboTraficService.fournirRepertoireDARWIN();
		final File repertoireDIRA = FournisseurArboTraficService.fournirRepertoireDIRA();
		final File repertoireDIRCE = FournisseurArboTraficService.fournirRepertoireDIRCE();
		final File repertoireDIRCO = FournisseurArboTraficService.fournirRepertoireDIRCO();
		final File repertoireDIRE = FournisseurArboTraficService.fournirRepertoireDIRE();
		final File repertoireDIRIF = FournisseurArboTraficService.fournirRepertoireDIRIF();
		final File repertoireDIRMC = FournisseurArboTraficService.fournirRepertoireDIRMC();
		final File repertoireDIRMED = FournisseurArboTraficService.fournirRepertoireDIRMED();
		final File repertoireDIRN = FournisseurArboTraficService.fournirRepertoireDIRN();
		final File repertoireDIRNO = FournisseurArboTraficService.fournirRepertoireDIRNO();
		final File repertoireDIRO = FournisseurArboTraficService.fournirRepertoireDIRO();
		final File repertoireDIRSO = FournisseurArboTraficService.fournirRepertoireDIRSO();
		final File repertoireEntree = FournisseurArboTraficService.fournirRepertoireEntree();
		final File repertoireEntreeDarwin = FournisseurArboTraficService.fournirRepertoireEntreeDarwin();
		final File repertoireEntreeDarwinANSI = FournisseurArboTraficService.fournirRepertoireEntreeDarwinANSI();
		final File repertoireEntreeDarwinUTF8 = FournisseurArboTraficService.fournirRepertoireEntreeDarwinUTF8();
		final File repertoireEntreeDarwinOriginal = FournisseurArboTraficService.fournirRepertoireEntreeDarwinOriginal();
		final File repertoireEntreeHit = FournisseurArboTraficService.fournirRepertoireEntreeHit();
		final File repertoireEntreeHitANSI = FournisseurArboTraficService.fournirRepertoireEntreeHitANSI();
		final File repertoireEntreeHitANSIAscii = FournisseurArboTraficService.fournirRepertoireEntreeHitANSIAscii();
		final File repertoireEntreeHitANSICsv = FournisseurArboTraficService.fournirRepertoireEntreeHitANSICsv();
		final File repertoireEntreeHitANSIFeorXML = FournisseurArboTraficService.fournirRepertoireEntreeHitANSIFeorXML();
		final File repertoireEntreeHitUTF8 = FournisseurArboTraficService.fournirRepertoireEntreeHitUTF8();
		final File repertoireEntreeHitUTF8Ascii = FournisseurArboTraficService.fournirRepertoireEntreeHitUTF8Ascii();
		final File repertoireEntreeHitUTF8Csv = FournisseurArboTraficService.fournirRepertoireEntreeHitUTF8Csv();
		final File repertoireEntreeHitUTF8FeorXML = FournisseurArboTraficService.fournirRepertoireEntreeHitUTF8FeorXML();
		final File repertoireEntreeHitOriginal = FournisseurArboTraficService.fournirRepertoireEntreeHitOriginal();
		final File repertoireSortie = FournisseurArboTraficService.fournirRepertoireSortie();
		final File repertoireSortieHistonatF07 = FournisseurArboTraficService.fournirRepertoireSortieHistonatF07();
		final File repertoireSortieHistonatF07Ascii = FournisseurArboTraficService.fournirRepertoireSortieHistonatF07Ascii();
		final File repertoireSortieHistonatF07AsciiANSI = FournisseurArboTraficService.fournirRepertoireSortieHistonatF07AsciiANSI();
		final File repertoireSortieHistonatF07AsciiUTF8 = FournisseurArboTraficService.fournirRepertoireSortieHistonatF07AsciiUTF8();
		final File repertoireSortieHistonatF07Csv = FournisseurArboTraficService.fournirRepertoireSortieHistonatF07Csv();
		final File repertoireSortieHistonatF07CsvANSI = FournisseurArboTraficService.fournirRepertoireSortieHistonatF07CsvANSI();
		final File repertoireSortieHistonatF07CsvUTF8 = FournisseurArboTraficService.fournirRepertoireSortieHistonatF07CsvUTF8();
		final File repertoireSortieHistonatF07FeorXML = FournisseurArboTraficService.fournirRepertoireSortieHistonatF07FeorXML();
		final File repertoireSortieHistonatF07FeorXMLANSI = FournisseurArboTraficService.fournirRepertoireSortieHistonatF07FeorXMLANSI();
		final File repertoireSortieHistonatF07FeorXMLUTF8 = FournisseurArboTraficService.fournirRepertoireSortieHistonatF07FeorXMLUTF8();
		final File repertoireSortieHistonatF08 = FournisseurArboTraficService.fournirRepertoireSortieHistonatF08();
		final File repertoireSortieHistonatF08Ascii = FournisseurArboTraficService.fournirRepertoireSortieHistonatF08Ascii();
		final File repertoireSortieHistonatF08AsciiANSI = FournisseurArboTraficService.fournirRepertoireSortieHistonatF08AsciiANSI();
		final File repertoireSortieHistonatF08AsciiUTF8 = FournisseurArboTraficService.fournirRepertoireSortieHistonatF08AsciiUTF8();
		
		
		
		
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println("RACINE : " + racine.getAbsolutePath());
			System.out.println("ANNEE D'EXPLOITATION : " + annee);
			System.out.println("REPERTOIRE DES TRAFICS ANNUELS " + anneeTest + DEUX_POINTS + repertoireTraficAnnuel.getAbsolutePath());
			System.out.println(TAB + "REPERTOIRE DES FICHIERS ORIGINAUX " + anneeTest + DEUX_POINTS + repertoireFichiersOriginaux.getAbsolutePath());
			System.out.println(TAB + "REPERTOIRE DES FICHIERS ORIGINAUX ANSI " + anneeTest + DEUX_POINTS + repertoireFichiersOriginauxANSI.getAbsolutePath());
			System.out.println(TAB2 + "REPERTOIRE DES FICHIERS ORIGINAUX ANSI csv " + anneeTest + DEUX_POINTS + repertoireFichiersOriginauxANSIcsv.getAbsolutePath());
			System.out.println(TAB + "REPERTOIRE DES FICHIERS ORIGINAUX UTF8 " + anneeTest + DEUX_POINTS + repertoireFichiersOriginauxUTF8.getAbsolutePath());
			System.out.println(TAB2 + "REPERTOIRE DES FICHIERS ORIGINAUX UTF8 csv " + anneeTest + DEUX_POINTS + repertoireFichiersOriginauxUTF8csv.getAbsolutePath());
			System.out.println(TAB + "REPERTOIRE DES FICHIERS NETTOYES UTF8 " + anneeTest + DEUX_POINTS + repertoireFichiersNettoyesUTF8.getAbsolutePath());
			System.out.println(TAB2 + "REPERTOIRE DES FICHIERS NETTOYES UTF8 csv " + anneeTest + DEUX_POINTS + repertoireFichiersNettoyesUTF8csv.getAbsolutePath());
			System.out.println(TAB + "REPERTOIRE DES FICHIERS REPONSE LOCALISATION UTF8 " + anneeTest + DEUX_POINTS + repertoireFichiersReponseLocalisationUTF8.getAbsolutePath());
			System.out.println(TAB + "REPERTOIRE DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireAnnee.getAbsolutePath());
			System.out.println(TAB2 + "REPERTOIRE DARWIN DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireDARWIN.getAbsolutePath());
			System.out.println(TAB2 + "REPERTOIRE DARWIN DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireDARWIN.getAbsolutePath());
			System.out.println(TAB2 + "REPERTOIRE DIRA DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireDIRA.getAbsolutePath());
			System.out.println(TAB2 + "REPERTOIRE DIRCE DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireDIRCE.getAbsolutePath());
			System.out.println(TAB2 + "REPERTOIRE DIRCO DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireDIRCO.getAbsolutePath());
			System.out.println(TAB2 + "REPERTOIRE DIRE DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireDIRE.getAbsolutePath());
			System.out.println(TAB2 + "REPERTOIRE DIRIF DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireDIRIF.getAbsolutePath());
			System.out.println(TAB2 + "REPERTOIRE DIRMC DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireDIRMC.getAbsolutePath());
			System.out.println(TAB2 + "REPERTOIRE DIRMED DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireDIRMED.getAbsolutePath());
			System.out.println(TAB2 + "REPERTOIRE DIRN DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireDIRN.getAbsolutePath());
			System.out.println(TAB2 + "REPERTOIRE DIRNO DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireDIRNO.getAbsolutePath());
			System.out.println(TAB2 + "REPERTOIRE DIRO DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireDIRO.getAbsolutePath());
			System.out.println(TAB2 + "REPERTOIRE DIRSO DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireDIRSO.getAbsolutePath());
			System.out.println(TAB2 + "REPERTOIRE Entree DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireEntree.getAbsolutePath());
			System.out.println(TAB3 + "REPERTOIRE Entree/Darwin DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireEntreeDarwin.getAbsolutePath());
			System.out.println(TAB4 + "REPERTOIRE Entree/Darwin/ANSI DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireEntreeDarwinANSI.getAbsolutePath());
			System.out.println(TAB4 + "REPERTOIRE Entree/Darwin/UTF8 DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireEntreeDarwinUTF8.getAbsolutePath());
			System.out.println(TAB4 + "REPERTOIRE Entree/Darwin/Original DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireEntreeDarwinOriginal.getAbsolutePath());
			System.out.println(TAB3 + "REPERTOIRE Entree/Hit DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireEntreeHit.getAbsolutePath());
			System.out.println(TAB4 + "REPERTOIRE Entree/Hit/ANSI DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireEntreeHitANSI.getAbsolutePath());
			System.out.println(TAB5 + "REPERTOIRE Entree/Hit/ANSI/Ascii DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireEntreeHitANSIAscii.getAbsolutePath());
			System.out.println(TAB5 + "REPERTOIRE Entree/Hit/ANSI/Csv DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireEntreeHitANSICsv.getAbsolutePath());
			System.out.println(TAB5 + "REPERTOIRE Entree/Hit/ANSI/FeorXML DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireEntreeHitANSIFeorXML.getAbsolutePath());
			System.out.println(TAB4 + "REPERTOIRE Entree/Hit/UTF8 DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireEntreeHitUTF8.getAbsolutePath());
			System.out.println(TAB5 + "REPERTOIRE Entree/Hit/UTF8/Ascii DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireEntreeHitUTF8Ascii.getAbsolutePath());
			System.out.println(TAB5 + "REPERTOIRE Entree/Hit/UTF8/Csv DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireEntreeHitUTF8Csv.getAbsolutePath());
			System.out.println(TAB5 + "REPERTOIRE Entree/Hit/UTF8/FeorXML DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireEntreeHitUTF8FeorXML.getAbsolutePath());
			System.out.println(TAB4 + "REPERTOIRE Entree/Hit/Originaux DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireEntreeHitOriginal.getAbsolutePath());
			System.out.println(TAB2 + "REPERTOIRE Sortie DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireSortie.getAbsolutePath());
			System.out.println(TAB3 + "REPERTOIRE Sortie/HistonatF07 DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireSortieHistonatF07.getAbsolutePath());
			System.out.println(TAB4 + "REPERTOIRE Sortie/HistonatF07/ASCII DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireSortieHistonatF07Ascii.getAbsolutePath());
			System.out.println(TAB5 + "REPERTOIRE Sortie/HistonatF07/ASCII/ANSI DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireSortieHistonatF07AsciiANSI.getAbsolutePath());
			System.out.println(TAB5 + "REPERTOIRE Sortie/HistonatF07/ASCII/UTF8 DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireSortieHistonatF07AsciiUTF8.getAbsolutePath());
			System.out.println(TAB4 + "REPERTOIRE Sortie/HistonatF07/CSV DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireSortieHistonatF07Csv.getAbsolutePath());
			System.out.println(TAB5 + "REPERTOIRE Sortie/HistonatF07/CSV/ANSI DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireSortieHistonatF07CsvANSI.getAbsolutePath());
			System.out.println(TAB5 + "REPERTOIRE Sortie/HistonatF07/CSV/UTF8 DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireSortieHistonatF07CsvUTF8.getAbsolutePath());
			System.out.println(TAB4 + "REPERTOIRE Sortie/HistonatF07/FeorXML DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireSortieHistonatF07FeorXML.getAbsolutePath());
			System.out.println(TAB5 + "REPERTOIRE Sortie/HistonatF07/FeorXML/ANSI DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireSortieHistonatF07FeorXMLANSI.getAbsolutePath());
			System.out.println(TAB5 + "REPERTOIRE Sortie/HistonatF07/FeorXML/UTF8 DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireSortieHistonatF07FeorXMLUTF8.getAbsolutePath());
			System.out.println(TAB3 + "REPERTOIRE Sortie/HistonatF08 DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireSortieHistonatF08.getAbsolutePath());
			System.out.println(TAB4 + "REPERTOIRE Sortie/HistonatF08/ASCII DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireSortieHistonatF08Ascii.getAbsolutePath());
			System.out.println(TAB5 + "REPERTOIRE Sortie/HistonatF08/ASCII/ANSI DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireSortieHistonatF08AsciiANSI.getAbsolutePath());
			System.out.println(TAB5 + "REPERTOIRE Sortie/HistonatF08/ASCII/UTF8 DE L'ANNEE " + anneeTest + DEUX_POINTS + repertoireSortieHistonatF08AsciiUTF8.getAbsolutePath());
		}
		
		assertNotNull("la racine ne doit pas être null : ", racine);
		assertTrue("la racine doit exister : ", racine.exists());
		assertTrue("la racine doit être un répertoire : ", racine.isDirectory());
		
		assertNotNull("l'année d'exploitation doit exister : ", annee);
		assertEquals("la racine doit valoir racineTest : ", racineTest, racine);
		assertEquals("l'année d'exploitation doit valoir anneeTest : ", anneeTest, annee);
		
		assertNotNull("le répertoire des trafics annuels ne doit pas être null : ", repertoireTraficAnnuel);
		assertTrue("le répertoire des trafics annuels doit exister : ", repertoireTraficAnnuel.exists());
		assertTrue("le répertoire des trafics annuels doit être un répertoire : ", repertoireTraficAnnuel.isDirectory());
		
		assertNotNull("le répertoire des fichiers originaux ne doit pas être null : ", repertoireFichiersOriginaux);
		assertTrue("le répertoire des fichiers originaux doit exister : ", repertoireFichiersOriginaux.exists());
		assertTrue("le répertoire des fichiers originaux doit être un répertoire : ", repertoireFichiersOriginaux.isDirectory());
		
		assertNotNull("le répertoire des fichiers originaux ANSI ne doit pas être null : ", repertoireFichiersOriginauxANSI);
		assertTrue("le répertoire des fichiers originaux ANSI doit exister : ", repertoireFichiersOriginauxANSI.exists());
		assertTrue("le répertoire des fichiers originaux ANSI doit être un répertoire : ", repertoireFichiersOriginauxANSI.isDirectory());
		
		assertNotNull("le répertoire des fichiers originaux ANSI csv ne doit pas être null : ", repertoireFichiersOriginauxANSIcsv);
		assertTrue("le répertoire des fichiers originaux ANSI csv doit exister : ", repertoireFichiersOriginauxANSIcsv.exists());
		assertTrue("le répertoire des fichiers originaux ANSI csv doit être un répertoire : ", repertoireFichiersOriginauxANSIcsv.isDirectory());
		
		assertNotNull("le répertoire des fichiers originaux UTF8 ne doit pas être null : ", repertoireFichiersOriginauxUTF8);
		assertTrue("le répertoire des fichiers originaux UTF8 doit exister : ", repertoireFichiersOriginauxUTF8.exists());
		assertTrue("le répertoire des fichiers originaux UTF8 doit être un répertoire : ", repertoireFichiersOriginauxUTF8.isDirectory());
		
		assertNotNull("le répertoire des fichiers originaux UTF8 csv ne doit pas être null : ", repertoireFichiersOriginauxUTF8csv);
		assertTrue("le répertoire des fichiers originaux UTF8 csv doit exister : ", repertoireFichiersOriginauxUTF8csv.exists());
		assertTrue("le répertoire des fichiers originaux UTF8 csv doit être un répertoire : ", repertoireFichiersOriginauxUTF8csv.isDirectory());
		
		assertNotNull("le répertoire des fichiers nettoyes UTF8 ne doit pas être null : ", repertoireFichiersNettoyesUTF8);
		assertTrue("le répertoire des fichiers nettoyes UTF8 doit exister : ", repertoireFichiersNettoyesUTF8.exists());
		assertTrue("le répertoire des fichiers nettoyes UTF8 doit être un répertoire : ", repertoireFichiersNettoyesUTF8.isDirectory());
		
		assertNotNull("le répertoire des fichiers nettoyes UTF8 csv ne doit pas être null : ", repertoireFichiersNettoyesUTF8csv);
		assertTrue("le répertoire des fichiers nettoyes UTF8 csv doit exister : ", repertoireFichiersNettoyesUTF8csv.exists());
		assertTrue("le répertoire des fichiers nettoyes UTF8 csv doit être un répertoire : ", repertoireFichiersNettoyesUTF8csv.isDirectory());
		
		assertNotNull("le répertoire des fichiers réponse localisation UTF8 ne doit pas être null : ", repertoireFichiersReponseLocalisationUTF8);
		assertTrue("le répertoire des fichiers réponse localisation UTF8 doit exister : ", repertoireFichiersReponseLocalisationUTF8.exists());
		assertTrue("le répertoire des fichiers réponse localisation UTF8 doit être un répertoire : ", repertoireFichiersReponseLocalisationUTF8.isDirectory());
		
		assertNotNull("le répertoire annee ne doit pas être null : ", repertoireAnnee);
		assertTrue("le répertoire annee doit exister : ", repertoireAnnee.exists());
		assertTrue("le répertoire annee doit être un répertoire : ", repertoireAnnee.isDirectory());
		
		assertNotNull("le répertoire DARWIN ne doit pas être null : ", repertoireDARWIN);
		assertTrue("le répertoire DARWIN doit exister : ", repertoireDARWIN.exists());
		assertTrue("le répertoire DARWIN doit être un répertoire : ", repertoireDARWIN.isDirectory());
		
		assertNotNull("le répertoire DARWIN ne doit pas être null : ", repertoireDARWIN);
		assertTrue("le répertoire DARWIN doit exister : ", repertoireDARWIN.exists());
		assertTrue("le répertoire DARWIN doit être un répertoire : ", repertoireDARWIN.isDirectory());
		
		assertNotNull("le répertoire DIRCE ne doit pas être null : ", repertoireDIRCE);
		assertTrue("le répertoire DIRCE doit exister : ", repertoireDIRCE.exists());
		assertTrue("le répertoire DIRCE doit être un répertoire : ", repertoireDIRCE.isDirectory());
		
		assertNotNull("le répertoire DIRCO ne doit pas être null : ", repertoireDIRCO);
		assertTrue("le répertoire DIRCO doit exister : ", repertoireDIRCO.exists());
		assertTrue("le répertoire DIRCO doit être un répertoire : ", repertoireDIRCO.isDirectory());
		
		assertNotNull("le répertoire DIRE ne doit pas être null : ", repertoireDIRE);
		assertTrue("le répertoire DIRE doit exister : ", repertoireDIRE.exists());
		assertTrue("le répertoire DIRE doit être un répertoire : ", repertoireDIRE.isDirectory());
		
		assertNotNull("le répertoire DIRIF ne doit pas être null : ", repertoireDIRIF);
		assertTrue("le répertoire DIRIF doit exister : ", repertoireDIRIF.exists());
		assertTrue("le répertoire DIRIF doit être un répertoire : ", repertoireDIRIF.isDirectory());
		
		assertNotNull("le répertoire DIRMC ne doit pas être null : ", repertoireDIRMC);
		assertTrue("le répertoire DIRMC doit exister : ", repertoireDIRMC.exists());
		assertTrue("le répertoire DIRMC doit être un répertoire : ", repertoireDIRMC.isDirectory());
		
		assertNotNull("le répertoire DIRMED ne doit pas être null : ", repertoireDIRMED);
		assertTrue("le répertoire DIRMED doit exister : ", repertoireDIRMED.exists());
		assertTrue("le répertoire DIRMED doit être un répertoire : ", repertoireDIRMED.isDirectory());
		
		assertNotNull("le répertoire DIRN ne doit pas être null : ", repertoireDIRN);
		assertTrue("le répertoire DIRN doit exister : ", repertoireDIRN.exists());
		assertTrue("le répertoire DIRN doit être un répertoire : ", repertoireDIRN.isDirectory());
		
		assertNotNull("le répertoire DIRNO ne doit pas être null : ", repertoireDIRNO);
		assertTrue("le répertoire DIRNO doit exister : ", repertoireDIRNO.exists());
		assertTrue("le répertoire DIRNO doit être un répertoire : ", repertoireDIRNO.isDirectory());
		
		assertNotNull("le répertoire DIRO ne doit pas être null : ", repertoireDIRO);
		assertTrue("le répertoire DIRO doit exister : ", repertoireDIRO.exists());
		assertTrue("le répertoire DIRO doit être un répertoire : ", repertoireDIRO.isDirectory());
		
		assertNotNull("le répertoire DIRSO ne doit pas être null : ", repertoireDIRSO);
		assertTrue("le répertoire DIRSO doit exister : ", repertoireDIRSO.exists());
		assertTrue("le répertoire DIRSO doit être un répertoire : ", repertoireDIRSO.isDirectory());
		
		assertNotNull("le répertoire Entree ne doit pas être null : ", repertoireEntree);
		assertTrue("le répertoire Entree doit exister : ", repertoireEntree.exists());
		assertTrue("le répertoire Entree doit être un répertoire : ", repertoireEntree.isDirectory());
		
		assertNotNull("le répertoire Entree/Darwin ne doit pas être null : ", repertoireEntreeDarwin);
		assertTrue("le répertoire Entree/Darwin doit exister : ", repertoireEntreeDarwin.exists());
		assertTrue("le répertoire Entree/Darwin doit être un répertoire : ", repertoireEntreeDarwin.isDirectory());
		
		assertNotNull("le répertoire Entree/Darwin/ANSI ne doit pas être null : ", repertoireEntreeDarwinANSI);
		assertTrue("le répertoire Entree/Darwin/ANSI doit exister : ", repertoireEntreeDarwinANSI.exists());
		assertTrue("le répertoire Entree/Darwin/ANSI doit être un répertoire : ", repertoireEntreeDarwinANSI.isDirectory());
		
		assertNotNull("le répertoire Entree/Darwin/UTF8 ne doit pas être null : ", repertoireEntreeDarwinUTF8);
		assertTrue("le répertoire Entree/Darwin/UTF8 doit exister : ", repertoireEntreeDarwinUTF8.exists());
		assertTrue("le répertoire Entree/Darwin/UTF8 doit être un répertoire : ", repertoireEntreeDarwinUTF8.isDirectory());
		
		assertNotNull("le répertoire Entree/Darwin/Original ne doit pas être null : ", repertoireEntreeDarwinOriginal);
		assertTrue("le répertoire Entree/Darwin/Original doit exister : ", repertoireEntreeDarwinOriginal.exists());
		assertTrue("le répertoire Entree/Darwin/Original doit être un répertoire : ", repertoireEntreeDarwinOriginal.isDirectory());
		
		assertNotNull("le répertoire Entree/Hit ne doit pas être null : ", repertoireEntreeHit);
		assertTrue("le répertoire Entree/Hit doit exister : ", repertoireEntreeHit.exists());
		assertTrue("le répertoire Entree/Hit doit être un répertoire : ", repertoireEntreeHit.isDirectory());
		
		assertNotNull("le répertoire Entree/Hit/ANSI ne doit pas être null : ", repertoireEntreeHitANSI);
		assertTrue("le répertoire Entree/Hit/ANSI doit exister : ", repertoireEntreeHitANSI.exists());
		assertTrue("le répertoire Entree/Hit/ANSI doit être un répertoire : ", repertoireEntreeHitANSI.isDirectory());
		
		assertNotNull("le répertoire Entree/Hit/ANSI/Ascii ne doit pas être null : ", repertoireEntreeHitANSIAscii);
		assertTrue("le répertoire Entree/Hit/ANSI/Ascii doit exister : ", repertoireEntreeHitANSIAscii.exists());
		assertTrue("le répertoire Entree/Hit/ANSI/Ascii doit être un répertoire : ", repertoireEntreeHitANSIAscii.isDirectory());
		
		assertNotNull("le répertoire Entree/Hit/ANSI/Csv ne doit pas être null : ", repertoireEntreeHitANSICsv);
		assertTrue("le répertoire Entree/Hit/ANSI/Csv doit exister : ", repertoireEntreeHitANSICsv.exists());
		assertTrue("le répertoire Entree/Hit/ANSI/Csv doit être un répertoire : ", repertoireEntreeHitANSICsv.isDirectory());
		
		assertNotNull("le répertoire Entree/Hit/ANSI/FeorXML ne doit pas être null : ", repertoireEntreeHitANSIFeorXML);
		assertTrue("le répertoire Entree/Hit/ANSI/FeorXML doit exister : ", repertoireEntreeHitANSIFeorXML.exists());
		assertTrue("le répertoire Entree/Hit/ANSI/FeorXML doit être un répertoire : ", repertoireEntreeHitANSIFeorXML.isDirectory());
		
		assertNotNull("le répertoire Entree/Hit/UTF8 ne doit pas être null : ", repertoireEntreeHitUTF8);
		assertTrue("le répertoire Entree/Hit/UTF8 doit exister : ", repertoireEntreeHitUTF8.exists());
		assertTrue("le répertoire Entree/Hit/UTF8 doit être un répertoire : ", repertoireEntreeHitUTF8.isDirectory());
		
		assertNotNull("le répertoire Entree/Hit/UTF8/Ascii ne doit pas être null : ", repertoireEntreeHitUTF8Ascii);
		assertTrue("le répertoire Entree/Hit/UTF8/Ascii doit exister : ", repertoireEntreeHitUTF8Ascii.exists());
		assertTrue("le répertoire Entree/Hit/UTF8/Ascii doit être un répertoire : ", repertoireEntreeHitUTF8Ascii.isDirectory());
		
		assertNotNull("le répertoire Entree/Hit/UTF8/Csv ne doit pas être null : ", repertoireEntreeHitUTF8Csv);
		assertTrue("le répertoire Entree/Hit/UTF8/Csv doit exister : ", repertoireEntreeHitUTF8Csv.exists());
		assertTrue("le répertoire Entree/Hit/UTF8/Csv doit être un répertoire : ", repertoireEntreeHitUTF8Csv.isDirectory());
		
		assertNotNull("le répertoire Entree/Hit/UTF8/FeorXML ne doit pas être null : ", repertoireEntreeHitUTF8FeorXML);
		assertTrue("le répertoire Entree/Hit/UTF8/FeorXML doit exister : ", repertoireEntreeHitUTF8FeorXML.exists());
		assertTrue("le répertoire Entree/Hit/UTF8/FeorXML doit être un répertoire : ", repertoireEntreeHitUTF8FeorXML.isDirectory());
		
		assertNotNull("le répertoire Entree/Hit/Original ne doit pas être null : ", repertoireEntreeHitOriginal);
		assertTrue("le répertoire Entree/Hit/Original doit exister : ", repertoireEntreeHitOriginal.exists());
		assertTrue("le répertoire Entree/Hit/Original doit être un répertoire : ", repertoireEntreeHitOriginal.isDirectory());
		
		assertNotNull("le répertoire Sortie ne doit pas être null : ", repertoireSortie);
		assertTrue("le répertoire Sortie doit exister : ", repertoireSortie.exists());
		assertTrue("le répertoire Sortie doit être un répertoire : ", repertoireSortie.isDirectory());
		
		assertNotNull("le répertoire Sortie/HistonatF07 ne doit pas être null : ", repertoireSortieHistonatF07);
		assertTrue("le répertoire Sortie/HistonatF07 doit exister : ", repertoireSortieHistonatF07.exists());
		assertTrue("le répertoire Sortie/HistonatF07 doit être un répertoire : ", repertoireSortieHistonatF07.isDirectory());
		
		assertNotNull("le répertoire Sortie/HistonatF07/ASCII ne doit pas être null : ", repertoireSortieHistonatF07Ascii);
		assertTrue("le répertoire Sortie/HistonatF07/ASCII doit exister : ", repertoireSortieHistonatF07Ascii.exists());
		assertTrue("le répertoire Sortie/HistonatF07/ASCII doit être un répertoire : ", repertoireSortieHistonatF07Ascii.isDirectory());
		
		assertNotNull("le répertoire Sortie/HistonatF07/ASCII/ANSI ne doit pas être null : ", repertoireSortieHistonatF07AsciiANSI);
		assertTrue("le répertoire Sortie/HistonatF07/ASCII/ANSI doit exister : ", repertoireSortieHistonatF07AsciiANSI.exists());
		assertTrue("le répertoire Sortie/HistonatF07/ASCII/ANSI doit être un répertoire : ", repertoireSortieHistonatF07AsciiANSI.isDirectory());
		
		assertNotNull("le répertoire Sortie/HistonatF07/ASCII/UTF8 ne doit pas être null : ", repertoireSortieHistonatF07AsciiUTF8);
		assertTrue("le répertoire Sortie/HistonatF07/ASCII/UTF8 doit exister : ", repertoireSortieHistonatF07AsciiUTF8.exists());
		assertTrue("le répertoire Sortie/HistonatF07/ASCII/UTF8 doit être un répertoire : ", repertoireSortieHistonatF07AsciiUTF8.isDirectory());
		
		assertNotNull("le répertoire Sortie/HistonatF07/CSV ne doit pas être null : ", repertoireSortieHistonatF07Csv);
		assertTrue("le répertoire Sortie/HistonatF07/CSV doit exister : ", repertoireSortieHistonatF07Csv.exists());
		assertTrue("le répertoire Sortie/HistonatF07/CSV doit être un répertoire : ", repertoireSortieHistonatF07Csv.isDirectory());
		
		assertNotNull("le répertoire Sortie/HistonatF07/CSV/ANSI ne doit pas être null : ", repertoireSortieHistonatF07CsvANSI);
		assertTrue("le répertoire Sortie/HistonatF07/CSV/ANSI doit exister : ", repertoireSortieHistonatF07CsvANSI.exists());
		assertTrue("le répertoire Sortie/HistonatF07/CSV/ANSI doit être un répertoire : ", repertoireSortieHistonatF07CsvANSI.isDirectory());
		
		assertNotNull("le répertoire Sortie/HistonatF07/CSV/UTF8 ne doit pas être null : ", repertoireSortieHistonatF07CsvUTF8);
		assertTrue("le répertoire Sortie/HistonatF07/CSV/UTF8 doit exister : ", repertoireSortieHistonatF07CsvUTF8.exists());
		assertTrue("le répertoire Sortie/HistonatF07/CSV/UTF8 doit être un répertoire : ", repertoireSortieHistonatF07CsvUTF8.isDirectory());

		assertNotNull("le répertoire Sortie/HistonatF07/FeorXML ne doit pas être null : ", repertoireSortieHistonatF07FeorXML);
		assertTrue("le répertoire Sortie/HistonatF07/FeorXML doit exister : ", repertoireSortieHistonatF07FeorXML.exists());
		assertTrue("le répertoire Sortie/HistonatF07/FeorXML doit être un répertoire : ", repertoireSortieHistonatF07FeorXML.isDirectory());
		
		assertNotNull("le répertoire Sortie/HistonatF07/FeorXML/ANSI ne doit pas être null : ", repertoireSortieHistonatF07FeorXMLANSI);
		assertTrue("le répertoire Sortie/HistonatF07/FeorXML/ANSI doit exister : ", repertoireSortieHistonatF07FeorXMLANSI.exists());
		assertTrue("le répertoire Sortie/HistonatF07/FeorXML/ANSI doit être un répertoire : ", repertoireSortieHistonatF07FeorXMLANSI.isDirectory());
		
		assertNotNull("le répertoire Sortie/HistonatF07/FeorXML/UTF8 ne doit pas être null : ", repertoireSortieHistonatF07FeorXMLUTF8);
		assertTrue("le répertoire Sortie/HistonatF07/FeorXML/UTF8 doit exister : ", repertoireSortieHistonatF07FeorXMLUTF8.exists());
		assertTrue("le répertoire Sortie/HistonatF07/FeorXML/UTF8 doit être un répertoire : ", repertoireSortieHistonatF07FeorXMLUTF8.isDirectory());

		assertNotNull("le répertoire Sortie/HistonatF08 ne doit pas être null : ", repertoireSortieHistonatF08);
		assertTrue("le répertoire Sortie/HistonatF08 doit exister : ", repertoireSortieHistonatF08.exists());
		assertTrue("le répertoire Sortie/HistonatF08 doit être un répertoire : ", repertoireSortieHistonatF08.isDirectory());
		
		assertNotNull("le répertoire Sortie/HistonatF08/ASCII ne doit pas être null : ", repertoireSortieHistonatF08Ascii);
		assertTrue("le répertoire Sortie/HistonatF08/ASCII doit exister : ", repertoireSortieHistonatF08Ascii.exists());
		assertTrue("le répertoire Sortie/HistonatF08/ASCII doit être un répertoire : ", repertoireSortieHistonatF08Ascii.isDirectory());
		
		assertNotNull("le répertoire Sortie/HistonatF08/ASCII/ANSI ne doit pas être null : ", repertoireSortieHistonatF08AsciiANSI);
		assertTrue("le répertoire Sortie/HistonatF08/ASCII/ANSI doit exister : ", repertoireSortieHistonatF08AsciiANSI.exists());
		assertTrue("le répertoire Sortie/HistonatF08/ASCII/ANSI doit être un répertoire : ", repertoireSortieHistonatF08AsciiANSI.isDirectory());
		
		assertNotNull("le répertoire Sortie/HistonatF08/ASCII/UTF8 ne doit pas être null : ", repertoireSortieHistonatF08AsciiUTF8);
		assertTrue("le répertoire Sortie/HistonatF08/ASCII/UTF8 doit exister : ", repertoireSortieHistonatF08AsciiUTF8.exists());
		assertTrue("le répertoire Sortie/HistonatF08/ASCII/UTF8 doit être un répertoire : ", repertoireSortieHistonatF08AsciiUTF8.isDirectory());

	} // Fin de testCreerArbo().___________________________________________
	

	
	/**
	 * .<br/>
	 * <ul>
	 * <li>.</li>
	 * </ul>
	 *
	 * @throws Exception :  :  .<br/>
	 */
	@SuppressWarnings(UNUSED)
	@Test
	public void testFournirArboTrafic() throws Exception {
				
		// **********************************
		// AFFICHAGE DANS LE TEST ou NON
		final boolean affichage = true;
		// **********************************
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println("********** CLASSE FournisseurArboTraficServiceTest - méthode testFournirArboTrafic() ********** ");
		}
		
		final File racineTest = new File("D:/Donnees/TestArbo/");
		final String anneeTest = "2020";
		
		FournisseurArboTraficService.creerRepertoireSurDisque(racineTest);
		FournisseurArboTraficService.selectionnerRootFile(racineTest);
		
		FournisseurArboTraficService.selectionnerAnneeExploitation(anneeTest);
		
		final IArboTrafic arbo = FournisseurArboTraficService
			.fournirArboTrafic(racineTest, anneeTest);

		final File racine = arbo.getRootFile();
		final String annee = arbo.getAnneeExploitation();
		final File repertoireTraficAnnuel = arbo.getRepertoireTraficAnnuel();
		final File repertoireFichiersOriginaux = arbo.getRepertoireFichiersOriginaux();
		final File repertoireFichiersOriginauxANSI = arbo.getRepertoireFichiersOriginauxANSI();
		final File repertoireFichiersOriginauxANSIcsv = arbo.getRepertoireFichiersOriginauxANSIcsv();
		final File repertoireFichiersOriginauxUTF8 = arbo.getRepertoireFichiersOriginauxUTF8();
		final File repertoireFichiersOriginauxUTF8csv = arbo.getRepertoireFichiersOriginauxUTF8csv();
		final File repertoireFichiersNettoyesUTF8 = arbo.getRepertoireFichiersNettoyesUTF8();
//		final File repertoireFichiersNettoyesUTF8csv = arbo.getRepertoireFichiersNettoyesUTF8csv();
//		final File repertoireFichiersReponseLocalisationUTF8 = arbo.getRepertoireFichiersReponseLocalisationUTF8();
//		final File repertoireAnnee = arbo.getRepertoireAnnee();
//		final File repertoireDARWIN = arbo.getRepertoireDARWIN();
//		final File repertoireDIRA = arbo.getRepertoireDIRA();
//		final File repertoireDIRCE = arbo.getRepertoireDIRCE();
//		final File repertoireDIRCO = arbo.getRepertoireDIRCO();
//		final File repertoireDIRE = arbo.getRepertoireDIRE();
//		final File repertoireDIRIF = arbo.getRepertoireDIRIF();
//		final File repertoireDIRMC = arbo.getRepertoireDIRMC();
//		final File repertoireDIRMED = arbo.getRepertoireDIRMED();
//		final File repertoireDIRN = arbo.getRepertoireDIRN();
//		final File repertoireDIRNO = arbo.getRepertoireDIRNO();
//		final File repertoireDIRO = arbo.getRepertoireDIRO();
//		final File repertoireDIRSO = arbo.getRepertoireDIRSO();
//		final File repertoireEntree = arbo.getRepertoireEntree();
//		final File repertoireEntreeDarwin = arbo.getRepertoireEntreeDarwin();
//		final File repertoireEntreeDarwinANSI = arbo.getRepertoireEntreeDarwinANSI();
//		final File repertoireEntreeDarwinUTF8 = arbo.getRepertoireEntreeDarwinUTF8();
//		final File repertoireEntreeDarwinOriginal = arbo.getRepertoireEntreeDarwinOriginal();
//		final File repertoireEntreeHit = arbo.getRepertoireEntreeHit();
//		final File repertoireEntreeHitANSI = arbo.getRepertoireEntreeHitANSI();
//		final File repertoireEntreeHitANSIAscii = arbo.getRepertoireEntreeHitANSIAscii();
//		final File repertoireEntreeHitANSICsv = arbo.getRepertoireEntreeHitANSICsv();
//		final File repertoireEntreeHitANSIFeorXML = arbo.getRepertoireEntreeHitANSIFeorXML();
//		final File repertoireEntreeHitUTF8 = arbo.getRepertoireEntreeHitUTF8();
//		final File repertoireEntreeHitUTF8Ascii = arbo.getRepertoireEntreeHitUTF8Ascii();
//		final File repertoireEntreeHitUTF8Csv = arbo.getRepertoireEntreeHitUTF8Csv();
//		final File repertoireEntreeHitUTF8FeorXML = arbo.getRepertoireEntreeHitUTF8FeorXML();
//		final File repertoireEntreeHitOriginal = arbo.getRepertoireEntreeHitOriginal();
//		final File repertoireSortie = arbo.getRepertoireSortie();
//		final File repertoireSortieHistonatF07 = arbo.getRepertoireSortieHistonatF07();
//		final File repertoireSortieHistonatF07Ascii = arbo.getRepertoireSortieHistonatF07Ascii();
//		final File repertoireSortieHistonatF07AsciiANSI = arbo.getRepertoireSortieHistonatF07AsciiANSI();
//		final File repertoireSortieHistonatF07AsciiUTF8 = arbo.getRepertoireSortieHistonatF07AsciiUTF8();
//		final File repertoireSortieHistonatF07Csv = arbo.getRepertoireSortieHistonatF07Csv();
//		final File repertoireSortieHistonatF07CsvANSI = arbo.getRepertoireSortieHistonatF07CsvANSI();
//		final File repertoireSortieHistonatF07CsvUTF8 = arbo.getRepertoireSortieHistonatF07CsvUTF8();
//		final File repertoireSortieHistonatF07FeorXML = arbo.getRepertoireSortieHistonatF07FeorXML();
//		final File repertoireSortieHistonatF07FeorXMLANSI = arbo.getRepertoireSortieHistonatF07FeorXMLANSI();
//		final File repertoireSortieHistonatF07FeorXMLUTF8 = arbo.getRepertoireSortieHistonatF07FeorXMLUTF8();
//		final File repertoireSortieHistonatF08 = arbo.getRepertoireSortieHistonatF08();
//		final File repertoireSortieHistonatF08Ascii = arbo.getRepertoireSortieHistonatF08Ascii();
//		final File repertoireSortieHistonatF08AsciiANSI = arbo.getRepertoireSortieHistonatF08AsciiANSI();
//		final File repertoireSortieHistonatF08AsciiUTF8 = arbo.getRepertoireSortieHistonatF08AsciiUTF8();
		
		
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			
//			System.out.println("RACINE : " + racine.getAbsolutePath());
//			System.out.println("ANNEE D'EXPLOITATION : " + annee);
//			System.out.println("REPERTOIRE TRAFIC ANNUEL : " + repertoireTraficAnnuel.getAbsolutePath());
//			System.out.println(TAB + "REPERTOIRE TRAFIC ANNUEL/FICHIERS ORIGINAUX : " + repertoireFichiersOriginaux.getAbsolutePath());
//			System.out.println(TAB + "REPERTOIRE TRAFIC ANNUEL/FICHIERS ORIGINAUX ANSI : " + repertoireFichiersOriginauxANSI.getAbsolutePath());
//			System.out.println(TAB2 + "REPERTOIRE TRAFIC ANNUEL/FICHIERS ORIGINAUX ANSI en CSV : " + repertoireFichiersOriginauxANSIcsv.getAbsolutePath());
//			System.out.println(TAB + "REPERTOIRE TRAFIC ANNUEL/FICHIERS ORIGINAUX UTF8 : " + repertoireFichiersOriginauxUTF8.getAbsolutePath());
//			System.out.println(TAB2 + "REPERTOIRE TRAFIC ANNUEL/FICHIERS ORIGINAUX UTF8 en CSV : " + repertoireFichiersOriginauxUTF8csv.getAbsolutePath());
//			System.out.println(TAB + "REPERTOIRE TRAFIC ANNUEL/FICHIERS NETTOYES UTF8 : " + repertoireFichiersNettoyesUTF8.getAbsolutePath());
//			System.out.println(TAB2 + "REPERTOIRE TRAFIC ANNUEL/FICHIERS NETTOYES UTF8 en CSV : " + repertoireFichiersNettoyesUTF8csv.getAbsolutePath());
//			System.out.println(TAB + "REPERTOIRE TRAFIC ANNUEL/FICHIERS REPONSE ISIDOR UTF8 : " + repertoireFichiersReponseLocalisationUTF8.getAbsolutePath());
//			System.out.println(TAB + "REPERTOIRE TRAFIC ANNUEL/ANNEE : " + repertoireAnnee.getAbsolutePath());
//			System.out.println(TAB2 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/DARWIN : " + repertoireDARWIN.getAbsolutePath());
//			System.out.println(TAB2 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/DIRA : " + repertoireDIRA.getAbsolutePath());
//			System.out.println(TAB2 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/DIRCE : " + repertoireDIRCE.getAbsolutePath());
//			System.out.println(TAB2 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/DIRCO : " + repertoireDIRCO.getAbsolutePath());
//			System.out.println(TAB2 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/DIRE : " + repertoireDIRE.getAbsolutePath());
//			System.out.println(TAB2 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/DIRIF : " + repertoireDIRIF.getAbsolutePath());
//			System.out.println(TAB2 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/DIRMC : " + repertoireDIRMC.getAbsolutePath());
//			System.out.println(TAB2 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/DIRMED : " + repertoireDIRMED.getAbsolutePath());
//			System.out.println(TAB2 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/DIRN : " + repertoireDIRN.getAbsolutePath());
//			System.out.println(TAB2 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/DIRNO : " + repertoireDIRNO.getAbsolutePath());
//			System.out.println(TAB2 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/DIRO : " + repertoireDIRO.getAbsolutePath());
//			System.out.println(TAB2 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/DIRSO : " + repertoireDIRSO.getAbsolutePath());
//			System.out.println(TAB2 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/Entree : " + repertoireEntree.getAbsolutePath());
//			System.out.println(TAB3 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/Entree/Darwin : " + repertoireEntreeDarwin.getAbsolutePath());
//			System.out.println(TAB4 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/Entree/Darwin/ANSI : " + repertoireEntreeDarwinANSI.getAbsolutePath());
//			System.out.println(TAB4 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/Entree/Darwin/UTF8 : " + repertoireEntreeDarwinUTF8.getAbsolutePath());
//			System.out.println(TAB4 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/Entree/Darwin/Original : " + repertoireEntreeDarwinOriginal.getAbsolutePath());
//			System.out.println(TAB3 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/Entree/Hit : " + repertoireEntreeHit.getAbsolutePath());
//			System.out.println(TAB4 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/Entree/Hit/ANSI : " + repertoireEntreeHitANSI.getAbsolutePath());
//			System.out.println(TAB5 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/Entree/Hit/ANSI/Ascii : " + repertoireEntreeHitANSIAscii.getAbsolutePath());
//			System.out.println(TAB5 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/Entree/Hit/ANSI/Csv : " + repertoireEntreeHitANSICsv.getAbsolutePath());
//			System.out.println(TAB5 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/Entree/Hit/ANSI/FeorXML : " + repertoireEntreeHitANSIFeorXML.getAbsolutePath());
//			System.out.println(TAB4 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/Entree/Hit/UTF8 : " + repertoireEntreeHitUTF8.getAbsolutePath());
//			System.out.println(TAB5 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/Entree/Hit/UTF8/Ascii : " + repertoireEntreeHitUTF8Ascii.getAbsolutePath());
//			System.out.println(TAB5 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/Entree/Hit/UTF8/Csv : " + repertoireEntreeHitUTF8Csv.getAbsolutePath());
//			System.out.println(TAB5 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/Entree/Hit/UTF8/FeorXML : " + repertoireEntreeHitUTF8FeorXML.getAbsolutePath());
//			System.out.println(TAB4 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/Entree/Hit/Originaux : " + repertoireEntreeHitOriginal.getAbsolutePath());
//			System.out.println(TAB2 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/Sortie : " + repertoireSortie.getAbsolutePath());
//			System.out.println(TAB3 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/Sortie/HistonatF07 : " + repertoireSortieHistonatF07.getAbsolutePath());
//			System.out.println(TAB4 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/Sortie/HistonatF07/Ascii : " + repertoireSortieHistonatF07Ascii.getAbsolutePath());
//			System.out.println(TAB5 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/Sortie/HistonatF07/Ascii/ANSI : " + repertoireSortieHistonatF07AsciiANSI.getAbsolutePath());
//			System.out.println(TAB5 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/Sortie/HistonatF07/Ascii/UTF8 : " + repertoireSortieHistonatF07AsciiUTF8.getAbsolutePath());
//			System.out.println(TAB4 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/Sortie/HistonatF07/Csv : " + repertoireSortieHistonatF07Csv.getAbsolutePath());
//			System.out.println(TAB5 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/Sortie/HistonatF07/Csv/ANSI : " + repertoireSortieHistonatF07CsvANSI.getAbsolutePath());
//			System.out.println(TAB5 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/Sortie/HistonatF07/Csv/UTF8 : " + repertoireSortieHistonatF07CsvUTF8.getAbsolutePath());
//			System.out.println(TAB4 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/Sortie/HistonatF07/FeorXML : " + repertoireSortieHistonatF07FeorXML.getAbsolutePath());
//			System.out.println(TAB5 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/Sortie/HistonatF07/FeorXML/ANSI : " + repertoireSortieHistonatF07FeorXMLANSI.getAbsolutePath());
//			System.out.println(TAB5 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/Sortie/HistonatF07/FeorXML/UTF8 : " + repertoireSortieHistonatF07FeorXMLUTF8.getAbsolutePath());
//			System.out.println(TAB3 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/Sortie/HistonatF08 : " + repertoireSortieHistonatF08.getAbsolutePath());
//			System.out.println(TAB4 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/Sortie/HistonatF08/Ascii : " + repertoireSortieHistonatF08Ascii.getAbsolutePath());
//			System.out.println(TAB5 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/Sortie/HistonatF08/Ascii/ANSI : " + repertoireSortieHistonatF08AsciiANSI.getAbsolutePath());
//			System.out.println(TAB5 + "REPERTOIRE TRAFIC ANNUEL/ANNEE/Sortie/HistonatF08/Ascii/UTF8 : " + repertoireSortieHistonatF08AsciiUTF8.getAbsolutePath());
			
			
			System.out.println(arbo.toStringFormate());
		}
		
		assertNotNull("la racine ne doit pas être null : ", racine);
		assertTrue("la racine doit exister : ", racine.exists());
		assertTrue("la racine doit être un répertoire : ", racine.isDirectory());
		
		assertNotNull("l'année d'exploitation doit exister : ", annee);
		assertEquals("la racine doit valoir racineTest : ", racineTest, racine);
		assertEquals("l'année d'exploitation doit valoir anneeTest : ", anneeTest, annee);
		
		assertNotNull("le répertoire des trafics annuels ne doit pas être null : ", repertoireTraficAnnuel);
		assertTrue("le répertoire des trafics annuels doit exister : ", repertoireTraficAnnuel.exists());
		assertTrue("le répertoire des trafics annuels doit être un répertoire : ", repertoireTraficAnnuel.isDirectory());
		
		assertNotNull("le répertoire des fichiers originaux ne doit pas être null : ", repertoireFichiersOriginaux);
		assertTrue("le répertoire des fichiers originaux doit exister : ", repertoireFichiersOriginaux.exists());
		assertTrue("le répertoire des fichiers originaux doit être un répertoire : ", repertoireFichiersOriginaux.isDirectory());
		
		assertNotNull("le répertoire des fichiers originaux ANSI ne doit pas être null : ", repertoireFichiersOriginauxANSI);
		assertTrue("le répertoire des fichiers originaux ANSI doit exister : ", repertoireFichiersOriginauxANSI.exists());
		assertTrue("le répertoire des fichiers originaux ANSI doit être un répertoire : ", repertoireFichiersOriginauxANSI.isDirectory());
		
		assertNotNull("le répertoire des fichiers originaux ANSI csv ne doit pas être null : ", repertoireFichiersOriginauxANSIcsv);
		assertTrue("le répertoire des fichiers originaux ANSI csv doit exister : ", repertoireFichiersOriginauxANSIcsv.exists());
		assertTrue("le répertoire des fichiers originaux ANSI csv doit être un répertoire : ", repertoireFichiersOriginauxANSIcsv.isDirectory());
		
		assertNotNull("le répertoire des fichiers originaux UTF8 ne doit pas être null : ", repertoireFichiersOriginauxUTF8);
		assertTrue("le répertoire des fichiers originaux UTF8 doit exister : ", repertoireFichiersOriginauxUTF8.exists());
		assertTrue("le répertoire des fichiers originaux UTF8 doit être un répertoire : ", repertoireFichiersOriginauxUTF8.isDirectory());
		
		assertNotNull("le répertoire des fichiers originaux UTF8 csv ne doit pas être null : ", repertoireFichiersOriginauxUTF8csv);
		assertTrue("le répertoire des fichiers originaux UTF8 csv doit exister : ", repertoireFichiersOriginauxUTF8csv.exists());
		assertTrue("le répertoire des fichiers originaux UTF8 csv doit être un répertoire : ", repertoireFichiersOriginauxUTF8csv.isDirectory());
		
		assertNotNull("le répertoire des fichiers nettoyes UTF8 ne doit pas être null : ", repertoireFichiersNettoyesUTF8);
		assertTrue("le répertoire des fichiers nettoyes UTF8 doit exister : ", repertoireFichiersNettoyesUTF8.exists());
		assertTrue("le répertoire des fichiers nettoyes UTF8 doit être un répertoire : ", repertoireFichiersNettoyesUTF8.isDirectory());

	} // Fin de testFournirArboTrafic().___________________________________

	
	
	/**
	 * teste la méthode getRootFile().<br/>
	 * <ul>
	 * <li>vérifie que la méthode retourne un SINGLETON.</li>
	 * </ul>
	 * 
	 * @throws Exception 
	 */
	@SuppressWarnings(UNUSED)
	@Test
	public void testGetRootFile() throws Exception {
		
		// **********************************
		// AFFICHAGE DANS LE TEST ou NON
		final boolean affichage = false;
		// **********************************
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println("********** CLASSE FournisseurArboTraficServiceTest - méthode testGetRootFile() ********** ");
		}
		
		/* sélectionne un rootFile. */
		FournisseurArboTraficService
			.selectionnerRootFile(new File(
					ROOTFILE_STRING_PAR_DEFAUT_EN_DUR));

		final File repertoire 
			= FournisseurArboTraficService.getRootFile();
	
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
		
		/* sélectionne un autre rootFile. */
		FournisseurArboTraficService
			.selectionnerRootFile(new File(
					ROOTFILE_STRING_PROVISOIRE_PAR_DEFAUT_EN_DUR));
		
		final File repertoire2 
		= FournisseurArboTraficService.getRootFile();

		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			if (repertoire2 != null) {
				System.out.println("REPERTOIRE ROOT : " + repertoire2.getAbsolutePath());
			} else {
				System.out.println("REPERTOIRE ROOT : null");
			}			
		}
		
		assertNotNull("le répertoire rootFile ne doit pas être null : "
				, repertoire2);
		
		
		final File repertoire3 
		= FournisseurArboTraficService.getRootFile();
		
		/* vérifie que la méthode retourne un SINGLETON. */
		assertSame("SINGLETON : ", repertoire2, repertoire3);
		
		/* sélectionne un autre rootFile. */
		FournisseurArboTraficService
			.selectionnerRootFile(new File(
					ROOTFILE_STRING_PAR_DEFAUT_EN_DUR));

	} // Fin de testGetRootFile()._________________________________________


	
	/**
	 * Teste la méthode getAnneeExploitation().<br/>
	 * <ul>
	 * <li>vérifie que la méthode retourne un SINGLETON.</li>
	 * </ul>
	 *
	 * @throws Exception
	 */
	@SuppressWarnings(UNUSED)
	@Test
	public void testGetAnneeExploitation() throws Exception {
		
		// **********************************
		// AFFICHAGE DANS LE TEST ou NON
		final boolean affichage = false;
		// **********************************
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println("********** CLASSE FournisseurArboTraficServiceTest - méthode testGetAnneeExploitation() ********** ");
		}
		
		final String annee 
		= FournisseurArboTraficService.getAnneeExploitation();

		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			if (annee != null) {
				System.out.println("ANNEE EXPLOITATION : " + annee);
			} else {
				System.out.println("ANNEE EXPLOITATION : null");
			}			
		}
		
		assertNotNull("ne doit pas être null : "
				, annee);
		
		/* sélectionne une autre valeur. */
		FournisseurArboTraficService
			.selectionnerAnneeExploitation("2022");
		
		final String annee2 
			= FournisseurArboTraficService.getAnneeExploitation();

		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			if (annee2 != null) {
				System.out.println("ANNEE EXPLOITATION : " + annee2);
			} else {
				System.out.println("ANNEE EXPLOITATION : null");
			}			
		}
		
		assertNotNull("ne doit pas être null : "
				, annee2);
		
		
		final String annee3 
			= FournisseurArboTraficService.getAnneeExploitation();
		
		/* vérifie que la méthode retourne un SINGLETON. */
		assertSame("SINGLETON : ", annee2, annee3);
		
		/* RAZ. */
		FournisseurArboTraficService
			.selectionnerAnneeExploitation(ANNEE2016);

		
	} // Fin de testGetAnneeExploitation().________________________________


	
	/**
	 * Teste la méthode fournirRepertoireTraficAnnuel().<br/>
	 * <ul>
	 * <li>vérifie que le répertoire retourné n'est pas null.</li>
	 * <li>vérifie que le répertoire retourné existe.</li>
	 * </ul>
	 *
	 * @throws Exception
	 */
	@SuppressWarnings(UNUSED)
	@Test
	public void testFournirRepertoireTraficAnnuel() throws Exception {
		
		// **********************************
		// AFFICHAGE DANS LE TEST ou NON
		final boolean affichage = false;
		// **********************************
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println("********** CLASSE FournisseurArboTraficServiceTest - méthode testFournirRepertoireTraficAnnuel() ********** ");
		}
		
		final File resultat 
			= FournisseurArboTraficService.fournirRepertoireTraficAnnuel(ANNEE2016);
		
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println(RESULTAT + resultat.getAbsolutePath());
		}
		
		/* vérifie que le répertoire retourné n'est pas null. */
		assertNotNull(OBJET_DOIT_PAS_NULL, resultat);
		
		/* vérifie que le répertoire retourné existe. */
		assertTrue(OBJET_DOIT_EXISTER, resultat.exists());
		
	} // Fin de testFournirRepertoireTraficAnnuel()._______________________


		
	/**
	 * Teste la méthode fournirRepertoireFichiersOriginaux().<br/>
	 * <ul>
	 * <li>vérifie que le répertoire retourné n'est pas null.</li>
	 * <li>vérifie que le répertoire retourné existe.</li>
	 * </ul>
	 *
	 * @throws Exception
	 */
	@SuppressWarnings(UNUSED)
	@Test
	public void testFournirRepertoireFichiersOriginaux() throws Exception {
		
		// **********************************
		// AFFICHAGE DANS LE TEST ou NON
		final boolean affichage = false;
		// **********************************
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println("********** CLASSE FournisseurArboTraficServiceTest - méthode testFournirRepertoireFichiersOriginaux() ********** ");
		}
		
		final File resultat 
			= FournisseurArboTraficService.fournirRepertoireFichiersOriginaux(ANNEE2016);
		
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println(RESULTAT + resultat.getAbsolutePath());
		}
		
		/* vérifie que le répertoire retourné n'est pas null. */
		assertNotNull(OBJET_DOIT_PAS_NULL, resultat);
		
		/* vérifie que le répertoire retourné existe. */
		assertTrue(OBJET_DOIT_EXISTER, resultat.exists());
		
	} // Fin de testFournirRepertoireFichiersOriginaux().__________________


	
	/**
	 * Teste la méthode fournirRepertoireFichiersOriginauxANSI().<br/>
	 * <ul>
	 * <li>vérifie que le répertoire retourné n'est pas null.</li>
	 * <li>vérifie que le répertoire retourné existe.</li>
	 * </ul>
	 *
	 * @throws Exception
	 */
	@SuppressWarnings(UNUSED)
	@Test
	public void testFournirRepertoireFichiersOriginauxANSI() throws Exception {
		
		// **********************************
		// AFFICHAGE DANS LE TEST ou NON
		final boolean affichage = false;
		// **********************************
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println("********** CLASSE FournisseurArboTraficServiceTest - méthode testFournirRepertoireFichiersOriginauxANSI() ********** ");
		}
		
		final File resultat 
			= FournisseurArboTraficService.fournirRepertoireFichiersOriginauxANSI(ANNEE2016);
		
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println(RESULTAT + resultat.getAbsolutePath());
		}
		
		/* vérifie que le répertoire retourné n'est pas null. */
		assertNotNull(OBJET_DOIT_PAS_NULL, resultat);
		
		/* vérifie que le répertoire retourné existe. */
		assertTrue(OBJET_DOIT_EXISTER, resultat.exists());
		
	} // Fin de testFournirRepertoireFichiersOriginauxANSI().______________


	
	/**
	 * Teste la méthode fournirRepertoireFichiersOriginauxANSIcsv().<br/>
	 * <ul>
	 * <li>vérifie que le répertoire retourné n'est pas null.</li>
	 * <li>vérifie que le répertoire retourné existe.</li>
	 * </ul>
	 *
	 * @throws Exception
	 */
	@SuppressWarnings(UNUSED)
	@Test
	public void testFournirRepertoireFichiersOriginauxANSIcsv() throws Exception {
		
		// **********************************
		// AFFICHAGE DANS LE TEST ou NON
		final boolean affichage = false;
		// **********************************
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println("********** CLASSE FournisseurArboTraficServiceTest - méthode testFournirRepertoireFichiersOriginauxANSIcsv() ********** ");
		}
		
		final File resultat 
			= FournisseurArboTraficService.fournirRepertoireFichiersOriginauxANSIcsv(ANNEE2016);
		
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println(RESULTAT + resultat.getAbsolutePath());
		}
		
		/* vérifie que le répertoire retourné n'est pas null. */
		assertNotNull(OBJET_DOIT_PAS_NULL, resultat);
		
		/* vérifie que le répertoire retourné existe. */
		assertTrue(OBJET_DOIT_EXISTER, resultat.exists());
		
	} // Fin de testFournirRepertoireFichiersOriginauxANSIcsv().___________

	
	
	/**
	 * Teste la méthode fournirRepertoireFichiersOriginauxUTF8().<br/>
	 * <ul>
	 * <li>vérifie que le répertoire retourné n'est pas null.</li>
	 * <li>vérifie que le répertoire retourné existe.</li>
	 * </ul>
	 *
	 * @throws Exception
	 */
	@SuppressWarnings(UNUSED)
	@Test
	public void testFournirRepertoireFichiersOriginauxUTF8() throws Exception {
		
		// **********************************
		// AFFICHAGE DANS LE TEST ou NON
		final boolean affichage = false;
		// **********************************
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println("********** CLASSE FournisseurArboTraficServiceTest - méthode testFournirRepertoireFichiersOriginauxUTF8() ********** ");
		}
		
		final File resultat 
			= FournisseurArboTraficService.fournirRepertoireFichiersOriginauxUTF8(ANNEE2016);
		
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println(RESULTAT + resultat.getAbsolutePath());
		}
		
		/* vérifie que le répertoire retourné n'est pas null. */
		assertNotNull(OBJET_DOIT_PAS_NULL, resultat);
		
		/* vérifie que le répertoire retourné existe. */
		assertTrue(OBJET_DOIT_EXISTER, resultat.exists());
		
	} // Fin de testFournirRepertoireFichiersOriginauxUTF8().______________


	
	/**
	 * Teste la méthode fournirRepertoireFichiersOriginauxUTF8csv().<br/>
	 * <ul>
	 * <li>vérifie que le répertoire retourné n'est pas null.</li>
	 * <li>vérifie que le répertoire retourné existe.</li>
	 * </ul>
	 *
	 * @throws Exception
	 */
	@SuppressWarnings(UNUSED)
	@Test
	public void testFournirRepertoireFichiersOriginauxUTF8csv() throws Exception {
		
		// **********************************
		// AFFICHAGE DANS LE TEST ou NON
		final boolean affichage = false;
		// **********************************
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println("********** CLASSE FournisseurArboTraficServiceTest - méthode testFournirRepertoireFichiersOriginauxUTF8csv() ********** ");
		}
		
		final File resultat 
			= FournisseurArboTraficService.fournirRepertoireFichiersOriginauxUTF8csv(ANNEE2016);
		
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println(RESULTAT + resultat.getAbsolutePath());
		}
		
		/* vérifie que le répertoire retourné n'est pas null. */
		assertNotNull(OBJET_DOIT_PAS_NULL, resultat);
		
		/* vérifie que le répertoire retourné existe. */
		assertTrue(OBJET_DOIT_EXISTER, resultat.exists());
		
	} // Fin de testFournirRepertoireFichiersOriginauxUTF8csv().___________


	
	/**
	 * Teste la méthode fournirRepertoireFichiersNettoyesUTF8().<br/>
	 * <ul>
	 * <li>vérifie que le répertoire retourné n'est pas null.</li>
	 * <li>vérifie que le répertoire retourné existe.</li>
	 * </ul>
	 *
	 * @throws Exception
	 */
	@SuppressWarnings(UNUSED)
	@Test
	public void testFournirRepertoireFichiersNettoyesUTF8() throws Exception {
		
		// **********************************
		// AFFICHAGE DANS LE TEST ou NON
		final boolean affichage = false;
		// **********************************
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println("********** CLASSE FournisseurArboTraficServiceTest - méthode testFournirRepertoireFichiersNettoyesUTF8() ********** ");
		}
		
		final File resultat 
			= FournisseurArboTraficService.fournirRepertoireFichiersNettoyesUTF8(ANNEE2016);
		
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println(RESULTAT + resultat.getAbsolutePath());
		}
		
		/* vérifie que le répertoire retourné n'est pas null. */
		assertNotNull(OBJET_DOIT_PAS_NULL, resultat);
		
		/* vérifie que le répertoire retourné existe. */
		assertTrue(OBJET_DOIT_EXISTER, resultat.exists());
		
	} // Fin de testFournirRepertoireFichiersNettoyesUTF8()._______________


	
	/**
	 * Teste la méthode fournirRepertoireFichiersNettoyesUTF8csv().<br/>
	 * <ul>
	 * <li>vérifie que le répertoire retourné n'est pas null.</li>
	 * <li>vérifie que le répertoire retourné existe.</li>
	 * </ul>
	 *
	 * @throws Exception
	 */
	@SuppressWarnings(UNUSED)
	@Test
	public void testFournirRepertoireFichiersNettoyesUTF8csv() throws Exception {
		
		// **********************************
		// AFFICHAGE DANS LE TEST ou NON
		final boolean affichage = false;
		// **********************************
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println("********** CLASSE FournisseurArboTraficServiceTest - méthode testFournirRepertoireFichiersNettoyesUTF8csv() ********** ");
		}
		
		final File resultat 
			= FournisseurArboTraficService.fournirRepertoireFichiersNettoyesUTF8csv(ANNEE2016);
		
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println(RESULTAT + resultat.getAbsolutePath());
		}
		
		/* vérifie que le répertoire retourné n'est pas null. */
		assertNotNull(OBJET_DOIT_PAS_NULL, resultat);
		
		/* vérifie que le répertoire retourné existe. */
		assertTrue(OBJET_DOIT_EXISTER, resultat.exists());
		
	} // Fin de testFournirRepertoireFichiersNettoyesUTF8csv()._______________


	
	/**
	 * Teste la méthode fournirRepertoireFichiersReponseLocalisationUTF8().<br/>
	 * <ul>
	 * <li>vérifie que le répertoire retourné n'est pas null.</li>
	 * <li>vérifie que le répertoire retourné existe.</li>
	 * </ul>
	 *
	 * @throws Exception
	 */
	@SuppressWarnings(UNUSED)
	@Test
	public void testFournirRepertoireFichiersReponseLocalisationUTF8() throws Exception {
		
		// **********************************
		// AFFICHAGE DANS LE TEST ou NON
		final boolean affichage = false;
		// **********************************
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println("********** CLASSE FournisseurArboTraficServiceTest - méthode testFournirRepertoireFichiersReponseLocalisationUTF8() ********** ");
		}
		
		final File resultat 
			= FournisseurArboTraficService.fournirRepertoireFichiersReponseLocalisationUTF8(ANNEE2016);
		
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println(RESULTAT + resultat.getAbsolutePath());
		}
		
		/* vérifie que le répertoire retourné n'est pas null. */
		assertNotNull(OBJET_DOIT_PAS_NULL, resultat);
		
		/* vérifie que le répertoire retourné existe. */
		assertTrue(OBJET_DOIT_EXISTER, resultat.exists());
		
	} // Fin de testFournirRepertoireFichiersReponseLocalisationUTF8().____


	
	/**
	 * Teste la méthode fournirRepertoireAnnee().<br/>
	 * <ul>
	 * <li>vérifie que le répertoire retourné n'est pas null.</li>
	 * <li>vérifie que le répertoire retourné existe.</li>
	 * </ul>
	 *
	 * @throws Exception
	 */
	@SuppressWarnings(UNUSED)
	@Test
	public void testFournirRepertoireAnnee() throws Exception {
		
		// **********************************
		// AFFICHAGE DANS LE TEST ou NON
		final boolean affichage = false;
		// **********************************
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println("********** CLASSE FournisseurArboTraficServiceTest - méthode testFournirRepertoireAnnee() ********** ");
		}
		
		final File resultat 
			= FournisseurArboTraficService.fournirRepertoireAnnee(ANNEE2016);
		
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println(RESULTAT + resultat.getAbsolutePath());
		}
		
		/* vérifie que le répertoire retourné n'est pas null. */
		assertNotNull(OBJET_DOIT_PAS_NULL, resultat);
		
		/* vérifie que le répertoire retourné existe. */
		assertTrue(OBJET_DOIT_EXISTER, resultat.exists());
		
	} // Fin de testFournirRepertoireAnnee().______________________________


	
	/**
	 * Teste la méthode fournirRepertoireDARWIN().<br/>
	 * <ul>
	 * <li>vérifie que le répertoire retourné n'est pas null.</li>
	 * <li>vérifie que le répertoire retourné existe.</li>
	 * </ul>
	 *
	 * @throws Exception
	 */
	@SuppressWarnings(UNUSED)
	@Test
	public void testFournirRepertoireDARWIN() throws Exception {
		
		// **********************************
		// AFFICHAGE DANS LE TEST ou NON
		final boolean affichage = false;
		// **********************************
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println("********** CLASSE FournisseurArboTraficServiceTest - méthode testFournirRepertoireDARWIN() ********** ");
		}
		
		final File resultat 
			= FournisseurArboTraficService.fournirRepertoireDARWIN(ANNEE2016);
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println(RESULTAT + resultat.getAbsolutePath());
		}
		
		/* vérifie que le répertoire retourné n'est pas null. */
		assertNotNull(OBJET_DOIT_PAS_NULL, resultat);
		
		/* vérifie que le répertoire retourné existe. */
		assertTrue(OBJET_DOIT_EXISTER, resultat.exists());
		
	} // Fin de testFournirRepertoireDARWIN()._______________________________


	
	/**
	 * Teste la méthode fournirRepertoireDIRA().<br/>
	 * <ul>
	 * <li>vérifie que le répertoire retourné n'est pas null.</li>
	 * <li>vérifie que le répertoire retourné existe.</li>
	 * </ul>
	 *
	 * @throws Exception
	 */
	@SuppressWarnings(UNUSED)
	@Test
	public void testFournirRepertoireDIRA() throws Exception {
		
		// **********************************
		// AFFICHAGE DANS LE TEST ou NON
		final boolean affichage = false;
		// **********************************
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println("********** CLASSE FournisseurArboTraficServiceTest - méthode testFournirRepertoireDIRA() ********** ");
		}
		
		final File resultat 
			= FournisseurArboTraficService.fournirRepertoireDIRA(ANNEE2016);
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println(RESULTAT + resultat.getAbsolutePath());
		}
		
		/* vérifie que le répertoire retourné n'est pas null. */
		assertNotNull(OBJET_DOIT_PAS_NULL, resultat);
		
		/* vérifie que le répertoire retourné existe. */
		assertTrue(OBJET_DOIT_EXISTER, resultat.exists());
		
	} // Fin de testFournirRepertoireDIRA()._______________________________


	
	/**
	 * Teste la méthode fournirRepertoireDIRCE().<br/>
	 * <ul>
	 * <li>vérifie que le répertoire retourné n'est pas null.</li>
	 * <li>vérifie que le répertoire retourné existe.</li>
	 * </ul>
	 *
	 * @throws Exception
	 */
	@SuppressWarnings(UNUSED)
	@Test
	public void testFournirRepertoireDIRCE() throws Exception {
		
		// **********************************
		// AFFICHAGE DANS LE TEST ou NON
		final boolean affichage = false;
		// **********************************
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println("********** CLASSE FournisseurArboTraficServiceTest - méthode testFournirRepertoireDIRCE() ********** ");
		}
		
		final File resultat 
			= FournisseurArboTraficService.fournirRepertoireDIRCE(ANNEE2016);
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println(RESULTAT + resultat.getAbsolutePath());
		}
		
		/* vérifie que le répertoire retourné n'est pas null. */
		assertNotNull(OBJET_DOIT_PAS_NULL, resultat);
		
		/* vérifie que le répertoire retourné existe. */
		assertTrue(OBJET_DOIT_EXISTER, resultat.exists());
		
	} // Fin de testFournirRepertoireDIRCE().______________________________


	
	/**
	 * Teste la méthode fournirRepertoireDIRCO().<br/>
	 * <ul>
	 * <li>vérifie que le répertoire retourné n'est pas null.</li>
	 * <li>vérifie que le répertoire retourné existe.</li>
	 * </ul>
	 *
	 * @throws Exception
	 */
	@SuppressWarnings(UNUSED)
	@Test
	public void testFournirRepertoireDIRCO() throws Exception {
		
		// **********************************
		// AFFICHAGE DANS LE TEST ou NON
		final boolean affichage = false;
		// **********************************
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println("********** CLASSE FournisseurArboTraficServiceTest - méthode testFournirRepertoireDIRCO() ********** ");
		}
		
		final File resultat 
			= FournisseurArboTraficService.fournirRepertoireDIRCO(ANNEE2016);
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println(RESULTAT + resultat.getAbsolutePath());
		}
		
		/* vérifie que le répertoire retourné n'est pas null. */
		assertNotNull(OBJET_DOIT_PAS_NULL, resultat);
		
		/* vérifie que le répertoire retourné existe. */
		assertTrue(OBJET_DOIT_EXISTER, resultat.exists());
		
	} // Fin de testFournirRepertoireDIRCO().______________________________


	
	/**
	 * Teste la méthode fournirRepertoireDIRE().<br/>
	 * <ul>
	 * <li>vérifie que le répertoire retourné n'est pas null.</li>
	 * <li>vérifie que le répertoire retourné existe.</li>
	 * </ul>
	 *
	 * @throws Exception
	 */
	@SuppressWarnings(UNUSED)
	@Test
	public void testFournirRepertoireDIRE() throws Exception {
		
		// **********************************
		// AFFICHAGE DANS LE TEST ou NON
		final boolean affichage = false;
		// **********************************
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println("********** CLASSE FournisseurArboTraficServiceTest - méthode testFournirRepertoireDIRE() ********** ");
		}
		
		final File resultat 
			= FournisseurArboTraficService.fournirRepertoireDIRE(ANNEE2016);
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println(RESULTAT + resultat.getAbsolutePath());
		}
		
		/* vérifie que le répertoire retourné n'est pas null. */
		assertNotNull(OBJET_DOIT_PAS_NULL, resultat);
		
		/* vérifie que le répertoire retourné existe. */
		assertTrue(OBJET_DOIT_EXISTER, resultat.exists());
		
	} // Fin de testFournirRepertoireDIRE().______________________________


	
	/**
	 * Teste la méthode fournirRepertoireDIRIF().<br/>
	 * <ul>
	 * <li>vérifie que le répertoire retourné n'est pas null.</li>
	 * <li>vérifie que le répertoire retourné existe.</li>
	 * </ul>
	 *
	 * @throws Exception
	 */
	@SuppressWarnings(UNUSED)
	@Test
	public void testFournirRepertoireDIRIF() throws Exception {
		
		// **********************************
		// AFFICHAGE DANS LE TEST ou NON
		final boolean affichage = false;
		// **********************************
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println("********** CLASSE FournisseurArboTraficServiceTest - méthode testFournirRepertoireDIRIF() ********** ");
		}
		
		final File resultat 
			= FournisseurArboTraficService.fournirRepertoireDIRIF(ANNEE2016);
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println(RESULTAT + resultat.getAbsolutePath());
		}
		
		/* vérifie que le répertoire retourné n'est pas null. */
		assertNotNull(OBJET_DOIT_PAS_NULL, resultat);
		
		/* vérifie que le répertoire retourné existe. */
		assertTrue(OBJET_DOIT_EXISTER, resultat.exists());
		
	} // Fin de testFournirRepertoireDIRIF().______________________________
	
	
	
	/**
	 * Teste la méthode fournirRepertoireDIRMC().<br/>
	 * <ul>
	 * <li>vérifie que le répertoire retourné n'est pas null.</li>
	 * <li>vérifie que le répertoire retourné existe.</li>
	 * </ul>
	 *
	 * @throws Exception
	 */
	@SuppressWarnings(UNUSED)
	@Test
	public void testFournirRepertoireDIRMC() throws Exception {
		
		// **********************************
		// AFFICHAGE DANS LE TEST ou NON
		final boolean affichage = false;
		// **********************************
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println("********** CLASSE FournisseurArboTraficServiceTest - méthode testFournirRepertoireDIRMC() ********** ");
		}
		
		final File resultat 
			= FournisseurArboTraficService.fournirRepertoireDIRMC(ANNEE2016);
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println(RESULTAT + resultat.getAbsolutePath());
		}
		
		/* vérifie que le répertoire retourné n'est pas null. */
		assertNotNull(OBJET_DOIT_PAS_NULL, resultat);
		
		/* vérifie que le répertoire retourné existe. */
		assertTrue(OBJET_DOIT_EXISTER, resultat.exists());
		
	} // Fin de testFournirRepertoireDIRMC().______________________________


	
	/**
	 * Teste la méthode fournirRepertoireDIRMED().<br/>
	 * <ul>
	 * <li>vérifie que le répertoire retourné n'est pas null.</li>
	 * <li>vérifie que le répertoire retourné existe.</li>
	 * </ul>
	 *
	 * @throws Exception
	 */
	@SuppressWarnings(UNUSED)
	@Test
	public void testFournirRepertoireDIRMED() throws Exception {
		
		// **********************************
		// AFFICHAGE DANS LE TEST ou NON
		final boolean affichage = false;
		// **********************************
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println("********** CLASSE FournisseurArboTraficServiceTest - méthode testFournirRepertoireDIRMED() ********** ");
		}
		
		final File resultat 
			= FournisseurArboTraficService.fournirRepertoireDIRMED(ANNEE2016);
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println(RESULTAT + resultat.getAbsolutePath());
		}
		
		/* vérifie que le répertoire retourné n'est pas null. */
		assertNotNull(OBJET_DOIT_PAS_NULL, resultat);
		
		/* vérifie que le répertoire retourné existe. */
		assertTrue(OBJET_DOIT_EXISTER, resultat.exists());
		
	} // Fin de testFournirRepertoireDIRMED()._____________________________


	
	/**
	 * Teste la méthode fournirRepertoireDIRN().<br/>
	 * <ul>
	 * <li>vérifie que le répertoire retourné n'est pas null.</li>
	 * <li>vérifie que le répertoire retourné existe.</li>
	 * </ul>
	 *
	 * @throws Exception
	 */
	@SuppressWarnings(UNUSED)
	@Test
	public void testFournirRepertoireDIRN() throws Exception {
		
		// **********************************
		// AFFICHAGE DANS LE TEST ou NON
		final boolean affichage = false;
		// **********************************
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println("********** CLASSE FournisseurArboTraficServiceTest - méthode testFournirRepertoireDIRN() ********** ");
		}
		
		final File resultat 
			= FournisseurArboTraficService.fournirRepertoireDIRN(ANNEE2016);
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println(RESULTAT + resultat.getAbsolutePath());
		}
		
		/* vérifie que le répertoire retourné n'est pas null. */
		assertNotNull(OBJET_DOIT_PAS_NULL, resultat);
		
		/* vérifie que le répertoire retourné existe. */
		assertTrue(OBJET_DOIT_EXISTER, resultat.exists());
		
	} // Fin de testFournirRepertoireDIRN()._______________________________


	
	/**
	 * Teste la méthode fournirRepertoireDIRNO().<br/>
	 * <ul>
	 * <li>vérifie que le répertoire retourné n'est pas null.</li>
	 * <li>vérifie que le répertoire retourné existe.</li>
	 * </ul>
	 *
	 * @throws Exception
	 */
	@SuppressWarnings(UNUSED)
	@Test
	public void testFournirRepertoireDIRNO() throws Exception {
		
		// **********************************
		// AFFICHAGE DANS LE TEST ou NON
		final boolean affichage = false;
		// **********************************
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println("********** CLASSE FournisseurArboTraficServiceTest - méthode testFournirRepertoireDIRNO() ********** ");
		}
		
		final File resultat 
			= FournisseurArboTraficService.fournirRepertoireDIRNO(ANNEE2016);
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println(RESULTAT + resultat.getAbsolutePath());
		}
		
		/* vérifie que le répertoire retourné n'est pas null. */
		assertNotNull(OBJET_DOIT_PAS_NULL, resultat);
		
		/* vérifie que le répertoire retourné existe. */
		assertTrue(OBJET_DOIT_EXISTER, resultat.exists());
		
	} // Fin de testFournirRepertoireDIRNO().______________________________


	
	/**
	 * Teste la méthode fournirRepertoireDIRO().<br/>
	 * <ul>
	 * <li>vérifie que le répertoire retourné n'est pas null.</li>
	 * <li>vérifie que le répertoire retourné existe.</li>
	 * </ul>
	 *
	 * @throws Exception
	 */
	@SuppressWarnings(UNUSED)
	@Test
	public void testFournirRepertoireDIRO() throws Exception {
		
		// **********************************
		// AFFICHAGE DANS LE TEST ou NON
		final boolean affichage = false;
		// **********************************
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println("********** CLASSE FournisseurArboTraficServiceTest - méthode testFournirRepertoireDIRO() ********** ");
		}
		
		final File resultat 
			= FournisseurArboTraficService.fournirRepertoireDIRO(ANNEE2016);
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println(RESULTAT + resultat.getAbsolutePath());
		}
		
		/* vérifie que le répertoire retourné n'est pas null. */
		assertNotNull(OBJET_DOIT_PAS_NULL, resultat);
		
		/* vérifie que le répertoire retourné existe. */
		assertTrue(OBJET_DOIT_EXISTER, resultat.exists());
		
	} // Fin de testFournirRepertoireDIRO()._______________________________

	
	
	/**
	 * Teste la méthode fournirRepertoireDIRSO().<br/>
	 * <ul>
	 * <li>vérifie que le répertoire retourné n'est pas null.</li>
	 * <li>vérifie que le répertoire retourné existe.</li>
	 * </ul>
	 *
	 * @throws Exception
	 */
	@SuppressWarnings(UNUSED)
	@Test
	public void testFournirRepertoireDIRSO() throws Exception {
		
		// **********************************
		// AFFICHAGE DANS LE TEST ou NON
		final boolean affichage = false;
		// **********************************
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println("********** CLASSE FournisseurArboTraficServiceTest - méthode testFournirRepertoireDIRSO() ********** ");
		}
		
		final File resultat 
			= FournisseurArboTraficService.fournirRepertoireDIRSO(ANNEE2016);
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println(RESULTAT + resultat.getAbsolutePath());
		}
		
		/* vérifie que le répertoire retourné n'est pas null. */
		assertNotNull(OBJET_DOIT_PAS_NULL, resultat);
		
		/* vérifie que le répertoire retourné existe. */
		assertTrue(OBJET_DOIT_EXISTER, resultat.exists());
		
	} // Fin de testFournirRepertoireDIRSO().______________________________

	
	
	/**
	 * Teste la méthode fournirRepertoireEntree().<br/>
	 * <ul>
	 * <li>vérifie que le répertoire retourné n'est pas null.</li>
	 * <li>vérifie que le répertoire retourné existe.</li>
	 * </ul>
	 *
	 * @throws Exception
	 */
	@SuppressWarnings(UNUSED)
	@Test
	public void testFournirRepertoireEntree() throws Exception {
		
		// **********************************
		// AFFICHAGE DANS LE TEST ou NON
		final boolean affichage = false;
		// **********************************
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println("********** CLASSE FournisseurArboTraficServiceTest - méthode testFournirRepertoireEntree() ********** ");
		}
		
		final File resultat 
			= FournisseurArboTraficService.fournirRepertoireEntree(ANNEE2016);
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println(RESULTAT + resultat.getAbsolutePath());
		}
		
		/* vérifie que le répertoire retourné n'est pas null. */
		assertNotNull(OBJET_DOIT_PAS_NULL, resultat);
		
		/* vérifie que le répertoire retourné existe. */
		assertTrue(OBJET_DOIT_EXISTER, resultat.exists());
		
	} // Fin de testFournirRepertoireEntree()._____________________________


	
	/**
	 * Teste la méthode fournirRepertoireEntreeDarwin().<br/>
	 * <ul>
	 * <li>vérifie que le répertoire retourné n'est pas null.</li>
	 * <li>vérifie que le répertoire retourné existe.</li>
	 * </ul>
	 *
	 * @throws Exception
	 */
	@SuppressWarnings(UNUSED)
	@Test
	public void testFournirRepertoireEntreeDarwin() throws Exception {
		
		// **********************************
		// AFFICHAGE DANS LE TEST ou NON
		final boolean affichage = true;
		// **********************************
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println("********** CLASSE FournisseurArboTraficServiceTest - méthode testFournirRepertoireEntreeDarwin() ********** ");
		}
		
		final File resultat 
			= FournisseurArboTraficService.fournirRepertoireEntreeDarwin(ANNEE2016);
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println(RESULTAT + resultat.getAbsolutePath());
		}
		
		/* vérifie que le répertoire retourné n'est pas null. */
		assertNotNull(OBJET_DOIT_PAS_NULL, resultat);
		
		/* vérifie que le répertoire retourné existe. */
		assertTrue(OBJET_DOIT_EXISTER, resultat.exists());
		
	} // Fin de testFournirRepertoireEntreeDarwin()._______________________

	
	
	/**
	 * Teste la méthode fournirRepertoireSortie().<br/>
	 * <ul>
	 * <li>vérifie que le répertoire retourné n'est pas null.</li>
	 * <li>vérifie que le répertoire retourné existe.</li>
	 * </ul>
	 *
	 * @throws Exception
	 */
	@SuppressWarnings(UNUSED)
	@Test
	public void testFournirRepertoireSortie() throws Exception {
		
		// **********************************
		// AFFICHAGE DANS LE TEST ou NON
		final boolean affichage = false;
		// **********************************
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println("********** CLASSE FournisseurArboTraficServiceTest - méthode testFournirRepertoireSortie() ********** ");
		}
		
		final File resultat 
			= FournisseurArboTraficService.fournirRepertoireSortie(ANNEE2016);
				
		/* AFFICHAGE A LA CONSOLE. */
		if (AFFICHAGE_GENERAL && affichage) {
			System.out.println(RESULTAT + resultat.getAbsolutePath());
		}
		
		/* vérifie que le répertoire retourné n'est pas null. */
		assertNotNull(OBJET_DOIT_PAS_NULL, resultat);
		
		/* vérifie que le répertoire retourné existe. */
		assertTrue(OBJET_DOIT_EXISTER, resultat.exists());
		
	} // Fin de testFournirRepertoireSortie()._____________________________

	

} // FIN DE LA CLASSE FournisseurArboTraficServiceTest.----------------------
