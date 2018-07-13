/**
 * 
 */
package levy.daniel.application.apptechnic.configurationmanagers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import levy.daniel.application.ConfigurationApplicationManager;

/**
 * CLASSE PreferencesFournisseurArboTraficManager :<br/>
 * Classe Utilitaire chargée de gérer les préférences 
 * du FournisseurArboTraficService.<br/>
 * <br/>
 * <br/>
 *
 * - Exemple d'utilisation :<br/>
 *<br/>
 * 
 * - Mots-clé :<br/>
 * Preferences, preferences, properties, Properties,<br/>
 * sauver Properties, sauver Properties en XML, <br/>
 * fichier properties, fichier Properties, <br/>
 * créer fichier sur disque dur, HDD, créer arborescence sur disque dur,<br/>
 * enregistrer Properties dans fichier .properties,<br/>
 * enregistrer Properties dans fichier .xml,<br/>
 * lire fichier .properties,
 * lire fichier .xml,<br/>
 * Expression régulière, Regex, 4 chiffres exactement,<br/>
 * MOTIF REGEX 4 chiffres "\\d{4}", expression régulière, RegEx, <br/>
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
public final class PreferencesFournisseurArboTraficManager {

	// ************************ATTRIBUTS************************************/

	/**
	 * VOUS_DEVEZ_METTRE_UN_REPERTOIRE : String :<br/>
	 * "Vous devez mettre un répertoire ".<br/>
	 */
	public static final String VOUS_DEVEZ_METTRE_UN_REPERTOIRE 
		= "Vous devez mettre un répertoire ";
	
	/**
	 * EXISTANT_DANS_LE : String :<br/>
	 * "existant dans le ".<br/>
	 */
	public static final String EXISTANT_DANS_LE 
		= "existant dans le ";
	
	/**
	 * ROOTFILE_STRING_PAR_DEFAUT_EN_DUR : String :<br/>
	 * rootFile (Trafic_Histonat) en dur.<br/>
	 * DOIT VISER UN REPERTOIRE EXISTANT.<br/>
	 * N'est utilisé que si l'application ne peut lire le répertoire 
	 * indiqué dans <b>ressources_externes/preferences/
	 * preferencesFournisseurArboTrafic.properties</b>.<br/>
	 * "D:\Donnees\Travail\Trafic_Histonat".<br/>
	 */
	public static final String ROOTFILE_STRING_PAR_DEFAUT_EN_DUR 
		= "D:\\Donnees\\Travail\\Trafic_Histonat";
	
	/**
	 * ANNEE_EXPOITATION_EN_DUR : String :<br/>
	 * année d'exploitation en dur.<br/>
	 * N'est utilisé que si l'application ne peut lire la Property 
	 * indiquée dans <b>ressources_externes/preferences/
	 * preferencesFournisseurArboTrafic.properties</b>.<br/>
	 * "2016".<br/>
	 */
	public static final String ANNEE_EXPOITATION_EN_DUR = "2016";
	
	/**
	 * EGAL : char :<br/>
	 * '='.<br/>
	 */
	public static final char EGAL = '=';
	
	/**
	 * SAUT_LIGNE_JAVA : char :<br/>
	 * '\n'.<br/>
	 */
	public static final char SAUT_LIGNE_JAVA = '\n';
	
	/**
	 * SEP_REP : String :<br/>
	 * Séparateur Java universel pour les répertoires.<br/>
	 * "/"<br/>
	 */
	public static final String SEP_REP = "/";
	
	/**
	 * CHARSET_UTF8 : Charset :<br/>
	 * Charset.forName("UTF-8").<br/>
	 * Eight-bit Unicode (or UCS) Transformation Format.<br/> 
	 */
	public static final Charset CHARSET_UTF8 
		= Charset.forName("UTF-8");

	
	/**
	 * KEY_ROOTFILE : String :<br/>
	 * "rootfile".<br/>
	 */
	public static final String KEY_ROOTFILE 
		= "rootfile";
	
	/**
	 * KEY_ANNEE : String :<br/>
	 * "annee.exploitation".<br/>
	 */
	public static final String KEY_ANNEE 
		= "annee.exploitation";
	
	/**
	 * rootFile : File :<br/>
	 * RACINE (Trafic_Histonat) des fichiers annuels de Trafic.<br/>
	 */
	private static File rootFile;
	
	/**
	 * anneeExploitation : String :<br/>
	 * année d'exploitation des fichiers annuels de trafic 
	 * (2013, 2017, ...).<br/>
	 */
	private static String anneeExploitation;
	
	/**
	 * preferences : Properties :<br/>
	 * Properties encapsulant les préférences.<br/>
	 */
	private static transient Properties preferences;
	
	/**
	 * pathAbsoluPreferencesProperties : Path :<br/>
	 * Path absolu vers 
	 * preferencesFournisseurArboTrafic.properties.<br/>
	 */
	private static Path pathAbsoluPreferencesProperties;
	
	/**
	 * pathAbsoluPreferencesXml : Path :<br/>
	 * Path absolu vers preferencesFournisseurArboTrafic.xml.<br/>
	 */
	private static Path pathAbsoluPreferencesXml;
	
	/**
	 * filePreferencesProperties : File :<br/>
	 * fichier preferencesFournisseurArboTrafic.properties.<br/>
	 */
	private static File filePreferencesProperties;
	
	/**
	 * filePreferencesXml : File :<br/>
	 * fichier preferencesFournisseurArboTrafic.xml.<br/>
	 */
	private static File filePreferencesXml;

	/**
	 * LOG : Log : 
	 * Logger pour Log4j (utilisant commons-logging).
	 */
	private static final Log LOG 
		= LogFactory.getLog(PreferencesFournisseurArboTraficManager.class);

	// *************************METHODES************************************/
	

	 /**
	 * method CONSTRUCTEUR PreferencesFournisseurArboTraficManager() :<br/>
	 * CONSTRUCTEUR D'ARITE NULLE.<br/>
	 * - private pour bloquer l'instanciation.<br/>
	 */
	private PreferencesFournisseurArboTraficManager() {		
		super();
	} // Fin de CONSTRUCTEUR D'ARITE NULLE.________________________________
	

	
	/**
	 * initialise le <i>Properties</i> <b>preferences</b> 
	 * en lisant le <i>fichier</i> .properties (ou .xml) correspondant 
	 * <b>ressources_externes/preferences/
	 * preferencesFournisseurArboTrafic.properties</b>.<br/>
	 * <ul>
	 * <li>instancie le <i>Properties</i> <b>preferences</b> et l'alimente 
	 * avec le <i>fichier</i> <b>ressources_externes/preferences/
	 * preferencesFournisseurArboTrafic.properties</b> 
	 * (ou ressources_externes/preferences/preferencesFournisseurArboTrafic.xml).</li>
	 * <li>(Optionnel) instancie le Properties preferences 
	 * et l'alimente avec le fichier preferencesFournisseurArboTrafic.xml.</li>
	 * </ul>
	 * 
	 * @throws Exception 
	 */
	private static void initialiserPreferences() throws Exception {
		
		synchronized (PreferencesFournisseurArboTraficManager.class) {
			
			/* instancie le Properties preferences et l'alimente 
			 * avec le fichier preferencesFournisseurArboTrafic.properties. */
			lireFichierPreferencesProperties();
			
			/* instancie le Properties preferences et l'alimente 
			 * avec le fichier preferencesFournisseurArboTrafic.xml. */
//			lireFichierPreferencesXml();
			
		} // Fin du bloc synchronized.__________________
		
	} // Fin de initialiserPreferences().__________________________________
	

	
	/**
	 * <b>sauvegarde sur disque dur un fichier 
	 * "ressources_externes/preferences/
	 * preferencesFournisseurArboTrafic.properties"</b> 
	 * (et preferencesFournisseurArboTrafic.xml) <b>initial alimenté 
	 * avec des valeurs en dur de la classe</b>.<br/>
	 * <ul>
	 * <li>instancie un <i>Properties</i> <b<preferences</b> si nécessaire.</li>
	 * <li>Ajoute les properties initiales au <i>Properties</i> 
	 * <b>preferences</b> en utilisant des valeurs en dur de la classe.</li>
	 * <li>remplit le fichier preferencesFournisseurArboTrafic.properties 
	 * avec des Properties.</li>
	 * </ul>
	 * 
	 * @throws Exception 
	 */
	private static void initialiserFichierPropertiesInitial() 
											throws Exception {
		
		synchronized (PreferencesFournisseurArboTraficManager.class) {
			
			/* instancie un Properties si nécessaire. */
			if (preferences == null) {
				preferences = new Properties();
			}
			
			/* Ajoute les properties initiales au Properties 
			 * preferences en utilisant des valeurs 
			 * en dur de la classe. */
			ajouterProperties();
			
			/* initialise les attributs relatifs aux fichiers 
			 * preferences (.properties et .xml) 
			 * et les crée sur disque si nécessaire. */
			instancierAttributsFichierProperties();
			
			/* ECRITURE SUR DISQUE. */
			enregistrerFichierPreferencesProperties();
			enregistrerFichierPreferencesXml();
			
		} // Fin du bloc synchronized.__________________
		
	} // Fin de creerFichierPropertiesInitial().___________________________
	

	
	/**
	 * <ul>
	 * <li>Instancie le <i>Properties</i> <b>preferences</b> 
	 * si il est null.</li>
	 * <li>alimente le <i>fichier</i> .properties correspondant
	 * avec des valeurs en dur de la classe si nécessaire.</li>
	 *</ul>
	 *
	 * @throws Exception
	 */
	private static void initialiserPreferencesEtProperties() 
			throws Exception {
		
		/* instancie le Properties preferences 
		 * si preferences == null. */
		if (preferences == null) {
			initialiserPreferences();
		}
		
		/* remplit le fichier .properties avec des valeurs en dur 
		 * de la classe si il est vide. */
		if (preferences.isEmpty()) {
			initialiserFichierPropertiesInitial();
		}

	} // Fin de initialiserPreferencesEtProperties().______________________
	

	
	/**
	 * Ajoute des propriétés initiales 
	 * au Properties <b>preferences</b>.<br/>
	 */
	private static void ajouterProperties() {
		
		synchronized (PreferencesFournisseurArboTraficManager.class) {
			
			preferences.setProperty(
					KEY_ROOTFILE, lireCheminRacineDansRessourcesExternesProperties());
			
			preferences.setProperty(
					KEY_ANNEE, ANNEE_EXPOITATION_EN_DUR);
			
		} // Fin du bloc synchronized.__________________
		
	} // Fin de ajouterProperties()._______________________________________
	

	
	/**
	 * <ul>
	 * <li><b>RETOURNE LE CHEMIN VERS LA RACINE DES TRAFICS</b> 
	 * (lu dans configuration_ressources_externes.properties 
	 * si possible, ROOTFILE_STRING_PAR_DEFAUT_EN_DUR sinon).</li>
	 * <li>lit si possible le pathData des 
	 * <b>données hors classpath de l'application</b> 
	 * dans <b>configuration_ressources_externes.properties</b>.<br/>
	 * Par exemple : <br/>
	 * <code>D:/Donnees/eclipse/eclipseworkspace_oxygen/
	 * traficweb_javafx/data/</code></li>
	 * <li>Tente de créer le path vers la RACINE des trafics 
	 * en concaténant pathData et "Trafic_Histonat".<br/>
	 * Par exemple :<br/>
	 * <code>D:/Donnees/eclipse/eclipseworkspace_oxygen/
	 * traficweb_javafx/data/Trafic_Histonat/</code></li>
	 * <li>crée cette RACINE data/Trafic_Histonat/ sur le disque 
	 * si elle n'existe pas.</li>
	 * <li>retourne ROOTFILE_STRING_PAR_DEFAUT_EN_DUR sinon.</li>
	 * </ul>
	 *
	 * @return : String : Chemin vers la racine des trafics.<br/>
	 */
	private static String lireCheminRacineDansRessourcesExternesProperties() {
		
		synchronized (PreferencesFournisseurArboTraficManager.class) {
			
			String pathData = null;
			String rootFileString = null;
						
			try {
				
				/* lit le pathData dans 
				 * configuration_ressources_externes.properties */
				pathData = ConfigurationBundlesManager.getPathData();
				
				/* concatène le pathData avec "Trafic_Histonat". */
				if (termineAvecSeparateurUniversel(pathData)) {
					rootFileString = pathData 
							+ "Trafic_Histonat" + SEP_REP;
				} else {
					rootFileString = pathData 
							+ SEP_REP + "Trafic_Histonat" + SEP_REP;
				}
								
				final File racineFile = new File(rootFileString);
				
				/* crée cette RACINE sur le disque si elle n'existe pas. */
				if (!racineFile.exists()) {
					Files.createDirectories(racineFile.toPath());
				}
			}
			catch (Exception e) {
				
				/* retourne ROOTFILE_STRING_PAR_DEFAUT_EN_DUR sinon. */
				rootFileString = ROOTFILE_STRING_PAR_DEFAUT_EN_DUR;
			}
			
			return rootFileString;
			
		} // Fin du bloc synchronized.__________________
		
	} // Fin de lireCheminRacineDansRessourcesExternesProperties().________
	

	
	/**
	 * détermine si pString se termine 
	 * par un séparateur universel "/".
	 * <ul>
	 * <li>retourne true si pString se termine par "/"</li>
	 * </ul>
	 * - retourne false si pString == null.<br/>
	 * <br/>
	 *
	 * @param pString : String.<br/>
	 * 
	 * @return : boolean : true si pString se termine par "/".<br/>
	 */
	private static boolean termineAvecSeparateurUniversel(
			final String pString) {
		
		/* retourne false si pString == null. */
		if (pString == null) {
			return false;
		}
		
		if (StringUtils.endsWith(pString, SEP_REP)) {
			return true;
		}
		
		return false;
		
	} // Fin de termineAvecSeparateurUniversel(...)._______________________
	
	
	
	/**
	 * Instancie tous les attributs relatifs 
	 * aux fichier de preferences.<br/>
	 * Crée les fichiers vides (et leur arborescence) sur HDD.<br/>
	 * <ul>
	 * <li>instancie pathAbsoluPreferencesProperties.</li>
	 * <li>instancie pathAbsoluPreferencesXml.</li>
	 * <li>instancie filePreferencesProperties.</li>
	 * <li>instancie filePreferencesXml.</li>
	 * <li>Crée sur le disque dur l'arborescence et le fichier 
	 * filePreferencesProperties si nécessaire.</li>
	 * <li>Crée sur le disque dur l'arborescence et le fichier 
	 * filePreferencesXml si nécessaire.</li>
	 * </ul>
	 *
	 * @throws Exception
	 */
	private static void instancierAttributsFichierProperties() 
			throws Exception {
		
		synchronized (PreferencesFournisseurArboTraficManager.class) {
			
			final Path pathRessourcesExternes 
			= Paths.get(ConfigurationApplicationManager
					.getPathRessourcesExternes());
		
			if (pathAbsoluPreferencesProperties == null) {
				pathAbsoluPreferencesProperties 
				= pathRessourcesExternes
					.resolve("preferences/"
							+ "preferencesFournisseurArboTrafic.properties");
			}
			
			if (pathAbsoluPreferencesXml == null) {
				pathAbsoluPreferencesXml 
				= pathRessourcesExternes
					.resolve("preferences/"
							+ "preferencesFournisseurArboTrafic.xml");
			}
			
			if (filePreferencesProperties == null) {
				filePreferencesProperties 
				= pathAbsoluPreferencesProperties.toFile();
				
				if (!filePreferencesProperties.exists()) {
					creerRepertoiresArbo(filePreferencesProperties);
					Files.createFile(pathAbsoluPreferencesProperties);
				}				
			}
			
			if (filePreferencesXml == null) {
				filePreferencesXml 
				= pathAbsoluPreferencesXml.toFile();
				
				if (!filePreferencesXml.exists()) {
					creerRepertoiresArbo(filePreferencesXml);
					Files.createFile(pathAbsoluPreferencesXml);
				}	
			}
					
		} // Fin du bloc synchronized.__________________
		
	} // Fin de instancierAttributsFichierProperties().____________________
	

	
	/**
	 * Crée sur disque dur l'arborescence des répertoires 
	 * parent de pFile.<br/>
	 * <ul>
	 * <li><code>Files.createDirectories(pathParent);</code></li>
	 * </ul>
	 * - ne fait rien si pFile == null.<br/>
	 * - ne fait rien si pFile est une racine (pas de parent).<br/>
	 * </br/>
	 *
	 * @param pFile : File : 
	 * fichier dont on veut créer l'arborescence sur disque dur.<br/>
	 * 
	 * @throws IOException
	 */
	private static void creerRepertoiresArbo(
			final File pFile) throws IOException {
		
		synchronized (PreferencesFournisseurArboTraficManager.class) {
			
			/* ne fait rien si pFile == null. */
			if (pFile == null) {
				return;
			}
			
			final Path pathFile = pFile.toPath();
			final Path pathParent = pathFile.getParent();
			
			/* ne fait rien si pFile est une racine (pas de parent). */
			if (pathParent != null) {
				Files.createDirectories(pathParent);
			}
						
		} // Fin du bloc synchronized.__________________
				
	} // Fin de creerRepertoiresArbo(...)._________________________________
	

	
	/**
	 * Vérifie si un File pFile est un bon répertoire existant.<br/>
	 * <ul>
	 * <li>retourne true si le répertoire existe.</li>
	 * </ul>
	 * - retourne false si pFile == null.<br/>
	 * - retourne false si pFile n'existe pas.<br/>
	 * - retourne false si pFile n'est pas un répertoire.<br/>
	 * <br/>
	 *
	 * @param pFile : File : répertoire existant.<br/>
	 * 
	 * @return : boolean : true si le répertoire existe.<br/>
	 */
	private static boolean verifierRepertoireExistant(
			final File pFile) {
		
		synchronized (PreferencesFournisseurArboTraficManager.class) {
			
			/* retourne false si pFile == null. */
			if (pFile == null) {
				return false;
			}
			
			/* retourne false si pFile n'existe pas. */
			if (!pFile.exists()) {
				return false;
			}
			
			/* retourne false si pFile n'est pas un répertoire. */
			if (!pFile.isDirectory()) {
				return false;
			}
			
			return true;
			
		} // Fin du bloc synchronized._______________
		
	} // Fin de verifierRepertoireExistant(...).___________________________
	
	
	
	/**
	 * Vérifie si une année d'exploitation pAnnee 
	 * contient bien exactement 4 chiffres.<br/>
	 * <ul>
	 * <li>retourne true si pAnnee contient exactement 4 chiffres.</li>
	 * </ul>
	 * - retourne false si pAnnee == null.<br/>
	 * - retourne false si pAnnee ne contient pas 
	 * exactement 4 chiffres.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String.<br/>
	 * 
	 * @return : Boolean : 
	 * true si pAnnee contient exactement 4 chiffres..<br/>
	 */
	private static Boolean verifierAnneeConvenable(
			final String pAnnee) {
		
		synchronized (PreferencesFournisseurArboTraficManager.class) {
			
			boolean resultat = true;
			
			/* retourne false si pAnnee == null. */
			if (pAnnee == null) {
				return false;
			}
			
			final String motif = "\\d{4}";
			
			final Pattern pattern = Pattern.compile(motif);
			final Matcher matcher = pattern.matcher(pAnnee);
			
			if (matcher.matches()) {
				resultat = true;
				
			/* retourne false si pAnnee ne contient pas 
			 * exactement 4 chiffres. */	
			} else {
				resultat = false;
			}
			
			return resultat;
			
		} // Fin du bloc synchronized._______________
		
	} // Fin de verifierAnneeConvenable(...).______________________________
	
	
	
	/**
	 * <b>Crée ou met à jour une Property</b> dans 
	 * le <i>Properties</i> <b>preferences</b>.<br/>
	 * <ul>
	 * <li>initialise le <i>Properties</i> <b>preferences</b> 
	 * et remplit le <i>fichier</i> .properties si nécessaire.</li>
	 * <li>Crée ou maj dans l'objet Properties <b>preferences</b> 
	 * <i>sans enregistrer la modification sur le disque dur</i>.</li>
	 * <li>preferences.setProperty(pKey, pValue);</li>
	 * </ul>
	 * - retourne false si pKey == null.<br/>
	 * - retourne false si pValue == null.<br/>
	 * <br/>
	 *
	 * @param pKey : String : Clé.<br/>
	 * @param pValue : String : Valeur.<br/>
	 * 
	 * @return : boolean : true si la property a été créée.<br/>
	 * 
	 * @throws Exception 
	 */
	public static boolean creerOuModifierProperty(
			final String pKey
				, final String pValue) throws Exception {
		
		synchronized (PreferencesFournisseurArboTraficManager.class) {
			
			/* initialise le Properties preferences et 
			 * remplit le fichier .properties si nécessaire. */
			initialiserPreferencesEtProperties();

			
			/* retourne false si pKey == null. */
			if (pKey == null) {
				return false;
			}
			
			/* retourne false si pValue == null. */
			if (pValue == null) {
				return false;
			}
			
			/* crée ou met à jour la Property dans preferences. */
			preferences.setProperty(pKey, pValue);
			
			return true;
			
		} // Fin du bloc synchronized.__________________
				
	} // Fin de creerOuModifierProperty(...).______________________________
	

	
	/**
	 * <b>Retire une Property</b> dans 
	 * le <i>Properties</i> <b>preferences</b>.<br/>
	 * <ul>
	 * <li>initialise le <i>Properties</i> <b>preferences</b> 
	 * et remplit le <i>fichier</i> .properties si nécessaire.</li>
	 * <li>retire dans l'objet Properties <b>preferences</b> 
	 * <i>sans enregistrer la modification sur le disque dur 
	 * (.properties)</i>.</li>
	 * <li><code>preferences.remove(pKey);</code>.</li>
	 * </ul>
	 * - retourne false si pKey == null.<br/>
	 * <br/>
	 *
	 * @param pKey : String : Clé.<br/>
	 * 
	 * @return : boolean : true si la property a été retirée.<br/>
	 * 
	 * @throws Exception 
	 */
	public static boolean retirerProperty(
			final String pKey) 
					throws Exception {
		
		synchronized (PreferencesFournisseurArboTraficManager.class) {
			
			/* initialise le Properties preferences et 
			 * remplit le fichier .properties si nécessaire. */
			initialiserPreferencesEtProperties();
			
			/* retourne false si pKey == null. */
			if (pKey == null) {
				return false;
			}
			
			/* retire la Property de preferences. */
			preferences.remove(pKey);
			
			return true;
			
		} // Fin du bloc synchronized.__________________
				
	} // Fin de retirerProperty(...).______________________________________
	

	
	/**
	 * vide le <i>Properties</i> <b>preferences</b>.<br/>
	 * <ul>
	 * <li>initialise le <i>Properties</i> <b>preferences</b> 
	 * et remplit le <i>fichier</i> .properties si nécessaire.</li>
	 * <li>vide l'objet <i>Properties</i> <b>preferences</b> 
	 * sans vider le <i>fichier</i> .properties correspondant 
	 * sur le disque dur.</li>
	 * <li><code>preferences.remove(cle);</code>.</li>
	 * </ul>
	 * - retourne false si l'ensemble des clés du 
	 * Properties preferences est null.<br/>
	 * <br/>
	 *
	 * @return : boolean : true si preferences a été vidée.<br/>
	 * 
	 * @throws Exception 
	 */
	public static boolean viderPreferences() throws Exception {
		
		synchronized (PreferencesFournisseurArboTraficManager.class) {
			
			/* initialise le Properties preferences et 
			 * remplit le fichier .properties si nécessaire. */
			initialiserPreferencesEtProperties();
				
			final Set<String> clesSet 
				= preferences.stringPropertyNames();
			
			/* retourne false si l'ensemble des clés 
			 * du Properties preferences est null. */
			if (clesSet == null) {
				return false;
			}
			
			/* vidage du Properties preferences. */
			for (final String cle : clesSet) {
				preferences.remove(cle);
			}
			
			return true;
			
		} // Fin du bloc synchronized.__________________
		
	} // Fin de viderPreferences().________________________________________
	
	
		
	/**
	 * <b>Enregistre en UTF8</b> le <i>Properties</i> preferences 
	 * sur disque dur dans 
	 * le <i>fichier</i> <b>ressources_externes/preferences/
	 * preferencesFournisseurArboTrafic.properties</b>.<br/>
	 * <ul>
	 * <li>initialise le <i>Properties</i> <b>preferences</b> 
	 * et remplit le <i>fichier</i> .properties si nécessaire.</li>
	 * <li>enregistre le <i>Properties</i> <b>preferences</b> 
	 * sur disque dur dans le <i>fichier</i> 
	 * .properties correspondant.</li>
	 * <li>Prise en compte (stockage) 
	 * d'une modification d'une Property.</li>
	 * <li><code>preferences.store(writer, null);</code></li>
	 * </ul>
	 * 
	 * @throws Exception 
	 */
	public static void enregistrerFichierPreferencesProperties() 
			throws Exception {
		
		synchronized (PreferencesFournisseurArboTraficManager.class) {
			
			/* initialise le Properties preferences et 
			 * remplit le fichier .properties si nécessaire. */
			initialiserPreferencesEtProperties();

			/* instancie  fichiers preferences. */
			instancierAttributsFichierProperties();
			
			/* try-with-resource qui se charge du close(). */
			try (Writer writer = Files.newBufferedWriter(
					pathAbsoluPreferencesProperties, CHARSET_UTF8)) {
				
				/* enregistre le Properties preferences sur disque dur 
				 * dans le fichier .properties correspondant. */
				preferences.store(writer, null);
				
			}

		} // Fin du bloc synchronized.__________________
		
	} // Fin de enregistrerFichierPreferences().___________________________
	

	
	/**
	 * <b>Enregistre en UTF8</b> le <i>Properties</i> preferences dans 
	 * le <i>fichier</i> <b>ressources_externes/preferences/
	 * preferencesFournisseurArboTrafic.xml</b>.<br/>
	 * <ul>
	 * <li>initialise le <i>Properties</i> <b>preferences</b> 
	 * et remplit le <i>fichier</i> .properties si nécessaire.</li>
	 * <li>Prise en compte (stockage) 
	 * d'une modification d'une Property.</li>
	 * <li><code>preferences.storeToXML(
	 * outputStream, null, "UTF-8");</code></li>
	 * </ul>
	 *
	 * @throws Exception
	 */
	public static void enregistrerFichierPreferencesXml() 
			throws Exception {
				
		synchronized (PreferencesFournisseurArboTraficManager.class) {
			
			/* initialise le Properties preferences et 
			 * remplit le fichier .properties si nécessaire. */
			initialiserPreferencesEtProperties();

			/* initialise les fichiers preferences. */
			instancierAttributsFichierProperties();
			
			/* try-with-resource qui se charge du close(). */
			try (OutputStream outputStream 
				= Files.newOutputStream(pathAbsoluPreferencesXml)) {
				preferences.storeToXML(outputStream, null, "UTF-8");
			}

		} // Fin du bloc synchronized.__________________

	} // Fin de enregistrerFichierPreferencesXml().________________________
	
	
	
	/**
	 * instancie le <i>Properties</i> <b>preferences</b> 
	 * et l'alimente en décodant en UTF8 le <i>fichier</i> 
	 * <b>ressources_externes/preferences/
	 * preferencesFournisseurArboTrafic.properties</b>.<br/>
	 * <ul>
	 * <li>instancie le <i>Properties</i> <b>preferences</b>.</li>
	 * <li><code>preferences.load(inputStream);</code></li>
	 * </ul>
	 * @throws Exception 
	 */
	public static void lireFichierPreferencesProperties() throws Exception {

		synchronized (PreferencesFournisseurArboTraficManager.class) {
			
			/* initialise les attributs relatifs aux fichiers preferences. */
			instancierAttributsFichierProperties();
					
			/* try-with-resource qui se charge du close(). */
			try (Reader reader = Files.newBufferedReader(
					pathAbsoluPreferencesProperties, CHARSET_UTF8)) {
				
				/* instancie le Properties preferences. */
				preferences = new Properties();
				
				/* décode le fichier .properties en UTF8 
				 * et le charge dans le Properties preferences.*/
				preferences.load(reader);
		
			}

		} // Fin du bloc synchronized.__________________
				
	} // Fin de lireFichierPreferences().__________________________________
	

	
	/**
	 * instancie le <i>Properties</i> <b>preferences</b> 
	 * et l'alimente en décodant en UTF8 le <i>fichier</i> 
	 * <b>ressources_externes/preferences/
	 * preferencesFournisseurArboTrafic.xml</b>.<br/>
	 * <ul>
	 * <li><code>preferences.loadFromXML(inputStream);</code></li>
	 * </ul>
	 *
	 * @throws Exception
	 */
	public static void lireFichierPreferencesXml() throws Exception {

		synchronized (PreferencesFournisseurArboTraficManager.class) {
			
			/* initialise les fichiers preferences. */
			instancierAttributsFichierProperties();
			
			/* try-with-resource qui se charge du close(). */
			try (InputStream inputStream 
				= Files.newInputStream(pathAbsoluPreferencesXml)) {
				
				/* instancie le Properties preferences. */
				preferences = new Properties();
				
				/* décode le fichier .xml en UTF8 
				 * et le charge dans le Properties preferences.*/
				preferences.loadFromXML(inputStream);
				
			}

		} // Fin du bloc synchronized.__________________
		
	} // Fin de lireFichierPreferences().__________________________________


	
	/**
	 * fournit une String pour l'affichage de 
	 * preferencesFournisseurArboTrafic.properties.<br/>
	 * <br/>
	 * - retourne null si preferences == null.<br/>
	 * <br/>
	 *
	 * @return : String.<br/>
	 * @throws Exception 
	 */
	public static String afficherPreferences() throws Exception {

		synchronized (PreferencesFournisseurArboTraficManager.class) {
			
			/* initialise le Properties preferences et 
			 * remplit le fichier .properties si nécessaire. */
			initialiserPreferencesEtProperties();
			
			/* retourne null si preferences == null. */
			if (preferences == null) {
				return null;
			}
			
			final StringBuffer stb = new StringBuffer();
			
			for (final String key : preferences.stringPropertyNames()) {
				stb.append(key);
				stb.append(EGAL);
				stb.append(preferences.getProperty(key));
				stb.append(SAUT_LIGNE_JAVA);
			}
			
			return stb.toString();

		} // Fin du bloc synchronized.__________________
		
	} // Fin de afficherPreferences()._____________________________________
	
	

	/**
	 * Getter des Properties encapsulant les préférences.<br/>
	 * <br/>
	 *
	 * @return preferences : Properties : this.preferences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static Properties getPreferences() throws Exception {

		synchronized (PreferencesFournisseurArboTraficManager.class) {
			
			/* initialise le Properties preferences et 
			 * remplit le fichier .properties si nécessaire. */
			initialiserPreferencesEtProperties();

			return preferences;

		} // Fin du bloc synchronized.__________________
		
	} // Fin de getPreferences().__________________________________________

	
	
	/**
	 * Getter du Path absolu vers 
	 * preferencesFournisseurArboTrafic.properties.<br/>
	 * <br/>
	 *
	 * @return pathAbsoluPreferencesProperties : Path : 
	 * this.pathAbsoluPreferencesProperties.<br/>
	 */
	public static Path getPathAbsoluPreferencesProperties() {
		return pathAbsoluPreferencesProperties;
	} // Fin de getPathAbsoluPreferencesProperties().______________________



	/**
	* Setter du Path absolu vers 
	* preferencesFournisseurArboTrafic.properties.<br/>
	* <br/>
	*
	* @param pPathAbsoluPreferencesProperties : Path : 
	* valeur à passer à this.pathAbsoluPreferencesProperties.<br/>
	*/
	public static void setPathAbsoluPreferencesProperties(
			final Path pPathAbsoluPreferencesProperties) {
		pathAbsoluPreferencesProperties = pPathAbsoluPreferencesProperties;
	} // Fin de setPathAbsoluPreferencesProperties(...).___________________



	/**
	 * Getter du Path absolu vers 
	 * preferencesFournisseurArboTrafic.xml.<br/>
	 * <br/>
	 *
	 * @return pathAbsoluPreferencesXml : Path : 
	 * this.pathAbsoluPreferencesXml.<br/>
	 */
	public static Path getPathAbsoluPreferencesXml() {
		return pathAbsoluPreferencesXml;
	} // Fin de getPathAbsoluPreferencesXml()._____________________________



	/**
	* Setter du Path absolu vers 
	* preferencesFournisseurArboTrafic.xml.<br/>
	* <br/>
	*
	* @param pPathAbsoluPreferencesXml : Path : 
	* valeur à passer à this.pathAbsoluPreferencesXml.<br/>
	*/
	public static void setPathAbsoluPreferencesXml(
			final Path pPathAbsoluPreferencesXml) {
		pathAbsoluPreferencesXml = pPathAbsoluPreferencesXml;
	} // Fin de setPathAbsoluPreferencesXml(...).__________________________



	/**
	 * Getter du fichier 
	 * preferencesFournisseurArboTrafic.properties.<br/>
	 * <br/>
	 *
	 * @return filePreferencesProperties : File : 
	 * this.filePreferencesProperties.<br/>
	 */
	public static File getFilePreferencesProperties() {
		return filePreferencesProperties;
	} // Fin de getFilePreferencesProperties().____________________________



	/**
	* Setter du fichier 
	* preferencesFournisseurArboTrafic.properties.<br/>
	* <br/>
	*
	* @param pFilePreferencesProperties : File : 
	* valeur à passer à this.filePreferencesProperties.<br/>
	*/
	public static void setFilePreferencesProperties(
			final File pFilePreferencesProperties) {
		filePreferencesProperties = pFilePreferencesProperties;
	} // Fin de setFilePreferencesProperties(...)._________________________



	/**
	 * Getter du fichier 
	 * preferencesFournisseurArboTrafic.xml.<br/>
	 * <br/>
	 *
	 * @return filePreferencesXml : File : 
	 * this.filePreferencesXml.<br/>
	 */
	public static File getFilePreferencesXml() {
		return filePreferencesXml;
	} // Fin de getFilePreferencesXml().___________________________________



	/**
	* Setter du fichier 
	* preferencesFournisseurArboTrafic.xml.<br/>
	* <br/>
	*
	* @param pFilePreferencesXml : File : 
	* valeur à passer à this.filePreferencesXml.<br/>
	*/
	public static void setFilePreferencesXml(
			final File pFilePreferencesXml) {
		filePreferencesXml = pFilePreferencesXml;
	} // Fin de setFilePreferencesXml(...).________________________________


	
	/**
	 * Getter de la RACINE (Trafic_Histonat) 
	 * des fichiers annuels de Trafic (SINGLETON) stockée dans 
	 * ressources_externes/preferences/
	 * preferencesFournisseurArboTrafic.properties.<br/>
	 * <ul>
	 * <li>LIT LA PROPERTY SUR DISQUE DUR 
	 * DANS LE FICHIER PROPERTIES.</li>
	 * <li>instancie l'attribut Properties <b>preferences</b> 
	 * si preferences == null.</li>
	 * <li>initialise le Properties avec des valeurs en 
	 * dur de la classe si il est vide.</li>
	 * <li>lit la valeur dans le Properties si elle existe.</li>
	 * <li>applique une valeur en dur de la classe 
	 * <b>ROOTFILE_STRING_PAR_DEFAUT_EN_DUR</b>
	 * si la Property n'existe pas.</li>
	 * <li>instancie l'attribut <b>rootFile</b> 
	 * après lecture de la Property.</li>
	 * <li>crée sur disque le répertoire rootFile lu dans 
	 * les préférences si il n'existe pas.</li>
	 * </ul>
	 * - rootFile = ROOTFILE_STRING_PAR_DEFAUT_EN_DUR si 
	 * rootFile lu dans les préférences ne peut être créé 
	 * (problème IO).<br/>
	 * - crée ROOTFILE_STRING_PAR_DEFAUT_EN_DUR sur le 
	 * disque si il n'existe pas.<br/>
	 * - rootFile = ROOTFILE_STRING_PAR_DEFAUT_EN_DUR si 
	 * la Property dans preferences n'a pas de valeur.<br/>
	 * - rootFile = ROOTFILE_STRING_PAR_DEFAUT_EN_DUR si 
	 * la Property dans preferences ne peut être lue.<br/>
	 * <br/>
	 *
	 * @return rootFile : File.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File getRootFile() throws Exception {

		synchronized (PreferencesFournisseurArboTraficManager.class) {

			/* SINGLETON. */
			if (rootFile == null) {

				/*
				 * initialise le Properties preferences 
				 * et remplit le fichier .properties si
				 * nécessaire.
				 */
				initialiserPreferencesEtProperties();

				/* lit la valeur dans le Properties si elle existe. */
				String rootFileDansPropertiesString = null;

				try {

					rootFileDansPropertiesString 
						= preferences.getProperty(KEY_ROOTFILE);

					/*  instancie l'attribut après lecture 
					 * de la Property. */
					if (rootFileDansPropertiesString != null) {

						try {

							rootFile 
								= new File(
										rootFileDansPropertiesString);

							/* crée sur disque le répertoire rootFile 
							 * lu dans les préférences si il n'existe pas. */
							if (!rootFile.exists()) {

								Files.createDirectories(rootFile.toPath());
								
							}

						/* rootFile = ROOTFILE_STRING_PAR_DEFAUT_EN_DUR 
						 * si rootFile lu dans les préférences ne peut 
						 * être créé (problème IO). */
						} catch (Exception e) {

							rootFile 
								= new File(
										ROOTFILE_STRING_PAR_DEFAUT_EN_DUR);

							/* crée ROOTFILE_STRING_PAR_DEFAUT_EN_DUR sur 
							 * le disque si il n'existe pas. */
							if (!rootFile.exists()) {

								Files.createDirectories(rootFile.toPath());
								
							}
						}

					/* rootFile = ROOTFILE_STRING_PAR_DEFAUT_EN_DUR si 
					 * la Property dans preferences n'a pas de valeur. */
					} else {

						rootFile 
							= new File(
									ROOTFILE_STRING_PAR_DEFAUT_EN_DUR);

						/* crée ROOTFILE_STRING_PAR_DEFAUT_EN_DUR sur 
						 * le disque si il n'existe pas. */
						if (!rootFile.exists()) {

							Files.createDirectories(rootFile.toPath());
							
						}

					}
					
				}

				/* rootFile = ROOTFILE_STRING_PAR_DEFAUT_EN_DUR si 
				 * la Property dans preferences ne peut être lue. */
				catch (Exception e1) {
										
					rootFile 
					= new File(
							ROOTFILE_STRING_PAR_DEFAUT_EN_DUR);

					/* crée ROOTFILE_STRING_PAR_DEFAUT_EN_DUR 
					 * sur le disque si il n'existe pas. */
					if (!rootFile.exists()) {
	
						Files.createDirectories(rootFile.toPath());
						
					}
					
				}

			}

			return rootFile;

		} // Fin du bloc synchronized.__________________

	} // Fin de getRootFile()._____________________________________________


	
	/**
	* Setter de la RACINE (Trafic_Histonat) 
	* des fichiers annuels de Trafic.<br/>
	* </ul>
	* <li>MODIFIE LA PROPERTY ET L'ENREGISTRE 
	* DANS LE FICHIER PROPERTIES DE PREFERENCES.</li>
	* <li>ne fait rien si le paramètre ne convient pas.</li>
	* <li>initialise le <i>Properties</i> <b>preferences</b> 
	* et remplit le <i>fichier</i> .properties si nécessaire.</li>
	* <li>passe le paramètre à l'attribut.</li>
	* <li>crée ou modifie la Property dans le <i>Properties</i> 
	* <b>preferences</b>.</li>
	* <li>enregistre le <i>Properties</i> <b>preferences</b> 
	* sur disque dur dans les <i>fichiers</i> .properties (et .xml).</li>
	* </ul>
	* <br/>
	*
	* @param pRootFile : File : 
	* valeur à passer à rootFile.<br/>
	* 
	 * @throws Exception 
	*/
	public static void setRootFile(
			final File pRootFile) throws Exception {

		synchronized (PreferencesFournisseurArboTraficManager.class) {
			
			/* ne fait rien si le paramètre ne convient pas. */
			if (verifierRepertoireExistant(pRootFile)) {
				
				/* initialise le Properties preferences et 
				 * remplit le fichier .properties si nécessaire. */
				initialiserPreferencesEtProperties();
				
				/* passe le paramètre à l'attribut. */
				rootFile = pRootFile;
				
				final String pathRootFileString 
					= pRootFile.getAbsolutePath();
				
				/* crée ou modifie la Property dans le 
				 * Properties preferences. */
				creerOuModifierProperty(
						KEY_ROOTFILE, pathRootFileString);
				
				/* enregistre le Properties preferences sur 
				 * disque dur dans les fichiers .properties 
				 * (et .xml). */
				enregistrerFichierPreferencesProperties();
				enregistrerFichierPreferencesXml();
			}

		} // Fin du bloc synchronized.__________________
						
	} // Fin de setRootFile(...).__________________________________________



	/**
	 * Getter de l'année d'exploitation des fichiers annuels de trafic 
	 * (2013, 2017, ...).<br/>
	 * <ul>
	 * <li>LIT LA PROPERTY SUR DISQUE DUR 
	 * DANS LE FICHIER PROPERTIES.</li>
	 * <li>instancie le Properties preferences 
	 * si preferences == null.</li>
	 * <li>initialise le Properties avec des valeurs en 
	 * dur de la classe si il est vide.</li>
	 * <li>lit la valeur dans le Properties si elle existe.</li>
	 * <li>applique une valeur en dur de la classe 
	 * si la Property n'existe pas.</li>
	 * <li>instancie l'attribut après lecture de la Property.</li>
	 * </ul>
	 *
	 * @return anneeExploitation : String.<br/>
	 * 
	 * @throws Exception 
	 */
	public static String getAnneeExploitation() throws Exception {
		
		synchronized (PreferencesFournisseurArboTraficManager.class) {

			/* SINGLETON. */
			if (anneeExploitation == null) {

				/*
				 * initialise le Properties preferences 
				 * et remplit le fichier .properties si
				 * nécessaire.
				 */
				initialiserPreferencesEtProperties();

				/* lit la valeur dans le Properties si elle existe. */
				String anneeDansPropertiesString = null;

				try {

					anneeDansPropertiesString 
						= preferences.getProperty(KEY_ANNEE);

					/*  instancie l'attribut après lecture 
					 * de la Property. */
					if (anneeDansPropertiesString != null) {

						anneeExploitation = anneeDansPropertiesString;

					} else {

						anneeExploitation = ANNEE_EXPOITATION_EN_DUR;

					}
					
				}

				catch (Exception e1) {
										
					anneeExploitation = ANNEE_EXPOITATION_EN_DUR;
				}

			}

			return anneeExploitation;

		} // Fin du bloc synchronized.__________________
		
	} // Fin de getAnneeExploitation().____________________________________



	/**
	* Setter de l'année d'exploitation des fichiers annuels de trafic 
	* (2013, 2017, ...).<br/>
	* </ul>
	* <li>MODIFIE LA PROPERTY ET L'ENREGISTRE 
	* DANS LE FICHIER PROPERTIES DE PREFERENCES.</li>
	* <li>ne fait rien si le paramètre ne convient pas.</li>
	* <li>initialise le <i>Properties</i> <b>preferences</b> 
	* et remplit le <i>fichier</i> .properties si nécessaire.</li>
	* <li>passe le paramètre à l'attribut.</li>
	* <li>crée ou modifie la Property dans le <i>Properties</i> 
	* <b>preferences</b>.</li>
	* <li>enregistre le <i>Properties</i> <b>preferences</b> 
	* sur disque dur dans les <i>fichiers</i> .properties (et .xml).</li>
	* </ul>
	* <br/>
	*
	* @param pAnneeExploitation : String : 
	* valeur à passer à anneeExploitation.<br/>
	* 
	 * @throws Exception 
	*/
	public static void setAnneeExploitation(
			final String pAnneeExploitation) throws Exception {

			synchronized (PreferencesFournisseurArboTraficManager.class) {
				
				/* ne fait rien si le paramètre ne convient pas. */
				if (verifierAnneeConvenable(pAnneeExploitation)) {
					
					/* initialise le Properties preferences et 
					 * remplit le fichier .properties si nécessaire. */
					initialiserPreferencesEtProperties();
					
					/* passe le paramètre à l'attribut. */
					anneeExploitation = pAnneeExploitation;
										
					/* crée ou modifie la Property dans le 
					 * Properties preferences. */
					creerOuModifierProperty(
							KEY_ANNEE, anneeExploitation);
					
					/* enregistre le Properties preferences sur 
					 * disque dur dans les fichiers .properties 
					 * (et .xml). */
					enregistrerFichierPreferencesProperties();
					enregistrerFichierPreferencesXml();
				}

			} // Fin du bloc synchronized.__________________
							
	} // Fin de setAnneeExploitation(...)._________________________________

	

} // FIN DE LA CLASSE PreferencesFournisseurArboTraficManager.---------------
