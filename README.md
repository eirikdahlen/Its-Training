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
Vi har tre hovedmapper i prosjektet vårt:


- *app.core* inneholder all logikk og kode for å knytte elementene sammen.
Logikk for tilknytning og opplastning til server/database ligger også i denne mappen.

- *app.ui* inneholder kontrolleren og fxml-filene. 
-- app.ui/src/main/java inneholder alle kontrollere, som knytter core-logikken opp mot grensesnittet. 
-- app.ui/src/main/resources innholder alle fxml-filene, som bygger opp grensesnittet vårt. 

- *web.server* inneholder en server bygget av spring, samt en servercontroller og en klasse som kommuniserer med MongoDB-databasen.


### Forutsetninger
- Java SE Runtime Environment 8
- Java SE Development Kit 8
- IDE([Eclipse](https://www.eclipse.org/downloads/) er brukt under dette prosjektet)

## Instrukser for kjøring
- For at programmet skal kunne kjøre må serveren kjøres samtidig. Serveren startes ved å kjøre filen 'Server.java' filen i 'web.server'-mappen.
- For å starte programmet kan en kjøre jar-filen vi leverte, eller kjøre filen 'FxApp.java' under 'app.ui'-mappen.
- Ved innlogging kan en best se funksjonaliteten med trener-brukeren "petter22" som har passord "petter123"
- Tilgang til utøversiden får en med brukernavn "TeddyWestside" og passord "theodor".


## Kjøre tester 
For å teste core-delen av programmet har vi jUnit-tester som ligger i app.core/src/test/java. 
Her har vi test-klasser som er tilknyttet hver sin klasse i src/main/java. 

For å teste ui-delen av programmet gjøres det ved hjelp av brukertester. 

## Lansere
For å lage en runnable jar fil må man: 
gå til **_File_** -> **_Export.._** -> velge **_Runnable JAR_** -> velge **_fxApp_** under **_Launch Configuration_**.
Deretter er det bare å følge instruksjonene. 

## Bygget med
- [Maven](https://maven.apache.org/)
- JavaFX
- [GMapsFX](https://github.com/rterp/GMapsFX)
- [JPX - Java GPX library](https://github.com/jenetics/jpx)
- [Spring](https://docs.spring.io/spring-boot/docs/current/maven-plugin/usage.html)

## Anerkjennelser
Takk til oss, fagstaben for god hjelp på Piazza og våre studentassistenter Andreas og Bartosz for god oppfølging :+1:.  