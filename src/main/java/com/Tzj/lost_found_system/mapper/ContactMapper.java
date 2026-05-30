package com.Tzj.lost_found_system.mapper;

import com.Tzj.lost_found_system.pojo.Contact;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ContactMapper {
    // PageHelper 分页：SQL 不写 LIMIT，由 Service 层 PageHelper.startPage() 自动添加分页
    @Select("select * from contact")
    List<Contact> getAll(@Param("page") Integer page, @Param("count") Integer count);

    List<Contact> getContact(@Param("page") Integer page, @Param("count") Integer count, @Param("id") Integer id, @Param("content") String content, @Param("contactTime") String contactTime, @Param("lostID") Integer lostID, @Param("userID") Integer userID,@Param("itemsUserID") Integer itemsUserID);

    @Update("update contact set content=#{content} where id=#{id}")
    int editContact(Contact contact);


    int addContact(Contact contact);

    // 修复：移除多余的负号，原SQL "id=-#{id}" 会导致删除错误的记录
    @Delete("delete from contact where id=#{id}")
    int deleteContact(@Param("id") Integer id);

    List<Contact> getFoundContact(@Param("page") Integer page, @Param("count") Integer count, @Param("id") Integer id, @Param("content") String content, @Param("contactTime") String contactTime, @Param("userID") Integer userID, @Param("foundID") Integer foundID,@Param("itemsUserID") Integer itemsUserID);
}
