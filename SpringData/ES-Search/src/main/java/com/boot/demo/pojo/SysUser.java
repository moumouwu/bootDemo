package com.boot.demo.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Map;


/**
 * @author binSin
 * @date 2021/9/2
 */
@Data
@Document(indexName = "user_index",type = "user")//  indexName:  用来指定索引名称 type:用来指定索引类型
public class SysUser implements java.io.Serializable {



    private static final long serialVersionUID = 1L;

    /**
     * @id 表示文档的唯一标识
     */
    @Id
    private Integer id;

    /**
     * @Field 字段的映射
     * type:类型
     * analyzer:指定分词器
     * Text： 会分词，然后进行索引 支持模糊、精确查询 不支持聚合
     * keyword：不进行分词，直接索引 支持模糊、精确查询 支持聚合
     */
    @Field(type = FieldType.Keyword)
    private String username;

    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String nickname;

    private Map<String, Object> specMap;

}
