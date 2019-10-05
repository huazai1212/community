package com.huazai.community.dto;

import java.util.LinkedList;
import java.util.List;

/**
 * 封装页面信息，包括问题信息
 */
public class PaginationDTO {
    private List<QuestionDTO> questionDTOList;//封装问题集合
    private boolean showPrevious;//前一页
    private boolean showFirstPage;//首页
    private boolean showNext;//下一页
    private boolean showEndPage;//尾页
    private Integer page;//当前页
    private Integer totalPage;//总页数
    private Integer totalCount;//总问题数
    private List<Integer> pages = new LinkedList<>();//显示的几个页码


    /**
     * @param totalQuestionCount
     * @param Currentpage
     * @param size
     */
    public void setPagination(Integer totalQuestionCount, Integer Currentpage, Integer size) {

        page = Currentpage;//将传进来的当前页赋值给对象

        totalCount=totalQuestionCount;//总问题数


        totalPage = (int) Math.ceil(totalQuestionCount * 1.0 / size);

        //使用page进行计算之前要对其进行验证，防止前端传进来的page < 1  或  page > totalPage
        if (page < 1) {
            page = 1;
        }

        if (page > totalPage) {
            page = totalPage;
        }

        //将当前页放进去
        pages.add(page);

        /**
         * 计算需要显示给前端的几个页码（假设显示7个）
         * 思路：
         *      1.将当前页（假设是5）看做中间页   2 3 4 5 6 7 8 ；
         *      2.使用循环在当前页前后分别增加三个页码；
         *      3.在前边增加的条件是 5 - i > 0   i -> 1 2 3
         *      4.同理，在后边增加的页码条件是 5 + i < 总页码（计算得出)
         *      5.所以可以使用for循环再加上if判断即可
         *      6.注意：先将当前页插入进去，当前页前后边的每次都在List末尾加入，
         *                                 当前页前边的每次在首位插入；
         *       7.频繁的插入可以使用LinkedList
         *
         */
        for (int i = 1; i <= 3; i++) {
            //在当前页前边增加页码 ,每次在首位插入；
            if (page - i > 0) {
                pages.add(0, page - i);
            }

            //当前页前后边的 每次都在List末尾加入
            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }

        //是否展示上一页按钮
        showPrevious = (page == 1) ? false : true;
        //是否展示首页按钮
        showFirstPage = (pages.contains(1)) ? false : true;
        //是否展示下一页按钮
        showNext = (page == totalPage) ? false : true;
        //是否展示尾页按钮
        showEndPage = (pages.contains(totalPage)) ? false : true;
    }


    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<QuestionDTO> getQuestionDTOList() {
        return questionDTOList;
    }

    public void setQuestionDTOList(List<QuestionDTO> questionDTOList) {
        this.questionDTOList = questionDTOList;
    }

    public boolean isShowPrevious() {
        return showPrevious;
    }

    public void setShowPrevious(boolean showPrevious) {
        this.showPrevious = showPrevious;
    }

    public boolean isShowFirstPage() {
        return showFirstPage;
    }

    public void setShowFirstPage(boolean showFirstPage) {
        this.showFirstPage = showFirstPage;
    }

    public boolean isShowNext() {
        return showNext;
    }

    public void setShowNext(boolean showNext) {
        this.showNext = showNext;
    }

    public boolean isShowEndPage() {
        return showEndPage;
    }

    public void setShowEndPage(boolean showEndPage) {
        this.showEndPage = showEndPage;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Integer> getPages() {
        return pages;
    }

    public void setPages(List<Integer> pages) {
        this.pages = pages;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

}
