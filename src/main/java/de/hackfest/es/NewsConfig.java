package de.hackfest.es;

import org.apache.log4j.Logger;

import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: abaranga
 * Date: 28.05.14
 * Time: 13:28
 * To change this template use File | Settings | File Templates.
 */
public class NewsConfig {

    private final static Logger LOGGER = Logger.getLogger(NewsConfig.class);

    private String indexName;
    private String esNodeName;
    private String esClusterName;
    private String esZenDiscoveryHosts;
    private Properties esProperties;

    public NewsConfig(Properties inProperties) {

        // get the index name
        indexName = inProperties.getProperty(Constants.INDEX_NAME);
        esNodeName = inProperties.getProperty(Constants.ES_NODE_NAME);
        esClusterName = inProperties.getProperty(Constants.ES_CLUSTER_NAME);
        esZenDiscoveryHosts = inProperties.getProperty(Constants.ES_ZEN_DISCOVERY_HOSTS);

        // filters only the ES properties
        esProperties = filterProperties(Constants.ES_PREFIX, inProperties);
        LOGGER.info(" Application started with ELASTICSEARCH WRITER CONFIG  " + toString());

    }

    public String getIndexName() {
        return indexName;
    }

    public String getEsNodeName() {
        return esNodeName;
    }

    public String getEsClusterName() {
        return esClusterName;
    }

    public String getEsZenDiscoveryHosts() {
        return esZenDiscoveryHosts;
    }

    public Properties getEsProperties() {
        return esProperties;
    }

    private static final class Constants {

        private static final String INDEX_NAME = "index.name";

        private static final String ES_PREFIX = "es";
        // this are Elasticsearch properties which are REQUIRED to work correctly
        private static final String ES_NODE_NAME = "es.node.name";
        private static final String ES_CLUSTER_NAME = "es.cluster.name";
        private static final String ES_ZEN_DISCOVERY_HOSTS = "es.discovery.zen.ping.unicast.hosts";

    }

    public static Properties filterProperties(String prefix, Properties properties) {

        Properties filteredProperties = new Properties();

        if (properties == null || properties.isEmpty()) {
            return filteredProperties;
        }

        for (Object prop : properties.keySet()) {

            String propertyName = String.valueOf(prop);
            if (propertyName.startsWith(prefix)) {
                String filtered = propertyName.substring(prefix.length() + 1, propertyName.length());
                filteredProperties.put(filtered, properties.get(prop));
            }

        }
        return filteredProperties;
    }

    @Override
    public String toString() {
        return "ElasticsearchWriterConfig {" + "indexName='" + indexName + '\'' + ", esNodeName='" + esNodeName + '\'' + ", esClusterName='"
                + esClusterName + '\'' + ", esProperties=" + esProperties + '}';
    }
}
