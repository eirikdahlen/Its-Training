# Gruppe 02 - It's Training
It's Training er en treningsplattform der trenere kan følge opp ulike utøvere. 
Som trener har du tilgang til ulike analyseverktøy for å ha en god oppfølging.
Som utøver har man mulighet til å holde oversikt over sine treningsøkter og sine trenere. 
Treningsplattformen tar i bruk en trener-utøver relasjon der utøver selv kan velge hvem som skal være trener,
og trener kan selv velge hvilke utøvere som ønskes. 

## Kom i gang
HTTPS-link er nødvendig for å kunne klone prosjektet. Denne finnes under prosjektet på GitLab.

Åpne Command Prompt/Terminal og skriv følgende: 

```
git clone https://gitlab.stud.iie.ntnu.no/tdt4140-2018/02.git
```
Mangler du git? Installasjon finner du [her](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git).

## Mappestruktur
Vi har to hovedmapper i prosjektet vårt, app.core og app.ui. 
app.core inneholder all logikk og kode for å knytte elementene sammen. 
Logikk for tilknytning og opplastning til database ligger også i denne mappen.
app.ui inneholder kontrolleren og fxml-filene. 
main/java.app.ui inneholder alle kontrollere, som knytter core opp mot grensesnittet. 
main/resources.ap.ui innholder alle fxml-filene, som bygger opp grensesnittet vårt. 


### Forutsetninger
- Java SE Runtime Environment 8
- Java SE Development Kit 8
- IDE([Eclipse](https://www.eclipse.org/downloads/) er brukt under dette prosjektet)


## Kjøre tester 
Hvordan teste programmet

## Lansere
For å lage en runnable jar fil må man: 
gå til **_File_** -> **_Export.._** -> velge **_Runnable JAR_** -> velge **_fxApp_** under **_Launch Configuration_**.
Deretter er det bare å følge instruksjonene. 

## Bygget med
- Maven
- JavaFX

## Anerkjennelser
Takk til oss, fagstaben for god hjelp på Piazza og våre studentassistenter Andreas og Bartosz for god oppfølging :+1:.  