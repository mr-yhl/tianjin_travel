package com.itheima.travel.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 设计pageBean对象,用于保存页面页码的数据
 * @param <E> 泛型
 */
@Data
@NoArgsConstructor
public class PageBean<E> {
    // 总记录数
    private Integer totalCount;

    // 总页数
    private Integer totalPage;

    // 查询到的结果集
    private List<E> list;

    // 当前页
    private Integer pageNum;

    // 每页的个数
    private Integer pageSize;

    // 设置分页的开始
    private Integer begin;
    // 设置分页的结束
    private Integer end;

    public PageBean(Integer pageNum, Integer pageSize,Integer totalCount, List list) {
        this.totalCount = totalCount;
        this.list = list;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalPage = (int) Math.ceil(totalCount * 1.0 /pageSize);
        JiSuan();
    }

    public void JiSuan(){
        if (totalPage < 10){
            begin = 1;
            end = totalPage;
        }else {
            begin = pageNum - 5;
            end = pageNum + 4;
            if (begin < 1){
                begin = 1;
                end = 10;
            }
            if (end > totalPage){
                end = totalPage;
                begin = totalPage - 9;
            }
        }

    }
}
