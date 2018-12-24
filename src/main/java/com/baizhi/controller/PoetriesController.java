package com.baizhi.controller;

import com.baizhi.ViewObject.Paging;
import com.baizhi.entity.Poetries;
import com.baizhi.entity.Poets;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wltea.analyzer.lucene.IKAnalyzer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/poetries")
public class PoetriesController {

    @RequestMapping("/tSSearch")
    public String tSSearch(Integer page,String keyword, HttpServletRequest request) throws IOException, ParseException, InvalidTokenOffsetsException {
        //1.指定索引的存储目录
        FSDirectory fsDirectory = FSDirectory.open(Paths.get("D:\\学习\\java\\frameWork\\后期项目\\后期项目\\扩展知识\\lucene\\索引目录\\03"));

        //2.获得索引读取器
        IndexReader indexReader = DirectoryReader.open(fsDirectory);

        //3.获得获得索引检索器
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        //4.获得查询分析器，分析query
        IKAnalyzer analyzer = new IKAnalyzer();
        QueryParser queryParser = new QueryParser("content",analyzer);
        //检索
        Query query = queryParser.parse("content:"+keyword+"~ OR name:"+keyword+" OR title:"+keyword);

        //没有显示条数
        Integer rows = 10;
        TopDocs topDocs = null;
        if(page == null || page == 1){
            page = 1;
            //保留符合条件的前n条数据
            topDocs = indexSearcher.search(query, rows);
        }else if(page > 1){
            //获取前一页的最后一条记录的scoreDoc
            TopDocs topDocs1 = indexSearcher.search(query, (page - 1) * rows);
            ScoreDoc[] scoreDocs = topDocs1.scoreDocs;
            ScoreDoc scoreDoc = scoreDocs[scoreDocs.length - 1];
            topDocs = indexSearcher.searchAfter(scoreDoc, query, rows);
        }

        //获得符合条件的总记录数
        int totalHits = topDocs.totalHits;

        //获得总页数
        int totalPage = 0;
        if(totalHits%rows==0){
            totalPage = totalHits/rows;
        }else{
            totalPage = totalHits/rows + 1;
        }

        //分页对象
        Paging paging = new Paging(page,rows,totalHits,totalPage);
        request.setAttribute("paging",paging);

        //创建高亮对象
        Scorer scorer = new QueryScorer(query);
        //自定义高亮样式
        Formatter formatter = new SimpleHTMLFormatter("<span style='color:red'>","</span>");
        Highlighter highlighter = new Highlighter(formatter,scorer);

        //将得到的结果转为数组
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        List<Poetries> poetriess = new ArrayList<Poetries>();
        for (ScoreDoc scoreDoc : scoreDocs) {
            Poets poet = new Poets();

            //获得文档编号
            int docId = scoreDoc.doc;
            //获得编号对应的文档
            Document document = indexReader.document(docId);

            //获得高亮的最佳片段
            String name = highlighter.getBestFragment(analyzer,"name",document.get("name"));
            String title = highlighter.getBestFragment(analyzer,"title",document.get("title"));
            String content = highlighter.getBestFragment(analyzer,"content",document.get("content"));

            if(name == null){
                name = document.get("name");
            }
            if(title == null){
                title = document.get("title");
            }
            if(content == null){
                content = document.get("content");
            }

            poet.setName(name);
            Poetries poetries = new Poetries(null,title,content,poet);
            poetriess.add(poetries);
        }
        //关流
        indexReader.close();

        request.setAttribute("poetriess",poetriess);
        request.setAttribute("keyword",keyword);
        return "search";
    }
}
