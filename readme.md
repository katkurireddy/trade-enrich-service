Application runs on port 10001 : http://localhost:10001/api/v1/enrich

Application verifies the date, price, currency of the given trade data. If anything is wrong in the data, it logs the error and goes on to process next row.

Example CURL:

curl --form file='@Book1.csv'  http://localhost:10001/api/v1/enrich

#Further Improvements :
Cache:
Application is using default Cache config and spring default concurrent hash map. This can be improved with good eviction 
policies and cache manager like EhCache/HazelCast

