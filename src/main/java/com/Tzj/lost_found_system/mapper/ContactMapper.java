package com.Tzj.lost_found_system.mapper;

import com.Tzj.lost_found_system.pojo.Contact;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ContactMapper {
    @Select("select * from contact")
    List<Contact> getAll(Integer page, Integer count);

    List<Contact> getContact(@Param("page") Integer page, @Param("count") Integer count, @Param("id") Integer id, @Param("content") String content, @Param("contactTime") String contactTime, @Param("lostID") Integer lostID, @Param("userID") Integer userID,@Param("itemsUserID") Integer itemsUserID);

    @Update("update contact set content=#{content} where id=#{id}")
    int editContact(Contact contact);


    int addContact(Contact contact);

    @Delete("delete from contact where id=-#{id}")
    int deleteContact(@Param("id") Integer id);

    List<Contact> getFoundContact(@Param("page") Integer page, @Param("count") Integer count, @Param("id") Integer id, @Param("content") String content, @Param("contactTime") String contactTime, @Param("userID") Integer userID, @Param("foundID") Integer foundID,@Param("itemsUserID") Integer itemsUserID);
}
