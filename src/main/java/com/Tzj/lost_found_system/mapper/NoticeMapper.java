package com.Tzj.lost_found_system.mapper;

import com.Tzj.lost_found_system.pojo.Notice;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoticeMapper {
    // PageHelper 分页插件：page/count 参数由 Service 层传入供分页使用，SQL 无需手写 LIMIT
    @Select("select * from notice left join user on notice.userID=user.id order by notice.id desc")
    List<Notice> getAll(@Param("page")Integer page,@Param("count")Integer count);

    List<Notice> getNotice(@Param("page") Integer page,@Param("count")Integer count,@Param("id") Integer id,@Param("title") String title,@Param("releaseDate") String releaseDate,@Param("userID") Integer userID);

    int editNotice(Notice notice);
    @Insert("insert into notice(title,userID) values (#{title},#{userID})")
    int addNotice(Notice notice);
    @Delete("delete from notice where id=#{id}")
    int deleteNotice(@Param("id")Integer id);
}
