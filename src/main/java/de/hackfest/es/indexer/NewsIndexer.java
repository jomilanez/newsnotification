package de.hackfest.es.indexer;

import org.apache.log4j.Logger;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;

/**
 * Created with IntelliJ IDEA. User: abaranga Date: 28.05.14 Time: 11:59 To change this template use File | Settings | File Templates.
 */
public class NewsIndexer {
	private final static Logger LOGGER = Logger.getLogger(NewsIndexer.class);
	private final Client esClient;
	private String indexName;


	public NewsIndexer(Client esClient, String indexName) {
		this.indexName = indexName;
		this.esClient = esClient;
	}

	public void index(final String newsJson) {

		try {
			final IndexRequest indexRequest = new IndexRequest(indexName, "news_item");
			indexRequest.source(newsJson);

			IndexResponse response = esClient.index(indexRequest).actionGet();
			LOGGER.info("Success on Indexing document." + response.getId());

		} catch (ElasticsearchException ese) {
			LOGGER.error("Exception on indexing", ese);
		}

	}

	public void close() {
		if (esClient != null) {
			esClient.close();
		}
	}

}
