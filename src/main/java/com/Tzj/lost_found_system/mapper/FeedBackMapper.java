package com.Tzj.lost_found_system.mapper;

import com.Tzj.lost_found_system.pojo.FeedBack;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FeedBackMapper {
    // PageHelper 分页插件：SQL 中无需 LIMIT，分页由 Service 层的 PageHelper.startPage() 拦截实现
    @Select("select * from feedback left join user on feedback.userID=user.id order by feedback.id desc")
    List<FeedBack> getAll(@Param("page") Integer page, @Param("count") Integer count);

    List<FeedBack> getFeedBack(@Param("page") Integer page, @Param("count") Integer count, @Param("id") Integer id, @Param("content") String content, @Param("released") String released, @Param("userID") Integer userID);

    @Insert("insert into feedback(content,userID) values(#{content},#{userID})")
    int addFeedBack(FeedBack feedBack);

    @Update("update feedback set content=#{content} where id=#{id}")
    int editFeedBack(FeedBack feedBack);

    @Delete("delete from feedback where id=#{id}")
    int deleteFeedBack(@Param("id") Integer id);
    @Select("select count(*) from feedback group by released order by released desc limit 4")
    List<Integer> getData();
    @Select("select distinct released from feedback order by released desc limit 4")
    List<String> getDate();
}
