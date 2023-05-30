Application runs on port 10001 : http://localhost:10001/api/v1/enrich

Application verifies the date, price, currency of the given trade data. If anything is wrong in the data, it logs the error and goes on to process next row.


#Issues in App

Application end points are tested with Postman. It's working as expected.
Attached the screenshot (screen_shot.png) from Postman

With curl, the csv file is not being read line by line. This needs further investigation.

#Further Improvements :
Cache:
Application is using default Cache config and spring default concurrent hash map. This can be improved with good eviction 
policies and cache manager like EhCache/HazelCast

