# Käyttöohjeet

Tässä on käyttöohjeet pesäpallon juoksuodottaman keräämiseen. Juoksun ja läpilyönnin todennäköisyydet ovat luotu logistisen regression avulla.
Kerääjää voi käyttää alemmilla sarjatasoilla, mutta muilla sarjatasoilla kuin ykköspesiksessä ovat todennäköisesti epätarkkoja todennäköisyyksiä
juoksuille.

## Malliin vaikuttavat tekijät ja muodostaminen

Läpilyönnin määritelmänä on, että lyönnillä tulee enemmän kuin yksi kärkilyönti.Läpilyönnissä merkki on vain vapaa tai merkattu. 
Lentomerkki on määritelty sen perusteella, mitataanko eteneminen.

| Muuttuja                | Tyyppi      |
|-------------------------|-------------|
| Merkki                  | Kategorinen |
| Tyyppi                  | Kategorinen |
| Kuvio                   | Kategorinen |
| Etenijän laatu          | Kategorinen |
| Sijainti                | Kategorinen |
| Etäisyys ulkopelaajasta | Jatkuva     |

Etäisyyden vaikutus on positiivinen, mitä kauempana pallo otetaan lähtökohdasta kiinni, sitä todennäköisempää on juoksun
syntyminen.

## Miten merkataan

Lyönti merkataan lähtökohtaisesti siihen, mistä se otetaan kiinni. Poikkeuksia ovat läpilyönnit, 
ja koppareille lyötävät lyönnit. Läpilyönneissä voidaan merkata kohtaan, mistä pallo menee läpi. Tämä ehdolla, 
ettei ulkopelaajalla ole mitään yritystä saada palloa kiinni. Jos kopparit pysäyttävät palloa, 
pallo merkataan joko ensimmäiseen maahan tulokohtaan tai kohtaan, jossa on mennyt linjasta läpi. Merkatessa kannattaa muistaa, 
että kauempana ulkopelaajasta todennäköisyys on suurempi.