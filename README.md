## Pesapalloxr

Pesäpalloxr on pesäpalloon luotu kerääjä kotiutustilanteita varten. Käyttäjä voi merkata lyönnnin, jonka avulla
lasketaan todennäköisyys juoksulle ja kuinka todennäköisesti se menee läpi. Riippuen kysynnästä ja ajasta kerääjään
voidaan lisätä muut tilanteet. Ohjelmisto on beta-vaiheessa, kun menee sen ohi versionumero alkaa numerolla yksi.

### Käyttö

Lataa zip-kansio Releases kohdasta ja pura haluttuun paikkaan. Avaa kansio ja sieltä bin.bat ja Kaynnista.

Tulossa rakenna itse ohjelmisto

### Mallit ja merkkaus

Mallit ovat luotu miesten ja naisten (tulee myöhemmin) Superpesiksen datan avulla. Kerääjää voi käyttää alemmilla
sarjatasoilla, mutta muilla sarjatasoilla kuin ykköspesiksessä ovat todennäköisesti epätarkkoja todennäköisyyksiä
juoksuille.

Juoksun ja läpilyönnin todennäköisyydet ovat luotu logistisen regression avulla. Läpilyönnin määritelmänä on, että
lyönnillä tulee enemmän kuin yksi kärkilyönti. Mallit ottavat huomioon lyönnin tyypin, merkin (määriteltu muiden
muuttujien avulla), ulkopelikuvion, sijainti (etukenttä (jaettu kahteen osaan), linja ja takakenttä), lyönnin etäisyys
lähimmäisestä pelaajasta ja ulkopelipaikan yhteisvaikutus ja etenijän laatu (jaettu viiteen kategoriaan juoksunopeuden
perusteella). Läpilyönnissä merkki on vain vapaa tai merkattu. Suoraan syötöstä merkki on määritelty sen perusteella,
mitataanko eteneminen.

Lyönnit merkataan yleisesti siihen kohtaan, mistä ulkopelaaja ottaa sen kiinni. Jos lyönti menee kopparille
ulkopelivirheen toimesta eli ulkopelaaja ei saa kiinni tai on yrittää palloon loppuun asti, lyönti merkataan
ulkopelivirheen kohtaan tai yrityksen kohtaan. Saumalyönneissä
käytetään samaa logiikkaa. Jos ulkopelaaja yrittää palloon merkataan siihen kohtaan, muutoin merkataan takakentälle
lyönti alla olevien ohjeiden mukaan.

Poikkeuksena ovat kopparit. Koppareilla merkataan ulkopelisuorituksen kohdalla kiinniottokohtaan. Kopparin pysäyttäessä
kohtaan, jossa lyönti on käynyt maahan. Poikkeuksena jatkelyönnit, jolloin ne voi merkata kiinniottokohtaan tai edellä
mainitulla tavalla.

### UKK

Tulossa myöhemmin, jos on tulee yleisiä kysymyksiä

### Lisenssi

Ohjelmistossa on MIT:n lisenssi.