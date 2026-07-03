# Käyttöohjeet

Käyttöohjeet pesäpallon juoksuodottaman keräämiseen. Juoksun ja läpilyönnin todennäköisyydet ovat luotu logistisen regression avulla. 
Mallin data on luotu Superpesiksen datan avulla. Kerääjää voi käyttää alemmilla sarjatasoilla, mutta muilla sarjatasoilla mallin antamat todennäköisyydet voivat olla epätarkkoja.

## Malliin vaikuttavat tekijät ja muodostaminen

Alla olevassa taulukkossa näkyy malliin vaikuttavat muuttujat. Läpilyönnin määritelmänä on, että lyönnillä tulee enemmän kuin yksi kärkilyönti. 
Läpilyönnissä merkki on vain vapaa tai merkattu eli muut merkit kuin vapaa. Lentomerkki on määritelty sen perusteella, mitataanko eteneminen.

| Muuttuja                | Tyyppi                  |
|-------------------------|-------------------------|
| Merkki                  | Kategorinen             |
| Tyyppi                  | Kategorinen             |
| Kuvio                   | Kategorinen             |
| Etenijän laatu          | Kategorinen             |
| Sijainti                | Kategorinen             |
| Etäisyys ulkopelaajasta | Jatkuva                 |
| Kulma ulkopelaajasta    | Jatkuava (0-90 astetta) |


Etäisyyden vaikutus on positiivinen, mitä kauempana pallo otetaan lähtökohdasta kiinni, sitä todennäköisempää on juoksun
syntyminen. Kulmassa suoraan sivuttain on 90 astetta. Suoraan alas- tai ylöspäin on nolla astetta.

## Miten merkataan

Lyönti merkataan lähtökohtaisesti siihen, mistä se otetaan kiinni. Poikkeuksia ovat läpilyönnit, 
ja koppareille lyötävät lyönnit. Läpilyönneissä voidaan merkata kohtaan, mistä pallo menee läpi. Tämä ehdolla, 
ettei ulkopelaajalla ole mitään yritystä saada palloa kiinni. Jos kopparit pysäyttävät palloa, 
pallo merkataan ensimmäisen pompun kohdalla tai kohtaan, jossa on mennyt linjasta läpi. Merkkauksessa kannattaa muistaa, 
että etäisyyden kasvaessa ulkopelaajasta juoksun todennäköisyys kasvaa.