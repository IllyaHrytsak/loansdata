**Develop REST client:**<br />
Download test data and store to DB by request: http://localhost:8080/api/sync<br />
I want to be able to search in DB or in Raw text Data (have 2 implementations):<br />
- get all data by query: http://localhost:8080/api/data/file<br />
- filter data by any combinations of predicates:<br />
- GENDER<br />
- YEAR OF BIRTH<br />
- LOAN STATE<br />
All responses has to be array of jsons<br />
**Examples of queries:**<br />
http://localhost:8080/api/data/file/gender/male<br />
http://localhost:8080/api/data/db/gender/female<br />
http://localhost:8080/api/data/db/year/1999<br />
http://localhost:8080/api/data/db/year/1999/gender/male/state/late<br />
http://localhost:8080/api/data/file/gender/female/state/current/state/repaid<br />
Log to log.file any income HTTP request(path, headers, body, etc)<br />
**Test coverage**<br />
Use:<br />
- Spring Core<br />
- Spring MVC<br />
- Spring Data + (Any db: postgres, muysql, InmemoryDB, etc)<br />
- JUnit<br />
testdata :<br />
https://s3-eu-west-1.amazonaws.com/b2bmobiledata.fidomockapp/dev/loans_data.csv<br />
schema : https://www.kaggle.com/diogo22santos/loans-data<br />

For starting:<br />
_1) mvn clean install_<br />
_2) mvn tomcat7:run_