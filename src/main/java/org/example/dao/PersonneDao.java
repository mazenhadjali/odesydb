package org.example.dao;

import org.example.entities.Personne;

import java.util.List;

public interface PersonneDao {

    boolean doesPersonExist(String cin);
    Long add(Personne personne);

    List<Personne> getAll();

    long countPersonneMale(String gouvernorat, String delegation, String secteur, String complexeRes);

    long sumNbHandicap(String gouvernorat, String delegation, String secteur, String complexeRes);

    long countPersonneFemale(String gouvernorat, String delegation, String secteur, String complexeRes);

    List<String> getAllGouvernorat();

    List<String> getAllDelegationByGouvernorat(String selectedGouvernorat);

    List<String> getAllsecteurByDelegationByGouvernorat(String selectedGouvernorat, String selectedDelegation);

    List<String> getAllComplexeResBySecteurByDelegationByGouvernorat(String selectedGouvernorat, String selectedDelegation, String selectedSecteur);

    int countPersonsByScolarite(int sexe, int scolariteValue, String gouvernorat, String delegation, String secteur, String complexeRes);

    int countPersonsByActPrincipale(int sexe, int actPrincipaleValue, String gouvernorat, String delegation, String secteur, String complexeRes);

    int countPersonsByEtatSociale(int sexe, int etatSocialeValue, String gouvernorat, String delegation, String secteur, String complexeRes);

    int countPersonsByExploitation(int sexe, int exploitationValue, String gouvernorat, String delegation, String secteur, String complexeRes);

    int countPersonsByMiniProjet(int sexe, int miniProjetValue, String gouvernorat, String delegation, String secteur, String complexeRes);

    int countPersonsByStruct_pro(String gouvernorat, String delegation, String secteur, String complexeRes);

}
