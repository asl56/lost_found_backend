package com.Tzj.lost_found_system.mapper;

import com.Tzj.lost_found_system.pojo.EchartsData;
import com.Tzj.lost_found_system.pojo.Found;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FoundMapper {
    // PageHelper 分页插件在 Service 层通过 PageHelper.startPage(page,count) 接管分页
    // Mapper 层 SQL 无需 LIMIT 子句
    @Select("select * from found order by releaseDate desc")
    List<Found> getFoundAll(@Param("page") Integer page, @Param("count") Integer count);

    List<Found> getFound(@Param("page") Integer page, @Param("count") Integer count, @Param("id") Integer id, @Param("title") String title, @Param("description") String description, @Param("phone") String phone, @Param("userID") String userID, @Param("releaseDate") String releaseDate, @Param("statusID") Integer statusID);

    int editFound(Found found);

    @Insert("insert into found(title, description, phone,ItemPhoto, userID) values(#{title}, #{description}, #{phone},#{ItemPhoto}, #{userID})")
    int addFound(Found lost);

    @Delete("delete from found where id=#{id}")
    int deleteFound(@Param("id") Integer id);

    @Select("select distinct count(*) value,status.status name from found left join status on found.statusID=status.id group by statusID")
    List<EchartsData> getFoundData();
}
