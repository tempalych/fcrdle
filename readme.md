# ⚽️ FCRDLE - like WORDLE, but about football.

Try to guess football club by League, stadium's location and total capacity.

Live demo: https://mtema.site/

### Modules
`server`: backend. Spring Boot Application, with PostgreSQL datasource and simple REST endpoits.

`client`: frontend. VueJS Application.

`wikidata`: data actualisation service. Takes data from https://query.wikidata.org, using SPARQL query and matches data with Database.