package com.wbg.Excel.dao;

import com.wbg.Excel.entity.Stu;
import java.util.List;

public interface StuMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stu
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String sId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stu
     *
     * @mbg.generated
     */
    int insert(Stu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stu
     *
     * @mbg.generated
     */
    Stu selectByPrimaryKey(String sId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stu
     *
     * @mbg.generated
     */
    List<Stu> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stu
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Stu record);
}