package es;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.Operator;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesResponse;
import org.elasticsearch.action.admin.indices.alias.exists.AliasesExistResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.bucket.filter.Filter;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.alibaba.fastjson.JSONObject;


public class ESdemo {
	private ESdemo() {
	}

	private volatile static TransportClient client;

	/**
	 * 获取es 客户端
	 * 
	 * @param hosts
	 *            集群IP地址
	 * @return 客户端实例
	 */
	@SuppressWarnings("unchecked")
	public static TransportClient getClient(String[] hosts) throws Exception{

		if (client == null) {
			synchronized (ESdemo.class) {
				if (client == null) {
					Settings settings = Settings.builder()
//					        .put("cluster.name", "my-application").put("client.transport.sniff", false).build();
					.put("cluster.name", "elasticsearch").put("client.transport.sniff", false).build();
					client = new PreBuiltTransportClient(settings);
					if (hosts.length > 0) {
						for (String host : hosts) {
						try {
						     client = client.addTransportAddress(
                           new TransportAddress(InetAddress.getByName(host),9361));
						  } catch (UnknownHostException e) {
								throw e;
						  }
						}
					}
//					Log.logger.info("连接成功");
					System.out.println("-------");
				}
			}
		}
		return client;
	}

	public static IndicesAdminClient getIndicesAdminClient(
			TransportClient client) {
		return client.admin().indices();
	}

	/**
	 * 创建索引 indexName 相当于创建数据库 indexName
	 * 
	 * @param client
	 * @param indexName
	 * @return
	 * @throws IOException 
	 */
	public static boolean createIndex(TransportClient client, String indexName,String type) throws IOException {
		if (!isIndexExists(client, indexName)) {
			CreateIndexResponse response = getIndicesAdminClient(client)
					.prepareCreate(indexName.toLowerCase()).get();
			
			XContentBuilder builder=XContentFactory.jsonBuilder()  
	                .startObject()  
	                .startObject(type)  
	                .startObject("properties")  
	                .startObject("userid").field("type","string").field("store", "yes").field("analyzer","standard").field("index","not_analyzed").endObject()  
	                .startObject("category").field("type", "string").field("store", "yes").field("analyzer","standard").field("index","not_analyzed").endObject()  
	                .startObject("originalName").field("type", "string").field("store", "yes").field("analyzer","standard").field("index","not_analyzed").endObject()  
	                .startObject("afterModifyName").field("type", "string").field("store", "yes").field("analyzer","standard").field("index","not_analyzed").endObject()  
	                .endObject()  
	                .endObject()  
	                .endObject();  
//			System.out.println(builder.string());
			
			
	        
	        PutMappingRequest mappingRequest = Requests.putMappingRequest(indexName).source(builder).type(indexName); 
			
			return response.isAcknowledged();
		}
		return Boolean.FALSE;
	}
	
	public static boolean createIndex2(TransportClient client, String indexName,String type) throws IOException {
		if (!isIndexExists(client, indexName)) {
			CreateIndexResponse response = getIndicesAdminClient(client)
					.prepareCreate(indexName.toLowerCase()).get();
			
//			PutMappingRequest mapping = Requests.putMappingRequest(indexName).type(type).source(ZhidaoMapping.getMapping());

			XContentBuilder builder=XContentFactory.jsonBuilder()  
					.startObject()  
					.startObject(type)  
					.startObject("properties")  
					.startObject("userid").field("type","string").field("store", "yes").field("analyzer","standard").field("index","not_analyzed").endObject()  
					.startObject("device").field("type", "string").field("store", "yes").field("analyzer","standard").field("index","not_analyzed").endObject()  
					.startObject("scene").field("type", "string").field("store", "yes").field("analyzer","standard").field("index","not_analyzed").endObject()  
					.endObject()  
					.endObject()  
					.endObject();  
//			System.out.println(builder.string());
			
			
			PutMappingRequest mappingRequest = Requests.putMappingRequest(indexName).source(builder).type(indexName); 
			
			return response.isAcknowledged();
		}
		return Boolean.FALSE;
	}

	/**
	* 给索引增加mapping。
	* 
	* @param index
	*            索引名
	* @param type
	*            mapping所对应的type
	*/
	public static void addMapping(TransportClient client, String index, String type) {
		try {
			// 使用XContentBuilder创建Mapping
			XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("properties").startObject()
			.field("userid").startObject().field("index", "not_analyzed").field("type", "string").endObject()
			.field("scene").startObject().field("index", "not_analyzed").field("type", "string").endObject()
			.endObject().endObject();
//			System.out.println(builder.string());
			PutMappingRequest mappingRequest = Requests.putMappingRequest(index).source(builder).type(type);
			client.admin().indices().putMapping(mappingRequest).actionGet();
		} catch (ElasticsearchException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 为索引创建别名
	 * 
	 * @param client
	 * @param index
	 * @param alias
	 * @return
	 */
	public static boolean addAliasIndex(TransportClient client,
			String indexName, String aliasNmae) {
			IndicesAliasesResponse response = getIndicesAdminClient(client)
					.prepareAliases().addAlias(indexName, aliasNmae).get();
		return response.isAcknowledged();
	}
	
	/**
	 * 判断别名是否存在
	 * 
	 * @param client
	 * @param aliases
	 * @return
	 */
	public static boolean isAliasExist(TransportClient client,
			String... aliases) {
		AliasesExistResponse response = getIndicesAdminClient(client)
				.prepareAliasesExist(aliases).get();
		return response.isExists();
	}

	/**
	 * //索引删除别名
	 * 
	 * @param client
	 * @param indexName
	 * @param aliasNmae
	 * @return
	 */
	public static boolean removeAliasIndex(TransportClient client,
			String indexName, String aliasNmae) {
//		if (isIndexExists(client, indexName)) {
			IndicesAliasesResponse response = getIndicesAdminClient(client)
					.prepareAliases().removeAlias(indexName, aliasNmae).get();
			return response.isAcknowledged();
//		}
//		return Boolean.FALSE;

	}

	/**
	 * 创建自定义mapping的索引 前提是得先要创建索引 建立mapping (相当于建立表结构)
	 * 
	 * @param client
	 * @param indexName
	 * @param typeName
	 * @param mapping
	 * @return
	 */
	public static boolean setIndexMapping(TransportClient client,
			String indexName, String typeName, String mapping) {
		PutMappingResponse response = getIndicesAdminClient(client)
				.preparePutMapping(indexName.toLowerCase()).setType(typeName)
				.setSource(mapping, XContentType.JSON).get();
		return response.isAcknowledged();
	}

	/**
	 * 索引是否存在
	 * 
	 * @param client
	 * @param indexName
	 * @return
	 */
	public static boolean isIndexExists(TransportClient client, String indexName) {
		IndicesExistsRequest request = new IndicesExistsRequest(
				indexName.toLowerCase());
		IndicesExistsResponse response = getIndicesAdminClient(client).exists(
				request).actionGet();
		return response.isExists();
	}

	/**
	 * 删除索引
	 * 
	 * @param client
	 * @param indexName
	 * @return
	 */
	public static boolean deleteIndex(TransportClient client, String indexName) {
		if (isIndexExists(client, indexName)) {
			DeleteIndexResponse response = getIndicesAdminClient(client)
					.prepareDelete(indexName.toLowerCase()).get();
			return response.isAcknowledged();
		}
		return Boolean.FALSE;
	}
	
	/**
	 * 插入数据 只支持String,map,json,XContentBuilder
	 * @param index
	 * @param type
	 * @param id
	 * @param source
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static IndexResponse insertData(String index,String type,String id,Object source){
		IndexRequestBuilder ird=client.prepareIndex(index, type, id);
		IndexResponse response=null;
		if(source instanceof Map){
			response=ird.setSource((Map<String,Object>)source).get();
		}else if(source instanceof String){
			response=ird.setSource((String)source).get();
		}else if(source instanceof XContentBuilder){
			response=ird.setSource((XContentBuilder)source).get();
		}else if(source instanceof JSONObject){
			response=ird.setSource((JSONObject)source).get();
		}
		if(response!=null){
			System.out.println("index名：" + response.getIndex() + " -- " + "type名：" + response.getType() + " -- " + "id为：" + response.getId());
		}
		return response;
	}
	
	/**
	 * 插入数据 只支持String,map,json,XContentBuilder
	 * @param index
	 * @param type
	 * @param id
	 * @param source
	 * @return
	 * @throws IOException 
	 */
	public static void insertBatchData(String index,String type,String id,TransportClient tc) throws IOException{
		String path = "D:\\huang\\Lip_词典_1.22\\UsedDomains.txt\\";
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "utf-8"));
		String json = null;
		String device = "device";
		int count = 0;
		Map<String, String> source = new HashMap<String, String>();
		//开启批量插入
		BulkRequestBuilder bulkRequest = tc.prepareBulk();
//		bulkRequest.add(tc.prepareIndex(index, type, id).setSource(source));
		while ((json = br.readLine()) != null) {
			source.put(device, json);
			source.put("userid", "12");
			bulkRequest.add(tc.prepareIndex(index, type).setSource(source));
//			bulkRequest.add(tc.prepareIndex("ai_jzy11","smsm").setSource(json));
			//每5条提交一次
			if (count % 5==0) {
				bulkRequest.execute().actionGet();
				System.out.println("提交了：" + count);
			}
			System.out.println(source.get("device"));
			count++;
		}
		bulkRequest.execute().actionGet();
        System.out.println("插入完毕");
        br.close();
	}
	
	public static List<String> query(String index, String type,String query) {

		 SearchResponse searchResponse = client.prepareSearch(index)  
	                .setTypes(type)  
//	                .setQuery(QueryBuilders.matchAllQuery()) //查询所有  
//	                .setQuery(QueryBuilders.matchQuery("device", "打开小白")) //根据tom分词查询name,默认or  
//	                .setQuery(QueryBuilders.multiMatchQuery("songs", "singers", "ablums")) //指定查询的字段  
	                //.setQuery(QueryBuilders.queryString("name:to* AND age:[0 TO 19]")) //根据条件查询,支持通配符大于等于0小于等于19  
	                .setQuery(QueryBuilders.termQuery("device", query))//查询时不分词  
	                .setSearchType(SearchType.QUERY_THEN_FETCH)  
//	                .setFrom(0).setSize(10)//分页  
//	                .addSort("id", SortOrder.DESC)//排序  
	                .get();  
		 
	          
	        SearchHits hits = searchResponse.getHits();  
	        long total = hits.getTotalHits();  
	        System.out.println(total);  
	        SearchHit[] searchHits = hits.getHits();  
	        for(SearchHit s : searchHits)  
	        {  
	            System.out.println(s.getSourceAsString()+"\t"+s.getScore()+"\t");  
	        }
			return null;  
	}
	
	public static JSONObject queryPage(String index, String type,String userId,String device,String query){
//		  MatchPhraseQueryBuilder mpq1 = QueryBuilders.matchPhraseQuery("userid", "2");
//	MatchPhraseQueryBuilder mpq2 = QueryBuilders.matchPhraseQuery("deivce", query);
//	QueryBuilder qb2 = QueryBuilders.boolQuery().must(mpq1).must(mpq2);
		
//		 QueryBuilder mustQuery = QueryBuilders.boolQuery();
//		        mustQuery.must(QueryBuilders.matchAllQuery());	//返回QueryBuilder对象
//		        mustQuery.must(QueryBuilders.termQuery("userid", userid));
//		        mustQuery.must(QueryBuilders.termQuery("category", device));
//		        mustQuery.must(QueryBuilders.matchQuery("originalName", query));
//		        mustQuery.must(QueryBuilders.matchQuery("scene", query));
//        SearchResponse response = client.prepareSearch(index)
//        .setTypes(type)
//        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
//        .setQuery(mustQuery)                 // Query
//        .setFrom(0).setSize(60).setExplain(true)
//        .get();
		
//		QueryBuilder queryBuilder = QueryBuilders.matchQuery("device", "110110");
//		
//		QueryBuilder queryBuilder=QueryBuilders.multiMatchQuery(
//				type,                            
//		        userId, device);
		
		 SearchResponse response=client.prepareSearch(index).get(); 
		 SearchHits hits2 = response.getHits();
		 JSONObject js=new JSONObject();
		 for (SearchHit searchHit : hits2) {
			 Map<String,Object> map=searchHit.getSourceAsMap();
			 	js.put("userId", map.get("userId"));
	            js.put("afterModifyName", map.get("afterModifyName"));
	            js.put("originalName", map.get("originalName"));
	            StringBuffer sbBuffer=new StringBuffer();
	            for(Object key : map.keySet( )){
	            	sbBuffer.append(key+":"+map.get(key)+"\t");
//	              System.out.println(key+"-"+searchHit.getId()+"-" +source.get( key )+"-" +searchHit.getScore());
	          }
	            System.out.println(sbBuffer.toString());
		}
		
//		        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index);
//		        if (StringUtils.isNotEmpty(type)) {
//		            searchRequestBuilder.setTypes(type.split(","));
//		        }
//
//		        if (StringUtils.isNotEmpty(highlightField)) {
//		            HighlightBuilder highlightBuilder = new HighlightBuilder();
//		            // 设置高亮字段
//		            highlightBuilder.field(highlightField);
//		            searchRequestBuilder.highlighter(highlightBuilder);
//		        }

//		        searchRequestBuilder.setQuery(null);

//		        if (StringUtils.isNotEmpty(fields)) {
//		            searchRequestBuilder.setFetchSource(fields.split(","), null);
//		        }
//		        searchRequestBuilder.setFetchSource(true);

//		        if (StringUtils.isNotEmpty(sortField)) {
//		            searchRequestBuilder.addSort(sortField, SortOrder.DESC);
//		        }
//
//		        if (size != null && size > 0) {
//		            searchRequestBuilder.setSize(size);
//		        }
//
//		        // 打印的内容 可以在 Elasticsearch head 和 Kibana 上执行查询
//		        LOGGER.info("\n{}", searchRequestBuilder);

//		        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();

//		        long totalHits = searchResponse.getHits().totalHits;
//		        long length = searchResponse.getHits().getHits().length;

//		        LOGGER.info("共查询到[{}]条数据,处理数据条数[{}]", totalHits, length);

//		        if (searchResponse.status().getStatus() == 200) {
//		            // 解析对象
//		            return setSearchResponse(searchResponse, highlightField);
//		        }
        
      
//        SearchHits hits = searchResponse.getHits();
//        JSONObject js=new JSONObject();
//        for (SearchHit searchHit : hits) {
//            Map<String, Object> source = searchHit.getSourceAsMap();
//            js.put("userid", source.get("userid"));
//            js.put("afterModifyName", source.get("afterModifyName"));
//            js.put("originalName", source.get("originalName"));
//            StringBuffer sbBuffer=new StringBuffer();
//            for(Object key : source.keySet( )){
//            	sbBuffer.append(key+":"+source.get(key)+"\t");
////              System.out.println(key+"-"+searchHit.getId()+"-" +source.get( key )+"-" +searchHit.getScore());
//          }
//            System.out.println(sbBuffer.toString());
//            return js;
////           
//        }
		return null;
    }

	
}