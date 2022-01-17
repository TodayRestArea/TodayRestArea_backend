package com.todayrestarea.diary.api;

import com.todayrestarea.common.dto.BaseException;
import com.todayrestarea.diary.model.DiaryListRes;
import com.todayrestarea.diary.service.DiaryService;
import com.todayrestarea.diary.service.DiaryServiceImpl;
import com.todayrestarea.user.entity.User;
import com.todayrestarea.user.service.UserService;
import com.todayrestarea.user.service.UserServiceImpl;
import com.todayrestarea.user.util.jwt.JwtAuthTokenProvider;
import com.todayrestarea.user.util.jwt.dto.AuthTokenPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.todayrestarea.common.dto.ComResponseDto;
import com.todayrestarea.diary.model.DiaryRes;
import com.todayrestarea.diary.model.PostDiaryRequest;

import java.util.List;
import java.util.Optional;

import static com.todayrestarea.common.ErrorCode.*;
import static com.todayrestarea.utils.ValidationRegex.isRegexDate;
import static com.todayrestarea.utils.ValidationRegex.isRegexYearMonth;

@RequiredArgsConstructor
@RestController
@RequestMapping("/diarys")
public class DiaryApi {

    final private DiaryService diaryService;
    final private UserService userService;
    final private JwtAuthTokenProvider jwtAuthTokenProvider;

    /**
     * 일기작성 API
     * [POST] /diarys
     */
    @PostMapping("")
    public ComResponseDto<DiaryRes> createDiary(@RequestHeader("Authorization") String jwtToken, @RequestBody PostDiaryRequest postDiaryRequest){
        try {
            // jwt 복호화 => user정보 얻기
            Long userId = jwtAuthTokenProvider.getPayload(jwtToken).getUserId();
            Optional<User> user = userService.findById(userId);

            // 유저가 존재하지 않음
            if (user.isEmpty())
                throw new BaseException(NOT_FOUND_USER_EXCEPTION);

            // date 형식 validation
            if(!isRegexDate(postDiaryRequest.getCratedAt()))
                throw new BaseException(BAD_REQUEST_WRONG_DATE_FORMAT_EXCEPTION);

            // 일기 생성

            DiaryRes  diaryRes = null;
            return ComResponseDto.success(diaryRes);

        } catch (BaseException exception)
        {
            return ComResponseDto.error(exception.getErrorCode());
        }
    }

    /**
     * 일기상세 조회 API
     * [GET] /diarys
     */
//    @GetMapping("/{diaryIdx}/details")
//    public ComResponseDto<DiaryRes> getDiaryDetail(@PathVariable("diaryIdx") int diaryIdx){
//        // TODO : accessToken 복호화 => userIdx정보 얻기
//
//        try {
//
//
//        } catch (BaseException exception)
//        {
//            return ComResponseDto.error(exception.getErrorCode());
//        }
//
//    }

    /**
     * 일기 월별 조회 API
     * [GET] /diarys
     */
    @GetMapping("/{year-month}")
    public ComResponseDto<List<DiaryListRes>> getDiaryList(@RequestHeader("Authorization") String jwtToken, @PathVariable("year-month") String yearMonth){
        try {
            // jwt 복호화 => user정보 얻기
            Long userId = jwtAuthTokenProvider.getPayload(jwtToken).getUserId();
            Optional<User> user = userService.findById(userId);

            // 유저가 존재하지 않음
            if (user.isEmpty())
                throw new BaseException(NOT_FOUND_USER_EXCEPTION);

            // date 형식 validation
            if(!isRegexYearMonth(yearMonth))
                throw new BaseException(BAD_REQUEST_WRONG_DATE_FORMAT_EXCEPTION);

            // 일기 생성

            List<DiaryListRes> diaryList = diaryService.getDiaryList(yearMonth);
            return ComResponseDto.success(diaryList);

        } catch (BaseException exception)
        {
            return ComResponseDto.error(exception.getErrorCode());
        }
    }
}
