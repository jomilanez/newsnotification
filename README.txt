Adding a percolator query

 curl -XPUT 'localhost:9200/localnews/.percolator/1' -d '{
  "query": {
    "match": {
      "category": "polizeimeldungen"
    }
  }
}'


 curl -XPUT 'http://localhost:9200/localnews/news_item/_mapping' -d @localnews.mapping.json
 
  curl -XDELETE 'http://localhost:9200/localnews'	
  
   curl -X PUT 'http://localhost:9200/localnews' -d @index.json
   
   
curl -XGET 'localhost:9200/localnews/news_item/_percolate' -d '{"doc" : {"category": "kiezleben"}}'
