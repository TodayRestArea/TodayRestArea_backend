package com.todayrestarea.diary.api;

import com.todayrestarea.common.dto.BaseException;
import com.todayrestarea.diary.entity.Diary;
import com.todayrestarea.diary.service.DiaryService;
import com.todayrestarea.user.util.jwt.JwtAuthTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.todayrestarea.common.dto.ComResponseDto;
import com.todayrestarea.diary.model.DiaryRes;
import com.todayrestarea.diary.model.PostDiaryRequest;

import javax.validation.Valid;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.todayrestarea.common.ErrorCode.*;
import static com.todayrestarea.utils.ValidationRegex.isRegexDate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/diarys")
public class DiaryApi {

    final private DiaryService diaryService;

    final private JwtAuthTokenProvider jwtAuthTokenProvider;
    final private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 일기작성 API
     * [POST] /diarys
     */
    @PostMapping("")
    public ComResponseDto<DiaryRes> createDiary(@RequestHeader("Authorization") String jwtToken, @Valid @RequestBody PostDiaryRequest postDiaryRequest, BindingResult bindingResult){
        try {
            // jwt 복호화 => user정보 얻기
            Long userId = jwtAuthTokenProvider.getPayload(jwtToken).getUserId();

            if (bindingResult.hasErrors())
                throw new BaseException(BAD_REQUEST_PARAMS);

            //date validate
            Date date;
            try {
                date = format.parse(postDiaryRequest.getCreatedDate());
            } catch (ParseException e) {
                throw new BaseException(DATE_PARSE_FAIL);
            }
            Date today = new Date();
            if (date.after(today))
                throw new BaseException(BAD_REQUEST_WRONG_DATE);

            // 일기 생성
            Diary diary = diaryService.createDiary(userId, date, postDiaryRequest);
            DiaryRes  diaryRes = new DiaryRes(diary.getDiaryId());
            return ComResponseDto.success(diaryRes);

        } catch (BaseException exception)
        {
            return ComResponseDto.error(exception.getErrorCode());
        }
    }

//    /**
//     * 일기상세 조회 API
//     * [GET] /diarys
//     */
//    @GetMapping("/{diaryIdx}/details")
//    public ComResponseDto<DiaryRes> getDiaryDetail(@PathVariable("diaryIdx") int diaryIdx){
//        // TODO : accessToken 복호화 => userIdx정보 얻기
//
////        try {
////
////
////        } catch (BaseException exception)
////        {
////            return ComResponseDto.error(exception.getErrorCode());
////        }
//
//    }
}
