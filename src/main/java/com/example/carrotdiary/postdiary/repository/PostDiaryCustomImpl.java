package com.example.carrotdiary.postdiary.repository;


import static com.example.carrotdiary.post.entity.QPost.post;
import static com.example.carrotdiary.postdiary.entity.QPostDiary.postDiary;

import com.example.carrotdiary.diary.entity.DiarySearch;
import com.example.carrotdiary.postdiary.entity.PostDiary;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class PostDiaryCustomImpl implements PostDiaryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<PostDiary> findAllPostDiariesPaging(Pageable pageable) {

        List<PostDiary> content = jpaQueryFactory
                .select(postDiary)
                .from(postDiary)
                .join(postDiary.post, post).fetchJoin()
                .orderBy(postDiary.id.desc())
                .fetch();


        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(postDiary.count())
                .from(postDiary);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<PostDiary> findPostDiaryBySearch(DiarySearch diarySearch, String searchContent, Pageable pageable) {
        List<PostDiary> content = jpaQueryFactory
                .select(postDiary)
                .from(postDiary)
                .join(postDiary.post, post).fetchJoin()
                .where(getSearchPredicate(diarySearch, searchContent))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(postDiary.count())
                .from(postDiary)
                .where(getSearchPredicate(diarySearch, searchContent));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression getSearchPredicate(DiarySearch diarySearch, String content) {
        return switch (diarySearch) {
            case USER -> searchByUser(content);
            case CONTENT -> searchByContent(content);
            case USER_CONTENT -> searchByUserOrContent(content);
        };
    }

    private BooleanExpression searchByUser(String content) {
        return postDiary.post.member.nickname.containsIgnoreCase(content);
    }

    private BooleanExpression searchByContent(String content) {
        return postDiary.diaries.any().content.containsIgnoreCase(content);
    }

    private BooleanExpression searchByUserOrContent(String content) {
        return searchByUser(content).or(searchByContent(content));
    }

}
