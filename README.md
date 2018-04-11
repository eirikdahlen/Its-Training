# Gruppe 02 - It's Training
It's Training er en treningsplattform der trenere kan følge opp ulike utøvere. 
Som trener har du tilgang til ulike analyseverktøy for å ha en god oppfølging.
Som utøver har man mulighet til å holde oversikt over sine treningsøkter og sine trenere. 
Treningsplattformen tar i bruk en trener-utøver relasjon der utøver selv kan velge hvem som skal være trener,
og trener kan selv velge hvilke utøvere som ønskes. 

Dette prosjektet er en del av faget [TDT4140](https://www.ntnu.no/studier/emner/TDT4140#tab=omEmnet) og er basert på krav deretter.

## Kom i gang
HTTPS-link er nødvendig for å kunne klone prosjektet. Denne finnes under prosjektet på GitLab.

Åpne Command Prompt/Terminal og skriv følgende: 

```
git clone https://gitlab.stud.iie.ntnu.no/tdt4140-2018/02.git
```
Mangler du git? Installasjon finner du [her](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git).

## Mappestruktur
Vi har to hovedmapper i prosjektet vårt:

app.core, 
app.ui


app.core inneholder all logikk og kode for å knytte elementene sammen.
Logikk for tilknytning og opplastning til database ligger også i denne mappen.

app.ui inneholder kontrolleren og fxml-filene. 

app.ui/src/main/java inneholder alle kontrollere, som knytter core-logikken opp mot grensesnittet. 
app.ui/src/main/resources innholder alle fxml-filene, som bygger opp grensesnittet vårt. 


### Forutsetninger
- Java SE Runtime Environment 8
- Java SE Development Kit 8
- IDE([Eclipse](https://www.eclipse.org/downloads/) er brukt under dette prosjektet)


## Kjøre tester 
For å teste core-delen av programmet har vi jUnit-tester som ligger i app.core/src/test/java. 
Her har vi test-klasser som er tilknyttet hver sin klasse i src/main/java. 

For å teste ui-delen av programmet gjøres det ved hjelp av brukertester. 

## Lansere
For å lage en runnable jar fil må man: 
gå til **_File_** -> **_Export.._** -> velge **_Runnable JAR_** -> velge **_fxApp_** under **_Launch Configuration_**.
Deretter er det bare å følge instruksjonene. 

## Bygget med
- Maven
- JavaFX

## Anerkjennelser
Takk til oss, fagstaben for god hjelp på Piazza og våre studentassistenter Andreas og Bartosz for god oppfølging :+1:.  