import extensions.CSVFile;
import extensions.File;
class GlucoCroco extends Program{

    Aliments[] Entrees = entreeload(); // tableau contenant les entrées
    Aliments[] Plats = platload(); // tableau contenant les plats 
    Aliments[] Desserts = dessertload(); // tableau les desserts 
    int reponseGlucide = 0; // reponse de l'utilisateur au mode1
    int compteur = 0;
    int[] existe = new int[3]; // creation tableau d'entier pour vérifier une saisie
    int idx = 0; // indice pour vérifier entier dans le tableau existe
    int scoreMode1 = 0; // score du mode1
    int scoreMode2 = 0; // socre du mode2
    boolean jouerMode1 = true; // boolean de vérification si le joueur veux jouer au mode1
    boolean jouerMode2 = true; // boolean de vérification si le joueur veux jouer au mode2
    boolean jouer = true; // boolean de vérification si le joueur veux jouer tout court 

    final String CROCOMENU = "../ressources/crocodiles/crocoMenu"; // initialisation du texte menu principal

    final String CHEMINMODE1 = "../ressources/crocodiles/mode1/"; // chemin texte mode1
    final String CHEMINMODE2 = "../ressources/crocodiles/mode2/"; // chemin texte mode2


    final String CROCO1 = "croco1"; // initialisation du texte 1
    final String CROCO2 = "croco2"; // initialisation du texte 2 
    final String CROCO3 = "croco3"; // initialisation du texte 3
    final String CROCOGAGNE = "crocoGagne";  // initialisation de victoire
    final String CROCOPERDU = "crocoPerdu";  // initialisation de défaite 
    final String CROCOPASLOIN = "crocoPasloin";  // initialisation de pas loin 1
    final String CROCOPASLOIN2 = "crocoPasloin2"; // initialisation de pas loin 2
    final String SCOREBOARDS1 = "../ressources/CSV/ScoreboardsMode1.csv";
    final String SCOREBOARDS2 = "../ressources/CSV/ScoreboardsMode2.csv";


    void algorithm(){
        int reponse;
        int mode;

        while(jouer == true){
            crocoParle(CROCOMENU, 0); // affichage menu 
            mode = mode();  // sélection du mode
            if(mode == 1){
                jeuxMODE1();    // mode1
            }

            else if(mode == 2){
                jeuxMODE2();   // mode2 
            }
            affichageSaisieRetourMenu();    // sélection retour menu principal
        }
    }

        // affiche le nombre de glucides que le repas va compter, ainsi que le choix que l'élève devra faire entre entrée plat dessert pour le mode 1.
        int affichageSaisieMenu(int resultat){
            int reponse;
            println("Je souhaite un repas qui comporte " + resultat + " grammes de glucides !!!");
            println();
            delay(1000);
            menu(new String[]{"Entrée", "Plat", "Dessert"});
            println();
            println("Saisissez le numéro de la partie que vous voulez préparer : ");
            print("--> ");
            reponse = readInt();
            while(verification(reponse) == false || existe(existe, reponse) == false){
            println("Veuillez choisir un numéro non sélectionné et existant !!!!");
            print("--> ");
            reponse = readInt();
        }
            existe[idx] = reponse;
            idx = idx + 1;
            return reponse;
    }

    // Affiche les entrées et l'élève choisit
    void affichageSaisieEntree(){
        println(ToString(Entrees));
            println("Choisissez l'entrée que vous voulez faire manger à GlucoCroco :");
            print("--> ");
            int repEntree = readInt();
            reponseGlucide = reponseGlucide + appelgramme(Entrees, repEntree);
            clearScreen();
            println("vous avez choisi " + appelnom(Entrees, repEntree));
            println();
            compteur = compteur +1;
    }

    // Affiche les plats et l'élève choisit
    void affichageSaisiePlat(){
        println(ToString(Plats));
            println("Choisissez le plat que vous voulez faire manger à GlucoCroco :");
            print("--> ");
            int repPlat = readInt();
            reponseGlucide = reponseGlucide + appelgramme(Plats, repPlat);
            clearScreen();
            println("vous avez choisi " + appelnom(Plats, repPlat));
            println();
            compteur = compteur +1;
    }

    // Affiche les desserts et l'élève choisit
    void affichageSaisieDessert(){
        println(ToString(Desserts));
            println("Choisissez le dessert que vous voulez faire manger à GlucoCroco :");
            print("--> ");
            int repDessert = readInt();
            reponseGlucide = reponseGlucide + appelgramme(Desserts, repDessert);
            clearScreen();
            println("vous avez choisi " + appelnom(Desserts, repDessert));
            println();
            compteur = compteur +1;
    }

    // Affiche la fin du jeu, perdre, pas loin ou gagner, ainsi que le nombre de glucides que le repas comportais et le nombre de glucides qu'il devait comporter
    //et le score. (mode de jeu 1)
    void affichageFinMode1(int resultat){
        if(reponseGlucide == resultat){
            crocoParle(CHEMINMODE1+CROCOGAGNE, 1000);
            scoreMode1 = scoreMode1 + 2;
        }
        else if(reponseGlucide <= resultat + (resultat*10/100) && reponseGlucide >= resultat - (resultat*10/100)){
            crocoParle(CHEMINMODE1+CROCOPASLOIN, 1000);
            crocoParle(CHEMINMODE1+CROCOPASLOIN2, 1000);
            scoreMode1 = scoreMode1 + 1;
        }
        else{
            crocoParle(CHEMINMODE1+CROCOPERDU, 1000);
        }   
        println();
        println("Votre repas comportais " + reponseGlucide + "g");
        println("Il devait comporter " + resultat + "g");
        println();
        println("Ton score est de : " + scoreMode1);
    }

    // initialise le menu rejouer du mode 1 Y pour oui N pour non et remet le score a 0 si oui. Réinitialise le tableau fait pour vérifier si nous avons déjà
    // choisit entrée plat ou dessert
    void affichageSaisieRejouerMode1(){
        boolean rejouer = rejouer(scoreMode1);
        if(rejouer == true){
            jouerMode1 = true;
            compteur = 0;
            reponseGlucide = 0;
            idx = 0;
            int[] NewExiste = new int[3];
            existe = NewExiste;
            scoreMode1 = scoreMode1;
        }
        else{
            jouerMode1 = false;
            compteur = 0 ;
            reponseGlucide = 0;
            idx = 0;
            int[] NewExiste = new int[3];
            existe = NewExiste;
            println("Vous vous enregistrer votre score ?  (Y/N)");
            print("--> ");
            char enregistrer = readChar();
            while(enregistrer != 'Y' && enregistrer != 'N'){
                println("Veuillez sélectioner Y (pour oui) ou N (pour non) : ");
                print("--> ");
                enregistrer = readChar();
            }
            if(enregistrer == 'Y'){
                println("Veuillez saisir votre nom (nombre de caractère maximum 10): ");
                print("--> ");
                String nom = readString();
                while(length(nom)==0 || length(nom) > 10){
                    println("Choisissez un nom avec moins de 10 caractères");
                    print("-->");
                    nom = readString();
                }
                for(int i = length(nom); i<10; i++){
                    nom=nom+" ";
                }
                saveGame(scoreboard(SCOREBOARDS1), nom, scoreMode1, SCOREBOARDS1);
            }
            scoreMode1 = 0;
        }
        println("          -------------------");
        println("                TABLEAU      ");
        println("                  DES        ");
        println("                 SCORES      ");
        println("          -------------------");
        println();
        println();
        println(ToString(SCOREBOARDS1));
        }
    

    // Mode de jeu 2, affiche ce que le crocodile va manger et l'élève devra répondre le nombre de glucides, affiche ensuite si il a gagné, pas loin ou perdu
    // en affichant la bonne réponse et ce qu'il a choisi.
    void affichageSaisieMode2(){
            int idxEntree = randomEntrees();
            int idxPlat = randomPlats();
            int idxDessert =  randomDesserts();
            Aliments[] menu = new Aliments[]{Entrees[idxEntree], Plats[idxPlat], Desserts[idxDessert]};
            int total = calculGlucides(menu);
            println("1- " + appelnom(Entrees, idxEntree) + "  2- " + appelnom(platload(), idxPlat) 
            + "  3- " + appelnom(dessertload(), idxDessert));
            

            println();
            println("Quelle est votre réponse :");
            print("--> ");
            int reponseMode2 = readInt();
            if(reponseMode2 == total){
                crocoParle(CHEMINMODE2+CROCOGAGNE, 1000);
                scoreMode2 = scoreMode2 + 2;
                println();
                println();
                println("1- " + appelnom(Entrees, idxEntree) + " = " + appelgramme(Entrees, idxEntree)+"g");
                println("2- " + appelnom(Plats, idxPlat) + " = " + appelgramme(Plats, idxPlat)+"g");
                println("3- " + appelnom(Desserts, idxDessert) + " = " + appelgramme(Desserts, idxDessert)+"g");
                println();
                println("Ton score est de : " + scoreMode2);
            }
            else if(reponseMode2 <= total + (total*10/100) && reponseMode2 >= total - (total*10/100)){
            crocoParle(CHEMINMODE2+CROCOPASLOIN, 1000);
            crocoParle(CHEMINMODE2+CROCOPASLOIN2, 1000);
            scoreMode2 = scoreMode2 + 1;
            println();
            println();
            println("La bonne réponse était : " + total+"g");
            println("Ta réponse était : " + reponseMode2 +"g");
            println();
            println();
            println("1- " + appelnom(Entrees, idxEntree) + " = " + appelgramme(Entrees, idxEntree)+"g");
            println("2- " + appelnom(Plats, idxPlat) + " = " + appelgramme(Plats, idxPlat)+"g");
            println("3- " + appelnom(Desserts, idxDessert) + " = " + appelgramme(Desserts, idxDessert)+"g");
            println();
            println("Ton score est de : " + scoreMode2);
        }
            else{
                crocoParle(CHEMINMODE2+CROCOPERDU, 1000);
                println();
                println();
                println("La bonne réponse était : " + total+"g");
                println("Ta réponse était : " + reponseMode2 +"g");
                println();
                println();
                println("1- " + appelnom(Entrees, idxEntree) + " = " + appelgramme(Entrees, idxEntree)+"g");
                println("2- " + appelnom(Plats, idxPlat) + " = " + appelgramme(Plats, idxPlat)+"g");
                println("3- " + appelnom(Desserts, idxDessert) + " = " + appelgramme(Desserts, idxDessert)+"g");
                println();
                println("Ton score est de : " + scoreMode2);

            }
    }

    // initialise le menu rejouer du mode 2 Y pour oui N pour non et remet le score a 0 si oui.
    void affichageSaisieRejouerMode2(){
        boolean rejouer = rejouer(scoreMode2);
        if(rejouer == true){
            jouerMode2 = true;
            scoreMode2 = scoreMode2;
        }
        else{
            jouerMode2 = false;
            println("Vous vous enregistrer votre score ?  (Y/N)");
            print("--> ");
            char enregistrer = readChar();
            while(enregistrer != 'Y' && enregistrer != 'N'){
                println("Veuillez sélectioner Y (pour oui) ou N (pour non) : ");
                print("--> ");
                enregistrer = readChar();
            }
            if(enregistrer == 'Y'){
                println("Veuillez saisir votre nom (nombre de caractère maximum 10): ");
                print("--> ");
                String nom = readString();
                while(length(nom)==0 || length(nom) > 10){
                    println("Choisissez un nom avec moins de 10 caractères");
                    print("-->");
                    nom = readString();
                }
                for(int i = length(nom); i<10; i++){
                    nom=nom+" ";
                }
                saveGame(scoreboard(SCOREBOARDS2), nom, scoreMode2, SCOREBOARDS2);
            }
            scoreMode2 = 0;
        }
        println("          -------------------");
        println("                TABLEAU      ");
        println("                  DES        ");
        println("                 SCORES      ");
        println("          -------------------");
        println();
        println();
        println(ToString(SCOREBOARDS2));
    }

    // Initialise l'affichage du retour au menu principal selon le mode auquel l'élève joue
    void affichageSaisieRetourMenu(){
        if(menu() == false){
            jouer = false;
        }
        else{
            jouer = true;
            jouerMode1 = true;
            jouerMode2 = true;
        }
    }

    // Affiche la suite d'ascii art d'intro où le crocodile parle
    void intro(String chemin){
        crocoParle(chemin+CROCO1, 3000);
        crocoParle(chemin+CROCO2, 3000); 
        crocoParle(chemin+CROCO3, 3000); 
    }

    // Initialise le mode de jeu n°2 et le fais tourner
    void jeuxMODE2(){
        while(jouerMode2 == true){
                    
                    intro(CHEMINMODE2);
                     

                    affichageSaisieMode2();
                    affichageSaisieRejouerMode2();
                }   
    }

    // Initialise le mode de jeu n°1 et le fais tourner
    void jeuxMODE1(){
        while(jouerMode1 == true){
                int resultat = nbglucide();
                int reponse;
                intro(CHEMINMODE1);

                while(compteur != 3){       
                    reponse = affichageSaisieMenu(resultat);
                    if (reponse == 1){
                        affichageSaisieEntree();
                    }

                    else if (reponse == 2){
                        affichageSaisiePlat();
                    }

                    else if (reponse == 3){
                        affichageSaisieDessert();
                    }   
                }
                affichageFinMode1(resultat);
                affichageSaisieRejouerMode1();
        }            
    }


    // Initialise le texte que le crocodile dira avec un délai.
    void texte(int temps, String txt){
        println(txt);
        delay(temps);
    }
    
    // initialise le menu (entrées, plats, desserts)
    String menu(String[] tab){
        for(int i=0; i<length(tab);i++){
            println("       - " + (i+1) + " " + tab[i]);
        }
        return "\n";
    }  
    
    // Fonction qui permettra de créer l'aliment dans le jeu depuis le fichier CSV
    Aliments newAliments(String nom, int gramme){
        Aliments a = new Aliments();
        a.nom = nom;
        a.gramme = gramme;
        return a;
    }     

    // Crée les entrées à partir du fichier CSV 
    Aliments[] entreeload(){
        CSVFile f = loadCSV("../ressources/CSV/Entrees.csv");
        int r = rowCount(f);
        int c = columnCount(f);
        Aliments[] entree = new Aliments[r];
        for(int i = 0; i<r; i++){
            String nom = getCell(f , i , 0);
            int gramme = stringToInt(getCell(f, i, 1));
            Aliments a = newAliments(nom, gramme);
            entree[i] = a;
        }
        return entree;
     }

    // Crée les plats à partir du fichier CSV 
     Aliments[] platload(){
        CSVFile f = loadCSV("../ressources/CSV/Plats.csv");
        int r = rowCount(f);
        int c = columnCount(f);
        Aliments[] plat = new Aliments[r];
        for(int i = 0; i<r; i++){
            String nom = getCell(f , i , 0);
            int gramme = stringToInt(getCell(f, i, 1));
            Aliments a = newAliments(nom, gramme);
            plat[i] = a;
        }
        return plat;
     }

    // Crée les desserts à partir du fichier CSV 
     Aliments[] dessertload(){
        CSVFile f = loadCSV("../ressources/CSV/Desserts.csv");
        int r = rowCount(f);
        int c = columnCount(f);
        Aliments[] dessert = new Aliments[r];
        for(int i = 0; i<r; i++){
            String nom = getCell(f , i , 0);
            int gramme = stringToInt(getCell(f, i, 1));
            Aliments a = newAliments(nom, gramme);
            dessert[i] = a;
        }
        return dessert;
     }

    // Initialise L'affichage des aliments.
     String ToString(Aliments[] tab){
        String resultat = "";
            for(int i = 0; i<length(tab); i++){
                resultat = resultat + ("       " + i + "- " + tab[i].nom);
                if(i==length(tab)/2){
                    resultat = resultat + "\n" + "\n";
                }
            }
        return resultat + "\n";
     }

    // Appelle le nombre de gramems que l'aliment contient.
     int appelgramme(Aliments[] tab, int i){
        int resultat = tab[i].gramme;
        return resultat;
     }

     void testappelgramme(){
        assertEquals(6 ,appelgramme(Entrees, 2));
        assertEquals(27 ,appelgramme(Plats,  0));
        assertEquals(28 ,appelgramme(Desserts,  5));
     }

    // Appelle le nom de l'aliment.
     String appelnom(Aliments[] tab, int i){
        return tab[i].nom;
     }

     void testappelnom(){
        assertEquals("Tomate" ,appelnom(Entrees, 5));
        assertEquals("Pizza" ,appelnom(Plats,  7));
        assertEquals("Gaufre" ,appelnom(Desserts,  9));
     }

    
     boolean verification(int i){
        boolean resultat = false;
        if(i == 1 || i == 2 || i ==3){
            resultat = true;
        }
        return resultat;
    }

    void testVerification(){
        assertTrue(verification(1));
        assertFalse(verification(5));
    }

    // Fonction qui empechera l'élève de choisir plusieurs fois un même service en mettant un choix deja fait dans un tableau et verifiant si 
    //il est deja dans ce tableau.
    boolean existe(int[] tab, int i){
        int j = 0;
        boolean resultat = true;
        while(resultat == true && j<length(tab)){
            if(i == tab[j]){
                resultat = false;
            }
            else{
                j = j+1;
            }
        }
        return resultat;
    }

    void testExiste(){
        int[] tab1 = new int[]{1, 0, 3};
        int[] tab2 = new int[]{1, 2, 3};
        assertTrue(existe(tab1, 2));
        assertFalse(existe(tab2, 3));
    }

    // Initialise l'entrée qui sera choisie aléatoirement pour le mode de jeu 2
    int randomEntrees(){
        CSVFile f = loadCSV("../ressources/CSV/Desserts.csv");
        int r = rowCount(f);
        double D = random(); 
        int alea = (int) (D*r);
        return alea;
    }

    // Initialise l'entrée qui sera choisie aléatoirement pour le mode de jeu 2
    int randomPlats(){
        CSVFile f = loadCSV("../ressources/CSV/Plats.csv");
        int r = rowCount(f);
        double D = random(); 
        int alea = (int) (D*r);
        return alea;
    }

    // Initialise l'entrée qui sera choisie aléatoirement pour le mode de jeu 2
    int randomDesserts(){
        CSVFile f = loadCSV("../ressources/CSV/Desserts.csv");
        int r = rowCount(f);
        double D = random(); 
        int alea = (int) (D*r);
        return alea;
    }

    // Fonction qui affichera l'ascii art du crocodile qui parle selon un texte et un délai donné
    void crocoParle(String ascii, int delai){
        clearScreen();
        File unTexte = newFile(ascii);
        while(ready(unTexte)){
            println(readLine(unTexte));
        }
        delay(delai);
    }

    // Choisit un noumbre de glucides aléatoire entre 11 et 138 (minimum et maximum de glucides possible en un repas)
    int nbglucide(){
        int reponse;
        double D = random();
        int alea = (int) (D*138);
        if(alea < 11){
            alea = alea + 11;
        }

        return alea;
    }

    // initialise l'affichage et prend en compte le choix du joueur du menu rejouer. Affichage différent selon si le score est égal a 0 ou non.
    boolean rejouer(int score){
        boolean resultat = false;
        char recommencer;
        if(score > 0){
            println("Voulez vous continuer ? (Y/N)");
            print("--> ");
            recommencer = readChar();
            while(recommencer != 'Y' && recommencer != 'N'){
                println("Veuillez sélectioner Y (pour oui) ou N (pour non) : ");
                print("--> ");
                recommencer = readChar();
            }
            if(recommencer == 'Y'){
                resultat = true;
            }
            else if(recommencer == 'N'){
                resultat = false;
            }
        }
        else{
            println("Voulez vous recommencer ? (Y/N)");
            print("--> ");
            recommencer = readChar();
                while(recommencer != 'Y' && recommencer != 'N'){
                println("Veuillez sélectioner Y (pour oui) ou N (pour non) : ");
                print("--> ");
                recommencer = readChar();
            }
            if(recommencer == 'Y'){
                resultat = true;
            }
            else if(recommencer == 'N'){
                resultat = false;
            }
        }
        return resultat;
    }

    // Initialise l'affichage du retour au menu ou de quitter le jeu.
    boolean menu(){
        boolean resultat = false;
        char menu;
        println("voulez-vous retourner au menu principal ? (Y/N)");
        print("--> ");
        menu = readChar();
        while(menu != 'Y' && menu != 'N'){
                println("Veuillez sélectioner Y (pour oui) ou N (pour non) : ");
                print("--> ");
                menu = readChar();
            }
            if(menu == 'Y'){
                resultat = true;
            }
            else if(menu == 'N'){
                resultat = false;
            }
            return resultat;
    }

    // fonction du choix du mode.
    int mode(){
        int mode;
        println("Sélectionner mode : ");
        print("--> ");
        mode = readInt();
         while(mode != 1 && mode != 2){
                println("Veuillez sélectioner 1 ou 2 : ");
                print("--> ");
                mode = readInt();
            }
            return mode;
    }


    // fonction qui calculera le nombre de glucides total dans un repas.
    int calculGlucides(Aliments[] menu){
        int total = 0;
        for(int i = 0; i<length(menu); i++){
            total = total + appelgramme(menu, i);
        }
        return total;
    }

    // fonction qui permet de charger un fichier Scoreboards.CSV et le rentrer dans un tableau en y ajoutant une nouvelle case.
    String[][] scoreboard(String scoreboards){
        CSVFile f = loadCSV(scoreboards);
        int r = rowCount(f);
        int c = columnCount(f);
        String[][] scoreboard = new String[r+1][c];
        for(int i = 0; i<r; i++){
            for(int j = 0; j<c; j++){
                scoreboard[i][j] = getCell(f , i , j);
            }
        }
        return scoreboard;
    }

    // fonction qui permet d'enregistrer dans un tableau puis dans le CSV correspondant le score et le nom de l'utilisateur.
    void saveGame(String[][] content, String nom, int score , String scoreboard){
		content[length(content, 1)-1] = new String[]{ nom, "  "+score + "  " };
		saveCSV(content, scoreboard);
	}

    // fonction qui permet d'affcher un fichier Scoreboards.CSV.
    String ToString(String scoreboards){
        String resultat = "";
        CSVFile f = loadCSV(scoreboards);
        int r = rowCount(f);
        int c = columnCount(f);
        String[][] scoreboard = new String[r][c];
        for(int i = 0; i<r; i++){
            for(int j = 0; j<c; j++){
                scoreboard[i][j] = getCell(f , i , j);
            }
        }
        for(int k = 0; k<r; k++){
            for(int l = 0; l<c; l++){
                resultat = resultat + " | " +scoreboard[k][l];
            }
            resultat = resultat + " |" + "\n";
        }
        return resultat;
    }
}
     

     