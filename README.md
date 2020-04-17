# RollingDiceKata

## Description

En tant que joueur de D&D, je souhaiterai pouvoir configurer un jet de dés en rentrant une chaîne simple de caractères.
Un lancer de dés est soumis aux règles suivantes :
  - Un lancer peut être décrit par la formule suivante : \
  <NombreDeLancers>[d]<ValeurDuDé>[+-]\<Modificateur>\  (ex: "2d6+1")
  - Le "+ ou -" et le modificateur sont optionnels (ex : "3d8" est une formule valable)
  - Le résultat d'un lancer ne peut pas être négatif (sauf dans le cas où la formule est incorrecte), en cas de résultat négatif le résultat est 0
  - Un lancer basé sur unee mauvaise formule retournera toujours la valeur -1
  - Un lancer ne peut pas avoir une valeur négative pour le nombre de lancers et pour la valeur du dé, si c'est le cas la formule est considérée incorrecte et le lancer retournera -1 (ex : "-1d8" est une formule incorrecte)
  #####Optionnel:
  - Il existe trois façons de faire un lancer:
    - Normal : un seul lancer est effectué
    - Avec Avantage : 2 lancers sont faits et le meilleur lancer est pris (pour 3d8 on lancera deux fois 3d8 et on prendra le meilleur résultat)
    - Avec Désavantage : 2 lancers sont faits et le moins bon est pris
  - Attention les modificateurs sont appliqués après le/les lancer(s) !!
  
## Guidelines

Commencez par implémenter le constructeur ```Roll(int DiceValue, int nbRoll, int modifier)``` et la méthode de classe ```public int roll(RollType type)``` avant de vous atteler au parsing de la formule.
Quand les premiers tests seront verts vous pourrez passer à la suite.
Aussi, les expressions régulières peuvent être une bonne idée...
  
## Setup

  - Importer le projet forké dans IntelliJ Idea : File > New > Project from Version Control > Git > Rentrer l'url du dépôt 
  - Clic droit sur le dossier java > Mark Directory as Root Source
  - File > Project Structure > renseigner votre jdk et votre fichier de sortie <VOTRE_WORKING_DIR/out>
  - Run > Edit Configuration > ajouter une Application > Renseigner votre jdk si ce n'est pas déjà fait
  - Pour ajouter JUnit, la façon la plus simple est la suivante : allez dans le fichier java > test > RollTest > alt-Enter sur un @Test rouge > add JUnit to classpath
  - Implémenter la classe Roll
  - Jouer les tests dans java > RollTest
  - Pousser votre pull request sur le dépôt :  git push -u origin <VOTRE_NOM_ET_PRENOM>
