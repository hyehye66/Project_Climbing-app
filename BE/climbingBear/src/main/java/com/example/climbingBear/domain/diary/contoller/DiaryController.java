package com.example.climbingBear.domain.diary.contoller;

import com.example.climbingBear.domain.diary.dto.DiaryPostReqDto;
import com.example.climbingBear.domain.diary.dto.DiaryPostResDto;
import com.example.climbingBear.domain.diary.dto.DiaryUpdateReqDto;
import com.example.climbingBear.domain.diary.service.DiaryService;
import com.example.climbingBear.domain.user.dto.SignupReqDto;
import com.example.climbingBear.domain.user.service.UserService;
import com.example.climbingBear.global.common.CommonResponse;
import com.example.climbingBear.global.jwt.AccessTokenInterceptor;
import com.example.climbingBear.global.jwt.JwtProvider;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
@Slf4j
public class DiaryController {
    private final DiaryService diaryService;
    private final JwtProvider jwtProvider;


    @PostMapping
    @ApiOperation(value = "등산 계획 저장", notes = "year, month, day, mntnSeq 입력, header에 token 입력")
    public ResponseEntity<CommonResponse> saveDiary(HttpServletRequest request, @RequestBody DiaryPostReqDto dto) throws Exception {
        Long userSeq = jwtProvider.getUserSeqFromRequest(request);
        return new ResponseEntity<>(CommonResponse.getSuccessResponse(diaryService.diarySave(dto, userSeq)), HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation(value = "등산 계획 조회", notes = "header에 token 입력")
    public ResponseEntity<CommonResponse> getDiary(HttpServletRequest request) throws Exception {
        Long userSeq = jwtProvider.getUserSeqFromRequest(request);
        return new ResponseEntity<>(CommonResponse.getSuccessResponse(diaryService.myDiarylist(userSeq)), HttpStatus.OK);
    }

    @PutMapping
    @ApiOperation(value = "등산 계획 수정", notes = "year, month, day, mntnSeq 입력, header에 token 입력")
    public ResponseEntity<CommonResponse> editDiary(HttpServletRequest request, @RequestBody DiaryUpdateReqDto dto) throws Exception {
        Long userSeq = jwtProvider.getUserSeqFromRequest(request);
        return new ResponseEntity<>(CommonResponse.getSuccessResponse(diaryService.diaryUpdate(dto, userSeq)), HttpStatus.OK);
    }

    @DeleteMapping
    @ApiOperation(value = "등산 계획 삭제", notes = "params에 diarySeq 입력")
    public ResponseEntity<CommonResponse> deleteDiary(HttpServletRequest request, @RequestParam("diarySeq")Long diarySeq) throws Exception {
        Long userSeq = jwtProvider.getUserSeqFromRequest(request);
        diaryService.diaryDelete(userSeq, diarySeq);
        return null;
    }
}