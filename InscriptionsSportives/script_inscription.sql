#------------------------------------------------------------
#        Script MySQL.
#------------------------------------------------------------

drop table candidat;
drop table personne;
drop table Competition;
drop table equipe;
drop table etre_inscrit;

#------------------------------------------------------------
# Table: candidat
#------------------------------------------------------------

CREATE TABLE candidat(
        numCandidat int (11) Auto_increment  NOT NULL ,
        nomCandidat Varchar (25),
        PRIMARY KEY (numCandidat )
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: personne
#------------------------------------------------------------

CREATE TABLE personne(
	    numPersonne          Int,
        prenomPersonne       Varchar (25),
        mail 				 Varchar (50),
        PRIMARY KEY (numCandidat )
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Compétition
#------------------------------------------------------------

CREATE TABLE Competition(
        numCompetition int (11) Auto_increment  NOT NULL ,
        nomCompetition Varchar (25) ,
        dateCloture    Date ,
        enEquipe	   boolean,
        PRIMARY KEY (numCompetition)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: equipe
#------------------------------------------------------------

CREATE TABLE equipe(
        numEquipe Int,
        PRIMARY KEY (numCandidat)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: être inscrit
#------------------------------------------------------------

CREATE TABLE etre_inscrit(
        numCandidat    Int,
        numCompetition Int,
        PRIMARY KEY (numCandidat ,numCompetition)
)ENGINE=InnoDB;

#------------------------------------------------------------
# Table: être dans une equipe
#------------------------------------------------------------

CREATE TABLE etre_dans_une_equipe(
        numEquipe      Int,
        numPersonne    Int,
        PRIMARY KEY (numEquipe ,numPersonne)
)ENGINE=InnoDB;


ALTER TABLE personne ADD CONSTRAINT FK_personne_numCandidat FOREIGN KEY (numPersonne) REFERENCES candidat(numCandidat);
ALTER TABLE equipe ADD CONSTRAINT FK_equipe_numCandidat FOREIGN KEY (numEquipe) REFERENCES candidat(numCandidat);
ALTER TABLE etre_inscrit ADD CONSTRAINT FK_etre_inscrit_numCandidat FOREIGN KEY (numCandidat) REFERENCES candidat(numCandidat);
ALTER TABLE etre_inscrit ADD CONSTRAINT FK_etre_inscrit_numCompetition FOREIGN KEY (numCompetition) REFERENCES Competition(numCompetition);
ALTER TABLE etre_dans_une_equipe ADD CONSTRAINT FK_etre_dans_une_equipe_numEquipe FOREIGN KEY (numEquipe) REFERENCES candidat(numCandidat);
ALTER TABLE etre_dans_une_equipe ADD CONSTRAINT FK_etre_dans_une_equipe_numPersonne FOREIGN KEY (numPersonne) REFERENCES candidat(numCandidat);
