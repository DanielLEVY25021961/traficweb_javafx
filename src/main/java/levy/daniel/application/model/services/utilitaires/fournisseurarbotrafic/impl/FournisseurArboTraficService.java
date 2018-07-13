package levy.daniel.application.model.services.utilitaires.fournisseurarbotrafic.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import levy.daniel.application.apptechnic.configurationmanagers.PreferencesFournisseurArboTraficManager;
import levy.daniel.application.model.services.utilitaires.fournisseurarbotrafic.IArboTrafic;

/**
 * CLASSE FournisseurArboTraficService :<br/>
 * SERVICE UTILITAIRE chargé de mettre à disposition de l'application 
 * les <b>répertoires et fichiers de l'arborescence des trafics</b>.<br/>
 * <br/>
 * <img src="../../../../../../../../../../../javadoc/images/Arborescence_trafics.png" 
 * alt="Arborescence des trafics" border="1" align="center" />
 * <br/>
 * <br/>
 * <ul>
 * <li>La <b>racine de l'arborescence</b> est toujours par convention 
 * <b>Trafic_Histonat</b> 
 * que l'on peut disposer où l'on veut.</li>
 * <li>Les <b>fichiers annuels de trafic</b> sont ensuite disposés directement 
 * <i>sous la racine</i> dans un répertoire annuel de la forme Trafic_aaaa 
 * comme Trafic_2018.</li>
 * <li>l'arborescence sous le fichier de trafic annuel est 
 * la même tous les ans.</li>
 * <li>On change de RACINE (Trafic_Histonat) en faisant <i>avant toute chose</i> :<br/>
 * <ul>
 * <li><code>FournisseurArboTraficService.creerRepertoireSurDisque(nouvelle racine);</code></li>
 * <li><code>FournisseurArboTraficService.selectionnerRootFile(nouvelle racine);</code></li></ul></li>
 * <li>on change d'ANNEE D'EXPLOITATION en faisant <i>avant toute chose</i> :<br/>
 * <ul>
 * <li><code>FournisseurArboTraficService.selectionnerAnneeExploitation(nouvelle année);</code></li>
 * </ul>
 * </li>
 * <li>les méthodes fournirRepertoireXXX créent le 
 * répertoire XXX si il n'existe pas.</li>
 * <li>les méthodes fournirRepertoireXXX retournent 
 * le répertoire XXX existant.</li>
 * </ul>
 * <br/>
 *
 * - Exemple d'utilisation :<br/>
 *<br/>
 * 
 * - Mots-clé :<br/>
 * créer fichier sur disque, créer fichier, enregistrer fichier, <br/>
 * créer répertoire sur disque, repertoire sur disque, <br/>
 * enregistrer fichier,<br/>
 * select case, switch case, switch case avec String,<br/>
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
public final class FournisseurArboTraficService {

	// ************************ATTRIBUTS************************************/

	/**
	 * CLASSE_FOURNISSEURARBOTRAFIC_SERVICE : String :<br/>
	 * "Classe FournisseurArboTraficService".<br/>
	 */
	public static final String CLASSE_FOURNISSEURARBOTRAFIC_SERVICE 
		= "Classe FournisseurArboTraficService";
	
	/**
	 * METHODE_CREER_REPERTOIRE_SUR_DISQUE : String :<br/>
	 * "méthode creerRepertoireSurDisque(File pFile)".<br/>
	 */
	public static final String METHODE_CREER_REPERTOIRE_SUR_DISQUE 
		= "méthode creerRepertoireSurDisque(File pFile)";

	/**
	 * METHODE_CREER_FICHIER_SUR_DISQUE : String :<br/>
	 * "méthode creerFichierSurDisque(File pFile)".<br/>
	 */
	public static final String METHODE_CREER_FICHIER_SUR_DISQUE 
		= "méthode creerFichierSurDisque(File pFile)";
	
	/**
	 * MESSAGE_FICHIER_NULL : String :<br/>
	 * Message retourné par la METHODE_ECRIRESTRINGDANSFILE 
	 * si le fichier est null.<br/>
	 * "Le fichier passé en paramètre est null".<br/>
	 */
	public static final String MESSAGE_FICHIER_NULL 
		= "Le fichier passé en paramètre est null";
		
	/**
	 * MESSAGE_FICHIER_INEXISTANT : String :<br/>
	 * Message retourné par la METHODE_ECRIRESTRINGDANSFILE 
	 * si le fichier est inexistant.<br/>
	 * "Le fichier passé en paramètre est inexistant : "
	 */
	public static final String MESSAGE_FICHIER_INEXISTANT 
		= "Le fichier passé en paramètre est inexistant : ";
	
	/**
	 * MESSAGE_FICHIER_REPERTOIRE : String :<br/>
	 * Message retourné par la METHODE_ECRIRESTRINGDANSFILE 
	 * si le fichier est un répertoire.<br/>
	 * "Le fichier passé en paramètre est un répertoire : ".<br/>
	 */
	public static final String MESSAGE_FICHIER_REPERTOIRE 
		= "Le fichier passé en paramètre est un répertoire : ";

	/**
	 * MESSAGE_STRING_BLANK : String :<br/>
	 * Message retourné par la METHODE_ECRIRESTRINGDANSFILE 
	 * si la String passée en paramètre est blank.<br/>
	 * "La chaine de caractères passée en paramètre est blank (null ou vide) : "
	 */
	public static final String MESSAGE_STRING_BLANK 
	= "La chaine de caractères passée en paramètre est blank (null ou vide) : ";
	
	
	//*****************************************************************/
	//********************* CARACTERES UTILES *************************/
	//*****************************************************************/
	
	/**
	 * UNDERSCORE : char :<br/>
	 * '_'.<br/>
	 */
	public static final char UNDERSCORE = '_';
	
	
	/**
	 * POINT : char :<br/>
	 * '.'.<br/>
	 */
	public static final char POINT = '.';
	
	
	/**
	 * SEPARATEUR_FILE : String :<br/>
	 * Séparateur Java Windows pour les répertoires.<br/>
	 * "\\"<br/>
	 */
	public static final String SEPARATEUR_FILE = "\\";
	
	
	/**
	 * SEP_REP : String :<br/>
	 * Séparateur Java universel pour les répertoires.<br/>
	 * "/"<br/>
	 */
	public static final String SEP_REP = "/";
	

	/**
	 * SEP_MOINS : String :<br/>
	 * " - ".<br/>
	 */
	public static final String SEP_MOINS = " - ";


	/**
	 * FILE_SEPARATOR : String :<br/>
	 * "file.separator".<br/>
	 * Utile pour récupérer le séparateur de fichiers 
	 * de la plateforme avec System.getProperty("file.separator").
	 */
	public static final String FILE_SEPARATOR = "file.separator";
	


	/**
	 * rootFile : File :<br/>
	 * RACINE (Trafic_Histonat) des fichiers annuels de Trafic.<br/>
	 * SINGLETON.<br/>
	 */
	private static transient File rootFile;
	
	/**
	 * anneeExploitation : String :<br/>
	 * année d'exploitation des fichiers annuels de trafic 
	 * (2013, 2017, ...).<br/>
	 */
	private static transient String anneeExploitation;
	
	
	/**
	 * LOG : Log : 
	 * Logger pour Log4j (utilisant commons-logging).
	 */
	private static final Log LOG 
		= LogFactory.getLog(FournisseurArboTraficService.class);

	// *************************METHODES************************************/

	
	 /**
	 * method CONSTRUCTEUR FournisseurArboTraficService() :<br/>
	 * CONSTRUCTEUR D'ARITE NULLE.<br/>
	 * private pour bloquer l'instanciation.<br/>
	 * <br/>
	 */
	private FournisseurArboTraficService() {
		super();
	} // Fin de CONSTRUCTEUR D'ARITE NULLE.________________________________
	
	
	
	/**
	 * <b>Sélectionne un répertoire</b> pRoot comme 
	 * racine des trafics (Trafic_Histonat) et 
	 * l'<b>enregistre dans les préférences</b>.<br/>
	 * Ne modifie la RACINE des trafics enregistrée dans les préférences 
	 * que si pRoot est différent de la racine enregistrée.<br/>
	 * Fait de pRoot la <b>RACINE 
	 * des trafics courante</b>.<br/>
	 * <ul>
	 * <li>On change de RACINE (Trafic_Histonat) en faisant <i>avant toute chose</i> :<br/>
	 * <ul>
	 * <li><code>FournisseurArboTraficService.creerRepertoireSurDisque(nouvelle racine);</code></li>
	 * <li><code>FournisseurArboTraficService.selectionnerRootFile(nouvelle racine);</code></li></ul></li>
	 * </ul>
	 * <ul>
	 * <li>ne fait rien si pRoot n'est pas un bon répertoire.</li>
	 * <li>initialise l'attribut rootFile si nécessaire.</li>
	 * <li>ne fait rien si pRoot == rootFile.</li>
	 * <li>passe pRoot à l'attribut rootFile.</li>
	 * <li>enregistre le nouveau rootFile dans les preferences.</li>
	 * </ul>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/methode_Fournisseur.selectionnerRoot.png" 
	 * alt="méthode selectionnerRootFile(pRoot)" border="1" align="center"/>
	 * <br/>
	 *
	 * @param pRoot : File : répertoire racine des trafics 
	 * (Trafic_Histonat).<br/>
	 * 
	 * @throws Exception
	 */
	public static void selectionnerRootFile(
			final File pRoot) 
						throws Exception {
		
		synchronized (FournisseurArboTraficService.class) {
			
			/* ne fait rien si pRoot n'est pas un bon répertoire. */
			if (verifierRepertoireExistant(pRoot)) {
				
				/* initialise l'attribut rootFile si nécessaire.*/
				if (!verifierRepertoireExistant(rootFile)) {
					initialiserRootFile();
				}
				
				/* ne fait rien si pRoot == rootFile. */
				if (!rootFile.equals(pRoot)) {
					
					/* passe pRoot à l'attribut rootFile. */
					rootFile = pRoot;
					
					/* enregistre le nouveau rootFile dans les preferences. */
					enregistrerPreferenceRootFile();
				}
				
			}
			
		} // Fin du bloc synchronized._______________
		
	} // Fin de selectionnerRootFile(...)._________________________________
	

	
	/**
	 * <b>Sélectionne une année</b> pAnneeExploitation comme 
	 * année des trafics (2016, 2017, ...) et l'
	 * <b>enregistre dans les préférences</b>.<br/>
	 * Ne modifie l'ANNEE D'EXPLOITATION des trafics enregistrée 
	 * dans les préférences que si pAnneeExploitation est différent 
	 * de l'année enregistrée.<br/>
	 * Fait de pAnneeExploitation l'<b>année 
	 * d'exploitation courante</b>.<br/>
	 * <ul>
	 * <li>on change d'ANNEE D'EXPLOITATION en faisant <i>avant toute chose</i> :<br/>
	 * <ul>
	 * <li><code>FournisseurArboTraficService.selectionnerAnneeExploitation(nouvelle année);</code></li>
	 * </ul>
	 * </li>
	 * </ul>
	 * <ul>
	 * <li>ne fait rien si pAnneeExploitation n'est pas un 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li>initialise l'attribut anneeExploitation 
	 * si nécessaire.</li>
	 * <li>ne fait rien si le paramètre vaut l'attribut.</li>
	 * <li>passe le paramètre à l'attribut.</li>
	 * <li>enregistre le nouvel attribut dans les preferences.</li>
	 * </ul>
	 *
	 * @param pAnneeExploitation : String : 
	 * année d'exploitation.<br/>
	 * 
	 * @throws Exception
	 */
	public static void selectionnerAnneeExploitation(
			final String pAnneeExploitation) 
										throws Exception {
		synchronized (FournisseurArboTraficService.class) {
			
			if (pAnneeExploitation == null) {
				return;
				
			}
			final String anneeLocale 
				= StringUtils.trim(pAnneeExploitation);
			
			/* ne fait rien si pAnneeExploitation n'est pas 
			 * une bonne année sur 4 chiffres exactement. */
			if (verifierAnneeConvenable(anneeLocale)) {
				
				/* initialise l'attribut anneeExploitation 
				 * si nécessaire. */
				if (!verifierAnneeConvenable(anneeExploitation)) {
					initialiserAnneeExploitation();
				}
				
				/* ne fait rien si le paramètre vaut l'attribut. */
				if (!anneeExploitation.equals(anneeLocale)) {
					
					/* passe le paramètre à l'attribut. */
					anneeExploitation = anneeLocale;
					
					/* enregistre le nouvel attribut dans les preferences. */
					enregistrerPreferenceAnneeExploitation();
				}
				
			}
			
		} // Fin du bloc synchronized._______________
		
	} // Fin de selectionnerAnneeExploitation(...).________________________
	

	
	/**
	 * <b>retourne une encapsulation ArboTrafic</b> 
	 * contenant tous les répertoires d'une arborescence 
	 * des trafics sous la RACINE pRoot pour l'année d'exploitation 
	 * pAnneeExploitation.<br/>
	 * <ul>
	 * <li>Alimente automatiquement tous les attributs 
	 * (repertoireTraficAnnuel, RepertoireFichiersOriginaux, ...).</li>
	 * <li><b>Crée l'arborescence sur disque</b> si elle n'existe pas déjà.</li>
	 * </ul>
	 * - retourne null si pRoot == null.<br/>
	 * - retourne null si pAnneeExploitation est blank.<br/>
	 * - retourne null si pAnneeExploitation ne 
	 * comporte pas exactement 4 digits.<br/>
	 * <br/>
	 *
	 * @param pRoot : File : 
	 * RACINE (abstraite) des trafics.<br/>
	 * @param pAnneeExploitation : String : 
	 * année sur 4 digits (2013,2017, 2022, ...).<br/>
	 * 
	 * @return : ArboTrafic : 
	 * encapsulation contenant tous les répertoires 
	 * de l'arborescence des trafics.<br/>
	 * 
	 * @throws Exception 
	 */
	public static IArboTrafic fournirArboTrafic(
			final File pRoot
				, final String pAnneeExploitation) throws Exception {
		
		synchronized (FournisseurArboTraficService.class) {
			
			/* retourne null si pRoot == null. */
			if (pRoot == null) {
				return null;
			}
			
			/* retourne null si pAnneeExploitation est blank. */
			if (StringUtils.isBlank(pAnneeExploitation)) {
				return null;
			}
			
			/* retourne null si pAnneeExploitation ne 
			 * comporte pas exactement 4 digits. */
			if (!verifierAnneeConvenable(pAnneeExploitation)) {
				return null;
			}
			
			final IArboTrafic arboTrafic = new ArboTrafic();
			
			arboTrafic.setRootFile(pRoot);
			arboTrafic.setAnneeExploitation(pAnneeExploitation);
			
			arboTrafic.setRepertoireTraficAnnuel(
					fournirRepertoireTraficAnnuel(
							pAnneeExploitation));
			arboTrafic.setRepertoireFichiersOriginaux(
					fournirRepertoireFichiersOriginaux(
							pAnneeExploitation));
			arboTrafic.setRepertoireFichiersOriginauxANSI(
					fournirRepertoireFichiersOriginauxANSI(
							pAnneeExploitation));
			arboTrafic.setRepertoireFichiersOriginauxANSIcsv(
					fournirRepertoireFichiersOriginauxANSIcsv(
							pAnneeExploitation));
			arboTrafic.setRepertoireFichiersOriginauxUTF8(
					fournirRepertoireFichiersOriginauxUTF8(
							pAnneeExploitation));
			arboTrafic.setRepertoireFichiersOriginauxUTF8csv(
					fournirRepertoireFichiersOriginauxUTF8csv(
							pAnneeExploitation));
			arboTrafic.setRepertoireFichiersNettoyesUTF8(
					fournirRepertoireFichiersNettoyesUTF8(
							pAnneeExploitation));
			arboTrafic.setRepertoireFichiersNettoyesUTF8csv(
					fournirRepertoireFichiersNettoyesUTF8csv(
							pAnneeExploitation));
			arboTrafic.setRepertoireFichiersReponseLocalisationUTF8(
					fournirRepertoireFichiersReponseLocalisationUTF8(
							pAnneeExploitation));
			arboTrafic.setRepertoireAnnee(
					fournirRepertoireAnnee(
							pAnneeExploitation));
			arboTrafic.setRepertoireDARWIN(
					fournirRepertoireDARWIN(
							pAnneeExploitation));
			arboTrafic.setRepertoireDIRA(
					fournirRepertoireDIRA(
							pAnneeExploitation));
			arboTrafic.setRepertoireDIRCE(
					fournirRepertoireDIRCE(
							pAnneeExploitation));
			arboTrafic.setRepertoireDIRCO(
					fournirRepertoireDIRCO(
							pAnneeExploitation));
			arboTrafic.setRepertoireDIRE(
					fournirRepertoireDIRE(
							pAnneeExploitation));
			arboTrafic.setRepertoireDIRIF(
					fournirRepertoireDIRIF(
							pAnneeExploitation));
			arboTrafic.setRepertoireDIRMC(
					fournirRepertoireDIRMC(
							pAnneeExploitation));
			arboTrafic.setRepertoireDIRMED(
					fournirRepertoireDIRMED(
							pAnneeExploitation));
			arboTrafic.setRepertoireDIRN(
					fournirRepertoireDIRN(
							pAnneeExploitation));
			arboTrafic.setRepertoireDIRNO(
					fournirRepertoireDIRNO(
							pAnneeExploitation));
			arboTrafic.setRepertoireDIRO(
					fournirRepertoireDIRO(
							pAnneeExploitation));
			arboTrafic.setRepertoireDIRSO(
					fournirRepertoireDIRSO(
							pAnneeExploitation));
			arboTrafic.setRepertoireEntree(
					fournirRepertoireEntree(
							pAnneeExploitation));
			arboTrafic.setRepertoireEntreeDarwin(
					fournirRepertoireEntreeDarwin(
							pAnneeExploitation));
			arboTrafic.setRepertoireEntreeDarwinANSI(
					fournirRepertoireEntreeDarwinANSI(
							pAnneeExploitation));
			arboTrafic.setRepertoireEntreeDarwinUTF8(
					fournirRepertoireEntreeDarwinUTF8(
							pAnneeExploitation));
			arboTrafic.setRepertoireEntreeDarwinOriginal(
					fournirRepertoireEntreeDarwinOriginal(
							pAnneeExploitation));
			arboTrafic.setRepertoireEntreeHit(
					fournirRepertoireEntreeHit(
							pAnneeExploitation));
			arboTrafic.setRepertoireEntreeHitANSI(
					fournirRepertoireEntreeHitANSI(
							pAnneeExploitation));
			arboTrafic.setRepertoireEntreeHitANSIAscii(
					fournirRepertoireEntreeHitANSIAscii(
							pAnneeExploitation));
			arboTrafic.setRepertoireEntreeHitANSICsv(
					fournirRepertoireEntreeHitANSICsv(
							pAnneeExploitation));
			arboTrafic.setRepertoireEntreeHitANSIFeorXML(
					fournirRepertoireEntreeHitANSIFeorXML(
							pAnneeExploitation));
			arboTrafic.setRepertoireEntreeHitUTF8(
					fournirRepertoireEntreeHitUTF8(
							pAnneeExploitation));
			arboTrafic.setRepertoireEntreeHitUTF8Ascii(
					fournirRepertoireEntreeHitUTF8Ascii(
							pAnneeExploitation));
			arboTrafic.setRepertoireEntreeHitUTF8Csv(
					fournirRepertoireEntreeHitUTF8Csv(
							pAnneeExploitation));
			arboTrafic.setRepertoireEntreeHitUTF8FeorXML(
					fournirRepertoireEntreeHitUTF8FeorXML(
							pAnneeExploitation));
			arboTrafic.setRepertoireEntreeHitOriginal(
					fournirRepertoireEntreeHitOriginal(
							pAnneeExploitation));
			arboTrafic.setRepertoireSortie(
					fournirRepertoireSortie(
							pAnneeExploitation));
			arboTrafic.setRepertoireSortieHistonatF07(
					fournirRepertoireSortieHistonatF07(
							pAnneeExploitation));
			arboTrafic.setRepertoireSortieHistonatF07Ascii(
					fournirRepertoireSortieHistonatF07Ascii(
							pAnneeExploitation));
			arboTrafic.setRepertoireSortieHistonatF07AsciiANSI(
					fournirRepertoireSortieHistonatF07AsciiANSI(
							pAnneeExploitation));
			arboTrafic.setRepertoireSortieHistonatF07AsciiUTF8(
					fournirRepertoireSortieHistonatF07AsciiUTF8(
							pAnneeExploitation));
			arboTrafic.setRepertoireSortieHistonatF07Csv(
					fournirRepertoireSortieHistonatF07Csv(
							pAnneeExploitation));
			arboTrafic.setRepertoireSortieHistonatF07CsvANSI(
					fournirRepertoireSortieHistonatF07CsvANSI(
							pAnneeExploitation));
			arboTrafic.setRepertoireSortieHistonatF07CsvUTF8(
					fournirRepertoireSortieHistonatF07CsvUTF8(
							pAnneeExploitation));
			arboTrafic.setRepertoireSortieHistonatF07FeorXML(
					fournirRepertoireSortieHistonatF07FeorXML(
							pAnneeExploitation));
			arboTrafic.setRepertoireSortieHistonatF07FeorXMLANSI(
					fournirRepertoireSortieHistonatF07FeorXMLANSI(
							pAnneeExploitation));
			arboTrafic.setRepertoireSortieHistonatF07FeorXMLUTF8(
					fournirRepertoireSortieHistonatF07FeorXMLUTF8(
							pAnneeExploitation));
			arboTrafic.setRepertoireSortieHistonatF08(
					fournirRepertoireSortieHistonatF08(
							pAnneeExploitation));
			arboTrafic.setRepertoireSortieHistonatF08Ascii(
					fournirRepertoireSortieHistonatF08Ascii(
							pAnneeExploitation));
			arboTrafic.setRepertoireSortieHistonatF08AsciiANSI(
					fournirRepertoireSortieHistonatF08AsciiANSI(
							pAnneeExploitation));
			arboTrafic.setRepertoireSortieHistonatF08AsciiUTF8(
					fournirRepertoireSortieHistonatF08AsciiUTF8(
							pAnneeExploitation));
			
			return arboTrafic;
			
		} // Fin du bloc synchronized._______________
		
	} // Fin de fournirArboTrafic(...).____________________________________
	
	
	
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
		
		synchronized (FournisseurArboTraficService.class) {
			
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
	 * <ul>
	 * <li><b>Crée sur le disque et retourne</b> le File 
	 * <b>répertoire</b> (Repertoire vide) pFile 
	 * si il n'existe pas déjà.</li>
	 * <li>Crée le chemin (arborescence) vers le Repertoire à créer 
	 * si il n'existe pas déjà sur le disque.</li>
	 * <li>Retourne pFile (Repertoire vide) 
	 * créé sur disque et donc existant.</li>
	 * <li>Ne peut JAMAIS écraser un Repertoire existant 
	 * puisque cette méthode retourne le Repertoire existant.</li>
	 * </ul>
	 * - retourne null et LOG INFO si pFile est null.<br/>
	 * - retourne pFile si celui-ci est déjà existant sur le disque.<br/>
	 * <br/>
	 *
	 * @param pFile : File : Repertoire à créer sur disque.<br/>
	 * 
	 * @return : File : pFile (répertoire vide) 
	 * créé sur disque et donc existant.<br/>
	 */
	public static File creerRepertoireSurDisque(
			final File pFile) {
		
		/* bloc static synchronized. */
		synchronized (FournisseurArboTraficService.class) {
			
			/* retourne null si le pFile est null. */
			if (pFile == null) {
				
				/* LOG de niveau INFO. */
				loggerInfo(
						CLASSE_FOURNISSEURARBOTRAFIC_SERVICE
							, METHODE_CREER_REPERTOIRE_SUR_DISQUE
								, MESSAGE_FICHIER_NULL);
				
				/* retour de null. */
				return null;
			}
			
			/* retourne pFile si celui-ci est déjà existant sur le disque. */
			if (pFile.exists()) {
				return pFile;
			}
						
			try {
				
				/* Récupération du path de pFile. */
				final Path pathFile = pFile.toPath();
				
				if (pathFile == null) {
					return null;
				}
				
				/* Récupération du parent de pFile. */
				final File repParentFile = pFile.getParentFile();
				
				if (repParentFile == null) {
					return null;
				}
				
				/* Récupération du Path du répertoire parent de pFile. */
				final Path pathRepParentFile = repParentFile.toPath();
				
				/* Crée le chemin (arborescence) du Repertoire à créer 
				 * si il n'existe pas déjà sur le disque. */
				if (!repParentFile.exists()) {
					Files.createDirectories(pathRepParentFile);
				}
				
				/* Création du Repertoire sur disque si il n'existe pas déjà. */
				if (!pFile.exists()) {
					Files.createDirectory(pathFile);
				}
								
			} catch (IOException ioe) {
				
				loggerError(
						CLASSE_FOURNISSEURARBOTRAFIC_SERVICE
							, METHODE_CREER_REPERTOIRE_SUR_DISQUE
								, ioe);
				
				return null;
				
			}
			
			return pFile;
			
		} // Fin du bloc static synchronized.________________________
				
	} // Fin de creerRepertoireSurDisque(...)._____________________________
	 
	
	
	/**
	 * <ul>
	 * <li><b>Crée sur le disque et retourne</b> le File 
	 * <b>fichier</b> (Fichier simple vide) pFile 
	 * si il n'existe pas déjà.</li>
	 * <li>Crée le chemin (arborescence) vers le fichier à créer 
	 * si il n'existe pas déjà sur le disque.</li>
	 * <li>Retourne pFile (fichier simple vide) 
	 * créé sur disque et donc existant.</li>
	 * <li>Ne peut JAMAIS écraser un fichier existant 
	 * puisque cette méthode retourne le fichier existant.</li>
	 * </ul>
	 * - retourne null et LOG INFO si pFile est null.<br/>
	 * - retourne pFile si celui-ci est déjà existant sur le disque.<br/>
	 * <br/>
	 *
	 * @param pFile : File : Fichier à créer sur disque.<br/>
	 * 
	 * @return : File : pFile (fichier simple vide) 
	 * créé sur disque et donc existant.<br/>
	 */
	private static File creerFichierSurDisque(
			final File pFile) {
		
		/* bloc static synchronized. */
		synchronized (FournisseurArboTraficService.class) {
			
			/* retourne null si le pFile est null. */
			if (pFile == null) {
				
				/* LOG de niveau INFO. */
				loggerInfo(
						CLASSE_FOURNISSEURARBOTRAFIC_SERVICE
							, METHODE_CREER_FICHIER_SUR_DISQUE
								, MESSAGE_FICHIER_NULL);
				
				/* retour de null. */
				return null;
			}
			
			/* retourne pFile si celui-ci est déjà existant sur le disque. */
			if (pFile.exists()) {
				return pFile;
			}
						
			try {
				
				/* Récupération du path de pFile. */
				final Path pathFile = pFile.toPath();
				
				if (pathFile == null) {
					return null;
				}
				
				/* Récupération du parent de pFile. */
				final File repParentFile = pFile.getParentFile();
				
				if (repParentFile == null) {
					return null;
				}
				
				/* Récupération du Path du répertoire parent de pFile. */
				final Path pathRepParentFile = repParentFile.toPath();
				
				/* Crée le chemin (arborescence) du fichier à créer 
				 * si il n'existe pas déjà sur le disque. */
				if (!repParentFile.exists()) {
					Files.createDirectories(pathRepParentFile);
				}
				
				/* Création du Fichier sur disque si il n'existe pas déjà. */
				if (!pFile.exists()) {
					Files.createFile(pathFile);
				}
								
			} catch (IOException ioe) {
				
				loggerError(
						CLASSE_FOURNISSEURARBOTRAFIC_SERVICE
							, METHODE_CREER_FICHIER_SUR_DISQUE
								, ioe);
				
				return null;
				
			}
			
			return pFile;
			
		} // Fin du bloc static synchronized.________________________
				
	} // Fin de creerFichierSurDisque(...).________________________________
	

	
	/**
	 * initialise <b>rootFile</b> avec la préference 
	 * stockée dans le fichier properties 
	 * preferencesFournisseurArboTrafic.properties.<br/>
	 * <ul>
	 * <li>récupère la RACINE (Trafic_Histonat) stockée 
	 * dans les préférences.</li>
	 * <li>délègue au GESTIONNAIRE DE PREFERENCES 
	 * PreferencesFournisseurArboTraficManager 
	 * la gestion des préferences.</li>
	 * </ul>
	 *
	 * @throws Exception
	 */
	private static void initialiserRootFile() throws Exception {
		rootFile 
			= PreferencesFournisseurArboTraficManager.getRootFile();
	} // Fin de initialiserRootFile()._____________________________________
	

	
	/**
	 * initialise <b>anneeExploitation</b> avec la préference 
	 * stockée dans le fichier properties 
	 * preferencesFournisseurArboTrafic.properties.<br/>
	 * <ul>
	 * <li>Récupère l'année d'exploitation stockée 
	 * dans les préférences.</li>
	 * <li>délègue au GESTIONNAIRE DE PREFERENCES 
	 * PreferencesFournisseurArboTraficManager 
	 * la gestion des préferences.</li>
	 * </ul>
	 *
	 * @throws Exception
	 */
	private static void initialiserAnneeExploitation() throws Exception {
		anneeExploitation 
			= PreferencesFournisseurArboTraficManager
				.getAnneeExploitation();
	} // Fin de initialiserAnneeExploitation().____________________________
	
	
	
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
		
		synchronized (FournisseurArboTraficService.class) {
			
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
	 * true si pAnnee contient exactement 4 chiffres.<br/>
	 */
	private static Boolean verifierAnneeConvenable(
			final String pAnnee) {
		
		synchronized (FournisseurArboTraficService.class) {
			
			boolean resultat = true;
			
			/* retourne false si pAnnee == null. */
			if (pAnnee == null) {
				return false;
			}
			
			/* MOTIF REGEX 4 chiffres "\\d{4}". */
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
	 * Enregistre dans les préferences un nouveau rootFile.<br/>
	 * <ul>
	 * <li>délègue au GESTIONNAIRE DE PREFERENCES 
	 * PreferencesFournisseurArboTraficManager 
	 * la gestion des préferences.</li>
	 * </ul>
	 *
	 * @throws Exception
	 */
	private static void enregistrerPreferenceRootFile() throws Exception {
		
		synchronized (FournisseurArboTraficService.class) {
			
			PreferencesFournisseurArboTraficManager
				.setRootFile(rootFile);
			
		} // Fin du bloc synchronized._______________
		
	} // Fin de enregistrerPreferenceRootFile().___________________________
	

	
	/**
	 * Enregistre dans les préferences un nouveau anneeExploitation.<br/>
	 * <ul>
	 * <li>délègue au GESTIONNAIRE DE PREFERENCES 
	 * PreferencesFournisseurArboTraficManager 
	 * la gestion des préferences.</li>
	 * </ul>
	 *
	 * @throws Exception
	 */
	private static void enregistrerPreferenceAnneeExploitation() 
			throws Exception {
		
		synchronized (FournisseurArboTraficService.class) {
			
			PreferencesFournisseurArboTraficManager
				.setAnneeExploitation(anneeExploitation);
			
		} // Fin du bloc synchronized._______________
		
	} // Fin de enregistrerPreferenceAnneeExploitation().__________________
	

	
	/**
	 * fournit le nom d'un gestionnaire (DARWIN, DIRA, DIRMED, ...) 
	 * à partir d'un suffixe extrait d'un nom de fichier 
	 * (DARWIN2016.csv, HITDIRA2016.txt, HITDIRMED2016.txt, ...).<br/>
	 * <br/>
	 * - retourne null si pSuffixe == null.<br/>
	 * - retourne null si pSuffixe ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pSuffixe : String : ("A" pour DIRA, "MED" pour DIRMED, ..).<br/>
	 * 
	 * @return : String : le nom du gestionnaire.<br/>
	 */
	private static String fournirNomGestionnaire(
			final String pSuffixe) {
		
		/* retourne null si pSuffixe == null. */
		if (pSuffixe == null) {
			return null;
		}
		
		String nomGestionnaire = null;
		
		switch (pSuffixe) {
		
		case "DARWIN" :
			nomGestionnaire = "DARWIN";
			break;
			
		case "A" :
			nomGestionnaire = "DIRA";
			break;
			
		case "CE" :
			nomGestionnaire = "DIRCE";
			break;
			
		case "CO" :
			nomGestionnaire = "DIRCO";
			break;
			
		case "E" :
			nomGestionnaire = "DIRE";
			break;
			
		case "IF" :
			nomGestionnaire = "DIRIF";
			break;
			
		case "MC" :
			nomGestionnaire = "DIRMC";
			break;
			
		case "MED" :
			nomGestionnaire = "DIRMED";
			break;
			
		case "N" :
			nomGestionnaire = "DIRN";
			break;
			
		case "NO" :
			nomGestionnaire = "DIRNO";
			break;
			
		case "O" :
			nomGestionnaire = "DIRO";
			break;
			
		case "SO" :
			nomGestionnaire = "DIRSO";
			break;
		
		/* retourne null si pSuffixe ne convient pas. */
		default:
			break;
		
		}
		
		return nomGestionnaire;
		
	} // Fin de fournirNomGestionnaire(...)._______________________________
	

	
	/**
	 * Retourne le chemin vers le répertoire d'un gestionnaire 
	 * défini par un suffixe pSuffixe déterminé dans 
	 * <code>fournirGestionnaire(String pSuffixe)</code>
	 * ("A" pour DIRA, "CE" pour DIRCE, "DARWIN" pour DARWIN, ...) dans 
	 * l'arborescence d'une année pAnnee<br/>
	 * <ul>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>Par exemple :<br/>
	 * <code>fournirCheminRepertoireGestionnaire("A", "2016")</code> 
	 * retourne le chemin vers le répertoire DIRA de l'année 2016.</li>
	 * </ul>
	 * - retourne null si pSuffixe == null.<br/>
	 * - retourne null si pAnnee n'est pas une bonne année 
	 * sur 4 chiffres exactement.<br/>
	 * - retourne null si pSuffixe ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pSuffixe : String : 
	 * ("A" pour DIRA, "MED" pour DIRMED, ..).<br/>
	 * @param pAnnee : String : année d'exploitation sur 4 digits.<br/>
	 * 
	 * @return String : chemin vers le répertoire annuel 
	 * du gestionnaire.<br/>
	 * 
	 * @throws Exception
	 */
	private static String fournirCheminRepertoireGestionnaire(
			final String pSuffixe
				, final String pAnnee) throws Exception {
		
		synchronized (FournisseurArboTraficService.class) {
						
			/* retourne null si pSuffixe == null. */
			if (pSuffixe == null) {
				return null;
			}
			
			String resultat =  null;
			
			/* retourne null si pAnnee n'est pas 
			 * une bonne année sur 4 chiffres exactement. */
			if (verifierAnneeConvenable(pAnnee)) {
							
				final String cheminAnnee 
					= fournirCheminRepertoireAnnee(pAnnee);
				
				final String cheminGestionnaire 
					= fournirNomGestionnaire(pSuffixe);
				
				/* retourne null si pSuffixe ne convient pas. */
				if (cheminGestionnaire != null) {
					resultat = cheminAnnee + cheminGestionnaire 
							+ SEP_REP;								
				}
							
			}
			
			return resultat;

		} // Fin du bloc synchronized._______________
		
	} // Fin de fournirCheminRepertoireGestionnaire(...).__________________
	
	
	
	/**
	 * Getter de la <b>RACINE (Trafic_Histonat) en cours dans les préférences</b>
	 * des fichiers annuels de Trafic.<br/>
	 * <ul>
	 * <li>retourne un <b>SINGLETON</b>.</li>
	 * <li>récupère la RACINE (Trafic_Histonat) stockée 
	 * dans les préférences si nécessaire.</li>
	 * <li>initialise la racine si la racine stockée 
	 * dans les préférences n'est pas un répertoire existant.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Travail/Trafic_Histonat/").<br/>
	 * <br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/racine_Trafic_Histonat.png" 
	 * alt="Racine des Trafics Trafic_Histonat" border="1" align="center" />
	 * <br/>
	 * <br/>
	 *
	 * @return rootFile : File : rootFile.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File getRootFile() throws Exception {
		
		synchronized (FournisseurArboTraficService.class) {
			
			if (!verifierRepertoireExistant(rootFile)) {
				
				/* récupère la RACINE (Trafic_Histonat) stockée 
				 * dans les préférences si nécessaire. */
				initialiserRootFile();
			}
			
			return rootFile;
			
		} // Fin du bloc synchronized._______________
		
	} // Fin de getRootFile()._____________________________________________

		
	
	/**
	 * Getter de l'<b>année d'exploitation en cours 
	 * dans les préférences</b> des fichiers annuels de trafic 
	 * (2013, 2017, ...).<br/>
	 * <ul>
	 * <li>retourne un <b>SINGLETON</b>.</li>
	 * <li>Récupère l'année d'exploitation stockée 
	 * dans les préférences.</li>
	 * <li>ne fait rien si pAnneeExploitation n'est pas un 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * <br/>
	 *
	 * @return anneeExploitation : String : 
	 * année d'exploitation sur 4 digits.<br/>
	 * 
	 * @throws Exception 
	 */
	public static String getAnneeExploitation() throws Exception {
		
		synchronized (FournisseurArboTraficService.class) {
			
			/* ne fait rien si pAnneeExploitation n'est pas 
			 * une bonne année sur 4 chiffres exactement. */
			if (!verifierAnneeConvenable(anneeExploitation)) {
				
				/* Récupère l'année d'exploitation stockée 
				 * dans les préférences.*/
				initialiserAnneeExploitation();
			}
			
			return anneeExploitation;
			
		} // Fin du bloc synchronized._______________
		
	} // Fin de getAnneeExploitation().____________________________________


	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire 
	 * des fichiers de trafic annuels  (Trafic_aaaa)
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas un 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/Trafic_annuel.png" 
	 * alt="Trafic annuel" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * des fichiers de trafic annuels
	 * pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminTraficAnnuel(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return getRootFile().getAbsolutePath() 
					+ SEP_REP + "Trafic_" 
						+ pAnnee + SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
		
	} // Fin de fournirCheminTraficAnnuel(...).____________________________
	

	/**
	 * <ul>
	 * <li><b>retourne le répertoire 
	 * des Trafics annuels</b> (Trafic_aaaa)
	 * sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/")
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/Trafic_annuel.png" 
	 * alt="Trafic annuel" border="1" align="center" />
	 * <br/>
	 *
	 * @return : File : répertoire 
	 * des trafics annuels pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireTraficAnnuel() throws Exception {
		return fournirRepertoireTraficAnnuel(getAnneeExploitation());
	} // Fin de fournirRepertoireTraficAnnuel().___________________________
	
	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire 
	 * des Trafics annuels</b> (Trafic_aaaa)
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/")
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/Trafic_annuel.png" 
	 * alt="Trafic annuel" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * des trafics annuels
	 * pour l'année pAnnee.<br/>
	 * @throws Exception 
	 */
	public static File fournirRepertoireTraficAnnuel(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminTraficAnnuel(pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireTraficAnnuel(...).________________________
	

	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire 
	 * des fichiers originaux (HIT et DARWIN) 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 0_Fichiers_originaux/" pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/Fichiers_originaux.png" 
	 * alt="fichiers originaux" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * des fichiers originaux (HIT et DARWIN) 
	 * pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminFichiersOriginaux(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminTraficAnnuel(pAnnee) 
					+ "0_Fichiers_originaux" + SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
		
	} // Fin de fournirCheminFichiersOriginaux(...)._______________________
	 

	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b> 
	 * des fichiers originaux (HIT et DARWIN) 
	 * sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 0_Fichiers_originaux/") pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/Fichiers_originaux.png" 
	 * alt="fichiers originaux" border="1" align="center" />
	 * <br/>
	 *
	 * @return : File : répertoire 
	 * des fichiers originaux (HIT et DARWIN) pour l'année 
	 * en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireFichiersOriginaux() 
											throws Exception {
		return fournirRepertoireFichiersOriginaux(getAnneeExploitation());
	} // Fin de fournirRepertoireFichiersOriginaux().______________________
	
	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b> 
	 * des fichiers originaux (HIT et DARWIN) 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 0_Fichiers_originaux/") pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/Fichiers_originaux.png" 
	 * alt="fichiers originaux" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * des fichiers originaux (HIT et DARWIN) pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireFichiersOriginaux(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminFichiersOriginaux(pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireFichiersOriginaux(
	 // String pAnnee).____________________________________________________
	

	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire 
	 * des fichiers originaux (HIT et DARWIN) 
	 * encodés en ANSI sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 1_Fichiers_originaux_encodés_en_ANSI/" pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/Fichiers_originaux_Ansi.png" 
	 * alt="fichiers originaux ANSI" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * des fichiers originaux (HIT et DARWIN) 
	 * encodés en ANSI pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminFichiersOriginauxANSI(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminTraficAnnuel(pAnnee) 
					+ "1_Fichiers_originaux_encodés_en_ANSI" + SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
		
	} // Fin de fournirCheminFichiersOriginauxANSI(...).___________________
	

	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b> 
	 * des fichiers originaux (HIT et DARWIN) 
	 * encodés en ANSI sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 1_Fichiers_originaux_encodés_en_ANSI/") pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/Fichiers_originaux_Ansi.png" 
	 * alt="fichiers originaux ANSI" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * des fichiers originaux (HIT et DARWIN) 
	 * encodés en ANSI pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireFichiersOriginauxANSI() 
												throws Exception {
		return fournirRepertoireFichiersOriginauxANSI(
				getAnneeExploitation());
	} // Fin de fournirRepertoireFichiersOriginauxANSI().__________________
	
	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b> 
	 * des fichiers originaux (HIT et DARWIN) 
	 * encodés en ANSI sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 1_Fichiers_originaux_encodés_en_ANSI/") pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/Fichiers_originaux_Ansi.png" 
	 * alt="fichiers originaux ANSI" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee  : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * des fichiers originaux (HIT et DARWIN) 
	 * encodés en ANSI pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireFichiersOriginauxANSI(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminFichiersOriginauxANSI(pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireFichiersOriginauxANSI(...)._______________
	 
	
	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire 
	 * des fichiers originaux (HIT et DARWIN) <b>transformés en csv</b>
	 * encodés en ANSI sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 1_Fichiers_originaux_encodés_en_ANSI/csv/" pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/Fichiers_originaux_Ansi_csv.png" 
	 * alt="fichiers originaux ANSI en csv" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * des fichiers originaux (HIT et DARWIN) transformés en csv
	 * encodés en ANSI pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminFichiersOriginauxANSIcsv(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminFichiersOriginauxANSI(pAnnee) 
					+ "csv" + SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
		
	} // Fin de fournirCheminFichiersOriginauxANSIcsv(...).________________
	

	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b> 
	 * des fichiers originaux (HIT et DARWIN) <b>transformés en csv</b> 
	 * encodés en ANSI sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 1_Fichiers_originaux_encodés_en_ANSI/csv/") pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/Fichiers_originaux_Ansi_csv.png" 
	 * alt="fichiers originaux ANSI en csv" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * des fichiers originaux (HIT et DARWIN) transformés en csv 
	 * encodés en ANSI pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */

	public static File fournirRepertoireFichiersOriginauxANSIcsv() 
													throws Exception {
		return fournirRepertoireFichiersOriginauxANSIcsv(
				getAnneeExploitation());
	} // Fin de fournirRepertoireFichiersOriginauxANSIcsv()._______________
	

	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b> 
	 * des fichiers originaux (HIT et DARWIN) <b>transformés en csv</b> 
	 * encodés en ANSI sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 1_Fichiers_originaux_encodés_en_ANSI/csv/") pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/Fichiers_originaux_Ansi_csv.png" 
	 * alt="fichiers originaux ANSI en csv" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee  : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * des fichiers originaux (HIT et DARWIN) transformés en csv 
	 * encodés en ANSI pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireFichiersOriginauxANSIcsv(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminFichiersOriginauxANSIcsv(pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireFichiersOriginauxANSIcsv(...).____________
	 
	
	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * des fichiers originaux (HIT et DARWIN) 
	 * encodés en UTF-8 sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 1_Fichiers_originaux_encodés_en_UTF8/" pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/Fichiers_originaux_Utf8.png" 
	 * alt="fichiers originaux UTF8" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * des fichiers originaux (HIT et DARWIN) 
	 * encodés en UTF8 pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminFichiersOriginauxUTF8(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminTraficAnnuel(pAnnee) 
					+ "1_Fichiers_originaux_encodés_en_UTF8" + SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
		
	} // Fin de fournirCheminFichiersOriginauxUTF8(...).___________________


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * des fichiers originaux (HIT et DARWIN) 
	 * encodés en UTF-8 sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 1_Fichiers_originaux_encodés_en_UTF8/") pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/Fichiers_originaux_Utf8.png" 
	 * alt="fichiers originaux UTF8" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * des fichiers originaux (HIT et DARWIN) 
	 * encodés en UTF8 pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireFichiersOriginauxUTF8() 
													throws Exception {
		return fournirRepertoireFichiersOriginauxUTF8(
				getAnneeExploitation());
	} // Fin de fournirRepertoireFichiersOriginauxUTF8().__________________
	
	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * des fichiers originaux (HIT et DARWIN) 
	 * encodés en UTF-8 sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 1_Fichiers_originaux_encodés_en_UTF8/") pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/Fichiers_originaux_Utf8.png" 
	 * alt="fichiers originaux UTF8" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee  : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * des fichiers originaux (HIT et DARWIN) 
	 * encodés en UTF8 pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireFichiersOriginauxUTF8(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminFichiersOriginauxUTF8(pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireFichiersOriginauxUTF8(...)._______________

	

	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * des fichiers originaux (HIT et DARWIN) <b>transformés en csv</b> 
	 * encodés en UTF-8 sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 1_Fichiers_originaux_encodés_en_UTF8/csv/" pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/Fichiers_originaux_Utf8_csv.png" 
	 * alt="fichiers originaux UTF8 en csv" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * des fichiers originaux (HIT et DARWIN) transformés en csv 
	 * encodés en UTF8 pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminFichiersOriginauxUTF8csv(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminFichiersOriginauxUTF8(pAnnee) 
					+ "csv" + SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
		
	} // Fin de fournirCheminFichiersOriginauxUTF8csv(...).________________


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * des fichiers originaux (HIT et DARWIN) <b>transformés en csv</b> 
	 * encodés en UTF-8 sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 1_Fichiers_originaux_encodés_en_UTF8/csv/") pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/Fichiers_originaux_Utf8_csv.png" 
	 * alt="fichiers originaux UTF8 en csv" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * des fichiers originaux (HIT et DARWIN) transformés en csv 
	 * encodés en UTF8 pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireFichiersOriginauxUTF8csv() 
													throws Exception {
		return fournirRepertoireFichiersOriginauxUTF8csv(
				getAnneeExploitation());
	} // Fin de fournirRepertoireFichiersOriginauxUTF8csv()._______________
	
	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * des fichiers originaux (HIT et DARWIN) <b>transformés en csv</b> 
	 * encodés en UTF-8 sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 1_Fichiers_originaux_encodés_en_UTF8/csv/") pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/Fichiers_originaux_Utf8_csv.png" 
	 * alt="fichiers originaux UTF8 en csv" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee  : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * des fichiers originaux (HIT et DARWIN) transformés en csv 
	 * encodés en UTF8 pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireFichiersOriginauxUTF8csv(
			final String pAnnee) throws Exception {
				
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminFichiersOriginauxUTF8csv(pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireFichiersOriginauxUTF8csv(...).____________
	

	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * des fichiers HIT nettoyés (sens confondus)
	 * encodés en UTF-8 sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2_Fichiers_nettoyes_sens_confondus_UTF8_a_localiser_dans_Isidor/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/Fichiers_Nettoyes_Utf8.png" 
	 * alt="fichiers nettoyés UTF8" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * des fichiers HIT nettoyés (sens confondus) 
	 * encodés en UTF8 pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminFichiersNettoyesUTF8(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminTraficAnnuel(pAnnee) 
					+ "2_Fichiers_nettoyes_sens_confondus_UTF8_a_localiser_dans_Isidor" 
					+ SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
		
	} // Fin de fournirCheminFichiersNettoyesUTF8(...).____________________
	

	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * des fichiers HIT nettoyés (sens confondus)
	 * encodés en UTF-8 sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2_Fichiers_nettoyes_sens_confondus_UTF8_a_localiser_dans_Isidor/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/Fichiers_Nettoyes_Utf8.png" 
	 * alt="fichiers nettoyés UTF8" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * des fichiers HIT nettoyés (sens confondus) 
	 * encodés en UTF8 pour l'année en cours 
	 * dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireFichiersNettoyesUTF8() 
												throws Exception {
		return fournirRepertoireFichiersNettoyesUTF8(
				getAnneeExploitation());
	} // Fin de fournirRepertoireFichiersNettoyesUTF8().___________________
	
	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * des fichiers HIT nettoyés (sens confondus)
	 * encodés en UTF-8 sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2_Fichiers_nettoyes_sens_confondus_UTF8_a_localiser_dans_Isidor/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/Fichiers_Nettoyes_Utf8.png" 
	 * alt="fichiers nettoyés UTF8" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * des fichiers HIT nettoyés (sens confondus) 
	 * encodés en UTF8 pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireFichiersNettoyesUTF8(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminFichiersNettoyesUTF8(pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireFichiersNettoyesUTF8(...).________________
	

	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * des fichiers HIT nettoyés (sens confondus) <b>transformés en csv</b> 
	 * encodés en UTF-8 sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2_Fichiers_nettoyes_sens_confondus_UTF8_a_localiser_dans_Isidor/csv/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/Fichiers_Nettoyes_UTF8csv.png" 
	 * alt="fichiers nettoyés UTF8 csv" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * des fichiers HIT nettoyés (sens confondus) transformés en csv 
	 * encodés en UTF8 pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminFichiersNettoyesUTF8csv(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminFichiersNettoyesUTF8(pAnnee) 
					+ "csv" + SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
		
	} // Fin de fournirCheminFichiersNettoyesUTF8csv(...)._________________
	

	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * des fichiers HIT nettoyés (sens confondus) <b>transformés en csv</b> 
	 * encodés en UTF-8 sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2_Fichiers_nettoyes_sens_confondus_UTF8_a_localiser_dans_Isidor/csv/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/Fichiers_Nettoyes_UTF8csv.png" 
	 * alt="fichiers nettoyés UTF8 csv" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * des fichiers HIT nettoyés (sens confondus) transformés en csv
	 * encodés en UTF8 pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireFichiersNettoyesUTF8csv() 
													throws Exception {
		return fournirRepertoireFichiersNettoyesUTF8csv(
				getAnneeExploitation());
	} // Fin de fournirRepertoireFichiersNettoyesUTF8csv().________________
	
	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * des fichiers HIT nettoyés (sens confondus) <b>transformés en csv</b> 
	 * encodés en UTF-8 sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2_Fichiers_nettoyes_sens_confondus_UTF8_a_localiser_dans_Isidor/csv/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/Fichiers_Nettoyes_UTF8csv.png" 
	 * alt="fichiers nettoyés UTF8 csv" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * des fichiers HIT nettoyés (sens confondus) transformés en csv
	 * encodés en UTF8 pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireFichiersNettoyesUTF8csv(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminFichiersNettoyesUTF8csv(pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireFichiersNettoyesUTF8csv(...)._____________
	

	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * des fichiers de réponse d'ISIDOR pour la <b>localisation</b>
	 * encodés en UTF-8 sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 3_Fichiers_reponse_localisation_Isidor_UTF8/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/Fichiers_ReponseLocalisation_Utf8.png" 
	 * alt="fichiers réponse localisation ISIDOR" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * des fichiers de réponse d'ISIDOR pour la localisation 
	 * encodés en UTF8 pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminFichiersReponseLocalisationUTF8(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminTraficAnnuel(pAnnee) 
					+ "3_Fichiers_reponse_localisation_Isidor_UTF8" 
						+ SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
		
	} // Fin de fournirCheminFichiersReponseLocalisationUTF8(...)._________
	

	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * des fichiers de réponse d'ISIDOR pour la <b>localisation</b>
	 * encodés en UTF-8 sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 3_Fichiers_reponse_localisation_Isidor_UTF8/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/Fichiers_ReponseLocalisation_Utf8.png" 
	 * alt="fichiers réponse localisation ISIDOR" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * des fichiers de réponse d'ISIDOR pour la localisation 
	 * encodés en UTF8 pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireFichiersReponseLocalisationUTF8() 
														throws Exception {
		return fournirRepertoireFichiersReponseLocalisationUTF8(
				getAnneeExploitation());
	} // Fin de fournirRepertoireFichiersReponseLocalisationUTF8().________
	
	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * des fichiers de réponse d'ISIDOR pour la <b>localisation</b>
	 * encodés en UTF-8 sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 3_Fichiers_reponse_localisation_Isidor_UTF8/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/Fichiers_ReponseLocalisation_Utf8.png" 
	 * alt="fichiers réponse localisation ISIDOR" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * des fichiers de réponse d'ISIDOR pour la localisation 
	 * encodés en UTF8 pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireFichiersReponseLocalisationUTF8(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminFichiersReponseLocalisationUTF8(pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireFichiersReponseLocalisationUTF8(...)._____

	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>annee</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_annee.png" 
	 * alt="répertoire annee" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "annee" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireAnnee(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminTraficAnnuel(pAnnee) 
					+ SEP_REP + pAnnee + SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
		
	} // Fin de fournirCheminRepertoireAnnee(...)._________________________
	

	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>annee</b> sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_annee.png" 
	 * alt="répertoire annee" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "annee" pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireAnnee() throws Exception {
		return fournirRepertoireAnnee(getAnneeExploitation());
	} // Fin de fournirRepertoireAnnee().__________________________________
	
	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>annee</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_annee.png" 
	 * alt="répertoire annee" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "annee" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireAnnee(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireAnnee(pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireAnnee(...)._______________________________
	

		
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>DARWIN</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/DARWIN/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_DARWIN.png" 
	 * alt="répertoire DARWIN" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "DARWIN" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireDARWIN(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireGestionnaire("DARWIN", pAnnee);
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;

	} // Fin de fournirCheminRepertoireDARWIN(...).________________________
	

	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>DARWIN</b> sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/DARWIN/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_DARWIN.png" 
	 * alt="répertoire DARWIN" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "DARWIN" pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireDARWIN() throws Exception {
		return fournirRepertoireDARWIN(getAnneeExploitation());
	} // Fin de fournirRepertoireDARWIN()._________________________________
	
	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>DARWIN</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/DARWIN/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_DARWIN.png" 
	 * alt="répertoire DARWIN" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "DARWIN" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireDARWIN(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireDARWIN(pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireDARWIN(...).______________________________
	
	
	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>DIRA</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/DIRA/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_DIRA.png" 
	 * alt="répertoire DIRA" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "DIRA" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireDIRA(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireGestionnaire("A", pAnnee);
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireDIRA(...).__________________________
	

	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>DIRA</b> sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/DIRA/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_DIRA.png" 
	 * alt="répertoire DIRA" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "DIRA" pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireDIRA() throws Exception {
		return fournirRepertoireDIRA(getAnneeExploitation());
	} // Fin de fournirRepertoireDIRA().___________________________________
	

	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>DIRA</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/DIRA/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_DIRA.png" 
	 * alt="répertoire DIRA" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "DIRA" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireDIRA(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireDIRA(pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireDIRA(...).________________________________
	

	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>DIRCE</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/DIRCE/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_DIRCE.png" 
	 * alt="répertoire DIRCE" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "DIRCE" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireDIRCE(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireGestionnaire("CE", pAnnee);
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireDIRCE(...)._________________________
	

	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>DIRCE</b> sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/DIRCE/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_DIRCE.png" 
	 * alt="répertoire DIRCE" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "DIRCE" pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireDIRCE() throws Exception {
		return fournirRepertoireDIRCE(getAnneeExploitation());
	} // Fin de fournirRepertoireDIRCE().__________________________________
	
	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>DIRCE</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/DIRCE/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_DIRCE.png" 
	 * alt="répertoire DIRCE" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "DIRCE" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireDIRCE(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireDIRCE(pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireDIRCE(...)._______________________________

	
		
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>DIRCO</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/DIRCO/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_DIRCO.png" 
	 * alt="répertoire DIRCO" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "DIRCO" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireDIRCO(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireGestionnaire("CO", pAnnee);
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireDIRCO(...)._________________________
	

	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>DIRCO</b> sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/DIRCO/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_DIRCO.png" 
	 * alt="répertoire DIRCO" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "DIRCO" pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireDIRCO() throws Exception {
		return fournirRepertoireDIRCO(getAnneeExploitation());
	} // Fin de fournirRepertoireDIRCO().__________________________________

	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>DIRCO</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/DIRCO/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_DIRCO.png" 
	 * alt="répertoire DIRCO" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "DIRCO" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireDIRCO(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireDIRCO(pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireDIRCO(...)._______________________________


	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>DIRE</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/DIRE/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_DIRE.png" 
	 * alt="répertoire DIRE" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "DIRE" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireDIRE(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireGestionnaire("E", pAnnee);
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireDIRE(...).__________________________


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>DIRE</b> sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/DIRE/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_DIRE.png" 
	 * alt="répertoire DIRE" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "DIRE" pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireDIRE() throws Exception {
		return fournirRepertoireDIRE(getAnneeExploitation());
	} // Fin de fournirRepertoireDIRE().___________________________________

	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>DIRE</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/DIRE/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_DIRE.png" 
	 * alt="répertoire DIRE" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "DIRE" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireDIRE(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireDIRE(pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireDIRE(...).________________________________

	
	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>DIRIF</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/DIRIF/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_DIRIF.png" 
	 * alt="répertoire DIRIF" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "DIRIF" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireDIRIF(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireGestionnaire("IF", pAnnee);
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireDIRIF(...)._________________________


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>DIRIF</b> sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/DIRIF/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_DIRIF.png" 
	 * alt="répertoire DIRIF" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "DIRIF" pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireDIRIF() throws Exception {
		return fournirRepertoireDIRIF(getAnneeExploitation());
	} // Fin de fournirRepertoireDIRIF().__________________________________

	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>DIRIF</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/DIRIF/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_DIRIF.png" 
	 * alt="répertoire DIRIF" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "DIRIF" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireDIRIF(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireDIRIF(pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireDIRIF(...)._______________________________


	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>DIRMC</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/DIRMC/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_DIRMC.png" 
	 * alt="répertoire DIRMC" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "DIRMC" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireDIRMC(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireGestionnaire("MC", pAnnee);
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireDIRMC(...)._________________________


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>DIRMC</b> sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/DIRMC/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_DIRMC.png" 
	 * alt="répertoire DIRMC" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "DIRMC" pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireDIRMC() throws Exception {
		return fournirRepertoireDIRMC(getAnneeExploitation());
	} // Fin de fournirRepertoireDIRMC().__________________________________

	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>DIRMC</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/DIRMC/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_DIRMC.png" 
	 * alt="répertoire DIRMC" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "DIRMC" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireDIRMC(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireDIRMC(pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireDIRMC(...)._______________________________


	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>DIRMED</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/DIRMED/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_DIRMED.png" 
	 * alt="répertoire DIRMED" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "DIRMED" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireDIRMED(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireGestionnaire("MED", pAnnee);
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireDIRMED(...).________________________


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>DIRMED</b> sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/DIRMED/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_DIRMED.png" 
	 * alt="répertoire DIRMED" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "DIRMED" pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireDIRMED() throws Exception {
		return fournirRepertoireDIRMED(getAnneeExploitation());
	} // Fin de fournirRepertoireDIRMED()._________________________________

	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>DIRMED</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/DIRMED/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_DIRMED.png" 
	 * alt="répertoire DIRMED" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "DIRMED" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireDIRMED(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireDIRMED(pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireDIRMED(...).______________________________

	
	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>DIRN</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/DIRN/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_DIRN.png" 
	 * alt="répertoire DIRN" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "DIRN" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireDIRN(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireGestionnaire("N", pAnnee);
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireDIRN(...).__________________________


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>DIRN</b> sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/DIRN/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_DIRN.png" 
	 * alt="répertoire DIRN" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "DIRN" pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireDIRN() throws Exception {
		return fournirRepertoireDIRN(getAnneeExploitation());
	} // Fin de fournirRepertoireDIRN().___________________________________

	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>DIRN</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/DIRN/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_DIRN.png" 
	 * alt="répertoire DIRN" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "DIRN" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireDIRN(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireDIRN(pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireDIRN(...).________________________________


	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>DIRNO</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/DIRNO/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_DIRNO.png" 
	 * alt="répertoire DIRNO" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "DIRNO" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireDIRNO(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireGestionnaire("NO", pAnnee);
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireDIRNO(...)._________________________


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>DIRNO</b> sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/DIRNO/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_DIRNO.png" 
	 * alt="répertoire DIRNO" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "DIRNO" pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireDIRNO() throws Exception {
		return fournirRepertoireDIRNO(getAnneeExploitation());
	} // Fin de fournirRepertoireDIRNO().__________________________________


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>DIRNO</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/DIRNO/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_DIRNO.png" 
	 * alt="répertoire DIRNO" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "DIRNO" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireDIRNO(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireDIRNO(pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireDIRNO(...)._______________________________

	
	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>DIRO</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/DIRO/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_DIRO.png" 
	 * alt="répertoire DIRO" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "DIRO" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireDIRO(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireGestionnaire("O", pAnnee);
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireDIRO(...).__________________________


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>DIRO</b> sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/DIRO/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_DIRO.png" 
	 * alt="répertoire DIRO" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "DIRO" pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireDIRO() throws Exception {
		return fournirRepertoireDIRO(getAnneeExploitation());
	} // Fin de fournirRepertoireDIRO().___________________________________

	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>DIRO</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/DIRO/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_DIRO.png" 
	 * alt="répertoire DIRO" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "DIRO" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireDIRO(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireDIRO(pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireDIRO(...).________________________________


	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>DIRSO</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/DIRSO/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_DIRSO.png" 
	 * alt="répertoire DIRSO" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "DIRSO" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireDIRSO(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireGestionnaire("SO", pAnnee);
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireDIRSO(...)._________________________


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>DIRSO</b> sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/DIRSO/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_DIRSO.png" 
	 * alt="répertoire DIRSO" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "DIRSO" pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireDIRSO() throws Exception {
		return fournirRepertoireDIRSO(getAnneeExploitation());
	} // Fin de fournirRepertoireDIRSO().__________________________________
	
	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>DIRSO</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/DIRSO/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_DIRSO.png" 
	 * alt="répertoire DIRSO" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "DIRSO" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireDIRSO(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireDIRSO(pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireDIRSO(...)._______________________________

		
	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>Entree/</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_Entree.png" 
	 * alt="répertoire Entree" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "Entree" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireEntree(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireAnnee(pAnnee) + "Entree" 
			+ SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireEntree(...).________________________


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Entree</b> sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_Entree.png" 
	 * alt="répertoire Entree" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "Entree" pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireEntree() throws Exception {
		return fournirRepertoireEntree(getAnneeExploitation());
	} // Fin de fournirRepertoireEntree()._________________________________
	

	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Entree</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_Entree.png" 
	 * alt="répertoire Entree" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "Entree" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireEntree(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireEntree(pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireEntree(...).______________________________


	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>Entree/Darwin/</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Darwin/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeDarwin.png" 
	 * alt="répertoire Entree Darwin" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "Entree/Darwin" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireEntreeDarwin(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireEntree(pAnnee) + "Darwin" 
			+ SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireEntreeDarwin(...).__________________


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Entree/Darwin</b> sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Darwin/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeDarwin.png" 
	 * alt="répertoire Entree Darwin" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "Entree/Darwin" pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireEntreeDarwin() throws Exception {
		return fournirRepertoireEntreeDarwin(getAnneeExploitation());
	} // Fin de fournirRepertoireEntreeDarwin().___________________________
	
	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Entree/Darwin</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Darwin/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeDarwin.png" 
	 * alt="répertoire Entree Darwin" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "Entree/Darwin" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireEntreeDarwin(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireEntreeDarwin(pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireEntreeDarwin(...).________________________


	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>Entree/Darwin/Darwin encodé en ANSI/</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Darwin/Darwin encodé en ANSI/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeDarwinANSI.png" 
	 * alt="répertoire Entree Darwin ANSI" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "Entree/Darwin/Darwin encodé en ANSI/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireEntreeDarwinANSI(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireEntreeDarwin(pAnnee) 
					+ "Darwin encodé en ANSI" 
					+ SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireEntreeDarwinANSI(...).______________


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Entree/Darwin/Darwin encodé en ANSI/</b> sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Darwin/Darwin encodé en ANSI/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeDarwinANSI.png" 
	 * alt="répertoire Entree Darwin ANSI" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "Entree/Darwin/Darwin encodé en ANSI/" pour l'année 
	 * en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireEntreeDarwinANSI() 
			throws Exception {
		return 	fournirRepertoireEntreeDarwinANSI(getAnneeExploitation());	
	} // Fin de fournirRepertoireEntreeDarwinANSI(...).____________________


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Entree/Darwin/Darwin encodé en ANSI/</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Darwin/Darwin encodé en ANSI/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeDarwinANSI.png" 
	 * alt="répertoire Entree Darwin ANSI" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "Entree/Darwin/Darwin encodé en ANSI/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireEntreeDarwinANSI(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(
						fournirCheminRepertoireEntreeDarwinANSI(pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireEntreeDarwinANSI(...).____________________

	
	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>Entree/Darwin/Darwin encodé en UTF8/</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Darwin/Darwin encodé en UTF8/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeDarwinUTF8.png" 
	 * alt="répertoire Entree Darwin UTF8" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "Entree/Darwin/Darwin encodé en UTF8/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireEntreeDarwinUTF8(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireEntreeDarwin(pAnnee) 
					+ "Darwin encodé en UTF8" 
					+ SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireEntreeDarwinUTF8(...).______________


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Entree/Darwin/Darwin encodé en UTF8/</b> sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Darwin/Darwin encodé en UTF8/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeDarwinUTF8.png" 
	 * alt="répertoire Entree Darwin UTF8" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "Entree/Darwin/Darwin encodé en UTF8/" pour l'année 
	 * en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireEntreeDarwinUTF8() 
			throws Exception {
		return 	fournirRepertoireEntreeDarwinUTF8(getAnneeExploitation());	
	} // Fin de fournirRepertoireEntreeDarwinUTF8(...).____________________


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Entree/Darwin/Darwin encodé en UTF8/</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Darwin/Darwin encodé en UTF8/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeDarwinUTF8.png" 
	 * alt="répertoire Entree Darwin UTF8" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "Entree/Darwin/Darwin encodé en UTF8/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireEntreeDarwinUTF8(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(
						fournirCheminRepertoireEntreeDarwinUTF8(pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireEntreeDarwinUTF8(...).____________________

	
	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>Entree/Darwin/Darwin original/</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Darwin/Darwin original/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeDarwinOriginal.png" 
	 * alt="répertoire Entree Darwin Original" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "Entree/Darwin/Darwin original/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireEntreeDarwinOriginal(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireEntreeDarwin(pAnnee) 
					+ "Darwin original" 
					+ SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireEntreeDarwinOriginal(...).__________


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Entree/Darwin/Darwin original/</b> sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Darwin/Darwin original/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeDarwinOriginal.png" 
	 * alt="répertoire Entree Darwin Original" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "Entree/Darwin/Darwin original/" pour l'année 
	 * en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireEntreeDarwinOriginal() 
			throws Exception {
		return 	fournirRepertoireEntreeDarwinOriginal(
				getAnneeExploitation());	
	} // Fin de fournirRepertoireEntreeDarwinOriginal(...).________________


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Entree/Darwin/Darwin original/</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Darwin/Darwin original/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeDarwinOriginal.png" 
	 * alt="répertoire Entree Darwin Original" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "Entree/Darwin/Darwin original/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireEntreeDarwinOriginal(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(
						fournirCheminRepertoireEntreeDarwinOriginal(
								pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireEntreeDarwinOriginal(...).________________


	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>Entree/Hit/</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Hit/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeHit.png" 
	 * alt="répertoire Entree Hit" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "Entree/Hit" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireEntreeHit(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireEntree(pAnnee) + "Hit" 
			+ SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireEntreeHit(...)._____________________


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Entree/Hit</b> sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Hit/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeHit.png" 
	 * alt="répertoire Entree Hit" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "Entree/Hit" pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireEntreeHit() throws Exception {
		return fournirRepertoireEntreeHit(getAnneeExploitation());
	} // Fin de fournirRepertoireEntreeHit().______________________________
	
	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Entree/Hit</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Hit/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeHit.png" 
	 * alt="répertoire Entree Hit" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "Entree/Hit" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireEntreeHit(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireEntreeHit(pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireEntreeHit(...).___________________________


	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>Entree/Hit/Hit encodés en ANSI/</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Hit/Hit encodés en ANSI/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeHitANSI.png" 
	 * alt="répertoire Entree Hit ANSI" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "Entree/Hit/Hit encodés en ANSI/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireEntreeHitANSI(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireEntreeHit(pAnnee) 
					+ "Hit encodés en ANSI" 
					+ SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireEntreeHitANSI(...)._________________


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Entree/Hit/Hit encodés en ANSI/</b> sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Hit/Hit encodés en ANSI/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeHitANSI.png" 
	 * alt="répertoire Entree Hit ANSI" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "Entree/Hit/Hit encodés en ANSI/" 
	 * pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireEntreeHitANSI() throws Exception {
		return fournirRepertoireEntreeHitANSI(getAnneeExploitation());
	} // Fin de fournirRepertoireEntreeHitANSI().__________________________
	
	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Entree/Hit/Hit encodés en ANSI/</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Hit/Hit encodés en ANSI/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeHitANSI.png" 
	 * alt="répertoire Entree Hit ANSI" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "Entree/Hit/Hit encodés en ANSI/" 
	 * pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireEntreeHitANSI(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireEntreeHitANSI(pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireEntreeHitANSI(...)._______________________


	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>Entree/Hit/Hit encodés en ANSI/Hit encodés en ANSI_Ascii/</b> 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Hit/Hit encodés en ANSI/Hit encodés en ANSI_Ascii/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeHitANSIAscii.png" 
	 * alt="répertoire Entree Hit ANSI Ascii" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "Entree/Hit/Hit encodés en ANSI/Hit encodés en ANSI_Ascii/" 
	 * pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireEntreeHitANSIAscii(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireEntreeHitANSI(pAnnee) 
					+ "Hit encodés en ANSI_Ascii" 
					+ SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireEntreeHitANSIAscii(...).____________


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Entree/Hit/Hit encodés en ANSI/Hit encodés en ANSI_Ascii/</b> 
	 * sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Hit/Hit encodés en ANSI/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeHitANSIAscii.png" 
	 * alt="répertoire Entree Hit ANSI Ascii" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "Entree/Hit/Hit encodés en ANSI/Hit encodés en ANSI_Ascii/" 
	 * pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireEntreeHitANSIAscii() 
												throws Exception {
		return fournirRepertoireEntreeHitANSIAscii(getAnneeExploitation());
	} // Fin de fournirRepertoireEntreeHitANSIAscii()._____________________
	
	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Entree/Hit/Hit encodés en ANSI/Hit encodés en ANSI_Ascii/</b> 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Hit/Hit encodés en ANSI/Hit encodés en ANSI_Ascii/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeHitANSIAscii.png" 
	 * alt="répertoire Entree Hit ANSI Ascii" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "Entree/Hit/Hit encodés en ANSI/Hit encodés en ANSI_Ascii/" 
	 * pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireEntreeHitANSIAscii(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireEntreeHitANSIAscii(
						pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireEntreeHitANSIAscii(...).__________________


	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>Entree/Hit/Hit encodés en ANSI/Hit encodés en ANSI_Csv/</b> 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Hit/Hit encodés en ANSI/Hit encodés en ANSI_Csv/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeHitANSICsv.png" 
	 * alt="répertoire Entree Hit ANSI Csv" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "Entree/Hit/Hit encodés en ANSI/Hit encodés en ANSI_Csv/" 
	 * pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireEntreeHitANSICsv(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireEntreeHitANSI(pAnnee) 
					+ "Hit encodés en ANSI_Csv" 
					+ SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireEntreeHitANSICsv(...).____________


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Entree/Hit/Hit encodés en ANSI/Hit encodés en ANSI_Csv/</b> 
	 * sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Hit/Hit encodés en ANSI/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeHitANSICsv.png" 
	 * alt="répertoire Entree Hit ANSI Csv" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "Entree/Hit/Hit encodés en ANSI/Hit encodés en ANSI_Csv/" 
	 * pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireEntreeHitANSICsv() 
												throws Exception {
		return fournirRepertoireEntreeHitANSICsv(getAnneeExploitation());
	} // Fin de fournirRepertoireEntreeHitANSICsv()._____________________
	
	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Entree/Hit/Hit encodés en ANSI/Hit encodés en ANSI_Csv/</b> 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Hit/Hit encodés en ANSI/Hit encodés en ANSI_Csv/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeHitANSICsv.png" 
	 * alt="répertoire Entree Hit ANSI Csv" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "Entree/Hit/Hit encodés en ANSI/Hit encodés en ANSI_Csv/" 
	 * pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireEntreeHitANSICsv(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireEntreeHitANSICsv(
						pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireEntreeHitANSICsv(...).____________________


	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>Entree/Hit/Hit encodés en ANSI/Hit encodés en ANSI_FeorXML/</b> 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Hit/Hit encodés en ANSI/Hit encodés en ANSI_FeorXML/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeHitANSIFeorXML.png" 
	 * alt="répertoire Entree Hit ANSI FeorXML" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "Entree/Hit/Hit encodés en ANSI/Hit encodés en ANSI_FeorXML/" 
	 * pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireEntreeHitANSIFeorXML(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireEntreeHitANSI(pAnnee) 
					+ "Hit encodés en ANSI_FeorXML" 
					+ SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireEntreeHitANSIFeorXML(...).____________


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Entree/Hit/Hit encodés en ANSI/Hit encodés en ANSI_FeorXML/</b> 
	 * sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Hit/Hit encodés en ANSI/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeHitANSIFeorXML.png" 
	 * alt="répertoire Entree Hit ANSI FeorXML" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "Entree/Hit/Hit encodés en ANSI/Hit encodés en ANSI_FeorXML/" 
	 * pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireEntreeHitANSIFeorXML() 
												throws Exception {
		return fournirRepertoireEntreeHitANSIFeorXML(getAnneeExploitation());
	} // Fin de fournirRepertoireEntreeHitANSIFeorXML()._____________________
	
	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Entree/Hit/Hit encodés en ANSI/Hit encodés en ANSI_FeorXML/</b> 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Hit/Hit encodés en ANSI/Hit encodés en ANSI_FeorXML/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeHitANSIFeorXML.png" 
	 * alt="répertoire Entree Hit ANSI FeorXML" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "Entree/Hit/Hit encodés en ANSI/Hit encodés en ANSI_FeorXML/" 
	 * pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireEntreeHitANSIFeorXML(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireEntreeHitANSIFeorXML(
						pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireEntreeHitANSIFeorXML(...).________________


	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>Entree/Hit/Hit encodés en UTF8/</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Hit/Hit encodés en UTF8/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeHitUTF8.png" 
	 * alt="répertoire Entree Hit UTF8" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "Entree/Hit/Hit encodés en UTF8/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireEntreeHitUTF8(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireEntreeHit(pAnnee) 
					+ "Hit encodés en UTF8" 
					+ SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireEntreeHitUTF8(...)._________________


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Entree/Hit/Hit encodés en UTF8/</b> sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Hit/Hit encodés en UTF8/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeHitUTF8.png" 
	 * alt="répertoire Entree Hit UTF8" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "Entree/Hit/Hit encodés en UTF8/" 
	 * pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireEntreeHitUTF8() throws Exception {
		return fournirRepertoireEntreeHitUTF8(getAnneeExploitation());
	} // Fin de fournirRepertoireEntreeHitUTF8().__________________________
	
	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Entree/Hit/Hit encodés en UTF8/</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Hit/Hit encodés en UTF8/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeHitUTF8.png" 
	 * alt="répertoire Entree Hit UTF8" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "Entree/Hit/Hit encodés en UTF8/" 
	 * pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireEntreeHitUTF8(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireEntreeHitUTF8(pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireEntreeHitUTF8(...)._______________________


	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>Entree/Hit/Hit encodés en UTF8/Hit encodés en UTF8_Ascii/</b> 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Hit/Hit encodés en UTF8/Hit encodés en UTF8_Ascii/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeHitUTF8Ascii.png" 
	 * alt="répertoire Entree Hit UTF8 Ascii" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "Entree/Hit/Hit encodés en UTF8/Hit encodés en UTF8_Ascii/" 
	 * pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireEntreeHitUTF8Ascii(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireEntreeHitUTF8(pAnnee) 
					+ "Hit encodés en UTF8_Ascii" 
					+ SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireEntreeHitUTF8Ascii(...).____________


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Entree/Hit/Hit encodés en UTF8/Hit encodés en UTF8_Ascii/</b> 
	 * sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Hit/Hit encodés en UTF8/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeHitUTF8Ascii.png" 
	 * alt="répertoire Entree Hit UTF8 Ascii" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "Entree/Hit/Hit encodés en UTF8/Hit encodés en UTF8_Ascii/" 
	 * pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireEntreeHitUTF8Ascii() 
												throws Exception {
		return fournirRepertoireEntreeHitUTF8Ascii(getAnneeExploitation());
	} // Fin de fournirRepertoireEntreeHitUTF8Ascii()._____________________
	
	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Entree/Hit/Hit encodés en UTF8/Hit encodés en UTF8_Ascii/</b> 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Hit/Hit encodés en UTF8/Hit encodés en UTF8_Ascii/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeHitUTF8Ascii.png" 
	 * alt="répertoire Entree Hit UTF8 Ascii" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "Entree/Hit/Hit encodés en UTF8/Hit encodés en UTF8_Ascii/" 
	 * pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireEntreeHitUTF8Ascii(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireEntreeHitUTF8Ascii(
						pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireEntreeHitUTF8Ascii(...).__________________


	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>Entree/Hit/Hit encodés en UTF8/Hit encodés en UTF8_Csv/</b> 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Hit/Hit encodés en UTF8/Hit encodés en UTF8_Csv/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeHitUTF8Csv.png" 
	 * alt="répertoire Entree Hit UTF8 Csv" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "Entree/Hit/Hit encodés en UTF8/Hit encodés en UTF8_Csv/" 
	 * pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireEntreeHitUTF8Csv(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireEntreeHitUTF8(pAnnee) 
					+ "Hit encodés en UTF8_Csv" 
					+ SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireEntreeHitUTF8Csv(...).____________


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Entree/Hit/Hit encodés en UTF8/Hit encodés en UTF8_Csv/</b> 
	 * sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Hit/Hit encodés en UTF8/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeHitUTF8Csv.png" 
	 * alt="répertoire Entree Hit UTF8 Csv" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "Entree/Hit/Hit encodés en UTF8/Hit encodés en UTF8_Csv/" 
	 * pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireEntreeHitUTF8Csv() 
												throws Exception {
		return fournirRepertoireEntreeHitUTF8Csv(getAnneeExploitation());
	} // Fin de fournirRepertoireEntreeHitUTF8Csv()._____________________
	
	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Entree/Hit/Hit encodés en UTF8/Hit encodés en UTF8_Csv/</b> 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Hit/Hit encodés en UTF8/Hit encodés en UTF8_Csv/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeHitUTF8Csv.png" 
	 * alt="répertoire Entree Hit UTF8 Csv" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "Entree/Hit/Hit encodés en UTF8/Hit encodés en UTF8_Csv/" 
	 * pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireEntreeHitUTF8Csv(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireEntreeHitUTF8Csv(
						pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireEntreeHitUTF8Csv(...).____________________


	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>Entree/Hit/Hit encodés en UTF8/Hit encodés en UTF8_FeorXML/</b> 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Hit/Hit encodés en UTF8/Hit encodés en UTF8_FeorXML/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeHitUTF8FeorXML.png" 
	 * alt="répertoire Entree Hit UTF8 FeorXML" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "Entree/Hit/Hit encodés en UTF8/Hit encodés en UTF8_FeorXML/" 
	 * pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireEntreeHitUTF8FeorXML(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireEntreeHitUTF8(pAnnee) 
					+ "Hit encodés en UTF8_FeorXML" 
					+ SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireEntreeHitUTF8FeorXML(...).____________


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Entree/Hit/Hit encodés en UTF8/Hit encodés en UTF8_FeorXML/</b> 
	 * sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Hit/Hit encodés en UTF8/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeHitUTF8FeorXML.png" 
	 * alt="répertoire Entree Hit UTF8 FeorXML" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "Entree/Hit/Hit encodés en UTF8/Hit encodés en UTF8_FeorXML/" 
	 * pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireEntreeHitUTF8FeorXML() 
												throws Exception {
		return fournirRepertoireEntreeHitUTF8FeorXML(getAnneeExploitation());
	} // Fin de fournirRepertoireEntreeHitUTF8FeorXML()._____________________
	
	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Entree/Hit/Hit encodés en UTF8/Hit encodés en UTF8_FeorXML/</b> 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Hit/Hit encodés en UTF8/Hit encodés en UTF8_FeorXML/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeHitUTF8FeorXML.png" 
	 * alt="répertoire Entree Hit UTF8 FeorXML" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "Entree/Hit/Hit encodés en UTF8/Hit encodés en UTF8_FeorXML/" 
	 * pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireEntreeHitUTF8FeorXML(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireEntreeHitUTF8FeorXML(
						pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireEntreeHitUTF8FeorXML(...).________________


	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>Entree/Hit/Hit originaux/</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par un 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Hit/Hit originaux/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeHitOriginal.png" 
	 * alt="répertoire Entree Hit Original" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "Entree/Hit/Hit originaux/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireEntreeHitOriginal(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireEntreeHit(pAnnee) 
					+ "Hit originaux" 
					+ SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireEntreeHitOriginal(...)._____________


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Entree/Hit/Hit originaux/</b> sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Hit/Hit originaux/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeHitOriginal.png" 
	 * alt="répertoire Entree Hit Original" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "Entree/Hit/Hit originaux/" pour l'année 
	 * en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireEntreeHitOriginal() 
			throws Exception {
		return 	fournirRepertoireEntreeHitOriginal(
				getAnneeExploitation());	
	} // Fin de fournirRepertoireEntreeHitOriginal(...).___________________


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Entree/Hit/Hit originaux/</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Entree/Hit/Hit originaux/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_EntreeHitOriginal.png" 
	 * alt="répertoire Entree Hit Original" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "Entree/Hit/Hit originaux/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireEntreeHitOriginal(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(
						fournirCheminRepertoireEntreeHitOriginal(
								pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireEntreeHitOriginal(...).___________________


	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>Sortie</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par une 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas un 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_Sortie.png" 
	 * alt="répertoire Sortie" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "Sortie" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireSortie(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireAnnee(pAnnee) + "Sortie" 
			+ SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireSortie(...).________________________


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Sortie</b> sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_Sortie.png" 
	 * alt="répertoire Sortie" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "Sortie" pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireSortie() throws Exception {
		return fournirRepertoireSortie(getAnneeExploitation());
	} // Fin de fournirRepertoireSortie()._________________________________

	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Sortie</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_Sortie.png" 
	 * alt="répertoire Sortie" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "Sortie" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireSortie(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireSortie(pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireSortie(...).______________________________


	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>Sortie/HistonatF07/</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par une 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas un 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF07/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF07.png" 
	 * alt="répertoire Sortie HistonatF07" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "Sortie/HistonatF07/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireSortieHistonatF07(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireSortie(pAnnee) + "HistonatF07" 
			+ SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireSortieHistonatF07(...)._____________


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Sortie/HistonatF07/</b> sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF07/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF07.png" 
	 * alt="répertoire Sortie HistonatF07" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "Sortie/HistonatF07/" pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireSortieHistonatF07() 
			throws Exception {
		return fournirRepertoireSortieHistonatF07(getAnneeExploitation());
	} // Fin de fournirRepertoireSortieHistonatF07().______________________

	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Sortie/HistonatF07/</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF07/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF07.png" 
	 * alt="répertoire Sortie HistonatF07" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "Sortie/HistonatF07/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireSortieHistonatF07(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireSortieHistonatF07(pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireSortieHistonatF07(...).___________________


	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>Sortie/HistonatF07/HistonatF07 en ASCII/</b> 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par une 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas un 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF07/HistonatF07 en ASCII/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF07Ascii.png" 
	 * alt="répertoire Sortie HistonatF07 Ascii" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "Sortie/HistonatF07/HistonatF07 en ASCII/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireSortieHistonatF07Ascii(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireSortieHistonatF07(pAnnee) 
					+ "HistonatF07 en ASCII" 
					+ SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireSortieHistonatF07Ascii(...).________


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Sortie/HistonatF07/HistonatF07 en ASCII/</b> 
	 * sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF07HistonatF07 en ASCII/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF07Ascii.png" 
	 * alt="répertoire Sortie HistonatF07 Ascii" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "Sortie/HistonatF07/HistonatF07 en ASCII/" 
	 * pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireSortieHistonatF07Ascii() 
			throws Exception {
		return fournirRepertoireSortieHistonatF07Ascii(
				getAnneeExploitation());
	} // Fin de fournirRepertoireSortieHistonatF07Ascii()._________________

	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Sortie/HistonatF07/HistonatF07 en ASCII/</b> 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF07/HistonatF07 en ASCII/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF07Ascii.png" 
	 * alt="répertoire Sortie HistonatF07 Ascii" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "Sortie/HistonatF07/HistonatF07 en ASCII/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireSortieHistonatF07Ascii(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireSortieHistonatF07Ascii(
						pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireSortieHistonatF07Ascii(...).______________

	
	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>Sortie/HistonatF07/HistonatF07 en ASCII/
	 * HistonatF07 ASCII encodé en ANSI/</b> 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par une 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas un 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF07/HistonatF07 en ASCII/
	 * HistonatF07 ASCII encodé en ANSI/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF07AsciiANSI.png" 
	 * alt="répertoire Sortie HistonatF07 Ascii ANSI" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "Sortie/HistonatF07/HistonatF07 en ASCII/
	 * HistonatF07 ASCII encodé en ANSI/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireSortieHistonatF07AsciiANSI(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireSortieHistonatF07Ascii(pAnnee) 
					+ "HistonatF07 ASCII encodé en ANSI" 
					+ SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireSortieHistonatF07AsciiANSI(...).____


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Sortie/HistonatF07/HistonatF07 en ASCII/
	 * HistonatF07 ASCII encodé en ANSI/</b> 
	 * sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF07HistonatF07 en ASCII/
	 * HistonatF07 ASCII encodé en ANSI/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF07AsciiANSI.png" 
	 * alt="répertoire Sortie HistonatF07 Ascii ANSI" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "Sortie/HistonatF07/HistonatF07 en ASCII/
	 * HistonatF07 ASCII encodé en ANSI/" 
	 * pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireSortieHistonatF07AsciiANSI() 
			throws Exception {
		return fournirRepertoireSortieHistonatF07AsciiANSI(
				getAnneeExploitation());
	} // Fin de fournirRepertoireSortieHistonatF07AsciiANSI()._____________

	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Sortie/HistonatF07/HistonatF07 en ASCII/
	 * HistonatF07 ASCII encodé en ANSI/</b> 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF07/HistonatF07 en ASCII/
	 * HistonatF07 ASCII encodé en ANSI/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF07AsciiANSI.png" 
	 * alt="répertoire Sortie HistonatF07 Ascii ANSI" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "Sortie/HistonatF07/HistonatF07 en ASCII/
	 * HistonatF07 ASCII encodé en ANSI/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireSortieHistonatF07AsciiANSI(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireSortieHistonatF07AsciiANSI(
						pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireSortieHistonatF07AsciiANSI(...).__________


	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>Sortie/HistonatF07/HistonatF07 en ASCII/
	 * HistonatF07 ASCII encodé en UTF8/</b> 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par une 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas un 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF07/HistonatF07 en ASCII/
	 * HistonatF07 ASCII encodé en UTF8/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF07AsciiUTF8.png" 
	 * alt="répertoire Sortie HistonatF07 Ascii UTF8" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "Sortie/HistonatF07/HistonatF07 en ASCII/
	 * HistonatF07 ASCII encodé en UTF8/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireSortieHistonatF07AsciiUTF8(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireSortieHistonatF07Ascii(pAnnee) 
					+ "HistonatF07 ASCII encodé en UTF8" 
					+ SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireSortieHistonatF07AsciiUTF8(...).____


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Sortie/HistonatF07/HistonatF07 en ASCII/
	 * HistonatF07 ASCII encodé en UTF8/</b> 
	 * sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF07HistonatF07 en ASCII/
	 * HistonatF07 ASCII encodé en UTF8/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF07AsciiUTF8.png" 
	 * alt="répertoire Sortie HistonatF07 Ascii UTF8" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "Sortie/HistonatF07/HistonatF07 en ASCII/
	 * HistonatF07 ASCII encodé en UTF8/" 
	 * pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireSortieHistonatF07AsciiUTF8() 
			throws Exception {
		return fournirRepertoireSortieHistonatF07AsciiUTF8(
				getAnneeExploitation());
	} // Fin de fournirRepertoireSortieHistonatF07AsciiUTF8()._____________

	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Sortie/HistonatF07/HistonatF07 en ASCII/
	 * HistonatF07 ASCII encodé en UTF8/</b> 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF07/HistonatF07 en ASCII/
	 * HistonatF07 ASCII encodé en UTF8/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF07AsciiUTF8.png" 
	 * alt="répertoire Sortie HistonatF07 Ascii UTF8" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "Sortie/HistonatF07/HistonatF07 en ASCII/
	 * HistonatF07 ASCII encodé en UTF8/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireSortieHistonatF07AsciiUTF8(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireSortieHistonatF07AsciiUTF8(
						pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireSortieHistonatF07AsciiUTF8(...).__________


	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>Sortie/HistonatF07/HistonatF07 en CSV/</b> 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par une 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas un 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF07/HistonatF07 en CSV/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF07Csv.png" 
	 * alt="répertoire Sortie HistonatF07 Csv" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "Sortie/HistonatF07/HistonatF07 en CSV/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireSortieHistonatF07Csv(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireSortieHistonatF07(pAnnee) 
					+ "HistonatF07 en CSV" 
					+ SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireSortieHistonatF07Csv(...).__________


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Sortie/HistonatF07/HistonatF07 en CSV/</b> 
	 * sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF07HistonatF07 en CSV/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF07Csv.png" 
	 * alt="répertoire Sortie HistonatF07 Csv" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "Sortie/HistonatF07/HistonatF07 en CSV/" 
	 * pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireSortieHistonatF07Csv() 
			throws Exception {
		return fournirRepertoireSortieHistonatF07Csv(
				getAnneeExploitation());
	} // Fin de fournirRepertoireSortieHistonatF07Csv().___________________

	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Sortie/HistonatF07/HistonatF07 en CSV/</b> 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF07/HistonatF07 en CSV/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF07Csv.png" 
	 * alt="répertoire Sortie HistonatF07 Csv" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "Sortie/HistonatF07/HistonatF07 en CSV/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireSortieHistonatF07Csv(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireSortieHistonatF07Csv(
						pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireSortieHistonatF07Csv(...).________________



	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>Sortie/HistonatF07/HistonatF07 en CSV/
	 * HistonatF07 CSV encodé en ANSI/</b> 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par une 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas un 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF07/HistonatF07 en CSV/
	 * HistonatF07 CSV encodé en ANSI/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF07CsvANSI.png" 
	 * alt="répertoire Sortie HistonatF07 Csv ANSI" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "Sortie/HistonatF07/HistonatF07 en CSV/
	 * HistonatF07 CSV encodé en ANSI/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireSortieHistonatF07CsvANSI(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireSortieHistonatF07Csv(pAnnee) 
					+ "HistonatF07 CSV encodé en ANSI" 
					+ SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireSortieHistonatF07CsvANSI(...).______


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Sortie/HistonatF07/HistonatF07 en CSV/
	 * HistonatF07 CSV encodé en ANSI/</b> 
	 * sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF07HistonatF07 en CSV/
	 * HistonatF07 CSV encodé en ANSI/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF07CsvANSI.png" 
	 * alt="répertoire Sortie HistonatF07 Csv ANSI" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "Sortie/HistonatF07/HistonatF07 en CSV/
	 * HistonatF07 CSV encodé en ANSI/" 
	 * pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireSortieHistonatF07CsvANSI() 
			throws Exception {
		return fournirRepertoireSortieHistonatF07CsvANSI(
				getAnneeExploitation());
	} // Fin de fournirRepertoireSortieHistonatF07CsvANSI()._______________

	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Sortie/HistonatF07/HistonatF07 en CSV/
	 * HistonatF07 CSV encodé en ANSI/</b> 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF07/HistonatF07 en CSV/
	 * HistonatF07 CSV encodé en ANSI/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF07CsvANSI.png" 
	 * alt="répertoire Sortie HistonatF07 Csv ANSI" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "Sortie/HistonatF07/HistonatF07 en CSV/
	 * HistonatF07 CSV encodé en ANSI/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireSortieHistonatF07CsvANSI(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireSortieHistonatF07CsvANSI(
						pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireSortieHistonatF07CsvANSI(...).____________


	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>Sortie/HistonatF07/HistonatF07 en CSV/
	 * HistonatF07 CSV encodé en UTF8/</b> 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par une 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas un 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF07/HistonatF07 en CSV/
	 * HistonatF07 CSV encodé en UTF8/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF07CsvUTF8.png" 
	 * alt="répertoire Sortie HistonatF07 Csv UTF8" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "Sortie/HistonatF07/HistonatF07 en CSV/
	 * HistonatF07 CSV encodé en UTF8/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireSortieHistonatF07CsvUTF8(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireSortieHistonatF07Csv(pAnnee) 
					+ "HistonatF07 CSV encodé en UTF8" 
					+ SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireSortieHistonatF07CsvUTF8(...).______


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Sortie/HistonatF07/HistonatF07 en CSV/
	 * HistonatF07 CSV encodé en UTF8/</b> 
	 * sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF07HistonatF07 en CSV/
	 * HistonatF07 CSV encodé en UTF8/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF07CsvUTF8.png" 
	 * alt="répertoire Sortie HistonatF07 Csv UTF8" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "Sortie/HistonatF07/HistonatF07 en CSV/
	 * HistonatF07 CSV encodé en UTF8/" 
	 * pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireSortieHistonatF07CsvUTF8() 
			throws Exception {
		return fournirRepertoireSortieHistonatF07CsvUTF8(
				getAnneeExploitation());
	} // Fin de fournirRepertoireSortieHistonatF07CsvUTF8()._____________

	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Sortie/HistonatF07/HistonatF07 en CSV/
	 * HistonatF07 CSV encodé en UTF8/</b> 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF07/HistonatF07 en CSV/
	 * HistonatF07 CSV encodé en UTF8/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF07CsvUTF8.png" 
	 * alt="répertoire Sortie HistonatF07 Csv UTF8" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "Sortie/HistonatF07/HistonatF07 en CSV/
	 * HistonatF07 CSV encodé en UTF8/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireSortieHistonatF07CsvUTF8(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireSortieHistonatF07CsvUTF8(
						pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireSortieHistonatF07CsvUTF8(...).____________


	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>Sortie/HistonatF07/HistonatF07 en FeorXML/</b> 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par une 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas un 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF07/HistonatF07 en FeorXML/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF07FeorXML.png" 
	 * alt="répertoire Sortie HistonatF07 FeorXML" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "Sortie/HistonatF07/HistonatF07 en FeorXML/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireSortieHistonatF07FeorXML(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireSortieHistonatF07(pAnnee) 
					+ "HistonatF07 en FeorXML" 
					+ SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireSortieHistonatF07FeorXML(...).______


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Sortie/HistonatF07/HistonatF07 en FeorXML/</b> 
	 * sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF07HistonatF07 en FeorXML/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF07FeorXML.png" 
	 * alt="répertoire Sortie HistonatF07 FeorXML" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "Sortie/HistonatF07/HistonatF07 en FeorXML/" 
	 * pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireSortieHistonatF07FeorXML() 
			throws Exception {
		return fournirRepertoireSortieHistonatF07FeorXML(
				getAnneeExploitation());
	} // Fin de fournirRepertoireSortieHistonatF07FeorXML()._______________

	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Sortie/HistonatF07/HistonatF07 en FeorXML/</b> 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF07/HistonatF07 en FeorXML/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF07FeorXML.png" 
	 * alt="répertoire Sortie HistonatF07 FeorXML" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "Sortie/HistonatF07/HistonatF07 en FeorXML/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireSortieHistonatF07FeorXML(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireSortieHistonatF07FeorXML(
						pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireSortieHistonatF07FeorXML(...).____________


	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>Sortie/HistonatF07/HistonatF07 en FeorXML/
	 * HistonatF07 FeorXML encodé en ANSI/</b> 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par une 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas un 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF07/HistonatF07 en FeorXML/
	 * HistonatF07 FeorXML encodé en ANSI/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF07FeorXMLANSI.png" 
	 * alt="répertoire Sortie HistonatF07 FeorXML ANSI" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "Sortie/HistonatF07/HistonatF07 en FeorXML/
	 * HistonatF07 FeorXML encodé en ANSI/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireSortieHistonatF07FeorXMLANSI(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireSortieHistonatF07FeorXML(pAnnee) 
					+ "HistonatF07 FeorXML encodé en ANSI" 
					+ SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireSortieHistonatF07FeorXMLANSI(...).__


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Sortie/HistonatF07/HistonatF07 en FeorXML/
	 * HistonatF07 FeorXML encodé en ANSI/</b> 
	 * sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF07HistonatF07 en FeorXML/
	 * HistonatF07 FeorXML encodé en ANSI/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF07FeorXMLANSI.png" 
	 * alt="répertoire Sortie HistonatF07 FeorXML ANSI" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "Sortie/HistonatF07/HistonatF07 en FeorXML/
	 * HistonatF07 FeorXML encodé en ANSI/" 
	 * pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireSortieHistonatF07FeorXMLANSI() 
			throws Exception {
		return fournirRepertoireSortieHistonatF07FeorXMLANSI(
				getAnneeExploitation());
	} // Fin de fournirRepertoireSortieHistonatF07FeorXMLANSI().___________

	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Sortie/HistonatF07/HistonatF07 en FeorXML/
	 * HistonatF07 FeorXML encodé en ANSI/</b> 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF07/HistonatF07 en FeorXML/
	 * HistonatF07 FeorXML encodé en ANSI/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF07FeorXMLANSI.png" 
	 * alt="répertoire Sortie HistonatF07 FeorXML ANSI" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "Sortie/HistonatF07/HistonatF07 en FeorXML/
	 * HistonatF07 FeorXML encodé en ANSI/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireSortieHistonatF07FeorXMLANSI(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireSortieHistonatF07FeorXMLANSI(
						pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireSortieHistonatF07FeorXMLANSI(...).________



	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>Sortie/HistonatF07/HistonatF07 en FeorXML/
	 * HistonatF07 FeorXML encodé en UTF8/</b> 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par une 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas un 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF07/HistonatF07 en FeorXML/
	 * HistonatF07 FeorXML encodé en UTF8/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF07FeorXMLUTF8.png" 
	 * alt="répertoire Sortie HistonatF07 FeorXML UTF8" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "Sortie/HistonatF07/HistonatF07 en FeorXML/
	 * HistonatF07 FeorXML encodé en UTF8/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireSortieHistonatF07FeorXMLUTF8(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireSortieHistonatF07FeorXML(pAnnee) 
					+ "HistonatF07 FeorXML encodé en UTF8" 
					+ SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireSortieHistonatF07FeorXMLUTF8(...).__


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Sortie/HistonatF07/HistonatF07 en FeorXML/
	 * HistonatF07 FeorXML encodé en UTF8/</b> 
	 * sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF07HistonatF07 en FeorXML/
	 * HistonatF07 FeorXML encodé en UTF8/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF07FeorXMLUTF8.png" 
	 * alt="répertoire Sortie HistonatF07 FeorXML UTF8" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "Sortie/HistonatF07/HistonatF07 en FeorXML/
	 * HistonatF07 FeorXML encodé en UTF8/" 
	 * pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireSortieHistonatF07FeorXMLUTF8() 
			throws Exception {
		return fournirRepertoireSortieHistonatF07FeorXMLUTF8(
				getAnneeExploitation());
	} // Fin de fournirRepertoireSortieHistonatF07FeorXMLUTF8().___________

	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Sortie/HistonatF07/HistonatF07 en FeorXML/
	 * HistonatF07 FeorXML encodé en UTF8/</b> 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF07/HistonatF07 en FeorXML/
	 * HistonatF07 FeorXML encodé en UTF8/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF07FeorXMLUTF8.png" 
	 * alt="répertoire Sortie HistonatF07 FeorXML UTF8" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "Sortie/HistonatF07/HistonatF07 en FeorXML/
	 * HistonatF07 FeorXML encodé en UTF8/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireSortieHistonatF07FeorXMLUTF8(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireSortieHistonatF07FeorXMLUTF8(
						pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireSortieHistonatF07FeorXMLUTF8(...).________


	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>Sortie/HistonatF08/</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par une 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas un 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF08/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF08.png" 
	 * alt="répertoire Sortie HistonatF08" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "Sortie/HistonatF08/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireSortieHistonatF08(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireSortie(pAnnee) + "HistonatF08" 
			+ SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireSortieHistonatF08(...)._____________


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Sortie/HistonatF08/</b> sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF08/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF08.png" 
	 * alt="répertoire Sortie HistonatF08" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "Sortie/HistonatF08/" pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireSortieHistonatF08() 
			throws Exception {
		return fournirRepertoireSortieHistonatF08(getAnneeExploitation());
	} // Fin de fournirRepertoireSortieHistonatF08().______________________

	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Sortie/HistonatF08/</b> sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF08/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF08.png" 
	 * alt="répertoire Sortie HistonatF08" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "Sortie/HistonatF08/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireSortieHistonatF08(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireSortieHistonatF08(pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireSortieHistonatF08(...).___________________


	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>Sortie/HistonatF08/HistonatF08 en ASCII/</b> 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par une 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas un 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF08/HistonatF08 en ASCII/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF08Ascii.png" 
	 * alt="répertoire Sortie HistonatF08 Ascii" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "Sortie/HistonatF08/HistonatF08 en ASCII/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireSortieHistonatF08Ascii(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireSortieHistonatF08(pAnnee) 
					+ "HistonatF08 en ASCII" 
					+ SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireSortieHistonatF08Ascii(...).________


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Sortie/HistonatF08/HistonatF08 en ASCII/</b> 
	 * sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF08HistonatF08 en ASCII/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF08Ascii.png" 
	 * alt="répertoire Sortie HistonatF08 Ascii" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "Sortie/HistonatF08/HistonatF08 en ASCII/" 
	 * pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireSortieHistonatF08Ascii() 
			throws Exception {
		return fournirRepertoireSortieHistonatF08Ascii(
				getAnneeExploitation());
	} // Fin de fournirRepertoireSortieHistonatF08Ascii()._________________

	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Sortie/HistonatF08/HistonatF08 en ASCII/</b> 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF08/HistonatF08 en ASCII/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF08Ascii.png" 
	 * alt="répertoire Sortie HistonatF08 Ascii" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "Sortie/HistonatF08/HistonatF08 en ASCII/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireSortieHistonatF08Ascii(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireSortieHistonatF08Ascii(
						pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireSortieHistonatF08Ascii(...).______________

	
	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>Sortie/HistonatF08/HistonatF08 en ASCII/
	 * HistonatF08 ASCII encodé en ANSI/</b> 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par une 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas un 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF08/HistonatF08 en ASCII/
	 * HistonatF08 ASCII encodé en ANSI/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF08AsciiANSI.png" 
	 * alt="répertoire Sortie HistonatF08 Ascii ANSI" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "Sortie/HistonatF08/HistonatF08 en ASCII/
	 * HistonatF08 ASCII encodé en ANSI/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireSortieHistonatF08AsciiANSI(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireSortieHistonatF08Ascii(pAnnee) 
					+ "HistonatF08 ASCII encodé en ANSI" 
					+ SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireSortieHistonatF08AsciiANSI(...).____


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Sortie/HistonatF08/HistonatF08 en ASCII/
	 * HistonatF08 ASCII encodé en ANSI/</b> 
	 * sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF08HistonatF08 en ASCII/
	 * HistonatF08 ASCII encodé en ANSI/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF08AsciiANSI.png" 
	 * alt="répertoire Sortie HistonatF08 Ascii ANSI" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "Sortie/HistonatF08/HistonatF08 en ASCII/
	 * HistonatF08 ASCII encodé en ANSI/" 
	 * pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireSortieHistonatF08AsciiANSI() 
			throws Exception {
		return fournirRepertoireSortieHistonatF08AsciiANSI(
				getAnneeExploitation());
	} // Fin de fournirRepertoireSortieHistonatF08AsciiANSI()._____________

	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Sortie/HistonatF08/HistonatF08 en ASCII/
	 * HistonatF08 ASCII encodé en ANSI/</b> 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF08/HistonatF08 en ASCII/
	 * HistonatF08 ASCII encodé en ANSI/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF08AsciiANSI.png" 
	 * alt="répertoire Sortie HistonatF08 Ascii ANSI" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "Sortie/HistonatF08/HistonatF08 en ASCII/
	 * HistonatF08 ASCII encodé en ANSI/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireSortieHistonatF08AsciiANSI(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireSortieHistonatF08AsciiANSI(
						pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireSortieHistonatF08AsciiANSI(...).__________


	
	/**
	 * <ul>
	 * <li>retourne le <b>chemin</b> du répertoire
	 * <b>Sortie/HistonatF08/HistonatF08 en ASCII/
	 * HistonatF08 ASCII encodé en UTF8/</b> 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>le chemin fourni se termine par une 
	 * séparateur universel de fichiers "/".</li>
	 * <li>ne fait rien si pAnnee n'est pas un 
	 * bonne année sur 4 chiffres exactement.</li>
	 * </ul>
	 * Par exemple : "D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF08/HistonatF08 en ASCII/
	 * HistonatF08 ASCII encodé en UTF8/" 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF08AsciiUTF8.png" 
	 * alt="répertoire Sortie HistonatF08 Ascii UTF8" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : String : chemin du répertoire 
	 * "Sortie/HistonatF08/HistonatF08 en ASCII/
	 * HistonatF08 ASCII encodé en UTF8/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	private static String fournirCheminRepertoireSortieHistonatF08AsciiUTF8(
			final String pAnnee) 
					throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			return fournirCheminRepertoireSortieHistonatF08Ascii(pAnnee) 
					+ "HistonatF08 ASCII encodé en UTF8" 
					+ SEP_REP;
			
		}
		
		/* retourne null si le paramètre ne convient pas. */
		return null;
	
	} // Fin de fournirCheminRepertoireSortieHistonatF08AsciiUTF8(...).____


	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Sortie/HistonatF08/HistonatF08 en ASCII/
	 * HistonatF08 ASCII encodé en UTF8/</b> 
	 * sur un disque dur pour 
	 * l'<b>année en cours dans les préférences</b>.</li>
	 * <li>ne fait rien si l'année en cours dans les préférences 
	 * n'est pas une bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF08HistonatF08 en ASCII/
	 * HistonatF08 ASCII encodé en UTF8/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF08AsciiUTF8.png" 
	 * alt="répertoire Sortie HistonatF08 Ascii UTF8" border="1" align="center" />
	 * <br/>
	 * 
	 * @return : File : répertoire 
	 * "Sortie/HistonatF08/HistonatF08 en ASCII/
	 * HistonatF08 ASCII encodé en UTF8/" 
	 * pour l'année en cours dans les préférences.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireSortieHistonatF08AsciiUTF8() 
			throws Exception {
		return fournirRepertoireSortieHistonatF08AsciiUTF8(
				getAnneeExploitation());
	} // Fin de fournirRepertoireSortieHistonatF08AsciiUTF8()._____________

	
	
	/**
	 * <ul>
	 * <li><b>retourne le répertoire</b>
	 * <b>Sortie/HistonatF08/HistonatF08 en ASCII/
	 * HistonatF08 ASCII encodé en UTF8/</b> 
	 * sur un disque dur pour 
	 * une année pAnnee (à 4 chiffres).</li>
	 * <li>ne fait rien si pAnnee n'est pas une 
	 * bonne année sur 4 chiffres exactement.</li>
	 * <li><b>crée le File sur disque</b> si il n'existe pas.</li>
	 * </ul>
	 * Par exemple : File("D:/Donnees/Trafic_Histonat/Trafic_2015/
	 * 2015/Sortie/HistonatF08/HistonatF08 en ASCII/
	 * HistonatF08 ASCII encodé en UTF8/") 
	 * pour l'année 2015.<br/>
	 * <br/>
	 * <img src="../../../../../../../../../../../javadoc/images/repertoire_SortieHistonatF08AsciiUTF8.png" 
	 * alt="répertoire Sortie HistonatF08 Ascii UTF8" border="1" align="center" />
	 * <br/>
	 * <br/>
	 * - retourne null si le paramètre ne convient pas.<br/>
	 * <br/>
	 *
	 * @param pAnnee : String : l'année sur 4 caractères comme 2015.<br/>
	 * 
	 * @return : File : répertoire 
	 * "Sortie/HistonatF08/HistonatF08 en ASCII/
	 * HistonatF08 ASCII encodé en UTF8/" pour l'année pAnnee.<br/>
	 * 
	 * @throws Exception 
	 */
	public static File fournirRepertoireSortieHistonatF08AsciiUTF8(
			final String pAnnee) throws Exception {
		
		/* ne fait rien si pAnnee n'est pas 
		 * une bonne année sur 4 chiffres exactement. */
		if (verifierAnneeConvenable(pAnnee)) {
			
			final File resultat 
				= new File(fournirCheminRepertoireSortieHistonatF08AsciiUTF8(
						pAnnee));
			
			/* crée le File sur disque si il n'existe pas. */
			if (!resultat.exists()) {
				creerRepertoireSurDisque(resultat);
			}
			
			return resultat;
		}
		
		/* retourne null si le paramètre ne convient pas. */	
		return null;
		
	} // Fin de fournirRepertoireSortieHistonatF08AsciiUTF8(...).__________




	/**
	 * method loggerInfo(
	 * String pClasse
	 * , String pMethode
	 * , String pMessage) :<br/>
	 * Créée un message de niveau INFO dans le LOG.<br/>
	 * <br/>
	 * - Ne fait rien si un des paramètres est null.<br/>
	 * <br/>
	 *
	 * @param pClasse : String : Classe appelante.<br/>
	 * @param pMethode : String : Méthode appelante.<br/>
	 * @param pMessage : String : Message particulier de la méthode.<br/>
	 */
	private static void loggerInfo(
			final String pClasse
				, final String pMethode
					, final String pMessage) {
		
		/* Ne fait rien si un des paramètres est null. */
		if (pClasse == null || pMethode == null || pMessage == null) {
			return;
		}
		
		/* LOG de niveau INFO. */			
		if (LOG.isInfoEnabled()) {
			
			final String message 
			= pClasse 
			+ SEP_MOINS
			+ pMethode
			+ SEP_MOINS
			+ pMessage;
			
			LOG.info(message);
		}
		
	} // Fin de la classe loggerInfo(
	 // String pClasse
	 // , String pMethode
	 // , String pMessage).________________________________________________
	

	
	/**
	 * method loggerInfo(
	 * String pClasse
	 * , String pMethode
	 * , String pMessage
	 * , String pComplement) :<br/>
	 * Créée un message de niveau INFO dans le LOG.<br/>
	 * <br/>
	 * - Ne fait rien si un des paramètres est null.<br/>
	 * <br/>
	 *
	 * @param pClasse : String : Classe appelante.<br/>
	 * @param pMethode : String : Méthode appelante.<br/>
	 * @param pMessage : String : Message particulier de la méthode.<br/>
	 * @param pComplement : String : Complément au message de la méthode.<br/>
	 */
	private static void loggerInfo(
			final String pClasse
				, final String pMethode
					, final String pMessage
						, final String pComplement) {
		
		/* Ne fait rien si un des paramètres est null. */
		if (pClasse == null || pMethode == null 
				|| pMessage == null || pComplement == null) {
			return;
		}
		
		/* LOG de niveau INFO. */			
		if (LOG.isInfoEnabled()) {
			
			final String message 
			= pClasse 
			+ SEP_MOINS
			+ pMethode
			+ SEP_MOINS
			+ pMessage
			+ pComplement;
			
			LOG.info(message);
		}
		
	} // Fin de loggerInfo(
	 // String pClasse
	 // , String pMethode
	 // , String pMessage
	 // , String pComplement)._____________________________________________
	
	
	
	/**
	 * method loggerError(
	 * String pClasse
	 * , String pMethode
	 * , Exception pException) :<br/>
	 * Créée un message de niveau ERROR dans le LOG.<br/>
	 * <br/>
	 * - Ne fait rien si un des paramètres est null.<br/>
	 * <br/>
	 *
	 * @param pClasse : String : Classe appelante.<br/>
	 * @param pMethode : String : Méthode appelante.<br/>
	 * @param pException : Exception : Exception transmise 
	 * par la méthode appelante.<br/>
	 */
	private static void loggerError(
			final String pClasse
				, final String pMethode
					, final Exception pException) {
		
		/* Ne fait rien si un des paramètres est null. */
		if (pClasse == null || pMethode == null || pException == null) {
			return;
		}
		
		/* LOG de niveau ERROR. */			
		if (LOG.isErrorEnabled()) {
			
			final String message 
			= pClasse 
			+ SEP_MOINS
			+ pMethode
			+ SEP_MOINS 
			+ pException.getMessage();
			
			LOG.error(message, pException);
			
		}
		
	} // Fin de loggerError(
	 // String pClasse
	 // , String pMethode
	 // , Exception pException).___________________________________________
	
	

	
} // FIN DE LA CLASSE FournisseurArboTraficService.--------------------------
