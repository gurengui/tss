package com.baizhi.service;

import com.baizhi.dao.PoetriesDAO;
import com.baizhi.entity.Poetries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PoetriesServiceImpl implements PoetriesService {

    @Autowired
    private PoetriesDAO poetriesDAO;

    //查询所有诗歌与诗人
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Poetries> findAllPoetries() {
        List<Poetries> poetriess = poetriesDAO.queryAllPoetries();
        return poetriess;
    }
}
