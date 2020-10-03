# ElasticSearchProject


### Download Elastic Search Docker image 

```
docker network create elasticsearc-network
docker run -d --name elasticsearch --net elasticsearc-network -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" elasticsearch:7.9.2

```

### Using Postman  create indexes and nsert data
```

PUT  : localhost:9200/school

Post : localhost:9200/school/_doc/10
Request: {
         "name":"Saint Paul School", "description":"ICSE Afiliation",
         "street":"Dawarka", "city":"Delhi", "state":"Delhi", "zip":"110075",
         "location":[28.5733056, 77.0122136], "fees":5000,
         "tags":["Good Faculty", "Great Sports"], "rating":"4.5"
          }

Post : localhost:9200/school/_doc/16
Request: {
         "name":"Crescent School", "description":"State Board Affiliation",
         "street":"Tonk Road",
         "city":"Jaipur", "state":"RJ", "zip":"176114","location":[26.8535922,75.7923988],
         "fees":2500, "tags":["Well equipped labs"], "rating":"4.5"
          }
```
