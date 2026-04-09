package com.bird_forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bird_forum.domain.po.Notice;
import org.apache.ibatis.annotations.Mapper;

/**
* @author lisa
* @description 针对表【notice(通知)】的数据库操作Mapper
* @createDate 2026-04-05 16:18:07
* @Entity com.bird_forum.Notice
*/
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {

}




