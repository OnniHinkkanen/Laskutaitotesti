# Laskutaitotesti

Tämä ohjelma luo halutun määrän laskutaitotestejä kurssille Calculus 1. Ohjelman ajaminen onnistuu joko komentoriviltä tai 
tuplaklikkaamalla run.bat -tiedostoa. Ajon konfiguraatiota voi muokata muokkaamalla run.bat -tiedostoa.

## Vaatimukset
Tämä ohjelma vaatii Maximan asennusta koneelle. Lisäksi, jos halutaan kääntää luodut .tex-tiedostot PDFiksi, tulee olla
asennettuna myös MiKTeX. Java-ohjelmana vaatii tietysti myös Javan asennuksen.

### Käytössä olevat parametrit
Näillä voi muokata ohjelman ajoa
 - -pdf Tällä komennolla voi pyytää ohjelmaa kääntämään laskutaitotestit PDFiksi
 - -tests Kuinka monta testiä luodaan. Oletuksena on 20.
 - --maxima-path Polku, josta Maxima-asennus löytyy. Jos Maxima löytyy ympäristömuuttujasta PATH, ei tätä tarvitse
 - --template-path Polku, josta löytyy LaTeX-template testille. Ei suositella käytettävän; jos .jar on samassa kansiossa kuin template.tex, toimii ilman.
 - -path Oletuksena sama polku, jossa .jar sijaitsee. Polku kansioon, jonne luo testit. Ei suositella käytettänäm

Esimerkki siitä, miltä run.bat:n sisältö voisi näyttää
~~~
cls
@pushd %~dp0
:::::::::::::::::::
java -jar laskutaitotesti.jar -pdf -tests 50 --maxima-path C:\devel\maxima-5.41.0\bin\
:::::::::::::::::::
@popd
pause
~~~
Tämä luo 50 testiä ja kääntää ne PDFiksi käyttäen em. kansiosta löytyvää Maximaa.

~~~
cls
@pushd %~dp0
:::::::::::::::::::
java -jar laskutaitotesti.jar
:::::::::::::::::::
@popd
pause
~~~
Ajaa ohjelman oletusasetuksilla. Luo kaksikymmentä testiä, ei käännä niitä. Vaatii Maximan polun olevan asetettu PATH-ympäristömuuttujaan ja tiedostojen olevan samassa kansiossa.
