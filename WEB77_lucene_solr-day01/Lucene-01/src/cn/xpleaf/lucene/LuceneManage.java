package cn.xpleaf.lucene;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class LuceneManage {

	IndexWriter indexWriter;

	// 初始化indexWriter
	@Before
	public void init() throws Exception {
		// 指定索引库存放的位置
		Directory directory = FSDirectory.open(new File("/Users/yeyonghao/Desktop/index"));
		// 创建一个标准分析器
		// Analyzer analyzer = new StandardAnalyzer();
		Analyzer analyzer = new IKAnalyzer();
		// 创建indexWriterConfig对象
		IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, analyzer);
		// 1.创建indexWriter对象
		this.indexWriter = new IndexWriter(directory, config);
	}

	// 使用MutilFieldQueryParser指定多个默认搜索域
	@Test
	public void testMultiFiledQueryParser() throws Exception {
		IndexSearcher indexSearcher = getIndexSearcher(); 
		// 可以指定默认搜索的域是多个
		String[] fields = { "fileName", "fileContent" };
		// 创建一个 MulitFiledQueryParser 对象
		MultiFieldQueryParser queryParser = new MultiFieldQueryParser(fields, new IKAnalyzer());
		Query query = queryParser.parse("lucene apache");
		System.out.println(query);
		// 执行查询
		printResult(query, indexSearcher);
	}

	// 使用QueryParser查询
	@Test
	public void testQueryParser() throws Exception {
		IndexSearcher indexSearcher = getIndexSearcher();
		// 创建 queryparser 对象
		// 第一个参数默认搜索的域
		// 第二个参数就是分析器对象
		QueryParser queryParser = new QueryParser("fileContent", new IKAnalyzer());
		Query query = queryParser.parse("Lucene 是 java 开发的"); // 分词器还会对查询的内容进行分词，然后再进行查询
		// 执行查询
		printResult(query, indexSearcher);
	}

	// 组合条件查询
	@Test
	public void testBooleanQuery() throws Exception {
		IndexSearcher indexSearcher = getIndexSearcher();
		// 创建一个布尔查询对象
		BooleanQuery query = new BooleanQuery();
		// 创建第一个查询条件
		Query query1 = new TermQuery(new Term("fileName", "apache"));
		Query query2 = new TermQuery(new Term("fileName", "lucene"));
		// 组合查询条件
		query.add(query1, Occur.MUST);
		query.add(query2, Occur.SHOULD);
		// 执行查询
		printResult(query, indexSearcher);
	}

	// 数值范围查询
	@Test
	public void testNumericRangeQuery() throws Exception {
		IndexSearcher indexSearcher = getIndexSearcher();
		// 创建查询
		// 参数:
		// 1.域名
		// 2.最小值
		// 3.最大值
		// 4.是否包含最小值
		// 5.是否包含最大值
		Query query = NumericRangeQuery.newLongRange("fileSize", 47l, 100l, true, true); // 执行查询
		printResult(query, indexSearcher);
	}

	// 查询索引目录中的所有文档
	@Test
	public void testMatchAllDocsQuery() throws Exception {
		IndexSearcher indexSearcher = getIndexSearcher(); // 创建查询条件
		Query query = new MatchAllDocsQuery();
		// 执行查询
		printResult(query, indexSearcher);
	}

	// 修改索引库
	@Test
	public void updateIndex() throws Exception {
		// 创建一个document对象
		Document document = new Document();
		// 向 document 对象中添加域
		// 不同的 document 可以有不同的域，同一个 document 可以有相同的域
		document.add(new TextField("fileName", "要更新的文档", Store.YES));
		document.add(new TextField("fileContent",
				"2013年11月18日 -Lucene 简介 Lucene 是一个基于 Java 的" + "全文信息检索工具包,它不是一个完整的搜索应用程序,而是为你的应用程序 供索引和搜索功能。",
				Store.YES));
		indexWriter.updateDocument(new Term("fileName", "apache"), document);
		// 上面的操作，会先找到fileName:apache域所属的文档，然后删除这些文档，最后再添加新的文档对象
		// 关闭 indexwriter
	}

	// 根据查询条件删除索引
	@Test
	public void deleteIndexByQuery() throws Exception {
		// 创建一个查询条件
		Query query = new TermQuery(new Term("fileName", "apache"));
		// 根据查询条件删除
		indexWriter.deleteDocuments(query);
		// 关闭 indexwriter
	}

	// 删除全部索引
	@Test
	public void deleteAllIndex() throws Exception {
		// 删除全部索引
		indexWriter.deleteAll();
		// 关闭 indexwriter
	}

	@After
	public void after() throws Exception {
		// 必须要关闭indexWriter才会使修改操作（包括删除操作）生效
		this.indexWriter.close();
	}

	// 获得indexSearcher
	public IndexSearcher getIndexSearcher() throws Exception {
		// 指定索引库的位置
		Directory directory = FSDirectory.open(new File("/Users/yeyonghao/Desktop/index"));
		// 创建indexReader对象
		IndexReader indexReader = DirectoryReader.open(directory);
		// 创建indexSearch对象
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);

		return indexSearcher;
	}

	// 输出打印topDocs对象
	public void printResult(Query query, IndexSearcher indexSearcher) throws Exception {
		TopDocs topDocs = indexSearcher.search(query, 10);
		for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
			// scoreDoc.doc属性就是document对象的id
			// 根据document的id找到document对象
			Document document = indexSearcher.doc(scoreDoc.doc);
			System.out.println("fileName:" + document.get("fileName"));
			// System.out.println("fileContent:" + document.get("fileContent"));
			System.out.println("filePath:" + document.get("filePath"));
			System.out.println("fileSize:" + document.get("fileSize"));
			System.out.println("=====================");
		}
		// 关闭indexReader
		indexSearcher.getIndexReader().close();
	}
}
