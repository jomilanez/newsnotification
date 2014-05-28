package de.hackfest.es.percolator;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.percolate.PercolateResponse;
import org.elasticsearch.client.Client;

/**
 * Created with IntelliJ IDEA. User: abaranga Date: 28.05.14 Time: 12:00 To
 * change this template use File | Settings | File Templates.
 */
public class NewsPercolate {

	private Client client;
	private String indexName;

	public NewsPercolate(Client esClient, String indexName) {
		this.client = esClient;
		this.indexName = indexName;
	}

	public List<String> percolate(String newsJson) {
		client.admin().cluster().prepareHealth().setWaitForYellowStatus()
				.execute().actionGet();

		PercolateResponse response = client.preparePercolate()
				.setIndices(indexName).setDocumentType("news_item")
				.setSource(prepareDoc(newsJson)).execute().actionGet();

		List<String> results = new ArrayList<String>();
		// Iterate over the results
		for (PercolateResponse.Match match : response) {
			results.add(match.getId().toString());
			System.out.println("PERCOLATOR " + match.getId().toString());
		}
		return results;
	}

	private static String prepareDoc(String esIndexableDoc) {
		StringBuilder docSB;
		docSB = new StringBuilder().append("{").append("\"doc\"").append(":")
				.append(esIndexableDoc).append("}");
		return docSB.toString();
	}

}
