package cn.xpleaf.lucene;

import static org.junit.Assert.*;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class LuceneTest {

	// 查看标准分析器的分词效果
	@Test
	public void testTokenStream() throws Exception {
		// 创建一个标准分析器对象
		Analyzer analyzer = new IKAnalyzer();
		// 获得 tokenStream 对象
		// 第一个参数:域名，可以随便给一个
		// 第二个参数:要分析的文本内容
		TokenStream tokenStream = analyzer.tokenStream("test",
				"希望母校的学子们可以考过高富帅，金榜提名！我是你们的坚实后盾！");
		// 添加一个引用，可以获得每个关键词
		CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
		// 添加一个偏移量的引用，记录了关键词的开始位置以及结束位置
		OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
		// 将指针调整到列表的头部
		tokenStream.reset();
		// 遍历关键词列表，通过 incrementToken 方法判断列表是否结束
		while (tokenStream.incrementToken()) {
			// 关键词的起始位置
			System.out.println("start->" + offsetAttribute.startOffset()); // 取关键词
			System.out.println(charTermAttribute);
			// 结束位置
			System.out.println("end->" + offsetAttribute.endOffset());
		}
		tokenStream.close();

	}

	@Test
	public void searchIndex() throws Exception {
		// 指定索引库的位置
		Directory directory = FSDirectory.open(new File("/Users/yeyonghao/Desktop/index"));
		// 创建indexReader对象
		IndexReader indexReader = DirectoryReader.open(directory);
		// 创建indexSearch对象
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		// 创建查询
		Query query = new TermQuery(new Term("fileName", "apache"));
		// 执行查询
		TopDocs topDocs = indexSearcher.search(query, 10);
		// 查询结果的总条数
		System.out.println("查询结果的总条数：" + topDocs.totalHits);
		// 遍历查询结果
		// topDocs.scoreDocs存储了document对象的id
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
		// 关闭indexReader对象
		indexReader.close();
	}

	@Test
	public void createIndex() throws Exception {
		// 指定索引库存放的位置
		Directory directory = FSDirectory.open(new File("/Users/yeyonghao/Desktop/index"));
		// 创建一个标准分析器
//		Analyzer analyzer = new StandardAnalyzer();
		Analyzer analyzer = new IKAnalyzer();
		// 创建indexWriterConfig对象
		IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, analyzer);
		// 1.创建indexWriter对象
		IndexWriter indexWriter = new IndexWriter(directory, config);
		File dir = new File("/Users/yeyonghao/Desktop/searchsource");
		// 3.创建field对象
		for (File f : dir.listFiles()) {
			// 文件名
			String fileName = f.getName();
			Field fileNameField = new TextField("fileName", fileName, Store.YES);

			// 文件内容
			String fileContent = FileUtils.readFileToString(f);
			Field fileContentField = new TextField("fileContent", fileContent, Store.YES);

			// 文件路径
			String filePath = f.getPath();
			// 不分析，不索引，只存储
			Field filePathField = new StoredField("filePath", filePath);

			// 文件大小
			long fileSize = FileUtils.sizeOf(f);
			// 分析，索引，存储
			Field fileSizeField = new LongField("fileSize", fileSize, Store.YES);

			// 2.创建document对象
			Document document = new Document();
			document.add(fileNameField);
			document.add(fileContentField);
			document.add(filePathField);
			document.add(fileSizeField);

			// 4.创建索引，并使用indexWriter对象将document对象写入索引库
			indexWriter.addDocument(document);
		}
		// 关闭indexWriter
		indexWriter.close();
	}
}
