package com.baizhi;

import com.baizhi.entity.Poetries;
import com.baizhi.service.PoetriesService;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TssApplicationTests {

    @Autowired
    private PoetriesService poetriesService;

    @Test
    public void createAllPoetriesIndex() throws IOException {
        //1.指定索引的存储目录
        FSDirectory fsDirectory = FSDirectory.open(Paths.get("D:\\学习\\java\\frameWork\\后期项目\\后期项目\\扩展知识\\lucene\\索引目录\\03"));

        //2.获得索引写入器
        //使用IK中文分词器
        Analyzer analyzer = new  IKAnalyzer();
        //索引写入器配置
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        //获得索引写入器
        IndexWriter indexWriter = new IndexWriter(fsDirectory,config);

        //获得诗歌集合
        List<Poetries> poetriess = poetriesService.findAllPoetries();
        //建立文档
        Document document = null;
        for (Poetries poetries : poetriess) {
            document = new Document();
            //初始化文档
            document.add(new IntField("id",poetries.getId(), Field.Store.YES));
            document.add(new StringField("name",poetries.getPoets().getName(), Field.Store.YES));
            document.add(new StringField("title",poetries.getTitle(), Field.Store.YES));
            document.add(new TextField("content",poetries.getContent(), Field.Store.YES));
            //针对文档生成索引
            indexWriter.addDocument(document);
        }
        //提交，关流
        indexWriter.commit();
        indexWriter.close();
    }

    @Test
    public void test1(){
        List<Poetries> poetriess = poetriesService.findAllPoetries();
        poetriess.forEach(poetries -> {
            System.out.println(poetries);
        });
    }
}

