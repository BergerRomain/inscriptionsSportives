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
        PRIMARY KEY (numCandidat )
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: personne
#------------------------------------------------------------

CREATE TABLE personne(
        nomPersonne          Varchar (25) ,
        prenomPersonne       Varchar (25) ,
        numCandidat          Int NOT NULL ,
        numCandidat_candidat Int ,
        PRIMARY KEY (numCandidat )
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Compétition
#------------------------------------------------------------

CREATE TABLE Competition(
        numCompetition int (11) Auto_increment  NOT NULL ,
        nomCompetition Varchar (25) ,
        dateCloture    Date ,
        PRIMARY KEY (numCompetition )
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: equipe
#------------------------------------------------------------

CREATE TABLE equipe(
        nomEquipe   Varchar (25) ,
        numCandidat Int NOT NULL ,
        PRIMARY KEY (numCandidat )
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: être inscrit
#------------------------------------------------------------

CREATE TABLE etre_inscrit(
        numCandidat    Int NOT NULL ,
        numCompetition Int NOT NULL ,
        PRIMARY KEY (numCandidat ,numCompetition )
)ENGINE=InnoDB;

ALTER TABLE personne ADD CONSTRAINT FK_personne_numCandidat FOREIGN KEY (numCandidat) REFERENCES candidat(numCandidat);
ALTER TABLE personne ADD CONSTRAINT FK_personne_numCandidat_candidat FOREIGN KEY (numCandidat_candidat) REFERENCES candidat(numCandidat);
ALTER TABLE equipe ADD CONSTRAINT FK_equipe_numCandidat FOREIGN KEY (numCandidat) REFERENCES candidat(numCandidat);
ALTER TABLE etre_inscrit ADD CONSTRAINT FK_etre_inscrit_numCandidat FOREIGN KEY (numCandidat) REFERENCES candidat(numCandidat);
ALTER TABLE etre_inscrit ADD CONSTRAINT FK_etre_inscrit_numCompetition FOREIGN KEY (numCompetition) REFERENCES Competition(numCompetition);
